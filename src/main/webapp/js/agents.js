
var i=true;
var j,k,l,m,n,o,p,q=true;


var filter= /[^a-zA-Z_0-9]+/g;
var nameFilter=/[^a-zA-Z]+$/;
var numberFilter=/[^0-9]+$/;


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



$(document).ready(function(){
	i=true;j=true;
	$('#mail').focusout(function(){
		emailvalidation();
	});
	
	$('#agentId').focusout(function(){
		var agent=$('#agentId').val();	
		var user=$('#username').val();
		$.ajax({
			url:'/hoveyenergy//agentIdexiststoedit.do?agentId='+agent+'&username='+user,
			type:'GET',
			async:false,
			success:function(data){
				if(data=="success"){
					j=true;
					$('#agent_error').html('');
				}
				else{
					j=false;
					$('#agent_error').html('*AgentId already assigned to another Agent');
				}
			}
		
		});
		
	});
	
	
});


function emailvalidation(){
	var mail=$('#mail').val();
	var user=$('#username').val();
	if(validateEmail(mail)){
		$.ajax({
			url:'/hoveyenergy/agentemailexiststoedit.do?email='+mail+'&username='+user,
			type:'GET',
			async:false,
			success:function(data){
				if(data=="success"){
					i=true;
					$('#mail_error').html('');
				}
				else{
					i=false;
					$('#mail_error').html('*Email already exists with another Agent');
				}
			}		
		});		
	}
	else{
		$('#mail_error').html('*Email is not valid');
		i=false;
	}
	
}

function validateAgentForm(){	
	var fname=$('#fname').val();
	var lname=$('#lname').val();
	var city=$('#city').val();
	var state=$('#state').val();
	var email=$('#mail').val();
	var zip=$('#zip').val();
	var agentId=$('#agentId').val();
	
	
	if(nameFilter.test(fname)){
		$('#fname_error').text("*Should only have alphabets");
		k=false;
	}
	else if(fname=="" || fname=="First name" ){
		$('#fname_error').text("*Cannot be empty");
		k=false;
	}
	else{
		$('#fname_error').text("");
		k=true;
	}
	


	if(nameFilter.test(lname)){
		$('#lname_error').text("*Should only have alphabets");
		l=false;
	}
	else if(lname==""||lname == "Last name" ){
		$('#lname_error').text("*Cannot be empty");
		l=false;
	}
	else{
		$('#lname_error').text("");
		l=true;
	}
	
	

	if(nameFilter.test(city)){
		$('#city_error').text("*Should only have alphabets");
		m=false;
	}		
	else{
		$('#city_error').text("");
		m=true;
	}

	
	if(nameFilter.test(state)){
		$('#state_error').text("*Should only have alphabets");
		q=false;
	}		
	else{
		$('#state_error').text("");
		q=true;
	}

   if(zip!=="" && zip.length!=5){
		$('#zipCode_error').text("*Should only have 5 digits");
		n=false;
	}
	else if(zip!="Zip Code" && zip!=="" && numberFilter.test(zip)){
		$('#zipCode_error').text("*Should only have digits");
		n=false;
	}
	
	else{
		$('#zipCode_error').text("");
		n=true;
	}
   

	 if ($.trim(email).length == 0) {
			$('#mail_error').text("* Email address is not valid");
			o=false;
      }
		else if (!validateEmail(email)) {
			$('#mail_error').text("*Please enter valid email");
      	o=false;
     }
		else{
			$('#mail_error').text("");
			o=true;
		}     			

	 emailvalidation();

	if(numberFilter.test(agentId)){
		$('#agent_error').text("*Should only have didgits");
		p=false;
	}
	else if(agentId==""||agentId=="Agent Number"){
		$('#agent_error').text("*Cannot be empty");
		p=false;
	}
	else if(agentId.length>4){
		$('#agent_error').text("*Should Not exceed 4 digits");
		p=false;
	}
	else{			
		
		$('#agent_error').text("");
		p=true;
	}

	if(i==true && j==true && l==true && m==true && n==true && o==true && p==true && q==true ){
		return true;
	}
	else{
		return false;
	}	
}