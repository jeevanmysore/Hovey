<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />

<c:set var="username" value="${sessionScope.username}" scope= "session" />


<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<title>Deal Sheet</title>
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
		 <link  href="/hoveyenergy/css/dealsheet.css" rel="stylesheet" type="text/css">
		<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	 <script type="text/javascript" src="/hoveyenergy/js/dealsheet.js"></script>
	 
	 <!--[if lt IE 10]>		
		<link rel="stylesheet" href="css/dealsheet_ie.css" type="text/css" />		
	 <![endif]-->
	 <script>
	 $(function() {
		var date1=1;
		 
		/*  $('.ui-datepicker-calender').hide();
			$( ".datepicker" ).datepicker({
			
			showOn: "button",
			buttonImage: "images/calendar.gif",
			buttonImageOnly: true,
			 dateFormat: 'MM yy',
			 showButtonPanel: true,
			 changeYear: true,
		        changeMonth: true,
		        yearRange: '2012:c+10',
		        onChangeMonthYear: function(dateText, inst) { 
		            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
		            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
		            $(this).datepicker('setDate', new Date(year, month, 1));
		        }
				
			}); */
		 $('.datepicker').datepicker( {
			 	showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
		        changeMonth: true,
		        changeYear: true,
		        showButtonPanel: true,
		        dateFormat: 'mm/dd/yy',
		        yearRange: '2012:c+10',
		        onSelect:function(dateText,inst){
					   date=1;
					  date=$(this).datepicker('getDate').getDate();
				   },
		        onClose: function(dateText, inst) { 
		            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
		            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
		            var curDate = $(this).datepicker("getDate");
		            if(null!=curDate  ){			            	
	            		 $(this).datepicker('setDate', new Date(year, month,date));	            					 				            	
	          		 }
	           	   else{
	            		$(this).datepicker('setDate', new Date(year, month, 1));
	            	}
	          		 date=1;
		        }
		    });
	});
			
	 	
	 $(document).ready(function(){
	 
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
			
		
		
		
	 </style>
	 
	
</head>

<body class="firefox" style="height: auto;">


        <!-- Top Header start -->
              
            <!-- Top Header end -->
		<form action="/hoveyenergy/logout.do" method="get" accept-charset="utf-8" id="" name="" class="">    
			<div style="float: right;text-align: left; margin: 5px auto;margin-right: 4%;">	
				<div id="user" style="text-transform:capitalize;margin:0 auto;">Hi ${user.firstName} <button type="submit" class="btn" id="logout">LOG OUT</button>
           		 </div>
            
					<div style="float: right;clear:both;">
					  <div style="margin-top: 18px; text-align:center;" id="clearBtn">
           				<button type="button" class="btn btn-success" style="font-size: 16px;padding:4px; ">Clear All Fields</button>
         			 </div>
				</div>
            </div>
        </form>
	
	<div id="header" style="margin-top: -59px;">
    	<div id="logo">
     	 	   <a href="/hoveyenergy/home.do">
     	 			<img src="/hoveyenergy/images/logo.png" alt="logo" height="76" width="275" />
     	 		</a>
     	 	</div>
           
	    
    </div>
     <div id="menubar" style="width: 94%;padding: 0 2%;float: left;height: auto;" class="noPrint">
			<div style="width: 100%;">
				<ul id="menu">
					
					<li style="margin-left:77px;"><a href="/hoveyenergy/agent/dealsheet.do">Create Order</a></li>
						
					
					
				</ul>
			</div>
	</div>
    
    
    
  <div id="content" style="margin-left: 2.12%;margin-bottom: 5px;"><!-- --- CONTENT BLOCK ---  -->
  
    
		<div id="feedback1" style="text-align: center;font-size: 18px;font-weight: bold;color: green" >
    		
    	</div>
  		<div class="title" style="text-decoration:none!important;">ORDER ENTRY</div>
  		
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
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
	<div id="outerContainer">		
		<div id="form_container">
    		<div class="box-content">
				<form:form method="post" id="dealsheet" name="dealsheet" class="appnitro" action="/hoveyenergy/agent/dealsheet.do" commandName="dealSheet"  autocomplete="off" >	
                 	<div class="row-fluid">
                  		 <div style="font-family:Arial, Helvetica, sans-serif; background:#999999; color:#FFF; height:25px; font-size:15px; font-weight:bold;" class="stitle">
                          	<p style="padding:3px;">Agent Info</p>
                         </div>
                          	
                          	
                          		<div class="span3 agnt1">
									<div class="control-group">
										<label class="control-label" for="textfield" >Date Time</label>
										<div class="controls controls-row">
										 	 <spring:bind path="dealSheet.orders[0].orderDate">
												<input type="text" name="${status.expression}" class="input-block-level " id="orderDate" value="<fmt:formatDate value="${now}" pattern="MM/dd/yyyy HH:mm:ss" />"  >
											</spring:bind>
										</div>
									</div>
								</div>
                          	
								<div class="span3 agnt">
									<div class="control-group">
										<label class="control-label" for="textfield">Agent Name</label>
										<div class="controls controls-row">
											<input type="text" class="input-block-level"   value="${user.firstName} ${user.lastName}" readonly="readonly">
										</div>
									</div>
								</div>
								<div class="span3 agnt">
									<div class="control-group">
										<label class="control-label" for="textfield">Agent Number</label>
										<div class="controls controls-row">
											<input type="text" class="input-block-level"  value="${user.agentNumber}" readonly="readonly" >
										</div>
									</div>
								</div>
								
								
								<div class="span3 agnt">
									<div class="control-group">
										<label class="control-label" for="textfield">Supplier</label>
										<div class="controls controls-row">
										   <spring:bind path="dealSheet.orders[0].supplierName.supplierName">
											<select class="input-large" id="supplier" name="${status.expression}" style="width:80%;">
											 <c:forEach var="supplier" items="${suppliers}">
											 	<option value="${supplier.supplierName}">${supplier.supplierName}</option>
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
                              				 <input type="checkbox" name="cust_check"  id="cust_check" value="1"  style="margin-top: -1px;margin-left: 51px;" />
										<span  class="exCust service" > Click Here for Existing customer</span></p>
							</div>
							<input type="hidden" name="customer.customerId" id="custid" value="0" />
							<div class="span3 cmn1" style="width:18%;margin-left: 15px;">
								<div class="control-group">
									<label class="control-label" for="textfield">First Name<span id="custname_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										
										<input id="custname" name="customer.firstName"" class="input-block-level" type="text" style="width:90%;" value="${dealSheet.customer.firstName }" />
									</div>
								</div>
							</div>
							<div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Last Name <span id="custname2_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input id="custname2" name="customer.lastName" type="text" class="input-block-level" style="width:90%;" />
									</div>
								</div>
							</div>
							<div class="span3 cmn" style="width:18%;">
								<div class="control-group" >
									<label class="control-label" for="textfield">Title<span id="custtitle_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
									<input id="custtitle" name="customer.title"  type="text" class="input-block-level" style="width:90%;" />
									</div>
								</div>
							</div>
                             <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Phone Number<span id="custphone_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input id="custphone" name="customer.phoneNo" type="text" class="input-block-level" style="width:90%;" />
									</div>
								</div>
							</div>
							 <div class="span3 txex" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Service Type</label>
									<div class="controls controls-row">
										<select class="input-large" id="comres" name="customer.type" style="width:88%;">
											<option value="commercial" selected="selected" >Commercial</option>
											<option value="residential" >Residential</option>
										</select>
									</div>
								</div>
							</div>
                            <div class="span3 cmn1" style="clear:both;width:18%;margin-left: 15px;">
								<div class="control-group">
									<label class="control-label" for="textfield">Email<span id="custemail_error" class="error_msg"></span></label>
									<div class="controls controls-row">
										<input id="email" name="customer.email"  type="text" class="input-block-level lowfont" style="width:90%;text-transform: none;" >
									</div>
								</div>
							</div>
                            <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Fax<span id="fax_error" class="error_msg"></span></label>
									<div class="controls controls-row">
										<input id="fax" name="customer.faxNo" class="input-block-level" type="text" style="width:90%;"/>
									</div>
								</div>
							</div>
                            <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Tax ID<span id="custtax_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input id="taxid" name="customer.taxId"  type="text" class="input-block-level" style="width:90%;text-transform: none;" />
									</div>
								</div>
							</div>
                            <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Tax Exempt (Y/N)</label>
									<div class="controls controls-row">
										<select class="input-large" id="taxexempt" name="customer.taxExempt" style="width:90%;">
												 <option value="na" selected="selected">N/A</option>
				                          <option value="yes">Yes</option>
				                          <option value="no" >No</option>	
										</select>
									</div>
								</div>
							</div>
							 <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Fronter<span id="fronter_error" class="error_msg"></span></label>
									<div class="controls controls-row">
										<input id="fronter" name="customer.fronter" class="input-block-level" type="text" style="width:90%;"/>
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
                        <div class="accTitle">Account #1
                        	<div style="display: inline;margin-left: 55px;" >
							 	<input type="checkbox" name="electric" checked="checked" value="electric"  class="svcchk" index="0" id="electric0" service="electric"  onclick="getservice(this.id);"/> <span class="service" > Electric</span>
							 	<input type="checkbox" name="gas" value="gas"  class="svcchk" style="margin-left: 50px;" index="0" id="gas0" service="gas"  onclick="getservice(this.id);"/> <span class="service" >Gas</span>
							 	<spring:bind path="dealSheet.orders[0].service">
							 		<input type="hidden" name="${status.expression}" id="service0" index="0" value="electric" />
							 	</spring:bind>
							 	
							 </div>
							 <input type="checkbox" name="contract"   class="svcchk" style="margin-left: 150px;" index="0" id="contract0"  onclick="changecontract(this.id);"/> <span class="service" >Renewal</span>
							 <spring:bind path="dealSheet.orders[0].contractType">
							 	<input type="hidden" name="${status.expression}" id="ctype0" value="new"  />
							 </spring:bind>
                        </div>
						 
						 <spring:bind path="dealSheet.orders[0].id">
						 	<input type="hidden" id="orderId0" name="${status.expression}" />
						 </spring:bind>
					  
						<div class="span3 util" style="margin-left: 15px;width:10.2%;">
							<div class="control-group">
								<label class="control-label" for="textfield">Utility<span id="utiltiy0_error" class="error_msg">*</span></label>
								<div class="controls controls-row">
								  <spring:bind path="dealSheet.orders[0].utility.utility">
									<select class="input-large " id="utility0" name="${status.expression}" style="width:100%;margin-top: 5px;">
										<c:forEach var="util" items="${utilities}">
			                        	 <option value="${util.utility}" ${util.utility eq 'AEP' ? 'selected' : '' }>${util.utility}</option>
			                        </c:forEach>	
									</select>
								  </spring:bind>
								</div>
							</div>
						</div>
						
						
						<div class="span3" style="margin-left: 3%;width: 19%;" >
							 <%-- <div style="margin-top: -25px;height: 23px;">
							 	<input type="checkbox" name="electric" checked="checked" value="electric" style="margin-top: -3px;" /> <span class="service" > Electric</span>
							 	<input type="checkbox" name="gas" value="gas"  style="margin: -3px 0px 0px 5px;" /> <span class="service" >Gas</span>
							 	<spring:bind path="dealSheet.orders[0]">
							 		<input type="hidden" name="${status.expression}" id="service0" index="0" />
							 	</spring:bind>
							 </div> 
							 --%>
							<div class="control-group">
								<label class="control-label" for="textfield" style="margin-top: -1px;">Account Number<span id="acc_error0" class="error_msg">*</span> </label>
								   <div class="accsize"><span id="acc1_error0" class="error_msg"></span></div>
									<div class="controls controls-row">
									  <spring:bind path="dealSheet.orders[0].accountNumber">
										<input type="text" id="account0" name="${status.expression}" class="input-block-level acc" index="0" onblur="validateaccountsize(this.id)" style="width: 98%;" / >
									</spring:bind>
									</div>
							</div>
						</div>
						
						
                    	<div class="span3" style="width:12.6%;margin-left: 2%;">
                    		 <div style="margin-top: -25px;height: 23px;"><span id="kwh_error0" class="error_msg"></span></div>	
							<div class="control-group">
								<label class="control-label" for="textfield" ><span id="svcLbl0">kWh</span>  <span class="chkbox"> (Annual)</span><span class="error_msg">*</span> </label>
								<div class="controls controls-row">
								   <spring:bind path="dealSheet.orders[0].kwh">
									<input id="kwh0" name="${status.expression}" type="text" class="input-block-level" style="width:100%"/>
								   </spring:bind>
								</div>
							</div>
						</div>      
                    
                    
                    
                       <div class="span3 startdate2" style="width:8%;margin-left: 2%;">  
                            <div style="margin-top: -25px;height: 23px;"><span id="term_error0" class="error_msg"></span> </div>                       
							<div class="control-group">
								<label class="control-label" for="textfield">Term<span class="error_msg">*</span> </label>
								<div class="controls controls-row">
								  <spring:bind path="dealSheet.orders[0].term" >
									<select class="input-large" id="term0" name="${status.expression}" style="width:100%">
										<option value=""selected="selected"></option>
										 <option value="3" >3</option>
										  <option value="6" >6</option>
										   <option value="9" >9</option>
										  <option value="12" >12</option>
					                        <option value="18">18</option>
					                        <option value="24">24</option>
					                       <!-- Added by Jeevan on April 01, 2014 as per Clients Modfications -->
					                        <c:forEach var="termcount" begin="25" end="36" step="1">
					                        	   <option value="${termcount }">${termcount }</option>					                        
					                        </c:forEach>
					                        <!-- Added by Jeevan on April 01, 2014 as per Clients Modfications --> 					                      
					                        <option value="1">Index </option>
									</select>
								</spring:bind>
								</div>
							</div>
					  </div>
					  <div class="span3 startdate2" style="width:10%;margin-left: 2%;">
					   <div style="margin-top: -25px;height: 23px;"><span id="rateClass_error0" class="error_msg"></span> </div>  
						<div class="control-group">
							<label class="control-label" for="textfield">Rate Class <span class="error_msg"></span></label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[0].rateClass">
								<input id="rateClass0" name="${status.expression}"  type="text" class="input-block-level" style="width:100%"/>
							  </spring:bind>
							</div>
						</div>
					</div>
					  
					  <div class="span3 startdate2" style="width:10%;margin-left:2%;">
					   <div style="margin-top: -25px;height: 23px;"><span id="rate_error0" class="error_msg"></span> </div>  
						<div class="control-group" style="margin-top: -3px;">
							<label class="control-label" for="textfield">Rate<span class="error_msg">*</span>
								<spring:bind path="dealSheet.orders[0].specialPricing">
							 		<input type="checkbox"  name="${status.expression}" id="specialpricing0"  style="margin-top: -1px;"  index="0" onclick="higlightRate(this.id);"  />
							 		 <span style="font-size: 12px;" class="sprice">Special</span>
						   		</spring:bind>
						   	</label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[0].rate">
								<input id="rate0" name="${status.expression}"  type="text" class="input-block-level" style="width:100%" />
							  </spring:bind>
							</div>
						</div>
					</div>
					
					 <div class="startdate3">
					  <div style="margin-top: -21px;height: 23px;"><span id="startdate_error0" class="error_msg"></span> </div>  
						<div class="control-group" style="margin-top: -3px;">
							<label class="control-label" for="textfield">Start Date<span id="startdate_error0" class="error_msg">*</span></label>
							<div class="controls controls-row">
							 <!-- Modified by Jeevan on july 18,2013 from startDate(String) to DealStartDate as Date format is Changed according to Client Request  -->
							  <spring:bind path="dealSheet.orders[0].dealStartDate">
							<!--  Modification complete -->
								<input id="startDate0" name="${status.expression}"  type="text" class="input-block-level datepicker strDate" style="width:80%;font-size: 12px;padding:2px;" readonly="readonly"  />
							  </spring:bind>
							</div>
						</div>
					</div>
                                                             
					</div>
                                    
                    <div class="row-fluid" style="margin-left:15px;">
                    	<div class="span3" style="width: 37%;">
							<div class="control-group">
								<label class="control-label" for="textfield">Business Name <span style="font-size: 12px;font-family: arial;font-weight: normal;">(on the bill)</span><span id="bname_error0" class="error_msg">*</span></label>
								<div class="controls controls-row">
								   <spring:bind path="dealSheet.orders[0].businessName">
									<input type="text" class="input-block-level buss" id="businessname0" name="${status.expression}"  style="width:100%;" />
								</spring:bind>
								</div>
							</div>
						</div>
						<div class="span3 dba" style="margin-left: 2%;width: 17%;">
						<div class="control-group">
							<label class="control-label" for="textfield">DBA <span class="chkbox">(If Applicable)</span> </label>
							<div class="controls controls-row">
								<spring:bind path="dealSheet.orders[0].dba">
								<input  id="dba0" name="${status.expression}" type="text" class="input-block-level lowfont" style="width: 95%;" />
								</spring:bind>
							</div>
						</div>
					  </div>
                    
                    
                     <div class="span3" style="margin-left: 1%;width: 11%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">TPV Number<span id="tpv_error0" class="error_msg">*</span></label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[0].tpv">
								<input id="tpv0" name="${status.expression}" type="text" class="input-block-level tpv" style="width: 88%;">
								</spring:bind>
							</div>
						</div>
					</div>     
					
					<div class="span3 county" style="margin-left: 0.5%;width: 14%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">County<span id="county_error0" class="error_msg"></span></label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[0].county">
								<input id="county0" name="${status.expression}" type="text" class="input-block-level " style="width: 88%;">
								</spring:bind>
							</div>
						</div>
					</div>     
					
					<div class="span3 meter" style="margin-left: 0%;width: 11%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">Meter Date<span id="meter_error0" class="error_msg"></span></label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[0].meterReadDate">
								<input id="meter0" name="${status.expression}" type="text" class="input-block-level datepicker" style="width: 80%;font-size: 12px;padding: 2px;" readonly="readonly" >
								</spring:bind>
							</div>
						</div>
					</div>     
					                                     
				</div>
                                    
                 <div class="row-fluid " style="margin-left:15px; ">
                    <div class="span4 street1" style="width:37%;">
						<div class="control-group">
							<label class="control-label" for="textfield">Service (Street)<span id="s_street_error0" class="error_msg">*</span></label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[0].serviceStreet">
								<input id="s_street0" name="${status.expression}"  type="text" class="input-block-level street" style="width:100%;" />
								</spring:bind>
							</div>
						</div>
					</div>
					<div class="span2 unit" style="margin-left: 2% ;">
						<div class="control-group">
							<label class="control-label add" for="textfield">Unit<span id="s_unit_error" class="error_msg"></span></label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[0].serviceUnit">
								<input type="text" class="input-block-level addr" id="s_unit0" name="${status.expression}" />
							   </spring:bind>
							</div>
						</div>
					</div>
					<div class="span2" style="margin-left:0%;">
						<div class="control-group">
							<label class="control-label add" for="textfield">City<span id="s_city_error0" class="error_msg">*</span></label>
						<div class="controls controls-row">
						  <spring:bind path="dealSheet.orders[0].serviceCity">
							<input type="text" class="input-block-level addr" id="s_city0" name="${status.expression}"  />
							</spring:bind>
						</div>
					</div>
				 </div>
                 <div class="span2 state2" style="margin-left:0%;width: 13%;">
					<div class="control-group">
						<label class="control-label add" for="textfield">State<span id="s_state_error0" class="error_msg">*</span></label>
						<div class="controls controls-row">
						  <spring:bind path="dealSheet.orders[0].serviceState.state">
							<select class="input-large1 addr" id="s_state0" name="${status.expression}" style="width:78%;">							
							<c:forEach var="state" items="${states}">
								<option value="${state.state}"  ${state.state eq 'OH' ? 'selected' : ''}  >${state.state}</option>
							</c:forEach>
	                      </select>
	                      </spring:bind>
					</div>
				 </div>
				</div>         
                <div class="span2" style="margin-left:0%;">
					<div class="control-group">
						<label class="control-label add" for="textfield">Zip<span id="s_zip_error0" class="error_msg">*</span></label>
						<div class="controls controls-row">
						     <spring:bind path="dealSheet.orders[0].serviceZip">
							<input type="text" class="input-block-level addr1" id="s_zip0" name="${status.expression}">
						 </spring:bind>
						</div>
					</div>
				</div>                                 
			</div>
                                    
         <div class="row-fluid" style="margin-left:15px;">
           <div class="span4 street1" style="width: 37%;">
				<div class="control-group">
					<label class="control-label" for="textfield">Billing (Street) &nbsp;
						<input type="checkbox" name="add_chk" value="checkbox"  id="add_chk0" style="margin-top:-2px;" index="0" onclick="copyadd(this.id);"  class="chkbox"/>
					  <span  class="chkbox">Same as Service </span>
					  <span id="b_street_error0" class="error_msg">*</span>
					 </label>
					<div class="controls controls-row">
					   <spring:bind path="dealSheet.orders[0].billingStreet">
						<input type="text" class="input-block-level street" id="b_street0" name="${status.expression }" style="width: 100%;" >
						</spring:bind>
					</div>
				</div>
		  </div>
		<div class="span2 unit" style="margin-left: 2%;">
			<div class="control-group" >
				<label class="control-label add" for="textfield">Unit<span id="b_unit_error" class="error_msg"></span></label>
				<div class="controls controls-row">
				 <spring:bind path="dealSheet.orders[0].billingUnit">
					<input type="text" class="input-block-level addr" id="b_unit0" name="${status.expression}" />
				 </spring:bind>
				</div>
			</div>
		</div>
		<div class="span2" style="margin-left: 0%;">
			<div class="control-group">
				<label class="control-label add" for="textfield">City<span id="b_city_error0" class="error_msg">*</span></label>
				<div class="controls controls-row">
				 <spring:bind path="dealSheet.orders[0].billingCity">
					<input type="text" class="input-block-level addr"  id="b_city0" name="${status.expression}" />
					</spring:bind>
				</div>
			</div>
		</div>
         <div class="span2 state2" style="margin-left: 0%;width: 13%;">
			<div class="control-group">
				<label class="control-label add" for="textfield">State<span id="b_state_error0" class="error_msg">*</span></label>
				<div class="controls controls-row">
				  <spring:bind path="dealSheet.orders[0].billingState.state">
					<select class="input-large1 addr" id="b_state0" name="${status.expression}" style="width:78%;">		                        
		                        <c:forEach var="state" items="${billingStates}">
									<option value="${state.state}"   ${state.state eq 'OH' ? 'selected' : ''}>${state.state}</option>
							  </c:forEach>
		                        </select>
		                       </spring:bind>
				</div>
			</div>
		</div>         
         <div class="span2" style="margin-left: 0%;">
			<div class="control-group">
				<label class="control-label add" for="textfield">Zip<span id="b_zip_error0" class="error_msg">*</span></label>
				<div class="controls controls-row" style="margin-left: 1px;">
				    <spring:bind path="dealSheet.orders[0].billingZip">
					<input type="text" class="input-block-level addr1"  id="b_zip0" name="${status.expression }">
					</spring:bind>
				</div>
			</div>
		</div>                                 
	</div>
                                    
      <div class="row-fluid" style="margin-left:15px;" id="bottom_link">
           <div class="span12">
			<div class="control-group">
				<label class="control-label" for="textfield">Comments</label>
				<div class="controls controls-row">
				   <spring:bind path="dealSheet.orders[0].agentNotes">
				      <textarea cols="10" rows="3" class="input-block-level comments" id="agentNotes" name="${status.expression}" style="width:80%;text-align: left;height: 70px;"></textarea>
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
						<button class="btn btn-success" style="margin-top:25px;" id="submitBtn" type="submit" >Submit Order</button>&nbsp;&nbsp;&nbsp;
						<button class="btn btn-primary" style="margin-top:25px;" type="button" id="addBtn">Add More Accounts</button>&nbsp;&nbsp;&nbsp;
						<button class="btn btn-print" style="margin-top:25px;" type="button" OnClick="window.print()">Print</button>&nbsp;&nbsp;&nbsp;
						<button class="btn btn-danger" style="margin-top:25px;" type="button"  id="removeAcc">Remove Last Account</button>
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
    					alert("Order successfully submitted");
    				</script>
    		</c:if>
    
    <c:if test="${not empty param.error }">
    		<script type="text/javascript">
    			alert("Problem While Saving Deal Sheet, One or more Orders are provided with Same Account Number... Account# Should be Unique among Orders..");
    		</script>
    </c:if>
    
  
    
    </div>


	  <div class="disclaimer" >
 		This document contains proprietary and confidential information of <strong>Hovey Energy, LLC</strong> and shall not be used, disclosed or reproduced, in whole or in part, for any purpose other than to benefit <strong>Hovey Energy, LLC</strong>. 
  This document and all the information contained herein remains the property of <strong>Hovey Energy, LLC</strong>. Any violation will subject to the fullest extent of the law.
   	 <br/> Copyright 2013. All rights reserved.
    </div>
 


 


</body>
</html>