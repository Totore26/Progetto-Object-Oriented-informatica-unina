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


}
