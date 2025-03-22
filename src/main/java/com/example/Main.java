package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/adressLayout.fxml"));
        Adressbuch adressbuch = new Adressbuch(true);
        AdressbuchController controller = new AdressbuchController(adressbuch);
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        } catch(Exception exception) {
            System.out.println("Fehler!");
            exception.printStackTrace();
        }
    }
    
}
