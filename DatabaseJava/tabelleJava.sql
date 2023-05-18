/*
	SCHEMA LOGICO:

	IMPIEGATO(matricola, nome, cognome, cf, curriculum, stipendio, sesso, foto, tipo, dirigente, data_licenziamento, data_assunzione)
	LABORATORIO(id_lab, topic, indirizzo, numero_telefono, numero_afferenti, responsabile)
	PROGETTO(CUP, nome_progetto, budget, data_inizio, data_fine,responsabile,referente)
	STORICO(ruolo_prec, nuovo_ruolo, data_scatto, matricola)
	AFFERENZA(matricola, id_lab, ore_giornaliere)
	GESTIONE(cup, id_lab)
	
*/

/*______________________________________________________________________________________________________________________________*/

-- DOMINI :


CREATE DOMAIN DOMINIO_SESSO AS CHAR CHECK(VALUE IN('M','F','N'));
--FARE DOMINIO MATRICOLA
CREATE DOMAIN DOMINIO_MATRICOLA AS VARCHAR(8) CHECK(VALUE LIKE('MAT-%'));
--DOMINIO DELL'IMPIEGATO
CREATE DOMAIN DOMINIO_IMPIEGATO AS VARCHAR CHECK(VALUE IN('junior','middle','senior'));
--DOMINIO DELLO SCATTO DELL'IMPIEGATO
CREATE DOMAIN DOMINIO_SCATTO AS VARCHAR CHECK(VALUE IN('junior','middle','senior','dirigente','NonDirigente'));

/*______________________________________________________________________________________________________________________________*/

--CREAZIONE DELLE TABELE FISICHE:

CREATE TABLE IF NOT EXISTS IMPIEGATO
(
	matricola DOMINIO_MATRICOLA,
	nome VARCHAR NOT NULL,
	cognome VARCHAR NOT NULL,
	codice_fiscale CHAR(16) NOT NULL UNIQUE, 
	curriculum VARCHAR,
	stipendio DECIMAL(12,2) NOT NULL,
	sesso DOMINIO_SESSO NOT NULL,
	foto BYTEA,
	tipo_impiegato DOMINIO_IMPIEGATO NOT NULL DEFAULT 'junior',
	dirigente BOOLEAN NOT NULL DEFAULT FALSE,
	data_assunzione DATE NOT NULL,
	data_licenziamento DATE DEFAULT NULL,

	CONSTRAINT data_corretta CHECK(data_assunzione < data_licenziamento),
	CONSTRAINT impiegato_pk PRIMARY KEY(matricola),
	CONSTRAINT stipendio_corretto CHECK(stipendio > 0)
);

CREATE TABLE IF NOT EXISTS LABORATORIO
(
	id_lab VARCHAR,
	topic VARCHAR NOT NULL,
	indirizzo VARCHAR NOT NULL,
	numero_telefono VARCHAR(12), 
	numero_afferenti INTEGER DEFAULT 1,
	r_scientifico DOMINIO_MATRICOLA NOT NULL UNIQUE,


	CONSTRAINT laboratorio_pk PRIMARY KEY(id_lab),
	CONSTRAINT r_scientifico_fk FOREIGN KEY(r_scientifico) REFERENCES IMPIEGATO(matricola)
		ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS PROGETTO
(
	cup VARCHAR,
	nome_progetto VARCHAR UNIQUE NOT NULL,
	budget DECIMAL(12,2) NOT NULL,
	data_inizio DATE NOT NULL,
	data_fine DATE DEFAULT NULL,  
	responsabile DOMINIO_MATRICOLA NOT NULL UNIQUE, 
	referente DOMINIO_MATRICOLA NOT NULL UNIQUE,  
	
	CONSTRAINT progetto_pk PRIMARY KEY(cup),
	CONSTRAINT budget_corretto CHECK(budget > 0),
	CONSTRAINT data_corretta CHECK(data_fine > data_inizio),
	CONSTRAINT responsabile_fk FOREIGN KEY(responsabile) REFERENCES IMPIEGATO(matricola)
		ON UPDATE CASCADE,
	CONSTRAINT referente_fk FOREIGN KEY(referente) REFERENCES IMPIEGATO(matricola)
		ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS STORICO
(
	ruolo_prec DOMINIO_SCATTO,
	nuovo_ruolo DOMINIO_SCATTO NOT NULL, 
	data_scatto DATE NOT NULL,
	matricola DOMINIO_MATRICOLA,

	CONSTRAINT storico_pk PRIMARY KEY(nuovo_ruolo, matricola, data_scatto),
	CONSTRAINT matricola_fk FOREIGN KEY(matricola) REFERENCES IMPIEGATO(matricola)
		ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT check_ruolo CHECK(((ruolo_prec is NULL) AND (nuovo_ruolo = 'junior')) OR 
								  ((ruolo_prec = 'junior') AND (nuovo_ruolo = 'middle')) OR
								  ((ruolo_prec = 'middle') AND (nuovo_ruolo = 'senior'))  OR
								  	(ruolo_prec = 'NonDirigente') AND (nuovo_ruolo = 'dirigente') OR
									(ruolo_prec = 'dirigente') AND (nuovo_ruolo = 'NonDirigente'))
);

CREATE TABLE IF NOT EXISTS AFFERENZA
(
	matricola DOMINIO_MATRICOLA NOT NULL,
	id_lab VARCHAR NOT NULL,

	CONSTRAINT afferenza_pk PRIMARY KEY(matricola,id_lab),
	CONSTRAINT impiegato_fk FOREIGN KEY(matricola) REFERENCES IMPIEGATO(matricola)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT laboratorio_fk FOREIGN KEY(id_lab) REFERENCES LABORATORIO(id_lab)
		ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS GESTIONE
(
	cup VARCHAR NOT NULL,
	id_lab   VARCHAR NOT NULL,

	CONSTRAINT gestione_pk PRIMARY KEY(cup, id_lab),
	CONSTRAINT progetto_fk FOREIGN KEY(cup) REFERENCES PROGETTO(cup)
		ON UPDATE CASCADE ON DELETE CASCADE ,
	CONSTRAINT laboratorio_fk FOREIGN KEY(id_lab) REFERENCES LABORATORIO(id_lab)
		ON UPDATE CASCADE ON DELETE CASCADE
);


/*______________________________________________________________________________________________________________________________*/