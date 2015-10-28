<!DOCTYPE html>
<html>

<head>
<link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
<title>Reset Password</title>

<style>
	 	#feedback{
	 		display: inline;
			position: relative;
			left: -500px;
			color: green;
			font-size: 20px;
	 	}
	 </style>
	  
	  <script type="text/javascript" src="/hoveyenergy/js/js/login.js"></script> 
	   <script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
	$(document).ready(function() {
	    $("table tr:nth-child(even)").css('background-color', '#e5e5e5');
	    
	});
	</script>
</head>
<%@ include file="header.jsp" %>

<body class="firefox" >
<div id="content">
		<br/>
	  <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;">
	  		${message}
	  
	  </div>
	 <div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" style="">
	  		<h1>Reset Password</h1>
	  		
	  		</div>
	  		
			<form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/resetpassword.do" onSubmit="return validatePass()">
			
									<input type="hidden" name="user" value="${user}" />
									<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Password:<span class="error_msg">*</span></label>										    
											<input type="password" name="password"  id="pass"  class="input-xlarge"  >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;margin: 0px;" id="Passwd"></span>										
									</div>
									<div class="control-group">
										<label class="control-label" for="emailfield">Confirm Password <span class="error_msg">*</span></label>
										
											<input type="password" name="cpassword"  id="cpass"  class="input-xlarge"  >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="cname2_error"></span>
										
									</div>
									
									
									
									
									
									
									<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										<button type="submit"  class="btn-update btn">Reset Password</button>
										
										<a href="/hoveyenergy/home.do" class="btn btn-undo" type="button" style="margin-left: 10px;">Cancel</a>
										
									</div>
				</form>
		       
          
                
                
            </div>
       
		
		</div> 
	  
	  
	  
	  
	  
	  <c:if test="${not empty status }">
			<script type="text/javascript">
				alert('Password Changed Successfully, Please Login with New Password to Continue');
				location.href="/hoveyenergy/logout.do";
			</script>
	</c:if>
	  
	  
	 
	 

</div>
</body>
</html>