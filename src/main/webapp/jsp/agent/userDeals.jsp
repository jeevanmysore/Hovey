<head>
	<title>Agent Deal Sheets</title>
</head>

   <%@ include file="header.jsp" %>
	<div id="content">
	
	  <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	  
	  </div>
		
		<h4 align="center">Saved Deal Sheets</h4>
		<br /><br /><br/>
		<div class="CSSTableGenerator" >
                <table >
                    <tr>
                       <td>Date</td>
				        <td>Customer Name</td>
				        <td>Customer Title</td>
				        <td>Tax Id</td>
				        
				    	<td></td>
                    </tr>
                    <c:forEach var="order" items="${orders}">
			   		 <tr>
				         <td><fmt:formatDate pattern="MM/dd/yyyy" value="${order.orderDate}" /></td>
				         <td>${order.taxId.firstName} ${order.taxId.lastName}</td>
				         <td>${order.taxId.title}</td>
				         <td>${order.taxId.taxId}</td>
				        
				         <td><a href="#" onClick="window.open('./printdeal.do?transactionId=${order.transDto.transactionId}','mywindow','width=1280px,height=800px,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,copyhistory=no,resizable=yes')">Print Deal Sheet</a></td>
			   		 </tr>
		   		 </c:forEach>
                </table>
            </div>
       
		
	
	</div>
   
 </body>
</html>