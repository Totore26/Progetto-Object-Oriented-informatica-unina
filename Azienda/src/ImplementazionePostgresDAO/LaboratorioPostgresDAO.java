package ImplementazionePostgresDAO;

import DAO.LaboratorioDAO;

import java.sql.SQLException;

public class LaboratorioPostgresDAO implements LaboratorioDAO {

    @Override
    public boolean aggiungiLaboratorioDAO() throws SQLException {
        return false;
    }

    @Override
    public boolean eliminaLaboratorioDAO() throws SQLException {
        return false;
    }

    @Override
    public boolean modificaLaboratorioDAO() throws SQLException {
        return false;
    }

    public boolean leggiProgettiDAO() throws SQLException{
        return false;
    }
}
