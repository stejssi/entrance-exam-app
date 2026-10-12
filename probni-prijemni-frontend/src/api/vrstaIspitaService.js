import api from "./axiosConfig";

export function getVrsteIspita() {
  return api.get("/api/vrsta-ispita");
}