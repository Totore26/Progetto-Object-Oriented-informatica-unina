package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Progetto dao.
 */
public interface ProgettoDAO {

    /**
     * Aggiungi progetto dao boolean.
     *
     * @param nome         the nome del progetto in questione
     * @param cup          the cup del progetto da aggiungere
     * @param budget       the budget del pro da aggiungere
     * @param dataInizio   the data inizio del progetto da aggiungere
     * @param dataFine     the data fine del progetto da aggiungere
     * @param responsabile the responsabile del progetto da aggiungere
     * @param referente    the referente del progetto da aggiungere
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean aggiungiProgettoDAO(String nome, String cup, float budget, Date dataInizio, Date dataFine, String responsabile, String referente)  throws SQLException;



    /**
     * Elimina progetto dao boolean.
     *
     * @param cup the cup del progetto da eliminare.
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean eliminaProgettoDAO(String cup) throws SQLException;



    /**
     * Modifica progetto dao boolean.
     *
     * @param cupScelto    the cup scelto per la modifica del progetto in questione.
     * @param budget       the budget modificato
     * @param dataFine     the data fine modificato
     * @param responsabile the responsabile modificato
     * @param referente    the referente modificato
     * @return the boolean
     * @throws SQLException the sql exception in caso in cui la modifica non va a buon fine
     */
    public boolean modificaProgettoDAO(String cupScelto, float budget, Date dataFine, String responsabile, String referente) throws SQLException;



    /**
     * Leggi gestione per progetto è la funzione che legge quali laboratori gestisce un progetto.
     *
     * @param cupScelto  the cup scelto
     * @param labGestiti the lab gestiti è la lista che viene riempita dei laboratori gestiti dal progetto. (Al max 3)
     * @return the boolean
     */
    public boolean leggiGestionePerProgetto(String cupScelto, ArrayList<String> labGestiti);



    /**
     * Aggiungi gestione boolean.
     *
     * @param cupScelto   the cup scelto
     * @param idlabScelto the idlab scelto per la gestione da parte del progetto.
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean aggiungiGestione(String cupScelto, String idlabScelto) throws SQLException;



    /**
     * Elimina gestione boolean.
     *
     * @param cupScelto   the cup scelto
     * @param idLabScelto the id lab scelto per eliminare la gestione di quel laboratorio.
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean eliminaGestione(String cupScelto,String idLabScelto) throws SQLException;
}
