package it.cnr.contab.web.rest.request;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class CreaMandatoRequest implements Serializable {
    private String cdCds;
    private String unitaOrganizzativa;
    private int esercizio;
    private String descrizioneMandato;
    private List<Long> pgDocumentiPassivi;
    private String tesoreria;
    private Timestamp dtEmissione;
    public String getUnitaOrganizzativa() {
        return unitaOrganizzativa;
    }

    public void setUnitaOrganizzativa(String unitaOrganizzativa) {
        this.unitaOrganizzativa = unitaOrganizzativa;
    }

    public List<Long> getPgDocumentiPassivi() {
        return pgDocumentiPassivi;
    }

    public void setPgDocumentiPassivi(List<Long> pgDocumentiPassivi) {
        this.pgDocumentiPassivi = pgDocumentiPassivi;
    }

    public String getDescrizioneMandato() {
        return descrizioneMandato;
    }

    public void setDescrizioneMandato(String descrizioneMandato) {
        this.descrizioneMandato = descrizioneMandato;
    }

    public String getCdCds() {
        return cdCds;
    }

    public void setCdCds(String cdCds) {
        this.cdCds = cdCds;
    }

    public int getEsercizio() {
        return esercizio;
    }

    public void setEsercizio(int esercizio) {
        this.esercizio = esercizio;
    }

    public String getTesoreria() {
        return tesoreria;
    }

    public void setTesoreria(String tesoreria) {
        this.tesoreria = tesoreria;
    }

    public Timestamp getDtEmissione() {
        return dtEmissione;
    }

    public void setDtEmissione(Timestamp dtEmissione) {
        this.dtEmissione = dtEmissione;
    }
}
