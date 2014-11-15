<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="clientMenu.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="account" class="mbank.database.beans.Account" scope="request"></jsp:useBean>

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
		            <td class="tdclass"><fmt:formatNumber type="number" maxFractionDigits="2" value="${account.balance}"/></td>
		            <td class="tdclass">${account.credit_limit}</td>
		          </tr>
		        </tbody>
			 </table>
		 <br/>
		 <br/>
		   
			<form action="Controller?command=withdraw" method="post">
				<table>
			 	<tr>	
			 		<td>(*)Withdrawal amount: </td>
			 		<td><input type="text" id="withdraw_amount" name="withdraw_amount"></<input></td>
			 		<td><span class="error">${withdraw_error}</span></td>
			 		<td><span class="info">${withdraw_info}</span></td>
			 	</tr>
			 	<tr>
			 		<td><input type="submit" value="Withdraw"></<input></td>
			 	</tr>
			 </table>
		 	</form>
		  
		  <br/>
		  
			 <form action="Controller?command=deposit" method="post">
			 	<table>
				<tr>	
			 			<td>(*)Deposit amount: </td> 				
			 			<td><input type="text" id="deposit_amount" name="deposit_amount"></input></td>
			 			<td><span class="error">${deposit_error}</span></td>
			 			<td><span class="info">${deposit_info}</span></td>	
			 	</tr>
			 	<tr>
			 		<td><input type="submit" value="Deposit"></<input></td>
			 	</tr>
			 	</table>
			 </form>
		 <br/>
		*Commission rate is ${commission}&#36;
	  </div>
    </div>
  </div>
</body>
</html>