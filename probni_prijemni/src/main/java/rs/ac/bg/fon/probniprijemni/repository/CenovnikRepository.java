/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.probniprijemni.domain.Cenovnik;

/**
 *
 * @author Stasa
 */
public interface CenovnikRepository extends JpaRepository<Cenovnik, Long>{
    @Query("select c from Cenovnik c where c.vrstaIspita.id = :vrstaIspitaId "
         + "and c.vaziOd <= :datum and (c.vaziDo is null or c.vaziDo >= :datum)")
    Optional<Cenovnik> findVazeciCenovnik(@Param("vrstaIspitaId") Long vrstaIspitaId,
                                          @Param("datum") LocalDate datum);
}
