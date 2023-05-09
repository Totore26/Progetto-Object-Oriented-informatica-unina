package GUI;

import MODEL.Impiegato;
import CONTROLLER.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class InserimentoImpiegato extends JDialog {
    private JTextField matricolaField;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceFiscaleField;
    private JRadioButton maschioRadioButton;
    private JRadioButton femminaRadioButton;
    private JTextArea curriculumTextArea;
    private JCheckBox dirigenteCheckBox;
    private JComboBox<String> tipoImpiegatoComboBox;
    private JSpinner dataAssunzioneSpinner;
    private JSpinner dataLicenziamentoSpinner;
    private JSpinner stipendioSpinner;

    public InserimentoImpiegato(JFrame framePadre,Controller controller) {
        super(framePadre, "Inserimento Impiegato", true);

        // Creiamo un pannello per contenere i campi d'input
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Aggiungiamo il campo "Matricola"
        inputPanel.add(new JLabel("Matricola:"));
        matricolaField = new JTextField();
        inputPanel.add(matricolaField);

        // Aggiungiamo il campo "Nome"
        inputPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        inputPanel.add(nomeField);

        // Aggiungiamo il campo "Cognome"
        inputPanel.add(new JLabel("Cognome:"));
        cognomeField = new JTextField();
        inputPanel.add(cognomeField);

        // Aggiungiamo il campo "Codice Fiscale"
        inputPanel.add(new JLabel("Codice Fiscale:"));
        codiceFiscaleField = new JTextField();
        inputPanel.add(codiceFiscaleField);

        // Aggiungiamo il campo "Sesso"
        inputPanel.add(new JLabel("Sesso:"));
        JPanel sessoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maschioRadioButton = new JRadioButton("M");
        femminaRadioButton = new JRadioButton("F");
        ButtonGroup sessoGroup = new ButtonGroup();
        sessoGroup.add(maschioRadioButton);
        sessoGroup.add(femminaRadioButton);
        sessoPanel.add(maschioRadioButton);
        sessoPanel.add(femminaRadioButton);
        inputPanel.add(sessoPanel);

        // Aggiungiamo il campo "Curriculum"
        inputPanel.add(new JLabel("Curriculum:"));
        curriculumTextArea = new JTextArea(5, 20);
        curriculumTextArea.setLineWrap(true);
        inputPanel.add(new JScrollPane(curriculumTextArea));

        // Aggiungiamo il campo "Tipo Impiegato"
        inputPanel.add(new JLabel("Tipo Impiegato:"));
        tipoImpiegatoComboBox = new JComboBox<>();
        tipoImpiegatoComboBox.addItem("junior");
        tipoImpiegatoComboBox.addItem("middle");
        tipoImpiegatoComboBox.addItem("senior");
        inputPanel.add(tipoImpiegatoComboBox);

        // Aggiungiamo il campo "Stipendio"
        inputPanel.add(new JLabel("Stipendio:"));
        stipendioSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 100.0));
        inputPanel.add(stipendioSpinner);

        // Aggiungiamo il campo "Data Assunzione"
        inputPanel.add(new JLabel("Data Assunzione:"));
        dataLicenziamentoSpinner = new JSpinner(new SpinnerDateModel());
        inputPanel.add(dataLicenziamentoSpinner);
        // Aggiungiamo il campo "Data Licenziamento"
        inputPanel.add(new JLabel("Data Licenziamento:"));
        dataLicenziamentoSpinner = new JSpinner(new SpinnerDateModel());
        inputPanel.add(dataLicenziamentoSpinner);

        // Aggiungiamo il campo "Dirigente"
        dirigenteCheckBox = new JCheckBox("Dirigente");
        inputPanel.add(dirigenteCheckBox);



        // Creiamo un pannello per contenere i bottoni "OK" e "Annulla"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Implementazione bottone Salva
        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String matricola = matricolaField.getText();
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String codiceFiscale = codiceFiscaleField.getText();
                String curriculum = curriculumTextArea.getText();
                String tipoImpiegato = (String) tipoImpiegatoComboBox.getSelectedItem();
                boolean dirigente = dirigenteCheckBox.isSelected();
                String dataAssunzione = (String) dataAssunzioneSpinner.getValue();
                String dataLicenziamento = (String) dataAssunzioneSpinner.getValue();
                float stipendio = (float) stipendioSpinner.getValue();
                // Recupero il sesso selezionato
                String sesso;
                ButtonModel selectedSesso = sessoGroup.getSelection();
                if (selectedSesso == maschioRadioButton.getModel()) {
                    sesso = "M";
                } else if (selectedSesso == femminaRadioButton.getModel()) {
                    sesso = "F";
                } else {
                    sesso = null; // Nessun sesso selezionato
                }
                Impiegato imp = new Impiegato(matricola,nome,cognome,codiceFiscale,curriculum,dirigente,tipoImpiegato,dataAssunzione,dataLicenziamento,stipendio,sesso);

                //dopo aver salvato in modo adeguato l'impiegato, controllo che l'inserimento vada a buon fine...
                controller.InserimentoImpiegato(imp);
                setVisible(false);
            }
        });
        buttonPanel.add(bottoneSalva);

        // Implementazione bottone Annulla
        JButton bottoneAnnulla = new JButton("Annulla");
        bottoneAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        buttonPanel.add(bottoneAnnulla);


        // Aggiungiamo i pannelli alla finestra
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Impostiamo le dimensioni della finestra
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Impostiamo la posizione della finestra al centro della finestra padre
        setLocationRelativeTo(framePadre);
    }






}