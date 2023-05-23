package GUI;

import CONTROLLER.Controller;
import com.toedter.calendar.JDateChooser;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class InserimentoProgettoGUI extends JDialog {
    private final JTextField cupField;
    private final JTextField nomeField;
    private final JSpinner budgetSpinner;
    private final JComboBox<String> responsabileComboBox;
    private final JComboBox<String> referenteComboBox;
    private final JDateChooser dataInizioChooser;
    private final JDateChooser dataFineChooser;

    public InserimentoProgettoGUI(Controller controller, String cupGenerato, JFrame framePadre){

        // Creiamo un pannello per contenere i campi d'ingresso
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));
        setTitle("Inserimento Impiegato");

        // Aggiungiamo il campo "Cup"
        inputPanel.add(new JLabel("Cup:"));
        cupField = new JTextField();
        cupField.setEditable(false);
        cupField.setText(cupGenerato);
        inputPanel.add(cupField);

        // Aggiungiamo il campo "Nome"
        inputPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        inputPanel.add(nomeField);

        // Aggiungiamo il campo "Bilancio"
        inputPanel.add(new JLabel("Budget:"));
        budgetSpinner = new JSpinner(new SpinnerNumberModel(0.0f, 0.0f, Double.MAX_VALUE, 100.0));
        inputPanel.add(budgetSpinner);

        // Aggiungiamo il campo "Responsabile"
        inputPanel.add(new JLabel("Responsabile:"));
        responsabileComboBox = new JComboBox<>();
        ArrayList<String> respScientList= controller.getListaResponsabiliScientificiDisponibiliGUI();
        for(String i : respScientList)
            responsabileComboBox.addItem(i);
        inputPanel.add(responsabileComboBox);

        // Aggiungiamo il campo "Referente"
        inputPanel.add(new JLabel("Referente:"));
        referenteComboBox = new JComboBox<>();
        ArrayList<String> refList = null; //TODO RIEMPIRE QUESTO ARRAYLIST CON I REFERENTI DISPONIBILI
        for(String i : refList)
            referenteComboBox.addItem(i);
        inputPanel.add(referenteComboBox);

        // Aggiungiamo il campo "Data Inizio"
        inputPanel.add(new JLabel("Data Inizio:"));
        dataInizioChooser = new JDateChooser();
        dataInizioChooser.setDateFormatString("yyyy-mm-dd");
        inputPanel.add(dataInizioChooser);

        // Aggiungiamo il campo "Data Fine"
        inputPanel.add(new JLabel("Data Fine:"));
        dataFineChooser = new JDateChooser();
        dataFineChooser.setDateFormatString("yyyy-mm-dd");
        inputPanel.add(dataFineChooser);

        //BOTTONI


        // Implementazione bottone Salva
        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> {
            setVisible(false);

            //controllo che non ci siano dei campi vuoti
            if (nomeField.getText().isEmpty() || cupField.getText().isEmpty() || referenteComboBox.getSelectedItem() == null ||
                    responsabileComboBox.getSelectedItem() == null || dataInizioChooser.getDate() == null ||
                    (Double) budgetSpinner.getValue() == 0.0 ) {
                JOptionPane.showMessageDialog(null, "La data di fine è opzionale, inserisci il resto dei dati per continuare", "Attenzione", JOptionPane.WARNING_MESSAGE);
                setVisible(true);
            } else {
                //Tutti i campi sono stati inseriti
                String cup = cupField.getText();
                String nome = nomeField.getText();
                String referente = (String) referenteComboBox.getSelectedItem();
                String responsabile = (String) responsabileComboBox.getSelectedItem();
                float budget = ((Number) budgetSpinner.getValue()).floatValue();
                //prendo la data in formato Date
                java.util.Date dataInizio = dataInizioChooser.getDate();
                java.util.Date dataFine = dataFineChooser.getDate();
                //converto la data in formato sql e aggiorno la variabile nel caso sia != null
                java.sql.Date sqlDataInizio = new java.sql.Date(dataInizio.getTime());
                java.sql.Date sqlDataFine = null;
                if(dataFineChooser.getDate() != null) {
                    sqlDataFine = new java.sql.Date(dataFine.getTime());
                }

                try {
                    controller.aggiungiProgetto(nome,cup,budget,sqlDataInizio,sqlDataFine,responsabile,referente);
                    JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                } catch (PSQLException ex) {
                    JOptionPane.showMessageDialog(null, "Errore durante il salvataggio dei dati del Progetto:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
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