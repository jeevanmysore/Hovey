<!DOCTYPE html>
<html>
<head>
	<title>Commission Reconciliation</title>
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
		
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  
	 
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Commission Reconciliation</div>
		
		
		<div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  		${message}	 
	  	</div>
	  	<c:if test="${empty message}">	  	
				<div class="CSSTableGenerator" >
		                <table>
		                    <tr>
		                       
		                        <td>Account#</td>
		                    	<td>Business Name</td>		                    	
		                       <td>Supplier</td>                		
		                   		<td>Term</td>
		                   		<td>kWh</td>
		                   		<td>Sent To Supplier Date</td>
		                   	<!-- 	added deal start date and year by bhagya on may 26th,2014 -->
		                   		<td>Deal Start Date</td>
		                   		<td>Cont Type</td>
		                   		<td>Status</td>
		                   		<td>Year</td>
		                   		<td>Comm Rate</td>
		                   		<td>Upfront Commi ssion Expected</td>
		                   		<td>Annual Commi ssion Expected</td>
		                   		<td>Annual Commission Received</td>
		                   		<td>Annual Paid Date</td>
		                   		<td width="60">Total Annual (Term) Commi ssion Received</td>
		                   		<td>More</td>
		                   		
		                   		
		                    </tr>
		                    <c:set var="i" value="1"></c:set>
		                    <c:set var="totalCommission" value="0.0"></c:set>
		                      <c:set var="totalTermCommission" value="0.0"></c:set>
		                    <c:set var="totalUpfrontCommission" value="0.0"></c:set>
		                    <c:forEach var="order" items="${orders}">
					   		 <tr>
					   		 		<td>${order.accountNumber}</td>
					   		 		<td>${order.businessName}</td>					   		 		
					   		 		<td>${order.supplierName.supplierName}</td>					   		 		
					   		 		<td>${order.term}</td>
					   		 		<td><fmt:formatNumber groupingUsed="true" value="${order.kwh}" /> 
					   		 			<input type="hidden" id="kwh${order.id}" value="${order.kwh}" />
					   		 		 </td>
					   		 		 <td><fmt:formatDate value="${order.sentToSupplier}" pattern="MM/dd/yyyy"/>  </td>
					   		 		 <td><fmt:formatDate value="${order.dealStartDate}" pattern="MM/dd/yyyy"/>  </td>
					   		 		 <td style="text-transform: capitalize;">${order.contractType }</td>
					   		 		 <td>${order.status }</td>
					   		 		 <td>${order.orderYear}</td>
					   		 		<td>  <input class="rec_input" type="text" id="comRate${order.id }" name="commissionRate"   value='<fmt:formatNumber value=" ${order.commissionRate}"  maxFractionDigits="3" minFractionDigits="3"/>' index="${order.id }"  onchange="changeCommission(this.id)"/> </td>
					   		 		<td>$ <input class="rec_input" type="text" id="commission${order.id }" name="commission"   value='<fmt:formatNumber groupingUsed="true" value=" ${order.commission}"  maxFractionDigits="2" minFractionDigits="2"/>' index="${order.id }"  onblur="reconcileCommission(this.id)"/></td>
					   		 		<td>$ 
					   		 			<input type="hidden" value="${order.termMonths }" id="termMonth${order.id}" />
					   		 			<input class="rec_input" type="text" id="termCommission${order.id }" name="termCommission"   value='<fmt:formatNumber groupingUsed="true" value=" ${order.termCommission}"  maxFractionDigits="2" minFractionDigits="2"/>' readonly="readonly"/></td>
					   		 		
					   		 		<td>$ <input class="rec_input" type="text" id="upfrontCommission${order.id }" name="upfrontCommission"   value='<fmt:formatNumber groupingUsed="true" value=" ${order.upfrontCommission}"  maxFractionDigits="2" minFractionDigits="2"/>'  index="${order.id }"  onblur="reconcileCommission(this.id)" /></td>					   		 		
					   		 		<td><fmt:formatDate value="${order.upfrontPaidDate}" pattern="MM/dd/yyyy"/>  </td>
					   		 		<td>$ <fmt:formatNumber groupingUsed="true" value=" ${order.totalUpfrontCommission}"  maxFractionDigits="2" minFractionDigits="2"/></td>
					   		 		<c:set var="totalCommission" value="${totalCommission +order.commission }" />
					   		 		<c:set var="totalTermCommission" value="${totalTermCommission +order.termCommission }" />
					   		 		<c:set var="totalUpfrontCommission" value="${totalUpfrontCommission+order.upfrontCommission }"></c:set>
					   		 		<!-- added by bhagya on may27th,2014 -->
					   		 		<td><a href="/hoveyenergy/admin/editreconcile.do?orderId=${order.id}"><img src="/hoveyenergy/images/edit_mapping.png"  title="List Of All Commissions" class="icon"/></a></td>
						        </tr>
				   		 </c:forEach>
		                </table>
		            </div>
            
            
            
       
				 <div class="summary" style="float: right;margin-top: 30px;text-align: center;margin-right: 30px; ">
				     <div>
				 		<label style="text-align: left;font-weight: bold;font-size: 16px;margin-left: 94px;">Total  Commission Expected :</label>
				 		<span class="result" id="totalCommission"> $ <fmt:formatNumber groupingUsed="true" maxFractionDigits="2" minFractionDigits="2" value="${totalCommission }" /> </span> <br />
				 	</div>
				 	
					 	 <div>
					 		<label style="text-align: left;font-weight: bold;font-size: 16px;">Total Commission Expected as per Term :</label>
					 		<span class="result" id="totalTermCommission"> $ <fmt:formatNumber groupingUsed="true" maxFractionDigits="2" minFractionDigits="2" value="${totalTermCommission }" /> </span> <br />
					 	</div>
				 	
				 	<div style="text-align: left;"> 
				 		<label style="text-align: left;font-weight: bold;font-size: 16px;margin-left: 96px;">Total Commission Received :</label>
				 		<span class="result" id="totalUpfront">$ <fmt:formatNumber groupingUsed="true" maxFractionDigits="2" minFractionDigits="2" value="${totalUpfrontCommission }" /> </span> <br />
				 	</div>	
				 </div>
			</c:if>
		
		<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										
									
			<a href="/hoveyenergy/admin/commissions.do"  class="btn btn-back"  style="margin-left: 10px;">Back</a>
		</div>
	
	
	</div>
   
 </body>
</html>