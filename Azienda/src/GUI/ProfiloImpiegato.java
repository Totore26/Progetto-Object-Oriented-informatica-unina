package GUI;

import java.awt.*;
import java.awt.event.*;
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

        // Inizializza i componenti
        matricolaField = new JTextField(10);
        nomeField = new JTextField(10);
        cognomeField = new JTextField(10);
        codiceFiscaleField = new JTextField(10);
        sessoField = new JTextField(10);
        curriculumTextArea = new JTextArea(5, 20);
        dirigenteCheckBox = new JCheckBox("Dirigente");
        tipoImpiegatoComboBox = new JTextField(10);
        stipendioSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 100));
        dataAssunzioneChooser = new JTextField(10);
        dataLicenziamentoChooser = new JDateChooser();
        tabellaStorico = new JTable(new DefaultTableModel(new String[]{"Colonna1", "Colonna2"}, 0));
        tabellaAfferenza = new JTable(new DefaultTableModel(new String[]{"Colonna1", "Colonna2"}, 0));

        // Rendi non modificabili alcuni campi
        nomeField.setEditable(false);
        cognomeField.setEditable(false);
        codiceFiscaleField.setEditable(false);
        sessoField.setEditable(false);
        tipoImpiegatoComboBox.setEditable(false);
        dataAssunzioneChooser.setEditable(false);

        // Aggiungi componenti al pannello
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 5, 5, 5);

        // Aggiungi i campi al pannello principale
        // Ad esempio, aggiungi il campo matricola
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(new JLabel("Matricola:"), c);
        c.gridx = 1;
        mainPanel.add(matricolaField, c);

        // Aggiungi gli altri campi allo stesso modo

        // Aggiungi il pannello principale al dialogo
        add(mainPanel, BorderLayout.CENTER);



        

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
                setVisible(false);
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

        // Imposta la dimensione del dialogo e lo rende visibile
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}