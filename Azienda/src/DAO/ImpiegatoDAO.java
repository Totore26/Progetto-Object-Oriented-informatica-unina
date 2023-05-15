package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface ImpiegatoDAO {

    public boolean aggiungiImpiegatoDAO(String matricola, String nome, String cognome, String codiceFiscale, String curriculum, String tipoImpiegato, boolean dirigente, Date dataAssunzione, Date dataLicenziamento, float stipendio, String sesso) throws SQLException;
    public boolean eliminaImpiegatoDAO(String matricolaSelezionata) throws SQLException;

    public boolean aggiungiAfferenzaDAO(String matricolaScelta, String idlabScelto) throws SQLException;

    public boolean eliminaAfferenzaDAO(String matricolaScelta, String idlabScelto) throws  SQLException;

    public boolean leggiAfferenzePerImpiegatoDAO(String matricolaSelezionata, ArrayList<String> laboratoriAssociati);
    public boolean modificaImpiegatoDAO(String matricolaSelezionata,String curriculum, boolean dirigente, Date dataLicenziamento, float stipendio)throws SQLException;


}



