<!DOCTYPE html>
<html>


<head>
<link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
<title>Add Utility</title>

<style>
	 	#feedback{
	 		display: inline;
			position: relative;
			left: -500px;
			color: green;
			font-size: 20px;
	 	}
	 </style>
	 
	 <script type="text/javascript">
	 var filter=/[^a-zA-Z]+/g;
	
	 var filter2=/[^0-9]+/g;
	 	function validUitlity(){
	 		var a,b=false;

			 if($('#utility').val()=="" || $('#utility').val()==null){
				 $('#utilerror').html('* Please Enter Utility Name');
				 a=false;
			 }
			 else if(filter.test($('#utility').val())){
				 $('#utilerror').html('* Should only have alphabets');
				 a=false;
			 }
			 else{
				 a=true;
			 }	    	 
	    	 if($('#length').val()=="" || $('#length').val()==null ){
	    		 b=false;
	    		 $('#lenerror').html('* Please enter account length');
	    	 }
	    	 else if(filter2.test($('#length').val())){
	    		 b=false;
	    		 $('#lenerror').html('*Should only contain digits');
	    	 }
	    	 else{
	    		 b=true;
	    	 } 
	    	 
	    	if(a==true && b==true){
	    		$('#utilerror').html('');
	    		 return true;
	    	 }
	    	else{
	    		return false;
	    	}
	    	 
	 		
	 	}
	 
	 </script>
</head>

<body class="firefox">
<%@ include file="header.jsp" %>

<div id="content">
		<br/>
	  <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;height:15px;">
	  		${message}
	  
	  </div>
	  
	  	 <div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" style="">
	  		<h1>Add Utility</h1>
	  		
	  		</div>
	  		
			<form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/addutility.do" onSubmit="return validUitlity()">
			
									<input type="hidden" name="agent" value="${agent}" />
									<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Utility Name:<span class="error_msg">*</span></label>										    
											<input type="text" name="utility"  id="utility"  class="input-xlarge"  >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;margin: 0px;" id="utilerror"></span>										
									</div>
									<div class="control-group">
										<label class="control-label" for="emailfield">Lenght of Account#<span class="error_msg">*</span></label>
										
											<input type="text" name="length" id="length" class="input-xlarge"  >
											<span for="confirmfield" class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="lenerror"></span>
										
									</div>
									
									
									
									
									
									
									<div style="text-align: center;margin-top: 20px;margin-bottom: 20px;clear: both;">
									
										<button type="submit"  class="btn-update btn">Add Utility</button>
										
										<a href="/hoveyenergy/admin/utility.do"  class="btn-undo btn" style="width: auto;margin-left: 10px;">Back</a>
										
									</div>
				</form>
		       
          
                
                
            </div>
       
		
		</div> 
	  
	  
	  
	  
	  
	  
	   
	  <!--  <div class="title">Add Utility</div>
	  <div style="margin: 15px auto;">
	  
	    <div style="text-align: center;">	<span id="utilerror" style="text-align: center;color: red;"></span> </div>
		<form method="post" action="./addutility.do" onSubmit="return validUitlity()">
		   <table style="margin: 0px auto;width: 500px;">
		    <tr>
		    	<td>Utility Name</td>
		    	<td><input type="text" name="utility"  id="utility"/></td>
		    </tr>
		    <tr>
		    	<td>Length of Account</td>
		    	<td><input type="text" name="length" id="length"/></td>
		    </tr>
		    
		    <tr>
		    	<td colspan="2" align="center"><input type="submit" value="Save Rule" style="height:44px;width:140px;text-align: center;" />
		    </tr>
		    
		   
		   </table>
		 
		</form>
	</div> -->
</div>
</body>
</html>