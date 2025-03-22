package com.example;

import static org.junit.jupiter.api.Assertions.*;
import java.time.*;
import org.junit.jupiter.api.*;

public class TerminTest {
    private Kontakt testKontakt;
    private Termin testTermin;
    private Termin testTerminNull;
    private Termin testTerminBlank;
    private LocalDate datum;

    @BeforeEach
    public void setUp() {
        datum = LocalDate.of(2025, 1, 1);
        testTermin = new Termin("Termin um 6", datum);
        testTerminNull = new Termin("Termin um 6", null);
        testTerminBlank = new Termin("", null);
        testKontakt = new Kontakt("aziz", "1234", "aziz@gmx.de", new Adresse());
    }

    @Test
    public void erstelleTermin() {
        assertNotNull(testTermin);
        assertNotNull(testTerminNull);
        assertNotNull(testTerminBlank);

        assertEquals("Termin um 6", testTermin.getBeschreibung());
        assertEquals(datum, testTermin.getDatum());
        assertThrows(IllegalStateException.class, () -> {
            testTermin.getBesitzer();
        });
    }

    @Test
    public void setterMethoden() {
        testTermin.setBeschreibung("Termin um 7");
        assertEquals("Termin um 7", testTermin.getBeschreibung());

        testTermin.setBeschreibung(null);
        assertEquals("", testTermin.getBeschreibung());

        testTermin.setDatum(LocalDate.of(2025, 2, 1));
        assertEquals(LocalDate.of(2025, 2, 1), testTermin.getDatum());

        testTermin.setBesitzer(testKontakt);
        assertEquals(testKontakt, testTermin.getBesitzer());
    }

    @Test
    public void compareToTest() {
        testTermin.setBesitzer(testKontakt);
        Termin newTermin = testTermin;
        Termin newnewTermin = new Termin("Termin um 5", LocalDate.of(2025, 3, 1));
        assertEquals(0, testTermin.compareTo(newTermin));
        assertEquals(-2, testTermin.compareTo(newnewTermin));
    }

    @Test
    public void testEquals() {
        Termin termin1 = new Termin("Arzttermin", LocalDate.of(2025, 3, 10));
        termin1.setBesitzer(testKontakt);
        Termin termin2 = new Termin("Arzttermin", LocalDate.of(2025, 3, 10));
        termin2.setBesitzer(testKontakt);
        Termin termin3 = new Termin("Meeting", LocalDate.of(2025, 3, 12));
        termin3.setBesitzer(testKontakt);
        Termin termin4 = null;

        assertTrue(termin1.equals(termin1));
        assertTrue(termin1.equals(termin2));
        assertFalse(termin1.equals(termin3));
        assertFalse(termin1.equals(termin4));
        assertFalse(termin1.equals(""));
    }
}
