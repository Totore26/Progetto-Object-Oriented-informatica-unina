package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Laboratorio dao.
 */
public interface LaboratorioDAO {

    /**
     * Aggiungi laboratorio dao boolean.
     *
     * @param idLab            the id lab da aggiungere nel database
     * @param topic            the topic del laboratorio in questione
     * @param indirizzo        the indirizzo del lab in questione
     * @param numeroTelefonico the numero telefonico del lab in questione
     * @param rScientifico     la matricola del responsabile scientifico del laboratorio in questione.
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean aggiungiLaboratorioDAO(String idLab, String topic, String indirizzo,String numeroTelefonico, String rScientifico) throws SQLException;



    /**
     * Elimina laboratorio dao boolean.
     *
     * @param idLabSelezionato the id lab selezionato per l'eliminazione dal databse.
     * @return the booleantrue
     * @throws SQLException the sql exception nel caso in cui il laboratorio non viene eliminato.
     */
    public boolean eliminaLaboratorioDAO(String idLabSelezionato) throws SQLException;



    /*
    * l'aggiunta o la rimozione di un progetto per un dato Laboratorio è gestito dal progetto stesso che decide
    * su quale laboratorio lavorare, motivo per il quale il laboratorio non può decidere in modo indipendente su quali
    * progetti lavorare.
    */


    /**
     * Leggi afferenze per laboratorio legge dal database gli impiegati che afferiscono al idLabSelezionato
     *
     * @param idLabSelezionato   the id lab selezionato indica il laboratorio che è stato selezionato
     * @param matricoleAssociate the matricole associate indica tutte le matricole che vengono lette dal database.
     * @return the boolean
     * @throws SQLException the sql exception nel caso in cui non vengono lette le afferenze.
     */
    public boolean leggiAfferenzePerLaboratorioDAO(String idLabSelezionato, ArrayList<String> matricoleAssociate) throws SQLException;



    /**
     * Modifica laboratorio è la funzione che modifica il laboratorio selezionato
     *
     * @param idLabScelto      the id lab scelto
     * @param indirizzo        the indirizzo modificato
     * @param numeroTelefonico the numero telefonico modificato
     * @param rScientifico     the responsabile scientifico modificato
     * @return the boolean
     * @throws SQLException the sql exception nel caso in cui la modifica non va in porto.
     */
    public boolean modificaLaboratorioDAO(String idLabScelto,String indirizzo,String numeroTelefonico,String rScientifico) throws SQLException;



    /**
     * Leggi progetti della dalla tabella Gestione tutti i progetti associati a un laboratorio specifico.
     *
     * @param idLabScelto       the id lab scelto
     * @param progettiAssociati the progetti associati è la lista in cui vengono salvati i cup dei progetti associati.
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean leggiProgettiDAO(String idLabScelto, ArrayList<String> progettiAssociati) throws SQLException;
}
