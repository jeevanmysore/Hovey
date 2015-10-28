<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<title>Print Deal Sheet</title>
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
		 <link  href="css/dealsheet.css" rel="stylesheet" type="text/css">
		<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	 <script type="text/javascript" src="js/dealsheet.js"></script>
	 
	 <!--[if lt IE 10]>		
		<link rel="stylesheet" href="css/dealsheet_ie.css" type="text/css" />		
	 <![endif]-->
	 <script>
	 
	 $(function() {
		
		 
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
				buttonImage: "images/calendar.gif",
				buttonImageOnly: true,
		        changeMonth: true,
		        changeYear: true,
		        showButtonPanel: true,
		        dateFormat: 'MM yy',
		        yearRange: '2012:c+10',
		        onClose: function(dateText, inst) { 
		            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
		            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
		            $(this).datepicker('setDate', new Date(year, month, 1));
		        }
		    });
	});
			
		window.print();
	 	
	 	
	 </script>
	 <style>
	
		 @media print 
		{
			#header{
				margin-top:-2px !important;
			}
			body, html{
			    height:100% !important;
			    padding:0 !important;
			    min-height: 100% !important;
			}
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
		    	margin:0px;
		    }
		    .span9,#cust_check,.exCust,.error_msg,#feedback1, .chkbox, .ui-datepicker-trigger, #clearBtn,.accsize {
		    	display: none !important;
		    }
		    
		    .datepicker{
		    	width:100%;
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
			
			input, textarea, .uneditable-input{
				font-size: 12px !important;
				font-family:Times New Roman !important;
				
				 padding: 0px !important; 
				
			}
			
			input[type="text"] {
				 padding: 0px !important; 
				 padding-bottom: 0px !important;
			}
			
			input[readonly]{
				padding-bottom: 0px !important;
			}
			
			select{
				font-size: 12px !important;
				font-family:Times New Roman !important;
				padding-left:0px !important;
				padding-top: 2px !important;
				
			}
			
			.acc{
				width: 100% !important;
				font-size: 10px !important;
			}
		    .row-fluid [class*="span"] {
		    
		   		 min-height: 20px !important;
		    	 max-height: 45px;		    	
		    }
		    
		    .agnt{
		    	margin-left: 1.2% !important;
		    	width:24% !important;
		    }
		    
		    .agnt input[type="text"]{
		    	width:97% !important;
		    }
		    
		   .agnt select{
		    	width:101% !important;
		    }
		    
		    .agnt1 {
		    	margin-left:2.4% !important;
		    	width:22% !important;
		    }
		    
		      .agnt1 input[type="text"],.agnt1 select{
		    	width:97% !important;
		    }
		   
		    
		    #comres{
		    	width:101% !important;
		    }
		    .cmn{
		    	
		    	margin-left: 1.9% !important;
		    }
		    
		    .cmn select{
		    	width:100% !important;
		    }
		    
		    
		      .cmn1 {
		    	margin-left:2.4% !important;		    	
		    }
		    
		    
		    .txex{
		    	width: 18% !important;	
		    	margin-left: 1.9% !important;	    	
		    }
		    
		    
		    
		    .buss{
		    	width: 100% !important;
		    }
		    
		    .util{
		    	width:15% !important;
		    }
		    
		    .officenotes{
		    	clear:both !important;
		    	display: inline-block ! important;
		    	margin-top: 40px;
		    	
		    }
		    
		    .comments{
		    	min-height: 150px;
		    	margin-top: 5px !important;
		    }
		    
		    a[href]:after {
    			content: none !important;
 			 }
 			 
 			 label,button {
 			 	font-size: 11px !important;
 			 	font-family: Times New Roman !important;;
 			 	padding:0px !imporrtant;"
 			 	text-decoration: none;
 			 
 			 }			 
 			/*  .seperator{
 			 	background-color:blue ! important ;
 			 	width: 100% ! important;
 			 	height: 1px ! important; 	
 			 	margin-bottom: 10px ! important; 
 			 	margin-top: 10px ! important; 
 			 } */
 			 
 			 .tpv{
 			 	width:105% ! important;
 			 }
 			 .strDate{
 			 	width:107.8% !important;
 			 }
 			 
 			 .strDate2{
 			 	width:105.5% !important;
 			 }
 			 .addr{
	     	width: 95% !important;
	     	margin-left: 15% !important;
	     }
	     
	      .addr1{
	     	width: 92.2% !important;
	     	margin-left: 15% !important;
	     }
 		
 		.street{
 			width: 100% !important;
 		}	
 			
 		 .accTitle{
 		 	clear:both !important;
	     	font-size: 14px !important;
	     	font-weight: bold !important;
	     	margin-left: 10px !important;
	     	  COLOR: #FFFFFF !important; 
            TEXT-DECORATION: none !important; 
            background: #2669B5 !important;
            font-family: verdana,arial !important; 
                 
	     }	 
	     
	     .sprice{
	     	/* font-size:8px !important;
	     	font-family: Times New Roman !important; */
	     	display: none !important; 
	     }
	     
	     .service{
	     	
	     	font-size: 8px !important;
	     }
	     
	     .orderacc{
	     	clear:both !important;
	     }
	     
	     .strdate2{
	     	margin-left: 1.3% !important;
	     }
	     
	     .startdate3{
	     	margin-left: 1.3% !important;
	     	width:10%!important;
	     	display: inline-block;
	     }
	     
	     .add{
	     	margin-left: 15px !important;
	     }
	     
	     .bzip{
	     	width: 110% !important;
	     	margin-left: 5px !important;
	     }
	     
	     .row-fluid{
	     	display: inline-block !important;
	     }
	     
	     .highrate{
	     	border: 2px solid !important;
	     }
	     
	     .disclaimer{
	     	
	     	/* clear:both !important;
	     	page-break-before:always;
	     	position:absolute !important;
	     	bottom:0px !important;    */
	     	width:89% !important;
	     	display:block!important;
	     	font-size: 10px !important;;
			color:#807C7C !important;;
			text-indent:0px !important;
			margin-bottom: 10px !important;;
			width:auto !important;;
			text-align: center !important;
			margin-right:4%;
	     }
	     
	    
	     
	}
		
	
	
	
	
	
		
		
		 .ui-datepicker-prev, .ui-datepicker-current,#user,#menubar{
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
		
	 </style>
	 
	
