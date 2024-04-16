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

package it.cnr.contab.config00.ejb;

import javax.ejb.Remote;

import it.cnr.contab.config00.esercizio.bulk.EsercizioBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.comp.ComponentException;

import java.rmi.RemoteException;

@Remote
public interface EsercizioComponentSession extends it.cnr.jada.ejb.CRUDComponentSession {
it.cnr.contab.config00.esercizio.bulk.EsercizioBulk apriPianoDiGestione(it.cnr.jada.UserContext param0,it.cnr.contab.config00.esercizio.bulk.EsercizioBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.config00.esercizio.bulk.EsercizioBulk cambiaStatoConBulk(it.cnr.jada.UserContext param0,it.cnr.contab.config00.esercizio.bulk.EsercizioBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isEsercizioChiuso(it.cnr.jada.UserContext userContext)throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.config00.esercizio.bulk.EsercizioBulk getLastEsercizioOpen(it.cnr.jada.UserContext userContext ) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isEsercizioAperto(it.cnr.jada.UserContext userContext)throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
EsercizioBulk getEsercizio( UserContext userContext ) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isEsercizioSpecificoChiusoPerAlmenoUnCds(UserContext userContext,Integer esercizio) throws ComponentException, RemoteException;
}
