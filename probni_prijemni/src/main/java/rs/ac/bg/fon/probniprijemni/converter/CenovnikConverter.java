/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.converter;

import rs.ac.bg.fon.probniprijemni.domain.Cenovnik;
import rs.ac.bg.fon.probniprijemni.dto.CenovnikDto;

/**
 *
 * @author Stasa
 */
public class CenovnikConverter {
    private CenovnikConverter() {
    }

    public static CenovnikDto toDto(Cenovnik cenovnik) {
    return CenovnikDto.builder()
            .id(cenovnik.getId())
            .vrstaIspitaId(cenovnik.getVrstaIspita().getId())
            .vrstaIspitaNaziv(cenovnik.getVrstaIspita().getNaziv())
            .cena(cenovnik.getCena())
            .vaziOd(cenovnik.getVaziOd())
            .vaziDo(cenovnik.getVaziDo())
            .build();
}
}
