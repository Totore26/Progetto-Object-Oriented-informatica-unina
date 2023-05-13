package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import CONTROLLER.Controller;
import com.toedter.calendar.JDateChooser;
import org.postgresql.util.PSQLException;

public class ProfiloImpiegato extends JDialog {
    private JTextField matricolaField;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceFiscaleField;
    private JTextField sessoField;
    private JTextArea curriculumTextArea;
    private JCheckBox dirigenteCheckBox;
    private JTextField tipoImpiegatoField;
    private JSpinner stipendioSpinner;
    private JTextField dataAssunzioneChooser;
    private JDateChooser dataLicenziamentoChooser;
    private JTable tabellaStorico;
    private JTable tabellaAfferenze;
    private JTextField impiegatoReferente;
    private JTextField impiegatoResponsabile;
    private JTextField impiegatoRScientifico;

    public ProfiloImpiegato(String matricolaSelezionata, Controller controller) {
        setTitle("Profilo Impiegato");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creiamo le variabili per ricevere dal controller i dati dell'impiegato
        String nomeSelezionato = controller.getSingoloImpiegatoNomeProfiloGUI(matricolaSelezionata);
        String cognomeSelezionato = controller.getSingoloImpiegatoCognomeProfiloGUI(matricolaSelezionata);
        String codiceFiscaleSelezionato = controller.getSingoloImpiegatoCodiceFiscaleProfiloGUI(matricolaSelezionata);
        String curriculumSelezionato = controller.getSingoloImpiegatoCurriculumProfiloGUI(matricolaSelezionata);
        boolean dirigenteSelezionato = controller.getSingoloImpiegatoDirigenteProfiloGUI(matricolaSelezionata);
        String tipoImpiegatoSelezionato = controller.getSingoloImpiegatoTipoProfiloGUI(matricolaSelezionata);
        java.sql.Date dataAssunzioneSelezionata= (java.sql.Date) controller.getSingoloImpiegatoDataAssProfiloGUI(matricolaSelezionata);
        java.sql.Date dataLicenziamentoSelezionata = (java.sql.Date) controller.getSingoloImpiegatoDataLicProfiloGUI(matricolaSelezionata);
        float stipendioSelezionato = controller.getSingoloImpiegatoStipendioProfiloGUI(matricolaSelezionata);
        String sessoSelezionato = controller.getSingoloImpiegatoSessooProfiloGUI(matricolaSelezionata);



        // Creiamo il pannello principale
        JPanel panel = new JPanel(new BorderLayout());

        // Creiamo il pannello per i dati anagrafici
        JPanel datiAnagraficiPanel = new JPanel(new GridLayout(0, 2,5,5));
        datiAnagraficiPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));

        // Creiamo il pannello sinistro per i dati anagrafici
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Matricola
        JLabel matricolaLabel = new JLabel("Matricola:", SwingConstants.CENTER);
        matricolaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        matricolaField = new JTextField();

        matricolaField.setText(matricolaSelezionata);
        datiAnagraficiPanel.add(matricolaLabel);
        datiAnagraficiPanel.add(matricolaField);

        // Nome
        JLabel nomeLabel = new JLabel("Nome:", SwingConstants.CENTER);
        nomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nomeField = new JTextField();
        nomeField.setText(nomeSelezionato);
        datiAnagraficiPanel.add(nomeLabel);
        datiAnagraficiPanel.add(nomeField);

        // Cognome
        JLabel cognomeLabel = new JLabel("Cognome:", SwingConstants.CENTER);
        cognomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cognomeField = new JTextField();
        cognomeField.setText(cognomeSelezionato);
        datiAnagraficiPanel.add(cognomeLabel);
        datiAnagraficiPanel.add(cognomeField);

        // Codice fiscale
        JLabel codiceFiscaleLabel = new JLabel("Codice Fiscale", SwingConstants.CENTER);
        codiceFiscaleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        codiceFiscaleField = new JTextField();
        codiceFiscaleField.setText(codiceFiscaleSelezionato);
        datiAnagraficiPanel.add(codiceFiscaleLabel);
        datiAnagraficiPanel.add(codiceFiscaleField);

        // Sesso
        JLabel sessoLabel = new JLabel("Sesso:", SwingConstants.CENTER);
        sessoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        sessoField = new JTextField();
        sessoField.setText(sessoSelezionato);
        datiAnagraficiPanel.add(sessoLabel);
        datiAnagraficiPanel.add(sessoField);

        // Tipo impiegato
        JLabel tipoImpiegatoLabel = new JLabel("Tipo Impiegato:", SwingConstants.CENTER);
        tipoImpiegatoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        tipoImpiegatoField = new JTextField();
        tipoImpiegatoField.setText(tipoImpiegatoSelezionato);
        datiAnagraficiPanel.add(tipoImpiegatoLabel);
        datiAnagraficiPanel.add(tipoImpiegatoField);

        // Stipendio
        JLabel stipendioLabel = new JLabel("Stipendio:", SwingConstants.CENTER);
        stipendioLabel.setHorizontalAlignment(SwingConstants.LEFT);
        SpinnerNumberModel stipendioModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 100.0);
        stipendioSpinner = new JSpinner(stipendioModel);
        JFormattedTextField stipendioText = ((JSpinner.NumberEditor) stipendioSpinner.getEditor()).getTextField();
        stipendioText.setColumns(10);
        stipendioSpinner.setValue(stipendioSelezionato);
        datiAnagraficiPanel.add(stipendioLabel);
        datiAnagraficiPanel.add(stipendioSpinner);

        // Data assunzione
        JLabel dataAssunzioneLabel = new JLabel("Data assunzione:", SwingConstants.CENTER);
        dataAssunzioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dataAssunzioneChooser = new JFormattedTextField();
        dataAssunzioneChooser.setText(dataAssunzioneSelezionata.toString());
        datiAnagraficiPanel.add(dataAssunzioneLabel);
        datiAnagraficiPanel.add(dataAssunzioneChooser);

        // Data licenziamento
        JLabel dataLicenziamentoLabel = new JLabel("Data licenziamento:", SwingConstants.LEFT);
        dataLicenziamentoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dataLicenziamentoChooser = new JDateChooser();
        dataLicenziamentoChooser.setDateFormatString("yyyy-mm-dd");
        if(dataLicenziamentoSelezionata != null){
            dataLicenziamentoChooser.setDate(dataLicenziamentoSelezionata);
        }
        datiAnagraficiPanel.add(dataLicenziamentoLabel);
        datiAnagraficiPanel.add(dataLicenziamentoChooser);


        // Dirigente
        JLabel dirigenteLabel = new JLabel("Dirigente:", SwingConstants.LEFT);
        dataLicenziamentoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dirigenteCheckBox = new JCheckBox();
        if(dirigenteSelezionato){
            dirigenteCheckBox.setSelected(true);
        } else {
            dirigenteCheckBox.setSelected(false);
        }
        datiAnagraficiPanel.add(dirigenteLabel);
        datiAnagraficiPanel.add(dirigenteCheckBox);

        // Curriculum
        JLabel curriculumLabel = new JLabel("Curriculum:", SwingConstants.CENTER);
        curriculumLabel.setHorizontalAlignment(SwingConstants.LEFT);
        curriculumTextArea = new JTextArea(5, 18);
        JScrollPane scrollPane = new JScrollPane(curriculumTextArea);
        curriculumTextArea.setText(curriculumSelezionato);
        datiAnagraficiPanel.add(curriculumLabel);
        datiAnagraficiPanel.add(scrollPane);

        // ... aggiungo i dati anagrafici al pannello
        leftPanel.add(datiAnagraficiPanel);
        leftPanel.setPreferredSize(new Dimension(700,600));
        panel.add(leftPanel, BorderLayout.CENTER);



        // Creiamo il pannello destro per la tabella di afferenza
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Laboratori Associati:"));


        // Tabella di afferenza
        ArrayList<String> listaLabAfferiti = controller.leggiAfferenzeImpiegato(matricolaSelezionata);
        DefaultTableModel tabellaAfferenzaModel = new DefaultTableModel();
        tabellaAfferenzaModel.addColumn("ID");
        Object[][] data = new Object[listaLabAfferiti.size()][1];
        for (int i = 0; i < listaLabAfferiti.size(); i++) {
            data[i][0] = listaLabAfferiti.get(i);
        }
        tabellaAfferenzaModel.setDataVector(data, new Object[]{"ID"});
        tabellaAfferenze = new JTable(tabellaAfferenzaModel);
        tabellaAfferenze.setShowGrid(true);
        //allineo il testo delle colonne al centro
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // Applicazione del renderizzatore personalizzato a tutte le colonne
        int columnCount = tabellaAfferenze.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            tabellaAfferenze.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        //barra di scorrimento
        scrollPane = new JScrollPane(tabellaAfferenze);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        rightPanel.add(new JScrollPane(tabellaAfferenze), BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(150,700));
        tabellaAfferenze.getTableHeader().setReorderingAllowed(false);


        panel.add(rightPanel, BorderLayout.EAST);

        // Creiamo il pannello inferiore per la tabella dello storico
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Storico:"));

        // Tabella dello storico
        Date[] listaScatti = controller.leggiStoriciImpiegato(matricolaSelezionata);
        DefaultTableModel tabellaStoricoModel = new DefaultTableModel();
        tabellaStoricoModel.addColumn("Scatto junior");
        tabellaStoricoModel.addColumn("Scatto middle");
        tabellaStoricoModel.addColumn("Scatto senior");
        tabellaStoricoModel.addRow(new Object[]{listaScatti[0],listaScatti[1],listaScatti[2]});
        tabellaStorico = new JTable(tabellaStoricoModel);
        tabellaStorico.setEnabled(false);
        bottomPanel.add(new JScrollPane(tabellaStorico), BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setPreferredSize(new Dimension(1000,75));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabellaStorico.getColumnModel().getColumn(0).setCellRenderer(renderer);
        tabellaStorico.getColumnModel().getColumn(1).setCellRenderer(renderer);
        tabellaStorico.getColumnModel().getColumn(2).setCellRenderer(renderer);
        tabellaStorico.getTableHeader().setReorderingAllowed(false);

        //BOTTONI


        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton bottoneSalva = new JButton("Salva modifiche");
        JButton bottoneAnnulla = new JButton("Annulla modifiche");
        JButton bottoneAggiungiAfferenza = new JButton("Aggiungi afferenza");
        JButton bottoneRimuoviAfferenza = new JButton("Rimuovi afferenza");
        panelBottoniLeft.add(bottoneSalva);
        panelBottoniLeft.add(bottoneAnnulla);
        panelBottoniRight.add(bottoneAggiungiAfferenza);
        panelBottoniRight.add(bottoneRimuoviAfferenza);

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);


        // Logica per salvare le modifiche
        bottoneSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                try {
                    float stipendioModificato = ((Number) stipendioSpinner.getValue()).floatValue();
                    boolean dirigenteModificato = dirigenteCheckBox.isSelected();
                    String curriculumModificato = curriculumTextArea.getText();
                    java.util.Date dataLicenziamento = dataLicenziamentoChooser.getDate();
                    java.sql.Date sqlDataLicenziamento = null;
                    if(dataLicenziamentoChooser.getDate() != null) {
                        sqlDataLicenziamento = new java.sql.Date(dataLicenziamento.getTime());
                    }
                    controller.modificaImpiegato(matricolaSelezionata,curriculumModificato,dirigenteModificato,sqlDataLicenziamento,stipendioModificato);
                    JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati dell'impiegato:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } finally {
                    dispose();
                }

            }
        });


        //bottone che torna alla vista impiegati
        bottoneAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //chiudo la finestra di dialogo
                dispose();
            }
        });


        // Logica per aggiungere afferenza
        bottoneAggiungiAfferenza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creazione e visualizzazione della finestra di dialogo
                JDialog dialog = new JDialog(); // Puoi utilizzare una classe di finestra di dialogo personalizzata
                dialog.setModal(true); // Imposta la finestra di dialogo come modale per bloccare l'interazione con la finestra principale
                dialog.setTitle("Aggiungi afferenza:\n");

                //creo la tabella
                DefaultTableModel tabellaAfferenzaModel = new DefaultTableModel();
                tabellaAfferenzaModel.addColumn("ID");
                ArrayList<String> listaCodLab = controller.getListaCodiciLaboratoriGUI();
                //rimuovo dalla lista dei laboratori quelli a cui l'impiegato afferisce gia
                listaCodLab.removeAll(listaLabAfferiti);
                //riempio la tabella
                Object[][] data = new Object[listaCodLab.size()][1];
                for (int i = 0; i < listaCodLab.size(); i++) {
                    data[i][0] = listaCodLab.get(i);
                }
                tabellaAfferenzaModel.setDataVector(data, new Object[]{"ID"});
                JTable tabellaCodiciLab = new JTable(tabellaAfferenzaModel);
                tabellaCodiciLab.setShowGrid(true);

                //allineo il testo delle colonne al centro
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                // Applicazione del renderizzatore personalizzato a tutte le colonne
                int columnCount = tabellaCodiciLab.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    tabellaCodiciLab.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                // Aggiungi la tabella a un componente di scorrimento
                JScrollPane scrollPane = new JScrollPane(tabellaCodiciLab);
                // Aggiungi il componente di scorrimento alla finestra di dialogo
                dialog.add(scrollPane);

                // Quando l'utente tocca un codice, parte questo Listener
                tabellaCodiciLab.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        // Ottieni il codice selezionato
                        int selectedRow = tabellaCodiciLab.getSelectedRow();
                        String codLabSelezionato = (String) tabellaCodiciLab.getValueAt(selectedRow, 0);
                        
                        // L utente ha selezionato una colonna
                        int response = JOptionPane.showOptionDialog(dialog, "Aggiungo alla matricola " + matricolaSelezionata + " l'afferenza al laboratorio " + codLabSelezionato + "?", "Conferma Salvataggio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
                        if (response == JOptionPane.YES_OPTION) {
                            //aggiungo l'afferenza al codLabSelezionato
                            try {
                                controller.aggiungiAfferenza(matricolaSelezionata,codLabSelezionato);
                            } catch (PSQLException ex) {
                                JOptionPane.showMessageDialog(null, "Errore durante l'aggiunta dell'afferenza al laboratorio:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception ee) {
                                JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                            } finally {
                                dialog.dispose();
                            }
                            updateTabella(controller,new String[]{"ID"},matricolaSelezionata);
                        }
                    }
                });
                
                // Imposta le dimensioni e la posizione della finestra di dialogo
                dialog.setSize(250, 400);
                dialog.setLocationRelativeTo(null); // Posiziona la finestra di dialogo al centro dello schermo
                dialog.setVisible(true);
            }
        });



        // Logica per rimuovere afferenza
        bottoneRimuoviAfferenza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });





        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);
        add(panelBottoni, BorderLayout.SOUTH);


        // Rendo non modificabili alcuni campi
        matricolaField.setEditable(false);
        nomeField.setEditable(false);
        cognomeField.setEditable(false);
        codiceFiscaleField.setEditable(false);
        sessoField.setEditable(false);
        tipoImpiegatoField.setEditable(false);
        dataAssunzioneChooser.setEditable(false);


        // Imposto la dimensione della finestra e la rendo visibile
        setSize(800, 600);
        setLocationRelativeTo(null);
        //disattivo la finestra padre
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void updateTabella(Controller controller,String[] colonneTabella,String matricolaSelezionata) {
        //LOAD DEI NUOVI DATI
        ArrayList<String> listaLabAfferiti = controller.leggiAfferenzeImpiegato(matricolaSelezionata);
        Object[][] nuoviDati = new Object[listaLabAfferiti.size()][3];
        for (int i = 0; i < listaLabAfferiti.size(); i++) {
            nuoviDati[i][0] = listaLabAfferiti.get(i);
        }

        //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
        DefaultTableModel model = (DefaultTableModel) tabellaAfferenze.getModel();
        model.setDataVector(nuoviDati, colonneTabella);
    }


}