<head>
	<title>User Orders</title>
</head>
<%@ include file="header.jsp" %>
	<div id="content">
	
	  <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	  
	  </div>
		
		<h4 align="center">Orders in Deal Sheet</h4>
		<br /><br /><br/>
		<div class="CSSTableGenerator" >
                <table >
                    <tr>
                       <td>Date</td>
				        <td>Agent Name</td>
				        <td>Agent ID</td>
				        <td>Utility</td>
				        <td>Business Name</td>
				        <td>AccountId</td>
				        <td>kwh</td>
				    	<td>Status</td>
				    	<td></td>
                    </tr>
                    <c:forEach var="order" items="${orders}">
			   		 <tr>
				         <td><fmt:formatDate pattern="MM/dd/yyyy" value="${order.orderDate}" /></td>
				         <td>${order.createdAgent.firstName} ${order.createdAgent.lastName}</td>
				         <td>${order.createdAgent.agentNumber}</td>
				         <td>${order.utility.utility}</td>
				         <td>${order.businessName}</td>
				         <td>${order.accountNumber}</td>
				         <td>${order.kwh}</td>
				         <td>${order.status}</td>
				         <td><a href="./printorder.do?orderId=${order.id}">View Order</a></td>
			   		 </tr>
		   		 </c:forEach>
                </table>
            </div>
        
		
	
	</div>
  