<%@ page pageEncoding="UTF-8"
	import="it.cnr.jada.action.*,
	    it.cnr.jada.UserContext,
		it.cnr.jada.bulk.*,
		it.cnr.jada.util.action.*,
		it.cnr.jada.util.jsp.*,
		it.cnr.contab.progettiric00.bp.*,
		it.cnr.contab.progettiric00.core.bulk.*"
%>

<%
	TestataProgettiRicercaBP bp = (TestataProgettiRicercaBP)BusinessProcess.getBusinessProcess(request);
	ProgettoBulk bulk = (ProgettoBulk)bp.getModel();
	SimpleDetailCRUDController controller = ( (TestataProgettiRicercaBP)bp ).getCrudDettagli();
	 boolean isUoEnte = bp.isUoEnte(HttpActionContext.getUserContext(session));
%>

<%	controller.writeHTMLTable(pageContext,(isUoEnte?"uo999":"uo"),true,false,true,"100%","100px"); %>

	<table class="Panel">
		<tr>
			<% controller.writeFormField(out,"default","unita_organizzativa"); %>
		</tr>
		<%if ( isUoEnte){%>
            <tr>
                  <% controller.writeFormField(out,"default","fl_visibile"); %>
            </tr>
           <%}%>
		<!-- Per ora l'importo non è gestito 
		<tr>
			<% controller.writeFormField(out,"default","importo"); %>
		</tr> -->
	</table>