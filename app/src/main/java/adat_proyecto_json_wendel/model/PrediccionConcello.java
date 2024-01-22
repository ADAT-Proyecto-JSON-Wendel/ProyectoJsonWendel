package adat_proyecto_json_wendel.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class PrediccionConcello {
    
    // Identificador único del concello
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConcello;

    // Lista de predicciones diarias para el concello puede haber 3 o 4
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prediccionConcello_id")
    private List<DiaPrediccion> listaPredDiaConcello;

    // Nombre del concello
    private String nome;

    public PrediccionConcello() {
        this.listaPredDiaConcello = new ArrayList<>();
    }

    // Getter y Setter

    public int getIdConcello() {return idConcello;}

    public void setIdConcello(int idConcello) {this.idConcello = idConcello;}

    public List<DiaPrediccion> getListaPredDiaConcello() {return listaPredDiaConcello;}

    public void setListaPredDiaConcello(List<DiaPrediccion> listaPredDiaConcello) {this.listaPredDiaConcello = listaPredDiaConcello;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

}

