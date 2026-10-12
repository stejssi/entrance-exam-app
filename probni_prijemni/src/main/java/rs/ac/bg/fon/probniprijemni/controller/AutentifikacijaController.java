/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.probniprijemni.dto.KandidatDto;
import rs.ac.bg.fon.probniprijemni.dto.KorisnikDto;
import rs.ac.bg.fon.probniprijemni.dto.LoginDto;
import rs.ac.bg.fon.probniprijemni.dto.PotvrdaKodaDto;
import rs.ac.bg.fon.probniprijemni.dto.RegistracijaDto;
import rs.ac.bg.fon.probniprijemni.service.AutentifikacijaService;

/**
 *
 * @author Stasa
 */ 
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutentifikacijaController {
   
    private final AutentifikacijaService authService;

    @PostMapping("/registracija")
    public ResponseEntity<KandidatDto> registracija(@Valid @RequestBody RegistracijaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrujKandidata(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<KorisnikDto> login(@Valid @RequestBody LoginDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    
    @PostMapping("/potvrdi-registraciju")
    public ResponseEntity<Void> potvrdiRegistraciju(@Valid @RequestBody PotvrdaKodaDto dto) {
        authService.potvrdiRegistraciju(dto);
        return ResponseEntity.ok().build();
    }
}

