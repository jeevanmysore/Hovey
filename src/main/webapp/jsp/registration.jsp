<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head manifest="tetris.manifest">     
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /> 
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen, mobile" title="main" charset="utf-8">
    <title>HOVEY ENERGY - Registration Form</title>  
    <script src="http://code.jquery.com/jquery-latest.js"></script>

    <title>Registration</title>    
    <script type="text/javascript" src="js/js/register.js"></script>
	<style>@import url(http://fonts.googleapis.com/css?family=Bree+Serif);







/* Download Button (Demo Only) */
.download {
	display: block;
	position: absolute;
	float: right;
	right: 25px;
	bottom: 25px;
	padding: 5px;
	
	font-weight: bold;
	font-size: 11px;
	text-align: right;
	text-decoration: none;
	color: rgba(0,0,0,0.5);
	text-shadow: 1px 1px 0 rgba(256,256,256,0.5);
}

.download:hover {
	color: rgba(0,0,0,0.75);
	text-shadow: 1px 1px 0 rgba(256,256,256,0.5);
}

.download:focus {
	bottom: 24px;
}



.gradient {
	/* Center Positioning */
	width: 600px;
	height: 600px;
	position: fixed;
	left: 50%;
	top: 50%;
	margin-left: -300px;
	margin-top: -300px;
	z-index: -2;
	
	/* Fallback */ 
	background-image: url(http://www.demo.amitjakhu.com/login-form/images/gradient.png); 
	background-repeat: no-repeat; 
	
	/* CSS3 Gradient */
	background-image: -webkit-gradient(radial, 0% 0%, 0% 100%, from(rgba(213,246,255,1)), to(rgba(213,246,255,0)));
	background-image: -webkit-radial-gradient(50% 50%, 40% 40%, rgba(213,246,255,1), rgba(213,246,255,0));
	background-image: -moz-radial-gradient(50% 50%, 50% 50%, rgba(213,246,255,1), rgba(213,246,255,0));
	background-image: -ms-radial-gradient(50% 50%, 50% 50%, rgba(213,246,255,1), rgba(213,246,255,0));
	background-image: -o-radial-gradient(50% 50%, 50% 50%, rgba(213,246,255,1), rgba(213,246,255,0));
}

/*******************
LOGIN FORM
*******************/

.login-form {
	width: 400px;
	margin: 0 auto;
	position: relative;	
	background: #f3f3f3;
	border: 1px solid #fff;
	border-radius: 5px;	
	box-shadow: 0 1px 3px rgba(0,0,0,0.5);
	-moz-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
	-webkit-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
	 behavior: url(js/js/PIE.htc);
	position:relative;
}

/*******************
HEADER
*******************/

.login-form .header {
	padding: 0px 30px 10px;
}

.login-form .header h1 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-weight: 300;
	text-align:center;
	text-transform:uppercase;
	font-size: 18px;
	line-height:34px;
	color: #414848;
	text-shadow: 1px 1px 0 rgba(256,256,256,1.0);
	margin-bottom: 10px;
}

.login-form .header span {
	font-size: 11px;
	line-height: 16px;
	color: #678889;
	text-shadow: 1px 1px 0 rgba(256,256,256,1.0);
}

/*******************
CONTENT
*******************/

.login-form .content {
	padding: 0 30px 25px 30px;
	
}

/* Input field */
.login-form .content .input {
    z-index:1;
    behavior: url(PIE.htc);
	width: 280px;
	padding: 15px 25px;	
	font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
	font-weight: 400;
	font-size: 14px;
	color: #9d9e9e;
	text-shadow: 1px 1px 0 rgba(256,256,256,1.0);	
	background: #fff;
	border: 1px solid #fff;
	border-radius: 5px;
	-webkit-border-radius: 5px;          
    -moz-border-radius: 5px;
	box-shadow: inset 0 1px 3px rgba(0,0,0,0.50);
	-moz-box-shadow: inset 0 1px 3px rgba(0,0,0,0.50);
	-webkit-box-shadow: inset 0 1px 3px rgba(0,0,0,0.50);
	behavior: url(js/js/PIE.htc);
	position:relative;
}


#arun {
	width: 280px;
	padding: 15px 25px;	
	font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
	font-weight: 400;
	font-size: 14px;
	color: #9d9e9e;
	text-shadow: 1px 1px 0 rgba(256,256,256,1.0);	
	background: #fff;
	border: 1px solid #fff;
	border-radius: 5px;
	-webkit-border-radius: 5px;          
    -moz-border-radius: 5px;
	box-shadow: inset 0 1px 3px rgba(0,0,0,0.50);
	-moz-box-shadow: inset 0 1px 3px rgba(0,0,0,0.50);
	-webkit-box-shadow: inset 0 1px 3px rgba(0,0,0,0.50);
	 behavior: url(PIE.htc);
	position:relative;
}


