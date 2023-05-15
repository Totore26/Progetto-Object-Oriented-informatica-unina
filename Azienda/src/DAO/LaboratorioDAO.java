package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LaboratorioDAO {

    public boolean aggiungiLaboratorioDAO(String idLab, String topic, String indirizzo,String numeroTelefonico, String rScientifico) throws SQLException;
    public boolean eliminaLaboratorioDAO(String idLabSelezionato) throws SQLException;

    /*
    * l'aggiunta o la rimozione di un progetto per un dato Laboratorio è gestito dal progetto stesso che decide
    * su quale laboratorio lavorare, motivo per il quale il laboratorio non può decidere in modo indipendente su quali
    * progetti lavorare.
    */

    public boolean modificaLaboratorioDAO(String idLabScelto,String indirizzo,String numeroTelefonico,String rScientifico) throws SQLException;
    public boolean leggiProgettiDAO(String idLabScelto, ArrayList<String> progettiAssociati) throws SQLException;
}
