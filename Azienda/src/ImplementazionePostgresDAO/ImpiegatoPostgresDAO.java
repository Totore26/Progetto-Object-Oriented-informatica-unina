package ImplementazionePostgresDAO;

import DAO.ImpiegatoDAO;
import DBconnection.Connessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class ImpiegatoPostgresDAO implements ImpiegatoDAO {

    private Connection connection;


    public ImpiegatoPostgresDAO(){
        try {
            connection = Connessione.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }





    @Override
    public boolean removeImpiegatoDAO(String matricolaSelezionata){
        try {
            PreparedStatement deleteImp;
            deleteImp = connection.prepareStatement("DELETE FROM IMPIEGATO WHERE MATRICOLA = ? ");
            deleteImp.setString(1, matricolaSelezionata);
            int result = deleteImp.executeUpdate();
            if (result == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean modifyImpiegatoDAO() {
        return false;
    }

    @Override
    public boolean addImpiegatoDAO(String matricola, String nome, String cognome, String codiceFiscale, String curriculum, String tipoImpiegato, boolean dirigente, Date dataAssunzione, Date dataLicenziamento, float stipendio, String sesso) {
        try {
            PreparedStatement insertImp;
            insertImp = connection.prepareStatement("INSERT INTO IMPIEGATO (matricola, nome, cognome, codice_fiscale, curriculum, dirigente, tipo_impiegato, data_assunzione, data_licenziamento, stipendio, sesso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertImp.setString(1, matricola);
            insertImp.setString(2, nome);
            insertImp.setString(3, cognome);
            insertImp.setString(4, codiceFiscale);
            insertImp.setString(5, curriculum);
            insertImp.setBoolean(6, dirigente);
            insertImp.setString(7, tipoImpiegato);
            insertImp.setDate(8, (java.sql.Date) dataAssunzione);
            insertImp.setDate(9, (java.sql.Date) dataLicenziamento);
            insertImp.setFloat(10, stipendio);
            insertImp.setString(11, sesso);
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

    @Override
    public boolean updateAfferenze() {
        return false;
    }

}
