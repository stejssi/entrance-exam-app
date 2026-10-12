/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.probniprijemni.service.impl;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.probniprijemni.domain.Prijava;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import rs.ac.bg.fon.probniprijemni.domain.Kandidat;
import rs.ac.bg.fon.probniprijemni.domain.Termin;
import rs.ac.bg.fon.probniprijemni.service.PdfService;
/**
 *
 * @author Stasa
 */
@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] generisiUplatnicu(Prijava prijava) {
        try (PDDocument dokument = new PDDocument()) {
            PDPage stranica = new PDPage(PDRectangle.A4); //dodavanje A4 stranice u dokument
            dokument.addPage(stranica);
                
                //platno preko koga dodajemo oblike i tekst
            try (PDPageContentStream cs = new PDPageContentStream(dokument, stranica)) {
                var fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                var fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

                //GLAVNI OKVIR UPLATNICE
                cs.setLineWidth(1.5f);
                cs.addRect(40, 500, 515, 280); // pravougaoni okviri - x, y, širina, visina
                cs.stroke();

                // Naslov uplatnice
                napisiTekst(cs, fontBold, 14, 50, 755, "NALOG ZA UPLATU");

                // LEVA STRANA (Uplatilac, Svrha, Primalac)
                cs.setLineWidth(0.8f);

                // Uplatilac
                napisiTekst(cs, fontBold, 8, 50, 735, "Uplatilac:");
                String uplatilacIme = ocisti(prijava.getKandidat().getIme() + " " + prijava.getKandidat().getPrezime());
                napisiTekst(cs, fontRegular, 10, 50, 720, uplatilacIme);
                cs.addRect(45, 710, 240, 35);
                cs.stroke();

                // Svrha uplate
                napisiTekst(cs, fontBold, 8, 50, 695, "Svrha uplate:");
                napisiTekst(cs, fontRegular, 9, 50, 680, "Prijava za probni prijemni ispit");
                cs.addRect(45, 670, 240, 35);
                cs.stroke();

                // Primalac
                napisiTekst(cs, fontBold, 8, 50, 645, "Primalac:");
                napisiTekst(cs, fontRegular, 9, 50, 630, "Fakultet organizacionih nauka");
                napisiTekst(cs, fontRegular, 8, 50, 618, "Jove Ilica 154, Beograd");
                cs.addRect(45, 605, 240, 38);
                cs.stroke();

                // DESNA STRANA (Sifra, Valuta, Iznos, Racun, Poziv na broj) 
                
                // Šifra plaćanja & Valuta
                napisiTekst(cs, fontBold, 8, 300, 735, "Sifra placanja");
                cs.addRect(300, 710, 50, 20);
                cs.stroke();
                napisiTekst(cs, fontRegular, 10, 315, 716, "289");

                napisiTekst(cs, fontBold, 8, 360, 735, "Valuta");
                cs.addRect(360, 710, 40, 20);
                cs.stroke();
                napisiTekst(cs, fontRegular, 10, 365, 716, "RSD");

                // Iznos
                napisiTekst(cs, fontBold, 8, 410, 735, "Iznos");
                cs.addRect(410, 710, 135, 20);
                cs.stroke();
                String iznosTekst = String.format("%.2f", prijava.getUkupnaCena());
                napisiTekst(cs, fontBold, 10, 420, 716, iznosTekst);

                // Račun primaoca
                napisiTekst(cs, fontBold, 8, 300, 695, "Racun primaoca:");
                cs.addRect(300, 670, 245, 20);
                cs.stroke();
                napisiTekst(cs, fontRegular, 10, 305, 676, "840-0000001312666-68");

                // Model i Poziv na broj
                napisiTekst(cs, fontBold, 8, 300, 655, "Model");
                cs.addRect(300, 630, 40, 20);
                cs.stroke();
                napisiTekst(cs, fontRegular, 10, 310, 636, "97");

                napisiTekst(cs, fontBold, 8, 350, 655, "Poziv na broj (odobrenje)");
                cs.addRect(350, 630, 195, 20);
                cs.stroke();
                napisiTekst(cs, fontRegular, 10, 355, 636, "PRIJAVA-" + prijava.getId());

                // DONJI DEO (Spisak izabranih termina) 
                cs.setLineWidth(0.5f);
                cs.moveTo(50, 605);
                cs.lineTo(535, 605);
                cs.stroke();

                napisiTekst(cs, fontBold, 9, 50, 590, "Prijavljeni termini:");
                //ispis termina
                if (prijava.getTermini() != null) {
                    int yTermin = 575; //y koordinata visina
                    for (var termin : prijava.getTermini()) {
                        String stravka = "- " + termin.getVrstaIspita().getNaziv() + " | " 
                                + termin.getDatumOdrzavanja() + " u " + termin.getVremePocetka() 
                                + " | " + ocisti(termin.getAdresa());
                        
                        napisiTekst(cs, fontRegular, 8, 55, yTermin, stravka);
                        yTermin -= 14; //obavezno spustamo za sledeci termin
                    }
                }
            }
            //cuvamo pdf kao niz bajtova
            ByteArrayOutputStream izlaz = new ByteArrayOutputStream();
            dokument.save(izlaz);
            return izlaz.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Greska pri generisanju PDF uplatnice", e);
        }
    }

    

    //za menjanje slova
    private String ocisti(String tekst) {
        if (tekst == null) return "";
        return tekst
                .replace("č", "c").replace("ć", "c").replace("š", "s")
                .replace("đ", "dj").replace("ž", "z")
                .replace("Č", "C").replace("Ć", "C").replace("Š", "S")
                .replace("Đ", "Dj").replace("Ž", "Z");
    }

    @Override
    public byte[] generisiSpisakKandidata(Termin termin, List<Kandidat> kandidati) {
        try (PDDocument dokument = new PDDocument()) {
            PDPage stranica = new PDPage(PDRectangle.A4);
            dokument.addPage(stranica);

            try (PDPageContentStream sadrzaj = new PDPageContentStream(dokument, stranica)) {
                var naslovFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                var obicanFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
                float y = 770;

                // 1. Naslov
                napisiTekst(sadrzaj, naslovFont, 15, 50, y, "Spisak prijavljenih kandidata");

                // 2. Podnaslov (informacije o terminu)
                y -= 22; //Spušta se kursor naniže za 22 tačke
                String infoTermin = ocisti(termin.getVrstaIspita().getNaziv() + " - " + termin.getDatumOdrzavanja()
                        + " u " + termin.getVremePocetka() + ", " + termin.getAdresa());
                napisiTekst(sadrzaj, obicanFont, 11, 50, y, infoTermin);

                // 3. Zaglavlje tabele
                y -= 35;
                napisiTekst(sadrzaj, naslovFont, 10, 50, y, "Br.");
                napisiTekst(sadrzaj, naslovFont, 10, 80, y, "Ime i prezime");
                napisiTekst(sadrzaj, naslovFont, 10, 230, y, "JMBG");
                napisiTekst(sadrzaj, naslovFont, 10, 340, y, "Telefon");

                // Linija ispod zaglavlja
                y -= 5;
                sadrzaj.setLineWidth(0.6f);
                sadrzaj.moveTo(50, y);
                sadrzaj.lineTo(545, y);
                sadrzaj.stroke();

                // 4. Ispis kandidata
                int redniBroj = 1;
                for (Kandidat kandidat : kandidati) {
                    y -= 20;
                    if (y < 50) break; // Zastita od prelivanja stranice

                    napisiTekst(sadrzaj, obicanFont, 10, 50, y, String.valueOf(redniBroj++));
                    napisiTekst(sadrzaj, obicanFont, 10, 80, y, ocisti(kandidat.getIme() + " " + kandidat.getPrezime()));
                    napisiTekst(sadrzaj, obicanFont, 10, 230, y, kandidat.getJmbg());
                    napisiTekst(sadrzaj, obicanFont, 10, 340, y, kandidat.getTelefon());
                }

                // Ako nema kandidata
                if (kandidati.isEmpty()) {
                    napisiTekst(sadrzaj, obicanFont, 10, 50, y - 20, "Trenutno nema prijavljenih kandidata za ovaj termin.");
                }
            }

            ByteArrayOutputStream izlaz = new ByteArrayOutputStream();
            dokument.save(izlaz);
            return izlaz.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Greska pri generisanju PDF spiska kandidata", e);
        }
    }
    private void napisiTekst(PDPageContentStream cs, PDType1Font font, float velicina, float x, float y, String tekst) throws IOException {
        cs.beginText(); 
        cs.setFont(font, velicina);
        cs.newLineAtOffset(x, y); //pomeramo kursor
        cs.showText(tekst);
        cs.endText();
    }
}

