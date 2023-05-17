package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;



//CLASSE CHE SERVE A FARE I DUMP DELL'AZIENDA...
public interface AziendaDAO {

    /*
        LE FUNZIONI SEGUENTI SERVONO AL DUMP DEI DATI[...]
    */
    public void getListImpiegatiDAO(ArrayList<String> matricolalist, ArrayList<String>nomelist, ArrayList<String> cognomelist, ArrayList<String> codiceFiscalelist, ArrayList<String> curriculumlist, ArrayList<Boolean> dirigentelist, ArrayList<String> tipoImpiegatolist, ArrayList<java.sql.Date> dataAssunzionelist, ArrayList<Date> dataLicenziamentolist, ArrayList<Float> stipendiolist, ArrayList<String> sessolist);
    public void getListLaboratorioDAO(ArrayList<String> idLablist, ArrayList<String> topiclist, ArrayList<String> indirizzolist, ArrayList<String> numeroTelefonicoList, ArrayList<Integer> numAfferentilist, ArrayList<String> matricolaResponsabileScientifico);
    public void getListProgettoDAO(ArrayList<String> nomelist, ArrayList<String> cuplist,  ArrayList<Float> budgetlist, ArrayList<Date> dataIniziolist, ArrayList<Date> dataFinelist, ArrayList<String> matricolaResponsabile, ArrayList<String> matricolaReferente);
    public void getListStoricoDAO(ArrayList<String> ruoloPrecedentelist, ArrayList<String> nuovoRuololist, ArrayList<java.sql.Date> dataScattolist, ArrayList<String> impiegatoMatricolalist);


    //todo aggiornamento database.
    /*
    * la seguente funzione aggiorna tutti gli storici gestiti all'interno del database
    * in modo tale che all'avvio del programma aggiorna tutti i dati relativi agli impiegati.
    *
    */
    public void aggiornaDatabaseDAO() throws SQLException;
}