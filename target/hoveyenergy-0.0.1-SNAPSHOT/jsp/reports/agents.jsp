<!DOCTYPE html>
<html>
<head>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<title>Agent Reports</title>
	
	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');    
		    $("#fromDate").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
			          $("#toDate").datepicker("option","minDate", selected)
			        }
			});
		    
		    $("#toDate").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
			          $("#fromDate").datepicker("option","maxDate", selected)
			        }
			}); 
		    
		    $("#fromDate2").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
			          $("#toDate2").datepicker("option","minDate", selected)
			        }
			});
		    
		    $("#toDate2").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
			          $("#fromDate2").datepicker("option","maxDate", selected)
			        }
			}); 
		    
		    
		    $('#excel').click(function(){
		    	
		    	$('<input type="hidden" name="export" value="excel" id="export" />').appendTo($('#agentForm'));    	
		    	$('#agentForm').submit();
		    	$('#export').remove();
		    });
		    
		    
 		$('#excel2').click(function(){
		    	
		    	$('<input type="hidden" name="export" value="excel" id="export2" />').appendTo($('#agentForm2'));    	
		    	$('#agentForm2').submit();
		    	$('#export2').remove();
		    });
 		
 		
		    
		});
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
			 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  		${message}	 
	  	</div>
	 
      <div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" >
	  			<h1>Agent Reports</h1>	  		
	  		</div>
	  		 
			 <form class="form-horizontal1 form-validate1" method="post" action="/hoveyenergy/reports/getagentreports.do" target="_blank" id="agentForm" >	 			<br />
						<div class="term">
							<div class="title" style="clear: both;">Weekly/Monthly Agent Reports</div>
							<div>
								<label style="margin-right: 10px;font-size: 17px;">Last Weeks:</label>
								<a href="/hoveyenergy/reports/getagentreports.do?term=week" target="_blank" class="btn btn-update"> Agents PDF Reports	</a>	
								<a href="/hoveyenergy/reports/getagentreports.do?term=week&export=excel" target="_blank" class="btn btn-success"> Agents EXCEL Reports	</a>
						
							</div>
								<br />
							<div>
								<label style="margin-right: 10px;font-size: 17px;">Last Months:</label>	
								<a href="/hoveyenergy/reports/getagentreports.do?term=month" target="_blank" class="btn btn-undo" style="clear: both;">Agents PDF Reports	</a>
								<a href="/hoveyenergy/reports/getagentreports.do?term=month&export=excel" target="_blank" class="btn btn-back" style="clear: both;">Agents EXCEL Reports	</a>							
							</div>
						</div>
						
						<div  class="range">
							<div class="title">Agent Reports by Date Range </div>
							<div style="display: inline-block;">
									
								  <div class="fromDate" >
										<label style="float: left;padding-top: 10px;">Start Date:</label>	<input type="text" id="fromDate" name="startDate"  style="width:80px; height: 15px;"/> 
								   </div>
								   <div class="toDate">
										<label style="float: left;padding-top: 10px;">End Date:</label>	<input type="text" id="toDate" name="endDate"  style="width:80px;height: 15px; "/>	
									</div>
									
							</div>
							 <div style="clear: both;margin-top: 10px;">
								<button type="submit" style="clear: both;margin: 5px auto;" class="btn btn-success">Generate PDF Report</button>
								<a href="#" style="clear: both;margin: 5px auto;" id="excel" class="btn btn-undo">Generate EXCEL Report</a>
							</div>
						</div>
						 </form>      
						<div  class="range">
						 <form method="post" action="/hoveyenergy/reports/getagentorderreports.do" target="_blank" id="agentForm2">
							<div class="title">Reports by Agent</div>
							<div style="display: inline-block;">
									<div class="fromDate">
										<label style="float: left;padding-top: 10px;">Agent:</label>	
										<select  style="width:135px;height: 25px;margin-top: 5px;" name="agent" >
											<option value="">--------</option>
											<c:forEach var="agent" items="${agents}">
												<option value="${agent.agentNumber}">${agent.firstName} ${agent.lastName }</option>
											</c:forEach>
										</select>	
									</div>
								  <div class="fromDate" >
										<label style="float: left;padding-top: 10px;">Start Date:</label>	<input type="text" id="fromDate2" name="startDate"  style="width:80px; height: 15px;"/> 
								   </div>
								   <div class="toDate">
										<label style="float: left;padding-top: 10px;">End Date:</label>	<input type="text" id="toDate2" name="endDate"  style="width:80px;height: 15px; "/>	
									</div>
									
							</div>
								<div style="clear: both;margin-top: 10px;">
									<button type="submit" style="clear: both;margin: 5px auto;" class="btn btn-success">Generate PDF Report</button>
									<a href="#" style="clear: both;margin: 5px auto;" id="excel2" class="btn btn-undo">Generate EXCEL Report</a>
								</div>
							</form>	
						</div>
									
			        
                
        </div>
    </div> 
	  
		 
		
		
	
	</div>
   
 </body>
</html>