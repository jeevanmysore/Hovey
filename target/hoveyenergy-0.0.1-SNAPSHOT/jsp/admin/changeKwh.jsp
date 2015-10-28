<!DOCTYPE html>
<html>
<head>

		 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
		 <script src="http://code.jquery.com/jquery-latest.js"></script>
		  <script type="text/javascript" src="/hoveyenergy/js/reconcile.js"></script>
			
	<title>Change kWh Limit</title>
	 
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#eee');
		    
		});
	</script>
	
	<style>
		
	

	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;">
	  	
	  
	  </div>
	  <br />
		
		<!-- <div class="title">Edit Customer</div> -->
		<br />
		
	  <div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" style="">
	  		<h1>Change kWh Limit</h1>
	  		
	  		</div>
	  		
			<form:form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/changekwhlimit.do"  onsubmit="return validateKwhLimit()">
									
								
									<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Current kWh Limit:<span class="error_msg">*</span></label>										    
											<input type="text" name="kWhLimit" value="<fmt:formatNumber value='${kwh.kWhLimit }' groupingUsed='true'  />" class="input-xlarge" id="kWhLimit"  >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="kWhLimit_error"></span>										
									</div>
									
									
									
									
									<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										<button type="submit"  class="btn-update btn" style="">Change Limit</button>
										
										
									
										<a href="/hoveyenergy/home.do"  class="btn btn-back"  style="margin-left: 10px;">Home</a>
									</div>
				</form:form>
		       
          
                
                
            </div>
       
		
		</div>
	</div>
   
   	<c:if test="${not empty param.message }">
   		<script type="text/javascript">
   			alert("kWh Limit Changed Successfully");
   		</script>
   	</c:if>
 </body>
</html>