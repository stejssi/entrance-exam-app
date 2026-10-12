/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.converter;

import rs.ac.bg.fon.probniprijemni.domain.Termin;
import rs.ac.bg.fon.probniprijemni.domain.VrstaIspita;
import rs.ac.bg.fon.probniprijemni.dto.TerminCreateDto;
import rs.ac.bg.fon.probniprijemni.dto.TerminDto;

/**
 *
 * @author Stasa
 */
public class TerminConverter {

    private TerminConverter() {
    }
    
    public static TerminDto toDto(Termin termin) {
    return TerminDto.builder()
            .id(termin.getId())
            .datumOdrzavanja(termin.getDatumOdrzavanja())
            .vremePocetka(termin.getVremePocetka())
            .vrstaIspitaId(termin.getVrstaIspita().getId())
            .vrstaIspitaNaziv(termin.getVrstaIspita().getNaziv())
            .adresa(termin.getAdresa())
            .build();
}

    public static Termin toEntity(TerminCreateDto dto, VrstaIspita vrstaIspita) {
        return Termin.builder()
                .datumOdrzavanja(dto.getDatumOdrzavanja())
                .vremePocetka(dto.getVremePocetka())
                .vrstaIspita(vrstaIspita)
                .adresa(dto.getAdresa())
                .build();
    }

    public static void popuniIzDto(Termin termin, TerminCreateDto dto, VrstaIspita vrstaIspita) {
        termin.setDatumOdrzavanja(dto.getDatumOdrzavanja());
        termin.setVremePocetka(dto.getVremePocetka());
        termin.setVrstaIspita(vrstaIspita);
        termin.setAdresa(dto.getAdresa());
    }

}
