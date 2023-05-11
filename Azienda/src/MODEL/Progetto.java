package MODEL;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Progetto {

    private String nome;
    private String cup;
    private float budget;
    private Date dataInizio;
    private Date dataFine;
    private Impiegato responsabile;
    private Impiegato referente;

    //ogni Progetto ha la lista dei laboratori associati[...]
    private List<Laboratorio> listaLaboratori = new ArrayList<Laboratorio>();


    public Progetto(String nome, String cup, float budget, Date dataInizio, Date dataFine, Impiegato responsabile, Impiegato referente){
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

    public Date getDataInizio() {
        return dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public Impiegato getResponsabile() {
        return responsabile;
    }

    public Impiegato getReferente() {
        return referente;
    }

    public List<Laboratorio> getListaLaboratori() {
        return listaLaboratori;
    }
}
