package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;



//CLASSE CHE SERVE A FARE I DUMP DELL'AZIENDA...
public interface AziendaDAO {

    /*
        LE FUNZIONI SEGUENTI SERVONO AL DUMP DEI DATI[...]
    */

    /**
    * @param matricolalist indica la lista di matricole.
    * @param nomelist indica la lista di nomi dell'impiegato.
    * @param cognomelist indica la lista di cognomi degli impiegati.
    * @param codiceFiscalelist indica la lista dei cognomi degli impiegati.
    * @param curriculumlist indica la lista di curriculum.
    * @param dirigentelist indica la lista dell'attributo dirigente.
      @param tipoImpiegatolist indica la lista dei tipi degli impiegati.
      @param dataAssunzionelist indica la lista delle date di assunzione.
      @param dataLicenziamentolist indica la lista delle date di licenziamento.
      @param stipendiolist indica la lista di stipendi.
      @param sessolist indica la lista dei sessi degli impiegati.
    */
    public void getListImpiegatiDAO(ArrayList<String> matricolalist, ArrayList<String>nomelist, ArrayList<String> cognomelist, ArrayList<String> codiceFiscalelist, ArrayList<String> curriculumlist, ArrayList<Boolean> dirigentelist, ArrayList<String> tipoImpiegatolist, ArrayList<java.sql.Date> dataAssunzionelist, ArrayList<Date> dataLicenziamentolist, ArrayList<Float> stipendiolist, ArrayList<String> sessolist);


    /**
    * la seguente funzione prende dal database la lista dei laboratori
    * @param idLablist la lista di idLab dei laboratori.
    * @param indirizzolist la lista d'indirizzi dei laboratori.
    * @param topiclist la lista di topic dei laboratori.
    * @param numAfferentilist indica la lista dei numeri di afferenti per laboratorio.
    * @param matricolaResponsabileScientifico indica la lista delle matricole dei responasbili scientifici del lab.
    * @param numeroTelefonicoList indica la lista dei numeri telefonici per laboratorio.
    * */


    public void getListLaboratorioDAO(ArrayList<String> idLablist, ArrayList<String> topiclist, ArrayList<String> indirizzolist, ArrayList<String> numeroTelefonicoList, ArrayList<Integer> numAfferentilist, ArrayList<String> matricolaResponsabileScientifico);


    /**
    * @param nomelist indica la lista dei nomi dei progetti
    * @param cuplist indica la lista dei cup dei progetti
    * @param budgetlist indica la lista dei budget per progetto
    * @param dataIniziolist indica la data inizio per ogni progetto
    * @param dataFinelist indica la lista delle date finale per ogni progetto.
    * @param matricolaResponsabile indica la lista dei responsabili dei progetti.
    * @param matricolaReferente indica la lista dei referenti dei progetti.
    * */
    public void getListProgettoDAO(ArrayList<String> nomelist, ArrayList<String> cuplist,  ArrayList<Float> budgetlist, ArrayList<Date> dataIniziolist, ArrayList<Date> dataFinelist, ArrayList<String> matricolaResponsabile, ArrayList<String> matricolaReferente);


    /**
     * Gets list storico dao.
     *
     * @param ruoloPrecedentelist    the ruolo precedentelist indica la lista dei ruoli precedenti.
     * @param nuovoRuololist         the nuovo ruololist indica la lista dei nuovi ruoli deigli impiegati.
     * @param dataScattolist         the data scattolist indica la data di scatto del nuovo ruolo.
     * @param impiegatoMatricolalist the impiegato matricolalist indica l'impiegato a cui si associa lo storico.
     */
    public void getListStoricoDAO(ArrayList<String> ruoloPrecedentelist, ArrayList<String> nuovoRuololist, ArrayList<java.sql.Date> dataScattolist, ArrayList<String> impiegatoMatricolalist);



    /**
    * La seguente funzione aggiorna tutti gli storici gestiti all'interno del database
    * in modo tale che all'avvio del programma aggiorna tutti i dati relativi agli impiegati.
    *
    */
    public void aggiornaDatabaseDAO() throws SQLException;
}