<!DOCTYPE html>
<html>
<head>

		<link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">

	<title>Delete Customer</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#eee');
		    
		});
	</script>
	
	
	<style>
		select{
			height: 25px;
		}
		
		.orderscount{		
			clear:both;
			text-align: center;
			font-weight: bold;			
			background-color: #ffffff;
			border: 1px solid #ccc;
			border-radius: 5px;
			 behavior: url(js/js/PIE.htc);
			position:relative;
			margin-top: 20px;
			height: auto;
			padding-top: 10px;
			padding-bottom: 10px;
		}
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	   <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	  
	  </div>
	  <br />
	  <br />
		
	  <div id="wrapper">
		
		
		<div class="data-input-form">
			
			<div class="sub-title" style="">
	  			<h1>Delete Customer</h1>	  		
	  		</div>		
			
			<div class="orderscount" style="margin-top: 50px;">
				There are <span style="color:green"> "${orders}"</span> orders  under this Customer.
			</div>     
         	<br />
          <div style="width:50%;">
         		<label style="float: left; font-size: 20px;text-decoration: underline;color:red;">Select Action:</label>
          </div>
           <br />
          <c:if test="${orders gt 0 }">     
         	<div class="orderscount" >
         	 <form method="post" action="/hoveyenergy/admin/changecustomerorders.do">
         		<label>Transfer Orders to another Customer:</label>
         		<input type="hidden" name="id" value="${id }"> 
         		<select name="taxId" style="height: 25px;clear: both;width:200px;float:none;">
         			<c:forEach var="customer" items="${customers }">
					<option value="${customer.taxId}">${customer.taxId }</option>
				</c:forEach>         			
         		</select><br/>
         		<button type="submit" class="btn btn-update	" style="margin-top: 10px;">Transfer Orders</button>
         	  </form>
        	 </div>         
         </c:if>
         <br />
        <div class="orderscount">
           <form method="post" action="/hoveyenergy/admin/deletecustomer.do">
        		<label>Delete Customer</label>
        		<input type="hidden" name="id" value="${id }"> <br />
        		<button type="submit" class="btn btn-undo" style="margin-top: 10px;">Delete</button>
        	</form>	
        </div>
        <br />
        <div class="orderscount" style="margin-bottom: 20px;">
        	<button type="button" class="btn btn-back"><a href="/hoveyenergy/admin/customer.do">Cancel Delete</a></button>
        </div>
       </div>
       </div>  
	</div>
   
 </body>
</html>