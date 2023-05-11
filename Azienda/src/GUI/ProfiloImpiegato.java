package GUI;

import java.awt.*;
import java.awt.event.*;
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
    private JTextField tipoImpiegatoComboBox;
    private JSpinner stipendioSpinner;
    private JTextField dataAssunzioneChooser;
    private JDateChooser dataLicenziamentoChooser;
    private JTable tabellaStorico;
    private JTable tabellaAfferenza;

    public ProfiloImpiegato(String matricolaSelezionata, Controller controller) {
        setTitle("Profilo Impiegato");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());



        // Creiamo il pannello principale
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Creiamo il pannello per i dati anagrafici
        JPanel datiAnagraficiPanel = new JPanel(new GridLayout(0, 2));
        datiAnagraficiPanel.setBorder(BorderFactory.createTitledBorder("Dati anagrafici"));

        // Matricola
        JLabel matricolaLabel = new JLabel("Matricola:", SwingConstants.LEFT);
        matricolaField = new JTextField();
        datiAnagraficiPanel.add(matricolaLabel);
        datiAnagraficiPanel.add(matricolaField);

        // Nome
        JLabel nomeLabel = new JLabel("Nome:", SwingConstants.LEFT);
        nomeField = new JTextField();
        datiAnagraficiPanel.add(nomeLabel);
        datiAnagraficiPanel.add(nomeField);

        // Cognome
        JLabel cognomeLabel = new JLabel("Cognome:", SwingConstants.LEFT);
        cognomeField = new JTextField();
        datiAnagraficiPanel.add(cognomeLabel);
        datiAnagraficiPanel.add(cognomeField);

        // Codice fiscale
        JLabel codiceFiscaleLabel = new JLabel("Codice fiscale:", SwingConstants.LEFT);
        codiceFiscaleField = new JTextField();
        datiAnagraficiPanel.add(codiceFiscaleLabel);
        datiAnagraficiPanel.add(codiceFiscaleField);

        // Sesso
        JLabel sessoLabel = new JLabel("Sesso:", SwingConstants.LEFT);
        sessoField = new JTextField();
        datiAnagraficiPanel.add(sessoLabel);
        datiAnagraficiPanel.add(sessoField);

        // Tipo impiegato
        JLabel tipoImpiegatoLabel = new JLabel("Tipo impiegato:", SwingConstants.LEFT);
        tipoImpiegatoComboBox = new JTextField();
        datiAnagraficiPanel.add(tipoImpiegatoLabel);
        datiAnagraficiPanel.add(tipoImpiegatoComboBox);

        // Stipendio
        JLabel stipendioLabel = new JLabel("Stipendio:", SwingConstants.LEFT);
        SpinnerNumberModel stipendioModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 100.0);
        stipendioSpinner = new JSpinner(stipendioModel);
        JFormattedTextField stipendioText = ((JSpinner.NumberEditor) stipendioSpinner.getEditor()).getTextField();
        stipendioText.setColumns(10);
        datiAnagraficiPanel.add(stipendioLabel);
        datiAnagraficiPanel.add(stipendioSpinner);

        // Data assunzione
        JLabel dataAssunzioneLabel = new JLabel("Data assunzione:", SwingConstants.LEFT);
        dataAssunzioneChooser = new JFormattedTextField("gg/mm/aaaa");
        datiAnagraficiPanel.add(dataAssunzioneLabel);
        datiAnagraficiPanel.add(dataAssunzioneChooser);

        // Data licenziamento
        JLabel dataLicenziamentoLabel = new JLabel("Data licenziamento:", SwingConstants.LEFT);
        dataLicenziamentoChooser = new JDateChooser();
        datiAnagraficiPanel.add(dataLicenziamentoLabel);
        datiAnagraficiPanel.add(dataLicenziamentoChooser);

        // Dirigente
        JLabel dirigenteLabel = new JLabel("Dirigente:", SwingConstants.LEFT);
        dirigenteCheckBox = new JCheckBox();
        datiAnagraficiPanel.add(dirigenteLabel);
        datiAnagraficiPanel.add(dirigenteCheckBox);

        // Curriculum
        JLabel curriculumLabel = new JLabel("Curriculum:", SwingConstants.LEFT);
        curriculumTextArea = new JTextArea(10, 20);
        JScrollPane scrollPane = new JScrollPane(curriculumTextArea);
        datiAnagraficiPanel.add(curriculumLabel);
        datiAnagraficiPanel.add(scrollPane);
        
        
        panel.add(datiAnagraficiPanel);

        // Creiamo il pannello per la tabella di afferenza
        JPanel tabellaAfferenzaPanel = new JPanel(new BorderLayout());
        tabellaAfferenzaPanel.setBorder(BorderFactory.createTitledBorder("Tabella di afferenza"));

        // Tabella di afferenza
        DefaultTableModel tabellaAfferenzaModel = new DefaultTableModel();
        tabellaAfferenzaModel.addColumn("ID");
        tabellaAfferenzaModel.addColumn("Nome");
        tabellaAfferenzaModel.addColumn("Cognome");
        tabellaAfferenzaModel.addRow(new Object[]{1, "Mario", "Rossi"});
        tabellaAfferenzaModel.addRow(new Object[]{2, "Luigi", "Verdi"});
        tabellaAfferenza = new JTable(tabellaAfferenzaModel);
        tabellaAfferenzaPanel.add(new JScrollPane(tabellaAfferenza), BorderLayout.CENTER);

        panel.add(tabellaAfferenzaPanel);

        // Creiamo il pannello per la tabella dello storico
        JPanel tabellaStoricoPanel = new JPanel(new BorderLayout());
        tabellaStoricoPanel.setBorder(BorderFactory.createTitledBorder("Tabella storico"));

        // Tabella dello storico
        DefaultTableModel tabellaStoricoModel = new DefaultTableModel();
        tabellaStoricoModel.addColumn("Data");
        tabellaStoricoModel.addColumn("Evento");
        tabellaStoricoModel.addRow(new Object[]{new Date(), "Assunzione"});
        tabellaStoricoModel.addRow(new Object[]{new Date(), "Promozione"});
        tabellaStorico = new JTable(tabellaStoricoModel);
        tabellaStoricoPanel.add(new JScrollPane(tabellaStorico), BorderLayout.CENTER);

        panel.add(tabellaStoricoPanel);

        // Aggiungiamo il pannello alla finestra
        add(panel);


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

        // Aggiungi il pannello dei bottoni al dialogo
        add(panelBottoni, BorderLayout.SOUTH);

        // Rendi non modificabili alcuni campi
        matricolaField.setEditable(false);
        nomeField.setEditable(false);
        cognomeField.setEditable(false);
        codiceFiscaleField.setEditable(false);
        sessoField.setEditable(false);
        tipoImpiegatoComboBox.setEditable(false);
        dataAssunzioneChooser.setEditable(false);

        // Imposta la dimensione del dialogo e lo rende visibile
        setSize(800, 600);
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
    }
}