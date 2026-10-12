/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.probniprijemni.dto.VrstaIspitaDto;
import rs.ac.bg.fon.probniprijemni.service.VrstaIspitaService;

/**
 *
 * @author Stasa
 */
@RestController
@RequiredArgsConstructor
public class VrstaIspitaController {
    private final VrstaIspitaService vrstaIspitaService;

    @GetMapping("/api/vrsta-ispita")
    public ResponseEntity<List<VrstaIspitaDto>> getSve() {
        return ResponseEntity.ok(vrstaIspitaService.getSve());
    }
}
