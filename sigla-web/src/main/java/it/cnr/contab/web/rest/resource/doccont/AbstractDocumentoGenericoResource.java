package it.cnr.contab.web.rest.resource.doccont;

import it.cnr.contab.config00.bulk.Configurazione_cnrBulk;
import it.cnr.contab.config00.sto.bulk.CdsKey;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaKey;
import it.cnr.contab.docamm00.docs.bulk.Documento_genericoBulk;
import it.cnr.contab.docamm00.docs.bulk.Documento_generico_rigaBulk;
import it.cnr.contab.docamm00.docs.bulk.Tipo_documento_ammKey;
import it.cnr.contab.docamm00.ejb.DocumentoGenericoComponentSession;
import it.cnr.contab.docamm00.tabrif.bulk.DivisaBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
import it.cnr.contab.util.enumeration.TipoIVA;
import it.cnr.contab.web.rest.exception.RestException;
import it.cnr.contab.web.rest.model.*;
import it.cnr.jada.UserContext;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.ejb.CRUDComponentSession;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Optional;
@Stateless
abstract public class AbstractDocumentoGenericoResource<T extends DocumentoGenericoDto>  {

    @Context
    protected SecurityContext securityContext;
    @EJB
    protected CRUDComponentSession crudComponentSession;
    @EJB
    protected DocumentoGenericoComponentSession documentoGenericoComponentSession;

    private void validaContestoDocumentoGenerico(CNRUserContext userContext,
                                            Integer esericizioObbligazione,
                                            String cdCdsObbligazione,
                                            String cdUoObbligazione){
        Optional.ofNullable(userContext.getEsercizio()).orElseThrow(() -> new RestException(Response.Status.BAD_REQUEST, "Errore, Esercizio del Contesto obbligatorio!"));
        Optional.ofNullable(userContext.getCd_cds()).orElseThrow(() -> new RestException(Response.Status.BAD_REQUEST, "Errore, Cds del Contesto obbligatorio !"));
        Optional.ofNullable(userContext.getCd_unita_organizzativa()).orElseThrow(() -> new RestException(Response.Status.BAD_REQUEST, "Errore, Uo del Contesto obbligatorio!"));

        Optional.ofNullable(esericizioObbligazione).orElseThrow(() -> new RestException(Response.Status.BAD_REQUEST, "Errore, Esercizio obbligatorio!"));
        Optional.ofNullable(cdCdsObbligazione).orElseThrow(() -> new RestException(Response.Status.BAD_REQUEST, "Errore, Cds  obbligatorio!"));
        Optional.ofNullable(cdUoObbligazione).orElseThrow(() -> new RestException(Response.Status.BAD_REQUEST, "Errore, UO  obbligatoria!"));

        Optional.ofNullable(esericizioObbligazione).filter(x -> userContext.getEsercizio().equals(x)).
                orElseThrow(() -> new RestException(Response.Status.BAD_REQUEST, "Esercizio del contesto diverso da quello del documento Genercio!"));
        Optional.ofNullable(cdCdsObbligazione).filter(x -> userContext.getCd_cds().equals(x)).
                orElseThrow(() -> new RestException(Response.Status.BAD_REQUEST, "CdS del contesto diverso da quello del documento Generico!"));
        Optional.ofNullable(cdUoObbligazione).filter(x -> userContext.getCd_unita_organizzativa().equals(x)).
                orElseThrow(() -> new RestException(Response.Status.BAD_REQUEST, "Unità Organizzativa del contesto diversa da quello del documento Generico!"));
    }




    public Response insertDocumentoGenerico(HttpServletRequest request, T documentoGenericoDto) throws Exception {
        try{
            CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
            validaContestoDocumentoGenerico(userContext,documentoGenericoDto.getEsercizio(),
                    documentoGenericoDto.getCdsKey().getCd_unita_organizzativa(),
                    documentoGenericoDto.getUnitaOrganizzativaKey().getCd_unita_organizzativa());
            //valida
            Documento_genericoBulk documentoGenericoBulk=documentoGenericoDtoToDocumentoGenBulk( userContext,documentoGenericoDto );
            return Response.status(Response.Status.OK).entity(documentoGenBulkToDocumentoGenDto(
                    documentoGenericoComponentSession.creaDocumentoGenericoWs(userContext,documentoGenericoBulk),userContext)).build();
        }catch (Throwable e){
            if ( e instanceof RestException)
                throw e;
            throw new RestException(Response.Status.INTERNAL_SERVER_ERROR,String.format(e.getMessage()));
        }

    }

    abstract String getCdTipoDocumentoAmm();

