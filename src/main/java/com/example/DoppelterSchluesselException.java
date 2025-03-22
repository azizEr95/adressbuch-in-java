package com.example;

public class DoppelterSchluesselException extends Exception {

    private String schluessel;

    public DoppelterSchluesselException(String schluessel) {
        super("Zum Schluessel " + schluessel + " gibt es schon einen im Adressbuch.");
        this.schluessel = schluessel;

    }
    
    public String getSchluessel() {
        return schluessel;
    }
}