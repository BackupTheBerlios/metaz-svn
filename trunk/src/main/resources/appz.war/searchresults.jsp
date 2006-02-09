<%@ taglib uri="/WEB-INF/customtags/portal.tld" prefix="portal" %>

<portal:SetContent name="ActiveTab"><% out.print(org.metaz.gui.portal.PortalTabTag.TAB_SEARCH_RESULTS); %></portal:SetContent>

<portal:SetContent name="Background">images/binocs.jpg</portal:SetContent>

<jsp:useBean id="searchBean" class="org.metaz.gui.portal.SearchBean" scope="request"/>

<portal:SetContent name="PageContent">
</portal:SetContent>

<%@ include file="template.jsp" %>
