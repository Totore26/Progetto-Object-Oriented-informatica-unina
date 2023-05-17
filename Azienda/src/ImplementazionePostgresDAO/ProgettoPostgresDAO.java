package ImplementazionePostgresDAO;

import DAO.ProgettoDAO;
import DBconnection.Connessione;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProgettoPostgresDAO implements ProgettoDAO {

    private Connection connection;

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
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminaProgettoDAO(String cup) throws SQLException {
        PreparedStatement deletePro;
        deletePro = connection.prepareStatement("DELETE FROM PROGETTO WHERE CUP = ?");
        deletePro.setString(1, cup);
        int rs = deletePro.executeUpdate();
        if(rs==1){
            return true;
        }

        return false;
    }

    @Override
    public boolean modificaProgettoDAO(String cupScelto, float budget, Date dataFine, String responsabile, String referente) throws SQLException {
        return false;
    }

    @Override
    public boolean aggiungiGestione(String cupScelto, String idlabScelto) throws SQLException {
        return false;
    }

    @Override
    public boolean eliminaGestione(String cupScelto,String idLabScelto) throws SQLException{return false;};

    @Override
    public boolean leggiGestionePerProgetto(String cupScelto, ArrayList<String> labGestiti){return false;};
}
