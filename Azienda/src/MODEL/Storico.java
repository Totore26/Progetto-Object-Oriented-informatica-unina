package MODEL;

public class Storico {

    private String ruoloPrecedente;
    private String nuovoRuolo;
    private String dataScatto;
    private String matricola;


    public  Storico(String ruoloPrecedente, String nuovoRuolo, String dataScatto, String matricola){
        this.dataScatto=dataScatto;
        this.matricola=matricola;
        this.nuovoRuolo=nuovoRuolo;
        this.ruoloPrecedente=ruoloPrecedente;
    }



}
