INSERT INTO Concellos (idConcello, nome) VALUES (1, 'Santiago de Compostela');
INSERT INTO Predicciones (idPrediccion, idConcello, dataPredicion, nivelAviso, tMax, tMin, uvMax) VALUES (1, 1, '2024-01-24', 2, 20, 10, 5);
INSERT INTO Cielo (idCielo, idPrediccion, manha, tarde, noite) VALUES (1, 1, 1, 2, 3);
INSERT INTO ProbabilidadChoiva (idProbabilidadeChoiva, idPrediccion, manha, tarde, noite) VALUES (1, 1, 20, 30, 10);
INSERT INTO TemperaturasFranxa (idTemperaturasFranxa, idPrediccion, manha, tarde, noite) VALUES (1, 1, 15, 18, 12);
INSERT INTO Vento (idVento, idPrediccion, manha, tarde, noite) VALUES (1, 1, 10, 15, 5);