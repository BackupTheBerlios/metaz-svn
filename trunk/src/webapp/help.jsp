<%@ include file="includes.jsp" %>

<portal:SetContent name="ActiveTab">
  <% out.print(org.metaz.gui.portal.PortalTabTag.TAB_HELP); %>
</portal:SetContent>
<portal:SetContent name="Background">images/binocs.jpg</portal:SetContent>
<portal:SetContent name="PageContent">Klikt u <a href="resources/Gebruikershandleiding.pdf">hier</a> om de gebruikershandleiding (PDF) van Applicatie Z te downloaden.</portal:SetContent>
<%@ include file="template.jsp" %>
