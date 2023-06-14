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

/*
 * Created by Aurelio's BulkGenerator 1.0
 * Date 01/10/2007
 */
package it.cnr.contab.doccont00.consultazioni.bulk;
import it.cnr.contab.doccont00.core.bulk.ReversaleBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.Persistent;
import it.cnr.jada.util.action.CRUDBP;

import java.sql.Timestamp;

public class V_cons_reversali_riscontrateBulk extends OggettoBulk implements Persistent {
	public V_cons_reversali_riscontrateBulk() {
		super();
	}
//  ESERCIZIO DECIMAL(4,0) NOT NULL
	private java.lang.Integer esercizio;
 
//    DT_REGISTRAZIONE TIMESTAMP(7) NOT NULL
	private java.sql.Timestamp dt_registrazione;

	//    DT_INCASSO TIMESTAMP(7) NOT NULL
	private java.sql.Timestamp dt_incasso;

//    CD_SOSPESO VARCHAR(24) NOT NULL
	private java.lang.String cd_sospeso;
 
//    CD_UNITA_ORGANIZZATIVA VARCHAR(30) NOT NULL
	private java.lang.String cd_unita_organizzativa;
 
//    PG_REVERSALE DECIMAL(10,0) NOT NULL
	private java.lang.Long pg_reversale;
 
//    CD_CDS  VARCHAR(30) NOT NULL
	private java.lang.String cd_cds;
 
//    IM_SOSPESO DECIMAL(15,2) NOT NULL
	private java.math.BigDecimal im_sospeso;
 
//    IM_ASSOCIATO DECIMAL(15,2) NOT NULL
	private java.math.BigDecimal im_associato;

	// TI_REVERSALE CHAR(1) NOT NULL
	private java.lang.String ti_reversale;

	public final static java.util.Dictionary tipoReversaleKeys = ReversaleBulk.tipoReversaleCdSKeys;
	public java.lang.Integer getEsercizio() {
		return esercizio;
	}
	public void setEsercizio(java.lang.Integer esercizio)  {
		this.esercizio=esercizio;
	}
	public java.sql.Timestamp getDt_registrazione() {
		return dt_registrazione;
	}
	public void setDt_registrazione(java.sql.Timestamp dt_registrazione)  {
		this.dt_registrazione=dt_registrazione;
	}
	public java.lang.String getCd_sospeso() {
		return cd_sospeso;
	}
	public void setCd_sospeso(java.lang.String cd_sospeso)  {
		this.cd_sospeso=cd_sospeso;
	}
	public java.lang.String getCd_unita_organizzativa() {
		return cd_unita_organizzativa;
	}
	public void setCd_unita_organizzativa(java.lang.String cd_unita_organizzativa)  {
		this.cd_unita_organizzativa=cd_unita_organizzativa;
	}
	public java.lang.String getCd_cds() {
		return cd_cds;
	}
	public void setCd_cds(java.lang.String cd_cds)  {
		this.cd_cds=cd_cds;
	}
	public java.math.BigDecimal getIm_sospeso() {
		return im_sospeso;
	}
	public void setIm_sospeso(java.math.BigDecimal im_sospeso)  {
		this.im_sospeso=im_sospeso;
	}
	public java.math.BigDecimal getIm_associato() {
		return im_associato;
	}
	public void setIm_associato(java.math.BigDecimal im_associato)  {
		this.im_associato=im_associato;
	}
	public java.lang.Long getPg_reversale() {
		return pg_reversale;
	}
	public void setPg_reversale(java.lang.Long pg_reversale) {
		this.pg_reversale = pg_reversale;
	}

	public Timestamp getDt_incasso() {
		return dt_incasso;
	}

	public void setDt_incasso(Timestamp dt_incasso) {
		this.dt_incasso = dt_incasso;
	}

	public String getTi_reversale() {
		return ti_reversale;
	}

	public void setTi_reversale(String ti_reversale) {
		this.ti_reversale = ti_reversale;
	}
}