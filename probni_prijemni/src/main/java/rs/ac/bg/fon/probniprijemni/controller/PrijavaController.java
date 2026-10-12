/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.probniprijemni.domain.StatusPrijave;
import rs.ac.bg.fon.probniprijemni.dto.KandidatDto;
import rs.ac.bg.fon.probniprijemni.dto.PrijavaCreateDto;
import rs.ac.bg.fon.probniprijemni.dto.PrijavaDto;
import rs.ac.bg.fon.probniprijemni.service.PrijavaService;

/**
 *
 * @author Stasa
 */
@RestController
@RequiredArgsConstructor
public class PrijavaController {

    private final PrijavaService prijavaService;

    @PostMapping("/api/kandidati/{kandidatId}/prijave")  
    public ResponseEntity<PrijavaDto> kreirajPrijavu(@PathVariable Long kandidatId, //@PathVariable uzima ID kandidata iz same URL putanje.
            @Valid @RequestBody PrijavaCreateDto dto) { //uzima podatke o izabranim terminima iz tela (body) HTTP zahteva i proverava valjanost podataka (@Valid).
        return ResponseEntity.status(HttpStatus.CREATED).body(prijavaService.kreirajPrijavu(kandidatId, dto));
    }

    @GetMapping("/api/kandidati/{kandidatId}/prijave")
    public ResponseEntity<List<PrijavaDto>> getPrijaveKandidata(@PathVariable Long kandidatId) {
        return ResponseEntity.ok(prijavaService.getPrijaveKandidata(kandidatId));
    }

    @DeleteMapping("/api/kandidati/{kandidatId}/prijave/{prijavaId}")
    public ResponseEntity<Void> otkaziPrijavu(@PathVariable Long kandidatId, @PathVariable Long prijavaId) {
        prijavaService.otkaziPrijavu(prijavaId, kandidatId);
        return ResponseEntity.noContent().build();
    }

    //ADMINISTRATIVNI DEO
    @GetMapping("/api/admin/prijave")
    public ResponseEntity<List<PrijavaDto>> pretraziPrijave(
            @RequestParam(required = false) StatusPrijave status, //znači da parametri nisu obavezni
            @RequestParam(required = false) Long terminId,
            @RequestParam(required = false) String prezime) {
        return ResponseEntity.ok(prijavaService.pretraziPrijave(status, terminId, prezime));
    }

    @GetMapping("/api/admin/termini/{terminId}/kandidati")
    public ResponseEntity<List<KandidatDto>> getKandidatiPoTerminu(@PathVariable Long terminId) {
        return ResponseEntity.ok(prijavaService.getKandidatiPoTerminu(terminId));
    }
    
    @GetMapping("/api/kandidati/{kandidatId}/prijave/{prijavaId}/uplatnica")
    public ResponseEntity<byte[]> preuzmiUplatnicu(@PathVariable Long kandidatId, @PathVariable Long prijavaId) {
        byte[] pdf = prijavaService.preuzmiUplatnicu(prijavaId);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf") //da je odgovor pdf dokument
                .header("Content-Disposition", "inline; filename=uplatnica.pdf") //inline znaci da se dokument otvara u novom tabu ne preuzima se automatski
                .body(pdf);
    }
    
    @GetMapping("/api/admin/termini/{terminId}/kandidati/pdf")
    public ResponseEntity<byte[]> eksportujKandidatePdf(@PathVariable Long terminId) {
        byte[] pdf = prijavaService.eksportujKandidatePdf(terminId);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=spisak-kandidata.pdf") //attachment (browser ga odmah skida kao fajl na disk, ne otvara) zato sto nam treba da ga eksportujemo(admin želi da ga sačuva, ne da samo pregleda)
                .body(pdf);
    }
}