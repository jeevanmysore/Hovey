function validateLogin(){
	var uname=$('#username').val();
	var pass=$('#pass').val();
	

		if(uname==""||uname=="Username"){
		$('#userId').text("* Username Cannot be Empty");
		l=false;
	}
	else{
		$('#userId').text("* ");
		l=true;
	}	
	if(l==true){
	if(pass==""||pass=="Password"){
	$('#Passwd').text("*Password cannot be Empty");
	m=false;
	}
	else{
		$('#Passwd').text("*");
		m=true;
		}
	}
	if(l==true && m==true){
		return true;
	}
	else
		return false;
}

function validatePass(){
	var pass=$('#pass').val();
	var cpass=$('#cpass').val();
	
	
	 if(pass.length<6 || pass.length>20||pass==""||pass=="Password"||pass.indexOf(" ") != -1 ){
		$('#Passwd').text("Password cannot be empty and should between 6-20 Characters");	
		m=false;	
	}else{
		$('#Passwd').text("");
		m=true;
	}
	if(m==true){
		if(cpass!=pass){
			$('#Passwd').text("* Password and Confirm Password Should Match");	
    		n=false;
		}
		else{
			$('#Passwd').text("");	
    		n=true;
		}
		
	}
	if(m==true && n==true){
		return true;
	}else{
		return false;
	}
	
}


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

var status=false;
function checkEmail(mail){
	var email= $('#email').val();
	if ($.trim(email).length == 0) {
		$('#mail').text("* Email address is not valid");
		
		c= false;
		
	}
	if(validateEmail(email) == true){
		$.ajax({
			url: "./checkEmail.do",
			type: "POST",						
			data:{email:email},
			success: function(data){
				
				if(data == 'success'){
					
					jQuery('#mail').html('');
					jQuery('#login-form').removeAttr('onsubmit');
					jQuery('#login-form').submit();
				}else{					
					//alert("hiii");
					jQuery('#mail').html('* Email Not Registered');
					//return temp;
				}
			}
		});
	} else{
		jQuery('#mail').html('* Email id not valid');
		return false;
	}	
	
}

	
	