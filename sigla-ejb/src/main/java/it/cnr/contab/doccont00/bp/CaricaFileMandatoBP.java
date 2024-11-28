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
import it.cnr.contab.doccont00.intcass.giornaliera.*;
import it.cnr.contab.doccont00.intcass.giornaliera.FlussoGiornaleDiCassa.InformazioniContoEvidenza;
import it.cnr.contab.doccont00.intcass.giornaliera.FlussoGiornaleDiCassa.InformazioniContoEvidenza.MovimentoContoEvidenza;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Config;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.ejb.CRUDComponentSession;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.util.RemoteIterator;
import it.cnr.jada.util.action.BulkBP;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class CaricaFileMandatoBP extends BulkBP {
    private static final long serialVersionUID = 1L;

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
    }

    public void caricaFile(ActionContext actioncontext, File file) throws BusinessProcessException, ComponentException, RemoteException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String identificativoFlusso = "FON-MAN-".concat(file.getName());
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';')
                        .build())
                .build()) {
            List<String[]> records = csvReader.readAll();
            FlussoGiornaleDiCassaBulk flusso = initFlusso(records.get(1), actioncontext.getUserContext().getUser(), identificativoFlusso);
            InformazioniContoEvidenzaBulk info = initInformazioniContoEvidenza(flusso, records.get(1));
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                String codiceEnte = parseString(record[0]);
                Integer esercizio = parseInteger(record[1]);
                Integer numeroMandato = parseInteger(record[2]);
                Integer numeroBeneficiario = parseInteger(record[3]);
                Date dataCarico = parseDate(record[4], dateFormat);
                Date dataPagamento = parseDate(record[5], dateFormat);
                BigDecimal importoMandato = parseBigDecimal(record[6]);
                BigDecimal importoBeneficiario = parseBigDecimal(record[7]);
                BigDecimal importoBeneficiarioPagato = parseBigDecimal(record[8]);
                BigDecimal importoBeneficiarioDaPagare = parseBigDecimal(record[9]);
                BigDecimal importoRitenute = parseBigDecimal(record[10]);
                String copertura = parseString(record[11]);
                Integer conto = parseInteger(record[12]);
                String anagrafica = parseString(record[13]);
                Integer codiceCausale = parseInteger(record[14]);
                String causale = parseString(record[15]);
                String indirizzo = parseString(record[16]);
                String cap = parseString(record[17]);
                String localita = parseString(record[18]);
                String codiceRiscossione = parseString(record[19]);
                String iban = parseString(record[20]);
                Integer numeroRicevuta = parseInteger(record[21]);
                Integer numeroRicSto = parseInteger(record[22]);
                Date dataValutaEnte = parseDate(record[23], dateFormat);
                String codiceFiscalePartitaIva = parseString(record[24]);
                String stato = parseString(record[25]);
                String squadr = parseString(record[26]);
                String codiceIdentificativoEntePagopa = parseString(record[27]);
                String numeroAvvisoPagopa = parseString(record[28]);
                MovimentoContoEvidenzaBulk movBulk = new MovimentoContoEvidenzaBulk(flusso.getEsercizio(), flusso.getIdentificativoFlusso(), info.getContoEvidenza(), "I", (long) (i + 1));
                movBulk.setTipoMovimento("USCITA");
                movBulk.setTipoDocumento(MovimentoContoEvidenzaBulk.TIPO_DOCUMENTO_MANDATO);
                movBulk.setTipoOperazione(MovimentoContoEvidenzaBulk.TIPO_OPERAZIONE_ESEGUITO);
                if(numeroMandato != null){
                    movBulk.setNumeroDocumento(Long.valueOf(numeroMandato));
                }
                if(numeroRicevuta != null){
                    movBulk.setProgressivoDocumento(numeroRicevuta.longValue());
                }
                movBulk.setImporto(importoMandato);
                movBulk.setImportoRitenute(importoRitenute);
                movBulk.setCausale(causale);
                if(dataPagamento != null){
                    movBulk.setDataMovimento(new Timestamp(dataPagamento.getTime()));
                }
                movBulk.setAnagraficaCliente(anagrafica);
                movBulk.setCodiceFiscaleCliente(codiceFiscalePartitaIva);
                movBulk.setPartitaIvaCliente(codiceFiscalePartitaIva);
                movBulk.setCoordinate(iban);
                movBulk.setToBeCreated();
                info.addToMovConto(movBulk);
            }
            info.setToBeCreated();
            flusso.addToInfoConto(info);
            flusso.setToBeCreated();
            flusso = (FlussoGiornaleDiCassaBulk) ((CRUDComponentSession) createComponentSession("JADAEJB_CRUDComponentSession", CRUDComponentSession.class))
                    .creaConBulk(actioncontext.getUserContext(false), flusso);
        }catch (IOException | CsvException e) {
            e.printStackTrace();
            throw new BusinessProcessException("Errore durante il parsing del CSV", e);
        }

    }

    private FlussoGiornaleDiCassaBulk initFlusso(String[] firstRecord, String user, String identificativoFlusso){
        Integer esercizio = parseInteger(firstRecord[1]);
        FlussoGiornaleDiCassaBulk flusso = new FlussoGiornaleDiCassaBulk(esercizio, identificativoFlusso);
        flusso.setUser(user);
        flusso.setIdentificativoFlusso(identificativoFlusso);
        flusso.setDataOraCreazioneFlusso(new Timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
        flusso.setDataInizioPeriodoRif(new Timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
        flusso.setDataFinePeriodoRif(new Timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
        flusso.setEsercizio(esercizio);
        return flusso;
    }

    private InformazioniContoEvidenzaBulk initInformazioniContoEvidenza(FlussoGiornaleDiCassaBulk flussoGiornaleDiCassaBulk, String[] firstRecord){
        Integer conto = parseInteger(firstRecord[12]);
        InformazioniContoEvidenzaBulk infoBulk = new InformazioniContoEvidenzaBulk(flussoGiornaleDiCassaBulk.getEsercizio(), flussoGiornaleDiCassaBulk.getIdentificativoFlusso(), String.valueOf(conto));
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
}