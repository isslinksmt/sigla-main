package it.cnr.contab.web.rest.resource.doccont;

import it.cnr.contab.config00.sto.bulk.CdsBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.docamm00.docs.bulk.Documento_genericoBulk;
import it.cnr.contab.docamm00.docs.bulk.Documento_generico_passivoBulk;
import it.cnr.contab.docamm00.ejb.DocumentoGenericoComponentSession;
import it.cnr.contab.doccont00.core.bulk.*;
import it.cnr.contab.doccont00.ejb.MandatoComponentSession;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
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
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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
    DocumentoGenericoComponentSession documentoGenericoComponentSession;

    @Override
    public Response get(String cdCds, String cdUnitaOrganizzativa, Integer esercizio, Long pgMandato) throws Exception {
        try{
            CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
            MandatoIBulk mandatoBulk = (MandatoIBulk) mandatoComponentSession.findByPrimaryKey(userContext, new MandatoIBulk(cdCds, esercizio, pgMandato));
            if (!Optional.ofNullable(mandatoBulk).isPresent()){
                throw new RestException(Response.Status.NOT_FOUND,"Mandato non presente.");
            }
            mandatoBulk = (MandatoIBulk)mandatoComponentSession.inizializzaBulkPerModifica(userContext, mandatoBulk);
            return Response.status(Response.Status.OK).entity(mandatoBulkToDto(mandatoBulk)).build();
        }catch (Throwable e){
            if ( e instanceof RestException)
                throw e;
            throw new RestException(Response.Status.INTERNAL_SERVER_ERROR,String.format(e.getMessage()));
        }
    }

    @Override
    public Response insert(String cdCds, String cdUnitaOrganizzativa, Integer esercizio, HttpServletRequest request, CreaMandatoRequest mandatoRequest) throws Exception {
        mandatoComponentSession = Utility.createMandatoComponentSession();
        try{
            List<V_doc_passivo_obbligazioneBulk> listaVDocPassivi = new ArrayList<>();
            BigDecimal importo = new BigDecimal(0);
            CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
            for (Long el : mandatoRequest.getPgDocumentiPassivi()) {
                Documento_generico_passivoBulk documentoGenericoPassivoBulk = new Documento_generico_passivoBulk(
                        cdCds,
                        Documento_genericoBulk.GENERICO_S,
                        cdUnitaOrganizzativa,
                        esercizio,
                        el);
                Documento_genericoBulk documento_genericoBulk = (Documento_genericoBulk) documentoGenericoComponentSession.findByPrimaryKey(userContext,
                        documentoGenericoPassivoBulk);
                if (!Optional.ofNullable(documento_genericoBulk).isPresent())
                    throw new RestException(Response.Status.NOT_FOUND, String.format("Documento Generico non presente!"));
                    listaVDocPassivi.add(mandatoComponentSession.getVDocPassiviObbligazione(userContext, el, cdCds, esercizio));
                }
                MandatoIBulk mandatoBulk = mandatoDtoToBulk(mandatoRequest, cdCds, cdUnitaOrganizzativa, esercizio, userContext);
                for(V_doc_passivo_obbligazioneBulk vdoc : listaVDocPassivi){
                    importo = importo.add(vdoc.getIm_totale_doc_amm());
                }
                mandatoBulk.setIm_mandato(importo);
                mandatoBulk.setIm_pagato(importo);
                mandatoBulk = (MandatoIBulk) mandatoComponentSession.aggiungiDocPassivi(userContext, mandatoBulk, listaVDocPassivi);
                mandatoBulk.setToBeCreated();
                MandatoIBulk mandatoBulkCreato=(MandatoIBulk) mandatoComponentSession.creaMandatoWs(userContext, mandatoBulk);
                return Response.status(Response.Status.OK).entity(mandatoBulkToDto(mandatoBulkCreato)).build();
        }catch (Throwable e){
            if ( e instanceof RestException)
                throw e;
            throw new RestException(Response.Status.INTERNAL_SERVER_ERROR,String.format(e.getMessage()));
        }
    }

    @Override
    public Response stampa(Long pgMandato, int esercizio, String cdCds, HttpServletRequest request) throws Exception {
        CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
        MandatoIBulk mandatoBulkCreato = (MandatoIBulk) mandatoComponentSession.stampaMandato(userContext, pgMandato, esercizio, cdCds);
        return Response.status(Response.Status.OK).entity(mandatoBulkToDto(mandatoBulkCreato)).build();
    }

    private MandatoDto mandatoBulkToDto(MandatoBulk bulk){
        MandatoDto mandatoDto = new MandatoDto();
        mandatoDto.setCdCds(bulk.getCd_cds());
        mandatoDto.setEsercizio(bulk.getEsercizio());
        mandatoDto.setPgMandato(bulk.getPg_mandato());
        return mandatoDto;
    }

    private MandatoIBulk mandatoDtoToBulk(CreaMandatoRequest request, String cdCds, String cdUnitaOrganizzativa, Integer esercizio, CNRUserContext userContext) throws ComponentException, RemoteException {
        MandatoIBulk mandatoBulk = new MandatoIBulk();
        mandatoBulk.setCds((CdsBulk) crudComponentSession.findByPrimaryKey( userContext,new CdsBulk(cdCds)));
        mandatoBulk.setUnita_organizzativa((Unita_organizzativaBulk) crudComponentSession.findByPrimaryKey(userContext,new Unita_organizzativaBulk(cdUnitaOrganizzativa)));
        mandatoBulk.setEsercizio(esercizio);
        mandatoBulk = (MandatoIBulk) mandatoComponentSession.inizializzaBulkPerInserimento(userContext, mandatoBulk);
        mandatoBulk.setCd_unita_organizzativa(cdUnitaOrganizzativa);
        mandatoBulk.setCd_uo_origine(cdUnitaOrganizzativa);
        mandatoBulk.setCd_tipo_documento_cont("MAN");
        mandatoBulk.setTi_mandato("P");
        mandatoBulk.setTi_competenza_residuo("C");
        mandatoBulk.setDs_mandato(request.getDescrizioneMandato());
        mandatoBulk.setStato("E");
        mandatoBulk.setDt_emissione(Timestamp.valueOf(LocalDateTime.now().minusHours(1).minusMinutes(1)));
        mandatoBulk.setStato_trasmissione(MandatoBulk.STATO_TRASMISSIONE_NON_INSERITO);
        mandatoBulk.setStato_coge("N");
        mandatoBulk.setCd_cds_origine(cdCds);
        mandatoBulk.setCd_cds(cdCds);
        return mandatoBulk;
    }
}