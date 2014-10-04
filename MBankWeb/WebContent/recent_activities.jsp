<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="clientMenu.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%--jsp:useBean id="client_activities" class="mbank.database.beans.Account" scope="session"></jsp:useBean> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div class="wrapper col2">
  <div id="topbar">
    <div id="topnav">
      <ul>
        <li><a href="Controller?command=account">Account</a></li>
        <li><a href="Controller?command=deposits"><span>Deposits</span></a></li>
        <li  class="active"><a href="Controller?command=recent_activities">Recent Activities</a></li>
  		<li><a href="Controller?command=myDetails"><span>My Details</span></a></li>
    	<li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
      </ul>
    </div>
    <br class="clear" />
  </div>
</div>


  <div class="container">
    <div class="content">
  <table border="1" summary="Client Activities" cellpadding="0" cellspacing="0">
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
		<tr>
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
   </div>
 </div>


</body>
</html>