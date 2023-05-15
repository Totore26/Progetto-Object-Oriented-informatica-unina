package ImplementazionePostgresDAO;

import DAO.LaboratorioDAO;
import DBconnection.Connessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public boolean aggiungiLaboratorioDAO(String idLab, String topic, String indirizzo,String numeroTelefonico, String rScientifico) throws SQLException{
        PreparedStatement InsertLab;
        InsertLab = connection.prepareStatement("INSERT INTO laboratorio (id_lab, topic, indirizzo, numero_telefono, r_scientifico) VALUES (?, ?, ?, ?, ?)");
        InsertLab.setString(1, idLab);
        InsertLab.setString(2, topic);
        InsertLab.setString(3, indirizzo);
        InsertLab.setString(4, numeroTelefonico);
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
        deleteLab = connection.prepareStatement("DELETE FROM LABORATORIO WHERE ID_LAB = ? ");
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

    public boolean leggiProgettiDAO(String idLabScelto, ArrayList<String> progettiAssociati) throws SQLException{
        try {
            PreparedStatement leggiProgetti;
            leggiProgetti = connection.prepareStatement("SELECT CUP FROM GESTIONE WHERE ID_LAB = ?");
            leggiProgetti.setString(1,idLabScelto);
            ResultSet rs = leggiProgetti.executeQuery();
            while(rs.next())
            {
                progettiAssociati.add(rs.getString("CUP"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean leggiAfferenzePerLaboratorioDAO(String idLabSelezionato, ArrayList<String> matricoleAssociate) throws SQLException{
        try {
            PreparedStatement leggiAfferenze;
            leggiAfferenze = connection.prepareStatement("SELECT MATRICOLA FROM AFFERENZA WHERE ID_LAB = ?");
            leggiAfferenze.setString(1,idLabSelezionato);
            ResultSet rs = leggiAfferenze.executeQuery();
            while(rs.next())
            {
                matricoleAssociate.add(rs.getString("matricola"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
