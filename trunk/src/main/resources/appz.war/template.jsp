<html>
<%@ include file="head.jsp" %>
<body 
  background="<portal:GetContent name="Background"/>"
>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td bgcolor="#FF0000"><center>
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
    <td><table width="600px" border="0" align="center" cellspacing="0">
        <tr>
          <td><br/>
            <%-- primary page content will be rendered here --%>
            <portal:GetContent name="PageContent"/>
          </td>
        </tr>
      </table></td>
    <td width="10%">&nbsp;</td>
  </tr>
</table>
</body></html>
