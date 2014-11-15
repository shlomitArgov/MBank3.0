<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="clientMenu.jsp" %>

<!-- <link rel="stylesheet" href="styles/layout.css" type="text/css" /> -->
<link rel="stylesheet" href="styles/style.css" type="text/css" />
 

<jsp:useBean id="client" class="mbank.database.beans.Client" scope="request"></jsp:useBean>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MBank</title>
</head>

<body>
 	<table summary="Client Details" class="dataTable">
 	 <caption>My Details</caption>
        <thead>
          <tr>
            <th class="thclass">Client ID</th>
            <th class="thclass">Name</th>
            <th class="thclass">Type</th>
            <th class="thclass">Address</th>
            <th class="thclass">E-mail</th>
            <th class="thclass">Phone #</th>
          </tr>
        </thead>
        
 		<tbody>
          <tr>
            <td class="tdclass">${client.client_id}</td>
            <td class="tdclass">${client.client_name}</td>
            <td class="tdclass">${client.type}</td>
            <td class="tdclass">${client.address}</td>
            <td class="tdclass">${client.email}</td>
            <td class="tdclass">${client.phone}</td>
          </tr>
        </tbody>
	</table>
	
	<br/>
	<table>
		<form action="Controller?command=updateClientDetails" method="post">
	 	<tr>	
	 		<td>Address</td>
	 		<td><input type="text" id="client_address" name="client_address"></<input></td>
	 		<td><span class="error">${address_error}</span></td>
	 		<td><span class="info">${address_info}</span></td>
	 	</tr>
	 	<tr>	
	 		<td>Email</td>
	 		<td><input type="text" id="client_email" name="client_email"></<input></td>
	 		<td><span class="error">${email_error}</span></td>
	 		<td><span class="info">${error_info}</span></td>
	 	</tr>
	 	<tr>	
	 		<td>Phone #</td>
	 		<td><input type="text" id="client_phone" name="client_phone"></<input></td>
	 		<td><span class="error">${phone_error}</span></td>
	 		<td><span class="info">${phone_info}</span></td>
	 	</tr>
	 	<tr>
	 		<td><input type="submit" value="Update"></<input></td>
	 	</tr>
	 	<tr>
	 		<td><span class="error">${update_client_details_error}</span></td>
	 	</tr>
	 </form>
 </table>
	
</body>
</html>