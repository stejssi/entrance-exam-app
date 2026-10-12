/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.probniprijemni.domain.Termin;
import rs.ac.bg.fon.probniprijemni.domain.VrstaIspita;

/**
 *
 * @author Stasa
 */
public interface TerminRepository extends JpaRepository<Termin, Long> {

    List<Termin> findByVrstaIspita(VrstaIspita vrstaIspita);
}
