<!DOCTYPE html>
<html>
<head>
	<title>Supplier Files</title>
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
	
	  <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	 
	  </div>
	 
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Supplier Report Files</div>
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
		<div class="CSSTableGenerator" style="width: 600px; margin: 0 auto;">
                <table>
                    <tr>
                        <td>File Name</td>                   
                   		<td>Edit</td>
                   		<td>Delete</td>
                   		
                   		<!-- <td>Edit</td> -->
                    </tr>
                    <c:forEach var="file" items="${files}">
			   		 <tr>
			   		 		<td> ${file.fileName }</td>
			   		 		
			   		 		
			   		 		<td><a href="#"><img src="/hoveyenergy/images/edit_mapping.png"  title="Edit Supplier" class="icon"/></a></td>
			   		 		
			   		 		<td><a href="#"><img src="/hoveyenergy/images/delete.png"  title="Edit Supplier" class="icon"/></a></td>
			   		 		
			   		 		
				        </tr>
		   		 </c:forEach>
                </table>
            </div>
       			
			<div style="text-align: center;margin-top: 50px;clear: both;">
									
										<button type="submit"  class="btn-update btn" style="width: auto;"><a href="./addmapping.do">Upload New File</a></button>
										
										
									</div>
	
	</div>
   
 </body>
</html>