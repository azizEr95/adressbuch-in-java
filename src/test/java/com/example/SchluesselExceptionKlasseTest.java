package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class SchluesselExceptionKlasseTest {

    @SuppressWarnings("static-access")
    @Test
    public void keinPassenderSchluesselTest() {
        KeinPassenderKontaktException keinPassenderSchluessel = new KeinPassenderKontaktException("schuessel");
        assertEquals(keinPassenderSchluessel.getSchluessel(), "schuessel");
        assertEquals(keinPassenderSchluessel.getMessage(), "Unter dem Schluessel schuessel konnte kein Kontakt gefunden werden.");
    }

    @SuppressWarnings("static-access")
    @Test
    public void DoppelterSchluesselException() {
        DoppelterSchluesselException doppelterSchluessel = new DoppelterSchluesselException("schuessel");
        assertEquals(doppelterSchluessel.getSchluessel(), "schuessel");
        assertEquals(doppelterSchluessel.getMessage(), "Zum Schluessel schuessel gibt es schon einen im Adressbuch.");
    }
}
