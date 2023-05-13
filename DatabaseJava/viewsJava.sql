/*
	SCHEMA LOGICO DEL PROGETTO:

	IMPIEGATO(matricola, nome, cognome, cf, curriculum, stipendio, sesso, foto, tipo, dirigente, data_licenziamento, data_assunzione)
	LABORATORIO(id_lab, topic, indirizzo, numero_telefono, numero_afferenti, responsabile)
	PROGETTO(CUP, nome_progetto, budget, data_inizio, data_fine,responsabile,referente)
	STORICO(ruolo_prec, nuovo_ruolo, data_scatto, matricola)
	AFFERENZA(matricola, id_lab, ore_giornaliere)
	GESTIONE(cup, id_lab)



    VIEW:
    Con le VIEW riusciamo a GESTIRE tutti quei processi ATTUALI dell'Azienda, mentre
    nelle tabelle fisiche salviamo tutto ciò che passa per il database.
*/

--_____________________________________________________________________________________________--

CREATE OR REPLACE VIEW Progetti_attuali AS
(
    SELECT *
    FROM progetto
    WHERE data_fine > CURRENT_DATE OR data_fine is NULL
);
--_____________________________________________________________________________________________--

CREATE OR REPLACE VIEW Impiegati_attuali AS
select*
from Impiegato
where data_licenziamento IS NULL or data_licenziamento > CURRENT_DATE;

--_____________________________________________________________________________________________--

CREATE OR REPLACE VIEW Storico_view AS 
(
    SELECT i.nome, i.cognome, i.matricola,
        s1.data_scatto AS data_scatto_junior,
        s2.data_scatto AS data_scatto_middle,
        s3.data_scatto AS data_scatto_senior
    FROM impiegato i
    LEFT JOIN STORICO s1 ON i.matricola = s1.matricola AND s1.nuovo_ruolo = 'junior'
    LEFT JOIN STORICO s2 ON i.matricola = s2.matricola AND s2.nuovo_ruolo = 'middle'
    LEFT JOIN STORICO s3 ON i.matricola = s3.matricola AND s3.nuovo_ruolo = 'senior'
);

--_____________________________________________________________________________________________--

CREATE OR REPLACE VIEW Dirigenti_Attuali AS 
(
    select *
    from Impiegati_attuali
    where dirigente is true 
);

--_____________________________________________________________________________________________--

CREATE OR REPLACE VIEW Referenti_attuali AS
(
    SELECT *
    FROM Impiegati_attuali as i
    WHERE i.matricola IN (SELECT referente FROM Progetti_attuali)
);

--_____________________________________________________________________________________________--

CREATE OR REPLACE VIEW Gestione_Attuale AS 
(
    SELECT g.cup,g.id_lab
    FROM gestione as g NATURAL JOIN Progetti_attuali as p
);

--_____________________________________________________________________________________________--

CREATE OR REPLACE VIEW Responsabili_scientifici_attuali AS
(
    SELECT *
    FROM Impiegati_attuali as i 
    WHERE i.matricola IN (SELECT r_scientifico FROM laboratorio)
);

--_____________________________________________________________________________________________--

CREATE OR REPLACE VIEW Progetti_terminati AS
(
    SELECT *
    FROM progetto
    WHERE data_fine <= CURRENT_DATE
);

--_____________________________________________________________________________________________--

CREATE OR REPLACE VIEW Afferenza_attuale AS
(
    SELECT a.matricola, a.id_lab
    FROM afferenza as a NATURAL JOIN Impiegati_attuali 
);

--_____________________________________________________________________________________________--

