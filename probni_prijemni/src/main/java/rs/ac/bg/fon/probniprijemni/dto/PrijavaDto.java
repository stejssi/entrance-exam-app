/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.bg.fon.probniprijemni.domain.StatusPrijave;

/**
 *
 * @author Stasa
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrijavaDto {//šta se vraća nazad
    private Long id;
    private Long kandidatId;
    private String kandidatImePrezime;
    private LocalDateTime datumPrijave;
    private StatusPrijave status;
    private BigDecimal ukupnaCena;
    private List<TerminDto> termini;
    private Boolean uplacena;
}
