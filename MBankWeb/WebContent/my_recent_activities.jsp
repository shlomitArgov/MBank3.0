<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="clientMenu.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    

<link rel="stylesheet" href="styles/layout.css" type="text/css" /> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MBank</title>
</head>
<body>
      <ul>
        <li><a href="Controller?command=my_account">Account</a></li>
        <li><a href="Controller?command=my_deposits"><span>Deposits</span></a></li>
        <li  class="active"><a href="Controller?command=my_recent_activities">Recent Activities</a></li>
  		<li><a href="Controller?command=my_details"><span>My Details</span></a></li>
    	<li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
      </ul>
    <br/>

  <table summary="Client Activities">
	<thead>
	  <tr>
		<th>Activity ID</th>
		<th>Amount</th>
		<th>Activity Type</th>
		<th>Commission</th>
		<th>Description</th>
		<th>Date</th>
	  </tr>
	</thead>
	<tbody>
		<c:forEach items="${client_activities}" var="activity">
			<jsp:useBean id="activity" class="mbank.database.beans.Account" scope="request"></jsp:useBean>
			<tr class="light">
			<td>${activity.id}</td>
			<td>${activity.amount}</td>
			<td>${activity.activityType}</td>
			<td>${activity.commission}</td>
			<td>${activity.description}</td>
			<td>${activity.activity_date}</td>
			</tr>	
		</c:forEach>
	</tbody>
  </table>
</body>
</html>