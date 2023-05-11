package DAO;

import java.util.Date;

public interface ImpiegatoDAO {

    public boolean addImpiegatoDAO(String matricola, String nome, String cognome, String codiceFiscale, String curriculum, String tipoImpiegato, boolean dirigente, Date dataAssunzione, Date dataLicenziamento, float stipendio, String sesso);

    public boolean updateAfferenze();

    public boolean removeImpiegatoDAO(String matricolaSelezionata);

    public boolean modifyImpiegatoDAO();
}



