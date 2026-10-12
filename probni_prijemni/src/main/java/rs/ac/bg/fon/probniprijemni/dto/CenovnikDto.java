/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Stasa
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CenovnikDto {

    private Long id;

    @NotNull(message = "Vrsta ispita je obavezna")
    private Long vrstaIspitaId;
    @NotNull(message = "Cena je obavezna")
    @Positive(message = "Cena mora biti pozitivan broj")
    private BigDecimal cena;
    
    private String vrstaIspitaNaziv;
    private LocalDate vaziOd;
    private LocalDate vaziDo;
}
