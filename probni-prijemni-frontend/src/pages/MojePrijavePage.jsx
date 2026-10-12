import { useState, useEffect } from "react";
import { Table, Badge, Button, Alert } from "react-bootstrap";
import { getPrijaveKandidata, otkaziPrijavu } from "../api/prijavaService";
import { useAuth } from "../context/AutentifikacijaContext";

function MojePrijavePage() {
  const [prijave, setPrijave] = useState([]); // Niz korisnikovih prijava
  const [poruka, setPoruka] = useState(null);

  // Preuzimamo objekat ulogovanog korisnika
  const { korisnik } = useAuth();

  //Učitavamo prijave odmah čim se komponenta učita
  useEffect(() => {
    ucitajPrijave();
  }, []);

  function ucitajPrijave() {
    // Pozivamo backend servis da dobijemo sve prijave za ulogovanog kandidata
    getPrijaveKandidata(korisnik.kandidatId).then((response) => {
      setPrijave(response.data);
    });
  }

  function otkaziKlik(prijavaId) {
    const potvrda = window.confirm("Da li sigurno želiš da otkažeš ovu prijavu?");
    if (!potvrda) return;

    setPoruka(null);
    otkaziPrijavu(korisnik.kandidatId, prijavaId)
      .then(() => {
        setPoruka({ tip: "success", tekst: "Prijava je otkazana." });
        ucitajPrijave();
      })
      .catch((error) => {
        const tekst = error.response?.data?.poruka || "Greška pri otkazivanju";
        setPoruka({ tip: "danger", tekst });
      });
  }

 
  const statusBoje = {
  KREIRANA: "#2C4A6F",              /* Tamnija, prigušena teget/plava (slaže se sa navbarom) */
  PRIJAVLJEN: "#3B6E52",            /* Pastelna, tlena maslinasto-zelena */
  OTKAZANA: "#9E4A4A",    /* Tvoja crvena/pečat - pošto nema dugmeta Otkaži, nema sudaranja */
};

  const statusTekst = {
  KREIRANA: "Kreirana (čeka uplatu)",
  PRIJAVLJEN: "Prijavljen/a",
  OTKAZANA: "Otkazana"
};

  return (
    <div>
      <h2 className="mb-3">Moje prijave</h2>

      {poruka && <Alert variant={poruka.tip}>{poruka.tekst}</Alert>}

      {/* Ako kandidat nema nijednu prijavu, ispisujemo poruku */}
      {prijave.length === 0 && <p>Trenutno nemate nijednu prijavu.</p>}
    <div className="table-responsive">
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Datum prijave</th>
            <th>Termini</th>
            <th>Ukupna cena</th>
            <th>Status</th>
            <th>Akcija</th>
          </tr>
        </thead>
        <tbody>
          {prijave.map((prijava) => (
            <tr key={prijava.id}>
                {/* new Date - formatira datum i vreme  */}     
              <td>{new Date(prijava.datumPrijave).toLocaleString()}</td>
              <td>
                {/* Jedna prijava može imati više termina, pa prolazimo i kroz taj podniz */}
                <ul className="mb-0 ps-3">
                  {prijava.termini.map((termin) => (
                    <li key={termin.id}>
                      {termin.vrstaIspitaNaziv} - {termin.datumOdrzavanja} u {termin.vremePocetka}
                    </li>
                  ))}
                </ul>
              </td>
              <td>{prijava.ukupnaCena} din</td>
              <td style={{ color: statusBoje[prijava.status], fontWeight: 500 }}>
                {statusTekst[prijava.status] || prijava.status}
              </td>
              <td>
                {/* Dugme "Otkaži" se prikazuje samo ako prijava nije uplaćena i nije već otkazana */}
                {!prijava.uplacena && prijava.status !== "OTKAZANA" && (
                  <Button variant="outline-danger" size="sm" onClick={() => otkaziKlik(prijava.id)}>
                    Otkaži
                  </Button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
    </div>
  );
}

export default MojePrijavePage;