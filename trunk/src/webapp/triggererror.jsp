<portal:SetContent name="Background">images/binocs.jpg</portal:SetContent>
<portal:SetContent name="PageContent">
  <% org.metaz.gui.portal.SearchBean bean = null;
   bean.processPost(request);
%>
</portal:SetContent>
<%@ include file="template.jsp" %>
