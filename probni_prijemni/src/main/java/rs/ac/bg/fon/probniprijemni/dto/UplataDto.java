package rs.ac.bg.fon.probniprijemni.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import rs.ac.bg.fon.probniprijemni.domain.NacinPlacanja;

/**
 *
 * @author Stasa
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UplataDto {
    // UplataDto.java
private Long id;
private Long prijavaId;
private Long administratorId;
private BigDecimal iznos;
private LocalDateTime datumUplate;
private NacinPlacanja nacinPlacanja;
}
