<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MBank Client Dashboard</title>
</head>
<body>
<font size="2">Welcome, ${username}</font>
<br>
<table>
	<tr>
		<td>
			<a href="Controller?command=account">Account</a>
		</td>
		<td>
			<a href="Controller?command=recent_activities">Recent Activities</a>
		</td>
		<td>
			<a href="Controller?command=deposits">Deposits</a>
		</td>
		<td>
			<a href="Controller?command=myDetails">My Details</a>
		</td>
		<td>
			<a href="Controller?command=mbank_properties">MBank Properties</a>
		</td>
	</tr>
</table>
<hr>
</body>
</html>