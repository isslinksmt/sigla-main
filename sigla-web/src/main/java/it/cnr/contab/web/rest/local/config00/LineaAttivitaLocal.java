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
import it.cnr.contab.web.rest.model.LineaAttivitaDto;
import it.cnr.contab.web.rest.model.UpdateLineaAttivitaDto;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Local
@Path("/lineaattivita")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(SIGLARoles.LINEA_ATTIVITA)
@Api("Linea Attivita")
public interface LineaAttivitaLocal {

	@POST
    @ApiOperation(value = "Inserisce Linea Attivita",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.LINEA_ATTIVITA +"'",
            response = LineaAttivitaDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response insert(@Context HttpServletRequest request, LineaAttivitaDto lineaAttivita) throws Exception;
    @GET
    @Path("/{cd_centro_responsabilita}/{cd_linea_attivita}")
    @ApiOperation(value = "Ritorna Linea Attivita",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.LINEA_ATTIVITA +"'",
            response = LineaAttivitaDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response get(@PathParam("cd_centro_responsabilita") String cd_centro_responsabilita,@PathParam("cd_linea_attivita") String cd_linea_attivita) throws Exception;
    @DELETE
    @Path("/{cd_centro_responsabilita}/{cd_linea_attivita}")
    @ApiOperation(value = "Elimina una Linea Attivita",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.LINEA_ATTIVITA +"'",
            response = String.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response delete(@PathParam("cd_centro_responsabilita") String cd_centro_responsabilita,@PathParam("cd_linea_attivita") String cd_linea_attivita) throws Exception;
    @PATCH
    @Path("/{cd_centro_responsabilita}/{cd_linea_attivita}")
    @ApiOperation(value = "Modifica una Linea Attivita",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.LINEA_ATTIVITA +"'",
            response = LineaAttivitaDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response update(@PathParam("cd_centro_responsabilita") String cd_centro_responsabilita, @PathParam("cd_linea_attivita") String cd_linea_attivita, UpdateLineaAttivitaDto updateLineaAttivitaDto) throws Exception;
}
