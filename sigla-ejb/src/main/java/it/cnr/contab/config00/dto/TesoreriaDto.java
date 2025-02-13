package it.cnr.contab.config00.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TesoreriaDto {
    private String ds_estesa;

    public String getDs_estesa() {
        return ds_estesa;
    }

    public void setDs_estesa(String ds_estesa) {
        this.ds_estesa = ds_estesa;
    }
}
