/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.probniprijemni.domain.Prijava;
import rs.ac.bg.fon.probniprijemni.domain.StatusPrijave;

/**
 *
 * @author Stasa
 */
public interface PrijavaRepository extends JpaRepository<Prijava, Long> {
    List<Prijava> findByKandidatId(Long kandidatId); //Vraća sve prijave za jednog konkretnog kandidata. 
                                                        //Koristi se kada se kandidat uloguje i otvori stranicu "Moje prijave".

    List<Prijava> findByStatus(StatusPrijave status);
    
    // Svi kandidati (prijave) koji su PRIJAVLJENI za konkretan termin - spisak po terminu
    @Query("select p from Prijava p join p.termini t where t.id = :terminId and p.status = 'PRIJAVLJEN'")
    List<Prijava> findPrijavljeneZaTermin(@Param("terminId") Long terminId);

    // Pretraga po razlicitim (opcionim) kriterijumima 
    @Query("select p from Prijava p where "
            + "(:status is null or p.status = :status) and "
            + "(:terminId is null or exists (select 1 from p.termini t where t.id = :terminId)) and "
            + "(:prezime is null or lower(p.kandidat.prezime) like lower(concat('%', :prezime, '%')))")
    List<Prijava> pretrazi(@Param("status") StatusPrijave status,
                            @Param("terminId") Long terminId,
                            @Param("prezime") String prezime);
    
    //omogućava delimično poklapanje (concat('%', ...) — npr. kucanjem "per" pronalazi "Perić"

}
