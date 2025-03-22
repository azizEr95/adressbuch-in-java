package com.example;

import java.util.Objects;

public class Adresse implements Comparable<Adresse> {
    private String strasse;
    private String hausnummer;
    private String plz;
    private String stadt;

    public Adresse(String strasse, String hausnummer, String plz, String stadt) {
        this.strasse = strasse == null ? "" : strasse.trim().toLowerCase();
        this.hausnummer = hausnummer == null ? "" : hausnummer.trim().toLowerCase();
        this.plz = plz == null ? "" : plz.trim().toLowerCase();
        this.stadt = stadt == null ? "" :stadt.trim().toLowerCase();
    }

    public Adresse() {
        this.strasse = "";
        this.hausnummer = "";
        this.plz = "";
        this.stadt = "";
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse == null ? "" : strasse.trim().toLowerCase();
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer == null ? "" : hausnummer.trim().toLowerCase();
    }

    public void setPLZ(String plz) {
        this.plz = plz == null ? "" : plz.trim().toLowerCase();
    }

    public void setStadt(String stadt) {
        this.stadt = stadt == null ? "" : stadt.trim().toLowerCase();
    }

    public String getStrasse() {
        return this.strasse;
    }

    public String getHausnummer() {
        return this.hausnummer;
    }

    public String getPLZ() {
        return this.plz;
    }

    public String getStadt() {
        return this.stadt;
    }

    @Override
    public String toString() {
        return this.strasse + " " + this.hausnummer + ", " + this.plz + " " + this.stadt;
    }

    @Override
    public int compareTo(Adresse jeneAdresse) {
        int vergleich = this.strasse.compareTo(jeneAdresse.getStrasse());
        if (vergleich == 0) {
            vergleich = this.hausnummer.compareTo(jeneAdresse.getHausnummer());
        }
        if (vergleich == 0) {
            vergleich = this.plz.compareTo(jeneAdresse.getPLZ());
        }
        if (vergleich == 0) {
            vergleich = this.stadt.compareTo(jeneAdresse.getStadt());
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
        if(!(obj instanceof Adresse)) {
            return false;
        }
        Adresse adr = (Adresse) obj;
        return this.compareTo(adr) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.strasse, this.hausnummer, this.plz, this.stadt);
    }
}
