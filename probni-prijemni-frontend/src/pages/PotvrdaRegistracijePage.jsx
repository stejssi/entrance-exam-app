import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Form, Button, Alert, Card } from "react-bootstrap";
import { potvrdiRegistraciju } from "../api/autentifikacijaService";

function PotvrdaRegistracijePage() {
  const location = useLocation(); // hook koji nam omogucava da pristupimo podacima prosledjenim kroz navigate
  const navigate = useNavigate();

  const [korisnickoIme, setKorisnickoIme] = useState(location.state?.korisnickoIme || ""); //izvlacimo korisnickoIme iz prosledjenih podataka, ako ih nema, postavljamo na prazan string
  const [kod, setKod] = useState("");
  const [poruka, setPoruka] = useState(null);

  function posaljiForm(event) {
    event.preventDefault();
    setPoruka(null);

    potvrdiRegistraciju({ korisnickoIme, kod })
      .then(() => {
        navigate("/login");
      })
      .catch((error) => {
        const tekst = error.response?.data?.poruka || "Greška pri potvrdi";
        setPoruka({ tip: "danger", tekst });
      });
  }

  return (
    <Card className="p-4" style={{ maxWidth: "400px", margin: "0 auto" }}>
      <h2 className="mb-3">Potvrda registracije</h2>
      <p className="text-muted">Poslali smo verifikacioni kod na vašu email adresu.</p>

      {poruka && <Alert variant={poruka.tip}>{poruka.tekst}</Alert>}

      <Form onSubmit={posaljiForm}>
        <Form.Group className="mb-3">
          <Form.Label>Korisničko ime</Form.Label>
          <Form.Control
            value={korisnickoIme}
            onChange={(e) => setKorisnickoIme(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Verifikacioni kod</Form.Label>
          <Form.Control
            value={kod}
            onChange={(e) => setKod(e.target.value)}
            maxLength={6}
            required
          />
        </Form.Group>

        <Button type="submit" variant="primary">Potvrdi nalog</Button>
      </Form>
    </Card>
  );
}

export default PotvrdaRegistracijePage;