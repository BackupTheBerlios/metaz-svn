<%@ include file="includes.jsp" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<portal:SetContent name="ActiveTab">
  <% out.print(org.metaz.gui.portal.PortalTabTag.TAB_SEARCH_RESULTS); %>
</portal:SetContent>
<portal:SetContent name="Background">images/binocs.jpg</portal:SetContent>
<portal:SetContent name="PageContent">

	<center>
		<display:table name="${searchBean.recordDetailsList}" class="results" export="true" sort="list" pagesize="8">
			<display:column property="name" title="Eigenchap" group="1" sortable="true" headerClass="sortable" />
			<display:column property="value" title="Waarde" group="2" sortable="true" headerClass="sortable" />
		</display:table>
	</center>

</portal:SetContent>
<%@ include file="template.jsp" %>
