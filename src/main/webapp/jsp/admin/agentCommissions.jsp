<!DOCTYPE html>
<html>
<head>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<title>Agent Commission</title>
	
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
			          maxDate.setDate(maxDate.getDate() +4);
			          $('#toDate').datepicker('setDate', maxDate);
			         /*  maxDate.setDate(maxDate.getDate() + 6);
			          $("#toDate").datepicker("option","maxDate", maxDate)	;	 */
			        }
			});
		    
		    $("#toDate").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
					 var minDate = $(this).datepicker('getDate');
			          $("#fromDate").datepicker("option","maxDate", selected);
			         
			          /* minDate.setDate(minDate.getDate() - 6);
			          $("#fromDate").datepicker("option","minDate", minDate)	;	 */
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
	  			<h1>Agent Commissions</h1>	  		
	  		</div>
	  		 
			 <form class="form-horizontal1 form-validate1" method="get" action="/hoveyenergy/admin/getagentcommissions.do" onsubmit="return validateComForm();" >	 	<br />
						<div  class="range">
							<div class="title">Select Agent and Date Range </div>
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
										<label style="float: left;padding-top: 10px;">Start Date:</label>	<input type="text" id="fromDate" name="startDate"  style="width:80px; height: 15px;"/> 
								   </div>
								   <div class="toDate">
										<label style="float: left;padding-top: 10px;">End Date:</label>	<input type="text" id="toDate" name="endDate"  style="width:80px;height: 15px; "/>	
									</div>
									
							</div>
								<div style="display:block;margin-top: 10px;">
									<button type="submit" style="clear: both;margin: 5px auto;margin-top: 10px;" class="btn btn-success">Get Agent Commissions</button>
									<a href="/hoveyenergy/admin/editagentcommission.do" class="btn btn-update">Paid Agent Commissions</a>
								</div>
						</div>
									
			 </form>              
                
        </div>
    </div> 
	  
		 
		
		
	
	</div>
   
 </body>
</html>