/* Second input field */
.login-form .content .password, .street,.city .login-form .content .pass-icon {
	
}

.login-form .content .input:hover {
	background: #dfe9ec;
	color: #414848;
}

.login-form .content .input:focus {
	background: #dfe9ec;
	color: #414848;
	
	box-shadow: inset 0 1px 2px rgba(0,0,0,0.25);
	-moz-box-shadow: inset 0 1px 2px rgba(0,0,0,0.25);
	-webkit-box-shadow: inset 0 1px 2px rgba(0,0,0,0.25);
}

.name-icon, .pass-icon, .phone-icon, .state-icon, .user-icon, .confirm-icon, .city-icon, .last-icon, .street-icon, .zip-icon, .number-icon, .email-icon, .agent-icon  {
	width: 46px;
	height: 47px;
	display: block;
	position: absolute;
	left: 0px;
	padding-right: 2px;
	z-index: -1;
	
	-moz-border-radius-topleft: 5px;
	-moz-border-radius-bottomleft: 5px;
	-webkit-border-top-left-radius: 5px;
	-webkit-border-bottom-left-radius: 5px;
}







.content input:focus + div{
	left: -46px;
}

/* Animation */
.input, .name-icon, .user-icon, .pass-icon, .last-icon, .confirm-icon,  .phone-icon, .state-icon, .city-icon, .street-icon, .zip-icon, .number-icon, .email-icon, .agent-icon,  .button, .register {
	transition: all 0.5s ease;
	-moz-transition: all 0.5s ease;
	-webkit-transition: all 0.5s ease;
	-o-transition: all 0.5s ease;
	-ms-transition: all 0.5s ease;
}

/*******************
FOOTER
*******************/

.login-form .footer {
	   padding: 0 139px 15px;
	overflow: auto;
	
	background: #d4dedf;
	border-top: 1px solid #fff;
	
	box-shadow: inset 0 1px 0 rgba(0,0,0,0.15);
	-moz-box-shadow: inset 0 1px 0 rgba(0,0,0,0.15);
	-webkit-box-shadow: inset 0 1px 0 rgba(0,0,0,0.15);
}

/* Login button */
.login-form .footer .button {
	float:right;
	padding: 11px 25px;
	
	font-family: 'Bree Serif', serif;
	font-weight: 300;
	font-size: 18px;
	color: #fff;
	text-shadow: 0px 1px 0 rgba(0,0,0,0.25);
	
	background: #56c2e1;
	border: 1px solid #46b3d3;
	border-radius: 5px;
	cursor: pointer;
	
	box-shadow: inset 0 0 2px rgba(256,256,256,0.75);
	-moz-box-shadow: inset 0 0 2px rgba(256,256,256,0.75);
	-webkit-box-shadow: inset 0 0 2px rgba(256,256,256,0.75);
}

.login-form .footer .button:hover {
	background: #3f9db8;
	border: 1px solid rgba(256,256,256,0.75);
	
	box-shadow: inset 0 1px 3px rgba(0,0,0,0.5);
	-moz-box-shadow: inset 0 1px 3px rgba(0,0,0,0.5);
	-webkit-box-shadow: inset 0 1px 3px rgba(0,0,0,0.5);
}

.login-form .footer .button:focus {
	position: relative;
	bottom: -1px;
	
	background: #56c2e1;
	
	box-shadow: inset 0 1px 6px rgba(256,256,256,0.75);
	-moz-box-shadow: inset 0 1px 6px rgba(256,256,256,0.75);
	-webkit-box-shadow: inset 0 1px 6px rgba(256,256,256,0.75);
}

