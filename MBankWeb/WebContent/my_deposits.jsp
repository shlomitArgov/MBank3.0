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
<body>

<div class="wrapper col2">
  <div id="topbar">
    <div id="topnav">
      <ul>
        <li><a href="Controller?command=account">Account</a></li>
        <li class="active"><a href="Controller?command=deposits"><span>Deposits</span></a></li>
        <li><a href="Controller?command=recent_activities">Recent Activities</a></li>
  		<li><a href="Controller?command=myDetails"><span>My Details</span></a></li>
    	<li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
      </ul>
    </div>
    <br class="clear" />
  </div>
</div>


<%-- display all client deposits --%>
<div class="container">
    <div class="content">
  <table border="1" summary="Deposits" cellpadding="0" cellspacing="0">
	<thead>
	  <tr>
		<th>Deposit ID</th>
		<th>Balance</th>
		<th>Type</th>
		<th>Estimated Balance</th>
		<th>Opening Date</th>
		<th>Closing Date</th>
	  </tr>
	</thead>
	<tbody>
	<c:forEach items="${client_deposits}" var="deposit">
		<jsp:useBean id="deposit" class="mbank.database.beans.Deposit" scope="request"></jsp:useBean>
		<tr class="light">
		<td>${deposit.deposit_id}</td>
		<td>${deposit.balance}</td>
		<td>${deposit.type.typeStringValue}</td>
		<td>${deposit.estimated_balance}</td>
		<td>${deposit.opening_date}</td>
		<td>${deposit.closing_date}</td>
		</tr>	
	</c:forEach>
	</tbody>
	</table>
   </div>
 </div>

</body>
</html>