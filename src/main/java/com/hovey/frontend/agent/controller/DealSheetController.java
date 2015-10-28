/** 
 *  ** $Header:  DealSheetController.java, M Jeevan Kumar, 19/04/2013
* Copyright (c) KNS Technologies Pvt. Ltd. 2013 All Rights Reserved
*
* NAME
* DealSheetController.java
* USAGE
* Controller of Deal Sheets.., handles all requests related to Deal Sheets.
* 
* DESCRIPTION
* Handles Deal Sheet Related Requests.
* CHANGES (most recent first)
* M JEEVAN KUMAR, 19/04/2013
 * */


package com.hovey.frontend.agent.controller;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import twitter4j.org.json.JSONObject;


import com.hovey.backend.agent.exception.CustomersNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.exception.StateNotFoundException;
import com.hovey.backend.supplier.exception.SupplierNotFoundException;
import com.hovey.frontend.admin.service.AdminService;
import com.hovey.frontend.admin.service.CustomerService;
import com.hovey.frontend.agent.dto.BillingStateDto;
import com.hovey.frontend.agent.dto.CustomerDto;
import com.hovey.frontend.agent.dto.CustomerSearchDto;
import com.hovey.frontend.agent.dto.DealSheetForm;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.agent.dto.StateDto;
import com.hovey.frontend.agent.dto.UtilityDto;
import com.hovey.frontend.agent.service.AgentService;
import com.hovey.frontend.agent.service.DealSheetService;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.supplier.service.SupplierService;
import com.hovey.frontend.user.dto.HoveyUserDto;
import com.hovey.frontend.user.service.UserService;

@Controller
public class DealSheetController {

	/**
	 * @author JEEVAN
	 */
	
