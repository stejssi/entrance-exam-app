/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.probniprijemni.converter.VrstaIspitaConverter;
import rs.ac.bg.fon.probniprijemni.domain.VrstaIspita;
import rs.ac.bg.fon.probniprijemni.dto.VrstaIspitaDto;
import rs.ac.bg.fon.probniprijemni.repository.VrstaIspitaRepository;
import rs.ac.bg.fon.probniprijemni.service.VrstaIspitaService;

/**
 *
 * @author Stasa
 */
@Service
@RequiredArgsConstructor
public class VrstaIspitaServiceImpl implements VrstaIspitaService {
    private final VrstaIspitaRepository vrstaIspitaRepository;

    @Override
    public List<VrstaIspitaDto> getSve() {
        List<VrstaIspitaDto> rezultat = new ArrayList<>();
        for (VrstaIspita v : vrstaIspitaRepository.findAll()) {
            rezultat.add(VrstaIspitaConverter.toDto(v));
        }
        return rezultat;
    }
}
