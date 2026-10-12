import api from "./axiosConfig";

export function getTermini() {
  return api.get("/api/termini");
}

export function kreirajTermin(podaci) {
  return api.post("/api/admin/termini", podaci);
}

export function azurirajTermin(id, podaci) {
  return api.put(`/api/admin/termini/${id}`, podaci);
}

export function obrisiTermin(id) {
  return api.delete(`/api/admin/termini/${id}`);
}