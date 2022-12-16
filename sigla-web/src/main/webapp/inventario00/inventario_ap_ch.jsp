<%@ page pageEncoding="UTF-8"
	import="it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		java.util.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.inventario00.tabrif.bulk.*,
		it.cnr.contab.inventario00.bp.*"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<% JSPUtils.printBaseUrl(pageContext); %>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="javascript" src="scripts/css.js"></script>
<title>Apertura/Chiusura Inventario</title>
</head>
<body class="Form"> 

<% CRUDInventarioApChBP bp = (CRUDInventarioApChBP)BusinessProcess.getBusinessProcess(request);
  bp.openFormWindow(pageContext); %>

  <div class="card p-2">
  <table class="Group" style="width:100%">
	<tr>
	  <td><% bp.getController().writeFormLabel(out,"pg_inventario");%></td>
	  <td colspan="3">
	    <% bp.getController().writeFormInput(out,"pg_inventario");%>
        <% bp.getController().writeFormInput(out,"ds_inventario");%>
      </td>
	</tr>
	<tr>
	  <td>
	  	<% bp.getController().writeFormLabel(out,"dt_apertura");%>
	  </td>
	  <td>
	  	<% bp.getController().writeFormInput(out,null,"dt_apertura",bp.isEditing(),null,null);%>
	  </td>
	  <td colspan="2">
	  	<% bp.getController().writeFormLabel(out,"dataChiusura");%>
	  	<% bp.getController().writeFormInput(out,null,"dataChiusura",bp.isEditing(),null,null);%>
	  </td>
	</tr>
	<tr>
	  <td><% bp.getController().writeFormLabel(out,"nr_bene_iniziale");%></td>
	  <td colspan="3"><% bp.getController().writeFormInput(out,null,"nr_bene_iniziale",(bp.isInventarioRO() || bp.isEditing()),null,null);%></td>
	</tr>
  </table>
  </div>

  <div class="card p-2">
  <table class="Group" style="width:100%">

	<tr>
    	<td colspan=4>
		  <span class="FormLabel" style="color:blue">Consegnatario Attuale</span>
		</td>
    </tr>  

	<tr>
	  <td><% bp.getController().writeFormLabel(out,"find_consegnatario");%></td>
	  <td colspan="2">
	    <% bp.getController().writeFormInput(out,"find_consegnatario");%>
	  </td>
	</tr>

	<tr>
	  <td><% bp.getController().writeFormLabel(out,"find_delegato");%></td>
	  <td colspan="2">
	    <% bp.getController().writeFormInput(out,"find_delegato");%>
	  </td>
	</tr>

	<tr>
	  <% bp.getController().writeFormField(out,"dt_inizio_validita");%>
	  <td>
	    <% bp.getController().writeFormLabel(out,"dt_fine_validita");%>
        <% bp.getController().writeFormInput(out,"dt_fine_validita");%>
      </td>
	</tr>
  </table>
  </div>
<% bp.closeFormWindow(pageContext); %>
</body>
</html>