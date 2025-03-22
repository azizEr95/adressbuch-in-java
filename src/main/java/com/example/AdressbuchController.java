package com.example;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mongodb.client.MongoDatabase;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;

public class AdressbuchController implements Initializable {

    protected Adressbuch adressbuch;
    protected ObservableList<Kontakt> tableContent;
    private MongoDBHelper mongoDBHelper;

    @FXML
    protected TextField searchField;
    
    @FXML
    protected TableView <Kontakt> tableView;

    @FXML
    protected TableColumn <Kontakt, String> nameColumn;

    @FXML
    protected TableColumn <Kontakt, String> numberColumn;

    @FXML
    protected TableColumn <Kontakt, String> emailColumn;

    @FXML
    protected TableColumn <Kontakt, String> adressColumn;

    @FXML
    protected Button addButton;

    @FXML 
    protected Button deleteButton;

    @FXML
    protected Button dateButton;

    @FXML
    protected Button closeButton;

    @FXML
    protected TextField nameField;

    @FXML
    protected TextField numberField;

    @FXML
    protected TextField emailField;

    @FXML
    protected TextField adressField;

    public AdressbuchController(Adressbuch adressbuch) {
        this.adressbuch = adressbuch;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(adressbuch == null) {
            adressbuch = new Adressbuch(true);
        }
        if (tableContent == null) {
            tableContent = FXCollections.observableArrayList();
        }
        MongoDatabase mongodatabase = MongoDBConnector.getDatabase("adressbuch_datenbank");
        mongoDBHelper = new MongoDBHelper(mongodatabase);
        this.loadKontakteAusDatenbank();

        this.configureTable();
        searchField.textProperty().addListener((event) -> filterList());
        addButton.setOnAction((eh) -> addKontakt());
        searchField.setOnAction((eh) -> searchKontakt());
        deleteButton.setOnAction((eh) -> deleteKontakt());
        dateButton.setOnAction((eh) -> terminUIOeffnen(tableView.getSelectionModel().getSelectedItem()));
        closeButton.setOnAction((eh) -> closeWindow());
    }

    private void filterList() {
        showKontakte(adressbuch.getAlleKontakte());
    }

    private void showKontakte(Kontakt[] kontakte) {
        tableContent.clear();
        tableContent.addAll(kontakte);
    }

