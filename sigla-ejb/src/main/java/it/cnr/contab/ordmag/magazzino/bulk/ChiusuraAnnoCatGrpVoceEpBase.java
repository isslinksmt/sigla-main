/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 19/03/2024
 */
package it.cnr.contab.ordmag.magazzino.bulk;
import it.cnr.jada.persistency.Keyed;

import java.math.BigDecimal;

public class ChiusuraAnnoCatGrpVoceEpBase extends ChiusuraAnnoCatGrpVoceEpKey implements Keyed {
//    IMP_TOTALE DECIMAL(21,6) NOT NULL
	private java.math.BigDecimal impTotale;

	private java.math.BigDecimal impPlusValenze;

	private java.math.BigDecimal impMinusValenze;

	private java.math.BigDecimal impDecrementi;

	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: CHIUSURA_ANNO_CATGRP_VOCE_EP
	 **/
	public ChiusuraAnnoCatGrpVoceEpBase() {
		super();
	}
	public ChiusuraAnnoCatGrpVoceEpBase(Integer pgChiusura, Integer anno, String tipoChiusura, String cdCategoriaGruppo, Integer esercizio, String cdVoceEp) {
		super(pgChiusura, anno, tipoChiusura, cdCategoriaGruppo, esercizio, cdVoceEp);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Importo totale per la categoria gruppo]
	 **/
	public java.math.BigDecimal getImpTotale() {
		return impTotale;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Importo totale per la categoria gruppo]
	 **/
	public void setImpTotale(java.math.BigDecimal impTotale)  {
		this.impTotale=impTotale;
	}

	public BigDecimal getImpPlusValenze() {
		return impPlusValenze;
	}

	public void setImpPlusValenze(BigDecimal impPlusValenze) {
		this.impPlusValenze = impPlusValenze;
	}

	public BigDecimal getImpMinusValenze() {
		return impMinusValenze;
	}

	public void setImpMinusValenze(BigDecimal impMinusValenze) {
		this.impMinusValenze = impMinusValenze;
	}

	public BigDecimal getImpDecrementi() {
		return impDecrementi;
	}

	public void setImpDecrementi(BigDecimal impDecrementi) {
		this.impDecrementi = impDecrementi;
	}
}