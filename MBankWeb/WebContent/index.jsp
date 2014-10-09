<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" href="styles/layout.css" type="text/css" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MBank</title>
</head>
<body>
<h1>Welcome to the MBank Website</h1>
<hr>
Login:
<br>
<form action="Controller" method="post">
	<table>
		<tr>
			<td>Username: </td>
			<td><input type="text" name="username" id="username"></td>
			<td></td>
		</tr>
		<tr>
			<td>Password: </td>
			<td><input type="password" name="password" id="password"></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="command" id="command" value="Login"></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td class="error">${error}</td>
			<td></td>
		</tr>
	</table>
</form>
</body>
</html>