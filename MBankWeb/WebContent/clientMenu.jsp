<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%--@ page session="false" --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="styles/style.css" type="text/css" />

<title>MBank Client Dashboard</title>
</head>
<body>
<div id="container">
	<div id="navcontainer">
	   	<ul class="nav">
	   	<h1><p>Welcome back, ${client_name}</p></h1>
		    <li><a href="Controller?command=my_account"><span>Account</span></a></li>
		    <li><a href="Controller?command=my_recent_activities"><span>Recent Activities</span></a></li>
		    <li><a href="Controller?command=my_deposits"><span>Deposits</span></a></li>
		    <li><a href="Controller?command=my_details"><span>My Details</span></a></li>
		    <li><a href="Controller?command=mbank_properties"><span>MBank Properties</span></a></li>
		    <li><a href="Controller?command=logout"><span>Logout</span></a></li>
  		</ul>
	</div>  
  <div id="content">
  	<div id="maincontent">

<!--  </body>-->
