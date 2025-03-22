package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;

public class KontaktTest {
    private Adresse adresse1;
    private Adresse adresse2;
    private Kontakt testKontakt;
    private Kontakt testKontaktEqualToOne;
    private Kontakt testKontaktNotEqual;
    private LocalDate datumMitTermin;
    private LocalDate datumOhneTermin;

    @BeforeEach
    public void setUp() {
        adresse1 = new Adresse("Reclamweg", "2", "13593", "Berlin");
        adresse2 = new Adresse("Kaiserdamm", "38", "14057", "Berlin");

        testKontakt = new Kontakt("Aziz Erol", "12345", "aziz@gmx.de", adresse1);
        testKontaktEqualToOne = new Kontakt("Aziz Erol", "12345", "aziz@gmx.de", adresse1);
        testKontaktNotEqual = new Kontakt("Mike Tyson", "123456", "mike@gmx.de", adresse2);

        datumMitTermin = LocalDate.of(2025, 3, 10);
        datumOhneTermin = LocalDate.of(2025, 3, 11);

        Termin termin = new Termin("Arzttermin", datumMitTermin);
        testKontakt.getTerminplaner().addTermin(datumMitTermin, termin);
    }

    @Test
    public void createKontakt() {
        assertEquals("aziz erol", testKontakt.getName());
        assertEquals("12345", testKontakt.getNummer());
        assertEquals("aziz@gmx.de", testKontakt.getEmail());
        assertEquals(adresse1, testKontakt.getAdresse());
        Terminplaner planer = testKontakt.getTerminplaner();
        assertEquals(planer, testKontakt.getTerminplaner());

        assertThrows(IllegalArgumentException.class, () -> {
            new Kontakt("", "", "", adresse1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Kontakt(null, "", "", adresse1);
        });

        testKontakt.setNummer(null);
        assertEquals("", testKontakt.getNummer());
    }

    @Test
    public void kontaktSetMethods() {
        testKontakt.setName("Thorsten Knobel");
        testKontakt.setNummer("123456");
        testKontakt.setEmail("thorsten@gmx.de");
        testKontakt.setAdresse(adresse2);

        assertEquals("thorsten knobel", testKontakt.getName());
        assertEquals("123456", testKontakt.getNummer());
        assertEquals("thorsten@gmx.de", testKontakt.getEmail());
        assertEquals(adresse2, testKontakt.getAdresse());
    }

    @Test
    public void setKontaktMitNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            testKontakt.setName(null);
        });
        testKontakt.setNummer(null);
        testKontakt.setEmail(null);
        testKontakt.setAdresse(null);

        assertEquals("", testKontakt.getNummer());
        assertEquals("", testKontakt.getEmail());
        assertEquals(new Adresse(), testKontakt.getAdresse());
    }

    @Test
    public void setKontaktBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            testKontakt.setName("");
        });
        testKontakt.setNummer("");
        testKontakt.setEmail("");
        testKontakt.setAdresse(new Adresse());

        assertEquals("", testKontakt.getNummer());
        assertEquals("", testKontakt.getEmail());
        assertEquals(new Adresse(), testKontakt.getAdresse());
    }

    @Test
    public void testGetTerminMitVorhandenemTermin() {
        Termin termin = testKontakt.getTermin(datumMitTermin);

        assertEquals("Arzttermin", termin.getBeschreibung());
        assertEquals(datumMitTermin, termin.getDatum());
    }

    @Test
    public void testGetTerminOhneVorhandenenTermin() {
        Termin termin = testKontakt.getTermin(datumOhneTermin);

        assertEquals("", termin.getBeschreibung());
        assertEquals(datumOhneTermin, termin.getDatum());

        Termin termin3 = testKontakt.getTermin(datumOhneTermin);

        assertEquals("", termin.getBeschreibung());
        assertEquals(datumOhneTermin, termin3.getDatum());
    }

    @Test
