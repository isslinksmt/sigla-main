package it.cnr.contab.web.rest.local.doccont;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import it.cnr.contab.web.rest.config.SIGLARoles;
import it.cnr.contab.web.rest.config.SIGLASecurityContext;
import it.cnr.contab.web.rest.model.MandatoDto;
import it.cnr.contab.web.rest.request.CreaMandatoRequest;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Local
@Path("/mandato")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(SIGLARoles.MANDATO_REST)
@Api("Mandato")
public interface MandatoLocal {

    @GET
    @Path("/{cd_cds}/{cd_unita_organizzativa}/{esercizio}/{pg_mandato}")
    @ApiOperation(value = "Ritorna mandato",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.MANDATO_REST +"'",
            response = MandatoDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response get(@PathParam("cd_cds") String cdCds,
                 @PathParam("cd_unita_organizzativa") String cdUnitaOrganizzativa,
                 @PathParam("esercizio") Integer esercizio,
                 @PathParam("pg_mandato") Long pgMandato ) throws Exception;

    @POST
    @ApiOperation(value = "Crea mandato",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.MANDATO_REST +"'",
            response = MandatoDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response insert(@Context HttpServletRequest request,
                    CreaMandatoRequest mandatoRequest) throws Exception;

    @POST
    @Path("/stampa/{pgMandato}/{esercizio}/{cd_cds}")
    @ApiOperation(value = "Stampa mandato",
            notes = "Accesso consentito solo alle utenze abilitate e con ruolo '" + SIGLARoles.MANDATO_REST +"'",
            response = MandatoDto.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR)
            }
    )
    Response stampa(@PathParam("pgMandato") Long pgMandato,
                    @PathParam("esercizio") int esercizio,
                    @PathParam("cd_cds") String cdCds,
                    @Context HttpServletRequest request) throws Exception;

}
