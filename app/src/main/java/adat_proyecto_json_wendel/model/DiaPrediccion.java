package adat_proyecto_json_wendel.model;

public class DiaPrediccion {
    
    // Estado del cielo para diferentes franjas horarias
    private Cielo ceo;

    // Fecha de la predicción
    private String dataPredicion;

    // Nivel de aviso meteorológico
    private Integer nivelAviso;

    // Probabilidad de lluvia para diferentes franjas horarias
    private ProbabilidadChoiva pchoiva;

    // Temperatura máxima del día
    private Integer tMax;

    // Temperatura mínima del día
    private Integer tMin;

    public DiaPrediccion() {
    }

    public DiaPrediccion(Cielo ceo, String dataPredicion, Integer nivelAviso, ProbabilidadChoiva pchoiva, Integer tMax,
            Integer tMin, TemperaturasFranxa tmaxFranxa, TemperaturasFranxa tminFranxa, Integer uvMax, Vento vento) {
        this.ceo = ceo;
        this.dataPredicion = dataPredicion;
        this.nivelAviso = nivelAviso;
        this.pchoiva = pchoiva;
        this.tMax = tMax;
        this.tMin = tMin;
        this.tmaxFranxa = tmaxFranxa;
        this.tminFranxa = tminFranxa;
        this.uvMax = uvMax;
        this.vento = vento;
    }

    // Temperaturas para diferentes franjas horarias
    private TemperaturasFranxa tmaxFranxa;
    private TemperaturasFranxa tminFranxa;

    // Índice ultravioleta máximo
    private Integer uvMax;

    // Información sobre el viento para diferentes franjas horarias
    private Vento vento;
               
    public Cielo getCeo() {return ceo;}

    public void setCeo(Cielo ceo) {this.ceo = ceo;}

    public String getDataPredicion() {return dataPredicion;}

    public void setDataPredicion(String dataPredicion) {this.dataPredicion = dataPredicion;}

    public Integer getNivelAviso() {return nivelAviso;}

    public void setNivelAviso(Integer nivelAviso) {this.nivelAviso = nivelAviso;}

    public ProbabilidadChoiva getPchoiva() {return pchoiva;}

    public void setPchoiva(ProbabilidadChoiva pchoiva) {this.pchoiva = pchoiva;}

    public Integer gettMax() {return tMax;}

    public void settMax(Integer tMax) {this.tMax = tMax;}

    public Integer gettMin() {return tMin;}

    public void settMin(Integer tMin) {this.tMin = tMin;}

    public TemperaturasFranxa getTmaxFranxa() {return tmaxFranxa;}

    public void setTmaxFranxa(TemperaturasFranxa tmaxFranxa) {this.tmaxFranxa = tmaxFranxa;}

    public TemperaturasFranxa getTminFranxa() { return tminFranxa;}

    public void setTminFranxa(TemperaturasFranxa tminFranxa) {this.tminFranxa = tminFranxa;}

    public Integer getUvMax() {return uvMax;}

    public void setUvMax(Integer uvMax) {this.uvMax = uvMax;}

    public Vento getVento() {return vento;}

    public void setVento(Vento vento) {this.vento = vento;}

    }