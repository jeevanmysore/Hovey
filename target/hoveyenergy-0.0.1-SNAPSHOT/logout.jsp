<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head manifest="tetris.manifest">     
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /> 
    <link rel="stylesheet" href="/hoveyenergy/css/style.css" type="text/css" media="screen, mobile" title="main" charset="utf-8">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <title>Logged Out</title>
   <style>
   	.error_msg{
   		color: #20B420;
		text-align: left;
		float: left;
		
		clear: both;
		  margin-bottom: 10px; 
		  margin-right: 50px;
		  font-size: 14px;		
   	}
   	
   	.error_msg1{
   		color: #20B420;
		text-align: left;
		float: right;		
		width: 500px;
		margin-bottom: 10px;
		font-size: 14px;
   		
   	}
   	.error_div{
   		
		margin: 0 auto;
		
		display: inline-block;
   	}
   
   </style>
    	
</head>		
<body>
	<div id="login_head_text"> 
		<a href="./home.do"><h1><img src="/hoveyenergy/images/logo.png"/></h1> </a>
	</div>
	
	<div id="login_upper">
		<h2 align="center"> You have been Logged Out</h2>
	</div>
	<br />
	<br />
	<h4 align="center">Please <a href="/hoveyenergy">Click</a> here to Login Again</h4>
</body>
</html>
<script type="text/javascript" src="/hoveyenergy/js/script.js"></script>
