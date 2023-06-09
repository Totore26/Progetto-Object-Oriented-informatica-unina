package CONTROLLER;
import DAO.*;
import ImplementazionePostgresDAO.*;
import MODEL.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Controller.
 */
public class Controller {

    private List<Impiegato> listaImpiegato = new ArrayList<>();
    private List<Progetto> listaProgetto = new ArrayList<>();
    private List<Laboratorio> listaLaboratorio = new ArrayList<>();
    private List<Storico> listaStorico = new ArrayList<>();


    /**
     * Instantiates a new Controller.
     */
    public  Controller() {
        dumpdati();
    }


    //____________________________________________FUNZIONI PER IL LOAD DEI DATI_______________________________________//


    //il dumpDati non inizializza le relazioni di afferenza e la gestione...
    private void dumpdati() {
        aggiornaDatabase();
        dumpDatiImpiegato();
        dumpDatiLaboratorio();
        dumpDatiStorico();
        dumpDatiProgetto();

    }


    /**
     * Aggiorna database.
     *
     * @throws SQLException the sql exception
     */
    public void aggiornaDatabase(){
        AziendaDAO aziend = new AziendaPostgresDAO();
        try {
            aziend.aggiornaDatabaseDAO();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    /**
     * Dump dati impiegato.
     */
    public void dumpDatiImpiegato() {
        AziendaDAO aziendaDAO = new AziendaPostgresDAO();

        ArrayList<String> matricolalist = new ArrayList<>();
        ArrayList<String> nomelist = new ArrayList<>();
        ArrayList<String> cognomelist = new ArrayList<>();
        ArrayList<String> codiceFiscalelist = new ArrayList<>();
        ArrayList<String> curriculumlist = new ArrayList<>();
        ArrayList<Boolean> dirigentelist = new ArrayList<>();
        ArrayList<String> tipoImpiegatolist = new ArrayList<>();
        ArrayList<java.sql.Date> dataAssunzionelist = new ArrayList<>();
        ArrayList<Date> dataLicenziamentolist = new ArrayList<>();
        ArrayList<Float> stipendiolist = new ArrayList<>();
        ArrayList<String> sessolist = new ArrayList<>();

        aziendaDAO.getListImpiegatiDAO(matricolalist, nomelist, cognomelist, codiceFiscalelist, curriculumlist, dirigentelist, tipoImpiegatolist, dataAssunzionelist, dataLicenziamentolist, stipendiolist, sessolist);

        //inizializzo la lista di Impiegati
        for (int i = 0; i < matricolalist.size(); i++) {
            listaImpiegato.add(new Impiegato(matricolalist.get(i), nomelist.get(i), cognomelist.get(i), codiceFiscalelist.get(i), curriculumlist.get(i), dirigentelist.get(i), tipoImpiegatolist.get(i), dataAssunzionelist.get(i), dataLicenziamentolist.get(i), stipendiolist.get(i), sessolist.get(i)));
        }
    }

    /**
     * Dump dati laboratorio.
     */
    public void dumpDatiLaboratorio() {
        AziendaDAO aziendaDAO = new AziendaPostgresDAO();

        ArrayList<String> idLablist = new ArrayList<>();
        ArrayList<String> topiclist = new ArrayList<>();
        ArrayList<String> indirizzolist = new ArrayList<>();
        ArrayList<String> numeroTelefonicoList = new ArrayList<>();
        ArrayList<Integer> numAfferentilist = new ArrayList<>();
        ArrayList<String> matricolaResponsabileScientifico = new ArrayList<>();

        aziendaDAO.getListLaboratorioDAO(idLablist, topiclist, indirizzolist, numeroTelefonicoList, numAfferentilist, matricolaResponsabileScientifico);

        for (int i = 0; i < idLablist.size(); i++) {
            Impiegato rScientifico = null;
            //trovo prima l'impiegato che è responsabile scientifico di quel laboratorio e poi istanzio il laboratorio...
            for (Impiegato imp : listaImpiegato) {
                if (imp.getMatricola().equals(matricolaResponsabileScientifico.get(i))) {
                    rScientifico = imp;
                }
            }
            listaLaboratorio.add(new Laboratorio(idLablist.get(i), topiclist.get(i), indirizzolist.get(i), numeroTelefonicoList.get(i), numAfferentilist.get(i), rScientifico));
        }
    }

    /**
     * Dump dati storico.
     */
    public void dumpDatiStorico() {
        AziendaDAO aziendaDAO = new AziendaPostgresDAO();

        ArrayList<String> ruoloPrecedentelist = new ArrayList<>();
        ArrayList<String> nuovoRuololist = new ArrayList<>();
        ArrayList<java.sql.Date> dataScattolist = new ArrayList<>();
        ArrayList<String> impiegatoMatricolalist = new ArrayList<>();

        aziendaDAO.getListStoricoDAO(ruoloPrecedentelist, nuovoRuololist, dataScattolist, impiegatoMatricolalist);


        for (int i = 0; i < nuovoRuololist.size(); i++) {
            Impiegato impiegato = null;
            //ciclo che associa ad ogni impiegato i suoi storici ed ad ogni storico il suo impiegato[...]
            for (Impiegato imp : listaImpiegato) {
                if (imp.getMatricola().equals(impiegatoMatricolalist.get(i))) {
                    impiegato = imp;
                    break;
                }
            }

            Storico s = new Storico(ruoloPrecedentelist.get(i), nuovoRuololist.get(i), dataScattolist.get(i), impiegato);
            listaStorico.add(s);
        }


    }

    /**
     * Dump dati progetto.
     */
    public void dumpDatiProgetto() {
        AziendaDAO aziendaDAO = new AziendaPostgresDAO();

        ArrayList<String> nomelist = new ArrayList<>();
        ArrayList<String> cuplist = new ArrayList<>();
        ArrayList<Float> budgetlist = new ArrayList<>();
        ArrayList<Date> dataIniziolist = new ArrayList<>();
        ArrayList<Date> dataFinelist = new ArrayList<>();
        ArrayList<String> matricolaResponsabile = new ArrayList<>();
        ArrayList<String> matricolaReferente = new ArrayList<>();

        aziendaDAO.getListProgettoDAO(nomelist, cuplist, budgetlist, dataIniziolist, dataFinelist, matricolaResponsabile, matricolaReferente);

        //a questo punto istanzio i progetti con i propri referenti e responsabili...
        for (int i = 0; i < cuplist.size(); i++) {
            Impiegato responsabileAttuale = null;
            Impiegato referenteAttuale = null;

            for (Impiegato imp : listaImpiegato) {
                if (matricolaResponsabile.get(i).equals(imp.getMatricola())) {
                    responsabileAttuale = imp;
                }
                if (matricolaReferente.get(i).equals(imp.getMatricola())) {
                    referenteAttuale = imp;
                }
            }


            Progetto p = new Progetto(nomelist.get(i), cuplist.get(i), budgetlist.get(i), dataIniziolist.get(i), dataFinelist.get(i), responsabileAttuale, referenteAttuale);
            listaProgetto.add(p);
        }
    }

//____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//




//_______________________________________FUNZIONI PER L'IMPIEGATO_____________________________________________________//


    /**
     * Aggiungi impiegato al database, nel caso affermativo anche nel model
     *
     * @param matricola         the matricola
     * @param nome              the nome
     * @param cognome           the cognome
     * @param codiceFiscale     the codice fiscale
     * @param curriculum        the curriculum
     * @param tipoImpiegato     the tipo impiegato
     * @param dirigente         the dirigente
     * @param dataAssunzione    the data assunzione
     * @param dataLicenziamento the data licenziamento
     * @param stipendio         the stipendio
     * @param sesso             the sesso
     * @throws SQLException the sql exception
     *
     *  funzione per aggiungere l'impiegato
     *  le eccezioni sono riportate direttamente nella GUI in modo tale che è l'utente stesso a
     *  gestire i dati sbagliati che ha inserito...
     */
    public void aggiungiImpiegato(String matricola, String nome, String cognome, String codiceFiscale, String curriculum, String tipoImpiegato, boolean dirigente, Date dataAssunzione, Date dataLicenziamento, float stipendio, String sesso) throws SQLException {
        ImpiegatoDAO i = new ImpiegatoPostgresDAO();

        boolean control = i.aggiungiImpiegatoDAO(matricola, nome, cognome, codiceFiscale, curriculum, tipoImpiegato, dirigente, dataAssunzione, dataLicenziamento, stipendio, sesso);

        if (control) {
            System.out.println("Impiegato aggiunto con successo!");
            Impiegato a = new Impiegato(matricola, nome, cognome, codiceFiscale, curriculum, dirigente, tipoImpiegato, dataAssunzione, dataLicenziamento, stipendio, sesso);
            listaImpiegato.add(a);
        }
    }


    /**
     * Elimina impiegato e l'elimina anche dal model nel caso affermativo del database
     *
     * @param matricolaSelezionata the matricola selezionata
     * @throws SQLException the sql exception
     */
    //funzione per eliminare l'impiegato
    public void eliminaImpiegato(String matricolaSelezionata) throws SQLException {
        ImpiegatoDAO i = new ImpiegatoPostgresDAO();

        boolean control = i.eliminaImpiegatoDAO(matricolaSelezionata);

        if (control) {
            System.out.println("Eliminazione avvenuta con successo ...!");

            //se L'ELIMINAZIONE HA AVUTO SUCCESSO ALLORA ELIMINO ANCHE DAL MODEL L'IMPIEGATO...
            for (Impiegato imp : listaImpiegato) {
                if (matricolaSelezionata.equals(imp.getMatricola())) {
                    listaImpiegato.remove(imp);
                    break;
                }
            }
        } else {
            System.out.println("Impossibile rimuovere l'Impiegato...");
        }
    }


    /**
     * Modifica impiegato e nel caso affermativo anche nel model
     *
     * @param matricolaSelezionata the matricola selezionata
     * @param curriculum           the curriculum
     * @param dirigente            the dirigente
     * @param dataLicenziamento    the data licenziamento
     * @param stipendio            the stipendio
     * @throws SQLException the sql exception
     *
     *
     * Funzione che modifica i campi modificabili dell'impiegato...
     * La seguente funzione salva le modifiche fatte all'impiegato nel database
     * nel caso affermativo aggiorna l'impiegato in questione anche nel model.
     */
    public void modificaImpiegato(String matricolaSelezionata, String curriculum, boolean dirigente, java.util.Date dataLicenziamento, float stipendio) throws SQLException {

        ImpiegatoDAO i = new ImpiegatoPostgresDAO();

        boolean control = i.modificaImpiegatoDAO(matricolaSelezionata, curriculum, dirigente, dataLicenziamento, stipendio);


        //se modifico l'impiegato nel database allora lo modifico anche nel MODEL
        if (control) {
            for (Impiegato imp : listaImpiegato)
                if (imp.getMatricola().equals(matricolaSelezionata)) {
                    imp.setStipendio(stipendio);
                    imp.setDirigente(dirigente);
                    imp.setCurriculum(curriculum);
                    imp.setDataLicenziamento(dataLicenziamento);
                    break;
                }
        }

    }


    /**
     * Leggi storici impiegato ritorna l'array di scatti di carriera per un impiegato.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the date [ ]
     *
     *
     * La seguente funzione quando l'utente nella gui richiede di vedere il profilo dell'Impiegato
     * inizializza la sua lista di storici (attributo listaStorico) e restituisce alla gui
     * le varie date di scatti di carriera, la quale mostra in che data ha fatto gli scatti
     */
    public Date[]  leggiStoriciImpiegato(String matricolaSelezionata) {

        ArrayList<String> listaStoriciGUI = new ArrayList<>();

        //trovo l'Impiegato a cui si riferisce
        Impiegato impDaVisualizzare = null;
        for (Impiegato imp : listaImpiegato)
            if (imp.getMatricola().equals(matricolaSelezionata))
                impDaVisualizzare = imp;

        //Inizalizzo la lista degli storici dell'Impiegato
        for (Storico s : listaStorico)
            if (s.getMatricola().equals(matricolaSelezionata)) {
                assert impDaVisualizzare != null;
                impDaVisualizzare.aggiungiStorico(s);
            }

        //a questo punto salvo nell'ArrayList<> le date scatto junior middle e senior...

        //trovo la data scatto Junior :
        Date[] listaScatti = new Date[3];

        for(Storico s: impDaVisualizzare.getListaStorico()){
            if(s.getNuovoRuolo().equals("junior")){
                listaScatti[0] = (Date) s.getDataScatto();
            }

            if(s.getNuovoRuolo().equals("middle")){
                listaScatti[1] = (Date) s.getDataScatto();
            }

            if(s.getNuovoRuolo().equals("senior")){
                listaScatti[2] = (Date) s.getDataScatto();
            }
        }
        return listaScatti;
    }


    /**
     * Leggi afferenze impiegato array list.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the array list degli idLab associati all'Impiegato
     *
     *
     *    La seguente funzione leggiAfferenzaImpiegato fa in modo tale che se richiesto dalla GUI,
     *    recupera dal database i laboratori in cui afferisce la matricolaSelezionata,
     *    inizializza L'arraylist dell'impiegato in questione e ritorna alla gui la lista di laboratori a cui
     *    è associato, in questo modo carica i dati dal database solamente quando sono richiesti dall'utente.
     */
    public ArrayList<String> leggiAfferenzeImpiegato(String matricolaSelezionata) {
        ImpiegatoDAO i = new ImpiegatoPostgresDAO();

        //step 0: trovo l'impiegato a cui si riferisce la vista
        Impiegato impDaVisualizzare = null;
        for (Impiegato imp : listaImpiegato)
            if (imp.getMatricola().equals(matricolaSelezionata)) {
                impDaVisualizzare = imp;
                break;
            }


        //2 step : trovo i laboratori associati
        ArrayList<String> laboratoriAssociati = new ArrayList<>();
        boolean control = i.leggiAfferenzePerImpiegatoDAO(matricolaSelezionata, laboratoriAssociati);

        //2 step : inzializzo la lista di laboratori a cui afferisce l'impiegato
        if (control) {
            for (Laboratorio lab : listaLaboratorio)
                for (String l : laboratoriAssociati)
                    if (lab.getIdLab().equals(l)) {
                        assert impDaVisualizzare != null;
                        impDaVisualizzare.aggiungiLaboratorio(lab);
                    }
        }

        return laboratoriAssociati;

    }


    /**
     * Aggiungi afferenza.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @param idlabSelezionato     the idlab selezionato
     * @throws SQLException the sql exception
     *
     *
     * La seguente funzione aggiunge al database l'afferenza,
     * nel caso affermativo allora aggiunge anche alla lista di afferenze dell'impiegato.
     */
    public void aggiungiAfferenza(String matricolaSelezionata, String idlabSelezionato) throws SQLException{

        //come prima cosa trovo gli oggetti della matricolaScelta e del laboratorio...
        Impiegato impiegatoScelto = null;
        for(Impiegato imp : listaImpiegato)
            if(imp.getMatricola().equals(matricolaSelezionata))
            {
                impiegatoScelto = imp;
                break;
            }

        //come seconda cosa trovo l'oggetto laboratorio
        Laboratorio laboratorioScelto = null;
        for(Laboratorio lab : listaLaboratorio)
            if(lab.getIdLab().equals(idlabSelezionato))
            {
                laboratorioScelto = lab;
                break;
            }

        ImpiegatoDAO i = new ImpiegatoPostgresDAO();

        boolean control = i.aggiungiAfferenzaDAO(matricolaSelezionata,idlabSelezionato);

        //se l'inserimento nel database è andato a buon fine allora aggiungo alla lista dell'imp il lab e nel laboratorio lo aggiungo alla lista degli afferenti
        if(control){
            impiegatoScelto.aggiungiLaboratorio(laboratorioScelto);
            laboratorioScelto.aggiungiAfferente(impiegatoScelto);
        }

    };


    /**
     * Elimina afferenza.
     *
     * @param matricolaScelta  the matricola scelta
     * @param idlabSelezionato the idlab selezionato
     * @throws SQLException the sql exception
     *
     *
     * LA SEGUENTE FUNZIONE ELIMINA L'AFFERENZA SELEZIONATA PER UN IMPIEGATO
     * NEL CASO IN CUI ELIMINA NEL DATABASE ALLORA ELIMINA ANCHE NEL MODEL...
     * NEL CASO DI ECCEZIONI ALLORA MANDA IL WARNING ALLA GUI.
     */
    public void eliminaAfferenza(String matricolaScelta, String idlabSelezionato) throws SQLException{
        //come prima cosa trovo gli oggetti della matricolaScelta e del laboratorio...
        Impiegato impiegatoScelto = null;
        for(Impiegato imp : listaImpiegato)
            if(imp.getMatricola().equals(matricolaScelta))
            {
                impiegatoScelto = imp;
                break;
            }

        //come seconda cosa trovo l'oggetto laboratorio
        Laboratorio laboratorioScelto = null;
        for(Laboratorio lab : listaLaboratorio)
            if(lab.getIdLab().equals(idlabSelezionato))
            {
                laboratorioScelto = lab;
                break;
            }


        ImpiegatoDAO i = new ImpiegatoPostgresDAO();

        boolean control = i.eliminaAfferenzaDAO(matricolaScelta,idlabSelezionato);

        if (control) {
            //se l'eliminazione ha avuto successo allora elimino anche dal model
            assert impiegatoScelto != null;
            impiegatoScelto.removeAfferenzaImp(idlabSelezionato);

        }

    }






//____________________________________________________________________________________________________________________//
//____________________________________________________________________________________________________________________//






//____________________________________________FUNZIONI SU LABORATORIO_________________________________________________//


    /**
     * Aggiungi laboratorio.
     *
     * @param idLab            the id lab
     * @param topic            the topic
     * @param indirizzo        the indirizzo
     * @param numeroTelefonico the numero telefonico
     * @param rScientifico     the r scientifico
     * @throws SQLException the sql exception
     *
     *
     * LA SEGUENTE FUNZIONE AGGIUNGE SE RICHIESTO ALL'INTERNO DELLA GUI UN NUOVO
     * LABORATORIO, SE LA QUERY VIENE ESEGUITA AGGIORNA ANCHE IL MODEL
     */
    public void aggiungiLaboratorio(String idLab, String topic, String indirizzo,String numeroTelefonico, String rScientifico) throws SQLException{

        LaboratorioDAO l = new LaboratorioPostgresDAO();

        boolean control = l.aggiungiLaboratorioDAO(idLab,topic,indirizzo,numeroTelefonico,rScientifico);

        if(control){

            //trovo il responsabile scientifico
            Impiegato respScientifico = null;
            for(Impiegato i : listaImpiegato)
                if(i.getMatricola().equals(rScientifico)){
                    respScientifico=i;
                }

            //a questo punto posso creare il mio oggeto Laboratorio e aggiungerlo anche al model...
            Laboratorio nuovoLab = new Laboratorio(idLab,topic,indirizzo,numeroTelefonico,respScientifico);

            listaLaboratorio.add(nuovoLab);

        }
    }


    /**
     * Elimina laboratorio.
     *
     * @param idLabScelto the id lab scelto
     * @throws SQLException the sql exception
     *
     *
     * LA SEGUENTE FUNZIONE ELIMINA SE RICHIESTO ALL'INTERNO DELLA GUI UN
     * LABORATORIO DANDO UN IDLAB , SE LA QUERY VIENE ESEGUITA AGGIORNA ANCHE IL MODEL
     */
    public void eliminaLaboratorio(String idLabScelto) throws SQLException{
        LaboratorioDAO l = new LaboratorioPostgresDAO();

        boolean control = l.eliminaLaboratorioDAO(idLabScelto);

        if (control) {
            //se L'ELIMINAZIONE HA AVUTO SUCCESSO ALLORA ELIMINO ANCHE DAL MODEL IL LABORATORIO...
            for (Laboratorio lab : listaLaboratorio)
                if (idLabScelto.equals(lab.getIdLab())) {
                    listaLaboratorio.remove(lab);
                    break;
                }

        }
    }


    /**
     * Modifica laboratorio.
     *
     * @param idLabScelto      the id lab scelto
     * @param indirizzo        the indirizzo
     * @param numeroTelefonico the numero telefonico
     * @param rScientifico     the r scientifico
     * @throws SQLException the sql exception
     *
     *
     * LA SEGUENTE FUNZIONE MODIFICA SE RICHIESTO ALL'INTERNO DELLA GUI UN
     * LABORATORIO DANDO UN IDLAB , SE LA QUERY VIENE ESEGUITA AGGIORNA ANCHE IL MODEL
     */
    public void modificaLaboratorio(String idLabScelto,String indirizzo,String numeroTelefonico,String rScientifico) throws SQLException{
        LaboratorioDAO lab = new LaboratorioPostgresDAO();

        boolean control = lab.modificaLaboratorioDAO(idLabScelto,indirizzo,numeroTelefonico,rScientifico);

        if(control){
            //allora modifico anche il model...
            Laboratorio laboratorio = null;
            for(Laboratorio l : listaLaboratorio)
                if(l.getIdLab().equals(idLabScelto)){
                    laboratorio = l;
                }

            //trovo il nuovo potenziale responsabile scientifico...
            Impiegato respScientificoNuovo = null;
            for(Impiegato i : listaImpiegato) {
                if (i.getMatricola().equals(rScientifico)) {
                    respScientificoNuovo = i;
                }
            }

            //trovato il laboratorio allora setto tutti gli attributi.
            assert laboratorio != null;
            laboratorio.setNumeroTelefonico(numeroTelefonico);
            laboratorio.setIndirizzo(indirizzo);
            laboratorio.setRScientifico(respScientificoNuovo);

        }
    }


    /**
     * Leggi progetti su cui lavora array list.
     *
     * @param idLabScelto the id lab scelto
     * @return the array list
     * @throws SQLException the sql exception
     *
     *
     * La seguente funzione preso in ingresso il laboratorio da visualizzare carica in memoria
     * (nel model) tutti quei progetti sui cui il laboratorio lavora.
     *
     * Ritorna quindi la serie di CUP che gestiscono tale laboratorio.
     * ricorda che l'associazione fra laboratori e progetti è molti a molti.
     *
     */
    public ArrayList<String> leggiProgettiSuCuiLavora(String idLabScelto) throws SQLException{
        LaboratorioDAO l = new LaboratorioPostgresDAO();
        //come prima cosa recupero qual è il laboratorio scelto
        Laboratorio laboratorioScelto=null;
        for(Laboratorio lab : listaLaboratorio){
            if(lab.getIdLab().equals(idLabScelto)){
                laboratorioScelto=lab;
            }
        }

        //2 step : trovo i Progetti associati
        ArrayList<String> progettiAssociati = new ArrayList<>();
        boolean control = l.leggiProgettiDAO(idLabScelto, progettiAssociati);

        //2 step : inzializzo la lista di progetti su cui lavora il laboratorio
        if (control) {
            for (Progetto prog : listaProgetto)
                for (String p : progettiAssociati)
                    if (prog.getCup().equals(p)) {
                        assert laboratorioScelto != null;
                        laboratorioScelto.aggiungiProgetto(prog);
                    }
        }

        return progettiAssociati;


    }


    /**
     * Leggi afferenze laboratorio array list.
     *
     * @param idLabSelezionato the id lab selezionato
     * @return the array list
     *
     *
     *    La seguente funzione leggiAfferenzaLaboratorio fa in modo tale che se richiesto dalla GUI,
     *    recupera dal database le matricole afferenti al lab selezionato,
     *    inizializza L'arraylist del laboratorio in questione e ritorna alla gui la lista di matricole a cui
     *    è associato, in questo modo carica i dati dal database solamente quando sono richiesti dall'utente.
     */
    public ArrayList<String> leggiAfferenzeLaboratorio(String idLabSelezionato) {
        LaboratorioDAO i = new LaboratorioPostgresDAO();

        //step 0: trovo l'impiegato a cui si riferisce la vista
        Laboratorio labDaVisualizzare = null;
        for (Laboratorio lab : listaLaboratorio)
            if (lab.getIdLab().equals(idLabSelezionato)) {
                labDaVisualizzare = lab;
                break;
            }

        //2 step : trovo i matricole associati
        ArrayList<String> matricoleAssociate = new ArrayList<>();
        boolean control = false;
        try {
            control = i.leggiAfferenzePerLaboratorioDAO(idLabSelezionato, matricoleAssociate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //2 step : Inizializzo la lista di afferenze che ha un laboratorio
        if (control) {
            for (Impiegato imp : listaImpiegato)
                for (String l : matricoleAssociate)
                    if (imp.getMatricola().equals(l)) {
                        assert labDaVisualizzare != null;
                        labDaVisualizzare.aggiungiMatricola(imp);
                    }
        }

        return matricoleAssociate;

    }



    //________________________________________________________________________________________________________________//
    //________________________________________________________________________________________________________________//





//____________________________________________FUNZIONI SU PROGETTO_________________________________________________//


    /**
     * Aggiungi progetto.
     *
     * @param nome         the nome
     * @param cup          the cup
     * @param budget       the budget
     * @param dataInizio   the data inizio
     * @param dataFine     the data fine
     * @param responsabile the responsabile
     * @param referente    the referente
     * @throws SQLException the sql exception nel caso in cui non viene aggiunto il progetto
     *
     *
     * Funzione che presi in input la lista di parametri per creare un Progetto
     * lo inserisce all'interno del database e aggiorna la lista di progetti[...]
     */
    public void aggiungiProgetto(String nome, String cup, float budget, Date dataInizio, Date dataFine,String responsabile, String referente) throws SQLException{
        ProgettoDAO p = new ProgettoPostgresDAO();

        boolean control = p.aggiungiProgettoDAO(nome,cup,budget,dataInizio,dataFine,responsabile,referente);
        if(control){

            //cerco il responsabile fra gli impiegati:
            Impiegato responsabileI = null;
            for(Impiegato i : listaImpiegato){
                if(i.getMatricola().equals(responsabile)){
                    responsabileI = i;
                    break;
                }
            }

            //cerco il referente fra i responsabili
            Impiegato referenteI = null;
            for(Impiegato i : listaImpiegato){
                if(i.getMatricola().equals(referente)){
                    referenteI = i;
                    break;
                }
            }

            Progetto progetto = new Progetto(nome,cup,budget,dataInizio,dataFine,responsabileI,referenteI);
            listaProgetto.add(progetto);
        }

    }


    /**
     * Elimina progetto.
     *
     * @param cup the cup
     * @throws SQLException the sql exception
     *
     *
     * La seguente funzione prende in ingresso il cup del progetto da eliminare
     * lo elimina nel database e se ha successo lo elimina anche nel MODEL[...]
     */
    public void eliminaProgetto(String cup) throws SQLException {
        ProgettoDAO p = new ProgettoPostgresDAO();

        boolean control = p.eliminaProgettoDAO(cup);

        if(control){

            //allora elimino anche dal model...
            for(Progetto pro : listaProgetto){
                if(pro.getCup().equals(cup)){
                    listaProgetto.remove(pro);
                    break;
                }
            }
        }


    }


    /**
     * Modifica progetto.
     *
     * @param cupScelto    the cup scelto
     * @param budget       the budget
     * @param dataFine     the data fine
     * @param responsabile the responsabile
     * @param referente    the referente
     * @throws SQLException the sql exception
     *
     *
     * La seguente funzione prende in ingresso i nuovi dati inseriti all'interno del Progetto
     * attua la modifica nel database, se essa ha successo allora cambia anche i dati all'interno
     * del MODEL[...]
     */
    public void modificaProgetto(String cupScelto, float budget, Date dataFine, String responsabile, String referente) throws SQLException{
        ProgettoDAO pro = new ProgettoPostgresDAO();

        boolean control = pro.modificaProgettoDAO(cupScelto,budget,dataFine,responsabile,referente);

        if(control){

            //trovo il mio oggetto Progetto[...]
            Progetto progettoScelto = null;
            for(Progetto o : listaProgetto)
                if(o.getCup().equals(cupScelto))
                    progettoScelto = o;

            //se il controllo ha avuto successo allora trovo il responsabile e aggiorno il mio model
            Impiegato respNuovo = null;
            for(Impiegato i : listaImpiegato)
                if(i.getMatricola().equals(responsabile))
                    respNuovo = i;


            //se il controllo ha avuto successo allora trovo il referente e aggiorno il mio model
            Impiegato refNuovo = null;
            for(Impiegato i : listaImpiegato)
                if(i.getMatricola().equals(referente))
                    refNuovo = i;

            //a questo punto aggiorno il mio model con i nuovi dati avuti dalla gui
            progettoScelto.setBudget(budget);
            progettoScelto.setDataFine(dataFine);
            progettoScelto.setReferente(refNuovo);
            progettoScelto.setResponsabile(respNuovo);
        }
    }


    /**
     * Leggi gestioni progetto array list.
     *
     * @param cupScelto the cup scelto
     * @return the array list
     *
     *
     * La seguente funzione dato in ingresso il cup del progetto legge dal database i dati relativi
     * inizializzando la lista di laboratori associati a un progetto.
     */
    public ArrayList<String> leggiGestioniProgetto(String cupScelto){
        ProgettoDAO i = new ProgettoPostgresDAO();

        //step 1°: trovo l'impiegato a cui si riferisce la vista
        Progetto prodaVisualizzare = null;
        for (Progetto pro : listaProgetto)
            if (pro.getCup().equals(cupScelto)) {
                prodaVisualizzare = pro;
                break;
            }

        //2 step : trovo i laboratori in gestione
        ArrayList<String> labGestione = new ArrayList<>();
        boolean control = i.leggiGestionePerProgettoDAO(cupScelto,labGestione);

        //2° step: inizializzo la lista di laboratori gestiti del progetto in questione.
        if (control) {

            for(Laboratorio lab : listaLaboratorio){
                for(String l : labGestione)
                    if(lab.getIdLab().equals(l)){

                        assert prodaVisualizzare != null;
                        prodaVisualizzare.aggiungiGestione(lab);
                    }
            }


        }

        return labGestione;
    }


    /**
     * Aggiungi gestione.
     *
     * @param cupScelto   the cup scelto
     * @param idlabScelto the idlab scelto
     * @throws SQLException the sql exception
     *
     *
     * La seguente funzione dato in input il cup del progetto e il laboratorio da gestire
     * aggiunge la gestione nel database e inzializza nel caso le liste di progetti/laboratori nel MODEL.
     */
    public void aggiungiGestione(String cupScelto, String idlabScelto)throws SQLException{
        ProgettoDAO progettoDAO= new ProgettoPostgresDAO();

        boolean control = progettoDAO.aggiungiGestioneDAO(cupScelto,idlabScelto);

        if(control){
            //trovo il progetto in questione nel model
            Progetto proDaVisualizzare = null;
            for(Progetto pro : listaProgetto)
                if(pro.getCup().equals(cupScelto)){
                    proDaVisualizzare = pro;
                    break;
                }
            //trovo il laboratorio in questione nel model
            Laboratorio daGestiore = null;
            for(Laboratorio lab : listaLaboratorio){
                if(lab.getIdLab().equals(idlabScelto)){
                    daGestiore = lab;
                    break;
                }
            }

            //come ultimo step aggiungo la gestione al laboratorio in questione e viceversa nella lista dei lab
            proDaVisualizzare.aggiungiGestione(daGestiore);
            daGestiore.aggiungiProgetto(proDaVisualizzare);
        }


    }


    /**
     * Elimina gestione.
     *
     * @param cupScelto   the cup scelto
     * @param idlabScelto the idlab scelto
     * @throws SQLException the sql exception
     *
     *
     * La seguente funzione dato in input il cup del progetto e il laboratorio in gestione
     * elimina dal database la gestione e aggiorna le liste relative nel MODEL.
     */
    public void eliminaGestione(String cupScelto, String idlabScelto)throws SQLException{

        //come prima cosa trovo gli oggetti della matricolaScelta e del laboratorio...
        Progetto progettoScelto = null;
        for(Progetto pro : listaProgetto)
            if(pro.getCup().equals(cupScelto))
            {
                progettoScelto = pro;
                break;
            }

        //come seconda cosa trovo l'oggetto laboratorio
        Laboratorio laboratorioScelto = null;
        for(Laboratorio lab : listaLaboratorio)
            if(lab.getIdLab().equals(idlabScelto))
            {
                laboratorioScelto = lab;
                break;
            }


        ProgettoDAO pro = new ProgettoPostgresDAO();

        boolean control = pro.eliminaGestioneDAO(cupScelto,idlabScelto);

        if (control) {
            //se l'eliminazione ha avuto successo allora elimino anche dal model
            assert progettoScelto != null;
            progettoScelto.removeGestioneProgetto(laboratorioScelto);

        }

    }





    //________________________________________________________________________________________________________________//
    //________________________________________________________________________________________________________________//



//___________________________________________FUNZIONI PER LA GUI__________________________________________________//
    /**
     * Gets lista impiegato matricole gui.
     *
     * @return the lista impiegato matricole gui
     *
     */
    public ArrayList<String> getListaImpiegatoMatricoleGUI() {
        ArrayList<String> stringMatricole = new ArrayList<>();

        for (Impiegato imp : listaImpiegato)
            stringMatricole.add(imp.getMatricola());

        return stringMatricole;
    }


    /**
     * Gets lista impiegato nomi gui.
     *
     * @return the lista impiegato nomi gui
     */
    public List<String> getListaImpiegatoNomiGUI() {
        ArrayList<String> stringNomi = new ArrayList<>();

        for (Impiegato imp : listaImpiegato)
            stringNomi.add(imp.getNome());

        return stringNomi;
    }



    /**
     * Gets lista impiegato cognomi gui.
     *
     * @return the lista impiegato cognomi gui
     */
    public List<String> getListaImpiegatoCognomiGUI() {
        ArrayList<String> stringCognomi = new ArrayList<>();

        for (Impiegato imp : listaImpiegato)
            stringCognomi.add(imp.getCognome());

        return stringCognomi;
    }



    /**
     * Get singolo impiegato nome profilo gui string.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the string
     *
    *
    * LA SEGUENTE FUNZIONE DATA IN INPUT LA MATRICOLA SELEZIONATA RITORNA ALLA GUI
    * LA LISTA DI ATTRIBUTI DELL'IMPIEGATO SELEZIONATO
    */
    public String getSingoloImpiegatoNomeProfiloGUI(String matricolaSelezionata){

        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.getNome();

    }



    /**
     * Get singolo impiegato cognome profilo gui string.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the string
     */
    public String getSingoloImpiegatoCognomeProfiloGUI(String matricolaSelezionata){

        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.getCognome();

    }



    /**
     * Get singolo impiegato curriculum profilo gui string.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the string
     */
    public String getSingoloImpiegatoCurriculumProfiloGUI(String matricolaSelezionata){

        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.getCurriculum();

    }



    /**
     * Get singolo impiegato codice fiscale profilo gui string.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the string
     */
    public String getSingoloImpiegatoCodiceFiscaleProfiloGUI(String matricolaSelezionata){

        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.getCodiceFiscale();

    }



    /**
     * Get singolo impiegato dirigente profilo gui boolean.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the boolean
     */
    public boolean getSingoloImpiegatoDirigenteProfiloGUI(String matricolaSelezionata){

        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.isDirigente();

    }



    /**
     * Get singolo impiegato tipo profilo gui string.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the string
     */
    public String getSingoloImpiegatoTipoProfiloGUI(String matricolaSelezionata){

        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.getTipoImpiegato();

    }



    /**
     * Get singolo impiegato data ass profilo gui java . util . date.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the java . util . date
     */
    public java.util.Date getSingoloImpiegatoDataAssProfiloGUI(String matricolaSelezionata){

        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.getDataAssunzione();

    }



    /**
     * Get singolo impiegato data lic profilo gui java . util . date.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the java . util . date
     */
    public java.util.Date getSingoloImpiegatoDataLicProfiloGUI(String matricolaSelezionata){

        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.getDataLicenziamento();

    }



    /**
     * Get singolo impiegato stipendio profilo gui float.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the float
     */
    public float getSingoloImpiegatoStipendioProfiloGUI(String matricolaSelezionata){
        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.getStipendio();
    }



    /**
     * Get singolo impiegato sessoo profilo gui string.
     *
     * @param matricolaSelezionata the matricola selezionata
     * @return the string
     */
    public String getSingoloImpiegatoSessooProfiloGUI(String matricolaSelezionata){
        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        return matScelta.getSesso();
    }




    /*
    *   Funzioni che servono per il Profilo laboratorio
    *   esse restituiscono tutti gli attributi singolarmente dato un idlab scelto nella gui.
    */

    /**
     * Get lista codici laboratori gui array list.
     *
     * @return the array list
     */
    //funzione che ritorna codici dei laboratori
    public ArrayList<String> getListaCodiciLaboratoriGUI(){
        ArrayList<String> idlablist = new ArrayList<String>();
        for (Laboratorio laboratorio : listaLaboratorio) {
            idlablist.add(laboratorio.getIdLab());
        }
        return idlablist;
    }



    /**
     * Get lista laboratorio gui.
     *
     * @param idlablist        the idlablist
     * @param topiclist        the topiclist
     * @param rscientificolist the rscientificolist
     */
    //funzione che serve per fare le tabelle
    public void getListaLaboratorioGUI(ArrayList<String> idlablist, ArrayList<String> topiclist, ArrayList<String> rscientificolist){

        for (Laboratorio laboratorio : listaLaboratorio) {
            idlablist.add(laboratorio.getIdLab());
            topiclist.add(laboratorio.getTopic());
            rscientificolist.add(laboratorio.getRScientifico().getMatricola());
        }
    }



    /**
     * Get singolo laboratorio topic gui string.
     *
     * @param idLabSelezionato the id lab selezionato
     * @return the string
     */
    public String getSingoloLaboratorioTopicGUI(String idLabSelezionato){

        Laboratorio labScelto = null;
        for(Laboratorio lab : listaLaboratorio){
            if(lab.getIdLab().equals(idLabSelezionato)){
                labScelto = lab;
                break;
            }
        }
        return labScelto.getTopic();
    }



    /**
     * Get singolo indirizzo gui string.
     *
     * @param idLabSelezionato the id lab selezionato
     * @return the string
     */
    public String getSingoloIndirizzoGUI(String idLabSelezionato){

        Laboratorio labScelto = null;
        for(Laboratorio lab : listaLaboratorio){
            if(lab.getIdLab().equals(idLabSelezionato)){
                labScelto = lab;
                break;
            }
        }
        return labScelto.getIndirizzo();
    }



    /**
     * Get singolo numero telefonico gui string.
     *
     * @param idLabSelezionato the id lab selezionato
     * @return the string
     */
    public String getSingoloNumeroTelefonicoGUI(String idLabSelezionato){

        Laboratorio labScelto = null;
        for(Laboratorio lab : listaLaboratorio){
            if(lab.getIdLab().equals(idLabSelezionato)){
                labScelto = lab;
                break;
            }
        }
        return labScelto.getNumeroTelefonico();
    }



    /**
     * Get singolo ref scientifico gui string.
     *
     * @param idLabSelezionato the id lab selezionato
     * @return the string
     */
    public String getSingoloRefScientificoGUI(String idLabSelezionato){

        Laboratorio labScelto = null;
        for(Laboratorio lab : listaLaboratorio){
            if(lab.getIdLab().equals(idLabSelezionato)){
                labScelto = lab;
                break;
            }
        }
        return labScelto.getRScientifico().getMatricola();
    }



    /**
     * Get lista dipendenti responsabili scientifici gui array list.
     *
     * @return the array list
     *
     *
     * Funzione che ritorna la lista dei responsabili scientifici disponibili per i laboratori
     * nell' Azienda.
     */
    public ArrayList<String> getListaResponsabiliScientificiDisponibiliGUI(){

        ArrayList<String> rscientificiDisponibili = new ArrayList<>();
        for(Impiegato imp : listaImpiegato){
            if(imp.getTipoImpiegato().equals("senior")){
                int i=0;
                boolean disponibile = true;
                while(i < listaLaboratorio.size() && disponibile){
                    if(listaLaboratorio.get(i).getRScientifico().equals(imp))
                        disponibile=false;
                    i++;
                }
                if(disponibile){
                    rscientificiDisponibili.add(imp.getMatricola());
                }
            }
        }
        return rscientificiDisponibili;
    }



    /**
     * Get lista dipendenti senior disponibili gui array list.
     *
     * @return the array list
     *
     *
     * Funzione che ritorna la lista di tutti i dipendenti senior
     * nell' Azienda.
     */
    public ArrayList<String> getListaDipendentiSeniorDisponibiliGUI(){

        ArrayList<String> referentiDisponibili = new ArrayList<>();
        for(Impiegato imp : listaImpiegato){
            if(imp.getTipoImpiegato().equals("senior") ){
                int i=0;
                boolean disponibile = true;
                while(i < listaProgetto.size() && disponibile){
                    if(listaProgetto.get(i).getReferente().equals(imp))
                        disponibile=false;
                    i++;
                }
                if(disponibile){
                    referentiDisponibili.add(imp.getMatricola());
                }
            }
        }
        return referentiDisponibili;
    }



    /**
     * Get lista dirigenti disponibili gui array list.
     *
     * @return the array list
     *
     *
     * Funzione che ritorna la lista di tutti i dirigenti
     * nell'Azienda che non sono associati a nessun progetto.
     */
    public ArrayList<String> getListaDirigentiDisponibiliGUI() {

        ArrayList<String> dirigentiDisponibili = new ArrayList<>();
        for(Impiegato imp : listaImpiegato){
            if(imp.isDirigente() == true){
                int i=0;
                boolean disponibile = true;
                while(i < listaProgetto.size() && disponibile){
                    if(listaProgetto.get(i).getResponsabile().equals(imp))
                        disponibile=false;
                    i++;
                }
                if(disponibile){
                    dirigentiDisponibili.add(imp.getMatricola());
                }
            }
        }
        return dirigentiDisponibili;
    }



    /**
     * Gets lista progetto gui.
     *
     * @param nomelist         the nomelist
     * @param cuplist          the cuplist
     * @param responsabilelist the responsabilelist
     * @param referentelist    the referentelist
     */
    public void getListaProgettoGUI(ArrayList<String> nomelist, ArrayList<String> cuplist, ArrayList<String> responsabilelist, ArrayList<String> referentelist) {

        for (Progetto progetto : listaProgetto) {
            nomelist.add(progetto.getNome());
            cuplist.add(progetto.getCup());
            responsabilelist.add(progetto.getResponsabile().getMatricola());
            referentelist.add(progetto.getReferente().getMatricola());
        }
    }

    public String getSingoloProgettoNomeProfiloGUI(String cupSelezionato) {
        Progetto progettoScelto = null;
        for(Progetto prog : listaProgetto) {
            if(prog.getCup().equals(cupSelezionato)) {
                progettoScelto = prog;
                break;
            }
        }
        return progettoScelto.getNome();
    }

    public float getSingoloProgettoBudgetProfiloGUI(String cupSelezionato) {
        Progetto progettoScelto = null;
        for(Progetto prog : listaProgetto) {
            if(prog.getCup().equals(cupSelezionato)) {
                progettoScelto = prog;
                break;
            }
        }
        return progettoScelto.getBudget();
    }

    public Object getSingoloProgettoDataInizioProfiloGUI(String cupSelezionato) {
        Progetto progettoScelto = null;
        for(Progetto prog : listaProgetto) {
            if(prog.getCup().equals(cupSelezionato)) {
                progettoScelto = prog;
                break;
            }
        }
        return progettoScelto.getDataInizio();
    }

    public Object getSingoloProgettoDataFineProfiloGUI(String cupSelezionato) {
        Progetto progettoScelto = null;
        for(Progetto prog : listaProgetto) {
            if(prog.getCup().equals(cupSelezionato)) {
                progettoScelto = prog;
                break;
            }
        }
        return progettoScelto.getDataFine();
    }

    public String getSingoloProgettoResponsabileProfiloGUI(String cupSelezionato) {
        Progetto progettoScelto = null;
        for(Progetto prog : listaProgetto) {
            if(prog.getCup().equals(cupSelezionato)) {
                progettoScelto = prog;
                break;
            }
        }
        return progettoScelto.getResponsabile().getMatricola();
    }

    public String getSingoloProgettoReferenteProfiloGUI(String cupSelezionato) {
        Progetto progettoScelto = null;
        for(Progetto prog : listaProgetto) {
            if(prog.getCup().equals(cupSelezionato)) {
                progettoScelto = prog;
                break;
            }
        }
        return progettoScelto.getReferente().getMatricola();
    }



    //________________________________________________________________________________________________________________//
    //________________________________________________________________________________________________________________//
    //________________________________________________________________________________________________________________//



























}

