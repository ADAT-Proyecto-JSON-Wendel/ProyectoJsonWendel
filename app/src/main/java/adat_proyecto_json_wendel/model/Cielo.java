package adat_proyecto_json_wendel.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Cielo {

    // Estado del cielo por la mañana
    private Integer manha;

    // Estado del cielo por la tarde
    private Integer tarde;

    // Estado del cielo por la noche
    private Integer noite;
        
    public Integer getManha() {return manha;}

    public void setManha(Integer manha) {this.manha = manha;}

    public Integer getTarde() {return tarde;}

    public void setTarde(Integer tarde) {this.tarde = tarde;}

    public Integer getNoite() {return noite;}

    public void setNoite(Integer noite) {this.noite = noite;}
        
}