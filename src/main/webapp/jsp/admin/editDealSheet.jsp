<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<title>Edit Deal Sheet</title>
	<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /> 
	<link rel="stylesheet" href="/hoveyenergy/css/style.css" type="text/css" media="screen, mobile" title="main" charset="utf-8">
	<link rel="stylesheet" href="/hoveyenergy/css/login.css" type="text/css" media="screen, mobile" title="main" charset="utf-8"> 	
	<link href="/hoveyenergy/css/view.css" rel="stylesheet" type="text/css">
	<link href="/hoveyenergy/css/tab_style.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="css/text/css" href="/hoveyenergy/css/jquery-ui.css">
	  <link rel="stylesheet" type="css/text/css" href="/hoveyenergy/css/jquery-calendar.css">
	
	    	
	<link href="/hoveyenergy/css/template1.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/hoveyenergy/js/view.js"></script>
	<script type="text/javascript" src="/hoveyenergy/js/calendar.js"></script>
	<script type="text/javascript" src="/hoveyenergy/js/jquery-1.js"></script>
	  <script type="text/javascript" src="/hoveyenergy/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/hoveyenergy/js/jquery-calendar.js"></script>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
		<link href="/hoveyenergy/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		 <link  href="/hoveyenergy/css/dealsheet.css" rel="stylesheet" type="text/css">
		<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	 <script type="text/javascript" src="/hoveyenergy/js/dealsheet.js"></script>
	 
	 <!--[if lt IE 10]>		
		<link rel="stylesheet" href="css/dealsheet_ie.css" type="text/css" />		
	 <![endif]-->
	 <script>
	 
		 var date;
		 $(function() {
			 $('.datepicker').datepicker( {
				 	showOn: "button",
					buttonImage: "/hoveyenergy/images/calendar.gif",
					buttonImageOnly: true,
			        changeMonth: true,
			        changeYear: true,
			        showButtonPanel: true,
			        dateFormat: 'mm/dd/yy',
			        yearRange: 'c-1:c+10',
			        onSelect:function(dateText,inst){
						   date=1;
						  date=$(this).datepicker('getDate').getDate();
						 
					   },
			        onClose: function(dateText, inst) {        	
			        	 var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
				            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
				            if(date>1){
				            	$(this).datepicker('setDate', new Date(year, month,date));		
				            }
				            else{
				            	$(this).datepicker('setDate', new Date(year, month, 1));
				            }
				            date=1;       	
			        }
			    });
			 
			 
			 $('#agentName').change(function(){
				
				 var agent= $(this).find('option:selected').val();
				 $('#agentNumber').val(agent);
			 });
			 
			 
			 
			//open popup
				$("#pop").click(function(){
				  $("#overlay_form").fadeIn(1000);
				  positionPopup();
				});
		
			//close popup
				$("#close").click(function(){
					$("#overlay_form").fadeOut(500);
				});
				
			
				$('#overlay_form').submit(function(e){	
					$('.results').remove();
					$.ajax({
						url:"${pageContext.request.contextPath}/getcustomer.do",
						type:"POST",
						data:$('#overlay_form').serialize(),
						success:function(data){	
							
							var obj = jQuery.parseJSON(data);								
							var customer=obj.customer;										
							$.each(customer, function(i,val) {							
								//needs to create table row...
								var fname=val.FIRST_NAME;
								//var lname=val.LAST_NAME;
								var tax=val.TAX_ID;
								var id=val.CUSTOMER_ID;
								$('#searchtbl').append('<tr class="results" id=results'+i+'><input type=hidden name=customerId value='+id+' id=custId'+i+' /><td><input type=radio name=cust id='+i+ '></td><td>'+fname+'</td><td>'+tax+'</td></tr>');
									
							}); 
						},
						error:function(data){
							$('#searchtbl').append('<tr class="results" id=results><td colspan="3">No Results Found</td></tr>');
						}
					});
					e.preventDefault();
					
				});
				
			
			 
			 
			 
		});
			
			//position the popup at the center of the page
			function positionPopup(){
			  if(!$("#overlay_form").is(':visible')){
			    return;
			  } 
			  $("#overlay_form").css({
			      left: ($(window).width() - $('#overlay_form').width()) / 2,
			      top: ($(window).width() - $('#overlay_form').width()) / 21,
			      position:'absolute'
			  });
			}
		
			//maintain the popup at center of the page when browser resized
			$(window).bind('resize',positionPopup);
	 	
	 	
	 </script>
	 <style>
		#loader {
			        width: 220px;
			        height: 80px;
			        position: fixed;
			        top: 50%;
			        left: 50%;
			        z-index: -1;
			        opacity: 0;
			       transition: all .5s ease-in-out;
			        margin: -40px 0 0 -110px;
			    }
			
			    #loader img {position: relative;  margin-top: -30px; left: 10px;}
			
			    .loading #loader {z-index: 1000; opacity: 1.0}
			
	
		.btn-success {
			background-color: #8D2A8D !important;
			background:  #8D2A8D !important;
			color:#fff !important;
			
			-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important;
  			 behavior: url(/hoveyenergy/js/js/PIE.htc);
			position:relative;
		}
		
		.btn-success:hover, .btn-success:focus, .btn-success:active{
		
			background:  #410741!important;
			color:#fff;
				-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important;
  			 behavior: url(/hoveyenergy/js/js/PIE.htc);
			position:relative;
		}	
		
		
		.btn-update {
			background-color: #62A82C !important;
			background:  #62A82C !important;
			color:#fff !important;
			 
			-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important;
  			behavior: url(/hoveyenergy/js/js/PIE.htc);
			position:relative;
			
  			
		}
		
		.btn-update:hover, .btn-update:focus, .btn-update:active{
		
			background:  #467D1D!important;
			color:#fff;
				-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important;
  			
		}
		
		.btn-undo {
			background-color: #363AF7 !important;
			background:  #363AF7 !important;
			color:#fff !important;
			 behavior: url(/hoveyenergy/js/js/PIE.htc);
			position:relative;
			-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important;
  			
		}
		
		.btn-undo:hover, .btn-undo:focus, .btn-undo:active{
		
			background:  #0E358F!important;
			color:#fff;
				-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important;
  				-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important; 
  			
		}
		
		.btn-back {
			background-color: #F57A0F !important;
			background:  #F57A0F !important;
			color:#fff !important;
			 behavior: url(/hoveyenergy/js/js/PIE.htc);
			position:relative;
				-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important; 
  			
		}
		
		.btn-back:hover, .btn-back:focus, .btn-back	:active{
		
			background:  #8C4A0F	!important;
			color:#fff;
				-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important;
		}
		
		
		.disclaimer{
			clear: both;
			display:block;
			/* position:static;
			bottom:2px; */
			margin-left:2%;
			width:90%;
			text-align:center;
			font-size: 12px;
			color:#807C7C;
			text-indent: 50px;			
		}
		
		 .ui-datepicker-prev, .ui-datepicker-current{
	 	display: none !important;
	 	
	 }
		
		
		.btn-success:hover, .btn-success:focus, .btn-success:active, .btn-success.active, .btn-success.disabled, .btn-success[disabled],
		.btn:hover, .btn:focus,.btn-success,.btn-primary,.btn-danger,.btn-print
		{
			
			 behavior: url(js/js/PIE.htc);
			position:relative;
			-webkit-box-shadow: 0px 0px 5px #c2c2c2 !important;
  			box-shadow: 5px 7px 5px #c2c2c2 !important;
		}
		
		.btn-print {
			background-color: #FA8202;
			background:  #FA8202 !important;
			color:#fff !important;
		}
		
		.btn-print:hover, .btn-print:focus, .btn-print:active{
		
			background:  #E07604;
			color:#fff;
		}
		
		.startdate3{
			width:11%;margin-left:1.5%;margin-top: -4px;display: inline-block;
		}
		
		.row-fluid [class*="span"]{
			margin-left: 1.4%;
		}
		
		.agnt1{
			margin-left: 15px;
		}
		
		a, a:ACTIVE,a:HOVER,a:VISITED{
			color:#fff;
			text-decoration: none;
		}
		
		
		.agnt1 .ui-datepicker-trigger{
			width:8% !important;
		}
		/* #menu li,#menu a{
	 		float:none !important;
	 		font: bold 16px/25px Arial, Helvetica;
	 	}
	  */
	  
	  
	  input[type="text"],textarea{
	  	padding: 0px !important;
	  }
	 </style>
	 
	
