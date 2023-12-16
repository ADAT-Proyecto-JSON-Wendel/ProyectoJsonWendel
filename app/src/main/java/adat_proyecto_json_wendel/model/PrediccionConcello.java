package model;
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

    public static class DiaPrediccion {
        private Cielo ceo;
        private String dataPredicion;
        private Integer nivelAviso;
        private ProbabilidadChoiva pchoiva;
        private int tMax;
        private int tMin;
        private TemperaturasFranxa tmaxFranxa;
        private TemperaturasFranxa tminFranxa;
        private int uvMax;
        private Vento vento;
        
        public Cielo getCeo() {
            return ceo;
        }
        public void setCeo(Cielo ceo) {
            this.ceo = ceo;
        }
        public String getDataPredicion() {
            return dataPredicion;
        }
        public void setDataPredicion(String dataPredicion) {
            this.dataPredicion = dataPredicion;
        }
        public Integer getNivelAviso() {
            return nivelAviso;
        }
        public void setNivelAviso(Integer nivelAviso) {
            this.nivelAviso = nivelAviso;
        }
        public ProbabilidadChoiva getPchoiva() {
            return pchoiva;
        }
        public void setPchoiva(ProbabilidadChoiva pchoiva) {
            this.pchoiva = pchoiva;
        }
        public int gettMax() {
            return tMax;
        }
        public void settMax(int tMax) {
            this.tMax = tMax;
        }
        public int gettMin() {
            return tMin;
        }
        public void settMin(int tMin) {
            this.tMin = tMin;
        }
        public TemperaturasFranxa getTmaxFranxa() {
            return tmaxFranxa;
        }
        public void setTmaxFranxa(TemperaturasFranxa tmaxFranxa) {
            this.tmaxFranxa = tmaxFranxa;
        }
        public TemperaturasFranxa getTminFranxa() {
            return tminFranxa;
        }
        public void setTminFranxa(TemperaturasFranxa tminFranxa) {
            this.tminFranxa = tminFranxa;
        }
        public int getUvMax() {
            return uvMax;
        }
        public void setUvMax(int uvMax) {
            this.uvMax = uvMax;
        }
        public Vento getVento() {
            return vento;
        }
        public void setVento(Vento vento) {
            this.vento = vento;
        }
        @Override
        public String toString() {
            return "DiaPrediccion [ceo=" + ceo + ", dataPredicion=" + dataPredicion + ", nivelAviso=" + nivelAviso
                    + ", pchoiva=" + pchoiva + ", tMax=" + tMax + ", tMin=" + tMin + ", tmaxFranxa=" + tmaxFranxa
                    + ", tminFranxa=" + tminFranxa + ", uvMax=" + uvMax + ", vento=" + vento + "]";
        }

        
    }

    public static class Cielo {
        private int manha;
        private int tarde;
        private int noite;
        public int getManha() {
            return manha;
        }
        public void setManha(int manha) {
            this.manha = manha;
        }
        public int getTarde() {
            return tarde;
        }
        public void setTarde(int tarde) {
            this.tarde = tarde;
        }
        public int getNoite() {
            return noite;
        }
        public void setNoite(int noite) {
            this.noite = noite;
        }
        @Override
        public String toString() {
            return "Cielo [manha=" + manha + ", tarde=" + tarde + ", noite=" + noite + "]";
        }

        
    }

    public static class ProbabilidadChoiva {
        private int manha;
        private int tarde;
        private int noite;
        public int getManha() {
            return manha;
        }
        public void setManha(int manha) {
            this.manha = manha;
        }
        public int getTarde() {
            return tarde;
        }
        public void setTarde(int tarde) {
            this.tarde = tarde;
        }
        public int getNoite() {
            return noite;
        }
        public void setNoite(int noite) {
            this.noite = noite;
        }
        @Override
        public String toString() {
            return "ProbabilidadChoiva [manha=" + manha + ", tarde=" + tarde + ", noite=" + noite + "]";
        }

        
    }

    public static class TemperaturasFranxa {
        private int manha;
        private int tarde;
        private int noite;
        public int getManha() {
            return manha;
        }
        public void setManha(int manha) {
            this.manha = manha;
        }
        public int getTarde() {
            return tarde;
        }
        public void setTarde(int tarde) {
            this.tarde = tarde;
        }
        public int getNoite() {
            return noite;
        }
        public void setNoite(int noite) {
            this.noite = noite;
        }
        @Override
        public String toString() {
            return "TemperaturasFranxa [manha=" + manha + ", tarde=" + tarde + ", noite=" + noite + "]";
        }

       
    }

    public static class Vento {
        private int manha;
        private int tarde;
        private int noite;

        public int getManha() {
            return manha;
        }
        public void setManha(int manha) {
            this.manha = manha;
        }
        public int getTarde() {
            return tarde;
        }
        public void setTarde(int tarde) {
            this.tarde = tarde;
        }
        public int getNoite() {
            return noite;
        }
        public void setNoite(int noite) {
            this.noite = noite;
        }
        @Override
        public String toString() {
            return "Vento [manha=" + manha + ", tarde=" + tarde + ", noite=" + noite + "]";
        }
        
    }
}
