<%@ include file="includes.jsp" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.List" %>
<%@page import="org.metaz.repository.Result" %>
<%@page import="org.metaz.domain.BooleanMetaData" %>
<%@page import="org.metaz.domain.Record" %>

<fmt:setLocale value="nl_NL" />

<portal:SetContent name="ActiveTab">
  <% out.print(org.metaz.gui.portal.PortalTabTag.TAB_SEARCH_RESULTS); %>
</portal:SetContent>
<portal:SetContent name="Background">images/results.jpg</portal:SetContent>
<portal:SetContent name="PageContent">

	<center>
			<span class="pagebanner">Producten van het Ruud de Moor Centrum: objectomschrijving</span>
			<br/>
			<display:table name="${searchBean.metazResults}" id="row" class="results" export="true" sort="list" pagesize="8" >
				<display:column title="Nr" >
	      			<c:out value="${row_rowNum}"/>
	    		</display:column>
	    		<display:column property="score" title="Score" sortable="false" />
	    		<display:column property="object.productType.value" title="Type product" sortable="false" />
				<display:column title="Beveiligd" sortable="false">
				<% List results = searchBean.getMetazResults();
				   Result result = (Result)results.get(row_rowNum.intValue()-1);
				   Record r = (Record)result.getObject();
				   BooleanMetaData btmd = r.getSecured();
				   boolean b = ((Boolean) btmd.getValue()).booleanValue();
					if(b){
				%>
					<c:out value="Ja"/>
				<%
					} else {
				%>
					<c:out value="Nee"/>
				<%
					}
				%>
				</display:column>
				<display:column property="object.title.value" title="Titel" sortable="false" />
				<display:column title="Meer gegevens">
					<a href="recorddetails.jsp?record=${row_rowNum}">Meer...</a>
				</display:column>
			</display:table>
	</center>

</portal:SetContent>
<%@ include file="template.jsp" %>