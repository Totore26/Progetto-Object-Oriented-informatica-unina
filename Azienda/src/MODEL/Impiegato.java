package MODEL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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


    //Crea il riferimento ad un Impiegato
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

    //aggiunge alla listaAfferenza il laboratorio in cui lavora
    public void aggiungiLaboratorio(Laboratorio lab){
        this.listaAfferenza.add(lab);
    }

    //aggiunge allo storico
    public void aggiungiStorico(Storico storico){
        this.listaStorico.add(storico);
    }




    //funzioni che ritornano gli attributi privati
    public String getNome(){return nome;}

    public String getMatricola() {
        return matricola;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public boolean isDirigente() {
        return dirigente;
    }

    public Date getDataAssunzione() {
        return dataAssunzione;
    }

    public String getTipoImpiegato() {
        return tipoImpiegato;
    }

    public Date getDataLicenziamento() {
        return dataLicenziamento;
    }

    public float getStipendio() {
        return stipendio;
    }

    public String getSesso() {
        return sesso;
    }

    public List<Storico> getListaStorico() {
        return listaStorico;
    }

    public List<Laboratorio> getListaAfferenza() {
        return listaAfferenza;
    }


    //set Impiegato

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public void setDirigente(boolean dirigente) {
        this.dirigente = dirigente;
    }

    public void setDataAssunzione(Date dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }

    public void setTipoImpiegato(String tipoImpiegato) {
        this.tipoImpiegato = tipoImpiegato;
    }

    public void setDataLicenziamento(Date dataLicenziamento) {
        this.dataLicenziamento = dataLicenziamento;
    }

    public void setStipendio(float stipendio) {
        this.stipendio = stipendio;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public void setListaStorico(List<Storico> listaStorico) {
        this.listaStorico = listaStorico;
    }

    public void setListaAfferenza(List<Laboratorio> listaAfferenza) {
        this.listaAfferenza = listaAfferenza;
    }


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