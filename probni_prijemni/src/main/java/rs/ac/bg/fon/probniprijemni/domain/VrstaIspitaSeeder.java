/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.probniprijemni.repository.VrstaIspitaRepository;

/**
 *
 * @author Stasa
 */
@Component
@RequiredArgsConstructor
public class VrstaIspitaSeeder implements CommandLineRunner {
    private final VrstaIspitaRepository repo;

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            repo.save(VrstaIspita.builder().naziv("Matematika").build());
            repo.save(VrstaIspita.builder().naziv("Test opste informisanosti").build());
        }
    }
}
