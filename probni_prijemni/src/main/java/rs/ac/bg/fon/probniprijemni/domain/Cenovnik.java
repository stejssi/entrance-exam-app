/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.domain;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author Stasa
 */

@Entity
@Table(name = "cenovnik")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cenovnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vrsta_ispita_id", nullable = false)
    private VrstaIspita vrstaIspita;

    @Column(nullable = false)
    private BigDecimal cena;

    @Column(name = "vazi_od", nullable = false)
    private LocalDate vaziOd;

    @Column(name = "vazi_do")
    private LocalDate vaziDo;   // null = trenutno važi, bez krajnjeg datuma
}

