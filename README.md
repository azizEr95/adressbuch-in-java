# Adressbuch Projekt

Dies ist ein einfaches Adressbuch, dass mit **Java** und **JavFx** entwickelt wurde. Es verwendet **MongoDB** als Datenbank, um Kontakte zu speichern.

## Funktionen

- **Kontakte verwalten**: Kontakte hinzufügen, bearbeiten und löschen.
- **Termine verwalten**: Zu jedem Kontakt können Termine hinzugefügt und angezeigt werden.
- **Datenbank**: MongoDB wird zur Speicherung der Daten verwendet.
- **Benutzeroberfläche**: JavaFX-Oberfläche zur Anzeige und Bearbeitung der Kontakte und Termine

## Technologien

- **Java** --> Version 21 genutzt
- **JavaFX** für die Benutzeroberfläche --> Version 23
- **JUnit** für die automatischen Tests --> Version 5.7.0
- **MongoDB** als NoSQL Datenbank --> Version 5.4.0-alpha0
- **Maven** zur Verwaltung von Abhängigkeiten
- 

## Projektstruktur

- `src/`
  - `main.java.com.example/` - Enthält alle Java Klassen
    - `Adressbuch.java` - Klasse für die Logik eines Adressbuches
    - `AdressbuchController.java` - Controller für die JavaFX Oberfläche
    - `Adresse.java` - Klasse für die Adresse eines Kontakts.
    - `DoppelterSchluesselException` - Exceptionklasse, die auftritt, wenn ein Kontakt mit selben Namen erstellt wird.
    - `KeinPassenderKontaktException` - Exceptionklasse, die auftritt, wenn ein Kontakt, der nicht existiert geaendert wird.
    - `Kontakt.java` - Klasse für ein Kontakt
    - `Main.java` - Main-Klasse zum starten der Anwendung
    - `MongoDBConnector.java` - Verbindet die Anwendung mit der MongoDB-Datenbank
    - `MongoDBHelper.java` - Hilfsklasse zum Interagieren mit MongoDB
    - `Termin.java` - Klasse für die Termine eines Kontakts
    - `TerminController.java` - Controller für die JavaFX Oberfläche des Terminplaners eines Kontaktes
    - `Terminplaner.java` - Klasse für die Logik eines Terminplaners
    - `ViewHelper.java` - erscheint, wenn eine Exception auftritt
  - `resources/`
    - `adressbuch.fxml` - FXML-Datei für die JavaFX Oberfläche
    - `dateLayout.fxml` - FXML-Datei für den Terminplaner Controller
  - `test/`
  - `java.com.example/` Tests für jeweils die Logik-Klassen des Projektes
      - AdressbuchTest.java
      - AdresseTest.java
      - KontaktTest.java
      - SchluesselExceptionTest.java
      - TerminplanerTest.java
      - TerminTest.java
   
## Voraussetzungen

- **Java 21** oder höher muss installiert sein.
- **MongoDB** muss lokal verfügbar sein
- **Maven** für die Abhängigkeitsverwaltung


## Projekt anwenden

1. **Projekt klonen**
   ```bash
   git clone https://github.com/dein-benutzername/adressbuch.git
   ```

2.**Abhängikeiten installieren**
  ```bash
  mvn install
  ```

3.**MongoDB starten**
3.1 **Homebrew installieren**, wenn es man noch nich getan hat
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```
3.2 **MongoDB installieren**
```bash
brew tap mongodb/brew
```
und dann

```bash
brew install mongodb-community@5.0
```
3.3. **MongoDB starten**
```bash
brew services start mongodb/brew/mongodb-community
```

4.**Projekt ausführen**
```bash
mvn javafx:run
```

## Hier ein paar Bilder meines Projektes

**Benutzeroberfläche, nach Start des Projektes mit Testdaten**

**Ein Kontakt hinzufügen**

**nach einem Kontakt suchen**

**Kontakt loeschen**

**Terminplaner vom Kontakt Mike**
