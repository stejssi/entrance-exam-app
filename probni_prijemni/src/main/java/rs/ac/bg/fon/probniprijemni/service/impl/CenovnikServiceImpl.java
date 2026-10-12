/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.probniprijemni.converter.CenovnikConverter;
import rs.ac.bg.fon.probniprijemni.domain.Cenovnik;
import rs.ac.bg.fon.probniprijemni.domain.VrstaIspita;
import rs.ac.bg.fon.probniprijemni.dto.CenovnikDto;
import rs.ac.bg.fon.probniprijemni.exception.ResourceNotFoundException;
import rs.ac.bg.fon.probniprijemni.repository.CenovnikRepository;
import rs.ac.bg.fon.probniprijemni.repository.VrstaIspitaRepository;
import rs.ac.bg.fon.probniprijemni.service.CenovnikService;

/**
 *
 * @author Stasa
 */
@Service
@RequiredArgsConstructor
public class CenovnikServiceImpl implements CenovnikService {

    private final CenovnikRepository cenovnikRepository;
private final VrstaIspitaRepository vrstaIspitaRepository;

    @Override
    public List<CenovnikDto> getSveCene() {
        // 1. Izvlačimo sve entitete iz baze
        List<Cenovnik> sviCenovniciIzBaze = cenovnikRepository.findAll();

        // 2. Pravimo praznu listu DTO objekata koju ćemo popuniti
        List<CenovnikDto> rezultatLista = new ArrayList<>();

        // 3. Prolazimo kroz svaki entitet iz baze jedan po jedan
        for (Cenovnik cenovnik : sviCenovniciIzBaze) {
            // Pretvaramo entitet u DTO
            CenovnikDto dto = CenovnikConverter.toDto(cenovnik);
            // Dodajemo DTO u našu rezultujuću listu
            rezultatLista.add(dto);
        }

        // 4. Vraćamo napunjenu listu DTO-ova
        return rezultatLista;
    }


@Override
@Transactional
public CenovnikDto definisiCenu(CenovnikDto dto) {
    LocalDate danas = LocalDate.now();

    cenovnikRepository.findVazeciCenovnik(dto.getVrstaIspitaId(), danas)
            .ifPresent(stari -> {
                stari.setVaziDo(danas.minusDays(1));
                cenovnikRepository.save(stari);
            });

    VrstaIspita vrstaIspita = vrstaIspitaRepository.findById(dto.getVrstaIspitaId())
            .orElseThrow(() -> new ResourceNotFoundException("Vrsta ispita ne postoji"));

    Cenovnik novi = Cenovnik.builder()
            .vrstaIspita(vrstaIspita)
            .cena(dto.getCena())
            .vaziOd(danas)
            .vaziDo(null)
            .build();

    return CenovnikConverter.toDto(cenovnikRepository.save(novi));
}

@Override
public BigDecimal getTrenutnaCena(VrstaIspita vrstaIspita) {
    return cenovnikRepository.findVazeciCenovnik(vrstaIspita.getId(), LocalDate.now())
            .map(Cenovnik::getCena)
            .orElse(null);
}
}