package MODEL;

import java.util.Date;

public class Storico {

    private String ruoloPrecedente;
    private String nuovoRuolo;
    private Date dataScatto;
    private Impiegato impiegato;


    //todo gestisci la matricola non come stringa ma come riferimento all'impiegato a cui è associato lo storico
    public  Storico(String ruoloPrecedente, String nuovoRuolo, Date dataScatto, Impiegato impiegato){
        this.dataScatto=dataScatto;
        this.impiegato=impiegato;
        this.nuovoRuolo=nuovoRuolo;
        this.ruoloPrecedente=ruoloPrecedente;
    }

    //getter dello Storico
    public String getRuoloPrecedente() {
        return ruoloPrecedente;
    }

    public String getNuovoRuolo() {
        return nuovoRuolo;
    }

    public Date getDataScatto() {
        return dataScatto;
    }

    public Impiegato getMatricola() {
        return impiegato;
    }



    //set dello Storico
    public void setRuoloPrecedente(String ruoloPrecedente) {
        this.ruoloPrecedente = ruoloPrecedente;
    }

    public void setNuovoRuolo(String nuovoRuolo) {
        this.nuovoRuolo = nuovoRuolo;
    }

    public void setDataScatto(Date dataScatto) {
        this.dataScatto = dataScatto;
    }

    public void setMatricola(Impiegato impiegato) {
        this.impiegato = impiegato;
    }
}
