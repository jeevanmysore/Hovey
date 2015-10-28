var flag=true;
var i,j,k,l,m,n,o,p,q,r,s,t,u,v,w=true;
var a=true;
var agres=false;
j=true;
var filter= /[^a-zA-Z_0-9]+/g;
var nameFilter=/[^a-zA-Z]+$/;
var numberFilter=/[^0-9]+$/;


$(document).ready(function(){
	
	$('#email').focusout(function(){		
		var mail=$('#email').val();
		if(null!=mail && mail!==""){
			$.ajax({
				url: "/hoveyenergy/validatemail.do",
				type: "POST",						
				data:{email:mail},
				async:false,
				success: function(data){
					
					if(data=="success"){					
						jQuery('#mail').html('');
						j=true;
					}
					else{
						jQuery('#mail').html('* Email Already Registered');
						j=false;
					}
				}
			});
		}		
	});
	
		$('#username').focusout(function(){
			
			var user=$('#username').val();
			$.ajax({
				url: "/hoveyenergy/validateuser.do",
				type: "POST",						
				data:{username:user},
				async:false,
				success: function(data){
					
					if(data=="success"){					
						jQuery('#userId').html('');
						i=true;
					}
					else{
						jQuery('#userId').html('* User Already Exists');
						i=false;
					}
				}
			});
	
		});
		
		$('#agentNumbr').focusout(function(){
			
			var no=$('#agentNumbr').val();
			if(agres==true){
				agres=false;
			}
			$.ajax({
				url: "/hoveyenergy/validateagent.do",
				type: "POST",						
				data:{no:no},
				async:false,
				success: function(data){
					
					if(data=="success"){					
						jQuery('#agent').html('* Agent is Already Registered with this Number');
						w=false;
					}
					else{
						if($('#agent').text()!=="*"){
							jQuery('#agent').html('');
						}
						jQuery('#agent').html('');
						w=true;
						agres=true;
					}
				}
			});
			
		});
		
		/*  $('#pass').focus(function(){
			  $(this).get(0).type='password';
			});*/
});


 // validating email format..
function validateEmail(sEmail) {
    var emailFilter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
       
    if (emailFilter.test(sEmail)) {
        return true;
    }
    else {
        return false;
    }
}

