<!DOCTYPE html>
<html>
<head>
	<title>Agent Deal Sheets</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	  <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
	$(document).ready(function() {
	    $("table tr:nth-child(even)").css('background-color', '#edeff0');
	    
	});
	
	
	function deleteDeal(id){
		var x=confirm("Are you sure you want to Delete?");					
		if(x==true ){	
			window.location='/hoveyenergy/admin/deletedealsheet.do?dealId='+id;
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
	<div id="feedback2" style="text-align: center;color:green;font-size: 20px;font-weight: bold;height: 16px;margin-top: 10px;">
	  		<c:if test="${not empty param.message }">
	  					Deal Edited Successfully
	  		</c:if>
	  		
	  		<c:if test="${not empty param.status }">
	  					Deal Deleted Successfully
	  		</c:if>
	  		
	 
	    </div>
		
	  
		
		<div class="title" style="padding-top: 15px;margin-bottom: -25px;">Saved Deal Sheets</div>
		<br />
		
		<div class="searchBox" >
		 				<form method="get" action="/hoveyenergy/admin/viewdealsheets.do" >
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
		
		<c:if test="${ empty message }">
				<div>
				<div class="pagecount" style="float: left;margin-left: 20px;">
														 Page <b>${page +1}</b> of <b>${end }</b>
												</div>
					<ul class="pager" >
					    <div style="float: right;text-align: right;">
					      <c:choose>
					      	<c:when test="${page eq 0 && end ne 1 }">
					      		<li>	 <a href="/hoveyenergy/admin/viewdealsheets.do?${query}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>
					      	</c:when>
					      
					        <c:when test="${page eq end-1 && end ne 1 }">
					                <a href="/hoveyenergy//admin/viewdealsheets.do?${query}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
					        </c:when>
					        
					        <c:when test="${page eq end-1 && end eq 1 }">
					               
					        </c:when>
					        
					        <c:otherwise>
					        	<li>	<a href="/hoveyenergy/admin/viewdealsheets.do?${query}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
								<li> <a href="/hoveyenergy/admin/viewdealsheets.do?${query}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>
					        
					        </c:otherwise>
					      </c:choose>
					    </div>
					    <div class="pagecount"> Showing ${first}- ${last} of ${total } Deals	</div>
					</ul>
				</div>
				<div class="CSSTableGenerator" >
		                <table >
		                    <tr>
		                    	<td><a href="/hoveyenergy/admin/viewdealsheets.do?${query2}&sortby=orderDate" >Order Date</a></td>
		                       <td><a href="/hoveyenergy/admin/viewdealsheets.do?${query2}&sortby=dealStartDate">Deal Start Date</a></td>
						        <td><a href="/hoveyenergy/admin/viewdealsheets.do?${query2}&sortby=businessName">Business Name</a></td>
						      
						        <td><a href="/hoveyenergy/admin/viewdealsheets.do?${query2}&sortby=agent">Agent Id</a></td>
						        <td><a href="/hoveyenergy/admin/viewdealsheets.do?${query2}&sortby=agentName">Agent Name</a></td>
						        <td><a href="/hoveyenergy/admin/viewdealsheets.do?${query2}&sortby=taxId.customerId">Tax Id</a></td>	
						        <td><a href="/hoveyenergy/admin/viewdealsheets.do?${query2}&sortby=order_id">No of Orders</a></td>
						         <td><a href="/hoveyenergy/admin/viewdealsheets.do?${query2}&sortby=kwh">Total kWh</a></td>			      
						    	<td>Edit</td>
						    	<td>Delete</td>
						    	<td>Print</td>
						    	
		                    </tr>
		                    <c:forEach var="order" items="${orders}">
					   		 <tr>
					   		 	  <td><fmt:formatDate pattern="MM/dd/yyyy" value="${order.orderDate}" /></td>
						         <td><fmt:formatDate pattern="MM/dd/yyyy" value="${order.dealStartDate}" /></td>
						         <td>${order.businessName}</td>
						          <td>${order.createdAgent.agentNumber }
						           <td>${order.createdAgent.firstName } ${order.createdAgent.lastName }</td>
						         <td>${order.taxId.taxId}</td>
						         <td>${order.totalAccounts }</td>
						         <td><fmt:formatNumber groupingUsed="true" value="${order.totalKwh}" /> </td>
						       <td><a href="/hoveyenergy/admin/editdealsheet.do?dealId=${order.transDto.transactionId }"><img src="/hoveyenergy/images/edit_deal.png" title="Edit Deal Sheet" class="icon"/></a></td>
						        
						        <td><a href="#"   id="${order.transDto.transactionId }" onClick="deleteDeal(this.id);"><img src="/hoveyenergy/images/delete.png" title="Edit Deal Sheet" class="icon"/></a></td> 
						          <td>
						         	<a href="#" onClick="window.open('/hoveyenergy/printdeal.do?dealId=${order.transDto.transactionId}','mywindow','width=1280px,height=800px,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,copyhistory=no,resizable=yes').print()">
												<img src="/hoveyenergy/images/print_deal.png" title="Print Deal Sheet" class="icon"/>
								   </a>
								 </td>								
					   		 </tr>
				   		 </c:forEach>
		                </table>
		            </div>
		            
		            
		            
		           <div style="text-align: center;width: 620px;margin: 40px auto;">									
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
		            
		            							<p style="clear:both;">
		            							</p>
		            						<br />
		            
		            
		            
		            
       </c:if>
		
	
	</div>
	
	<c:if test="${not empty param.error }">
		<script type="text/javascript">
			alert("Problem While Editing Deal Sheet, Account# should be Unique among Orders.. Please Try Again..");
		</script>
	</c:if>
   
 </body>
</html>