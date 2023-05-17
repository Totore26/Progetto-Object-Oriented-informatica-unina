
package ImplementazionePostgresDAO;
import DAO.AziendaDAO;
import DBconnection.Connessione;

import java.sql.*;
import java.util.ArrayList;

public class AziendaPostgresDAO implements AziendaDAO {

    private Connection connection;

    //il costruttore crea la connessione con il db[...]
    public AziendaPostgresDAO(){
        try {
            connection = Connessione.getInstance().getConnection();
        }
            catch(SQLException e) {
                e.printStackTrace();
            }
    };



    @Override
    public void getListLaboratorioDAO(ArrayList<String> idLablist, ArrayList<String> topiclist, ArrayList<String> indirizzolist, ArrayList<String> numeroTelefonicoList, ArrayList<Integer> numAfferentilist, ArrayList<String> matricolaResponsabileScientifico){
        try {
            PreparedStatement selectListaLaboratori;
            selectListaLaboratori = connection.prepareStatement("SELECT * FROM laboratorio ORDER BY id_lab");
            ResultSet rs = selectListaLaboratori.executeQuery();
            while (rs.next() ) {
                String idLab = rs.getString("id_lab");
                String topic = rs.getString("topic");
                String indirizzo = rs.getString("indirizzo");
                String numeroTelefonico = rs.getString("numero_telefono");
                int numAfferenti = rs.getInt("numero_afferenti");
                String RScientifico = rs.getString("r_scientifico");

                idLablist.add(idLab);
                topiclist.add(topic);
                indirizzolist.add(indirizzo);
                numeroTelefonicoList.add(numeroTelefonico);
                numAfferentilist.add(numAfferenti);
                matricolaResponsabileScientifico.add(RScientifico);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void getListImpiegatiDAO(ArrayList<String> matricolalist, ArrayList<String>nomelist, ArrayList<String> cognomelist, ArrayList<String> codiceFiscalelist, ArrayList<String> curriculumlist, ArrayList<Boolean> dirigentelist, ArrayList<String> tipoImpiegatolist, ArrayList<Date> dataAssunzionelist, ArrayList<Date> dataLicenziamentolist, ArrayList<Float> stipendiolist, ArrayList<String> sessolist) {

        try {
            PreparedStatement selectListaImpiegati;
            selectListaImpiegati = connection.prepareStatement("SELECT * FROM impiegato ORDER BY matricola");
            ResultSet rs = selectListaImpiegati.executeQuery();
            while (rs.next() ) {
                String matricola = rs.getString("matricola");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String codiceFiscale = rs.getString("codice_fiscale");
                String curriculum = rs.getString("curriculum");
                boolean dirigente = rs.getBoolean("dirigente");
                String tipoImpiegato = rs.getString("tipo_impiegato");
                Date dataAssunzione = rs.getDate("data_assunzione");
                Date dataLicenziamento = rs.getDate("data_licenziamento");
                float stipendio = rs.getFloat("stipendio");
                String sesso = rs.getString("sesso");

                //riempio i vettori che saranno usati per istanziare oggetti nel controller[...]
                matricolalist.add(matricola);
                nomelist.add(nome);
                cognomelist.add(cognome);
                codiceFiscalelist.add(codiceFiscale);
                curriculumlist.add(curriculum);
                dirigentelist.add(dirigente);
                tipoImpiegatolist.add(tipoImpiegato);
                dataAssunzionelist.add(dataAssunzione);
                dataLicenziamentolist.add(dataLicenziamento);
                stipendiolist.add(stipendio);
                sessolist.add(sesso);
            }

            connection.close();

        } catch (SQLException e) {
            System.out.println("Errore nel dump dei dati Impiegato...");
        }

    }
    @Override
    public void getListProgettoDAO(ArrayList<String> nomelist, ArrayList<String> cuplist,  ArrayList<Float> budgetlist, ArrayList<Date> dataIniziolist, ArrayList<Date> dataFinelist, ArrayList<String> matricolaResponsabile, ArrayList<String> matricolaReferente){

        try {
            PreparedStatement selectListaProgetti;
            selectListaProgetti = connection.prepareStatement("SELECT * FROM progetto ORDER BY nome_progetto");
            ResultSet rs = selectListaProgetti.executeQuery();
            while (rs.next() ) {
                String cup = rs.getString("cup");
                String nome = rs.getString("nome_progetto");
                float budget = rs.getInt("budget");
                Date dataInizio =(Date) rs.getDate("data_inizio");
                Date dataFine =(Date) rs.getDate("data_fine");
                String responsabile = rs.getString("responsabile");
                String referente = rs.getString("referente");

                cuplist.add(cup);
                nomelist.add(nome);
                budgetlist.add(budget);
                dataIniziolist.add(dataInizio);
                dataFinelist.add(dataFine);
                matricolaResponsabile.add(responsabile);
                matricolaReferente.add(referente);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void getListStoricoDAO(ArrayList<String> ruoloPrecedentelist, ArrayList<String> nuovoRuololist, ArrayList<java.sql.Date> dataScattolist, ArrayList<String> impiegatoMatricolalist){
        try {
            PreparedStatement selectListaStorici;
            selectListaStorici = connection.prepareStatement("SELECT * FROM storico ORDER BY matricola");
            ResultSet rs = selectListaStorici.executeQuery();
            while (rs.next() ) {
                String ruoloPrecedente = rs.getString("ruolo_prec");
                String nuovoRuolo = rs.getString("nuovo_ruolo");

                java.util.Date fdataScatto = rs.getDate("data_scatto");
                java.sql.Date dataScatto = new java.sql.Date(fdataScatto.getTime());
                String matricola = rs.getString("matricola");


                ruoloPrecedentelist.add(ruoloPrecedente);
                nuovoRuololist.add(nuovoRuolo);
                dataScattolist.add(dataScatto);
                impiegatoMatricolalist.add(matricola);


            }
            //ritorno la lista dei progetti.

        } catch (SQLException e) {
            System.out.println("Errore nel dump di storico...");
        }
    }


    public void aggiornaDatabaseDAO() throws SQLException {

        try {
            PreparedStatement aggData;
            connection.prepareStatement("CALL update_database();");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}

