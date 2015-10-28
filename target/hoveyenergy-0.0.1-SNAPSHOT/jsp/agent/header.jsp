<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<c:set var="username" value="${sessionScope.username}" scope= "session" />




	<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /> 
	<link rel="stylesheet" href="/hoveyenergy/css/style.css" type="text/css" media="screen, mobile" title="main" charset="utf-8">
	<link rel="stylesheet" href="/hoveyenergy/css/login.css" type="text/css" media="screen, mobile" title="main" charset="utf-8"> 	
	<link href="/hoveyenergy/css/view.css" rel="stylesheet" type="text/css">
	<link href="/hoveyenergy/css/tab_style.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="/hoveyenergy/css/jquery-ui.css">
	  <link rel="stylesheet" type="text/css" href="/hoveyenergy/css/jquery-calendar.css">
	
	<link href="/hoveyenergy/css/template1.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/hoveyenergy/js/view.js"></script>
	<script type="text/javascript" src="/hoveyenergy/js/calendar.js"></script>
	<script type="text/javascript" src="/hoveyenergy/js/jquery-1.js"></script>
	  <script type="text/javascript" src="/hoveyenergy/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/hoveyenergy/js/jquery-calendar.js"></script>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
	<link href="/hoveyenergy/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		
		<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	 <script type="text/javascript" src="/hoveyenergy/js/dealsheet.js"></script>
	 <script>
	 	
	 $(function() {
		  if ($.browser.msie && $.browser.version.substr(0,1)<7)
		  {
			$('li').has('ul').mouseover(function(){
				$(this).children('ul').css('visibility','visible');
				}).mouseout(function(){
				$(this).children('ul').css('visibility','hidden');
				})
		  }
		}); 
	 
	 $(function() {
         if ($.browser.msie && $.browser.version.substr(0,1)<10)
         {
			$('li').has('ul').mouseover(function(){
				$(this).children('ul').show();
				}).mouseout(function(){
				$(this).children('ul').hide();
				})
         }
       });     
	 	
	 	
	 </script>
	 <style>
	 	#feedback{
	 		display: inline;
			position: relative;
			left: -500px;
			color: green;
			font-size: 20px;
	 	}
	 	
	 /* 	#menu li,#menu a{
	 		float:none !important;
	 	} */
	 	
	 	
	 </style>
	 
	





        <!-- Top Header start -->
              
            <!-- Top Header end -->
	<form action="/hoveyenergy/logout.do" method="get" accept-charset="utf-8" id="" name="" class="" > 
	   	<div id="user" style="text-transform:capitalize;margin-right: 4.2%;">Hi ${user.firstName} <button type="submit" class="btn" id="logout">LOG OUT</button>
	   
        </div>
     </form>
	<div id="header">
     	
     	 	<div id="logo">
     	 	   <a href="/hoveyenergy/home.do">
     	 			<img src="/hoveyenergy/images/logo.png" alt="logo" height="76" width="275" />
     	 		</a>
     	 	</div>
     	 
        
	    <div id="title"></div>
    </div>
	
   
  
	
	<div id="menubar" style="width: 94%;padding: 0 2%;float: left;height: auto;">
			<div style="width: 100%;">
				<ul id="menu">
					
					<li style="margin-left:77px; "><a href="/hoveyenergy/agent/dealsheet.do">Create Order</a></li>
						
					
					
				</ul>
			</div>
	</div>
	
	
	
	
	
   
