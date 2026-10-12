/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.exception;

/**
 *
 * @author Stasa
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String poruka) {
        super(poruka);
    }
}
