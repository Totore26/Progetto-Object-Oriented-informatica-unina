package DAO;

import java.sql.SQLException;

public interface LaboratorioDAO {

    public boolean aggiungiLaboratorioDAO() throws SQLException;
    public boolean eliminaLaboratorioDAO() throws SQLException;
    public boolean modificaLaboratorioDAO() throws SQLException;
    public boolean leggiProgettiDAO() throws SQLException;
}
