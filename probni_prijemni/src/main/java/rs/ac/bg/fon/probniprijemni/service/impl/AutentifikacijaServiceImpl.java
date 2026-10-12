/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.probniprijemni.converter.KandidatConverter;
import rs.ac.bg.fon.probniprijemni.domain.Kandidat;
import rs.ac.bg.fon.probniprijemni.domain.Korisnik;
import rs.ac.bg.fon.probniprijemni.domain.Uloga;
import rs.ac.bg.fon.probniprijemni.dto.KandidatDto;
import rs.ac.bg.fon.probniprijemni.dto.KorisnikDto;
import rs.ac.bg.fon.probniprijemni.dto.LoginDto;
import rs.ac.bg.fon.probniprijemni.dto.PotvrdaKodaDto;
import rs.ac.bg.fon.probniprijemni.dto.RegistracijaDto;
import rs.ac.bg.fon.probniprijemni.exception.BadRequestException;
import rs.ac.bg.fon.probniprijemni.repository.KandidatRepository;
import rs.ac.bg.fon.probniprijemni.repository.KorisnikRepository;
import rs.ac.bg.fon.probniprijemni.service.AutentifikacijaService;
import rs.ac.bg.fon.probniprijemni.service.EmailService;

/**
 *
 * @author Stasa
 */
@Service
@RequiredArgsConstructor
public class AutentifikacijaServiceImpl implements AutentifikacijaService {

    private final KorisnikRepository korisnikRepository;
    private final KandidatRepository kandidatRepository;
    private final EmailService emailService;


    @Override
@Transactional
public KandidatDto registrujKandidata(RegistracijaDto dto) {
    if (korisnikRepository.existsByKorisnickoIme(dto.getKorisnickoIme())) {
        throw new BadRequestException("Korisnicko ime je vec zauzeto");
    }
    if (korisnikRepository.existsByEmail(dto.getEmail())) {
        throw new BadRequestException("Nalog sa ovim emailom vec postoji");
    }
    if (kandidatRepository.existsByJmbg(dto.getJmbg())) {
        throw new BadRequestException("Kandidat sa ovim JMBG-om je vec registrovan");
    }

    String kod = String.format("%06d", new java.util.Random().nextInt(999999));

    // Sada se pravi SAMO Kandidat - on je istovremeno i Korisnik (naslednik)
    Kandidat kandidat = Kandidat.builder()
            .korisnickoIme(dto.getKorisnickoIme())
            .lozinka(dto.getLozinka())
            .email(dto.getEmail())
            .aktivan(false)
            .verifikacioniKod(kod)
            .kodIstice(LocalDateTime.now().plusMinutes(15))
            .ime(dto.getIme())
            .prezime(dto.getPrezime())
            .jmbg(dto.getJmbg())
            .telefon(dto.getBrojTelefona())
            .build();

    kandidat = kandidatRepository.save(kandidat); // upisuje i u korisnik i u kandidat tabelu (JOINED)

    emailService.posaljiVerifikacioniKod(dto.getEmail(), kod);

    return KandidatConverter.toDto(kandidat);
}

@Override
@Transactional
public void potvrdiRegistraciju(PotvrdaKodaDto dto) {
    Korisnik korisnik = korisnikRepository.findByKorisnickoIme(dto.getKorisnickoIme())
            .orElseThrow(() -> new BadRequestException("Korisnik ne postoji"));

    if (korisnik.isAktivan()) {
        throw new BadRequestException("Nalog je vec potvrdjen");
    }
    if (korisnik.getKodIstice() == null || korisnik.getKodIstice().isBefore(LocalDateTime.now())) {
        throw new BadRequestException("Verifikacioni kod je istekao");
    }
    if (!korisnik.getVerifikacioniKod().equals(dto.getKod())) {
        throw new BadRequestException("Neispravan verifikacioni kod");
    }

    korisnik.setAktivan(true);
    korisnik.setVerifikacioniKod(null);
    korisnik.setKodIstice(null);
    korisnikRepository.save(korisnik);
}

@Override
public KorisnikDto login(LoginDto dto) {
    Korisnik korisnik = korisnikRepository.findByKorisnickoIme(dto.getKorisnickoIme()).orElse(null);

    if (korisnik == null) {
        throw new BadRequestException("Pogresno korisnicko ime ili lozinka");
    }
    if (!korisnik.getLozinka().equals(dto.getLozinka())) {
        throw new BadRequestException("Pogresno korisnicko ime ili lozinka");
    }
    if (!korisnik.isAktivan()) {
        throw new BadRequestException("Nalog nije potvrdjen. Proverite email za verifikacioni kod.");
    }

    // Uloga se sada utvrdjuje preko konkretnog tipa, ne preko polja
    Long kandidatId = null;
    Uloga uloga;
    if (korisnik instanceof Kandidat kandidat) {
        kandidatId = kandidat.getId();
        uloga = Uloga.KANDIDAT;
    } else {
        uloga = Uloga.ADMIN;
    }

    return KorisnikDto.builder()
            .id(korisnik.getId())
            .korisnickoIme(korisnik.getKorisnickoIme())
            .email(korisnik.getEmail())
            .uloga(uloga)
            .kandidatId(kandidatId)
            .build();
}
}