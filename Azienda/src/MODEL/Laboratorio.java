package MODEL;

import java.util.ArrayList;
import java.util.List;

public class Laboratorio {

    private String idLab;
    private String topic;
    private String indirizzo;
    private int numeroTelefonico;
    private int numAfferenti;

    //todo  cambiare il Rscientifico con l'oggetto Impiegato adeguato.
    private String RScientifico;

    //il seguente attributo considera la lista di afferenti per un laboratorio
    private List<Impiegato> ListaAfferenti = new ArrayList<>();

    public Laboratorio(String idLab, String topic, String indirizzo, int numeroTelefonico, int numAfferenti, String RScientifico) {
        this.idLab = idLab;
        this.topic = topic;
        this.indirizzo = indirizzo;
        this.numeroTelefonico = numeroTelefonico;
        this.numAfferenti = numAfferenti;
        this.RScientifico = RScientifico;
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

    public int getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public int getNumAfferenti() {
        return numAfferenti;
    }

    public String getRScientifico() {
        return RScientifico;
    }

    public List<Impiegato> getListaAfferenti() {
        return ListaAfferenti;
    }



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

    public void setNumeroTelefonico(int numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public void setNumAfferenti(int numAfferenti) {
        this.numAfferenti = numAfferenti;
    }

    public void setRScientifico(String RScientifico) {
        this.RScientifico = RScientifico;
    }

    public void setListaAfferenti(List<Impiegato> listaAfferenti) {
        ListaAfferenti = listaAfferenti;
    }
}
