package com.example;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Kontakt implements Comparable<Kontakt> {
    private String name;
    private String nummer;
    private String email;
    private Adresse adresse;
    private Terminplaner planer;


    public Kontakt(String name, String nummer, String email, Adresse adresse) {

        this.setName(name);
        this.setNummer(nummer);
        this.setEmail(email);
        this.setAdresse(adresse);

        this.planer = new Terminplaner(this);
    }

    public void setName(String name) {
        if ((name == null || name.isBlank())) {
            throw new IllegalArgumentException("Name muss angegeben werden.");
        }
        this.name = name.trim().toLowerCase();
    }

    public void setNummer(String nummer) {
        this.nummer = (nummer == null) ? "" : nummer.trim().toLowerCase();
    }

    public void setEmail(String email) {
        this.email = (email == null) ? "" : email.trim().toLowerCase();
    }

    public void setAdresse(Adresse adresse) {
        if(adresse == null) {
            this.adresse = new Adresse("", "", "", "");
            return;
        }
        this.adresse = adresse;
    }

    public void setTermin(LocalDate datum, Termin termin) {
        planer.addTermin(datum, termin);
    }

    public String getName() {
        return this.name;
    }

    public String getNummer() {
        return this.nummer;
    } 

    public String getEmail() {
        return this.email;
    }

    public Adresse getAdresse() {
        return this.adresse;
    } 

    public Termin getTermin(LocalDate datum) {
        return planer.getTermin(datum);
    }

    public Terminplaner getTerminplaner() {
        return this.planer;
    }

    public Termin[] getAlleTermine() {
        return planer.getAllTermine();
    }

    @Override
    public String toString() {
        return "Kontakt: " + name + "\n" 
        + "Tel:"  + nummer + "\n" 
        + "E-Mail: " + email + "\n" 
        + "Adresse: " + adresse.toString() + "\n"
        + "Termine: " + (planer.getAllTermine().length > 0 ? Arrays.toString(getAlleTermine()) : "Kein Termin");
    }

    @Override
    public int compareTo(Kontakt jeneKontakt) {
        int vergleich = name.compareTo(jeneKontakt.getName());

        if (vergleich == 0) {
            vergleich = nummer.compareTo(jeneKontakt.getNummer());
        }
        if (vergleich == 0) {
            vergleich = email.compareTo(jeneKontakt.getEmail());
        }
        return vergleich;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        Kontakt k = (Kontakt) obj;
        return name.equals(k.name) &&
        nummer.equals(k.nummer) &&
        email.equals(k.email) &&
        adresse.equals(k.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nummer, email, adresse);
    }
}
