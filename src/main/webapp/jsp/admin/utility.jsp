<!DOCTYPE html>
<html>
<head>
	<title>Manage Utility</title>
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
	
	  <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	 
	  </div>
	 
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Manage Utility</div>
		
		<div class="CSSTableGenerator" style="width: 500px;margin: 0 auto;" >
                <table>
                    <tr>
                        <td>Utility Name</td>
                        <td>Length of Account#</td>                    	               
                   		<td>Edit</td>
                   		<td> Enable/Disable</td>                   		
                   		<!-- <td>Edit</td> -->
                    </tr>
                    <c:forEach var="util" items="${utils}">
			   		 <tr>
			   		 		<td> ${util.utility }</td>
			   		 		<td><fmt:formatNumber value="${util.accountLength }" /></td> 		 		
			   		 		<td><a  href="/hoveyenergy/admin/editutility.do?id=${util.id}"><img src="/hoveyenergy/images/edit-icon.png"  title="Edit Supplier" class="icon"/></a></td>
			   		 		<c:choose>
			   		 				<c:when test="${util.isEnabled eq true }">
			   		 							<td><a class="cust" href="/hoveyenergy/admin/enableutility.do?id=${util.id}&status=disable">Disable</a></td>			   		 					
			   		 				</c:when>
			   		 				<c:otherwise>
			   		 				
			   		 					<td><a class="cust" href="/hoveyenergy/admin/enableutility.do?id=${util.id}&status=enable">Enable</a></td>	
			   		 				</c:otherwise>
			   		 		
			   		 		</c:choose>
			   		 		
			   		 				   		 		
				        </tr>
		   		 </c:forEach>
                </table>
            </div>
       
		<div style="text-align: center;margin-top: 50px;clear: both;">
									
										<a href="/hoveyenergy/admin/addutility.do"  class="btn-update btn" style="width: auto;">Add Utility</a>
										
										
		</div>
	
	</div>
   
 </body>
</html>