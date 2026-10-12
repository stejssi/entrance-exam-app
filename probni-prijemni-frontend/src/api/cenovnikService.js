import api from "./axiosConfig";

export function getCenovnik() {
  return api.get("/api/cenovnik");
}

export function definisiCenu(podaci) {
  return api.post("/api/admin/cenovnik", podaci);
}