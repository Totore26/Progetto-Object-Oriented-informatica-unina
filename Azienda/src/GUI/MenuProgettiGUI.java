package GUI;

import CONTROLLER.Controller;
import com.toedter.calendar.JDateChooser;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Menu progetti gui.
 */
public class MenuProgettiGUI {
    private final JTable tabella;
    private final JTextField barraDiRicerca;

    /**
     * Instantiates a new Menu progetti gui.
     *
     * @param controller          the controller
     * @param frameMenuPrincipale the frame menu principale
     */
    public MenuProgettiGUI(Controller controller, JFrame frameMenuPrincipale) {
        // Creiamo una finestra

        JFrame frameMenuProgetti = new JFrame("Finestra Progetti");
        frameMenuProgetti.setSize(800, 600);
        frameMenuProgetti.setLocationRelativeTo(null);
        frameMenuProgetti.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // TABELLA IMPIEGATI



        String[] colonneTabella = {"Cup", "Nome", "Responsabile","Referente"};
        ArrayList<String> listaCup = new ArrayList<>();
        ArrayList<String> listaNomi = new ArrayList<>();
        ArrayList<String> listaResponsabili = new ArrayList<>();
        ArrayList<String> listaReferenti = new ArrayList<>();

        controller.getListaProgettoGUI(listaNomi,listaCup,listaResponsabili,listaReferenti);

        Object[][] data = new Object[listaCup.size()][4];
        for (int i = 0; i < listaNomi.size(); i++) {
            data[i][0] = listaCup.get(i);
            data[i][1] = listaNomi.get(i);
            data[i][2] = listaResponsabili.get(i);
            data[i][3] = listaReferenti.get(i);
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
        frameMenuProgetti.add(scrollPane, BorderLayout.CENTER);



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
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchString, 1));
            }
        });

        // Aggiungiamo la barra di ricerca alla finestra
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSearch.add(new JLabel("Cerca per nome: "));
        panelSearch.add(barraDiRicerca);

        frameMenuProgetti.add(panelSearch, BorderLayout.NORTH);



        // BOTTONI



        // Creiamo il pulsante per aprire la finestra d'inserimento impiegato
        JButton bottoneInserisci = new JButton("Inserisci");
        bottoneInserisci.addActionListener(e -> {
            InserimentoProgettoGUI dialog = new InserimentoProgettoGUI(controller,generaCup(),frameMenuProgetti);
            frameMenuProgetti.setVisible(false);
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
                // Il cup si trova nella prima colonna della tabella
                String cupSelezionato = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();
                int response = JOptionPane.showOptionDialog(frameMenuProgetti, "Sei sicuro di voler eliminare il progetto " + cupSelezionato + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                if (response == JOptionPane.YES_OPTION) {
                    //elimino il progetto con la matricola selezionata
                    try {
                        controller.eliminaProgetto(cupSelezionato);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dei dati del progetto:\n" + ex.getMessage(), "Errore di Eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                    //aggiorno la tabella appena dopo l'eliminazione dell'impiegato
                    updateTable(controller,colonneTabella);
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuProgetti, "Seleziona un progetto per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });


        JButton bottoneMenuPrincipale = new JButton("Menù Principale");
        bottoneMenuPrincipale.addActionListener(e -> {
            frameMenuProgetti.dispose();
            frameMenuPrincipale.setVisible(true);
        });


        JButton bottoneProfiloProgetto = new JButton("Profilo Progetto");
        bottoneProfiloProgetto.addActionListener(e -> {
            int selectedRow = tabella.getSelectedRow();
            int selectedColumn = tabella.getSelectedColumn();
            // L'utente ha selezionato una cella
            if (selectedRow != -1 && selectedColumn != -1) {
                // la matricola è nella prima colonna della tabella
                String cupSelezionato = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();

                // Creo un'istanza della finestra di dialogo ProfiloImpiegato
                ProfiloProgettoGUI profiloProgetto = new ProfiloProgettoGUI(cupSelezionato,controller,frameMenuProgetti);
                frameMenuProgetti.setVisible(false);
                // Mostro la finestra di dialogo
                profiloProgetto.setVisible(true);

                // Imposto l'azione da eseguire alla chiusura della finestra
                profiloProgetto.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        updateTable(controller,colonneTabella);
                    }
                });

            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(frameMenuProgetti, "Seleziona un progetto per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });



        // Aggiungiamo i pulsanti alla finestra
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panelBottoniLeft.add(bottoneMenuPrincipale);
        panelBottoniRight.add(bottoneProfiloProgetto);
        panelBottoniRight.add(bottoneInserisci);
        panelBottoniRight.add(bottoneElimina);

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);
        frameMenuProgetti.add(panelBottoni, BorderLayout.SOUTH);


        // Mostriamo la finestra
        frameMenuProgetti.setVisible(true);
    }


    private void updateTable(Controller controller,String[] colonneTabella) {

        //LOAD DEI NUOVI DATI
        ArrayList<String> listaCup = new ArrayList<>();
        ArrayList<String> listaNomi = new ArrayList<>();
        ArrayList<String> listaResponsabili = new ArrayList<>();
        ArrayList<String> listaReferenti = new ArrayList<>();

        controller.getListaProgettoGUI(listaNomi,listaCup,listaResponsabili,listaReferenti);

        Object[][] nuoviDati = new Object[listaCup.size()][4];
        for (int i = 0; i < listaNomi.size(); i++) {
            nuoviDati[i][0] = listaCup.get(i);
            nuoviDati[i][1] = listaNomi.get(i);
            nuoviDati[i][2] = listaResponsabili.get(i);
            nuoviDati[i][3] = listaReferenti.get(i);
        }

        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) tabella.getModel();
        model.setDataVector(nuoviDati, colonneTabella);
    }

    private String generaCup() {
        // Cerca il cup più alto nella tabella
        int maxCup = 0;
        for (int i = 0; i < tabella.getModel().getRowCount(); i++) {
            String matricola = (String) tabella.getModel().getValueAt(i, 0);
            String[] parts = matricola.split("-");
            int numCup = Integer.parseInt(parts[1]);
            if (numCup > maxCup) {
                maxCup = numCup;
            }
        }
        // Incrementa il numero della matricola più alta e lo utilizza per generare la nuova matricola
        int newMaxCup = maxCup + 1;
        return "CUP-" + String.format("%03d", newMaxCup);
    }

}
