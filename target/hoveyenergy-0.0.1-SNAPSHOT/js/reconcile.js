/*
 * 
 * 
 * JavaScript file for handling Commission Reconciliation..
 * 
 * Created by Jeevan on July 23,2013..
 *  * 
 * 
 * 
 */

/*
 * function for removing comma's and other special characters from currency value..
 */
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
 
 //formatting numbers with ,
function commaFormatted(amount) {
		var delimiter = ","; // replace comma if desired
		var a = amount.split('.',2)
		var d = a[1];
		var i = parseInt(a[0]);
		if(isNaN(i)) { return ''; }
		var minus = '';
		if(i < 0) { minus = '-'; }
		i = Math.abs(i);
		var n = new String(i);
		var a = [];
		while(n.length > 3) {
			var nn = n.substr(n.length-3);
			a.unshift(nn);
			n = n.substr(0,n.length-3);
		}
		if(n.length > 0) { a.unshift(n); }
		n = a.join(delimiter);
		if(d.length < 1) { amount = n; }
		else { amount = n + '.' + d; }
		amount = minus + amount;
		return amount;
}


function changeCommission(id){
	var i=$('#'+id).attr('index');
	var kwh=$('#kwh'+i).val();
	var comRate=formatNumber($('#comRate'+i).val());
	$('#commission'+i).val((kwh*comRate).toFixed(2));
	reconcileCommission(id);
}



function reconcileCommission(id){
	var i=$('#'+id).attr('index');
	var commission=formatNumber($('#commission'+i).val());
	var upfrontCommission=formatNumber($('#upfrontCommission'+i).val());
	var comRate=$('#comRate'+i).val();
	 var termMonth=$('#termMonth'+i).val();
	 var termCommission=commission*((termMonth)/12);
	 $('#termCommission'+i).val((termCommission).toFixed(2));
	$.ajax({
		url:'/hoveyenergy/admin/reconcile.do',
		type:'POST',
		data:{
				commission:commission,
				upfrontCommission:upfrontCommission,
				id:i,
				commissionRate:comRate
			},
		async:false,
		success:function(data){
			var obj = jQuery.parseJSON(data);
			var totalCommission=obj.TOTAL_COMMISSION;
			var totalUpfront=obj.TOTAL_UPFRONT;
			var totalTermCom=obj.TOTAL_TERMCOMMISSION;
			$('#totalCommission').text('$ '+commaFormatted(totalCommission.toFixed(2)));
			$('#totalUpfront').text('$ '+commaFormatted(totalUpfront.toFixed(2)));
			$('#totalTermCommission').text('$ '+commaFormatted(totalTermCom.toFixed(2)));
			
			
		}
	});
}



function validateKwhLimit(){
	var a=true;
	var filter2=/[^0-9.,]+/g;
	var limit=$('#kWhLimit').val();
	if(null==limit || limit==""){
		$('#kWhLimit_error').text('Cannot be Empty');
		a=false;
	}
	else if(filter2.test(limit)){
		$('#kWhLimit_error').text('Should Only have Digits');
		a=false;
	}
	else{
		limit=formatNumber(limit);
		$('#kWhLimit').val(limit);
		a=true;
		$('#kWhLimit_error').text('');
	}
	
	if(a==true){
		return true;
	}
	else{
		return false;
	}	
}





//For Agen Commisssions

function changeAgentCommission(id){
	var i=$('#'+id).attr('index');
	var kwh=$('#kwh'+i).val();	
	var comRate=formatNumber($('#comRate'+i).val()); 	
	$('#commission'+i).val(commaFormatted((kwh*comRate).toFixed(2)));
	saveAgentCommission(id);
}



function changeComPayable(id){
	var filter2=/[^0-9.-]+/g;
	var i=$('#'+id).attr('index');
	var payable=$('#comPayable'+i).val();
	if(payable=="no" ){
		$('#comRate'+i).val(0); 			
		$('#comRate'+i).attr('readonly','readonly');
		changeAgentCommission(id);
	}
	else{
		$('#comRate'+i).removeAttr('readonly');
		changeAgentCommission(id);
	}	
}


