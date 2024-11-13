package it.cnr.contab.web.rest.request;

import java.io.Serializable;
import java.util.List;

public class CreaMandatoRequest implements Serializable {
    private String unitaOrganizzativa;
    private boolean tipoPagamento; //Pagamento o Regolamento sospeso
    private String descrizioneMandato;
    private List<Long> pgObbligazioni;

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

    public List<Long> getPgObbligazioni() {
        return pgObbligazioni;
    }

    public void setPgObbligazioni(List<Long> pgObbligazioni) {
        this.pgObbligazioni = pgObbligazioni;
    }

    public String getDescrizioneMandato() {
        return descrizioneMandato;
    }

    public void setDescrizioneMandato(String descrizioneMandato) {
        this.descrizioneMandato = descrizioneMandato;
    }
}
