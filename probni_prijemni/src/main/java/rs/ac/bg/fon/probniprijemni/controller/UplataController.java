/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.probniprijemni.dto.UplataDto;
import rs.ac.bg.fon.probniprijemni.service.UplataService;

/**
 *
 * @author Stasa
 */
@RestController
@RequiredArgsConstructor
public class UplataController {

    private final UplataService uplataService;

    @PostMapping("/api/admin/{administratorId}/prijave/{prijavaId}/uplata")
    public ResponseEntity<UplataDto> evidentirajUplatu(
            @PathVariable Long administratorId,
            @PathVariable Long prijavaId) {
        return ResponseEntity.ok(uplataService.evidentirajUplatu(administratorId, prijavaId));
    }
}

