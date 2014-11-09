<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="clientMenu.jsp" %>

<link rel="stylesheet" href="styles/layout.css" type="text/css" /> 

<jsp:useBean id="client" class="mbank.database.beans.Client" scope="request"></jsp:useBean>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MBank</title>
</head>

<body>
    <ul>
       <li><a href="Controller?command=my_account">Account</a></li>
       <li><a href="Controller?command=my_deposits"><span>Deposits</span></a></li>
       <li><a href="Controller?command=my_recent_activities">Recent Activities</a></li>
	   <li class="active"><a href="Controller?command=my_details"><span>My Details</span></a></li>
	   <li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
    </ul>
	<br />
    
 	<table summary="Client Details" cellpadding="0" cellspacing="0">
        <thead>
          <tr>
            <th>Client ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Address</th>
            <th>E-mail</th>
            <th>Phone#</th>
          </tr>
        </thead>
        
 		<tbody>
          <tr class="detailsTable">
            <td>${client.client_id}</td>
            <td>${client.client_name}</td>
            <td>${client.type}</td>
            <td>${client.address}</td>
            <td>${client.email}</td>
            <td>${client.phone}</td>
          </tr>
        </tbody>
	</table>
</body>
</html>