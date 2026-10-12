/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class RegistracijaDto {

    @NotBlank(message = "Korisnicko ime je obavezno")
    @Size(min = 4, max = 50, message = "Korisnicko ime mora imati izmedju 4 i 50 karaktera")
    private String korisnickoIme;

    @NotBlank(message = "Lozinka je obavezna")
    @Size(min = 6, message = "Lozinka mora imati najmanje 6 karaktera")
    private String lozinka;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email nije validnog formata")
    private String email;

    @NotBlank(message = "Ime je obavezno")
    private String ime;

    @NotBlank(message = "Prezime je obavezno")
    private String prezime;

    @NotBlank(message = "JMBG je obavezan")
    @Size(min = 13, max = 13, message = "JMBG mora imati tacno 13 cifara")
    private String jmbg;

    @NotBlank(message = "Broj telefona je obavezan")
    @Pattern(regexp = "^06[0-9]{7,8}$", message = "Neispravan format broja telefona")
    private String brojTelefona;
}
