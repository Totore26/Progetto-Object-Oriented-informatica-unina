package GUI;
import MODEL.*;
import CONTROLLER.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
//NON ESTENTE JDIALOG PERCHE VIENE GESTITA IN MODO DIVERSO
    public class FinestraImpiegati
    {
        private JTable tabella;
        private JScrollPane scrollPane;

        public FinestraImpiegati(Controller controller, Frame frameMenuPrincipale) {
            // Creiamo una finestra di esempio per testare la finestra d'inserimento impiegato

            JFrame frameVistaImpiegato = new JFrame("Finestra Impiegati");
            frameVistaImpiegato.setSize(800, 600);
            frameVistaImpiegato.setLocationRelativeTo(null);
            frameVistaImpiegato.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            // Creiamo la tabella impiegati
            String[] columns = {"Matricola", "Nome", "Cognome"};
            JFrame frameFinestraImpiegati = new JFrame("Finestra Impiegati");
            frameFinestraImpiegati.setSize(800, 600);
            frameFinestraImpiegati.setLocationRelativeTo(null);
            frameFinestraImpiegati.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Creiamo la tabella impiegati
            //TODO AGGIUSTA QUESTA COSA POICHE' VIOLA LA BCE
            String[] colonneTabella = {"Matricola", "Nome", "Cognome"};
            ArrayList<Impiegato> listaImpiegati = (ArrayList<Impiegato>) controller.getListaImpiegato();
            Object[][] data = new Object[listaImpiegati.size()][3];
            for (int i = 0; i < listaImpiegati.size(); i++) {
                data[i][0] = listaImpiegati.get(i).getMatricola();
                data[i][1] = listaImpiegati.get(i).getNome();
                data[i][2] = listaImpiegati.get(i).getCognome();
            }

            tabella = new JTable(data, colonneTabella);
            tabella.setDefaultEditor(Object.class, null);
            tabella.setDefaultEditor(Object.class, null);
            tabella.setShowGrid(true);
            //COLORI TABELLA
            tabella.setGridColor(Color.BLACK);
            tabella.setBackground(Color.DARK_GRAY);
            tabella.getTableHeader().setBackground(Color.BLACK);
            tabella.getTableHeader().setForeground(Color.WHITE);

            tabella.getTableHeader().setReorderingAllowed(false);

            DefaultTableModel modelloTabella = new DefaultTableModel(data, colonneTabella);
            tabella.setModel(modelloTabella);

            scrollPane = new JScrollPane(tabella);
            frameFinestraImpiegati.add(scrollPane, BorderLayout.CENTER);



            // BOTTONI

            // Creiamo il pulsante per aprire la finestra d'inserimento impiegato
            JButton bottoneInserisci = new JButton("Inserisci");
            bottoneInserisci.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    InserimentoImpiegato dialog = new InserimentoImpiegato(controller);
                    dialog.setVisible(true);
                    // Aggiungo un listener per la finestra di dialogo
                    dialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            // Chiamo il metodo updateTable() dopo la chiusura della finestra di dialogo
                            updateTable(controller, colonneTabella);
                        }
                    });
                }
            });



            //DA IMPLEMENTARE IL CODICE DI ELIMINAZIONE IMPIEGATO NELL ACTION LISTENER
            JButton bottoneElimina = new JButton("Elimina");
            bottoneElimina.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = tabella.getSelectedRow();
                    int selectedColumn = tabella.getSelectedColumn();

                    if (selectedRow != -1 && selectedColumn != -1) {
                        // La matricola si trova nella prima colonna della tabella
                        String matricolaSelezionata = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();
                        int response = JOptionPane.showOptionDialog(frameFinestraImpiegati, "Sei sicuro di voler eliminare la matricola " + matricolaSelezionata + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");

                        if (response == JOptionPane.YES_OPTION) {
                            //elimino l'impiegato con la matricola selezionata
                            controller.eliminaImpiegato(matricolaSelezionata);
                            //aggiorno la tabella appena dopo l'eliminazione dell'impiegato
                            updateTable(controller,colonneTabella);
                        }
                    } else {
                        // L'utente non ha selezionato una cella
                        JOptionPane.showMessageDialog(frameFinestraImpiegati, "Seleziona un impiegato per eliminarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });


            JButton bottoneMenuPrincipale = new JButton("Menù Pricipale");
            bottoneMenuPrincipale.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameFinestraImpiegati.dispose();
                    frameMenuPrincipale.setVisible(true);
                }
            });


            //DA IMPLEMENTARE IL CODICE PER SALVARE LE MODIFICHE
            JButton bottoneProfiloImpiegato = new JButton("Profilo Impiegato");
            bottoneProfiloImpiegato.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = tabella.getSelectedRow();
                    int selectedColumn = tabella.getSelectedColumn();
                    // L'utente ha selezionato una cella
                    if (selectedRow != -1 && selectedColumn != -1) {
                        // la matricola è nella prima colonna della tabella
                        String matricolaSelezionata = tabella.getValueAt(tabella.getSelectedRow(), 0).toString();

                        // Creao un'istanza della finestra di dialogo ProfiloImpiegato
                        ProfiloImpiegato profiloImpiegato = new ProfiloImpiegato(matricolaSelezionata,controller);
                        // Mostro la finestra di dialogo
                        profiloImpiegato.setVisible(true);
                    } else {
                        // L'utente non ha selezionato una cella
                        JOptionPane.showMessageDialog(frameFinestraImpiegati, "Seleziona un impiegato per continuare", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });




            // Aggiungiamo i pulsanti alla finestra
            JPanel panelBottoni = new JPanel(new BorderLayout());
            JPanel panelBottoniLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel panelBottoniRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            panelBottoniLeft.add(bottoneMenuPrincipale);
            panelBottoniRight.add(bottoneProfiloImpiegato);
            panelBottoniRight.add(bottoneInserisci);
            panelBottoniRight.add(bottoneElimina);


            panelBottoni.add(panelBottoniLeft, BorderLayout.WEST);
            panelBottoni.add(panelBottoniRight, BorderLayout.EAST);

            frameFinestraImpiegati.add(panelBottoni, BorderLayout.SOUTH);


            // Mostrimo la finestra
            frameFinestraImpiegati.setVisible(true);


        }

        private void updateTable(Controller controller,String[] colonneTabella) {

            //LOAD DEI NUOVI DATI
            ArrayList<Impiegato> listaImpiegati = (ArrayList<Impiegato>) controller.getListaImpiegato();
            Object[][] nuoviDati = new Object[listaImpiegati.size()][3];
            for (int i = 0; i < listaImpiegati.size(); i++) {
                nuoviDati[i][0] = listaImpiegati.get(i).getMatricola();
                nuoviDati[i][1] = listaImpiegati.get(i).getNome();
                nuoviDati[i][2] = listaImpiegati.get(i).getCognome();
            }

            //CODICE PER AGGIORNARE LA TABELLA CON I NUOVI DATI
            DefaultTableModel model = (DefaultTableModel) tabella.getModel();
            model.setDataVector(nuoviDati, colonneTabella);
        }

    }


