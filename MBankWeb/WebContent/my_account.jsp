<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="clientMenu.jsp" %>

<jsp:useBean id="account" class="mbank.database.beans.Account" scope="request"></jsp:useBean>

<!-- <link rel="stylesheet" href="styles/layout.css" type="text/css" /> -->
<link rel="stylesheet" href="styles/style.css" type="text/css" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MBank</title>
</head>
  <table summary="Account Details" class="dataTable">
  <caption>Account Details</caption>
        <thead>
          <tr>
            <th class="thclass">Client ID</th>
            <th class="thclass">Account ID</th>
            <th class="thclass">Current Balance</th>
            <th class="thclass">Credit Limit</th>
          </tr>
        </thead>
        
 		<tbody>
          <tr>
            <td class="tdclass">${account.client_id}</td>
            <td class="tdclass">${account.account_id}</td>
            <td class="tdclass">${account.balance}</td>
            <td class="tdclass">${account.credit_limit}</td>
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
	 		<td><span class="info">${withdraw_info}</span></td>
	 	</tr>
	 	<tr>
	 		<td><input type="submit" value="Withdraw"></<input></td>
	 	</tr>
 	</form>
  </table>
  
  <br/>
  
  <table>
	 <form action="Controller?command=deposit" method="post">
		<tr>	
	 			<td>(*)Deposit amount: </td> 				
	 			<td><input type="text" id="deposit_amount" name="deposit_amount"></input></td>
	 			<td><span class="error">${deposit_error}</span></td>
	 			<td><span class="info">${deposit_info}</span></td>	
	 	</tr>
	 	<tr>
	 		<td><input type="submit" value="Deposit"></<input></td>
	 	</tr>
	  </form>
 </table>
 
 <br/>
*Commission rate is ${commission}&#36;

</body>
</html>