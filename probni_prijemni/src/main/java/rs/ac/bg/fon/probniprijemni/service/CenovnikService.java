/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service;

import java.math.BigDecimal;
import java.util.List;
import rs.ac.bg.fon.probniprijemni.domain.VrstaIspita;
import rs.ac.bg.fon.probniprijemni.dto.CenovnikDto;

/**
 *
 * @author Stasa
 */
public interface CenovnikService {
    List<CenovnikDto> getSveCene();

    CenovnikDto definisiCenu(CenovnikDto dto);

    BigDecimal getTrenutnaCena(VrstaIspita vrstaIspita);
}
