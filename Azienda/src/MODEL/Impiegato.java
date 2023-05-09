package MODEL;


import java.util.ArrayList;
import java.util.List;

//TODO
public class Impiegato {

    //Questi rappresentano tutti gli attributi di un impiegato.
    private String matricola;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String curriculum;
    private boolean dirigente;
    private String dataAssunzione;

    private String tipoImpiegato;
    private String dataLicenziamento;
    private float stipendio;
    private String sesso;

    //il seguente attributo ha la lista degli storici di un impiegato.
    private List<Storico> listaStorico= new ArrayList<>();
    //il seguente attributo considera a quanti laboratori è associato un impiegato
    private List<Laboratorio> ListaAfferenza = new ArrayList<>();




    public Impiegato(String matricola, String nome, String cognome, String codiceFiscale, String curriculum, boolean dirigente, String tipoImpiegato, String dataAssunzione, String dataLicenziamento, float stipendio, String sesso) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.curriculum = curriculum;
        this.dirigente = dirigente;
        this.dataAssunzione = dataAssunzione;
        this.dataLicenziamento = dataLicenziamento;
        this.stipendio = stipendio;
        this.sesso = sesso;
    }

    public String getNome(){return nome;}



    //funzioni che ritornano gli attributi privati
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

    public String getDataAssunzione() {
        return dataAssunzione;
    }

    public String getTipoImpiegato() {
        return tipoImpiegato;
    }

    public String getDataLicenziamento() {
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
        return ListaAfferenza;
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

    public void setDataAssunzione(String dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }

    public void setTipoImpiegato(String tipoImpiegato) {
        this.tipoImpiegato = tipoImpiegato;
    }

    public void setDataLicenziamento(String dataLicenziamento) {
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
        ListaAfferenza = listaAfferenza;
    }
}