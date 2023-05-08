import CONTROLLER.Controller;
import GUI.Impiegati;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application
{
    public static void main(String[] args)
    {
        System.out.println("GRANDE RAIMONDO!");
        //AVVIO IL CICLO DI VITA DELL'APPLICAZIONE E RICHIAMO IL METODO START CHE LANCIA L'APPLICAZIONE
        launch(args);

        Controller c = new Controller();

    }








    @Override//METODO START CHE APRE LA PRIMA FINESTRA(MENU PRINCIPALE)
    public void start(Stage primaryStage) throws Exception {
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VISTA/MenuPrincipale.fxml"));
        Parent root = loader.load();
        // Crea la scena e la finestra principale
        Scene scene = new Scene(root);
        primaryStage.setTitle("Menu principale");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


