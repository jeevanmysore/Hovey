<!DOCTYPE html>
<html>
<head>
	<title>Agent Management</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 
	 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0');
		    
		    
		    $('#agent').change(function(){
		    	var selected = $(this).find('option:selected').val();  
		    	window.location="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter="+selected;
		    });
		    
		    
		    
		});
	</script>
	
	<style>
		.link{
				font-weight: bold;
				color: blue !important ;
				text-decoration: underline !important;
		}
		
		.link:HOVER {
				font-weight: bold;
				color: blue;
				text-decoration: underline !important;
		}
		
		.link:ACTIVE {
				font-weight: bold;
				color: blue;
				text-decoration: underline !important;
		}
	
	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  <div id="feedback2" style="text-align: center;display:left:0px;height:0px;color:green;font-size: 20px;font-weight: bold;">
	  	<c:if test="${not empty param.message }" >
	  		Agent Updated Successfully
	  	</c:if>
	  			${status}	  ${messageStatus } 
	  </div>
	  <br /><br />
		
		<div class="title" style="padding-top: 10px;margin-left: 165px;">Manage Agents
					<div style="float:right;display: inline-block;">
					  <div style="float: left;padding-top: 3px;">
						<label style="display: inline;font-weight: normal;font-size: 14px;">Select Agents :</label>
						</div>
						<select name="agentselect" id="agent" style="border: 1px solid #ccc;margin-left: 33px;height: 27px;width: 152px;border-radius: 3px;display: inline-block;padding: 3px;">
								<option value="all"  ${filter eq 'all' ? 'selected' : '' }>All</option>
								<option value="enabled"  ${filter eq 'enabled' ? 'selected' : '' }>Enabled</option>
								<option value="disabled"  ${filter eq 'disabled' ? 'selected' : '' }>Disabled</option>						
						</select>
					 </div>
		</div>
		<br />
		<c:if test="${empty status }">
		
				<div> 
				<div class="pagecount" style="float: left;margin-left: 20px;text-decoration: underline;">
														 Page <b>${page +1}</b> of <b>${end }</b>
												</div>
					  <ul class="pager" >
					   <div style="float: right;text-align: right;">
					      <c:choose>
					      	<c:when test="${page eq 0 && end ne 1 }">
					      		<li>	 <a href="/hoveyenergy/admin/agents.do?filter=${filter}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>
					      	</c:when>
					      
					        <c:when test="${page eq end-1 && end ne 1 }">
					                <a href="/hoveyenergy/admin/agents.do?filter=${filter}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
					        </c:when>
					        
					        <c:when test="${page eq end-1 && end eq 1 }">
					               
					        </c:when>
					        
					        <c:otherwise>
					        	<li>	<a href="/hoveyenergy/admin/agents.do?filter=${filter}&page=${page-1}"><img src="/hoveyenergy/images/prev.png" /></a></li>
								<li> <a href="/hoveyenergy/admin/agents.do?filter=${filter}&page=${page+1}"><img src="/hoveyenergy/images/next.png" /></a></li>
					        
					        </c:otherwise>
					      </c:choose>
					    </div>
					      <div class="pagecount" style="margin-right: 25px;float: right;padding-top: 3px;font-weight: bold;text-decoration: underline;"> Showing ${first}- ${last} of ${total } Agents</div>
					</ul>
				
				</div>
		
		
		
				<div class="CSSTableGenerator" >
		                <table >
		                    <tr>
		                        <td>Username</td>
		                    	<td>Agent Id</td>
		                       <td>Agent Name</td> 	                      
		                   		<td>Status</td>
		                   		<td>Enable/Disable</td>
		                   		<td> Edit Agent</td>
		                   		<td>Reset Password</td>
		                   		<!-- <td>Edit</td> -->
		                    </tr>
		                    <c:forEach var="agent" items="${agents}">
					   		 <tr>
					   		 		<td><%-- <a href="/hoveyenergy/admin/changeusername.do?agentId=${agent.agentNumber }" class="username" title="Click here to Change Username"> --%>${agent.username } <!-- </a> --></td>
					   		 		<td>${agent.agentNumber }</td>
					   		 		<td>${agent.firstName } ${agent.lastName }</td>
					   		 		<c:choose>
					   		 			<c:when test="${agent.enabled eq true }">
					   		 			   <td>Enabled</td>
					   		 			   <td><a href="enableordisableagent.do?agent=${agent.agentNumber}&status=disable" class="link">Disable</a>
					   		 			</c:when>
					   		 			<c:otherwise>
					   		 				<td>Disabled</td>
					   		 				<td><a href="enableordisableagent.do?agent=${agent.agentNumber}&status=enable" class="link">Enable</a>
					   		 			</c:otherwise>
					   		 		</c:choose>
					   		 		<td>
					   		 			<a href="/hoveyenergy/admin/editagent.do?agentId=${agent.agentNumber}" class="link">
					   		 				<img src="/hoveyenergy/images/edit_agent.png"  title="Edit Agent" class="icon"/>
					   		 			</a>
					   		 		</td>
					   		 		 <td><a href="/hoveyenergy/admin/resetagentpassword.do?agent=${agent.agentNumber}" class="link">Reset Password</a></td>
					   		 		
					   		 		<%-- <td><a href="editagent.do?no=${agent.agentNumber}">Edit</a></td> --%>
						        </tr>
				   		 </c:forEach>
		                </table>
		            </div>
		            
		            
		            
		            
		           <div style="text-align: center;width: 450px;margin: 40px auto;margin-bottom: 10px;">									
													<ul id="pagin">
													 <!--  For Previous -->	
													 
													 	
														<c:if test="${page ne 0 }">
														<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter}&page=0">First</a></li>
															<li>  <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${page-1}">Prev</a></li>
														</c:if>
													
														<c:choose>
														<c:when test="${end eq 1 }"></c:when>
																<c:when test="${end lt 10 }">
																	<c:forEach var="i" begin="0" end="${end-1 }">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>																
																</c:when>
																
																<c:when test="${ page  lt 5}">
																	<c:forEach var="i" begin="0" end="9">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>																
																</c:when>
																
																
																<c:when test="${page ge 5 && page+5 le end }">
																		<c:forEach var="i" begin="${page-5 }" end="${page+4 }">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>												
																</c:when>
																
																<c:otherwise>
																	<c:forEach var="i" begin="${end-10 }" end="${end-1 }">
																		<c:choose>
																			<c:when test="${page eq i }">
																				<li><a class="current" href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${i}">${i+1 }</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${i}">${i+1 }</a></li>
																			</c:otherwise>
																		</c:choose>																	
																	</c:forEach>															
																</c:otherwise>								
														</c:choose>									
													 <!-- For Next -->
													  
													   <c:if test="${page lt end-1 }">
															<li>  <a href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${page+1}">Next</a></li>
														</c:if>
														<c:if test="${page ne end-1 }">
													  		<li><a  href="/hoveyenergy${requestScope['javax.servlet.forward.servlet_path']}?filter=${filter }&page=${end-1}">Last</a></li>
														</c:if>
													</ul>					
												</div> 
		            
		            	
		            		<p style="clear:both"></p>
		            		<br />
		            
		       <div style="padding: 10px;background: #E7EAF8;border: 1px solid #ccc;border-radius: 5px;text-align: center;">
		       		<form method="get" action="/hoveyenergy/admin/welcomemessage.do">
		       				<label>Default Welcome Message:</label> 
		       				<input type="text" name="welcomeMessage" required="required" />
		       				<input type="submit" class="btn btn-update" value="Save" />
		       		</form>
			  </div>     
		            
		            
		            
       </c:if>
		
		
	
	</div>
   
 </body>
</html>