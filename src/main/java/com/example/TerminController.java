package com.example;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TerminController implements Initializable {

    @FXML
    protected TextField textFieldKontakt;

    @FXML
    protected DatePicker datePicker;

    @FXML
    protected TextField textFieldDescription;

    @FXML
    protected Button saveButton;

    @FXML
    protected Button closeButton;

    private Kontakt kontakt;

    private Adressbuch adressbuch;

    private Terminplaner terminplaner;

    public TerminController() {
        
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnAction((eh) -> saveTermin());
        closeButton.setOnAction((eh) -> closeWindow());
        datePicker.setOnAction((eh) -> showTermin(datePicker.getValue()));
    }

    public void setAddressbuch(Adressbuch adressbuch) {
        if(adressbuch == null) {
            throw new IllegalArgumentException("Adressbuch muss existieren");
        }
        this.adressbuch = adressbuch;
    }

    public void setKontakt(Kontakt kontakt) {
        if (kontakt == null) {
            System.out.println("Kontakt ist null!");
            return;
        }
        this.kontakt = kontakt;
        terminplaner = kontakt.getTerminplaner();
        textFieldKontakt.setText(kontakt.getName());
        textFieldKontakt.setDisable(true);

        Termin[] termine = kontakt.getAlleTermine();
        if (termine.length > 0) {
            Termin letzterTermin = termine[termine.length - 1]; // Letzten Termin nehmen
            datePicker.setValue(letzterTermin != null ? letzterTermin.getDatum() : LocalDate.now());
            textFieldDescription.setText(letzterTermin != null ? letzterTermin.getBeschreibung() : "kein Termin vorhanden");
        }
    }

    public void showTermin(LocalDate datum) {
        if(datum == null) {
            ViewHelper.showError("Bitte ein Datum angeben");
        }
        textFieldDescription.setText(terminplaner.getTermin(datum).getBeschreibung());
    }

    private void saveTermin() {
        if (kontakt == null) {
            ViewHelper.showError("Kein Kontakt ausgewählt.");
            return;
        }
        if (datePicker.getValue() == null) {
            ViewHelper.showError("Bitte ein Datum auswählen.");
            return;
        }

        LocalDate datum = datePicker.getValue();
        Termin termin = new Termin(textFieldDescription.getText(), datePicker.getValue());
        kontakt.setTermin(datum, termin);
        
    
        // Speichern in der Adressbuch-Instanz (falls nötig)
        try {
            adressbuch.updateKontakt(kontakt.getName(), kontakt);
        } catch(KeinPassenderKontaktException e) {
            ViewHelper.showError(e.getMessage());
        }
        ViewHelper.showInfo("Termin gespeichert.");
    }

    private void closeWindow() {
    Stage window = (Stage) closeButton.getScene().getWindow();
    window.close();
    }
}
