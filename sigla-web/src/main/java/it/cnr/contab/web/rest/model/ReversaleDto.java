package it.cnr.contab.web.rest.model;

import java.io.Serializable;

public class ReversaleDto implements Serializable {
    private Long pgReversale;

    public Long getPgReversale() {
        return pgReversale;
    }

    public void setPgReversale(Long pgReversale) {
        this.pgReversale = pgReversale;
    }
}
