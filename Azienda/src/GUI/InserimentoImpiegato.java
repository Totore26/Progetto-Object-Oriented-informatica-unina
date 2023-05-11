package GUI;

import com.toedter.calendar.JDateChooser;
import CONTROLLER.*;
import org.postgresql.util.PSQLException;

import javax.lang.model.type.NullType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

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
    private JSpinner stipendioSpinner;
    private JDateChooser dataAssunzioneChooser;
    private JDateChooser dataLicenziamentoChooser;
    private String[] colonneTabella = {"Matricola", "Nome", "Cognome"};


    public InserimentoImpiegato(Controller controller) {


        // Creiamo un pannello per contenere i campi d'input
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        setTitle("Inserimento Impigato");
        // Aggiungiamo il campo "Matricola"
        inputPanel.add(new JLabel("Matricola:"));
        matricolaField = new JTextField();
        //TODO devo fare in modo che nel campo matricola ci sia gia la prima matricola disponibile
        //-->
        //faccio in modo che il campo non sia modificabile
        //matricolaField.setEditable(false);
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
        stipendioSpinner = new JSpinner(new SpinnerNumberModel(0.0f, 0.0f, Double.MAX_VALUE, 100.0));
        inputPanel.add(stipendioSpinner);

        // Aggiungiamo il campo "Data Assunzione"
        inputPanel.add(new JLabel("Data Assunzione:"));
        dataAssunzioneChooser = new JDateChooser();
        dataAssunzioneChooser.setDateFormatString("yyyy-mm-dd");
        inputPanel.add(dataAssunzioneChooser);

        // Aggiungiamo il campo "Data Licenziamento"
        inputPanel.add(new JLabel("Data Licenziamento:"));
        dataLicenziamentoChooser = new JDateChooser();
        dataLicenziamentoChooser.setDateFormatString("yyyy-mm-dd");
        inputPanel.add(dataLicenziamentoChooser);

        // Aggiungiamo il campo "Dirigente"
        dirigenteCheckBox = new JCheckBox("Dirigente");
        inputPanel.add(dirigenteCheckBox);



        // Creiamo un pannello per contenere i bottoni "Salva" e "Annulla"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Implementazione bottone Salva
        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String matricolaRegExp = "^MAT-[0-9]{3}$";
                Pattern patternMatricola = Pattern.compile(matricolaRegExp);

                //controllo che non ci siano dei campi vuoti
                if (matricolaField.getText().isEmpty() || nomeField.getText().isEmpty() ||
                        cognomeField.getText().isEmpty() || codiceFiscaleField.getText().isEmpty() ||
                        curriculumTextArea.getText().isEmpty() || tipoImpiegatoComboBox.getSelectedItem() == null ||
                        dataAssunzioneChooser.getDate() == null || (Double) stipendioSpinner.getValue() == 0.0 ||
                        sessoGroup.getSelection() == null) {
                    JOptionPane.showMessageDialog(null, "La data di licenziamento è opzionale, inserisci il resto dei dati per continuare", "Attenzione", JOptionPane.WARNING_MESSAGE);
                } else {
                    String matricola = matricolaField.getText();
                    String nome = nomeField.getText();
                    String cognome = cognomeField.getText();
                    String codiceFiscale = codiceFiscaleField.getText();
                    String curriculum = curriculumTextArea.getText();
                    String tipoImpiegato = (String) tipoImpiegatoComboBox.getSelectedItem();
                    boolean dirigente = dirigenteCheckBox.isSelected();
                    float stipendio = ((Number) stipendioSpinner.getValue()).floatValue();
                    //prendo la data in formato Date
                    java.util.Date dataAssunzione = dataAssunzioneChooser.getDate();
                    java.util.Date dataLicenziamento = dataLicenziamentoChooser.getDate();
                    //converto la data in formato sql e aggiorno la variabile nel caso sia != null
                    java.sql.Date sqlDataAssunzione = new java.sql.Date(dataAssunzione.getTime());
                    java.sql.Date sqlDataLicenziamento = null;
                    if(dataLicenziamentoChooser.getDate() != null) {
                        sqlDataLicenziamento = new java.sql.Date(dataLicenziamento.getTime());
                    }
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

                    try {
                        controller.aggiungiImpiegato(matricola, nome, cognome, codiceFiscale, curriculum, tipoImpiegato, dirigente, sqlDataAssunzione, sqlDataLicenziamento, stipendio, sesso);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante il salvataggio dei dati dell'impiegato:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        dispose();
                    }
                }
            }
        });


        // Implementazione bottone Annulla
        JButton bottoneAnnulla = new JButton("Annulla");
        bottoneAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });


        buttonPanel.add(bottoneSalva);
        buttonPanel.add(bottoneAnnulla);


        // Aggiungiamo i pannelli alla finestra
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Impostiamo le dimensioni della finestra
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Impostiamo la posizione della finestra al centro della finestra padre
        setLocationRelativeTo(null);
    }

}