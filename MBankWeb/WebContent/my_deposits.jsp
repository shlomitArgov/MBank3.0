<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
    
<%@ include file="clientMenu.jsp" %>

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
        <li class="active"><a href="Controller?command=my_deposits"><span>Deposits</span></a></li>
        <li><a href="Controller?command=my_recent_activities">Recent Activities</a></li>
  		<li><a href="Controller?command=my_details"><span>My Details</span></a></li>
    	<li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
      </ul>
    <br class="clear" />

<%-- display all client deposits --%>
  <table summary="Deposits">
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
		<tr>
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
	<br/>
	
	<h2>Create a new deposit</h2>
	<table>
		<form action="Controller?command=createDeposit" method="post">
		 	<tr>	
		 		<td>Deposit amount: </td>
		 		<td><input type="text" id="initial_deposit_amount" name="initial_deposit_amount"></<input></td>
		 		<td><span class="error">${deposit_amount_error}</span></td>
		 	</tr>
		 	<tr>	
		 		<td>Deposit end-date (DD-MM-YYYY): </td>
		 		<td><input type="text" id="deposit_end_date" name="deposit_end_date"></<input></td>
		 		<td><span class="error">${deposit_end_date_error}</span></td>
		 	</tr>
		 	<tr>
		 		<td><input type="submit" value="Create deposit"></<input></td>
		 	</tr>
		 	<tr align="center"><span class="info"><td>${create_deposit_error}</td></span></tr>
		 	<tr align="center"><span class="info"><td>${create_deposit_info}</td></span></tr>
		 </form>
	 </table>
</body>
</html>