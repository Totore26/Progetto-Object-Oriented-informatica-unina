package DAO;

import java.sql.SQLException;

public interface LaboratorioDAO {

    public boolean aggiungiLaboratorioDAO(String idLab, String topic, String indirizzo,String numeroTelefonico, int numAfferenti, String rScientifico) throws SQLException;
    public boolean eliminaLaboratorioDAO(String idLabSelezionato) throws SQLException;
    public boolean modificaLaboratorioDAO(String idLabScelto,String indirizzo,String numeroTelefonico,String rScientifico) throws SQLException;
    public boolean leggiProgettiDAO() throws SQLException;
}
