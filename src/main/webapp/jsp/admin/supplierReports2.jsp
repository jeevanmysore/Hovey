<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



			<div class="control-group" style="text-align: left;" id="upload${count}">
						<label class="control-label" for="textfield">Choose File	:<span class="error_msg">*</span></label>	
						
							<form:input type="file" path="reportForm.reports[${count}].file" class="input-xlarge" id="file${count}"  index="${count}" onchange="getFileName(this.id)"/>
						
						<span class="help-block error" style="margin-top: 0px;margin-bottom: -20px;height: 25px;" id="fileerror${count }"></span>
						     
									<form:hidden path="reportForm.reports[${count}].fileName" id="fileName${count}" />	
							
					</div>