package MODEL;


import java.util.ArrayList;
import java.util.List;

//TODO
public class Impiegato {

    //Questi rappresentano tutti gli attributi di un impiegato.
    public String matricola;
    public String nome;
    public String cognome;
    public String codiceFiscale;
    public String curriculum;
    public boolean dirigente;
    public String dataAssunzione;

    public String tipoImpiegato;
    public String dataLicenziamento;
    public float stipendio;
    public String sesso;

    //il seguente attributo ha la lista degli storici di un impiegato.
    public List<Storico> listaStorico= new ArrayList<>();
    //il seguente attributo considera a quanti laboratori è associato un impiegato
    public List<Laboratorio> ListaAfferenza = new ArrayList<>();




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

}