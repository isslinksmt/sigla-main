package it.cnr.contab.web.rest.request;

import java.io.Serializable;
import java.util.List;

public class CreaMandatoRequest implements Serializable {
    private String unitaOrganizzativa;
    private boolean tipoPagamento; //Pagamento o Regolamento sospeso
    private String descrizioneMandato;
    private List<Long> pgDocumentiPassivi;
    private boolean stampa;
    public String getUnitaOrganizzativa() {
        return unitaOrganizzativa;
    }

    public void setUnitaOrganizzativa(String unitaOrganizzativa) {
        this.unitaOrganizzativa = unitaOrganizzativa;
    }

    public boolean isTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(boolean tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
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

    public boolean isStampa() {
        return stampa;
    }

    public void setStampa(boolean stampa) {
        this.stampa = stampa;
    }
}
