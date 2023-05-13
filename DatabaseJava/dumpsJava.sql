/*
    DUMPS[...]
*/

--INSERIMENTO NELLA TABELLA IMPIEGATO
INSERT INTO IMPIEGATO (matricola, nome, cognome, codice_fiscale, curriculum, stipendio, sesso, tipo_impiegato, dirigente, data_assunzione)
VALUES
('MAT-001', 'Mario', 'Rossi', 'RSSMRA01A01F205F', 'Laurea in Informatica', 37000.00, 'M', 'senior', true, '2000-01-01'),
('MAT-002', 'Laura', 'Bianchi', 'BNCLRA02B02H501A', 'Laurea in Economia', 39000.00, 'F', 'senior', true, '2009-06-01'),
('MAT-003', 'Giovanni', 'Verdi', 'VRDGNN03C03I601C', 'Diploma di Ragioniere', 36000.00, 'M', 'senior', true , '2000-03-15'),
('MAT-004', 'Francesca', 'Neri', 'NRIFNC04D04L219S', 'Laurea in Ingegneria', 35000.00, 'F', 'senior', true, '2005-01-01'),
('MAT-005', 'Andrea', 'Russo', 'RSSNDR05E05P123Q', 'Laurea in Scienze Politiche', 30000.00, 'M', 'senior', true, '2004-09-01'),
('MAT-006', 'Alessandra', 'Rizzo', 'RZZLSA06F06M765T', 'Diploma di Tecnico Informatico', 34000.00, 'F', 'senior', true, '2015-02-01'),
('MAT-007', 'Luca', 'Greco', 'GRCMCU07G07N456L', 'Laurea in Architettura', 32000.00, 'M', 'senior', true, '1999-05-01'),
('MAT-008', 'Martina', 'Lombardi', 'LMBMRT08H08S789G', 'Laurea in Lingue', 33000.00, 'F', 'senior', false, '2003-11-01'),
('MAT-009', 'Simone', 'Ferrari', 'FRRSMN09I09T012K', 'Diploma di Perito Elettronico', 43000.00, 'M', 'senior', false, '2000-03-01'),
('MAT-010', 'Elisa', 'Giordano', 'GRDELZ10L10Z456T', 'Laurea in Giurisprudenza', 54000.00, 'F', 'senior', false, '1960-02-01'),
('MAT-011', 'Fabio', 'Moretti', 'MRTFBI11M11A123Z', 'Diploma di Geometra', 18000.00, 'M', 'junior', false, '2021-01-01'),
('MAT-012', 'Sara', 'Ricci', 'RCCSRA12N12B654H', 'Laurea in Psicologia', 27000.00, 'F', 'middle', false, '2019-04-01'),
('MAT-013', 'Davide', 'Gatti', 'GTDDVD13O13C789Y', 'Diploma di Tecnico Meccanico', 16000.00, 'M', 'junior', false, '2020-06-01'),
('MAT-014', 'Valentina', 'Conti', 'CNTVLN14P14F123M', 'Laurea in Biologia', 24000.00, 'F', 'middle', false, '2017-02-01'),
('MAT-015', 'Gabriele', 'Longo', 'LNGGBL15Q15H456T', 'Laurea in Storia dell Arte', 28000.00, 'M', 'middle', false, '2018-06-01'),
('MAT-016', 'Silvia', 'Marino', 'MRNSLV16R16L789K', 'Laurea in Scienze della Comunicazione', 29000.00, 'F', 'middle', false, '2017-09-01'),
('MAT-017', 'Antonio', 'Palumbo', 'PLBNTN17S17P012A', 'Diploma di Perito Agrario', 15000.00, 'M', 'junior', false, '2021-02-01'),
('MAT-018', 'Giulia', 'Fabbri', 'FBBGLI18T18Z456K', 'Laurea in Lettere', 21000.00, 'F', 'middle', false, '2018-02-01'),
('MAT-019', 'Marco', 'Ricci', 'RCCMRC19U19B654Y', 'Diploma di Tecnico Chimico', 12000.00, 'M', 'junior', false, '2020-06-01'),
('MAT-020', 'Paola', 'Fontana', 'FNTPLA20V20C789L', 'Laurea in Filosofia', 20000.00, 'F', 'middle', false, '2017-02-01');

