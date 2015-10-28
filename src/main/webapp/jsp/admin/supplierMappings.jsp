<!DOCTYPE html>
<html>
<head>
	<title>Supplier Mappings</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
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
	
	  
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Supplier Report File Mappings</div>
		<div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	 
	  </div>
	 
	<c:if test="${empty message }">
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
                   		<td>Edit</td>
                   		
                   		<!-- <td>Edit</td> -->
                    </tr>
                    <c:forEach var="mapping" items="${mappings}">
			   		 <tr>
			   		 		<td> ${mapping.supplierName.supplierName }</td>
			   		 		<td>${mapping.fieldForCustomer}</td>
			   		 		<td>${mapping.fieldForAccount}</td>
			   		 		<td>${mapping.fieldForStartDate}</td>
			   		 		<td>${mapping.fieldForEndDate}</td>
			   		 		<td>${mapping.fieldForTerm}</td>
			   		 		<td>${mapping.fieldForKwh}</td>
			   		 		<td>${mapping.fieldForRate}</td>
			   		 		<td>${mapping.fieldForCommissionRate}</td>
			   		 		<td>${mapping.fieldForPaidDate}</td>
			   		 		
			   		 		<td>${mapping.fieldForTotalCommissionPaid}</td>
			   		 		
			   		 		
			   		 		<td><a href="/hoveyenergy/admin/editmapping.do?supplier=${mapping.supplierName.supplierName}"><img src="/hoveyenergy/images/edit_mapping.png"  title="Edit Supplier" class="icon"/></a></td>
			   		 		
			   		 		
				        </tr>
		   		 </c:forEach>
                </table>
            </div>
           </c:if>
       			
			<div style="text-align: center;margin-top: 50px;clear: both;">
									
									<a href="./addmapping.do"  class="btn-update btn" style="width: auto;">Create New Supplier Mapping</a>
										
										
									</div>
	
	</div>
   
 </body>
</html>