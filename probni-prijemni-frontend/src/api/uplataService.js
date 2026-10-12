import api from "./axiosConfig";

export function evidentirajUplatu(administratorId, prijavaId) {
  return api.post(`/api/admin/${administratorId}/prijave/${prijavaId}/uplata`);
}