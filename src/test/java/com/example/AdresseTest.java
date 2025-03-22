package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class AdresseTest {
    Adresse test;
    Adresse test2;
    Adresse test3;
    Adresse testNull;
    Adresse adresseLeer;

    @BeforeEach
    public void setUp() {
        test = new Adresse("Reclamweg", "2", "13593", "Berlin");
        test2 = new Adresse("Reclamweg", "2", "13593", "Berlin");
        test3 = new Adresse("Obstallee", "3", "13594", "Hamburg");
        testNull = new Adresse(null, null, null, null);
        adresseLeer = new Adresse();

    }

    @Test
    public void adressenErstellung() {
        assertEquals("reclamweg", test.getStrasse());
        assertEquals("2", test.getHausnummer());
        assertEquals("13593", test.getPLZ());
        assertEquals("berlin", test.getStadt());
    }

    public void adressenErstellungLeer() {
        assertEquals("", adresseLeer.getStrasse());
        assertEquals("2", adresseLeer.getHausnummer());
        assertEquals("13593", adresseLeer.getPLZ());
        assertEquals("berlin", adresseLeer.getStadt());
    }

    @Test
    public void adresseSetterMethoden() {
        test.setStrasse("Obstallee");
        assertEquals("obstallee", test.getStrasse());
        test.setHausnummer("23");
        assertEquals("23", test.getHausnummer());
        test.setPLZ("13594");
        assertEquals("13594", test.getPLZ());
        test.setStadt("Bärlin");
        assertEquals("bärlin", test.getStadt());
    }

    @Test
    public void adressenErstelltungMitNull() {
        assertEquals("", testNull.getStrasse());
        assertEquals("", testNull.getHausnummer());
        assertEquals("", testNull.getPLZ());
        assertEquals("", testNull.getStadt());
    }

    @Test
    public void adresseSetterMethodenMitNull() {
        test.setStrasse(null);
        assertEquals("", test.getStrasse());
        test.setHausnummer(null);
        assertEquals("", test.getHausnummer());
        test.setPLZ(null);
        assertEquals("", test.getPLZ());
        test.setStadt(null);
        assertEquals("", test.getStadt());
    }

    @Test
    public void adresseSetterMethodenMitTrimUndLowerCase() {
        test.setStrasse(" OBSTALLEE  ");
        assertEquals("obstallee", test.getStrasse());
        test.setHausnummer("  23 ");
        assertEquals("23", test.getHausnummer());
        test.setPLZ("    13594   ");
        assertEquals("13594", test.getPLZ());
        test.setStadt("   BÄRLIN  ");
        assertEquals("bärlin", test.getStadt());
    }

    @Test
    public void addresseToString() {
        assertEquals("reclamweg 2, 13593 berlin", test.toString());
    }

    @Test
    public void addresseEqualsMethod() {
        assertEquals(test, test2);
        assertTrue(test.equals(test2));
        assertNotEquals(test, test3);
        assertFalse(test.equals(test3));
        assertNotEquals(test2, test3);
        assertFalse(test2.equals(test3));
        assertNotEquals(testNull, test);
        assertFalse(test.equals(null));
        assertFalse(test.equals(""));
        
    }

    @Test
    public void adresseHashCodeMethod() {
        assertEquals(test.hashCode(), test2.hashCode()); // gleiches Objekt, gleicher hashCode
        assertNotEquals(test.hashCode(), test3.hashCode()); // unterschiedliche Objekt, unterschiedlicher hashCode
    }
}