</head>

<body class="firefox">

 <%@ include file="header.jsp" %>
		
		
    
    
    
  <div id="content" style="margin-left: 2.12%;margin-bottom: 5px;"><!-- --- CONTENT BLOCK ---  -->
  
    
		<div id="feedback1" style="text-align: center;font-size: 18px;font-weight: bold;color: green">
    		
    	</div>
  		<div class="title" style="text-decoration:none!important;">EDIT DEAL</div>
  		<div id="loader" >
  			<img src="/hoveyenergy/images/loader.GIF" />
  		</div>
  		
  		
  		
  		
  		  		
  		<!-- UI for Exisiting Customers -->
  	<div id="fancybox-content" style="border-width: 10px; width: auto; height: auto;">
	  
		<div id="main_container" class="container">
			<div class="cb">
				<div class="inner-page">
				 <form:form  commandName="customerSearch" id="overlay_form"  style="display:none"  >
					  
	  
				<div class="title">Search Customer By:</div>
				
				
			 	<div class="lab_top" style="margin-top:5px;">
			 		<div id="ordererror" class="multisearcherror"></div>
				  <div class="fc-text">First Name: </div> 
					<div class="fc-text1">
						<select style="border:1px solid #ccc; margin-left:33px;   height: 25px; width: 125px; margin-top: 10px; " name="firstName" id="sfname">
							<option value="">--select--</option>
							<c:forEach var="firstName" items="${firstNames }">
							   <c:if test="${firstName ne '' }">
								 <option value="${firstName}">${firstName }</option>
								 </c:if>
							</c:forEach>
						
						</select>
					</div>      
				</div>
				
				
				<div class="lab_top" style="margin-top:5px;">
			 		<div id="ordererror" class="multisearcherror"></div>
				  <div class="fc-text">Last Name: </div> 
					<div class="fc-text1">
						<select style="border:1px solid #ccc; margin-left:33px;   height: 25px; width: 125px; margin-top: 10px; " name="lastName" id="slname">
							<option value="">--select--</option>
							<c:forEach var="lastName" items="${lastNames }">
							   <c:if test="${lastName ne '' }">
								 <option value="${lastName }">${lastName}</option>
								 </c:if>
							</c:forEach>
						
						</select>
					</div>      
				</div>
				
				
				<div class="lab_top" style="margin-top:5px;">
			 		<div id="ordererror" class="multisearcherror"></div>
				  <div class="fc-text">Tax ID: </div> 
					<div class="fc-text1">
						<select style="border:1px solid #ccc; margin-left:33px;   height: 25px; width: 125px; margin-top: 10px; " name="taxId" id="staxid">
							<option value="">--select--</option>
							<c:forEach var="taxId" items="${taxIds }">
							   <c:if test="${not empty  taxId}">
								  <option value="${taxId }">${taxId}</option>
								 </c:if>
							</c:forEach>
						
						</select>
					</div>      
				</div>
				
			    
			    <div class="lab_top" style="margin-top:5px;">
			 		<div id="ordererror" class="multisearcherror"></div>
				  <div class="fc-text">Phone Number: </div> 
					<div class="fc-text1">
						<select style="border:1px solid #ccc; margin-left:33px;   height: 25px; width: 125px; margin-top: 10px; " name="phoneNo" id="sphone">
							<option value="">--select--</option>
							<c:forEach var="phoneNo" items="${phoneNos }">
							   <c:if test="${phoneNo ne '' }">
								 <option value="${phoneNo }">${phoneNo }</option>
								 </c:if>
							</c:forEach>
						
						</select>
					</div>      
				</div>
				
				
				<div class="lab_top" style="margin-top:5px;">
			 		<div id="ordererror" class="multisearcherror"></div>
				  <div class="fc-text">Business Name: </div> 
					<div class="fc-text1">
						<select style="border:1px solid #ccc; margin-left:33px;   height: 25px; width: 125px; margin-top: 10px; " name="businessName" id="sbname">
							<option value="">--select--</option>
							<c:forEach var="cust" items="${businessNames }">
							   <c:if test="${cust ne '' }">
								 <option value="${cust}">${cust}</option>
								 </c:if>
							</c:forEach>						
						</select>
					</div>      
				</div>
				
					    
			  
			    <br />
			    <div class="su-menu" style="margin-top: 0px;">
			      
			      <span class="button">
			        <input name="submit" value="Search" id="search_cust" class="button80" style="height: 33px;width: 133px"tabindex="10" type="submit" />
			      </span> </div>
			 
			   
			    
			    
			    <div style="clear:both;text-align: center;" class="CSSTableGenerator"><span style="text-align: center;font-size: 14px;font-weight: bold;"> Search Results</span>
			    	<table id="searchtbl" align="center" style="border: solid 1px;">
			    	 <tr>
			    	 		<td>Select</td>
			    	 		<td>Business Name</td>
			    	 		<td>Tax Id</td>			    	 
			    	 </tr>
			    	
			    	
			    	
			    	
			    	</table>
			    		<input type="button" class="btn btn-success" value="Select" id="resBtn" style="margin: 5px;" />
			    
			    </div>
			
			<div class="login-desc">
			      <a href="#" id="close"  style="color:#4B4D50;float: right;margin-top: 20px;">Cancel</a>
			    </div>
			
		</form:form>    	
			
			    					</div>
							</div>
				
			</div>
	</div>
	  
	  
  		
  		
  		<!-- End of Existing Customers UI -->
  		
  		
  		
  		
  		
  		
	<div id="outerContainer" style="margin-bottom: 30px;">		
		<div id="form_container">
    		<div class="box-content">
				<form:form method="post" id="dealsheet" name="dealsheet" class="appnitro" action="/hoveyenergy/admin/editdealsheet.do" commandName="dealSheet"  autocomplete="off" >	
                 	<div class="row-fluid">
                  		 <div style="font-family:Arial, Helvetica, sans-serif; background:#999999; color:#FFF; height:25px; font-size:15px; font-weight:bold;" class="stitle">
                          	<p style="padding:3px;">Agent Info</p>
                         </div>
                          	
                          		<input type="hidden" name="dealId" value="${dealId }" id="dealId" />
                          		<div class="span3 agnt1">
									<div class="control-group">
										<label class="control-label" for="textfield">Date Time</label>
										<div class="controls controls-row">
										 	 <spring:bind path="dealSheet.orders[0].orderDate">
												<input type="text" id="orderDate" name="${status.expression}" class="input-block-level datepicker" value="<fmt:formatDate value="${orders[0].orderDate }" pattern="MM/dd/yyyy HH:mm:ss" />"  readonly="readonly" >
												
											</spring:bind>
										</div>
									</div>
								</div>
                          	
								<div class="span3 agnt">
									<div class="control-group">
										<label class="control-label" for="textfield">Agent Name</label>
										<div class="controls controls-row">
											 <select name="agentName" id="agentName">
											      <c:forEach var="agent" items="${agents }">
											      	<option value="${agent.agentNumber}" ${orders[0].createdAgent.agentNumber eq agent.agentNumber ? 'selected' : '' }  > ${agent.firstName}  ${agent.lastName}</option>
											      
											      </c:forEach>											 
											 </select>
											
										</div>
									</div>
								</div>
								<div class="span3 agnt">
									<div class="control-group">
										<label class="control-label" for="textfield">Agent Number</label>
										<div class="controls controls-row">
										   <spring:bind path="dealSheet.orders[0].createdAgent.agentNumber">
											<input type="text" class="input-block-level"  value="${orders[0].createdAgent.agentNumber}" readonly="readonly" id="agentNumber"  name="${status.expression}" >
										   </spring:bind>
										</div>
									</div>
								</div>
								
								
								<div class="span3 agnt">
									<div class="control-group">
										<label class="control-label" for="textfield">Supplier</label>
										<div class="controls controls-row">
										   <spring:bind path="dealSheet.orders[0].supplierName.supplierName">
										   		<select class="input-block-level"  name="${status.expression}" style="width: 80%;">
										   			<c:forEach var="supplier" items="${suppliers}">
										   				<option value="${supplier.supplierName}"  ${supplier.supplierName eq  orders[0].supplierName.supplierName ? 'selected' : '' } >${supplier.supplierName}</option>
 										   			
										   			</c:forEach>
										   		</select>
											</spring:bind>	
										</div>
									</div>
								</div>
										
						</div>
						
						<div class="row-fluid">
                           <div style="font-family:Arial, Helvetica, sans-serif; background:#999999; color:#FFF; height:25px; font-size:15px; font-weight:bold;" class="stitle">
                              <p style="padding: 3px;">Customer Info
                                         <!--  Modified as per clients request to Save Customer Details if edited -->
                                             <input type="checkbox" name="cust_check"  id="cust_check" value="1"  style="margin-top: -1px;margin-left: 51px;" />
											 <span  class="exCust service" > Click Here for Existing customer</span>
                              				  <input type="checkbox" name="newCust" id="newCust"   style="margin-left: 20px;margin-bottom: 5px;height: 14px;" onclick="saveCustomerasNew();"/> <span class="service" style="color: lightyellow;text-decoration: underline;" > Click here to Save as a New Customer</span>
                              				 <!-- <input type="checkbox" name="cust_check"  id="cust_check" value="1"  style="margin-top: -1px;" />
										<span style="font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:normal;" class="exCust"> Click Here for Existing customer</span> --></p>
							</div>
							<input type="hidden" name="customer.customerId" id="custid" value="${orders[0].taxId.customerId}" />
							<input type="hidden"  id="custid1" value="${orders[0].taxId.customerId}" />
							<div class="span3 cmn1" style="width:18%;margin-left: 15px;">
								<div class="control-group">
									<label class="control-label" for="textfield">First Name<span id="custname_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										
										<input id="custname" name="customer.firstName" class="input-block-level" type="text" style="width:90%;"   value="${orders[0].taxId.firstName}"/>
									</div>
								</div>
							</div>
							<div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Last Name <span id="custname2_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input id="custname2" name="customer.lastName" type="text" class="input-block-level" style="width:90%;"  value="${orders[0].taxId.lastName}" />
									</div>
								</div>
							</div>
							<div class="span3 cmn" style="width:18%;">
								<div class="control-group" >
									<label class="control-label" for="textfield">Title<span id="custtitle_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
									<input id="custtitle" name="customer.title"  type="text" class="input-block-level" style="width:90%;"    value="${orders[0].taxId.title}"/>
									</div>
								</div>
							</div>
                             <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Phone Number<span id="custphone_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input id="custphone" name="customer.phoneNo" type="text" class="input-block-level" style="width:90%;"   value="${orders[0].taxId.phoneNo}" />
									</div>
								</div>
							</div>
							 <div class="span3 txex" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Service Type</label>
									<div class="controls controls-row">
										<select class="input-large" id="comres" name="customer.type" style="width:88%;">
											<option value="commercial" ${orders[0].taxId.type eq 'commercial' ? 'selected' : '' } >Commercial</option>
											<option value="residential"  ${orders[0].taxId.type eq 'residential' ? 'selected' : '' } >Residential</option>
										</select>
									
									
										
										 
									</div>
								</div>
							</div>
                            <div class="span3 cmn1" style="clear:both;width:18%;margin-left: 15px;">
								<div class="control-group">
									<label class="control-label" for="textfield">Email<span id="custemail_error" class="error_msg"></span></label>
									<div class="controls controls-row">
										<input id="email" name="customer.email"  type="text" class="input-block-level" style="width:90%;text-transform: none;"   value="${orders[0].taxId.email}" >
									</div>
								</div>
							</div>
                            <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Fax<span id="fax_error" class="error_msg"></span></label>
									<div class="controls controls-row">
										<input id="fax" name="customer.faxNo" class="input-block-level" type="text" style="width:90%;"   value="${orders[0].taxId.faxNo}"/>
									</div>
								</div>
							</div>
                            <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Tax ID<span id="custtax_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input id="taxid" name="customer.taxId"  type="text" class="input-block-level" style="width:90%;"   value="${orders[0].taxId.taxId}"/>
										
									</div>
								</div>
							</div>
                            <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Tax Exempt (Y/N)</label>
									<div class="controls controls-row">
									 							  
									  <select class="input-large" id="taxexempt" name="customer.taxExempt" style="width:90%;">
												 <option value="na"  ${ orders[0].taxId.taxExempt eq null ? 'selected' : ''}>N/A</option>
				                          <option value="yes" ${ orders[0].taxId.taxExempt eq true ? 'selected' : ''}>Yes</option>
				                          <option value="no" ${ orders[0].taxId.taxExempt eq false ? 'selected' : ''} >No</option>	
										</select>
											
									</div>
								</div>
							</div>
							
							 
									<div class="span3 cmn" style="width:18%;">
										<div class="control-group">
											<label class="control-label" for="textfield">Fronter<span id="fronter_error" class="error_msg"></span></label>
											<div class="controls controls-row">
												<input id="fronter" name="customer.fronter" class="input-block-level" type="text" style="width:90%;" value="${orders[0].taxId.fronter }"/>
											</div>
										</div>
								 </div>
							
							<!-- <div class="span3" style="width:16%;">
								<div style="margin-top: 18px; text-align:center;" id="clearBtn">
           							<button type="button" class="btn btn-success" style="width:91%;font-size: 16px;">Clear All Fields</button>
         						  </div>
							</div> -->
					</div>
					
					
					
									
					<div class="row-fluid">
                       <div style="font-family:Arial, Helvetica, sans-serif; background:#999999; color:#FFF; height:25px; font-size:15px; font-weight:bold;" class="stitle">
                           <p style="padding: 3px;">Order Info </p>                          
                        </div>
                    </div>
                    <c:set var="i" value="1"></c:set>
	     		   <c:forEach items="${orders}" var="order">
	     		   
                     <div class="row-fluid">   
                        <spring:bind path="dealSheet.orders[${i-1 }].id">
							 		<input type="hidden" name="${status.expression}" id="orderId${i-1 }" index="${i-1 }" value="${order.id }" />
							 	</spring:bind>
                        
                        
                        <c:choose>
                        	<c:when test="${order.status eq 'Rescinded' }">
                        		<div class="accTitle1">Account #${i }
                        			<div style="display: inline;margin-left: 55px;" >
			                        	
										 	<input type="checkbox" name="electric"  value="electric"  class="svcchk" index="${i-1 }" id="electric${i-1 }" service="electric"  onclick="getservice(this.id);"
										 			${order.service eq 'electric' ? 'checked' : '' }
										 	/>
										 	 <span class="service" > Electric</span>
										 	<input onload="getservice(this.id);" type="checkbox" name="gas" value="gas"  class="svcchk" style="margin-left: 50px;" index="${i-1 }" id="gas${i-1 }" service="gas"  onclick="getservice(this.id);" ${order.service eq 'gas' ? 'checked' : '' }/> <span class="service" >Gas</span>
										 	<spring:bind path="dealSheet.orders[${i-1 }].service">
										 		<input type="hidden" name="${status.expression}" id="service${i-1 }" index="${i-1 }" value="electric" />
										 	</spring:bind>
										 </div>
										 <input type="checkbox" name="contract"   class="svcchk" style="margin-left: 150px;" index="${i-1}" id="contract${i-1}"  onclick="changecontract(this.id);" ${order.contractType eq 'renewal' ? 'checked' : '' }/> <span class="service" >Renewal</span>
										 <spring:bind path="dealSheet.orders[${i-1 }].contractType">
										 	<input type="hidden" name="${status.expression}" id="ctype${i-1 }" value="${order.contractType}"   />
										 </spring:bind>			
										 
										 <c:if test="${fn:length(orders) gt 1}">
											<div style="display: inline-block;margin-left: 115px;">
											  <a href="#" style="background: rgb(255, 0, 92);padding: 1px 20px 0px 20px;width: 110px;border: 1px solid;border-radius: 3px;z-index: 1000;" index="${i-1}" id="remove${i-1}"  onclick="removeAccount(this.id);" > Remove Account</a> 
											<%-- <input type="checkbox" name="remove"   class="svcchk" style="margin-left: 150px;" index="${i-1}" id="remove${i-1}"  onclick="removeAccount(this.id);"  /> <span class="service" >Remove Account</span> --%> 							 
											 </div>
										</c:if>
                        		</div>
                        	</c:when>
                        	<c:otherwise>
                        	
	                        	<div class="accTitle">Account #${i }
			                        		<div style="display: inline;margin-left: 55px;" >
			                        	
										 	<input type="checkbox" name="electric"  value="electric"  class="svcchk" index="${i-1 }" id="electric${i-1 }" service="electric"  onclick="getservice(this.id);"
										 			${order.service eq 'electric' ? 'checked' : '' }
										 	/>
										 	 <span class="service" > Electric</span>
										 	<input onload="getservice(this.id);" type="checkbox" name="gas" value="gas"  class="svcchk" style="margin-left: 50px;" index="${i-1 }" id="gas${i-1 }" service="gas"  onclick="getservice(this.id);" ${order.service eq 'gas' ? 'checked' : '' }/> <span class="service" >Gas</span>
										 	<spring:bind path="dealSheet.orders[${i-1 }].service">
										 		<input type="hidden" name="${status.expression}" id="service${i-1 }" index="${i-1 }" value="electric" />
										 	</spring:bind>
										 </div>
										 <input type="checkbox" name="contract"   class="svcchk" style="margin-left: 150px;" index="${i-1}" id="contract${i-1}"  onclick="changecontract(this.id);" ${order.contractType eq 'renewal' ? 'checked' : '' }/> <span class="service" >Renewal</span>
										 <spring:bind path="dealSheet.orders[${i-1 }].contractType">
										 	<input type="hidden" name="${status.expression}" id="ctype${i-1 }" value="${order.contractType}"   />
										 </spring:bind>	
										 <c:if test="${fn:length(orders) gt 1}">
											  <div style="display: inline-block;margin-left: 115px;">
											  <a href="#" style="background: rgb(255, 0, 92);padding: 1px 20px 0px 20px;width: 110px;border: 1px solid;border-radius: 3px;z-index: 1000;" index="${i-1}" id="remove${i-1}"  onclick="removeAccount(this.id);" > Remove Account</a> 
											<%-- <input type="checkbox" name="remove"   class="svcchk" style="margin-left: 150px;" index="${i-1}" id="remove${i-1}"  onclick="removeAccount(this.id);"  /> <span class="service" >Remove Account</span> --%> 							 
											 </div>
										 </c:if>
										 									 
										 
								</div>	
						
                        	
                        	
                        	</c:otherwise>
                        </c:choose>
                       
					  
						<div class="span3 util" style="margin-left: 15px;width:10.2%;">
							<div class="control-group">
								<label class="control-label" for="textfield">Utility<span id="utiltiy${i-1}_error" class="error_msg">*</span></label>
								<div class="controls controls-row">
								  <spring:bind path="dealSheet.orders[${i-1}].utility.utility">
									<select class="input-large " id="utility${i-1}" name="${status.expression}" style="width:100%;margin-top: 5px;" >
										<c:forEach var="util" items="${utilities}">
			                        	 <option value="${util.utility}" ${util.utility eq order.utility.utility ? 'selected' : '' }>${util.utility}</option>
			                        </c:forEach>	
									</select>
								  </spring:bind>
								</div>
							</div>
						</div>
						
						
						<div class="span3" style="margin-left: 3%;width: 19%;" >
							<%--  <div style="margin-top: -25px;height: 23px;">
							 	<input type="checkbox" name="electric" checked="checked" value="electric" style="margin-top: -3px;" /> <span class="service" > Electric</span>
							 	<input type="checkbox" name="gas" value="gas"  style="margin: -3px 0px 0px 5px;" /> <span class="service" >Gas</span>
							 	<spring:bind path="dealSheet.orders[${i-1}]">
							 		<input type="hidden" name="${status.expression}" id="service${i-1}" index="${i-1}" />
							 	</spring:bind>
							 </div>  --%>
							
							<div class="control-group">
								<label class="control-label" for="textfield" style="margin-top: -1px;">Account Number<span id="acc_error${i-1}" class="error_msg">*</span> </label>
								   <div class="accsize"><span id="acc1_error${i-1}" class="error_msg"></span></div>
									<div class="controls controls-row">
									  <spring:bind path="dealSheet.orders[${i-1}].accountNumber">
										<input type="text" id="account${i-1}" name="${status.expression}" class="input-block-level acc" index="${i-1}" onblur="validateeditaccountsize(this.id)" style="width: 98%;"  value="${order.accountNumber }"/ >
									</spring:bind>
									</div>
							</div>
						</div>
						
						
                    	<div class="span3" style="width:12.6%;margin-left: 2%;">
                    		 <div style="margin-top: -25px;height: 23px;"><span id="kwh_error${i-1}" class="error_msg"></span></div>	
							<div class="control-group">
								<label class="control-label" for="textfield" >  
										<span id="svcLbl${i-1}"> ${ order.service eq 'electric' ? 'kWh' : 'Therm'}</span> 
									 <span class="chkbox"> (Annual)</span><span class="error_msg">*</span> </label>
								<div class="controls controls-row">
								   <spring:bind path="dealSheet.orders[${i-1}].kwh">
									<input id="kwh${i-1}" name="${status.expression}" type="text" class="input-block-level" style="width:100%" value="${order.kwh }"/>
								   </spring:bind>
								</div>
							</div>
						</div>      
                    
                    
                    
                       <div class="span3 startdate2" style="width:8%;margin-left: 2%;">  
                            <div style="margin-top: -25px;height: 23px;"><span id="term_error${i-1}" class="error_msg"></span> </div>                       
							<div class="control-group">
								<label class="control-label" for="textfield">Term<span class="error_msg">*</span> </label>
								<div class="controls controls-row">
								  <spring:bind path="dealSheet.orders[${i-1}].term" >
									<select class="input-large" id="term${i-1}" name="${status.expression}" style="width:100%" >
											<option value="3" ${order.term eq '3' ? 'selected' : '' }>3</option>
											<option value="6" ${order.term eq '6' ? 'selected' : '' }>6</option>
											<option value="9" ${order.term eq '9' ? 'selected' : '' }>9</option>
											<option value="12" ${order.term eq '12' ? 'selected' : '' }>12</option>
											<option value="18" ${order.term eq '18' ? 'selected' : '' }>18</option>
											<option value="24" ${order.term eq '24' ? 'selected' : '' }>24</option>
										<!-- Added by Jeevan on April 01, 2014 as per Clients Modifications -->	
											<c:forEach var="termcount" begin="25" end="36" step="1">
												<option value="${termcount}" ${order.term eq termcount ? 'selected' : '' }>${termcount }</option>
											</c:forEach>	
										<!-- Added by Jeevan on April 01, 2014 as per Clients Modifications -->		
					                        <option value="1" ${order.term eq '1' ? 'selected' : '' } >Index </option>
									</select>
							</spring:bind>
								</div>
							</div>
					  </div>
					  <div class="span3 startdate2" style="width:10%;margin-left: 2%;">
					   
						<div class="control-group">
							<label class="control-label" for="textfield">Rate Class <span class="error_msg"></span></label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[${i-1 }].rateClass">
								<input id="rateClass${i-1 }" name="${status.expression}"  type="text" class="input-block-level" style="width:100%" value="${order.rateClass}"/>
							  </spring:bind>
							</div>
						</div>
					</div>
					  
					  <div class="span3 startdate2" style="width:10%;margin-left:2%;">
					   <div style="margin-top: -25px;height: 23px;"><span id="rate_error${i-1 }" class="error_msg"></span> </div>  
						<div class="control-group" style="margin-top: -3px;">
							<label class="control-label" for="textfield">Rate<span class="error_msg">*</span>
								<spring:bind path="dealSheet.orders[${i-1 }].specialPricing">
								  <c:choose>
								  	<c:when test="${order.specialPricing eq true }">
								  			<input type="checkbox"  name="${status.expression}" id="specialpricing${i-1 }"  style="margin-top: -1px;"  index="${i-1 }" onclick="higlightRate(this.id);" checked="checked"  />
								  	</c:when>
								  	<c:otherwise>
								  		<input type="checkbox"  name="${status.expression}" id="specialpricing${i-1 }"  style="margin-top: -1px;"  index="${i-1 }" onclick="higlightRate(this.id);"  />
								  	</c:otherwise>
								  </c:choose>
							 		
							 		 <span style="font-size: 12px;" class="sprice">Special</span>
						   		</spring:bind>
						   	</label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[${i-1 }].rate">
								<input id="rate${i-1 }" name="${status.expression}"  type="text" class="input-block-level" style="width:100%" value="${order.rate }" />
							  </spring:bind>
							</div>
						</div>
					</div>
					
					 <div class="startdate3">
					  <div style="margin-top: -21px;height: 23px;"><span id="startdate_error${i-1 }" class="error_msg"></span> </div>  
						<div class="control-group" style="margin-top: -3px;">
							<label class="control-label" for="textfield">Start Date<span id="startdate_error${i-1 }" class="error_msg">*</span></label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[${i-1 }].dealStartDate">
								<input id="startDate${i-1 }" name="${status.expression}"  type="text" class="input-block-level datepicker strDate" style="width:80%;font-size: 12px;padding:2px;" readonly="readonly"  value="<fmt:formatDate value='${order.dealStartDate}'  pattern='MM/dd/yyyy'/>"/>
							  </spring:bind>
							</div>
						</div>
					</div>
                                                             
					</div>
                                    
                    <div class="row-fluid" style="margin-left:15px;">
                    	<div class="span3" style="width: 37%;">
							<div class="control-group">
								<label class="control-label" for="textfield">Business Name <span style="font-size: 12px;font-family: arial;font-weight: normal;">(on the bill)</span><span id="bname_error${i-1 }" class="error_msg">*</span>
								  <c:if test="${i gt 1 }">
								  	<input type="checkbox" name="bname_chk" value="checkbox"  id="bname_chk${i-1}" style="margin-left: 2px;margin-top: -2px;" index="${i-1}" onclick="copybusinessname(this.id);" class="chkbox" />
						      			<span  class="chkbox"  > Same as Previous</span>
								  
								  </c:if>
								
								
								</label>
								<div class="controls controls-row">
								   <spring:bind path="dealSheet.orders[${i-1 }].businessName">
									<input type="text" class="input-block-level buss" id="businessname${i-1 }" name="${status.expression}"  style="width:100%;" value="${order.businessName}"/>
								</spring:bind>
								</div>
							</div>
						</div>
						
						<div class="span3" style="margin-left: 2%;width: 17%;">
						<div class="control-group">
							<label class="control-label" for="textfield">DBA <span class="chkbox">(If Applicable)</span>
							<c:if test="${i gt 1 }">
								<input type="checkbox" name="dba_chk" value="checkbox"  id="dba_chk${i-1}" style="margin-left: 2px;margin-top: -2px;" index="${i-1}" onclick="copydba(this.id);"  class="chkbox" />
			      					<span  class="chkbox"  > Same as Previous</span>
							</c:if>
							 </label>
							<div class="controls controls-row">
								<spring:bind path="dealSheet.orders[${i-1 }].dba">
								<input  id="dba${i-1 }" name="${status.expression}" type="text" class="input-block-level" style="width: 95%;"  value="${order.dba}"/>
								</spring:bind>
							</div>
						</div>
					  </div>
                    
                    
                     <div class="span3" style="margin-left: 1%;width: 11%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">TPV#<span id="tpv_error${i-1 }" class="error_msg">*</span>
							<c:if test="${i gt 1 }">
								 <input type="checkbox" name="tpv_chk" value="checkbox"  id="tpv_chk${i-1}" style="margin-left: 2px;margin-top: -2px;" index="${i-1}" onclick="copyTpv(this.id);" class="chkbox" />
			      					<span class="chkbox" >Previous</span>
							</c:if>
							
							</label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[${i-1 }].tpv">
								<input id="tpv${i-1 }" name="${status.expression}" type="text" class="input-block-level tpv" style="width: 88%;" value="${order.tpv}">
								</spring:bind>
							</div>
						</div>
					</div>  
					
					<div class="span3 county" style="margin-left: 0.5%;width: 14%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">County<span id="county_error${i-1}" class="error_msg"></span></label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[${i-1 }].county">
								<input id="county${i-1 }" name="${status.expression}" type="text" class="input-block-level " style="width: 88%;" value="${order.county}">
								</spring:bind>
							</div>
						</div>
					</div>     
					
					<div class="span3 meter" style="margin-left: 0%;width: 11%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">Meter Date<span id="meter_error${i-1 }" class="error_msg"></span></label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[${i-1 }].meterReadDate">
								<input id="meter${i-1 }" name="${status.expression}" type="text" class="input-block-level datepicker" style="width: 80%;font-size: 12px;padding: 2px;" readonly="readonly"
									 value="<fmt:formatDate value='${order.meterReadDate}'  pattern='MM/dd/yyyy'/>" >
								</spring:bind>
							</div>
						</div>
					</div>     
					
					                                        
				</div>
                                    
                 <div class="row-fluid" style="margin-left:15px; ">
                    <div class="span4" style="width:37%;">
						<div class="control-group">
							<label class="control-label" for="textfield">Service (Street)<span id="s_street_error${i-1 }" class="error_msg">*</span>
								<c:if test="${i gt 1 }">
									 <input type="checkbox" name="sadd_chk" value="checkbox"  id="sadd_chk${i-1}" style="margin-left: 5px;margin-top: -2px;"  index="${i-1}" onclick="copyserviceaddress(this.id);" class="chkbox" />
			      					<span class="chkbox"  > Same as Previous</span>
								</c:if>
							
							
							</label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[${i-1 }].serviceStreet">
								<input id="s_street${i-1 }" name="${status.expression}"  type="text" class="input-block-level street" style="width:100%;" value="${order.serviceStreet}" />
								</spring:bind>
							</div>
						</div>
					</div>
					<div class="span2" style="margin-left: 2% ;">
						<div class="control-group">
							<label class="control-label add" for="textfield">Unit<span id="s_unit_error" class="error_msg"></span></label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[${i-1 }].serviceUnit">
								<input type="text" class="input-block-level addr" id="s_unit${i-1 }" name="${status.expression}" value="${order.serviceUnit}" />
							   </spring:bind>
							</div>
						</div>
					</div>
					<div class="span2" style="margin-left:0%;">
						<div class="control-group">
							<label class="control-label add" for="textfield">City<span id="s_city_error${i-1 }" class="error_msg">*</span></label>
						<div class="controls controls-row">
						  <spring:bind path="dealSheet.orders[${i-1 }].serviceCity">
							<input type="text" class="input-block-level addr" id="s_city${i-1 }" name="${status.expression}"  value="${order.serviceCity}" />
							</spring:bind>
						</div>
					</div>
				 </div>
                 <div class="span2" style="margin-left:0%;width: 13%;">
					<div class="control-group">
						<label class="control-label add" for="textfield">State<span id="s_state_error${i-1 }" class="error_msg">*</span></label>
						<div class="controls controls-row">
						  <spring:bind path="dealSheet.orders[${i-1 }].serviceState.state">
							<select class="input-large1 addr" id="s_state${i-1 }" name="${status.expression}" style="width:78%;">
							
							<c:forEach var="state" items="${states}">
								<option value="${state.state}"  ${state.state eq order.serviceState.state ? 'selected' : ''}  >${state.state}</option>
							</c:forEach>
								
		                       <!--  <option value="AR"  selected="selected">AR</option>
		                        <option value="AZ">AZ</option>
		                         <option value="CA">CA</option>		                        
		                        <option value="DE">DE</option>
		                         <option value="IL">IL</option>
		                         <option value="MA">MA</option>		                        
		                        <option value="MD">MD</option>
		                         <option value="MI">MI</option>		                        
		                        <option value="MT">MT</option>
		                          <option value="NJ">NJ</option>		                        
		                        <option value="NM">NM</option>
		                          <option value="NV">NV</option>		                        
		                        <option value="NY">NY</option>		                       
		                        <option value="OH">OH</option>
		                          <option value="OK">OK</option>		                        
		                        <option value="OR">OR</option>
		                        <option value="PA">PA</option>
		                          <option value="RI">RI</option>		                        
		                        <option value="TX">TX</option>		                         	                        
		                        <option value="VA">VA</option> -->
	                      </select>
	                      </spring:bind>
					</div>
				 </div>
				</div>         
                <div class="span2" style="margin-left:0%;">
					<div class="control-group">
						<label class="control-label add" for="textfield">Zip<span id="s_zip_error${i-1 }" class="error_msg">*</span></label>
						<div class="controls controls-row">
						     <spring:bind path="dealSheet.orders[${i-1 }].serviceZip">
							<input type="text" class="input-block-level addr1" id="s_zip${i-1 }" name="${status.expression}" value="${order.serviceZip}">
						 </spring:bind>
						</div>
					</div>
				</div>                                 
			</div>
                                    
         <div class="row-fluid" style="margin-left:15px;">
           <div class="span4" style="width: 37%;">
				<div class="control-group">
					<label class="control-label" for="textfield">Billing (Street) &nbsp;  <span id="b_street_error${i-1 }" class="error_msg">*</span>
						 <c:if test="${i gt 1 }">
					  	 <input type="checkbox" name="prevadd_chk" value="checkbox"  id="prevadd_chk${i-1}" style="margin-left: 0px;margin-top: -2px;" onclick="copybillingaddress(this.id);" index="${i-1}"  class="chkbox" />
					    		  <span class="chkbox" > Same as Previous</span>
					  </c:if>
					
						
						<input type="checkbox" name="add_chk" value="checkbox"  id="add_chk${i-1 }" style="margin-top:-2px;" index="${i-1 }" onclick="copyadd(this.id);"  class="chkbox"/>
					  <span  class="chkbox">Same as Service </span>
					 
					 </label>
					<div class="controls controls-row">
					   <spring:bind path="dealSheet.orders[${i-1 }].billingStreet">
						<input type="text" class="input-block-level street" id="b_street${i-1 }" name="${status.expression }" style="width: 100%;" value="${order.billingStreet}" >
						</spring:bind>
					</div>
				</div>
		</div>
		<div class="span2" style="margin-left: 2%;">
			<div class="control-group" >
				<label class="control-label add" for="textfield">Unit<span id="b_unit_error" class="error_msg"></span></label>
				<div class="controls controls-row">
				 <spring:bind path="dealSheet.orders[${i-1 }].billingUnit">
					<input type="text" class="input-block-level addr" id="b_unit${i-1 }" name="${status.expression}" value="${order.billingUnit}" />
				 </spring:bind>
				</div>
			</div>
		</div>
		<div class="span2" style="margin-left: 0%;">
			<div class="control-group">
				<label class="control-label add" for="textfield">City<span id="b_city_error${i-1 }" class="error_msg">*</span></label>
				<div class="controls controls-row">
				 <spring:bind path="dealSheet.orders[${i-1 }].billingCity">
					<input type="text" class="input-block-level addr"  id="b_city${i-1 }" name="${status.expression}" value="${order.billingCity}" />
					</spring:bind>
				</div>
			</div>
		</div>
         <div class="span2" style="margin-left: 0%;width: 13%;">
			<div class="control-group">
				<label class="control-label add" for="textfield">State<span id="b_state_error${i-1 }" class="error_msg">*</span></label>
				<div class="controls controls-row">
				  <spring:bind path="dealSheet.orders[${i-1 }].billingState.state">
					<select class="input-large1 addr" id="b_state${i-1 }" name="${status.expression}" style="width:78%;">
						    <!-- option value="AR"  selected="selected">AR</option>
		                        <option value="AZ">AZ</option>
		                         <option value="CA">CA</option>		                        
		                        <option value="DE">DE</option>
		                         <option value="IL">IL</option>
		                         <option value="MA">MA</option>		                        
		                        <option value="MD">MD</option>
		                         <option value="MI">MI</option>		                        
		                        <option value="MT">MT</option>
		                          <option value="NJ">NJ</option>		                        
		                        <option value="NM">NM</option>
		                          <option value="NV">NV</option>		                        
		                        <option value="NY">NY</option>		                       
		                        <option value="OH">OH</option>
		                          <option value="OK">OK</option>		                        
		                        <option value="OR">OR</option>
		                        <option value="PA">PA</option>
		                          <option value="RI">RI</option>		                        
		                        <option value="TX">TX</option>		                         	                        
		                        <option value="VA">VA</option> -->
		                        
		                        <c:forEach var="state" items="${billingStates}">
								<option value="${state.state}"   ${state.state eq order.billingState.state ? 'selected' : ''}>${state.state}</option>
							</c:forEach>
		                        </select>
		                       </spring:bind>
				</div>
			</div>
		</div>         
         <div class="span2" style="margin-left: 0%;">
			<div class="control-group">
				<label class="control-label add" for="textfield">Zip<span id="b_zip_error${i-1 }" class="error_msg">*</span></label>
				<div class="controls controls-row" style="margin-left: 1px;">
				    <spring:bind path="dealSheet.orders[${i-1 }].billingZip">
					<input type="text" class="input-block-level addr1"  id="b_zip${i-1 }" name="${status.expression }" value="${order.billingZip}">
					</spring:bind>
				</div>
			</div>
		</div>                                 
	</div>
		<input type="hidden" id="acccount" value="${i}" />	
       <c:set var="i" value="${i+1 }"></c:set>     	
