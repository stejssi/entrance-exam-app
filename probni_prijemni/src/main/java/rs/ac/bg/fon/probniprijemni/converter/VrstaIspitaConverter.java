/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.converter;

import rs.ac.bg.fon.probniprijemni.domain.VrstaIspita;
import rs.ac.bg.fon.probniprijemni.dto.VrstaIspitaDto;

/**
 *
 * @author Stasa
 */
public class VrstaIspitaConverter {
    private VrstaIspitaConverter() {}

    public static VrstaIspitaDto toDto(VrstaIspita v) {
        return VrstaIspitaDto.builder()
                .id(v.getId())
                .naziv(v.getNaziv())
                .build();
    }
}
