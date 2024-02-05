CREATE TABLE Concellos (
    idConcello INT PRIMARY KEY,
    nome VARCHAR(255)
);

CREATE TABLE Predicciones (
    idPrediccion INT AUTO_INCREMENT PRIMARY KEY,
    idConcello INT,
    dataPredicion VARCHAR(255),
    nivelAviso INT,
    tMax INT,
    tMin INT,
    uvMax INT,
    FOREIGN KEY (idConcello) REFERENCES Concellos(idConcello)
);

CREATE TABLE Cielo (
    idCielo INT AUTO_INCREMENT PRIMARY KEY,
    idPrediccion INT,
    manha INT,
    tarde INT,
    noite INT,
    FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion)
);

CREATE TABLE ProbabilidadeChoiva (
    idProbabilidadeChoiva INT AUTO_INCREMENT PRIMARY KEY,
    idPrediccion INT,
    manha INT,
    tarde INT,
    noite INT,
    FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion)
);

CREATE TABLE TemperaturasFranxa (
    idTemperaturasFranxa INT AUTO_INCREMENT PRIMARY KEY,
    idPrediccion INT,
    manha INT,
    tarde INT,
    noite INT,
    FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion)
);

CREATE TABLE Vento (
    idVento INT AUTO_INCREMENT PRIMARY KEY,
    idPrediccion INT,
    manha INT,
    tarde INT,
    noite INT,
    FOREIGN KEY (idPrediccion) REFERENCES Predicciones(idPrediccion)
);