function validateReg(){
	
	
	
	
	var uname=$('#username').val();
	var pass=$('#pass').val();
	var cpass=$('#cpass').val();
	
	var fname=$('#fname').val();
	var lname=$('#lname').val();
	var city = $('#city').val();
	var state=$('#state').val();
	var zip = $('#zip').val();
	//var phnNumbr = $('#phnNumbr').val();
	var email=$('#email').val();
	var agentNumbr = $('#agentNumbr').val();
	
	
	/*if(uname==""||pass==""||cpass==""||fname==""||lname==""||city==""||zip==""||phnNumbr==""||email==""||agentNumbr==""){
		//k=false;
		jQuery('#userId').html('Please fill all the fields');
	}*/
	
	
	/*k=true;
	
	
	if(k==true)
	{*/
		// username validations........
		if(filter.test(uname)){
			$('#userId').text("* Special Characters not allowed in Username");
			l=false;
		}
		else if(uname==null||uname.indexOf(" ") != -1){
			jQuery('#userId').html('Username Should not have space');
			l=false;    			
		}
		else if(uname.length<6 || uname.length>20||uname==""||uname=="Username"){
    		$('#userId').text("Username cannot be empty and should be between 6-20 characters");	
    		l=false;		    		
    	}else{
			$('#userId').text("");
			l=true;
    	}
		
	/*}*/
	
	
	
		
		if(pass==null||pass.indexOf(" ") != -1 ||  pass==""){
			jQuery('#Passwd').html('Password should not be empty / should not have spaces');
			m=false;
		}
		else if(pass.length<6 || pass.length>20||pass==""||pass=="Password"){
    		$('#Passwd').text("Password cannot be empty and should be should be between 6-20 characters");	
    		m=false;	
    	}else{
			$('#Passwd').text("");
			m=true;
    	}
	
	
	
		if(cpass==null || cpass.indexOf(" ") != -1 || cpass=="Confirm Password" ||cpass==""){
			$('#confirmPasswd').text("* Confirm Password cannot be empty");	
    		n=false;
		}
		else if(cpass!=pass){
			$('#confirmPasswd').text("* Password and Confirm Password should match");	
    		n=false;
		}
		else{
			$('#confirmPasswd').text("");	
    		n=true;
		}
		
	
	
	
		if(nameFilter.test(fname)){
			$('#FirstName').text("* First Name should only have alphabets");
			o=false;
		}
		else if(fname=="" || fname=="First name" ){
			$('#FirstName').text("*First Name cannot be empty");
			o=false;
		}
		else{
			$('#FirstName').text("");
			o=true;
		}
		
	
	
		if(nameFilter.test(lname)){
			$('#LastName').text("* Special Characters or numbers not allowed in Name");
			p=false;
		}
		else if(lname==""||lname == "Last name" ){
			$('#LastName').text("*Last Name cannot be empty");
			p=false;
		}
		else{
			$('#LastName').text("");
			p=true;
		}

	
	
	
		if(nameFilter.test(city)){
			$('#cty').text("*City Name should only have alphabets");
			q=false;
		}		
		else{
			$('#cty').text("");
			if($('#city').val()=="City"){
				$('#city').val('');
			}
			q=true;
		}
		
	
	
	
	
	   if(zip!="Zip Code" && zip!=="" && zip.length!=5){
			$('#ZipCode').text("* Zip should have 5 digits");
			s=false;
		}
		else if(zip!="Zip Code" && zip!=="" && numberFilter.test(zip)){
			$('#ZipCode').text("*Zip code should only have digits");
			s=false;
		}
		
		else{
			$('#ZipCode').text("");
			if($('#zip').val()=="Zip Code"){
				$('#zip').val('');
			}
			s=true;
		}
		
	
	
  /*	if(s==true){
		if(filter.test(city)){
			$('#phn').text("* Special Characters not allowed, only Numbers");
			r=false;
		}
		else if(phnNumbr==""||phnNumbr=="Phone Number"){
			$('#phn').text("* Phone Number Cannot be Empty");
			r=false;
		}
		else{
			t=true;
		}*/
		
	//}
	   if(null!=email && email!==""){
		   if ($.trim(email).length == 0) {
					$('#mail').text("* Email address is not valid");
					u=false;
		   }
		   else if (!validateEmail(email)) {
					$('#mail').text("*Please enter valid email");
		        	u=false;
		    }
			else{
					$('#mail').text("");
					u=true;
				}     			
	   }
	   else{
		   u=true;
	   }
	
	
		if(numberFilter.test(agentNumbr)){
			$('#agent').text("*Agent Number should only have didgits");
			v=false;
		}
		else if(agentNumbr==""||agentNumbr=="Agent Number"){
			$('#agent').text("* Agent Number cannot be empty");
			v=false;
		}
		else if(agentNumbr.length>4){
			$('#agent').text("* Agent Number should Not exceed 4 digits");
			v=false;
		}
		else{			
			
			$('#agent').text("");
			v=true;
		}
	
		if(i==true && j==true && l==true && m==true && n==true && o==true && p==true && q==true && s==true && u==true && v==true && w==true && agres==true){
		
		return true;
		
		
	}
	else if(i==false|| j==false){
		$('#userId').text("* Username/ Email Already Registerd");
		return false;
	}
	else if(w==false){
		$('#userId').text("* Agent Number Already Registered");
		$('#agent').text("* Agent Number Already Registered");
		return false;
	}
	else{
		
		return false;
	}
	
	
}
	



