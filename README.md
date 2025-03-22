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

<img width="597" alt="Bildschirmfoto 2025-03-22 um 13 28 59" src="https://github.com/user-attachments/assets/9e4d31c3-b95b-4b42-a6d1-dbaf78fa9bdf" />

**Ein Kontakt hinzufügen**


<img width="597" alt="Bildschirmfoto 2025-03-22 um 13 31 20" src="https://github.com/user-attachments/assets/a9db0945-7c29-410e-8465-9867e2c57443" />

**nach einem Kontakt suchen**


<img width="597" alt="Bildschirmfoto 2025-03-22 um 13 31 52" src="https://github.com/user-attachments/assets/7f3ce504-bb1a-47ae-b121-67e4b6f42b69" />



<img width="597" alt="Bildschirmfoto 2025-03-22 um 13 32 02" src="https://github.com/user-attachments/assets/30b064f6-5cee-40fb-b93c-df1c5660f94b" />

**Kontakt loeschen**


<img width="597" alt="Bildschirmfoto 2025-03-22 um 13 32 20" src="https://github.com/user-attachments/assets/d2b4ea77-d8b9-42cb-8081-4f9d7880d8c8" />


<img width="597" alt="Bildschirmfoto 2025-03-22 um 13 32 30" src="https://github.com/user-attachments/assets/056f9a8c-2cc2-41ad-8586-dec0d202f651" />

**Terminplaner vom Kontakt Mike**


<img width="597" alt="Bildschirmfoto 2025-03-22 um 13 32 41" src="https://github.com/user-attachments/assets/4b8b05f4-8d91-4cde-be68-0ee69a6dd72c" />

