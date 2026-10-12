import { useState, useEffect } from "react";
import { Table, Form, Button, Alert, Row, Col, Card } from "react-bootstrap";
import { getTermini, kreirajTermin, azurirajTermin, obrisiTermin } from "../api/terminService";
import { getVrsteIspita } from "../api/vrstaIspitaService";

const PRAZNA_FORMA = {
  datumOdrzavanja: "",
  vremePocetka: "",
  vrstaIspitaId: "",
  adresa: "",
};

function AdminTerminiPage() {
  const [termini, setTermini] = useState([]);
  const [vrsteIspita, setVrsteIspita] = useState([]);
  const [forma, setForma] = useState(PRAZNA_FORMA);
  const [editId, setEditId] = useState(null);
  const [poruka, setPoruka] = useState(null);

  useEffect(() => {
    ucitajTermine();
    getVrsteIspita().then((response) => setVrsteIspita(response.data));
  }, []);

  function ucitajTermine() {
    getTermini().then((response) => setTermini(response.data));
  }

  function izmeniPolje(event) {
    setForma({ ...forma, [event.target.name]: event.target.value });
  }

  function posaljiForm(event) {
    event.preventDefault();
    setPoruka(null);

    const izmenaILIkreiranje = editId
      ? azurirajTermin(editId, forma)
      : kreirajTermin(forma);

    izmenaILIkreiranje
      .then(() => {
        setPoruka({ tip: "success", tekst: editId ? "Termin je izmenjen." : "Termin je kreiran." });
        resetujFormu();
        ucitajTermine();
      })
      .catch((error) => {
        const tekst = error.response?.data?.poruka || "Greška pri čuvanju termina";
        setPoruka({ tip: "danger", tekst });
      });
  }

  function kliknutoIzmeni(termin) {
    setEditId(termin.id);
    setForma({
      datumOdrzavanja: termin.datumOdrzavanja,
      vremePocetka: termin.vremePocetka,
      vrstaIspitaId: termin.vrstaIspitaId,
      adresa: termin.adresa,
    });
  }

  function resetujFormu() {
    setEditId(null);
    setForma(PRAZNA_FORMA);
  }

  function kliknutoObrisi(id) {
    const potvrda = window.confirm("Da li sigurno želiš da obrišeš ovaj termin?");
    if (!potvrda) return;

    obrisiTermin(id)
      .then(() => {
        setPoruka({ tip: "success", tekst: "Termin je obrisan." });
        ucitajTermine();
      })
      .catch((error) => {
        const tekst = error.response?.data?.poruka || "Greška pri brisanju termina";
        setPoruka({ tip: "danger", tekst });
      });
  }

  return (
    <div>
      <h2 className="mb-3">Administracija termina</h2>

      {poruka && <Alert variant={poruka.tip}>{poruka.tekst}</Alert>}

      <Card className="p-3 mb-4">
        <h5>{editId ? "Izmena termina" : "Novi termin"}</h5>
        <Form onSubmit={posaljiForm}>
          <Row>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Datum</Form.Label>
                <Form.Control
                  type="date"
                  name="datumOdrzavanja"
                  value={forma.datumOdrzavanja}
                  onChange={izmeniPolje}
                  required
                />
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Vreme</Form.Label>
                <Form.Control
                  type="time"
                  name="vremePocetka"
                  value={forma.vremePocetka}
                  onChange={izmeniPolje}
                  required
                />
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Vrsta ispita</Form.Label>
                <Form.Select name="vrstaIspitaId" value={forma.vrstaIspitaId} onChange={izmeniPolje} required>
                  <option value="">Izaberi...</option>
                  {vrsteIspita.map((v) => (
                    <option key={v.id} value={v.id}>{v.naziv}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={3}>
              <Form.Group className="mb-3">
                <Form.Label>Adresa</Form.Label>
                <Form.Control
                  type="text"
                  name="adresa"
                  value={forma.adresa}
                  onChange={izmeniPolje}
                  required
                />
              </Form.Group>
            </Col>
          </Row>

          <Button type="submit" variant="primary" className="me-2">
            {editId ? "Sačuvaj izmene" : "Dodaj termin"}
          </Button>

          {editId && (
            <Button variant="secondary" onClick={resetujFormu}>
              Otkaži izmenu
            </Button>
          )}
        </Form>
      </Card>

      <div className="table-responsive">
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Datum</th>
              <th>Vreme</th>
              <th>Vrsta ispita</th>
              <th>Adresa</th>
              <th>Akcije</th>
            </tr>
          </thead>
          <tbody>
            {termini.map((termin) => (
              <tr key={termin.id}>
                <td>{termin.datumOdrzavanja}</td>
                <td>{termin.vremePocetka}</td>
                <td>{termin.vrstaIspitaNaziv}</td>
                <td>{termin.adresa}</td>
                <td>
                  <Button variant="outline-primary" size="sm" className="me-2" onClick={() => kliknutoIzmeni(termin)}>
                    Izmeni
                  </Button>
                  <Button variant="outline-danger" size="sm" onClick={() => kliknutoObrisi(termin.id)}>
                    Obriši
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
}

export default AdminTerminiPage;