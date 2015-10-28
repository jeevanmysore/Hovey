<!DOCTYPE html>
<html>
<head>

		 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
		 <script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="/hoveyenergy/js/agents.js"></script>	
	<title>Edit Profile</title>
	 
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
	  	${message }
	  
	  </div>
	  <br />
		
		<!-- <div class="title">Edit Customer</div> -->
		<br />
		
	  <div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" style="">
	  		<h1>Edit Profile</h1>
	  		
	  		</div>
	  		
			<form:form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/editprofile.do" commandName="admin" onsubmit="return validateAgentForm()">
									
									<input type="hidden" name="username" value="${admin.username }" id="username" />
									<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">First Name:<span class="error_msg">*</span></label>										    
											<input type="text" name="firstName" value="${admin.firstName }" class="input-xlarge" id="fname"  >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="fname_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="textfield">Last Name:<span class="error_msg">*</span></label>										    
											<input type="text" name="lastName" value="${admin.lastName }" class="input-xlarge" id="lname"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="lname_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="textfield">City:</label>										    
											<input type="text" name="city" value="${admin.city }" class="input-xlarge" id="city"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="city_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="textfield">State:</label>										    
											<input type="text" name="state" value="${admin.state }" class="input-xlarge" id="state"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="state_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="textfield">Zip Code:</label>										    
											<input type="text" name="zipcode" value="${admin.zipcode }" class="input-xlarge" id="zip"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="zipCode_error"></span>										
									</div>
									<div class="control-group">
										<label class="control-label" for="textfield">Email:<span class="error_msg">*</span></label>										    
											<input type="text" name="email" value="${admin.email }" class="input-xlarge" id="mail" style="text-transform: none;"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="mail_error"></span>										
									</div>
																		    
											<input type="hidden" name="agentNumber" value="000" class="input-xlarge" id="agentId"   >
																					
									
									
									
									
									
									<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										<button type="submit"  class="btn-update btn" style="width: 100px;">Update</button>
										
										
									
										<a href="/hoveyenergy/home.do"  class="btn btn-back"  style="margin-left: 10px;">Home</a>
									</div>
				</form:form>
		       
          
                
                
            </div>
       
		
		</div>
	</div>
	
	
   
 </body>
</html>