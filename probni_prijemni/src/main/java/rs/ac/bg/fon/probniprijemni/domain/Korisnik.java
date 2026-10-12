/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 *
 * @author Stasa
 */
//Nalog za prijavu na sistem (autentikacioni podaci).
// Koristi ga i Kandidat i Administrator - razlikuju se preko polja "uloga".

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "korisnik")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "korisnicko_ime", nullable = false, unique = true, length = 50)
    private String korisnickoIme;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String lozinka;

    @Column(nullable = false)
    private boolean aktivan;

    private String verifikacioniKod;

    private LocalDateTime kodIstice;
}
