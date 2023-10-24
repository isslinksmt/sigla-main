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

package it.cnr.contab.prevent01.bulk;

import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk;
import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceHome;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.PersistentCache;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;

import java.sql.Connection;

/**
 * Insert the type's description here.
 * Creation date: (24/10/2023 12.20.00)
 * @author: Piergiorgio Faraglia
 */
public class RiaccertamentoResiduiPassiviHome extends BulkHome {

	public RiaccertamentoResiduiPassiviHome(Class clazz, Connection conn) {
		super(clazz, conn);
	}

	public RiaccertamentoResiduiPassiviHome(Class clazz, Connection conn, PersistentCache persistentCache) {
		super(clazz, conn, persistentCache);
	}

	public RiaccertamentoResiduiPassiviHome(Connection conn, PersistentCache persistentCache) {
		super(RiaccertamentoResiduiPassiviBulk.class, conn, persistentCache);
	}

	public SQLBuilder selectVoce_daByClause(UserContext userContext, RiaccertamentoResiduiPassiviBulk parametri,
										   Elemento_voceHome elementoVoceHome, Elemento_voceBulk elementoVoceBulk,
										   CompoundFindClause compoundfindclause) throws PersistencyException, ComponentException {
		SQLBuilder sql = elementoVoceHome.createSQLBuilder();
		sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS, CNRUserContext.getEsercizio(userContext));
		sql.addSQLClause("AND","TI_GESTIONE", sql.EQUALS, parametri.getTi_gestione());
		sql.addOrderBy("CD_ELEMENTO_VOCE");
		sql.addClause( compoundfindclause );
		return sql;
	}

	public SQLBuilder selectVoce_aByClause(UserContext userContext, RiaccertamentoResiduiPassiviBulk parametri,
											Elemento_voceHome elementoVoceHome, Elemento_voceBulk elementoVoceBulk,
											CompoundFindClause compoundfindclause) throws PersistencyException, ComponentException {
		SQLBuilder sql = elementoVoceHome.createSQLBuilder();
		sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS, CNRUserContext.getEsercizio(userContext));
		sql.addSQLClause("AND","TI_GESTIONE", sql.EQUALS, parametri.getTi_gestione());
		sql.addOrderBy("CD_ELEMENTO_VOCE");
		sql.addClause( compoundfindclause );
		return sql;
	}

}