/* Register button */
.login-form .footer .register {
	display: block;
	float: right;
	padding: 10px;
	margin-right: 20px;
	
	background: none;
	border: none;
	cursor: pointer;
	
	font-family: 'Bree Serif', serif;
	font-weight: 300;
	font-size: 18px;
	color: #414848;
	text-shadow: 0px 1px 0 rgba(256,256,256,0.5);
}

.login-form .footer .register:hover {
	color: #3f9db8;
}

.login-form .footer .register:focus {
	position: relative;
	bottom: -1px;
	
}


select {
    background: -moz-linear-gradient(19% 75% 90deg, #EDEDED, #FBFBFB) repeat scroll 0 0 transparent;
    border: 1px solid #D0D0D0;
    color: #444444;
    width: 200px;
}
input, textarea, select {
    background: none repeat scroll 0 0 #FBFBFB;
    border: 1px solid #D0D0D0;
    border-radius: 5px 5px 5px 5px;
    color: #444444;
    font-size: 14px;
    line-height: 1.2em;
   padding: 15px 25px;
   width:330px;
}
body, input, textarea, select {
    font-family: 'helvetica neue',helvetica,arial,sans-serif;
    font-size: 16px;
}


body {
    overflow-y: scroll;
}
body, button, input, label, select, td, textarea {
    font-family: 'lucida grande',tahoma,verdana,arial,sans-serif;
    font-size: 11px;
	
}
body {
    background: none repeat scroll 0 0 #FFFFFF;
    color: #333333;
    direction: ltr;
    line-height: 1.28;
    margin: 0;
    padding: 0;
    text-align: left;
    unicode-bidi: embed;
}
form ul li {
    margin: 0 0 11px;
}
ol, ul, li {
    list-style: none outside none;
}

select {
    color: #444444;
    background-color: #FBFBFB !important;
   position : relative;
	z-index : 9999;
    behavior: url(js/js/PIE.htc);
}
input, textarea, select {
    color: #444444;
    font-size: 14px;
    line-height: 1.2em;
   
    
}
</style>  

<script>

$(document).ready(function(){
	
	 $('#pass').focusout(function(){
		var pwd=$('#pass').val();
		if(pwd==""||pwd==null||pwd=="Password"){
			$('#pass').val('');
			$('#pass').css('display','none');
			$('#pass').removeAttr('name');
			$('#text_password').css('display','inline');
			$('#text_password').attr('name',"password");
			
		}
		else{
			
		}
	});
	
	$('#text_password').focus(function(){
		$('#text_password').removeAttr('name');
		$('#pass').css('display','inline');
		$('#pass').attr('name',"password");
		$('#text_password').css('display','none');
		$('#pass').focus();
	});
	
	
	$('#cpass').focusout(function(){
		var pwd=$('#cpass').val();
		if(pwd==""||pwd==null||pwd=="Confirm Password"){
			$('#cpass').val('');
			$('#cpass').css('display','none');
			
			$('#ctext_password').css('display','inline');
			
			
		}
		else{
			
		}
	});
	
	$('#ctext_password').focus(function(){
	
		$('#cpass').css('display','inline');
	
		$('#ctext_password').css('display','none');
		$('#cpass').focus();
	});
	
	
	
});

</script>
</head>		
<body>
	<div id="login_head_text" style=" padding: 15px 0 0 15px;"> 
		<h1><a href="./home.do"><img src="images/logo.png" border="0"/></a></h1> 
	</div>
	
	
	<div id="login_bottom_image">
		<img src="images/top_shadow.png">
		<c:if test="${ not empty param.status}">
			<div id="login_head_text" style="position: relative;top: -34px;font-family: serif;color: red;font-size: 18px;">
				Failed to Register Successfully..Try Again
			</div>
		</c:if>
	</div>
	<div id="login_upper">
	  <div id="wrapper">

	<form name="login-form" class="login-form" action="./register.do" method="POST"onSubmit="return validateReg()">
	
	  <div class="header">
		<h1>Registration Form</h1>
		
		</div>
	
		<div class="content">
		<label class="gd" id="userId">*</label>
		<input type="text" required="required" class="input Password" name="username" id="username" value="Username" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)"/>
		<br/><br/>
		<label class="gd" id="Passwd">*</label>
		<input  type="password" class="input password" style="display: none;" id="pass"  />
		<input id="text_password" name="j_password" class="input password"  value="Password" type="text" />
		<br/><br/> 
		<br/><br/>
		<label class="gd" id="confirmPasswd" style="margin-top: -30px;">*</label>
		<input  type="password" class="input password" style="display: none; margin-top: -12px" id="cpass"  />
		<input id="ctext_password"  class="input password"  value="Confirm Password" type="text"  style="margin-top: -12px;"/>
		<br/><br/>
		<label class="gd" id="FirstName">*</label>
		<input name="firstname" type="text" class="input Firstname"  value="First name" id="fname" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)" />
		<br/><br/>
		<label class="gd" id="LastName">*</label>
		<input class="input password" type="text" required="required" name="lastname" value="Last name" id="lname" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)"/>
		<br/><br/>
		<div style="margin-top:0px;height: 14px;">
		<label class="gd"  id="cty"></label></div>
		<input class="input password" type="text"  name="city" value="City" id="city" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)"/>
		<br/><br/>
		<div style="margin-top:0px;height: 14px;">
		<label class="gd" id="State"></label>
		</div>			
		<select id="state" name="state">
		<option selected="selected" value="">State</option>

