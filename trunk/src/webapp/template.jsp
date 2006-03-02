<html>
<%@ include file="head.jsp" %>
<body 
  background="<portal:GetContent name="Background"/>"
>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td bgcolor="#FFFFFF"><center>
        <br/>
        <ul id="tablist">
          <portal:PortalTab/>
        </ul>
    </center></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td width="10%">&nbsp;</td>
    <td width="80%"><center>
        <table border="0" cellspacing="0">
          <tr>
            <td><br/>
              <%-- primary page content will be rendered here --%>
              <portal:GetContent name="PageContent"/>
            </td>
          </tr>
        </table>
      </center></td>
    <td width="10%">&nbsp;</td>
  </tr>
</table>
</body></html>
