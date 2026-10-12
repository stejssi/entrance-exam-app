/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.probniprijemni.domain.Korisnik;

/**
 *
 * @author Stasa
 */
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

    Optional<Korisnik> findByKorisnickoIme(String korisnickoIme);

    boolean existsByKorisnickoIme(String korisnickoIme);

    boolean existsByEmail(String email);
}
