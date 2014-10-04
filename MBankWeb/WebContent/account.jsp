<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="clientMenu.jsp" %>
<jsp:useBean id="account" class="mbank.database.beans.Account" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" href="styles/layout.css" type="text/css" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%--<title>Insert title here</title> --%>
</head>
<body id="top">

  <div class="container">
    <div class="content">
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
          <tr class="light">
            <td>${account.client_id}</td>
            <td>${account.account_id}</td>
            <td>${account.balance}</td>
            <td>${account.credit_limit}</td>
          </tr>
        </tbody>
 </table>
 </div>
 </div>

<br class="clear" />

<%--<table>
	<thead>
		<tr>
			<td>
				Client ID
			</td>
			<td>
				Account ID
			</td>
			<td>
				Current Balance
			</td>
			<td>
				Credit Limit
			</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				${client_id}
			</td>
			<td>
				${account_id}
			</td>
			<td>
				${current_balance}
			</td>
			<td>
				${credit_limit}
			</td>
		</tr>
	</tbody>
</table> --%>
</body>
</html>