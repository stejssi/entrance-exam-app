/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.converter;

import rs.ac.bg.fon.probniprijemni.domain.Uplata;
import rs.ac.bg.fon.probniprijemni.dto.UplataDto;

/**
 *
 * @author Stasa
 */
public class UplataConverter {
    private UplataConverter() {
    }

    public static UplataDto toDto(Uplata uplata) {
    return UplataDto.builder()
            .id(uplata.getId())
            .prijavaId(uplata.getPrijava().getId())
            .administratorId(uplata.getAdministrator().getId())
            .iznos(uplata.getIznos())
            .datumUplate(uplata.getDatumUplate())
            .nacinPlacanja(uplata.getNacinPlacanja())
            .build();
}
}

