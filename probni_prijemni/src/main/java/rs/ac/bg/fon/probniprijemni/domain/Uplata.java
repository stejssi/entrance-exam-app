/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 *
 * @author Stasa
 */


@Entity
@Table(name = "uplata")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Uplata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prijava_id", unique = true)
    private Prijava prijava;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id", nullable = false)
    private Administrator administrator;   // ko je evidentirao uplatu

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iznos;

    @Column(name = "datum_uplate", nullable = false)
    private LocalDateTime datumUplate;

    @Column(name = "nacin_placanja", nullable = false)
    private NacinPlacanja nacinPlacanja;
}
