<!DOCTYPE html>
<html>
<head manifest="tetris.manifest">     
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /> 
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen, mobile" title="main" charset="utf-8">
	<link rel="stylesheet" href="css/confirm.css" type="text/css" media="screen, mobile" title="main" charset="utf-8">
    <title>HOVEY ENERGY -  Forgot Password</title> 
	<script src="http://code.jquery.com/jquery-latest.js"></script>
     <script type="text/javascript" src="js/js/login.js"></script>  
</head>		
<body>
	<div id="login_head_text" style=" padding: 15px 0 0 15px;"> 
		<h1><a href="index.html"><img src="images/logo.png" width="275" height="76" border="0"/></a></h1> 
	</div>
	<div id="login_bottom_image">
		<img src="images/top_shadow.png">
	</div>
	<div id="login_upper">
		<div id="wrapper">

	<form name="login-form" class="login-form" action="./reset.do" method="POST" onSubmit="return validatePass()">
	
		<div class="header">
				
		
		</div>
	
	
		<div class="content">
		
				<label class="gd" id="Passwd"></label>
		
		<div style="margin-top:20px;height: 16px;clear: both;">
			     <label class="titleText">Password</label> <label class="gd">*</label>
		    	
		    </div>
			<input  type="password" class="input password" style="margin-top: 3px;" id="pass"  name="password" />
			<label class="gd" id="Passwd"></label>
			<!-- <input id="text_password" name="j_password" class="input password"  value="Password" type="text" style="margin-top: 0px;" style="text-transform: none;" /> -->
			
			<input type="hidden" value="${username}" name="username" />
		
		
		
		<div style="margin-top:20px;height: 27px;">
			
			
			<label class="titleText">Confirm Password</label> <label class="gd">*</label>
	    </div>
		<input  type="password" class="input password" style=" margin-top: -8px" id="cpass"  name="cpassword"/>
		<label class="gd" id="confirmPasswd" ></label>
		
		
		
		
		
		
		
		
		
		
		 <!--  <input name="password" type="text" class="input username"  required="required" value="New Password" id="pass" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)" />
		  <div class="forgot-icon"></div>
		  <label class="gd">*</label>
		  <input name="cpassword" type="text" class="input password" required="required" value="Confirm New Password" id="cpass" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)" />
		<div class="confirm-icon"></div>	 -->				
	  </div>

		<div class="footer">
		<div class="bottom_link">
				
					<div class="login_button">
						<button class="signin_button">Reset</button>
					</div>
		  </div> <br/>
		</div>
	</form>

</div>
<div class="gradient"></div>

	</div>
</body>
</html>
<script type="text/javascript" src="js/script.js"></script>
