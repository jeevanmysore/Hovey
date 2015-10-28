<!DOCTYPE html>
<html>
<head>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<title>Edit Agent Commission</title>
	
	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');    
		    $("#fromDate").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
					  var maxDate = $(this).datepicker('getDate');
			          $("#toDate").datepicker("option","minDate", selected)	;	
			          maxDate.setDate(maxDate.getDate() + 6);
			          $("#toDate").datepicker("option","maxDate", maxDate)	;	
			        }
			});
		    
		    $("#toDate").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
					 var minDate = $(this).datepicker('getDate');
			          $("#fromDate").datepicker("option","maxDate", selected);
			          minDate.setDate(minDate.getDate() - 6);
			          $("#fromDate").datepicker("option","minDate", minDate)	;	
			        }
			}); 
		    
		});
		
		
		function validateComForm(){
			var fromDate=$('#fromDate').val();
			var toDate=$('#toDate').val();
			
			var a=true;var b=true;
			if(fromDate==""){
				alert("Please Select Start Date");
				a=false;
			}
			else{
				a=true;
			}
			
			if(toDate==""){
				alert("Please Select End Date");
				b=false;
			}
			else{
				b=true;
			}
			
		 	if(a==true && b== true){
				return true;
			}
			else{
				return false;
			}
			 
			
		}
	</script>
	
	<style>
		a:ACTIVE,a:VISITED,a:FOCUS{
			color: blue;
		}	
		a:HOVER {
			color:purple;
		}
		
		
		
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="../admin/header.jsp" %>
   
   
	<div id="content">	
				<div class="title"></div>	
				<br />
			 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;margin-bottom: 10px;">
	  			<c:if test="${not empty param.message }">
	  				<c:out value="${param.message }"></c:out>
	  			</c:if>
	  	</div>
	 
      <div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" >
	  			<h1>Edit Agent Commissions</h1>	  		
	  		</div>
	  		 
			 <form class="form-horizontal1 form-validate1" method="post" action="/hoveyenergy/admin/editagentcommission.do" onsubmit="return validateComForm();" >	 			<br />
						
						
						<div  class="range">
							<div class="title">Select Agent and Week </div>
							<div style="display: inline-block;">
									<div class="fromDate">
										<label style="float: left;padding-top: 10px;">Agent:</label>	
										<select  style="width:135px;height: 25px;margin-top: 5px;" name="agent" >
											<option value="">--------</option>
											<c:forEach var="agent" items="${agents}">
												<option value="${agent.agentNumber}" >${agent.firstName} ${agent.lastName}</option>
											</c:forEach>
										</select>	
									</div>
								  <div class="fromDate" >
										      <label style="float: left;padding-top: 10px;">Enter Week:</label>	
										      	<select name="week" style="width:80px; height: 25px;margin-top: 5px;">
										      		<option value="">--select--</option>
										      		<c:forEach var="i" begin="1" end="53" step="1">
										      			<option value="${i }">${i }</option>
										      		</c:forEach>
										      	</select>
											
								   </div>
								   
								     <div class="fromDate" >
										      <label style="float: left;padding-top: 10px;">Enter Year:</label>	
										      	<select name="year" style="width:80px; height: 25px;margin-top: 5px;">
										      		<option value="">--select--</option>
										      		<c:forEach var="i" begin="2012" end="2032" step="1">
										      			<option value="${i }">${i }</option>
										      		</c:forEach>
										      	</select>
									   </div>
								   
									
							</div>
								<div style="margin-top: 10px;clear: both;">
									<button type="submit" style="clear: both;margin: 5px auto;margin-top: 10px;" class="btn btn-success">Get  Commissions</button>
									<a href="/hoveyenergy/admin/agentcommissions.do" class="btn btn-update" style="clear: both;margin: 5px auto;margin-top: 10px;" >Back</a>
								</div>
						</div>
									
			 </form>              
                
        </div>
    </div> 
	  
		 
		
		
	
	</div>
   
 </body>
</html>