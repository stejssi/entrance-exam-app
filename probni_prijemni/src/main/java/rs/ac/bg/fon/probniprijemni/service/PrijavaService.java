/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service;

import java.util.List;
import rs.ac.bg.fon.probniprijemni.domain.StatusPrijave;
import rs.ac.bg.fon.probniprijemni.dto.KandidatDto;
import rs.ac.bg.fon.probniprijemni.dto.PrijavaCreateDto;
import rs.ac.bg.fon.probniprijemni.dto.PrijavaDto;

/**
 *
 * @author Stasa
 */
public interface PrijavaService {
    PrijavaDto kreirajPrijavu(Long kandidatId, PrijavaCreateDto dto);

    List<PrijavaDto> getPrijaveKandidata(Long kandidatId);

    List<PrijavaDto> getSvePrijave();

    List<PrijavaDto> pretraziPrijave(StatusPrijave status, Long terminId, String prezimeKandidata);

    void otkaziPrijavu(Long prijavaId, Long kandidatId);

    List<KandidatDto> getKandidatiPoTerminu(Long terminId);
    
    byte[] preuzmiUplatnicu(Long prijavaId);
    
    byte[] eksportujKandidatePdf(Long terminId);
}
