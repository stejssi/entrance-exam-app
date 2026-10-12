import { useState, useEffect } from "react";
import { Form, Button, Alert, Row, Col, Card } from "react-bootstrap";
import { getCenovnik, definisiCenu } from "../api/cenovnikService";
import { getVrsteIspita } from "../api/vrstaIspitaService";

const PRAZNA_FORMA = {
  vrstaIspitaId: "",
  cena: "",
};

function AdminCenovnikPage() {
  const [cenovnik, setCenovnik] = useState([]);
  const [vrsteIspita, setVrsteIspita] = useState([]);
  const [forma, setForma] = useState(PRAZNA_FORMA);
  const [poruka, setPoruka] = useState(null);

  useEffect(() => {
    ucitajCenovnik();
    getVrsteIspita().then((response) => setVrsteIspita(response.data));
  }, []);

  function ucitajCenovnik() {
    getCenovnik().then((response) => setCenovnik(response.data));
  }

  // Prikazujemo samo trenutno važeće cene (vaziDo == null), istorija se ne prikazuje ovde
  const trenutneCene = cenovnik.filter((c) => c.vaziDo === null);

  function izmeniPolje(event) {
    setForma({ ...forma, [event.target.name]: event.target.value });
  }

  function pripremiIzmenu(item) {
    setForma({
      vrstaIspitaId: item.vrstaIspitaId,
      cena: item.cena,
    });
  }

  function posaljiForm(event) {
    event.preventDefault();
    setPoruka(null);
    definisiCenu(forma)
      .then(() => {
        setPoruka({ tip: "success", tekst: "Cena je uspešno sačuvana." });
        setForma(PRAZNA_FORMA);
        ucitajCenovnik();
      })
      .catch((error) => {
        const tekst = error.response?.data?.poruka || "Greška pri čuvanju cene";
        setPoruka({ tip: "danger", tekst });
      });
  }

  return (
    <div>
      <h2 className="mb-4">Administracija cenovnika</h2>

      {poruka && <Alert variant={poruka.tip}>{poruka.tekst}</Alert>}

      <h5 className="mb-3 text-muted fw-normal" style={{ fontSize: "1rem" }}>
        Trenutno definisane cene
      </h5>
      <Row className="mb-4">
        {trenutneCene.map((red) => (
          <Col md={6} key={red.id} className="mb-3">
            <Card className="shadow-sm border-0 bg-white">
              <Card.Body className="d-flex justify-content-between align-items-center p-3">
                <div>
                  <small className="text-uppercase text-muted fw-semibold d-block mb-1"
                    style={{ letterSpacing: "0.03em", fontSize: "0.75rem" }}>
                    {red.vrstaIspitaNaziv}
                  </small>
                  <div className="d-flex align-items-baseline gap-1">
                    <span className="fw-bold" style={{ fontSize: "1.6rem", color: "#1B2A4A" }}>
                      {Number(red.cena).toLocaleString("sr-RS")}
                    </span>
                    <span className="text-muted fs-6">RSD</span>
                  </div>
                  <small className="text-muted d-block mt-1">Važi od {red.vaziOd}</small>
                </div>
                <Button variant="outline-secondary" size="sm" onClick={() => pripremiIzmenu(red)}>
                  Izmeni cenu
                </Button>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      <Card className="shadow-sm border-0">
        <Card.Header className="bg-white py-3 border-bottom-0">
          <h5 className="mb-0 fw-semibold">Ažuriraj ili dodaj cenu</h5>
        </Card.Header>
        <Card.Body className="p-4 pt-1">
          <Form onSubmit={posaljiForm}>
            <Row className="align-items-end g-3">
              <Col md={5}>
                <Form.Group>
                  <Form.Label className="fw-semibold small">Vrsta ispita</Form.Label>
                  <Form.Select name="vrstaIspitaId" value={forma.vrstaIspitaId} onChange={izmeniPolje} required>
                    <option value="">Izaberi...</option>
                    {vrsteIspita.map((v) => (
                      <option key={v.id} value={v.id}>{v.naziv}</option>
                    ))}
                  </Form.Select>
                </Form.Group>
              </Col>

              <Col md={5}>
                <Form.Group>
                  <Form.Label className="fw-semibold small">Nova cena (din)</Form.Label>
                  <Form.Control
                    type="number"
                    name="cena"
                    value={forma.cena}
                    onChange={izmeniPolje}
                    placeholder="Unesi iznos u dinarima"
                    min="0"
                    step="1"
                    required
                  />
                </Form.Group>
              </Col>

              <Col md={2}>
                <Button type="submit" variant="dark" className="w-100">
                  Sačuvaj
                </Button>
              </Col>
            </Row>
          </Form>
        </Card.Body>
      </Card>
    </div>
  );
}

export default AdminCenovnikPage;