    @SuppressWarnings("unchecked")
    public Class reflectClassType() {
        return ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }



    abstract protected void addInfoContToDocRigheDto(Documento_generico_rigaBulk rigaBulk,  DocumentoGenericoRigaDto  rigaDto,UserContext userContext);


    protected void addRigheToDocumentoGenDto(Documento_genericoBulk bulk, DocumentoGenericoDto documentoGenericoDto,UserContext userContext) throws InstantiationException, IllegalAccessException {
        if (Optional.ofNullable(bulk.getDocumento_generico_dettColl()).isPresent()){
            for (Documento_generico_rigaBulk riga:bulk.getDocumento_generico_dettColl()){
                DocumentoGenericoRigaDto rigaDto = (DocumentoGenericoRigaDto) documentoGenericoDto.reflectClassType().newInstance();

                rigaDto.setProgressivo_riga(riga.getProgressivo_riga());
                rigaDto.setDs_riga(riga.getDs_riga());
                rigaDto.setIm_riga(riga.getIm_riga());
                rigaDto.setDt_a_competenza_coge(riga.getDt_a_competenza_coge());
                rigaDto.setDt_da_competenza_coge(riga.getDt_da_competenza_coge());
                rigaDto.setDt_cancellazione(riga.getDt_cancellazione());
                rigaDto.setAssMandRev( EnumAssMandRevDocGenRiga.getValueFrom(bulk.getTi_associato_manrev()));

                addInfoContToDocRigheDto( riga,rigaDto,userContext);
                documentoGenericoDto.getRighe().add(rigaDto);
            }
        }
    }

    abstract protected T completeDocumentoGenDto( Documento_genericoBulk bulk, T  documentoGenericoDto,UserContext userContext);
    protected T documentoGenBulkToDocumentoGenDto(Documento_genericoBulk bulk, UserContext userContext) throws InstantiationException, IllegalAccessException {
        if ( !Optional.ofNullable(bulk).isPresent())
            return null;
        DocumentoGenericoDto documentoGenericoDto = ( DocumentoGenericoDto) reflectClassType().newInstance();
        documentoGenericoDto.setCdsKey( new CdsKey(bulk.getCd_cds()));
        documentoGenericoDto.setUnitaOrganizzativaKey(new Unita_organizzativaKey(bulk.getCd_unita_organizzativa()));
        documentoGenericoDto.setTipoDocumentoAmmKey(new Tipo_documento_ammKey(bulk.getCd_tipo_documento_amm()));
        documentoGenericoDto.setEsercizio(bulk.getEsercizio());
        documentoGenericoDto.setTipo(TipoIVA.getValueFrom(bulk.getTi_istituz_commerc()));
        documentoGenericoDto.setDs_documento_generico(bulk.getDs_documento_generico());
        documentoGenericoDto.setPg_documento_generico(bulk.getPg_documento_generico());
        documentoGenericoDto.setData_registrazione(bulk.getData_registrazione());
        documentoGenericoDto.setDt_scadenza(bulk.getDt_scadenza());
        documentoGenericoDto.setDt_a_competenza_coge(bulk.getDt_a_competenza_coge());
        documentoGenericoDto.setDt_da_competenza_coge(bulk.getDt_da_competenza_coge());
        documentoGenericoDto.setStato(EnumStatoDocumentoGenerico.getValueFrom(bulk.getStato_cofi()));
        documentoGenericoDto.setAssMandRev(EnumAssMandRevDocGen.getValueFrom(bulk.getTi_associato_manrev()));

        addRigheToDocumentoGenDto( bulk,documentoGenericoDto,userContext);


        return  completeDocumentoGenDto( bulk,( T) documentoGenericoDto,userContext);
    }



    abstract protected Documento_genericoBulk initializeDocumentoGenerico( UserContext userContext, T documentoGenericoDto) throws ComponentException, RemoteException;

