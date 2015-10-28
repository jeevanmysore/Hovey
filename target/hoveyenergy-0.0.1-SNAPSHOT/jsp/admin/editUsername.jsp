<!DOCTYPE html>
<html>
<head>

		 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
		 <script src="http://code.jquery.com/jquery-latest.js"></script>
		
	<title>Edit Agent</title>
	 
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#eee');
		    
		});
	</script>
	<script type="text/javascript">
			function validateChangeUsernameForm(){
				
				var filter= /[^a-zA-Z_0-9]+/g;
				var a=true;
				var uname=$('#username').val();				
				
				
				if(filter.test(uname)){
					$('#uname_error').text("* Special Characters not allowed in Username");
					a=false;
				}
				else if(uname==null||uname.indexOf(" ") != -1){
					jQuery('#uname_error').html('* Username Should not have space');
					a=false;    			
				}
				else if(uname.length<6 || uname.length>20||uname==""||uname=="Username"){
		    		$('#uname_error').text("* Username should be between 6-20 characters");	
		    		a=false;		    		
		    	}else{
					
		    		$.ajax({
						url: "/hoveyenergy/validateuser.do",
						type: "POST",						
						data:{username:uname},
						async:false,
						success: function(data){
							
							if(data=="success"){					
								jQuery('#uname_error').text('');
								a=true;
							}
							else{
								jQuery('#uname_error').text('* Username Already Exists');
								a=false;
							}
						}
					});
		    		
		    	}
				if(a==true){
					return true;
				}
				else{
					return false;		
				}
				
						
			}
		
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
	  		<h1>Change Username</h1>
	  		
	  		</div>
	  		
			<form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/changeusername.do" onsubmit="return validateChangeUsernameForm()">
									
									<input type="hidden" name="agentId" value="${agent.agentNumber }" id="agentId" />
									
									
									
									<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Existing Username:</label>										    
											<input type="text" name="firstName" value="${agent.username }" class="input-xlarge" id="fname" readonly="readonly" >
																				
									</div>
									
									<div class="control-group">
										<label class="control-label" for="textfield">New  Username:<span class="error_msg">*</span></label>										    
											<input type="text" name="username"  class="input-xlarge" id="username"   >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="uname_error"></span>										
									</div>
									
																					
									
									
									
									
									
									<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										<button type="submit"  class="btn-update btn" style="width: 200px;">Change Username</button>
										
										<a href="/hoveyenergy/admin/changeusername.do?agentId=${agent.agentNumber}" class="btn btn-undo"style="margin-left: 10px;">Undo</a>
									
										<a href="/hoveyenergy/admin/agents.do"  class="btn btn-back"  style="margin-left: 10px;">Back</a>
									</div>
				</form>
		       
          
                
                
            </div>
       
		
		</div>
	</div>
   
 </body>
</html>