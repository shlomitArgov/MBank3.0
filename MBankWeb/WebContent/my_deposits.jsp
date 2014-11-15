<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ include file="clientMenu.jsp" %>

<%-- display all client deposits --%>
  <table summary="Deposits" class="dataTable">
  <caption>Deposits</caption>
	<thead>
	  <tr>
		<th class="thclass">Deposit ID</th>
		<th class="thclass">Balance</th>
		<th class="thclass">Type</th>
		<th class="thclass">Estimated Balance</th>
		<th class="thclass">Opening Date</th>
		<th class="thclass">Closing Date</th>
	  </tr>
	</thead>
	<tbody>
	<c:forEach items="${client_deposits}" var="deposit">
		<jsp:useBean id="deposit" class="mbank.database.beans.Deposit" scope="request"></jsp:useBean>
		<tr>
		<td class="tdclass">${deposit.deposit_id}</td>
		<td class="tdclass"><fmt:formatNumber type="number" maxFractionDigits="2" value="${deposit.balance}"/></td>
		<td class="tdclass">${deposit.type.typeStringValue}</td>
		<td class="tdclass"><fmt:formatNumber type="number" maxFractionDigits="2" value="${deposit.estimated_balance}"/></td>
		<td class="tdclass">${deposit.opening_date_simple_format}</td>
		<td class="tdclass">${deposit.closing_date_simple_format}</td>
		</tr>	
	</c:forEach>
	</tbody>
	</table>
	<br/>

	<form action="Controller?command=createDeposit" method="post">	
	 <table>
	   <caption>Create a new deposit</caption>
		 	<tr>	
		 		<td>Deposit amount: </td>
		 		<td><input type="text" id="initial_deposit_amount" name="initial_deposit_amount"></<input></td>
		 		<td><span class="error">${deposit_amount_error}</span></td>
		 	</tr>
		 	<tr>	
		 		<td>Deposit end-date in the format DD-MM-YYYY (e.g. 13-01-2014): </td>
		 		<td><input type="text" id="deposit_end_date" name="deposit_end_date"></<input></td>
		 		<td><span class="error">${deposit_end_date_error}</span></td>
		 	</tr>
		 	<tr>
		 		<td><input type="submit" value="Create deposit"></<input></td>
		 	</tr>
	 </table>
	 <br/>
	 <span class="error">${create_deposit_error}</span>
	 <span class="info">${create_deposit_info}</span>
	</form>
	 <br/>

	<form action="Controller?command=preOpenDeposit" method="post">
		<table>
	     <caption>Pre-open a long-term deposit</caption>
		 	<tr>	
		 		<td>Deposit ID#: </td>
		 		<td><input type="text" id="depositId" name="depositId"></<input></td>
		 		<td><span class="error">${deposit_id_error}</span></td>
		 	</tr>
		 	<tr></tr>
		 	<tr>
		 		<td><input type="submit" value="Pre-open deposit"></<input></td>
		 	</tr>
		 </table>
		 <br/>
		 <span class="error">${pre_open_deposit_error}</span>
		 <span class="info">${pre_open_deposit_info}</span>
	 </form>
	</div>
   </div>
  </div>	 
</body>
</html>