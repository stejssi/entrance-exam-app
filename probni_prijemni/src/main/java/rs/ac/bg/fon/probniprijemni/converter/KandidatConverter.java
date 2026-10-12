/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.converter;

import rs.ac.bg.fon.probniprijemni.domain.Kandidat;
import rs.ac.bg.fon.probniprijemni.dto.KandidatDto;

/**
 *
 * @author Stasa
 */
public class KandidatConverter {

    private KandidatConverter() {
    }
    
    public static KandidatDto toDto(Kandidat k) {
    return KandidatDto.builder()
            .id(k.getId())
            .ime(k.getIme())
            .prezime(k.getPrezime())
            .jmbg(k.getJmbg())
            .brojTelefona(k.getTelefon())
            .email(k.getEmail())              // bilo: k.getKorisnik().getEmail()
            .korisnickoIme(k.getKorisnickoIme())  // bilo: k.getKorisnik().getKorisnickoIme()
            .build();
}
}
