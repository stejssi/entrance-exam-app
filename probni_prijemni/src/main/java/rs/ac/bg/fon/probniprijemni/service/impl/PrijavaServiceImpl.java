/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.probniprijemni.converter.KandidatConverter;
import rs.ac.bg.fon.probniprijemni.converter.PrijavaConverter;
import rs.ac.bg.fon.probniprijemni.domain.Kandidat;
import rs.ac.bg.fon.probniprijemni.domain.Prijava;
import rs.ac.bg.fon.probniprijemni.domain.StatusPrijave;
import rs.ac.bg.fon.probniprijemni.domain.Termin;
import rs.ac.bg.fon.probniprijemni.dto.KandidatDto;
import rs.ac.bg.fon.probniprijemni.dto.PrijavaCreateDto;
import rs.ac.bg.fon.probniprijemni.dto.PrijavaDto;
import rs.ac.bg.fon.probniprijemni.exception.BadRequestException;
import rs.ac.bg.fon.probniprijemni.exception.ResourceNotFoundException;
import rs.ac.bg.fon.probniprijemni.repository.PrijavaRepository;
import rs.ac.bg.fon.probniprijemni.repository.TerminRepository;
import rs.ac.bg.fon.probniprijemni.service.CenovnikService;
import rs.ac.bg.fon.probniprijemni.service.EmailService;
import rs.ac.bg.fon.probniprijemni.service.KandidatService;
import rs.ac.bg.fon.probniprijemni.service.PdfService;
import rs.ac.bg.fon.probniprijemni.service.PrijavaService;

/**
 *
 * @author Stasa
 */
@Service
@RequiredArgsConstructor
public class PrijavaServiceImpl implements PrijavaService {

    private final PrijavaRepository prijavaRepository;
    private final TerminRepository terminRepository;
    private final KandidatService kandidatService;
    private final CenovnikService cenovnikService;
    
    private final PdfService pdfService;
    private final EmailService emailService;

    @Override
    @Transactional
    public PrijavaDto kreirajPrijavu(Long kandidatId, PrijavaCreateDto dto) {
        //pronalazi kandidata po ID-ju
        Kandidat kandidat = kandidatService.getEntity(kandidatId);

        //Pronalazi sve izabrane termine iz baze
        List<Termin> termini = terminRepository.findAllById(dto.getTerminIds());
        if (termini.size() != dto.getTerminIds().size()) {
            throw new ResourceNotFoundException("Jedan ili vise izabranih termina ne postoji");
        }

        //racunanje cene
        BigDecimal ukupnaCena = BigDecimal.ZERO;
        for (Termin termin : termini) {
            BigDecimal cena = cenovnikService.getTrenutnaCena(termin.getVrstaIspita());
            if (cena == null) {
                throw new BadRequestException("Cena nije definisana za vrstu ispita: " + termin.getVrstaIspita());
            }
            ukupnaCena = ukupnaCena.add(cena);
        }

        Prijava prijava = Prijava.builder()
                .kandidat(kandidat)
                .datumPrijave(LocalDateTime.now())
                .status(StatusPrijave.KREIRANA)
                .ukupnaCena(ukupnaCena)
                .termini(new HashSet<>(termini))
                .build();

        Prijava sacuvana = prijavaRepository.save(prijava);
        // Generisanje PDF-a i slanje na mejl
        byte[] pdf = pdfService.generisiUplatnicu(sacuvana);
        String tekstEmaila = "Postovani/a " + kandidat.getIme() + ",\n\n"
                + "Uspesno ste se prijavili na probni prijemni ispit. U prilogu se nalazi uplatnica.\n\n"
                + "Srdačan pozdrav,\nFON";

        emailService.posaljiUplatnicu(kandidat.getEmail(), tekstEmaila, pdf);  // bilo: kandidat.getKorisnik().getEmail()
        return PrijavaConverter.toDto(sacuvana, cenovnikService::getTrenutnaCena);
    }
    
