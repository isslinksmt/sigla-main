package it.cnr.contab.web.rest.resource.doccont;

import it.cnr.contab.config00.sto.bulk.CdsBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.doccont00.core.bulk.MandatoBulk;
import it.cnr.contab.doccont00.core.bulk.MandatoHome;
import it.cnr.contab.doccont00.core.bulk.Mandato_rigaBulk;
import it.cnr.contab.doccont00.core.bulk.ObbligazioneBulk;
import it.cnr.contab.doccont00.ejb.MandatoComponentSession;
import it.cnr.contab.doccont00.ejb.ObbligazioneComponentSession;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.web.rest.exception.RestException;
import it.cnr.contab.web.rest.local.doccont.MandatoLocal;
import it.cnr.contab.web.rest.model.MandatoDto;
import it.cnr.contab.web.rest.request.CreaMandatoRequest;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.ejb.CRUDComponentSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class MandatoResource implements MandatoLocal {

    private final Logger LOGGER = LoggerFactory.getLogger(MandatoResource.class);

    @Context
    SecurityContext securityContext;

    @EJB
    CRUDComponentSession crudComponentSession;

    @EJB
    MandatoComponentSession mandatoComponentSession;

    @EJB
    ObbligazioneComponentSession obbligazioneComponentSession;

    @Override
    public Response get(String cdCds, String cdUnitaOrganizzativa, Integer esercizio, Long pgMandato) throws Exception {
        try{
            CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
            MandatoBulk mandatoBulk = (MandatoBulk) mandatoComponentSession.findByPrimaryKey(userContext, new MandatoBulk(cdCds, esercizio, pgMandato));
            if (!Optional.ofNullable(mandatoBulk).isPresent()){
                throw new RestException(Response.Status.NOT_FOUND,"Mandato non presente.");
            }
            mandatoBulk = (MandatoBulk)mandatoComponentSession.inizializzaBulkPerModifica(userContext, mandatoBulk);
            return Response.status(Response.Status.OK).entity(mandatoBulkToDto(mandatoBulk)).build();
        }catch (Throwable e){
            if ( e instanceof RestException)
                throw e;
            throw new RestException(Response.Status.INTERNAL_SERVER_ERROR,String.format(e.getMessage()));
        }
    }

    @Override
    public Response getsecond(String cd_cds, String cd_unita_organizzativa, Integer esercizio, Long pg_mandato) throws Exception {
        CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
        return null;
    }

    @Override
    public Response insert(String cdCds, String cdUnitaOrganizzativa, Integer esercizio, HttpServletRequest request, CreaMandatoRequest mandatoRequest) throws Exception {
        try{
            List<ObbligazioneBulk> obbligazioneBulks = new ArrayList<>();
            CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
            for (Long el : mandatoRequest.getPgObbligazioni()) {
                ObbligazioneBulk obbligazioneBulk = obbligazioneComponentSession.findObbligazione(userContext, new ObbligazioneBulk(cdCds, esercizio, esercizio, el));
                obbligazioneBulk = (ObbligazioneBulk) obbligazioneComponentSession.inizializzaBulkPerModifica(userContext, obbligazioneBulk);
                if ( !Optional.ofNullable(obbligazioneBulk).isPresent()){
                    throw new RestException(Response.Status.NOT_FOUND,String.format("Obbligazione %d non presente!", el));
                }
                obbligazioneBulks.add(obbligazioneBulk);
                MandatoBulk mandatoBulk = mandatoDtoToBulk(mandatoRequest, cdCds, cdUnitaOrganizzativa, esercizio, userContext);
                mandatoBulk.setToBeCreated();
                MandatoBulk mandatoBulkCreato = (MandatoBulk) mandatoComponentSession.creaConBulk(userContext, mandatoBulk);

                LOGGER.info("Mandato creato con successo. Procedo all'associazione delle righe.");
            }
        }catch (Throwable e){
            if ( e instanceof RestException)
                throw e;
            throw new RestException(Response.Status.INTERNAL_SERVER_ERROR,String.format(e.getMessage()));
        }
        return null;
    }

    private MandatoDto mandatoBulkToDto(MandatoBulk bulk){
        MandatoDto mandatoDto = new MandatoDto();
        mandatoDto.setCdCds(bulk.getCd_cds());
        mandatoDto.setEsercizio(bulk.getEsercizio());
        mandatoDto.setPgMandato(bulk.getPg_mandato());
        return mandatoDto;
    }

    private MandatoBulk mandatoDtoToBulk(CreaMandatoRequest request, String cdCds, String cdUnitaOrganizzativa, Integer esercizio, CNRUserContext userContext) throws ComponentException, RemoteException {
        MandatoBulk mandatoBulk = new MandatoBulk();
        //mandatoBulk.setCd_cds(cdCds);
        mandatoBulk.setCds((CdsBulk) crudComponentSession.findByPrimaryKey( userContext,new CdsBulk(cdUnitaOrganizzativa)));
        mandatoBulk.setUnita_organizzativa((Unita_organizzativaBulk) crudComponentSession.findByPrimaryKey(userContext,new Unita_organizzativaBulk(cdUnitaOrganizzativa)));
        mandatoBulk.setEsercizio(esercizio);
        mandatoBulk.setCd_cds_origine(cdCds);
        mandatoBulk = (MandatoBulk) mandatoComponentSession.inizializzaBulkPerInserimento(userContext, mandatoBulk);
        //mandatoBulk.setCd_unita_organizzativa(cdUnitaOrganizzativa);
        mandatoBulk.setCd_uo_origine(cdUnitaOrganizzativa);
        mandatoBulk.setCd_tipo_documento_cont("MAN");
        mandatoBulk.setTi_mandato("P");
        mandatoBulk.setTi_competenza_residuo("C");
        mandatoBulk.setDs_mandato(request.getDescrizioneMandato());
        mandatoBulk.setStato("E");
        mandatoBulk.setDt_emissione(Timestamp.valueOf(LocalDateTime.now()));
        mandatoBulk.setStato_trasmissione("I");
        mandatoBulk.setStato_coge("N");
        return mandatoBulk;
    }
}