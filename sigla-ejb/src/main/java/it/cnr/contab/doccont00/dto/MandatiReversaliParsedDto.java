package it.cnr.contab.doccont00.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MandatiReversaliParsedDto implements Serializable {
    private String codiceEnte;
    private Integer esercizio;
    private Integer numeroDocumento;
    private Integer numeroBeneficiario;
    private Date dataCarico;
    private Date dataPagamento;
    private BigDecimal importo;
    private BigDecimal importoBeneficiario;
    private BigDecimal importoBeneficiarioPagato;
    private BigDecimal importoBeneficiarioDaPagare;
    private BigDecimal importoRitenute;
    private String copertura;
    private Integer conto;
    private String anagrafica;
    private Integer codiceCausale;
    private String causale;
    private String indirizzo;
    private String cap;
    private String localita;
    private String codiceRiscossione;
    private String iban;
    private Integer numeroRicevuta;
    private Integer numeroRicSto;
    private Date dataValutaEnte;
    private String codiceFiscalePartitaIva;
    private String stato;
    private String squadr;
    private String codiceIdentificativoEntePagopa;
    private String numeroAvvisoPagopa;

    public MandatiReversaliParsedDto() {
    }

    public String getCodiceEnte() {
        return codiceEnte;
    }

    public void setCodiceEnte(String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }

    public Integer getEsercizio() {
        return esercizio;
    }

    public void setEsercizio(Integer esercizio) {
        this.esercizio = esercizio;
    }

    public Integer getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Integer getNumeroBeneficiario() {
        return numeroBeneficiario;
    }

    public void setNumeroBeneficiario(Integer numeroBeneficiario) {
        this.numeroBeneficiario = numeroBeneficiario;
    }

    public Date getDataCarico() {
        return dataCarico;
    }

    public void setDataCarico(Date dataCarico) {
        this.dataCarico = dataCarico;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public BigDecimal getImportoBeneficiario() {
        return importoBeneficiario;
    }

    public void setImportoBeneficiario(BigDecimal importoBeneficiario) {
        this.importoBeneficiario = importoBeneficiario;
    }

    public BigDecimal getImportoBeneficiarioPagato() {
        return importoBeneficiarioPagato;
    }

    public void setImportoBeneficiarioPagato(BigDecimal importoBeneficiarioPagato) {
        this.importoBeneficiarioPagato = importoBeneficiarioPagato;
    }

    public BigDecimal getImportoBeneficiarioDaPagare() {
        return importoBeneficiarioDaPagare;
    }

    public void setImportoBeneficiarioDaPagare(BigDecimal importoBeneficiarioDaPagare) {
        this.importoBeneficiarioDaPagare = importoBeneficiarioDaPagare;
    }

    public BigDecimal getImportoRitenute() {
        return importoRitenute;
    }

    public void setImportoRitenute(BigDecimal importoRitenute) {
        this.importoRitenute = importoRitenute;
    }

    public String getCopertura() {
        return copertura;
    }

    public void setCopertura(String copertura) {
        this.copertura = copertura;
    }

    public Integer getConto() {
        return conto;
    }

    public void setConto(Integer conto) {
        this.conto = conto;
    }

    public String getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(String anagrafica) {
        this.anagrafica = anagrafica;
    }

    public Integer getCodiceCausale() {
        return codiceCausale;
    }

    public void setCodiceCausale(Integer codiceCausale) {
        this.codiceCausale = codiceCausale;
    }

    public String getCausale() {
        return causale;
    }

    public void setCausale(String causale) {
        this.causale = causale;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getCodiceRiscossione() {
        return codiceRiscossione;
    }

    public void setCodiceRiscossione(String codiceRiscossione) {
        this.codiceRiscossione = codiceRiscossione;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Integer getNumeroRicevuta() {
        return numeroRicevuta;
    }

    public void setNumeroRicevuta(Integer numeroRicevuta) {
        this.numeroRicevuta = numeroRicevuta;
    }

    public Integer getNumeroRicSto() {
        return numeroRicSto;
    }

    public void setNumeroRicSto(Integer numeroRicSto) {
        this.numeroRicSto = numeroRicSto;
    }

    public Date getDataValutaEnte() {
        return dataValutaEnte;
    }

    public void setDataValutaEnte(Date dataValutaEnte) {
        this.dataValutaEnte = dataValutaEnte;
    }

    public String getCodiceFiscalePartitaIva() {
        return codiceFiscalePartitaIva;
    }

    public void setCodiceFiscalePartitaIva(String codiceFiscalePartitaIva) {
        this.codiceFiscalePartitaIva = codiceFiscalePartitaIva;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getSquadr() {
        return squadr;
    }

    public void setSquadr(String squadr) {
        this.squadr = squadr;
    }

    public String getCodiceIdentificativoEntePagopa() {
        return codiceIdentificativoEntePagopa;
    }

    public void setCodiceIdentificativoEntePagopa(String codiceIdentificativoEntePagopa) {
        this.codiceIdentificativoEntePagopa = codiceIdentificativoEntePagopa;
    }

    public String getNumeroAvvisoPagopa() {
        return numeroAvvisoPagopa;
    }

    public void setNumeroAvvisoPagopa(String numeroAvvisoPagopa) {
        this.numeroAvvisoPagopa = numeroAvvisoPagopa;
    }
}
