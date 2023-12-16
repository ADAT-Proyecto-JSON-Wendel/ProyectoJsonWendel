package model;

public class DiaPrediccion {
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