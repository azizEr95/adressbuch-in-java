package com.example;

import java.time.*;

public class Termin implements Comparable<Termin> {
    private String beschreibung;
    private LocalDate datum;
    private Kontakt besitzer;
    
    public Termin(String beschreibung, LocalDate datum) {
        this.setBeschreibung(beschreibung);
        this.setDatum(datum);
        this.setBesitzer(null);
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung != null ? beschreibung : "";
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum != null ? datum : LocalDate.now();
    }

    public void setBesitzer(Kontakt besitzer) {
        this.besitzer = besitzer;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Kontakt getBesitzer() throws IllegalStateException {
        if(besitzer == null) {
            throw new IllegalStateException();
        }

        return besitzer;
    }

    @Override
    public int compareTo(Termin jenerTermin) {
        int vergleich = this.datum.compareTo(jenerTermin.getDatum());
        if (vergleich == 0) {
            vergleich = this.beschreibung.compareTo(jenerTermin.getBeschreibung());
        }
        if (vergleich == 0) {
            vergleich = this.besitzer.compareTo(jenerTermin.getBesitzer());
        }
        return vergleich;
    }

    @Override
    public boolean equals(Object jenes) {
        if (this == jenes) {
            return true;
        }
        if (jenes == null) {
            return false;
        }
        if(!(jenes instanceof Termin)) {
            return false;
        }
        Termin jenerTermin = (Termin) jenes;
        return this.compareTo(jenerTermin) == 0;
    }

    public String toString() {
        String termin = "";
        termin = this.besitzer + " - " + this.datum + " : " + this.beschreibung;
        return termin;
    }
}
