<%-- mark page as an error page --%>
<%@ page isErrorPage="true" contentType="text/html" %>
<%-- error page does not use templating engine to prevent (possible) nesting errors --%>
<jsp:useBean id="now" scope="request" class="java.util.Date" /><html>
<%@ include file="head.jsp" %>
<body>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td width="10%">&nbsp;</td>
    <td><table width="600px" border="0" align="center" cellspacing="0">
        <tr>
          <td><br/>
            <font color="red"> Er is helaas een (interne) applicatie fout opgetreden. <br/>
            Mail de webmaster als het onderstaande probleem herhaaldelijk wordt waargenomen: </font> <br/>
            <br/>
            <b>Datum/tijd</b> <br/>
            ${now} <br/>
            <br/>
            <b>Verzoek</b> <br/>
            ${pageContext.errorData.requestURI} <br/>
            <br/>
            <b>Status code</b> <br/>
            ${pageContext.errorData.statusCode} <br/>
            <br/>
            <b>Servlet</b> <br/>
            ${pageContext.errorData.servletName} <br/>
            <br/>
            <b>Fout code</b> <br/>
            ${pageContext.errorData.throwable} <br/>
            <br/>
            <br/>
            <b>U kunt proberen om met de <u>back</u> knop terug te surfen en de actie nogmaals uit te voeren.</b> </td>
        </tr>
      </table></td>
    <td width="10%">&nbsp;</td>
  </tr>
</table>
</body>
</html>
