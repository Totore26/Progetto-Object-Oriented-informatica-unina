package MODEL;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Laboratorio.
 */
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


    /**
     * Instantiates a new Laboratorio e non considera il numero afferenti nel caso in cui lo crea nuovo
     *
     * @param idLab            the id lab
     * @param topic            the topic
     * @param indirizzo        the indirizzo
     * @param numeroTelefonico the numero telefonico
     * @param numAfferenti     the num afferenti
     * @param RScientifico     the r scientifico
     */
//serve a quando lo prendo in input con il numero di afferenti
    public Laboratorio(String idLab, String topic, String indirizzo, String numeroTelefonico, int numAfferenti, Impiegato RScientifico) {
        this.idLab = idLab;
        this.topic = topic;
        this.indirizzo = indirizzo;
        this.numeroTelefonico = numeroTelefonico;
        this.numAfferenti = numAfferenti;
        this.RScientifico = RScientifico;
    }

    /**
     * Instantiates a new Laboratorio per quando lo prende in input dal database.
     *
     * @param idLab            the id lab
     * @param topic            the topic
     * @param indirizzo        the indirizzo
     * @param numeroTelefonico the numero telefonico
     * @param RScientifico     the r scientifico
     */
//serve ad aggiungere, nel caso di aggiunta il numero di afferenti è pari a 0
    public Laboratorio(String idLab, String topic, String indirizzo, String numeroTelefonico, Impiegato RScientifico) {
        this.idLab = idLab;
        this.topic = topic;
        this.indirizzo = indirizzo;
        this.numeroTelefonico = numeroTelefonico;
        this.RScientifico = RScientifico;
    }


    /**
     * Aggiungi afferente.
     *
     * @param imp the imp
     */
//aggiunge un afferente ad un dato laboratorio
    public void aggiungiAfferente(Impiegato imp){
        this.listaAfferenti.add(imp);
    }



    /**
     * Aggiungi progetto.
     *
     * @param pro the pro
     */
    public void aggiungiProgetto(Progetto pro){
        this.listaProgetti.add(pro);
    }



    /**
     * Gets id lab.
     *
     * @return the id lab
     */
//getter di Laboratorio
    public String getIdLab() {
        return idLab;
    }



    /**
     * Gets topic.
     *
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }



    /**
     * Gets indirizzo.
     *
     * @return the indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }



    /**
     * Gets numero telefonico.
     *
     * @return the numero telefonico
     */
    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }



    /**
     * Gets num afferenti.
     *
     * @return the num afferenti
     */
    public int getNumAfferenti() {
        return numAfferenti;
    }



    /**
     * Gets r scientifico.
     *
     * @return the r scientifico
     */
    public Impiegato getRScientifico() {
        return RScientifico;
    }



    /**
     * Gets lista afferenti.
     *
     * @return the lista afferenti
     */
    public List<Impiegato> getListaAfferenti() {
        return listaAfferenti;
    }



    /**
     * Get lista laboratori su cui lavora list.
     *
     * @return the list
     */
    public List<Progetto> getListaLaboratoriSuCuiLavora(){ return listaProgetti; }



    /**
     * Sets id lab.
     *
     * @param idLab the id lab
     */
//set Laboratorio
    public void setIdLab(String idLab) {
        this.idLab = idLab;
    }



    /**
     * Sets topic.
     *
     * @param topic the topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }



    /**
     * Sets indirizzo.
     *
     * @param indirizzo the indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }



    /**
     * Sets numero telefonico.
     *
     * @param numeroTelefonico the numero telefonico
     */
    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }



    /**
     * Sets num afferenti.
     *
     * @param numAfferenti the num afferenti
     */
    public void setNumAfferenti(int numAfferenti) {
        this.numAfferenti = numAfferenti;
    }



    /**
     * Sets r scientifico.
     *
     * @param RScientifico the r scientifico
     */
    public void setRScientifico(Impiegato RScientifico) {
        this.RScientifico = RScientifico;
    }



    /**
     * Sets lista afferenti.
     *
     * @param listaAfferenti the lista afferenti
     */
    public void setListaAfferenti(List<Impiegato> listaAfferenti) {
        listaAfferenti = listaAfferenti;
    }



    /**
     * Aggiungi matricola alla lista di afferenti
     *
     * @param imp the imp
     */
    public void aggiungiMatricola(Impiegato imp) {
        this.listaAfferenti.add(imp);
    }
}
