package it.cnr.contab.web.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MandatoDto implements Serializable {

    private String cdCds;
    private Integer esercizio;
    private Long pgMandato;
    private String cdUnitaOrganizzativa;
    private String cdCdsOrigine;
    private String cdUoOrigine;
    private String cdTipoDocumentoCont;
    private String tiMandato;
    private String tiCompetenzaResiduo;
    private String descrizioneMandato;
    private String stato;
    private LocalDate dataEmissione;
    private LocalDate dataTrasmissione;
    private String dataPagamento;
    private String dataAnnullamento;
    private BigDecimal importoMandato;
    private BigDecimal importoPagato;
    private String statoTrasimissione;
    private String statoCoge;
    private BigDecimal importoRitenute;
    private LocalDate dataRitrasmissione;
    private LocalDate dataFirma;
    private String tesoreria;

    public String getCdCds() {
        return cdCds;
    }

    public void setCdCds(String cdCds) {
        this.cdCds = cdCds;
    }

    public Integer getEsercizio() {
        return esercizio;
    }

    public void setEsercizio(Integer esercizio) {
        this.esercizio = esercizio;
    }

    public Long getPgMandato() {
        return pgMandato;
    }

    public void setPgMandato(Long pgMandato) {
        this.pgMandato = pgMandato;
    }

    public String getCdUnitaOrganizzativa() {
        return cdUnitaOrganizzativa;
    }

    public void setCdUnitaOrganizzativa(String cdUnitaOrganizzativa) {
        this.cdUnitaOrganizzativa = cdUnitaOrganizzativa;
    }

    public String getCdCdsOrigine() {
        return cdCdsOrigine;
    }

    public void setCdCdsOrigine(String cdCdsOrigine) {
        this.cdCdsOrigine = cdCdsOrigine;
    }

    public String getCdUoOrigine() {
        return cdUoOrigine;
    }

    public void setCdUoOrigine(String cdUoOrigine) {
        this.cdUoOrigine = cdUoOrigine;
    }

    public String getCdTipoDocumentoCont() {
        return cdTipoDocumentoCont;
    }

    public void setCdTipoDocumentoCont(String cdTipoDocumentoCont) {
        this.cdTipoDocumentoCont = cdTipoDocumentoCont;
    }

    public String getTiMandato() {
        return tiMandato;
    }

    public void setTiMandato(String tiMandato) {
        this.tiMandato = tiMandato;
    }

    public String getTiCompetenzaResiduo() {
        return tiCompetenzaResiduo;
    }

    public void setTiCompetenzaResiduo(String tiCompetenzaResiduo) {
        this.tiCompetenzaResiduo = tiCompetenzaResiduo;
    }

    public String getDescrizioneMandato() {
        return descrizioneMandato;
    }

    public void setDescrizioneMandato(String descrizioneMandato) {
        this.descrizioneMandato = descrizioneMandato;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public LocalDate getDataTrasmissione() {
        return dataTrasmissione;
    }

    public void setDataTrasmissione(LocalDate dataTrasmissione) {
        this.dataTrasmissione = dataTrasmissione;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDataAnnullamento() {
        return dataAnnullamento;
    }

    public void setDataAnnullamento(String dataAnnullamento) {
        this.dataAnnullamento = dataAnnullamento;
    }

    public BigDecimal getImportoMandato() {
        return importoMandato;
    }

    public void setImportoMandato(BigDecimal importoMandato) {
        this.importoMandato = importoMandato;
    }

    public BigDecimal getImportoPagato() {
        return importoPagato;
    }

    public void setImportoPagato(BigDecimal importoPagato) {
        this.importoPagato = importoPagato;
    }

    public String getStatoTrasimissione() {
        return statoTrasimissione;
    }

    public void setStatoTrasimissione(String statoTrasimissione) {
        this.statoTrasimissione = statoTrasimissione;
    }

    public String getStatoCoge() {
        return statoCoge;
    }

    public void setStatoCoge(String statoCoge) {
        this.statoCoge = statoCoge;
    }

    public BigDecimal getImportoRitenute() {
        return importoRitenute;
    }

    public void setImportoRitenute(BigDecimal importoRitenute) {
        this.importoRitenute = importoRitenute;
    }

    public LocalDate getDataRitrasmissione() {
        return dataRitrasmissione;
    }

    public void setDataRitrasmissione(LocalDate dataRitrasmissione) {
        this.dataRitrasmissione = dataRitrasmissione;
    }

    public LocalDate getDataFirma() {
        return dataFirma;
    }

    public void setDataFirma(LocalDate dataFirma) {
        this.dataFirma = dataFirma;
    }

    public String getTesoreria() {
        return tesoreria;
    }

    public void setTesoreria(String tesoreria) {
        this.tesoreria = tesoreria;
    }
}
