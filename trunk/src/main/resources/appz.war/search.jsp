<%@ taglib uri="/WEB-INF/customtags/portal.tld" prefix="portal" %>

<portal:SetContent name="ActiveTab"><% out.print(org.metaz.gui.portal.PortalTabTag.TAB_SIMPLE_SEARCH); %></portal:SetContent>

<portal:SetContent name="Background">images/magnify.jpg</portal:SetContent>

<jsp:useBean id="searchBean" class="org.metaz.gui.portal.SearchBean" scope="session"/>

<portal:SetContent name="PageContent">
	<form action="searchhandler.jsp" method="post" enctype="multipart/form-data">
<%@ include file="basicsearchfields.jsp" %>
<%@ include file="freetextsearchfields.jsp" %>
<%@ include file="submitfields.jsp" %>
	</form>
</portal:SetContent>

<%@ include file="template.jsp" %>
