package rs.ac.bg.fon.probniprijemni.dto;


/**
 *
 * @author Stasa
 */
import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDto(
    LocalDateTime timestamp,
    int status,
    String poruka,
    String putanja,
    List<String> detalji
) {}