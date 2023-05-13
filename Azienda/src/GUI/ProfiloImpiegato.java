package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;

import CONTROLLER.Controller;
import com.toedter.calendar.JDateChooser;

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
    private JTable tabellaAfferenza;
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
        JPanel datiAnagraficiPanel = new JPanel(new GridLayout(0, 2));
        datiAnagraficiPanel.setBorder(BorderFactory.createTitledBorder("Dati anagrafici"));

        // Creiamo il pannello sinistro per i dati anagrafici
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Matricola
        JLabel matricolaLabel = new JLabel("MATRICOLA:", SwingConstants.CENTER);
        matricolaLabel.setFont(matricolaLabel.getFont().deriveFont(Font.BOLD, 15));
        matricolaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        matricolaField = new JTextField();
        matricolaField.setText(matricolaSelezionata);
        datiAnagraficiPanel.add(matricolaLabel);
        datiAnagraficiPanel.add(matricolaField);

        // Nome
        JLabel nomeLabel = new JLabel("NOME:", SwingConstants.CENTER);
        nomeLabel.setFont(nomeLabel.getFont().deriveFont(Font.BOLD, 15));
        nomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nomeField = new JTextField();
        nomeField.setText(nomeSelezionato);
        datiAnagraficiPanel.add(nomeLabel);
        datiAnagraficiPanel.add(nomeField);

        // Cognome
        JLabel cognomeLabel = new JLabel("COGNOME:", SwingConstants.CENTER);
        cognomeLabel.setFont(cognomeLabel.getFont().deriveFont(Font.BOLD, 15));
        cognomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cognomeField = new JTextField();
        cognomeField.setText(cognomeSelezionato);
        datiAnagraficiPanel.add(cognomeLabel);
        datiAnagraficiPanel.add(cognomeField);

        // Codice fiscale
        JLabel codiceFiscaleLabel = new JLabel("CODICE FISCALE", SwingConstants.CENTER);
        codiceFiscaleLabel.setFont(codiceFiscaleLabel.getFont().deriveFont(Font.BOLD, 15));
        codiceFiscaleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        codiceFiscaleField = new JTextField();
        codiceFiscaleField.setText(codiceFiscaleSelezionato);
        datiAnagraficiPanel.add(codiceFiscaleLabel);
        datiAnagraficiPanel.add(codiceFiscaleField);

        // Sesso
        JLabel sessoLabel = new JLabel("SESSO:", SwingConstants.CENTER);
        sessoLabel.setFont(sessoLabel.getFont().deriveFont(Font.BOLD, 15));
        sessoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        sessoField = new JTextField();
        sessoField.setText(sessoSelezionato);
        datiAnagraficiPanel.add(sessoLabel);
        datiAnagraficiPanel.add(sessoField);

        // Tipo impiegato
        JLabel tipoImpiegatoLabel = new JLabel("TIPO IMPIEGATO:", SwingConstants.CENTER);
        tipoImpiegatoLabel.setFont(tipoImpiegatoLabel.getFont().deriveFont(Font.BOLD, 15));
        tipoImpiegatoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        tipoImpiegatoField = new JTextField();
        tipoImpiegatoField.setText(tipoImpiegatoSelezionato);
        datiAnagraficiPanel.add(tipoImpiegatoLabel);
        datiAnagraficiPanel.add(tipoImpiegatoField);

        // Stipendio
        JLabel stipendioLabel = new JLabel("STIPENDIO:", SwingConstants.CENTER);
        stipendioLabel.setFont(stipendioLabel.getFont().deriveFont(Font.BOLD, 15));
        stipendioLabel.setHorizontalAlignment(SwingConstants.LEFT);
        SpinnerNumberModel stipendioModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 100.0);
        stipendioSpinner = new JSpinner(stipendioModel);
        JFormattedTextField stipendioText = ((JSpinner.NumberEditor) stipendioSpinner.getEditor()).getTextField();
        stipendioText.setColumns(10);
        stipendioSpinner.setValue(stipendioSelezionato);
        datiAnagraficiPanel.add(stipendioLabel);
        datiAnagraficiPanel.add(stipendioSpinner);

        // Data assunzione
        JLabel dataAssunzioneLabel = new JLabel("DATA ASSUNZIONE:", SwingConstants.CENTER);
        dataAssunzioneLabel.setFont(dataAssunzioneLabel.getFont().deriveFont(Font.BOLD, 15));
        dataAssunzioneLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dataAssunzioneChooser = new JFormattedTextField();
        dataAssunzioneChooser.setText(dataAssunzioneSelezionata.toString());
        datiAnagraficiPanel.add(dataAssunzioneLabel);
        datiAnagraficiPanel.add(dataAssunzioneChooser);

        // Data licenziamento
        JLabel dataLicenziamentoLabel = new JLabel("DATA LICENZIAMENTO:", SwingConstants.LEFT);
        dataLicenziamentoLabel.setFont(dataLicenziamentoLabel.getFont().deriveFont(Font.BOLD, 15));
        dataLicenziamentoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dataLicenziamentoChooser = new JDateChooser();
        dataLicenziamentoChooser.setDateFormatString("yyyy-mm-dd");
        if(dataLicenziamentoSelezionata != null){
            dataLicenziamentoChooser.setDate(dataLicenziamentoSelezionata);
        }
        datiAnagraficiPanel.add(dataLicenziamentoLabel);
        datiAnagraficiPanel.add(dataLicenziamentoChooser);


        // Dirigente
        JLabel dirigenteLabel = new JLabel("DIRIGENTE:", SwingConstants.LEFT);
        dirigenteLabel.setFont(dirigenteLabel.getFont().deriveFont(Font.BOLD, 15));
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
        JLabel curriculumLabel = new JLabel("CURRICULUM:", SwingConstants.CENTER);
        curriculumLabel.setFont(curriculumLabel.getFont().deriveFont(Font.BOLD, 15));
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
        ArrayList<String> listaLab = controller.leggiAfferenzeImpiegato(matricolaSelezionata);
        DefaultTableModel tabellaAfferenzaModel = new DefaultTableModel();
        tabellaAfferenzaModel.addColumn("ID");
        Object[][] data = new Object[listaLab.size()][1];
        for (int i = 0; i < listaLab.size(); i++) {
            data[i][0] = listaLab.get(i);
        }
        tabellaAfferenzaModel.setDataVector(data, new Object[]{"ID"});
        tabellaAfferenza = new JTable(tabellaAfferenzaModel);
        tabellaAfferenza.setShowGrid(true);

        //barra di scorrimento
        scrollPane = new JScrollPane(tabellaAfferenza);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        rightPanel.add(new JScrollPane(tabellaAfferenza), BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(150,700));
        tabellaAfferenza.getTableHeader().setReorderingAllowed(false);


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


        // Logica per salvare le modifiche//
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
        setSize(1000, 800);
        setLocationRelativeTo(null);
        //disattivo la finestra padre
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }



}