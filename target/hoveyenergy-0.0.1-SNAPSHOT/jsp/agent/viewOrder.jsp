
	<head>
		<title>	View Order</title>
	</head>
	
	<%@ include file="header.jsp" %>
	<div id="content">
	
	  <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	  
	  </div>
		
		<h4 align="center">Order Data</h4>
		<br /><br /><br/>
		<div class="CSSTableGenerator" style="width:40%; margin: 0px auto" >
                <table >
                	 <tr>
                    	<td>Order Date</td>
                    	<td> <fmt:formatDate value="${order.orderDate}" pattern="MM/dd/yyyy" /></td>
                    </tr>
                    <tr>
                    	<td>Customer Name</td>
                    	<td>${order.taxId.firstName } ${order.taxId.lastName } </td>
                    </tr>
                    <tr>
                    	<td>Tax Id</td>
                    	<td>${order.taxId.taxId }  </td>
                    </tr>
                    <tr>
                    	<td>Phone Number</td>
                    	<td>${order.taxId.phoneNo}  </td>
                    </tr>
                    <tr>
                    	<td>Fax</td>
                    	<td>${order.taxId.faxNo}  </td>
                    </tr>
                    <tr>
                    	<td>E-mail</td>
                    	<td>${order.taxId.email}  </td>
                    </tr>
                    <tr>
                    	<td>Tax Exempt </td>
                    	<c:choose>
                    	<c:when test="${order.taxId.taxExempt eq true }">
                    		<td>YES  </td>
                    	</c:when>
                    	<c:otherwise>
                    		<td>NO</td>
                    	</c:otherwise>
                    	</c:choose>
                    </tr>
                    <tr>
                    	<td>Title</td>
                    	<td>${order.taxId.title}  </td>
                    </tr>
                    
                    <tr>
                    	<td>Utility</td>
                    	<td>${order.utility.utility}  </td>
                    </tr>
                    <tr>
                    	<td>Account Number</td>
                    	<td>${order.accountNumber }  </td>
                    </tr>
                    <tr>
                      <td> Rate Class</td>
                      <td>${order.rateClass }</td>
                    </tr>
                    <tr>
                      <td> Rate</td>
                      <td><fmt:formatNumber currencyCode="US" value="${order.rate }" /></td>
                    </tr>
                    <tr>
                      <td> Term</td>
                      <td>${order.term }</td>
                    </tr>
                    <tr>
                      <td> KWH</td>
                      <td><fmt:formatNumber currencyCode="US" value="${order.kwh }" /></td>
                    </tr>
                     <tr>
                      <td> Business Name</td>
                      <td>${order.businessName }</td>
                    </tr>
                     <tr>
                      <td> DBA</td>
                      <td>${order.dba }</td>
                    </tr>
                     <tr>
                       <td colspan="2"><b>Service Address</b></td>
                    </tr>
                     <tr>
                      <td> Street</td>
                      <td>${order.serviceStreet }</td>
                    </tr>
                     <tr>
                      <td> City</td>
                      <td>${order.serviceCity }</td>
                    </tr>
                     <tr>
                      <td> State</td>
                      <td>${order.serviceState }</td>
                    </tr>
                     <tr>
                      <td> ZipCode</td>
                      <td>${order.serviceZip }</td>
                    </tr>
                    <tr>
                       <td colspan="2"><b>Billing Address</b></td>
                    </tr>
                     <tr>
                      <td> Street</td>
                      <td>${order.billingStreet }</td>
                    </tr>
                     <tr>
                      <td> City</td>
                      <td>${order.billingCity }</td>
                    </tr>
                     <tr>
                      <td> State</td>
                      <td>${order.billingState }</td>
                    </tr>
                     <tr>
                      <td> ZipCode</td>
                      <td>${order.billingZip }</td>
                    </tr>
                </table>
            </div>
    
		
	
	</div>
   
