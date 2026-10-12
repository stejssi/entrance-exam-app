package rs.ac.bg.fon.probniprijemni.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.ac.bg.fon.probniprijemni.dto.ErrorResponseDto;

/**
 * Centralizovana obrada gresaka za sve REST kontrolere.
 * Svaka greska se vraca klijentu u jedinstvenom formatu (ErrorResponseDto).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return odgovor(HttpStatus.NOT_FOUND, ex.getMessage(), req, null);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return odgovor(HttpStatus.BAD_REQUEST, ex.getMessage(), req, null);
    }

    // Greske Bean Validation-a (@Valid na DTO-ovima u kontrolerima)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> detalji = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        return odgovor(HttpStatus.BAD_REQUEST, "Neispravni podaci u zahtevu", req, detalji);
    }

    // Sve ostale, neocekivane greske
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleOstalo(Exception ex, HttpServletRequest req) {
        log.error("Neocekivana greska na putanji {}", req.getRequestURI(), ex); // <-- OVO nedostaje
        return odgovor(HttpStatus.INTERNAL_SERVER_ERROR, "Doslo je do neocekivane greske na serveru", req, null);
    }

    private ResponseEntity<ErrorResponseDto> odgovor(HttpStatus status, String poruka,
                                                       HttpServletRequest req, List<String> detalji) {
        ErrorResponseDto telo = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                poruka,
                req.getRequestURI(),
                detalji
        );
        return ResponseEntity.status(status).body(telo);
    }

    
}
