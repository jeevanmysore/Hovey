<!DOCTYPE html>
<html>
<head>

	<title>Add or Remove States</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
	$(document).ready(function() {
	    $("table tr:nth-child(even)").css('background-color', '#edeff0');
	    
	});
	</script>
</head>



<body class="firefox">
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;height: 16px">
	  	${message}
	  
	  </div>
		
		<div class="title">Add or Remove States</div>
		<div style="width: 60%;text-align: center;margin: 0px auto;">
		<div style="text-align: center;float:left;" >
				
				<h4>Add State</h4>
				<form method="post" action="./states.do">
					<input type="text" style="width:175px;" is="state"  name="state" required="required"/><br>	
					<button type="submit" class="btn btn-update"  style="margin-top: 5px;"> Add State</button>
				</form>
			</div>
	
		
		<div class="CSSTableGenerator"  style="width: 300px;margin: 10px auto;">
                <table >
                    <tr>
                       <td>State</td>
				        <td>Delete ?</td>      
				       
				        
				    	
                    </tr>
                    <c:forEach var="state" items="${states}">
			   		 <tr>
				         <td>${state.state }</td>
				        
				        <td><a href="/hoveyenergy/admin/deletestate.do?id=${state.id}"><img src="/hoveyenergy/images/delete.png" width="35px" height="25px" title="Delete State"  class="icon"/></td>
			   		 </tr>
		   		 </c:forEach>
                </table>
            </div>
       </div>
			
	</div>
   
 </body>
</html>