package GUI;
import MODEL.*;
import CONTROLLER.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

    public class FinestraImpiegati
    {
        private JTable tabella;
        private JScrollPane scrollPane;

        public FinestraImpiegati(Controller controller, Frame frameMenuPrincipale) {
            // Creiamo una finestra di esempio per testare la finestra d'inserimento impiegato
            JFrame frameVistaImpiegato = new JFrame("Finestra Impiegati");
            frameVistaImpiegato.setSize(800, 600);
            frameVistaImpiegato.setLocationRelativeTo(null);
            frameVistaImpiegato.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            // Creiamo la tabella impiegati
            String[] columns = {"Matricola", "Nome", "Cognome"};
            //TODO AGGIUSTA QUESTA COSA POICHE' VIOLA LA BCE
            ArrayList<Impiegato> listaImpiegati = (ArrayList<Impiegato>) controller.getListaImpiegato();
            Object[][] data = new Object[listaImpiegati.size()][3];
            for (int i = 0; i < listaImpiegati.size(); i++) {
                data[i][0] = listaImpiegati.get(i).getMatricola();
                data[i][1] = listaImpiegati.get(i).getNome();
                data[i][2] = listaImpiegati.get(i).getCognome();
            }
            tabella = new JTable(data, columns);
            tabella.setDefaultEditor(Object.class, null);
            tabella.setDefaultEditor(Object.class, null);
            tabella.setShowGrid(true);
            //COLORI TABELLA
            tabella.setGridColor(Color.BLACK);
            tabella.setBackground(Color.DARK_GRAY);
            tabella.getTableHeader().setBackground(Color.BLACK);
            tabella.getTableHeader().setForeground(Color.WHITE);

            tabella.getTableHeader().setReorderingAllowed(false);
            scrollPane = new JScrollPane(tabella);
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
                    int selectedRow = tabella.getSelectedRow();
                    int selectedColumn = tabella.getSelectedColumn();

                    if (selectedRow != -1 && selectedColumn != -1) {
                        // La matricola si trova nella prima colonna della tabella
                        String matricolaSelezionata = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();
                        int response = JOptionPane.showOptionDialog(frameVistaImpiegato, "Sei sicuro di voler eliminare la matricola " + matricolaSelezionata + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                        if (response == JOptionPane.YES_OPTION) {

                            controller.eliminaImpiegato(matricolaSelezionata);

                            //TODO AGGIORNA LA GUI APPENA AVVIENE L'ELIMINAZIONE DI UN IMPIEGATO.

                        }
                    } else {
                        // L'utente non ha selezionato una cella
                        JOptionPane.showMessageDialog(frameVistaImpiegato, "Seleziona un impiegato per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
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


            //DA IMPLEMENTARE IL CODICE PER SALVARE LE MODIFICHE
            JButton bottoneProfiloImpiegato = new JButton("Profilo Impiegato");
            bottoneProfiloImpiegato.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = tabella.getSelectedRow();
                    int selectedColumn = tabella.getSelectedColumn();
                    // L'utente ha selezionato una cella
                    if (selectedRow != -1 && selectedColumn != -1) {
                        // la matricola è nella prima colonna della tabella
                        String matricolaSelezionata = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();

                        // Creao un'istanza della finestra di dialogo ProfiloImpiegato
                        ProfiloImpiegato profiloImpiegato = new ProfiloImpiegato(matricolaSelezionata);
                        // Mostro la finestra di dialogo
                        profiloImpiegato.setVisible(true);
                    } else {
                        // L'utente non ha selezionato una cella
                        JOptionPane.showMessageDialog(frameVistaImpiegato, "Seleziona un impiegato per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });




            // Aggiungiamo i pulsanti alla finestra
            JPanel panelBottoni = new JPanel(new BorderLayout());
            JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            panelBottoniLeft.add(bottoneMenuPrincipale);
            panelBottoniRight.add(bottoneProfiloImpiegato);
            panelBottoniRight.add(bottoneInserisci);
            panelBottoniRight.add(bottoneElimina);


            panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
            panelBottoni.add(panelBottoniRight, BorderLayout.EAST);

            frameVistaImpiegato.add(panelBottoni, BorderLayout.SOUTH);



            // Mostrimo la finestra
            frameVistaImpiegato.setVisible(true);
        }

    }


