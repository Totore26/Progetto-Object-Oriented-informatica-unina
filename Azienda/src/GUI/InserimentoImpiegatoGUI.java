package GUI;

import com.toedter.calendar.JDateChooser;
import CONTROLLER.*;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The type Inserimento impiegato gui.
 */
public class InserimentoImpiegatoGUI extends JDialog {
    private final JTextField matricolaField;
    private final JTextField nomeField;
    private final JTextField cognomeField;
    private final JTextField codiceFiscaleField;
    private final JRadioButton maschioRadioButton;
    private final JRadioButton femminaRadioButton;
    private final JTextArea curriculumTextArea;
    private final JCheckBox dirigenteCheckBox;
    private final JComboBox<String> tipoImpiegatoComboBox;
    private final JSpinner stipendioSpinner;
    private final JDateChooser dataAssunzioneChooser;
    private final JDateChooser dataLicenziamentoChooser;

    /**
     * Instantiates a new Inserimento impiegato gui.
     *
     * @param controller     the controller
     * @param nuovaMatricola the nuova matricola
     * @param framePadre     the frame padre
     */
    public InserimentoImpiegatoGUI(Controller controller,String nuovaMatricola, JFrame framePadre) {

        // Creiamo un pannello per contenere i campi d'ingresso
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));
        setTitle("Inserimento Impiegato");
        // Aggiungiamo il campo "Matricola"
        inputPanel.add(new JLabel("Matricola:"));
        matricolaField = new JTextField();
        matricolaField.setText(nuovaMatricola);
        matricolaField.setEditable(false);
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


        //BOTTONI


        // Implementazione bottone Salva
        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> {
            setVisible(false);

            //controllo che non ci siano dei campi vuoti
            if (matricolaField.getText().isEmpty() || nomeField.getText().isEmpty() ||
                    cognomeField.getText().isEmpty() || codiceFiscaleField.getText().isEmpty() ||
                    curriculumTextArea.getText().isEmpty() || tipoImpiegatoComboBox.getSelectedItem() == null ||
                    dataAssunzioneChooser.getDate() == null || (Double) stipendioSpinner.getValue() == 0.0 ||
                    sessoGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(null, "La data di licenziamento è opzionale, inserisci il resto dei dati per continuare", "Attenzione", JOptionPane.WARNING_MESSAGE);
                setVisible(true);
            } else {
                //Tutti i campi sono stati inseriti
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
                    JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                } catch (PSQLException ex) {
                    JOptionPane.showMessageDialog(null, "Errore durante il salvataggio dei dati dell'impiegato:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } finally {
                    dispose();
                    framePadre.setVisible(true);
                }
            }
        });


        // Implementazione bottone Annulla
        JButton bottoneAnnulla = new JButton("Annulla");
        bottoneAnnulla.addActionListener(e -> {
            dispose();
            framePadre.setVisible(true);
        });


        // Creiamo un pannello per contenere i bottoni "Salva" e "Annulla"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(bottoneSalva);
        buttonPanel.add(bottoneAnnulla);


        // Aggiungiamo i pannelli alla finestra
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Impostiamo le dimensioni della finestra
        setSize(800, 600);
        setLocationRelativeTo(null);

        //disattivo la finestra padre
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //listener per mostrare la finestra padre quando viene chiusa la finestra di dialogo
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                framePadre.setVisible(true);
            }
        });
        //mostriamo la finestra
        setVisible(true);
    }

}