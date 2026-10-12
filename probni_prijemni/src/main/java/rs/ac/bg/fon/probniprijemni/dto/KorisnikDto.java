/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.bg.fon.probniprijemni.domain.Uloga;

/**
 *
 * @author Stasa
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KorisnikDto {

    private Long id;
    private String korisnickoIme;
    private String email;
    private Uloga uloga;
    private Long kandidatId;// kandidatId popunjen samo ako je uloga KANDIDAT 
}
