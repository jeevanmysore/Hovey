<!DOCTYPE html>
<html>
<head>
	<title>Upload Supplier Reports</title>
	 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 	<script type="text/javascript" src="/hoveyenergy/js/supplierReports.js"></script>
	  <link href="/hoveyenergy/css/common.css" rel="stylesheet" type="text/css">
	<script>
		$(document).ready(function() {
		    $("table tr:nth-child(even)").css('background-color', '#e5e5e5');
		  
		   
		});
	</script>
	
</head>


<body class="firefox" >
   <%@ include file="header.jsp" %>
	<div id="content">
		<br />
		<div id="wrapper">
	  	
		<div class="data-input-form">
			<div class="sub-title" style="">
	  		<h1>Load Supplier Report Files</h1>
	  		
	  		</div>
	  		
			<form:form class="form-horizontal1 form-validate1" method="POST" action="/hoveyenergy/admin/loadsupplierreport.do" commandName="reportForm" enctype="multipart/form-data" onsubmit="return validateReportsForm()" >
					<div class="control-group" style="margin-top: 60px;height: 13px;">
										<label class="control-label" for="textfield">Supplier Name:<span class="error_msg">*</span></label>	
										  <spring:bind path="reportForm.reports[0].supplierName">									    
											<select name="${status.expression }" class="input-xlarge" id="sname" style="height: 30px;padding: 4px;" >
												<c:forEach var="sup" items="${mappings}">
													<option value="${sup.supplierName.supplierName}">${sup.supplierName.supplierName }</option>
												</c:forEach>
											
											</select>
										</spring:bind>
																				
					</div>
					<div class="control-group" style="text-align: left;">
						<label class="control-label" for="textfield">Choose File	:<span class="error_msg">*</span></label>	
						<spring:bind path="reportForm.reports[0].file">
							<input type="file"  name="${status.expression }"class="input-xlarge" id="file0"  index="0" onchange="getFileName(this.id)" />
						</spring:bind>
						<span class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="fileerror0" ></span>
						     <spring:bind path="reportForm.reports[0].fileName">
									<input type="hidden"  name="${status.expression }" id="fileName0"  />	
							</spring:bind>
					</div>
				 
				 
				 
				  <div class="control-group" style="text-align: left;" id="attachfile">
				   <a href="#" style="color:green;text-decoration: underline;margin-top: -3px;" id="addFiles" class="control-label" >Upload More Files</a>
				   <a href="#" style="color:green;text-decoration: underline;" id="removeFiles">Remove Files</a>
				   
				</div >
				
					<div style="text-align: center;margin-bottom: 20px;clear: both;">
            			<button type="submit" class="btn btn-update" >Upload Report</button>
					</div>
			</form:form>
       
	 
	 	</div>
	 </div>
		
	
	</div>
   
 </body>
</html>