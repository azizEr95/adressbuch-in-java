package com.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AdressbuchTest {
    private Adressbuch adressbuch;
    private Adressbuch adressbuchOhneTestdaten;
    private Kontakt testKontakt;
    private Adresse adresse1;


    @BeforeEach
    public void setUp() {
        adressbuch = new Adressbuch(true);
        adressbuchOhneTestdaten = new Adressbuch(false);
        adresse1 = new Adresse("Reclamweg", "2", "13593", "Berlin");
        testKontakt = new Kontakt("Aziz Erol", "12345", "aziz@gmx.de", adresse1);
    }

    @Test
    public void adressbuchExistiert() {
        assertNotNull(adressbuch);
        assertNotNull(adressbuchOhneTestdaten);
    }

    @Test
    public void testSchluesselBekannt() {
        assertThrows(IllegalArgumentException.class, () -> {
            adressbuch.schluesselBekannt(null);
        });
        
        assertTrue(adressbuch.schluesselBekannt("mike"));
    }

    @Test
    public void testGetKontakt() {
        Kontakt expected = new Kontakt("mike", "08459 100000", "mike-tyson@gmx.de", new Adresse("Reclamweg", "2", "13593", "Berlin"));
        try {
            assertNotNull(adressbuch.getKontakt("mike"));
            assertEquals(expected, adressbuch.getKontakt("mike"));
        } catch(KeinPassenderKontaktException exception) {
            
        }
    }

    @Test
    public void testGetKontaktExceptions() {
        assertThrows(IllegalArgumentException.class, () -> {
            adressbuch.getKontakt(null);
        });

        assertThrows(KeinPassenderKontaktException.class, () -> {
            adressbuch.getKontakt("mausel");
        });
    }

    @Test
    public void testAddKontaktExceptions() {
        Kontakt test = new Kontakt("mike", "08459 100000", "mike-tyson@gmx.de", new Adresse("Reclamweg", "2", "13593", "Berlin"));

        assertThrows(IllegalArgumentException.class, () -> {
            adressbuch.addKontakt(null);
        });
        assertThrows(DoppelterSchluesselException.class, () -> {
            adressbuch.addKontakt(test);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            adressbuch.addKontakt(new Kontakt("", "", "", new Adresse()));
        });
    }

    @Test
    public void testAddKontaktWithCount() throws DoppelterSchluesselException, KeinPassenderKontaktException {
        assertEquals(5, adressbuch.getAnzahlKontakte());
        adressbuch.addKontakt(testKontakt);
        assertEquals(testKontakt, adressbuch.getKontakt("aziz erol"));
        assertEquals(6, adressbuch.getAnzahlKontakte());
    }

    @Test
    public void testDeleteKontaktExceptions() {

        assertThrows(IllegalArgumentException.class, () -> {
            adressbuch.deleteKontakt(null);
        });
        assertThrows(KeinPassenderKontaktException.class, () -> {
            adressbuch.deleteKontakt("mausel");
        });
        assertThrows(KeinPassenderKontaktException.class, () -> {
            adressbuch.deleteKontakt("");
        });
    }

    @Test
    public void testDeleteKontaktWithCount() throws KeinPassenderKontaktException{
        Kontakt expected = new Kontakt("mike", "08459 100000", "mike-tyson@gmx.de", new Adresse("Reclamweg", "2", "13593", "Berlin"));
        assertEquals(5, adressbuch.getAnzahlKontakte());
        Kontakt actual = adressbuch.deleteKontakt("mike");
        assertEquals(expected, actual);
        assertEquals(4, adressbuch.getAnzahlKontakte());
    }

    @Test
    public void testUpdateKontaktException() {
        Kontakt toUpdate = new Kontakt("mike", "08459 11111", "mike-tyson@gmx.de", new Adresse("Reclamweg", "2", "13593", "Berlin"));
        assertThrows(KeinPassenderKontaktException.class, () -> {
            adressbuch.updateKontakt("", toUpdate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            adressbuch.updateKontakt(null, toUpdate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            adressbuch.updateKontakt("mike", null);
        });
        assertThrows(KeinPassenderKontaktException.class, () -> {
            adressbuch.updateKontakt("pluto", toUpdate);
        });
    }

    @Test
    public void testUpdate() throws DoppelterSchluesselException, KeinPassenderKontaktException{
        Kontakt toUpdate = new Kontakt("mike", "08459 11111", "mike-tyson@gmx.de", new Adresse("Reclamweg", "2", "13593", "Berlin"));
        adressbuch.updateKontakt("mike", toUpdate);
        Kontakt expected = adressbuch.getKontakt("mike");
        assertEquals(expected, toUpdate);
    }

    @Test
    public void searchKontakteExceptions() {
        assertThrows(IllegalArgumentException.class, () -> {
            adressbuch.searchKontakte(null);
        });
        Kontakt[] ergebnis = adressbuch.searchKontakte(""); 
        assertNotNull(ergebnis);
    }

    @Test
    public void searchKontakte() {
        Kontakt[] result = adressbuch.searchKontakte("Nicht Vorhanden");
        assertNotNull(result);
        assertEquals(0, result.length);

        result = adressbuch.searchKontakte("mike");
        assertNotNull(result);
        assertEquals(1, result.length);

        result = adressbuch.searchKontakte("08459 100000");
        assertNotNull(result);
        assertEquals(1, result.length);

        result = adressbuch.searchKontakte("mike-tyson@gmx.de");
        assertNotNull(result);
        assertEquals(1, result.length);

        result = adressbuch.searchKontakte("reclamweg");
        assertNotNull(result);
        assertEquals(1, result.length);
    }

    @Test
    public void testGetAlleKontakte() {
        Kontakt[] actual = adressbuch.getAlleKontakte();
        assertNotNull(actual);
        int expectedSize = adressbuch.getAnzahlKontakte();
        assertEquals(expectedSize, actual.length);
        for (Kontakt kontakt : actual) {
            assertTrue(adressbuch.schluesselBekannt(kontakt.getName()));
        }
    }

    @Test
    public void testToString() {
        assertNotNull(adressbuch.toString());
        assertTrue(adressbuch.toString().contains("mike"));
        assertTrue(adressbuch.toString().contains("08459 100000"));
        assertTrue(adressbuch.toString().contains("mike-tyson@gmx.de"));
        assertTrue(adressbuch.toString().contains(adresse1.toString()));
        assertTrue(adressbuch.toString().contains("Kontakt: "));
    }

    @Test
    public void setTestDaten() {
        Kontakt toAdd = new Kontakt("mike", "08459 11111", "mike-tyson@gmx.de", new Adresse("Reclamweg", "2", "13593", "Berlin"));
        try {
            adressbuch.addKontakt(toAdd);
        } catch (DoppelterSchluesselException e) {
            assertThrows(DoppelterSchluesselException.class, () -> {
                adressbuch.addKontakt(toAdd);
            });
        }
    }
}
