package CONTROLLER;
import GUI.*;
import DAO.AziendaDAO;
import GUI.InserimentoImpiegato;
import ImplementazionePostgresDAO.AziendaPostgresDAO;
import MODEL.Impiegato;
import MODEL.Laboratorio;
import MODEL.Progetto;
import MODEL.Storico;

import java.sql.Date;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

public class Controller
{

    private List<Impiegato> listaImpiegato = new ArrayList<>();
    private List<Progetto> listaProgetto = new ArrayList<>();
    private List<Laboratorio> listaLaboratorio = new ArrayList<>();
    private List<Storico> listaStorico = new ArrayList<>();


    public Controller() {dumpdati();}


    //il dumpDati non inizializza le relazioni di afferenza e la gestione...
    public void dumpdati(){
        dumpDatiImpiegato();
        dumpDatiLaboratorio();
        dumpDatiStorico();
        dumpDatiProgetto();
    }


    //tale funzione attiva il dump dei dati dal DB, caricando i dati

    //funziona!
    public void dumpDatiImpiegato() {
       AziendaDAO aziendaDAO = new AziendaPostgresDAO();

        ArrayList<String> matricolalist= new ArrayList<>();
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

       aziendaDAO.getListImpiegatiDAO( matricolalist, nomelist, cognomelist,codiceFiscalelist, curriculumlist,  dirigentelist,  tipoImpiegatolist,  dataAssunzionelist,  dataLicenziamentolist,  stipendiolist, sessolist);

       //inizializzo la lista di Impiegati
        for(int i=0; i<matricolalist.size(); i++){
            listaImpiegato.add(new Impiegato(matricolalist.get(i),nomelist.get(i),cognomelist.get(i),codiceFiscalelist.get(i),curriculumlist.get(i),dirigentelist.get(i),tipoImpiegatolist.get(i),dataAssunzionelist.get(i),dataLicenziamentolist.get(i),stipendiolist.get(i),sessolist.get(i) ));
        }
    }

    public void dumpDatiLaboratorio(){
            AziendaDAO aziendaDAO = new AziendaPostgresDAO();

            ArrayList<String> idLablist= new ArrayList<>();
            ArrayList<String> topiclist = new ArrayList<>();
            ArrayList<String> indirizzolist = new ArrayList<>();
            ArrayList<String> numeroTelefonicoList = new ArrayList<>();
            ArrayList<Integer> numAfferentilist = new ArrayList<>();
            ArrayList<String> matricolaResponsabileScientifico = new ArrayList<>();

            aziendaDAO.getListLaboratorioDAO( idLablist, topiclist, indirizzolist,  numeroTelefonicoList, numAfferentilist, matricolaResponsabileScientifico);

            for(int i=0; i<idLablist.size(); i++){
                Impiegato rScientifico = null;
                //trovo prima l'impiegato che è responsabile scientifico di quel laboratorio e poi istanzio il laboratorio...
                for(Impiegato imp : listaImpiegato){
                    if(imp.getMatricola().equals(matricolaResponsabileScientifico.get(i))){
                        rScientifico = imp;}
                }
                listaLaboratorio.add( new Laboratorio(idLablist.get(i),topiclist.get(i),indirizzolist.get(i),numeroTelefonicoList.get(i),numAfferentilist.get(i),rScientifico));
            }
    }

    //funzione che istanzia gli storici
    public void dumpDatiStorico(){
        AziendaDAO aziendaDAO = new AziendaPostgresDAO();

        ArrayList<String> ruoloPrecedentelist = new ArrayList<>();
        ArrayList<String> nuovoRuololist = new ArrayList<>();
        ArrayList<SQLData> dataScattolist = new ArrayList<>();
        ArrayList<String> impiegatoMatricolalist = new ArrayList<>();

        aziendaDAO.getListStoricoDAO(ruoloPrecedentelist, nuovoRuololist, dataScattolist, impiegatoMatricolalist);


        for(int i=0; i<nuovoRuololist.size(); i++){
            Impiegato impiegato = null;
            //ciclo che associa ad ogni impiegato i suoi storici ed ad ogni storico il suo impiegato[...]
            for(Impiegato imp : listaImpiegato )
            {
                if(imp.getMatricola().equals(impiegatoMatricolalist.get(i) ))
                {
                    impiegato = imp;
                    break;
                }
            }

            Storico s = new Storico(ruoloPrecedentelist.get(i),nuovoRuololist.get(i),dataScattolist.get(i),impiegato);
            listaStorico.add(s);
        }


    }

    public void dumpDatiProgetto(){
        AziendaDAO aziendaDAO = new AziendaPostgresDAO();

        ArrayList<String> nomelist = new ArrayList<>();
        ArrayList<String> cuplist = new ArrayList<>();
        ArrayList<Float> budgetlist = new ArrayList<>();
        ArrayList<Date> dataIniziolist = new ArrayList<>();
        ArrayList<Date> dataFinelist = new ArrayList<>();
        ArrayList<String> matricolaResponsabile = new ArrayList<>();
        ArrayList<String> matricolaReferente = new ArrayList<>();

        aziendaDAO.getListProgettoDAO(nomelist,cuplist,budgetlist,dataIniziolist,dataFinelist,matricolaResponsabile,matricolaReferente);

        //a questo punto istanzio i progetti con i propri referenti e responsabili...
        for(int i=0; i<cuplist.size(); i++){
            Impiegato responsabileAttuale = null;
            Impiegato referenteAttuale = null;

            for(Impiegato imp : listaImpiegato)
            {
                if(matricolaResponsabile.get(i).equals(imp.getMatricola()))
                {
                    responsabileAttuale = imp;
                }
                if(matricolaReferente.get(i).equals(imp.getMatricola()))
                {
                    referenteAttuale = imp;
                }
            }


            Progetto p = new Progetto(nomelist.get(i), cuplist.get(i),budgetlist.get(i),dataIniziolist.get(i),dataFinelist.get(i),responsabileAttuale, referenteAttuale);
        }

    }




    //todo aggiungere ad ogni impiegato la lista di storici e la lista di lab a cui afferisce


    //todo aggiungere ad ogni laboratorio la lista degli afferenti e dei laboratori a cui lavora


    //todo aggiungere ad ogni progetto la lista dei laboratori che gestisce





    //funzioni che ritornano i Model
    public List<Impiegato> getListaImpiegato() {
        return listaImpiegato;
    }

    public List<Progetto> getListaProgetto() {
        return listaProgetto;
    }

    public List<Laboratorio> getListaLaboratorio() {
        return listaLaboratorio;
    }

    public List<Storico> getListaStorico() {
        return listaStorico;
    }
}