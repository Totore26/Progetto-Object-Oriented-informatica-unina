package GUI;

import CONTROLLER.Controller;
import com.toedter.calendar.JDateChooser;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class InserimentoLaboratorioGUI extends JDialog{

    private JTextField idLabField;
    private JTextField topicField;
    private JTextField indirizzoField;
    private JTextField numeroTelefonicoField;
    private JComboBox<String> rScientificoComboBox;
    public InserimentoLaboratorioGUI(Controller controller,String nuovoId, JFrame framePadre) {

        // Creiamo un pannello per contenere i campi d'input
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 10, 100));
        setTitle("Inserimento Laboratorio");


        // Aggiungiamo il campo "idLab"
        inputPanel.add(new JLabel("Id Laboratorio:"));
        idLabField = new JTextField();
        idLabField.setText(nuovoId);
        idLabField.setEditable(false);
        inputPanel.add(idLabField);

        // Aggiungiamo il campo "Topic"
        inputPanel.add(new JLabel("Topic Laboratorio:"));
        topicField = new JTextField();
        inputPanel.add(topicField);

        // Aggiungiamo il campo "Indirizzo"
        inputPanel.add(new JLabel("Indirizzo:"));
        indirizzoField = new JTextField();
        inputPanel.add(indirizzoField);

        // Aggiungiamo il campo "Numero Telefonico"
        inputPanel.add(new JLabel("Numero Telefonico:"));
        numeroTelefonicoField = new JTextField();
        inputPanel.add(numeroTelefonicoField);

        // Aggiungiamo il campo "rScientifici"
        inputPanel.add(new JLabel("Responsabile Scientifico:"));
        rScientificoComboBox = new JComboBox<>();
        ArrayList<String> rScientificiDisponibili= controller.getListaResponsabiliScientificiDisponibiliGUI();
        for(String s : rScientificiDisponibili)
            rScientificoComboBox.addItem(s);

        inputPanel.add(new JScrollPane(rScientificoComboBox));



        //BOTTONI


        // Implementazione bottone Salva
        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);

                //controllo che non ci siano dei campi vuoti
                if (idLabField.getText().isEmpty() || topicField.getText().isEmpty() ||
                        numeroTelefonicoField.getText().isEmpty() || indirizzoField.getText().isEmpty() ||
                        rScientificoComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Inserisci il resto dei dati per continuare", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    setVisible(true);
                } else {
                    //Tutti i campi sono stati inseriti
                    String idLab = idLabField.getText();
                    String topic = topicField.getText();
                    String indirizzo = indirizzoField.getText();
                    String numeroTelefono = numeroTelefonicoField.getText();
                    String rScientifico = (String) rScientificoComboBox.getSelectedItem();

                    try {
                        controller.aggiungiLaboratorio(idLab,topic,indirizzo,numeroTelefono,rScientifico);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante il salvataggio dei dati del laboratorio:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        dispose();
                        framePadre.setVisible(true);
                    }
                }
            }
        });


        // Implementazione bottone Annulla
        JButton bottoneAnnulla = new JButton("Annulla");
        bottoneAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                framePadre.setVisible(true);
            }
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
