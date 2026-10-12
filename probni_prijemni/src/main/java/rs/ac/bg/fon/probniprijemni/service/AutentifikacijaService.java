/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service;

import rs.ac.bg.fon.probniprijemni.dto.KandidatDto;
import rs.ac.bg.fon.probniprijemni.dto.KorisnikDto;
import rs.ac.bg.fon.probniprijemni.dto.LoginDto;
import rs.ac.bg.fon.probniprijemni.dto.PotvrdaKodaDto;
import rs.ac.bg.fon.probniprijemni.dto.RegistracijaDto;

/**
 *
 * @author Stasa
 */
public interface AutentifikacijaService {
    KandidatDto registrujKandidata(RegistracijaDto dto);

    KorisnikDto login(LoginDto dto);
    
    void potvrdiRegistraciju(PotvrdaKodaDto dto);
}
