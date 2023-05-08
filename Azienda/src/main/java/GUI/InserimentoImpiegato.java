package GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class InserimentoImpiegato
{
    public CheckBox checkBoxDirigente;
    public DatePicker DatePickerDataAssunzione;
    public DatePicker DatePickerDataLicenziamento;
    @FXML
    private ChoiceBox choicheBoxSesso;
    @FXML
    private TextArea textAreaCurriculum;
    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneSalvaDati;
    @FXML
    private TextField matricolaField;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField codiceFiscaleField;
    @FXML
    private TextField curriculumField;
    @FXML
    private CheckBox dirigenteCheckBox;
    @FXML
    private TextField stipendioField;


    @FXML
    private void touchBottoneSalvaDati() {
        // Implementa la logica per salvare i dati e tornare alla finestra impiegato
    }
    @FXML//CHIUSURA DI TUTTE LE FINESTRE CORRENTI E VISUALIZZAZIONE DELLA FINESTRA IMPIEGATI
    private void touchBottoneAnnulla() throws IOException {
        // Ottieni la finestra corrente e la chiudo
        Stage stage = (Stage) bottoneSalvaDati.getScene().getWindow();
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
}
