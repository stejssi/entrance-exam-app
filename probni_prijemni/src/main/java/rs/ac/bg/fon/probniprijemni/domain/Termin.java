/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author Stasa
 */

@Entity
@Table(name = "termin")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vrsta_ispita_id", nullable = false)
    private VrstaIspita vrstaIspita;

    @Column(name = "datum_odrzavanja", nullable = false)
    private LocalDate datumOdrzavanja;

    @Column(name = "vreme_pocetka", nullable = false)
    private LocalTime vremePocetka;

    @Column(nullable = false)
    private String adresa;
    
    @ManyToMany(mappedBy = "termini")
    @Builder.Default
    private Set<Prijava> prijave = new HashSet<>();
}

