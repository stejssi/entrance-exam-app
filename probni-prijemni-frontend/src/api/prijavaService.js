import api from "./axiosConfig";

export function kreirajPrijavu(kandidatId, terminIds) {
  return api.post(`/api/kandidati/${kandidatId}/prijave`, { terminIds });
}

export function getPrijaveKandidata(kandidatId) {
  return api.get(`/api/kandidati/${kandidatId}/prijave`);
}

export function otkaziPrijavu(kandidatId, prijavaId) {
  return api.delete(`/api/kandidati/${kandidatId}/prijave/${prijavaId}`);
}

export function pretraziPrijave(parametri) {
  return api.get("/api/admin/prijave", { params: parametri });
}

export function preuzmiSpisakKandidataPdf(terminId) {
  return api.get(`/api/admin/termini/${terminId}/kandidati/pdf`, { responseType: "blob" });
}
//Blob = "Binary Large Object" tako cuvamo pdf fajl