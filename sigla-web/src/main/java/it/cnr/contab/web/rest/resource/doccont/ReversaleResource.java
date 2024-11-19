package it.cnr.contab.web.rest.resource.doccont;

import it.cnr.contab.config00.sto.bulk.CdsBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.docamm00.docs.bulk.Documento_genericoBulk;
import it.cnr.contab.docamm00.docs.bulk.Documento_generico_attivoBulk;
import it.cnr.contab.docamm00.ejb.DocumentoGenericoComponentSession;
import it.cnr.contab.doccont00.core.bulk.*;
import it.cnr.contab.doccont00.ejb.ReversaleComponentSession;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.web.rest.exception.RestException;
import it.cnr.contab.web.rest.local.doccont.ReversaleLocal;
import it.cnr.contab.web.rest.model.ReversaleDto;
import it.cnr.contab.web.rest.request.CreaReversaleRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class ReversaleResource implements ReversaleLocal {

    private final Logger LOGGER = LoggerFactory.getLogger(ReversaleResource.class);

    @Context
    SecurityContext securityContext;

    @EJB
    CRUDComponentSession crudComponentSession;

    @EJB
    ReversaleComponentSession reversaleComponentSession;

    @EJB
    DocumentoGenericoComponentSession documentoGenericoComponentSession;

    @Override
    public Response insert(HttpServletRequest request, CreaReversaleRequest reversaleRequest) throws Exception {
        try{
            List<V_doc_attivo_accertamentoBulk> listaVDocAttivi = new ArrayList<>();
            BigDecimal importo = new BigDecimal(0);
            CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
            for (Long el : reversaleRequest.getPgDocumentiAttivi()) {
                Documento_generico_attivoBulk documentoGenericoAttivoBulk = new Documento_generico_attivoBulk(
                        reversaleRequest.getCds(),
                        Documento_genericoBulk.GENERICO_E,
                        reversaleRequest.getUnitaOrganizzativa(),
                        reversaleRequest.getEsercizio(),
                        el);
                Documento_genericoBulk documento_genericoBulk = (Documento_genericoBulk) documentoGenericoComponentSession.findByPrimaryKey(userContext,
                        documentoGenericoAttivoBulk);
                if (!Optional.ofNullable(documento_genericoBulk).isPresent()){
                    throw new RestException(Response.Status.NOT_FOUND, String.format("Documento Generico non presente!"));
                }
                listaVDocAttivi.add(reversaleComponentSession.getVDocAttiviAccertamento(userContext, el, reversaleRequest.getCds(), reversaleRequest.getEsercizio()));
            }
            ReversaleIBulk reversaleIBulk = createReversaleBulk(reversaleRequest, userContext);
            for(V_doc_attivo_accertamentoBulk vdoc : listaVDocAttivi){
                importo = importo.add(vdoc.getIm_totale_doc_amm());
            }
            reversaleIBulk.setIm_reversale(importo);
            reversaleIBulk.setIm_incassato(importo);
            reversaleIBulk = (ReversaleIBulk) reversaleComponentSession.aggiungiDocAttivi(userContext, reversaleIBulk, listaVDocAttivi);
            reversaleIBulk.setToBeCreated();
            ReversaleIBulk reversaleCreata=(ReversaleIBulk) reversaleComponentSession.creaReversaleWs(userContext, reversaleIBulk);
            return Response.status(Response.Status.OK).entity(reversaleBulkToDto(reversaleCreata)).build();
        }catch (Throwable e){
            if ( e instanceof RestException)
                throw e;
            throw new RestException(Response.Status.INTERNAL_SERVER_ERROR,String.format(e.getMessage()));
        }
    }

    @Override
    public Response stampa(Long pgMandato, int esercizio, String cdCds, HttpServletRequest request) throws Exception {
        CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
        ReversaleIBulk reversaleStampato = (ReversaleIBulk) reversaleComponentSession.stampaReversale(userContext, pgMandato, esercizio, cdCds);
        return Response.status(Response.Status.OK).entity(reversaleBulkToDto(reversaleStampato)).build();
    }

    private ReversaleIBulk createReversaleBulk(CreaReversaleRequest reversaleRequest, CNRUserContext userContext) throws ComponentException, RemoteException {
        ReversaleIBulk reversaleIBulk = new ReversaleIBulk();
        reversaleIBulk.setCds((CdsBulk) crudComponentSession.findByPrimaryKey( userContext, new CdsBulk(reversaleRequest.getCds())) );
        reversaleIBulk.setUnita_organizzativa((Unita_organizzativaBulk) crudComponentSession.findByPrimaryKey(userContext,new Unita_organizzativaBulk(reversaleRequest.getUnitaOrganizzativa())));
        reversaleIBulk.setEsercizio(reversaleRequest.getEsercizio());
        reversaleIBulk.setCd_cds_origine(reversaleRequest.getCdsOrigine());
        reversaleIBulk = (ReversaleIBulk) reversaleComponentSession.inizializzaBulkPerInserimento(userContext, reversaleIBulk);
        reversaleIBulk.setCd_unita_organizzativa(reversaleRequest.getUnitaOrganizzativa());
        reversaleIBulk.setCd_uo_origine(reversaleRequest.getUnitaOrganizzativa());
        reversaleIBulk.setCd_tipo_documento_cont("REV");
        reversaleIBulk.setTi_reversale("I");
        reversaleIBulk.setTi_competenza_residuo("C");
        reversaleIBulk.setDs_reversale(reversaleRequest.getDescrizioneReversale());
        reversaleIBulk.setStato(ReversaleIBulk.STATO_REVERSALE_EMESSO);
        reversaleIBulk.setDt_emissione(Timestamp.valueOf(LocalDateTime.now().minusHours(1).minusMinutes(1)));
        reversaleIBulk.setStato_trasmissione(ReversaleIBulk.STATO_TRASMISSIONE_NON_INSERITO);
        reversaleIBulk.setStato_coge("N");
        reversaleIBulk.setCd_cds(reversaleRequest.getCds());
        return reversaleIBulk;
    }

    private ReversaleDto reversaleBulkToDto(ReversaleIBulk reversaleIBulk){
        ReversaleDto reversaleDto = new ReversaleDto();
        reversaleDto.setPgReversale(reversaleIBulk.getPg_reversale());
        reversaleDto.setCds(reversaleIBulk.getCd_cds());
        reversaleDto.setUnitaOrganizzativa(reversaleIBulk.getCd_unita_organizzativa());
        reversaleDto.setImportoReversale(reversaleIBulk.getIm_reversale());
        reversaleDto.setDescrizioneReversale(reversaleIBulk.getDs_reversale());
        return reversaleDto;
    }
}
