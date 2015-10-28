<!DOCTYPE html>
<html>
<head>
	<title>Renewal Orders</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script>
	 $(document).ready(function() {
	    $("table tr:nth-child(even)").css('background-color', '#edeff0');
	      $("#fromDate").datepicker({
	    	  	changeMonth: true,
		        changeYear: true,
			   	showButtonPanel: true,	
			   	closeText: 'Clear',
		        dateFormat: 'mm/dd/yy',
		        yearRange: '2012:c+10',
				/* showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true, */
				 onSelect: function(selected) {
				        $("#toDate").datepicker("option","minDate", selected);
		        }
		});
	    
	    $("#toDate").datepicker({
	    	changeMonth: true,
	        changeYear: true,
		   	showButtonPanel: true,	
		   	closeText: 'Clear',
	        dateFormat: 'mm/dd/yy',
	        yearRange: '2012:c+10',
			/* showOn: "button",
			buttonImage: "/hoveyenergy/images/calendar.gif",
			buttonImageOnly: true, */
			 onSelect: function(selected) {
		          $("#fromDate").datepicker("option","maxDate", selected);
		        }
		}); 
	    
	    
	    $('#fromDate').val($('#fromDate1').val());
	    $('#toDate').val($('#toDate1').val());
	
	 });
	</script>
	
	<style>
	/* 	.renewals{
			display: table;
		}
		
		.renewals label,.renewals select{
			display: table-cell;
			vertical-align: middle;
		} */
		a:ACTIVE,a:VISITED,a:FOCUS{
			color: blue;
		}	
		a:HOVER {
			color:purple;
		}
		
		
		#renBtn{
			height: 30px;
			width: 130px;
			border-radius: 5px;
			padding-top: 30px;
			background-image: url(/hoveyenergy/images/get_orders.png);
			position: relative;
			behavior: url(js/js/PIE.htc);
			/* top: 10px; */
			/* margin-left: 25px; */
			background-repeat: no-repeat;
			margin-bottom: 9px;
		}
	
	</style>
</head>



