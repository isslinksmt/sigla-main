/*
 * Copyright (C) 2021  Consiglio Nazionale delle Ricerche
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

package it.cnr.contab.doccont00.bp;

import it.cnr.contab.doccont00.core.bulk.MandatoBulk;
import it.cnr.contab.doccont00.core.bulk.MandatoIBulk;
import it.cnr.contab.doccont00.core.bulk.Numerazione_doc_contBulk;
import it.cnr.contab.doccont00.core.bulk.ReversaleIBulk;
import it.cnr.contab.doccont00.ejb.DistintaCassiereComponentSession;
import it.cnr.contab.doccont00.intcass.bulk.StatoTrasmissione;
import it.cnr.contab.service.SpringUtil;
import it.cnr.contab.spring.service.StorePath;
import it.cnr.contab.util.Utility;
import it.cnr.contab.util00.bulk.storage.AllegatoGenericoBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.ejb.CRUDComponentSession;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.util.RemoteIterator;
import it.cnr.jada.util.action.FormBP;
import it.cnr.jada.util.action.SimpleCRUDBP;
import it.cnr.jada.util.ejb.EJBCommonServices;
import it.cnr.jada.util.upload.UploadedFile;
import it.cnr.si.spring.storage.*;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class AllegaMandatoFirmatoBP extends SimpleCRUDBP {
    private final List<StatoTrasmissione> documents;
    protected StoreService storeService;
    private String directory;

    public AllegaMandatoFirmatoBP(List<StatoTrasmissione> documents) {
        this.documents = documents;
    }

    public AllegaMandatoFirmatoBP(String s, List<StatoTrasmissione> documents) {
        super(s);
        this.documents = documents;
    }

    @Override
    protected void initialize(ActionContext actioncontext)
            throws BusinessProcessException {
        storeService = SpringUtil.getBean("storeService", StoreService.class);
        directory = SpringUtil.getBean("storePath", StorePath.class).getBaseDirectory();
        super.initialize(actioncontext);
    }

    @Override
    public RemoteIterator find(ActionContext actionContext, CompoundFindClause compoundFindClause, OggettoBulk oggettoBulk, OggettoBulk oggettoBulk1, String s) throws BusinessProcessException {
        return null;
    }

    public String getLabel() {
        return "Salva documento firmato per: " + documentsLabel(documents);
    }

    private String documentsLabel(List<StatoTrasmissione> list) {
        return list
                .stream()
                .map(statoTrasmissione ->
                        statoTrasmissione.getEsercizio() + "/" +
                                statoTrasmissione.getPg_documento_cont()
                )
                .collect(Collectors.joining(" "));
    }

    @Override
    public void save(ActionContext actioncontext) throws ValidationException, BusinessProcessException {
        AllegatoGenericoBulk allegato = (AllegatoGenericoBulk) getModel();

        List<UploadedFile> uploadedFiles = ((it.cnr.jada.action.HttpActionContext) actioncontext)
                .getMultipartParameters("main.file");
        for (UploadedFile uploadedFile : uploadedFiles) {
            allegato.setContentType(
                    Optional.ofNullable(uploadedFile)
                            .flatMap(uploadedFile1 -> Optional.ofNullable(uploadedFile1.getContentType()))
                            .orElseThrow(() -> handleException(new ApplicationException("Non è stato possibile determinare il tipo di file!")))
            );
            allegato.setNome(
                    Optional.ofNullable(uploadedFile)
                            .flatMap(uploadedFile1 -> Optional.ofNullable(uploadedFile1.getName()))
                            .orElseThrow(() -> handleException(new ApplicationException("Non è stato possibile determinare il nome del file!")))

            );
            allegato.setFile(
                    Optional.ofNullable(uploadedFile)
                            .flatMap(uploadedFile1 -> Optional.ofNullable(uploadedFile1.getFile()))
                            .orElseThrow(() -> handleException(new ApplicationException("File non presente!")))
            );
            try {
                for (StatoTrasmissione statoTrasmissione : documents) {
                    if(!Objects.equals(allegato.getNome(), statoTrasmissione.getCMISName())){
                        allegato.setNome(statoTrasmissione.getCMISName());
                    }
                    Path destinationDir = Paths.get(directory.concat("/").concat(statoTrasmissione.getStorePath()));
                    String fileName = StringUtils.cleanPath(Objects.requireNonNull(allegato.getNome()));
                    Path targetLocation = destinationDir.resolve(fileName);
                    if (Files.exists(targetLocation)) {
                        Path backupFile = destinationDir.resolve(fileName.replace(".pdf", "_backup.pdf"));
                        Files.move(targetLocation, backupFile, StandardCopyOption.REPLACE_EXISTING);
                    }
                    Files.copy(new FileInputStream(allegato.getFile()), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                    aggiornaStato(actioncontext, MandatoBulk.STATO_TRASMISSIONE_PRIMA_FIRMA, statoTrasmissione);
                    /*final Optional<StorageObject> parentFolder =
                            Optional.ofNullable(storeService.getStorageObjectByPath(statoTrasmissione.getStorePath()));
                    if (parentFolder.isPresent()) {
                        storeService.storeSimpleDocument(
                                allegato,
                                new FileInputStream(allegato.getFile()),
                                allegato.getContentType(),
                                allegato.getNome(),
                                statoTrasmissione.getStorePath()
                        );
                    }*/
                }
            } catch (FileNotFoundException e) {
                throw handleException(e);
            } catch (StorageException e) {
                if (e.getType().equals(StorageException.Type.CONSTRAINT_VIOLATED))
                    throw handleException(new ApplicationException("File [" + allegato.getNome() + "] gia' presente. Inserimento non possibile!"));
                throw handleException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ComponentException e) {
                throw new RuntimeException(e);
            }
        }
        setMessage(FormBP.INFO_MESSAGE, "Documento firmato salvato correttamente.");
    }

    @Override
    public boolean isSearchButtonHidden() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isDeleteButtonHidden() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isNewButtonHidden() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isFreeSearchButtonHidden() {
        return Boolean.TRUE;
    }

    @Override
    public void openForm(PageContext context, String action, String target) throws IOException, ServletException {
        openForm(context, action, target, "multipart/form-data");
    }

    protected void aggiornaStato(ActionContext actioncontext, String stato, StatoTrasmissione...bulks) throws ComponentException, RemoteException, BusinessProcessException {
        CRUDComponentSession crudComponentSession = createComponentSession();
        for (StatoTrasmissione v_mandato_reversaleBulk : bulks) {
            if (v_mandato_reversaleBulk.getCd_tipo_documento_cont().equalsIgnoreCase(Numerazione_doc_contBulk.TIPO_MAN)) {
                MandatoIBulk mandato = new MandatoIBulk(v_mandato_reversaleBulk.getCd_cds(), v_mandato_reversaleBulk.getEsercizio(), v_mandato_reversaleBulk.getPg_documento_cont());
                mandato = (MandatoIBulk) crudComponentSession.findByPrimaryKey(actioncontext.getUserContext(), mandato);
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
                crudComponentSession.modificaConBulk(actioncontext.getUserContext(), mandato);
                /*for (StatoTrasmissione statoTrasmissione : distintaCassiereComponentSession.findReversaliCollegate(actioncontext.getUserContext(), (V_mandato_reversaleBulk) v_mandato_reversaleBulk)) {
                    aggiornaStatoReversale(actioncontext, statoTrasmissione, stato);
                }*/
            } else {
                aggiornaStatoReversale(actioncontext, v_mandato_reversaleBulk, stato);
            }
        }
    }

    private void aggiornaStatoReversale(ActionContext actioncontext, StatoTrasmissione v_mandato_reversaleBulk, String stato) throws ComponentException, RemoteException, BusinessProcessException {
        CRUDComponentSession crudComponentSession = createComponentSession();
        ReversaleIBulk reversale = new ReversaleIBulk(v_mandato_reversaleBulk.getCd_cds(), v_mandato_reversaleBulk.getEsercizio(), v_mandato_reversaleBulk.getPg_documento_cont());
        reversale = (ReversaleIBulk) crudComponentSession.findByPrimaryKey(actioncontext.getUserContext(), reversale);
        if(reversale.getStato().compareTo(MandatoBulk.STATO_MANDATO_ANNULLATO)==0){
            if (!v_mandato_reversaleBulk.getStato_trasmissione().equals(reversale.getStato_trasmissione_annullo()))
                throw new ApplicationException("Risorsa non più valida, eseguire nuovamente la ricerca!");
            reversale.setStato_trasmissione_annullo(stato);
            if (stato.equalsIgnoreCase(MandatoBulk.STATO_TRASMISSIONE_PRIMA_FIRMA))
                reversale.setDt_firma_annullo(EJBCommonServices.getServerTimestamp());
            else
                reversale.setDt_firma_annullo(null);
        }else{
            if (!v_mandato_reversaleBulk.getStato_trasmissione().equals(reversale.getStato_trasmissione()))
                throw new ApplicationException("Risorsa non più valida, eseguire nuovamente la ricerca!");
            reversale.setStato_trasmissione(stato);
            if (stato.equalsIgnoreCase(MandatoBulk.STATO_TRASMISSIONE_PRIMA_FIRMA))
                reversale.setDt_firma(EJBCommonServices.getServerTimestamp());
            else
                reversale.setDt_firma(null);
        }
        reversale.setToBeUpdated();
        crudComponentSession.modificaConBulk(actioncontext.getUserContext(), reversale);
    }
}
