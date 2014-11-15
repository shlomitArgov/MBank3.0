<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ include file="clientMenu.jsp" %>
		
		<%-- display all system properties --%>
		  <table summary="System properties" class="dataTable">
		   <caption>System Properties</caption>
			<thead>
			  <tr>
				<th class="thclass">Name</th>
				<th class="thclass">Value</th>
			  </tr>
			</thead>
			<tbody>
			<c:forEach items="${system_properties}" var="property">
				<jsp:useBean id="property" class="mbank.database.beans.Property" scope="request"></jsp:useBean>
				<tr>
				<td class="tdclass">${property.prop_key}</td>
				<td class="tdclass">${property.prop_value}</td>
				</tr>	
			</c:forEach>
			</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>