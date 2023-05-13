/*
    Parte relativa ai trigger [...]
*/


/*SCHEMA LOGICO:


	IMPIEGATO(matricola, nome, cognome, cf, curriculum, stipendio, sesso, foto, tipo, dirigente, data_licenziamento,data_assunzione)
	LABORATORIO(id_lab, topic, indirizzo, numero_telefono, numero_afferenti, r_scientifico)
	PROGETTO(CUP, nome_progetto, budget, data_inizio, data_fine,responsabile,referente)
	STORICO(ruolo_prec, nuovo_ruolo, data_scatto, matricola)
	AFFERENZA(matricola, id_lab, ore_giornaliere)
	GESTIONE(cup, id_lab)
*/

CREATE OR REPLACE PROCEDURE update_database() AS
$$
	DECLARE
		--cursore con tutti gli impiegati con le ultime date scatto fatte
		cursore_impiegati cursor is (select*
									 from Impiegati_attuali as i natural join storico as s
									 where s.nuovo_ruolo = i.tipo_impiegato);

		imp_corrente record;

	BEGIN
		--AGGIORNAMENTO IMPIEGATI[...]
		open cursore_impiegati;
		LOOP
		FETCH cursore_impiegati INTO imp_corrente;
		IF NOT FOUND THEN
            EXIT;
        end if;

			--CASO IN CUI E' JUNIOR
			IF(imp_corrente.tipo_impiegato = 'junior') THEN
					--se sono passati piu di tre anni allora discrimino gli scatti di carriera fatti.
				IF(imp_corrente.data_assunzione + INTERVAL '3 years' <= CURRENT_DATE) THEN

					INSERT INTO STORICO VALUES('junior','middle',imp_corrente.data_assunzione + INTERVAL '3 YEARS', imp_corrente.matricola);
					IF(imp_corrente.data_assunzione + INTERVAL '7 years' >= CURRENT_DATE) THEN

						UPDATE IMPIEGATO
						SET tipo_impiegato = 'middle'
						WHERE impiegato.matricola = imp_corrente.matricola;

					--altrimenti sono passati 7 anni quindi aggiorno a senior.
					ELSE
						INSERT INTO STORICO VALUES('middle','senior',imp_corrente.data_assunzione + INTERVAL '7 YEARS', imp_corrente.matricola);

						UPDATE IMPIEGATO
						SET tipo_impiegato = 'senior'
						WHERE impiegato.matricola = imp_corrente.matricola;

					END IF;
				END IF;
			END IF;

			--CASO IN CUI E' MIDDLE
			IF(imp_corrente.tipo_impiegato = 'middle') THEN
				IF(imp_corrente.data_assunzione + INTERVAL '7 years' <= CURRENT_DATE) THEN
					INSERT INTO STORICO VALUES('middle','senior',imp_corrente.data_assunzione + INTERVAL '7 YEARS', imp_corrente.matricola);
					UPDATE IMPIEGATO
					SET tipo_impiegato = 'senior'
					WHERE impiegato.matricola = imp_corrente.matricola;
				end if;
			END IF;
		END LOOP;
		close cursore_impiegati;

	END;

$$ LANGUAGE plpgsql;


