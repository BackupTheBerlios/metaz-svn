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
            <font color="blue">U kunt proberen om met de <b>back</b> knop terug te surfen en de actie nogmaals uit te voeren.</font> <br/>
            <br/>
            <b>Datum/tijd</b> <br/>
            <small> ${now} <br/>
            </small> <br/>
            <b>Verzoek</b> <br/>
            <small> ${pageContext.errorData.requestURI} <br/>
            </small> <br/>
            <b>Status code</b> <br/>
            <small> ${pageContext.errorData.statusCode} <br/>
            </small> <br/>
            <b>Servlet</b> <br/>
            <small> ${pageContext.errorData.servletName} <br/>
            </small> <br/>
            <b>Fout code</b> <br/>
            <small> ${pageContext.errorData.throwable} <br/>
            </small> <br/>
            <b>"Stack trace"</b> <br/>
            <small>
<%
  ErrorData errorData = pageContext.getErrorData() ;
  Throwable throwable = errorData.getThrowable() ;
  StackTraceElement[] stackTrace = throwable.getStackTrace() ;
  for ( int i = 0 ; i < stackTrace.length ; ++i ) {
%>
            <%= stackTrace[i] %>
<%
  }
%>
            </small> <br/>
            <br/>
            <b>"Root cause"</b> <br/>
            <small>
<%
  errorData = pageContext.getErrorData() ;
  throwable = errorData.getThrowable().getCause() ;
	if (throwable != null)
	{
    stackTrace = throwable.getStackTrace() ;
    for ( int i = 0 ; i < stackTrace.length ; ++i ) {
%>
            <%= stackTrace[i] %>
<%
    }
	}
	else
	{
%>
            Geen root cause
<%
	}
%>
            </small> <br/>
            <br/>
          </td>
        </tr>
      </table></td>
    <td width="10%">&nbsp;</td>
  </tr>
</table>
</body>
</html>
