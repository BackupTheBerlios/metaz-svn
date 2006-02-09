<%@ taglib uri="/WEB-INF/customtags/portal.tld" prefix="portal" %>

<portal:SetContent name="ActiveTab"><% out.print(org.metaz.gui.portal.PortalTabTag.TAB_ADVANCED_SEARCH); %></portal:SetContent>

<portal:SetContent name="Background">images/binocs.jpg</portal:SetContent>

<portal:SetContent name="PageContent">
	<form action="advancedsearchhandler.jsp" method="post" enctype="multipart/form-data">
<%@ include file="basicsearchfields.jsp" %>
<%@ include file="advancedsearchfields.jsp" %>
<%@ include file="freetextsearchfields.jsp" %>
<%@ include file="submitfields.jsp" %>
	</form>
</portal:SetContent>

<%@ include file="template.jsp" %>
