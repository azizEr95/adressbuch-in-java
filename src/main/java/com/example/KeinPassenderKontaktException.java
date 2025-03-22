package com.example;

public class KeinPassenderKontaktException extends Exception{

    private String schluessel; 
    public KeinPassenderKontaktException(String schluessel) {
        super("Unter dem Schluessel " + schluessel + " konnte kein Kontakt gefunden werden.");
        this.schluessel = schluessel;
    }

    public String getSchluessel() {
        return schluessel;
    }

}