public void testGetTerminMitKeinemVorhandenenTermin() {

    LocalDate datumOhneTermin = LocalDate.of(2025, 3, 13);

    Termin termin = testKontakt.getTermin(datumOhneTermin);

    assertEquals("", termin.getBeschreibung());
    assertEquals(datumOhneTermin, termin.getDatum());
}

    @Test
    public void kontaktToStringMethod() {
        assertEquals(
                "Kontakt: aziz erol\nTel:12345\nE-Mail: aziz@gmx.de\nAdresse: reclamweg 2, 13593 berlin\nTermine: [null - 2025-03-10 : Arzttermin]",
                testKontakt.toString());
    }

    @Test
    public void kontaktHashCode() {
        assertEquals(testKontakt.hashCode(), testKontaktEqualToOne.hashCode());
        assertNotEquals(testKontakt.hashCode(), testKontaktNotEqual.hashCode());
        assertNotEquals(testKontaktEqualToOne.hashCode(), testKontaktNotEqual.hashCode());
    }

    @Test
    public void kontaktEqualsMethod() {
        assertTrue(testKontakt.equals(testKontaktEqualToOne));
        assertFalse(testKontakt.equals(testKontaktNotEqual));
        assertFalse(testKontaktEqualToOne.equals(testKontaktNotEqual));
        
        assertTrue(testKontakt.equals(testKontakt));
        assertFalse(testKontakt.equals(null));
    }

    @Test
    public void kontaktEqualsDifferentAddress() {
        Kontakt kontaktMitAndererAdresse = new Kontakt("Aziz Erol", "12345", "aziz@gmx.de", adresse2);
        assertFalse(testKontakt.equals(kontaktMitAndererAdresse));
    }

    @Test
    public void kontaktCompareToIdentisch() {
        Kontakt identischerKontakt = new Kontakt("Aziz Erol", "12345", "aziz@gmx.de", adresse1);
        assertEquals(0, testKontakt.compareTo(identischerKontakt));
    }

    @Test
    public void kontaktCompareToDifferentName() {
        Kontakt kontaktMitAnderemNamen = new Kontakt("Ben Becker", "12345", "aziz@gmx.de", adresse1);
        assertTrue(testKontakt.compareTo(kontaktMitAnderemNamen) < 0);

        Kontakt kontaktMitFrueheremNamen = new Kontakt("Aaron Adams", "12345", "aziz@gmx.de", adresse1);
        assertTrue(testKontakt.compareTo(kontaktMitFrueheremNamen) > 0);
    }

    @Test
    public void kontaktCompareToSameNameDifferentNummer() {
        Kontakt kontaktMitAndererNummer = new Kontakt("Aziz Erol", "54321", "aziz@gmx.de", adresse1);
        assertTrue(testKontakt.compareTo(kontaktMitAndererNummer) < 0);

        Kontakt kontaktMitFruehererNummer = new Kontakt("Aziz Erol", "01234", "aziz@gmx.de", adresse1);
        assertTrue(testKontakt.compareTo(kontaktMitFruehererNummer) > 0);
    }

    @Test
    public void kontaktCompareToSameNameNummerDifferentEmail() {
        Kontakt kontaktMitAndererEmail = new Kontakt("Aziz Erol", "12345", "zzz@gmx.de", adresse1);
        assertTrue(testKontakt.compareTo(kontaktMitAndererEmail) < 0);

        Kontakt kontaktMitFruehererEmail = new Kontakt("Aziz Erol", "12345", "aaa@gmx.de", adresse1);
        assertTrue(testKontakt.compareTo(kontaktMitFruehererEmail) > 0);
    }

    @Test
    public void kontaktCompareToWithNull() {
        assertThrows(NullPointerException.class, () -> {
            testKontakt.compareTo(null);
        });
    }

    @Test
    public void kontaktEqualsDifferentNummer() {
        Kontakt kontaktMitAndererNummer = new Kontakt("Aziz Erol", "99999", "aziz@gmx.de", adresse1);
        assertFalse(testKontakt.equals(kontaktMitAndererNummer));
    }

    @Test
    public void kontaktEqualsDifferentEmail() {
        Kontakt kontaktMitAndererEmail = new Kontakt("Aziz Erol", "12345", "andere@gmx.de", adresse1);
        assertFalse(testKontakt.equals(kontaktMitAndererEmail));
    }

    @Test
    public void kontaktEqualsDifferentNummerUndEmail() {
        Kontakt kontaktMitAndererNummerUndEmail = new Kontakt("Aziz Erol", "99999", "andere@gmx.de", adresse1);
        assertFalse(testKontakt.equals(kontaktMitAndererNummerUndEmail));
    }

    @Test
    public void kontaktEqualsNull() {
        assertFalse(testKontakt.equals(null));
    }

}
