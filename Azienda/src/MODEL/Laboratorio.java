package MODEL;

import java.util.ArrayList;
import java.util.List;

public class Laboratorio {

    private String idLab;
    private String topic;
    private String indirizzo;
    private String numeroTelefonico;
    private int numAfferenti;
    private Impiegato RScientifico;

    //il seguente attributo considera la lista di afferenti per un laboratorio
    private List<Impiegato> listaAfferenti = new ArrayList<>();

    //il seguente attributo considera la lista di progetti su cui lavora il singolo Laboratorio
    private List<Progetto> listaProgetti = new ArrayList<>();


    //serve a quando lo prendo in input con il numero di afferenti
    public Laboratorio(String idLab, String topic, String indirizzo, String numeroTelefonico, int numAfferenti, Impiegato RScientifico) {
        this.idLab = idLab;
        this.topic = topic;
        this.indirizzo = indirizzo;
        this.numeroTelefonico = numeroTelefonico;
        this.numAfferenti = numAfferenti;
        this.RScientifico = RScientifico;
    }

    //serve ad aggiungere, nel caso di aggiunta il numero di afferenti è pari a 0
    public Laboratorio(String idLab, String topic, String indirizzo, String numeroTelefonico, Impiegato RScientifico) {
        this.idLab = idLab;
        this.topic = topic;
        this.indirizzo = indirizzo;
        this.numeroTelefonico = numeroTelefonico;
        this.RScientifico = RScientifico;
    }


    //aggiunge un afferente ad un dato laboratorio
    public void aggiungiAfferente(Impiegato imp){
        this.listaAfferenti.add(imp);
    }

    public void aggiungiProgetto(Progetto pro){
        this.listaProgetti.add(pro);
    }

    //getter di Laboratorio
    public String getIdLab() {
        return idLab;
    }

    public String getTopic() {
        return topic;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public int getNumAfferenti() {
        return numAfferenti;
    }

    public Impiegato getRScientifico() {
        return RScientifico;
    }

    public List<Impiegato> getListaAfferenti() {
        return listaAfferenti;
    }

    public List<Progetto> getListaLaboratoriSuCuiLavora(){ return listaProgetti; }



    //set Laboratorio
    public void setIdLab(String idLab) {
        this.idLab = idLab;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public void setNumAfferenti(int numAfferenti) {
        this.numAfferenti = numAfferenti;
    }

    public void setRScientifico(Impiegato RScientifico) {
        this.RScientifico = RScientifico;
    }

    public void setListaAfferenti(List<Impiegato> listaAfferenti) {
        listaAfferenti = listaAfferenti;
    }
}
