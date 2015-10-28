
var a,b,c,d=true;

$(document).ready(function(){
	
	$('#sname').focusout(function(){
		checksupplier();
	});
});


function checksupplier(){
	var supplierName=$('#sname').val();
	$.ajax({
		url:"/hoveyenergy/validatesupplier.do?supplierName="+supplierName,
		type: 'GET',
		async:false,
		success:function(data){
			if(data=="success"){
				d=true;
				$('#sname_error').html('');
			}
			else if(data=="fail"){
				d=false;
				$('#sname_error').html('Supplier is already registered');
			}
			else{
				a=false;
			}
		}
	});
}


//validating add supplier form
function validateSupplierForm(){
	var filter=/[^a-zA-Z ]+/g;
	var filter2=/[^0-9.]+/g;
	var filter3=/[^0-9.]+/g;
	var supplier=$('#sname').val();
	var ccom=$('#ccom').val();
	var rcom=$('#rcom').val();
	
	
	if(null==supplier || supplier==""){
		a=false;
		$('#sname_error').html('Supplier cannot be empty');
	}
	else if(filter.test(supplier)){
		a=false;
		$('#sname_error').html('*Should only have alphabets');
	}
	else{
		a=true;
		checksupplier();
	}
	
	
	if(null==ccom || ccom==""){
		b=false;
		$('#ccom_error').html('*Cannot be empty');
	}
	else if(filter2.test(ccom)){
		b=false;
		$('#ccom_error').html('*Should only have digits');
	}
	else{
		b=true;
		$('#ccom_error').html('');
	}
	
	

	if(null==rcom || rcom==""){
		c=false;
		$('#rcom_error').html('*Cannot be empty');
	}
	else if(filter3.test(rcom)){
		c=false;
		$('#rcom_error').html('*Should only have digits');
	}
	else{
		c=true;
		$('#rcom_error').html('');
	}
	
	
	
	
	
	if(a== true && b==true && c==true && d==true){
		return true;
	}
	else{
		return false;
	}	
}



//validating edit form..

function validateEditSupplierForm(){
	var filter=/[^a-zA-Z ]+/g;
	var filter2=/[^0-9.]+/g;
	var filter3=/[^0-9.]+/g;
	
	var ccom=$('#ccom').val();
	var rcom=$('#rcom').val();
	var i,j=true;
	
	
	
	
	if(null==ccom || ccom==""){
		i=false;
		$('#ccom_error').html('*Cannot be empty');
	}
	else if(filter2.test(ccom)){
		i=false;
		$('#ccom_error').html('*Should only have digits');
	}
	else{
		i=true;
		$('#ccom_error').html('');
	}
	
	

	if(null==rcom || rcom==""){
		j=false;
		$('#rcom_error').html('*Cannot be empty');
	}
	else if(filter3.test(rcom)){
		j=false;
		$('#rcom_error').html('*Should only have digits');
	}
	else{
		j=true;
		$('#rcom_error').html('');
	}
	
	
	
	
	
	if(i==true && j==true){
		return true;
	}
	else{
		return false;
	}	
}





var filter=/[^a-zA-Z ]+/g;
function validateSupplierMappingForm(){
	
	var p,q,r,s,t,u,v,w,x,y,z=true;
	
	var cust=$('#cust').val();
	var acc=$('#acc').val();
	var sdate=$('#sdate').val();
	var edate=$('#edate').val();
	var term=$('#term').val();
	var kwh=$('#kwh').val();
	var rate=$('#rate').val();
	var crate=$('#crate').val();
	var pdate=$('#pdate').val();
	var com=$('#com').val();
	
	
		if(null==cust|| cust==""){
			p=false;
			$('#cust_error').html('*Requied field');		
		}
		else if(filter.test(cust)){
			p=false;
			$('#cust_error').html('*Should only have aplhabets');		
		}
		else{
			p=true;
			$('#cust_error').html('');		
		}
		
		
		if(null==acc|| acc==""){
			q=false;
			$('#acc_error').html('*Requied field');		
		}
		else if(filter.test(acc)){
			q=false;
			$('#acc_error').html('*Should only have aplhabets');		
		}
		else{
			q=true;
			$('#acc_error').html('');		
		}
		
		
		if(null==sdate|| sdate==""){
			r=false;
			$('#sdate_error').html('*Requied field');		
		}
		else if(filter.test(sdate)){
			r=false;
			$('#sdate_error').html('*Should only have aplhabets');		
		}
		else{
			r=true;
			$('#sdate_error').html('');		
		}
	
		
		if(null!==edate && filter.test(edate)){
			s=false;
			$('#edate_error').html('*Should only have aplhabets');		
		}
		else{
			s=true;
			$('#edate_error').html('');		
		}
		
		
		
		if(null!==term && filter.test(term)){
			t=false;
			$('#term_error').html('*Should only have aplhabets');		
		}
		else{
			t=true;
			$('#term_error').html('');		
		}
		
		
		if(null==kwh|| kwh==""){
			u=false;
			$('#kwh_error').html('*Requied field');		
		}
		else if(filter.test(kwh)){
			u=false;
			$('#kwh_error').html('*Should only have aplhabets');		
		}
		else{
			u=true;
			$('#kwh_error').html('');		
		}
		
		
		if(null!==rate && filter.test(rate)){
			v=false;
			$('#rate_error').html('*Should only have aplhabets');		
		}
		else{
			v=true;
			$('#rate_error').html('');		
		}
		
		

		if(null==crate|| crate==""){
			w=false;
			$('#crate_error').html('*Requied field');		
		}
		else if(filter.test(crate)){
			w=false;
			$('#crate_error').html('*Should only have aplhabets');		
		}
		else{
			w=true;
			$('#crate_error').html('');		
		}
		
		
		if(null!==pdate && filter.test(pdate)){
			x=false;
			$('#pdate_error').html('*Should only have aplhabets');		
		}
		else{
			x=true;
			$('#pdate_error').html('');		
		}
		
		

		if(null==com|| com==""){
			y=false;
			$('#com_error').html('*Requied field');		
		}
		else if(filter.test(com)){
			y=false;
			$('#com_error').html('*Should only have aplhabets');		
		}
		else{
			y=true;
			$('#com_error').html('');		
		}
		
		
		if(p==true && q==true && r==true && s==true && u==true && v==true && w==true && x==true && y==true  ){
			return true;
		}
		else{
			return false;
		}
		
}




function validateSupplierReport(){
	
	
}