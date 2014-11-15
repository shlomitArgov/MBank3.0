<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ include file="clientMenu.jsp" %>

<link rel="stylesheet" href="styles/style.css" type="text/css" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MBank</title>
</head>
<body>

<%-- display all system properties --%>
  <table summary="System properties" class="dataTable">
   <caption>System Properties</caption>
	<thead>
	  <tr>
		<th class="thclass">Name</th>
		<th class="thclass">Value</th>
	  </tr>
	</thead>
	<tbody>
	<c:forEach items="${system_properties}" var="property">
		<jsp:useBean id="property" class="mbank.database.beans.Property" scope="request"></jsp:useBean>
		<tr>
		<td class="tdclass">${property.prop_key}</td>
		<td class="tdclass">${property.prop_value}</td>
		</tr>	
	</c:forEach>
	</tbody>
	</table>
</body>
</html>