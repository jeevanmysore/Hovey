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

	  <link rel="stylesheet" type="css/text/css" href="/hoveyenergy/css/jquery-calendar.css">
	  
	<link href="/hoveyenergy/css/template1.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/hoveyenergy/js/view.js"></script>


	 <style>
	 	.btn {
				display: inline-block;
				padding: 4px 12px;
				margin-bottom: 0;
				font-size: 16px;
				line-height: 20px;
				
				text-align: center;
				text-shadow: 0 1px 1px rgba(255,255,255,0.75);
				vertical-align: middle;
				cursor: pointer;
			
				background-repeat: repeat-x;
				border: 1px solid #ccc;
				border-color: #e6e6e6 #e6e6e6 #bfbfbf;
				border-color: rgba(0,0,0,0.1) rgba(0,0,0,0.1) rgba(0,0,0,0.25);
				border-bottom-color: #b3b3b3;
				-webkit-border-radius: 4px;
				-moz-border-radius: 4px;
				border-radius: 4px;
				filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff',endColorstr='#ffe6e6e6',GradientType=0);
				filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
				-webkit-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
				-moz-box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
				box-shadow: inset 0 1px 0 rgba(255,255,255,0.2),0 1px 2px rgba(0,0,0,0.05);
			}
	 
	 
	 
	 
	 
	 	#feedback{
	 		display: inline;
			position: relative;
			left: -500px;
			color: green;
			font-size: 20px;
	 	}
	 </style>
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
	 	
	 	
	 </script>
	 
	 	<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script> -->
    <script type="text/javascript">
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
	 	





        <!-- Top Header start -->
              
            <!-- Top Header end -->
	<form action="/hoveyenergy/logout.do" method="get" accept-charset="utf-8" id="" name="" class="" > 
	   	<div id="user" style="text-transform:capitalize;margin-right: 2%;">Hi Admin <button type="submit" class="btn" id="logout">LOG OUT</button>
        </div>
     </form>
	<div id="header">
     	
     	 	<div id="logo">
     	 	   <a href="/hoveyenergy//home.do">
     	 			<img src="/hoveyenergy/images/logo.png" alt="logo" height="76" width="275" />
     	 		</a>
     	 	</div>
     	 
        
	    <div id="title"></div>
    </div>
	
   
    
	
	<div id="menubar" style="width: 96%;padding: 0 2%;float: left;height: auto;">
	<!-- <div id="content"> -->
		<div style="width: 100%;">
			<ul id="menu">
				<li ><a href='/hoveyenergy/home.do'>Dashboard</a></li>
				<li><a href="#">Deal Sheet Management</a>
					<ul>
						<li><a href='/hoveyenergy/admin/getpipeline.do'>Pipeline</a></li>
						<li><a href='/hoveyenergy/admin/getannivpaypipeline.do'>Anniv Pay Pipeline</a>	</li>				
							<li><a href='/hoveyenergy/admin/getannivpayduespipeline.do'>Anniv Pay Dues</a>	</li>			
						<li><a href='/hoveyenergy/admin/customer.do'>Customer Management</a></li>
						<li><a href='/hoveyenergy/admin/viewdealsheets.do'>Deal Manangement</a></li>
						<li ><a href="/hoveyenergy/admin/commissions.do" style="padding-left: 0px;">Commission Management</a> </li>
						<li><a href='/hoveyenergy/admin/utility.do'>Utility Management</a></li>
						<li><a href='/hoveyenergy/admin/states.do'>Add/Remove States</a></li>
						<li><a href='/hoveyenergy/admin/contracttypes.do'style="padding-left: 0px;">Add/Remove ContractTypes</a></li>
						
					</ul>
				</li>
				<li><a href="#">Reports</a>
					<ul>
						<li><a href="/hoveyenergy/reports/commissionsreports.do">Commission Reports</a></li>
						<li><a href="/hoveyenergy/reports/anniversaryreports.do">Anniv Payment Reports</a></li>
						<li><a href="/hoveyenergy/reports/supplierreports.do">Supplier Reports</a></li>
						<li><a href="/hoveyenergy/reports/agentreports.do">Agent Reports</a></li>		
						<li><a href="/hoveyenergy/reports/renewalreports.do">Renewal Reports</a></li>	
						<li><a href="/hoveyenergy/reports/getagentcommissionreports.do" style="padding-left: 0px;">Agent Commission Reports</a></li>			
					</ul>
				
				
				</li>
				<li><a href="/hoveyenergy/admin/renewals.do">Renewals</a></li>
				<li><a href="#">Agent Management</a>
					<ul>
						 <li><a href="/hoveyenergy/admin/register.do">Create Agent</a></li>					
						<li><a href='/hoveyenergy/admin/agents.do'>Manage Agents</a></li>
						<li><a href='/hoveyenergy/admin/agentcommissions.do'>Agent Commissions</a></li>
						<li><a href='/hoveyenergy/admin/changekwhlimit.do'>Change kWh Limit</a></li>
						
					</ul>
				</li>
				<li><a href="#">Supplier Management</a>
					<ul>
					<!-- 	<li><a href="./addsupplier.do">Create Supplier</a></li> -->
						<li><a href="/hoveyenergy/admin/suppliers.do">Manage Suppliers</a></li>
						<li><a href="/hoveyenergy/admin/mappings.do">Supplier Mappings</a></li>
						<li><a href="/hoveyenergy/admin/loadsupplierreport.do">Upload Supplier Reports</a></li>
						<li><a href="/hoveyenergy/admin/loadexistingreport.do">Load Existing Reports</a></li>
						<li><a href="/hoveyenergy/admin/removeuploadedsupplierreport.do">Remove Supplier Reports</a></li>
						
						<!-- <li><a href="./supplierfiles.do">Manage Supplier Reports</a></li> -->
					</ul>
				</li>
				<li><a href="#">Profile Management</a>
					<ul>
						<li><a href="/hoveyenergy/admin/editprofile.do?username=hoveyenergy">Edit Profile</a></li>
						<li><a href="/hoveyenergy/admin/resetpassword.do?username=hoveyenergy">Reset Password</a></li>
					</ul>
				</li>
			</ul>
		</div>	
	</div>
   
 