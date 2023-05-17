package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProgettoDAO {

    public boolean aggiungiProgettoDAO(String nome, String cup, float budget, Date dataInizio, Date dataFine, String responsabile, String referente)  throws SQLException;
    public boolean eliminaProgettoDAO(String cup) throws SQLException;
    public boolean modificaProgettoDAO(String cupScelto, float budget, Date dataFine, String responsabile, String referente) throws SQLException;

    public boolean leggiGestionePerProgetto(String cupScelto, ArrayList<String> labGestiti);
    public boolean aggiungiGestione(String cupScelto, String idlabScelto) throws SQLException;
    public boolean eliminaGestione(String cupScelto,String idLabScelto) throws SQLException;
}
