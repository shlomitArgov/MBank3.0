<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ include file="clientMenu.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body id="top">

<div class="wrapper col2">
  <div id="topbar">
    <div id="topnav">
      <ul>
        <li><a href="Controller?command=my_account">Account</a></li>
        <li><a href="Controller?command=my_deposits"><span>Deposits</span></a></li>
        <li><a href="Controller?command=my_recent_activities">Recent Activities</a></li>
  		<li><a href="Controller?command=my_details"><span>My Details</span></a></li>
    	<li class="active"><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
      </ul>
    </div>
    <br class="clear" />
  </div>
</div>


<%-- display all system properties --%>
<div class="container">
    <div class="content">
  <table border="1" summary="System properties" cellpadding="0" cellspacing="0">
	<thead>
	  <tr>
		<th>Name</th>
		<th>Value</th>
	  </tr>
	</thead>
	<tbody>
	<c:forEach items="${system_properties}" var="property">
		<jsp:useBean id="property" class="mbank.database.beans.Property" scope="request"></jsp:useBean>
		<tr class="light">
		<td>${property.prop_key}</td>
		<td>${property.prop_value}</td>
		</tr>	
	</c:forEach>
	</tbody>
	</table>
   </div>
 </div>

</body>
</html>