CREATE OR REPLACE PROCEDURE avviso_su_impiegati_licenziati() AS
$$
	DECLARE
		--utilizzo 3 cursori per rendere il codice più leggibile e conoscere subito il messaggio di warning da inviare

		--cursore dei ref_scientifici licenziati presenti in laboratorio
		cursore_rscientifico CURSOR IS (SELECT i.matricola, l.id_lab
										FROM impiegato AS i JOIN laboratorio AS l ON i.matricola = l.r_scientifico
										WHERE i.matricola NOT IN (SELECT matricola FROM Impiegati_attuali));

		--cursore dei referenti licenziati presenti in progetto
		cursore_referenti CURSOR IS (SELECT i.matricola, pa.cup
									 FROM impiegato AS i JOIN PROGETTI_ATTUALI AS pa ON (i.matricola = pa.referente)
									 WHERE i.matricola NOT IN (SELECT matricola FROM Impiegati_attuali));

		--cursore dei responsabili licenziati presenti in progetto
		cursore_responsabili CURSOR IS (SELECT i.matricola, pa.cup
									 	FROM impiegato AS i JOIN PROGETTI_ATTUALI AS pa ON (i.matricola = pa.responsabile)
										WHERE i.matricola NOT IN (SELECT matricola FROM Impiegati_attuali));

		rigacorrente RECORD;
	BEGIN
		--loop per responsabili scientifici in laboratorio
		OPEN cursore_rscientifico;
		LOOP
			FETCH cursore_rscientifico INTO rigacorrente;
			IF NOT FOUND THEN
				EXIT;
			END IF;

			RAISE WARNING 'L IMPIEGATO CON MATRICOLA % E STATO LICENZIATO, AGGIORNA IL REFERENTE SCIENTIFICO NEL LABORATORIO %',
						  rigacorrente.matricola, rigacorrente.id_lab;
		END LOOP;
		CLOSE cursore_rscientifico;

		--loop per referenti in progetto
		OPEN cursore_referenti;
		LOOP
			FETCH cursore_referenti INTO rigacorrente;
			IF NOT FOUND THEN
				EXIT;
			END IF;

			RAISE WARNING 'L IMPIEGATO CON MATRICOLA % E STATO LICENZIATO, AGGIORNA IL REFERENTE NEL PROGETTO %',
						  rigacorrente.matricola, rigacorrente.cup;
		END LOOP;
		CLOSE cursore_referenti;

		--loop per responsabili in progetto
		OPEN cursore_responsabili;
		LOOP
			FETCH cursore_responsabili INTO rigacorrente;
			IF NOT FOUND THEN
				EXIT;
			END IF;

			RAISE WARNING 'L IMPIEGATO CON MATRICOLA % E STATO LICENZIATO, AGGIORNA IL RESPONSABILE NEL PROGETTO %',
						  rigacorrente.matricola, rigacorrente.cup;
		END LOOP;
		CLOSE cursore_responsabili;
	END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION f_avviso_su_impiegati_licenziati() RETURNS TRIGGER AS
$$
	DECLARE
		impiegato RECORD;
	BEGIN
		IF NEW.data_licenziamento IS NOT NULL THEN

			--caso in cui ho un ref_scientifico licenziato
			SELECT l.r_scientifico, l.id_lab INTO impiegato
			FROM laboratorio AS l
			WHERE NEW.matricola = l.r_scientifico
			AND l.r_scientifico NOT IN (SELECT matricola FROM Impiegati_attuali);

			IF FOUND THEN
				RAISE WARNING 'L IMPIEGATO CON MATRICOLA % E STATO LICENZIATO, AGGIORNA IL REFERENTE SCIENTIFICO NEL LABORATORIO %',
								NEW.matricola, impiegato.id_lab;
			END IF;

			--caso in cui ho un referente
			SELECT pa.referente, pa.cup INTO impiegato
			FROM PROGETTI_ATTUALI as pa
			WHERE NEW.matricola = pa.referente
			AND pa.referente NOT IN (SELECT matricola FROM Impiegati_attuali);

			IF FOUND THEN
				RAISE WARNING 'L IMPIEGATO CON MATRICOLA % E STATO LICENZIATO, AGGIORNA IL REFERENTE NEL PROGETTO %',
								NEW.matricola, impiegato.cup;
			END IF;

			--caso in cui ho un responsabile
			SELECT pa.responsabile, pa.cup INTO impiegato
			FROM PROGETTI_ATTUALI as pa
			WHERE NEW.matricola = pa.responsabile
			AND pa.responsabile NOT IN (SELECT matricola FROM Impiegati_attuali);

			IF FOUND THEN
				RAISE WARNING 'L IMPIEGATO CON MATRICOLA % E STATO LICENZIATO, AGGIORNA IL RESPONSABILE NEL PROGETTO %',
								NEW.matricola, impiegato.cup;
			END IF;

		END IF;

		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER trigger_avviso_su_impiegati_licenziati
