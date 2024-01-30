package adat_proyecto_json_wendel.gestion.BBDD.H2;

import java.util.ArrayList;
import java.util.List;

import adat_proyecto_json_wendel.model.Cielo;
import adat_proyecto_json_wendel.model.DiaPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;
import adat_proyecto_json_wendel.model.ProbabilidadChoiva;
import adat_proyecto_json_wendel.model.TemperaturasFranxa;
import adat_proyecto_json_wendel.model.Vento;

public class PrediccionExample {
    

     public static PrediccionConcello getPrediccionConcelloEjemplo() {
        PrediccionConcello prediccionConcello = new PrediccionConcello();
        prediccionConcello.setIdConcello(1);
        prediccionConcello.setNome("Santiago de Compostela prueba");

        List<DiaPrediccion> listaPredicciones = new ArrayList<>();

        DiaPrediccion dia1 = new DiaPrediccion();
        dia1.setCeo(crearCieloEjemplo(1, 2, 3));
        dia1.setDataPredicion("2024-01-24");
        dia1.setNivelAviso(2);
        dia1.setPchoiva(crearProbabilidadChoivaEjemplo(20, 30, 10));
        dia1.settMax(20);
        dia1.settMin(10);
        dia1.setTmaxFranxa(crearTemperaturasFranxaEjemplo(15, 18, 12));
        dia1.setUvMax(5);
        dia1.setVento(crearVentoEjemplo(10, 15, 5));
        listaPredicciones.add(dia1);

        DiaPrediccion dia2 = new DiaPrediccion();
        dia2.setDataPredicion("2024-01-24");
        listaPredicciones.add(dia2);


        prediccionConcello.setListaPredDiaConcello(listaPredicciones);

        return prediccionConcello;
    }

    private static Cielo crearCieloEjemplo(int manha, int tarde, int noite) {
        Cielo cielo = new Cielo();
        cielo.setManha(manha);
        cielo.setTarde(tarde);
        cielo.setNoite(noite);
        return cielo;
    }

    private static ProbabilidadChoiva crearProbabilidadChoivaEjemplo(int manha, int tarde, int noite) {
        ProbabilidadChoiva probabilidadChoiva = new ProbabilidadChoiva();
        probabilidadChoiva.setManha(manha);
        probabilidadChoiva.setTarde(tarde);
        probabilidadChoiva.setNoite(noite);
        return probabilidadChoiva;
    }

    private static TemperaturasFranxa crearTemperaturasFranxaEjemplo(int manha, int tarde, int noite) {
        TemperaturasFranxa temperaturasFranxa = new TemperaturasFranxa();
        temperaturasFranxa.setManha(manha);
        temperaturasFranxa.setTarde(tarde);
        temperaturasFranxa.setNoite(noite);
        return temperaturasFranxa;
    }

    private static Vento crearVentoEjemplo(int manha, int tarde, int noite) {
        Vento vento = new Vento();
        vento.setManha(manha);
        vento.setTarde(tarde);
        vento.setNoite(noite);
        return vento;
    }

}
