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
    private String dataLicenziamento;
    private float stipendio;
    private char sesso;

    //il seguente attributo ha la lista degli storici di un impiegato.
    private List<Storico> listaStorico= new ArrayList<>();
    //il seguente attributo considera a quanti laboratori è associato un impiegato
    private List<Laboratorio> ListaAfferenza = new ArrayList<>();




    public Impiegato(String matricola, String nome, String cognome, String codiceFiscale, String curriculum, boolean dirigente, String dataAssunzione, String dataLicenziamento, float stipendio, char sesso) {
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