	private static Logger log=Logger.getLogger(DealSheetController.class);
	
	
	@Resource(name="dealSheetService")
	private DealSheetService dealSheetService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="supplierService")
	private SupplierService supplierService;
	
	@Resource(name="adminService")
	private AdminService adminService;
	
	@Resource(name="customerService")
	private CustomerService customerService;
	
	@Resource(name="agentService")
	private AgentService agentService;
	
	
	/*
	 * 
	 *  for Handling conditions when Date may be null, useful while editing a Pipeline, where date may be entered for certain Orders..
	 *  Comes Handy to accept Null Meter Read Valuez
	 *  */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);

	    // true passed to CustomDateEditor constructor means convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
		
	
	
	
	
	
	
	
	// Setting Deal Sheet as model Attribute..
	@ModelAttribute("dealSheet")
    public DealSheetForm getDealSheetForm()
    {
		DealSheetForm dealSheet=new DealSheetForm();
		dealSheet.setOrders(new AutoPopulatingList<OrdersDto>(OrdersDto.class));		
		return dealSheet;
    }
	
	
  /* ************************** Saving Deal Sheet  ************************************************************************/	
	
	// Displays a deal sheet form..
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/agent/dealsheet.do",method=RequestMethod.GET)
	public String displayDealSheet(Map<String,Object> map,HttpServletRequest request,@ModelAttribute("customerSearch") CustomerSearchDto customerSearch) throws Exception{
		log.info("inside displayDealSheet()");	
		
		//dealSheet.setOrders(new AutoPopulatingList<OrdersDto>(OrdersDto.class));	
		try{
			
				Authentication auth=SecurityContextHolder.getContext().getAuthentication();				
				String user=auth.getName();					
			HoveyUserDto userDto=this.userService.getUserByUsername(user);
			map.put("user",userDto);		
			try{
				ArrayList<CustomerDto> customerDtos=this.dealSheetService.getCustomersFromDb();
				map.put("customers", customerDtos);
				Map<String, Object> customerMap=this.dealSheetService.getSortedCustomerDetails(customerDtos);
				ArrayList<String> firstNames=(ArrayList<String>) customerMap.get("firstNames");
				ArrayList<String> lastNames=(ArrayList<String>) customerMap.get("lastNames");
				ArrayList<String> taxIds=(ArrayList<String>) customerMap.get("taxIds");
				ArrayList<String> phoneNos=(ArrayList<String>) customerMap.get("phoneNos");				
				map.put("firstNames", firstNames);
				map.put("lastNames", lastNames);
				map.put("taxIds", taxIds);
				map.put("phoneNos", phoneNos);
				
			}
			catch(CustomersNotFoundException e){
				log.error("Customers not Found for Given Agent"+e.toString());
			}
			
			try{
				ArrayList<UtilityDto> utilDtos=this.dealSheetService.getUtilitiesFromDb();
				map.put("utilities",utilDtos);
			}
			catch(Exception e){
				log.error("Utilities not Found for Given Agent"+e.toString());
			}
			
			try{
				ArrayList<StateDto> stateDtos=this.dealSheetService.getStatesFromDao();
				map.put("states", stateDtos);
			}
			catch(StateNotFoundException e){
				log.error("States Not Found "+e.toString());
			}
			try{
				ArrayList<BillingStateDto> stateDtos=this.dealSheetService.getBillingStatesFromDao();
				map.put("billingStates", stateDtos);
			}
			catch(StateNotFoundException e){
				log.error("Billing States Not Found "+e.toString());
			}
			
			try{
				ArrayList<SupplierDto> supplierDtos=this.supplierService.getSuppliersFromDAO();
				map.put("suppliers", supplierDtos);
			}
			catch(SupplierNotFoundException e){
				log.error("No Supplier Found"+e.toString());
			}
			
			
			try{
				ArrayList<String>businessNames=this.adminService.getBusinessNames();
				 map.put("businessNames", businessNames);
			}
			catch (Exception e) {
				log.error("No Business Name Found"+e.toString());
			}		
			return "agent/dealSheet";
		}	
		catch(Exception e){
			log.error("Exception in Displaying Deal Sheet"+e.toString());
			String message="Error in Displaying Deals Sheet";
			map.put("message", message);
			e.printStackTrace();
			return "error";
		}		
	}
	
	
	
	/*//for saving dynamically added deal sheet forms...
	@RequestMapping(value="/agent/dealsheet.do",method=RequestMethod.POST)
	public String saveDealSheets(@ModelAttribute("dealSheet") DealSheetForm dealSheet,Map<String,Object> map,HttpServletRequest request){
		try{
			log.info("inside saveDealSheeeeeets()");
			Authentication auth=SecurityContextHolder.getContext().getAuthentication();				
			String user=auth.getName();		
			HoveyUserDto userDto=this.userService.getUserByUsername(user);
			String agentNumber=userDto.getAgentNumber();
			this.dealSheetService.saveDealSheetToDB(dealSheet,agentNumber);
			String message="Orders Saved Successfully";
			map.put("message", message);
			return "redirect:/agent/dealsheet.do";
		}
		catch (DataIntegrityViolationException e) {
			log.error("Error "+e.toString());
			log.error(e.getStackTrace().toString());			
			return "redirect:/agent/dealsheet.do?error=true";
		}
		catch(Exception e){
			e.printStackTrace();
			String message="error in saving deal sheet, Please Try Again";
			map.put("message", message);
			return "error";
		}		
	}*/
	
	
	//for saving dynamically added deal sheet forms...
		@RequestMapping(value="/agent/dealsheet.do",method=RequestMethod.POST)
		@ResponseBody
		public String saveDealSheets(@ModelAttribute("dealSheet") DealSheetForm dealSheet,Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
			try{
				log.info("inside saveDealSheeeeeets()");
				Authentication auth=SecurityContextHolder.getContext().getAuthentication();				
				String user=auth.getName();		
				HoveyUserDto userDto=this.userService.getUserByUsername(user);
				String agentNumber=userDto.getAgentNumber();
				this.dealSheetService.saveDealSheetToDB(dealSheet,agentNumber);
				String message="Orders Saved Successfully";
				map.put("message", message);
				return "success";
			}
			catch (DataIntegrityViolationException e) {
				log.error("Error "+e.toString());
				log.error(e.getStackTrace().toString());	
				response.setStatus(501);
				return "Problem While Saving Deal Sheet, One or more Orders are provided with Same Account Number... Account# Should be Unique among Orders..";
			}
			catch(Exception e){
				log.error(e.getStackTrace().toString());
				String message="error in saving deal sheet, Please Try Again";
				map.put("message", message);
				try {
					response.sendError(502, "ERROR");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return "error";
			}		
		}
		
		@RequestMapping(value="/agent/dealsheeterror.do",method=RequestMethod.GET)
		public String handleDealSheetError(Map<String, Object> map){
			log.info("handleDealSheetError()");
			String message="error in saving deal sheet, Please Try Again";
			map.put("message", message);
			return "error";
		}
		
		
		@RequestMapping(value="/admin/dealsheeterror.do",method=RequestMethod.GET)
		public String handleEditrDealSheetError(Map<String, Object> map){
			log.info("handleDealSheetError()");
			String message="error in Removing Account From Deal Sheet, Please Try Again";
			map.put("message", message);
			return "error";
		}
		
	
	
	
	
  /* *****************************************************end************************************************************/

	
	
	
	
	
	
  /* ***************************************************Editing Deal Sheet*******************************************************************/	
	
	
	     //Displaying a page to Edit Deal Sheet, it Will be used by Admin to Edit..
	    @SuppressWarnings("unchecked")
		@RequestMapping(value="/admin/editdealsheet.do",method=RequestMethod.GET)
	  public String displayDealSheetToEdit(Map<String,Object> map,HttpServletRequest request,@RequestParam("dealId") Integer transactionId,
			  @RequestParam(value="error",required=false)String error){
		  log.info("inside displayDealSheetToEdit()");
		  try{	
			  Authentication auth=SecurityContextHolder.getContext().getAuthentication();				
			  String user=auth.getName();		
			  HoveyUserDto userDto=this.userService.getUserByUsername(user);
			  map.put("user",userDto);
			  ArrayList<OrdersDto> orderDtos=this.dealSheetService.getDealSheetByTransactionIdFromDB(transactionId);				
			  ArrayList<CustomerDto> customerDtos=this.dealSheetService.getCustomersFromDb();			 
			  ArrayList<UtilityDto> utilDtos=this.dealSheetService.getUtilitiesFromDb();
			  ArrayList<StateDto> stateDtos=this.dealSheetService.getStatesFromDao();
			  ArrayList<BillingStateDto> billingStateDtos=this.dealSheetService.getBillingStatesFromDao();
			  ArrayList<SupplierDto> supplierDtos=this.supplierService.getSuppliersFromDAO();
			  ArrayList<HoveyUserDto> hoveyUserDtos=this.agentService.getAgentsFromDao("", null, null);
			  Map<String, Object> customerMap=this.dealSheetService.getSortedCustomerDetails(customerDtos);
			  ArrayList<String> firstNames=(ArrayList<String>) customerMap.get("firstNames");
			  ArrayList<String> lastNames=(ArrayList<String>) customerMap.get("lastNames");
			  ArrayList<String> taxIds=(ArrayList<String>) customerMap.get("taxIds");
			  ArrayList<String> phoneNos=(ArrayList<String>) customerMap.get("phoneNos");
			  ArrayList<String>businessNames=this.adminService.getBusinessNames();
			  map.put("businessNames", businessNames);				
			  map.put("firstNames", firstNames);
			  map.put("lastNames", lastNames);
			  map.put("taxIds", taxIds);
			  map.put("phoneNos", phoneNos);
			  map.put("agents",hoveyUserDtos);
			  map.put("suppliers", supplierDtos);
			  map.put("billingStates", billingStateDtos);
			  map.put("states", stateDtos);
			  map.put("utilities",utilDtos);
			  map.put("customers", customerDtos);
			  map.put("orders", orderDtos);
			  map.put("dealId", transactionId);
			  if(null!=error && error!=""){
				  map.put("error", error);
			  }
			  return "admin/editDealSheet";			  
		  }
		  catch(OrderNotFoundException e){
			  String message="Error While Editing Deal Sheet, No Deal Found";
			  log.info(message+e.toString());
			  map.put("message", message);
			  return "error";
		  }
		  catch(Exception e){
			  String message="Error While Editing Deal Sheet";
			  log.info(message+e.toString());
			  map.put("message", message);
			  return "error";
		  } 
	  }
 	
	    
	  //for saving dynamically added deal sheet forms...
		@RequestMapping(value="/admin/editdealsheet.do",method=RequestMethod.POST)
		public String editDealSheets(@ModelAttribute("dealSheet") DealSheetForm dealSheet,Map<String,Object> map,HttpServletRequest request,
				@RequestParam("dealId") Integer dealId){
			try{
				log.info("inside editDealSheeeeeets()");				
				int result=this.dealSheetService.editDealSheetToDB(dealSheet);				
				if(result>0){
					map.put("message", "Edited Successfully");
					return "redirect:/admin/viewdealsheets.do";
				}
				else{
					throw new Exception();
				}				
			}
			catch(DataIntegrityViolationException e){
				return "redirect:/admin/editdealsheet.do?dealId="+dealId+"&error=true" ;
			}
			catch(ConstraintViolationException e){
				return "redirect:/admin/editdealsheet.do?dealId="+dealId+"&error=true" ;
			}
			catch(Exception e){
				e.printStackTrace();
				String message="error in saving deal sheet, Please Try Again";
				map.put("message", message);
				return "error";
			}		
		}
	
	
  /* *************************************************End******************************/	
	
	
	
	
	
		/* *************************DELETING DEAL *****************************************/
		
		
	    @RequestMapping(value="/admin/deletedealsheet.do",method=RequestMethod.GET)
		  public String deleteDealSheet(Map<String,Object> map,HttpServletRequest request,@RequestParam("dealId") Integer transactionId,
				  @RequestParam(value="error",required=false)String error){
			  log.info("inside displayDealSheetToEdit()");			  	
			  try{
				 boolean deleteStatus= this.dealSheetService.processDeleteDealSheet(transactionId);
				 if(deleteStatus==true){
					 String status="Deal Deleted Successfully";
					 map.put("status", status);
					 return "redirect:/admin/viewdealsheets.do";
				 }
				 else{
					 throw new Exception() ;
				 }
			  }
			  catch(Exception e){
				  e.printStackTrace();
					String message="Error while Deleting Deal Sheet, Please Try Again";
					map.put("message", message);
					return "error";
			  }     
	    }
	
	    
	    
	    /*
	     * Added by Jeevan on November 08,2013..
	     * Method to Remove Order From Deal Sheet into an Individual Deal
	     * 
	     */
	    
	    @RequestMapping(value="/admin/removeorderfromdeal.do",method=RequestMethod.GET)
	    @ResponseBody
	    public String removeOrderFromDealToNewDeal(@RequestParam("orderId")Integer orderId,HttpServletResponse response){
	    	log.info("inside removeOrderFromDealToNewDeal()");
	    	try{
	    		String status=this.dealSheetService.removeORderFromDealToaNewDeal(orderId);
	    		return status;
	    	}
	    	catch(Exception e){
	    		try {
					response.sendError(502, "ERROR");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		return "error";
	    	}	    	
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	
	
	
  /* ***************************************************Validations for Deal Sheet*******************************************************************/	
		/*
		 * Modified on July 201,2013 by Jeevan acc to change in Deal Sheet ..
		 * Instead of TaxID, Customer will be retreived based on ID
		 * 
		 */
		
	// for getting customer details                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
	@RequestMapping(value="/getCustomer.do",method=RequestMethod.GET)
	@ResponseBody
	public String getCustomerDetails(@RequestParam("id") Integer id){
		log.info("inside getCustomer()");
		try{
			CustomerDto customerDto=this.customerService.getCustomerByIdFromDAO(id);
			JSONObject json=new JSONObject();
			json.accumulate("FIRST_NAME",customerDto.getFirstName());
			json.accumulate("LAST_NAME",customerDto.getLastName());
			json.accumulate("TITLE", customerDto.getTitle());
			json.accumulate("ID",customerDto.getCustomerId());
			json.accumulate("PHONE", customerDto.getPhoneNo());
			json.accumulate("EMAIL", customerDto.getEmail());
			json.accumulate("FAX",customerDto.getFaxNo());
			json.accumulate("TYPE", customerDto.getType());
			json.accumulate("TAX_ID", customerDto.getTaxId());
			json.accumulate("FRONTER", customerDto.getFronter());
			//json.accumulate("SERVICE", customerDto.getService());
			if(null!=customerDto.getTaxExempt()){
				if(customerDto.getTaxExempt()=="true"){
					json.accumulate("TAX","yes");
				}
				else if(customerDto.getTaxExempt()=="false"){
					json.accumulate("TAX","no");			
				}
			}			
			else
				json.accumulate("TAX","na");	
			return json.toString();
		}
		catch(Exception e){
			log.error("Error in getting Customer Details()");
			return "";
		}
	}
	
	
	
	
	
	// for valdating whether taxid is already registered with customer..
	@RequestMapping(value="/validatecustomer.do",method=RequestMethod.POST)
	@ResponseBody
	public String validateCustomer(@RequestParam("taxid") String taxId,@RequestParam("id")Integer id){
	   log.info("inside validateCustomer()");
	   try{
		   if(null!=taxId && taxId!=""){
			   if(taxId.equalsIgnoreCase("xxxxxxxxx") || taxId.equalsIgnoreCase("*********")){
				   return "success";
			   }
			   else{
				   CustomerDto customerDto=this.dealSheetService.getCustomerByTaxIdFromDao(taxId);
				   if(null!=customerDto){
					   if(id.equals(customerDto.getCustomerId())){
						   return "success";
					   }
					   else{
						   return "Customer Already Registered with Tax Id";
					   }
				   }
				   else{
					   throw new IndexOutOfBoundsException();
				   }
			   }
		   }
		   else{
			   throw new IndexOutOfBoundsException();
		   }
	   }
	   catch(IndexOutOfBoundsException e){
		   log.error("No Customer Found with Given Id "+e.toString());
		   return "success";
	   }
	   catch(Exception e){
		   log.error("Failed to validate Customer"+e.toString());
		   e.printStackTrace();
		   return "";
	   }
	   
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/addorders.do")
	protected String appendAccounts(@RequestParam Integer fieldId, ModelMap model) throws Exception
	{	
		ArrayList<UtilityDto> utilDtos=this.dealSheetService.getUtilitiesFromDb();
		ArrayList<StateDto> stateDtos=this.dealSheetService.getStatesFromDao();
		ArrayList<BillingStateDto> billingStateDtos=this.dealSheetService.getBillingStatesFromDao();
		model.put("billingStates",billingStateDtos );
		model.put("utilities",utilDtos);
		model.put("states",stateDtos);
		model.addAttribute("number", fieldId);
		return "agent/dealSheet2";
	}
	
	
	//displays Utility
		@RequestMapping(value="/admin/utility.do",method=RequestMethod.GET)
	public String getUtilities(Map<String, Object>map){
		log.info("inside getUtilities()");
		try{
			ArrayList<UtilityDto> utilities=this.dealSheetService.getAllUtilitiesFromDb();
			map.put("utils", utilities);
			return "admin/utility";
		}
		catch(Exception e){
			String message="Error while Displaying Utilities";
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	
	//displays addUtility
	@RequestMapping(value="/admin/addutility.do",method=RequestMethod.GET)
	public String displayUtility(Map<String, Object>map){
		log.info("inside displayUtility()");
		try{
			return "admin/addUtility";
		}
		catch(Exception e){
			log.error("Error while displaying Utility");
			String message="Error while displaying Utility";
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	//For saving Utility
	@RequestMapping(value="/admin/addutility.do",method=RequestMethod.POST)
	public String saveUtility(@RequestParam("utility") String utility,@RequestParam("length")int length, Map<String, Object> map){
		log.info("inside saveUtility()");
		try{
			int result=this.dealSheetService.saveUtilityToDB(utility, length);
			if(result>0){
				String message="Utility saved Successfully";
				map.put("message",message);
				return "redirect:/admin/utility.do";
			}
			else{
				String message="Error in Saving Utility";
				map.put("message",message);
				return "admin/utility";
			}
		}
		catch(Exception e){
			log.error("Failed to save Utility"+e.toString());
			String message="Error in Saving Utility";
			map.put("message",message);
			return "error";
		}
	}
	
	
	
	
	
	//editing addUtility
	@RequestMapping(value="/admin/editutility.do",method=RequestMethod.GET)
	public String displayUtilityforEdit(Map<String, Object>map,@RequestParam("id") Integer id){
		log.info("inside displayUtilityforEdit()");
		try{
			
			UtilityDto utility=this.dealSheetService.getUtilityByID(id);
			System.out.println(utility.getUtility());
			map.put("util", utility);
			return "admin/editUtility";
		}
		catch(Exception e){
			log.error("Error while displaying Utility to Edit");
			String message="Error while displaying Utility";
			map.put("message", message);
			return "error";
		}
	}
	
	
	@RequestMapping(value="/admin/enableutility.do",method=RequestMethod.GET)
	public String enableDisableUtility(Map<String, Object>map,@RequestParam("id") Integer id,@RequestParam("status") String status){
		try{
			int result=this.dealSheetService.enableDisable(id, status);
			if(result>0){
				String message="Utility"+ status+"d Successfully";
				map.put("message",message);
				return "redirect:/admin/utility.do";
			}
			else{
				String message="Error in Enabling/Disabling Utility";
				map.put("message",message);
				return "admin/utility";
			}
		}
		catch(Exception e){
			log.error("Failed to Enable / Diable Utility"+e.toString());
			String message="Error in Enable/Disable Utility";
			map.put("message",message);
			return "error";
		}		
	}
	
	
	//For editing Utility
		@RequestMapping(value="/admin/editutility.do",method=RequestMethod.POST)
		public String editUtility(@RequestParam("utility") String utility,@RequestParam("length")int length,@RequestParam("id") int id, Map<String, Object> map){
			log.info("inside saveUtility()");
			try{
				int result=this.dealSheetService.editUtility(utility, length, id);
				if(result>0){
					String message="Utility Edited Successfully";
					map.put("message",message);
					return "redirect:/admin/utility.do";
				}
				else{
					String message="Error in Editing Utility";
					map.put("message",message);
					return "admin/utility";
				}
			}
			catch(Exception e){
				log.error("Failed to save Utility"+e.toString());
				String message="Error in Saving Utility";
				map.put("message",message);
				return "error";
			}
		}
	
	
	
	
	
	
	
	//displays orders of agents
	@RequestMapping(value="/getuserorders.do",method=RequestMethod.GET)
	public String viewOrdersofAgent(Map<String, Object> map,HttpServletRequest request) {
		log.info("inside OrdersofAgent()");
		try{
			HttpSession session=request.getSession();
			String username=session.getAttribute("username").toString();
			HoveyUserDto userDto=this.userService.getUserByUsername(username);
			String agentNumber=userDto.getAgentNumber();			
			ArrayList<OrdersDto> ordersDto=this.dealSheetService.getOrdersofAgentFromDB(agentNumber);
			
			if(!ordersDto.isEmpty()){						
				map.put("orders", ordersDto);
			}
			return "agent/userOrders";
		}		
		catch(OrderNotFoundException e){
			log.error("Orders Not Found for Current Agent");
			return "agent/userOrders";
		}
		catch(Exception e){
			log.error("Error in getting Order Details of Agent");
			String message="Error in getting Order Details of Agent";
			map.put("message", message);
			e.printStackTrace();
			return "error";
		}
	}
	
	
	//displays deal sheet of agents
		@RequestMapping(value="/getuserdeals.do",method=RequestMethod.GET)
		public String viewDealSheetsofAgent(Map<String, Object> map,HttpServletRequest request) {
			log.info("inside viewDealSheetsofAgent()");
			try{
				HttpSession session=request.getSession();
				String username=session.getAttribute("username").toString();
				HoveyUserDto userDto=this.userService.getUserByUsername(username);
				String agentNumber=userDto.getAgentNumber();			
				ArrayList<OrdersDto> ordersDto=this.dealSheetService.getDealSheetsofAgentFromDB(agentNumber);
				
				if(!ordersDto.isEmpty()){						
					map.put("orders", ordersDto);
				}
				return "agent/userDeals";
			}		
			catch(OrderNotFoundException e){
				log.error("Orders Not Found for Current Agent");
				return "agent/userDeals";
			}
			catch(Exception e){
				log.error("Error in getting Order Details of Agent");
				String message="Error in getting Order Details of Agent";
				map.put("message", message);
				e.printStackTrace();
				return "error";
			}
		}
		
	
	
	//printing dealsheets.
		@RequestMapping(value="/printorder.do",method=RequestMethod.GET)
	public String printOrder(@RequestParam("orderId") int orderId,Map<String, Object>map){
		log.info("inside printDealSheet()");
		try{
			
			OrdersDto orderDto=this.dealSheetService.getOrderByOrderIdFromDB(orderId);
			ArrayList<OrdersDto> orderDtos=new ArrayList<OrdersDto>();
			orderDtos.add(orderDto);
			 map.put("orders", orderDtos);		 
			return "admin/printDealSheet";
			
		}
		catch(Exception e){
			log.error("error while Printing ORder"+e.toString());
			String message="error while Printing Order";
			map.put("message", message);
			return "error";
		}		
	}
		
		//printing dealsheets.
				@RequestMapping(value="/adminprintorder.do",method=RequestMethod.GET)
			public String printOrderByAdmin(@RequestParam("orderId") int orderId,Map<String, Object>map){
				log.info("inside printOrderByAdmin()");
				try{
					
					OrdersDto orderDto=this.dealSheetService.getOrderByOrderIdFromDB(orderId);
					map.put("order", orderDto);
					return "admin/viewOrder";
					
				}
				catch(Exception e){
					e.printStackTrace();
					log.error("error while Printing ORder"+e.toString());
					String message="error while Printing Order";
					map.put("message", message);
					return "error";
					
				}		
			}
	
	
	
	//For Printing the Deal Sheet.
	@RequestMapping(value="/printdeal.do",method=RequestMethod.GET)
	public String printDealSheet(@RequestParam("dealId") int transactionId,Map<String, Object> map){
		log.info("inside printDealSheet()");
		try{
			ArrayList<OrdersDto> orderDtos=this.dealSheetService.getDealSheetByTransactionIdFromDB(transactionId);
			
			 
			 map.put("orders", orderDtos);
			 
			return "admin/printDealSheet";
		}
		catch(Exception e){
			log.error("Failed to Print Deal Sheet "+e.toString());
			String message="Error in Printing Deal Sheet";
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	//For Printing the Deal Sheet.
	@RequestMapping(value="/printrenewaldeal.do",method=RequestMethod.GET)
	public String printRenewalDealSheet(@RequestParam("dealId") int transactionId,Map<String, Object> map){
		log.info("inside printDealSheet()");
		try{
			ArrayList<OrdersDto> orderDtos=this.dealSheetService.getNonRescindedDealSheetByTransactionIdFromDB(transactionId);
			
			 
			 map.put("orders", orderDtos);
			 
			return "admin/printDealSheet";
		}
		catch(Exception e){
			log.error("Failed to Print Deal Sheet "+e.toString());
			String message="Error in Printing Deal Sheet";
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	
	
	
	
	
	/*// Checks Whether Account No is Already Registered.
	@RequestMapping(value="/checkaccountnumber.do",method=RequestMethod.GET)
	@ResponseBody
	public String isAccountExists(@RequestParam("accountNumber") String accountNumber){
		log.info("inside isAccountExists()");
		try{
			
			OrdersDto orderDto=this.dealSheetService.getOrderByAccountNumberFromDao(accountNumber);
			if(null!=orderDto.getAccountNumber()){
				return "Account Already Exists";
			}
			else{
				return "success";
			}
				
		}
		catch(OrderNotFoundException e){
			log.error("Account Not Found"+e.toString());
			return "success";
		}
		catch(Exception e){
			log.error("Error in Validating Account "+e.toString());
			e.printStackTrace();
			return "";
		}
	}
	*/
	
	
	//validating accountNumber based on Utiltity
		@RequestMapping(value="/validateaccountno.do",method=RequestMethod.GET)
		@ResponseBody
		public String validateAccountNumber(@RequestParam("acc") String accountNo,@RequestParam("util") String utility, @RequestParam("orderDate") Date orderDate) throws Exception{			
			log.info("inside validateAccountNumber()");			
				try{
					OrdersDto orderDto=this.dealSheetService.getOrderByAccountNumberFromDao(accountNo);				
					if(null!=orderDto){
						throw new OrderNotFoundException();
					}
					else{
						throw new OrderNotFoundException();
					}
				}
				catch(OrderNotFoundException e){
					log.error("Account Not Found"+e.toString());
					String status=this.dealSheetService.validateAccountNo(accountNo, utility);
					return status;
				}
				catch(Exception e){
					log.error("Error in validating Account Number"+e.toString());
					e.printStackTrace();
					return "";
				}		
			}
	
	
		
		//validating account# for editing Deal Sheets..
		@RequestMapping(value="/validateeditaccountno.do",method=RequestMethod.GET)
		@ResponseBody
		public String validateEditAccountNumber(@RequestParam("acc") String accountNo,@RequestParam("util") String utility,@RequestParam("id") int orderId,
				@RequestParam("orderDate") Date orderDate) throws Exception{
			log.info("inside validateAccountNumber()");			
				try{
					OrdersDto orderDto=this.dealSheetService.getOrderByAccountNumberFromDao(accountNo);						
					if(null!=orderDto && orderDto.getId()!=orderId){						
							throw new OrderNotFoundException();					
					}
					else{
						throw new OrderNotFoundException();
					}
				}
				catch(OrderNotFoundException e){
					log.error("Account Not Found"+e.toString());
					String status=this.dealSheetService.validateAccountNo(accountNo, utility);
					return status;
				}
				catch(Exception e){
					log.error("Error in validating Account Number"+e.toString());
					e.printStackTrace();
					return "no";
				}		
			}
		
		
		//validating populating orderId for renewal accounts so that it can be updateds..
		@RequestMapping(value="/getorderid.do",method=RequestMethod.GET)
		@ResponseBody
		public String getOrderIdofRenewalAccount(@RequestParam("acc") String accountNo){
			try{
				log.info("	inside getOrderIdofRenewalAccount()");
				OrdersDto order=this.dealSheetService.getOrderByAccountNumberFromDao(accountNo);
				return order.getId().toString();			
			}
			catch(OrderNotFoundException e){
				log.error("New Account "+e.toString());
				return null;
			}
			catch (Exception e) {
				log.error("New Account "+e.toString());
				return null;
			}
		}
		
		
		
		/* *******Added on July 19,2013 to enable Searching Existing Customer
		 * 
		 * 
		 * Retreives all the customers Satisfied by the Criteria.
		 * 
		 */
		@RequestMapping(value="/getcustomer.do",method=RequestMethod.POST)
		@ResponseBody
		public String getCustomersSatisfiedBySearchCondition(@ModelAttribute("customerSearch") CustomerSearchDto customerSearch,HttpServletResponse res){
			log.info("inside getCustomersSatisfiedBySearchCondition()");
			try{
				ArrayList<CustomerDto> customers=this.dealSheetService.getCustomersSatisfiedBySearchConditionsFfromDao(customerSearch);
				JSONObject json=new JSONObject();

				for(CustomerDto cust:customers){
					JSONObject json1=new JSONObject();
					json1.accumulate("CUSTOMER_ID", cust.getCustomerId());
					json1.accumulate("FIRST_NAME",cust.getFirstName());
					json1.accumulate("LAST_NAME", cust.getLastName());
					json1.accumulate("TAX_ID", cust.getTaxId());
					json.append("customer", json1);
				}
				
				return json.toString();
			}
			catch(Exception e){
				log.error("Error in Getting CustomerDetails" +e.toString());		
				res.setStatus(400);
				return null;
			}
		}
		
		
		
	 /* *****
	  * 
	  * Added by Jeevan on October 16, 2013
	  * 
	  * checking if Orders exists with same acc# n startDate
	  * 
	  * ****/
		@RequestMapping(value="/checkaccountexistance.do",method=RequestMethod.GET)
		@ResponseBody
		public String checkIfOrdersExistswithAccountNoandStartDate(@RequestParam("account")String accountNumber,@RequestParam("startDate") Date dealStartDate){
			log.info("inside checkIfOrdersExistswithAccountNoandStartDate()");
			try{
				Integer orders=this.dealSheetService.getNoOfOrdersBetweenDealStartDateAccount(accountNumber, dealStartDate);
				if(orders>0){
					log.info("Orders Exists with Account# and DealStartDate");
					return "fail" ;
				}
				else{
					throw new OrderNotFoundException();
				}
			}
			catch(OrderNotFoundException e ){
					log.info("No Order Found for AccountNo, Deal Date verification Condition");
			return "success";
			}
			catch(Exception e){
				log.error("Error while checking AccountNo Existance");
				return "fail";				
			}
		}		
		
		
		
		
		
		
		
	 /* *****
	  * 
	  * Added by Jeevan on October 16, 2013
	  * 
	  * checking if Orders exists with same acc# n startDate
	  * 
	  * ****/
		@RequestMapping(value="/checkaccountexistanceforedit.do",method=RequestMethod.GET)
		@ResponseBody
		public String checkIfOrdersExistswithAccountNoandStartDateForEDitDeals(@RequestParam("account")String accountNumber,@RequestParam("startDate") Date dealStartDate,
				@RequestParam("id") Integer orderId){
			log.info("inside checkIfOrdersExistswithAccountNoandStartDate()");
			try{
				String status=this.dealSheetService.getOrdersBetweenDealStartDateAccount(accountNumber, dealStartDate, orderId);
				return status;
		   }
		  catch(OrderNotFoundException e ){
				log.info("No Order Found for AccountNo, Deal Date verific0ation Condition");
				return "success";
			}
			catch(Exception e){
				log.error("Error while checking AccountNo Existance");
				return "fail";				
			}			
		}
		
		
	
		
	
}
