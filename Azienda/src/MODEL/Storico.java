package MODEL;

import java.util.Date;

/**
 * The type Storico.
 */
public class Storico {

    private String ruoloPrecedente;
    private String nuovoRuolo;
    private Date dataScatto;
    private Impiegato impiegato;


    /**
     * Instantiates a new Storico.
     *
     * @param ruoloPrecedente the ruolo precedente
     * @param nuovoRuolo      the nuovo ruolo
     * @param dataScatto      the data scatto
     * @param impiegato       the impiegato
     */


    public  Storico(String ruoloPrecedente, String nuovoRuolo, Date dataScatto, Impiegato impiegato){
        this.dataScatto=dataScatto;
        this.impiegato=impiegato;
        this.nuovoRuolo=nuovoRuolo;
        this.ruoloPrecedente=ruoloPrecedente;
    }

    /**
     * Gets ruolo precedente.
     *
     * @return the ruolo precedente
     */
//getter dello Storico
    public String getRuoloPrecedente() {
        return ruoloPrecedente;
    }



    /**
     * Gets nuovo ruolo.
     *
     * @return the nuovo ruolo
     */
    public String getNuovoRuolo() {
        return nuovoRuolo;
    }



    /**
     * Gets data scatto.
     *
     * @return the data scatto
     */
    public Date getDataScatto() {
        return dataScatto;
    }



    /**
     * Get impiegato a cui è associato lo storico.
     *
     * @return the impiegato
     */
    public Impiegato getImpiegato(){return impiegato;}



    /**
     * Gets matricola.
     *
     * @return the matricola
     */
    public String getMatricola() {
        return impiegato.getMatricola();
    }



    /**
     * Sets ruolo precedente.
     *
     * @param ruoloPrecedente the ruolo precedente
     */
//set dello Storico
    public void setRuoloPrecedente(String ruoloPrecedente) {
        this.ruoloPrecedente = ruoloPrecedente;
    }



    /**
     * Sets nuovo ruolo.
     *
     * @param nuovoRuolo the nuovo ruolo
     */
    public void setNuovoRuolo(String nuovoRuolo) {
        this.nuovoRuolo = nuovoRuolo;
    }



    /**
     * Sets data scatto.
     *
     * @param dataScatto the data scatto
     */
    public void setDataScatto(Date dataScatto) {
        this.dataScatto = dataScatto;
    }



    /**
     * Sets matricola.
     *
     * @param impiegato the impiegato
     */
    public void setMatricola(Impiegato impiegato) {
        this.impiegato = impiegato;
    }
}
