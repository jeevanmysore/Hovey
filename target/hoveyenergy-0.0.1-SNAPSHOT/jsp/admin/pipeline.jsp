<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>






<html>	
<head>

	<title>Pipeline</title>	
	
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script  type="text/javascript" src="/hoveyenergy/js/filterorder.js" ></script>
	<script  type="text/javascript" src="/hoveyenergy/js/editdropdown.js" ></script>
	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	
	
	
	<link href="/hoveyenergy/css/user_login.css" rel="stylesheet" type="text/css" />
	<!-- <script  type="text/javascript" src="js/popup.js" ></script> -->
	
  <style>

	 
	     
	 /*   .datepicker2 .ui-datepicker-calendar{
	     	display:none !important;
	     }
			
 */

  .body_bg {
	background: #eee;
	background: -webkit-gradient(linear, left top, left bottom, from(#ffffff), to(#edeeef));
	background: -moz-linear-gradient(top,  #ffffff,  #edeeef);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#edeeef');
	border-color: #ccc;
    border-imonone;
    border:1px solid #999;
	
	}
	
	input[type="text1"] {
	    -moz-border-bottom-colors: none;
	    -moz-border-left-colors: none;
	    -moz-border-right-colors: none;
	    -moz-border-top-colors: none;
	border-color: #999999 #DDDDDD #DDDDDD;
	border-image: none;
	border-radius: 1px 1px 1px 1px;
	border-style: solid;
	border-width: 1px;
	font-family: arial;
	font-size: 10px;
	height: 20px;
	line-height: 1.22em;
	margin: 4px 0px 4px;
	padding: 2px 0;
	text-transform: capitalize;
	width: 89%;
	}
	  
	  tr:nth-child(even) td {
	  	background-color: #edeff0;
	  }
	  
	  .ppltb tr:first-child td{
	  	background-color: #B2B2B2;
	  }
	  .pptbl a{
	  	color:#fff;
	  }
	  
	  .ppltbl{
	  			border-right: 5px;
	  	}
	  	
	  	.pptbl input[type='text1']{
	  		text-align:center;
	  	}
	  
	  .filtertext{
	  	color: darkgray;
	  }
	  .bg_purple1 {
	   background-image: url(/hoveyenergy/images/box-pattern.png);
	}
			
			
			.searchbtn{
				height: 30px;
				width: 130px;
				border-radius: 5px;
				padding-top: 30px;
				background-image: url(/hoveyenergy/images/search.png);
				position : relative;
			   
			    behavior: url(/hoveyenergy/js/js/PIE.htc);
			    top:10px;
			    margin-left: 25px;
			    background-repeat: no-repeat;
			}
			
			.pipeline{
				font-size: 10px;
				height: 20px;
				border: 1px solid;
				border-color: #999999 #DDDDDD #DDDDDD;
				
				margin: 0px 5px 0px 5px;
			}
			
			table{
				font-size: 10px;
				border-radius:5px;
				position : relative;
			    color:black;
			    behavior: url(/hoveyenergy/js/js/PIE.htc);
				
			}
			
			#overlay_form{
				position: absolute;
				border: 5px solid rgb(140, 177, 231);
				padding: 10px;
				background: white;
				width: 700px;
				height: auto;
				border-radius: 33px;		   
			    behavior: url(/hoveyenergy/js/js/PIE.htc);
				z-index: 9999;
			}
			#pop{
				
				
				/* position: relative;
				right: 197px;
				top: -57px; */
				font-size: 15px;
				float: left;
				color:blue;
				/* height: 0px; */
				text-decoration: underline;
				
			}
			
			
			.multisearcherror{
				/* float:right; */
				color: red;
				font-size: 10px;
				height: 10px;
				margin-left: 32px;
			}
			
			
			.headerrow td{
				padding-left: 5px;
				padding-right: 5px;
				
			}
			
			.headerrow{
				background-color: /* rgb(255, 221, 187) */ #4c4c4c !important;	
			}
			
		 .ui-datepicker-current{
			display: none;
		} 
	     
	     .ui-datepicker-trigger {
	     		float: none !important;
	     		margin-top: 0px !important;
	       }
	       
	       .pagecount b{
	       	font-weight: bold;
	       } 
	       
	       
	       
	       /* Added on Oct 31 */
	            
									       
	       
	       
	       
			
			
	  </style>
	  
	  
	  
 <script>
 var date;
		$(function() {
			
			/* $('.ui-datepicker-current').css('display','none'); */
			
			 $( ".datepicker" ).datepicker({
				 changeMonth: true,
			        changeYear: true,
				   showButtonPanel: true,	
				   closeText: 'Clear',
			        dateFormat: 'mm/dd/yy',
			        yearRange: '2012:c+10',
			        onSelect:function(dateText,inst){
						   date=0;
						  date=$(this).datepicker('getDate').getDate();
					   },
			        onClose: function(dateText, inst) { 
			            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
			            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
			            if(date>0){
			            	$(this).datepicker('setDate', new Date(year, month,date));		
			            }
			            else{
			            	$(this).val('');
			            	$(this).datepicker('setDate', '');			            	
			            }
				           date=0;
				           editpipeline(this);
			        }
				
				/*  showButtonPanel: true,
				 closeText: 'Clear',
				 onClose: function(dateText, inst) { 
					   if ($(window.event.srcElement).hasClass('ui-datepicker-close')){
						   /*  var id2= $(this).attr('id');
						    $('#'+id2).val('');
						   
				           }
					   alert('s');
					   $(this).val('');
					   $(this).datepicker('setDate', '');
			        } */
			});
			
			
			$( "#orderDatepicker" ).datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true ,
				closeText: 'Clear'
				
					          
				}); 
		
			
			 $('.datepicker2').datepicker( {
				 	/* showOn: "button",
					buttonImage: "/hoveyenergy/images/calendar.gif",
					buttonImageOnly: true, */
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
			            if(date>1){
			            	$(this).datepicker('setDate', new Date(year, month,date));		
			            }
			            else{
			            	$(this).datepicker('setDate', new Date(year, month, 1));
			            }
				           date=1;
				          editpipeline(this);
			        }
			 
			    });  
			 
			 /* $(".datepicker2").focus(function () {
			        $(".ui-datepicker-calendar").hide();
			        $(".ui-datepicker-prev").hide();
			        $("#ui-datepicker-div").position({
			            my: "center top",
			            at: "center bottom",
			            of: $(this)
			        });
			    });

			    $(".datepicker2").blur(function () {
			      
			       
			    }); */
		});
		
		
			
		
		
	

  
  	$(document).ready(function(){
  		
  		 $('#orderfilter .ui-datepicker-trigger').css('display','none');
  		 $('.deal .ui-datepicker-calendar').css('display','none');
  		 
  		 
  		 
  		 //handling single search
  		 
  		 if($('#filtertype').val()=="date"){
  			 
		  		$('#value').val('');
	    	 
	    	 $('#value').css('display','none');
	    	 $('#value').removeAttr('name');
	    	 $('#orderDatepicker').css('display','inline');
	    	 $('#orderfilter .ui-datepicker-trigger').css('display','inline');
	    	 $('#orderDatepicker').attr('name','value');
		  }
		  else{
			  $('#orderDatepicker').val('');
		    	 $('#orderDatepicker').css('display','none');
		    	 $('#orderfilter .ui-datepicker-trigger').css('display','none');
		    	 $('#orderDatepicker').removeAttr('name');
		    	 $('#value').css('display','inline');
		    	 $('#value').attr('name','value');
		  }
  		 
  		 
  		 
  		 
  		 
  		 // table stripe..
  	
  		 $(".pptbl>tbody>tr:nth-child(even)").css('background-color', '#e5e5e5');

  		 $(".pptbl>tbody>tr:nth-child(odd)").css('background-color', '#ffffff');
  		 $(".pptbl>tbody>tr:first-child").css('background-color', '#B2B1B0');
  		
  		
  		var statusCount=0;
  		var status=1;
  		$('#ordersrange').change(function(){
  			var selected = $(this).find('option:selected');  			
 		     var range = selected.val();
 		     var query=$('#querystring').val(); 		    
 		     var que=query.toString();
 		     var replacer="range="+range+"&";
 		     var rangestr=$('#range').val()+"&"; 		     
 	        var querystring=query.replace(rangestr,"");
 	        //querystring="&"+querystring 		   
 		     window.location="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?"+replacer+querystring;
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
  	
  
  	
  	function showPopup(id){
  		var selected = $('#'+id).find('option:selected');
	     var status = selected.val();
	     if(status=="rescinded"){
	    	
	    	 alert("Please Enter a Reason in the Notes");
	     }
  	}
  
  	
  	
  	 
	
</script>

  
 

</head>

<body class="firefox">
 
<%@ include file="header.jsp" %>
	 
	 <c:set var="queryString" value="${requestScope['javax.servlet.forward.query_string']}" />
	 <c:set var="delimeter" value="&page=${page }" />
	<c:set var="query" value="${fn:replace(queryString,delimeter,'')}" />
	<c:set var="delimeter2" value="&sortby=${sortby}" />
	<c:set var="query2" value="${fn:replace(queryString,'&','')}" />
	<c:set var="query2" value="${fn:replace(queryString,delimeter2,'')}" />
	
	
	<div id="content">
	  
	  <input type="hidden" id="querystring" name="query" value="${query}"/>
	  <input type="hidden" id="range" name="range" value="range=${range}"/>
	  <br />
	  
	  	<c:set var="action" />
	 		 <c:choose>
				  	<c:when test="${empty rescinded }">
				  				<c:set var="action" value="./performsearch.do" />
				  	</c:when>
				  	<c:otherwise>
				  		<c:set var="action" value="./performsearchonannivpayments.do" />

				  	</c:otherwise>
	  		 </c:choose>
	 
  <!--  Multi Search UI  -->
	  <div id="fancybox-content" style="border-width: 10px; width: auto; height: auto;">
	  
		<div id="main_container" class="container">
			<div class="cb">
				<div class="inner-page">
				  
		<form:form method="get" action="${action }" commandName="search" id="overlay_form"  style="display:none" onsubmit="return validateMultiSearch()" >			  
	      <div class="login-area">	  
				<div class="title">Advanced Search</div>
			 	<div class="lab_top" style="margin-top:-5px;">
			 		<div id="ordererror" class="multisearcherror"></div>
				  <div class="fc-text">Order Date: </div> 
					<div class="fc-text1">From <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " class="datepicker" name="startDate"  id="startDate"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;  To <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " class="datepicker" name="endDate" id="endDate"/></div>      </div>
				
				<div class="lab_top">
					<div id="dealerror" class="multisearcherror"></div>
			      <div class="fc-text">Deal Start Date:   </div>
				  <div class="fc-text1">From <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " class="datepicker" name="dealStartDate1"  id="dealStartDate1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; To <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " class="datepicker" name="dealStartDate2"  id="dealStartDate2"/></div>   </div>     
			    
				<div class="lab_top">
					<div id="suppliererror" class="multisearcherror"></div>
			      <div class="fc-text">Sent To Supplier Date:  </div>
				  <div class="fc-text1">From <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " class="datepicker" name="sentToSupplier1"  id="sentToSupplier1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; To <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " class="datepicker" name="sentToSupplier2"  id="sentToSupplier2"/></div>  </div>      
			    
				<div class="lab_top">
					<div id="upfronterror" class="multisearcherror"></div>
			      <div class="fc-text">Upfront Paid Date:   </div>
				  <div class="fc-text1">From <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " class="datepicker"  name="upfrontPaidDate1" id="upfrontPaidDate1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; To <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " class="datepicker" name="upfrontPaidDate2" id="upfrontPaidDate2"/></div> </div>     
			    
				<div class="lab_top1">
					<div id="kwherror" class="multisearcherror"></div> 
			      <div class="fc-text">Kwh: </div>
				  <div class="fc-text1">From <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " name="kwh1" id="kwh1"  />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;  To <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " name="kwh2" id="kwh2" /></div> </div>     
			   
				<div class="lab_top">
					 <div id="comerror" class="multisearcherror"></div> 
			      <div class="fc-text">Commission:</div>
				  <div class="fc-text1">From <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; "  name="commission1" id="commission1"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; To <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; "  name="commission2" id="commission2"/></div>      </div>
			   
				<div class="lab_top">
					<div id="termerror" class="multisearcherror"></div>	
			      <div class="fc-text">Term:  </div>
				  <div class="fc-text1">From<input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px;margin-left: 3px; "  name="term1"  id="term1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; To <input type="text" style="border:1px solid #ccc;   height: 15px; width: 125px; " name="term2"  id="term2"/></div>      </div>
			    
				<div class="lab_top1" style="margin-top:25px; ">
			      <div class="fc-text">Account #:	</div>
			        <div class="fc-text1">  <input type="text" style="border:1px solid #ccc; margin-left:33px;   height: 15px; width: 250px; "  name="accountNumber" /></div>   
				  
				   <div class="fc-text">Notes:	</div>
				   	  <div class="fc-text1">  <input type="text" style="border:1px solid #ccc; margin-left:33px;   height: 15px; width: 250px; " name="notes"  /></div>  
				   
				   <c:if test="${empty rescinded }">
				   <div class="fc-text">Upfront Commission Status:	</div>
				   	  <div class="fc-text1">  
				   	  	<select  style="border: 1px solid #ccc;margin-left: 33px;height: 27px;width: 252px;margin-top: 5px;border-radius: 3px;" name="commissionStatus" >
				   	  				<option value="">- select -</option>	
				   	  				<option value="paid">PAID</option>
				   	  				<option value="unpaid">NOT PAID</option>
				   	  				<option value="equal">Equal to Expected Commission</option>
				   	  				<option value="less">Less Than Expected Commission</option>
				   	  				<option value="greater">Greater Than Expected Commission</option>
				   	  	</select>
				   	  	<span style="color:red">(* Only for First year Orders)</span>
				   	  </div>  
				   </c:if>
			<!-- Modiified by Jeevan on July 18,2013 to add States and Utility for Dynamic Search -->
				   <div class="fc-text" style="margin-top:33px;">Supplier Name: </div>
				   	  <div class="fc-text1">	
				   	    <select style="border:1px solid #ccc; margin-left:33px;   height: 25px; width: 125px; margin-top: 30px; float: left;" name="supplierName">			  
			  				<option value="">- select -</option>	
			  				<c:forEach items="${suppliers }" var="supplier">
			  					<option value="${supplier.supplierName }">${supplier.supplierName }</option>
			  				</c:forEach>				  						  			
						</select>
						
						 <div class="fc-text" style="text-align: left;margin-left: 50px;margin-top:33px;width: 60px;">Utility:</div>
						<select style="border:1px solid #ccc; margin-left:33px;   height: 60px; width: 125px; margin-top: 27px;float: left " name="utility" multiple="multiple">			  
			  				<option value="">- select -</option>			  			
			  				 <c:forEach var="util" items="${utils }">
			  					<option value="${util.utility }">${util.utility }</option>		
			  				</c:forEach>	  			
						</select>
						
					</div> 
				   
				   <div class="fc-text" style="margin-top:15px;">Contract Type:	</div>
				    <div class="fc-text1">	
				     <select style="border:1px solid #ccc; margin-left:33px;margin-top: 11px;   height: 25px; width: 125px;float: left; " name="contractType">
				      		<option value="">- select -</option>
						  <c:forEach var="contractTypes" items="${contractTypes}">
			   		 	<option value="${contractTypes.contractType }" ${order.contractType eq contractTypes.contractType ? 'selected' : ''  } > ${contractTypes.contractType}</option>
                         </c:forEach>
						</select>
						
						 <div class="fc-text" style="text-align: left;margin-left: 50px;margin-top:15px;width: 60px;">State:</div>
						<select style="border:1px solid #ccc; margin-left:33px;   height: 25px; width: 125px; margin-top: 11px;float: left ;" name="state">			  
			  				<option value="">- select -</option>			  			
			  				 <c:forEach var="state" items="${states }">
			  					<option value="${state.state }">${state.state }</option>		
			  				</c:forEach>	  			
						</select>
						
					</div>  
				   
				<!-- Modiified by Jeevan on July 18,2013 to add States and Utility for Dynamic Search -->   
				   
				   <div class="fc-text" style="margin-top:15px;clear:both">Agent Name:</div>
				   	 <div class="fc-text1">
				    	<select style="border:1px solid #ccc; margin-left:33px; margin-top: 11px;   height: 25px; width: 125px;float: left ; " name="agentName">
						  <option value="">- select -</option>
						  <c:forEach var="agent" items="${agents }">
						  	<option value="${agent.firstName} ${agent.lastName}">${agent.firstName} ${agent.lastName}</option>
						  </c:forEach>
						</select>
					</div> 
					<!-- Modiified by bhagya on April 30,2014 to add  Rescinded Order agent for Dynamic Search -->   
				   
				   <div class="fc-text" style="margin-top:15px;clear:both">Rescinded Order Agent:</div>
				   	 <div class="fc-text1">
				    	<select style="border:1px solid #ccc; margin-left:33px; margin-top: 11px;   height: 25px; width: 125px;float: left ; " name="resAgentName">
						  <option value="">- select -</option>
						  <c:forEach var="agent" items="${agents }">
						  	<option value="${agent.firstName} ${agent.lastName}">${agent.firstName} ${agent.lastName}</option>
						  </c:forEach>
						</select>
					</div>     
				   
				   <div class="fc-text" style="margin-top:15px;clear:both">Business Name:</div>
				   <div class="fc-text1">
				   	  <select style="border:1px solid #ccc; margin-left:33px; margin-top: 11px;    height: 25px; width: 225px;float: left ; " name="businessName">
			  <option value="">- select -</option>
			  <c:forEach var="businessName" items="${businessNames}">
			  				<option value="${businessName}">${businessName}</option>
			  			
			  			</c:forEach>
			</select></div>    
			
			 
				   
				   <div class="fc-text" style="margin-top:15px;clear:both">Status:</div>
				   
					   <div class="fc-text1">
					   	  <select style="border:1px solid #ccc; margin-left:33px; margin-top: 11px;  height: 50px; width: 125px;float: left ; " name="status" multiple="multiple">
							  <option value="">- select -</option>
							  <option value="agent">Agent</option>
							  <option value="under review">Under Review</option>
							  <option value="approved">Approved</option>
								<c:if test="${empty rescinded }">	 <option value="rescinded">Rescinded</option></c:if>
						</select>
						
						<div style="margin-top:20px; color:red;margin-left: 10px;">
								* ctrl+click to select multiple status
						</div>
					</div>     
			    	  </div>
			    
			  
			    
			    <div class="su-menu">
			      
			      <span class="button">
			        <input name="submit" value="Search" id="submit_button" class="button80" style="height: 33px;width: 133px"tabindex="10" type="submit" />
			      </span> </div>
			 
			   <div class="login-desc">
			      <a href="#" id="close"  style="color:#4B4D50;">Cancel</a>
			    </div>
			   
			</div >
		</form:form>    	
			
			    					</div>
							</div>
				
			</div>
	</div>
	  
	  
	 <!--  Multi Search UI  -->  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
		
		<div class="title">Pipeline</div>
		
		<!-- <div id="sort">
		<label>Sort By</label>
				<select>
					<option>Agent Name</option>
					<option>Agent Id</option>
					<option>Supplier</option>
					<option>Deal Date</option>
				</select>
		</div> -->
		
		<!-- For Filtering Results.. -->
		
	  <div>
		<div id="orderfilter" style="text-align: center;margin-top: 10px;width: 50%;margin: 25px auto;margin-bottom: 5px;" class="bg_purple1">
		<c:choose>
			<c:when test="${ empty rescinded }">
				 <form method="get" action="./getfilteredorder.do?range="${range}" onsubmit="return validatefilter();">
			</c:when>
			<c:otherwise>
				 <form method="get" action="./getannivpayfilteredorder.do?range="${range}" onsubmit="return validatefilter();">
			</c:otherwise>
		</c:choose>
				<label style="font-size: 14px;font-family: arial;font-weight: bold;">Search By: </label>
				 <select name="field" style="width:147px; padding-top:4px;padding-bottom:0px;padding-right:1px;border: 1px solid;border-color: #999999 #DDDDDD #DDDDDD;height: 25px;position: relative;top: 2px;" id="selectfilter" index="1">
				    	 <option value=""  data-e=""></option>
						<option value="accountNumber" ${field=='accountNumber'? 'selected' : '' } data-e="string">Account Number</option>
						
				 	<option value="agent" ${field=='agent' ? 'selected' : '' } data-e="string">Agent </option>
				 	<option value="businessName" ${field=='businessName'? 'selected' : '' } data-e="string">Business Name</option>	
				 	<option value="dba" ${field=='dba'? 'selected' : '' } data-e="string">DBA</option>	
				 	<option value="phoneNo" ${field=='phoneNo'? 'selected' : '' } data-e="string">Phone No</option>	
				 	<option value="customer" ${field=='customer'? 'selected' : '' } data-e="string">Contact</option>	
				 	<option value="taxId" ${field=='taxId'? 'selected' : '' } data-e="string">Tax ID</option>				 
				 	<option value="orderDate"  ${field=='orderDate' ? 'selected' : '' } data-e="date">Order Date</option>
				 	<!-- <option value="taxId.taxId" data-e="string">Tax ID</option>
				 	<option value="tpv" data-e="long">tpv</option>	
				 	<option value="status" data-e="string">Status</option> -->
				 	
				 </select>
				<c:choose>
					 <c:when test="${not empty value }">
						 <input type="text" class="filtertext" id="value" style="width:187px; padding-right:1px; margin-top:5px; margin-bottom:5px;text-align: center;height:15px;" value="${value}" name="value"  >
					</c:when>
					<c:otherwise>
						<input type="text"  class="filtertext" id="value" style="width:187px; padding-right:1px; margin-top:5px; margin-bottom:5px;text-align: center;height:15px;" value="Enter Filter Value" name="value" >
					</c:otherwise>
					    
				 </c:choose>
				 
					
				    <input id="orderDatepicker" type="text"  readonly="readonly"  style="width:172px; padding-right:1px; margin-top:5px; margin-bottom:5px;text-align: center;display:none;height: 15px;" value="${value }"/>
				 <input type="hidden" name="type" id="filtertype" value="${type}">
				 
				<button type="submit" class="searchbtn" id="searchBtn"></button>
				
		 </form>
		 			<span id="filtererror" style="font-size:12px;color: red"></span>
		 				
		</div>
		<c:if test="${empty dues }">
		 <div  style="clear: left;float: right;width: 23%;margin: -38px auto;">
			<a href="#" id="pop" >Advanced Search</a>
			<a href="./getpipeline.do"  style="font-size: 15px;color: blue;margin-left:30px;float:left;text-decoration: underline;" >Reset</a>
		 </div>
		</c:if>
	</div>
	
	<div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;">
	 				${message}	  
	 </div>
		
	<c:if test="${empty message}">
		

		<div style="text-align: center;"> 
			<span class="error_msg" id="pipeline1_error" style="font-size: 12px;"></span>
			<span class="error_msg" id="pipeline2_error" style="font-size: 12px;"></span>
			<span class="error_msg" id="pipeline3_error" style="font-size: 12px;"></span>
			<span class="error_msg" id="pipeline4_error" style="font-size: 12px;"></span>
		</div>
		
		
	<!--  PIPELINE Table  -->
		<form action="" method="post" name="form1" id="form1">
		<table  cellspacing="0" cellpadding="0" style="margin: 0px auto;">
                                      <tbody><tr>
                                        <td  bgcolor="#FFFFFF">
										<table  cellspacing="0" cellpadding="0">
                                    <tbody><tr>
                                      <td  bgcolor="#FFFFFF">
                                          
									
									
										  <div style="margin: 0px auto">
										  	 <div  style="height: 50px;">
		
												<c:if test="${message ne 'No Orders Found'}">
												
												<div class="pagecount" style="float: left;margin-left: 20px;">
														 Page <b>${page +1}</b> of <b>${end }</b>
												</div>
												<div class="paginationbody">
												
												<ul class="pager" >
														<div style="float: right;text-align: right;">
												      <c:choose>
												      	<c:when test="${page eq 0 && end ne 1 }">
												      	
												      		<li>	 <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>
												      	</c:when>
												      
												        <c:when test="${page eq end-1 && end ne 1 }">
												        			
												        				
												            <li>    <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
												     
												        </c:when>
												        
												        <c:when test="${page eq end-1 && end eq 1 }">
												               
												        </c:when>
												        
												        <c:otherwise>
												        	<li>	<a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
															<li> <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>												        
												        </c:otherwise>
												      </c:choose>
												      </div>
												      <div class="pagecount"> Showing ${first}- ${last} of ${total } Orders</div>
												</ul>
												</div>
												</c:if>
												</div>
										  
                                            <div style="width:auto;  position:relative;clear: both;">
                                              <table class="pptbl" cellpadding="1" cellspacing="1" bgcolor="#B2B2B2" style="border-right: 1px solid #c8cfd2; padding: 3px 1px;">
                                                <tbody>
                                                <tr style=" background:url(/hoveyenergy/images/actions-bg.png);border-bottom: 1px solid #C0C8D0; height:30px;/* background-color: rgb(253, 224, 196) */ background-color: #4c4c4c;" class="headerrow">
                                                  <td  height="35" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=orderDate">Order Date</a></td>
                                                  <td height="35" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;width: 50px;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=supplierName">Supplier Name</a> </td>
                                                  <td  height="35" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=accountNumber">Account Number </a> </td>
                                                  <td height="35" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;width: 125px;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=businessName">Business Name </a>  </td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=dba">DBA</a></td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=contractType">Contract Type</a></td>
                                                  <td  height="35" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=kwh">Kwh /Therm</a></td>
                                                  <td  height="35" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=agent">Agent Name</a> </td>
                                                   <td  height="35" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=agent.agentNumber">Agent No</a> </td>
                                                  <td align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=status">Status </a></td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=commissionRate">Com Rate</a></td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=commission">Upfront Commission Expected</a></td>
                                                  <!-- Modified by bhagya on may23rd,2014 added a Term commission field -->
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=commission">Annual Commission Expected</a></td>
                                                  <td align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=sentToSupplier"> Sent to Supplier</a>  </td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=dealStartDate"> Deal Start Date</a>  </td> </td>
                                                  <td align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=upfrontCommission"> Annual Commission Received</a>  </td></td>
                                                 <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=upfrontPaidDate">Upfront Paid Date</a>  </td></td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=term">Term</a></td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=faxReceived">Bill Rcvd</a></td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=QA">QA</a></td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=notes">Notes</a></td>
                                                  <td  align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&sortby=notes">Rescinded Order Agent</a></td>
                                                  
                                                  
                                                  
                                                  
                                                 <!-- 
                                                  <td width="5%" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;"><a href="./getorders.do?sortby=tpv">TPV</a></td>
                                                  <td width="5%" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;">Edit1</td>
                                                  <td width="5%" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;">Edit2 </td>
                                                  <td width="5%" align="center" class="title1" style="font-family:arial; font-size:14px; color:#666;">Edit3</td> -->
                                                 
                                                </tr>
                                                
                                               <c:forEach var="order" items="${orders}">  
                                                <tr id="${order.id}">
                                                	<input type="hidden" name="orderId" value="${order.id}" id="orderId${order.id}" />
                                                  <td height="35" align="center" ><fmt:formatDate value="${order.orderDate}"  pattern="MM/dd/yyyy"/>
                                                  			<input type="hidden" id="orderDate${order.id}" value="<fmt:formatDate value='${order.orderDate}'  pattern='MM/dd/yyyy'/>" />
                                                 </td>
                                                  <td height="35" align="center" >
                                                  		<c:choose>
                                                  			<c:when test="${order.supplierName.supplierName eq 'Champion Energy'}">
                                                  				Champion
                                                  			</c:when>
                                                  			<c:when test="${order.supplierName.supplierName eq 'Glacial Energy'}">
                                                  				Glacial
                                                  			</c:when>
                                                  			<c:otherwise>
                                                  				${order.supplierName.supplierName}
                                                  			</c:otherwise>
                                                  		</c:choose>
                                                  </td>
                                                 
                                                 
                                             
                                                  
                                                  <td height="35" align="left" bordercolor="#666666" >
                                                  	<a href="#" style="color: blue;text-decoration: underline;"onClick="window.open('/hoveyenergy/printdeal.do?dealId=${order.transDto.transactionId}','mywindow','width=1280px,height=800px,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,copyhistory=no,resizable=yes')">${order.accountNumber }</a>
                                                  			<input type="hidden"  value="${order.accountNumber }"   data_index="${order.id}" id="account${order.id}" />
                                                  	</td>
                                                  <td height="35" align="left" bordercolor="#666666" style="font-weight: bold;"> ${order.businessName }</td>
                                                   <td height="35" align="center" bordercolor="#666666" >${order.dba }</td>
                                                   
                                                   
                                                   <!--  modification done on contract types by bhagya on May 07th,2014 -->
                                                   <td height="35" align="center" bordercolor="#666666" >
                                                   <select name="contractType" id="contract${order.id}" onblur="editpipeline(this)" data_index="${order.id}" style="text-transform: capitalize;margin: 0px;" class="pipeline">
                                                   <c:forEach var="contractTypes" items="${contractTypes}">
			   		 								<option value="${contractTypes.contractType }" ${order.contractType eq contractTypes.contractType ? 'selected' : ''  } > ${contractTypes.contractType}</option>
                                                   </c:forEach>
                                                   </select>
                                                   </td>
                                                   
                                                   	<!-- 	end of the modification on contract types	 -->	                                       		
                                                   
                                                   <fmt:setLocale value="en_US"/>
                                                   <td height="35" align="center" bordercolor="#666666" ><fmt:formatNumber groupingUsed="true" value="${order.kwh}" />
                                                   			<input type="hidden" id="kwh${order.id}" value="${order.kwh }"  />
                                                   </td>
                                                  <td height="35" align="center" bordercolor="#666666" >${order.createdAgent.firstName } ${order.createdAgent.lastName }</td>
                                                    <td height="35" align="center" bordercolor="#666666" >${order.createdAgent.agentNumber } </td>
                                                  <td width="60px;" height="35" align="center" bordercolor="#666666" >
                                                  	<select name="status" id="status${order.id}" class="pipeline" style="margin:0px;width:73px; "  size="1"  onblur="editpipeline(this)" data_index="${order.id}"  >
                                                      <option value="agent" ${order.status eq 'Agent' ? 'selected' : '' }>Agent</option>
												        <option value="under review" ${order.status eq'Under Review' ? 'selected' : '' }>Under Review</option>
												        <option value="approved" ${order.status eq 'Approved' ? 'selected' : '' }>Approved</option>
												         <option value="rescinded" ${order.status eq 'Rescinded' ? 'selected' : '' }>Rescinded</option>
												        
												       
												      </select>
											      </td>
											      	<td align="center" bordercolor="#666666" ><input type="text1" name="commissionRate"  onchange="changeCommission(this)" data_index="${order.id}" id="comRate${order.id}" value="<fmt:formatNumber  groupingUsed='true' value='${order.commissionRate}' maxFractionDigits="3" minFractionDigits="3" />"   onblur="editpipeline(this)" data_index="${order.id}"/></td>
                                					  <td align="center" bordercolor="#666666" >
                                					  		<c:choose>
                                					  			<c:when test="${order.specialPricing eq true }">
                                					  				<input type="text1" name="commission" id="commission${order.id}"  value="<fmt:formatNumber  groupingUsed='true' value='${order.commission}' maxFractionDigits="2" minFractionDigits="2" />"   onblur="editpipeline(this)" data_index="${order.id}" style="background-color: #F7F76D;"  />
                                					  			</c:when>
                                					  			<c:otherwise>
                                					  			      <input type="text1" name="commission" id="commission${order.id}"  value="<fmt:formatNumber  groupingUsed='true' value='${order.commission}' maxFractionDigits="2" minFractionDigits="2" />"   onblur="editpipeline(this)" data_index="${order.id}"   />                 					  			
                                					  			</c:otherwise>
                                					  		</c:choose>
                                					  		<!-- modified by bhagya on may 23rd,2014,added a term commission -->
                                					  	<td align="center" bordercolor="#666666">
                                					  		<input type="text1" name="termCommission" id="termCommission${order.id}" readonly="readonly" value="<fmt:formatNumber  groupingUsed='true' value='${order.termCommission}' maxFractionDigits="2" minFractionDigits="2" />"   data_index="${order.id}"  style="width:50px;" />
                                					  	</td>
                                					  		<input type="hidden" value="${order.termMonths }" id="termMonth${order.id }" />
                                					  <!-- 	end of the modification -->
                                                  <td align="center" bordercolor="#666666"><input type="text1" name="sentToSupplier" class="datepicker" id="sentToSupplier${order.id}" style="width:70%;" value="<fmt:formatDate value='${order.sentToSupplier}'  pattern='MM/dd/yyyy'/>"   data_index="${order.id}" readonly="readonly"/></td>                 
                                                    <td width="100px" align="center" bordercolor="#666666" ><div class="deal"><input type="text1" name="dealStartDate"  class="datepicker2" id="dealStartDate${order.id}" style="width:60px;" value="<fmt:formatDate value='${order.dealStartDate}'  pattern='MM/dd/yyyy'/>" data_index="${order.id}" readonly="readonly"/></div></td>
                                                  <td align="center" bordercolor="#666666" ><input type="text1" name="upfrontCommission"  onblur="editpipeline(this)" data_index="${order.id}" id="upfrontCommission${order.id}" value="<fmt:formatNumber  groupingUsed='true' value='${order.upfrontCommission}' maxFractionDigits="2" minFractionDigits="2" />"    data_index="${order.id}"/></td>
                                                  
                                                  
                                                
                                                 
       
     												<td align="center" bordercolor="#666666" ><input type="text1" name="upfrontPaidDate" class="datepicker" style="width:70%;" id="upfrontPaidDate${order.id}" value="<fmt:formatDate value='${order.upfrontPaidDate}'  pattern='MM/dd/yyyy'/>"  onchange="editpipeline(this)" data_index="${order.id}" readonly="readonly" /></td>
                                                <td align="center" bordercolor="#666666" >
                                                	<select name="term" class="pipeline"  id="term${order.id}"  onblur="editpipeline(this)" data_index="${order.id}" onKeyDown="fnKeyDownHandler_A(this, event);" onKeyUp="fnKeyUpHandler_A(this, event); return false;" onKeyPress = "return fnKeyPressHandler_A(this, event);"  onChange="fnChangeHandler_A(this);" onFocus="fnFocusHandler_A(this);" style="width: 44px;">
                                                		<option value="3" ${order.term=='3' ? 'selected' : '' }>3</option>
                                                		<option value="6" ${order.term=='6' ? 'selected' : '' }>6</option>
                                                		<option value="9" ${order.term=='9' ? 'selected' : '' }>9</option>
                                                		<option value="12" ${order.term=='12' ? 'selected' : '' }>12</option>
                                                		<option value="18" ${order.term=='18' ? 'selected' : '' }>18</option>
                                                		<option value="24" ${order.term=='24' ? 'selected' : '' }>24</option>
                                                	<!--  Added by Jeevan on April 01, 2014 as per clients need to have terms fromn 24 to 36 -->
                                                		<c:forEach var="termcount" begin="25" end="36" step="1">
                                                			<option value="${termcount }" ${order.term== termcount ? 'selected' : '' }>${termcount }</option>
                                                		</c:forEach>
                                                		<option value="1" ${order.term=='1' ? 'selected' : '' }>Index</option>
                                                	<!--  Added by Jeevan on April 01, 2014 as per clients need to have terms fromn 24 to 36 -->
                                                    </select>
                                                	
                                                </td>
                                                <td align="center" bordercolor="#666666" >
                                                		<c:choose>
                                                			<c:when test="${order.faxReceived eq true }">
                                                				<input type="checkbox" name="faxReceived" checked="checked"  id="faxReceived${order.id}" value="1"  onclick="editpipeline(this)" data_index="${order.id}"/>
                                                			</c:when>
                                                			<c:otherwise>
                                                				<input type="checkbox"  name="faxReceived" id="faxReceived${order.id}" value="0"  onclick="editpipeline(this)" data_index="${order.id}" />
                                                			</c:otherwise>
                                                		</c:choose>
                                                
                                                </td>	
                                                
                                                <td align="center" bordercolor="#666666" >
                                                		<c:choose>
                                                			<c:when test="${order.QA eq true }">
                                                				<input type="checkbox" name="QA" checked="checked"  id="QA${order.id}" value="1"  onclick="editpipeline(this)" data_index="${order.id}"/>
                                                			</c:when>
                                                			<c:otherwise>
                                                				<input type="checkbox"  name="QA" id="QA${order.id}" value="0"  onclick="editpipeline(this)" data_index="${order.id}" />
                                                			</c:otherwise>
                                                		</c:choose>
                                                
                                                </td>	
                                                
                                                
                                                
                                                
                                                  <td align="center" bordercolor="#666666" ><input type="text1" name="notes"  id="notes${order.id}" value="${order.notes}"  title="${order.notes}" onblur="editpipeline(this)" data_index="${order.id}"/></td>
                                                 
                                                 
                                                 <!--  added by bhagya on april 15th 2014 -->
                                                   
                                                   
                                                   <td height="35" align="center" bordercolor="#666666" >
                                                   	<select style="border:1px solid #ccc; margin-left:0px; margin-top: 11px;   height: 25px; width:75px;float: left ; " name="resAgent"
                                                   	data_index=${order.id } onblur="editpipeline(this);"id=resAgent${order.id}>
						 								 <option value="">- select -</option>
						  									<c:forEach var="agent" items="${agents }">
						  								<option value="${agent.agentNumber }" ${order.resAgent.agentNumber eq agent.agentNumber ? 'selected' : '' }>${agent.firstName} ${fn:substring(agent.lastName, 0,1)}</option>
						  									</c:forEach>
														</select></td>
														<!--  --> 
                                                   <td></td>
                                                </tr>
                                                </c:forEach>
                                                
                                               
                                              </tbody></table>
                                            </div>
                                          </div>
										  
                                     </td>
                                    </tr>
                                    
                                      </tbody></table>
										
										
										
                                        </td>
                                      </tr>
                                      <tr>
                                        <td height="35" style=" background:url(/hoveyenergy/images/actions-bg.png) repeat-x;">&nbsp;</td>
                                      </tr>                    
                                    
                                  </tbody></table>	
		
		
					  		
                                      	 <div style="text-align: center;">
                                      			<div style="text-align: center;width: 620px;margin: 0 auto;">									
													<ul id="pagin">
													 <!--  For Previous -->	
													 
													 	
														<c:if test="${page ne 0 }">
														<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=0">First</a></li>
															<li>  <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${page-1}">Prev</a></li>
														</c:if>
													
														<c:choose>
														
																	<c:when test="${end eq 1 }"></c:when>
																<c:when test="${end lt 10 }">
																	<c:forEach var="i" begin="0" end="${end-1 }">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>																
																</c:when>
																
																<c:when test="${ page  lt 5}">
																	<c:forEach var="i" begin="0" end="9">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>																
																</c:when>
																
																
																<c:when test="${page ge 5 && page+5 le end }">
																		<c:forEach var="i" begin="${page-5 }" end="${page+4 }">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>												
																</c:when>
																
																<c:otherwise>
																	<c:forEach var="i" begin="${end-10 }" end="${end-1 }">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>															
																</c:otherwise>								
														</c:choose>									
													 <!-- For Next -->
													  
													   <c:if test="${page lt end-1 }">
															<li>  <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${page+1}">Next</a></li>
														</c:if>
														<c:if test="${page ne end-1 }">
													  		<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${end-1}">Last</a></li>
														</c:if>
													</ul>					
												</div>
                                      	
                                      	                                  		
	                                      	  	<div style="float:right;font-size: 14px;">
	                                      	  		<label><b>View Range:</b></label>
	                                      			<select name="pageSize" class="pipeline" id="ordersrange" style="height: 24px;margin-top: 0px;padding: 3px;font-weight: bold;font-size: 11px;" >
	                                      				<option value="20" ${range=='20' ? 'selected' : '' }>20</option>
	                                      				<option value="50"  ${range=='50' ? 'selected' : '' }>50</option>
	                                      				<option  value="100"  ${range=='100' ? 'selected' : '' }>100</option>
	                                      			</select>
	                                      		</div>
	                                     </div>
	                                     
	                                      <div style="text-align: center;clear: both;margin-top: 55px;">	    
                                      	   		<a href="#" class="btn btn-update" style=" width: 180px;"   onclick="window.location.href='/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&output=excel'">Export to Excel</a>
	                                      	 	<a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&output=pdf" class="btn btn-back" style=" width: 180px;margin-left: 20px;"  target="_blank" >Get Report</a>
                                      	 </div>
		 </form>
					           
		
	</c:if>
	

		
		
			<!--  PIPELINE Table  -->
		
		
	  
	
	</div>
  
 </body>
</html>