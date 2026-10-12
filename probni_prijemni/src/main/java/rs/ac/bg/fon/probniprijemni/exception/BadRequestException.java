package rs.ac.bg.fon.probniprijemni.exception;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Stasa
 */
public class BadRequestException extends RuntimeException{
    public BadRequestException(String poruka) {
        super(poruka);
    }
 //Baca se kada zahtev krsi poslovno pravilo (npr. otkazivanje vec uplacene prijave).
 //GlobalExceptionHandler je mapira na HTTP 400.
 
}
