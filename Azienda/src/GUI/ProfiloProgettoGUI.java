package GUI;

import CONTROLLER.Controller;
import com.toedter.calendar.JDateChooser;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ProfiloProgettoGUI extends JDialog{
    private final JComboBox<Object> responsabileComboBox;
    private final JComboBox<Object> referenteComboBox;
    private final JSpinner budgetSpinner;
    private final JDateChooser dataFineChooser;
    private final JTable tabellaGestione;

    public ProfiloProgettoGUI(String cupSelezionato, Controller controller, JFrame framePadre) {
        setTitle("Profilo Progetto");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creiamo le variabili per ricevere dal controller i dati del progetto
        String nomeSelezionato = controller.getSingoloProgettoNomeProfiloGUI(cupSelezionato);
        java.sql.Date dataInizioSelezionata= (java.sql.Date) controller.getSingoloProgettoDataInizioProfiloGUI(cupSelezionato);
        java.sql.Date dataFineSelezionata = (java.sql.Date) controller.getSingoloProgettoDataFineProfiloGUI(cupSelezionato);
        float budgetSelezionato = controller.getSingoloProgettoBudgetProfiloGUI(cupSelezionato);
        String responsabileSelezionato = controller.getSingoloProgettoResponsabileProfiloGUI(cupSelezionato);
        String referenteSelezionato = controller.getSingoloProgettoReferenteProfiloGUI(cupSelezionato);


        // Creiamo il pannello principale
        JPanel panel = new JPanel(new BorderLayout());

        // Creiamo il pannello per i dati anagrafici
        JPanel datiAnagraficiPanel = new JPanel(new GridLayout(0, 2,5,5));
        datiAnagraficiPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));

        // Creiamo il pannello sinistro per i dati anagrafici
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Cup
        JLabel cupLabel = new JLabel("Cup:", SwingConstants.CENTER);
        cupLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField cupField = new JTextField();
        cupField.setText(cupSelezionato);
        datiAnagraficiPanel.add(cupLabel);
        datiAnagraficiPanel.add(cupField);

        // Nome
        JLabel nomeLabel = new JLabel("Nome:", SwingConstants.CENTER);
        nomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField nomeField = new JTextField();
        nomeField.setText(nomeSelezionato);
        datiAnagraficiPanel.add(nomeLabel);
        datiAnagraficiPanel.add(nomeField);

        // Budget
        JLabel budgetLabel = new JLabel("Budget:", SwingConstants.CENTER);
        budgetLabel.setHorizontalAlignment(SwingConstants.LEFT);
        SpinnerNumberModel budgetModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 100.0);
        budgetSpinner = new JSpinner(budgetModel);
        JFormattedTextField budgetText = ((JSpinner.NumberEditor) budgetSpinner.getEditor()).getTextField();
        budgetText.setColumns(10);
        budgetSpinner.setValue(budgetSelezionato);
        datiAnagraficiPanel.add(budgetLabel);
        datiAnagraficiPanel.add(budgetSpinner);

        // Data Inizio
        JLabel dataInizioLabel = new JLabel("Data Inizio:", SwingConstants.CENTER);
        dataInizioLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField dataInizioField = new JFormattedTextField();
        dataInizioField.setText(dataInizioSelezionata.toString());
        datiAnagraficiPanel.add(dataInizioLabel);
        datiAnagraficiPanel.add(dataInizioField);

        // Data Fine
        JLabel dataFineLabel = new JLabel("Data Fine:", SwingConstants.LEFT);
        dataFineLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dataFineChooser = new JDateChooser();
        dataFineChooser.setDateFormatString("yyyy-MM-dd");
        if(dataFineSelezionata != null){
            dataFineChooser.setDate(dataFineSelezionata);
        }
        datiAnagraficiPanel.add(dataFineLabel);
        datiAnagraficiPanel.add(dataFineChooser);

        // Responsabile
        JLabel responsabile = new JLabel("Responsabile:",SwingConstants.LEFT);
        responsabile.setHorizontalAlignment(SwingConstants.LEFT);
        responsabileComboBox = new JComboBox<>();
        responsabileComboBox.addItem(responsabileSelezionato);
        ArrayList<String> responsabiliDisponibili= controller.getListaDirigentiDisponibiliGUI();
        for(String s : responsabiliDisponibili)
            responsabileComboBox.addItem(s);
        datiAnagraficiPanel.add(responsabile);
        datiAnagraficiPanel.add(responsabileComboBox, BorderLayout.WEST );

        // Referente
        JLabel referente = new JLabel("Referente:",SwingConstants.LEFT);
        referente.setHorizontalAlignment(SwingConstants.LEFT);
        referenteComboBox = new JComboBox<>();
        referenteComboBox.addItem(referenteSelezionato);
        //utilizzo il metodo che mi ritorna tutti gli impiegati senior
        ArrayList<String> referentiDisponibili= controller.getListaDipendentiSeniorDisponibiliGUI();
        for(String s : referentiDisponibili)
            referenteComboBox.addItem(s);
        datiAnagraficiPanel.add(referente);
        datiAnagraficiPanel.add(referenteComboBox, BorderLayout.WEST );



        // ... aggiungo i dati anagrafici al pannello
        leftPanel.add(datiAnagraficiPanel);
        leftPanel.setPreferredSize(new Dimension(700,600));
        panel.add(leftPanel, BorderLayout.CENTER);



        // Creiamo il pannello destro per la tabella di gestione
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Laboratori Gestiti:"));



        //TABELLE



        // Tabella di gestione
        ArrayList<String> listaLabGestiti = controller.leggiGestioniProgetto(cupSelezionato);

        DefaultTableModel tabellaGestioneModel = new DefaultTableModel();
        Object[][] data = new Object[listaLabGestiti.size()][1];
        for (int i = 0; i < listaLabGestiti.size(); i++) {
            data[i][0] = listaLabGestiti.get(i);
        }
        tabellaGestioneModel.setDataVector(data, new Object[]{"ID"});
        tabellaGestione = new JTable(tabellaGestioneModel);
        tabellaGestione.setShowGrid(true);

        //allineo il testo delle colonne al centro
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // Applicazione del renderizzatore personalizzato a tutte le colonne
        int columnCount = tabellaGestione.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            tabellaGestione.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tabellaGestioneModel);
        // Impostiamo il TableRowSorter sulla tabella
        tabellaGestione.setRowSorter(sorter);

        tabellaGestione.setDefaultEditor(Object.class, null);
        tabellaGestione.setDefaultEditor(Object.class, null);
        tabellaGestione.getTableHeader().setReorderingAllowed(false);
        tabellaGestione.setShowGrid(true);
        //COLORI TABELLA
        tabellaGestione.setGridColor(Color.DARK_GRAY);
        tabellaGestione.setBackground(Color.DARK_GRAY);
        tabellaGestione.getTableHeader().setBackground(Color.DARK_GRAY);
        tabellaGestione.getTableHeader().setForeground(Color.WHITE);

        //barra di scorrimento
        JScrollPane scrollPane = new JScrollPane(this.tabellaGestione);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        rightPanel.add(new JScrollPane(this.tabellaGestione), BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(150,700));
        tabellaGestione.getTableHeader().setReorderingAllowed(false);

        panel.add(rightPanel, BorderLayout.EAST);




        //BOTTONI


        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton bottoneSalva = new JButton("Salva modifiche");
        JButton bottoneAnnulla = new JButton("Annulla modifiche");
        JButton bottoneAggiungiGestione = new JButton("Aggiungi Gestione");
        JButton bottoneRimuoviGestione = new JButton("Rimuovi Gestione");
        panelBottoniLeft.add(bottoneSalva);
        panelBottoniLeft.add(bottoneAnnulla);
        panelBottoniRight.add(bottoneAggiungiGestione);
        panelBottoniRight.add(bottoneRimuoviGestione);

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);


        // Logica per salvare le modifiche
        bottoneSalva.addActionListener(e -> {
            setVisible(false);

            float budgetModificato = ((Number) budgetSpinner.getValue()).floatValue();
            String responsabileModificato = (String) responsabileComboBox.getSelectedItem();
            String referenteModificato = (String) referenteComboBox.getSelectedItem();
            Date dataFineModificata = dataFineChooser.getDate();
            java.sql.Date sqlDataFine = null;
            if(dataFineChooser.getDate() != null) {
                sqlDataFine = new java.sql.Date(dataFineModificata.getTime());
            }

            /*
            //TODO salvo i dati solo se sono state effettuate modifiche (questo metodo non funziona)
            if(stipendioSelezionato == stipendioModificato || dirigenteSelezionato == dirigenteModificato || curriculumSelezionato.equals(curriculumModificato) || dataLicenziamentoSelezionata == dataLicenziamentoModificata) {
                JOptionPane.showMessageDialog(null, "i dati non sono stati modificati!\n", "Nessuna modifica da eseguire", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                framePadre.setVisible(true);
            } else {

             */

            try {
                controller.modificaProgetto(cupSelezionato,budgetModificato,sqlDataFine,responsabileModificato,referenteModificato);
                JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati del progetto:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } finally {
                dispose();
                framePadre.setVisible(true);
            }
            //}

        });


        //bottone che torna alla vista impiegati
        bottoneAnnulla.addActionListener(e -> {
            //chiudo la finestra di dialogo
            dispose();
            framePadre.setVisible(true);
        });


        // Logica per aggiungere afferenza
        bottoneAggiungiGestione.addActionListener(e -> {
            // Creazione e visualizzazione della finestra di dialogo
            JDialog dialog = new JDialog(); //utilizzo una classe di finestra di dialogo personalizzata
            dialog.setModal(true); // Imposto la finestra di dialogo come modale per bloccare l'interazione con la finestra principale
            dialog.setTitle("Aggiungi gestione:\n");

            //creo la tabella
            DefaultTableModel tabellaGestioniModel = new DefaultTableModel();
            tabellaGestioniModel.addColumn("ID");
            ArrayList<String> listaCodLab = controller.getListaCodiciLaboratoriGUI();
            //rimuovo dalla lista dei laboratori quelli a cui l'impiegato afferisce gia
            listaCodLab.removeAll(listaLabGestiti);
            //riempio la tabella
            Object[][] data1 = new Object[listaCodLab.size()][1];
            for (int i = 0; i < listaCodLab.size(); i++) {
                data1[i][0] = listaCodLab.get(i);
            }
            tabellaGestioniModel.setDataVector(data1, new Object[]{"ID"});
            JTable tabellaCodiciLab = new JTable(tabellaGestioniModel);
            tabellaCodiciLab.setShowGrid(true);

            //allineo il testo delle colonne al centro
            DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
            centerRenderer1.setHorizontalAlignment(SwingConstants.CENTER);
            // Applicazione del renderizzatore personalizzato a tutte le colonne
            int columnCount1 = tabellaCodiciLab.getColumnCount();
            for (int i = 0; i < columnCount1; i++) {
                tabellaCodiciLab.getColumnModel().getColumn(i).setCellRenderer(centerRenderer1);
            }

            // Aggiungi la tabella a un componente di scorrimento
            JScrollPane scrollPane1 = new JScrollPane(tabellaCodiciLab);
            // Aggiungi il componente di scorrimento alla finestra di dialogo
            dialog.add(scrollPane1);

            // Quando l'utente tocca un codice, parte questo Listener
            tabellaCodiciLab.getSelectionModel().addListSelectionListener(e1 -> {
                // Ottieni il codice selezionato
                int selectedRow = tabellaCodiciLab.getSelectedRow();
                String codLabSelezionato = (String) tabellaCodiciLab.getValueAt(selectedRow, 0);

                // L utente ha selezionato una colonna
                int response = JOptionPane.showOptionDialog(dialog, "Aggiungo al progetto " + cupSelezionato + " la gestione del laboratorio " + codLabSelezionato + "?", "Conferma Salvataggio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
                if (response == JOptionPane.YES_OPTION) {
                    //aggiungo l'afferenza al codLabSelezionato
                    try {
                        controller.aggiungiGestione(cupSelezionato,codLabSelezionato);
                        dialog.setVisible(false);
                        JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'aggiunta della gestione al progetto:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        dialog.dispose();
                    }
                    updateTabella(controller,cupSelezionato);
                }
            });

            // Imposta le dimensioni e la posizione della finestra di dialogo
            dialog.setSize(250, 400);
            dialog.setLocationRelativeTo(null); // Posiziona la finestra di dialogo al centro dello schermo
            dialog.setVisible(true);
        });


        // Logica per rimuovere gestione
        bottoneRimuoviGestione.addActionListener(e -> {
            int selectedRow = tabellaGestione.getSelectedRow();
            int selectedColumn = tabellaGestione.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                // La matricola si trova nella prima colonna della tabella
                String codLabSelezionato = tabellaGestione.getValueAt(tabellaGestione.getSelectedRow(), 0).toString();
                int response = JOptionPane.showOptionDialog( panel, "Sei sicuro di voler eliminare l'afferenza al laboratorio " + codLabSelezionato + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                if (response == JOptionPane.YES_OPTION) {
                    //elimino la gestione
                    try {
                        controller.eliminaGestione(cupSelezionato,codLabSelezionato);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dell'afferenza:\n" + ex.getMessage(), "Errore di eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                    //aggiorno la tabella appena dopo l'eliminazione dell'impiegato
                    updateTabella(controller,cupSelezionato);
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(panel, "Seleziona un codice laboratorio per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });



        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);
        add(panelBottoni, BorderLayout.SOUTH);


        // Rendo non modificabili alcuni campi
        cupField.setEditable(false);
        nomeField.setEditable(false);
        dataInizioField.setEditable(false);


        // Imposto la dimensione della finestra e la rendo visibile
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //listener per mostrare la finestra padre quando viene chiusa quella figlia
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                framePadre.setVisible(true);
            }
        });

        //QUESTO METODO SERVE A RENDERE MODALE LA FINESTRA, IN MODO DA DISATTIVARE LA FINESTRA PADRE MENTRE E ATTIVA QUELLA DI DIALOGO
        //NON LA UTILIZZIAMO PERCHE UNA VOLTA SETTATA A TRUE RISULTANO ALCUNI COMPORTAMENTI GRAFICI ANOMALI NELL APP

        //setModal(true);

        setVisible(true);
    }

    private void updateTabella(Controller controller, String cupSelezionato) {
        //load dei nuovi dati
        ArrayList<String> listaLabGestiti = controller.leggiGestioniProgetto(cupSelezionato);
        Object[][] nuoviDati = new Object[listaLabGestiti.size()][listaLabGestiti.size()];
        for (int i = 0; i < listaLabGestiti.size(); i++) {
            nuoviDati[i][0] = listaLabGestiti.get(i);
        }
        // Aggiungi le nuove righe alla tabella
        DefaultTableModel model = (DefaultTableModel) tabellaGestione.getModel();
        model.setDataVector(nuoviDati,new Object[]{"ID"});
    }


}
