<!DOCTYPE html>
<html>
<head>

	<title>Add or Remove ContractTypes</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
	$(document).ready(function() {
	    $("table tr:nth-child(even)").css('background-color', '#edeff0');
	    
	});

	function deleteContractType(id){
		var x=confirm("Do you want to Delete the ContractType");
		if(x==true){
		window.location="/hoveyenergy/admin/deletecontracttype.do?id="+id;
		}
	}
 	</script>
</head>



<body class="firefox">
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;height: 16px">
	  	${message}
	  
	  </div>
		
		<div class="title">Add or Remove ContractTypes</div>
		<div style="width: 60%;text-align: center;margin: 0px auto;">
		<div style="text-align: center;float:left;" >
				
				<h4>Add ContractType</h4>
				<form method="post" action="./contracttypes.do">
					<input type="text" style="width:175px;text-transform: none;" is="contractType"  name="contractType" required="required"/><br>	
					<button type="submit" class="btn btn-update"  style="margin-top: 5px;"> Add ContractType</button>
				</form>
			</div>
	
		
		<div class="CSSTableGenerator"  style="width: 300px;margin: 10px auto;">
                <table >
                    <tr>
                       <td>ContractType</td>
				        <td>Delete ?</td>      
				   </tr>
                    <c:forEach var="contractType" items="${contractTypes}">
			   		 <tr>
				         <td style="text-transform:capitalize;">${contractType.contractType }</td>
				        <td style="vertical-align: middle;"> 
						<a href="#" id="${contractType.id }" onclick="deleteContractType(this.id)"><img src="/hoveyenergy/images/delete.png" width="35px" height="25px"   class="icon"/></a>
						</td>
				    </tr>
		   		 </c:forEach>
                </table>
            </div>
       </div>
			
	</div>
   
 </body>

</html>