<!DOCTYPE html>
<html>
<head>

		 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
		 <script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="/hoveyenergy/js/agents.js"></script>	
	<title>Edit Agent</title>
	 
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
	  	
	  
	  </div>
	  <br />
		
		<!-- <div class="title">Edit Customer</div> -->
		<br />
		
	  <div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" style="">
	  		<h1>Edit Agent</h1>
	  		
	  		</div>
	  		
			<form:form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/editagent.do" commandName="agent" onsubmit="return validateAgentForm()">
									
									<input type="hidden" name="username" value="${agent.username }" id="username" />
									
									
									
									<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">First Name:<span class="error_msg">*</span></label>										    
											<input type="text" name="firstName" value="${agent.firstName }" class="input-xlarge" id="fname"  >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="fname_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="textfield">Last Name:<span class="error_msg">*</span></label>										    
											<input type="text" name="lastName" value="${agent.lastName }" class="input-xlarge" id="lname"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="lname_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="textfield">City:</label>										    
											<input type="text" name="city" value="${agent.city }" class="input-xlarge" id="city"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="city_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="textfield">State:</label>										    
											<input type="text" name="state" value="${agent.state }" class="input-xlarge" id="state"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="state_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="textfield">Zip Code:</label>										    
											<input type="text" name="zipcode" value="${agent.zipcode }" class="input-xlarge" id="zip"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="zipCode_error"></span>										
									</div>
									<div class="control-group">
										<label class="control-label" for="textfield">Email:<span class="error_msg">*</span></label>										    
											<input type="text" name="email" value="${agent.email }" class="input-xlarge" id="mail" style="text-transform: none;"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="mail_error"></span>										
									</div>
									<div class="control-group">
										<label class="control-label" for="textfield">Agent Number:<span class="error_msg">*</span></label>										    
											<input type="text" name="agentNumber" value="${agent.agentNumber}" class="input-xlarge" id="agentId"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="agent_error"></span>										
									</div>													
									
									
									<div class="control-group">
										<label class="control-label" for="textfield">Welcome Message:<span class="error_msg">*</span></label>										    
											<input type="text" name="welcomeMessage" value="${agent.welcomeMessage}" class="input-xlarge" id="message"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="message_error"></span>										
									</div>
									
									
									
									<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										<button type="submit"  class="btn-update btn" style="width: 100px;">Update</button>
										
										<a href="/hoveyenergy/admin/editagent.do?agentId=${agent.agentNumber}" class="btn btn-undo"style="margin-left: 10px;">Undo</a>
									
										<a href="/hoveyenergy/admin/agents.do"  class="btn btn-back"  style="margin-left: 10px;">Back</a>
									</div>
				</form:form>
		       
          
                
                
            </div>
       
		
		</div>
	</div>
   
 </body>
</html>