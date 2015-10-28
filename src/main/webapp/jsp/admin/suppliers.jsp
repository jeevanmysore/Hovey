<!DOCTYPE html>
<html>
<head>
	<title>Manage Suppliers</title>
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
	
	 
	
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Manage Suppliers</div>
		 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	 
	  </div>
	 
	<c:if test="${empty message}">
		<div class="CSSTableGenerator" style="width: 800px;margin: 0 auto;" >
                <table>
                    <tr>
                        <td>Supplier Name</td>
                        <td>Contract Commission Rate</td>
                    	<td>Renewal Commission Rate</td>
                       	 <!-- UploadKwhFromSupplier ,added by bhagya on May1st,2014  -->
                       	<td>Upload Kwh From Supplier</td>                    
                   		<td>Edit</td>
                   		
                   		<!-- <td>Edit</td> -->
                    </tr>
                    <c:forEach var="supplier" items="${suppliers}">
			   		 <tr>
			   		 		<td> ${supplier.supplierName }</td>
			   		 		<td><fmt:formatNumber value="${supplier.contractCommission }" /></td>
			   		 		<td><fmt:formatNumber value="${supplier.renewalCommission }" /></td>
			   		 		<!-- UploadKwhFromSupplier ,added by bhagya on May1st,2014  -->
			   		 		<td> ${supplier.uploadKwhFromSupplier}</td>
			   		 		
			   		 		
			   		 		<td><a href="/hoveyenergy/admin/editsupplier.do?id=${supplier.supplierId}"><img src="/hoveyenergy/images/edit-icon.png"  title="Edit Supplier" class="icon"/></a></td>
			   		 		
			   		 		
				        </tr>
		   		 </c:forEach>
                </table>
            </div>
            
         </c:if>
       
		<div style="text-align: center;margin-top: 50px;clear: both;">
									
										<a href="/hoveyenergy/admin/addsupplier.do" class="btn-update btn" style="width: auto;">Create Supplier</a>
										
										
		</div>
	
	</div>
   
 </body>
</html>