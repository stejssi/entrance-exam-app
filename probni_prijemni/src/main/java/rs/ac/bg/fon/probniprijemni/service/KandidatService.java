/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service;

import rs.ac.bg.fon.probniprijemni.domain.Kandidat;
import rs.ac.bg.fon.probniprijemni.dto.KandidatDto;

/**
 *
 * @author Stasa
 */
public interface KandidatService {
    KandidatDto getById(Long id);

    Kandidat getEntity(Long id);
}