AFTER UPDATE OF data_licenziamento ON impiegato
FOR EACH ROW
EXECUTE FUNCTION f_avviso_su_impiegati_licenziati();


--________________________________________________________________________________________________________________________________--

/*
    --TRIGGER: INSERIMENTO DI UN IMPIEGATO, CONTROLLO VALIDITA' DI DATA E NEL CASO POSITIVO AGGIUNGO NELLO STORICO.

    NEL MOMENTO IN CUI INSERISCO UN NUOVO IMPIEGATO, AGGIORNO IL SUO STATUS
    ALL'INTERNO DELLO STORICO CON LA DATA DI SCATTO CON IL TIPO INSERITO,
    NEL CASO IN CUI IL TIPO E' >JUNIOR, ALLORA INSERISCE ANCHE GLI ALTRI SCATTI,
    CALCOLANDOLI GRAZIE ALLA DATA DI ASSUNZIONE.
*/

CREATE OR REPLACE FUNCTION f_insert_storico() RETURNS TRIGGER AS
$$
BEGIN
	--controllo che la data assunzione sia consistente rispetto al tipo di impiegato
	IF (NEW.tipo_impiegato = 'junior') THEN
		IF((NEW.data_assunzione + INTERVAL '3 years') >= CURRENT_DATE ) THEN
			INSERT INTO storico VALUES (NULL, 'junior', NEW.data_assunzione, NEW.matricola);
		ELSE
			RAISE EXCEPTION 'DATA DI ASSUNZIONE NON VALIDA PER UN DIPENDENTE JUNIOR';
		END IF;

	ELSIF (NEW.tipo_impiegato = 'middle') THEN
		IF ((NEW.data_assunzione + INTERVAL '3 years') <= CURRENT_DATE AND
			NEW.data_assunzione + INTERVAL '7 years' >= CURRENT_DATE ) THEN
				INSERT INTO storico VALUES (NULL, 'junior', NEW.data_assunzione, NEW.matricola);
				INSERT INTO storico VALUES ('junior', 'middle', NEW.data_assunzione + INTERVAL '3 years', NEW.matricola);
		ELSE
			RAISE EXCEPTION 'DATA DI ASSUNZIONE NON VALIDA PER UN DIPENDENTE MIDDLE';
		END IF;

	ELSIF (NEW.tipo_impiegato = 'senior') THEN
		IF((NEW.data_assunzione + INTERVAL '7 years') <= CURRENT_DATE ) THEN
			INSERT INTO storico VALUES (NULL, 'junior', NEW.data_assunzione, NEW.matricola);
			INSERT INTO storico VALUES ('junior', 'middle', NEW.data_assunzione + INTERVAL '3 years', NEW.matricola);
			INSERT INTO storico VALUES ('middle', 'senior', NEW.data_assunzione + INTERVAL '7 years', NEW.matricola);
		ELSE
			-- Error message
			RAISE EXCEPTION 'DATA DI ASSUNZIONE NON VALIDA PER UN DIPENDENTE SENIOR';
		END IF;
	END IF;

	IF(new.dirigente is true) THEN
			INSERT INTO STORICO VALUES('NonDirigente','dirigente', CURRENT_DATE, new.matricola);
		END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER insert_storico
AFTER INSERT ON impiegato
FOR EACH ROW
EXECUTE FUNCTION f_insert_storico();

--________________________________________________________________________________________________________________________________--

/*
	TRIGGER:
	Se voglio modificare il tipo impiegato non posso farlo una volta inserito, stessa cosa per la data_assunzione
*/

