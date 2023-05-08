package DAO;

import MODEL.Impiegato;
import MODEL.Laboratorio;
import MODEL.Progetto;
import MODEL.Storico;

import java.util.List;

public interface AziendaDAO {

    /*
        LE FUNZIONI SEGUENTI SERVONO AL DUMP DEI DATI[...]
    */
    public List<Impiegato> getListImpiegatiDAO();
    public List<Progetto> getListProgettoDAO();
    public List<Laboratorio> getListLaboratorioDAO();
    public List<Storico> getListStoricoDAO();

    /*
        Le seguente funzioni servono a Gestire l'aggiunta o eliminazione di impiegati laboratori e progetti[...]
    */
    public boolean removeImpiegatoDAO(String matricola);
    public boolean addImpiegatoDAO(Impiegato imp);
    public boolean addProgettoDAO();
    public boolean removeProgettoDAO();
    public boolean addLaboratorioDAO();
    public boolean removeLaboratorioDAO();

}