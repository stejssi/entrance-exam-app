/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.bg.fon.probniprijemni.domain.VrstaIspita;

/**
 *
 * @author Stasa
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminCreateDto {

    @NotNull(message = "Datum odrzavanja je obavezan")
    @FutureOrPresent(message = "Datum odrzavanja ne moze biti u proslosti")
    private LocalDate datumOdrzavanja;

    @NotNull(message = "Vreme pocetka je obavezno")
    private LocalTime vremePocetka;

    @NotNull(message = "Vrsta ispita je obavezna")
    private Long vrstaIspitaId;
    @NotBlank(message = "Adresa je obavezna")
    private String adresa;
}