function saveAgentCommission(id){
	var i=$('#'+id).attr('index');
	var iid=$('#id'+i).val();
	var payable=$('#comPayable'+i).val();	
	var orderId=$('#orderId'+i).val();
	var comRate=formatNumber($('#comRate'+i).val()); 	
	var commission=formatNumber($('#commission'+i).val());
	var week=$('#week').val();
	var year=$('#year').val();	
	var totalCommission=0;
	var totalOrders=$('#calcom').val();
	var totalResCommission=formatNumber($('#totalResCommission').val());
	var totalPayable;	
	if(comRate>=0){		
		$.ajax({
			url:'/hoveyenergy/admin/saveagentcommission.do',
			type:'POST',
			async:false,
			data:{
					id:iid,
					orderId:orderId,
					agentCommission:commission,
					commissionPayable:payable,
					commissionRate:comRate,
					week:week,
					year:year					
				},
			success:function(data){
				if(data>1){
					$('#id'+i).val(formatNumber(data));
					/*$("input[name=commission]").each(function(){
						
					});*/					
				}
			}
		});			
		for(var i=0;i<totalOrders;i++){
			var a=$("#comTable").find('.com'+i).val();
			var index=$("#comTable").find('.com'+i).attr('index');
			var comPayable=$('#comPayable'+index).val();
			console.log(a);
			console.log(comPayable);
			
			if(a==="" || null===a){
				a=0;
			}
			if(comPayable==="" || null===comPayable){
				a=0;
			}
			a=formatNumber(a);
			
			totalCommission=parseFloat(totalCommission)+parseFloat(a);
			totalPayable=parseFloat(totalCommission)-parseFloat(totalResCommission);			
		}
		$('#totalAgentCommission').val(commaFormatted(totalCommission.toFixed(2)));
		$('#totalCommission').val(commaFormatted(totalPayable.toFixed(2)));
	}
}






/*  for Rescissions
 * 
 * 
 *  */

function saveAgentCommission2(id){
	
	var i=$('#'+id).attr('index');
	var iid=$('#id'+i).val();
	var payable=$('#comPayable1'+i).val();
	var orderId=$('#orderId'+i).val();
	var comRate=formatNumber($('#comRate'+i).val()); 	
	var commission=formatNumber($('#commission'+i).val()); 	
	var totalAgentCommission=formatNumber($('#totalAgentCommission').val());	
	var totalResCommission=formatNumber($('#totalResCommission').val());	
	var totalPayable;
	var week=$('#week').val();
	var year=$('#year').val();	
	
	if(payable=="yes" && comRate>=0){
		$.ajax({
			url:'/hoveyenergy/admin/saveagentcommission.do',
			type:'POST',
			data:{					
					orderId:orderId,
					agentCommission:commission,
					commissionPayable:payable,
					commissionRate:comRate,
					refund:true,
					week:week,
					year:year	
				},
			async:false,
			success:function(data){
				if(data>1){
					$('#res'+i).remove();					
				}
			}
		});		
		totalResCommission=parseFloat(totalResCommission)+parseFloat(commission);
		totalPayable=parseFloat(totalAgentCommission)-parseFloat(totalResCommission);		
		$('#totalResCommission').val(commaFormatted(totalResCommission.toFixed(2)));
		$('#totalCommission').val(commaFormatted(totalPayable.toFixed(2)));
		
	}
	location.href="#totalResCommission";
}



/*
 * 
 * For Edit Agent Commissions
 */


function changeEditAgentCommission(id){
	var i=$('#'+id).attr('index');
	var kwh=$('#kwh'+i).val();	
	var comRate=formatNumber($('#comRate'+i).val()); 
	var comstatus=formatNumber($('#comvalue'+i).val());
	if(comstatus>=0){
		$('#commission'+i).val(commaFormatted((kwh*comRate).toFixed(2)));
	}
	else{
		$('#commission'+i).val(commaFormatted((kwh*-comRate).toFixed(2)));
	}
	saveEditAgentCommission(id);
}



function changeEditComPayable(id){
	var filter2=/[^0-9.-]+/g;
	var i=$('#'+id).attr('index');
	var payable=$('#comPayable'+i).val();
	
	if(payable=="no"){		
		$('#comRate'+i).val(0); 			
		$('#comRate'+i).attr('readonly','readonly');
		changeEditAgentCommission(id);
	}
	else{
		$('#comRate'+i).removeAttr('readonly');
		changeEditAgentCommission(id);
	}	
}


function saveEditAgentCommission(id){
	var i=$('#'+id).attr('index');
	var iid=$('#id'+i).val();
	var payable=$('#comPayable'+i).val();
	var orderId=$('#orderId'+i).val();
	var comRate=formatNumber($('#comRate'+i).val()); 	
	var commission=formatNumber($('#commission'+i).val());
	var week=$('#week').val();
	var year=$('#year').val();	
	if(comRate>=0){
		$.ajax({
			url:'/hoveyenergy/admin/saveagentcommission.do',
			type:'POST',
			data:{
					id:iid,
					orderId:orderId,
					agentCommission:commission,
					commissionPayable:payable,
					commissionRate:comRate,
					week:week,
					year:year	
				},
			async:false,
			success:function(data){
				if(data>1){
					$('#id'+i).val(formatNumber(data));
					/*$("input[name=commission]").each(function(){
						
					});*/					
				}
			}
		});		
	}
}



