<!DOCTYPE html>
<html>
<head>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<title>Supplier Report</title>
	
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
		    
		    
		    $("#fromDate4").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
			          $("#toDate4").datepicker("option","minDate", selected)
			        }
			});
		    
		    $("#toDate4").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				 onSelect: function(selected) {
			          $("#fromDate4").datepicker("option","maxDate", selected)
			        }
			}); 
		    
		    
 			$('#excel').click(function(){
				    	
		    	$('<input type="hidden" name="export" value="excel" id="export" />').appendTo($('#supForm'));    	
		    	$('#supForm').submit();
		    	$('#export').remove();
		    });
 			
 			
 			$('#excel2').click(function(){
		    	
		    	$('<input type="hidden" name="export" value="excel" id="export2" />').appendTo($('#supForm2'));    	
		    	$('#supForm2').submit();
		    	$('#export2').remove();
		    });
		    
 			

 			$('#excel3').click(function(){
		    	
		    	$('<input type="hidden" name="export" value="excel" id="export3" />').appendTo($('#supForm3'));    	
		    	$('#supForm3').submit();
		    	$('#export3').remove();
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
	  			<h1>Supplier Reports</h1>	  		
	  		</div>
	  		 
			 <form class="form-horizontal1 form-validate1" method="post" action="/hoveyenergy/reports/getsupplierreports.do" target="_blank" id="supForm">	 			<br />
						<div class="term">
							<div class="title" style="clear: both;">Weekly/Monthly Supplier Reports</div>
							<div>
								<label style="margin-right: 10px;font-size: 17px;">Last Weeks:</label>
								<a href="/hoveyenergy/reports/getsupplierreports.do?term=week" target="_blank" class="btn btn-update"> Suppliers PDF Report	</a>	
								<a href="/hoveyenergy/reports/getsupplierreports.do?term=week&export=excel" target="_blank" class="btn btn-success">Suppliers EXCEL Report	</a>	
							</div>
							<br />
							<div>
								<label style="margin-right: 10px;font-size: 17px;">Last Months:</label>
								<a href="/hoveyenergy/reports/getsupplierreports.do?term=month" target="_blank" class="btn btn-undo" style="clear: both;">Suppliers PDF Reports	</a>					
								<a href="/hoveyenergy/reports/getsupplierreports.do?term=month&export=excel" target="_blank" class="btn btn-back" style="clear: both;">Suppliers EXCEL Reports	</a>							
							</div>
						</div>
						
						<div  class="range">
							<div class="title">Supplier Reports by Date Range </div>
							<div style="display: inline-block;">
									<div class="fromDate">
										<label style="float: left;padding-top: 10px;">Supplier:</label>	
										<select  style="width:135px;height: 25px;margin-top: 5px;" name="supplier" >
											<option value="">--------</option>
											<c:forEach var="supplier" items="${suppliers}">
												<option value="${supplier.supplierName}">${supplier.supplierName }</option>
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
							  <div style="clear: both;margin-top: 10px;">
								<button type="submit" style="clear: both;margin: 5px auto;" class="btn btn-success">Generate PDF Report</button>
								<a href="#" style="clear: both;margin: 5px auto;" id="excel" class="btn btn-undo">Generate EXCEL Report</a>
							  </div>
						</div>
					 </form>   	
						
						 	
						<div  class="range">
						 <form method="post" action="/hoveyenergy/reports/getsupplierreportsofaccount.do" target="_blank"  id="supForm2" >
							<div class="title">Supplier Reports by Account </div>
							<div style="display: inline-block;">
									<div class="fromDate" style=" width:auto;">
										<label style="float: left;padding-top: 10px;">Business Name:</label>	
										<select  style="width:200px;height: 25px;margin-top: 5px;" name="businessName" >
											<option value="">--------</option>
											<c:forEach var="businessName" items="${businessNames}">
												<option value="${businessName}">${businessName }</option>
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
						
						<div  class="range">
						  <form method="post" action="/hoveyenergy/reports/getsupplierreportsnotinpipeline.do" target="_blank"  id="supForm3" >
							<div class="title">Supplier Reports Not Updated to Pipeline </div>
							<div style="display: inline-block;">
									<div class="fromDate">
										<label style="float: left;padding-top: 10px;">Supplier:</label>	
										<select  style="width:135px;height: 25px;margin-top: 5px;" name="supplier" >
											<option value="">--------</option>
											<c:forEach var="supplier" items="${suppliers}">
												<option value="${supplier.supplierName}">${supplier.supplierName }</option>
											</c:forEach>
										</select>	
									</div>
								  <div class="fromDate" >
										<label style="float: left;padding-top: 10px;">Start Date:</label>	<input type="text" id="fromDate4" name="startDate"  style="width:80px; height: 15px;"/> 
								   </div>
								   <div class="toDate">
										<label style="float: left;padding-top: 10px;">End Date:</label>	<input type="text" id="toDate4" name="endDate"  style="width:80px;height: 15px; "/>	
									</div>
									
							</div>
							 <div style="clear: both;margin-top: 10px;">
								<button type="submit" style="clear: both;margin: 5px auto;" class="btn btn-success">Generate PDF Report</button>
								<a href="#" style="clear: both;margin: 5px auto;" id="excel3" class="btn btn-undo">Generate EXCEL Report</a>
							</div>
						</form>
						</div>			
			       
                
        </div>
    </div> 
	  
		 
		
		
	
	</div>
   
 </body>
</html>