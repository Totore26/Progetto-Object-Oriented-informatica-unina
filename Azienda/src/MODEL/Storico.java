package MODEL;

public class Storico {

    private String ruoloPrecedente;
    private String nuovoRuolo;
    private String dataScatto;
    private String matricola;


    //todo gestisci la matricola non come stringa ma come riferimento all'impiegato a cui è associato lo storico
    public  Storico(String ruoloPrecedente, String nuovoRuolo, String dataScatto, String matricola){
        this.dataScatto=dataScatto;
        this.matricola=matricola;
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

    public String getDataScatto() {
        return dataScatto;
    }

    public String getMatricola() {
        return matricola;
    }



    //set dello Storico
    public void setRuoloPrecedente(String ruoloPrecedente) {
        this.ruoloPrecedente = ruoloPrecedente;
    }

    public void setNuovoRuolo(String nuovoRuolo) {
        this.nuovoRuolo = nuovoRuolo;
    }

    public void setDataScatto(String dataScatto) {
        this.dataScatto = dataScatto;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }
}
