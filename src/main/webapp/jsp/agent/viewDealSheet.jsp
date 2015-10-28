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
	<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black" /> 
	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen, mobile" title="main" charset="utf-8">
	<link rel="stylesheet" href="css/login.css" type="text/css" media="screen, mobile" title="main" charset="utf-8"> 	
	<link href="css/view.css" rel="stylesheet" type="text/css">
	<link href="css/tab_style.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="css/text/css" href="css/jquery-ui.css">
	  <link rel="stylesheet" type="css/text/css" href="css/jquery-calendar.css">
	  <link rel="stylesheet" type="css/text/css" href="css/menu.css">
	<link href="css/template1.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="js/view.js"></script>
	<script type="text/javascript" src="js/calendar.js"></script>
	<script type="text/javascript" src="js/jquery-1.js"></script>
	  <script type="text/javascript" src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery-calendar.js"></script>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
	<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">	
		<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	
	 <script>
	 	$(document).ready(function(){
	 		
	 		//$('#datepicker').val("<fmt:formatDate value="${now}" pattern="MM/dd/yyyy " />");
	 		
	 		
	 	});
	 	
	 	
	 	
	 </script>
	 <style>
	 
	  @media print 
		{
		    .noPrint 
		    {
		        display:none;
		    }
		    
		    .title{
		    	text-align: center;
		    	font-weight: bold;
		    }
		    
		    #user{
		    	display: none;
		    }
		    
		    .input-large{
		    	width: 200px;
		    }
		    
		    .title{
		    	margin:-5px;
		    }
		    .span9,#cust_check,.exCust,.error_msg{
		    	display: none !important;
		    }
		    
		    #outerContainer{
		    	padding:0px;
		    }
		    .stitle{
		    	height: 20px!important;
		    }
		    .controls-row {
				margin-top: -5px;
			}
		    
		}
	 
	 	#feedback{
	 		display: inline;
			position: relative;
			left: -500px;
			color: green;
			font-size: 20px;
	 	}
	 	
	 	
	 	
	 	#test ul{
	           		width:20% !important;
	           		float:left !important;
	           }
	    #test label{
	    	width:222px !important;
	    }
	    
	    #test .smallul{
	    	width:14.5% !important;
	    }
	    #test .bigul{
	    	width:25.2% !important;
	    }
	    
	    .login_button2 .submit1_button{
	    	background: url(images/submit.png);
	    	width: 180px;
			height: 38px;
	    }
	    
	     .login_button2 .addmore_button{
	     	background: url(images/add.png);
	     	width: 180px;
			height: 38px;
	     }
	     
	      .accTitle{
	     	font-size: 14px;
	     	font-weight: bold;
	     	margin-left: 10px;
	     }
	 </style>
	 
	
</head>

