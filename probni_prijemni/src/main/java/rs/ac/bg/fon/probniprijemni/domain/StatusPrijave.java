package rs.ac.bg.fon.probniprijemni.domain;

/**
 *
 * @author Stasa
 */
public enum StatusPrijave {
    KREIRANA,  //prijava je napravljena, uplata jos nije evidentirana (moze se otkazati)
    PRIJAVLJEN,  //administrator je evidentirao uplatu, kandidat je na spisku
    OTKAZANA     //kandidat je otkazao prijavu pre nego sto je uplata evidentirana
}
