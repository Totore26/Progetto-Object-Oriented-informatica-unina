package ImplementazionePostgresDAO;

import DAO.ImpiegatoDAO;
import DBconnection.Connessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public boolean eliminaImpiegatoDAO(String matricolaSelezionata) throws SQLException{

            PreparedStatement deleteImp;
            deleteImp = connection.prepareStatement("DELETE FROM IMPIEGATO WHERE MATRICOLA = ? ");
            deleteImp.setString(1, matricolaSelezionata);
            int result = deleteImp.executeUpdate();
            if (result == 1) {
                return true;
            }
            return false;


    }

    @Override
    public boolean aggiungiImpiegatoDAO(String matricola, String nome, String cognome, String codiceFiscale, String curriculum, String tipoImpiegato, boolean dirigente, Date dataAssunzione, Date dataLicenziamento, float stipendio, String sesso) throws SQLException {
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
    }

    @Override
    public boolean leggiAfferenzeDAO(String matricolaSelezionata, ArrayList<String> laboratori){
        try {
            PreparedStatement leggiAfferenze;
            leggiAfferenze = connection.prepareStatement("SELECT ID_LAB FROM AFFERENZA WHERE MATRICOLA = ?");
            leggiAfferenze.setString(1,matricolaSelezionata);
            ResultSet rs = leggiAfferenze.executeQuery();
            while(rs.next())
            {
                laboratori.add(rs.getString("id_lab"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean aggiungiAfferenzaDAO(String matricolaScelta, String idlabScelto) throws SQLException{
        PreparedStatement insertImp;
        insertImp = connection.prepareStatement("INSERT INTO AFFERENZA (matricola, id_lab) VALUES (?,?)");
        insertImp.setString(1, matricolaScelta);
        int rs = insertImp.executeUpdate();
        if(rs==1){
            return true;
        }

        return false;
    }

    @Override
    public boolean eliminaAfferenzaDAO(String matricolaScelta, String idlabScelto) throws  SQLException{
        PreparedStatement insertImp;
        insertImp = connection.prepareStatement("DELETE FROM AFFERENZA WHERE MATRICOLA = ?");
        insertImp.setString(1, matricolaScelta);
        int rs = insertImp.executeUpdate();
        if(rs==1){
            return true;
        }

        return false;
    }

    @Override
    public boolean modificaImpiegatoDAO(String matricolaSelezionata,String curriculum, boolean dirigente, Date dataLicenziamento, float stipendio)throws SQLException {

        PreparedStatement insertImp;
        insertImp = connection.prepareStatement("UPDATE IMPIEGATO SET curriculum=? ,dirigente = ? ,data_licenziamento = ? , stipendio=? WHERE MATRICOLA = ?");
        insertImp.setString(1, curriculum);
        insertImp.setBoolean(2, dirigente);
        insertImp.setDate(3, (java.sql.Date) dataLicenziamento);
        insertImp.setFloat(4, stipendio);
        insertImp.setString(5,matricolaSelezionata);
        int rs = insertImp.executeUpdate();
        if (rs>0) {
            return true;
        }
        return false;
    }
}
