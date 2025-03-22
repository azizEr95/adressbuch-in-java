package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.*;

public class TerminplanerTest {
    private Kontakt testKontakt;
    private Terminplaner terminplaner;
    private LocalDate testDatum1;
    private LocalDate testDatum2;
    private Termin termin1;
    private Termin termin2;

    @BeforeEach
    public void setUp() {
        // Setup Testdaten
        testKontakt = new Kontakt("Max Mustermann", "12345", "max@example.com", new Adresse("Musterstraße", "1", "12345", "Musterstadt"));
        terminplaner = new Terminplaner(testKontakt);
        testDatum1 = LocalDate.of(2025, 3, 10);
        testDatum2 = LocalDate.of(2025, 3, 15);
        termin1 = new Termin("Arzttermin", testDatum1);
        termin2 = new Termin("Meeting", testDatum2);
    }

    @Test
    public void testTerminplanerInitialisierung() {
        assertNotNull(terminplaner);
        assertEquals(testKontakt, terminplaner.getBesitzer());
    }

    @Test
    public void testSetBesitzer() {
        Kontakt neuerBesitzer = new Kontakt("Erika Musterfrau", "67890", "erika@example.com", new Adresse("Beispielstraße", "2", "67890", "Beispielstadt"));
        terminplaner.setBesitzer(neuerBesitzer);
        assertEquals(neuerBesitzer, terminplaner.getBesitzer());
    }

    @Test
    public void testAddTermin() {
        terminplaner.addTermin(testDatum1, termin1);
        assertEquals(termin1, terminplaner.getTermin(testDatum1));
    }

    @Test
    public void testAddTerminNullTermin() {
        terminplaner.addTermin(testDatum2, null);
        Termin defaultTermin = new Termin("", testDatum2);
        assertEquals(defaultTermin.getBeschreibung(), terminplaner.getTermin(testDatum2).getBeschreibung());
    }

    @Test
    public void testDeleteTermin() {
        terminplaner.addTermin(testDatum1, termin1);
        assertTrue(terminplaner.deleteTermin(testDatum1));
        assertNotNull(terminplaner.getTermin(testDatum1));
    }

    @Test
    public void testDeleteTerminNotExist() {
        assertFalse(terminplaner.deleteTermin(testDatum2)); // Termin existiert nicht
    }

    @Test
    public void testGetTermin() {
        terminplaner.addTermin(testDatum1, termin1);
        Termin result = terminplaner.getTermin(testDatum1);
        assertEquals(termin1, result);
    }

    @Test
    public void testGetTerminNoTermin() {
        Termin result = terminplaner.getTermin(testDatum2);
        Termin defaultTermin = new Termin("", testDatum2);
        assertEquals(defaultTermin.getBeschreibung(), result.getBeschreibung()); // Standardwert
    }

    @Test
    public void testGetAllTermine() {
        terminplaner.addTermin(testDatum1, termin1);
        terminplaner.addTermin(testDatum2, termin2);

        Termin[] termine = terminplaner.getAllTermine();
        assertEquals(2, termine.length);
        assertEquals(termin1, termine[1]);
        assertEquals(termin2, termine[0]);
    }

    @Test
    public void testConstructorWithNullKontakt() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Terminplaner(null);
        });
    }

    @Test
    public void testSetBesitzerWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            terminplaner.setBesitzer(null);
        });
    }

    @Test
    public void testAddTerminWithNullDatum() {
        assertThrows(IllegalArgumentException.class, () -> {
            terminplaner.addTermin(null, termin1);
        });
    }
}
