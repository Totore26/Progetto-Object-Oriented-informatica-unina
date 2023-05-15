package CONTROLLER;
import DAO.*;
import ImplementazionePostgresDAO.*;
import MODEL.*;
import jdk.internal.icu.text.UnicodeSet;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Controller {

    private List<Impiegato> listaImpiegato = new ArrayList<>();
    private List<Progetto> listaProgetto = new ArrayList<>();
    private List<Laboratorio> listaLaboratorio = new ArrayList<>();
    private List<Storico> listaStorico = new ArrayList<>();


    public Controller() {
        dumpdati();
    }


    //____________________________________________FUNZIONI PER IL LOAD DEI DATI_______________________________________//


    //il dumpDati non inizializza le relazioni di afferenza e la gestione...
    private void dumpdati() {
        dumpDatiImpiegato();
        dumpDatiLaboratorio();
        dumpDatiStorico();
        dumpDatiProgetto();
    }


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
//_______________________________________FUNZIONI PER L'IMPIEGATO_____________________________________________________//







    /*  funzione per aggiungere l'impiegato
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



    /*
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



    /*
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




    /*
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
        boolean control = i.leggiAfferenzeDAO(matricolaSelezionata, laboratoriAssociati);

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




    /*
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




    /*
     * LA SEGUENTE FUNZIONE ELIMINA L'AFFERENZA SELEZIONATA PER UN IMPIEGATO
     * NEL CASO IN CUI ELIMINA NEL DATABASE ALLORA ELIMINA ANCHE NEL MODEL...
     * NEL CASO DI ECCEZIONI ALLORA MANDA IL WARNING ALLA GUI
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


    /*
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



    /*
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



    /*
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


    /*
    * La seguente funzione preso in input il laboratorio da visualizzare carica in memoria
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





















    //________________________________________________________________________________________________________________//
    //________________________________________________________________________________________________________________//










































    //___________________________________________FUNZIONI PER LA GUI__________________________________________________//
    /*
    * le seguenti tre funzioni hanno come compito quello di passare
    * alla GUI le varie informazioni che servono per creare le tabelle dell'impiegato...
    */
    public List<String> getListaImpiegatoMatricoleGUI() {
        ArrayList<String> stringMatricole = new ArrayList<>();

        for (Impiegato imp : listaImpiegato)
            stringMatricole.add(imp.getMatricola());

        return stringMatricole;
    }
    public List<String> getListaImpiegatoNomiGUI() {
        ArrayList<String> stringNomi = new ArrayList<>();

        for (Impiegato imp : listaImpiegato)
            stringNomi.add(imp.getNome());

        return stringNomi;
    }
    public List<String> getListaImpiegatoCognomiGUI() {
        ArrayList<String> stringCognomi = new ArrayList<>();

        for (Impiegato imp : listaImpiegato)
            stringCognomi.add(imp.getCognome());

        return stringCognomi;
    }

    /*
    * Funzioni che ritorna la lista di responsabili scientifici disponibili
    * nell'Azienda.
    */
    public ArrayList<String> getListaResponsabiliScientificiDisponibiliGUI(){
        Set<String> matricoleDisponibili = new HashSet<>();
        for(Impiegato imp : listaImpiegato){
            if(imp.getTipoImpiegato().equals("senior")){
                boolean disponibile = true;
                for(Laboratorio lab : listaLaboratorio){
                    if(imp.getMatricola().equals(lab.getRScientifico().getMatricola())){
                        disponibile = false;
                        break;
                    }
                }
                if(disponibile){
                    matricoleDisponibili.add(imp.getMatricola());
                }
            }
        }

        ArrayList<String> rscientificiDisponibili = new ArrayList<>(matricoleDisponibili);
        return rscientificiDisponibili;
    }




    /*
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

    public ArrayList<String> getSingoloImpiegatoAfferenzeGUI(String matricolaSelezionata){
        Impiegato matScelta = null;
        for(Impiegato imp : listaImpiegato){
            if(imp.getMatricola().equals(matricolaSelezionata)){
                matScelta = imp;
                break;
            }
        }

        ArrayList<String> ritornoLab = new ArrayList<>();
        for(Laboratorio lab : matScelta.getListaAfferenza())
            ritornoLab.add(lab.getIdLab());


        return ritornoLab;
    }

    public void getListaProgettoGUI(ArrayList<String> nomelist, ArrayList<String> cuplist, ArrayList<String> responsabilelist, ArrayList<String> referentelist) {

        for (int i = 0; i < listaProgetto.size(); i++) {
            nomelist.add(listaProgetto.get(i).getNome());
            cuplist.add(listaProgetto.get(i).getCup());
            responsabilelist.add(listaProgetto.get(i).getResponsabile().getMatricola());
            referentelist.add(listaProgetto.get(i).getResponsabile().getMatricola());
        }
    }

    public void getListaLaboratorioGUI(ArrayList<String> idlablist, ArrayList<String> topiclist, ArrayList<String> rscientificolist){

        for (Laboratorio laboratorio : listaLaboratorio) {
            idlablist.add(laboratorio.getIdLab());
            topiclist.add(laboratorio.getTopic());
            rscientificolist.add(laboratorio.getRScientifico().getMatricola());
        }
    }

    public ArrayList<String> getListaCodiciLaboratoriGUI(){
        ArrayList<String> idlablist = new ArrayList<String>();
        for (Laboratorio laboratorio : listaLaboratorio) {
            idlablist.add(laboratorio.getIdLab());
        }
        return idlablist;
    }



    //________________________________________________________________________________________________________________//
    //________________________________________________________________________________________________________________//
    //________________________________________________________________________________________________________________//



























}

