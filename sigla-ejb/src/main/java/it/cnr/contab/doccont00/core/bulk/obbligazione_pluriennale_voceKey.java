/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 24/11/2023
 */
package it.cnr.contab.doccont00.core.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class obbligazione_pluriennale_voceKey extends OggettoBulk implements KeyedPersistent {
	private String cdCds;
	private Long esercizio;
	private Long esercizioOriginale;
	private Long pgObbligazione;
	private Long anno;
	private String cdCentroResponsabilita;
	private String cdLineaAttivita;
	private String tiAppartenenza;
	private String tiGestione;
	private String cdVoce;
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: OBBLIGAZIONE_PLURIENNALE_VOCE
	 **/
	public obbligazione_pluriennale_voceKey() {
		super();
	}
	public obbligazione_pluriennale_voceKey(String cdCds, Long esercizio, Long esercizioOriginale, Long pgObbligazione, Long anno, String cdCentroResponsabilita, String cdLineaAttivita, String tiAppartenenza, String tiGestione, String cdVoce) {
		super();
		this.cdCds=cdCds;
		this.esercizio=esercizio;
		this.esercizioOriginale=esercizioOriginale;
		this.pgObbligazione=pgObbligazione;
		this.anno=anno;
		this.cdCentroResponsabilita=cdCentroResponsabilita;
		this.cdLineaAttivita=cdLineaAttivita;
		this.tiAppartenenza=tiAppartenenza;
		this.tiGestione=tiGestione;
		this.cdVoce=cdVoce;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof obbligazione_pluriennale_voceKey)) return false;
		obbligazione_pluriennale_voceKey k = (obbligazione_pluriennale_voceKey) o;
		if (!compareKey(getCdCds(), k.getCdCds())) return false;
		if (!compareKey(getEsercizio(), k.getEsercizio())) return false;
		if (!compareKey(getEsercizioOriginale(), k.getEsercizioOriginale())) return false;
		if (!compareKey(getPgObbligazione(), k.getPgObbligazione())) return false;
		if (!compareKey(getAnno(), k.getAnno())) return false;
		if (!compareKey(getCdCentroResponsabilita(), k.getCdCentroResponsabilita())) return false;
		if (!compareKey(getCdLineaAttivita(), k.getCdLineaAttivita())) return false;
		if (!compareKey(getTiAppartenenza(), k.getTiAppartenenza())) return false;
		if (!compareKey(getTiGestione(), k.getTiGestione())) return false;
		if (!compareKey(getCdVoce(), k.getCdVoce())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getCdCds());
		i = i + calculateKeyHashCode(getEsercizio());
		i = i + calculateKeyHashCode(getEsercizioOriginale());
		i = i + calculateKeyHashCode(getPgObbligazione());
		i = i + calculateKeyHashCode(getAnno());
		i = i + calculateKeyHashCode(getCdCentroResponsabilita());
		i = i + calculateKeyHashCode(getCdLineaAttivita());
		i = i + calculateKeyHashCode(getTiAppartenenza());
		i = i + calculateKeyHashCode(getTiGestione());
		i = i + calculateKeyHashCode(getCdVoce());
		return i;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Cds dell'obbligazione]
	 **/
	public void setCdCds(String cdCds)  {
		this.cdCds=cdCds;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Cds dell'obbligazione]
	 **/
	public String getCdCds() {
		return cdCds;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Esercizio dell'obbligazione]
	 **/
	public void setEsercizio(Long esercizio)  {
		this.esercizio=esercizio;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Esercizio dell'obbligazione]
	 **/
	public Long getEsercizio() {
		return esercizio;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Esercizio Originale dell'obbligazione]
	 **/
	public void setEsercizioOriginale(Long esercizioOriginale)  {
		this.esercizioOriginale=esercizioOriginale;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Esercizio Originale dell'obbligazione]
	 **/
	public Long getEsercizioOriginale() {
		return esercizioOriginale;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Numero dell'obbligazione]
	 **/
	public void setPgObbligazione(Long pgObbligazione)  {
		this.pgObbligazione=pgObbligazione;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Numero dell'obbligazione]
	 **/
	public Long getPgObbligazione() {
		return pgObbligazione;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Anno Obbligazione Pluriennale]
	 **/
	public void setAnno(Long anno)  {
		this.anno=anno;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Anno Obbligazione Pluriennale]
	 **/
	public Long getAnno() {
		return anno;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Codice identificativo del centro di responsabilità]
	 **/
	public void setCdCentroResponsabilita(String cdCentroResponsabilita)  {
		this.cdCentroResponsabilita=cdCentroResponsabilita;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Codice identificativo del centro di responsabilità]
	 **/
	public String getCdCentroResponsabilita() {
		return cdCentroResponsabilita;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Codice della linea di attivita]
	 **/
	public void setCdLineaAttivita(String cdLineaAttivita)  {
		this.cdLineaAttivita=cdLineaAttivita;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Codice della linea di attivita]
	 **/
	public String getCdLineaAttivita() {
		return cdLineaAttivita;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Definisce l'appartenenza (CNR o CdS) delle voci del piano finanziario di riferimento dell'obbligazione]
	 **/
	public void setTiAppartenenza(String tiAppartenenza)  {
		this.tiAppartenenza=tiAppartenenza;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Definisce l'appartenenza (CNR o CdS) delle voci del piano finanziario di riferimento dell'obbligazione]
	 **/
	public String getTiAppartenenza() {
		return tiAppartenenza;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Definisce la sezione (entrata o spesa) delle voci del piano finanziario di riferimento dell'obbligazione]
	 **/
	public void setTiGestione(String tiGestione)  {
		this.tiGestione=tiGestione;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Definisce la sezione (entrata o spesa) delle voci del piano finanziario di riferimento dell'obbligazione]
	 **/
	public String getTiGestione() {
		return tiGestione;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Codice del capitolo finanziario (mastrino VOCE_F) di riferimento dell'obbligazione]
	 **/
	public void setCdVoce(String cdVoce)  {
		this.cdVoce=cdVoce;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Codice del capitolo finanziario (mastrino VOCE_F) di riferimento dell'obbligazione]
	 **/
	public String getCdVoce() {
		return cdVoce;
	}
}