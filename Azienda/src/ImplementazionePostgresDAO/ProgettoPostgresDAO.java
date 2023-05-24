package ImplementazionePostgresDAO;

import DAO.ProgettoDAO;
import DBconnection.Connessione;

import java.sql.*;
import java.util.ArrayList;

public class ProgettoPostgresDAO implements ProgettoDAO {

    private Connection connection;

    /**
     * Instantiates a new Progetto postgres dao and set a connection with Database.
     */
    public ProgettoPostgresDAO(){
        try {
            connection = Connessione.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean aggiungiProgettoDAO(String nome, String cup, float budget, Date dataInizio, Date dataFine, String responsabile, String referente)  throws SQLException{
        PreparedStatement insertPro;
        insertPro = connection.prepareStatement("INSERT INTO PROGETTO (nome_progetto, cup, budget, data_inizio, data_fine, responsabile, referente) VALUES (?, ?, ?, ?, ?, ?, ?)");
        insertPro.setString(1, nome);
        insertPro.setString(2, cup);
        insertPro.setFloat(3, budget);
        insertPro.setDate(4, dataInizio);
        insertPro.setDate(5, dataFine);
        insertPro.setString(6, responsabile);
        insertPro.setString(7, referente);
        int result = insertPro.executeUpdate();
        return result == 1;
    }

    @Override
    public boolean eliminaProgettoDAO(String cup) throws SQLException {
        PreparedStatement deletePro;
        deletePro = connection.prepareStatement("DELETE FROM PROGETTO WHERE CUP = ?");
        deletePro.setString(1, cup);
        int rs = deletePro.executeUpdate();
        return rs == 1;
    }

    @Override
    public boolean modificaProgettoDAO(String cupScelto, float budget, Date dataFine, String responsabile, String referente) throws SQLException {
        PreparedStatement modificaPro;
        modificaPro = connection.prepareStatement("UPDATE PROGETTO SET budget = ?, data_fine = ?, responsabile = ?, referente = ? WHERE cup = ?");
        modificaPro.setFloat(1,budget);
        modificaPro.setDate(2,dataFine);
        modificaPro.setString(3,responsabile);
        modificaPro.setString(4,referente);
        modificaPro.setString(5,cupScelto);
        int rs = modificaPro.executeUpdate();
        return rs == 1;
    }

    @Override
    public boolean aggiungiGestioneDAO(String cupScelto, String idlabScelto) throws SQLException {
        PreparedStatement insertGestione;
        insertGestione = connection.prepareStatement("INSERT INTO GESTIONE (cup, id_lab) VALUES (?,?)");
        insertGestione.setString(1, cupScelto);
        insertGestione.setString(2,idlabScelto);
        int rs = insertGestione.executeUpdate();
        return rs == 1;
    }

    @Override
    public boolean eliminaGestione(String cupScelto,String idLabScelto) throws SQLException{
        PreparedStatement deleteGestione;
        deleteGestione = connection.prepareStatement("DELETE FROM GESTIONE WHERE CUP = ? AND ID_LAB=?");
        deleteGestione.setString(1, cupScelto);
        deleteGestione.setString(2,idLabScelto);
        int rs = deleteGestione.executeUpdate();
        return rs == 1;
    };

    @Override
    public boolean leggiGestionePerProgetto(String cupScelto, ArrayList<String> labGestiti){
        try {
            PreparedStatement leggiGestioni;
            leggiGestioni = connection.prepareStatement("SELECT ID_LAB FROM GESTIONE WHERE CUP = ?");
            leggiGestioni.setString(1,cupScelto);
            ResultSet rs = leggiGestioni.executeQuery();
            while(rs.next())
            {
                labGestiti.add(rs.getString("id_lab"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    };
}
