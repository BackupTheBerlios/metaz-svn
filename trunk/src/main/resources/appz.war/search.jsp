<%@ taglib uri="/WEB-INF/customtags/portal.tld" prefix="portal" %>

<portal:SetContent name="ActiveTab"><% out.print(org.metaz.gui.portal.PortalTabTag.TAB_SIMPLE_SEARCH); %></portal:SetContent>

<portal:SetContent name="Background">images/magnify.jpg</portal:SetContent>

<portal:SetContent name="PageContent">
	<form action="/code/forms/" method="post" enctype="multipart/form-data">
<%@ include file="basicsearchfields.jsp" %>
	</form>
</portal:SetContent>

<%@ include file="template.jsp" %>
