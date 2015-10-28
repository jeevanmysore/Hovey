<!DOCTYPE html>
<html>


<head>
	<title>Enable Agents</title>
	<style>
				
			
			.pager {
		  margin-left: 0;
		  margin-bottom: 18px;
		  list-style: none;
		  text-align: center;
		  *zoom: 1;
		  margin-top: 25px;
		}
		.pager:before,
		.pager:after {
		  display: table;
		  content: "";
		}
		.pager:after {
		  clear: both;
		}
		.pager li {
		  display: inline;
		}
		.pager a {
		  display: inline-block;
		  padding: 5px 14px;
		  background-color: #fff;
		  border: 1px solid #ddd;
		  -webkit-border-radius: 15px;
		  -moz-border-radius: 15px;
		  border-radius: 15px;
		  color: green;
		}
		.pager a:hover {
		  text-decoration: none;
		  background-color: #f5f5f5;
		}
		.pager .next a {
		  float: right;
		}
		.pager .previous a {
		  float: left;
		}
		.pager .disabled a,
		.pager .disabled a:hover {
		  color: #999999;
		  background-color: #fff;
		  cursor: default;
		}
	</style>
</head>

<body class="firefox" >

   <%@ include file="header.jsp" %>
  
	<div id="content">
	   
	  
		
		<h4 align="center">Registered Agents</h4>
		<br /><br /><br/>
		<div class="CSSTableGenerator" >
                <table >
                    <tr>
                       <td>Agent Id</td>
				        <td>Agent Name</td>
				      
				        <td>Username</td>
				        <td>Email</td>
				        <td>Enable?</td>
				        
				    	
                    </tr>
                    <c:forEach var="agent" items="${agents}">
			   		 <tr>
				         <td>${agent.agentNumber} </td>
				         <td>${agent.firstName} ${agent.lastName}</td>
				          <td>${agent.username }
				           <td>${agent.email}</td>
				           <td><a href="enableagent.do?agentId=${agent.agentNumber}">Enable Agent</a>
				        </tr>
		   		 </c:forEach>
                </table>
            </div>
       
       
		
		<ul class="pager" >
		      <c:choose>
		      	<c:when test="${page eq 0 && end ne 1 }">
		      		<li>	 <a href="./getdisabledagents.do?page=${page+1}">Next</a></li>
		      	</c:when>
		      
		        <c:when test="${page eq end-1 && end ne 1 }">
		                <a href="./getdisabledagents.do?page=${page-1}">Previous</a></li>
		        </c:when>
		        
		        <c:when test="${page eq end-1 && end eq 1 }">
		               
		        </c:when>
		        
		        <c:otherwise>
		        	<li>	<a href="./getdisabledagents.do?page=${page-1}">Previous</a></li>
					<li> <a href="./getdisabledagents.do?page=${page+1}">Next</a></li>
		        
		        </c:otherwise>
		      </c:choose>
		
		
			 <%--   <c:if test="${page eq 0 }">
        		<li>	 <a href="./getdisabledagents.do?page=${page+1}">Next</a></li>
       		 </c:if>
			
			<c:if test="${page ge end-1}">
				<li>	 <a href="./getdisabledagents.do?page=${page-1}">Preivous</a></li>
			</c:if>
			
		
			<c:if test="${page gt 0 || page lt end-1 }">
			<li>	<a href="./getdisabledagents.do?page=${page-1}">Preivous</a></li>
				<li> <a href="./getdisabledagents.do?page=${page+1}">Next</a></li>
			</c:if> --%>
		</ul>
	</div>
   
 </body>
</html>