    @Override
    public byte[] preuzmiUplatnicu(Long prijavaId) {
        Prijava prijava = prijavaRepository.findById(prijavaId)
                .orElseThrow(() -> new ResourceNotFoundException("Prijava sa id-jem " + prijavaId + " ne postoji"));
        return pdfService.generisiUplatnicu(prijava);
    }
    
    //ZA PDF SPISAK
    @Override
    public byte[] eksportujKandidatePdf(Long terminId) {
        Termin termin = terminRepository.findById(terminId)
            .orElseThrow(() -> new ResourceNotFoundException("Termin ne postoji: " + terminId));

        //Izvlačenje prijava za izabrani termin
        List<Prijava> prijave = prijavaRepository.findPrijavljeneZaTermin(terminId);
        List<Kandidat> kandidati = new ArrayList<>();
        
        //Prolazi kroz svaku vraćenu prijavu iz prethodnog koraka, 
        //uzima njenog kandidata preko i dodaje ga u listu kandidati
        for (Prijava prijava : prijave) {
            kandidati.add(prijava.getKandidat());
        }

        return pdfService.generisiSpisakKandidata(termin, kandidati);
    }
    
    //vraća sve prijave za specifičnog kandidata
    //sve pretvaramo u dto da bi poslali klijentu
    @Override
    @Transactional(readOnly = true)
    public List<PrijavaDto> getPrijaveKandidata(Long kandidatId) {
        List<Prijava> prijave = prijavaRepository.findByKandidatId(kandidatId);
        List<PrijavaDto> dtos = new ArrayList<>();
        for (Prijava p : prijave) {
            dtos.add(PrijavaConverter.toDto(p, cenovnikService::getTrenutnaCena));
        }
        return dtos;
    }

    //vraća apsolutno sve prijave iz baze
    @Override
    @Transactional(readOnly = true)
    public List<PrijavaDto> getSvePrijave() {
        List<Prijava> prijave = prijavaRepository.findAll();
        List<PrijavaDto> dtos = new ArrayList<>();
        for (Prijava p : prijave) {
            dtos.add(PrijavaConverter.toDto(p, cenovnikService::getTrenutnaCena));
        }
        return dtos;
    }

    //pretražuje prijave po statusu, terminu ili prezimenu kandidata
    @Override
    @Transactional(readOnly = true)
    public List<PrijavaDto> pretraziPrijave(StatusPrijave status, Long terminId, String prezimeKandidata) {
        List<Prijava> prijave = prijavaRepository.pretrazi(status, terminId, prezimeKandidata);
        List<PrijavaDto> dtos = new ArrayList<>();
        for (Prijava p : prijave) {
            dtos.add(PrijavaConverter.toDto(p, cenovnikService::getTrenutnaCena));
        }
        return dtos;
    }

    @Override
    @Transactional
    public void otkaziPrijavu(Long prijavaId, Long kandidatId) {
        Prijava prijava = prijavaRepository.findById(prijavaId).orElse(null);
        
        if (prijava == null) {
            throw new ResourceNotFoundException("Prijava sa id-jem " + prijavaId + " ne postoji");
        }
        
        if (!prijava.getKandidat().getId().equals(kandidatId)) {
            throw new BadRequestException("Ne mozete otkazati prijavu koja ne pripada vama");
        }
        if (prijava.getUplata() != null) {
            throw new BadRequestException("Prijava se ne moze otkazati jer je uplata vec evidentirana");
        }

        prijava.setStatus(StatusPrijave.OTKAZANA);
    }

    @Override
    public List<KandidatDto> getKandidatiPoTerminu(Long terminId) {
        List<Prijava> prijave = prijavaRepository.findPrijavljeneZaTermin(terminId);
        List<KandidatDto> dtos = new ArrayList<>();
        for (Prijava p : prijave) {
            dtos.add(KandidatConverter.toDto(p.getKandidat()));
        }
        return dtos;
    }

}
