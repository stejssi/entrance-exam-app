import { Navbar, Nav, Container, Button } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AutentifikacijaContext";

function NavigacionaTraka() {
  const { korisnik, odjaviKorisnika } = useAuth();
  const navigate = useNavigate();

  function odjavaKlik() {
    odjaviKorisnika();
    navigate("/login");
  }

  return (
    <Navbar expand="lg" className="navbar-fon mb-4 py-2.5" variant="dark">
      <Container className="align-items-center">
        {/* Čist, moderan brand u sans-serifu */}
        <Navbar.Brand as={Link} to="/" className="app-brand me-4 d-flex align-items-center gap-2">
          <span className="fw-bold">FON</span>
          <span className="text-white-50 fw-light">|</span>
          <span>Probni prijemni</span>
        </Navbar.Brand>
        
        <Navbar.Toggle aria-controls="glavni-meni" />
        
        <Navbar.Collapse id="glavni-meni" className="align-items-center">
          <Nav className="me-auto align-items-center">
            <Nav.Link as={Link} to="/">Termini</Nav.Link>
            
            {korisnik?.uloga === "KANDIDAT" && (
              <Nav.Link as={Link} to="/moje-prijave">Moje prijave</Nav.Link>
            )}
            {korisnik?.uloga === "ADMIN" && (
              <>
                <Nav.Link as={Link} to="/admin/termini">Admin - Termini</Nav.Link>
                <Nav.Link as={Link} to="/admin/cenovnik">Admin - Cenovnik</Nav.Link>
                <Nav.Link as={Link} to="/admin/prijave">Admin - Prijave</Nav.Link>
              </>
            )}
          </Nav>

          <Nav className="align-items-center gap-3">
            {korisnik ? (
              <>
                <span className="text-white-50 fs-6">
                  {korisnik.korisnickoIme}
                </span>
                <Button variant="outline-light" size="sm" onClick={odjavaKlik}>
                  Odjavi se
                </Button>
              </>
            ) : (
              <>
                <Nav.Link as={Link} to="/login">Prijava</Nav.Link>
                <Nav.Link as={Link} to="/registracija">Registracija</Nav.Link>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavigacionaTraka;