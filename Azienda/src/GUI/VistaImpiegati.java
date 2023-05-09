package GUI;
import MODEL.*;
import CONTROLLER.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

    public class VistaImpiegati
    {
        private JTable table;
        private JScrollPane scrollPane;

        public VistaImpiegati(Controller controller, Frame frameMenuPrincipale) {
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

            /*
             * PROBLEMA DEVO FARE IN MODO CHE IL TESTO NON POSSA ESSERE MODIFICATO
             */
            // Creiamo la tabella impiegati
            String[] columns = {"Matricola", "Nome", "Cognome"};
            ArrayList<Impiegato> listaImpiegati = (ArrayList<Impiegato>) controller.getListaImpiegato();
            Object[][] data = new Object[listaImpiegati.size()][3];
            for (int i = 0; i < listaImpiegati.size(); i++) {
                data[i][0] = listaImpiegati.get(i).getMatricola();
                data[i][1] = listaImpiegati.get(i).getNome();
                data[i][2] = listaImpiegati.get(i).getCognome();
            }
            table = new JTable(data, columns);
            scrollPane = new JScrollPane(table);
            frameVistaImpiegato.add(scrollPane, BorderLayout.CENTER);

            // Mostrimo la finestra
            frameVistaImpiegato.setLocationRelativeTo(null);
            frameVistaImpiegato.setVisible(true);
        }

    }


