<%@ include file="includes.jsp" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.List" %>
<%@page import="org.metaz.repository.Result" %>
<%@page import="org.metaz.domain.BooleanMetaData" %>
<%@page import="org.metaz.domain.Record" %>

<c:set var="currentRecord" value="${searchBean.metazResults[param.record]}"/>
<fmt:setLocale value="nl_NL" />

<portal:SetContent name="ActiveTab">
  <% out.print(org.metaz.gui.portal.PortalTabTag.TAB_SEARCH_RESULTS); %>
</portal:SetContent>
<portal:SetContent name="Background">images/microscope.jpg</portal:SetContent>
<portal:SetContent name="PageContent">

	<center>
		<span class="pagebanner">Producten van het Ruud de Moor Centrum: objectomschrijving</span>
		<table class="results">
			<thead>
				<tr>
					<th colspan="2">&nbsp;</th>
				</tr>
			</thead>
			<tr class="odd">
				<td align="right">
				<%  
					int i = Integer.parseInt(request.getParameter("record"));
					List results = searchBean.getMetazResults();
				   	Result result = (Result)results.get(i-1);
				   	Record r = (Record)result.getObject();
					BooleanMetaData btmd = r.getSecured();
				   boolean b = ((Boolean) btmd.getValue()).booleanValue();
					if(b){
				%>
					<img src="images/key.jpg" height="50" alt="Sleutel" 
								title="Om dit product te kunnen gebruiken moet u inloggen."/>
				<%
					}
				%>
				</td>
				<td valign="middle">
					<c:url value="${currentRecord.object.uri.value}" var="url"/>
					<a href="${url}" target="_blank">
						<c:out value="${currentRecord.object.title.value}"/>
					</a>
				</td>
			</tr>
			<tr class="even">
				<td>
					Onderwerp:
				</td>
				<td>
					<c:out value="${currentRecord.object.subject.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Omschrijving:
				</td>
				<td>
					<c:out value="${currentRecord.object.title.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Sleutelwoorden:
				</td>
				<td>
					<c:out value="${currentRecord.object.keywords.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Didactische functie:
				</td>
				<td>
					<c:out value="${currentRecord.object.didacticFunction.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Didactisch scenario:
				</td>
				<td>
					<c:out value="${currentRecord.object.didacticScenario.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Schooltype:
				</td>
				<td>
					<c:out value="${currentRecord.object.schoolType.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Vakleergebied:
				</td>
				<td>
					<c:out value="${currentRecord.object.schoolDiscipline.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Beroepssituatie:
				</td>
				<td>
					<c:out value="${currentRecord.object.professionalSituation.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Competentie:
				</td>
				<td>
					<c:out value="${currentRecord.object.competence.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Aggregatieniveau:
				</td>
				<td>
					<c:out value="${currentRecord.object.schoolDiscipline.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Beoogde eindgebruiker:
				</td>
				<td>
					<c:out value="${currentRecord.object.targetEndUser.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Rechten:
				</td>
				<td>
					<c:out value="${currentRecord.object.rights.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Producttype:
				</td>
				<td>
					<c:out value="${currentRecord.object.productType.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Benodigde tijd:
				</td>
				<td>
					<jsp:useBean id="requiredTime" class="java.util.Date" />
					<jsp:setProperty name="requiredTime" property="time" 
  						value="${currentRecord.object.requiredTime.value}" /> 
					<fmt:formatDate value="${requiredTime}" type="time" timeStyle="medium"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Afspeelduur:
				</td>
				<td>
					<jsp:useBean id="playTime" class="java.util.Date" />
					<jsp:setProperty name="playTime" property="time" 
  						value="${currentRecord.object.playingTime.value}" /> 
					<fmt:formatDate value="${playTime}" type="time" timeStyle="medium"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Bestandsformaat:
				</td>
				<td>
					<c:out value="${currentRecord.object.fileFormat.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Bestandsgrootte:
				</td>
				<td>
					<c:out value="${currentRecord.object.fileSize.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Technische vereisten:
				</td>
				<td>
					<c:out value="${currentRecord.object.technicalRequirements.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Creatie datum:
				</td>
				<td>
					<fmt:formatDate value="${currentRecord.object.creationDate.value}" type="date" pattern="dd MMMM yyyy" />
				</td>
			</tr>
			<tr class="odd">
				<td>
					Laatst gewijzgd:
				</td>
				<td>
					<fmt:formatDate value="${currentRecord.object.lastChangedDate.value}" type="date" pattern="dd MMMM yyyy" />
				</td>
			</tr>
			<tr class="even">
				<td>
					Versie:
				</td>
				<td>
					<c:out value="${currentRecord.object.version.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Status:
				</td>
				<td>
					<c:out value="${currentRecord.object.status.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Rol en naam:
				</td>
				<td>
					<c:out value="${currentRecord.object.roleName.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<a href="searchresults.jsp">Terug naar de resultatenlijst</a>
					&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
					<a href="search.jsp">Opnieuw zoeken</a>
				</td>
			</tr>
		</table>
	</center>

</portal:SetContent>
<%@ include file="template.jsp" %>
