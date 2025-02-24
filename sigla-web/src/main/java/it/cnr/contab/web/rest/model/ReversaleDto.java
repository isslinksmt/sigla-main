package it.cnr.contab.web.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReversaleDto implements Serializable {
    private Long pgReversale;
    private String cds;
    private String unitaOrganizzativa;
    private String descrizioneReversale;
    private BigDecimal importoReversale;
    private String tesoreria;
    public Long getPgReversale() {
        return pgReversale;
    }

    public void setPgReversale(Long pgReversale) {
        this.pgReversale = pgReversale;
    }

    public String getCds() {
        return cds;
    }

    public void setCds(String cds) {
        this.cds = cds;
    }

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

    public BigDecimal getImportoReversale() {
        return importoReversale;
    }

    public void setImportoReversale(BigDecimal importoReversale) {
        this.importoReversale = importoReversale;
    }
    public String getTesoreria() {
        return tesoreria;
    }

    public void setTesoreria(String tesoreria) {
        this.tesoreria = tesoreria;
    }
}
