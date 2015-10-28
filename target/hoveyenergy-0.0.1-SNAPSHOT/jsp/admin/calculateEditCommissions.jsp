<!DOCTYPE html>
<html>
<head>
	<title>Edit Agent Commission</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	  <script type="text/javascript" src="/hoveyenergy/js/reconcile.js"></script>
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');
		    
		});
	</script>
	
	<style>
		.result{
			margin-left: 20px;
			font-size: 17px;
		}	
		.rec_input{
			height: 25px !important;
			padding: 1px !important;
		}
		
		td{
			width:130px;
		}
		
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  
	 
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Edit Agent Commissions</div>
		
		
		<div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  		${message}	 
	  	</div>
	  	<c:if test="${empty message}">	 
	  			<input type="hidden" id="week" value="${orders[0].week }" />
	  			<input type="hidden" id="year" value="${orders[0].year }" />
	  			 	
	  			<div class="title" style="color: green;text-decoration: none;">Week : ${orders[0].week} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Year:${orders[0].year }</div>
				<div class="CSSTableGenerator" >
		                <table>
		                    <tr>
		                     <td> S No</td>
		                     <td>Agent Name</td>
		                     <td>Sent To Supplier</td>
		                      <td>Account nuber</td>
		                      <td>Business Name</td>
		                      <td>kWh</td>
		                       <td>Upfront Commission</td>
		                       	<td>Upfront Paid Date</td>
		                       	<td> Contract Type</td>
		                       		<td>Status</td>
		                      <td>Commission Payable?</td>
		                      <td>Commission Rate</td>
		                   	<td> Agent Commission</td>
		                   	<td>Supplier</td>	
		                                 		
		                    </tr>
		                    <c:set var="i" value="1"></c:set>      
		                   
		                    <c:forEach var="order" items="${orders}">
						   		 <tr>
						   		 		<input type="hidden"  id="id${order.id }" value="${order.id }" />
						   		 		<input type="hidden" id="orderId${order.id }" value="${order.order.id }" />
						   		 		<td> ${i }</td>
						   		 		<td>${order.order.createdAgent.firstName} ${order.order.createdAgent.lastName }</td>
						   		 		<td> <fmt:formatDate pattern="MM/dd/yyyy" value="${order.order.sentToSupplier }"  /></td>
						   		 			<td>${order.order.accountNumber}</td>
						   		 		<td>${order.order.businessName}</td> 							   		 			 		
						   		 		<td><fmt:formatNumber groupingUsed="true" value="${order.order.kwh}" /><input type="hidden" id="kwh${order.id }" value="${order.order.kwh }" /> </td>
						   		 		<td><fmt:formatNumber groupingUsed="true" value="${order.order.upfrontCommission}" /> </td>
						   				<td> <fmt:formatDate pattern="MM/dd/yyyy" value="${order.order.upfrontPaidDate }"  /></td>
						   				<td style="text-transform: capitalize;">${order.order.contractType }</td>
						   				<td style="text-transform: capitalize;">${order.order.status }</td>
						   		 		<td>
						   		 			<select name="comPayable" id="comPayable${order.id }" index="${order.id  }"  style="width: 100px;padding: 2px;" onchange="changeEditComPayable(this.id)" onblur="saveEditAgentCommission(this.id);">
						   		 				
						   		 				<option value="yes" ${order.commissionPayable eq true ? 'selected' : '' }>Yes</option>
						   		 				<option value="no" ${order.commissionPayable eq false ? 'selected' : '' }>No</option>
						   		 			
						   		 			</select>
						   		 		</td>						   		 		 
						   		 		<td>  <input class="rec_input" type="text" id="comRate${order.id  }" name="commissionRate"   value="<fmt:formatNumber value='${order.commissionRate }' maxFractionDigits="5" minFractionDigits="3"   />" 
						   		 			index="${order.id  }"   onchange="changeEditAgentCommission(this.id)" onblur="saveEditAgentCommission(this.id)"/> </td>
						   		 			
						   		 		<td>$ <input class="rec_input" type="text" id="commission${order.id  }" name="commission"  readonly="readonly" value="<fmt:formatNumber value='${order.agentCommission }' maxFractionDigits="5" minFractionDigits="3"   />"						   		 		
						   		 		   index="${order.id }"  onblur="saveEditAgentCommission(this.id)"/></td>
						   		 		   <c:choose>
						   		 				<c:when test="${order.agentCommission < 0}">
						   		 					<input type="hidden" id="comvalue${order.id}" value="-1" />	
						   		 				</c:when>
						   		 				<c:otherwise>
						   		 					<input type="hidden" id="comvalue${order.id}" value="0" />		
						   		 				</c:otherwise>
						   		 			</c:choose>
						   		 		   
						   		 		<td>${order.order.supplierName.supplierName}</td>		
						   		 		
							        </tr>
							      <c:set var="i" value="${i+1 }"></c:set>      
				   		 </c:forEach>
		                </table>
		            </div>
            
            
            
            	
       
       
       				
       
       
       
			</c:if>
		
		<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										
			<a href="/hoveyenergy/admin/exportcommissions.do?week=${orders[0].week}&year=${orders[0].year}"  class="btn btn-update"  style="margin-left: 10px;">Export to Excel</a>										
			<a href="/hoveyenergy/admin/editagentcommission.do"  class="btn btn-back"  style="margin-left: 10px;">Back</a>
		</div>
	
	
	</div>
   
 </body>
</html>