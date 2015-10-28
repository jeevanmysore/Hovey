<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

   <div id="orderaccount${number}" class="orderacc"> 
	<div class="row-fluid">
		 <!-- <div class="seperator" style="background-color:#c2c2c2;width: 100%;height: 1px;"><hr></div> -->
		 <div class="accTitle"> Account #${number+1}
		 	<div style="display: inline;margin-left: 55px;" >
							 	<input type="checkbox" name="electric" checked="checked" value="electric"  service="electric" class="svcchk" index="${number}" id="electric${number}" onclick="getservice(this.id);"/> <span class="service" > Electric</span>
							 	<input type="checkbox" name="gas" value="gas"  service="gas" class="svcchk" style="margin-left: 50px;" index="${number}" id="gas${number}"  onclick="getservice(this.id);"/> <span class="service" >Gas</span>
							 
							 		<form:hidden path="dealSheet.orders[${number}].service" id="service${number}" index="${number}" value="electric" />
							 	
			</div>		
			<input type="checkbox" name="contract"   class="svcchk" style="margin-left: 150px;" index="${number }" id="contract${number }"  onclick="changecontract(this.id);"/> <span class="service" >Renewal</span>
							 
							 	<form:input type="hidden" path="dealSheet.orders[${number}].contractType" id="ctype${number}" value="new"  />
							
			 
		 </div>
		 		<form:hidden path="dealSheet.orders[${number}].id" id="orderId${number}" />
		 
		 		<div class="span3 util" style="margin-left: 15px;width:10%;">
							<div class="control-group">
								<label class="control-label" for="textfield">Utility<span id="utiltiy${number }_error" class="error_msg">*</span></label>
								<div class="controls controls-row">
								 
									<form:select class="input-large" id="utility${number}" path="dealSheet.orders[${number}].utility.utility" index="${number}" style="width:100%;margin-top:5px;">
										<c:forEach var="util" items="${utilities}">
			                        	 <option value="${util.utility}" ${util.utility eq 'AEP' ? 'selected' : '' }>${util.utility}</option>
			                        </c:forEach>	
									</form:select>
								 
								</div>
							</div>
						</div>
						
						
						<div class="span3" style="margin-left:3%;width: 19%;" >							 
							<div class="control-group">
								<label class="control-label" for="textfield" style="margin-top: -1px;">Account Number<span id="acc_error${number}" class="error_msg">*</span> </label>
								 <div class="accsize"><span id="acc1_error${number}" class="error_msg"></span></div>
									<div class="controls controls-row">									  
										<form:input type="text" id="account${number}" path="dealSheet.orders[${number }].accountNumber"  class="input-block-level acc" index="${number}" onblur="validateaccountsize(this.id)"  style="width:98%;" />
									
									</div>
							</div>
						</div>
						
						
                    	<div class="span3" style="width:12%;margin-left: 2%;">
                    		 <div style="margin-top: -25px;height: 23px;"><span id="kwh_error${number}" class="error_msg"></span></div>	
							<div class="control-group">
								<label class="control-label" for="textfield" ><span id="svcLbl${number}">kWh</span><span class="chkbox"> (Annual)</span><span class="error_msg">*</span> </label>
								<div class="controls controls-row">
								   <form:input id="kwh${number}" path="dealSheet.orders[${number}].kwh" index="${number}" type="text" class="input-block-level" style="width:100%" />	
								</div>
							</div>
						</div>      
                    
                    
                    
                       <div class="span3" style="width:8%;margin-left: 2%;">  
                            <div style="margin-top: -25px;height: 23px;"><span id="term_error${number}" class="error_msg"></span> </div>                       
							<div class="control-group">
								<label class="control-label" for="textfield">Term <span class="error_msg">*</span> </label>
								<div class="controls controls-row">								
									<form:select class="input-large" id="term${number}" path="dealSheet.orders[${number}].term"  index="${number}"  style="width:100%;">
										  <option value=""selected="selected"></option>
										   <option value="3" >3</option>
										  <option value="6" >6</option>
										   <option value="9" >9</option>
										  <option value="12" >12</option>
					                        <option value="18">18</option>
					                        <option value="24">24</option>
					                      <!-- Added by Jeevan on April 01, 2014 as per Clients Modfications -->
					                        <c:forEach var="termcount" begin="25" end="36" step="1">
					                        	   <option value="${termcount }">${termcount }</option>					                        
					                        </c:forEach>
					                        <!-- Added by Jeevan on April 01, 2014 as per Clients Modfications --> 	
					                        <option value="1">Index </option>
									</form:select>								
								</div>
							</div>
					  </div>
					  <div class="span3" style="width:10%;margin-left: 2%;">
					   <div style="margin-top: -25px;height: 23px;"><span id="rateClass_error${number}" class="error_msg"></span> </div>  
						<div class="control-group">
							<label class="control-label" for="textfield">Rate Class<span class="error_msg"></span></label>
							<div class="controls controls-row">
							     <form:input id="rateClass${number}" path="dealSheet.orders[${number}].rateClass" index="${number}" type="text" class="input-block-level"  style="width:100%"/>	
							</div>
						</div>
					</div>
					  
					  <div class="span3" style="width:11%;margin-left:2%;">
					   <div style="margin-top: -25px;height: 23px;"><span id="rate_error${number}" class="error_msg"></span> </div>  
						<div class="control-group" style="margin-top: -3px;">
							<label class="control-label" for="textfield">Rate<span class="error_msg">*</span>								
							 		<form:checkbox  path="dealSheet.orders[${number }].specialPricing" id="specialpricing${number}" style="margin-top: -1px;" index="${number}" onclick="higlightRate(this.id);"/>
							 		 <span class="sprice" style="font-size: 12px;">Spcl</span>	
							 		 <input type="checkbox" name="rate_chk" value="checkbox"  id="rate_chk${number}" style="margin-left:10px;margin-top: 0px;" index="${number}" onclick="copyRate(this.id);" class="chkbox" />
						      			<span  class="chkbox"  > Prev</span>			 		 					
							</label>
							<div class="controls controls-row">
							<form:input id="rate${number}" path="dealSheet.orders[${number}].rate" index="${number}" type="text" class="input-block-level"  style="width:100%;" />	
							</div>
						</div>
					</div>
					
					 <div class="startdate3">
					  <div style="margin-top: -21px;height: 23px;"><span id="startdate_error${number}" class="error_msg"></span> </div>  
						<div class="control-group" style="margin-top: -3px;">
							<label class="control-label" for="textfield">Start Date<span id="startDate_error${number}" class="error_msg">*</span>
								 <input type="checkbox" name="sdarte_chk" value="checkbox"  id="startDate_chk${number}" style="margin-left:10px;margin-top:0px;" index="${number}" onclick="copystartDate(this.id);" class="chkbox" />
						      			<span  class="chkbox"  > Prev</span>
							</label>
							<div class="controls controls-row">	
							 <!-- Modified by Jeevan on july 18,2013 from startDate(String) to DealStartDate as Date format is Changed according to Client Request  -->						 
								<form:input id="startDate${number}" path="dealSheet.orders[${number}].dealStartDate" index="${number}"  type="text" readonly="readonly" class="input-block-level datepicker strDate2" style="width:80%;font-size:12px;padding:2px;"  />							  
							<!-- Modification Completed  -->
							</div>
						</div>
					</div>
                                                             
					</div>
                                    
                    <div class="row-fluid" style="margin-left:15px;">
                    	<div class="span3" style="width: 37%;">
							<div class="control-group">
								<label class="control-label" for="textfield">Business Name (on the bill)<span id="bname_error${number}" class="error_msg">*</span> 
									<input type="checkbox" name="bname_chk" value="checkbox"  id="bname_chk${number}" style="margin-left: 2px;margin-top: -2px;" index="${number}" onclick="copybusinessname(this.id);" class="chkbox" />
						      			<span  class="chkbox"  > Same as Previous</span>
				                      
								
								</label>
								<div class="controls controls-row">
								   <form:input id="businessname${number}" path="dealSheet.orders[${number}].businessName"  index="${number}" type="text" class="input-block-level buss" style="width: 100%;" />								
								</div>
							</div>
						</div>
						<div class="span3 dba" style="margin-left: 2%;width: 17%;">
						<div class="control-group">
							<label class="control-label" for="textfield">DBA <span class="chkbox"></span>
							
									<input type="checkbox" name="dba_chk" value="checkbox"  id="dba_chk${number}" style="margin-left: 2px;margin-top: -2px;" index="${number}" onclick="copydba(this.id);"  class="chkbox" />
			      					<span  class="chkbox"  > Same as Previous</span>
							
							 </label>
							<div class="controls controls-row">
									<form:input id="dba${number}" path="dealSheet.orders[${number}].dba"  index="${number}" type="text" class="input-block-level lowfont" style="width: 95%;" />								
							</div>
						</div>
					  </div>
                    
                    
                     <div class="span3" style="margin-left: 1%;width: 11%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">TPV<span id="tpv_error${number}" class="error_msg">*</span> 
								 <input type="checkbox" name="tpv_chk" value="checkbox"  id="tpv_chk${number}" style="margin-left: 2px;margin-top: -2px;" index="${number}" onclick="copyTpv(this.id);" class="chkbox" />
			      					<span class="chkbox" > Previous</span>
	                     		
							</label>
							<div class="controls controls-row">							  
								<form:input id="tpv${number}" path="dealSheet.orders[${number}].tpv" index="${number}" type="text" class="input-block-level tpv"style="width: 88%;" />
								
							</div>
						</div>
					</div> 
					
					
					<div class="span3 county" style="margin-left: 0.5%;width: 14%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">County<span id="county_error${number}" class="error_msg"></span>
								<input type="checkbox" name="county_chk" value="checkbox"  id="county_chk${number}" style="margin-left:10px;margin-top:0px;" index="${number}" onclick="copycounty(this.id);" class="chkbox" />
						      			<span  class="chkbox"  > Prev</span>
							</label>
							<div class="controls controls-row">							 
								<form:input id="county${number}" path="dealSheet.orders[${number}].county" type="text" class="input-block-level " style="width: 88%;" />								
							</div>
						</div>
					</div>     
					
					<div class="span3 meter" style="margin-left: 0%;width: 11%;">
						<div class="control-group" >
							<label class="control-label" for="textfield">Meter Date<span id="meter_error${number}" class="error_msg"></span>
								<input type="checkbox" name="meter_chk" value="checkbox"  id="meter_chk${number}" style="margin-left:10px;margin-top:0px;" index="${number}" onclick="copymeter(this.id);" class="chkbox" />
						      			<span  class="chkbox"  > Prev</span>
							</label>
							<div class="controls controls-row">
							 
								<form:input id="meter${number}" path="dealSheet.orders[${number}].meterReadDate" type="text" class="input-block-level datepicker" style="width: 80%;" />
								
							</div>
						</div>
					</div>     
					
					
					
					                                         
				</div>
		 
		 
		 
						
                                    
                 <div class="row-fluid " style="margin-left:15px;">
                    <div class="span4 street1" style="width: 37%;">
						<div class="control-group">
							<label class="control-label" for="textfield">Service (Street)<span id="s_street_error${number}" class="error_msg">*</span>
								  <input type="checkbox" name="sadd_chk" value="checkbox"  id="sadd_chk${number}" style="margin-left: 5px;margin-top: -2px;"  index="${number}" onclick="copyserviceaddress(this.id);" class="chkbox" />
			      					<span class="chkbox"  > Same as Previous</span>
			      					
							</label>
							<div class="controls controls-row">							   
								 <form:input id="s_street${number}" path="dealSheet.orders[${number}].serviceStreet" index="${number}"  type="text" class="input-block-level street" style="width: 100%;" />								
							</div>
						</div>
					</div>
					<div class="span2 unit" style="margin-left: 2%;">
						<div class="control-group">
							<label class="control-label add" for="textfield">Unit   <span id="s_unit_error" class="error_msg"></span></label>
							<div class="controls controls-row">							 
								 <form:input type="text" class="input-block-level addr"  id="s_unit${number}" path="dealSheet.orders[${number}].serviceUnit" index="${number}"/>							  
							</div>
						</div>
					</div>
					<div class="span2" style="margin-left: 0%;">
						<div class="control-group">
							<label class="control-label add" for="textfield">City  <span id="s_city_error${number}" class="error_msg">*</span></label>
						<div class="controls controls-row">						 
							  <form:input id="s_city${number}" path="dealSheet.orders[${number}].serviceCity" class="input-block-level addr" index="${number}"  />							
						</div>
					</div>
				 </div>
                 <div class="span2 state2" style="margin-left: 0%;width: 13%">
					<div class="control-group">
						<label class="control-label add" for="textfield">State   <span id="s_state_error${number}" class="error_msg">*</span></label>
						<div class="controls controls-row">						
							  <form:select  class="input-large1 addr" id="s_state${number}" path="dealSheet.orders[${number}].serviceState.state" index="${number}" style="width:78%;" >
								
								
								
								<c:forEach var="state" items="${states}">
									<option value="${state.state}"  ${state.state eq 'OH' ? 'selected' : ''}  >${state.state}</option>
								</c:forEach>							
	                      </form:select>	                      
					</div>
				 </div>
				</div>         
                <div class="span2" style="margin-left: 0%;">
					<div class="control-group">
						<label class="control-label add" for="textfield">Zip <span id="s_zip_error${number}" class="error_msg">*</span></label>
						<div class="controls controls-row">
						     <spring:bind path="dealSheet.orders[0].serviceZip">
							 <form:input id="s_zip${number}" path="dealSheet.orders[${number}].serviceZip" type="text" class="input-block-level addr1" index="${number}" />
						 </spring:bind>
						</div>
					</div>
				</div>                                 
			</div>
                     
            <div class="row-fluid " style="margin-left:15px;">
                    <div class="span4 street1" style="width: 37%;">
						<div class="control-group">
							<label class="control-label" for="textfield">Billing (Street)<span id="b_street_error${number}" class="error_msg">*</span> &nbsp;
								  <input type="checkbox" name="prevadd_chk" value="checkbox"  id="prevadd_chk${number}" style="margin-left: 0px;margin-top: -2px;" onclick="copybillingaddress(this.id);" index="${number}"  class="chkbox" />
					    		  <span class="chkbox" > Same as Previous</span>			      
			            		 <input type="checkbox" name="add_chk" value="checkbox"  id="add_chk${number}" style="margin-left: 4px;margin-top: -2px;" onclick="copyadd(this.id);" index="${number}" class="chkbox"   />
					    		  <span class="chkbox"  > Same as Service</span>
					    		  
							 </label>
							 
							<div class="controls controls-row">					  
								 <form:input id="b_street${number}" path="dealSheet.orders[${number}].billingStreet"  class="input-block-level street" index="${number}" style="width: 100%;" />						
							</div>
						</div>
					</div>
					<div class="span2 unit" style="margin-left: 2%;">
						<div class="control-group">
							<label class="control-label add" for="textfield">Unit   <span id="b_unit_error" class="error_msg"></span></label>
							<div class="controls controls-row">							 
								 <form:input type="text" class="input-block-level addr"  id="b_unit${number}" path="dealSheet.orders[${number}].billingUnit" index="${number}"/>							  
							</div>
						</div>
					</div>
					<div class="span2" style="margin-left: 0%;">
						<div class="control-group">
							<label class="control-label add" for="textfield">City  <span id="b_city_error${number}" class="error_msg">*</span></label>
						<div class="controls controls-row">						 
							  <form:input id="b_city${number}" path="dealSheet.orders[${number}].billingCity" class="input-block-level addr" index="${number}"  />							
						</div>
					</div>
				 </div>
                 <div class="span2 state2" style="margin-left: 0%;width: 13%">
					<div class="control-group">
						<label class="control-label add" for="textfield">State   <span id="b_state_error${number}" class="error_msg">*</span></label>
						<div class="controls controls-row">						
							  <form:select  class="input-large1 addr" id="b_state${number}" path="dealSheet.orders[${number}].billingState.state" index="${number}" style="width:78%;" >
									<c:forEach var="state" items="${billingStates}">
									<option value="${state.state}"  ${state.state eq 'OH' ? 'selected' : ''}  >${state.state}</option>
								</c:forEach>
								
	                      </form:select>	                      
					</div>
				 </div>
				</div>         
                <div class="span2" style="margin-left: 0%;">
					<div class="control-group">
						<label class="control-label add" for="textfield">Zip <span id="b_zip_error${number}" class="error_msg">*</span></label>
						<div class="controls controls-row">
						     <spring:bind path="dealSheet.orders[0].billingZip">
							 <form:input id="b_zip${number}" path="dealSheet.orders[${number}].billingZip" type="text" class="input-block-level addr1" index="${number}" />
						 </spring:bind>
						</div>
					</div>
				</div>                                 
			</div>                                      
   </div>  