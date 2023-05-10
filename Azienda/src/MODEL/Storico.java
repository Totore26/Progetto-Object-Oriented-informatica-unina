package MODEL;

import java.sql.SQLData;

public class Storico {

    private String ruoloPrecedente;
    private String nuovoRuolo;
    private SQLData dataScatto;
    private Impiegato impiegato;


    //todo gestisci la matricola non come stringa ma come riferimento all'impiegato a cui è associato lo storico
    public  Storico(String ruoloPrecedente, String nuovoRuolo, SQLData dataScatto, Impiegato impiegato){
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

    public SQLData getDataScatto() {
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

    public void setDataScatto(SQLData dataScatto) {
        this.dataScatto = dataScatto;
    }

    public void setMatricola(Impiegato impiegato) {
        this.impiegato = impiegato;
    }
}
