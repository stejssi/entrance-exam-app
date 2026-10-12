/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.probniprijemni.domain.Kandidat;

/**
 *
 * @author Stasa
 */
public interface KandidatRepository extends JpaRepository<Kandidat, Long>{
    //Optional<Kandidat> findByKorisnikId(Long korisnikId);
    
    /*Kada se korisnik uloguje na sistem, ti iz sesije ili tokena znaš samo njegov korisnikId. 
    Da bi aplikacija saznala koje ime, prezime i JMBG pripadaju tom ulogovanom korisniku, 
    mora da potraži kandidata po stranom ključu korisnik_id.*/
    
    boolean existsByJmbg(String jmbg);
    
    /*Zašto exists umesto findByJmbg: Kada kandidat popunjava formu, 
    želiš da napraviš validaciju i proveriš da li je taj JMBG već iskorišćen. 
    Umesto da iz baze povlačiš ceo objekat sa svim podacima, ova metoda vrši brz
    SQL upit koji vraća samo true ili false. Ako vrati true, aplikacija izbaci grešku 
    "Kandidat sa ovim JMBG-om već postoji" pre nego što uopšte pokuša upis u bazu.*/

}
