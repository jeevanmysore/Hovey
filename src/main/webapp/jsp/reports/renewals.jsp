<!DOCTYPE html>
<html>
<head>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<title>Renewal Report</title>
	
	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');    
		    $("#fromDate").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
			          $("#toDate").datepicker("option","minDate", selected);
			        }
			});
		    
		    $("#toDate").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
			          $("#fromDate").datepicker("option","maxDate", selected);
			        }
			}); 
		    
 			 $('#excel').click(function(){
		    	
		    	$('<input type="hidden" name="export" value="excel" id="export" />').appendTo($('#renForm'));    	
		    	$('#renForm').submit();
		    	$('#export').remove();
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
	  			<h1>Renewal Reports</h1>	  		
	  		</div>
	  		 
			 <form class="form-horizontal1 form-validate1" method="get" action="/hoveyenergy/reports/getrenewalreports.do" target="_blank"  id="renForm">	 			<br />
						
						
						<div  class="range">
							<div class="title">Select Range</div>
							<div style="display: inline-block;width: 500px;">
								  <div class="fromDate" style="width: 280px;">
								  	</div>
									<div class="fromDate" >
										<label style="float: left;padding-top: 10px;">Start Date:</label>
										<input type="text" id="fromDate" name="startDate"  style="width:80px; height: 15px;"/> 
								   </div>
								   <div class="toDate">
										<label style="float: left;padding-top: 10px;">End Date:</label>
										<input type="text" id="toDate" name="endDate"  style="width:80px;height: 15px; "/>	
									</div>
								   
							</div>
							<div style="clear: both;margin-top: 10px;">
								<button type="submit" style="clear: both;margin: 5px auto;" class="btn btn-success">Generate Report</button>
								<a href="#" style="clear: both;margin: 5px auto;" id="excel" class="btn btn-undo">Generate EXCEL Report</a>
							</div>
						</div>	
			 </form>              
                
        </div>
    </div> 
	  
		 
		
		
	
	</div>
   
 </body>
</html>