    abstract protected void addDocumentoGenericoRighe (UserContext userContext,Documento_genericoBulk documentoGenericoBulk,T documentoGenericoDto) throws Exception;
    protected Documento_genericoBulk documentoGenericoDtoToDocumentoGenBulk(UserContext userContext,T documentoGenericoDto) throws Exception {
        Documento_genericoBulk documentoGenericoBulk = initializeDocumentoGenerico(userContext,documentoGenericoDto);
        documentoGenericoBulk.setEsercizio(documentoGenericoDto.getEsercizio());
        documentoGenericoBulk.setCd_tipo_documento_amm( getCdTipoDocumentoAmm());
        documentoGenericoBulk.setCd_cds( documentoGenericoDto.getCdsKey().getCd_unita_organizzativa());
        documentoGenericoBulk.setCd_cds_origine(documentoGenericoBulk.getCd_cds());
        documentoGenericoBulk.setCd_unita_organizzativa(documentoGenericoDto.getUnitaOrganizzativaKey().getCd_unita_organizzativa());

        documentoGenericoBulk.setDs_documento_generico( documentoGenericoDto.getDs_documento_generico());
        documentoGenericoBulk.setDs_documento_generico( documentoGenericoDto.getDs_documento_generico());
        documentoGenericoBulk.setTi_istituz_commerc(documentoGenericoDto.getTipo().value());
       documentoGenericoBulk.setTi_associato_manrev(EnumAssMandRevDocGen.NO_ASSOCIATO_A_MAND_REV.getAssMandRev());
        documentoGenericoBulk.setStato_coge(Documento_genericoBulk.NON_REGISTRATO_IN_COGE);
        documentoGenericoBulk.setStato_coan(Documento_genericoBulk.NON_CONTABILIZZATO_IN_COAN);
        documentoGenericoBulk.setStato_cofi(Documento_genericoBulk.STATO_CONTABILIZZATO);

        documentoGenericoBulk.setData_registrazione(documentoGenericoDto.getData_registrazione());
        documentoGenericoBulk.setDt_scadenza(documentoGenericoDto.getDt_scadenza());
        documentoGenericoBulk.setDt_da_competenza_coge(documentoGenericoDto.getDt_da_competenza_coge());
        documentoGenericoBulk.setDt_a_competenza_coge(documentoGenericoDto.getDt_a_competenza_coge());
        documentoGenericoBulk.setStato_liquidazione(EnumStatoLiqDocumentoGen.LIQUIDABILE.getStato_liquidazione());

        DivisaBulk divisa = new DivisaBulk( Utility.createConfigurazioneCnrComponentSession().getVal01(userContext, new Integer(0), "*", Configurazione_cnrBulk.PK_CD_DIVISA,Configurazione_cnrBulk.SK_EURO ));
        documentoGenericoBulk.setValuta( divisa );
        documentoGenericoBulk.setCambio( new BigDecimal(1));
        documentoGenericoBulk.setToBeCreated();
        addDocumentoGenericoRighe(userContext,documentoGenericoBulk,documentoGenericoDto);
        return documentoGenericoBulk;
    }


    public Response getDocumentoGenerico( Documento_genericoBulk documentoGenericoBulk) throws Exception {
        try{
            CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
            Documento_genericoBulk documento_genericoBulk= ( Documento_genericoBulk) documentoGenericoComponentSession.findByPrimaryKey(userContext,
                    documentoGenericoBulk);
            documento_genericoBulk= ( Documento_genericoBulk)documentoGenericoComponentSession.inizializzaBulkPerModifica(userContext,documento_genericoBulk);
            if ( !Optional.ofNullable(documento_genericoBulk).isPresent())
                throw new RestException(Response.Status.NOT_FOUND,String.format("Documento Generico non presente!"));
            //obbligazioneBulk= ( ObbligazioneBulk)obbligazioneComponentSession.inizializzaBulkPerModifica(userContext,obbligazioneBulk);
            //return Response.status(Response.Status.OK).entity(obbligazioneBulkToObbligazioneDto( obbligazioneBulk )).build();
            return Response.status(Response.Status.OK).entity(documentoGenBulkToDocumentoGenDto( documento_genericoBulk,userContext)).build();
        }catch (Throwable e){
            if ( e instanceof RestException)
                throw e;
            throw new RestException(Response.Status.INTERNAL_SERVER_ERROR,String.format(e.getMessage()));
        }
    }


    public Response deleteDocumentoGenerico(String cd_cds, String cd_unita_organizzativa, Integer esercizio,  Long pg_documento_generico) throws Exception {
        try{

            CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
            Boolean result=documentoGenericoComponentSession.deleteDocumentoGenericoWs
                    (userContext,cd_cds,getCdTipoDocumentoAmm(),cd_unita_organizzativa,esercizio,pg_documento_generico);
            if ( result)
                return Response.status(Response.Status.OK).build();

            return Response.status(Response.Status.NO_CONTENT).build();

        }catch (Throwable e){
            throw new RestException(Response.Status.INTERNAL_SERVER_ERROR,String.format(e.getMessage()));
        }
    }


    public Response updateDocumentoGenerico(String cd_cds, String cd_unita_organizzativa, Integer esercizio,  Long pg_documento_generico) throws Exception {
        return null;
    }
}
