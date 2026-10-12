/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
/**
 *
 * @author Stasa
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PotvrdaKodaDto {

    @NotBlank(message = "Korisnicko ime je obavezno")
    private String korisnickoIme;

    @NotBlank(message = "Kod je obavezan")
    private String kod;
}

