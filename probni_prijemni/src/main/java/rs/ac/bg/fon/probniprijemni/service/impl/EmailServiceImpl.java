/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service.impl;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.probniprijemni.service.EmailService;
/**
 *
 * @author Stasa
 */
@Service
public class EmailServiceImpl implements EmailService{
        
    private final JavaMailSender mailSender; //komponenta koja komunicira sa gmail

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async // Poziv ove metode se sada izvršava u pozadinskom thread-u da ne bi bilo sporo
    public void posaljiUplatnicu(String naAdresu, String tekst, byte[] pdfBajtovi) {
        try {
            //za kompleksne mejlove sa html tekstom i attachement
            MimeMessage poruka = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(poruka, true);

            helper.setTo(naAdresu);
            helper.setFrom("stasadjurkic2412@gmail.com", "Probni Prijemni FON");
            helper.setSubject("Uplatnica - probni prijemni ispit FON");
            helper.setText(tekst);
            helper.addAttachment("uplatnica.pdf", new ByteArrayResource(pdfBajtovi));
            //uzima generisani pdf i kaci ga kao fajl u prilogu mejla
            
            mailSender.send(poruka);
        } catch (Exception e) {
            // Ako slanje mejla ne uspe (npr. pogresan email ili prekid mreze), 
            // ispisujemo grešku ali ne rušimo kreiranju prijave u bazi.
            System.err.println("Slanje emaila nije uspelo: " + e.getMessage());
        }
    }
    
    @Async
    public void posaljiVerifikacioniKod(String naAdresu, String kod) {
        try {
            SimpleMailMessage poruka = new SimpleMailMessage();
            poruka.setTo(naAdresu);
            poruka.setSubject("Verifikacija naloga - probni prijemni FON");
            poruka.setText("Vas verifikacioni kod je: " + kod + "\n\nKod vazi 15 minuta.");
            mailSender.send(poruka);
        } catch (Exception e) {
            System.err.println("Slanje verifikacionog koda nije uspelo: " + e.getMessage());
        }
    }
}

