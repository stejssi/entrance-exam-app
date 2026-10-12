import { useState, useEffect } from "react";
import { Table, Form, Button, Alert, Row, Col, Card } from "react-bootstrap";
import { evidentirajUplatu } from "../api/uplataService";
import { getTermini } from "../api/terminService";
import { pretraziPrijave, preuzmiSpisakKandidataPdf } from "../api/prijavaService";
import { useAuth } from "../context/AutentifikacijaContext";

const PRAZNI_FILTERI = { status: "", terminId: "", prezime: "" };

function AdminPrijavePage() {
  const [prijave, setPrijave] = useState([]);
  const [termini, setTermini] = useState([]);
  const [filteri, setFilteri] = useState(PRAZNI_FILTERI);
  const [poruka, setPoruka] = useState(null);

  const { korisnik } = useAuth();

  useEffect(() => {
    getTermini().then((response) => setTermini(response.data));
    ucitajPrijave(PRAZNI_FILTERI);
  }, []);

  function izmeniFilter(event) {
    setFilteri({ ...filteri, [event.target.name]: event.target.value });
  }

  function ucitajPrijave(trenutniFilteri) {
  const parametri = {};
  if (trenutniFilteri.status) parametri.status = trenutniFilteri.status;
  if (trenutniFilteri.terminId) parametri.terminId = trenutniFilteri.terminId;
  if (trenutniFilteri.prezime) parametri.prezime = trenutniFilteri.prezime;

  pretraziPrijave(parametri)
    .then((response) => setPrijave(response.data))
    .catch((error) => {
      const tekst = error.response?.data?.poruka || "Greška pri učitavanju prijava sa servera";
      setPoruka({ tip: "danger", tekst });
    });
}

  function posaljiPretragu(event) {
    event.preventDefault();
    ucitajPrijave(filteri);
  }

  function resetujFiltere() {
    setFilteri(PRAZNI_FILTERI);
    ucitajPrijave(PRAZNI_FILTERI);
  }

  function evidentirajKlik(prijavaId) {
    setPoruka(null);
    evidentirajUplatu(korisnik.id, prijavaId)
      .then(() => {
        setPoruka({ tip: "success", tekst: "Uplata je evidentirana." });
        ucitajPrijave(filteri);
      })
      .catch((error) => {
        const tekst = error.response?.data?.poruka || "Greška pri evidentiranju uplate";
        setPoruka({ tip: "danger", tekst });
      });
  }

  function preuzmiPdfKlik() {
  if (!filteri.terminId) {
    setPoruka({ tip: "warning", tekst: "Prvo izaberi konkretan termin da preuzmeš spisak." });
    return;
  }

  preuzmiSpisakKandidataPdf(filteri.terminId)
  .then((response) => {
    //pretvara taj Blob u privremeni URL koji postoji samo u memoriji browsera
    const url = window.URL.createObjectURL(new Blob([response.data], { type: "application/pdf" }));
    const link = document.createElement("a");//kreira se nevidljiv <a>
    link.href = url; //postavlja se href na taj privremeni URL
    link.download = "spisak-kandidata.pdf"; //postavlja se ime fajla koji ce se preuzeti
    document.body.appendChild(link);
    link.click();  //simulira se klik na taj link, i browser preuzima fajl
    link.remove();
    window.URL.revokeObjectURL(url);
    setPoruka({ tip: "success", tekst: "Spisak je preuzet." });
    })
    .catch((error) => {
      console.error("Greska pri preuzimanju PDF-a:", error);
      setPoruka({ tip: "danger", tekst: "Greška pri preuzimanju spiska." });
    });
}

  const statusBoje = {
    KREIRANA: "#2C4A6F",
    PRIJAVLJEN: "#3B6E52",
    OTKAZANA: "#9E4A4A",
  };

  const statusTekst = {
    KREIRANA: "Kreirana (čeka uplatu)",
    PRIJAVLJEN: "Prijavljen/a",
    OTKAZANA: "Otkazana",
  };

  return (
    <div>
      <h2 className="mb-3">Pregled i pretraga prijava</h2>

      {poruka && <Alert variant={poruka.tip}>{poruka.tekst}</Alert>}

      <Card className="p-3 mb-4">
      <Form onSubmit={posaljiPretragu}>
        <Row className="align-items-end g-2">
          <Col lg={2} md={4}>
            <Form.Group>
              <Form.Label>Status</Form.Label>
              <Form.Select name="status" value={filteri.status} onChange={izmeniFilter}>
                <option value="">Svi</option>
                <option value="KREIRANA">Kreirana</option>
                <option value="PRIJAVLJEN">Prijavljen</option>
                <option value="OTKAZANA">Otkazana</option>
              </Form.Select>
            </Form.Group>
          </Col>

          <Col lg={3} md={4}>
            <Form.Group>
              <Form.Label>Termin</Form.Label>
              <Form.Select name="terminId" value={filteri.terminId} onChange={izmeniFilter}>
                <option value="">Svi termini</option>
                {termini.map((termin) => (
                <option key={termin.id} value={termin.id}>
                  {termin.vrstaIspitaNaziv} - {termin.datumOdrzavanja}
                </option>
              ))}
              </Form.Select>
            </Form.Group>
          </Col>

          <Col lg={3} md={4}>
            <Form.Group>
              <Form.Label>Prezime kandidata</Form.Label>
              <Form.Control
                type="text"
                name="prezime"
                value={filteri.prezime}
                onChange={izmeniFilter}
                placeholder="npr. Petrović"
              />
            </Form.Group>
          </Col>

          {/* Sva tri dugmeta sa razmakom u jednom redu */}
          <Col lg={4} md={12} className="d-flex justify-content-end gap-2">
            <Button type="submit" variant="primary">
              Pretraži
            </Button>
            <Button type="button" variant="outline-secondary" onClick={resetujFiltere}>
              Resetuj
            </Button>
            <Button 
              type="button" 
              onClick={preuzmiPdfKlik}
              style={{ color: "#800020", borderColor: "#800020", backgroundColor: "transparent" }}
            >
              Preuzmi spisak (PDF)
            </Button>
          </Col>
        </Row>
      </Form>
    </Card>

      {prijave.length === 0 && <p>Nema prijava koje odgovaraju pretrazi.</p>}

    <div className="table-responsive">
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Kandidat</th>
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
              <td>{prijava.kandidatImePrezime}</td>
              <td>{new Date(prijava.datumPrijave).toLocaleString()}</td>
              <td>
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
                {!prijava.uplacena && prijava.status !== "OTKAZANA" && (
                  <Button variant="outline-success" size="sm" onClick={() => evidentirajKlik(prijava.id)}>
                    Evidentiraj uplatu
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

export default AdminPrijavePage;