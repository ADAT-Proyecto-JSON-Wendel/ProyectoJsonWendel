DROP SCHEMA IF EXISTS prediccionconcellos;

CREATE SCHEMA IF NOT EXISTS prediccionconcellos;

USE prediccionconcellos;

CREATE TABLE IF NOT EXISTS Concellos (
	idConcello INT PRIMARY KEY,
	nome VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Predicciones (
	idPrediccion INT AUTO_INCREMENT PRIMARY KEY,
	idConcello INT,
	dataPredicion VARCHAR(255),
	nivelAviso INT,
	tMax INT,
	tMin INT,
	uvMax INT,
FOREIGN KEY (idConcello) REFERENCES Concellos(idConcello)
);

CREATE TABLE IF NOT EXISTS Cielo (
	idCielo INT AUTO_INCREMENT PRIMARY KEY,
	idPrediccion INT,
	manha INT,
	tarde INT,
	noite INT,
FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion)
);

CREATE TABLE IF NOT EXISTS ProbabilidadChoiva (
	idProbabilidadeChoiva INT AUTO_INCREMENT PRIMARY KEY,
	idPrediccion INT,
	manha INT,
	tarde INT,
	noite INT,
FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion)
);

CREATE TABLE IF NOT EXISTS TemperaturasFranxa (
	idTemperaturasFranxa INT AUTO_INCREMENT PRIMARY KEY,
	idPrediccion INT,
	manha INT,
	tarde INT,
	noite INT,
FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion)
);

CREATE TABLE IF NOT EXISTS Vento (
	idVento INT AUTO_INCREMENT PRIMARY KEY,
	idPrediccion INT,
	manha INT,
	tarde INT,
	noite INT,
	FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion)
);

INSERT INTO Concellos (idConcello, nome) VALUES (1, 'Santiago de Compostela');
INSERT INTO Predicciones (idPrediccion, idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) VALUES (1, 1, '2024-01-24', 2, 20, 10, 5);
INSERT INTO Cielo (idCielo, idPrediccion, manha, tarde, noite) VALUES (1, 1, 1, 2, 3);
INSERT INTO ProbabilidadChoiva (idProbabilidadeChoiva, idPrediccion, manha, tarde, noite) VALUES (1, 1, 20, 30, 10);
INSERT INTO TemperaturasFranxa (idTemperaturasFranxa, idPrediccion, manha, tarde, noite) VALUES (1, 1, 15, 18, 12);
INSERT INTO Vento (idVento, idPrediccion, manha, tarde, noite) VALUES (1, 1, 10, 15, 5);

START TRANSACTION;
BEGIN;
	SET SQL_SAFE_UPDATES = 0;
	DELETE FROM cielo;
	DELETE FROM probabilidadchoiva;
	DELETE FROM temperaturasfranxa;
	DELETE FROM vento;
	DELETE FROM predicciones;
	DELETE FROM concellos;
COMMIT;
SET AUTOCOMMIT = 1;
SET SQL_SAFE_UPDATES = 1;



