<%@ page isErrorPage="true" contentType="text/html" %>

<%-- error page does not use templating engine to prevent possible "nesting errors" --%>

<jsp:useBean id="now" scope="request" class="java.util.Date" /> 

<html>
<head>
<title>Ruud de Moor Centrum - Applicatie Z</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="copyright" content="&#169; Copyright 2006 team Meta/Z, Open Universiteit Nederland" />
<meta name="author" content="Team Meta/Z, Open Universiteit Nederland" />
<meta name="ROBOTS" content="INDEX, FOLLOW" />
<meta name="resource-type" content="document" />
<meta name="distribution" content="Global" />
<meta name="robots" content="All" />
<meta name="rating" content="General" />
<meta name="revisit-after" content="1 days" />
<meta http-equiv="imagetoolbar" content="no" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<style type="text/css">
	@import "stylesheets/main.css";
</style>
</head>
<body>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td width="10%">&nbsp;</td>
    <td>
		  <table width="600px" border="0" align="center" cellspacing="0">
        <tr>
          <td>
			  <br/>

<font color="red">
Er is helaas een (interne) applicatie fout opgetreden.
<br/>
Mail de webmaster als het onderstaande probleem nogmaals wordt gerapporteerd:
</font>
<br/>
<br/>

<b>Datum/tijd</b>
<br/>
${now}
<br/>
<br/>

<b>Verzoek</b>
<br/>
${pageContext.errorData.requestURI}
<br/>
<br/>

<b>Status code</b>
<br/>
${pageContext.errorData.statusCode}
<br/>
<br/>

<b>Servlet</b>
<br/>
${pageContext.errorData.servletName}
<br/>
<br/>

<b>Fout code</b>
<br/>
${pageContext.errorData.throwable}
<br/>
<br/>
<br/>

<b>U kunt proberen om met de <u>back</u> knop terug te surfen en de actie nogmaals uit te voeren.</b>

		</td>
        </tr>
      </table></td>
    <td width="10%">&nbsp;</td>
  </tr>
</table>
</body>
</html>

