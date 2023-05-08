package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuPrincipale {
    @FXML
    private Button bottoneMenuImpiegati;
    @FXML
    private Button bottoneMenuLab;
    @FXML
    private Button bottoneMenuProg;
    @FXML
    private Button bottoneMenuStorico;


    //QUANDO APRO UNA NUOVA FINESTRA, LA FINESTRA DEL MENU PRINCIPALE VIENE CHIUSA
    @FXML//APERTURA DELLA FINESTRA IMPIEGATI
    private void touchBottoneMenuImpiegati() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/Impiegati.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Impiegati");
            stage.setScene(new Scene(root));
            stage.show();
            // chiudo la finestra corrente
            Stage stageToClose = (Stage) bottoneMenuImpiegati.getScene().getWindow();
            stageToClose.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML//APERTURA DELLA FINESTRA LABORATORI
    private void touchBottoneMenuLab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/Laboratori.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Laboratori");
            stage.setScene(new Scene(root));
            stage.show();
            // chiudo la finestra corrente
            Stage stageToClose = (Stage)bottoneMenuLab.getScene().getWindow();
            stageToClose.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML//APERTURA DELLA FINESTRA PROGETTI
    private void touchBottoneMenuProg() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/Progetti.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Progetti");
            stage.setScene(new Scene(root));
            stage.show();
            // chiudo la finestra corrente
            Stage stageToClose = (Stage)bottoneMenuProg.getScene().getWindow();
            stageToClose.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML//APERTURA DELLA FINESTRA STORICO
    private void touchBottoneMenuStorico() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/Storico.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Storico");
            stage.setScene(new Scene(root));
            stage.show();
            // chiudo la finestra corrente
            Stage stageToClose = (Stage)bottoneMenuStorico.getScene().getWindow();
            stageToClose.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

