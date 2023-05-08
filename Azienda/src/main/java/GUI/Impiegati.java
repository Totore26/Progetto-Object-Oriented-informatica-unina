package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Impiegati
{
    @FXML
    private Hyperlink linkModificaImpiegato;
    @FXML
    private Button BottoneInserimentoImpiegato;
    @FXML
    private Button bottoneMenuPrincipale;


    @FXML//APERTURA DI UNA FINESTRA DOVE POSSO FARE MODIFICHE AD UN IMPIEGATO
    private void touchLinkModificaImpiegato() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/ModificaImpiegato.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Impiegati");
            stage.setScene(new Scene(root));
            stage.show();
            // nascondi la finestra corrente
            Stage stageToClose =(Stage) linkModificaImpiegato.getScene().getWindow();
            stageToClose.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML//APERTURA DI UNA FINESTRA DOVE POSSO INSERIRE UN NUOVO IMPIEGATO
    private void touchBottoneInserimentoImpiegato() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/InserimentoImpiegato.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Inserimento");
            stage.setScene(new Scene(root));
            stage.show();
            // chiudi la finestra corrente
            Stage stageToClose =(Stage) BottoneInserimentoImpiegato.getScene().getWindow();
            stageToClose.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML//CHIUSURA DI TUTTE LE FINESTRE CORRENTI E VISUALIZZAZIONE DELLA FINESTRA MENU PRINCIPALE(NASCOSTA)
    private void touchBottoneMenuPrincipale() throws IOException {
        // Ottieni la finestra corrente e la chiudo
        Stage stage = (Stage) BottoneInserimentoImpiegato.getScene().getWindow();
        stage.close();
        // Chiudi tutte le finestre
        for (Window w : Window.getWindows())
        {
            if (w != stage)
                w.hide();
        }
        //APRO LA NUOVA FINESTRA MENU PRINCIPALE
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/MenuPrincipale.fxml"));
        Parent root = loader.load();
        // Crea la scena e la finestra principale
        Scene scene = new Scene(root);
        stage.setTitle("Menu principale");
        stage.setScene(scene);
        stage.show();
    }


}
