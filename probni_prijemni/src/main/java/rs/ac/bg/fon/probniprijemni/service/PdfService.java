/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service;

import java.util.List;
import rs.ac.bg.fon.probniprijemni.domain.Kandidat;
import rs.ac.bg.fon.probniprijemni.domain.Prijava;
import rs.ac.bg.fon.probniprijemni.domain.Termin;

/**
 *
 * @author Stasa
 */
public interface PdfService {
     byte[] generisiUplatnicu(Prijava prijava);
     byte[] generisiSpisakKandidata(Termin termin, List<Kandidat> kandidati);
}
