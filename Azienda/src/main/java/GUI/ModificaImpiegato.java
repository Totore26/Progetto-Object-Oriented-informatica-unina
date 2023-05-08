package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ModificaImpiegato
{

    public ImageView immagineImpiegato;
    public TextField nomeField;
    public TextField cognomeField;
    public TextField matricolaField;
    public TextField codiceFiscaleField;
    public TextField stipendioField;
    public Button bottoneMenuPrincipale;
    public Button bottoneMenuImpiegati;
    public ChoiceBox choiceBoxSesso;
    public CheckBox checkBoxDirigente;
    public TextArea textAreaCurriculum;
    public DatePicker datePickerDataAssunzione;
    public DatePicker datePickerDataLicenziamento;
    public Button bottoneSalvaLeModifiche;

    @FXML
    public void touchBottoneMenuPrincipale() throws IOException {
        // Ottieni la finestra corrente e la chiudo
        Stage stage = (Stage) bottoneMenuPrincipale.getScene().getWindow();
        stage.close();
        // Chiudi tutte le finestre
        for (Window w : Window.getWindows())
            if (w != stage)
                w.hide();
        //APRO LA NUOVA FINESTRA IMPIEGATI
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/MenuPrincipale.fxml"));
        Parent root = loader.load();
        // Crea la scena e la finestra principale
        Scene scene = new Scene(root);
        stage.setTitle("Menu Principale");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void touchBottoneMenuImpiegati() throws IOException {
        // Ottieni la finestra corrente e la chiudo
        Stage stage = (Stage) bottoneMenuImpiegati.getScene().getWindow();
        stage.close();
        // Chiudi tutte le finestre
        for (Window w : Window.getWindows())
            if (w != stage)
                w.hide();
        //APRO LA NUOVA FINESTRA IMPIEGATI
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/Impiegati.fxml"));
        Parent root = loader.load();
        // Crea la scena e la finestra principale
        Scene scene = new Scene(root);
        stage.setTitle("Impiegati");
        stage.setScene(scene);
        stage.show();
    }

    public void touchBottoneSalvaLeModifiche() {
    }
}

