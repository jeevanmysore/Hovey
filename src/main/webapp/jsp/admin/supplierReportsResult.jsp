<!DOCTYPE html>
<html>
<head>
	<title>Supplier Reports Result</title>
	<link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
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
	
	 
	
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Supplier Reports Summary</div>
		 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	 
	  </div>
	  
	  
			  <div style="margin: 0 auto;display: inline-block;background-color: #DFDFDF;width: 1000px;margin: 10px;padding: 10px;border: 1px solid #000;border-radius: 5px;">
					<c:if test="${not empty savedReports}">
					
					 	<div style="float: left;font-size: 14px;font-weight: bold;margin-bottom: 10px;">
					 		Reports Updated Successfully to Pipeline Orders:
					    </div>
						<div class="CSSTableGenerator" style="margin: 0 auto;border: none;clear: both;" >		
				                <table>
				                    <tr>
				                    	<td></td>
				                        <td>Supplier Name</td>
				                        <td>Account #</td>
				                    	<td>Customer</td>                    
				                   		<td>Commission Paid</td>
				                   		<td>Paid Date</td>                          		
				                    </tr>
				                     <c:set var="i" value="1" />
				                    <c:forEach var="report" items="${savedReports}">
							   		 <tr>
							   		 		<td>${i }</td>
							   		 		<td> ${report.supplierName.supplierName }</td>
							   		 		<td>${report.accountNumber }</td>
							   		 		<td>${report.customerName }</td>
							   		 		<td><fmt:formatNumber currencyCode="US" groupingUsed="true" maxFractionDigits="2" minFractionDigits="2" value="${report.totalCommissionPaid }" /> </td>
							   		 		<td><fmt:formatDate value="${report.datePaid }" pattern="MM/dd/yyyy"  /></td> 		 				   		 		
							   		 		
								        </tr>
								         <c:set var="i" value="${i+1 }" />
						   		 </c:forEach>
				                </table>
				            </div>
				           
				            
				   	</c:if>
			   <c:if test="${empty savedReports}">
			   		 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
				  							No Orders in Pipeline Updated with Supplier Reports
				 
				  </div>
			   </c:if>
		       
			</div>	
			
			
			
			<div style="margin: 0 auto;display: inline-block;background-color: #DFDFDF;width: 1000px;margin: 10px;padding: 10px;border: 1px solid #000;border-radius: 5px;">
					<c:if test="${not empty nonPipelineOrders}">
					
					 	<div style="float: left;font-size: 14px;font-weight: bold;margin-bottom: 10px;">
					 		Orders Not Found in Pipeline, but Loaded Successfully from Reports, They can be Updated using Load Existing Suppliers
					    </div>
						<div class="CSSTableGenerator" style="margin: 0 auto;border: none;clear: both;" >		
				                <table>
				                    <tr>
				                    	<td></td>
				                        <td>Supplier Name</td>
				                        <td>Account #</td>
				                    	<td>Customer</td>                    
				                   		<td>Commission Paid</td>
				                   		<td>Paid Date</td>                          		
				                    </tr>
				                     <c:set var="i" value="1" />
				                    <c:forEach var="report" items="${nonPipelineOrders}">
							   		 <tr>
							   		 		<td>${i }</td>
							   		 		<td> ${report.supplierName.supplierName }</td>
							   		 		<td>${report.accountNumber }</td>
							   		 		<td>${report.customerName }</td>
							   		 		<td><fmt:formatNumber currencyCode="US" groupingUsed="true" maxFractionDigits="2" minFractionDigits="2" value="${report.totalCommissionPaid }" /> </td>
							   		 		<td><fmt:formatDate value="${report.datePaid }" pattern="MM/dd/yyyy"  /></td> 		 				   		 		
							   		 		
								        </tr>
								         <c:set var="i" value="${i+1 }" />
						   		 </c:forEach>
				                </table>
				            </div>
				           
				            
				   	</c:if>
			   <c:if test="${empty savedReports}">
			   		 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
				  							No Orders in Pipeline Updated with Supplier Reports
				 
				  </div>
			   </c:if>
		       
			</div>	
			
			 <div style="margin: 0 auto;display: inline-block;background-color: #DFDFDF;width: 1000px;margin: 10px;padding: 10px;border: 1px solid #000;border-radius: 5px;">
					<c:if test="${not empty errorReports}">
					
					 	<div style="float: left;font-size: 14px;font-weight: bold;margin-bottom: 10px;">
					 		Error in Loading following Accounts Information from Supplier Reports;
					    </div>
						<div style="margin: 0 auto;border: none;clear: both;" >		
				                <table style="background-color: #E5ECD9;border: 1px solid #7F7F66;width: 820px;border-radius:5px;">
				                    <tr></tr>
				                    <c:forEach var="report" items="${errorReports}">
							   		 <tr style="float: left;margin: 12px;font-size: 14px;">
							   		 		<td> ${report }</td>
							   		 					   		 		
							   		 		
								        </tr>
						   		 </c:forEach>
				                </table>
				            </div>
				           
				            
				   	</c:if>
			   <c:if test="${empty errorReports}">
			   		 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
				  							Successfully Loaded All Supplier Reports
				 
				  </div>
			   </c:if>
		       
			</div>	
			
			
			<div style="text-align: center;margin-top: 50px;clear: both;">									
										<a href="/hoveyenergy/admin/getpipeline.do" class="btn-success btn" style="width: auto;margin-right: 20px;">Goto Pipeline</a>
										<a href="/hoveyenergy/admin/loadsupplierreport.do" class="btn-update btn" style="width: auto;margin-right: 20px;">Upload More Files</a>
										<a href="/hoveyenergy/admin/loadsupplierreport.do" class="btn-undo btn" style="width: auto;">Home</a>	
										
		</div>
			
	</div>
 
 </body>
</html>