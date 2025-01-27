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

package it.cnr.contab.doccont00.ejb;

import java.rmi.RemoteException;

public class TransactionalAccertamentoResiduoComponentSession extends TransactionalAccertamentoComponentSession implements AccertamentoResiduoComponentSession {


    public String controllaDettagliScadenzaAccertamento(it.cnr.jada.UserContext param0, it.cnr.contab.doccont00.core.bulk.AccertamentoBulk param1, it.cnr.contab.doccont00.core.bulk.Accertamento_scadenzarioBulk param2) throws RemoteException, it.cnr.jada.comp.ComponentException {
        try {
            return (String) invoke("controllaDettagliScadenzaAccertamento", new Object[]{
                    param0,
                    param1,
                    param2});
        } catch (java.rmi.RemoteException e) {
            throw e;
        } catch (java.lang.reflect.InvocationTargetException e) {
            try {
                throw e.getTargetException();
            } catch (it.cnr.jada.comp.ComponentException ex) {
                throw ex;
            } catch (Throwable ex) {
                throw new java.rmi.RemoteException("Uncaugth exception", ex);
            }
        }
    }

    public void cancellaAccertamentoModTemporanea(it.cnr.jada.UserContext param0, it.cnr.contab.doccont00.core.bulk.Accertamento_modificaBulk param1) throws it.cnr.jada.comp.ComponentException, it.cnr.jada.persistency.PersistencyException, it.cnr.jada.persistency.IntrospectionException, java.rmi.RemoteException {
        try {
            invoke("cancellaAccertamentoModTemporanea", new Object[]{
                    param0,
                    param1});
        } catch (java.rmi.RemoteException e) {
            throw e;
        } catch (java.lang.reflect.InvocationTargetException e) {
            try {
                throw e.getTargetException();
            } catch (it.cnr.jada.comp.ComponentException ex) {
                throw ex;
            } catch (Throwable ex) {
                throw new java.rmi.RemoteException("Uncaugth exception", ex);
            }
        }
    }


}
