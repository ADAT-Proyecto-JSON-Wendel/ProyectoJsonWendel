package adat_proyecto_json_wendel.model;

import java.util.List;

public class PrediccionConcello {
    private int idConcello;
    private List<DiaPrediccion> listaPredDiaConcello;
    private String nome;



    public int getIdConcello() {
        return idConcello;
    }



    public void setIdConcello(int idConcello) {
        this.idConcello = idConcello;
    }



    public List<DiaPrediccion> getListaPredDiaConcello() {
        return listaPredDiaConcello;
    }



    public void setListaPredDiaConcello(List<DiaPrediccion> listaPredDiaConcello) {
        this.listaPredDiaConcello = listaPredDiaConcello;
    }



    public String getNome() {
        return nome;
    }



    public void setNome(String nome) {
        this.nome = nome;
    }



    @Override
    public String toString() {
        return "PrediccionConcello [idConcello=" + idConcello + ", listaPredDiaConcello=" + listaPredDiaConcello
                + ", nome=" + nome + "]";
    }
}
