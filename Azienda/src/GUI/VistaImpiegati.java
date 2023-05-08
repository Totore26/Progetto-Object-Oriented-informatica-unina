package GUI;

import CONTROLLER.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaImpiegati
{
    private static Controller controller = new Controller();

    public static void main(String[] args) {
        // Creiamo una finestra di esempio per testare la finestra di inserimento impiegato
        JFrame frameVistaImpiegato = new JFrame("Finestra Impiegati");
        frameVistaImpiegato.setSize(800, 600);
        frameVistaImpiegato.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creiamo il pulsante per aprire la finestra di inserimento impiegato
        JButton inserisciButton = new JButton("Inserisci");
        inserisciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InserimentoImpiegato dialog = new InserimentoImpiegato(frameVistaImpiegato,controller);
                dialog.setVisible(true);
            }
        });

        // Aggiungiamo il pulsante alla finestra
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(inserisciButton);
        frameVistaImpiegato.add(buttonPanel, BorderLayout.SOUTH);

        // Mostrimo la finestra
        frameVistaImpiegato.setLocationRelativeTo(null);
        frameVistaImpiegato.setVisible(true);
    }
}
