package adat_proyecto_json_wendel.gestion.BBDD;

import java.sql.Connection;

import adat_proyecto_json_wendel.model.DiaPrediccion;
import adat_proyecto_json_wendel.model.PrediccionConcello;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CargarBaseDeDatos {

    public static EntityManagerFactory emf;
    public static EntityManager em;

    public static void closeEntities(){
        em.close();
        emf.close();
    }

    public static void crearEntidades(Connection conn){
        emf = Persistence.createEntityManagerFactory("app/src/main/java/adat_proyecto_json_wendel/gestion/BBDD/persistence.xml");
        em = emf.createEntityManager();

        em.getTransaction().begin();

        cargarDatos(em);

        em.getTransaction().commit();
    }

    public static void cargarDatos(EntityManager em){
        PrediccionConcello concello = new PrediccionConcello();
        concello.setNome("nomeConcello");

        DiaPrediccion diaPrediccion = new DiaPrediccion();

        concello.getListaPredDiaConcello().add(diaPrediccion);

        em.persist(concello);
        em.persist(diaPrediccion);
    }
}
