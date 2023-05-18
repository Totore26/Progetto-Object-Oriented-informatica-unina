package MODEL;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The type Progetto.
 */
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


    /**
     * Instantiates a new Progetto.
     *
     * @param nome         the nome
     * @param cup          the cup
     * @param budget       the budget
     * @param dataInizio   the data inizio
     * @param dataFine     the data fine
     * @param responsabile the responsabile
     * @param referente    the referente
     */
    public Progetto(String nome, String cup, float budget, Date dataInizio, Date dataFine, Impiegato responsabile, Impiegato referente){
        this.nome = nome;
        this.cup=cup;
        this.budget=budget;
        this.dataFine=dataFine;
        this.dataInizio=dataInizio;
        this.referente=referente;
        this.responsabile=responsabile;
    }

    /**
     * Get nome string.
     *
     * @return the string
     */
//funzioni che ritornano gli attributi privati
    public String getNome(){return nome;}



    /**
     * Gets cup.
     *
     * @return the cup
     */
    public String getCup() {
        return cup;
    }



    /**
     * Gets budget.
     *
     * @return the budget
     */
    public float getBudget() {
        return budget;
    }



    /**
     * Gets data inizio.
     *
     * @return the data inizio
     */
    public Date getDataInizio() {
        return dataInizio;
    }



    /**
     * Gets data fine.
     *
     * @return the data fine
     */
    public Date getDataFine() {
        return dataFine;
    }



    /**
     * Gets responsabile.
     *
     * @return the responsabile
     */
    public Impiegato getResponsabile() {
        return responsabile;
    }



    /**
     * Gets referente.
     *
     * @return the referente
     */
    public Impiegato getReferente() {
        return referente;
    }



    /**
     * Gets lista laboratori.
     *
     * @return the lista laboratori
     */
    public List<Laboratorio> getListaLaboratori() {
        return listaLaboratori;
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
     * Sets cup.
     *
     * @param cup the cup
     */
    public void setCup(String cup) {
        this.cup = cup;
    }


    /**
     * Sets budget.
     *
     * @param budget the budget
     */
    public void setBudget(float budget) {
        this.budget = budget;
    }


    /**
     * Sets data inizio.
     *
     * @param dataInizio the data inizio
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }



    /**
     * Sets data fine.
     *
     * @param dataFine the data fine
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }



    /**
     * Sets responsabile.
     *
     * @param responsabile the responsabile
     */
    public void setResponsabile(Impiegato responsabile) {
        this.responsabile = responsabile;
    }



    /**
     * Sets referente.
     *
     * @param referente the referente
     */
    public void setReferente(Impiegato referente) {
        this.referente = referente;
    }


    /**
     * Aggiungi gestione.
     *
     * @param pro the pro
     */
    public void aggiungiGestione(Laboratorio pro){
        this.listaLaboratori.add(pro);
    }



    /**
     * Remove gestione progetto.
     *
     * @param lab the lab
     */
    public void removeGestioneProgetto(Laboratorio lab){

        this.listaLaboratori.remove(lab);
    }

}
