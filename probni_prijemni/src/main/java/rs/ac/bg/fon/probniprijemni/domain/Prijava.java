/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author Stasa
 */

@Entity
@Table(name = "prijava")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Prijava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Govori bazi da sama generiše vrednost za ID (odgovara AUTO_INCREMENT u MySQL-u)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  //više prijava može pripadati jednom kandidatu
    @JoinColumn(name = "kandidat_id")
    private Kandidat kandidat;

    @ManyToMany  //jedna prijava može sadržati više termina, a za jedan termin može postojati više prijava različitih kandidata
    @JoinTable(
        name = "prijava_termin",
        joinColumns = @JoinColumn(name = "prijava_id"),
        inverseJoinColumns = @JoinColumn(name = "termin_id")
    )
    @Builder.Default
    private Set<Termin> termini = new HashSet<>();  //Korišćenje Set<Termin> onemogućava dupliranje istog termina unutar jedne prijave

    @Column(name = "datum_prijave", nullable = false)
    private LocalDateTime datumPrijave;

    @Column(name = "ukupna_cena", nullable = false, precision = 10, scale = 2)
    private BigDecimal ukupnaCena;

    @Enumerated(EnumType.STRING)  //Enum upisuje kao reč ("KREIRANA", "OTKAZANA"), umesto kao redni broj (0, 1, 2)
    @Column(name = "status_prijave", nullable = false)
    private StatusPrijave status = StatusPrijave.KREIRANA;    

    @OneToOne(mappedBy = "prijava", cascade = CascadeType.ALL, orphanRemoval = true)
    private Uplata uplata;
}

