package GUI;
import CONTROLLER.Controller;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * The type Menu impiegati gui.
 */
//LA CLASSE NON ESTENTE JDIALOG PERCHE VIENE GESTITA IN MODO DIVERSO
    public class MenuImpiegatiGUI
    {
        private final JTable tabella;
        private final JTextField barraDiRicerca;

        /**
         * Instantiates a new Menu impiegati gui.
         *
         * @param controller          the controller
         * @param frameMenuPrincipale the frame menu principale
         */
        public MenuImpiegatiGUI(Controller controller, JFrame frameMenuPrincipale) {
            // Creiamo una finestra

            JFrame frameMenuImpiegati = new JFrame("Finestra Impiegati");
            frameMenuImpiegati.setSize(800, 600);
            frameMenuImpiegati.setLocationRelativeTo(null);
            frameMenuImpiegati.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



            // TABELLA IMPIEGATI



            String[] colonneTabella = {"Matricola", "Nome", "Cognome"};
            ArrayList<String> listaNomi = (ArrayList<String>) controller.getListaImpiegatoNomiGUI();
            ArrayList<String> listaCognomi = (ArrayList<String>) controller.getListaImpiegatoCognomiGUI();
            ArrayList<String> listaMatricole = controller.getListaImpiegatoMatricoleGUI();
            Object[][] data = new Object[listaMatricole.size()][3];
            for (int i = 0; i < listaNomi.size(); i++) {
                data[i][0] = listaMatricole.get(i);
                data[i][1] = listaNomi.get(i);
                data[i][2] = listaCognomi.get(i);
            }
            // Creiamo il modello di tabella
            DefaultTableModel modelloTabella = new DefaultTableModel(data, colonneTabella);
            // Creiamo la tabella
            tabella = new JTable(modelloTabella);
            // Creiamo il TableRowSorter con il tipo di modello di tabella corretto
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelloTabella);
            sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
            // Impostiamo il TableRowSorter sulla tabella
            tabella.setRowSorter(sorter);

            tabella.setDefaultEditor(Object.class, null);
            tabella.setDefaultEditor(Object.class, null);
            tabella.getTableHeader().setReorderingAllowed(false);
            tabella.setShowGrid(true);
            //COLORI TABELLA
            tabella.setGridColor(Color.BLACK);
            tabella.setBackground(Color.DARK_GRAY);
            tabella.getTableHeader().setBackground(Color.BLACK);
            tabella.getTableHeader().setForeground(Color.WHITE);

            //barra di scorrimento
            JScrollPane scrollPane = new JScrollPane(tabella);
            frameMenuImpiegati.add(scrollPane, BorderLayout.CENTER);



            //BARRA DI RICERCA



            // Creiamo la barra di ricerca
            barraDiRicerca = new JTextField(20);
            barraDiRicerca.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(barraDiRicerca.getText());
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(barraDiRicerca.getText());
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(barraDiRicerca.getText());
                }
                public void search(String searchString) {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchString, 2));
                }
            });

            // Aggiungiamo la barra di ricerca alla finestra
            JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelSearch.add(new JLabel("Cerca per cognome: "));
            panelSearch.add(barraDiRicerca);

            frameMenuImpiegati.add(panelSearch, BorderLayout.NORTH);



            // BOTTONI



            // Creiamo il pulsante per aprire la finestra d'inserimento impiegato
            JButton bottoneInserisci = new JButton("Inserisci");
            bottoneInserisci.addActionListener(e -> {
                InserimentoImpiegatoGUI dialog = new InserimentoImpiegatoGUI(controller,generaMatricola(),frameMenuImpiegati);
                frameMenuImpiegati.setVisible(false);
                dialog.setVisible(true);
                // Aggiungo un listener per la finestra di dialogo
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // Chiamo il metodo updateTable() dopo la chiusura della finestra di dialogo
                        updateTable(controller, colonneTabella);
                    }
                });
            });


            //DA IMPLEMENTARE IL CODICE DI ELIMINAZIONE IMPIEGATO NELL ACTION LISTENER
            JButton bottoneElimina = new JButton("Elimina");
            bottoneElimina.addActionListener(e -> {
                int selectedRow = tabella.getSelectedRow();
                int selectedColumn = tabella.getSelectedColumn();

                if (selectedRow != -1 && selectedColumn != -1) {
                    // La matricola si trova nella prima colonna della tabella
                    String matricolaSelezionata = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();
                    int response = JOptionPane.showOptionDialog(frameMenuImpiegati, "Sei sicuro di voler eliminare la matricola " + matricolaSelezionata + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                    if (response == JOptionPane.YES_OPTION) {
                        //elimino l'impiegato con la matricola selezionata
                        try {
                            controller.eliminaImpiegato(matricolaSelezionata);
                        } catch (PSQLException ex) {
                            JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dei dati dell'impiegato:\n" + ex.getMessage(), "Errore di Eliminazione", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                        //aggiorno la tabella appena dopo l'eliminazione dell'impiegato
                        updateTable(controller,colonneTabella);
                    }
                } else {
                    // L'utente non ha selezionato una cella
                    JOptionPane.showMessageDialog(frameMenuImpiegati, "Seleziona un impiegato per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            });


            JButton bottoneMenuPrincipale = new JButton("Menù Principale");
            bottoneMenuPrincipale.addActionListener(e -> {
                frameMenuImpiegati.dispose();
                frameMenuPrincipale.setVisible(true);
            });


            JButton bottoneProfiloImpiegato = new JButton("Profilo Impiegato");
            bottoneProfiloImpiegato.addActionListener(e -> {
                int selectedRow = tabella.getSelectedRow();
                int selectedColumn = tabella.getSelectedColumn();
                // L'utente ha selezionato una cella
                if (selectedRow != -1 && selectedColumn != -1) {
                    // la matricola è nella prima colonna della tabella
                    String matricolaSelezionata = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();

                    // Creo un'istanza della finestra di dialogo ProfiloImpiegato
                    ProfiloImpiegatoGUI profiloImpiegato = new ProfiloImpiegatoGUI(matricolaSelezionata,controller,frameMenuImpiegati);
                    frameMenuImpiegati.setVisible(false);
                    // Mostro la finestra di dialogo
                    profiloImpiegato.setVisible(true);
                } else {
                    // L'utente non ha selezionato una cella
                    JOptionPane.showMessageDialog(frameMenuImpiegati, "Seleziona un impiegato per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
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
            frameMenuImpiegati.add(panelBottoni, BorderLayout.SOUTH);


            // Mostriamo la finestra
            frameMenuImpiegati.setVisible(true);
        }


        private void updateTable(Controller controller,String[] colonneTabella) {

            //LOAD DEI NUOVI DATI
            ArrayList<String> listaNomi = (ArrayList<String>) controller.getListaImpiegatoNomiGUI();
            ArrayList<String> listaCognomi = (ArrayList<String>) controller.getListaImpiegatoCognomiGUI();
            ArrayList<String> listaMatricole = controller.getListaImpiegatoMatricoleGUI();
            Object[][] nuoviDati = new Object[listaMatricole.size()][3];
            for (int i = 0; i < listaNomi.size(); i++) {
                nuoviDati[i][0] = listaMatricole.get(i);
                nuoviDati[i][1] = listaNomi.get(i);
                nuoviDati[i][2] = listaCognomi.get(i);
            }

            //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
            DefaultTableModel model = (DefaultTableModel) tabella.getModel();
            model.setDataVector(nuoviDati, colonneTabella);
        }

        private String generaMatricola() {
        // Cerca la matricola più alta nella tabella
        int maxMatricola = 0;
        for (int i = 0; i < tabella.getModel().getRowCount(); i++) {
            String matricola = (String) tabella.getModel().getValueAt(i, 0);
            String[] parts = matricola.split("-");
            int numMatricola = Integer.parseInt(parts[1]);
            if (numMatricola > maxMatricola) {
                maxMatricola = numMatricola;
            }
        }
        // Incrementa il numero della matricola più alta e lo utilizza per generare la nuova matricola
        int newMatricolaNum = maxMatricola + 1;
        return "MAT-" + String.format("%03d", newMatricolaNum);
        }

    }


