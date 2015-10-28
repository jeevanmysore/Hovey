<!DOCTYPE html>
<html>
<head>

		 <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
		 <script src="http://code.jquery.com/jquery-latest.js"></script>
		<!-- <script type="text/javascript" src="/hoveyenergy/js/suppliers.js"></script>	 -->
		<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
		  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<title>List Of All Commissions</title>
	 
	<script>
		
		
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#edeff0'); 
		    
		   $(".hasDatePicker").datepicker({
				showOn: "button",
				buttonImage: "/hoveyenergy/images/calendar.gif",
				buttonImageOnly: true,
				onSelect: function(selected) {
			          $(".hasDatePicker").datepicker("option", selected);
			        }
		    });  
		   
		   
			  /*  $('#editRecForm').submit(function(e){
				   e.preventDefault();
			         // update input value to have no commas
			         var val = $('input').val();
			         val = val.replace(/,/g, ' ');
			         $('input').val(val);
			         // let submit go through and submit
			         $(this).submit();
				}); */
			  		   
	   });
		 
		function formatNumber(number){		
			number       = number.toString(); 
			var simpleNumber = '';  
			// Strips out the dollar sign and commas. 
			for (var i = 0; i < number.length; ++i) 
			{ 
				if ("0123456789.-".indexOf(number.charAt(i)) >= 0) 
					simpleNumber += number.charAt(i); 
			} 		 
			number = parseFloat(simpleNumber); 
			return number;
		}
		
		 function validateReconcileCommissions(){
			    var upCom1=$('#upCom1').val();
			    $('#upCom1').val(formatNumber($('#upCom1').val()));
		    	var upDate1=$('#upDate1').val();
		    	var upCom2=$('#upCom2').val();
		    	 $('#upCom2').val(formatNumber($('#upCom2').val()));
		    	var upDate2=$('#upDate2').val();
		    	var upCom3=$('#upCom3').val();
		    	 $('#upCom3').val(formatNumber($('#upCom3').val()));
		    	var upDate3=$('#upDate3').val();
		    	var i=true;
		    	var j=true;
		    	var k=true;
		    	var filter=/[^0-9.,]+/g;
		    	var filter1=/[^0-9.,]+/g;
		    	var filter2=/[^0-9.,]+/g;
		    	if(null!=upCom1 && upCom1!=0.0 && upCom1!=''){
		    		  if(filter.test(upCom1)){
		    			    i=false;
				    		$('#upCom1_error').html('*Should only have digits');
				    	  }
		    		    else if(null!=upDate1 && upDate1.trim()!=""){
		    			    i=true;
			    	  	    $('#upDate1_error').html('');	
			    	  	    $('#upCom1_error').html('');
		    			  }
		    	      else{
		    	    	  i=false;
			    	  	  $('#upDate1_error').html('*Cannot be empty');
		    		  	 }
		     	}
		    	if(null!=upCom2 && upCom2!=0.0 && upCom2!=''){
			    	  if(filter1.test(upCom2)){
			    			j=false;
					    	$('#upCom2_error').html('*Should only have digits');
					    	}
			    		 else if(null!=upDate2 && upDate2.trim()!=""){
			    			 j=true;
				    	  	  $('#upDate2_error').html('');	
				    	  	  $('#upCom2_error').html('');
			    			 }
			    	         else{
			    	    	    j=false;
				    	  	    $('#upDate2_error').html('*Cannot be empty');
			    		 	   }
			    	}
			   if(null!=upCom3 && upCom3!=0.0 && upCom3!=''){
				     if(filter2.test(upCom3)){
				    	k=false;
						$('#upCom3_error').html('*Should only have digits');
						}
				    else if(null!=upDate3 && upDate3.trim()!=""){
				    	 k=true;
					     $('#upDate3_error').html('');	
					     $('#upCom3_error').html('');
				    	}
				    	 else{
				    	    k=false;
					    	$('#upDate3_error').html('*Cannot be empty');
				    	 }  		 
			     }
		    	 
		    	if(i==true && j==true && k==true){
		    			return true;
		    		}
		    	else{
		    		  return false;
		    		}
		    }
		
	</script>
	
	<style>
		input[type="text"]{
				text-transform: none !important;
		}
	

	</style>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
	
	  <div id="feedback2" style="text-align: center;display:left:0px;color:green;font-size: 20px;font-weight: bold;">
	  	${message}
	  
	  </div>
	  <br />
		
		<!-- <div class="title">Edit Customer</div> -->
		<br />
		
	  <div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" style="">
	  		<h1>List Of All Commissions</h1>
	  		
	  		</div>
	  		
			<form:form id="editRecForm" class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/editreconcile.do"  onsubmit="return validateReconcileCommissions();">
								<input type="hidden" name="id" value="${order.id}"/>
								<input type="hidden" name="customerId" value="${order.taxId.customerId}"/>
			
									<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Account Number:<span class="error_msg">*</span></label>										    
											<input name="accountNumber" class="input-xlarge" id="acc" value="${order.accountNumber}" readonly="readonly" type="text" />
												
																				
									</div>
									<div class="control-group">
										<label class="control-label" for="emailfield">Business Name:<span class="error_msg">*</span></label>
										
											<input type="text" name="businessName"  class="input-xlarge" id="bname" value="${order.businessName}" readonly="readonly" type="text">
											<span class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="cust_error"  ></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">Term<span class="error_msg">*</span></label>
										
											<input type="text" name="term"  class="input-xlarge" id="term" value="${order.term}" readonly="readonly" type="text" >
											<span  class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;"  id="acc_error"></span>
										
									</div>
									<div class="control-group">
										<label class="control-label" for="emailfield">UpfrontCommission1</label>
										
											<input type="text" name="upfrontCommission"  class="input-xlarge" id="upCom1" value='<fmt:formatNumber  currencyCode="US" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${order.upfrontCommission}"/>' >
											
											<span  class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="sdate_error"></span>										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">UpfrontPaidDate1</label>
										<input type="text" name="upfrontPaidDate"  class="hasDatePicker" id="upDate1" value='<fmt:formatDate  value="${order.upfrontPaidDate}" pattern="MM/dd/yyyy"/>'>
											
											<span  class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="upDate1_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">UpfrontCommission2</label>
										
											<input type="text" name="upfrontCommission2"  class="input-xlarge" id="upCom2" value='<fmt:formatNumber  currencyCode="US" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${order.upfrontCommission2}"/>'>
											<span  class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="term_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">UpfrontPaidDate2</label>
										
											<input type="text" name="upfrontPaidDate2"  class="hasDatePicker" id="upDate2" value='<fmt:formatDate  value="${order.upfrontPaidDate2}" pattern="MM/dd/yyyy"/>' />
											<span class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="upDate2_error"></span>
										
									</div>
									
									<div class="control-group">
										<label class="control-label" for="emailfield">UpfrontCommission3</label>										
											<input type="text" name="upfrontCommission3"  class="input-xlarge" id="upCom3" value='<fmt:formatNumber  currencyCode="US" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" value="${order.upfrontCommission3}"/>'>
											<span  class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="rate_error"></span>										
									</div>
									
									
									<div class="control-group">
										<label class="control-label" for="emailfield">UpfrontPaidDate3</label>										
											<input type="text" name="upfrontPaidDate3"  class="hasDatePicker" id="upDate3" value='<fmt:formatDate  value="${order.upfrontPaidDate3}" pattern="MM/dd/yyyy"/>'/>
											<span  class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="upDate3_error"></span>										
									</div>			
									
									<div style="text-align: center;margin-bottom: 20px;clear: both;">									
										<button type="submit"  class="btn-update btn"  style="width: 100px;">Update</button>										
										<a href="/hoveyenergy/admin/editreconcile.do?orderId=${order.id}" class="btn btn-undo"  style="margin-left: 10px;">Undo</a>
										<a href="/hoveyenergy/admin/reconcile.do?customerId=${order.taxId.customerId}"  class="btn btn-back"  style="margin-left: 10px;">Back</a>
									</div>
				</form:form>
		       
          
                
                
            </div>
                     
		</div>
	</div>
   
 </body>
</html>