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

	<title>Deal Sheet</title>
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
	 <script type="text/javascript" src="js/dealsheet.js"></script>
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
              
            <!-- Top Header end -->
	<form action="./logout.do" method="get" accept-charset="utf-8" id="" name="" class="" > 
	   	<div id="user" style="text-transform:capitalize;">Hi Agent  - ${username} <button type="submit" id="logout">LOGOUT</button>
	   	
        </div>
     </form>
	
	<div id="header">
     	
     	 	<div id="logo">
     	 	   <a href="./home.do">
     	 			<img src="images/logo.png" alt="logo" height="76" width="275" />
     	 		</a>
     	 	</div>
     	 
        
	    <div id="title"></div>
	    
    </div>
    <!-- <div id='cssmenu'>
		<ul>
		   <li ><a href='./home.do'><span>Home</span></a></li>
		   <li class='has-sub active '><a href='#'><span>Deal Sheets</span></a>
		      <ul>
		         <li><a href='./dealsheet.do'><span>Fill DealSheet</span></a></li>
		         <li><a href='./getuserdeals.do'><span>View Orders</span></a></li>
		      </ul>
		   </li>
		   
		</ul>
	</div> -->
    
    
    <div id="content">
	      <div style="width: 100%;">
				<ul id="menu">
					<li ><a href='./home.do'>Dashboard</a></li>
					<li><a href="./dealsheet.do">Create Deal</a></li>
					
					
				</ul>
			</div>
    
		<div id="feedback1" style="text-align: center;font-size: 18px;font-weight: bold;color: green">
    		<c:if test="${not empty param.message}" >
    				Orders Saved Successfully
    		</c:if>
    	</div>
	
	
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
								  <spring:bind path="dealSheet.orders[0].orderDate">
									  <input id="datepicker" name="${status.expression}" class="content_data1_input element text large" value="<fmt:formatDate value="${now}" pattern="MM/dd/yyyy HH:mm ss" />" type="text" readonly="readonly" />
								  </spring:bind>
								</div>
							</li> 
							</ul>
		               </td>
	                	<td width="33%">
	                		<ul>
		                 		 <li class="" id="li_1">
		                    		<label class="description" for="element_1">Agent Name </label>
		                    		<div>
		                      			<input id="agentname" name="agentname" class="element text large" maxlength="255" type="text" value="${user.firstName} ${user.lastName}" readonly="readonly" />
		                   			 </div>
		                  		</li>
	                	</ul>
	                </td>
	                <td width="34%">
	                <ul>
	                 	 <li id="li_2">
	                    	<label class="description" for="element_2">Agent Number </label>
	                    	<div>
	                      		<input id="agentnumber" name="agentnumber" class="element text medium" maxlength="255" type="text" value="${user.agentNumber}" readonly="readonly" />
	                    	</div>
	                   </li> </ul>  
	              </td>
	           </tr>
	             
	       </table>

				
		  <div class="form_description">
			    <h3>Customer Details 
					<input type="checkbox" name="cust_check"  id="cust_check" value="1"  style="margin-left: 20px;" />
					     <span style="font-family:Arial, Helvetica, sans-serif; font-size:14px;" >Click here for Existing customer</span>
				</h3>
					<input type="hidden" name="customer.customerId" id="custid" value="0" />	
		   </div>						
			<ul>
				<table width="100%" cellspacing="0" cellpadding="0" style="width:1200px;">
	                <tr>
	               	 	 <td>
	                 		 <li id="li_2">
	                   			 <label class="description" for="label5">Tax ID  <span id="custtax_error" class="error_msg">*</span></label>
	                    		  <div>
	                      				<input id="taxid"  class="element text medium" maxlength="255" type="text" name="customer.taxId" />
	                      				<select class="element select medium" style="padding:4px 0;display:none;margin-top: 5px;" id="tax" >
	                      				
					                       <c:choose>
					                           <c:when test="${fn:length(customers) gt 0 }">
					                           	<%-- <option selected="selected"  value="${customers[0].taxId}">${customers[0].taxId} </option>	 --%>
						                          <c:forEach items="${customers}" var="customer">
						                          	<option value="${customer.taxId}" selected="selected">${customer.taxId}</option>
						                          </c:forEach>
					                        </c:when>
					                        <c:otherwise>
					                        	<option selected="selected" value="" >No Records </option>
					                        </c:otherwise>
					                       </c:choose>    
					                          	                         
	                        			</select>
	                    		</div>
	                 		 </li>
	                    </td>
	                    <td>
	                 		 <li class="" id="li_1">
	                   			 <label class="description" for="label">First Name  <span id="custname_error" class="error_msg">*</span></label>
	                    		<div>
	                      			<input id="custname" name="customer.firstName" class="element text large" maxlength="255" type="text" />
	                    		</div>
	                  		</li>
	                   </td>
	                    <td>
	                  		<li id="li_2">
	                    		<label class="description" for="label2">Last Name   <span id="custname2_error" class="error_msg">*</span></label>
	                    		<div>
	                      			<input id="custname2" name="customer.lastName" class="element text large" maxlength="255" type="text" />
	                    		</div>
	                  		</li>
	                  </td>
	                  <td>
	                  		<li id="li_2">
	                    		<label class="description" for="label2">Title  <span id="custtitle_error" class="error_msg">*</span></label>
	                    		<div>
	                      			<input id="custtitle" name="customer.title" class="element text medium" maxlength="255" type="text" />
	                   		 	</div>
	                  		</li>
	                  </td>
	                  
	             </tr>
	             
	          
	              
	         <tr>
	          		<td>
	                  		<li id="li_2">
	                    		<label class="description" for="label2">Email  <span id="custemail_error" class="error_msg"></span></label>
	                    		<div>
	                      			<input id="email" name="customer.email" class="element text medium" maxlength="255" type="text" style="text-transform: none;" />
	                   		 	</div>
	                  		</li>
	                  </td>
	                  <td>
	                  		<li id="li_2">
	                    		<label class="description" for="label2">Phone Number  <span id="custphone_error" class="error_msg">*</span></label>
	                    		<div>
	                      			<input id="custphone" name="customer.phoneNo" class="element text medium" maxlength="255" type="text" />
	                   		 	</div>
	                  		</li>
	                  </td>
	         	<td>
	                  	<li id="li_2">
	                   		 <label class="description" for="label6">Tax Exempt (Y/N)</label>
	                    	
	                      		<div>
	                        		<select class="element select medium" style="padding:4px 0;margin-top: 5px;" id="taxexempt" name="customer.taxExempt">
				                          <option value="na" selected="selected">N/A</option>
				                          <option value="yes">Yes</option>
				                          <option value="no" >No</option>				                         
	                        		</select>
	                      		</div>
	                    	
	                  </li>          
                  </td>
	             <td>
	                 <li id="li_2">
	                       <label class="description" for="label6">Fax  <span id="fax_error" class="error_msg"></span></label>
	                    	<div>
	                      		<input id="fax" name="customer.faxNo" class="element text medium" maxlength="255" type="text" />
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
		<ul>
			<table width="96%" cellspacing="0" cellpadding="0" style="width:1200px;">
			<tr>
				<td><h4 style="font-size:16px; text-decoration: underline;">Account #1</h3></td>
				
			</tr>
	         <tr>
	         	<td width="25%">
	           	  <ul>
	                    <li>
	                    	<label class="description" for="label14">Utility <span id="utiltiy0_error" class="error_msg">*</span></label>
	                    	<div>
	                    	  <spring:bind path="dealSheet.orders[0].utility.utility">
	                      		<select class="element select medium" style="padding:4px 0;margin-top: 5px;" id="utility0" name="${status.expression}">
			                       
			                        <c:forEach var="util" items="${utilities}">
			                        	 <option value="${util.utility}" selected="selected">${util.utility}</option>
			                        </c:forEach>			                       
	                      		</select>
	                      	 </spring:bind>
	                       </div>
	                   </li>
	              </ul> 
	         </td>
	         <td>
	    		<ul>
	            	<li id="li_2">
	                    <label class="description" for="label11">Account Number <span id="acc_error0" class="error_msg">*</span></label>
	                    <div>
	                        <spring:bind path="dealSheet.orders[0].accountNumber">
	                    	 <input id="account0" name="${status.expression}" class="element text medium" maxlength="255" type="text"  index="0" onblur="validateaccountsize(this.id)"/>
	                      </spring:bind>
	                    </div>
	                 </li>
	           </ul>
	       </td>
	       <td width="25%">
                <ul>
	           		<li>
	                    <label class="description" for="label14">Rate Class  <span id="rateclass_error0" class="error_msg"></span> </label>
	                    <div>
	                     <spring:bind path="dealSheet.orders[0].rateClass">
	                      	<select class="element select medium" style="padding:4px 0;margin-top: 5px;" id="rateclass0" name="${status.expression}" >
		                        
		                        <option value="gs1" selected="selected">GS1</option>
		                        <option value="gs2">GS2</option>
		                        <option value="gs3">GS3</option>
	                       </select>
	                     </spring:bind>
	                    </div>
	               </li>
	          </ul>
             </td>
             <td>
	              <li id="li_2">
	              	<label class="description" for="label8">Rate  <span id="rate_error0" class="error_msg">*</span></label>
	                    <div>
	                        <spring:bind path="dealSheet.orders[0].rate">
	                    	  	<input id="rate0" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                       </spring:bind>
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
	                       <spring:bind path="dealSheet.orders[0].term">
	                      	<select class="element select medium" style="padding:4px 0;margin-top: 5px;" id="term" name="${status.expression}" >
		                        
		                        <option value="12" selected="selected">12</option>
		                        <option value="18">18</option>
		                        <option value="24">24</option>
		                        <option value="30">30</option>
		                        <option value="36">36</option>
	                       </select>
	                     </spring:bind>
	                    </div>
	                </li>
	           </ul> 
	      <!--  </td>
	       <td> -->
	       	<ul class="smallul">
	            <li id="li_2">
	                    <label class="description" for="label12">KWH  <span id="kwh_error0" class="error_msg">*</span></label>
	                    <div>
	                       <spring:bind path="dealSheet.orders[0].kwh">
	                      	<input id="kwh0" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                       </spring:bind>
	                     </div>
	             </li>
	        </ul> 
	     <!--  </td>
	      <td> -->
	       	<ul>
	            <li id="li_2">
	                    <label class="description" for="label12">TPV Number  <span id="tpv_error0" class="error_msg">*</span></label>
	                    <div>
	                       <spring:bind path="dealSheet.orders[0].tpv">
	                      	<input id="tpv0" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                       </spring:bind>
	                     </div>
	             </li>
	        </ul> 
	     <!--  </td>
	        <td> -->
	       	  <ul class="bigul" style="margin-left: 12px;">
	          	   <li id="li_2">
	                    <label class="description" for="label9">Business Name  <span id="bname_error0" class="error_msg">*</span> </label>
	                     <div>
	                        <spring:bind path="dealSheet.orders[0].businessName">
	                     	 <input id="businessname0" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                       </spring:bind>
	                    </div>
	               </li>
	         </ul>
	    <!--   </td>
	      <td> -->
	      	<ul class="bigul" style="margin-left: -12px;">
	     		<li id="li_2" style="padding-left: 16px;">
	           		<label class="description" for="label10">DBA (If Applicable)</label>
	           		<div>
	           		  <spring:bind path="dealSheet.orders[0].dba">
	               		<input id="dba0" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	           		  </spring:bind>
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
	       <td  >
	    	   <li>
	           		<label class="description" for="label13">Street  <span id="s_street_error0" class="error_msg">*</span></label>
	                <div>
	                     <spring:bind path="dealSheet.orders[0].serviceStreet">
	                      <input id="s_street0" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                     </spring:bind>
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
	                  <spring:bind path="dealSheet.orders[0].serviceCity">
	                   <input id="s_city0" name="${status.expression}" class="element text medium" maxlength="255" type="text"  />
	                  </spring:bind>
	                </div>
	           </li>
	        </ul>
	     </td>
	     <td>
	      	<ul>
	           <li>
	                <label class="description" for="label13">State  <span id="s_state_error0" class="error_msg">*</span></label>
	                 <div>
	                  <spring:bind path="dealSheet.orders[0].serviceState">
	                     <select class="element select medium" style="padding:4px 0; margin-top: 5px;" id="s_state0" name="${status.expression}">
		                        
		                        <option value="al">AL</option>
		                        <option value="ak">AK</option>
		                        <option value="ar">AR</option>
		                        <option value="az">AZ</option>
		                         <option value="ca">CA</option>
		                        <option value="co">CO</option>
		                        <option value="ct">CT</option>
		                        <option value="de">DE</option>
		                        <option value="il" selected="selected">IL</option>
		                        
		                        <option value="nj">NJ</option>
		                        <option value="oh">OH</option>
		                        <option value="pa">PA</option>
	                      </select>
	                      </spring:bind>
	                  </div>
	            </li>
	       </ul>
	   </td>
	  		 <td>
	  	<ul>
	       <li>
	           <label class="description" for="label13">Zip  <span id="s_zip_error0" class="error_msg">*</span></label>
	           <div>
	               <spring:bind path="dealSheet.orders[0].serviceZip">
	                <input id="s_zip0" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	             </spring:bind>
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
	             <input type="checkbox" name="add_chk" value="checkbox"  id="add_chk0" style="margin-left: 20px;" index="0" onclick="copyadd(this.id);" />
			      <span style="font-family:Arial, Helvetica, sans-serif; font-size:10px;" > Same as Service Address</span>
	          </label></td>
	          <td>&nbsp;</td>
	          <td>&nbsp;</td>
      </tr>
	   <tr>
	       <td><ul>
	            <li>
	                <label class="description" for="label13">Street  <span id="b_street_error0" class="error_msg"></span></label>
	                <div>
	                  <spring:bind path="dealSheet.orders[0].billingStreet">
	                    <input id="b_street0" name="${status.expression }" class="element text medium" maxlength="255" type="text" />
	                  </spring:bind>
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
	                      <spring:bind path="dealSheet.orders[0].billingCity">
	                        <input id="b_city0" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                      </spring:bind>
	                    </div>
	                </li>
	           </ul></td>
	           <td><ul>
	                 <li>
	                     <label class="description" for="label13">State  <span id="b_state_error0" class="error_msg"></span></label>
	                      <div>
	                        <spring:bind path="dealSheet.orders[0].billingState">
		                        <select class="element select medium" style="padding:4px 0;margin-top: 5px;" id="b_state0" name="${status.expression}" >
		                         
		                          
			                        <option value="al">AL</option>
		                        <option value="ak">AK</option>
		                        <option value="ar">AR</option>
		                        <option value="az">AZ</option>
		                         <option value="ca">CA</option>
		                        <option value="co">CO</option>
		                        <option value="ct">CT</option>
		                        <option value="de">DE</option>
		                        <option value="il" selected="selected">IL</option>
		                        
		                        <option value="nj">NJ</option>
		                        <option value="oh">OH</option>
		                        <option value="pa">PA</option>
		                        </select>
		                       </spring:bind>
	                      </div>
	                  </li>
	            </ul></td>
	            <td valign="middle"><ul>
	                <li>
	                  <label class="description" for="label13">Zip  <span id="b_zip_error0" class="error_msg"></span></label>
	                   <div>
	                     <spring:bind path="dealSheet.orders[0].billingZip">
	                       <input id="b_zip0" name="${status.expression }" class="element text medium" maxlength="255" type="text" />
	                    </spring:bind>
	                   </div>
	                </li>
	            </ul></td>
	      </tr>
	             
	       <tr>	            
	            
	             <td>&nbsp;</td>
	        </tr>
	             
	     </table>
	  </ul>
				
		<div class="bottom_link">
			  <div class="login_button2" style="margin-left: 312px;">
			   		<a href="#"><button type="submit" class="submit1_button" id="submitBtn" ></button></a>					
			  </div>
			  <div class="login_button2">					
						<a href="#"><button type="button" class="addmore_button" id="addBtn" ></button></a>
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