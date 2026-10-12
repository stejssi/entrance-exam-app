/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service;

import java.util.List;
import rs.ac.bg.fon.probniprijemni.dto.TerminCreateDto;
import rs.ac.bg.fon.probniprijemni.dto.TerminDto;

/**
 *
 * @author Stasa
 */
public interface TerminService {
    List<TerminDto> getSviTermini();
    TerminDto getById(Long id);
    TerminDto kreirajTermin(TerminCreateDto dto);
    TerminDto azurirajTermin(Long id, TerminCreateDto dto);
    void obrisiTermin(Long id);
}
