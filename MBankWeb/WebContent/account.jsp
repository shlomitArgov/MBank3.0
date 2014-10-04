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


<div class="wrapper col2">
  <div id="topbar">
    <div id="topnav">
      <ul>
        <li class="active"><a href="Controller?command=account">Account</a></li>
        <li><a href="Controller?command=deposits"><span>Deposits</span></a></li>
        <li><a href="Controller?command=recent_activities">Recent Activities</a></li>
  		<li><a href="Controller?command=myDetails"><span>My Details</span></a></li>
    	<li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
      </ul>
    </div>
    <br class="clear" />
  </div>

</div>
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
 <br/>
 
 <ul>
 	<li><a href="Controller?command=withdraw">Withdraw</a></li>
 	<li><a href="Controller?command=deposit">Deposit</a></li>
 </ul>
<br class="clear" />
</body>
</html>