    private void configureTable() {
        tableContent = FXCollections.observableArrayList();

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName().toString()));
        numberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNummer().toString()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail().toString()));
        adressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse().toString()));

        tableView.setEditable(true);

        nameColumn.setCellFactory(TextFieldTableCell.<Kontakt>forTableColumn());
        numberColumn.setCellFactory(TextFieldTableCell.<Kontakt>forTableColumn());
        emailColumn.setCellFactory(TextFieldTableCell.<Kontakt>forTableColumn());
        adressColumn.setCellFactory(TextFieldTableCell.<Kontakt>forTableColumn());

        nameColumn.setOnEditCommit((eh) -> updateKontaktName(eh));
        numberColumn.setOnEditCommit((eh) -> updateKontaktNummer(eh));
        emailColumn.setOnEditCommit((eh) -> updateKontaktEmail(eh));
        adressColumn.setOnEditCommit((eh) -> updateKontaktAdresse(eh));

        showKontakte(adressbuch.getAlleKontakte());
        tableView.setItems(tableContent);
    }

    private void closeWindow() {
        Stage window = (Stage) closeButton.getScene().getWindow();
        window.close();
    }

    private void updateKontaktName(TableColumn.CellEditEvent<Kontakt, String> kontakt) {
        String neuerName = kontakt.getNewValue();
        if (neuerName == null || neuerName.isBlank()) {
            ViewHelper.showError("Name darf nicht leer sein.");
            return;
        }
        updateKontakt(kontakt.getRowValue(), neuerName, kontakt.getRowValue().getNummer(), kontakt.getRowValue().getEmail(), kontakt.getRowValue().getAdresse());
    }
    
    private void updateKontaktNummer(TableColumn.CellEditEvent<Kontakt, String> kontakt) {
        String neueNummer = kontakt.getNewValue();
        updateKontakt(kontakt.getRowValue(), kontakt.getRowValue().getName(), neueNummer, kontakt.getRowValue().getEmail(), kontakt.getRowValue().getAdresse());
    }

    private void updateKontaktEmail(TableColumn.CellEditEvent<Kontakt, String> kontakt) {
        String neueEmail = kontakt.getNewValue();
        updateKontakt(kontakt.getRowValue(), kontakt.getRowValue().getName(), kontakt.getRowValue().getNummer(), neueEmail, kontakt.getRowValue().getAdresse());
    }

    private void updateKontaktAdresse(TableColumn.CellEditEvent<Kontakt, String> kontakt) {
        String[] adresse = kontakt.getNewValue().split("[ ,]+");
        if (adresse.length != 4) {
            ViewHelper.showError("Adresse muss aus 4 Teilen bestehen (Straße, Hausnummer, PLZ, Stadt).");
            return;
        }
        Adresse neueAdresse = new Adresse(adresse[0], adresse[1], adresse[2], adresse[3]);
        updateKontakt(kontakt.getRowValue(), kontakt.getRowValue().getName(), 
                      kontakt.getRowValue().getNummer(), kontakt.getRowValue().getEmail(), neueAdresse);
    }
    
    private void updateKontakt(Kontakt alterKontakt, String neuerName, String neueNummer, String neueEmail, Adresse adresse) {
        try {
            Kontakt neuerKontakt = new Kontakt(neuerName, neueNummer, neueEmail, adresse);
            adressbuch.updateKontakt(alterKontakt.getName(), neuerKontakt);
            showKontakte(adressbuch.getAlleKontakte());
        } catch (KeinPassenderKontaktException | IllegalStateException exception) {
            ViewHelper.showError(exception.getMessage());
        } finally {
            filterList();
        }
    }
    

    private void searchKontakt() {
        Kontakt[] gefiltert = adressbuch.searchKontakte(searchField.getText());
        searchField.clear();
        showKontakte(gefiltert);
}

    private void addKontakt() {
        String tmpName = nameField.getText();
        String tmpPhone = numberField.getText();
        String tmpEmail = emailField.getText();
        String[] tmpAdresse = adressField.getText().split("[ ,]+");
        if (tmpName.isEmpty() || tmpPhone.isEmpty() || tmpEmail.isEmpty() || tmpAdresse.length != 4) {
            ViewHelper.showError("Bitte alle Felder korrekt ausfüllen. Adresse muss aus 4 Teilen bestehen Mit 'Leerzeichen oder Komma getrennt'.");
            return;
        }
        try {
            Kontakt kontakt = new Kontakt(tmpName, tmpPhone, tmpEmail, new Adresse(tmpAdresse[0], tmpAdresse[1], tmpAdresse[2], tmpAdresse[3]));
            adressbuch.addKontakt(kontakt);
            mongoDBHelper.saveKontakt(kontakt);
            nameField.clear();
            numberField.clear();
            emailField.clear();
            adressField.clear();
            showKontakte(adressbuch.getAlleKontakte());
        } catch (DoppelterSchluesselException | IllegalStateException exception) {
            ViewHelper.showError(exception.getMessage());
        }
    }

    private void deleteKontakt() {
        try {
            Kontakt zuLoeschen = tableView.getSelectionModel().getSelectedItem();
            if(zuLoeschen == null) {
                ViewHelper.showError("Bitte wähle ein Kontakt zum Löschen aus");
                return;
            }
            adressbuch.deleteKontakt(zuLoeschen.getName());

            mongoDBHelper.deleteKontakt(zuLoeschen);

            showKontakte(adressbuch.getAlleKontakte());
        } catch (KeinPassenderKontaktException | IllegalStateException exception) {
            ViewHelper.showError(exception.getMessage());
        }
    }

    private void terminUIOeffnen(Kontakt kontakt) {
        try {
            if (kontakt == null) {
                ViewHelper.showError("Bitte wähle einen existierenden Kontakt aus");
                return;
            }
    
            // Debugging: Überprüfe, ob die FXML-Datei gefunden wird
            URL fxmlUrl = getClass().getResource("/dateLayout.fxml");
            if (fxmlUrl == null) {
                System.out.println("FXML-Datei nicht gefunden!");
                return;
            }
            System.out.println("FXML-Datei gefunden: " + fxmlUrl);
    
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
    
            TerminController terminController = loader.getController();
            if (terminController == null) {
                System.out.println("Controller ist null!");
                return;
            }
            System.out.println("Controller erfolgreich geladen.");
    
            terminController.setKontakt(kontakt); // Kontakt explizit setzen
            terminController.setAddressbuch(adressbuch);
    
            Stage stage = new Stage();
            stage.setTitle("Termin für " + kontakt.getName());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
    
        } catch (IOException e) {
            e.printStackTrace();
            ViewHelper.showError("Fehler beim Laden des Termin-Fensters: " + e.getMessage());
        }
    }

    private void loadKontakteAusDatenbank() {
        showKontakte(mongoDBHelper.loadKontakte().stream().peek(k -> {
            try {
                adressbuch.addKontakt(k);
            } catch(DoppelterSchluesselException e) {
                ViewHelper.showError(e.getMessage());
            }
        })
        .toArray(size -> new Kontakt[size]));
    }
}
