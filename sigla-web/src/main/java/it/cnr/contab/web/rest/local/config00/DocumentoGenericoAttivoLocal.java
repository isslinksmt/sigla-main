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

package it.cnr.contab.web.rest.local.config00;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import it.cnr.contab.web.rest.config.SIGLARoles;
import it.cnr.contab.web.rest.config.SIGLASecurityContext;
import it.cnr.contab.web.rest.model.DocumentoGenericoAttivoDto;
import it.cnr.contab.web.rest.model.TerzoUnitaOrganizzativaDto;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Local
@Path("/genericoattivo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(SIGLARoles.DOCUMENTO_GEN_ATTIVO)
@Api("Documento Generico Attivo")
public interface DocumentoGenericoAttivoLocal {

	@POST
    @ApiOperation(value = "Crea documento generico passivo ",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.DOCUMENTO_GEN_ATTIVO +"'",
            response = DocumentoGenericoAttivoDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response insert(@Context HttpServletRequest request, DocumentoGenericoAttivoDto documentoGenericoAttivoDto) throws Exception;
    @GET
    @Path("/{cd_cds}/{cd_unita_organizzativa}/{esercizio}/{pg_documento_generico}")
    @ApiOperation(value = "Ritorna documento generico attivo",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.DOCUMENTO_GEN_ATTIVO +"'",
            response = DocumentoGenericoAttivoDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response get(@PathParam("cd_cds") String cd_cds,@PathParam("cd_unita_organizzativa") String cd_unita_organizzativa,@PathParam("esercizio")Integer esercizio,
               @PathParam("pg_documento_generico") Long pg_documento_generico ) throws Exception;
    @DELETE
    @Path("/{cd_cds}/{cd_unita_organizzativa}/{esercizio}/{pg_documento_generico}")
    @ApiOperation(value = "Elimina un documento Generico Attivo'",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.DOCUMENTO_GEN_ATTIVO +"'",
            response = String.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response delete(@PathParam("cd_cds") String cd_cds,@PathParam("cd_unita_organizzativa") String cd_unita_organizzativa,@PathParam("esercizio")Integer esercizio,
                   @PathParam("pg_documento_generico") Long pg_documento_generico ) throws Exception;

    @PATCH
    @Path("/{cd_cds}/{cd_unita_organizzativa}/{esercizio}/{pg_documento_generico}")
    @ApiOperation(value = "Modifica un documento Generico Attivo",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.DOCUMENTO_GEN_ATTIVO +"'",
            response = DocumentoGenericoAttivoDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response update(@PathParam("cd_cds") String cd_cds,@PathParam("cd_unita_organizzativa") String cd_unita_organizzativa,@PathParam("esercizio")Integer esercizio,
                    @PathParam("pg_documento_generico") Long pg_documento_generico ) throws Exception;
    @GET
    @Path("/terzo/{esercizio}/{cd_unita_organizzativa}")
    @ApiOperation(value = "Ritorna il terzo dell'unita organizzativa con le modalità di incassao",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.DOCUMENTO_GEN_ATTIVO +"'",
            response = TerzoUnitaOrganizzativaDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response terzoUnitaOrganizzativa(@PathParam("esercizio") Integer esercizio,@PathParam("cd_unita_organizzativa") String cd_unita_organizzativa ) throws Exception;

}
