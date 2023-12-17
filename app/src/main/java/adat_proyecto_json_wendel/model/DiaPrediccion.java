package adat_proyecto_json_wendel.model;

public class DiaPrediccion {
        private Cielo ceo;
        private String dataPredicion;
        private Integer nivelAviso;
        private ProbabilidadChoiva pchoiva;
        private Integer tMax;
        private Integer tMin;
        private TemperaturasFranxa tmaxFranxa;
        private TemperaturasFranxa tminFranxa;
        private Integer uvMax;
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




        public Integer gettMax() {
            return tMax;
        }




        public void settMax(Integer tMax) {
            this.tMax = tMax;
        }




        public Integer gettMin() {
            return tMin;
        }




        public void settMin(Integer tMin) {
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




        public Integer getUvMax() {
            return uvMax;
        }




        public void setUvMax(Integer uvMax) {
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