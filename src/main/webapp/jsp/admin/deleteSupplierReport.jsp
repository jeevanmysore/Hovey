<!DOCTYPE html>
<html>
<head>
	<title>Remove Supplier Reports</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 	<script type="text/javascript" src="/hoveyenergy/js/supplierReports.js"></script>
	  <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#e5e5e5');
		  
		   
		});
		
		function validateremoval(){
			var file=$('#file').val();
			
			if(file==null || file==""){
				$('#error').html('* Select File');
				return false;
			}
			else{
				$('#error').html('');
				var x=confirm("Are You Sure, You Want to Remove Supplier Reports File ");
				if(x==true){
					return true;
				}
				else{
					return false;
				}
			}
		}
	</script>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
		<br />
		<div id="wrapper">
		
		   <c:if test="${not empty param.message }">
		   		<div class="title" style="color:red;"> Supplier Report Removed Successfully</div>
		   </c:if>
		
	  	
		<div class="data-input-form">
			<div class="sub-title" style="">
	  		<h1>Remove Uploaded Supplier Reports</h1>
	  		
	  		</div>
	  		
			<form:form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/removeuploadedsupplierreport.do" onsubmit="return validateremoval()"  >
					<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Select File:	<span class="error_msg" >*</span></label>																    
											<select name="fileId" class="input-xlarge" id="file" style="height: 30px;padding: 4px;" >
												<option value="" >---Select--</option>
												<c:forEach var="file" items="${files}">
													<option value="${file.id}">${file.fileName }</option>
												</c:forEach>											
											</select>		
									<span class="error_msg" id="error"  style="float: left;margin-left: 10px;font-size: 12px;padding-top: 5px;">*</span>															
					</div>
				
				
					<div style="text-align: center;margin-bottom: 20px;clear: both;">
            			<button type="submit" class="btn btn-update" >Remove Supplier Reports</button>
					</div>
			</form:form>
       
	 
	 	</div>
	 </div>
		
	
	</div>
   
 </body>
</html>