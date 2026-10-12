/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import rs.ac.bg.fon.probniprijemni.domain.Prijava;
import rs.ac.bg.fon.probniprijemni.domain.Termin;
import rs.ac.bg.fon.probniprijemni.domain.VrstaIspita;
import rs.ac.bg.fon.probniprijemni.dto.PrijavaDto;
import rs.ac.bg.fon.probniprijemni.dto.TerminDto;

/**
 *
 * @author Stasa
 */
public class PrijavaConverter {
    
    private PrijavaConverter() {
    }

    public static PrijavaDto toDto(Prijava prijava, Function<VrstaIspita, BigDecimal> cenaResolver) {
        // 1. Pravimo praznu listu za DTO termine
        List<TerminDto> terminDtoList = new ArrayList<>();

        // 2. Prolazimo kroz svaki termin u prijavi pomoću obične for petlje
        if (prijava.getTermini() != null) {
            for (Termin termin : prijava.getTermini()) {
                TerminDto dto = TerminConverter.toDto(termin);
                terminDtoList.add(dto);
            }
        }
        // Bezbedna provera za kandidata
        Long kandidatId = (prijava.getKandidat() != null) ? prijava.getKandidat().getId() : null;
        String imePrezime = (prijava.getKandidat() != null) 
                ? prijava.getKandidat().getIme() + " " + prijava.getKandidat().getPrezime() 
                : "";

        return PrijavaDto.builder()
                .id(prijava.getId())
                .kandidatId(kandidatId)                  
                .kandidatImePrezime(imePrezime)                
                .datumPrijave(prijava.getDatumPrijave())
                .status(prijava.getStatus())
                .ukupnaCena(prijava.getUkupnaCena())
                .termini(terminDtoList)
                .uplacena(prijava.getUplata() != null)
                .build();
    }
}

