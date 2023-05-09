package MODEL;


import java.util.ArrayList;
import java.util.List;


public class Progetto {

    private String nome;
    private String cup;
    private float budget;
    private String dataInizio;
    private String dataFine;

    //todo cambiare gli attributi seguenti con gli oggetti Impiegato.
    private String responsabile;
    private String referente;

    //ogni Progetto ha la lista dei laboratori associati[...]
    private List<Laboratorio> listaLaboratori = new ArrayList<Laboratorio>();


    public Progetto(String nome, String cup, float budget, String dataInizio, String dataFine, String responsabile, String referente){
        this.nome = nome;
        this.cup=cup;
        this.budget=budget;
        this.dataFine=dataFine;
        this.dataInizio=dataInizio;
        this.referente=referente;
        this.responsabile=responsabile;
    }

    //funzioni che ritornano gli attributi privati
    public String getNome(){return nome;}

    public String getCup() {
        return cup;
    }

    public float getBudget() {
        return budget;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public String getReferente() {
        return referente;
    }

    public List<Laboratorio> getListaLaboratori() {
        return listaLaboratori;
    }
}
