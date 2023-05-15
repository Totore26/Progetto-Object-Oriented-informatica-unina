package GUI;

import CONTROLLER.Controller;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MenuLaboratoriGUI {
    private JTable tabella;
    private JScrollPane scrollPane;
    private JTextField barraDiRicerca;
    public MenuLaboratoriGUI(Controller controller, JFrame frameMenuPrincipale) {
        // Creiamo una finestra

        JFrame frameMenuLaboratori = new JFrame("Finestra Impiegati");
        frameMenuLaboratori.setSize(800, 600);
        frameMenuLaboratori.setLocationRelativeTo(null);
        frameMenuLaboratori.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // TABELLA LABORATORI



        String[] colonneTabella = {"Id Laboratorio", "Topic", "Responsabile Scientifico"};
        ArrayList<String> listaTopic = new ArrayList<String>();
        ArrayList<String> listaIdLab = new ArrayList<String>();
        ArrayList<String> listaRespScientifico = new ArrayList<String>();

        controller.getListaLaboratorioGUI(listaTopic,listaIdLab,listaRespScientifico);

        Object[][] data = new Object[listaIdLab.size()][3];
        for (int i = 0; i < listaIdLab.size(); i++) {
            data[i][0] = listaTopic.get(i);
            data[i][1] = listaIdLab.get(i);
            data[i][2] = listaRespScientifico.get(i);
        }
        // Creiamo il modello di tabella
        DefaultTableModel modelloTabella = new DefaultTableModel(data, colonneTabella);
        // Creiamo la tabella
        tabella = new JTable(modelloTabella);

        tabella.setDefaultEditor(Object.class, null);
        tabella.setDefaultEditor(Object.class, null);
        tabella.getTableHeader().setReorderingAllowed(false);
        tabella.setShowGrid(true);
        //COLORI TABELLA
        tabella.setGridColor(Color.BLACK);
        tabella.setBackground(Color.DARK_GRAY);
        tabella.getTableHeader().setBackground(Color.BLACK);
        tabella.getTableHeader().setForeground(Color.WHITE);

        // Creiamo il TableRowSorter con il tipo di modello di tabella corretto
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelloTabella);
        // Impostiamo il TableRowSorter sulla tabella
        tabella.setRowSorter(sorter);

        //barra di scorrimento
        scrollPane = new JScrollPane(tabella);
        frameMenuLaboratori.add(scrollPane, BorderLayout.CENTER);



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
        panelSearch.add(new JLabel("Cerca per topic: "));
        panelSearch.add(barraDiRicerca);

        frameMenuLaboratori.add(panelSearch, BorderLayout.NORTH);



        // BOTTONI



        // Creiamo il pulsante per aprire la finestra d'inserimento laboratorio
        JButton bottoneInserisci = new JButton("Inserisci");
        bottoneInserisci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InserimentoLaboratorioGUI dialog = new InserimentoLaboratorioGUI(controller,generaIdLab(),frameMenuLaboratori);
                frameMenuLaboratori.setVisible(false);
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
                    String idLabSelezionato = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();
                    int response = JOptionPane.showOptionDialog(frameMenuLaboratori, "Sei sicuro di voler eliminare il laboratorio " + idLabSelezionato + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                    if (response == JOptionPane.YES_OPTION) {
                        //elimino il laboratorio selezionata
                        try {
                            controller.eliminaLaboratorio(idLabSelezionato);
                        } catch (PSQLException ex) {
                            JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione del laboratorio:\n" + ex.getMessage(), "Errore di Eliminazione", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                        //aggiorno la tabella appena dopo l'eliminazione dell'impiegato
                        updateTable(controller,colonneTabella);
                    }
                } else {
                    // L'utente non ha selezionato una cella
                    JOptionPane.showMessageDialog(frameMenuLaboratori, "Seleziona un laboratorio per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JButton bottoneMenuPrincipale = new JButton("Menù Pricipale");
        bottoneMenuPrincipale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameMenuLaboratori.dispose();
                frameMenuPrincipale.setVisible(true);
            }
        });


        JButton bottoneProfiloImpiegato = new JButton("Profilo Laboratorio");
        bottoneProfiloImpiegato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabella.getSelectedRow();
                int selectedColumn = tabella.getSelectedColumn();
                // L'utente ha selezionato una cella
                if (selectedRow != -1 && selectedColumn != -1) {
                    // la matricola è nella prima colonna della tabella
                    String idLabSelezionato = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();

                    // Creao un'istanza della finestra di dialogo ProfiloImpiegato
                    ProfiloImpiegatoGUI profiloImpiegato = new ProfiloImpiegatoGUI(idLabSelezionato,controller,frameMenuLaboratori);
                    frameMenuLaboratori.setVisible(false);
                    // Mostro la finestra di dialogo
                    profiloImpiegato.setVisible(true);
                } else {
                    // L'utente non ha selezionato una cella
                    JOptionPane.showMessageDialog(frameMenuLaboratori, "Seleziona un impiegato per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
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
        frameMenuLaboratori.add(panelBottoni, BorderLayout.SOUTH);


        // Mostrimo la finestra
        frameMenuLaboratori.setVisible(true);
    }

    private void updateTable(Controller controller,String[] colonneTabella) {

        //LOAD DEI NUOVI DATI
        ArrayList<String> listaTopic = new ArrayList<String>();
        ArrayList<String> listaIdLab = new ArrayList<String>();
        ArrayList<String> listaRespScientifico = new ArrayList<String>();

        controller.getListaLaboratorioGUI(listaTopic,listaIdLab,listaRespScientifico);

        Object[][] nuoviDati = new Object[listaIdLab.size()][3];
        for (int i = 0; i < listaIdLab.size(); i++) {
            nuoviDati[i][0] = listaTopic.get(i);
            nuoviDati[i][1] = listaIdLab.get(i);
            nuoviDati[i][2] = listaRespScientifico.get(i);
        }

        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) tabella.getModel();
        model.setDataVector(nuoviDati, colonneTabella);
    }

    private String generaIdLab() {
        // Cerca la matricola più alta nella tabella
        int maxIdLab = 0;
        for (int i = 0; i < tabella.getModel().getRowCount(); i++) {
            String matricola = (String) tabella.getModel().getValueAt(i, 0);
            String[] parts = matricola.split("-");
            int numIdLab = Integer.parseInt(parts[1]);
            if (numIdLab > maxIdLab) {
                maxIdLab = numIdLab;
            }
        }
        // Incrementa il numero della matricola più alta e lo utilizza per generare la nuova matricola
        int newMatricolaNum = maxIdLab + 1;
        return "LAB-" + String.format("%03d", newMatricolaNum);
    }

}