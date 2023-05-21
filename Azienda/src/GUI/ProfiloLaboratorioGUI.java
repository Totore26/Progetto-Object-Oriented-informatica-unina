package GUI;

import CONTROLLER.Controller;
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

public class ProfiloLaboratorioGUI extends JDialog{
    private final JTable tabellaAfferenze;
    private final JTextField indirizzoField;
    private final JTextField numeroTelefonicoField;
    private final JComboBox<String> rScientificoComboBox;

    public ProfiloLaboratorioGUI(String idLabSelezionato, Controller controller, JFrame framePadre) throws SQLException {
        setTitle("Profilo Laboratorio");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creiamo le variabili per ricevere dal controller i dati del laboratorio
        String topicSelezionato = controller.getSingoloLaboratorioTopicGUI(idLabSelezionato);
        String indirizzoSelezionato = controller.getSingoloIndirizzoGUI(idLabSelezionato);
        String numeroTelefonicoSelezionato = controller.getSingoloNumeroTelefonicoGUI(idLabSelezionato);
        String rScientificoSelezionato = controller.getSingoloRefScientificoGUI(idLabSelezionato);


        // Creiamo il pannello principale
        JPanel panel = new JPanel(new BorderLayout());

        // Creiamo il pannello per i dati anagrafici
        JPanel datiAnagraficiPanel = new JPanel(new GridLayout(0, 2,5,5));
        datiAnagraficiPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 20));

        // Creiamo il pannello sinistro per i dati anagrafici
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // IdLab
        JLabel idLabLabel = new JLabel("Id Laboratorio:", SwingConstants.LEFT);
        idLabLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField idLabField = new JTextField();
        idLabField.setText(idLabSelezionato);
        datiAnagraficiPanel.add(idLabLabel);
        datiAnagraficiPanel.add(idLabField);

        //Topic
        JLabel topicLabel = new JLabel("Topic:", SwingConstants.LEFT);
        topicLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField topicField = new JTextField();
        topicField.setText(topicSelezionato);
        datiAnagraficiPanel.add(topicLabel);
        datiAnagraficiPanel.add(topicField);

        //Indirizzo
        JLabel indirizzoLabel = new JLabel("Indirizzo:", SwingConstants.LEFT);
        topicLabel.setHorizontalAlignment(SwingConstants.LEFT);
        indirizzoField = new JTextField();
        indirizzoField.setText(indirizzoSelezionato);
        datiAnagraficiPanel.add(indirizzoLabel);
        datiAnagraficiPanel.add(indirizzoField);

        //Numero
        JLabel numeroTelefonicoLabel = new JLabel("Numero Telefonico:", SwingConstants.LEFT);
        numeroTelefonicoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        numeroTelefonicoField = new JTextField();
        numeroTelefonicoField.setText(numeroTelefonicoSelezionato);
        datiAnagraficiPanel.add(numeroTelefonicoLabel);
        datiAnagraficiPanel.add(numeroTelefonicoField);

        //RScientifico
        JLabel rscient = new JLabel("responsabile scientifico :",SwingConstants.LEFT);
        rscient.setHorizontalAlignment(SwingConstants.LEFT);
        rScientificoComboBox = new JComboBox<>();
        rScientificoComboBox.addItem(rScientificoSelezionato);
        ArrayList<String> rScientificiDisponibili= controller.getListaResponsabiliScientificiDisponibiliGUI();
        for(String s : rScientificiDisponibili)
            rScientificoComboBox.addItem(s);
        datiAnagraficiPanel.add(rscient);
        datiAnagraficiPanel.add(rScientificoComboBox, BorderLayout.WEST );




        // ... aggiungo i dati anagrafici al pannello
        leftPanel.add(datiAnagraficiPanel);
        leftPanel.setPreferredSize(new Dimension(700,600));
        panel.add(leftPanel, BorderLayout.CENTER);


        JPanel rightPanel = new JPanel(new BorderLayout());
        // Creiamo il pannello destro per la tabella di afferenza
        JPanel rightPanel1 = new JPanel(new BorderLayout());
        //Creiamo il pannello destro per la tabella di gestione
        JPanel rightPanel2 = new JPanel(new BorderLayout());


        rightPanel1.setBorder(BorderFactory.createTitledBorder("Impiegati Associati:"));
        rightPanel2.setBorder(BorderFactory.createTitledBorder("Progetti in lavoro :"));

        //TABELLE


        // Tabella di afferenza
        ArrayList<String> listaMatAfferenti = controller.leggiAfferenzeLaboratorio(idLabSelezionato);

        DefaultTableModel tabellaAfferenzeModel = new DefaultTableModel();
        Object[][] data = new Object[listaMatAfferenti.size()][listaMatAfferenti.size()];
        for (int i = 0; i < listaMatAfferenti.size(); i++) {
            data[i][0] = listaMatAfferenti.get(i);
        }
        tabellaAfferenzeModel.setDataVector(data, new Object[]{"MAT"});
        this.tabellaAfferenze = new JTable(tabellaAfferenzeModel);
        this.tabellaAfferenze.setShowGrid(true);

        //allineo il testo delle colonne al centro
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // Applicazione del renderizzatore personalizzato a tutte le colonne
        int columnCount = this.tabellaAfferenze.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            this.tabellaAfferenze.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tabellaAfferenzeModel);
        // Impostiamo il TableRowSorter sulla tabella
        this.tabellaAfferenze.setRowSorter(sorter);

        tabellaAfferenze.setDefaultEditor(Object.class, null);
        tabellaAfferenze.getTableHeader().setReorderingAllowed(false);
        tabellaAfferenze.setShowGrid(true);
        //COLORI TABELLA
        tabellaAfferenze.setGridColor(Color.DARK_GRAY);
        tabellaAfferenze.setBackground(Color.DARK_GRAY);
        tabellaAfferenze.getTableHeader().setBackground(Color.DARK_GRAY);
        tabellaAfferenze.getTableHeader().setForeground(Color.WHITE);
        //barra di scorrimento

        JScrollPane tabellaAfferenzeScrollPane = new JScrollPane(this.tabellaAfferenze);
        rightPanel1.add(tabellaAfferenzeScrollPane, BorderLayout.CENTER);
        rightPanel1.add(new JScrollPane(this.tabellaAfferenze), BorderLayout.CENTER);
        //GESTIONE

        ArrayList<String> listaCupGestiti = controller.leggiProgettiSuCuiLavora(idLabSelezionato);
        DefaultTableModel tabellaGestioneModel = new DefaultTableModel();
        data = new Object[listaCupGestiti.size()][1];
        for (int i = 0; i < listaCupGestiti.size(); i++) {
            data[i][0] = listaCupGestiti.get(i);
        }
        tabellaGestioneModel.setDataVector(data, new Object[]{"CUP"});
        JTable tabellaGestione = new JTable(tabellaGestioneModel);
        tabellaGestione.setShowGrid(true);

        // Applicazione del renderizzatore personalizzato a tutte le colonne
        columnCount = tabellaGestione.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            tabellaGestione.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        sorter = new TableRowSorter<>(tabellaGestioneModel);
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
        rightPanel2.add(new JScrollPane(tabellaGestione), BorderLayout.CENTER);



        rightPanel.add(rightPanel1,BorderLayout.EAST);
        rightPanel1.setPreferredSize(new Dimension(150,700));
        rightPanel.add(rightPanel2,BorderLayout.WEST);
        rightPanel2.setPreferredSize(new Dimension(150,700));
        panel.add(rightPanel, BorderLayout.EAST);



        //BOTTONI


        // Crea il pannello dei bottoni
        JPanel panelBottoni = new JPanel(new BorderLayout());
        JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton bottoneSalva = new JButton("Salva modifiche");
        JButton bottoneAnnulla = new JButton("Annulla modifiche");
        JButton bottoneAggiungiAfferenza = new JButton("Aggiungi afferente");
        JButton bottoneRimuoviAfferenza = new JButton("Rimuovi afferente");
        panelBottoniLeft.add(bottoneSalva);
        panelBottoniLeft.add(bottoneAnnulla);
        panelBottoniRight.add(bottoneAggiungiAfferenza);
        panelBottoniRight.add(bottoneRimuoviAfferenza);

        panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
        panelBottoni.add(panelBottoniRight, BorderLayout.EAST);


        // Logica per salvare le modifiche
        bottoneSalva.addActionListener(e -> {
            setVisible(false);

            try {

                String indirizzoModificato = indirizzoField.getText();
                String numeroTelefonicoModificato = numeroTelefonicoField.getText();
                String rScientificoModificato = (String) rScientificoComboBox.getSelectedItem();

                controller.modificaLaboratorio(idLabSelezionato,indirizzoModificato,numeroTelefonicoModificato,rScientificoModificato);

                JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore durante la modifica dei dati del laboratorio:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } finally {
                dispose();
                framePadre.setVisible(true);
            }

        });


        //bottone che torna alla vista impiegati
        bottoneAnnulla.addActionListener(e -> {
            //chiudo la finestra di dialogo
            dispose();
            framePadre.setVisible(true);
        });


        // Logica per aggiungere afferenza
        bottoneAggiungiAfferenza.addActionListener(e -> {
            // Creazione e visualizzazione della finestra di dialogo
            JDialog dialog = new JDialog(); // utilizzo una classe di dialogo personalizzata
           // dialog.setModal(true); // Imposto la finestra di dialogo come modale per bloccare l'interazione con la finestra principale
            dialog.setTitle("Aggiungi afferenza:\n");

            //creo la tabella
            DefaultTableModel tabellaAfferenzaModel = new DefaultTableModel();
            tabellaAfferenzaModel.addColumn("MAT");
            ArrayList<String> listaMat = controller.getListaImpiegatoMatricoleGUI();
            //rimuovo dalla lista dei laboratori quelli a cui l'impiegato afferisce gia
            listaMat.removeAll(listaMatAfferenti);
            //riempio la tabella
            Object[][] data1 = new Object[listaMat.size()][1];
            for (int i = 0; i < listaMat.size(); i++) {
                data1[i][0] = listaMat.get(i);
            }
            tabellaAfferenzaModel.setDataVector(data1, new Object[]{"ID"});
            JTable tabellaMatricole = new JTable(tabellaAfferenzaModel);
            tabellaMatricole.setShowGrid(true);

            //allineo il testo delle colonne al centro
            DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
            centerRenderer1.setHorizontalAlignment(SwingConstants.CENTER);
            // Applicazione del renderizzatore personalizzato a tutte le colonne
            int columnCount1 = tabellaMatricole.getColumnCount();
            for (int i = 0; i < columnCount1; i++) {
                tabellaMatricole.getColumnModel().getColumn(i).setCellRenderer(centerRenderer1);
            }

            // Aggiungi la tabella a un componente di scorrimento
            JScrollPane scrollPane = new JScrollPane(tabellaMatricole);
            // Aggiungi il componente di scorrimento alla finestra di dialogo
            dialog.add(scrollPane);

            // Quando l'utente tocca un codice, parte questo ActionListener
            tabellaMatricole.getSelectionModel().addListSelectionListener(e1 -> {
                // Ottieni il codice selezionato
                int selectedRow = tabellaMatricole.getSelectedRow();
                String matricolaSelezionata = (String) tabellaMatricole.getValueAt(selectedRow, 0);

                // L utente ha selezionato una colonna
                int response = JOptionPane.showOptionDialog(dialog, "Aggiungo l'afferenza dell'impiegato " + matricolaSelezionata + " al laboratorio " + idLabSelezionato  + "?", "Conferma Salvataggio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
                if (response == JOptionPane.YES_OPTION) {
                    //aggiungo l'afferenza al codLabSelezionato
                    try {
                        controller.aggiungiAfferenza(matricolaSelezionata,idLabSelezionato);
                        JOptionPane.showMessageDialog(null, "Modifica eseguita correttamente!\n", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'aggiunta dell'afferenza al laboratorio:\n" + ex.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        dialog.dispose();
                        updateTabellaAfferenze(controller,idLabSelezionato);
                    }
                }
            });

            // Imposta le dimensioni e la posizione della finestra di dialogo
            dialog.setSize(250, 400);
            dialog.setLocationRelativeTo(null); // Posiziona la finestra di dialogo al centro dello schermo
            dialog.setVisible(true);
        });


        // Logica per rimuovere afferenza
        bottoneRimuoviAfferenza.addActionListener(e -> {
            int selectedRow = ProfiloLaboratorioGUI.this.tabellaAfferenze.getSelectedRow();
            int selectedColumn = ProfiloLaboratorioGUI.this.tabellaAfferenze.getSelectedColumn();

            if (selectedRow != -1 && selectedColumn != -1) {
                // La matricola si trova nella prima colonna della tabella
                String matricolaSelezionata = ProfiloLaboratorioGUI.this.tabellaAfferenze.getValueAt(ProfiloLaboratorioGUI.this.tabellaAfferenze.getSelectedRow(), 0).toString();
                int response = JOptionPane.showOptionDialog( panel, "Sei sicuro di voler eliminare l'afferenza della matricola " + matricolaSelezionata + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                if (response == JOptionPane.YES_OPTION) {
                    //elimino l'impiegato con la matricola selezionata
                    try {
                        controller.eliminaAfferenza(matricolaSelezionata,idLabSelezionato);
                    } catch (PSQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione dell'afferenza:\n" + ex.getMessage(), "Errore di eliminazione", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "Errore durante l'esecuzione del programma: " + ee.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                    //aggiorno la tabella appena dopo l'eliminazione dell'impiegato
                    updateTabellaAfferenze(controller,idLabSelezionato);
                }
            } else {
                // L'utente non ha selezionato una cella
                JOptionPane.showMessageDialog(panel, "Seleziona una matricola per eliminare la sua afferenza.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });



        // Aggiungo i pannelli alla finestra principale
        add(panel,BorderLayout.CENTER);
        add(panelBottoni, BorderLayout.SOUTH);


        // Rendo non modificabili alcuni campi
        idLabField.setEditable(false);
        topicField.setEditable(false);


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

        private void updateTabellaAfferenze(Controller controller, String idLabScelto) {
            //load dei nuovi dati
            ArrayList<String> listaLabAfferiti = controller.leggiAfferenzeLaboratorio(idLabScelto);
            Object[][] nuoviDati = new Object[listaLabAfferiti.size()][listaLabAfferiti.size()];
            for (int i = 0; i < listaLabAfferiti.size(); i++) {
                nuoviDati[i][0] = listaLabAfferiti.get(i);
            }
            // Aggiungi le nuove righe alla tabella
            DefaultTableModel model = (DefaultTableModel) tabellaAfferenze.getModel();
            model.setDataVector(nuoviDati,new Object[]{"MAT"});
            //allineo il testo delle colonne al centro
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            //applico l'allineamento
            int columnCount = this.tabellaAfferenze.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                this.tabellaAfferenze.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

}
