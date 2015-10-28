<!DOCTYPE html>
<html>
<head>

		 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
		 <script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="/hoveyenergy/js/suppliers.js"></script>	
	<title>Create Supplier</title>
	 
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#eee');
		    
		});
	</script>
	<script type="text/javascript">
	
		
	</script>
	
	<style>
		
		input[type="text"]{
			text-transform: none;
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
		
		<!-- <div class="title">Edit Customer</div> -->
		<br />
		
	  <div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" style="">
	  		<h1>Create Supplier</h1>
	  		
	  		</div>
	  		
			<form:form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/addsupplier.do" commandName="supplier" onsubmit="return validateSupplierForm()">
			
									<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Supplier Name:<span class="error_msg">*</span></label>										    
											<input type="text" name="supplierName" class="input-xlarge" id="sname" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="sname_error"></span>										
									</div>
									<div class="control-group">
										<label class="control-label" for="emailfield">Contract Commission Rate:<span class="error_msg">*</span></label>
										
											<input type="text" name="contractCommission"  class="input-xlarge" id="ccom" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="ccom_error"></span>
										
									</div>
									<div class="control-group" style="height: 13px;">
										<label class="control-label" for="pwfield">Renewal Commission Rate:<span class="error_msg">*</span></label>
										
											<input type="text" name="renewalCommission" class="input-xlarge" id="rcom" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;margin: 0px;" id="rcom_error"></span>
										
									</div>								
								 	<!--  UploadKwhFromSupplier ,added by bhagya on May1st,2014 	 -->
				 					  <div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Upload Kwh From Supplier:<span class="error_msg">*</span></label>   
											<select name="uploadKwhFromSupplier" class="input-xlarge" id="uploadkwhfromsupplier" style="height: 30px;padding: 4px;" >
											<option value="No">No</option>
											<option value="Yes">Yes</option>	
											</select>
									</div>
									
									
 								
									
									<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										<button type="submit"  class="btn-update btn" style="width: 100px;">Save</button>
										
										<%-- <button class="btn btn-undo" type="button" style="margin-left: 10px;"><a href="./editcustomer.do?id=${customer.customerId}">Undo</a></button>
										<button class="btn btn-back" type="button" style="margin-left: 10px;"><a href="./customer.do">Back</a></button> --%>
									</div>
				</form:form>
		       
          
                
                
            </div>
       
		
		</div>
	</div>
   
 </body>
</html>