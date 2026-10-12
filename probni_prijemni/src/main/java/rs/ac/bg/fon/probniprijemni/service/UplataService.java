/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service;

import rs.ac.bg.fon.probniprijemni.dto.UplataDto;

/**
 *
 * @author Stasa
 */
public interface UplataService {
    UplataDto evidentirajUplatu(Long administratorId, Long prijavaId);

}
