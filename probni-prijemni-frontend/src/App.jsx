import { Routes, Route, Navigate } from 'react-router-dom';
import NavigacionaTraka from './components/NavigacionaTraka.jsx';
import LoginPage from './pages/LoginPage.jsx';
import RegistracijaPage from './pages/RegistracijaPage.jsx';
import TerminiPage from './pages/TerminiPage.jsx';
import MojePrijavePage from './pages/MojePrijavePage.jsx';
import AdminTerminiPage from './pages/AdminTerminiPage.jsx';
import AdminPrijavePage from './pages/AdminPrijavePage.jsx';
import AdminCenovnikPage from './pages/AdminCenovnikPage.jsx';
import PotvrdaRegistracijePage from "./pages/PotvrdaRegistracijePage";

function App() {
  return (
    <>
      <NavigacionaTraka />
      <div className="container">
        <Routes>
          <Route path="/" element={<TerminiPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/registracija" element={<RegistracijaPage />} />
          <Route path="/potvrda-registracije" element={<PotvrdaRegistracijePage />} />
          <Route path="/moje-prijave" element={<MojePrijavePage />} />
          <Route path="/admin/termini" element={<AdminTerminiPage />} />
          <Route path="/admin/cenovnik" element={<AdminCenovnikPage />} />
          <Route path="/admin/prijave" element={<AdminPrijavePage />} />
        </Routes>
      </div>
    </>
  );
}

export default App;