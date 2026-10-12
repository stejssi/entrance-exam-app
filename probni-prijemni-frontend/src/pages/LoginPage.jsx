import { useState } from "react"; //useState pamti podatke
import { useNavigate } from "react-router-dom"; //useNavigate služi za preusmeravanje na drugu stranicu
import { Form, Button, Alert, Card } from "react-bootstrap"; //gotove komponente
import { login } from "../api/autentifikacijaService"; //poziv funkcije koja šalje podatke na backend
import { useAuth } from "../context/AutentifikacijaContext";//poziv contexta koji pamti ko je ulogovan


function LoginPage() {
  //polja za formu, useState("")-kutijica za trenutnu vrednost
  const [korisnickoIme, setKorisnickoIme] = useState("");
  //    trenutna vrednost, funkcija koja menja tu vrednost
  const [lozinka, setLozinka] = useState("");
  const [greska, setGreska] = useState("");

  const { prijaviKorisnika } = useAuth(); //funkcija koja pamti ko je ulogovan, iz contexta
  const navigate = useNavigate();  //promena stranice

  function posaljiForm(event) { //kad se klikne dugme "Prijavi se", poziva se ova funkcija
    event.preventDefault();  //sprečava da se stranica osveži i izgube podaci iz forme
    setGreska("");

    login({ korisnickoIme, lozinka })
      .then((response) => {   //ako je prijava uspešna, poziva se funkcija koja pamti ko je ulogovan i preusmerava na početnu stranicu
        prijaviKorisnika(response.data);
        navigate("/");
      })
      .catch((error) => {    //ako je prijava neuspešna, greska
        const poruka = error.response?.data?.poruka || "Greska pri prijavi";
        setGreska(poruka);
      });
  }

  //PRIKAZ NA EKRANU
  return (
    <Card className="p-4" style={{ maxWidth: "400px", margin: "0 auto" }}>
      <h2 className="mb-3">Prijava na sistem</h2>

      {/*Ako postoji greska, prikazuje se Alert komponenta sa porukom o gresci*/}
      {greska && <Alert variant="danger">{greska}</Alert>}

      {/*Polja unutar forme*/}
      <Form onSubmit={posaljiForm}>
        <Form.Group className="mb-3">
          <Form.Label>Korisničko ime</Form.Label>
          {/*input polje na ekranu uvek prikazuje ono što je u korisnickoIme state-u, i svaki put kad ukucaš slovo, onChange odmah ažurira taj state*/}
          <Form.Control
            type="text"
            value={korisnickoIme}
            onChange={(e) => setKorisnickoIme(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Lozinka</Form.Label>
          <Form.Control
            type="password"
            value={lozinka}
            onChange={(e) => setLozinka(e.target.value)}
            required
          />
        </Form.Group>

        <Button type="submit" variant="primary">Prijavi se</Button>
      </Form>
    </Card>
  );
}

export default LoginPage;