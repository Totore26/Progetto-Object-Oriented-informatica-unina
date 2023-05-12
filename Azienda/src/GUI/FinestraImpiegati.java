package GUI;
import CONTROLLER.Controller;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

//LA CLASSE NON ESTENTE JDIALOG PERCHE VIENE GESTITA IN MODO DIVERSO
    public class FinestraImpiegati
    {
        private JTable tabella;
        private JScrollPane scrollPane;
        private JTextField barraDiRicerca;

        public FinestraImpiegati(Controller controller, Frame frameMenuPrincipale) {
            // Creiamo una finestra di esempio per testare la finestra d'inserimento impiegato

            JFrame frameVistaImpiegato = new JFrame("Finestra Impiegati");
            frameVistaImpiegato.setSize(800, 600);
            frameVistaImpiegato.setLocationRelativeTo(null);
            frameVistaImpiegato.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            // Creiamo la tabella impiegati
            String[] columns = {"Matricola", "Nome", "Cognome"};
            JFrame frameFinestraImpiegati = new JFrame("Finestra Impiegati");
            frameFinestraImpiegati.setSize(800, 600);
            frameFinestraImpiegati.setLocationRelativeTo(null);
            frameFinestraImpiegati.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



            // TABELLA IMPIEGATI


            String[] colonneTabella = {"Matricola", "Nome", "Cognome"};
            ArrayList<String> listaNomi = (ArrayList<String>) controller.getListaImpiegatoNomiGUI();
            ArrayList<String> listaCognomi = (ArrayList<String>) controller.getListaImpiegatoCognomiGUI();
            ArrayList<String> listaMatricole = (ArrayList<String>) controller.getListaImpiegatoMatricoleGUI();
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
            scrollPane = new JScrollPane(tabella);
            frameFinestraImpiegati.add(scrollPane, BorderLayout.CENTER);



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

            frameFinestraImpiegati.add(panelSearch, BorderLayout.NORTH);



            // BOTTONI



            // Creiamo il pulsante per aprire la finestra d'inserimento impiegato
            JButton bottoneInserisci = new JButton("Inserisci");
            bottoneInserisci.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    InserimentoImpiegato dialog = new InserimentoImpiegato(controller);
                    dialog.setVisible(true);
                    // Aggiungo un listener per la finestra di dialogo
                    dialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            // Chiamo il metodo updateTable() dopo la chiusura della finestra di dialogo
                            updateTable(controller, colonneTabella);
                        }
                    });
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
                        int response = JOptionPane.showOptionDialog(frameFinestraImpiegati, "Sei sicuro di voler eliminare la matricola " + matricolaSelezionata + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

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
                        JOptionPane.showMessageDialog(frameFinestraImpiegati, "Seleziona un impiegato per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });


            JButton bottoneMenuPrincipale = new JButton("Menù Pricipale");
            bottoneMenuPrincipale.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameFinestraImpiegati.dispose();
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
                        ProfiloImpiegato profiloImpiegato = new ProfiloImpiegato(matricolaSelezionata,controller);
                        // Mostro la finestra di dialogo
                        profiloImpiegato.setVisible(true);
                    } else {
                        // L'utente non ha selezionato una cella
                        JOptionPane.showMessageDialog(frameFinestraImpiegati, "Seleziona un impiegato per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
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
            frameFinestraImpiegati.add(panelBottoni, BorderLayout.SOUTH);


            // Mostrimo la finestra
            frameFinestraImpiegati.setVisible(true);
        }


        private void updateTable(Controller controller,String[] colonneTabella) {

            //LOAD DEI NUOVI DATI
            ArrayList<String> listaNomi = (ArrayList<String>) controller.getListaImpiegatoNomiGUI();
            ArrayList<String> listaCognomi = (ArrayList<String>) controller.getListaImpiegatoCognomiGUI();
            ArrayList<String> listaMatricole = (ArrayList<String>) controller.getListaImpiegatoMatricoleGUI();
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

    }


