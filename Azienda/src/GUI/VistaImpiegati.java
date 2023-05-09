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
            // Creiamo una finestra di esempio per testare la finestra d'inserimento impiegato
            JFrame frameVistaImpiegato = new JFrame("Finestra Impiegati");
            frameVistaImpiegato.setSize(800, 600);
            frameVistaImpiegato.setLocationRelativeTo(null);
            frameVistaImpiegato.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

            table.setDefaultEditor(Object.class, null);
            scrollPane = new JScrollPane(table);
            frameVistaImpiegato.add(scrollPane, BorderLayout.CENTER);



            // BOTTONI

            // Creiamo il pulsante per aprire la finestra d'inserimento impiegato
            JButton bottoneInserisci = new JButton("Inserisci");
            bottoneInserisci.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    InserimentoImpiegato dialog = new InserimentoImpiegato(frameVistaImpiegato,controller);
                    dialog.setVisible(true);
                }
            });


            //DA IMPLEMENTARE IL CODICE DI ELIMINAZIONE IMPIEGATO NELL ACTION LISTENER
            JButton bottoneElimina = new JButton("Elimina");
            bottoneElimina.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    int selectedColumn = table.getSelectedColumn();

                    if (selectedRow != -1 && selectedColumn != -1) {
                        // L'utente ha selezionato una cella valida
                        int modelColumnIndex = table.convertColumnIndexToModel(selectedColumn);
                        if (modelColumnIndex == 0) {
                            // La cella selezionata è nella prima colonna (quella contenente la matricola)
                            String matricola = table.getValueAt(selectedRow, selectedColumn).toString();
                            int response = JOptionPane.showOptionDialog(frameVistaImpiegato, "Sei sicuro di voler eliminare la matricola " + matricola + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
                            if (response == JOptionPane.YES_OPTION) {
                                // L'utente ha confermato l'eliminazione
                                // ...
                            }
                        } else {
                            // La cella selezionata non è nella prima colonna
                            JOptionPane.showMessageDialog(frameVistaImpiegato, "Seleziona una matricola nella prima colonna.", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // L'utente non ha selezionato una cella valida
                        JOptionPane.showMessageDialog(frameVistaImpiegato, "Seleziona una matricola nella prima colonna per eliminare l'impiegato.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });


            JButton bottoneMenuPrincipale = new JButton("Menù Pricipale");
            bottoneMenuPrincipale.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameVistaImpiegato.dispose();
                    frameMenuPrincipale.setVisible(true);
                }
            });



            // Aggiungiamo i pulsanti alla finestra
            JPanel panelBottoni = new JPanel(new BorderLayout());
            JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            panelBottoniLeft.add(bottoneMenuPrincipale);
            panelBottoniRight.add(bottoneInserisci);
            panelBottoniRight.add(bottoneElimina);

            panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
            panelBottoni.add(panelBottoniRight, BorderLayout.EAST);

            frameVistaImpiegato.add(panelBottoni, BorderLayout.SOUTH);



            // Mostrimo la finestra
            frameVistaImpiegato.setVisible(true);
        }

    }


