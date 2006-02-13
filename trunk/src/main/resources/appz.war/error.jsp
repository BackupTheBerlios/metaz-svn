<%@ page isErrorPage="true" contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %

<%@ include file="includes.jsp" %>

<portal:SetContent name="Background">images/binocs.jpg</portal:SetContent>

<portal:SetContent name="PageContent">

<c:if test="${pageContext.errorData} != null">
    
Er is helaas een (interne) applicatie fout opgetreden.
Mail de webmaster als het onderstaande probleem nogmaals wordt gerapporteerd:

  <b>Datum/tijd</b>
  ${now}

  <b>Verzoek</b>
  ${pageContext.errorData.requestURI}

  <b>Status code</b>
  ${pageContext.errorData.statusCode}

  <b>Fout code</b>
  ${pageContext.errorData.throwable}

</c:if>

</portal:SetContent>

<%@ include file="template.jsp" %>
