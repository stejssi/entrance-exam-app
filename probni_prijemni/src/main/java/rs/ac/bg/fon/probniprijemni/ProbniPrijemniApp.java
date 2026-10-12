/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package rs.ac.bg.fon.probniprijemni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 * @author Stasa
 */
@SpringBootApplication
@EnableAsync //da radi u pozadini- asinhrono
public class ProbniPrijemniApp {

    public static void main(String[] args) {
        SpringApplication.run(ProbniPrijemniApp.class, args);    }
}
