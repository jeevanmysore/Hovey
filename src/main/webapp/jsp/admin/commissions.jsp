<!DOCTYPE html>
<html>
<head>
	<title>Manage Commissions</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');
		    
		});
	</script>
	
	<style>
		a:ACTIVE,a:VISITED,a:FOCUS{
			color: blue;
		}	
		a:HOVER {
			color:purple;
		}
		
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
   
   <c:set var="queryString" value="${requestScope['javax.servlet.forward.query_string']}" />
	 <c:set var="delimeter" value="&page=${page }" />
	<c:set var="query" value="${fn:replace(queryString,delimeter,'')}" />
	<c:set var="delimeter2" value="&sortby=${sortby}" />
	<c:set var="query2" value="${fn:replace(queryString,'&','')}" />
	<c:set var="query2" value="${fn:replace(queryString,delimeter2,'')}" />
	<div id="content">
	
	 
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Manage Commissions</div>
		
		<div class="searchBox" >
		 				<form method="get" action="/hoveyenergy/admin/commissions.do" >
		 				<div style="display: inline-block;">
		 				  <div style="display: block;float: left;">
		 					<label>Business Name:</label>
		 					<input type="text" name="searchBy" class="searchText"/>
		 				</div>
		 				<div style="display: block;float: right;">
		 					<button type="submit" class="searchBtn" style="top:0px;" ></button>	
		 				  </div>	
		 				 </div>	 			
		 				</form>
		 					
			</div>
		
		
		
		 <div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;">
	  		${message}	 
	  	</div>
	 
		    <c:if test="${empty message }">
				 <div> 
				 	<div class="pagecount" style="float: left;margin-left: 20px;">
														 Page <b>${page +1}</b> of <b>${end }</b>
												</div>
					  <ul class="pager" >
					   <div style="float: right;text-align: right;">
					      <c:choose>
					      	<c:when test="${page eq 0 && end ne 1 }">
					      		<li>	 <a href="/hoveyenergy/admin/commissions.do?${query}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>
					      	</c:when>
					      
					        <c:when test="${page eq end-1 && end ne 1 }">
					                <a href="/hoveyenergy/admin/commissions.do?${query}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
					        </c:when>
					        
					        <c:when test="${page eq end-1 && end eq 1 }">
					               
					        </c:when>
					        
					        <c:otherwise>
					        	<li>	<a href="/hoveyenergy/admin/commissions.do?${query}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
								<li> <a href="/hoveyenergy/admin/commissions.do?${query}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>
					        
					        </c:otherwise>	
					      </c:choose>
					    </div>
					      <div class="pagecount"> Showing ${first}- ${last} of ${total } Customer Commmissions</div>
					</ul>
				
				</div>
				<div class="CSSTableGenerator" >
		                <table>
		                    <tr>
		                        <td><a href="/hoveyenergy/admin/commissions.do?${query2}&sortby=businessName">Business Name</a></td>                		                   		
		                   		<td><a href="/hoveyenergy/admin/commissions.do?${query2}&sortby=commission">Total Upfront Commission Expected</a></td>
		                   		<td><a href="/hoveyenergy/admin/commissions.do?${query2}&sortby=commission">Total Annual Commission Expected</a></td>
		                   		<td><a href="/hoveyenergy/admin/commissions.do?${query2}&sortby=upfront_commission">Total Annual Commission Received</a></td>
		                   		<td><a href="/hoveyenergy/admin/commissions.do?${query2}&sortby=upfront_commission">Net Commission<br />
		                   			<span style="font-size: 10px;font-weight: normal;">
		                   				(Recvd Comm - Annual Comm)
		                   			</span>
		                   		</a></td>
		                   		<td><a href="/hoveyenergy/admin/commissions.do?${query2}&sortby=order_id">Total Accounts</a></td>	
		                   		<td>Reconciliation	    </td>               		
		                   		<!-- <td>Edit</td> -->
		                    </tr>
		                    <c:forEach var="order" items="${orders}">
		                     <c:set  var="net" value="${ order.taxId.totalCommissionReceived - order.taxId.totalTermCommission}"/>
					   		 <tr>
					   		 		<td>${order.businessName}</td>
					   		 		
					   		 		
					   		 			
					   		 		<td>   		 			
					   		 				$<fmt:formatNumber  maxFractionDigits="2" minFractionDigits="2" groupingUsed="true" value=" ${order.taxId.totalExpectedCommission }" />  
					   		 		</td>
					   		 			<td>   		 			
					   		 				$<fmt:formatNumber  maxFractionDigits="2" minFractionDigits="2" groupingUsed="true" value=" ${order.taxId.totalTermCommission }" />  
					   		 		</td>
					   		 		<td>   		 			
					   		 				$<fmt:formatNumber  maxFractionDigits="2" minFractionDigits="2" groupingUsed="true" value="${order.taxId.totalCommissionReceived }" />  
					   		 		</td>
					   		 		
					   		 		<td>
					   		 			<c:choose>
					   		 				<c:when test="${net gt 0.01 }">
					   		 					$ +<fmt:formatNumber  maxFractionDigits="2" minFractionDigits="2" groupingUsed="true" value="${net }" />
					   		 				</c:when>
					   		 				<c:when test="${net eq 0 }">
					   		 					$ <fmt:formatNumber  maxFractionDigits="2" minFractionDigits="2" groupingUsed="true" value="${net }" />
					   		 				</c:when>
					   		 				<c:otherwise>
					   		 					$ <fmt:formatNumber  maxFractionDigits="2" minFractionDigits="2" groupingUsed="true" value="${net }" />
					   		 				</c:otherwise>
					   		 			</c:choose>	
					   		 		
					   		 		</td>
					   		 		
					   		 		<td>${order.taxId.totalAccounts }</td>
					   		 		<td><a href="/hoveyenergy/admin/reconcile.do?customerId=${order.taxId.customerId}" class="cust">Reconcile</a></td>
					   		 		
						        </tr>
				   		 </c:forEach>
		                </table>
		            </div>
		            
		            
		            
		            <div style="text-align: center;width: 620px;margin: 40px auto;margin-bottom: 10px;">									
													<ul id="pagin">
													 <!--  For Previous -->	
													 
													 	
														<c:if test="${page ne 0 }">
														<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=0">First</a></li>
															<li>  <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&page=${page-1}">Prev</a></li>
														</c:if>
													
														<c:choose>
																<c:when test="${end eq 1 }"></c:when>
																<c:when test="${end lt  10 }">
																	<c:forEach var="i" begin="0" end="${end-1 }">
																		<c:choose>
																		
																			<c:when test="${page eq end-1 }"></c:when>
																		
																		
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
		            
		            
		            	<p style="clear: both;">
		            		<br />
		            	</p>
		            
		            	<div>
		            		<a href="#" class="btn btn-update" style=" width: 180px;"   onclick="window.location.href='/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query2}&output=excel'">Export to Excel</a>
		            	</div>
		            
		            
       			</c:if>
		
	
	</div>
   
 </body>
</html>