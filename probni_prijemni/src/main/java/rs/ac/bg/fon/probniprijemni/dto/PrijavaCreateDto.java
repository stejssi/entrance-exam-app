/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
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
public class PrijavaCreateDto {   //kandidat šalje samo listu ID-jeva termina

    @NotEmpty(message = "Potrebno je izabrati bar jedan termin")
    private List<Long> terminIds;
}