CREATE OR REPLACE FUNCTION f_not_update_tipo_impiegato() RETURNS TRIGGER AS
    $$
    BEGIN
        RAISE EXCEPTION 'UNA VOLTA INSERITO UN IMPIEGATO NON PUOI MODIFICARE IL SUO TIPO O LA DATA DI ASSUNZIONE.';
        RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

--lancio un eccezione nel momento in cui voglio modificare il tipo_impiegatp
CREATE OR REPLACE TRIGGER  not_update_tipo_impiegato
    AFTER UPDATE OF tipo_impiegato,data_assunzione ON impiegato
    FOR EACH ROW
    EXECUTE FUNCTION f_not_update_tipo_impiegato();


--________________________________________________________________________________________________________________________________--

/*
	TRIGGER:
	Nel momento in cui elimino un dirigente che è associato ad un progetto allora devo chiedere all'utente di sostituire
	il responsabile di quel progetto altrimenti lanciando un messaggio di errore.
	stessa cosa per referente per un progetto
	Questo giustificato dal fatto che un progetto non può esserci senza responsabile e referente.
*/

create or replace function f_eliminazione_impiegati_speciali() RETURNS TRIGGER AS
$$
	BEGIN
		--if del responsabile di un progetto
		IF EXISTS(select*
				  from PROGETTI_ATTUALI
				  where responsabile = NEW.matricola) THEN

		RAISE EXCEPTION 'IMPOSSIBILE ELIMINARE IL RESPONSABILE DI UN PROGETTO ATTIVO, PRIMA BISOGNA SOSTITUIRLO!';
		END IF;

		--if del referente di un progetto
		IF EXISTS(select*
				  from PROGETTI_ATTUALI
				  where referente = NEW.matricola) THEN

		RAISE EXCEPTION 'IMPOSSIBILE ELIMINARE IL REFERENTE DI UN PROGETTO ATTIVO, PRIMA BISOGNA SOSTITUIRLO!';
		END IF;

		--if del responsabile scientifico in un laboratorio
		IF EXISTS(select*
				  from LABORATORIO
				  where r_scientifico = NEW.matricola) THEN

		RAISE EXCEPTION 'IMPOSSIBILE ELIMINARE IL RESPONSABILE SCIENTIFICO DI UN LABORATORIO, PRIMA BISOGNA SOSTITUIRLO!';
		END IF;

		RETURN OLD;
	END;
$$ language plpgsql;

CREATE OR REPLACE TRIGGER eliminazione_impiegati_speciali
BEFORE DELETE ON IMPIEGATO
FOR EACH ROW
EXECUTE function f_eliminazione_impiegati_speciali();


--________________________________________________________________________________________________________________________________--

/*
	TRIGGER:
	(update false->true)nel caso in cui viene aggiornato l'attributo booleano dirigente in Impiegato, allora
	bisogna inserire la data scatto all'interno dello storico.
	(update true->false)nel caso in cui il dirigente gestisce qualche progetto mandare messaggio di errore, altrimenti,
	fare inserimento all'interno dello storico della data_scatto 'nonDirigente'->'dirigente'.
*/

create or replace function f_update_dirigente() returns trigger
as
$$
	BEGIN
		IF((OLD.dirigente = false ) AND NEW.dirigente = true)THEN
			INSERT INTO STORICO VALUES('NonDirigente','dirigente', CURRENT_DATE, new.matricola);

		ELSIF (NEW.dirigente = false AND OLD.dirigente = true)THEN
			IF EXISTS(SELECT* FROM PROGETTO WHERE responsabile = new.matricola
											and (data_fine is null or data_fine > CURRENT_DATE) ) THEN

				RAISE EXCEPTION 'NON PUOI DECLASSARE UN DIRIGENTE SE GESTISCE UN PROGETTO, BISOGNA PRIMA CAMBIARLO!';


			ELSE
				INSERT INTO STORICO VALUES('dirigente','NonDirigente', CURRENT_DATE, new.matricola);
			END IF;

		END IF;

		RETURN NEW;

	END;

