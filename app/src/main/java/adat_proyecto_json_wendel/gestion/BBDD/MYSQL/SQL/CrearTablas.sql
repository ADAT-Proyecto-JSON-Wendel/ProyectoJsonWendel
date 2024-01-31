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