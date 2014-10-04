<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%--@ page session="false" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="styles/layout.css" type="text/css" />
<title>MBank Client Dashboard</title>
</head>
<body id="top">

<div class="wrapper col1">
  <div id="header">
    <div class="fl_left">
      <h1><a href="#">MBank Corp.</a></h1>
      <p>Welcome, ${username}</p>
    </div>
    <div class="fl_right"><a href="Controller?command=logout">Logout</div>
    <br class="clear" />
  </div>
</div>




<%-- <div id="tabs">
  <ul>
    <li><a href="Controller?command=account"><span>Account</span></a></li>
    <li><a href="Controller?command=recent_activities"><span>Recent Activities</span></a></li>
    <li><a href="Controller?command=deposits"><span>Deposits</span></a></li>
    <li><a href="Controller?command=myDetails"><span>My Details</span></a></li>
    <li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
    <li><a href="Controller?command=logout"><span>Logout</span></a></li>
  </ul>
</div>
--%>
<!--<table>
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
		<td>
			<a href="Controller?command=logout">Logout</a>
		</td>
	</tr>
</table> 

<br/>
<hr>
-->

</body>
</html>