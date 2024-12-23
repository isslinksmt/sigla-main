<%@ page pageEncoding="UTF-8"
	import="it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		java.util.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.docamm00.tabrif.bulk.*,
		it.cnr.contab.docamm00.docs.bulk.*,
		it.cnr.contab.docamm00.bp.*,
		it.cnr.contab.anagraf00.tabrif.bulk.*"
%>

<% CRUDFatturaPassivaBP bp = (CRUDFatturaPassivaBP)BusinessProcess.getBusinessProcess(request);
	Fattura_passivaBulk fatturaPassiva = (Fattura_passivaBulk)bp.getModel();
	it.cnr.contab.anagraf00.core.bulk.TerzoBulk fornitore = fatturaPassiva.getFornitore();
	boolean roOnAutoGen = false;
	if (bp instanceof IDocumentoAmministrativoBP)
		roOnAutoGen = ((IDocumentoAmministrativoBP)bp).isAutoGenerated();
%>

	<div class="Group card">
		<table>
			<tr>
				<td>
					<% bp.getController().writeFormLabel(out,"fornitore");%>
				</td>
				<td colspan="3">
					<% bp.getController().writeFormInput(out, null, "fornitore", roOnAutoGen, null, "");%>
				</td>
			</tr>
			<tr>
				<td>
					<% bp.getController().writeFormLabel(out,"cd_precedente");%>
				</td>
				<td colspan="3">
					<% bp.getController().writeFormInput(out, null, "cd_precedente", roOnAutoGen, null, "");%>
				</td>
			</tr>
			<%	if (fornitore != null && fornitore.getAnagrafico() != null) {
					if ((fornitore.getAnagrafico().isStrutturaCNR() ||
						fornitore.getAnagrafico().isPersonaGiuridica() ||
						fornitore.getAnagrafico().isDittaIndividuale()) &&
						fatturaPassiva.getRagione_sociale() != null &&
						fatturaPassiva.getRagione_sociale().length() > 0) { %>
						<tr>
							<%	if (fornitore.getAnagrafico().isStrutturaCNR()) { %>
									<td>
										<b>Nome</b>
									</td>
							<%	} else { %>
									<td>
										<%bp.getController().writeFormLabel(out,"ragione_sociale");%>
									</td>
							<% } %>
							<td  colspan="3">
								<%bp.getController().writeFormInput(out,"ragione_sociale");%>
							</td>
						</tr>
				<%	}
					if (fornitore.getAnagrafico().isPersonaFisica()) { %>
						<tr>
							<td>
								<% bp.getController().writeFormLabel(out,"cognome");%>
							</td>
							<td>
								<%bp.getController().writeFormInput(out,"cognome");%>
							</td>
							<td>
								<% bp.getController().writeFormLabel(out,"nome");%>
							</td>
							<td>
								<%bp.getController().writeFormInput(out,"nome");%>
							</td>
						</tr>
				<%	} %>
					<tr>
						<td>
							<% bp.getController().writeFormLabel(out,"denominazione_sede"); %>
						</td>
						<td colspan="3">
							<% bp.getController().writeFormInput(out,"denominazione_sede"); %>
						</td>
					</tr>
				<%	if (!fornitore.getAnagrafico().isStrutturaCNR()) { %>
						<tr>
							<% 	if (fornitore.getAnagrafico().isPersonaGiuridica() || 
									fornitore.getAnagrafico().isDittaIndividuale()) { %>
										<td>
											<% bp.getController().writeFormLabel(out,"partita_iva"); %>
										</td>
										<td>
											<% bp.getController().writeFormInput(out,"partita_iva"); %>
										</td>
							<%	} %>
							<% bp.getController().writeFormField(out,"codice_fiscale"); %>
							<% if (bp.isSearching() || fornitore.getCrudStatus() != it.cnr.jada.bulk.OggettoBulk.NORMAL)
								bp.getController().writeFormField(out,"partita_iva");%>
						</tr>
			<%		} 
				} else { %>
					<tr>
						<td>
							<% bp.getController().writeFormLabel(out,"ragione_sociale");%>
						</td>
						<td colspan="3">
							<% bp.getController().writeFormInput(out,"ragione_sociale");%>
						</td>
					</tr>
					<tr>
						<% bp.getController().writeFormField(out,"nome");%>
						<% bp.getController().writeFormField(out,"cognome");%>
					</tr>
					<tr>
						<% bp.getController().writeFormField(out,"codice_fiscale");%>
						<% bp.getController().writeFormField(out,"partita_iva");%>
					</tr>
			<%	} %>
	      <tr>
			<% bp.getController().writeFormField(out,"via_fiscale");%>
			<% bp.getController().writeFormField(out,"num_civico");%>
	      </tr>
	      <tr>
			<% bp.getController().writeFormField(out,"ds_comune");%>
			<% bp.getController().writeFormField(out,"ds_provincia");%>
	      </tr> 
		</table>
	</div>

   <div class="Group card">   
	<table>	
	  <tr>
     	<td>
 	     	<% bp.getController().writeFormLabel(out,"termini_pagamento");%>
      	</td>      	
     	<td>
	      	<% bp.getController().writeFormInput(out,null,"termini_pagamento",roOnAutoGen,null,"");%>	
      	</td>   
      </tr>
      <tr>
     	<td>
 	     	<% bp.getController().writeFormLabel(out,"modalita_pagamento");%>
      	</td>      	
     	<td>
	      	<% bp.getController().writeFormInput(out,null,"modalita_pagamento",roOnAutoGen,null,"onChange=\"submitForm('doOnModalitaPagamentoChange')\"");%>	
      	</td>   
		<td>
			<% 	if (fatturaPassiva.getBanca() != null) {
					bp.getController().writeFormInput(out, null, "listabanche", roOnAutoGen, null, "");
				} %>
   		</td>
      </tr>
		<tr>
		  	<td colspan="2">
		<%	if (fatturaPassiva.getBanca() != null) {
				if (Rif_modalita_pagamentoBulk.BANCARIO.equalsIgnoreCase(fatturaPassiva.getBanca().getTi_pagamento())) {
			 	     	bp.getController().writeFormInput(out,"contoB");
				} else if (Rif_modalita_pagamentoBulk.POSTALE.equalsIgnoreCase(fatturaPassiva.getBanca().getTi_pagamento())) {
			 	     	bp.getController().writeFormInput(out,"contoP");
				} else if (Rif_modalita_pagamentoBulk.QUIETANZA.equalsIgnoreCase(fatturaPassiva.getBanca().getTi_pagamento())) {
			 	     	bp.getController().writeFormInput(out,"contoQ");
				} else if (Rif_modalita_pagamentoBulk.ALTRO.equalsIgnoreCase(fatturaPassiva.getBanca().getTi_pagamento())
				        && !fatturaPassiva.getModalita_pagamento().isPAGOPA()) {
			 	     	bp.getController().writeFormInput(out,"contoA");
				} else if (Rif_modalita_pagamentoBulk.IBAN.equalsIgnoreCase(fatturaPassiva.getBanca().getTi_pagamento())) { 
		 	     	bp.getController().writeFormInput(out,"contoN");
				} else if (Rif_modalita_pagamentoBulk.BANCA_ITALIA.equalsIgnoreCase(fatturaPassiva.getBanca().getTi_pagamento()) && fatturaPassiva.getBanca().isTABB()) {
                    bp.getDettaglio().writeFormInput(out,"contoB");
                }
			} else if (fatturaPassiva.getModalita_pagamento() != null && (fornitore != null && fornitore.getCrudStatus() != fornitore.UNDEFINED)) { %>
				<span class="FormLabel" style="color:red">
					Nessun riferimento trovato per la modalità di pagamento selezionata!
				</span>
		<%	} else if(fatturaPassiva.getModalita_pagamento() == null && fatturaPassiva.getModalita()!= null && fatturaPassiva.getModalita().size() == 0 && (fornitore != null && fornitore.getCrudStatus() != fornitore.UNDEFINED)){ %>
				<span class="FormLabel" style="color:red">
				Attenzione! Nessuna modalità di pagamento trovata!
			</span>
		 <% } %>
			<td>
		<tr>
    </table>
   </div>
	<% if (fatturaPassiva.getModalita_pagamento() != null && fatturaPassiva.getCessionario() != null) { %>
		<div class="Group card">   
			<table>	
				<tr>
					<td>
						<% bp.getController().writeFormLabel(out,"cd_cessionario");%>
					</td>   
					<td>
						<% bp.getController().writeFormInput(out,null,"cd_cessionario",roOnAutoGen,null,"");%>	
					</td>   
					<td>
						<% bp.getController().writeFormInput(out,null,"denom_sede_cessionario",roOnAutoGen,null,"");%>	
					</td>   
				</tr>
			</table>
		</div>
	<% } %>