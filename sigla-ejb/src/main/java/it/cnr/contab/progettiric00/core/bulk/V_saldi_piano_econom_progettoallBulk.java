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
 * Created on Mar 17, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.progettiric00.core.bulk;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author mspasiano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class V_saldi_piano_econom_progettoallBulk extends V_saldi_piano_econom_progettoBase {
	private static final long serialVersionUID = 5918224310476589096L;

	public V_saldi_piano_econom_progettoallBulk() {
		super();
	}
	
	/**
	 * Ritorna l'assestato della quota finanziata calcolata da:
	 * Stanziamento + Variazioni Positive - Variazioni Negative + Quote Ricevute
	 */
	public BigDecimal getAssestatoFinanziamento() {
		return getStanziamentoFin().add(getVariapiuFin()).subtract(getVariamenoFin()).add(getTrasferitoFinanziamento());
	}

	/*
	 * Ritorna la quota trasferita della quota finanziata calcolata come valore netto di:
	 * Quote Trasferite - Quote Ricevute
	 */
	public BigDecimal getTrasferitoFinanziamento() {
		return Optional.ofNullable(this.getSaldoTrasferimentoFinanziamento())
				 	   .filter(el->el.compareTo(BigDecimal.ZERO)<0)
				 	   .map(BigDecimal::abs)
				 	   .orElse(BigDecimal.ZERO);
	}
	
	/*
	 * Ritorna la quota utilizzata dell'assestato della quota finanziata calcolata da:
	 * Quota Impegnata - Quota Trasferita
	 */
	public BigDecimal getUtilizzatoAssestatoFinanziamento() {
		return this.getImpaccFin().add(this.getTrasferitoFinanziamento());
	}

	/*
	 * Ritorna la quota di assestato di tipo finanziamento non utilizzato disponibile per nuovi impegni/trasferimenti
	 */
	public BigDecimal getDispAssestatoFinanziamento() {
		return getAssestatoFinanziamento().subtract(this.getUtilizzatoAssestatoFinanziamento());
	}
	
	/*
	 * Ritorna l'assestato della quota cofinanziata calcolata da:
	 * Stanziamento + Variazioni Positive - Variazioni Negative + Quote Ricevute
	 */
	public BigDecimal getAssestatoCofinanziamento() {
		return getStanziamentoCofin().add(getVariapiuCofin()).subtract(getVariamenoCofin()).add(getTrasferitoCofinanziamento());
	}

	/*
	 * Ritorna la quota trasferita della quota cofinanziata calcolata come valore netto di:
	 * Quote Trasferite - Quote Ricevute
	 */
	public BigDecimal getTrasferitoCofinanziamento() {
		return Optional.ofNullable(this.getSaldoTrasferimentoCofinanziamento())
				 	   .filter(el->el.compareTo(BigDecimal.ZERO)<0)
				 	   .map(BigDecimal::abs)
				 	   .orElse(BigDecimal.ZERO);
	}

	/*
	 * Ritorna la quota utilizzata dell'assestato della quota cofinanziata calcolata da:
	 * Assestato - Quota Impegnata - Quota Trasferita
	 */
	public BigDecimal getUtilizzatoAssestatoCofinanziamento() {
		return this.getImpaccCofin().add(this.getTrasferitoCofinanziamento());
	}

	/*
	 * Ritorna la quota di assestato di tipo cofinanziamento non utilizzato e 
	 * disponibile per nuovi impegni/trasferimenti
	 */
	public BigDecimal getDispAssestatoCofinanziamento() {
		return this.getAssestatoCofinanziamento().subtract(this.getUtilizzatoAssestatoCofinanziamento());
	}

	/*
	 * Ritorna la quota finanziata disponibile per nuovo stanziamento calcolato come:
	 * Importo Previsto - Importo già Assestato
	 */
	public BigDecimal getDispResiduaFinanziamento() {
		return getImportoFin().subtract(getAssestatoFinanziamento());
	}

	/*
	 * Ritorna la quota cofinanziata disponibile per nuovo stanziamento calcolato come:
	 * Importo Previsto - Importo già Assestato
	 */
	public BigDecimal getDispResiduaCofinanziamento() {
		return getImportoCofin().subtract(getAssestatoCofinanziamento());
	}

	/*
	 * Ritorna l'assestato totale calcolata da:
	 * assestato della quota finanziata + assestato della quota cofinanziata
	 */
	public BigDecimal getAssestato() {
		return getAssestatoFinanziamento().add(getAssestatoCofinanziamento());
	}

	/*
	 * Ritorna la quota totale trasferita calcolata come valore di:
	 * Quote Trasferite Finanziate - Quote Trasferite Cofinanziate
	 */
	public BigDecimal getTrasferito() {
		return getTrasferitoFinanziamento().add(getTrasferitoCofinanziamento());
	}
	
	/*
	 * Ritorna la quota totale disponibile per nuovo stanziamento calcolata come:
	 * Quota Finanziata Disponibile - Quota Cofinanziata Disponibile
	 */
	public BigDecimal getDispResidua() {
		return getDispResiduaFinanziamento().add(getDispResiduaCofinanziamento());
	}

	/*
	 * Ritorna il saldo delle quote finanziate trasferite calcolate come:
	 * Quota Trasferita - Quota Ricevuta
	 */
	public BigDecimal getSaldoTrasferimentoFinanziamento() {
		return getTrasfpiuFin().subtract(getTrasfmenoFin());
	}

	/*
	 * Ritorna il saldo delle quote cofinanziate trasferite calcolate come:
	 * Quota Trasferita - Quota Ricevuta
	 */
	public BigDecimal getSaldoTrasferimentoCofinanziamento() {
		return getTrasfpiuCofin().subtract(getTrasfmenoCofin());
	}
	
	/*
	 * Ritorna il totale impegni/accertamenti ottenuto come:
	 * Totale Impegni/Accertamenti Quota Finanziata + Totale Impegni/Accertamenti Quota Cofinanziata
	 */
	public BigDecimal getImpacc() {
		return this.getImpaccFin().add(this.getImpaccCofin());
	}

	/*
	 * Ritorna il totale mandati/reversali ottenuto come:
	 * Totale Mandati/Reversali Quota Finanziata + Totale Mandati/Reversali Quota Cofinanziata
	 */
	public BigDecimal getManris() {
		return this.getManrisFin().add(this.getManrisCofin());
	}
}