/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service.impl;

import java.util.ArrayList;
import rs.ac.bg.fon.probniprijemni.converter.TerminConverter;
import rs.ac.bg.fon.probniprijemni.domain.Termin;
import rs.ac.bg.fon.probniprijemni.dto.TerminCreateDto;
import rs.ac.bg.fon.probniprijemni.dto.TerminDto;
import rs.ac.bg.fon.probniprijemni.exception.BadRequestException;
import rs.ac.bg.fon.probniprijemni.exception.ResourceNotFoundException;
import rs.ac.bg.fon.probniprijemni.repository.TerminRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import rs.ac.bg.fon.probniprijemni.domain.VrstaIspita;
import rs.ac.bg.fon.probniprijemni.repository.VrstaIspitaRepository;
import rs.ac.bg.fon.probniprijemni.service.TerminService;
/**
 *
 * @author Stasa
 */


@Service
@RequiredArgsConstructor  //Automatski kreira konstruktor koji ubacuje (inject-uje) TerminRepository bez pisanja @Autowired
public class TerminServiceImpl implements TerminService {

    private final TerminRepository terminRepository;
private final VrstaIspitaRepository vrstaIspitaRepository;
    @Override
    public List<TerminDto> getSviTermini() {
        // 1. Izvlačimo sve entitete iz baze
        List<Termin> sviTerminiIzBaze = terminRepository.findAll();

        // 2. Pravimo praznu listu DTO objekata koju ćemo popuniti
        List<TerminDto> rezultatLista = new ArrayList<>();

        // 3. Prolazimo kroz svaki entitet iz baze jedan po jedan
        for (Termin termin : sviTerminiIzBaze) {
            // Pretvaramo entitet u DTO
            TerminDto dto = TerminConverter.toDto(termin);
            // Dodajemo DTO u našu rezultujuću listu
            rezultatLista.add(dto);
        }

        // 4. Vraćamo napunjenu listu DTO-ova
        return rezultatLista;
    }    

    @Override
    public TerminDto getById(Long id) {
        return TerminConverter.toDto(pronadjiIliBaci(id));
    }

    

    @Override
    @Transactional
    public TerminDto kreirajTermin(TerminCreateDto dto) {
        VrstaIspita vrstaIspita = vrstaIspitaRepository.findById(dto.getVrstaIspitaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vrsta ispita ne postoji"));
        Termin termin = TerminConverter.toEntity(dto, vrstaIspita);
        return TerminConverter.toDto(terminRepository.save(termin));
    }

    @Override
    @Transactional
    public TerminDto azurirajTermin(Long id, TerminCreateDto dto) {
        Termin termin = pronadjiIliBaci(id);
        VrstaIspita vrstaIspita = vrstaIspitaRepository.findById(dto.getVrstaIspitaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vrsta ispita ne postoji"));
        TerminConverter.popuniIzDto(termin, dto, vrstaIspita);
        return TerminConverter.toDto(terminRepository.save(termin));
    }

    @Override
    @Transactional
    public void obrisiTermin(Long id) {
        Termin termin = pronadjiIliBaci(id);
        if (!termin.getPrijave().isEmpty()) {
            throw new BadRequestException("Termin ima prijave, ne moze se obrisati");
        }
        terminRepository.delete(termin);
    }

    private Termin pronadjiIliBaci(Long id) {
        Termin termin = terminRepository.findById(id).orElse(null);
        //Daj mi Termin iz baze, a ako ga nema u bazi, vrati mi null.

        if (termin == null) {
            throw new ResourceNotFoundException("Termin sa id-jem " + id + " ne postoji");
        }

        return termin;
    }
}

