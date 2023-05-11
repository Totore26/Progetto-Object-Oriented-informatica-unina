package DAO;

import java.sql.SQLException;

public interface ProgettoDAO {

    public boolean aggiungiProgettoDAO()  throws SQLException;
    public boolean eliminaProgettoDAO()  throws SQLException;
    public boolean modificaProgettoDAO() throws SQLException;
    public boolean aggiungiGestione() throws SQLException;
}
