/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.probniprijemni.converter.KandidatConverter;
import rs.ac.bg.fon.probniprijemni.domain.Kandidat;
import rs.ac.bg.fon.probniprijemni.dto.KandidatDto;
import rs.ac.bg.fon.probniprijemni.exception.ResourceNotFoundException;
import rs.ac.bg.fon.probniprijemni.repository.KandidatRepository;
import rs.ac.bg.fon.probniprijemni.service.KandidatService;

/**
 *
 * @author Stasa
 */
@Service
@RequiredArgsConstructor
public class KandidatServiceImpl implements KandidatService{

    private final KandidatRepository kandidatRepository;

    @Override
    //da ne dobije direktan pristup bazi
    public KandidatDto getById(Long id) {
        return KandidatConverter.toDto(getEntity(id));
    }

    @Override
    public Kandidat getEntity(Long id) {
        Kandidat kandidat = kandidatRepository.findById(id).orElse(null);

        if (kandidat == null) {
            throw new ResourceNotFoundException("Kandidat sa id-jem " + id + " ne postoji");
        }

        return kandidat;    
    }
    //koristi se unutar vašeg Java koda kada spajate tabele u bazi, 
    //a ujedno pazi da aplikacija ne pukne ako taj kandidat ne postoji u bazi.
}