$$ language plpgsql;



CREATE OR REPLACE TRIGGER update_dirigente
AFTER UPDATE OF dirigente On impiegato
FOR EACH ROW
EXECUTE FUNCTION f_update_dirigente();

--________________________________________________________________________________________________________________________________--

/*
	VINCOLO DI INTEGRITA' SEMANTICA[...] :
	Un impiegato Junior o un middle non può avere lo stipendio più alto di un senior.
*/
create or replace function f_check_stipendio() returns TRIGGER AS
$$
	BEGIN
		IF(NEW.dirigente is true)then
		    RETURN NEW;
		END IF;

		IF(new.tipo_impiegato = 'junior' AND EXISTS(select* from Impiegato as i
													where i.tipo_impiegato <> 'junior' and i.stipendio < new.stipendio )) then

			RAISE EXCEPTION 'UN IMPIEGATO JUNIOR NON PUO AVERE LO STIPENDIO PIU ALTO DI UN MIDDLE';

		ELSIF(new.tipo_impiegato = 'middle' AND EXISTS(select* from Impiegato as i
													where i.tipo_impiegato ='senior' and i.stipendio < new.stipendio)) then

			RAISE EXCEPTION 'UN IMPIEGATO MIDDLE NON PUO AVERE LO STIPENDIO PIU ALTO DI UN SENIOR';

		END IF;

		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;



create or replace trigger check_stipendio
after insert or update of stipendio on Impiegato
for each ROW
execute function f_check_stipendio();

--________________________________________________________________________________________________________________________________--
/*
	TRIGGER :
		(insert prog) quando aggiungo un referente e un responsabile devo fare in modo tale
		che sia il primo senior e il secondo dirigente,
		altrimenti mando un messaggio di errore e non faccio l'inserimento.
*/

create or replace function f_check_referente_or_dirigente() returns trigger AS
$$
	BEGIN
		IF(new.responsabile not in (select matricola from Dirigenti_Attuali)) then
			RAISE EXCEPTION 'IL RESPONSABILE DEVE ESSERE UN DIRIGENTE NON LICENZIATO!';
        END IF;
		IF(new.referente not in (select matricola from Impiegati_attuali where tipo_impiegato = 'senior'))THEN
			RAISE EXCEPTION 'IL REFERENTE DEVE ESSERE UN IMPIEGATO SENIOR NON LICENZIATO!';
		END IF;

	RETURN NEW;

	END;
$$ language plpgsql;


CREATE OR REPLACE TRIGGER check_update_dirigente_or_responsabile
AFTER INSERT OR UPDATE OF referente, responsabile ON PROGETTO
FOR EACH ROW
EXECUTE FUNCTION f_check_referente_or_dirigente();

--________________________________________________________________________________________________________________________________--
/*
		TRIGGER:
		(vincolo di gestione) Un progetto ATTIVO (data fine = null) ha al più
		tre laboratori associati. (sulla tabella gestione).
*/

CREATE OR REPLACE FUNCTION f_max_labs_per_cup() RETURNS TRIGGER AS
$$
DECLARE
    lab_count INTEGER;
BEGIN
	--controlla inanzitutto se il progetto è in corso, altrimenti lancia l'errore.
	IF(new.cup in (select cup from PROGETTI_TERMINATI)) then
		RAISE EXCEPTION 'NON PUOI ASSOCIARE UN PROGETTO TERMINATO AD UN LABORATORIO';
	END IF;

    SELECT COUNT(*) INTO lab_count FROM Gestione_Attuale WHERE cup = NEW.cup;
    IF lab_count > 3 THEN
        RAISE EXCEPTION 'NON E POSSIBILE ASSOCIARE PIU DI TRE ID_LAB AD UN CUP (CODICE PROGETTO)';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER max_labs_per_cup
