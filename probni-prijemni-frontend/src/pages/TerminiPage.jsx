import { useState, useEffect } from "react";
import { Table, Form, Button, Alert, Card } from "react-bootstrap";
import { getTermini } from "../api/terminService";
import { getCenovnik } from "../api/cenovnikService";
import { kreirajPrijavu } from "../api/prijavaService";
import { useAuth } from "../context/AutentifikacijaContext";

function TerminiPage() {
  const [termini, setTermini] = useState([]);
  const [cenovnik, setCenovnik] = useState([]);
  const [izabraniIds, setIzabraniIds] = useState([]);
  const [poruka, setPoruka] = useState(null);

  const { korisnik } = useAuth();

  useEffect(() => {
    getTermini().then((response) => setTermini(response.data));
    getCenovnik().then((response) => setCenovnik(response.data));
  }, []);

  function nadjiCenu(vrstaIspitaId) {
    // Samo trenutno važeće cene (vaziDo == null) učestvuju u pretrazi
    const red = cenovnik.find((c) => c.vrstaIspitaId === vrstaIspitaId && c.vaziDo === null);
    return red ? red.cena : null;
  }

  function prekidacIzbora(terminId) {
    if (izabraniIds.includes(terminId)) {
      setIzabraniIds(izabraniIds.filter((id) => id !== terminId));
    } else {
      setIzabraniIds([...izabraniIds, terminId]);
    }
  }

  const ukupnaCena = termini
    .filter((t) => izabraniIds.includes(t.id))
    .reduce((zbir, t) => zbir + (nadjiCenu(t.vrstaIspitaId) || 0), 0);

  function posaljiPrijavu() {
    setPoruka(null);
    kreirajPrijavu(korisnik.kandidatId, izabraniIds)
      .then(() => {
        setPoruka({ tip: "success", tekst: "Uspešno ste se prijavili!" });
        setIzabraniIds([]);
      })
      .catch((error) => {
        const tekst = error.response?.data?.poruka || "Greška pri prijavi";
        setPoruka({ tip: "danger", tekst });
      });
  }

  return (
    <div>
      <h2>Raspoloživi termini</h2>

      {!korisnik && (
        <Alert variant="light" className="border">
          Prijavite se ili se registrujte da biste se prijavili na termin.
        </Alert>
      )}

      {poruka && <Alert variant={poruka.tip}>{poruka.tekst}</Alert>}
      <div className="table-responsive">
        <Table striped bordered hover className="align-middle">
          <thead>
            <tr>
              {korisnik?.uloga === "KANDIDAT" && <th style={{ width: "40px" }}></th>}
              <th>Vrsta ispita</th>
              <th>Datum</th>
              <th>Vreme</th>
              <th>Adresa</th>
              <th>Cena</th>
            </tr>
          </thead>
          <tbody>
            {termini.map((termin) => (
              <tr key={termin.id}>
                {korisnik?.uloga === "KANDIDAT" && (
                  <td>
                    <Form.Check
                      type="checkbox"
                      checked={izabraniIds.includes(termin.id)}
                      onChange={() => prekidacIzbora(termin.id)}
                    />
                  </td>
                )}
                <td>{termin.vrstaIspitaNaziv}</td>
                <td>{termin.datumOdrzavanja}</td>
                <td>{termin.vremePocetka}</td>
                <td>{termin.adresa}</td>
                <td style={{ fontFamily: "var(--font-mono)" }}>
                  {nadjiCenu(termin.vrstaIspitaId) ?? "—"}
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
      {korisnik?.uloga === "KANDIDAT" && izabraniIds.length > 0 && (
        <Card className="p-3">
          <p className="mb-1">Izabrano termina: {izabraniIds.length}</p>
          <p>Ukupna cena: <strong style={{ fontFamily: "var(--font-mono)" }}>{ukupnaCena} din</strong></p>
          <Button variant="success" onClick={posaljiPrijavu}>Prijavi se</Button>
        </Card>
      )}
    </div>
  );
}

export default TerminiPage;