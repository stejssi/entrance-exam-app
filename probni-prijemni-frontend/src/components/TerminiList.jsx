import { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import { Table, Container, Spinner, Alert } from 'react-bootstrap';

export default function TerminiList() {
  const [termini, setTermini] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    api.get('/api/termini')
      .then(response => {
        setTermini(response.data);
        setLoading(false);
      })
      .catch(err => {
        console.error('Greška:', err);
        setError('Neuspešno učitavanje termina sa servera.');
        setLoading(false);
      });
  }, []);

  if (loading) return <Container className="mt-5 text-center"><Spinner animation="border" /></Container>;
  if (error) return <Container className="mt-5"><Alert variant="danger">{error}</Alert></Container>;

  return (
    <Container className="mt-4">
      <h2>Pregled Termina</h2>
      <Table striped bordered hover responsive className="mt-3">
        <thead>
          <tr>
            <th>ID</th>
            <th>Naziv / Opis</th>
            <th>Datum</th>
          </tr>
        </thead>
        <tbody>
          {termini.length > 0 ? (
            termini.map((t) => (
              <tr key={t.id || t.terminId}>
                <td>{t.id || t.terminId}</td>
                <td>{t.naziv || t.opis || t.nazivTermina}</td>
                <td>{t.datum || t.vreme}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3" className="text-center">Nema unetih termina.</td>
            </tr>
          )}
        </tbody>
      </Table>
    </Container>
  );
}
