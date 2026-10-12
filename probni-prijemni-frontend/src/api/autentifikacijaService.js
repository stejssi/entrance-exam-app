//pozivi ka bekendu
import api from "./axiosConfig";

export function registrujKandidata(podaci) {
  return api.post("/api/auth/registracija", podaci);
}

export function login(podaci) {
  return api.post("/api/auth/login", podaci);
}
export function potvrdiRegistraciju(podaci) {
  return api.post("/api/auth/potvrdi-registraciju", podaci);
}