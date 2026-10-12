/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service;

/**
 *
 * @author Stasa
 */
public interface EmailService {
    void posaljiUplatnicu(String naAdresu, String tekst, byte[] pdfBajtovi);
    void posaljiVerifikacioniKod(String naAdresu, String kod);
}
