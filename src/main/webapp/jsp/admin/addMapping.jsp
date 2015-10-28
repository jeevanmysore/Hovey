<!DOCTYPE html>
<html>
<head>

		 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
		 <script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="/hoveyenergy/js/suppliers.js"></script>	
	<title>Create Supplier Mapping</title>
	 
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#eee');
		    
		});
	</script>
	<script type="text/javascript">
	
		
	</script>
	
	<style>
		
	input[type="text"]{
			text-transform: none !important;
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
	  		<h1>Create Supplier Reports File Mapping</h1>
	  		
	  		</div>
	  		
			<form:form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/addmapping.do" commandName="mapping" onsubmit="return validateSupplierMappingForm()">
			
									<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Supplier Name:<span class="error_msg">*</span></label>										    
											<select name="supplierName.supplierName" class="input-xlarge" id="sname" style="height: 30px;" >
												<c:forEach var="sup" items="${suppliers}">
													<option value="${sup.supplierName}">${sup.supplierName }</option>
												</c:forEach>
											
											</select>
																				
									</div>
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Customer Name:<span class="error_msg">*</span></label>
										
											<input type="text" name="fieldForCustomer"  class="input-xlarge" id="cust" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="cust_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Account#:<span class="error_msg">*</span></label>
										
											<input type="text" name="fieldForAccount"  class="input-xlarge" id="acc" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="acc_error"></span>
										
									</div>
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Start Date:<span class="error_msg">*</span></label>
										
											<input type="text" name="fieldForStartDate"  class="input-xlarge" id="sdate" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="sdate_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for End Date:</label>
										
											<input type="text" name="fieldForEndDate"  class="input-xlarge" id="edate" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="edate_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Term:<span class="error_msg">*</span></label>
										
											<input type="text" name="fieldForTerm"  class="input-xlarge" id="term" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="term_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Annual kWh:<span class="error_msg">*</span></label>
										
											<input type="text" name="fieldForKwh"  class="input-xlarge" id="kwh" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="kwh_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Rate:<span class="error_msg"></span></label>
										
											<input type="text" name="fieldForRate"  class="input-xlarge" id="rate" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="rate_error"></span>
										
									</div>
									
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Commission Rate:<span class="error_msg">*</span></label>
										
											<input type="text" name="fieldForCommissionRate"  class="input-xlarge" id="crate" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="crate_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Agent ID:<span class="error_msg"></span></label>
										
											<input type="text" name="fieldForAgentId"  class="input-xlarge" id="agent" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="agent_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Paid Date:<span class="error_msg">*</span></label>
										
											<input type="text" name="fieldForPaidDate"  class="input-xlarge" id="pdate" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="pdate_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Field for Total Commission:<span class="error_msg">*</span></label>
										
											<input type="text" name="fieldForTotalCommissionPaid"  class="input-xlarge" id="com" >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="com_error"></span>
										
									</div>
									
									
									<div style="text-align: center;margin-bottom: 20px;clear: both;">
									
										<button type="submit"  class="btn-update btn" style="width: 100px;">Save</button>
										
										<%-- <button class="btn btn-undo" type="button" style="margin-left: 10px;"><a href="./editcustomer.do?id=${customer.customerId}">Undo</a></button>
										<button class="btn btn-back" type="button" style="margin-left: 10px;"><a href="./customer.do">Back</a></button> --%>
									</div>
				</form:form>
		       
          
                
                
            </div>
            
            <div style="width:60%;text-align: center;padding-top: 20px;margin:0 auto;">
            	Note:- Please Enter The Field Name(headings) of the Supplier Excel Report files against the appropriate Item. This information will be helpful in loading data from suppliers, where each supplier has their Unique reports. 
            
            </div>
       
		
		</div>
	</div>
   
 </body>
</html>