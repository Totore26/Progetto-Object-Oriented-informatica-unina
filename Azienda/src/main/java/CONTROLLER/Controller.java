package CONTROLLER;

import DAO.AziendaDAO;
import ImplementazionePostgresDAO.AziendaPostgresDAO;
import MODEL.Impiegato;
import MODEL.Laboratorio;
import MODEL.Progetto;
import MODEL.Storico;

import java.util.ArrayList;
import java.util.List;

public class Controller
{
    public List<Impiegato> listaImpiegato = new ArrayList<>();
    public List<Progetto> listaProgetto = new ArrayList<>();
    public List<Laboratorio> listaLaboratorio = new ArrayList<>();
    public List<Storico> listaStorico = new ArrayList<>();


    public Controller() {
        dumpDati();
    }

    //tale funzione attiva il dump dei dati dal DB, caricando i dati
    private void dumpDati() {
       AziendaDAO aziendaDAO = new AziendaPostgresDAO();
       //Istanzio nel Model le liste di Impiegati,Progetti,Laboratori e Storici.
        listaImpiegato = aziendaDAO.getListImpiegatiDAO();
        listaProgetto = aziendaDAO.getListProgettoDAO();
        listaLaboratorio = aziendaDAO.getListLaboratorioDAO();
        listaStorico = aziendaDAO.getListStoricoDAO();
    }

    //tutte le funzioni implementate su storico laboratorio progetto e impiegato





}