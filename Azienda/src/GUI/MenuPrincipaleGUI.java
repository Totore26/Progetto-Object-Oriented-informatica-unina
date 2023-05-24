package GUI;

import CONTROLLER.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * The type Menu principale gui.
 */
public class MenuPrincipaleGUI {
    /**
     * The Controller.
     */
    Controller controller = new Controller();
    private final JFrame frame;

    /**
     * Instantiates a new Menu principale gui.
     */
    public MenuPrincipaleGUI() {
        frame = new JFrame("Menu Principale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea la JLabel di benvenuto
        JLabel labelBenvenuto = new JLabel("Benvenuto nel gestionale dell'Azienda!");
        labelBenvenuto.setFont(new Font("Arial",Font.PLAIN,18));

        // Crea i bottoni
        JButton bottoneImpiegati = new JButton("Impiegati");
        JButton bottoneLaboratori = new JButton("Laboratori");
        JButton bottoneProgetti = new JButton("Progetti");

        // Imposta il font dei bottoni
        bottoneImpiegati.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneLaboratori.setFont(new Font("Arial", Font.PLAIN, 16));
        bottoneProgetti.setFont(new Font("Arial", Font.PLAIN, 16));

        // Imposta il layout del frame come BoxLayout
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Imposta l'allineamento orizzontale dei componenti al centro
        labelBenvenuto.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneImpiegati.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneLaboratori.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneProgetti.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aggiungi la JLabel e i bottoni al frame
        frame.add(Box.createVerticalStrut(50));
        frame.add(labelBenvenuto);
        frame.add(Box.createVerticalStrut(50));
        frame.add(bottoneImpiegati);
        frame.add(Box.createVerticalStrut(10));
        frame.add(bottoneLaboratori);
        frame.add(Box.createVerticalStrut(10));
        frame.add(bottoneProgetti);

        Dimension buttonSize = new Dimension(200, 50);
        bottoneImpiegati.setMaximumSize(buttonSize);
        bottoneLaboratori.setMaximumSize(buttonSize);
        bottoneProgetti.setMaximumSize(buttonSize);

        bottoneImpiegati.addActionListener(e -> {
            // chiudo la finestra corrente
            frame.setVisible(false);
            // apro la finestra MenuImpiegati
            MenuImpiegatiGUI menuImpiegati =new MenuImpiegatiGUI(controller,frame);

        });

        bottoneLaboratori.addActionListener(e -> {
            // chiudo la finestra corrente
            frame.setVisible(false);
            // apro la finestra MenuLaboratori
            MenuLaboratoriGUI menuLaboratori = new MenuLaboratoriGUI(controller,frame);

        });


        bottoneProgetti.addActionListener(e -> {
            // chiudo la finestra corrente
            frame.setVisible(false);
            // apro la finestra MenuImpiegati
            MenuProgettiGUI menuProgetti = new MenuProgettiGUI(controller,frame);

        });

        // Imposta le dimensioni del frame, la posizione al centro e rendilo visibile
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        new MenuPrincipaleGUI();
    }

}
