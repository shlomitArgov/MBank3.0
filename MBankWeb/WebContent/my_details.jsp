<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" %>
<%@ include file="clientMenu.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

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


</body>
</html>