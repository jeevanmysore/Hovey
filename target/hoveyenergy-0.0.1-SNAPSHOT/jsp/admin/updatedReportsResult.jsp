<!DOCTYPE html>
<html>
<head>
	<title>Updated Supplier Reports Result</title>
	<link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');
		    
		    
		    
		    $('#manualUpdate').click(function(){
		    	var ids=new Array();
		    	
		    	$('.reports').each(function(){		    		
		    		if($(this).is(':checked')){
		    			var i=$(this).attr('index');
		    			ids.push(i);
		    		}		    		
		    	});
		    	
		    	if(ids.length>0){
		    		var x=confirm ("Updating Supplier Commission Manually");
					if(x==true){
						window.location="/hoveyenergy/admin/uploadreportmanually.do?id="+ids;
					}
		    	}	
		    	else{
					alert('Please Select Atleat One Report to Apply Manually');
				}
		    });
		    
		    
		    
		    
		    
		    
		});
		
		function updateReportManually(id){
			
			var i=$('#'+id).attr('index');
			if($('#'+id).is(':checked')){
				var x=confirm ("Updating Supplier Commission Manually");
				if(x==true){
					window.location="/hoveyenergy/admin/uploadreportmanually.do?id="+i;
				}
			}
			
			
		}
		
		
		
		
		
		
	</script>
	
	<style>
			
		
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	 
	
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Supplier Reports Updated to Pipeline Summary</div>
		 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	 
	  </div>
	  
	  
			  <div style="margin: 0 auto;display: inline-block;background-color: #DFDFDF;width: 1000px;margin: 10px;padding: 10px;border: 1px solid #000;border-radius: 5px;">
					<c:if test="${not empty updatedReports}">
					
					 	<div style="float: left;font-size: 14px;font-weight: bold;margin-bottom: 10px;">
					 		Reports Updated Successfully to Pipeline Orders:
					    </div>
						<div class="CSSTableGenerator" style="margin: 0 auto;border: none;clear: both;" >		
				                <table>
				                    <tr>
				                    	<td></td>
				                       
				                        <td>Account #</td>
				                        <td>Contract Start Date</td>   
				                    	<td>Customer</td>                    
				                   		<td>Commission Paid</td>
				                   		<td>Paid Date</td>
				                   		
				                   		                		
				                    </tr>
				                     <c:set var="i" value="1" />
				                    <c:forEach var="report" items="${updatedReports}">
							   		 <tr>
							   		 		<td>${i }</td>
							   		 		
							   		 		<td>${report.accountNumber }</td>
							   		 		<td><fmt:formatDate value="${report.contractStartDate }" pattern="MM/dd/yyyy"  /></td> 	
							   		 		<td>${report.customerName }</td>
							   		 		<td><fmt:formatNumber currencyCode="US" groupingUsed="true" maxFractionDigits="2" minFractionDigits="2" value="${report.totalCommissionPaid }" /> </td>
							   		 		<td>${report.datePaid }</td>
							   		 		 				   		 		
							   		 		
								        </tr>
								         <c:set var="i" value="${i+1 }" />
						   		 </c:forEach>
				                </table>
				            </div>
				           
				            
				   	</c:if>
			   <c:if test="${empty updatedReports}">
			   		 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
				  							No Orders in Pipeline Updated with Supplier Reports
				 
				  </div>
			   </c:if>
		       
			</div>	
			
			
			
			 <div style="margin: 0 auto;display: inline-block;background-color: #FFFFFF;width: 1000px;margin: 10px;padding: 10px;border: 1px solid #000;border-radius: 5px;">
					<c:if test="${not empty notUpdatedReports}">
					
					 	<div style="float: left;font-size: 14px;font-weight: bold;margin-bottom: 10px;">
					 		Reports Not Updated to Pipeline
					    </div>
						<div class="CSSTableGenerator" style="margin: 0 auto;border: none;clear: both;" >		
				                <table >
				                   <tr>
				                    	<td></td>
				                      
				                        <td>Account #</td>
				                        <td>Contract Start Date</td>   
				                    	<td>Customer</td>                    
				                   		<td>Commission Paid</td>
				                   		<td>Paid Date</td>
				                   		<td>Update to Pipeline Manually ?</td>
				                   		                       		
				                    </tr>
				                     <c:set var="j" value="1" />
				                    <c:forEach var="report" items="${notUpdatedReports}">
							   		 <tr>
							   		 		<td>${j }</td>							   		 		
							   		 		<td>${report.accountNumber }</td>
							   		 		<td><fmt:formatDate value="${report.contractStartDate }" pattern="MM/dd/yyyy"  /></td> 	
							   		 		<td>${report.customerName }</td>
							   		 		<td><fmt:formatNumber currencyCode="US" groupingUsed="true" maxFractionDigits="2" minFractionDigits="2" value="${report.totalCommissionPaid }" /> </td>
							   		 		<td> <fmt:formatDate value="${report.datePaid }"  pattern="MM/dd/yyyy"  /> </td>
							   		 		<td>
							   		 		
							   		 			<input type="checkbox" class="reports" id="update${report.reportId}" index="${report.reportId}"  />
							   		 		
							   		 		</td> 				   		 		
							   		 		
								        </tr>
								         <c:set var="j" value="${j+1 }" />
								      </c:forEach>
				                </table>
				            </div>
				           
				            
				   	</c:if>
			   <c:if test="${empty notUpdatedReports}">
			   		 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
				  							Successfully Updated All Supplier Reports
				 
				  </div>
			   </c:if>
		       
		       <a href="#" class="btn-update btn" style="width: auto;float: right;margin-top: 25px;" id="manualUpdate">Update Reports Manually</a>
		       
			</div>	
			
			
			<div style="text-align: center;margin-top: 50px;clear: both;">									
										<a href="/hoveyenergy/admin/addsupplier.do" class="btn-success btn" style="width: auto;">Goto Pipeline</a>
													<a href="/hoveyenergy/admin/addsupplier.do" class="btn-undo btn" style="width: auto;">Home</a>										
		   </div>
			
	</div>
 
 </body>
</html>