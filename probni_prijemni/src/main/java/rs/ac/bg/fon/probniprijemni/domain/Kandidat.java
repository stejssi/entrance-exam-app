/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 *
 * @author Stasa
 */

@Entity
@Table(name = "kandidat")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class Kandidat extends Korisnik {

    @Column(nullable = false, length = 50)
    private String ime;

    @Column(nullable = false, length = 50)
    private String prezime;

    @Column(nullable = false, unique = true, length = 13)
    private String jmbg;

    private String telefon;
}

