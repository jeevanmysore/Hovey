 var a,b,c,d,e,f,g,cust=true;
	
	 function validateCustomerwithTaxid(){
			var tax=$('#ctax').val();
			
			$.ajax({
				url:"/hoveyenergy/validatecustomer.do",
				type:"POST",
				async:false,
				data:{taxid:tax},
				success:function(data){
					if(data!="success"){
						$('#ctax_error').html('* '+data);
						cust=false;							
					}
					else if(data=="success"){
						$('#ctax_error').html('');
						cust=true;
					}
				}
			});
		}
	
		$(document).ready(function(){
			$('#ctax').change(function(){				
				validateCustomerwithTaxid();
			});
			
		});
		
		
		function validateCustomerForm(){
			var filter1= /[^a-zA-Z 0-9]+/g;
			 var filter=/[^a-zA-Z ]+/g;
			 var filter7=/[^a-zA-Z ]+/g;
			 var filter8=/[^a-zA-Z ]+/g;
			 var filter2=/[^0-9x*]+/g;
			 var filter3=/[^0-9.]+/g;
			 var emailFilter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
			 
			 
			var tax=$('#ctax').val();
			if(tax!="" && tax.length!=9){
				$('#ctax_error').html('*Should have 9 Digits');
				a=false;
			}
			else if(tax!="" && filter2.test(tax)){
				$('#ctax_error').html('*Should only have Digits');
				a=false;
			}
			else{
				$('#ctax_error').html('');
				a=true;
			}
			
			
			
			
			var cname=$('#cname').val();
			if(cname=="" || cname==null){
				b=false;
				$('#cname_error').html('*Required');
			}
			else if(filter.test(cname)){
				b=false;
				$('#cname_error').html('*Should only have Alphabets');
			}
			else{
				b=true;
				$('#cname_error').html('');
			}
			
			
			
			var cname2=$('#cname2').val();
			if(cname2=="" || cname2==null){
				g=false;			
				$('#cname2_error').html('*Required');
			}
			else if(filter7.test(cname2)){
				g=false;
				$('#cname2_error').html('*Should only have Alphabets');
			}
			else{
				g=true;
				$('#cname2_error').html('');
			}
			
			
			var ctitle=$('#ctitle').val();
			if(ctitle=="" || ctitle==null){
				c=false;
				$('#ctitle_error').html('*Required');
			}
			else if(filter8.test(ctitle)){
				c=false;
				$('#ctitle_error').html('*Should Only have Alphabets');
			}
			else{
				c=true;
				$('#ctitle_error').html('');
			}
			
			
			var cphone=$('#cphone').val();
			if(cphone=="" || cphone==null){
				d=false;
				$('#cphone_error').html('*Required');
			}
			else if(cphone.length!=10){
				d=false;
				$('#cphone_error').html('*Should have 10 Digits');
			}
			else if(cphone=="0000000000"){
				d=false;
				$('#cphone_error').html('*Not Valid');
			}
			else if(filter2.test(cphone)){
				d=false;
				$('#phone_error').html('*Not Valid');
			}
			else{
				d=true;
				$('#cphone_error').html('');
			}
			
			
			var cemail=$('#cmail').val();
			 if(cemail!="" && !emailFilter.test(cemail)){
				e=false;
				$('#cmail_error').html('* Email Address is not Valid');
				
			}
			else{
				e=true;
				$('#cmail_error').html('');
			}
			
			
			
			var cfax=$('#cfax').val();
			if(cfax!="" && filter2.test(cfax)){
				f=false;
				$('#cfax_error').html('*Not Valid');
			}
			else if(cfax!="" && cfax.length!=10){
				f=false;
				$('#cfax_error').html('*Should have 10 Digits');
			}
			else if(cfax!="" && cfax==0000000000){
				f=false;
				$('#cfax_error').html('*Not Valid');
			}
			else{
				f=true;
				$('#cfax_error').html('');
			}
			
			
			if(a==true && b==true && c==true && d==true &&  e==true && f==true && g==true && cust==true){
				return true;
			}
			else{
				return false;
			}
			
			
		}