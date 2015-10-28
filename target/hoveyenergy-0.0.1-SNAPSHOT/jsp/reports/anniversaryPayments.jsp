<!DOCTYPE html>
<html>
<head>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<title>AnniversaryPayments Reports</title>
	
	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');    
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
	  			<h1>AnniversaryPayments Reports</h1>	  		
	  		</div>
	  		 
			 <form class="form-horizontal1 form-validate1" method="post" action="/hoveyenergy/reports/getanniversaryreports.do" target="_blank"  >
			 <div  class="range">
							<div class="title"> Anniversary Payments </div>
							<div style="display: inline-block;">
									
								 
									
							</div>
							 <div style="clear: both;margin-top: 10px;">
								<a href="/hoveyenergy/reports/getanniversarypayments.do?output=pdf" style="clear: both;margin: 5px auto;" id="pdf"  class="btn btn-success">Click Here to Generate PDF Report</a>
								<a href="/hoveyenergy/reports/getanniversarypayments.do?output=excel" style="clear: both;margin: 5px auto;" id="excel" class="btn btn-undo">Click Here to Generate EXCEL Report</a>
							</div>
						</div>
						 </form>
						
						 <form class="form-horizontal1 form-validate1" method="post" action="/hoveyenergy/reports/getanniversaryreports.do" target="_blank"  >
						  <div  class="range">
			 				<div class="title"> Anniversary Payments With Payment Due</div>
							<div style="display: inline-block;">
									
								 
							</div>
							<a href="/hoveyenergy/reports/getanniversarypaymentsdues.do?output=pdf" style="clear: both;margin: 5px auto;" id="pdf"  class="btn btn-success">Click Here to Generate PDF Report</a>
								<a href="/hoveyenergy/reports/getanniversarypaymentsdues.do?output=excel" style="clear: both;margin: 5px auto;" id="excel" class="btn btn-undo">Click Here to Generate EXCEL Report</a>
							
							</div>
						 </form> 
						
						  </div>
    </div> 
	  		 			
	</div>
   
 </body>
</html>      