BEFORE INSERT ON GESTIONE
FOR EACH ROW
EXECUTE FUNCTION f_max_labs_per_cup();

--________________________________________________________________________________________________________________________________--
/*
	TRIGGER: RESPONSABILE SCIENTIFICO
	verifica che quando inserisco o update di un laboratorio, il suo responsabile scientifico sia senior.
*/
CREATE OR REPLACE FUNCTION f_check_responsabile_scientifico() RETURNS TRIGGER AS
$$
BEGIN
	IF NEW.r_scientifico NOT IN (SELECT matricola FROM Impiegati_attuali WHERE tipo_impiegato = 'senior') THEN
		RAISE EXCEPTION 'IL REFERENTE SCIENTIFICO DEVE ESSERE UN SENIOR ATTUALE DELL AZIENDA!';
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_responsabile_scientifico
BEFORE INSERT OR UPDATE OF r_scientifico ON laboratorio
FOR EACH ROW
EXECUTE FUNCTION f_check_responsabile_scientifico();

--________________________________________________________________________________________________________________________________--
/*
	TRIGGER
	Il trigger controlla in fase di inserimento o update che un impiegato non può essere associato ad un
	laboratorio se è licenziato, e controlla anche che
	un impiegato non lavora per più di otto ore al giorno (tabella afferenza), altrimenti
	lanciare un messaggio di errore.
*/
CREATE OR REPLACE FUNCTION f_check_afferenza() RETURNS TRIGGER AS
$$
	BEGIN
		--E SE IO VOLESSI INSERIRE UN IMPIEGATO LICENZIATO IN AFFERENZA IN UN SECONDO MOMENTO PER TENERNE TRACCIA?
		IF(new.matricola not in(select matricola from Impiegati_attuali)) then
			RAISE EXCEPTION 'NON PUOI FAR AFFERIRE AD UN LABORATORIO UN IMPIEGATO LICENZITO';
		END IF;
		--
		--non faccio la count delle ore giornaliere perche (matr,cod_lab) è pk e quindi univoco nella tabella afferenza)
		IF (new.ore_giornaliere > 8) THEN
			RAISE EXCEPTION 'UN IMPIEGATO NON PUO LAVORARE PER PIU DI OTTO ORE AL GIORNO!';
		END IF;
		RETURN NEW;
	END;

$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_afferenza
AFTER INSERT OR UPDATE OF ore_giornaliere ON AFFERENZA
FOR EACH ROW
EXECUTE FUNCTION f_check_afferenza();
--________________________________________________________________________________________________________________________________--
/*
	TRIGGER:
	ogni volta che aggiungo o elimino un'afferenza impiegato-laboratorio allora aggiorno il numero di afferenti di quel
	laboratorio.
*/
create or replace function f_inserisci_afferenti() returns trigger
as
$$
	BEGIN
		UPDATE laboratorio
		SET numero_afferenti = numero_afferenti + 1
		WHERE id_lab = new.id_lab;
	RETURN NEW;
	END;
$$ Language plpgsql;



CREATE OR REPLACE FUNCTION f_cancella_afferenti() RETURNS trigger AS
$$
	BEGIN
		UPDATE laboratorio
		SET numero_afferenti = numero_afferenti - 1
		WHERE id_lab = old.id_lab;
	RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER inserisci_afferenti
AFTER INSERT ON AFFERENZA
FOR EACH ROW
EXECUTE FUNCTION f_inserisci_afferenti();

CREATE OR REPLACE TRIGGER cancella_afferenti
AFTER DELETE ON AFFERENZA
FOR EACH ROW
EXECUTE FUNCTION f_cancella_afferenti();

--________________________________________________________________________________________________________________________________--
--________________________________________________________________________________________________________________________________--
--________________________________________________________________________________________________________________________________--