<body class="firefox">


        <!-- Top Header start -->
              
    
    
    
    <div id="content">
	      
    
		
	
	<!-- --- CONTENT BLOCK ---  -->
	<br />
		<!-- <ul id="tabs">
			
			<li id="current"><a href="#">Deal Sheet</a></li>		
		</ul> -->
	<div id="outerContainer">		
		<div id="form_container">
		  <div class="box-content">	
			<form:form method="post" id="dealsheet" name="dealsheet" class="appnitro" action="./dealsheet.do" commandName="dealSheet"  >	
				<div class="row-fluid">
                  		 <div style="font-family:Arial, Helvetica, sans-serif; background:#999999; color:#FFF; height:25px; font-size:15px; font-weight:bold;" class="stitle">
                          	<p style="padding:3px;">Agent Info</p>
                         </div>
                          	
                          	
                          		<div class="span3">
									<div class="control-group">
										<label class="control-label" for="textfield">Date Time</label>
										<div class="controls controls-row">
										 	 <spring:bind path="dealSheet.orders[0].orderDate">
												<input type="text" name="${status.expression}" class="input-block-level" value="<fmt:formatDate value="${orders[0].orderDate}" pattern="MM/dd/yyyy HH:mm:ss" />"  readonly="readonly" >
											</spring:bind>
										</div>
									</div>
								</div>
                          	
								<div class="span3">
									<div class="control-group">
										<label class="control-label" for="textfield">Agent Name</label>
										<div class="controls controls-row">
											<input type="text" class="input-block-level"   value="${orders[0].createdAgent.firstName} ${orders[0].createdAgent.lastName}" readonly="readonly">
										</div>
									</div>
								</div>
								<div class="span3">
									<div class="control-group">
										<label class="control-label" for="textfield">Agent Number</label>
										<div class="controls controls-row">
											<input type="text" class="input-block-level"  value="${orders[0].createdAgent.agentNumber}" readonly="readonly" >
										</div>
									</div>
								</div>
								
								
								<div class="span3">
									<div class="control-group">
										<label class="control-label" for="textfield">Supplier</label>
										<div class="controls controls-row">
										  
											<input type="text" class="input-block-level"  value="${orders[0].supplierName}" readonly="readonly" >
									    
										</div>
									</div>
								</div>
										
						</div>
										
				      
						<div class="row-fluid">
                           <div style="font-family:Arial, Helvetica, sans-serif; background:#999999; color:#FFF; height:25px; font-size:15px; font-weight:bold;" class="stitle">
                              <p style="padding: 3px;">Customer Info
                              				 <input type="checkbox" name="cust_check"  id="cust_check" value="1"  style="margin-top: -1px;" />
										<span style="font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:normal;" class="exCust"> Click Here for Existing customer</span></p>
							</div>
							<input type="hidden" name="customer.customerId" id="custid" value="0" />
							<div class="span3">
								<div class="control-group">
									<label class="control-label" for="textfield">First Name <span id="custname_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										
										<input type="text" class="input-block-level"  value="${orders[0].taxId.firstName}" readonly="readonly" >
									</div>
								</div>
							</div>
							<div class="span3">
								<div class="control-group">
									<label class="control-label" for="textfield">Last Name  <span id="custname2_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input type="text" class="input-block-level"  value="${orders[0].taxId.lastName}" readonly="readonly" >
									</div>
								</div>
							</div>
							<div class="span3">
								<div class="control-group">
									<label class="control-label" for="textfield">Title <span id="custtitle_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
									<input type="text" class="input-block-level"  value="${orders[0].taxId.title}" readonly="readonly" >
									</div>
								</div>
							</div>
                             <div class="span3">
								<div class="control-group">
									<label class="control-label" for="textfield">Phone Number <span id="custphone_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input type="text" class="input-block-level"  value="${orders[0].taxId.phoneNo}" readonly="readonly" >
									</div>
								</div>
							</div>
                            <div class="span3">
								<div class="control-group">
									<label class="control-label" for="textfield">Email<span id="custemail_error" class="error_msg"></span></label>
									<div class="controls controls-row">
										<input type="text" class="input-block-level"  value="${orders[0].taxId.email}" readonly="readonly" >
									</div>
								</div>
							</div>
                            <div class="span3">
								<div class="control-group">
									<label class="control-label" for="textfield">Fax  <span id="fax_error" class="error_msg"></span></label>
									<div class="controls controls-row">
									  
										<input type="text" class="input-block-level"  value="${orders[0].taxId.faxNo}" readonly="readonly" >
									</div>
								</div>
							</div>
                            <div class="span3">
								<div class="control-group">
									<label class="control-label" for="textfield">Tax ID <span id="custtax_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input type="text" class="input-block-level"  value="${orders[0].taxId.taxId}" readonly="readonly" >
										
									</div>
								</div>
							</div>
                            <div class="span3">
								<div class="control-group">
									<label class="control-label" for="textfield">Tax Exempt (Y/N)</label>
									<div class="controls controls-row">
										 <c:choose>
									   		<c:when test="${orders[0].taxId.taxExempt eq true}">
									   			<input type="text" class="input-block-level"  value="Yes" readonly="readonly" >
									   		</c:when>
									   		<c:when test="${orders[0].taxId.taxExempt eq false}">
									   			<input type="text" class="input-block-level"  value="No" readonly="readonly" >
									   		</c:when>
									   		<c:otherwise>
									   			<input type="text" class="input-block-level"  value="NA" readonly="readonly" >
									   		</c:otherwise>
									   </c:choose>
										
									</div>
								</div>
							</div>
					</div>
					<div class="row-fluid">
                       <div style="font-family:Arial, Helvetica, sans-serif; background:#999999; color:#FFF; height:25px; font-size:15px; font-weight:bold;" class="stitle">
                           <p style="padding: 3px;">Order Info </p>                          
                        </div>	
				   </div>
				         <c:set var="i" value="1"></c:set>
	     				<c:forEach items="${orders}" var="order">	
				   <div class="row-fluid">
				        
	     				
	     					 <div class="accTitle"> Account #${i }</div>
	     					 <div class="span3" style="width: 26%;">
							<div class="control-group">
								<label class="control-label" for="textfield">Business Name <span id="bname_error0" class="error_msg">*</span></label>
								<div class="controls controls-row">								  
									<input type="text" class="input-block-level" id="businessname0" value="${order.businessName }" readonly="readonly" style="width:100%;"/>							
								</div>
							</div>
						</div>
						<div class="span3" style="margin-left: 5.5%">
						<div class="control-group">
							<label class="control-label" for="textfield">DBA (If Applicable)</label>
							<div class="controls controls-row">								
								<input  id="dba0" value="${order.dba }" readonly="readonly" type="text" class="input-block-level" />								
							</div>
						</div>
					  </div>
					  
						<div class="span3" style="margin-left: 1%;width:17%;">
							<div class="control-group">
								<label class="control-label" for="textfield">Utility <span id="utiltiy0_error" class="error_msg">*</span></label>
								<div class="controls controls-row">
								
									<input class="input-large" id="utility0" value="${order.utility.utility }" readonly="readonly" style="width:65%;" />
										
								  
								</div>
							</div>
						</div>
						
						
						<div class="span3" >
							<div class="control-group">
								<label class="control-label" for="textfield">Account Number  <span id="acc_error0" class="error_msg">*</span> </label>
									<div class="controls controls-row">
									 
										<input type="text" id="account0" value="${order.accountNumber }" readonly="readonly" class="input-block-level" index="0" onblur="validateaccountsize(this.id)" / >
									
									</div>
							</div>
						</div>
					</div>	
						 <div class="row-fluid" style="margin-left:15px;">
                    	<div class="span3">
							<div class="control-group">
								<label class="control-label" for="textfield">KWH (Annual) <span id="kwh_error0" class="error_msg">*</span></label>
								<div class="controls controls-row">
								 
									<input id="kwh0" value="${order.kwh }" readonly="readonly" type="text" class="input-block-level" />
								   
								</div>
							</div>
						</div>      
                    
                    
                    
                       <div class="span3" style="width:17%;margin-left: 1%;">                         
							<div class="control-group">
								<label class="control-label" for="textfield">Term  <span id="term_error0" class="error_msg">*</span></label>
								<div class="controls controls-row">
								 
									<input class="input-large" id="term" value="${order.term }" readonly="readonly" style="width:65%" />
										  
								</div>
							</div>
					  </div>
					  
					  <div class="span3" style="width:17%;margin-left: 0%;">
						<div class="control-group">
							<label class="control-label" for="textfield">Rate Class(If Applicable) <span id="rateClass_error0" class="error_msg"></span></label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[0].rateClass">
								<input id="rateClass0" value="${order.rateClass }" readonly="readonly"  type="text" class="input-block-level" style="width:65%"/>
							  </spring:bind>
							</div>
						</div>
					</div>
					  
					  <div class="span3" style="width:17%;margin-left:0.5%;">
						<div class="control-group">
							<label class="control-label" for="textfield">Rate <span id="rate_error0" class="error_msg">*</span></label>
							<div class="controls controls-row">
							 
								<input id="rate0" value="${order.rate }" readonly="readonly" type="text" class="input-block-level" style="width:65%" />
							
							</div>
						</div>
					</div>
					
                     <div class="span3" style="margin-left: -1.6%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">TPV Number <span id="tpv_error0" class="error_msg">*</span></label>
							<div class="controls controls-row">
							   
								<input id="tpv0" value="${order.tpv }" readonly="readonly" type="text" class="input-block-level">
								
							</div>
						</div>
					</div>                                          
				</div>
				    <div class="row-fluid" style="margin-left:15px;">
                    <div class="span4">
						<div class="control-group">
							<label class="control-label" for="textfield">Services Address (Street) <span id="s_street_error0" class="error_msg">*</span></label>
							<div class="controls controls-row">
							  
								<input id="s_street0" value="${order.serviceStreet }" readonly="readonly"  type="text" class="input-block-level" />
								
							</div>
						</div>
					</div>
					<div class="span2">
						<div class="control-group">
							<label class="control-label" for="textfield">Unit  <span id="s_unit_error" class="error_msg"></span></label>
							<div class="controls controls-row">
							  
								<input type="text" class="input-block-level" id="s_unit0" value="${order.serviceUnit }" readonly="readonly" />
							   
							</div>
						</div>
					</div>
					<div class="span2">
						<div class="control-group">
							<label class="control-label" for="textfield">City <span id="s_city_error0" class="error_msg">*</span></label>
						<div class="controls controls-row">
						  
							<input type="text" class="input-block-level" id="s_city0" value="${order.serviceCity }" readonly="readonly"  />
							
						</div>
					</div>
				 </div>
                 <div class="span2">
					<div class="control-group">
						<label class="control-label" for="textfield">State  <span id="s_state_error0" class="error_msg">*</span></label>
						<div class="controls controls-row">
						 
							<input class="input-large1" id="s_state0" value="${order.serviceState }" readonly="readonly" style="width:78%;" />
								
					</div>
				 </div>
				</div>         
                <div class="span2">
					<div class="control-group">
						<label class="control-label" for="textfield">Zip <span id="s_zip_error0" class="error_msg">*</span></label>
						<div class="controls controls-row">
						    
							<input type="text" class="input-block-level" id="s_zip0" value="${order.serviceZip }" readonly="readonly">
						
						</div>
					</div>
				</div>                                 
			</div>
                                    
         <div class="row-fluid" style="margin-left:15px;">
           <div class="span4">
				<div class="control-group">
					<label class="control-label" for="textfield">Billing Address (Street) &nbsp;
						
					 </label>
					<div class="controls controls-row">
					   
						<input type="text" class="input-block-level" id="b_street0"value="${order.billingStreet }" readonly="readonly" >
						
					</div>
				</div>
		</div>
		<div class="span2">
			<div class="control-group">
				<label class="control-label" for="textfield">Unit<span id="b_unit_error" class="error_msg"></span></label>
				<div class="controls controls-row">
				 
					<input type="text" class="input-block-level" id="b_unit0" value="${order.billingUnit }" readonly="readonly" />
				
				</div>
			</div>
		</div>
		<div class="span2">
			<div class="control-group">
				<label class="control-label" for="textfield">City <span id="b_city_error0" class="error_msg"></span></label>
				<div class="controls controls-row">
				
					<input type="text" class="input-block-level"  id="b_city0" value="${order.billingCity }" readonly="readonly" />
					
				</div>
			</div>
		</div>
         <div class="span2">
			<div class="control-group">
				<label class="control-label" for="textfield">State <span id="b_state_error0" class="error_msg"></span></label>
				<div class="controls controls-row">
				 
					<input class="input-large1" id="b_state0" value="${order.billingState }" readonly="readonly" style="width:78%;" />
						   
			                 
		                     
				</div>
			</div>
		</div>         
         <div class="span2">
			<div class="control-group">
				<label class="control-label" for="textfield">Zip <span id="b_zip_error0" class="error_msg"></span></label>
				<div class="controls controls-row">
				  
					<input type="text" class="input-block-level"  id="b_zip0" value="${order.billingZip }" readonly="readonly">
					
			</div>
		</div>                                 
	</div>
				
	</div>			
				
				
	     					 					
			
	    				<c:set var="i" value="${i+1 }"></c:set>
	  					</c:forEach>
	  	
	  	 <div class="row-fluid" style="margin-left:15px;" id="bottom_link">
           <div class="span12">
			<div class="control-group">
				<label class="control-label" for="textfield">Special Notes</label>
				<div class="controls controls-row">
				  
					 <input type="text" class="input-block-level" id="agentNotes" value="${orders[0].agentNotes }" readonly="readonly">
				 
				</div>
			</div>
		</div>
      </div>				
	  			  
	 									
	         <div class="span9">
				<div class="control-group">
					<div class="controls controls-row" style="margin-left: 425px;">
						&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;
						<button class="btn btn-danger" style="margin-top:25px;" type="button" OnClick="window.print()">Print</button>
					</div>
				</div>
			</div>
				
		
      </form:form>
     </div>
  </div>
  
  
  

  </div>


  

     
 </body>
</html>