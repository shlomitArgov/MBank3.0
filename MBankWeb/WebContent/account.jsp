<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" %>
<%@ include file="clientMenu.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%--<title>Insert title here</title> --%>
</head>
<body>
<table>
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
</table>
</body>
</html>