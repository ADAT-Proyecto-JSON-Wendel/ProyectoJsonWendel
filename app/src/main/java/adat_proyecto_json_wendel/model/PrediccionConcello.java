package adat_proyecto_json_wendel.model;

import java.util.ArrayList;
import java.util.List;

public class PrediccionConcello {
    
    // Identificador único del concello
    private int idConcello;

    // Lista de predicciones diarias para el concello puede haber 3 o 4
    private List<DiaPrediccion> listaPredDiaConcello;

    // Nombre del concello
    private String nome;

    public PrediccionConcello() {
        this.listaPredDiaConcello = new ArrayList<>();
    }
    

    // Getter y Setter



    public PrediccionConcello(int idConcello, List<DiaPrediccion> listaPredDiaConcello, String nome) {
        this.idConcello = idConcello;
        this.listaPredDiaConcello = listaPredDiaConcello;
        this.nome = nome;
    }


    public int getIdConcello() {return idConcello;}

    public void setIdConcello(int idConcello) {this.idConcello = idConcello;}

    public List<DiaPrediccion> getListaPredDiaConcello() {return listaPredDiaConcello;}

    public void setListaPredDiaConcello(List<DiaPrediccion> listaPredDiaConcello) {this.listaPredDiaConcello = listaPredDiaConcello;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

}

