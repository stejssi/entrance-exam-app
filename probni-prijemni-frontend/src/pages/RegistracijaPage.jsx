import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Form, Button, Alert, Card } from "react-bootstrap";
import { registrujKandidata } from "../api/autentifikacijaService";

function RegistracijaPage() {
  const [podaci, setPodaci] = useState({
    korisnickoIme: "",
    lozinka: "",
    email: "",
    ime: "",
    prezime: "",
    jmbg: "",
    brojTelefona: "",
  });
  // Greške pamtimo po imenima polja (npr. greskePolja.lozinka)
  const [greskePolja, setGreskePolja] = useState({});
  const [opstaGreska, setOpstaGreska] = useState("");
  const navigate = useNavigate();
  
  function izmeniPolje(event) {
    setPodaci({ ...podaci, //kopira sve trenutne vrednosti iz obj podaci
      [event.target.name]: event.target.value }); //uzima ime polja u kom korisnik trenutno kuca i dodeljuje novu vrednost
  }

  //slanje forme backendu
  function posaljiForm(event) {
    event.preventDefault();  //sprecava osvezavanje
    setGreskePolja({});
    setOpstaGreska("");

    registrujKandidata(podaci) //salje podatke na backend
      .then(() => {            //ako je registracija uspešna, salje na login stranicu
      navigate("/potvrda-registracije", { state: { korisnickoIme: podaci.korisnickoIme } });
      })
      .catch((error) => {
        const data = error.response?.data;

        //Obrada različitih formata grešaka sa backenda
        if (data?.detalji && Array.isArray(data.detalji)) {
          const noveGreske = {};
          data.detalji.forEach((item) => {
            if (typeof item === "string" && item.includes(":")) {
              const [polje, ...porukaDelovi] = item.split(":");
              noveGreske[polje.trim()] = porukaDelovi.join(":").trim();
            }
          });
          setGreskePolja(noveGreske);
        } else if (typeof data === "string") {
          setOpstaGreska(data);
        } else if (data?.poruka) {
          setOpstaGreska(data.poruka);
        } else {
          setOpstaGreska("Greška pri registraciji");
        }
      });
  }

  return (
    <Card className="p-4" style={{ maxWidth: "500px", margin: "0 auto" }}>
      <h2 className="mb-3">Registracija kandidata</h2>

      {opstaGreska && <Alert variant="danger">{opstaGreska}</Alert>}

      <Form onSubmit={posaljiForm}>
        <Form.Group className="mb-3">
          <Form.Label>Ime</Form.Label>
          <Form.Control name="ime" value={podaci.ime} onChange={izmeniPolje} required />
          {greskePolja.ime && <div className="text-danger small mt-1">{greskePolja.ime}</div>}
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Prezime</Form.Label>
          <Form.Control name="prezime" value={podaci.prezime} onChange={izmeniPolje} required />
          {greskePolja.prezime && <div className="text-danger small mt-1">{greskePolja.prezime}</div>}
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>JMBG</Form.Label>
          <Form.Control
            name="jmbg" // mora da se poklapa sa imenom ključa u podaci state-u, da bi izmeniPolje znalo koji ključ da menja
            value={podaci.jmbg} //cita trenutnu vrednost iz podaci state-a
            onChange={izmeniPolje}
            required
            maxLength={13}
          />
          {greskePolja.jmbg && <div className="text-danger small mt-1">{greskePolja.jmbg}</div>}
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Broj telefona</Form.Label>
          <Form.Control 
            name="brojTelefona" 
            value={podaci.brojTelefona} 
            onChange={izmeniPolje} 
            required
            pattern="06[0-9]{7,8}"
            title="Broj telefona mora početi sa 06 i imati ukupno 9 ili 10 cifara (npr. 0691234567)" 
          />
          {greskePolja.brojTelefona && <div className="text-danger small mt-1">{greskePolja.brojTelefona}</div>}
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Email</Form.Label>
          <Form.Control type="email" name="email" value={podaci.email} onChange={izmeniPolje} required />
          {greskePolja.email && <div className="text-danger small mt-1">{greskePolja.email}</div>}
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Korisničko ime</Form.Label>
          <Form.Control name="korisnickoIme" value={podaci.korisnickoIme} onChange={izmeniPolje} required />
          {greskePolja.korisnickoIme && <div className="text-danger small mt-1">{greskePolja.korisnickoIme}</div>}
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Lozinka</Form.Label>
          <Form.Control 
            type="password" 
            name="lozinka" 
            value={podaci.lozinka} 
            onChange={izmeniPolje} 
            required 
            title="Lozinka mora imati najmanje 6 karaktera"
          />
          {greskePolja.lozinka && <div className="text-danger small mt-1">{greskePolja.lozinka}</div>}
        </Form.Group>

        <Button type="submit" variant="primary">Registruj se</Button>
      </Form>
    </Card>
  );
}

export default RegistracijaPage;