//pamćenje ko je ulogovan
import { createContext, useContext, useState } from "react";

const AuthContext = createContext(null); //pravi "kutiju" u koju se stavljaju podaci

export function AuthProvider({ children }) {
  const [korisnik, setKorisnik] = useState(() => {
    const sacuvano = localStorage.getItem("korisnik"); //localStorage (mesto u browseru koje pamti podatke i posle refresh-a/zatvaranja taba)
    return sacuvano ? JSON.parse(sacuvano) : null;  //koristimo to ako ima
  });

  function prijaviKorisnika(podaciKorisnika) {
    setKorisnik(podaciKorisnika);
    localStorage.setItem("korisnik", JSON.stringify(podaciKorisnika));
  }

  function odjaviKorisnika() {
    setKorisnik(null);
    localStorage.removeItem("korisnik");
  }

  return (                      //ono što delimo "napolje" svakoj komponenti unutar
    <AuthContext.Provider value={{ korisnik, prijaviKorisnika, odjaviKorisnika }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}