<body class="firefox">
   <%@ include file="header.jsp" %>
	<div id="content">
				<fmt:formatDate value='${startDate}' pattern='MM/dd/yyyy' var="formattedStartDate"/>
				<fmt:formatDate value='${endDate}' pattern='MM/dd/yyyy' var="formattedEndDate"/>
				
				<input type="hidden" id="fromDate1" class="fromDate" name="startDate" value='<fmt:formatDate value="${startDate}" pattern="MM/dd/yyyy"/>' /> 
	       		<input type="hidden"  id="toDate1" class="toDate" name="endDate" value='<fmt:formatDate value="${endDate}" pattern="MM/dd/yyyy"/>' />
	 			<c:set var="startDate" value="${formattedStartDate }"/>
	 			<c:set var="endDate" value="${formattedEndDate }"/>
		
		<div class="title" style="padding-top: 15px;margin-bottom: 15px;">Renewal Orders</div>
		
		<div class="renewals" style="text-align: center;width: 95%;margin: 0px auto; background-image: url(/hoveyenergy/images/box-pattern.png);"
	 class="bg_purple1">
	       <form action="/hoveyenergy/admin/renewals.do" method="get">	       		
	       		
				<label style="float:left;padding-top: 10px;margin-left:15px; font-size: 14px;font-family: arial;font-weight: bold;margin-top: 4px;">Start Date:</label>	
					<input type="text" id="fromDate" class="fromDate" name="startDate"  style="border:1px solid #ccc;  margin-top: 10px;  height:15px; width:85px;margin-bottom: 10px;top: -1px;margin-left: 03px;left: 1px;" />
					 
				<label style="float: left;padding-top: 10px; margin-left:10px;font-size: 14px;font-family: arial;font-weight: bold;margin-top: 4px;">End Date:</label>	
					<input type="text" id="toDate" class="toDate" name="endDate"  style="border:1px solid #ccc; margin-top: 10px;  height:15px; width:85px;margin-bottom: 10px; top: -1px;margin-left:03px;left: 1px;" />	
				
				
			 <!-- added Status by bhagya on may 06th,2014 -->
			<label style="font-size: 14px;font-family: arial;font-weight: bold; margin-left:0px;">Status:</label>
				 <select name="status"style="border:1px solid #ccc; margin-top: 10px;  height: 29px; width:110px;margin-bottom: 10px; top:-1px;" >
							  <option value="">- Select -</option>
							  <option value="agent" ${status eq 'agent' ? 'selected' : ''}>Agent</option>
							  <option value="approved" ${status eq 'approved' ? 'selected' : ''}>Approved</option>
							  <option value="under review" ${status eq 'under review' ? 'selected' : '' }>Under Review</option>
				</select>
			 
				<label style="font-size: 14px;font-family: arial;font-weight: bold; margin-left:02px;">Supplier:</label>
				<select name="supplier" style="border:1px solid #ccc;   height: 29px; width: 130px; margin-top: 10px;margin-bottom: 10px; top:-1px;">
											 <option value="">- Select -</option>
											<c:forEach var="sup" items="${suppliers}">
												<option value="${sup.supplierName}" ${supplier eq sup.supplierName ? 'selected' : '' }>${sup.supplierName}</option>
											</c:forEach>
				</select>
				<!--  added contract type by bhagya on may 09th,2014 -->
					<label style="font-size: 14px;font-family: arial;font-weight: bold; margin-left:02px;">Contract Type:</label>
					<select name="contractType" id="contractType"  data_index="${order.id}" style="text-transform: capitalize;margin: 0px; border:1px solid #ccc;top:-1px;   height: 29px; width: 90px; margin-top: 10px;margin-bottom: 10px;"class="pipeline">
                          	<option value="">- select -</option>
                         	<c:forEach var="contractTypes" items="${contractTypes}">
			   		 		<option value="${contractTypes.contractType }" ${contractType eq contractTypes.contractType ? 'selected' : ''  } > ${contractTypes.contractType}</option>
                          	</c:forEach>
                    </select>
                    
                    <!--  added service type by bhagya on october 27th,2014  based on client requirement-->
                    <label style="font-size: 14px;font-family: arial;font-weight: bold; margin-left:0px;">Service Type:</label>
				 <select name="serviceType"style="border:1px solid #ccc; margin-top: 10px;  height: 29px; width:110px;margin-bottom: 10px; top:-1px;" >
							  <option value="">- Select -</option>
							  <option value="commercial" ${serviceType eq 'commercial' ? 'selected' : ''}>Commercial</option>
							  <option value="residential" ${serviceType eq 'residential' ? 'selected' : ''}>Residential</option>
							  
				</select>
				<button type="submit" id="renBtn"></button>
			</form>
		</div>
		<br />
		 <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;height: 16px">
	  			${message}
	  
	     </div>
	     
	     <c:if test="${ empty message }">
		
			<div class="pagecount" style="float: left;margin-left: 20px;">
														 Page <b>${page +1}</b> of <b>${end }</b>
												</div>
			<ul class="pager" >
			<!--   modified by bhagya on May 2nd,2014 -->
			 <div style="float: right;text-align: right;">
			      <c:choose>
			      	<c:when test="${page eq 0 && end ne 1 }">
			      		<li>	 <a href="/hoveyenergy/admin/renewals.do?startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>
			      	</c:when>
			      
			        <c:when test="${page eq end-1 && end ne 1 }">
			              <li>  <a href="/hoveyenergy/admin/renewals.do?startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
			        </c:when>
			        
			        <c:when test="${page eq end-1 && end eq 1 }">
			               
			        </c:when>
			        
			        <c:otherwise>
			        	<li>	<a href="/hoveyenergy/admin/renewals.do?startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
						<li> <a href="/hoveyenergy/admin/renewals.do?startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>
			        </c:otherwise>
			      </c:choose>
			    </div>  
			    <!-- end of the modification -->
			       <div class="pagecount"> Showing ${first}- ${last} of ${total } Renewals</div>
			</ul>
		
			<div class="CSSTableGenerator" >
	                <table >
	                    <tr>
	                    	<td>Order Date</td>
	                       <td>Deal Start Date</td>
	                        <td>Deal End Date</td>
					        <td>Account #</td>
					         <td>Customer Name </td>
					          <td>Supplier Name</td>
					         <td>Term</td>
					         <td>Status</td>
					        <td>Agent Name</td>
					      	<td>Print</td>      
					    	
					    	
	                    </tr>
	                    <c:forEach var="order" items="${renewals}">
				   		 <tr>
				   		 	  <td><fmt:formatDate pattern="MM/dd/yyyy" value="${order.orderDate}" /></td>
					         <td><fmt:formatDate pattern="MM/dd/yyyy" value="${order.dealStartDate}" /></td>
					             <td><fmt:formatDate pattern="MM/dd/yyyy" value="${order.dealEndDate}" /></td>
					         <td> ${order.accountNumber}</td>
					          <td> ${order.businessName}</td>
					           <td> ${order.supplierName.supplierName}</td>
					            <td> ${order.term}</td>		            
					         	<td>${order.status}</td>
					           <td>${order.createdAgent.firstName } ${order.createdAgent.lastName }</td>
					           
					           <td><a href="#"  onclick="window.open('/hoveyenergy/printrenewaldeal.do?dealId=${order.transDto.transactionId}', 'printOrder', 'width=1280px,height=800px,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,copyhistory=no,resizable=yes');"> 
					           		<img src="/hoveyenergy/images/print_deal.png" title="Print Deal Sheet" class="icon">
					           </a></td>
					         
					       <%-- <td><a href="./editdealsheet.do?dealId=${order.transDto.transactionId }"><img src="images/edit_deal.png" title="Edit Deal Sheet" class="icon"/></a></td>
					         <td><a href="#" onClick="window.open('./printdeal.do?dealId=${order.transDto.transactionId}','mywindow','width=1280px,height=800px,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,copyhistory=no,resizable=yes').print()">
											<img src="images/print_deal.png" title="Print Deal Sheet" class="icon"/>
							</a></td> --%>
				   		 </tr>
			   		 </c:forEach>
	                </table>
	            </div>
	            
	            
	            <div style="text-align: center;width: 620px;margin: 40px auto;">									
													<ul id="pagin">
													 <!--  For Previous -->	
													 
													 	
														<c:if test="${page ne 0 }">
														<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=0">First</a></li>
															<li>  <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${page-1}">Prev</a></li>
														</c:if>
													
														<c:choose>
														
																<c:when test="${end eq 1 }"></c:when>
																<c:when test="${end lt 10 }">
																	<c:forEach var="i" begin="0" end="${end-1 }">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>																
																</c:when>
																
																<c:when test="${ page  lt 5}">
																	<c:forEach var="i" begin="0" end="9">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>																
																</c:when>
																
																
																<c:when test="${page ge 5 && page+5 le end }">
																		<c:forEach var="i" begin="${page-5 }" end="${page+4 }">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>												
																</c:when>
																
																<c:otherwise>
																	<c:forEach var="i" begin="${end-10 }" end="${end-1 }">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>															
																</c:otherwise>								
														</c:choose>									
													 <!-- For Next -->
													  
													   <c:if test="${page lt end-1 }">
															<li>  <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${page+1}">Next</a></li>
														</c:if>
														<c:if test="${page ne end-1 }">
													  		<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?${query}&startDate=${startDate}&endDate=${endDate}&supplier=${supplier}&status=${status}&contractType=${contractType}&serviceType=${serviceType}&page=${end-1}">Last</a></li>
														</c:if>
													</ul>					
												</div>
		            
       
		</c:if>
		<br><br>
	
	</div>
   
 </body>
</html>