package it.cnr.contab.web.rest.request;

import java.io.Serializable;
import java.util.List;

public class CreaReversaleRequest implements Serializable {
    private String unitaOrganizzativa;
    private String cds;
    private String cdsOrigine;
    private int esercizio;
    private String descrizioneReversale;
    private List<Long> pgDocumentiAttivi;

    public String getUnitaOrganizzativa() {
        return unitaOrganizzativa;
    }

    public void setUnitaOrganizzativa(String unitaOrganizzativa) {
        this.unitaOrganizzativa = unitaOrganizzativa;
    }

    public String getDescrizioneReversale() {
        return descrizioneReversale;
    }

    public void setDescrizioneReversale(String descrizioneReversale) {
        this.descrizioneReversale = descrizioneReversale;
    }

    public List<Long> getPgDocumentiAttivi() {
        return pgDocumentiAttivi;
    }

    public void setPgDocumentiAttivi(List<Long> pgDocumentiAttivi) {
        this.pgDocumentiAttivi = pgDocumentiAttivi;
    }

    public String getCds() {
        return cds;
    }

    public void setCds(String cds) {
        this.cds = cds;
    }

    public int getEsercizio() {
        return esercizio;
    }

    public void setEsercizio(int esercizio) {
        this.esercizio = esercizio;
    }

    public String getCdsOrigine() {
        return cdsOrigine;
    }

    public void setCdsOrigine(String cdsOrigine) {
        this.cdsOrigine = cdsOrigine;
    }
}
