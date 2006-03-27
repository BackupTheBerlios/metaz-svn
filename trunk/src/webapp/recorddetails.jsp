<%@ include file="includes.jsp" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
				<td>
					Beveiligd:
				</td>
				<td>
					<c:out value="${currentRecord.object.secured.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Omschrijving:
				</td>
				<td>
					<c:out value="${currentRecord.object.title.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Sleutelwoorden:
				</td>
				<td>
					<c:out value="${currentRecord.object.keywords.value}"/>
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
					Benodigde tijd:
				</td>
				<td>
					<c:out value="${currentRecord.object.requiredTime.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Aggregatieniveau:
				</td>
				<td>
					<c:out value="${currentRecord.object.aggregationLevel.value}"/>
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
					Type product:
				</td>
				<td>
					<c:out value="${currentRecord.object.productType.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Bestandsformaat:
				</td>
				<td>
					<c:out value="${currentRecord.object.fileFormat.value}"/>
				</td>
			</tr>
			<tr class="odd">
				<td>
					Bestandsgrootte:
				</td>
				<td>
					<c:out value="${currentRecord.object.fileSize.value}"/>
				</td>
			</tr>
			<tr class="even">
				<td>
					Afspeelduur:
				</td>
				<td>
					<c:out value="${currentRecord.object.playingTime.value}"/>
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
					Laatst gewijzgd:
				</td>
				<td>
					<fmt:formatDate value="${currentRecord.object.lastChangedDate.value}" type="date" pattern="dd MMMM yyyy" />
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
