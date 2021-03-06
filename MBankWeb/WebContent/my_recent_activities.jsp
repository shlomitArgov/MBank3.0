<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="clientMenu.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	  <table summary="Client Activities" class="dataTable">
		<caption><div id="mainheading">Recent Activities</div></caption>
			<thead>
			  <tr>
				<th class="thclass">Activity ID</th>
				<th class="thclass">Amount</th>
				<th class="thclass">Activity Type</th>
				<th class="thclass">Commission</th>
				<th class="thclass">Description</th>
				<th class="thclass">Date</th>
			  </tr>
			</thead>
			<tbody>
				<c:forEach items="${client_activities}" var="activity">
					<jsp:useBean id="activity" class="mbank.database.beans.Account" scope="request"></jsp:useBean>
					<tr class="light">
					<td class="tdclass">${activity.id}</td>
					<td class="tdclass"><fmt:formatNumber type="number" maxFractionDigits="2" value="${activity.amount}"/></td>
					<td class="tdclass">${activity.activityType}</td>
					<td class="tdclass">${activity.commission}</td>
					<td class="tdclass">${activity.description}</td>
					<td class="tdclass">${activity.activity_date_simple_format}</td>
					</tr>	
				</c:forEach>
			</tbody>
		  </table>
		</div>
	  </div>
	</div>
  </body>
</html>