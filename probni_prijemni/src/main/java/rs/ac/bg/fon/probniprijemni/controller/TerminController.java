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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.probniprijemni.dto.TerminCreateDto;
import rs.ac.bg.fon.probniprijemni.dto.TerminDto;
import rs.ac.bg.fon.probniprijemni.service.TerminService;

/**
 *
 * @author Stasa
 */

//Njegova uloga je da primi zahteve koji stižu sa interneta (iz Postman-a ili sa Frontenda), 
//pozove odgovarajući servis koji odradi posao, i vrati odgovor onome ko je uputio zahtev.


@RestController //ova klasa HTTP kontroler koji vraća podatke direktno u JSON formatu (umesto HTML stranica).
@RequiredArgsConstructor  //Automatski pravi konstruktor koji injektuje TerminService, tako da ne moraš ručno pisati @Autowired.
public class TerminController { 
    
    private final TerminService terminService;

    //preuzimanje svih termina
    @GetMapping("/api/termini")  //Sluša na HTTP GET zahtev na adresi /api/termini.
    public ResponseEntity<List<TerminDto>> getSviTermini() {
        return ResponseEntity.ok(terminService.getSviTermini());
        //dobija listu svih DTO objekata i pakuje ih u ResponseEntity.ok(...) 
        //koji vraća HTTP status 200 OK zajedno sa listom termina u JSON formatu.
    }
    
    //preuzimanje jednog termina
    @GetMapping("/api/termini/{id}")  //{id} je promenljivi deo adrese (npr. /api/termini/5).
    public ResponseEntity<TerminDto> getById(@PathVariable Long id) {  //@PathVariable - broj iz adrese (npr. 5) prosledi u metodu kao promenljivu id
        return ResponseEntity.ok(terminService.getById(id));
    }
    
    
    @PostMapping("/api/admin/termini")   //Sluša na POST zahtev na navedenoj adresi (zaštićenoj za admina).
    public ResponseEntity<TerminDto> kreirajTermin(@Valid @RequestBody TerminCreateDto dto) {  //@RequestBody TerminCreateDto dto - Uzima JSON podatak koji je poslat u telu zahteva i pretvara ga u Java objekat dto.
                                                                                                //@Valid - proverava validnost
        return ResponseEntity.status(HttpStatus.CREATED).body(terminService.kreirajTermin(dto));
        
        //Poziva servis za kreiranje i vraća status 201 CREATED 
        //(HttpStatus.CREATED) zajedno sa novokreiranim terminom.
    }
    
    @PutMapping("/api/admin/termini/{id}")  //Sluša na PUT zahtev na adresi sa ID-jem termina koji se menja
    public ResponseEntity<TerminDto> azurirajTermin(@PathVariable Long id, @Valid @RequestBody TerminCreateDto dto) {
        
    //Kombinuje @PathVariable (koji termin menjamo) i @Valid @RequestBody (sa kojim novim podacima)
        return ResponseEntity.ok(terminService.azurirajTermin(id, dto));
    }

    @DeleteMapping("/api/admin/termini/{id}")  //Sluša na DELETE zahtev na adresi za određeni ID.
    public ResponseEntity<Void> obrisiTermin(@PathVariable Long id) {
        terminService.obrisiTermin(id);
        return ResponseEntity.noContent().build(); //HTTP statusu 204 No Content (uspešno obrisano, nema tela odgovora).
    }
    
    
    
    
}