</head>

<body class="firefox">

 <%@ include file="header.jsp" %>
		
		
    
    
    
  <div id="content" style="margin-left: 2.12%;margin-bottom: 5px;"><!-- --- CONTENT BLOCK ---  -->
  
    
		<div id="feedback1" style="text-align: center;font-size: 18px;font-weight: bold;color: green">
    		
    	</div>
  		<div class="title" style="text-decoration:none!important;">DEAL SHEET</div>
	<div id="outerContainer">		
		<div id="form_container">
    		<div class="box-content">
				<form:form method="post" id="dealsheet" name="dealsheet" class="appnitro" action="./editdealsheet.do" commandName="dealSheet"  >	
                 	<div class="row-fluid">
                  		 <div style="font-family:Arial, Helvetica, sans-serif; background:#999999; color:#FFF; height:25px; font-size:15px; font-weight:bold;" class="stitle">
                          	<p style="padding:3px;">Agent Info</p>
                         </div>
                          	
                          	
                          		<div class="span3 agnt1">
									<div class="control-group">
										<label class="control-label" for="textfield">Date Time</label>
										<div class="controls controls-row">
										 	 <spring:bind path="dealSheet.orders[0].orderDate">
												<input type="text" name="${status.expression}" class="input-block-level" value="<fmt:formatDate value="${orders[0].orderDate }" pattern="MM/dd/yyyy HH:mm:ss" />"  readonly="readonly" >
												
											</spring:bind>
										</div>
									</div>
								</div>
                          	
								<div class="span3 agnt">
									<div class="control-group">
										<label class="control-label" for="textfield">Agent Name</label>
										<div class="controls controls-row">
											<input type="text" class="input-block-level"   value="${orders[0].createdAgent.firstName} ${orders[0].createdAgent.lastName}" readonly="readonly">
										</div>
									</div>
								</div>
								<div class="span3 agnt">
									<div class="control-group">
										<label class="control-label" for="textfield">Agent Number</label>
										<div class="controls controls-row">
											<input type="text" class="input-block-level"  value="${orders[0].createdAgent.agentNumber}" readonly="readonly" >
										</div>
									</div>
								</div>
								
								
								<div class="span3 agnt">
									<div class="control-group">
										<label class="control-label" for="textfield">Supplier</label>
										<div class="controls controls-row">
										   <spring:bind path="dealSheet.orders[0].supplierName">
											<input type="text" class="input-block-level"  value="${orders[0].supplierName.supplierName}" readonly="readonly" >
									     </spring:bind>	
										</div>
									</div>
								</div>
										
						</div>
						
						<div class="row-fluid">
                           <div style="font-family:Arial, Helvetica, sans-serif; background:#999999; color:#FFF; height:25px; font-size:15px; font-weight:bold;" class="stitle">
                              <p style="padding: 3px;">Customer Info
                              				 <!-- <input type="checkbox" name="cust_check"  id="cust_check" value="1"  style="margin-top: -1px;" />
										<span style="font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:normal;" class="exCust"> Click Here for Existing customer</span> --></p>
							</div>
							<input type="hidden" name="customer.customerId" id="custid" value="0" />
							<div class="span3 cmn1" style="width:18%;margin-left: 15px;">
								<div class="control-group">
									<label class="control-label" for="textfield">First Name<span id="custname_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										
										<input id="custname" name="customer.firstName" class="input-block-level" type="text" style="width:90%;" readonly="readonly"  value="${orders[0].taxId.firstName}"/>
									</div>
								</div>
							</div>
							<div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Last Name <span id="custname2_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input id="custname2" name="customer.lastName" type="text" class="input-block-level" style="width:90%;" readonly="readonly"  value="${orders[0].taxId.lastName}" />
									</div>
								</div>
							</div>
							<div class="span3 cmn" style="width:18%;">
								<div class="control-group" >
									<label class="control-label" for="textfield">Title<span id="custtitle_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
									<input id="custtitle" name="customer.title"  type="text" class="input-block-level" style="width:90%;"  readonly="readonly"  value="${orders[0].taxId.title}"/>
									</div>
								</div>
							</div>
                             <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Phone Number<span id="custphone_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input id="custphone" name="customer.phoneNo" type="text" class="input-block-level" style="width:90%;" readonly="readonly"  value="${orders[0].taxId.phoneNo}" />
									</div>
								</div>
							</div>
							 <div class="span3 txex" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Service Type</label>
									<div class="controls controls-row">
										<input class="input-block-level" type="text" id="comres" name="customer.type" style="width:88%;" readonly="readonly"  value="${orders[0].taxId.type}" />
										 
									</div>
								</div>
							</div>
                            <div class="span3 cmn1" style="clear:both;width:18%;margin-left: 15px;">
								<div class="control-group">
									<label class="control-label" for="textfield">Email<span id="custemail_error" class="error_msg"></span></label>
									<div class="controls controls-row">
										<input id="email" name="customer.email"  type="text" class="input-block-level lowfont" style="width:90%;text-transform: none;" readonly="readonly"  value="${orders[0].taxId.email}" >
									</div>
								</div>
							</div>
                            <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Fax<span id="fax_error" class="error_msg"></span></label>
									<div class="controls controls-row">
										<input id="fax" name="customer.faxNo" class="input-block-level" type="text" style="width:90%;" readonly="readonly"  value="${orders[0].taxId.faxNo}"/>
									</div>
								</div>
							</div>
                            <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Tax ID<span id="custtax_error" class="error_msg">*</span></label>
									<div class="controls controls-row">
										<input id="taxid" name="customer.taxId"  type="text" class="input-block-level" style="width:90%;"  readonly="readonly"  value="${orders[0].taxId.taxId}"/>
										
									</div>
								</div>
							</div>
                            <div class="span3 cmn" style="width:18%;">
								<div class="control-group">
									<label class="control-label" for="textfield">Tax Exempt (Y/N)</label>
									<div class="controls controls-row">
									  <c:choose>
									  	<c:when test="${orders[0].taxId.taxExempt eq true }">
									  		<input class="input-block-level" type="text" id="taxexempt" name="customer.taxExempt" style="width:90%;" readonly="readonly"  value="Yes" />
									  	   
									  	</c:when>
									  <c:when test="${orders[0].taxId.taxExempt eq false }">
									  		<input class="input-block-level" type="text" id="taxexempt" name="customer.taxExempt" style="width:90%;" readonly="readonly"  value="No" />
									  	   
									  	</c:when>
									  	<c:otherwise>
									  		<input class="input-block-level" type="text" id="taxexempt" name="customer.taxExempt" style="width:90%;" readonly="readonly"  value="N/A" />
									  	</c:otherwise>
									  </c:choose>
										
											
									</div>
								</div>
							</div>
							
							<div class="span3 cmn" style="width:18%;">
										<div class="control-group">
											<label class="control-label" for="textfield">Fronter<span id="fronter_error" class="error_msg"></span></label>
											<div class="controls controls-row">
												<input id="fronter" name="customer.fronter" class="input-block-level" type="text" style="width:90%;" value="${orders[0].taxId.fronter }" readonly="readonly"/>
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
							 
                        </div>
						
					  
						<div class="span3 util" style="margin-left: 15px;width:10.2%;">
							<div class="control-group">
								<label class="control-label" for="textfield">Utility<span id="utiltiy${i-1}_error" class="error_msg">*</span></label>
								<div class="controls controls-row">
								  <spring:bind path="dealSheet.orders[${i-1}].utility.utility">
									<input type="text" class="input-block-level" readonly="readonly" id="utility${i-1}" name="${status.expression}" style="width:100%;margin-top: 5px;" value="${order.utility.utility }"  />
										
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
										<input type="text" id="account${i-1}" readonly="readonly" name="${status.expression}" class="input-block-level acc" index="${i-1}" onblur="validateeditaccountsize(this.id)" style="width: 98%;"  value="${order.accountNumber }"/ >
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
									<input id="kwh${i-1}"  readonly="readonly" name="${status.expression}" type="text" class="input-block-level" style="width:100%" value="${order.kwh }"/>
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
									<input type="text" readonly="readonly" class="input-block-level" id="term${i-1}" name="${status.expression}" style="width:100%;" value="${order.term}" / >
											
							</spring:bind>
								</div>
							</div>
					  </div>
					  <div class="span3 startdate2" style="width:10%;margin-left: 2%;">
					   <div style="margin-top: -25px;height: 23px;"><span id="rateClass_error${i-1 }" class="error_msg"></span> </div>  
						<div class="control-group">
							<label class="control-label" for="textfield">Rate Class <span class="error_msg"></span></label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[${i-1 }].rateClass">
								<input id="rateClass${i-1 }" name="${status.expression}" readonly="readonly" type="text" class="input-block-level" style="width:100%" value="${order.rateClass}"/>
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
								  			<input type="checkbox"  readonly="readonly" name="${status.expression}" id="specialpricing${i-1 }"  style="margin-top: -1px;"  index="${i-1 }" onclick="higlightRate(this.id);" checked="checked"  />
								  	</c:when>
								  	<c:otherwise>
								  		<input type="checkbox"  readonly="readonly" name="${status.expression}" id="specialpricing${i-1 }"  style="margin-top: -1px;"  index="${i-1 }" onclick="higlightRate(this.id);"  />
								  	</c:otherwise>
								  </c:choose>
							 		
							 		 <span style="font-size: 12px;" class="sprice">Special</span>
						   		</spring:bind>
						   	</label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[${i-1 }].rate">
								<input id="rate${i-1 }" name="${status.expression}" readonly="readonly" type="text" class="input-block-level" style="width:100%" value="${order.rate }" />
							  </spring:bind>
							</div>
						</div>
					</div>
					
					 <div class="startdate3">
					  <div style="margin-top: -21px;height: 23px;"><span id="startdate_error${i-1 }" class="error_msg"></span> </div>  
						<div class="control-group" style="margin-top: -3px;">
							<label class="control-label" for="textfield">Start Date<span id="startdate_error${i-1 }" class="error_msg">*</span></label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[${i-1 }].startDate">
								<input id="startDate${i-1 }" name="${status.expression}" readonly="readonly"  type="text" class="input-block-level datepicker strDate" style="width:80%;font-size: 12px;padding:2px;" readonly="readonly"  value="<fmt:formatDate value='${order.dealStartDate}'  pattern='MM/dd/yyyy'/>"/>
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
								  	<input type="checkbox" name="bname_chk" value="checkbox"  readonly="readonly" id="bname_chk${i-1}" style="margin-left: 2px;margin-top: -2px;" index="${i-1}" onclick="copybusinessname(this.id);" class="chkbox" />
						      			<span  class="chkbox"  > Same as Previous</span>
								  
								  </c:if>
								
								
								</label>
								<div class="controls controls-row">
								   <spring:bind path="dealSheet.orders[${i-1 }].businessName">
									<input type="text" class="input-block-level buss" readonly="readonly" id="businessname${i-1 }" name="${status.expression}"  style="width:100%;" value="${order.businessName}"/>
								</spring:bind>
								</div>
							</div>
						</div>
						<div class="span3 dba" style="margin-left: 2%;width: 17%;">
						<div class="control-group">
							<label class="control-label" for="textfield">DBA <span class="chkbox">(If Applicable)</span>
							<c:if test="${i gt 1 }">
								<input type="checkbox" name="dba_chk" value="checkbox"  readonly="readonly" id="dba_chk${i-1}" style="margin-left: 2px;margin-top: -2px;" index="${i-1}" onclick="copydba(this.id);"  class="chkbox" />
			      					<span  class="chkbox"  > Same as Previous</span>
							</c:if>
							 </label>
							<div class="controls controls-row">
								<spring:bind path="dealSheet.orders[${i-1 }].dba">
								<input  id="dba${i-1 }" name="${status.expression}" type="text" readonly="readonly" class="input-block-level lowfont" style="width: 95%;"  value="${order.dba}"/>
								</spring:bind>
							</div>
						</div>
					  </div>
                    
                    
                     <div class="span3" style="margin-left: 1%;width: 11%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">TPV <span id="tpv_error${i-1 }" class="error_msg">*</span>
							<c:if test="${i gt 1 }">
								 <input type="checkbox" name="tpv_chk" value="checkbox"  id="tpv_chk${i-1}" style="margin-left: 2px;margin-top: -2px;" index="${i-1}" onclick="copyTpv(this.id);" class="chkbox" />
			      					<span class="chkbox" > Same as Previous</span>
							</c:if>
							
							</label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[${i-1 }].tpv">
								<input id="tpv${i-1 }" name="${status.expression}" readonly="readonly" type="text" class="input-block-level tpv" style="width: 88%;" value="${order.tpv}">
								</spring:bind>
							</div>
						</div>
					</div>          
					
					<div class="span3 county" style="margin-left: 0.5%;width: 14%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">County<span id="county_error${i-1 }" class="error_msg"></span></label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[${i-1 }].county">
								<input id="county${i-1 }" name="${status.expression}" type="text" class="input-block-level " style="width: 88%;" value="${order.county}" }>
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
								
												 value="<fmt:formatDate value='${order.meterReadDate}'  pattern='MM/dd/yyyy'/>"	>
								</spring:bind>
							</div>
						</div>
					</div>     
					
					
					
					                                
				</div>
                                    
                 <div class="row-fluid" style="margin-left:15px; ">
                    <div class="span4 street1" style="width:37%;">
						<div class="control-group">
							<label class="control-label" for="textfield">Service (Street)<span id="s_street_error${i-1 }" class="error_msg">*</span>
								<c:if test="${i gt 1 }">
									 <input type="checkbox" name="sadd_chk" value="checkbox"  id="sadd_chk${i-1}" style="margin-left: 5px;margin-top: -2px;"  index="${i-1}" onclick="copyserviceaddress(this.id);" class="chkbox" />
			      					<span class="chkbox"  > Same as Previous</span>
								</c:if>
							
							
							</label>
							<div class="controls controls-row">
							   <spring:bind path="dealSheet.orders[${i-1 }].serviceStreet">
								<input id="s_street${i-1 }" name="${status.expression}" readonly="readonly"  type="text" class="input-block-level street" style="width:100%;" value="${order.serviceStreet}" />
								</spring:bind>
							</div>
						</div>
					</div>
					<div class="span2 unit" style="margin-left: 2% ;">
						<div class="control-group">
							<label class="control-label add" for="textfield">Unit<span id="s_unit_error" class="error_msg"></span></label>
							<div class="controls controls-row">
							  <spring:bind path="dealSheet.orders[${i-1 }].serviceUnit">
								<input type="text" class="input-block-level addr" id="s_unit${i-1 }"  readonly="readonly" name="${status.expression}" value="${order.serviceUnit}" />
							   </spring:bind>
							</div>
						</div>
					</div>
					<div class="span2" style="margin-left:0%;">
						<div class="control-group">
							<label class="control-label add" for="textfield">City<span id="s_city_error${i-1 }" class="error_msg">*</span></label>
						<div class="controls controls-row">
						  <spring:bind path="dealSheet.orders[${i-1 }].serviceCity">
							<input type="text" class="input-block-level addr" id="s_city${i-1 }" readonly="readonly" name="${status.expression}"  value="${order.serviceCity}" />
							</spring:bind>
						</div>
					</div>
				 </div>
                 <div class="span2 state2" style="margin-left:0%;width: 13%;">
					<div class="control-group">
						<label class="control-label add" for="textfield">State<span id="s_state_error${i-1 }" class="error_msg">*</span></label>
						<div class="controls controls-row">
						  <spring:bind path="dealSheet.orders[${i-1 }].serviceState.state">
							<input class="input-block-level addr" id="s_state${i-1 }" name="${status.expression}" style="width:78%;" type="text" readonly="readonly" value="${order.serviceState.state}" />
							
	                      </spring:bind>
					</div>
				 </div>
				</div>         
                <div class="span2" style="margin-left:0%;">
					<div class="control-group">
						<label class="control-label add" for="textfield">Zip<span id="s_zip_error${i-1 }" class="error_msg">*</span></label>
						<div class="controls controls-row">
						     <spring:bind path="dealSheet.orders[${i-1 }].serviceZip">
							<input type="text" class="input-block-level addr1" id="s_zip${i-1 }" name="${status.expression}" value="${order.serviceZip}" readonly="readonly">
						 </spring:bind>
						</div>
					</div>
				</div>                                 
			</div>
                                    
         <div class="row-fluid" style="margin-left:15px;">
           <div class="span4 street1" style="width: 37%;">
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
						<input type="text" class="input-block-level street" readonly="readonly" id="b_street${i-1 }" name="${status.expression }" style="width: 100%;" value="${order.billingStreet}" >
						</spring:bind>
					</div>
				</div>
		</div>
		<div class="span2 unit" style="margin-left: 2%;">
			<div class="control-group" >
				<label class="control-label add" for="textfield">Unit<span id="b_unit_error" class="error_msg"></span></label>
				<div class="controls controls-row">
				 <spring:bind path="dealSheet.orders[${i-1 }].billingUnit">
					<input type="text" class="input-block-level addr" readonly="readonly" id="b_unit${i-1 }" name="${status.expression}" value="${order.billingUnit}" />
				 </spring:bind>
				</div>
			</div>
		</div>
		<div class="span2" style="margin-left: 0%;">
			<div class="control-group">
				<label class="control-label add" for="textfield">City<span id="b_city_error${i-1 }" class="error_msg">*</span></label>
				<div class="controls controls-row">
				 <spring:bind path="dealSheet.orders[${i-1 }].billingCity">
					<input type="text" class="input-block-level addr" readonly="readonly"  id="b_city${i-1 }" name="${status.expression}" value="${order.billingCity}" />
					</spring:bind>
				</div>
			</div>
		</div>
         <div class="span2 state2" style="margin-left: 0%;width: 13%;">
			<div class="control-group">
				<label class="control-label add" for="textfield">State<span id="b_state_error${i-1 }" class="error_msg">*</span></label>
				<div class="controls controls-row">
				  <spring:bind path="dealSheet.orders[${i-1 }].billingState.state">
					<input class="input-block-level addr" id="b_state${i-1 }" name="${status.expression}" style="width:78%;" value="${order.billingState.state}" type="text" readonly="readonly" />
						  
		                       </spring:bind>
				</div>
			</div>
		</div>         
         <div class="span2" style="margin-left: 0%;">
			<div class="control-group">
				<label class="control-label add" for="textfield">Zip<span id="b_zip_error${i-1 }" class="error_msg">*</span></label>
				<div class="controls controls-row" style="margin-left: 1px;">
				    <spring:bind path="dealSheet.orders[${i-1 }].billingZip">
					<input type="text" class="input-block-level addr1"  readonly="readonly" id="b_zip${i-1 }" name="${status.expression }" value="${order.billingZip}">
					</spring:bind>
				</div>
			</div>
		</div>                                 
	</div>
		<input type="hidden" id="acccount" value="${i}" />
       <c:set var="i" value="${i+1 }"></c:set>
</c:forEach>
	  	                             
      <div class="row-fluid" style="margin-left:15px;" id="bottom_link">
           <div class="span12">
			<div class="control-group">
				<label class="control-label" for="textfield">Comments</label>
				<div class="controls controls-row">
				   <spring:bind path="dealSheet.orders[0].agentNotes">
				      <textarea cols="10" rows="3" class="input-block-level comments" readonly="readonly" id="agentNotes" name="${status.expression}" style="text-align: left;min-height: 100px;" title="">${orders[0].agentNotes}</textarea>
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
						<button class="btn-success btn" style="margin-top:25px;" onclick="window.print()" >Print</button>&nbsp;&nbsp;&nbsp;
						<button class="btn-print btn" style="margin-top:25px;" onclick="window.close()" >Close</button>&nbsp;&nbsp;&nbsp;
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
    
    
    
  
    
    </div>


 


 


</body>
</html>