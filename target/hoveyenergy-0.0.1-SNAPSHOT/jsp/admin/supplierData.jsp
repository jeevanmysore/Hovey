<!DOCTYPE html>
<html>
<head>
	<title>Supplier Data</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link href="css/common.css" rel="stylesheet" type="text/css">
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');
		    
		});
	</script>
	
	<style>
			
		
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  		The following are the data obtained from Supplier Report
	 
	  </div>
	 
		
		<div class="title" style="margin-top: 15px;margin-bottom: 15px;">Supplier Report Data</div>
		<%-- <ul class="pager" >
		      <c:choose>
		      	<c:when test="${page eq 0 && end ne 1 }">
		      		<li>	 <a href="./customer.do?page=${page+1}"><img src="images/next.png" /></a></li>
		      	</c:when>
		      
		        <c:when test="${page eq end-1 && end ne 1 }">
		                <a href="./customer.do?page=${page-1}"><img src="images/prev.png" /></a></li>
		        </c:when>
		        
		        <c:when test="${page eq end-1 && end eq 1 }">
		               
		        </c:when>
		        
		        <c:otherwise>
		        	<li>	<a href="./customer.do?page=${page-1}"><img src="images/prev.png" /></a></li>
					<li> <a href="./customer.do?page=${page+1}"><img src="images/next.png" /></a></li>
		        
		        </c:otherwise>
		      </c:choose>
		</ul> --%>
		<div class="CSSTableGenerator" >
                <table>
                    <tr>
                        <td>Supplier Name</td>
                        <td>Customer Name </td>
                    	<td>Account#</td> 
                    	<td>Start Date</td>
                    	<td>End Date</td>
                    	<td>Term</td>
                    	<td>kWh </td>
                    	<td>Rate</td>
                    	<td> Commission Rate</td>
                    	<td>Paid Date</td>
                    	<td>Total Commission</td>                   
                   		
                   		
                   		<!-- <td>Edit</td> -->
                    </tr>
                    <c:forEach var="report" items="${reports}">
			   		 <tr>
			   		 		<td> ${report.supplierName.supplierName }</td>
			   		 		<td>${report.customerName}</td>
			   		 		<td>${report.accountNumber}</td>
			   		 		<td><fmt:formatDate value="${report.contractStartDate}" pattern="MM/dd/yyyy"/>  </td>
			   		 		<td><fmt:formatDate value="${report.contractEndDate}" pattern="MM/dd/yyyy"/></td>
			   		 		<td>${report.term}</td>
			   		 		<td>${report.kwh}</td>
			   		 		<td>${report.rate}</td>
			   		 		<td>${report.commissionRate}</td>
			   		 		<td><fmt:formatDate value="${report.upfrontPaidDate}" pattern="MM/dd/yyyy"/></td>
			   		 		<td><fmt:formatNumber  maxFractionDigits="2" >${report.totalCommissionPaid}</fmt:formatNumber> </td>
			   		 		
				        </tr>
		   		 </c:forEach>
                </table>
            </div>
       			
			<div style="text-align: center;margin-top: 50px;clear: both;">
									
										<button type="button"  class="btn-update btn" style="width: auto;" onclick="alert('will be implemented after obtaining sample data')">Save Supplier Data</button>
										
										
									</div>
	
	</div>
   
 </body>
</html>