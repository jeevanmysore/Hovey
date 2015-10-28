 $(document).ready(function(){			 	
	 
  		$('#selectfilter').change(function(){
  			
  			 var selected = $(this).find('option:selected');
  		     var filtertype = selected.data('e'); 
  		     
  		     
  		     if(filtertype==""){
  		    	$('#value').val('Enter Filter Value');
  				$('#value').addClass('filtertext');
  		     }  	
  		     
  		     else if(filtertype=="date"){  		    	 
  		    	 
  		    	 $('#value').val('');
  		    	 
  		    	 $('#value').css('display','none');
  		    	 $('#value').removeAttr('name');
  		    	 $('#orderDatepicker').css('display','inline');
  		    	 $('#orderfilter .ui-datepicker-trigger').css('display','inline');
  		    	 $('#orderDatepicker').attr('name','value');		    
  		    }
  		     else{
  		    	$('#orderDatepicker').val('');
  		    	 $('#orderDatepicker').css('display','none');
  		    	 $('#orderfilter .ui-datepicker-trigger').css('display','none');
  		    	 $('#orderDatepicker').removeAttr('name');
  		    	 $('#value').css('display','inline');
  		    	 $('#value').attr('name','value');
  		    	
  		    	
  		    	if($('[name="value"]').val()!="Enter Filter Value"){ 
  		    		$('[name="value"]').removeClass('filtertext');
  		    	}
  		    	else{
  		    		$('[name="value"]').addClass('filtertext');
  		    	}
  		     }
  		     
  		     
  	         $('#filtertype').val(filtertype);
  		}).change();
  		
  		
  		$('[name="value"]').focus(function(){
  			if($('[name="value"]').val()=="Enter Filter Value" || $('[name="value"]').val()==""){
  				$('[name="value"]').removeClass('filtertext');
  				$('[name="value"]').val('');
	  			
  			}
  			else{
  				
  			}
  			
  			/*else{
  				
  				$('[name="value"]').addClass('filtertext');
  			}*/
  		})
  		
  		
  		$('[name="value"]').focusout(function(){
  			if($('[name="value"]').val()=="" || $('[name="value"]').val()==null){
  				
  				$('[name="value"]').val('Enter Filter Value');
  				$('[name="value"]').addClass('filtertext');
  			}
  			
  		})
  		
  		
  		
  		
  	});
 
 
  
     function validatefilter(){
    	 var a=false;
		 var b=false;    
		 
		
		 if($('#selectfilter').val()=="" || $('#selectfilter').val()==null){
			
			 a=false;
		 }
		 else{
			 a=true;
		 }	    	 
    	 if($('[name="value"]').val()=="" || $('[name="value"]').val()==null || $('[name="value"]').val()=="Enter Filter Value" ){
    		 b=false;
    	 }
    	 else{
    		 b=true;
    	 } 
    	 
    	if(a==true && b==true){
    		 return true;
    	 }
    	if(a==false){
    		
    		$('#filtererror').html('* Please Select any One Field');
    		return false;
    	}
    	if(b==false){
    		$('#filtererror').html('* Please Enter Value');
    		return false;
    	}
    	 else{
    		 return false;
    	 }  
    	
     }
     
     
     
     function compareDates(startDate,endDate){
    	 if( (new Date(startDate).getTime() > new Date(endDate).getTime()))
    	 {
    		 return false;
    	 }
    	 else{
    		 return true;
    	 }
     }
     
     function validateMultiSearch(){
    	 var a,b,c,d,e,f,g=true;
    	 var h,i,j,k,l=true;
    	 var startDate=$('#startDate').val();
    	 var endDate=$('#endDate').val();
    	 var dealStartDate1=$('#dealStartDate1').val();
    	 var dealStartDate2=$('#dealStartDate2').val();
    	 var s2s1=$('#sentToSupplier1').val();
    	 var s2s2=$('#sentToSupplier2').val();
    	 var update1=$('#upfrontPaidDate1').val();
    	 var update2=$('#upfrontPaidDate2').val();
    	 var kwh1=formatNumber($('#kwh1').val());
    	 var kwh2=formatNumber($('#kwh2').val());
    	 var com1=formatNumber($('#commission1').val());
    	 var com2=formatNumber($('#commission2').val());
    	 var term1=$('#term1').val();
    	 var term2=$('#term2').val();
    	 
    	 var filter=/[^0-9.]+/g;
    	 var filter2=/[^0-9.]+/g;
    	 
    	 a=compareDates(startDate,endDate);
    	 if(a==false){
    		 $('#ordererror').text('* Order Start Date cannot be Higher than End Date');
    	 }
    	 else{
    		 a=true;
    		 $('#ordererror').text('');
    	 }
    	 
    	 
    	 b=compareDates(dealStartDate1,dealStartDate2);
    	 if(b==false){
    		 $('#dealerror').text('* Deal Start Date cannot be Higher than End Date');
    	 }
    	 else{
    		 b=true;
    		 $('#dealerror').text('');
    	 }
    	
    	 c=compareDates(s2s1,s2s2);
    	 if(c==false){
    		 $('#suppliererror').html('* Sent to Supplier Start Date cannot be Higher than End Date');
    	 }
    	 else{
    		 c=true;
    		 $('#suppliererror').html('');
    	 }
    	 
    	 d=compareDates(update1,update2);
    	 if(d==false){
    		 $('#upfronterror').html('* Upfront Paid Start Date cannot be Higher than End Date');
    	 }
    	 else{
    		 d=true;
    		 $('#upfronterror').html('');
    	 }
    	 
    	 if(kwh!="" && khw2!=""){
	    	  if(filter.test(kwh1) || filter.test(kwh2)){
	    		 $('#kwherror').html('KWH1/KWH2 Entered is Not Valid');
	    		 e=false;
	    	 }
	    	 else if(kwh1>kwh2){
	    		 e=false;
	    		 $('#kwherror').html('* KWH From Value Should be Less than To Value');
	    	 }
	    	 else{
	    		e=true;
	    		 $('#kwherror').html('');
	    	 }
    	 }
    	 
    	 if(com1!="" && com2!=""){
	    	 if(filter.test(com1) || filter.test(com2)){
	    		 $('#comerror').html('Commission1/Commission2 Entered is Not Valid');
	    		 f=false;
	    	 }
	    	 
	    	 else if(com1>com2){
	    		 f=false;
	    		 $('#comerror').html('* Commission From Value Should be Less than To Value');
	    	 }    	 
	    	 else{
	    		f=true;
	    		 $('#comerror').html('');
	    	 }
    	 }
    	 
    	if(filter2.test(term1) || filter2.test(term2)){
    		 $('#termerror').html('Term1/Term2 Entered is Not Valid');
    		 g=false;
    	 }
    	 
    	else if(term1>term2){
    		 g=false;
    		 $('#termerror').html('* Term From Value Should be Less than To Value');
    	 }
    	 
    	 else{
    		g=true;
    		 $('#termerror').html('');
    	 }
    	 
    	 
    	 if(a==true && b==true && c==true && d==true && e==true && f==true && g==true  ){
    		 return true;
    	 }
    	 else{
    		 return false;
    	 }
     }
     
     
     
     function formatNumber(number){		
			number= number.toString(); 
			var simpleNumber = '';  
			// Strips out the dollar sign and commas. 
			for (var i = 0; i < number.length; ++i) 
			{ 
				if ("-0123456789.".indexOf(number.charAt(i)) >= 0) 
					simpleNumber += number.charAt(i); 
			} 		 
			number = parseFloat(simpleNumber); 
			return number;
		}
     
     
     
     
     function changeCommission(e){
    	 var id=jQuery('#'+e.id).attr('data_index');    	 
    	 var kwh=$('#kwh'+id).val();
    	 var comRate=$('#comRate'+id).val();
    	 var termMonth=$('#termMonth'+id).val();
    	 var termCommission=(kwh*comRate) *(termMonth/12);
    	 $('#commission'+id).val((kwh*comRate).toFixed(2));
    	 $('#termCommission'+id).val(termCommission.toFixed(2));
    	 editpipeline(e);
     }
     
     
     
     function editpipeline(e){
    	 /* alert("success"+id);
    	 console.log(jQuery('#'+id.id).attr('data_index')); */
    	 
    	 
    	 var id=jQuery('#'+e.id).attr('data_index');    	
    	var status=$('#status'+id).val();
    	var com=$('#commission'+id).val();
    	var sentToSupplier=$('#sentToSupplier'+id).val();
    	var dealStartDate=$('#dealStartDate'+id).val();
    	var notes=$('#notes'+id).val();
    	var upfront=$('#upfrontCommission'+id).val();
    	var upfrontPaidDate=$('#upfrontPaidDate'+id).val();
    	var term=$('#term'+id).val();
    	var orderId=$('#orderId'+id).val();
    	var orderDate=$('#orderDate'+id).val();
    	var comRate=$('#comRate'+id).val();
    	var contractType=$('#contract'+id).val();
    	var resAgent=$('#resAgent'+id).val();
    	var faxReceived;
    	var qa;    	
    	
    		if($('#faxReceived'+id).is(':checked')){
    			faxReceived=true;
    		}
    		else{
    			faxReceived=false;
    		}    		
    		if($('#QA'+id).is(':checked')){
    			qa=true;
    		}
    		else{
    			qa=false;
    		}
    		
    	
    	 	
    	var d=true;
    	
	     if(status=="rescinded" && notes==""){
	    	 d=false
	    	 $('#pipeline1_error').html("* Error:Please Enter a Reason in the Notes for Account# "+$('#account'+id).val()+", Failed to Save Order");
	     }
	     else{
	    	 d=true;
	    	 $('#pipeline1_error').html('');
	     }
    	
    	/*var a=compareDates(orderDate,sentToSupplier);
    	var b=compareDates(sentToSupplier,dealStartDate);
    	var c=compareDates(dealStartDate,upfrontPaidDate);    */
	     var a=true;
	     var b=true;var c=true;
    	var filter=/[^-0-9.,]+/g;
    	var filter5=/[^-0-9]+/g;
    	var e,f,g,h=true; 
    	var i=true;
    	if(filter.test(com)){
    		 $('#pipeline2_error').html("<br />*Error: Commission Entered in Not Valid for Account# "+$('#account'+id).val()+", Failed to Save Order");
    		e=false;
    	}
    	else{
    		 $('#pipeline2_error').html('');
    		e=true;
    	}
    	var commission=formatNumber($('#commission'+id).val());
    	if(filter.test(upfront)){
    		 $('#pipeline3_error').html("<br />* Error: Upfront Commission Entered in Not Valid for Account# "+$('#account'+id).val()+", Failed to Save Order");
    		f=false;
    	}
    	else{
    		 $('#pipeline3_error').html('');
    		f=true;
    	}
    	var upfrontCommission=formatNumber($('#upfrontCommission'+id).val());
    	
    	
    	
    	if(filter.test(comRate)){
	   		 $('#pipeline3_error').html("<br />* Error: Commission Rate Entered in Not Valid for Account# "+$('#account'+id).val()+", Failed to Save Order");
	   		i=false;
    	}
	   	else{
	   		 $('#pipeline3_error').html('');
	   		i=true;
	   	}
    	
    	
    	
    	/*if(term!="index" && filter5.test(term)){
    		alert("Term Entered in Not Valid");
    		h=false;
    	}
    	else{
    		h=true;
    	}*/
    	
    	
    	
    	if(a==true && b==true && c==true && d== true && e==true && f==true && h==true && i==true){
    		 $('#pipeline4_error').html('');
		    	$.ajax({
		    		url:"/hoveyenergy/admin/editpipeline.do",
		    		type:"POST",
		    		async:false,
		    		data:{
		    				status:status,
		    				commission:commission,
		    				notes:notes,
		    				sentToSupplier:sentToSupplier,
		    				dealStartDate:dealStartDate,
		    				upfrontPaidDate:upfrontPaidDate,
		    				upfrontCommission:upfrontCommission,
		    				orderId:orderId,
		    				term:term,
		    				commissionRate:comRate,
		    				faxReceived:faxReceived,
		    				qa:qa,
		    				contractType:contractType,
		    				resAgent:resAgent
		    			 },
		    		success:function(data){
		    			
		    		}
		    		
		    	}); 		    	
     }
    	
    	else if(a==false){
    		 $('#pipeline4_error').html("<br />* Error:Sent To Supplier Date Cannot be Less than Order Date for Account# "+$('#account'+id).val()+", Failed to Save Order");
    	}
    	else if(b==false){
    		 $('#pipeline4_error').html("<br />* Error:Deal Start Date Cannot be Less than Sent to Supplier Date for Account# "+$('#account'+id).val()+", Failed to Save Order");
    	}
    	
    	else if(c==false){
    		 $('#pipeline4_error').html("*<br /> Error:Upfront Paid Date Cannot be Less than Deal Start Date for Account# "+$('#account'+id).val()+", Failed to Save Order");
    	}
    	   	 
     }
  	
     
     
     function isNumberKey(evt)
     {
        var charCode =  event.keyCode
        if (charCode > 31 && (charCode < 43 || charCode > 57 ) )
           return false;
      
        return true;
     }