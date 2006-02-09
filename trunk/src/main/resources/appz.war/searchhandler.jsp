<jsp:useBean id="searchBean" class="org.metaz.gui.portal.SearchBean" scope="request">
  <jsp:setProperty name="searchBean" property="*"/>
</jsp:useBean>

<% 
if (searchBean.process()) 
{
%>
<jsp:forward page="searchresults.jsp"/>
<%
}
else 
{
%>
<jsp:forward page="search.jsp"/>
<%
}
%>