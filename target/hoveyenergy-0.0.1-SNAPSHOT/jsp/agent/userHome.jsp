<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>


<head>
	<title>Agent Home</title>
</head>
<style>
#card {
	width: 800px;
	height:200px;
	display: inline-block;
	vertical-align: middle;
	background: #F0F0F0;
	border-radius: 3px 3px 4px 4px;
	
	
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-ms-box-sizing: border-box;
	box-sizing: border-box;
	border-top: 1px solid white;

	-webkit-box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
	box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
	padding:44px ;
	font-size:40px ;
}

#card h2{
	
}
</style>

<body class="firefox">

<%@ include file="header.jsp" %>

	<div id="content" style="padding-top: 100px;">
						
						<h2 ID="card">Welcome ${user.firstName }, <br />
						   <div>	${user.welcomeMessage } </div>
						</h2>
			
	</div>
</body>
</html>