--INSERIMENTO NELLA TABELLA LABORATORIO
INSERT INTO LABORATORIO (id_lab, topic, indirizzo, numero_telefono, r_scientifico)
VALUES 
('LAB-001', 'Intelligenza Artificiale', 'Via Roma 1, Milano', '02 1234567', 'MAT-001'),
('LAB-002', 'Scienze Cognitive', 'Via Garibaldi 2, Torino', '011 1234567', 'MAT-002'),
('LAB-003', 'Biochimica', 'Via Dante 3, Roma', '06 1234567', 'MAT-003'),
('LAB-004', 'Ingegneria Meccanica', 'Via dei Mille 4, Napoli', '081 1234567', 'MAT-004'),
('LAB-005', 'Scienze Politiche', 'Piazza del Popolo 5, Firenze', '055 1234567', 'MAT-005'),
('LAB-006', 'Fisica Teorica', 'Via dei Condotti 6, Roma', '06 1234567', 'MAT-006'),
('LAB-007', 'Tecnologie dell Informazione', 'Via XX Settembre 7, Bologna', '051 1234567','MAT-007'),
('LAB-008', 'Neuroscienze', 'Via Veneto 8, Roma', '06 1234567','MAT-008'),
('LAB-009', 'Scienze della Terra', 'Via Palestro 9, Milano', '02 1234567','MAT-009'),
('LAB-010', 'Chimica Organica', 'Corso Vittorio Emanuele 10, Torino', '011 1234567','MAT-010');


--INSERIMENTO NELLA TABELLA PROGETTO
INSERT INTO PROGETTO (cup, nome_progetto, budget, data_inizio, data_fine, responsabile, referente)
VALUES
('CUP-001', 'Progetto Intelligenza Artificiale', 100000.00, '2023-01-01', '3000-12-31', 'MAT-001', 'MAT-002'),
('CUP-002', 'Progetto Scienze Cognitive', 150000.00, '2023-02-01', '2034-12-31', 'MAT-002', 'MAT-003'),
('CUP-003', 'Progetto Biochimica', 200000.00, '2023-03-01', '2029-12-31', 'MAT-003', 'MAT-004'),
('CUP-004', 'Progetto Ingegneria Meccanica', 250000.00, '2023-04-01', '2028-12-31', 'MAT-004', 'MAT-005'),
('CUP-005', 'Progetto Scienze Politiche', 300000.00, '2023-05-01', '2027-12-31', 'MAT-005', 'MAT-006'),
('CUP-006', 'Progetto Fisica Teorica', 350000.00, '2023-06-01', '2025-12-31', 'MAT-006', 'MAT-007');


--INSERIMENTO IN AFFERENZA
INSERT INTO AFFERENZA ( matricola, id_lab)
VALUES
('MAT-002', 'LAB-001'),
('MAT-001', 'LAB-001'),
('MAT-003', 'LAB-001'),
('MAT-004', 'LAB-001'),
('MAT-005', 'LAB-001'),
('MAT-006', 'LAB-001'),
('MAT-007', 'LAB-001'),
('MAT-008', 'LAB-001'),
('MAT-009', 'LAB-001'),
('MAT-010', 'LAB-001'),
('MAT-011', 'LAB-001'),
('MAT-012', 'LAB-002'),
('MAT-013', 'LAB-002'),
('MAT-014', 'LAB-002'),
('MAT-015', 'LAB-002'),
('MAT-016', 'LAB-002'),
('MAT-017', 'LAB-002'),
('MAT-018', 'LAB-002'),
('MAT-019', 'LAB-002'),
('MAT-020', 'LAB-002'),
('MAT-001', 'LAB-002'),
('MAT-006', 'LAB-003'),
('MAT-004', 'LAB-003'),
('MAT-005', 'LAB-003'),
('MAT-002', 'LAB-003'),
('MAT-012', 'LAB-003'),
('MAT-007', 'LAB-003'),
('MAT-008', 'LAB-003'),
('MAT-009', 'LAB-003'),
('MAT-010', 'LAB-003');

--INSERIMENTO SU GESTIONE
INSERT INTO GESTIONE (cup, id_lab)
VALUES
('CUP-001', 'LAB-001'),
('CUP-001', 'LAB-002'),
('CUP-001', 'LAB-003'),
('CUP-002', 'LAB-001'),
('CUP-002', 'LAB-002'),
('CUP-002', 'LAB-003'),
('CUP-003', 'LAB-002'),
('CUP-003', 'LAB-003'),
('CUP-003', 'LAB-004'),
('CUP-004', 'LAB-003'),
('CUP-004', 'LAB-004'),
('CUP-004', 'LAB-005'),
('CUP-005', 'LAB-004'),
('CUP-005', 'LAB-005'),
('CUP-005', 'LAB-006'),
('CUP-006', 'LAB-005'),
('CUP-006', 'LAB-006'),
('CUP-006', 'LAB-007');
