<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="client" class="mbank.database.beans.Client" scope="session"></jsp:useBean>
<%@ include file="clientMenu.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body id="top">

<div class="wrapper col2">
  <div id="topbar">
    <div id="topnav">
      <ul>
        <li><a href="Controller?command=account">Account</a></li>
        <li><a href="Controller?command=deposits"><span>Deposits</span></a></li>
        <li><a href="Controller?command=recent_activities">Recent Activities</a></li>
  		<li class="active"><a href="Controller?command=myDetails"><span>My Details</span></a></li>
    	<li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
      </ul>
    </div>
    <br class="clear" />
  </div>
</div>

  <div class="container">
    <div class="content">
  <table summary="Client Details" cellpadding="0" cellspacing="0">
        <thead>
          <tr class="light">
            <th>Client ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Address</th>
            <th>E-mail</th>
            <th>Phone#</th>
          </tr>
        </thead>
        
 		<tbody>
          <tr class="light">
            <td>${client.client_id}</td>
            <td>${client.client_name}</td>
            <td>${client.type}</td>
            <td>${client.address}</td>
            <td>${client.email}</td>
            <td>${client.phone}</td>
          </tr>
        </tbody>
	 </table>
 	</div>
 </div>
 

</body>
</html>