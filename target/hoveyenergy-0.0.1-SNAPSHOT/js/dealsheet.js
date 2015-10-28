var a,b,c,d,e,f,g,h=true;
var cust=true;
var acc=false;
var k,m,n,o,p,q,r,s,t,u,v,w,x,y,z,tp,sd,ter=true;
var acc_strDate=true;
var date;
var dealsize=0;
		var count=0;
		var orderPosition =0;
		$(document).ready(function(){	
			 /*    Handling Special pricing                     */
			         $('#specialpricing').click(function(){
			        	 if($('#specialpricing').is(':checked')){ 
			        		 $('#specialpricing').val(true);
			        	 }
			        	 else{
			        		 $('#specialpricing').val(false);
			        	 }
			         });	
			         
			         dealsize=$('#acc_count').val();
			        
				/* for dynamically adding accounts */ 		
				 		$("#addBtn").click(function() {
				 			
			                if(orderPosition<24){
			                	orderPosition++;
					 			$.get("/hoveyenergy/addorders.do", { fieldId: orderPosition},
					 				function(data){
					 					$("#bottom_link").before(data);	
					 					$("#rate"+orderPosition).val('');
					 					$("#kwh"+orderPosition).val('');
					 					$("#tpv"+orderPosition).val('');
					 					$("#utility"+orderPosition).focus();
					 					
					 					$('#startDate'+orderPosition).datepicker({
					 						showOn: "button",
					 						buttonImage: "../images/calendar.gif",
					 						buttonImageOnly: true,
					 						 dateFormat: 'mm/dd/yy',
					 						 changeYear: true,
					 					     changeMonth: true,
					 					    yearRange: '2012:c+10',
					 					   showButtonPanel: true,
					 					   onSelect:function(dateText,inst){
					 						   date=1;
					 						  date=$(this).datepicker('getDate').getDate();
					 					   },
					 					  onClose: function(dateText, inst) { 
					 				            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
					 				            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
					 				            var curDate = $(this).datepicker("getDate");
					 				            if(null!=curDate  ){
					 				            	
					 				            		 $(this).datepicker('setDate', new Date(year, month,date));
					 				            					 				            	
					 				            }
					 				            else{
					 				            	$(this).datepicker('setDate', new Date(year, month, 1));
					 				            }
					 				           date=1;
					 				        }
					 					  
					 					});
					 					
					 					$('#meter'+orderPosition).datepicker({
					 						showOn: "button",
					 						buttonImage: "../images/calendar.gif",
					 						buttonImageOnly: true,
					 						 dateFormat: 'mm/dd/yy',
					 						 changeYear: true,
					 					     changeMonth: true,
					 					    yearRange: '2012:c+10',
					 					   showButtonPanel: true,
					 					   onSelect:function(dateText,inst){
					 						   date=1;
					 						  date=$(this).datepicker('getDate').getDate();
					 					   },
					 					  onClose: function(dateText, inst) { 
					 				            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
					 				            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
					 				            var curDate = $(this).datepicker("getDate");
					 				            if(null!=curDate  ){
					 				            	
					 				            		 $(this).datepicker('setDate', new Date(year, month,date));
					 				            					 				            	
					 				            }
					 				            else{
					 				            	$(this).datepicker('setDate', new Date(year, month, 1));
					 				            }
					 				           date=1;
					 				        }
					 					  
					 					});
					 					
					 					$('#startDate'+orderPosition).attr('readonly','readonly');
					 					$('#meter'+orderPosition).attr('readonly','readonly');
					 			});
			                }
			                else{
			                	
			                }
				 			
				 		});
				 		
				 		
				 		//added on Aug 16,2013 by Jeevan to enable Edit DealSheets to add Accounts..
				 		$("#editAddBtn").click(function() {
				 			count=$('#acc_count').val();
				 			
			                if(count<25){	                	
					 			$.get("/hoveyenergy/addorders.do", { fieldId: count},
					 				function(data){
					 					$("#bottom_link1").before(data);
					 					$("#rate"+(count-1)).val('');
					 					$("#kwh"+(count-1)).val('');
					 					$("#tpv"+(count-1)).val('');
					 					$("#utility"+(count-1)).focus();
					 					
					 					$('#startDate'+(count-1)).datepicker({
					 						showOn: "button",
					 						buttonImage: "../images/calendar.gif",
					 						buttonImageOnly: true,
					 						 dateFormat: 'mm/dd/yy',
					 						 changeYear: true,
					 					     changeMonth: true,
					 					    yearRange: '2012:c+10',
					 					   showButtonPanel: true,
					 					   onSelect:function(dateText,inst){
					 						   date=1;
					 						  date=$(this).datepicker('getDate').getDate();
					 					   },
					 					  onClose: function(dateText, inst) { 
					 				            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
					 				            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
					 				            var curDate = $(this).datepicker("getDate");
					 				            if(null!=curDate  ){
					 				            	
					 				            		 $(this).datepicker('setDate', new Date(year, month,date));
					 				            					 				            	
					 				            }
					 				            else{
					 				            	$(this).datepicker('setDate', new Date(year, month, 1));
					 				            }
					 				           date=1;
					 				        }
					 					  
					 					});
					 					
					 					$('#startDate'+(count-1)).attr('readonly','readonly');
					 			});
			                }
			                else{
			                	alert('');
			                }
			                count++;
			                $('#acc_count').val(count);
			                $('#editRemoveBtn').css('display','inline-block');
				 			
				 		});
				 		
			
			//swapping between existing and new customers
			$('#cust_check').click(function(){
				if($('#cust_check').is(':checked')){
					$("#overlay_form").fadeIn(1000);
					  positionPopup();					
				}
				else{					
					$("#overlay_form").fadeOut(500);					
					$('#taxid').val('');
					$('#custid').val(0);
					$('#custname').val("");
					$('#custname2').val("");
					$('#custtitle').val("");
					$('#custphone').val("");
					$('#email').val("");
					$('#fax').val("");
					$('#fronter').val('');
					$('#taxexempt').val("na");
					$('#comres').val('commercial');
					$('#service').val('electric');
					$('#cust_check').val("");
					clearSearch();
				}				
			});
			
			
			//Handling Customer Dynamic Search
			$('#resBtn').click(function(){
				var selected=$('[name="cust"]:checked');
				var selectedValue;
				if (selected.length > 0){
				    selectedValue = selected.attr('id');
				    var id=$('#custId'+selectedValue).val();				    
				    getCustomer(id);
				}
				$("#overlay_form").fadeOut(500);
			});
			
			
			
			
			
			//clearing customer search fields and results..
			function clearSearch(){
				$('#sfname').val('');
				$('#slname').val('');
				$('#taxid').val('');
				$('#phone').val('');
				$('#sbname').val('');
				$('.results').remove();
			}
			
			
			// getting customer details based on tax id through AJAX.
			/*
			 * Modified by Jeevan on 20 July,2013 acc to change in Deal Sheet
			 * 
			 * Id will be send in request instead of TaxId
			 */
			function getCustomer(customerId){
				//var taxId=$('#tax').val();
				$.ajax({
					url: "/hoveyenergy/getCustomer.do?id="+customerId,
					type:"GET",
					success:function(data){						
						var obj = jQuery.parseJSON(data);
						$('#custname').val(obj.FIRST_NAME);
						$('#custname2').val(obj.LAST_NAME);
						$('#custtitle').val(obj.TITLE);
						$('#custphone').val(obj.PHONE);
						$('#email').val(obj.EMAIL);
						$('#fax').val(obj.FAX);
						$('#taxexempt').val(obj.TAX);
						$('#custid').val(obj.ID);
						$('#custid1').val(obj.ID);
						$('#comres').val(obj.TYPE);
						$('#service').val(obj.SERVICE);
						$('#taxid').val(obj.TAX_ID);
						$('#fronter').val(obj.FRONTER);
						cust=true;
						}
				});
			}
			
			
			
			//fetching customer details on taxid change
			
			
			
			$('#taxid').focusout(function(){
				validateCustomerwithTaxid();
			});
			
					
			//validarting account
			$('#account').focusout(function(){
				validateAccountNo();
			});
			
			//
			
			
			function validateCustomerwithTaxid(){
				var tax=$('#taxid').val();
				var cid=$('#custid').val();				
						$.ajax({
							url:"/hoveyenergy/validatecustomer.do",
							type:"POST",
							async:false,
							data:{taxid:tax,id:cid},
							success:function(data){
								if(data!="success"){
									$('#custtax_error').html('* '+data);
									cust=false;
									
								}
								else if(data=="success"){
									$('#custtax_error').html('');
									cust=true;
								}
							}
						});
			}			
			
			
			
			
			//Handling Remove Accounts Button, removes account
			 $('#removeAcc').click(function(){
				 if(orderPosition==0){
					 alert("No Account to Remove");
				 }
				 else{
		        	 $('#orderaccount'+orderPosition).remove();
		        	 orderPosition--;
		        	 
				 }
	         });
			
			 
			//Handling Remove Accounts Button, removes account
			 $('#editRemoveBtn').click(function(){
				 if(count<=dealsize){
	        		 $('#editRemoveBtn').css('display','none');
	        	 }
				 else{
					 count--;
		        	 $('#orderaccount'+count).remove();  	 
		        	 
		        	 $('#acc_count').val(count);
				 }
	         });
			
			
			 /*
				 * Created by Jeevan on October 16, 2013 to check if Order with same Acc# exists on that particular Date
				 */
				function checkAccountExistanceforStartDate(i){			
							
					var startDate=$('#startDate'+i).val();
					var account=$('#account'+i).val();
					$.ajax({
						url:"/hoveyenergy/checkaccountexistance.do?account="+account+"&startDate="+startDate,
						type:'GET',
						async:false,
						success:function(data){
								if(data==="success"){
									acc_strDate=true;						
								}		
								else{
									alert("Order Already Exists with Account# "+account+" on Start Date "+startDate);
									acc_strDate=false;				
								}								
						}
					});		
				}
				
			 
				 /*
				 * Created by Jeevan on October 16, 2013 to check if Order with same Acc# exists on that particular Date
				 */
				function checkEditAccountExistanceforStartDate(i){			
							
					var startDate=$('#startDate'+i).val();
					var account=$('#account'+i).val();
					var id=$('#orderId'+i).val();
					$.ajax({
						url:"/hoveyenergy/checkaccountexistanceforedit.do?account="+account+"&startDate="+startDate+"&id="+id,
						type:'GET',
						async:false,
						success:function(data){
								if(data==="success"){
									acc_strDate=true;						
								}		
								else{
									alert("Order Already Exists with Account# "+account+" on Start Date "+startDate);
									acc_strDate=false;				
								}								
						}
					});		
				}
			 
			 
			 //Handling Deal Sheet Submission
			$('#submitBtn').click(function(){
				var res=true;
				//validateCustomerwithTaxid();
				for(var tem=0;tem<=orderPosition;tem++){	
					 validateaccountsize("account"+tem);
					 checkAccountExistanceforStartDate(tem);
				   if(validateForm(tem)==false){
					   res=false;
				   }
				   //validateCustomerwithTaxid();
				   if(acc==false || acc_strDate==false ){
					   res=false;
				   }
				}
				
				if(res==true){
					var x=confirm("Are you sure you want to submit?");					
					if(x==true ){	
						var form=$('#dealsheet');
						var data=form.serialize();
						var $body=$('body');
						
						 $body.addClass('loading'); 
						 $.ajax({
							url:"/hoveyenergy/agent/dealsheet.do",
							data:data,
							type: "POST",	
							 beforeSend: function(){
						            $body.addClass('loading');
						        },
							success: function(data){
								if(data=="success"){
									window.location="/hoveyenergy/agent/dealsheet.do?message=Deal Saved Successfully";									 
								}
								else{									
									$('#acc1_error'+i).html('');									
								//	window.location="/hoveyenergy/agent/dealsheet.do?error=true";
								}
							},
							error:function(data,status){
								if(data.status==501){
									alert(data.responseText);
								}
								else{
									window.location="/hoveyenergy/agent/dealsheeterror.do";
								}
							},
							complete: function(){
					            $body.removeClass('loading');
					        } 
						});		
						
					  return false;
					}
					else{
						return false;
					}
				}
				else{
					return false;
				}				
			});
			
			
			
			//This is for Editing Deal Sheet from Admin
			$('#editBtn').click(function(){
				
				var res=true;
				var count=$('#acc_count').val();
				//validateCustomerwithTaxid();
				for(var tem=0;tem<count;tem++){	
					 validateeditaccountsize("account"+tem);
					 checkEditAccountExistanceforStartDate(tem);
				   if(validateForm(tem)==false){
					   res=false;
				   }  				   
				   if(acc==false || acc_strDate==false ){
					   res=false;
				   }
				}				
				if(res==true){
					var x=confirm("Are you sure you want to submit?");
					
					if(x==true ){						
					  return true;
					}
					else{
						return false;
					}
				}
				else{
					return false;
				}	
				
				
			});
			
			
			
			//Validation of Deal Sheet
			function validateForm(temp){
				
				
				 var filter1= /[^a-zA-Z 0-9]+/g;
				 var filter=/[^a-zA-Z ]+/g;
				 var filter2=/[^0-9]+/g;
				 var taxFilter=/[^0-9xX*]+/g;
				 var filter3=/[^0-9.]+/g;
				 var emailFilter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
				 var nameFilter=/[^a-zA-Z\(\);\:'"\/\\_\-\.\, ]+/;
				
									
					var tax=$('#taxid').val();
					if(tax=="" || tax==null){
						$('#custtax_error').html('*TaxId cannot be Empty');
						a=false;
					}
					else if(tax!="" && tax.length!=9){
						$('#custtax_error').html('*Should have 9 Digits');
						a=false;
					}
					else if(tax!="" && taxFilter.test(tax)){
						$('#custtax_error').html('*Should only have Digits');
						a=false;
					}
					else{
						$('#custtax_error').html('');
						a=true;
					}
					
						
				var cname=$('#custname').val();
				if(cname=="" || cname==null){
					b=false;
					$('#custname_error').html('*Required');
				}
				else if(nameFilter.test(cname)){
					b=false;
					$('#custname_error').html('*Should only have Alphabets');
				}
				else{
					b=true;
					$('#custname_error').html('');
				}
				
				
				
				var cname2=$('#custname2').val();
				if(cname2=="" || cname2==null){
					g=false;
					$('#custname2_error').html('*Required');
				}
				else if(nameFilter.test(cname2)){
					g=false;
					$('#custname2_error').html('*Should only have Alphabets');
				}
				else{
					g=true;
					$('#custname2_error').html('');
				}
				
				
				var ctitle=$('#custtitle').val();
				if(ctitle=="" || ctitle==null){
					c=false;
					$('#custtitle_error').html('*Required');
				}
				else if(nameFilter.test(ctitle)){
					c=false;
					$('#custtitle_error').html('*Should Only have Alphabets');
				}
				else{
					c=true;
					$('#custtitle_error').html('');
				}
				
				
				var cphone=$('#custphone').val();
				if(cphone=="" || cphone==null){
					d=false;
					$('#custphone_error').html('*Required');
				}
				else if(cphone.length!=10){
					d=false;
					$('#custphone_error').html('*Should have 10 Digits');
				}
				else if(cphone==0000000000){
					d=false;
					$('#custphone_error').html('*Not Valid');
				}
				else if(filter2.test(cphone)){
					d=false;
					$('#custphone_error').html('*Not Valid');
				}
				else{
					d=true;
					$('#custphone_error').html('');
				}
				
				
				var cemail=$('#email').val();
				 if(cemail!="" && !emailFilter.test(cemail)){
					e=false;
					$('#custemail_error').html('* Email Address is not Valid');
					
				}
				else{
					e=true;
					$('#custemail_error').html('');
				}
				
				
				
				var cfax=$('#fax').val();
				if(cfax!="" && filter2.test(cfax)){
					f=false;
					$('#fax_error').html('*Not Valid');
				}
				else if(cfax!="" && cfax.length!=10){
					f=false;
					$('#fax_error').html('*Should have 10 Digits');
				}
				else if(cfax!="" && cfax==0000000000){
					f=false;
					$('#fax_error').html('*Not Valid');
				}
				else{
					f=true;
					$('#fax_error').html('');
				}
				
				
			
					    //validating account number
						var oacc=$('#account'+temp).val();
						if(oacc=="" || oacc==null){
							k=false;
							$('#acc_error'+temp).html('*Required');
						}
						else if(filter2.test(oacc)){
							k=false;
							$('#acc_error'+temp).html('*Should only Contain Digits');
						}
						else{
							k=true;
							$('#acc_error'+temp).html('*');
						}
						
						// validating rate.
						var orate=$('#rate'+temp).val();
						if(orate=="" || orate==null){
							l=false;
							$('#rate_error'+temp).html('*Required');
						}
						else if(filter3.test(orate)){
							l=false;
							$('#rate_error'+temp).html('*Should only Contain Digits');
						}
						else{
							l=true;
							$('#rate_error'+temp).html('');
						}
						
						//validating KWH
						var okwh=$('#kwh'+temp).val();
						if(okwh=="" || okwh==null){
							m=false;
							$('#kwh_error'+temp).html('*Required');
						}
						else if(filter2.test(okwh)){
							m=false;
							$('#kwh_error'+temp).html('*Should only Contain Digits');
						}
						else{
							m=true;
							$('#kwh_error'+temp).html('');
						}
						
						
						
						if($('#contract'+temp).is(':checked')){
							tp=true;
							$('#tpv_error'+temp).html('');
						}
						else{
							//validating TPV.
							var otpv=$('#tpv'+temp).val();
							if(otpv=="" || otpv==null){
								tp=false;
								$('#tpv_error'+temp).html('*Required');
							}
							else if(filter2.test(otpv)){
								tp=false;
								$('#tpv_error'+temp).html('* Should only Contain Digits');
							}
							else if(otpv.length>15){
								tp=false;
								$('#tpv_error'+temp).html('*Should not Exceed 15 Digits');
							}
							else{
								tp=true;
								$('#tpv_error'+temp).html('*');
							}
						}
						
					
						
						
						
						var oterm=$('#term'+temp).val();
						if(oterm=="" || oterm==null){
							$('#term_error'+temp).html('*Required');
							ter=false;
						}
						else{
							$('#term_error'+temp).html('');
							ter=true;
						}
						
						
						var sdate=$('#startDate'+temp).val();
						if(sdate=="" || sdate==null){
							sd=false;
							$('#startdate_error'+temp).html('* Required');
						}
						else{
							sd=true;
							$('#startdate_error'+temp).html('');
						}
						
						
						//validating business name
						var obname=$('#businessname'+temp).val();
						if(obname=="" || obname==null){
							n=false;
							$('#bname_error'+temp).html('* Required');
						}
						/*else if(filter.test(obname)){
							n=false;
							$('#bname_error'+temp).html('* Should only have Alphabets');
						}*/
						else{
							n=true;
							$($('#bname_error'+temp)).html('*');
						}
						
						
						var osstreet=$('#s_street'+temp).val();
						if(osstreet=="" || osstreet==null){
							o=false;
							$('#s_street_error'+temp).html('*Required');
						}						
						else{
							o=true;
							$($('#s_street_error'+temp)).html('*');
						}
						
						
						
						var oscity=$('#s_city'+temp).val();
						if(oscity=="" || oscity==null){
							p=false;
							$('#s_city_error'+temp).html('*Required');
						}
						else if(filter.test(oscity)){
							p=false;
							$('#s_city_error'+temp).html('*Should only have Alphabets');
						}
						else{
							p=true;
							$($('#s_city_error'+temp)).html('*');
						}
						
						
						var oszip=$('#s_zip'+temp).val();
						if(oszip=="" || oszip==null){
							q=false;
							$('#s_zip_error'+temp).html('*Required');
						}
						else if(filter2.test(oszip)){
							q=false;
							$('#s_zip_error'+temp).html('*Should only Contain Digits');
						}
						else if(oszip.length!=5){
							q=false;
							$('#s_zip_error'+temp).html('*Should be 0f 5 Digits');
						}
						else{
							q=true;
							$('#s_zip_error'+temp).html('*');
						}
						
						
						var obstreet=$('#b_street'+temp).val();
						if(obstreet=="" || obstreet==null){
							r=false;
							$('#b_street_error'+temp).html('*Required');
						}						
						else{
							r=true;
							$($('#b_street_error'+temp)).html('*');
						}
						
						
						
						var obcity=$('#b_city'+temp).val();
						if(obcity=="" || obcity==null){
							s=false;
							$('#b_city_error'+temp).html('*Required');
						}
						else if(filter.test(obcity)){
							s=false;
							$('#b_city_error'+temp).html('*Should Only have Alphabets');
						}
						else{
							s=true;
							$($('#b_city_error'+temp)).html('*');
						}
						
						
						var obzip=$('#b_zip'+temp).val();
						if(obzip=="" || obzip==null){
							t=false;
							$('#b_zip_error'+temp).html('*Required');
						}
						else if(filter2.test(obzip)){
							t=false;
							$('#b_zip_error'+temp).html('*Should only Contain Digits');
						}
						else if(obzip.length!=5){
							t=false;
							$('#b_zip_error'+temp).html('*Should be of 5 Digits');
						}
						else{
							t=true;
							$('#b_zip_error'+temp).html('*');
						}
						
						
						
						
						if(acc==true && cust==true && a==true && b==true && c==true && d==true && e==true && f==true && g==true && h==true  && k==true && l==true && m==true && n==true && o==true && p==true && q==true && r==true && s==true && t==true && tp==true && sd==true && ter==true){
							return true;
						}
						else if(acc==false){
							
							return false;
						}
						
						else if(cust==false){
							$('#custtax_error').html('Customer Already Registered With TaxId');
							return false;
						}
						else if(k==false){
							$('#acc_error'+temp).html('*Should only Contain Digits');
							return false;
						}
						
						else{
							return false;
						}				
			   }
			
			
			
			
			
			
			

			//Validation of Deal Sheet
			function validateDealEditForm(temp){
				
				
				 var filter1= /[^a-zA-Z 0-9]+/g;
				 var filter=/[^a-zA-Z ]+/g;
				 var filter2=/[^0-9]+/g;
				 var taxFilter=/[^0-9a-zA-Z]+/g;
				 var filter3=/[^0-9.]+/g;
				 var emailFilter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
				 var nameFilter=/[^a-zA-Z\(\);\:'"\/\\_\-\.\, ]+/;
				 
				 var aa,ab,ac,ad,ae,af,ag,ah,aj,ak,am,an=true;
				 			
				
				/*for(var temp=0;temp<=orderPosition;temp++){*/
					   
					    //validating account number
						var oacc=$('#account'+temp).val();
						if(oacc=="" || oacc==null){
							aa=false;
							$('#acc_error'+temp).html('*Required');
						}
						else if(filter2.test(oacc)){
							aa=false;
							$('#acc_error'+temp).html('*Should only Contain Digits');
						}
						else{
							aa=true;
							$('#acc_error'+temp).html('*');
						}
						
						// validating rate.
						var orate=$('#rate'+temp).val();
						if(orate=="" || orate==null){
							ab=false;
							$('#rate_error'+temp).html('*Required');
						}
						else if(filter3.test(orate)){
							ab=false;
							$('#rate_error'+temp).html('*Should only Contain Digits');
						}
						else{
							ab=true;
							$('#rate_error'+temp).html('');
						}
						
						//validating KWH
						var okwh=$('#kwh'+temp).val();
						if(okwh=="" || okwh==null){
							ac=false;
							$('#kwh_error'+temp).html('*Required');
						}
						else if(filter2.test(okwh)){
							ac=false;
							$('#kwh_error'+temp).html('*Should only Contain Digits');
						}
						else{
							ac=true;
							$('#kwh_error'+temp).html('');
						}
						
						
						
						
						if($('#contract'+temp).is(':checked')){
							ad=true;
							$('#tpv_error'+temp).html('');
						}
						else{
							//validating TPV.
							var otpv=$('#tpv'+temp).val();
							if(otpv=="" || otpv==null){
								ad=false;
								$('#tpv_error'+temp).html('*Required');
							}
							else if(filter2.test(otpv)){
								ad=false;
								$('#tpv_error'+temp).html('* Should only Contain Digits');
							}
							else if(otpv.length>15){
								ad=false;
								$('#tpv_error'+temp).html('*Should not Exceed 15 Digits');
							}
							else{
								ad=true;
								$('#tpv_error'+temp).html('*');
							}
						}
						
						
						
						
						var oterm=$('#term'+temp).val();
						if(oterm=="" || oterm==null){
							$('#term_error'+temp).html('*Required');
							ae=false;
						}
						else{
							$('#term_error'+temp).html('');
							ae=true;
						}
						
						
						var sdate=$('#startDate'+temp).val();
						if(sdate=="" || sdate==null){
							af=false;
							$('#startdate_error'+temp).html('* Required');
						}
						else{
							af=true;
							$('#startdate_error'+temp).html('');
						}
						
						
						//validating business name
						var obname=$('#businessname'+temp).val();
						if(obname=="" || obname==null){
							ag=false;
							$('#bname_error'+temp).html('* Required');
						}
						/*else if(filter.test(obname)){
							n=false;
							$('#bname_error'+temp).html('* Should only have Alphabets');
						}*/
						else{
							ag=true;
							$($('#bname_error'+temp)).html('*');
						}
						
						
						var osstreet=$('#s_street'+temp).val();
						if(osstreet=="" || osstreet==null){
							ah=false;
							$('#s_street_error'+temp).html('*Required');
						}						
						else{
							ah=true;
							$($('#s_street_error'+temp)).html('*');
						}
						
						
						
						var oscity=$('#s_city'+temp).val();
						if(oscity=="" || oscity==null){
							ai=false;
							$('#s_city_error'+temp).html('*Required');
						}
						else if(filter.test(oscity)){
							ai=false;
							$('#s_city_error'+temp).html('*Should only have Alphabets');
						}
						else{
							ai=true;
							$($('#s_city_error'+temp)).html('*');
						}
						
						
						var oszip=$('#s_zip'+temp).val();
						if(oszip=="" || oszip==null){
							aj=false;
							$('#s_zip_error'+temp).html('*Required');
						}
						else if(filter2.test(oszip)){
							aj=false;
							$('#s_zip_error'+temp).html('*Should only Contain Digits');
						}
						else if(oszip.length!=5){
							aj=false;
							$('#s_zip_error'+temp).html('*Should be 0f 5 Digits');
						}
						else{
							aj=true;
							$('#s_zip_error'+temp).html('*');
						}
						
						
						var obstreet=$('#b_street'+temp).val();
						if(obstreet=="" || obstreet==null){
							ak=false;
							$('#b_street_error'+temp).html('*Required');
						}						
						else{
							ak=true;
							$($('#b_street_error'+temp)).html('*');
						}
						
						
						
						var obcity=$('#b_city'+temp).val();
						if(obcity=="" || obcity==null){
							am=false;
							$('#b_city_error'+temp).html('*Required');
						}
						else if(filter.test(obcity)){
							am=false;
							$('#b_city_error'+temp).html('*Should Only have Alphabets');
						}
						else{
							am=true;
							$($('#b_city_error'+temp)).html('*');
						}
						
						
						var obzip=$('#b_zip'+temp).val();
						if(obzip=="" || obzip==null){
							an=false;
							$('#b_zip_error'+temp).html('*Required');
						}
						else if(filter2.test(obzip)){
							an=false;
							$('#b_zip_error'+temp).html('*Should only Contain Digits');
						}
						else if(obzip.length!=5){
							an=false;
							$('#b_zip_error'+temp).html('*Should be of 5 Digits');
						}
						else{
							an=true;
							$('#b_zip_error'+temp).html('*');
						}
						
						
						
						
						if(acc==true && aa==true && ab==true && ac==true && ad==true && ae==true && af==true && ag==true && ah==true && ai==true && aj==true && ak==true  && am==true && an==true ){
							return true;
						}
						else if(acc==false){
							
							return false;
						}
						
						else if(cust==false){
							$('#custtax_error').html('Customer Already Registered With TaxId');
							return false;
						}
						else if(k==false){
							$('#acc_error'+temp).html('*Should only Contain Digits');
							return false;
						}
						
						else{
							return false;
						}			
				
				
			    }
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			$('#clearBtn').click(function(){
				var clr=confirm("Are you sure you want to clear all fields?");
				if(clr==true){
				window.location="/hoveyenergy/agent/dealsheet.do"
				/*
					for(var tem=0;tem<=orderPosition;tem++){
						clearFields(tem);
					}
					*/
				}
			});
			
			
			function clearFields(id){	
				
				
					 if($('#cust_check').is(':checked')){
						 $('#cust_check').click();
					 }
					 else{
						 $('#custname').val('');
						 $('#custname2').val('');
						 $('#custtitle').val('');
						 $('#custphone').val('');
						 $('#email').val('');
						 $('#fax').val('');
						 $('#taxid').val('');
						 $('#taxexempt').val('na');
						 $('#comres').val('commercial');
						 $('#service').val('electric');
					 }
					 
					 $('#businessname'+id).val('');
					 $('#dba'+id).val('');
					 $('#account'+id).val('');
					 $('#kwh'+id).val('');
					 $('#rateClass'+id).val('');
					 $('#rate'+id).val('');
					 $('#tpv'+id).val('');
					 $('#s_street'+id).val('');
					 $('#s_unit'+id).val('');
					 $('#s_city'+id).val('');
					 $('#s_zip'+id).val('');
					 $('#b_street'+id).val('');
					 $('#b_unit'+id).val('');
					 $('#b_city'+id).val('');
					 $('#b_zip'+id).val('');
					 $('#agentNotes').val('');
					 $('#specialprice').attr('checked',false);
					 $('input:checkbox').attr('checked',false);						
				//				
				}		 
			
	});
	//end of document.ready	
		
		
		//for copying billing address from service address		
		function copyadd(id){
			var i=$('#'+id).attr('index');
			if($('#prevadd_chk'+i).is(':checked')){				
				$('#prevadd_chk'+i).attr('checked', false);
			}			
			if($('#'+id).is(':checked')){	
				
				$('#b_street'+i).val($('#s_street'+i).val());
				$('#b_unit'+i).val($('#s_unit'+i).val());
				$('#b_city'+i).val($('#s_city'+i).val());
				$('#b_state'+i).val($('#s_state'+i).val());
				$('#b_zip'+i).val($('#s_zip'+i).val());
				
			}
			else{
				
				$('#b_street'+i).val("");
				$('#b_unit'+i).val("");
				$('#b_city'+i).val("");
				$('#b_state'+i).val("");
				$('#b_unit'+i).val("");
				$('#b_zip'+i).val("");				
			}
		}
		
		
		//copying businessname		
		function copybusinessname(id){
			var i=$('#'+id).attr('index');
			var j=i-1;
			if($('#'+id).is(':checked')){	
				 $('#businessname'+i).val($('#businessname'+j).val());				
			}
			else{			
				 $('#businessname'+i).val("");
			}			
		}
		
		//copying dba
		function copydba(id){
			var i=$('#'+id).attr('index');
			var j=i-1;
			if($('#'+id).is(':checked')){
				$('#dba'+i).val($('#dba'+j).val());				
			}
			else{				
				$('#dba'+i).val("");				
			}
		}	
		
		function copyTpv(id){
			var i=$('#'+id).attr('index');
			var j=i-1;
			if($('#'+id).is(':checked')){
				$('#tpv'+i).val($('#tpv'+j).val());				
			}
			else{				
				$('#tpv'+i).val("");				
			}
		}

		function copystartDate(id){
			var i=$('#'+id).attr('index');
			var j=i-1;
			if($('#'+id).is(':checked')){
				$('#startDate'+i).val($('#startDate'+j).val());				
			}
			else{				
				$('#startDate'+i).val("");				
			}
		}
		
		function copycounty(id){
			var i=$('#'+id).attr('index');
			var j=i-1;
			if($('#'+id).is(':checked')){
				$('#county'+i).val($('#county'+j).val());				
			}
			else{				
				$('#county'+i).val("");				
			}
		}
		
		
		function copymeter(id){
			var i=$('#'+id).attr('index');
			var j=i-1;
			if($('#'+id).is(':checked')){
				$('#meter'+i).val($('#meter'+j).val());				
			}
			else{				
				$('#meter'+i).val("");				
			}
		}
		
		
		function copyRate(id){
			var i=$('#'+id).attr('index');
			var j=i-1;
			if($('#'+id).is(':checked')){
				$('#rate'+i).val($('#rate'+j).val());				
			}
			else{				
				$('#rate'+i).val("");				
			}
		}
		
		
		//copying serviceaddress as previous
		function copyserviceaddress(id){
			var i=$('#'+id).attr('index');
			var j=i-1;
			if($('#'+id).is(':checked')){
				$('#s_street'+i).val($('#s_street'+j).val());
				$('#s_city'+i).val($('#s_city'+j).val());
				$('#s_state'+i).val($('#s_state'+j).val());
				$('#s_unit'+i).val($('#s_unit'+j).val());
				$('#s_zip'+i).val($('#s_zip'+j).val());				
			}
			else{			
				$('#s_street'+i).val("");
				$('#s_city'+i).val("");
				$('#s_state'+i).val("");
				$('#s_zip'+i).val("");	
				$('#s_unit'+i).val("");
				
			}
		}
		
		
		//copies billing address as previous...
		function copybillingaddress(id){
			var i=$('#'+id).attr('index');
			var j=i-1;
			if($('#add_chk'+i).is(':checked')){				
				$('#add_chk'+i).attr('checked', false);

			}
			if($('#'+id).is(':checked')){
				$('#b_street'+i).val($('#b_street'+j).val());
				$('#b_city'+i).val($('#b_city'+j).val());
				$('#b_state'+i).val($('#b_state'+j).val());
				$('#b_unit'+i).val($('#b_unit'+j).val());
				$('#b_zip'+i).val($('#b_zip'+j).val());					
			}
			else{			
				$('#b_street'+i).val("");
				$('#b_city'+i).val("");
				$('#b_state'+i).val("");
				$('#b_zip'+i).val("");
				$('#b_unit'+i).val("");
			}
		}
		
		
		//validating account size
		function validateaccountsize(id){			
			var i=$('#'+id).attr('index');			
				var util1=$('#utility'+i).val();
				var acc1=$('#account'+i).val();
				var orderDate=$('#orderDate').val();
				$.ajax({
					url:"/hoveyenergy/validateaccountno.do?acc="+acc1+"&util="+util1+"&orderDate="+orderDate,
					type: "GET",	
					async:false,
					success: function(data){
						if(data!="success"){							
							$('#acc1_error'+i).html('* '+data);
							acc=false;							
						}
						else{						
						//	getOrderByAccount(acc1,i);		  to disable automatic order types..					
							$('#acc1_error'+i).html('');
							acc=true;
						}
					}
				});	
				
				var i=$('#'+id).attr('index');	
				$('#li_acc'+i).removeClass('highlighted');
		}
		
		
		//for editing accounts
		function validateeditaccountsize(id){					
					var i=$('#'+id).attr('index');			
						var util1=$('#utility'+i).val();
						var acc1=$('#account'+i).val();
						var id=$('#orderId'+i).val();
						var orderDate=$('#orderDate').val();
						$.ajax({
							url:"/hoveyenergy/validateeditaccountno.do?acc="+acc1+"&util="+util1+"&id="+id+"&orderDate="+orderDate,
							type: "GET",	
							async:false,
							success: function(data){
								if(data!="success"){
									$('#acc1_error'+i).html('* '+data);
									acc=false;
								}
								else{									
									$('#acc1_error'+i).html('');
									acc=true;
								}
							}
						});		
						//getOrderByAccount(acc1,i);
						var i=$('#'+id).attr('index');	
						$('#li_acc'+i).removeClass('highlighted');
		}
		
		
		
		//for getting OrderId for renewal accounts based on Account#.
		function getOrderByAccount(acc1,i){
			$.ajax({
				url:"/hoveyenergy/getorderid.do?acc="+acc1,
				type:'GET',
				success:function(data){
					if(null!=data && data>0){
					 /* Modified on 20 July 2013 as per client needs, Renewals should not replace existing Orders */				
						$('#ctype'+i).val("renewal");					
					}
					else{					
						$('#ctype'+i).val("new");						
					}					
				}
			});
		}
		
		
		function changecontract(id){
			var i=$('#'+id).attr('index');			
			if($('#contract'+i).is(':checked')){
				alert("Contract type is changed to Renewal");
				$('#ctype'+i).val('renewal');
			}
			else{
				alert("Contract type is changed to New");
				$('#ctype'+i).val('new');
			}
		}
		
		
		
		// for focussing all the appended elements.................
		function test1(){
			document.getElementById("li_test").className = "highlighted";
		}
		
		function focusUtil(id){
			var i=$('#'+id).attr('index');	
			$('#li_util'+i).addClass('highlighted');
		}
		
		function removeUtil(id){
			var i=$('#'+id).attr('index');	
			$('#li_util'+i).removeClass('highlighted');
			
		}
		
		function focusAcc(id){
			var i=$('#'+id).attr('index');	
			$('#li_acc'+i).addClass('highlighted');
		}
		
		function focusRC(id){
			var i=$('#'+id).attr('index');	
			$('#li_rc'+i).addClass('highlighted');
		}
		
		function removeRC(id){
			var i=$('#'+id).attr('index');	
			$('#li_rc'+i).removeClass('highlighted');
			
		}
		
		function focusRate(id){
			var i=$('#'+id).attr('index');	
			$('#li_rate'+i).addClass('highlighted');
		}
		
		function removeRate(id){
			var i=$('#'+id).attr('index');	
			$('#li_rate'+i).removeClass('highlighted');
			
		}
		
		function focusTerm(id){
			var i=$('#'+id).attr('index');	
			$('#li_term'+i).addClass('highlighted');
		}
		
		function removeTerm(id){
			var i=$('#'+id).attr('index');	
			$('#li_term'+i).removeClass('highlighted');
			
		}
		
		
		function focusKWH(id){
			var i=$('#'+id).attr('index');	
			$('#li_kwh'+i).addClass('highlighted');
		}
		
		function removeKWH(id){
			var i=$('#'+id).attr('index');	
			$('#li_kwh'+i).removeClass('highlighted');
			
		}
		
		function focusBname(id){
			var i=$('#'+id).attr('index');	
			$('#li_bname'+i).addClass('highlighted');
		}
		
		function removeBname(id){
			var i=$('#'+id).attr('index');	
			$('#li_bname'+i).removeClass('highlighted');
			
		}
		
		function focusDBA(id){
			var i=$('#'+id).attr('index');	
			$('#li_dba'+i).addClass('highlighted');
		}
		
		function removeDBA(id){
			var i=$('#'+id).attr('index');	
			$('#li_dba'+i).removeClass('highlighted');
			
		}
		
		function focusSstreet(id){
			var i=$('#'+id).attr('index');	
			$('#li_sstreet'+i).addClass('highlighted');
		}
		
		function removeSstreet(id){
			var i=$('#'+id).attr('index');	
			$('#li_sstreet'+i).removeClass('highlighted');
			
		}
		
		function focusScity(id){
			var i=$('#'+id).attr('index');	
			$('#li_scity'+i).addClass('highlighted');
		}
		
		function removeScity(id){
			var i=$('#'+id).attr('index');	
			$('#li_scity'+i).removeClass('highlighted');
			
		}
		
		function focusSstate(id){
			var i=$('#'+id).attr('index');	
			$('#li_sstate'+i).addClass('highlighted');
		}
		
		function removeSstate(id){
			var i=$('#'+id).attr('index');	
			$('#li_sstate'+i).removeClass('highlighted');
			
		}
		
		function focusSzip(id){
			var i=$('#'+id).attr('index');	
			$('#li_szip'+i).addClass('highlighted');
		}
		
		function removeSzip(id){
			var i=$('#'+id).attr('index');	
			$('#li_szip'+i).removeClass('highlighted');
			
		}
		
		
		function focusBstreet(id){
			var i=$('#'+id).attr('index');	
			$('#li_bstreet'+i).addClass('highlighted');
		}
		
		function removeBstreet(id){
			var i=$('#'+id).attr('index');	
			$('#li_bstreet'+i).removeClass('highlighted');
			
		}
		
		function focusBcity(id){
			var i=$('#'+id).attr('index');	
			$('#li_bcity'+i).addClass('highlighted');
		}
		
		function removeBcity(id){
			var i=$('#'+id).attr('index');	
			$('#li_bcity'+i).removeClass('highlighted');
			
		}
		
		function focusBstate(id){
			var i=$('#'+id).attr('index');	
			$('#li_bstate'+i).addClass('highlighted');
		}
		
		function removeBstate(id){
			var i=$('#'+id).attr('index');	
			$('#li_bstate'+i).removeClass('highlighted');
			
		}
		
		function focusBzip(id){
			var i=$('#'+id).attr('index');	
			$('#li_bzip'+i).addClass('highlighted');
		}
		
		function removeBzip(id){
			var i=$('#'+id).attr('index');	
			$('#li_bzip'+i).removeClass('highlighted');
			
		}
		
		
		
		function focusTPV(id){
			var i=$('#'+id).attr('index');	
			$('#li_tpv'+i).addClass('highlighted');
		}
		
		function removeTPV(id){
			var i=$('#'+id).attr('index');	
			$('#li_tpv'+i).removeClass('highlighted');
			
		}
		
		
		function higlightRate(id){
			var i=$('#'+id).attr('index');	
			if($('#'+id).is(':checked')){
				$('#rate'+i).css('background-color','#FCFFA3');
				$('#rate'+i).addClass('highrate');
			}
			else{
				$('#rate'+i).css('background-color','#fff');
				$('#rate'+i).removeClass('highrate');
			}
		}
		
		
		function getservice(id){
			var i=$('#'+id).attr('index');		
			var service=$('#'+id).attr('service');
			
			if(service==="electric"){
				 if($('#electric'+i).is(':checked')){
					$('#gas'+i).removeAttr('checked');
					$('#service'+i).val('electric');	
					$('#svcLbl'+i).text('kWh');
				}
				 else{
					 $('#service'+i).val('electric');	
					 $('#electric'+i).attr('checked','checked');
					 $('#svcLbl'+i).text('kWh');
				 } 				
			}
			else if(service==="gas"){
				if($('#gas'+i).is(':checked')){
					$('#electric'+i).removeAttr('checked');
					$('#service'+i).val('gas');	
					$('#svcLbl'+i).text('Therm');
				}
				 else{
					 $('#service'+i).val('electric');	
					 $('#electric'+i).attr('checked',true);
					 $('#svcLbl'+i).text('kWh');
				 } 
			}
			else{
				 $('#service'+i).val('electric');	
				 $('#electric'+i).attr('checked',true);	
				 $('#svcLbl'+i).text('kWh');
			}		
		}
		
		
		
		
		
		
		
		
		function removeAccount(id){		
				var x=confirm("Click OK to Remove Account From Deal");					
				if(x==true ){
					var i=$('#'+id).attr('index');	
					
					var orderId=$('#orderId'+i).val();
					var dealId=$('#dealId').val();
					
					var $body=$('body');
					
					 $body.addClass('loading'); 
					 $.ajax({
							url:"/hoveyenergy/admin/removeorderfromdeal.do?orderId="+orderId,							
							type: "GET",	
							 beforeSend: function(){
						            $body.addClass('loading');
						        },
							success: function(data){
								if(data=="success"){
									window.location="/hoveyenergy/admin/editdealsheet.do?dealId="+dealId;									 
								}
								else{									
									$('#acc1_error'+i).html('');									
								//	window.location="/hoveyenergy/agent/dealsheet.do?error=true";
								}
							},
							error:function(data,status){
								if(data.status==501){
									alert(data.responseText);
								}
								else{
									window.location="/hoveyenergy/admin/dealsheeterror.do";
								}
							},
							complete: function(){
					            $body.removeClass('loading');
					        } 
					});					
				}				
			}
		
		
		/*
		 * Added by Jeevan on December 12, 2013.
		 * A Method to SaveCustomerAsNew as per clients need.
		 * Change custId as per requiremenr
		 */
		function saveCustomerasNew(){
			if($('#newCust').is(':checked')){
				
				alert('Customer Details will be Saved Afresh Discarding Existing Details for Current Deal');
				$('#custid').val(0);
			}
			else{
				alert('Existing Customer Details Applicable to Current Deal');
				$('#custid').val($('#custid1').val());
			}
			
		}
		
		
	