</c:forEach>
	 <input type="hidden" name="acc_count" id="acc_count" value="${i-1 }" />
	  	                             
      <div class="row-fluid" style="margin-left:15px;" id="bottom_link1">
           <div class="span12">
			<div class="control-group">
				<label class="control-label" for="textfield">Comments</label>
				<div class="controls controls-row">
				   <spring:bind path="dealSheet.orders[0].agentNotes">
				      <textarea cols="10" rows="3" class="input-block-level comments" id="agentNotes" name="${status.expression}" style="width:80%;text-align: left;min-height: 150px;">${orders[0].agentNotes}</textarea>
					 <%-- <input type="text" class="input-block-level" id="agentNotes" name="${status.expression }"> --%>
				   </spring:bind>	
				</div>
			</div>
		</div>
     </div>
      <div class="row-fluid officenotes" style="margin-left:15px;text-align: center;">
	      	
	         <div class="span9">
				<div class="control-group" >
					<div class="controls controls-row" style="margin-left: 125px;">
						<button class="btn-update btn" style="margin-top:25px;" id="editBtn" type="submit"  >Edit Deal</button>&nbsp;&nbsp;&nbsp;
						<a href="#" class="btn-success btn" style="margin-top:25px;" id="editAddBtn"  >Add Account</a>&nbsp;&nbsp;&nbsp;
						<a href="#" class="btn-undo btn" style="margin-top:25px;display: none;" id="editRemoveBtn">Remove Account</a>&nbsp;&nbsp;
						<a href="/hoveyenergy/admin/viewdealsheets.do" class="btn-back btn" style="margin-top:25px;">Back</a>
					</div>
				</div>
			</div>
	    </div>
     </form:form>
   </div>
  </div>
 </div>
    	<c:if test="${not empty param.message}" >
    				<script>
    					alert("Deal Updated Successfully");
    				</script>
    		</c:if>
    
         <c:if test="${not empty param.error}" >
    				<script>
    					alert("Failed to Update Deal, One or More Accounts have Same Account# and Deal Start Date");
    				</script>
    		</c:if>
    
    
    
  
    
    </div>


 


 


</body>
</html>