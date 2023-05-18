package MODEL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The type Impiegato.
 */
public class Impiegato {

    //Questi rappresentano tutti gli attributi di un impiegato.
    private String matricola;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String curriculum;
    private boolean dirigente;
    private String tipoImpiegato;
    private Date dataAssunzione;
    private Date dataLicenziamento;
    private float stipendio;
    private String sesso;

    private List<Storico> listaStorico = new ArrayList<>();
    private List<Laboratorio> listaAfferenza = new ArrayList<>();


    /**
     * Instantiates a new Impiegato.
     *
     * @param matricola         the matricola
     * @param nome              the nome
     * @param cognome           the cognome
     * @param codiceFiscale     the codice fiscale
     * @param curriculum        the curriculum
     * @param dirigente         the dirigente
     * @param tipoImpiegato     the tipo impiegato
     * @param dataAssunzione    the data assunzione
     * @param dataLicenziamento the data licenziamento
     * @param stipendio         the stipendio
     * @param sesso             the sesso
     */
    public Impiegato(String matricola, String nome, String cognome, String codiceFiscale, String curriculum, boolean dirigente, String tipoImpiegato, Date dataAssunzione, Date dataLicenziamento, float stipendio, String sesso) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.curriculum = curriculum;
        this.dirigente = dirigente;
        this.tipoImpiegato = tipoImpiegato;
        this.dataAssunzione = dataAssunzione;
        this.dataLicenziamento = dataLicenziamento;
        this.stipendio = stipendio;
        this.sesso = sesso;
    }


    /**
     * Aggiungi laboratorio.
     *
     * @param lab the lab
     */
//aggiunge alla listaAfferenza il laboratorio in cui lavora
    public void aggiungiLaboratorio(Laboratorio lab){
        this.listaAfferenza.add(lab);
    }



    /**
     * Aggiungi storico.
     *
     * @param storico the storico
     */
//aggiunge allo storico
    public void aggiungiStorico(Storico storico){
        this.listaStorico.add(storico);
    }




    /**
     * Get nome string.
     *
     * @return the string
     */
//funzioni che ritornano gli attributi privati
    public String getNome(){return nome;}



    /**
     * Gets matricola.
     *
     * @return the matricola
     */
    public String getMatricola() {
        return matricola;
    }



    /**
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }



    /**
     * Gets codice fiscale.
     *
     * @return the codice fiscale
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }



    /**
     * Gets curriculum.
     *
     * @return the curriculum
     */
    public String getCurriculum() {
        return curriculum;
    }



    /**
     * Is dirigente boolean.
     *
     * @return the boolean
     */
    public boolean isDirigente() {
        return dirigente;
    }



    /**
     * Gets data assunzione.
     *
     * @return the data assunzione
     */
    public Date getDataAssunzione() {
        return dataAssunzione;
    }



    /**
     * Gets tipo impiegato.
     *
     * @return the tipo impiegato
     */
    public String getTipoImpiegato() {
        return tipoImpiegato;
    }



    /**
     * Gets data licenziamento.
     *
     * @return the data licenziamento
     */
    public Date getDataLicenziamento() {
        return dataLicenziamento;
    }



    /**
     * Gets stipendio.
     *
     * @return the stipendio
     */
    public float getStipendio() {
        return stipendio;
    }



    /**
     * Gets sesso.
     *
     * @return the sesso
     */
    public String getSesso() {
        return sesso;
    }



    /**
     * Gets lista storico.
     *
     * @return the lista storico
     */
    public List<Storico> getListaStorico() {
        return listaStorico;
    }



    /**
     * Gets lista afferenza, ovvero la lista dei laboratori a cui afferisce.
     *
     * @return the lista afferenza
     */
    public List<Laboratorio> getListaAfferenza() {
        return listaAfferenza;
    }


    //set Impiegato

    /**
     * Sets matricola.
     *
     * @param matricola the matricola
     */
    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }



    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }



    /**
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }



    /**
     * Sets codice fiscale.
     *
     * @param codiceFiscale the codice fiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }



    /**
     * Sets curriculum.
     *
     * @param curriculum the curriculum
     */
    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }


    /**
     * Sets dirigente.
     *
     * @param dirigente the dirigente
     */
    public void setDirigente(boolean dirigente) {
        this.dirigente = dirigente;
    }



    /**
     * Sets data assunzione.
     *
     * @param dataAssunzione the data assunzione
     */
    public void setDataAssunzione(Date dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }



    /**
     * Sets tipo impiegato.
     *
     * @param tipoImpiegato the tipo impiegato
     */
    public void setTipoImpiegato(String tipoImpiegato) {
        this.tipoImpiegato = tipoImpiegato;
    }



    /**
     * Sets data licenziamento.
     *
     * @param dataLicenziamento the data licenziamento
     */
    public void setDataLicenziamento(Date dataLicenziamento) {
        this.dataLicenziamento = dataLicenziamento;
    }



    /**
     * Sets stipendio.
     *
     * @param stipendio the stipendio
     */
    public void setStipendio(float stipendio) {
        this.stipendio = stipendio;
    }



    /**
     * Sets sesso.
     *
     * @param sesso the sesso
     */
    public void setSesso(String sesso) {
        this.sesso = sesso;
    }



    /**
     * Sets lista storico.
     *
     * @param listaStorico the lista storico
     */
    public void setListaStorico(List<Storico> listaStorico) {
        this.listaStorico = listaStorico;
    }



    /**
     * Sets lista afferenza.
     *
     * @param listaAfferenza the lista afferenza
     */
    public void setListaAfferenza(List<Laboratorio> listaAfferenza) {
        this.listaAfferenza = listaAfferenza;
    }


    /**
     * Remove afferenza imp, rimuove l'afferenza dalla lista di laboratori a cui afferisce l'impiegato.
     *
     * @param idlabToRemove the idlab to remove
     */
//la funzione elimina idlab scelto
    public void removeAfferenzaImp(String idlabToRemove){
        for(Laboratorio lab : this.listaAfferenza){
            if(lab.getIdLab().equals(idlabToRemove)){
                listaAfferenza.remove(lab);
                break;
            }
        }
    }


}