<option>Alabama</option>
<option>Alaska</option>
<option>Arizona</option>
<option>Arkansas</option>
<option>California</option>
<option>Colorado</option>
<option>Connecticut</option>
<option>Delaware</option>
<option>Florida</option>
<option>Georgia</option>
<option>Hawaii</option>
<option>Idaho</option>
<option>Illinois</option>
<option>Indiana</option>
<option>Iowa</option>
<option>Kansas</option>
<option>Kentucky</option>
<option>Louisiana</option>
<option>Maine</option>
<option>Maryland</option>
<option>Massachusetts</option>
<option>Michigan</option>
<option>Minnesota</option>
<option>Mississippi</option>
<option>Missouri</option>
<option>Montana</option>
<option>Nebraska</option>
<option>Nevada</option>
<option>New Hampshire</option>
<option>New Jersey</option>
<option>New Mexico</option>
<option>New York</option>
<option>North Carolina</option>
<option>North Dakota</option>
<option>Ohio</option>
<option>Oklahoma</option>
<option>Oregon</option>
<option>Pennsylvania</option>
<option>Rhode Island</option>
<option>South Carolina</option>
<option>South Dakota</option>
<option>Tennesse</option>
<option>Texas</option>
<option>Utah</option>
<option>Vermont</option>
<option>Virginia</option>
<option>Washington</option>
<option>West Virginia</option>
<option>Wisconsin</option>
<option>Wyoming</option>
</select>

		<br/><br/>		
		<div style="margin-top:0px;height: 14px;">
		<label class="gd" id="ZipCode"></label>
		</div>
		<input  class="input password" type="text" name="zipcode" value="Zip Code" id ="zip" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)"/>
		<br/><br/>
		<!-- <label class="gd" id="phn">*</label>
		<input class="input password" type="text" required="required" name="phnNumber" value="Phone Number" id= "phnNumbr" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)"/>
		<br/><br/> -->
		<label class="gd" id="mail">*</label>
		<input  class="input password" type="text" required="required" id="email" name="email" value="Email Adress" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)"/>
		<br/><br/>
		<label class="gd" id= "agent">*</label>
		<input  class="input password" type="text" required="required" name="agentNumber" value="Agent Number" id ="agentNumbr" onFocus="hide_placeholder(this)" onBlur="show_placeholder(this)"/>
		
			
		</div>

		<div class="footer"><br/>
				<div class="bottom_link">
				  <div class="login_button">
					<button class="register_button" >Register</button>
				</div>
		  </div>
					
		
	  </div>
	
	</form>

</div>
<div class="gradient"></div>

	</div>
</body>
</html>
<script type="text/javascript" src="js/script.js"></script>
