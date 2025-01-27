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

package it.cnr.contab.doccont00.bp;


import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import it.cnr.contab.doccont00.dto.MandatiReversaliParsedDto;
import it.cnr.contab.doccont00.ejb.DistintaCassiereComponentSession;
import it.cnr.contab.doccont00.intcass.bulk.V_ext_cassiere00Bulk;
import it.cnr.contab.doccont00.intcass.giornaliera.*;
import it.cnr.contab.utenze00.bp.WSUserContext;
import it.cnr.jada.DetailedRuntimeException;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Config;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.ejb.CRUDComponentSession;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.util.RemoteIterator;
import it.cnr.jada.util.action.BulkBP;
import it.cnr.jada.util.ejb.EJBCommonServices;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class CaricaFileMandatoBP extends BulkBP {
    private static final long serialVersionUID = 1L;
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private DistintaCassiereComponentSession distintaCassiereComponentSession;

    public CaricaFileMandatoBP() {
        super();
    }

    public CaricaFileMandatoBP(String s) {
        super(s);
    }

    @Override
    public RemoteIterator find(ActionContext actioncontext,
                               CompoundFindClause compoundfindclause, OggettoBulk oggettobulk,
                               OggettoBulk oggettobulk1, String s) throws BusinessProcessException {
        return null;
    }

    @Override
    public void openForm(PageContext pagecontext, String action, String target,
                         String encType) throws IOException, ServletException {
        super.openForm(pagecontext, action, target, "multipart/form-data");
    }

    @Override
    protected void init(Config config, ActionContext actioncontext)
            throws BusinessProcessException {
        super.init(config, actioncontext);
        setModel(actioncontext, new FlussoGiornaleDiCassaBulk());
        this.distintaCassiereComponentSession = Optional.ofNullable(EJBCommonServices.createEJB("CNRDOCCONT00_EJB_DistintaCassiereComponentSession"))
                .filter(DistintaCassiereComponentSession.class::isInstance)
                .map(DistintaCassiereComponentSession.class::cast)
                .orElseThrow(() -> new DetailedRuntimeException("cannot find ejb CNRDOCCONT00_EJB_DistintaCassiereComponentSession"));
    }

    public void caricaFile(ActionContext actioncontext, File file) throws BusinessProcessException, ComponentException, RemoteException {
        String identificativoFlusso = "FON-MAN-".concat(file.getName());
        generaMovimenti(file, actioncontext, identificativoFlusso, "USCITA", MovimentoContoEvidenzaBulk.TIPO_DOCUMENTO_MANDATO);

    }

    public void caricaFileReversale(ActionContext actioncontext, File file) throws BusinessProcessException, ComponentException{
        String identificativoFlusso = "FON-REV-".concat(file.getName());
        generaMovimenti(file, actioncontext, identificativoFlusso, MovimentoContoEvidenzaBulk.TIPO_MOVIMENTO_ENTRATA, MovimentoContoEvidenzaBulk.TIPO_DOCUMENTO_REVERSALE);
    }

    private void generaMovimenti(File file, ActionContext actioncontext, String identificativoFlusso, String USCITA, String tipoDocumentoMandato) throws ComponentException, BusinessProcessException {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';')
                        .build())
                .build()) {
            List<String[]> records = csvReader.readAll();
            List<MandatiReversaliParsedDto> parsedRecords = parseRecords(records);
            FlussoGiornaleDiCassaBulk flusso = initFlusso(parsedRecords.get(0), actioncontext.getUserContext().getUser(), identificativoFlusso);
            InformazioniContoEvidenzaBulk info = initInformazioniContoEvidenza(flusso, parsedRecords.get(0));
            for (int i = 0; i < parsedRecords.size(); i++) {
                MandatiReversaliParsedDto dto = parsedRecords.get(i);
                MovimentoContoEvidenzaBulk movBulk = new MovimentoContoEvidenzaBulk(flusso.getEsercizio(), flusso.getIdentificativoFlusso(), info.getContoEvidenza(), "I", (long) (i + 1));
                movBulk.setTipoMovimento(USCITA);
                movBulk.setTipoDocumento(tipoDocumentoMandato);
                movBulk.setTipoOperazione(MovimentoContoEvidenzaBulk.TIPO_OPERAZIONE_ESEGUITO);
                if (dto.getNumeroDocumento() != null) {
                    movBulk.setNumeroDocumento(Long.valueOf(dto.getNumeroDocumento()));
                }
                if (dto.getNumeroRicevuta() != null) {
                    movBulk.setProgressivoDocumento(dto.getNumeroRicevuta().longValue());
                }
                movBulk.setImporto(dto.getImporto());
                movBulk.setImportoRitenute(dto.getImportoRitenute());
                movBulk.setCausale(dto.getCausale());
                if (dto.getDataPagamento() != null) {
                    movBulk.setDataMovimento(new Timestamp(dto.getDataValutaEnte().getTime()));
                }
                movBulk.setCodiceRifOperazione(dto.getCodiceRiscossione());
                movBulk.setAnagraficaCliente(dto.getAnagrafica());
                movBulk.setCodiceFiscaleCliente(dto.getCodiceFiscalePartitaIva());
                movBulk.setPartitaIvaCliente(dto.getCodiceFiscalePartitaIva());
                movBulk.setCoordinate(dto.getIban());
                movBulk.setToBeCreated();
                info.addToMovConto(movBulk);
            }
            info.setToBeCreated();
            flusso.addToInfoConto(info);
            flusso.setToBeCreated();
            flusso = (FlussoGiornaleDiCassaBulk) ((CRUDComponentSession) createComponentSession("JADAEJB_CRUDComponentSession", CRUDComponentSession.class))
                    .creaConBulk(actioncontext.getUserContext(false), flusso);
            processaFile(actioncontext, flusso);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            throw new BusinessProcessException("Errore durante l'elaborazione del file reversale.", e);
        }
    }

    private void processaFile(ActionContext actioncontext, FlussoGiornaleDiCassaBulk flusso) throws ComponentException, RemoteException {
        V_ext_cassiere00Bulk v_ext_cassiere00Bulk = new V_ext_cassiere00Bulk();
        v_ext_cassiere00Bulk.setNome_file(flusso.getIdentificativoFlusso());
        v_ext_cassiere00Bulk.setEsercizio(flusso.getEsercizio());
        distintaCassiereComponentSession.processaFile(
                new WSUserContext(actioncontext.getUserContext().getUser(), null,
                        flusso.getEsercizio(),
                        null, null, null),
                v_ext_cassiere00Bulk);
    }

    private FlussoGiornaleDiCassaBulk initFlusso(MandatiReversaliParsedDto firstRecord, String user, String identificativoFlusso){
        FlussoGiornaleDiCassaBulk flusso = new FlussoGiornaleDiCassaBulk(firstRecord.getEsercizio(), identificativoFlusso);
        flusso.setUser(user);
        flusso.setIdentificativoFlusso(identificativoFlusso);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        flusso.setDataOraCreazioneFlusso(currentTimestamp);
        flusso.setDataInizioPeriodoRif(currentTimestamp);
        flusso.setDataFinePeriodoRif(currentTimestamp);
        flusso.setEsercizio(firstRecord.getEsercizio());
        return flusso;
    }

    private InformazioniContoEvidenzaBulk initInformazioniContoEvidenza(FlussoGiornaleDiCassaBulk flussoGiornaleDiCassaBulk, MandatiReversaliParsedDto firstRecord){
        InformazioniContoEvidenzaBulk infoBulk = new InformazioniContoEvidenzaBulk(flussoGiornaleDiCassaBulk.getEsercizio(), flussoGiornaleDiCassaBulk.getIdentificativoFlusso(), String.valueOf(firstRecord.getConto()));
        return infoBulk;
    }

    private String parseString(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    private Integer parseInteger(String value) {
        try {
            return (value == null || value.trim().isEmpty()) ? null : Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return (value == null || value.trim().isEmpty()) ? null : new BigDecimal(value.trim()
                    .replace(".", "")
                    .replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Date parseDate(String value, SimpleDateFormat dateFormat) {
        try {
            return (value == null || value.trim().isEmpty()) ? null : dateFormat.parse(value.trim());
        } catch (ParseException e) {
            return null;
        }
    }

    private List<MandatiReversaliParsedDto> parseRecords(List<String[]> records) {
        records = records.subList(1, records.size());
        List<MandatiReversaliParsedDto> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        for (String[] record : records) {
            MandatiReversaliParsedDto dto = new MandatiReversaliParsedDto();
            dto.setCodiceEnte(parseString(record[0]));
            dto.setEsercizio(parseInteger(record[1]));
            dto.setNumeroDocumento(parseInteger(record[2]));
            dto.setNumeroBeneficiario(parseInteger(record[3]));
            dto.setDataCarico(parseDate(record[4], dateFormat));
            dto.setDataPagamento(parseDate(record[5], dateFormat));
            dto.setImporto(parseBigDecimal(record[6]));
            dto.setImportoBeneficiario(parseBigDecimal(record[7]));
            dto.setImportoBeneficiarioPagato(parseBigDecimal(record[8]));
            dto.setImportoBeneficiarioDaPagare(parseBigDecimal(record[9]));
            dto.setImportoRitenute(parseBigDecimal(record[10]));
            dto.setCopertura(parseString(record[11]));
            dto.setConto(parseInteger(record[12]));
            dto.setAnagrafica(parseString(record[13]));
            dto.setCodiceCausale(parseInteger(record[14]));
            dto.setCausale(parseString(record[15]));
            dto.setIndirizzo(parseString(record[16]));
            dto.setCap(parseString(record[17]));
            dto.setLocalita(parseString(record[18]));
            dto.setCodiceRiscossione(parseString(record[19]));
            dto.setIban(parseString(record[20]));
            dto.setNumeroRicevuta(parseInteger(record[21]));
            dto.setNumeroRicSto(parseInteger(record[22]));
            dto.setDataValutaEnte(parseDate(record[23], dateFormat));
            dto.setCodiceFiscalePartitaIva(parseString(record[24]));
            dto.setStato(parseString(record[25]));
            dto.setSquadr(parseString(record[26]));
            dto.setCodiceIdentificativoEntePagopa(parseString(record[27]));
            dto.setNumeroAvvisoPagopa(parseString(record[28]));
            result.add(dto);
        }
        return result;
    }
}