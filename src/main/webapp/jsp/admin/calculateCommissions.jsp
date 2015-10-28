<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>


 	<c:set var="queryString" value="${requestScope['javax.servlet.forward.query_string']}" />
	 <c:set var="delimeter" value="&filter=${filter }" />
	<c:set var="query" value="${fn:replace(queryString,delimeter,'')}" />

<html>
<head>
	<title>Agent Commission</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	  <script type="text/javascript" src="/hoveyenergy/js/reconcile.js"></script>
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');
		    
		});
		
		/* 
			Added by Jeevan on December 09, 2013 as per clients request
			method to include filter based upon the checked value
		*/
		function filterCommissions(){
			if($('#filter').is(':checked')){
				window.location="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&filter=true";
			}
			else{
				window.location="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&filter=false";
			}
			
		}
	</script>
	
	<style>
		.result{
			margin-left: 20px;
			font-size: 17px;
		}	
		.rec_input{
			height: 25px !important;
			padding: 1px !important;
		}
		.CSSTableGenerator td {
			padding-left: 1px !important;
			padding-right: 1px !important;
		}
		
		
		input[type="text"]{
			width:100px !important;
		}
		
		
		
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  
	 
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Agent Commissions</div>
		
		
		<div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  		${message}	 
	  	</div>
	  	<c:if test="${empty message}">	 
	  	
	  			<input type="hidden" id="week" value="${orders[0].week }" />
	  			<input type="hidden" id="year" value="${orders[0].year }" />
	  			
	  			<div style="float:right;width: 250px;padding: 3px;border: 1px solid;font-weight: bold;background: #EDF0FC;">
	  					<input type="checkbox" id="filter" name="filter" onclick="filterCommissions();" ${filter eq true ? 'checked' : '' }/>
	  					Display only Week's Commission
	  			</div>
	  			
	  			
	  			<div class="title" style="color: green;text-decoration: none;">Week : ${orders[0].week} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Year:${orders[0].year }</div> 	
				<br />
				
				<div class="CSSTableGenerator" id="comTable">
		                <table>
		                    <tr>
		                      <td>S.No</td> 	
		                       <td>Agent Name</td>	                      
		                     <td>Sent to Supplier</td>
		                     <td> Account#</td>
		                      <td style="width:180px;">Business Name</td>
		                      
		                      <td>kWh</td>
		                      <td style="width:115px;">Commission Received</td>
		                      	<td style="width:80px;">Upfront Paid Date</td>
		                      	<td style="width:60px;">Contract Type</td>
		                      	<td>Status</td>
		                      <td>Commission Payable?</td>
		                      <td>Commission Rate</td>
		                   	<td>Commission Payable</td>
		                   
		                   	<td>Supplier</td>	
		                   		
		                    </tr>
		                    <c:set var="i" value="0"></c:set>
		                   
		                   
		                   
		                    <c:forEach var="order" items="${orders}">
		                      <c:if test="${order.agentCommissionStatus eq 'unpaid' }">
						   		 <tr>
						   		 		<input type="hidden"  id="id${order.id}" value="0" />
						   		 		<input type="hidden" id="orderId${order.id}" value="${order.id }" />
						   		 		
						   		 		
						   		 		<td> ${i+1 }</td>
						   		 		<td>${order.createdAgent.firstName} ${order.createdAgent.lastName }</td>
						   		 		<td> <fmt:formatDate pattern="MM/dd/yyyy" value="${order.sentToSupplier }"  /></td>
						   		 		<td>${order.accountNumber }</td>
						   		 		<td>${order.businessName}</td> 		 		
						   		 		<td><fmt:formatNumber groupingUsed="true" value="${order.kwh}" /><input type="hidden" id="kwh${order.id}" value="${order.kwh }" /> </td>
						   		 			<td> $<fmt:formatNumber groupingUsed="true" value="${order.upfrontCommission }" maxFractionDigits="2" minFractionDigits="2" /> </td>
						   		 		<td> <fmt:formatDate pattern="MM/dd/yyyy" value="${order.upfrontPaidDate }"  /></td>
						   		 		<td style="text-transform: capitalize;">${order.contractType }</td>
						   		 		<td style="text-transform: capitalize;">${order.status }</td>
						   		 		<td>
						   		 			<select name="comPayable" id="comPayable${order.id}" index="${order.id }"  style="width: 100px;padding: 2px;" onchange="changeComPayable(this.id)" >
						   		 				<option value="">--Select--</option>
						   		 				<option value="yes">Yes</option>
						   		 				<option value="no">No</option>
						   		 			
						   		 			</select>
						   		 		</td>
						   		 		 
						   		 		<td> <input class="rec_input" type="text" id="comRate${order.id }" name="commissionRate"   value='<fmt:formatNumber value="${order.agentCommissionRate}"  maxFractionDigits="10" />'  index="${order.id }" onchange="changeAgentCommission(this.id)" onblur="saveAgentCommission(this.id)" /> </td>
						   		 		<td>$ <input class="rec_input com${i}" type="text" id="commission${order.id }" name="commission"  readonly="readonly" value='' index="${order.id }"  onblur="saveAgentCommission(this.id)"/></td>
						   		 		
						   		 		
						   		 		<td > ${order.supplierName.supplierName}</td>						   		 	
							        </tr>
							          <c:set var="i" value="${i+1 }"></c:set>  
							     </c:if>
							   
				   		 </c:forEach>				   		 		  
		                </table>
		                <input type="hidden" id="calcom" value="${i}" />
		            </div>
            
            
            
            	<c:if test="${not empty commissions }">
            		<div class="CSSTableGenerator" style="clear: both;margin-top:50px;" id="resTable" >
            			<table>
            				<tr>
            					<td colspan="13">Rescinded Orders</td>
            				</tr>	
            				 <c:set var="j" value="0"></c:set>
            				<c:forEach var="commission" items="${commissions }">            				
		            					<tr id="res${commission.order.id}">
		            						<input type="hidden"  id="id${commission.order.id}" value="${commission.id}" />
								   		 		<input type="hidden" id="orderId${commission.order.id}" value="${commission.order.id }" />								   		 		
								   		 		<td>${j+1 }</td>
								   		 		<td>${commission.order.createdAgent.firstName } ${commission.order.createdAgent.lastName }</td>
		            						<td> <fmt:formatDate pattern="MM/dd/yyyy" value="${commission.order.sentToSupplier }"  /></td>
		            						
		            						<td>${commission.order.accountNumber }</td>
		            						<td>${commission.order.businessName }</td>
		            						
		            						<td><fmt:formatNumber groupingUsed="true" value="${commission.order.kwh}" /><input type="hidden" id="kwh${commission.order.id}" value="${commission.order.kwh }" /></td>            						
		            						<td> $ <fmt:formatNumber groupingUsed="true" value="${commission.order.upfrontCommission}"  maxFractionDigits="2" minFractionDigits="2"/></td>
		            						<td> <fmt:formatDate pattern="MM/dd/yyyy" value="${commission.order.upfrontPaidDate }"  /></td>
		            						<td style="text-transform: capitalize;">${commission.order.status }</td>
		            						<td>
		            							<select name="comPayable" id="comPayable1${commission.order.id}" index="${commission.order.id }"  style="width: 100px;padding: 2px;" >
						   		 				<option value="">--Select--</option>
						   		 				<option value="yes">Yes</option>
						   		 				<option value="no">No</option>						   		 			
						   		 			</select>
		            						</td>		            						
		            						<td>
		            							<input class="rec_input" type="text" id="comRate${commission.order.id }" name="commissionRate" readonly="readonly"   value="<fmt:formatNumber value='${commission.commissionRate }' maxFractionDigits="5" minFractionDigits="3"   />" index="${commission.order.id }"  onchange="changeAgentCommission2(this.id)" /> 
		            						</td>
		            						<td>$ <input class="rec_input rcom${j}" type="text" id="commission${commission.order.id }" name="commission"  readonly="readonly" value="<fmt:formatNumber value='${commission.agentCommission }' maxFractionDigits="5" minFractionDigits="3"   />" index="${commission.order.id }"  /></td>
		            						<%--  <td>${commission.order.supplierName.supplierName } </td> --%>
		            						<td><a href="#" id="link${commission.order.id}" index="${commission.order.id}" class="btn btn-update" onclick="saveAgentCommission2(this.id);">Claim</a></td>
		            					</tr>
            							<c:set  var="j" value="${j+1 }" ></c:set>
            				</c:forEach>            						
            			</table>
            				<input type="hidden" id="rescom" value="${j}" />
            		</div>
            		
            	</c:if>	
            
       
       
       
       			 <div class="summary" style="float: left;margin-top: 30px;text-align: right;margin-right: 30px; ">
       				<div style="clear: both;">	
				 		<label style="text-align: left;font-weight: bold;font-size: 16px; ">	Total Week's kWh : </label>
				 		<input class="result" type="text" readonly="readonly" value= '<fmt:formatNumber currencyCode="US" groupingUsed="true" maxFractionDigits="0" value="${orders[0].weekskWh}" />'style="float: right;margin-top: -5px;"/>   <br />            				
            		</div>
					<c:forEach items="${orders[0].agentCommissionsMap }" var="commission">
						<p style="clear: both;">
					     <div style="clear: both;margin-top: 15px;">
					 		<label style="text-align: left;font-weight: bold;font-size: 16px;">Total ${commission.key }'s Commission :</label>
					 		<input class="result"  type="text" readonly="readonly" value='<fmt:formatNumber currencyCode="US" groupingUsed="true" maxFractionDigits="0" value="${commission.value}" />' style="float: right;margin-top: -5px;" />   <br /> 
					 	</div>				
					</c:forEach>	       				
       			</div>
       
       
				 <div class="summary" style="float: right;margin-top: 30px;text-align: right;margin-right: 30px; ">
				 	<div style="clear: both;">	
				 		<label style="text-align: left;font-weight: bold;font-size: 16px; ">	Total kWh : </label>
				 		<input class="result" type="text" readonly="readonly" value=' <fmt:formatNumber currencyCode="US" groupingUsed="true" maxFractionDigits="0" value="${orders[0].totalKwh}" />'  style="float: right;margin-top: -5px;"/>   <br />            				
            					
				 	</div>
				 	<p style="clear: both;">
				     <div style="clear: both;margin-top: 15px;">
				 		<label style="text-align: left;font-weight: bold;font-size: 16px;">Total Agent's Commission :</label>
				 		<input class="result" id="totalAgentCommission" type="text" readonly="readonly" value="0" style="float: right;margin-top: -5px;" />   <br /> 
				 	</div>
				 		<p style="clear: both;">
				 	<div style="clear: both;margin-top: 15px;">
				 		<label style="text-align: left;font-weight: bold;font-size: 16px;">Total Rescinded Orders Commission :</label>
				 		<input class="result" id="totalResCommission" type="text" readonly="readonly" value="0" style="float: right;margin-top: -5px;"/>   <br />
				 	</div>
				 		<p style="clear: both;">
				 	<div style="clear: both;margin-top: 15px;">	
				 		<label style="text-align: left;font-weight: bold;font-size: 16px; ">Total Payable  Commission:</label>
				 		<input class="result" id="totalCommission" type="text"  readonly="readonly" value="0" style="float: right;margin-top: -5px;"/>   <br />            				
            					
				 	</div>
				 	
				 
				 	
				 </div>
			</c:if>
		
		<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
			<a href="#" class="btn btn-success" style=" width: 180px;"   onclick="window.location.href='/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${queryString}&output=excel'">Export ALL Commissions to Excel</a>							
			<a href="/hoveyenergy/admin/exportcommissions.do?week=${orders[0].week}&year=${orders[0].year}"  class="btn btn-update"  style="margin-left: 10px;">Export PAID Commissions to Excel</a>						
			<a href="/hoveyenergy/admin/agentcommissions.do"  class="btn btn-back"  style="margin-left: 10px;">Back</a>
		</div>
	
	
	</div>
   
 </body>
</html>