<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<head>
	
</head>


			<table width="96%" cellspacing="0" cellpadding="0" style="width:1200px;">
			<tr>
				<td><h4 style="font-size:16px; text-decoration: underline;">Account #${number+1}</h3></td>
				
			</tr>
	         <tr>
	         	<td width="25%">
	           	  <ul>
	                    <li id="li_util${number}">
	                    	<label class="description" for="label14">Utility <span id="utiltiy${number}_error" class="error_msg">*</span></label>
	                    	<div>
	                    	  
	                      		<form:select class="element select medium" style="padding:4px 0;" id="utility${number}" path="dealSheet.orders[${number}].utility.utility" index="${number}" onfocus="focusUtil(this.id);" onfocusout="removeUtil(this.id);">
			                        
			                        <c:forEach var="util" items="${utilities}">
			                        	 <option value="${util.utility}" selected="selected">${util.utility}</option>
			                        </c:forEach>			                       
	                      		</form:select>
	                      	 
	                       </div>
	                   </li>
	              </ul> 
	         </td>
	         <td>
	    		<ul>
	            	<li id="li_acc${number}">
	                    <label class="description" for="label11">Account Number <span id="acc_error${number}" class="error_msg">*</span></label>
	                    <div>
	                        
	                    	 <form:input id="account${number}" path="dealSheet.orders[${number }].accountNumber" class="element text large" maxlength="255" type="text" index="${number}"  onfocusout="validateaccountsize(this.id);" onfocus="focusAcc(this.id);"  />
	                     
	                    </div>
	                 </li>
	           </ul>
	       </td>
	       <td width="25%">
                <ul>
	           		<li id="li_rc${number}">
	                    <label class="description" for="label14">Rate Class  <span id="rateclass_error${number}" class="error_msg"></span> </label>
	                    <div>	                    
	                      	<form:select class="element select large" style="padding:4px 0;" id="rateclass${number}" path="dealSheet.orders[${number}].rateClass" index="${number}" onfocus="focusRC(this.id);" onfocusout="removeRC(this.id);" >
		                       <option value="gs1" selected="selected">GS1</option>
		                        <option value="gs2">GS2</option>
		                        <option value="gs3">GS3</option>
	                       </form:select>	                     
	                    </div>
	               </li>
	          </ul>
             </td>
             <td>
	              <li id="li_rate${number}">
	              	<label class="description" for="label8">Rate  <span id="rate_error${number}" class="error_msg">*</span></label>
	                    <div>
	                        
	                    	  	<form:input id="rate${number}" path="dealSheet.orders[${number}].rate" class="element text medium" maxlength="255" type="text"  value="" index="${number}" onfocus="focusRate(this.id);" onfocusout="removeRate(this.id);"/>
	                      
	                    </div>
	             </li>  
	        </td>
	       
	               
               <%--  <td width="25%">
	            	<li id="li_2">
	                    <label class="description" for="label7">TPV  <span id="tpv_error" class="error_msg"></span></label>
	                    <div>
	                      <spring:bind path="dealSheet.orders[0].tpv">
	                    	<input id="tpv" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                    	 </spring:bind>
	                    </div>
	                </li>  
               </td> --%>
                
	       </tr>
	              
	       <tr>
	           
	          
	        <td colspan="4" id="test">
	        	<ul class="smallul">
	            	<li id="li_term${number}">
	                    <label class="description" for="label13">Term  <span id="term_error${number}" class="error_msg">*</span></label>
	                    <div>
	                       
	                      	<form:select class="element select medium" style="padding:4px 0;" id="term${number}" path="dealSheet.orders[${number}].term"  index="${number}" onfocus="focusTerm(this.id);" onfocusout="removeTerm(this.id);">
		                        
		                        <option value="12" selected="selected">12</option>
		                        <option value="18">18</option>
		                        <option value="24">24</option>
		                        <option value="30">30</option>
		                        <option value="36">36</option>
	                       </form:select>	                      
	                    </div>
	                </li>
	           </ul> 
	      <!--  </td>
	       <td> -->
	       	<ul class="smallul">
	            <li id="li_kwh${number}">
	                    <label class="description" for="label12">KWH  <span id="kwh_error${number}" class="error_msg">*</span></label>
	                    <div>	                       
	                      	<form:input id="kwh${number}" path="dealSheet.orders[${number}].kwh" class="element text medium" maxlength="255" type="text"  value="" index="${number}" onfocus="focusKWH(this.id);" onfocusout="removeKWH(this.id);"/>
	                     </div>
	             </li>
	        </ul> 
	    <!--   </td>
	       <td> -->
	       	<ul >
	            <li id="li_tpv${number}">
	                    <label class="description" for="label12">TPV Number 
	                     <input type="checkbox" name="tpv_chk" value="checkbox"  id="tpv_chk${number}" style="margin-left: 2px;" index="${number}" onclick="copyTpv(this.id);" />
			      			<span style="font-family:Arial; font-size:8px;float:none" > Same as Previous</span>
	                      <span id="tpv_error${number}" class="error_msg">*</span></label>
	                    <div>	                       
	                      	<form:input id="tpv${number}" path="dealSheet.orders[${number}].tpv" class="element text medium" maxlength="255" type="text"  value="" index="${number}" onfocus="focusTPV(this.id);" onfocusout="removeTPV(this.id);"/>
	                     </div>
	             </li>
	        </ul> 
	      <!-- </td>
	        <td> -->
	       	  <ul class="bigul" style="margin-left: 12px;" >
	          	   <li id="li_bname${number}">
	                    <label class="description" for="label9">Business Name 
	                       <input type="checkbox" name="bname_chk" value="checkbox"  id="bname_chk${number}" style="margin-left: 2px;" index="${number}" onclick="copybusinessname(this.id);" />
			      			<span style="font-family:Arial; font-size:8px;float:none" > Same as Previous</span>
	                      <span id="bname_error${number}" class="error_msg">*</span> </label>
	                     <div>	                        
	                     	 <form:input id="businessname${number}" path="dealSheet.orders[${number}].businessName" class="element text medium" maxlength="255" type="text" index="${number}" onfocus="focusBname(this.id);" onfocusout="removeBname(this.id);"/>
	                     </div>
	               </li>
	         </ul>
	     <!--  </td>
	      <td> -->
	      	<ul class="bigul" style="margin-left: -12px;">
	     		<li id="li_dba${number}"  style="padding-left: 16px;">
	           		<label class="description" for="label10">DBA (If Applicable)
	           		   <input type="checkbox" name="dba_chk" value="checkbox"  id="dba_chk${number}" style="margin-left: 2px;" index="${number}" onclick="copydba(this.id);" />
			      			<span style="font-family:Arial; font-size:8px;float:none" > Same as Previous</span>
	           		</label>
	           		<div>	           		  
	               		<form:input id="dba${number}" path="dealSheet.orders[${number}].dba" class="element text medium" maxlength="255" type="text" index="${number}" onfocus="focusDBA(this.id);" onfocusout="removeDBA(this.id);" />
	           		 </div>
	       		</li>
	   		</ul> 
          </td>
          
	   </tr>
	    <tr>
	    	
	       
	      <td>&nbsp;</td>
	    </tr>
	    <tr>
	       <td><label class="form_description" for="label13"><strong style="font-size:14px; font-weight:700;">Service Address</strong>
	         <input type="checkbox" name="sadd_chk" value="checkbox"  id="sadd_chk${number}" style="margin-left: 20px;"  index="${number}" onclick="copyserviceaddress(this.id);"/>
			      			<span style="font-family:Arial, Helvetica, sans-serif; font-size:10px;float:none" > Same as Previous</span>
	       </label></td>
	       <td>&nbsp;</td>
	        <td>&nbsp;</td>
	    </tr>
	    <tr >
	       <td  >
	    	   <li id="li_sstreet${number}">
	           		<label class="description" for="label13">Street  <span id="s_street_error${number}" class="error_msg">*</span></label>
	                <div>	                    
	                       <form:input id="s_street${number}" path="dealSheet.orders[${number}].serviceStreet" class="element text medium" maxlength="255" type="text" index="${number}" onfocus="focusSstreet(this.id);" onfocusout="removeSstreet(this.id);"  />
	                  </div>
	            </li>
	        </td>
	        <%-- <td>
	        	<ul>
	                <li>
	                    <label class="description" for="label13">Unit  <span id="s_unit_error" class="error_msg"></span></label>
	                    <div>
	                       <spring:bind path="dealSheet.orders[0].serviceUnit">
	                      	<input id="s_unit" name="${status.expression}" class="element text medium" maxlength="255" type="text"  />
	                      	</spring:bind>
	                    </div>
	                </li>
	            </ul>
	      </td> --%>
	      <td>
	      	<ul>
	           <li id="li_scity${number}">
	               <label class="description" for="label13">City  <span id="s_city_error${number}" class="error_msg"></span>*</label>
	               <div>	                  
	                   <form:input id="s_city${number}" path="dealSheet.orders[${number}].serviceCity" class="element text medium" maxlength="255" type="text" index="${number}"  onfocus="focusScity(this.id);" onfocusout="removeScity(this.id);"/>
	                </div>
	           </li>
	        </ul>
	     </td>
	     <td>
	      	<ul>
	           <li id="li_sstate${number}">
	                <label class="description" for="label13">State  <span id="s_state_error${number}" class="error_msg">*</span></label>
	                 <div>
	                 
	                     <form:select class="element select medium" style="padding:4px 0;" id="s_state${number}" path="dealSheet.orders[${number}].serviceState" index="${number}" onfocus="focusSstate(this.id);" onfocusout="removeSstate(this.id);">
		                        
		                        <option value="al">AL</option>
		                        <option value="ak">AK</option>
		                        <option value="ar">AR</option>
		                        <option value="az">AZ</option>
		                         <option value="ca">CA</option>
		                        <option value="co">CO</option>
		                        <option value="ct">CT</option>
		                        <option value="de">DE</option>
		                        <option value="il" selected="selected">IL</option>
		                        
		                        <option value="nj">NJ</option>
		                        <option value="oh">OH</option>
		                        <option value="pa">PA</option>
	                      </form:select>
	                      
	                  </div>
	            </li>
	       </ul>
	   </td>
	  		 <td>
	  	<ul>
	       <li id="li_szip${number}">
	           <label class="description" for="label13">Zip  <span id="s_zip_error${number}" class="error_msg">*</span></label>
	           <div>	              
	                <form:input id="s_zip${number}" path="dealSheet.orders[${number}].serviceZip" class="element text medium" maxlength="255" type="text" index="${number}" onfocus="focusSzip(this.id);" onfocusout="removeSzip(this.id);" />
	            </div>
	            
	       </li>
	    </ul>
	 </td>
	   </tr>
	   
	   <tr>
	      
	  
	 <td>&nbsp;</td>
   </tr>
	<tr>
	       <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	  </tr>
	   <tr>
	         <td colspan="2"><label  ><strong style="font-size:14px; font-weight:700;">Billing Address</strong>
	         	 <input type="checkbox" name="prevadd_chk" value="checkbox"  id="prevadd_chk${number}" style="margin-left: 20px;" onclick="copybillingaddress(this.id);" index="${number}" />
			      <span style="font-family:Arial, Helvetica, sans-serif; font-size:10px;" > Same as Previous Address</span>
			      
	             <input type="checkbox" name="add_chk" value="checkbox"  id="add_chk${number}" style="margin-left: 20px;" onclick="copyadd(this.id);" index="${number}" />
			      <span style="font-family:Arial, Helvetica, sans-serif; font-size:10px;" > Same as Service Address</span>
	          </label></td>
	          <td>&nbsp;</td>
	          <td>&nbsp;</td>
      </tr>
	   <tr>
	       <td><ul>
	            <li id="li_bstreet${number}">
	                <label class="description" for="label13">Street  <span id="b_street_error${number}" class="error_msg"></span></label>
	                <div>	                  
	                    <form:input id="b_street${number}" path="dealSheet.orders[${number}].billingStreet" class="element text medium" maxlength="255" type="text" index="${number}" onfocus="focusBstreet(this.id);" onfocusout="removeBstreet(this.id);" />
	                </div>
	            </li>
	       </ul></td>
	        <%-- <td><ul>
	             <li>
	                 <label class="description" for="label13">Unit <span id="b_unit_error" class="error_msg"></span></label>
	                 <div>
	                   <spring:bind path="dealSheet.orders[0].billingUnit">
	                      <input id="b_unit" name="${status.expression}" class="element text medium" maxlength="255" type="text" />
	                   </spring:bind>
	                  </div>
	              </li>
	         </ul></td> --%>
	          <td><ul>
	              <li id="li_bcity${number}">
	                  <label class="description" for="label13">City  <span id="b_city_error${number}" class="error_msg"></span></label>
	                   <div>	                     
	                        <form:input id="b_city${number}" path="dealSheet.orders[${number}].billingCity" class="element text medium" maxlength="255" type="text" index="${number}" onfocus="focusBcity(this.id);"  onfocusout="removeBcity(this.id);"/>
	                    </div>
	                </li>
	           </ul></td>
	           <td><ul>
	                 <li id="li_bstate${number}">
	                     <label class="description" for="label13">State  <span id="b_state_error${number}" class="error_msg"></span></label>
	                      <div>	                        
		                        <form:select class="element select medium" style="padding:4px 0;" id="b_state${number}" path="dealSheet.orders[${number}].billingState" index="${number}" onfocus="focusBstate(this.id);" onfocusout="removeBstate(this.id);" >
		                         
		                          <option value="al">AL</option>
		                        <option value="ak">AK</option>
		                        <option value="ar">AR</option>
		                        <option value="az">AZ</option>
		                         <option value="ca">CA</option>
		                        <option value="co">CO</option>
		                        <option value="ct">CT</option>
		                        <option value="de">DE</option>
		                        <option value="il" selected="selected">IL</option>
		                        
		                        <option value="nj">NJ</option>
		                        <option value="oh">OH</option>
		                        <option value="pa">PA</option>
		                        </form:select>		                      
	                      </div>
	                  </li>
	            </ul></td>
	            <td valign="middle"><ul>
	                <li id="li_bzip${number}">
	                  <label class="description" for="label13">Zip  <span id="b_zip_error${number}" class="error_msg"></span></label>
	                   <div>	                     
	                       <form:input id="b_zip${number}" path="dealSheet.orders[${number}].billingZip" class="element text medium" maxlength="255" type="text" index="${number}" onfocus="focusBzip(this.id);" onfocusout="removeBzip(this.id);"/>
	                    </div>
	                </li>
	            </ul></td>
	      </tr>
	             
	       <tr>	            
	            
	             <td>&nbsp;</td>
	        </tr>
	             
	     </table>