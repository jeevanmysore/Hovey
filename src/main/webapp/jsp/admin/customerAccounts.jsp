<!DOCTYPE html>
<html>
<head>
	<title>Customer Accounts</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
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
		
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  
	 
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Customer Accounts</div>
		<div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	 
	  	</div>
	  	<c:if test="${empty message }">
			<div class="CSSTableGenerator" >
	                <table>
	                    <tr>
	                       
	                        <td></td>
	                    	<td>Business Name</td>
	                    	<td>Account#</td>
	                       <td>Supplier</td>             		
	                   		<td>Start Date</td>
	                   		<td>Term</td>
	                   		<td>kWh</td>
	                   		<td>Commission Expected</td>
	                   		<td>Commission Received</td>
	                   		
	                   		
	                    </tr>
	                    <c:set var="i" value="1"></c:set>
	                    <c:set var="totalCommission" value="0.0"></c:set>
	                    <c:set var="totalUpfrontCommission" value="0.0"></c:set>
	                    <c:forEach var="order" items="${orders}">
				   		 <tr>
				   		 		<td>${i }</td>
				   		 		<td>${order.businessName}</td>
				   		 		<td>${order.accountNumber}</td>
				   		 		<td>${order.supplierName.supplierName}</td>
				   		 		<td><fmt:formatDate value="${order.dealStartDate}"  pattern="MM/dd/yyyy" /> </td>
				   		 		<td>${order.term}</td>
				   		 		<td><fmt:formatNumber groupingUsed="true" value="${order.kwh}" />  </td>
				   		 		<td>$<fmt:formatNumber groupingUsed="true" value=" ${order.commission}"  maxFractionDigits="2" minFractionDigits="2"/></td>
				   		 		<td>$<fmt:formatNumber groupingUsed="true" value=" ${order.upfrontCommission}"  maxFractionDigits="2" minFractionDigits="2"/></td>
				   		 		<c:set  var="i" value="${i+1 }" ></c:set>
				   		 		<c:set var="totalCommission" value="${totalCommission +order.commission }" />
				   		 		<c:set var="totalUpfrontCommission" value="${totalUpfrontCommission+order.upfrontCommission }"></c:set>
					        </tr>
			   		 </c:forEach>
	                </table>
	            </div>
	            
            
            
       
			 <div class="summary" style="float: right;margin-top: 30px;text-align: center;margin-right: 30px; ">
				     <div>
				 		<label style="text-align: left;font-weight: bold;font-size: 16px;">Total Commission Expected :</label>
				 		<span class="result" id="totalCommission"> $ <fmt:formatNumber groupingUsed="true" maxFractionDigits="2" minFractionDigits="2" value="${totalCommission }" /> </span> <br />
				 	</div>
				 	<div style="text-align: left;"> 
				 		<label style="text-align: left;font-weight: bold;font-size: 16px;">Total Commission Received :</label>
				 		<span class="result" id="totalUpfront">$ <fmt:formatNumber groupingUsed="true" maxFractionDigits="2" minFractionDigits="2" value="${totalUpfrontCommission }" /> </span> <br />
				 	</div>	
				 </div>
		</c:if>
		<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										
									
			<a href="/hoveyenergy/admin/customer.do"  class="btn btn-back"  style="margin-left: 10px;">Back</a>
		</div>
	
	
	</div>
   
 </body>
</html>