package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * The interface Impiegato dao.
 */
public interface ImpiegatoDAO {

    /**
     * Aggiungi impiegato dao boolean è la funzione che aggiungi un impiegato al database.
     *
     * @param matricola         La matricola è dell'impiegato da aggiungere.
     * @param nome              La nome è dell'impiegato da aggiungere.
     * @param cognome           La cognome è dell'impiegato da aggiungere.
     * @param codiceFiscale     La codice fiscale è dell'impiegato da aggiungere.
     * @param curriculum        La curriculum è dell'impiegato da aggiungere.
     * @param tipoImpiegato     the tipo impiegato è dell'impiegato da aggiungere.
     * @param dirigente         the dirigente è dell'impiegato da aggiungere.
     * @param dataAssunzione    the data assunzione è dell'impiegato da aggiungere.
     * @param dataLicenziamento the data licenziamento è dell'impiegato da aggiungere.
     * @param stipendio         the stipendio è dell'impiegato da aggiungere.
     * @param sesso             the sesso è dell'impiegato da aggiungere.
     * @return the boolean ritorna true se l'aggiunta ha avuto successo false altrimenti.
     * @throws SQLException the sql exception, in particolare nel caso in cui la l'impiegato non viene aggiunto per qualche errore[...]
     */
    public boolean aggiungiImpiegatoDAO(String matricola, String nome, String cognome, String codiceFiscale, String curriculum, String tipoImpiegato, boolean dirigente, Date dataAssunzione, Date dataLicenziamento, float stipendio, String sesso) throws SQLException;



    /**
     * Elimina impiegato dao boolean è la funzione che elimina dal database l'impiegato selezionato nella gui.
     *
     * @param matricolaSelezionata the matricola selezionata per l'eliminazione dell'impeigato in questione.
     * @return the boolean ritorna true se viene eliminato false altrimenti.
     * @throws SQLException the sql exception nel caso in cui l'impiegato non può essere eliminato.
     */
    public boolean eliminaImpiegatoDAO(String matricolaSelezionata) throws SQLException;



    /**
     * Aggiungi afferenza dao boolean.
     *
     * @param matricolaScelta the matricola scelta per l'afferenza.
     * @param idlabScelto     the idlab scelto per l'afferenza.
     * @return the boolean  : true -> viene aggiunta l'afferenza, false altrimenti.
     * @throws SQLException the sql exception nel caso in cui non avviene l'afferenza.
     */
    public boolean aggiungiAfferenzaDAO(String matricolaScelta, String idlabScelto) throws SQLException;



    /**
     * Elimina afferenza dao boolean.
     *
     * @param matricolaScelta the matricola scelta per eliminare l'afferenza
     * @param idlabScelto     the idlab scelto per eliminare l'afferenza a quel laboratorio.
     * @return the boolean    true -> viene eliminato l'afferenza, false altrimenti.
     * @throws SQLException the sql exception nel caso in cui l'eliminazione non va a buon fine.
     */
    public boolean eliminaAfferenzaDAO(String matricolaScelta, String idlabScelto) throws  SQLException;



    /**
     * Leggi afferenze per impiegato prende dal database le afferenze di un imp per un laboratorio e ritorna i valori.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @param laboratoriAssociati  the laboratori associati è la lista di idLab a cui l'impiegato afferisce.
     * @return the boolean true-> vengono letti i dati, false altrimenti.
     */
    public boolean leggiAfferenzePerImpiegatoDAO(String matricolaSelezionata, ArrayList<String> laboratoriAssociati);



    /**
     * Modifica impiegato dao boolean.
     *
     * @param matricolaSelezionata the matricola selezionata dell'impiegato da modificare.
     * @param curriculum           the curriculum dell'impiegato da modificare
     * @param dirigente            the dirigente (nel caso in cui avviene un up-grade del grado in dirigente oppure un down-grade)
     * @param dataLicenziamento    the data licenziamento aggiunta della data licenziamento
     * @param stipendio            the stipendio
     * @return the boolean         true->modifica effettuata, false altrimenti.
     * @throws SQLException the sql exception nel caso in cui la modifica non viene effettuata.
     */
    public boolean modificaImpiegatoDAO(String matricolaSelezionata,String curriculum, boolean dirigente, Date dataLicenziamento, float stipendio)throws SQLException;


}



