/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.probniprijemni.dto.CenovnikDto;
import rs.ac.bg.fon.probniprijemni.service.CenovnikService;

/**
 *
 * @author Stasa
 */
@RestController
@RequiredArgsConstructor
public class CenovnikController {

    private final CenovnikService cenovnikService;

    @GetMapping("/api/cenovnik")
    public ResponseEntity<List<CenovnikDto>> getSveCene() {
        return ResponseEntity.ok(cenovnikService.getSveCene());
    }

    @PostMapping("/api/admin/cenovnik")
    public ResponseEntity<CenovnikDto> definisiCenu(@Valid @RequestBody CenovnikDto dto) {
        return ResponseEntity.ok(cenovnikService.definisiCenu(dto));
    }
}
