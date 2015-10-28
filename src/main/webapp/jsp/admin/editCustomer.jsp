<!DOCTYPE html>
<html>
<head>

		 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
		 <script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="/hoveyenergy/js/customers.js"></script>	
	<title>Edit Customer</title>
	 
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#eee');
		    
		});
	</script>
	<script type="text/javascript">
	
		
	</script>
	
	<style>
		
	

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
	  		<h1>Edit Customer</h1>
	  		
	  		</div>
	  		
			<form:form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/editcustomer.do" commandName="customer" onsubmit="return validateCustomerForm()">
									<div class="control-group" style="margin-top: 60px;">
										<label class="control-label" for="textfield">First Name:<span class="error_msg">*</span></label>										    
											<input type="text" name="firstName" value="${customer.firstName}" class="input-xlarge" id="cname" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="cname_error"></span>										
									</div>
									<div class="control-group">
										<label class="control-label" for="emailfield">Last Name:<span class="error_msg">*</span></label>
										
											<input type="text" name="lastName" value="${customer.lastName}" class="input-xlarge" id="cname2" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="cname2_error"></span>
										
									</div>
									<div class="control-group" style="height: 13px;">
										<label class="control-label" for="pwfield">Title:<span class="error_msg">*</span></label>
										
											<input type="text" name="title" value="${customer.title}" class="input-xlarge" id="ctitle" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;margin: 0px;" id="ctitle_error"></span>
										
									</div>
									<div class="control-group" >
										<label class="control-label" for="pwfield">Phone Number:<span class="error_msg">*</span></label>
										
											<input type="text" name="phoneNo" value="${customer.phoneNo}" class="input-xlarge" id="cphone" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="cphone_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="confirmfield">Service Type:<span class="error_msg">*</span></label>
										
											<select  name="type" class="" id="service" style="height: 25px;margin-bottom: 33px;margin-left: 0px;" >
												<option value="commercial" ${customer.type eq 'commercial' ? 'selected' : '' }>Commercial</option>
												<option value="residential"${customer.type eq 'residential' ? 'selected' : '' }>Residential</option>											
											</select>											
										
									</div>
									<div class="control-group">
										<label class="control-label" for="pwfield">Email:</label>
										
											<input type="text" name="email" value="${customer.email}" class="input-xlarge" id="cmail" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="cmail_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="pwfield">Fax:</label>
										
											<input type="text" name="faxNo" value="${customer.faxNo}" class="input-xlarge" id="cfax" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="cfax_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="pwfield">Tax ID:</label>
										
											<input type="text" name="taxId" value="${customer.taxId}" class="input-xlarge" id="ctax" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="ctax_error"></span>
										
									</div>
									
									
									
									
									<div class="control-group">
										<label class="control-label" for="confirmfield">Tax Exempt:</label>
										
											<select  name="taxExempt"  class="input-xlarge" id="taxExempt" style="height: 25px;margin-bottom: 50px;margin-left: 0px;" >
												<option value="yes" ${customer.taxExempt eq true ? 'selected' : '' }>Yes</option>
												<option value="no"${customer.taxExempt eq false ? 'selected' : '' }>No</option>			
												<option value="na"${customer.taxExempt eq null ? 'selected' : '' }>N/A</option>										
											</select>											
										
									</div>
									<input type="hidden" value="${customer.customerId }" name="customerId" />
									
									<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										<button type="submit"  class="btn-update btn">Update</button>
										
										<a href="/hoveyenergy/admin/editcustomer.do?id=${customer.customerId}" style="margin-left: 15px;" class="btn btn-undo">Undo</a></button>
										<a class="btn btn-back" style="margin-left: 15px;" href="/hoveyenergy/admin/customer.do">Back</a>
									</div>
				</form:form>
		       
          
                
                
            </div>
       
		
		</div>
	</div>
   
 </body>
</html>