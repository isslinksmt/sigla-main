/*
 * Copyright (C) 2019  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package it.cnr.contab.coepcoan00.core.bulk;

import it.cnr.contab.anagraf00.core.bulk.TerzoBulk;
import it.cnr.contab.config00.pdcep.bulk.ContoBulk;
import it.cnr.contab.config00.pdcep.bulk.Voce_epBulk;
import it.cnr.contab.config00.sto.bulk.CdsBulk;
import it.cnr.contab.docamm00.docs.bulk.TipoDocumentoEnum;
import it.cnr.contab.util.enumeration.TipoIVA;
import it.cnr.jada.bulk.FieldProperty;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.util.OrderedHashtable;

import java.util.*;
import java.util.stream.Collectors;

public class Movimento_cogeBulk extends Movimento_cogeBase {

    // sezione
    public final static String SEZIONE_DARE = "D";
    public final static String SEZIONE_AVERE = "A";
    public final static Dictionary sezioneKeys;
    public final static Dictionary naturaContoKeys = Voce_epBulk.natura_voce_Keys;
    public final static String STATO_DEFINITIVO = "D";
    // tipo

    public final static java.util.Dictionary STATO_ATTIVA;
    public final static String ATTIVA_YES = "Y";
    public final static String ATTIVA_NO = "N";

    public final static Dictionary
            tipoRigaKeys = new OrderedHashtable(),
            tipoKeys = TipoIVA.TipoIVAKeys;

    static {
        for (TipoRiga tipoRiga : TipoRiga.values()) {
            tipoRigaKeys.put(tipoRiga.value, tipoRiga.label);
        }
    }

    static {
        sezioneKeys = new Hashtable();
        sezioneKeys.put(SEZIONE_DARE, "Dare");
        sezioneKeys.put(SEZIONE_AVERE, "Avere");
    }

    static {
        STATO_ATTIVA = new it.cnr.jada.util.OrderedHashtable();
        STATO_ATTIVA.put(ATTIVA_YES, "Y");
        STATO_ATTIVA.put(ATTIVA_NO, "N");
    }

    public java.lang.Long pgScritturaAnnullata;
    // Attiva
    public java.lang.String attiva;
    protected ContoBulk conto = new ContoBulk();
    protected Scrittura_partita_doppiaBulk scrittura = new Scrittura_partita_doppiaBulk();
    protected TerzoBulk terzo;
    protected IDocumentoCogeBulk documentoCoge;
    private PartitarioBulk partitario;
    public Movimento_cogeBulk() {
        super();
    }

    public Movimento_cogeBulk(java.lang.String cd_cds, java.lang.Integer esercizio, java.lang.Long pg_movimento, java.lang.Long pg_scrittura) {
        super(cd_cds, esercizio, pg_movimento, pg_scrittura);
        setScrittura(new it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk(cd_cds, esercizio, pg_scrittura));
    }

    public static String getControSezione(String sezione) {
        return Movimento_cogeBulk.SEZIONE_DARE.equals(sezione) ? Movimento_cogeBulk.SEZIONE_AVERE : Movimento_cogeBulk.SEZIONE_DARE;
    }

    /**
     * Insert the method's description here.
     * Creation date: (29/08/2003 9.21.30)
     *
     * @return java.lang.String
     */
    public java.lang.String getAttiva() {
        it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk scrittura = this.getScrittura();
        if (scrittura == null)
            return null;
        return scrittura.getAttiva();
    }

    /**
     * Insert the method's description here.
     * Creation date: (29/08/2003 9.21.30)
     *
     * @param newAttiva java.lang.String
     */
    public void setAttiva(java.lang.String newAttiva) {
        this.getScrittura().setAttiva(newAttiva);
    }

    public java.lang.String getCd_cds() {
        it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk scrittura = this.getScrittura();
        if (scrittura == null)
            return null;
        return scrittura.getCd_cds();
    }

    public void setCd_cds(java.lang.String cd_cds) {
        this.getScrittura().setCd_cds(cd_cds);
    }

    public Integer getCd_terzo() {
        return Optional.ofNullable(terzo)
                .map(TerzoBulk::getCd_terzo)
                .orElse(null);
    }

    public void setCd_terzo(Integer cd_terzo) {
        Optional.ofNullable(terzo)
                .orElse(new TerzoBulk())
                .setCd_terzo(cd_terzo);
    }

    public java.lang.String getCd_unita_organizzativa() {
        it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk scrittura = this.getScrittura();
        if (scrittura == null)
            return null;
        return scrittura.getCd_unita_organizzativa();
    }

    public void setCd_unita_organizzativa(java.lang.String cd_uo) {
        this.getScrittura().setCd_unita_organizzativa(cd_uo);
    }

    public java.lang.String getCd_voce_ep() {
        it.cnr.contab.config00.pdcep.bulk.ContoBulk conto = this.getConto();
        if (conto == null)
            return null;
        return conto.getCd_voce_ep();
    }

    public void setCd_voce_ep(java.lang.String cd_voce_ep) {
        this.getConto().setCd_voce_ep(cd_voce_ep);
    }

    public CdsBulk getCds() {
        it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk scrittura = this.getScrittura();
        if (scrittura == null)
            return null;
        return scrittura.getCds();
    }

    public void setCds(CdsBulk cds) {
        this.getScrittura().setCds(cds);
    }

    /**
     * @return it.cnr.contab.config00.pdcep.bulk.ContoBulk
     */
    public it.cnr.contab.config00.pdcep.bulk.ContoBulk getConto() {
        return conto;
    }

    /**
     * @param newConto it.cnr.contab.config00.pdcep.bulk.ContoBulk
     */
    public void setConto(it.cnr.contab.config00.pdcep.bulk.ContoBulk newConto) {
        conto = newConto;
    }

    public String getDs_terzo() {
        if (scrittura != null && scrittura.getTerzo() != null)
            return scrittura.getTerzo().getDenominazione_sede();
        return "";
    }

    public java.lang.Integer getEsercizio() {
        it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk scrittura = this.getScrittura();
        if (scrittura == null)
            return null;
        return scrittura.getEsercizio();
    }

    public void setEsercizio(java.lang.Integer esercizio) {
        this.getScrittura().setEsercizio(esercizio);
    }

    public java.lang.Long getPg_scrittura() {
        it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk scrittura = this.getScrittura();
        if (scrittura == null)
            return null;
        return scrittura.getPg_scrittura();
    }

    public void setPg_scrittura(java.lang.Long pg_scrittura) {
        this.getScrittura().setPg_scrittura(pg_scrittura);
    }

    /**
     * Insert the method's description here.
     * Creation date: (29/08/2003 9.21.30)
     *
     * @return java.lang.Long
     */
    public java.lang.Long getPgScritturaAnnullata() {
        it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk scrittura = this.getScrittura();
        if (scrittura == null)
            return null;
        return scrittura.getPg_scrittura_annullata();
    }

    /**
     * Insert the method's description here.
     * Creation date: (29/08/2003 9.21.30)
     *
     * @param newPgScritturaAnnullata java.lang.Long
     */
    public void setPgScritturaAnnullata(java.lang.Long newPgScritturaAnnullata) {
        this.getScrittura().setPg_scrittura_annullata(newPgScritturaAnnullata);
    }

    /**
     * @return it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk
     */
    public Scrittura_partita_doppiaBulk getScrittura() {
        return scrittura;
    }

    /**
     * @param newScrittura it.cnr.contab.coepcoan00.core.bulk.Scrittura_partita_doppiaBulk
     */
    public void setScrittura(Scrittura_partita_doppiaBulk newScrittura) {
        scrittura = newScrittura;
    }

    /**
     * Il metodo restituisce il dictionary per la gestione degli stati ATTIVA
     * e NON ATTIVA
     */
    public java.util.Dictionary getStato_attivaKeys() {
        return STATO_ATTIVA;
    }

    /**
     * Inizializza il ricevente per la visualizzazione in un <code>FormController</code>
     * in stato <code>SEARCH</code>.
     * Questo metodo viene invocato automaticamente da un
     * <code>it.cnr.jada.util.action.CRUDBP</code> quando viene inizializzato
     * per la ricerca di un OggettoBulk.
     */
    public OggettoBulk initializeForSearch(it.cnr.jada.util.action.CRUDBP bp, it.cnr.jada.action.ActionContext context) {
        setEsercizio(((it.cnr.contab.utenze00.bp.CNRUserContext) context.getUserContext()).getEsercizio());
        setCd_cds(((it.cnr.contab.utenze00.bp.CNRUserContext) context.getUserContext()).getCd_cds());
        getScrittura().setUo(it.cnr.contab.utenze00.bulk.CNRUserInfo.getUnita_organizzativa(context));
        getScrittura().setTerzo(new it.cnr.contab.anagraf00.core.bulk.TerzoBulk());
        return this;
    }

    public boolean isROConto() {
        return getConto() != null &&
                getConto().getCrudStatus() != UNDEFINED;

    }

    /**
     * @return La rappresentazione a video della partita
     */
    public String getPartita() {
        return Optional.ofNullable(getCd_tipo_documento())
                .map(s -> {
                    return Arrays.asList(
                            TipoDocumentoEnum.fromValue(s).getLabel(),
                            Optional.ofNullable(getEsercizio_documento()).map(String::valueOf).orElse(null),
                            Optional.ofNullable(getCd_cds_documento()).orElse(null),
                            Optional.ofNullable(getPg_numero_documento()).map(String::valueOf).orElse(null)
                    ).stream().filter(Objects::nonNull).collect(
                            Collectors.joining("/")
                    );
                }).orElse(null);
    }

    /**
     * Effettua una validazione formale del contenuto dello stato dell'oggetto
     * bulk. Viene invocato da <code>CRUDBP</code> in
     * seguito ad una richiesta di salvataggio.
     *
     * @throws it.cnr.jada.bulk.ValidationException Se la validazione fallisce.
     *                                              Contiene il messaggio da visualizzare all'utente per la notifica
     *                                              dell'errore di validazione.
     * @see it.cnr.jada.util.action.CRUDBP
     */
    public void validate() throws ValidationException {
        if (getCd_voce_ep() == null)
            throw new ValidationException("E' necessario selezionare il Conto per ogni movimento inserito");
        if (getTi_riga() == null)
            throw new ValidationException("E' necessario selezionare la Tipologia di Riga");
        if (getDt_da_competenza_coge() == null)
            throw new ValidationException("E' necessario inserire il \"Periodo Competenza Da\" per ogni movimento inserito");
        if (getDt_a_competenza_coge() == null)
            throw new ValidationException("E' necessario inserire il \"Periodo Competenza A\" per ogni movimento inserito");
        if (getIm_movimento() == null)
            throw new ValidationException("E' necessario inserire l'Importo per ogni movimento inserito");
        if (getIm_movimento().compareTo(new java.math.BigDecimal(0)) <= 0)
            throw new ValidationException("L'Importo movimento deve essere maggiore di zero");
        if (getDt_da_competenza_coge().compareTo(getDt_a_competenza_coge()) > 0)
            throw new ValidationException("Il \"Periodo Competenza Da\" deve essere inferiore al \"Periodo Competenza A\" per ogni movimento inserito");
    }

    public boolean isSezioneDare() {
        return Movimento_cogeBulk.SEZIONE_DARE.equals(this.getSezione());
    }

    public boolean isSezioneAvere() {
        return Movimento_cogeBulk.SEZIONE_AVERE.equals(this.getSezione());
    }

    public boolean isRigaTipoDebito() {
        return TipoRiga.DEBITO.value().equals(this.getTi_riga());
    }

    public boolean isRigaTipoTesoreria() {
        return TipoRiga.TESORERIA.value().equals(this.getTi_riga());
    }

    public boolean isRigaTipoIvaAcquisto() {
        return TipoRiga.IVA_ACQUISTO.value().equals(this.getTi_riga());
    }

    public boolean isRigaTipoIvaAcquistoSplit() {
        return TipoRiga.IVA_ACQUISTO_SPLIT.value().equals(this.getTi_riga());
    }

    public boolean isRigaTipoIvaVendite() {
        return TipoRiga.IVA_VENDITE.value().equals(this.getTi_riga());
    }

    public boolean isRigaTipoIvaVenditeSplit() {
        return TipoRiga.IVA_VENDITE_SPLIT.value().equals(this.getTi_riga());
    }

    public boolean isRigaTipoIvaOrdinaria() {
        return this.isRigaTipoIvaAcquisto() ||
                this.isRigaTipoIvaVendite();
    }

    public boolean isRigaTipoIvaSplit() {
        return this.isRigaTipoIvaAcquistoSplit() ||
                this.isRigaTipoIvaVenditeSplit();
    }

    public boolean isRigaTipoIva() {
        return this.isRigaTipoIvaOrdinaria() ||
                this.isRigaTipoIvaSplit();
    }

    public TerzoBulk getTerzo() {
        return terzo;
    }

    public void setTerzo(TerzoBulk terzo) {
        this.terzo = terzo;
    }

    public IDocumentoCogeBulk getDocumentoCoge() {
        return Optional.ofNullable(this.documentoCoge)
                .orElseGet(() -> {
                    return Optional.ofNullable(getCd_tipo_documento())
                            .map(s -> TipoDocumentoEnum.fromValue(s))
                            .map(tipoDocumentoEnum -> tipoDocumentoEnum.getDocumentoCogeBulk())
                            .orElse(null);
                });
    }

    public void setDocumentoCoge(IDocumentoCogeBulk documentoCoge) {
        this.documentoCoge = documentoCoge;
    }

    @Override
    public String getCd_cds_documento() {
        return Optional.ofNullable(this.getDocumentoCoge())
                .flatMap(documentoCogeBulk -> Optional.ofNullable(documentoCogeBulk.getCd_cds()))
                .orElse(super.getCd_cds_documento());
    }

    @Override
    public String getCd_uo_documento() {
        return Optional.ofNullable(this.getDocumentoCoge())
                .flatMap(documentoCogeBulk -> Optional.ofNullable(documentoCogeBulk.getCd_uo()))
                .orElse(super.getCd_uo_documento());
    }

    @Override
    public Integer getEsercizio_documento() {
        return Optional.ofNullable(this.getDocumentoCoge())
                .flatMap(documentoCogeBulk -> Optional.ofNullable(documentoCogeBulk.getEsercizio()))
                .orElse(super.getEsercizio_documento());
    }

    @Override
    public Long getPg_numero_documento() {
        return Optional.ofNullable(this.getDocumentoCoge())
                .flatMap(documentoCogeBulk -> Optional.ofNullable(documentoCogeBulk.getPg_doc()))
                .orElse(super.getPg_numero_documento());
    }

    public PartitarioBulk getPartitario() {
        return Optional.ofNullable(partitario)
                .orElseGet(() -> {
                    PartitarioBulk partitarioBulk = new PartitarioBulk();
                    partitarioBulk.setCd_tipo_documento(getCd_tipo_documento());
                    partitarioBulk.setEsercizio_documento(getEsercizio_documento());
                    partitarioBulk.setCd_cds_documento(getCd_cds_documento());
                    partitarioBulk.setCd_uo_documento(getCd_uo_documento());
                    partitarioBulk.setPg_numero_documento(getPg_numero_documento());
                    return partitarioBulk;
                });
    }

    public void setPartitario(PartitarioBulk partitario) {
        this.partitario = partitario;
        setCd_tipo_documento(Optional.ofNullable(partitario).map(PartitarioBulk::getCd_tipo_documento).orElse(null));
        setEsercizio_documento(Optional.ofNullable(partitario).map(PartitarioBulk::getEsercizio_documento).orElse(null));
        setCd_cds_documento(Optional.ofNullable(partitario).map(PartitarioBulk::getCd_cds_documento).orElse(null));
        setCd_uo_documento(Optional.ofNullable(partitario).map(PartitarioBulk::getCd_uo_documento).orElse(null));
        setPg_numero_documento(Optional.ofNullable(partitario).map(PartitarioBulk::getPg_numero_documento).orElse(null));
    }

    public boolean isDebitoCredito() {
        return Arrays.asList(TipoRiga.CREDITO, TipoRiga.DEBITO)
                .stream()
                .filter(tipoRiga -> tipoRiga.value.equals(getTi_riga()))
                .findAny().isPresent();

    }

    @Override
    public Boolean isOptionDisabled(FieldProperty fieldProperty, Object key) {
        if (fieldProperty.getName().equalsIgnoreCase("ti_riga")) {
            if (Optional.ofNullable(getScrittura())
                    .flatMap(scritturaPartitaDoppiaBulk -> Optional.ofNullable(scritturaPartitaDoppiaBulk.getOrigine_scrittura()))
                    .map(s -> s.equalsIgnoreCase(Scrittura_partita_doppiaBulk.Origine.PRIMA_NOTA_MANUALE.name()))
                    .orElse(Boolean.FALSE)
            ) {
                return !Arrays.asList(TipoRiga.COSTO, TipoRiga.RICAVO, TipoRiga.ATTIVITA,
                                TipoRiga.PASSIVITA, TipoRiga.CREDITO, TipoRiga.DEBITO, TipoRiga.GENERICO)
                        .stream()
                        .filter(tipoRiga -> tipoRiga.value.equals(key))
                        .findAny().isPresent();
            }
        }
        return super.isOptionDisabled(fieldProperty, key);
    }

    public enum TipoRiga {
        IVA_ACQUISTO("IVAA", "IVA ACQUISTO"),
        IVA_ACQUISTO_SPLIT("IVAS", "IVA ACQUISTO SPLIT"),
        IVA_VENDITE("IVAV", "IVA VENDITA"),
        IVA_VENDITE_SPLIT("IVVS", "IVA VENDITE SPLIT"),
        COSTO("COS", "COSTO", "EEC"),
        RICAVO("RIC", "RICAVO", "EER"),
        DEBITO("DEB", "DEBITO", "NUP"),
        CREDITO("CRE", "CREDITO", "NUA"),
        ATTIVITA("ATT", "ATTIVITA'", "NUA"),
        PASSIVITA("PAS", "PASSIVITA'", "NUP"),
        CESPITE("CSP", "CESPITE"),
        TESORERIA("BAN", "TESORERIA"),
        GENERICO("GEN", "GENERICO");

        private final String value;
        private final String label;
        private String naturaConto;

        TipoRiga(String value, String label) {
            this.value = value;
            this.label = label;
        }
        TipoRiga(String value, String label, String naturaConto) {
            this(value, label);
            this.naturaConto = naturaConto;
        }

        public static TipoRiga getValueFrom(String value) {
            for (TipoRiga tipoRiga : TipoRiga.values()) {
                if (tipoRiga.value.equals(value))
                    return tipoRiga;
            }
            throw new IllegalArgumentException("Tipo riga no found for value: " + value);
        }

        public String value() {
            return value;
        }

        public String label() {
            return label;
        }

        public String naturaConto() {
            return naturaConto;
        }
    }
}
