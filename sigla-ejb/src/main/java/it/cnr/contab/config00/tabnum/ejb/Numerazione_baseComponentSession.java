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

import javax.ejb.Remote;

@Remote
public interface Numerazione_baseComponentSession extends it.cnr.jada.ejb.GenericComponentSession {
java.lang.Long creaNuovoProgressivo(it.cnr.jada.UserContext param0,java.lang.Integer param1,java.lang.String param2,java.lang.String param3,java.lang.String param4) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.bulk.BusyResourceException,java.rmi.RemoteException;
java.lang.Long creaNuovoProgressivoTemp(it.cnr.jada.UserContext param0,java.lang.Integer param1,java.lang.String param2,java.lang.String param3,java.lang.String param4) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.bulk.BusyResourceException,java.rmi.RemoteException;
java.lang.Long creaNuovoProgressivoOffset(it.cnr.jada.UserContext param0,java.lang.Integer param1,java.lang.String param2,java.lang.String param3,java.lang.String param4, Integer param5, boolean param6) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.bulk.BusyResourceException,java.rmi.RemoteException;

}
