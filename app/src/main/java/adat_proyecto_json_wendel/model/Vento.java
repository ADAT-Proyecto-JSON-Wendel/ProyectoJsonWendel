package adat_proyecto_json_wendel.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Vento {
    
    // Atributos que representan la intensidad del viento en diferentes momentos del día
        private Integer manha;
        private Integer tarde;
        private Integer noite;

               
        public Integer getManha() {return manha;}

        public void setManha(Integer manha) {this.manha = manha;}

        public Integer getTarde() {return tarde;}

        public void setTarde(Integer tarde) {this.tarde = tarde;}

        public Integer getNoite() {return noite;}

        public void setNoite(Integer noite) {this.noite = noite;}

    }
