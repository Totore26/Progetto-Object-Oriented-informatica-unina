package ImplementazionePostgresDAO;

import DAO.LaboratorioDAO;
import DBconnection.Connessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class LaboratorioPostgresDAO implements LaboratorioDAO {

    private Connection connection;

    public LaboratorioPostgresDAO(){
        try {
            connection = Connessione.getInstance().getConnection();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    };

    @Override
    public boolean aggiungiLaboratorioDAO(String idLab, String topic, String indirizzo,String numeroTelefonico, int numAfferenti, String rScientifico) throws SQLException{
        PreparedStatement InsertLab;
        InsertLab = connection.prepareStatement("INSERT INTO laboratorio (id_lab, topic, indirizzo, numero_telefono, numero_afferenti, r_scientifico) VALUES (?, ?, ?, ?, ?, ?)");
        InsertLab.setString(1, idLab);
        InsertLab.setString(2, indirizzo);
        InsertLab.setString(3, numeroTelefonico);
        InsertLab.setInt(4, numAfferenti);
        InsertLab.setString(5, rScientifico);

        int result = InsertLab.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminaLaboratorioDAO(String idLabSelezionato) throws SQLException {
        PreparedStatement deleteLab;
        deleteLab = connection.prepareStatement("DELETE FROM LABORATORIO WHERE MATRICOLA = ? ");
        deleteLab.setString(1, idLabSelezionato);
        int result = deleteLab.executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;

    }

    @Override
    public boolean modificaLaboratorioDAO(String idLabScelto,String indirizzo,String numeroTelefonico,String rScientifico) throws SQLException{
        PreparedStatement modificaLab;
        modificaLab = connection.prepareStatement("UPDATE LABORATORIO SET indirizzo = ? ,numero_telefono = ? , r_scientifico = ? WHERE id_lab = ?");
        modificaLab.setString(1, indirizzo);
        modificaLab.setString(2, numeroTelefonico);
        modificaLab.setString(3, rScientifico);
        modificaLab.setString(4, idLabScelto);
        int rs = modificaLab.executeUpdate();
        if (rs>0) {
            return true;
        }
        return false;
    }

    public boolean leggiProgettiDAO() throws SQLException{
        return false;
    }
}
