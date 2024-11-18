package it.cnr.contab.web.rest.resource.doccont;

import it.cnr.contab.config00.sto.bulk.CdsBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.docamm00.docs.bulk.Documento_genericoBulk;
import it.cnr.contab.docamm00.docs.bulk.Documento_generico_passivoBulk;
import it.cnr.contab.docamm00.ejb.DocumentoGenericoComponentSession;
import it.cnr.contab.doccont00.core.bulk.*;
import it.cnr.contab.doccont00.ejb.DistintaCassiereComponentSession;
import it.cnr.contab.doccont00.ejb.MandatoComponentSession;
import it.cnr.contab.doccont00.intcass.bulk.StatoTrasmissione;
import it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk;
import it.cnr.contab.reports.bulk.Print_spoolerBulk;
import it.cnr.contab.reports.bulk.Report;
import it.cnr.contab.reports.service.PrintService;
import it.cnr.contab.service.SpringUtil;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
import it.cnr.contab.web.rest.exception.RestException;
import it.cnr.contab.web.rest.local.doccont.MandatoLocal;
import it.cnr.contab.web.rest.model.MandatoDto;
import it.cnr.contab.web.rest.request.CreaMandatoRequest;
import it.cnr.jada.UserContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.ejb.CRUDComponentSession;
import it.cnr.jada.util.DateUtils;
import it.cnr.jada.util.ejb.EJBCommonServices;
import it.cnr.si.spring.storage.StorageObject;
import it.cnr.si.spring.storage.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
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
                //Devo creare associazioni mandato riga e devo assicurarmi che sia sempro S
                mandatoBulk.setToBeCreated();
                MandatoIBulk mandatoBulkCreato=(MandatoIBulk) mandatoComponentSession.creaMandatoWs(userContext, mandatoBulk);
    /*
                MandatoIBulk mandatoBulkCreato = (MandatoIBulk) mandatoComponentSession.creaConBulk(userContext, mandatoBulk);
                 mandatoBulkCreato = (MandatoIBulk) mandatoComponentSession.findByPrimaryKey(userContext, new MandatoIBulk(cdCds, esercizio, mandatoBulkCreato.getPg_mandato()));
                if (!Optional.ofNullable(mandatoBulk).isPresent()){
                    throw new RestException(Response.Status.NOT_FOUND,"Mandato non presente.");
                }
                mandatoBulkCreato = (MandatoIBulk)mandatoComponentSession.inizializzaBulkPerModifica(userContext, mandatoBulkCreato);

                V_mandato_reversaleBulk vMandatoReversaleBulk = mandatoComponentSession.cercaVMandatoReversaleBulk(userContext, mandatoBulkCreato);
                if(mandatoRequest.isStampa()){
                    predisponiPerLaFirma(userContext, vMandatoReversaleBulk);
                }
                LOGGER.info("Mandato creato con successo. Procedo all'associazione delle righe.");
*/
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

    public void predisponi(UserContext userContext, V_mandato_reversaleBulk v_mandato_reversaleBulk, Format dateFormat) throws ComponentException, IOException {
        Print_spoolerBulk print = new Print_spoolerBulk();
        print.setPgStampa(UUID.randomUUID().getLeastSignificantBits());
        print.setFlEmail(false);
        print.setReport(v_mandato_reversaleBulk.getReportName());
        print.setNomeFile(v_mandato_reversaleBulk.getCMISName());
        print.setUtcr(userContext.getUser());
        print.addParam("aCd_cds", v_mandato_reversaleBulk.getCd_cds(), String.class);
        print.addParam("aCd_terzo", "%", String.class);
        print.addParam("aEs", v_mandato_reversaleBulk.getEsercizio().intValue(), Integer.class);
        print.addParam("aPg_a", v_mandato_reversaleBulk.getPg_documento_cont().longValue(), Long.class);
        print.addParam("aPg_da", v_mandato_reversaleBulk.getPg_documento_cont().longValue(), Long.class);
        print.addParam("aDt_da", DateUtils.firstDateOfTheYear(1970), Date.class, dateFormat);
        print.addParam("aDt_a", DateUtils.firstDateOfTheYear(3000), Date.class, dateFormat);

        Report report = SpringUtil.getBean("printService",
                PrintService.class).executeReport(userContext,
                print);
        final StorageObject storageObject = SpringUtil.getBean("storeService", StoreService.class).restoreSimpleDocument(
                v_mandato_reversaleBulk,
                report.getInputStream(),
                report.getContentType(),
                report.getName(),
                v_mandato_reversaleBulk.getStorePath(),
                true);
        aggiornaStato(userContext, MandatoBulk.STATO_TRASMISSIONE_PREDISPOSTO, v_mandato_reversaleBulk);
    }

    protected void aggiornaStato(UserContext userContext, String stato, StatoTrasmissione...bulks) throws ComponentException, RemoteException {
        DistintaCassiereComponentSession distintaCassiereComponentSession = Utility.createDistintaCassiereComponentSession();
        for (StatoTrasmissione v_mandato_reversaleBulk : bulks) {
            if (v_mandato_reversaleBulk.getCd_tipo_documento_cont().equalsIgnoreCase(Numerazione_doc_contBulk.TIPO_MAN)) {
                MandatoIBulk mandato = new MandatoIBulk(v_mandato_reversaleBulk.getCd_cds(), v_mandato_reversaleBulk.getEsercizio(), v_mandato_reversaleBulk.getPg_documento_cont());
                mandato = (MandatoIBulk) mandatoComponentSession.findByPrimaryKey(userContext, mandato);
                if(mandato.getStato().compareTo(MandatoBulk.STATO_MANDATO_ANNULLATO)==0){
                    if (!v_mandato_reversaleBulk.getStato_trasmissione().equals(mandato.getStato_trasmissione_annullo()))
                        throw new ApplicationException("Risorsa non più valida, eseguire nuovamente la ricerca!");
                    mandato.setStato_trasmissione_annullo(stato);
                    if (stato.equalsIgnoreCase(MandatoBulk.STATO_TRASMISSIONE_PRIMA_FIRMA))
                        mandato.setDt_firma_annullo(EJBCommonServices.getServerTimestamp());
                    else
                        mandato.setDt_firma_annullo(null);
                }else{
                    if (!v_mandato_reversaleBulk.getStato_trasmissione().equals(mandato.getStato_trasmissione()))
                        throw new ApplicationException("Risorsa non più valida, eseguire nuovamente la ricerca!");
                    mandato.setStato_trasmissione(stato);
                    if (stato.equalsIgnoreCase(MandatoBulk.STATO_TRASMISSIONE_PRIMA_FIRMA))
                        mandato.setDt_firma(EJBCommonServices.getServerTimestamp());
                    else
                        mandato.setDt_firma(null);
                }
                mandato.setToBeUpdated();
                mandatoComponentSession.modificaConBulk(userContext, mandato);
                /*for (StatoTrasmissione statoTrasmissione : distintaCassiereComponentSession.findReversaliCollegate(actioncontext.getUserContext(), (V_mandato_reversaleBulk) v_mandato_reversaleBulk)) {
                    aggiornaStatoReversale(actioncontext, statoTrasmissione, stato);
                }*/
            /*} else {
                aggiornaStatoReversale(actioncontext, v_mandato_reversaleBulk, stato);
            }*/
            }
        }
    }

    public void predisponiPerLaFirma(UserContext userContext, V_mandato_reversaleBulk v_mandato_reversaleBulk) throws BusinessProcessException, ComponentException, IOException {
        String message = "";
        boolean isBloccoFirma = false;
        Format dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            if (v_mandato_reversaleBulk.isMandato()) {
                if (Utility.createMandatoComponentSession().esisteAnnullodaRiemettereNonCollegato(
                        userContext,v_mandato_reversaleBulk.getEsercizio(),v_mandato_reversaleBulk.getCd_cds_origine())) {
                    message += "\nEsistono mandati di annullo con riemissione da completare.";
                    isBloccoFirma=true;
                }
                boolean isReversaleCollegataSiope = true;
                try {
                    Utility.createMandatoComponentSession().esistonoPiuModalitaPagamento(userContext,
                            new MandatoIBulk(v_mandato_reversaleBulk.getCd_cds(),v_mandato_reversaleBulk.getEsercizio(),v_mandato_reversaleBulk.getPg_documento_cont()));
                } catch (ApplicationException _ex) {
                    message += "\nSul mandato n."+ v_mandato_reversaleBulk.getPg_documento_cont() + " , le modalità di pagamento dei dettagli del mandato sono diverse, " +
                            "pertanto è stato escluso dalla selezione.";
                }

                if (!Utility.createMandatoComponentSession().isCollegamentoSiopeCompleto(
                        userContext,new MandatoIBulk(v_mandato_reversaleBulk.getCd_cds(),v_mandato_reversaleBulk.getEsercizio(),v_mandato_reversaleBulk.getPg_documento_cont()))) {
                    message += "\nIl mandato n."+ v_mandato_reversaleBulk.getPg_documento_cont()+ " non risulta associato completamente a codici Siope, pertanto è stato escluso dalla selezione.";
                }
                if (v_mandato_reversaleBulk.getStato().compareTo( MandatoBulk.STATO_MANDATO_ANNULLATO)!=0 &&!Utility.createMandatoComponentSession().isCollegamentoSospesoCompleto(
                        userContext,new MandatoIBulk(v_mandato_reversaleBulk.getCd_cds(),v_mandato_reversaleBulk.getEsercizio(),v_mandato_reversaleBulk.getPg_documento_cont()))) {
                    message += "\nIl mandato n."+ v_mandato_reversaleBulk.getPg_documento_cont()+ " non risulta associato completamente a sospeso, pertanto è stato escluso dalla selezione.";
                }
            }
            predisponi(userContext, v_mandato_reversaleBulk, dateFormat);
        } catch (Exception e) {
            LOGGER.error("Errore durante la predisposizione della firma : {}", e.getMessage());
            throw e;
        }
    }

}