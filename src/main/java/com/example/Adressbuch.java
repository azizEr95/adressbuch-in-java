package com.example;

import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.*;

public class Adressbuch {

    private HashMap<String, Kontakt> buch;
    private int anzahlKontakte;

    public Adressbuch(boolean mitTestdaten) {
        this.buch = new HashMap<>();
        this.anzahlKontakte = 0;
        if (mitTestdaten) {
            setTestDaten();
        }
    }

    public boolean schluesselBekannt(String schluessel)
            throws IllegalArgumentException {
        if (schluessel == null) {
            throw new IllegalArgumentException("Schluessel darf nich null sein");
        }
        return buch.containsKey(schluessel);
    }

    public Kontakt getKontakt(String schluessel) throws KeinPassenderKontaktException {
        if (!schluesselBekannt(schluessel)) {
            throw new KeinPassenderKontaktException(schluessel);
        }
        return buch.get(schluessel);
    }

    public int getAnzahlKontakte() {
        return this.anzahlKontakte;
    }

    public Kontakt[] getAlleKontakte() {
        ArrayList<Kontakt> liste = new ArrayList<>(buch.values());
        liste.sort((k1, k2) -> k1.compareTo(k2));
        return liste.toArray(new Kontakt[0]);
    }

    public void addKontakt(Kontakt kontakt) throws DoppelterSchluesselException {
        if (kontakt == null) {
            throw new IllegalArgumentException("Kontakt darf nicht null sein!");
        }
        if (schluesselBekannt(kontakt.getName())) {
            throw new DoppelterSchluesselException(kontakt.getName());
        }
        buch.put(kontakt.getName(), kontakt);
        anzahlKontakte++;
    }

    public Kontakt deleteKontakt(String schluessel) throws KeinPassenderKontaktException {
        if (!schluesselBekannt(schluessel)) {
            throw new KeinPassenderKontaktException(schluessel);
        }
        anzahlKontakte--;
        return buch.remove(schluessel);
    }

    public void updateKontakt(String alterSchluessel, Kontakt neueDaten)
            throws KeinPassenderKontaktException {
        if (!schluesselBekannt(alterSchluessel)) {
            throw new KeinPassenderKontaktException(alterSchluessel);
        }
        if (neueDaten == null) {
            throw new IllegalArgumentException();
        }
        buch.put(alterSchluessel, neueDaten);
    }

    public Kontakt[] searchKontakte(String suche) {
        if (suche == null) {
            throw new IllegalArgumentException();
        }
        if (suche.isBlank()) {
            return this.getAlleKontakte();
        }
        String s = suche.trim().toLowerCase();
        return Stream.of(this.getAlleKontakte())
                .filter(k -> k.getName().contains(s) ||
                        k.getNummer().contains(s) ||
                        k.getEmail().contains(s) ||
                        k.getAdresse().toString().contains(s))
                .toArray(size -> new Kontakt[size]);
    }

    @Override
    public String toString() {
        return buch.values().stream()
                .map(k -> "Kontakt: " + k.toString())
                .collect(Collectors.joining("\n"));
    }

    private void setTestDaten() {
        Adresse[] testAdressen = {
                new Adresse("Reclamweg", "2", "13593", "Berlin"),
                new Adresse("Kaiserdamm", "38", "14057", "Berlin"),
                new Adresse("Knobelsdorffstrasse", "105", "14050", "Berlin"),
                new Adresse("Klosterstrasse", "3", "10179", "Berlin"),
                new Adresse("Friedrichstrasse", "200", "10117", "Berlin")
        };
        Kontakt[] testdaten = {
                new Kontakt("mike", "08459 100000", "mike-tyson@gmx.de", testAdressen[0]),
                new Kontakt("michael", "08459 200000", "michael@gmx.de", testAdressen[1]),
                new Kontakt("john", "08459 300000", "john@gmx.de", testAdressen[1]),
                new Kontakt("heike", "08459 400000", "heike@gmx.de", testAdressen[3]),
                new Kontakt("emma", "08459 500000", "emma@gmx.de", testAdressen[4])
        };
        Stream.of(testdaten)
                .peek(k -> {
                    try {
                        addKontakt(k);
                    } catch (DoppelterSchluesselException e) {
                        System.out.println(e.getMessage());
                    }
                })
                .filter(k -> k.getName().equals("mike") || k.getName().equals("michael"))
                .forEach(k -> {
                    Termin terminMike = new Termin("Arzttermin", LocalDate.of(2025, 3, 10));
                    k.setTermin(terminMike.getDatum(), terminMike);

                    Termin terminMichael = new Termin("Meeting", LocalDate.of(2025, 3, 12));
                    k.setTermin(terminMichael.getDatum(), terminMichael);
                });
    }
}
