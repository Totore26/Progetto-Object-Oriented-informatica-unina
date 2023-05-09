
package ImplementazionePostgresDAO;


import DAO.AziendaDAO;
import DBconnection.Connessione;
import MODEL.Impiegato;
import MODEL.Laboratorio;
import MODEL.Progetto;
import MODEL.Storico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Laboratorio> getListLaboratorioDAO(){
        {
            try {
                PreparedStatement selectListaLaboratori;
                List<Laboratorio> listaLaboratorio = new ArrayList<>();
                selectListaLaboratori = connection.prepareStatement("SELECT * FROM laboratorio ORDER BY nome");
                ResultSet rs = selectListaLaboratori.executeQuery();
                while (rs.next() ) {
                    String IdLab = rs.getString("id_lab");
                    String topic = rs.getString("topic");
                    String indirizzo = rs.getString("indirizzo");
                    int numeroTelefonico = rs.getInt("numero_telefonico");
                    int numAfferenti = rs.getInt("numero_afferenti");
                    String RScientifico = rs.getString("r_scientifico");

                    Laboratorio lab = new Laboratorio(IdLab, topic, indirizzo, numeroTelefonico, numAfferenti, RScientifico);

                    //todo prendi da afferenza tutti gli impiegati che afferiscono ad un Laboratorio.
                    listaLaboratorio.add(lab);
                }
                //ritorno la lista dei progetti.
                return listaLaboratorio;

            } catch (SQLException e) {
                return null;
            }

        }

    }
    @Override
    public List<Impiegato> getListImpiegatiDAO() {

        try {
            PreparedStatement selectListaImpiegati;
            List<Impiegato> listaImpiegati = new ArrayList<>();
            selectListaImpiegati = connection.prepareStatement("SELECT * FROM impiegato ORDER BY nome");
            ResultSet rs = selectListaImpiegati.executeQuery();
            while (rs.next() ) {
                String matricola = rs.getString("matricola");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String codiceFiscale = rs.getString("codice_fiscale");
                String curriculum = rs.getString("curriculum");
                boolean dirigente = rs.getBoolean("dirigente");
                String tipoImpiegato = rs.getString("tipo_impiegato");
                String dataAssunzione =rs.getString("data_assunzione");
                String dataLicenziamento = rs.getString("data_licenziamento");
                float stipendio = rs.getFloat("stipendio");
                String sesso = rs.getString("sesso");

                //TODO ricorda di gestire le date e formattarle in modo coerente.
                //todo anche di inizializzare i laboratori a cui afferisce prendendoli da AFFERENZA
                Impiegato imp = new Impiegato(matricola, nome, cognome, codiceFiscale, curriculum, dirigente,tipoImpiegato, dataAssunzione ,dataLicenziamento, stipendio, sesso);
                listaImpiegati.add(imp);
            }

            //ritorno la lista degli impiegati.
            return listaImpiegati;

        } catch (SQLException e) {
            return null;
        }

    }
    @Override
    public List<Progetto> getListProgettoDAO(){

        try {
            PreparedStatement selectListaProgetti;
            List<Progetto> listaProgetto = new ArrayList<>();
            selectListaProgetti = connection.prepareStatement("SELECT * FROM progetto ORDER BY nome_progetto");
            ResultSet rs = selectListaProgetti.executeQuery();
            while (rs.next() ) {
                String cup = rs.getString("cup");
                String nome = rs.getString("nome_progetto");
                int budget = rs.getInt("budget");
                String dataInizio = rs.getString("data_inizio");
                String dataFine = rs.getString("data_fine");
                String responsabile = rs.getString("responsabile");
                String referente = rs.getString("referente");


                //todo ricorda di inizializzare anche i laboratori a cui è associato il progetto.
                Progetto pro = new Progetto(nome,cup,budget,dataInizio,dataFine,responsabile,referente);

                listaProgetto.add(pro);
            }
            //ritorno la lista dei progetti.
            return listaProgetto;

        } catch (SQLException e) {
            return null;
        }

    }
    @Override
    public List<Storico> getListStoricoDAO(){
        try {
            PreparedStatement selectListaStorici;
            List<Storico> listaStorico = new ArrayList<>();
            selectListaStorici = connection.prepareStatement("SELECT * FROM storico ORDER BY matricola");
            ResultSet rs = selectListaStorici.executeQuery();
            while (rs.next() ) {
                String ruoloPrecedente = rs.getString("ruolo_prec");
                String nuovoRuolo = rs.getString("nuovo_ruolo");
                String datascatto = rs.getString("data_scatto");
                String matricola = rs.getString("matricola");

                //todo creare i vari storici che poi vanno inseriti all'interno della listaStorici di impiegato.
                Storico sto = new Storico(ruoloPrecedente,nuovoRuolo,datascatto,matricola);
                listaStorico.add(sto);
            }
            //ritorno la lista dei progetti.
            return listaStorico;

        } catch (SQLException e) {
            return null;
        }
    }





    //todo AGGIUNGERE UN IMPIEGATO SIA NEL MODEL CHE NEL DATABASE.
    @Override
    // Aggiunge un impiegato nel database
    public boolean addImpiegatoDAO(Impiegato imp) {
        try {
            PreparedStatement insertImp;
            insertImp = connection.prepareStatement("INSERT INTO IMPIEGATO (matricola, nome, cognome, codice_fiscale, curriculum, dirigente, tipo_impiegato, data_assunzione, data_licenziamento, stipendio, sesso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertImp.setString(1, imp.getMatricola());
            insertImp.setString(2, imp.getNome());
            insertImp.setString(3, imp.getCognome());
            insertImp.setString(4, imp.getCodiceFiscale());
            insertImp.setString(5, imp.getCurriculum());
            insertImp.setBoolean(6, imp.isDirigente());
            insertImp.setString(7, imp.getTipoImpiegato());
            insertImp.setString(8, imp.getDataAssunzione());
            insertImp.setString(9, imp.getDataLicenziamento());
            insertImp.setFloat(10, imp.getStipendio());
            insertImp.setString(11, imp.getSesso());
            int result = insertImp.executeUpdate();
            if (result == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //todo ELIMINARE UN IMPIEGATO NEL MODEL E NEL DATABASE
    @Override //supongo che data la matricola elimino l'impiegato.
    public boolean removeImpiegatoDAO(String matricola) {
        return false;
    }



    //Todo AGGIUNGERE UN NUOVO PROGETTO
    @Override
    public boolean addProgettoDAO() {
        return false;
    }



    //Todo ELIMINARE UN PROGETTO
    @Override
    public boolean removeProgettoDAO() {
        return false;
    }



    //Todo AGGIUNGERE UN NUOVO LABORATORIO
    @Override
    public boolean addLaboratorioDAO() {
        return false;
    }



    //Todo ELIMINARE UN LABORATORIO
    @Override
    public boolean removeLaboratorioDAO() {
        return false;
    }



}

