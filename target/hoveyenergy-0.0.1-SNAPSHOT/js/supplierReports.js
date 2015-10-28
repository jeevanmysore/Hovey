var fileCount=0;
var a,b,c,d=true;

$(document).ready(function(){
	
	$('#addFiles').click(function(){
			
		if(fileCount<10){
			fileCount++;	
			$.get("/hoveyenergy/admin/attachsupplierfiles.do",{fileCount:fileCount},
					function(data){
					 $('#attachfile').before(data);
			});			
		}		
	});
	
	
	$('#removeFiles').click(function(){
		if(fileCount>0){
			$('#upload'+fileCount).remove();
			fileCount--;
		}
	});
	
	
	
});



function validateReportsForm(){
	var res=true;
	for(var i=0;i<=fileCount;i++){
		if(validate(i)==false){
			res=false;
		}
	}
	
	if(res==true){
		return true;
	}
	else{
		return false;
	}
	
	
	
	
}


function validate(i){
	
	var fileName=$('#fileName'+i).val();
	var fileType=fileName.split('.').pop();
	
	var hash = {
			  'xls'  : 1,
			  'xlsx' : 1,
			};

	
	if(null==fileName || fileName==""){
		a=false;
		$('#fileerror'+i).html('*Please select a file');
		
	}
	else if(!hash[fileType]){
		a=false;
		$('#fileerror'+i).html('*Only Excel files can be uploaded');
		
	}
	else{
		$.ajax({
			url:'/hoveyenergy/validatefile.do?fileName='+fileName,
			type:'GET',
			async:false,
			success:function(data){
				if(data!='success'){
					a=false;
					$('#fileerror'+i).html('*Supplier Report file already uploaded');
				}
				else{
					a=true;
					$('#fileerror'+i).html('');
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




function getFileName(id){
	var i=$('#'+id).attr('index');	
	var filename = $('#file'+i).val().split('\\').pop();
	$('#fileName'+i).val(filename);
	
}