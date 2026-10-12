/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.probniprijemni.converter.UplataConverter;
import rs.ac.bg.fon.probniprijemni.domain.Administrator;
import rs.ac.bg.fon.probniprijemni.domain.NacinPlacanja;
import rs.ac.bg.fon.probniprijemni.domain.Prijava;
import rs.ac.bg.fon.probniprijemni.domain.StatusPrijave;
import rs.ac.bg.fon.probniprijemni.domain.Uplata;
import rs.ac.bg.fon.probniprijemni.dto.UplataDto;
import rs.ac.bg.fon.probniprijemni.exception.BadRequestException;
import rs.ac.bg.fon.probniprijemni.exception.ResourceNotFoundException;
import rs.ac.bg.fon.probniprijemni.repository.AdministratorRepository;
import rs.ac.bg.fon.probniprijemni.repository.PrijavaRepository;
import rs.ac.bg.fon.probniprijemni.repository.UplataRepository;
import rs.ac.bg.fon.probniprijemni.service.UplataService;

/**
 *
 * @author Stasa
 */
@Service
@RequiredArgsConstructor
public class UplataServiceImpl implements UplataService {

    private final UplataRepository uplataRepository;
private final PrijavaRepository prijavaRepository;
private final AdministratorRepository administratorRepository;

@Override
@Transactional
public UplataDto evidentirajUplatu(Long administratorId, Long prijavaId) {
    Administrator administrator = administratorRepository.findById(administratorId)
            .orElseThrow(() -> new ResourceNotFoundException("Administrator sa id-jem " + administratorId + " ne postoji"));

    Prijava prijava = prijavaRepository.findById(prijavaId).orElse(null);
    if (prijava == null) {
        throw new ResourceNotFoundException("Prijava sa id-jem " + prijavaId + " ne postoji");
    }
    if (prijava.getStatus() == StatusPrijave.OTKAZANA) {
        throw new BadRequestException("Ne moze se evidentirati uplata za otkazanu prijavu");
    }
    if (prijava.getUplata() != null) {
        throw new BadRequestException("Uplata je vec evidentirana za ovu prijavu");
    }

    Uplata uplata = Uplata.builder()
            .prijava(prijava)
            .administrator(administrator)
            .iznos(prijava.getUkupnaCena())
            .datumUplate(LocalDateTime.now())
            .nacinPlacanja(NacinPlacanja.ADMINISTRATIVNO)
            .build();

    prijava.setUplata(uplata);
    prijava.setStatus(StatusPrijave.PRIJAVLJEN);
    prijavaRepository.save(prijava);

    return UplataConverter.toDto(uplata);
}
}
