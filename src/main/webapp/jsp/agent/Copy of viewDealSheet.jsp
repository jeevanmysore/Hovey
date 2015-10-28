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
		
		<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	
	 <script>
	 	$(document).ready(function(){
	 		
	 		//$('#datepicker').val("<fmt:formatDate value="${now}" pattern="MM/dd/yyyy " />");
	 		
	 		
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
			
			<form:form method="post" id="dealsheet" name="dealsheet" class="appnitro" action="./dealsheet.do" commandName="dealSheet"  >	
				<div class="form_description">
					<h3>Agent Details </h3>								
				</div>
										
				
					<table width="100%" cellspacing="0" cellpadding="0">
	              	<tr>
	               		 <td width="33%">
	               		 <ul>
	                  		<li id="li_3">
								<label class="description" for="element_3">Date </label>
								<div>
								 
									  <input  value="<fmt:formatDate value="${orders[0].orderDate}" pattern="MM/dd/yyyy HH:mm ss" />" type="text" readonly="readonly" />
								 
								</div>
							</li> 
							</ul>
		               </td>
	                	<td width="33%">
	                		<ul>
		                 		 <li class="" id="li_1">
		                    		<label class="description" for="element_1">Agent Name </label>
		                    		<div>
		                      			<input  type="text" value="${orders[0].createdAgent.firstName} ${orders[0].createdAgent.lastName}" readonly="readonly" />
		                   			 </div>
		                  		</li>
	                	</ul>
	                </td>
	                <td width="34%">
	                <ul>
	                 	 <li id="li_2">
	                    	<label class="description" for="element_2">Agent Number </label>
	                    	<div>
	                      		<input type="text" value="${orders[0].createdAgent.agentNumber}" readonly="readonly" />
	                    	</div>
	                   </li> </ul>  
	              </td>
	           </tr>
	             
	       </table>

				
		  <div class="form_description">
			    <h3>Customer Details 
					
				</h3>
					
		   </div>						
			<ul>
				<table width="100%" cellspacing="0" cellpadding="0" style="width:1200px;">
	                <tr>
	               	 	 <td>
	                 		 <li id="li_2">
	                   			 <label class="description" for="label5">Tax ID  <span id="custtax_error" class="error_msg">*</span></label>
	                    		  <div>
	                      				<input id="taxid"  type="text" value="${orders[0].taxId.taxId }" readonly="readonly"/>
	                      				
	                    		</div>
	                 		 </li>
	                    </td>
	                    <td>
	                 		 <li class="" id="li_1">
	                   			 <label class="description" for="label">First Name  <span id="custname_error" class="error_msg">*</span></label>
	                    		<div>
	                      			<input  type="text"  value="${orders[0].taxId.firstName }" readonly="readonly"/>
	                    		</div>
	                  		</li>
	                   </td>
	                    <td>
	                  		<li id="li_2">
	                    		<label class="description" for="label2">Last Name   <span id="custname2_error" class="error_msg">*</span></label>
	                    		<div>
	                      			<input  type="text"  value="${orders[0].taxId.lastName }" readonly="readonly"/>
	                    		</div>
	                  		</li>
	                  </td>
	                  <td>
	                  		<li id="li_2">
	                    		<label class="description" for="label2">Title  <span id="custtitle_error" class="error_msg">*</span></label>
	                    		<div>
	                      			<input  type="text"  value="${orders[0].taxId.title}" readonly="readonly"/>
	                   		 	</div>
	                  		</li>
	                  </td>
	                  
	             </tr>
	             
	          
	              
	         <tr>
	          		<td>
	                  		<li id="li_2">
	                    		<label class="description" for="label2">Email  <span id="custemail_error" class="error_msg"></span></label>
	                    		<div>
	                      			<input  type="text"  value="${orders[0].taxId.email }" readonly="readonly"/>
	                   		 	</div>
	                  		</li>
	                  </td>
	                  <td>
	                  		<li id="li_2">
	                    		<label class="description" for="label2">Phone Number  <span id="custphone_error" class="error_msg">*</span></label>
	                    		<div>
	                      			<input  type="text"  value="${orders[0].taxId.phoneNo }" readonly="readonly"/>
	                   		 	</div>
	                  		</li>
	                  </td>
	         	<td>
	                  	<li id="li_2">
	                   		 <label class="description" for="label6">Tax Exempt (Y/N)</label>
	                    	
	                      		<div>
	                        		<input  type="text"  value="${orders[0].taxId.taxExempt }" readonly="readonly"/>
	                      		</div>
	                    	
	                  </li>          
                  </td>
	             <td>
	                 <li id="li_2">
	                       <label class="description" for="label6">Fax  <span id="fax_error" class="error_msg"></span></label>
	                    	<div>
	                      		<input  type="text"  value="${orders[0].taxId.faxNo }" readonly="readonly"/>
	                    	</div>
	                  </li> 
                </td>
                <td>&nbsp;</td>
	            <td>&nbsp;</td>
	        </tr>
	              
	     </table>
	  </ul>
		  		
	  <div class="form_description">
			<h3>Order Details </h3>
	   </div>
	      <c:set var="i" value="1"></c:set>
	     <c:forEach items="${orders}" var="order">						
			<ul>
			<table width="96%" cellspacing="0" cellpadding="0" style="width:1200px;">
			<tr>
				<td><h4 style="font-size:16px; text-decoration: underline;">Account #${i}</h3></td>
				
			</tr>
	         <tr>
	         	<td width="25%">
	           	  <ul>
	                    <li>
	                    	<label class="description" for="label14">Utility <span id="utiltiy0_error" class="error_msg">*</span></label>
	                    	<div>
	                    	  	 <input  type="text"  value="${order.utility.utility }" readonly="readonly"/>                      
	                      	
	                       </div>
	                   </li>
	              </ul> 
	         </td>
	         <td>
	    		<ul>
	            	<li id="li_2">
	                    <label class="description" for="label11">Account Number <span id="acc_error0" class="error_msg">*</span></label>
	                    <div>
	                        <input  type="text"  value="${order.accountNumber }" readonly="readonly"/>   
	                    </div>
	                 </li>
	           </ul>
	       </td>
	       <td width="25%">
                <ul>
	           		<li>
	                    <label class="description" for="label14">Rate Class  <span id="rateclass_error0" class="error_msg"></span> </label>
	                    <div>
	                      <input  type="text"  value="${order.rateClass }" readonly="readonly"/>   
	                    </div>
	               </li>
	          </ul>
             </td>
             <td>
	              <li id="li_2">
	              	<label class="description" for="label8">Rate  <span id="rate_error0" class="error_msg">*</span></label>
	                    <div>
	                         <input  type="text"  value="${order.rate }" readonly="readonly"/>   
	                    </div>
	             </li>  
	        </td>
	       
	               
               <%--  <td width="25%">
	            	<li id="li_2">
	                    <label class="description" for="label7">TPV  <span id="tpv_error" class="error_msg"></span></label>
	                    <div>
	                      <spring:bind path="dealSheet.orders[0].tpv">
	                    	<input id="tpv" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                    	 </spring:bind>
	                    </div>
	                </li>  
               </td> --%>
                
	       </tr>
	              
	       <tr>
	           
	          
	        <td colspan="4" id="test">
	        	<ul class="smallul">
	            	<li id="li_2">
	                    <label class="description" for="label13">Term  <span id="term_error0" class="error_msg">*</span></label>
	                    <div>
	                       <input type="text" readonly="readonly" value="${order.term }" />
	                   
	                    </div>
	                </li>
	           </ul> 
	      <!--  </td>
	       <td> -->
	       	<ul class="smallul">
	            <li id="li_2">
	                    <label class="description" for="label12">KWH  <span id="kwh_error0" class="error_msg">*</span></label>
	                    <div>
	                       <input type="text" readonly="readonly" value="${order.kwh }" />
	                     </div>
	             </li>
	        </ul> 
	     <!--  </td>
	      <td> -->
	       	<ul>
	            <li id="li_2">
	                    <label class="description" for="label12">TPV Number  <span id="tpv_error0" class="error_msg">*</span></label>
	                    <div>
	                      <input type="text" readonly="readonly" value="${order.tpv }" />
	                     </div>
	             </li>
	        </ul> 
	     <!--  </td>
	        <td> -->
	       	  <ul class="bigul" style="margin-left: 12px;">
	          	   <li id="li_2">
	                    <label class="description" for="label9">Business Name  <span id="bname_error0" class="error_msg">*</span> </label>
	                     <div>
	                        <input type="text" readonly="readonly" value="${order.businessName }" />
	                    </div>
	               </li>
	         </ul>
	    <!--   </td>
	      <td> -->
	      	<ul class="bigul" style="margin-left: -12px;">
	     		<li id="li_2" style="padding-left: 16px;">
	           		<label class="description" for="label10">DBA (If Applicable)</label>
	           		<div>
	           		  <input type="text" readonly="readonly" value="${order.dba }" />
	           		</div>
	       		</li>
	   		</ul> 
          </td>
          
	   </tr>
	    <tr>
	    	
	       
	      <td>&nbsp;</td>
	    </tr>
	    <tr>
	       <td><label class="form_description" for="label13"><strong style="font-size:14px; font-weight:700;">Service Address</strong></label></td>
	       <td>&nbsp;</td>
	        <td>&nbsp;</td>
	    </tr>
	    <tr >
	       <td >
	    	   <li>
	           		<label class="description" for="label13">Street  <span id="s_street_error0" class="error_msg">*</span></label>
	                <div>
	                      <input  type="text"  value="${order.serviceStreet }" readonly="readonly"/>   
	                 </div>
	            </li>
	        </td>
	        <%-- <td>
	        	<ul>
	                <li>
	                    <label class="description" for="label13">Unit  <span id="s_unit_error" class="error_msg"></span></label>
	                    <div>
	                       <spring:bind path="dealSheet.orders[0].serviceUnit">
	                      	<input id="s_unit" name="${status.expression}" class="element text medium" maxlength="255" type="text"  />
	                      	</spring:bind>
	                    </div>
	                </li>
	            </ul>
	      </td> --%>
	      <td>
	      	<ul>
	           <li>
	               <label class="description" for="label13">City  <span id="s_city_error0" class="error_msg">*</span></label>
	               <div>
	                   <input  type="text"  value="${order.serviceCity }" readonly="readonly"/>   
	                </div>
	           </li>
	        </ul>
	     </td>
	     <td>
	      	<ul>
	           <li>
	                <label class="description" for="label13">State  <span id="s_state_error0" class="error_msg">*</span></label>
	                 <div>
	                   <input  type="text"  value="${order.serviceState }" readonly="readonly"/>   
	                  </div>
	            </li>
	       </ul>
	   </td>
	  		 <td>
	  	<ul>
	       <li>
	           <label class="description" for="label13">Zip  <span id="s_zip_error0" class="error_msg">*</span></label>
	           <div>
	               <input  type="text"  value="${order.serviceZip }" readonly="readonly"/>   
	            </div>
	            
	       </li>
	    </ul>
	 </td>
	   </tr>
	   
	   <tr>
	      
	  
	 <td>&nbsp;</td>
   </tr>
	<tr>
	       <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	  </tr>
	   <tr>
	         <td colspan="2"><label  ><strong style="font-size:14px; font-weight:700;">Billing Address</strong>
	             
	          </label></td>
	          <td>&nbsp;</td>
	          <td>&nbsp;</td>
      </tr>
	   <tr>
	       <td><ul>
	            <li>
	                <label class="description" for="label13">Street  <span id="b_street_error0" class="error_msg"></span></label>
	                <div>
	                   <input  type="text"  value="${order.billingStreet }" readonly="readonly"/>   
	                 </div>
	            </li>
	       </ul></td>
	        <%-- <td><ul>
	             <li>
	                 <label class="description" for="label13">Unit <span id="b_unit_error" class="error_msg"></span></label>
	                 <div>
	                   <spring:bind path="dealSheet.orders[0].billingUnit">
	                      <input id="b_unit" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                   </spring:bind>
	                  </div>
	              </li>
	         </ul></td> --%>
	          <td><ul>
	              <li>
	                  <label class="description" for="label13">City  <span id="b_city_error0" class="error_msg"></span></label>
	                   <div>
	                       <input  type="text"  value="${order.billingCity }" readonly="readonly"/>   
	                    </div>
	                </li>
	           </ul></td>
	           <td><ul>
	                 <li>
	                     <label class="description" for="label13">State  <span id="b_state_error0" class="error_msg"></span></label>
	                      <div>
	                         <input  type="text"  value="${order.billingState }" readonly="readonly"/>   
	                      </div>
	                  </li>
	            </ul></td>
	            <td valign="middle"><ul>
	                <li>
	                  <label class="description" for="label13">Zip  <span id="b_zip_error0" class="error_msg"></span></label>
	                   <div>
	                      <input  type="text"  value="${order.billingZip }" readonly="readonly"/>   
	                   </div>
	                </li>
	            </ul></td>
	      </tr>
	             
	       <tr>	            
	            
	             <td>&nbsp;</td>
	        </tr>
	             
	     </table>
	  </ul>
	    <c:set var="i" value="${i+1 }"></c:set>
	  </c:forEach>
				
		<div class="bottom_link">
			  <div class="login_button2" style="margin-left: 312px;">
			   		<a href="#"><button type="button" class="register_button2" OnClick="window.print()"  >Print</button></a>					
			  </div>
			  <div class="login_button2">					
						<a href="#"><button type="button" class="signin_button2" OnClick="window.close()" >Close</button></a>
				</div>
		</div>
      </form:form>
     </div>
  </div>
  
  
  
  
	<!-- <script type="text/javascript">
		Calendar.setup({
		inputField	 : "element_3_3",
		baseField    : "element_3",
		displayArea  : "calendar_3",
		button		 : "cal_img_3",
		ifFormat	 : "%B %e, %Y",
		onSelect	 : selectDate
		});
		Calendar.setup({
		inputField	 : "element_4_3",
		baseField    : "element_4",
		displayArea  : "calendar_4",
		button		 : "cal_img_4",
		ifFormat	 : "%B %e, %Y",
		onSelect	 : selectDate
		});
	</script>

	<script type="text/javascript">
		$(document).ready(function (){ 
	
					$(".calendar").calendar();
					$( ".datepicker" ).datepicker();				
				});
	</script>  -->
        
  </div>


    <!-- Footer Starts -->  
                        <!-- Footer Ends -->



     <div id="calendar_div"></div>
     <div id="ui-datepicker-div" class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div>
 </body>
</html>