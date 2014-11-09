<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="clientMenu.jsp" %>

<jsp:useBean id="account" class="mbank.database.beans.Account" scope="request"></jsp:useBean>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" href="styles/layout.css" type="text/css" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MBank</title>
</head>
      <ul>
        <li class="active"><a href="Controller?command=account">Account</a></li>
        <li><a href="Controller?command=deposits"><span>Deposits</span></a></li>
        <li><a href="Controller?command=recent_activities">Recent Activities</a></li>
  		<li><a href="Controller?command=myDetails"><span>My Details</span></a></li>
    	<li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
      </ul>
  <table summary="Account Details" cellpadding="0" cellspacing="0">
        <thead>
          <tr>
            <th>Client ID</th>
            <th>Account ID</th>
            <th>Current Balance</th>
            <th>Credit Limit</th>
          </tr>
        </thead>
        
 		<tbody>
          <tr>
            <td>${account.client_id}</td>
            <td>${account.account_id}</td>
            <td>${account.balance}</td>
            <td>${account.credit_limit}</td>
          </tr>
        </tbody>
	 </table>
 <br/>
 <br/>
   
  <table>

 <form action="Controller?command=withdraw" method="post">
 	<tr>	
 		<td>(*)Withdrawal amount: </td>
 		<td><input type="text" id="withdraw_amount" name="withdraw_amount"></<input></td>
 		<td><span class="error">${withdraw_error}</span></td>
 	</tr>
 	<tr>
 		<td><input type="submit" value="Withdraw"></<input></td>
 	</tr>
 	<tr>
 		<td></td>	
 		<td><span class="info">${withdraw_info}</span></td>
 	</tr>
 </form>

 <form action="Controller?command=deposit" method="post">
	<tr>	
 			<td>(*)Deposit amount: </td> 				
 			<td><input type="text" id="deposit_amount" name="deposit_amount"></input></td>
 			<td><span class="error">${deposit_error}</span></td>
 			
 	</tr>
 	<tr>
 		<td><input type="submit" value="Deposit"></<input></td>
 		
 	</tr>
 	<tr>
		<td></td>
 		<td><span class="info">${deposit_info}</span></td>
 	</tr>
 	<tr>
 		<td class="tip">*Commission rate is ${commission}&#36;</td>
 	</tr>
    </form>
 </table>

</body>
</html>