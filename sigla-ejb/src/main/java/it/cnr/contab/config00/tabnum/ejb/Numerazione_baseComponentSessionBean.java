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

package it.cnr.contab.config00.tabnum.ejb;
import javax.annotation.PostConstruct;
import javax.ejb.*;
@Stateless(name="CNRCONFIG00_TABNUM_EJB_Numerazione_baseComponentSession")
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Numerazione_baseComponentSessionBean extends it.cnr.jada.ejb.GenericComponentSessionBean implements Numerazione_baseComponentSession {
	private it.cnr.contab.config00.tabnum.comp.Numerazione_baseComponent componentObj;
	@Remove
	public void ejbRemove() throws EJBException {
		componentObj.release();
	}
	@PostConstruct
		public void ejbCreate() {
		componentObj = new it.cnr.contab.config00.tabnum.comp.Numerazione_baseComponent();
	}
	public static 
	Numerazione_baseComponentSessionBean newInstance() throws EJBException {
		return new Numerazione_baseComponentSessionBean();
	}
	public java.lang.Long creaNuovoProgressivo(it.cnr.jada.UserContext param0,java.lang.Integer param1,java.lang.String param2,java.lang.String param3,java.lang.String param4) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.bulk.BusyResourceException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			java.lang.Long result = componentObj.creaNuovoProgressivo(param0,param1,param2,param3,param4);
			component_invocation_succes(param0,componentObj);
			return result;
		} catch(it.cnr.jada.comp.NoRollbackException e) {
			component_invocation_succes(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.comp.ComponentException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(RuntimeException e) {
			throw uncaughtRuntimeException(param0,componentObj,e);
		} catch(Error e) {
			throw uncaughtError(param0,componentObj,e);
		}
	}
	public java.lang.Long creaNuovoProgressivoTemp(it.cnr.jada.UserContext param0,java.lang.Integer param1,java.lang.String param2,java.lang.String param3,java.lang.String param4) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.bulk.BusyResourceException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			java.lang.Long result = componentObj.creaNuovoProgressivoTemp(param0,param1,param2,param3,param4);
			component_invocation_succes(param0,componentObj);
			return result;
		} catch(it.cnr.jada.comp.NoRollbackException e) {
			component_invocation_succes(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.comp.ComponentException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(RuntimeException e) {
			throw uncaughtRuntimeException(param0,componentObj,e);
		} catch(Error e) {
			throw uncaughtError(param0,componentObj,e);
		}
	}
	public java.lang.Long creaNuovoProgressivoOffset(it.cnr.jada.UserContext param0,java.lang.Integer param1,java.lang.String param2,java.lang.String param3,java.lang.String param4, Integer param5, boolean param6) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.bulk.BusyResourceException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			java.lang.Long result = componentObj.creaNuovoProgressivoOffset(param0,param1,param2,param3,param4, param5, param6);
			component_invocation_succes(param0,componentObj);
			return result;
		} catch(it.cnr.jada.comp.NoRollbackException e) {
			component_invocation_succes(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.comp.ComponentException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(RuntimeException e) {
			throw uncaughtRuntimeException(param0,componentObj,e);
		} catch(Error e) {
			throw uncaughtError(param0,componentObj,e);
		}
	}
}
