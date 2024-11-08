<%@ page pageEncoding="UTF-8"
	import="it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		java.util.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.docamm00.tabrif.bulk.*,
		it.cnr.jada.*,
		it.cnr.contab.docamm00.docs.bulk.*,
		it.cnr.contab.docamm00.bp.*,
		it.cnr.contab.anagraf00.tabrif.bulk.*"
%>
<%	CRUDAutofatturaBP bp = (CRUDAutofatturaBP)BusinessProcess.getBusinessProcess(request);
	AutofatturaBulk autofattura = (AutofatturaBulk)bp.getModel();
	boolean roForSplit = autofattura!=null && !autofattura.isToBeCreated() &&
    			             autofattura.getFl_split_payment()!=null && autofattura.getFl_split_payment();

	UserContext uc = HttpActionContext.getUserContext(session);

%>
   <div class="Group card">
	 <table>

	  <tr>
	  <td>
	 	<% bp.getController().writeFormLabel(out,"esercizio");%>
	   </td>
	   <td>
	   	<% bp.getController().writeFormInput(out,null,"esercizio",false,null,"");%>
	   </td>
	   <td>   
	 	<% bp.getController().writeFormLabel(out,"pg_autofattura");%>
	 	</td><td>
	 	<% bp.getController().writeFormInput(out,null,"pg_autofattura",false,null,"");%>
	   </td>
	 </tr>
	</table>
	  <div class="Group card">
      	<table>
    		<tr>
    			<% if (!bp.isSearching()) { %>
    		     	<td>
    		      		<% bp.getController().writeFormLabel(out,"ti_istituz_commerc");%>
    		      	</td>
    		     	<td colspan="10">
    		      		<% bp.getController().writeFormInput(out,null,"ti_istituz_commerc",roForSplit,null,"onChange=\"submitForm('doOnIstituzionaleCommercialeChange')\"");%>
    		      	</td>
    			<% } else { %>
    		     	<td>
    		      		<% bp.getController().writeFormLabel(out,"ti_istituz_commercSearch");%>
    		      	</td>
    		     	<td colspan="10">
    		      		<% bp.getController().writeFormInput(out,null,"ti_istituz_commercSearch",roForSplit,null,"onChange=\"submitForm('doOnIstituzionaleCommercialeChange')\"");%>
    		      	</td>
    			<% } %>
          </tr>
	 <% if (bp.isSearching()) { %>
         <tr>
                <td>
                            <% bp.getController().writeFormLabel(out, "sezionaliFlagsRadioGroup");%>
                        </td>
                        <td colspan="3">
                            <% bp.getController().writeFormInput(out, null, "sezionaliFlagsRadioGroup", false, null, "onClick=\"submitForm('doOnSezionaliFlagsChange')\"");%>
                        </td>
         </tr>
         <% }%>
	 <tr>
		<% if (!bp.isSearching()) { %>	 
	   		<td><% bp.getController().writeFormLabel(out,"stato_cofi");%></td>
	   		<td><% bp.getController().writeFormInput(out,null,"stato_cofi",false,null,"");%></td>
	   		<!--
		     <td><% bp.getController().writeFormLabel(out,"ti_associato_manrev");%></td>
		        <td><% bp.getController().writeFormInput(out, null,"ti_associato_manrev",false,null,"");%></td>
		    -->
		<%} else {%>
	   		<td><% bp.getController().writeFormLabel(out,"stato_cofiForSearch");%></td>
	   		<td><% bp.getController().writeFormInput(out,null,"stato_cofiForSearch",false,null,"");%></td>
	   		<!--
			    <td><% bp.getController().writeFormLabel(out,"ti_associato_manrevForSearch");%></td>
			    <td><% bp.getController().writeFormInput(out, null,"ti_associato_manrevForSearch",false,null,"");%></td>
			-->
		<%}%>
   	 </tr>
	 <tr>      	
  		<td>
      		<% bp.getController().writeFormLabel(out,"dt_registrazione");%>
 		</td><td>
 			<% bp.getController().writeFormInput(out,null,"dt_registrazione",false,null,"submitForm('doOnDataRegistrazioneChange')\"");%>
      	</td> 
		<td>
      		<% bp.getController().writeFormLabel(out,"data_esigibilita_iva");%>
	 	</td><td>
 			<% bp.getController().writeFormInput(out,null,"data_esigibilita_iva",false,null,"");%>
      	</td> 
     </tr>
     <tr>      	

		<td>
      		<% bp.getController().writeFormLabel(out,"protocollo_iva");%>
      	</td><td>
      		<% bp.getController().writeFormInput(out,null,"protocollo_iva",false,null,"");%>
      	</td> 
     </tr>
     <tr>      	

		<td>
      		<% bp.getController().writeFormLabel(out,"protocollo_iva_generale");%>
      	</td><td>
      		<% bp.getController().writeFormInput(out,null,"protocollo_iva_generale",false,null,"");%>
      	</td> 
     </tr>
     </table>
   </div>

