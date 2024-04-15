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

package it.cnr.contab.doccont00.core.bulk;

/**
 * Insert the type's description here.
 * Creation date: (18/06/2003 11.47.16)
 * @author: Simonetta Costa
 */
public class ImpegnoPGiroResiduoBulk extends ImpegnoPGiroBulk {
/**
 * ImpegnoPGiroResiduoBulk constructor comment.
 */
public ImpegnoPGiroResiduoBulk() {
	super();
	initialize();		
}
/**
 * ImpegnoPGiroResiduoBulk constructor comment.
 * @param cd_cds java.lang.String
 * @param esercizio java.lang.Integer
 * @param esercizio_originale java.lang.Integer
 * @param pg_obbligazione java.lang.Long
 */
public ImpegnoPGiroResiduoBulk(String cd_cds, Integer esercizio, Integer esercizio_originale, Long pg_obbligazione) {
	super(cd_cds, esercizio, esercizio_originale, pg_obbligazione);
	initialize();	
}
// metodo per inizializzare l'oggetto bulk
private void initialize () {
//	setCd_tipo_documento_cont( Numerazione_doc_contBulk.TIPO_IMP_RES );
	setFl_pgiro( new Boolean( true ));
}
}
