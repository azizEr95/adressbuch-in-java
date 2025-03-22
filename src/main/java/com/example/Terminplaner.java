package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Terminplaner {

    private HashMap<LocalDate, Termin> terminplaner;
    private Kontakt besitzer;

    public Terminplaner(Kontakt kontakt) {
        if(kontakt == null) {
            throw new IllegalArgumentException();
        }
        this.besitzer = kontakt;
        terminplaner = new HashMap<>();
        setTestDaten();
    }

    public void setBesitzer(Kontakt kontakt) {
        if(kontakt == null) {
            throw new IllegalArgumentException();
        }
        this.besitzer = kontakt;
    }

    public Kontakt getBesitzer() {
        return this.besitzer;
    }

    public void addTermin(LocalDate datum, Termin termin) {
        if(datum == null) {
            throw new IllegalArgumentException();
        }
        Termin neuerTermin = termin != null ? termin : new Termin("", datum);
        terminplaner.put(datum, neuerTermin);
    }

    public boolean deleteTermin(LocalDate datum) {
        Termin zuLoeschen = terminplaner.get(datum);
        return terminplaner.remove(datum, zuLoeschen);
    }

    public Termin getTermin(LocalDate datum) {
        return terminplaner.getOrDefault(datum, new Termin("", datum));
    }

    public Termin[] getAllTermine() {
        ArrayList<Termin> termine = new ArrayList<>();
        for(Termin t : terminplaner.values()) {
            termine.add(t);
        }
        return termine.toArray(new Termin[0]);
    }

    private void setTestDaten() {
        
    }
}
