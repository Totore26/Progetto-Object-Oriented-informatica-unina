package CONTROLLER;
import DAO.*;
import ImplementazionePostgresDAO.*;
import MODEL.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Impiegato> listaImpiegato = new ArrayList<>();
    private List<Progetto> listaProgetto = new ArrayList<>();
    private List<Laboratorio> listaLaboratorio = new ArrayList<>();
    private List<Storico> listaStorico = new ArrayList<>();


    public Controller() {
        dumpdati();
    }


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
     * La seguente funzione quando l'utente nella gui richiede di vedere il profilo dell'Impiegato
     * inizializza la sua lista di storici (attributo listaStorico) e restituisce alla gui
     * la vista presente nel database "View_Storico", la quale mostra in che data ha fatto gli scatti
     */
    public ArrayList<String> leggiStoriciImpiegato(String matricolaSelezionata) {

        ArrayList<String> listaStoriciGUI = new ArrayList<>();

        //trovo l'Impiegato a cui si riferisce
        Impiegato impDaVisualizzare = null;
        for (Impiegato imp : listaImpiegato)
            if (imp.getMatricola().equals(matricolaSelezionata))
                impDaVisualizzare = imp;

        for (Storico s : listaStorico)
            if (s.getMatricola().equals(matricolaSelezionata)) {
                impDaVisualizzare.aggiungiStorico(s);
            }

        ImpiegatoDAO i = new ImpiegatoPostgresDAO();
        //todo da continuare l'implementazione
        return listaStoriciGUI;
    }


    /*funzioni per aggiungere l'impiegato
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
        } else {
            System.out.println("errore nell'aggiunta dell'Impiegato");
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

    //funzione per modificare l'impiegato
    public void modificaImpiegato(String matricolaSelezionata, String curriculum, boolean dirigente, java.util.Date dataLicenziamento, float stipendio) throws SQLException {

        ImpiegatoDAO i = new ImpiegatoPostgresDAO();

        boolean control = i.modificaImpiegatoDAO(matricolaSelezionata, curriculum, dirigente, dataLicenziamento, stipendio);


        //se elimino correttamente, allora elimino anche nel model
        if (control) {
            for (Impiegato imp : listaImpiegato)
                if (imp.getMatricola().equals(matricolaSelezionata)) {
                    listaImpiegato.remove(imp);
                    break;
                }
        }

    }


    //todo aggiungere ad ogni progetto la lista dei laboratori che gestisce


    //funzioni che ritornano nella GUI FinestraImpiegati per riempire la tabella
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


    public void getListaProgettoGUI(ArrayList<String> nomelist, ArrayList<String> cuplist, ArrayList<String> responsabilelist, ArrayList<String> referentelist) {

        for (int i = 0; i < listaProgetto.size(); i++) {
            nomelist.add(listaProgetto.get(i).getNome());
            cuplist.add(listaProgetto.get(i).getCup());
            responsabilelist.add(listaProgetto.get(i).getResponsabile().getMatricola());
            referentelist.add(listaProgetto.get(i).getResponsabile().getMatricola());
        }
    }
















































}

