package com.hovey.frontend.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.Order;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
/*
 * Created by Jeevan on July 23,2013 to perform Commission Reconciliation
 * 
 */
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.org.json.JSONObject;

import com.hovey.backend.admin.exception.AgentCommissionNotFoundException;
import com.hovey.backend.agent.exception.CustomersNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.model.KwhLimit;
import com.hovey.backend.agent.model.Orders;
import com.hovey.frontend.admin.service.AdminService;
import com.hovey.frontend.admin.service.CommissionService;
import com.hovey.frontend.admin.service.CustomerService;
import com.hovey.frontend.agent.dto.AgentCommissionsDto;
import com.hovey.frontend.agent.dto.CustomerDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.supplier.dto.SupplierMappingDto;
import com.hovey.frontend.user.dto.HoveyUserDto;

@Controller
public class CommissionController {

	private static Logger log=Logger.getLogger(CommissionController.class);
	
	@Resource(name="customerService")
	private CustomerService customerService;
	
	@Resource(name="commissionService")
	private CommissionService commissionService;
	
	@Resource(name="adminService")
	private AdminService adminService;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);
	    // true passed to CustomDateEditor constructor means convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/*
	 * 
	 * Added by Jeevan on July 23,2013. for Commissions
	 * It is almost a replaction of customer.do but it is developed to implement to Commissions handling and reconcilation
	 */	
	
	// Displays the Commissions of Customer with an  option to reconcile
		@RequestMapping(value="/admin/commissions.do", method=RequestMethod.GET)
		public String displayCustomers(Map<String,Object> map,@RequestParam(value="page",required=false,defaultValue="0")Integer pageNo,
				@RequestParam(value="range",required=false,defaultValue="20")	Integer pageSize,@RequestParam(value="sortby",required=false,defaultValue="businessName")String sortBy,
				@RequestParam(value="searchBy",required=false)String searchBy, @RequestParam(value="output",required=false) String output){
			log.info("inside displayCustomers()");
			try{				
				if(null!=output && output.equalsIgnoreCase("excel")){
					ArrayList<CustomerDto> customerDtos=this.customerService.getCustomersFromDb(0, 0,"businessName",null);
					ArrayList<OrdersDto> orderDtos=this.customerService.getOrdersOfaCustomer(customerDtos);
					int i=0;
					if(!customerDtos.isEmpty() && !orderDtos.isEmpty() ){
						/*
						 * Modified from Customer to Orders By Jeevan as Per Clients Need to Display Business Name instead of Customer Name...
						 */
						for(OrdersDto order:orderDtos){	
							try{
								Map<String, Object>customerCommissions=this.customerService.getCommissionsOfaCustomerById(order.getTaxId().getCustomerId());
								order.getTaxId().setTotalExpectedCommission((Double) customerCommissions.get("expectedCommission"));
								order.getTaxId().setTotalCommissionReceived((Double)customerCommissions.get("receivedCommission"));
								order.getTaxId().setTotalTermCommission((Double)customerCommissions.get("expectedTermCommission"));
								double totalAccounts=(Double) customerCommissions.get("accounts");
								order.getTaxId().setTotalAccounts((int) totalAccounts);
								//Modified by Jeevan on May 29, 2014 to calculate on basis of Term Commission
								order.setNetCommission(order.getTaxId().getTotalCommissionReceived()-order.getTaxId().getTotalTermCommission());
							}
							catch(OrderNotFoundException e){
								order.getTaxId().setTotalExpectedCommission(0.0);
								order.getTaxId().setTotalCommissionReceived(0.0);
								order.getTaxId().setTotalTermCommission(0.0);
								order.getTaxId().setTotalAccounts(0);
								log.error("No Orders Exists for Current Customer"+e.toString());
							}					
						}
					}
					Map<String, OrdersDto> commissionData=new LinkedHashMap<String, OrdersDto>();
					for(OrdersDto order:orderDtos){						
						commissionData.put("order"+i, order);
						i++;
					}
					map.put("orders", commissionData);				
					return "commissionXlsReport";
				}
				else{
					int pagesNeeded=this.customerService.findTotalNoOfCustomerPages(pageSize,searchBy);	
					int firstCustomer=pageSize*pageNo+1;
					int totalCustomers=this.customerService.getTotalCustomers(searchBy);
					int lastCustomer;
					ArrayList<CustomerDto> customerDtos=this.customerService.getCustomersFromDb(pageNo, pageSize,sortBy,searchBy);
					ArrayList<OrdersDto> orderDtos=this.customerService.getOrdersOfaCustomer(customerDtos);
					if(!customerDtos.isEmpty() && !orderDtos.isEmpty() ){
						/*
						 * Modified from Customer to Orders By Jeevan as Per Clients Need to Display Business Name instead of Customer Name...
						 */
						for(OrdersDto order:orderDtos){	
							try{
								Map<String, Object>customerCommissions=this.customerService.getCommissionsOfaCustomerById(order.getTaxId().getCustomerId());
								order.getTaxId().setTotalExpectedCommission((Double) customerCommissions.get("expectedCommission"));
								order.getTaxId().setTotalCommissionReceived((Double)customerCommissions.get("receivedCommission"));
								order.getTaxId().setTotalTermCommission((Double)customerCommissions.get("expectedTermCommission"));
								double totalAccounts=(Double) customerCommissions.get("accounts");
								order.getTaxId().setTotalAccounts((int) totalAccounts);
								//Modified by Jeevan on May 29, 2014 to calculate on basis of Term Commission
								order.setNetCommission(order.getTaxId().getTotalCommissionReceived()-order.getTaxId().getTotalTermCommission());
							}
							catch(OrderNotFoundException e){
								order.getTaxId().setTotalExpectedCommission(0.0);
								order.getTaxId().setTotalCommissionReceived(0.0);
								order.getTaxId().setTotalTermCommission(0.0);
								order.getTaxId().setTotalAccounts(0);
								log.error("No Orders Exists for Current Customer"+e.toString());
							}					
						}
						lastCustomer=this.getCustomersOfLastPageFromRangeandTotalCustomers(pageSize, totalCustomers, firstCustomer);
						map.put("first", firstCustomer);
						map.put("total", totalCustomers);
						map.put("last", lastCustomer);
						map.put("customers", customerDtos);
						map.put("page",pageNo);
						map.put("end", pagesNeeded);
						map.put("orders", orderDtos);
						map.put("sortby", sortBy);
					}
					return "admin/commissions";
				}
			}
			catch(CustomersNotFoundException e){
				String message="No Customers Found To Display Commissions";
				map.put("message",message);
				log.error(message+e.toString());
				return "admin/commissions";			
			}
			catch(OrderNotFoundException e){
				String message="No Orders Found To Display Commissions";
				map.put("message",message);
				log.error(message+e.toString());
				return "admin/commissions";			
			}			
			catch(Exception e){
				e.printStackTrace();
				String message="Error While Displaying Customers";
				map.put("message",message);
				log.error(message+e.toString());
				return "error";
			}	
		}
	
	
	/*
	 * Added by Jeevan on July 23,2013 for Displaying List of ORders for Reconciliation
	 * 	
	 * Displays a page with List of Accounts and their Commissions along with Reconciliation option for Customers..
	 * 
	 * Almost a replication to customeraccounts.do
	 */	
		@RequestMapping(value="/admin/reconcile.do",method=RequestMethod.GET)
		public String initCommissionReconciliation(@RequestParam("customerId")Integer customerId, Map<String, Object> map){
			log.info("inside initCommissionReconciliation()" );
			try{
				ArrayList<OrdersDto> orderDtos=this.customerService.getOrdersOfaCustomerFromDAO(customerId);
				map.put("orders", orderDtos);
				return "admin/reconcileCommission";			
			}
			catch(OrderNotFoundException e){
				log.error("No Orders Found for the Current Customers"+e.toString());
				String message="No Orders found for Customer ";
				map.put("message", message);
				return "admin/reconcileCommission";
			}
			catch(Exception e){
				e.printStackTrace();
				log.error("Error while getting Customer Accounts");
				map.put("message","Error while getting Customer Accounts");
				return "error";
			}
		}
	
	/* ******end ********/
		
	/*
	 * 
	 * Added By Jeevan on July 23,2013 .
	 * 
	 * Reconciles Commission,
	 * Saves Updated Commission Values to Database
	 * 	
	 */
		@RequestMapping(value="/admin/reconcile.do",method=RequestMethod.POST)
	@ResponseBody
	public String reconcileCommission(@RequestParam("commission")Double commission,@RequestParam("upfrontCommission") Double upfrontCommission,
			@RequestParam("id")Integer orderId,@RequestParam("commissionRate") Double commissionRate){
		log.info("inside reconcileCommission()");
		try{
			Map<String, Double> commissionsMap=this.commissionService.reconcileCommissions(commission, upfrontCommission, orderId,commissionRate);
			if(!commissionsMap.isEmpty()){
				JSONObject json =new JSONObject();
				json.accumulate("TOTAL_COMMISSION",commissionsMap.get("totalCommission"));
				json.accumulate("TOTAL_UPFRONT", commissionsMap.get("totalUpfront"));		
				json.accumulate("TOTAL_TERMCOMMISSION", commissionsMap.get("totalTermCommission")); //added by Jeevan on May 30, 2014
				return json.toString();
			}
			else{
				throw new Exception();
			}
		}
		catch(Exception e){
			log.error("Erro while Performing Commission Reconciliation "+e.toString());
			return "fail";
		}
	}
		
	
		
		private int getCustomersOfLastPageFromRangeandTotalCustomers(int range,int totalOrders,int firstOrder){
			log.info("inside getOrdersOfLastPageFromRangeandTotalOrders()	");
			int lastOrder=firstOrder+range-1;
			if(lastOrder>totalOrders){
				lastOrder=(totalOrders%range)+firstOrder-1;
			}
			return lastOrder;
		}
		
		
		
		
/******************************************************************************************Handling Agent Commissions **************************************************8********/
		/*
		 * For Displaying Agent Commissions Page
		 */
		@RequestMapping(value="/admin/agentcommissions.do",method=RequestMethod.GET)
		public String initAgentCommissions(Map<String, Object>map){
			log.info("inside initAgentCommissions()");
			try{
				ArrayList<HoveyUserDto> agentDtos=this.adminService.getAgentsFromDao();
				map.put("agents", agentDtos);
				return "admin/agentCommissions";
			}
			catch(Exception e){
				String message="Error While initiating Agent Commissions";
				log.error(message+" "+e.toString());
				map.put("message", message);
				return "error";
			}
		}
		
		
		
		
		
		/*
		 * Displaying All The Commissions.
		 */
		@RequestMapping(value="/admin/getagentcommissions.do",method=RequestMethod.GET)
		public String displayAgentsEligibleForCommissions(@RequestParam("startDate")Date startDate,@RequestParam("endDate")Date endDate,Map<String, Object>map,
				@RequestParam(value="agent",required=false)String agent, @RequestParam(value="filter",defaultValue="false",required=false) Boolean filter,
				@RequestParam(value="output",required=false)String excelOutput){
			log.info("inside displayAgentsEligibleForCommissions()");
			try{			
				ArrayList<OrdersDto> eligibleOrders=this.commissionService.getAllEligbleOrdersforAgentCommissions(startDate, endDate,agent,filter);
				ArrayList<AgentCommissionsDto> commissionDtos=null;		
				System.out.println("sixee "+eligibleOrders.size());		
				if(!eligibleOrders.isEmpty()){
						try{
							commissionDtos=this.commissionService.getAgentCommissionsOfRescindedAccounts(eligibleOrders);								
						}
						catch(Exception e){							
							log.error("No ORder Exists for Rescinded");
						}					
				}				
				else{
					throw new OrderNotFoundException();
				}
				
				if(null!=excelOutput && excelOutput!=""){
					Map<String, AgentCommissionsDto> agentData=new LinkedHashMap<String, AgentCommissionsDto>();
					Map<String, AgentCommissionsDto> rescData=new LinkedHashMap<String, AgentCommissionsDto>();
					int i=0;
					for(OrdersDto eligibleOrder:eligibleOrders){
						if(eligibleOrder.getAgentCommissionStatus().equalsIgnoreCase("unpaid")){
							AgentCommissionsDto agentCommission=new AgentCommissionsDto();
							agentCommission.setAgent(eligibleOrder.getCreatedAgent());
							agentCommission.setOrder(eligibleOrder);
							agentCommission.setSupplier(eligibleOrder.getSupplierName());
							agentCommission.setWeek(eligibleOrder.getWeek());
							agentCommission.setYear(eligibleOrder.getYear());	
							agentCommission.setCommissionPayable(true);
							agentData.put("order"+i, agentCommission);
							i++;
						}
					}
					int j=0;
					for(AgentCommissionsDto resCom:commissionDtos){
						rescData.put("order"+j, resCom);
						j++;
					}
					map.put("agents", agentData);				
					map.put("rescissions", rescData);
					return "agentCommissionXlsReport";	
				}	
				else{				
					map.put("filter",filter);
					map.put("commissions", commissionDtos);
					map.put("orders", eligibleOrders);		
					return "admin/calculateCommissions";
				}				
			}
			catch(OrderNotFoundException e){
				String message="No Eligible Orders found for the Agent within the selected Date Range";
				log.error(message+" "+e.toString());
				map.put("message", message);
				if(null!=excelOutput && excelOutput!=""){
					return "error";
				}
				else{
					return "redirect:/admin/agentcommissions.do";
				}
			}
			catch(Exception e){
				e.printStackTrace();
				String message="Error Occured While getting Agent Commissions";
				map.put("message", message);
				return "error";
			}			
		}		
		
		
		
		
		
		/*
		 * Saving Agent Commissions From Ajax....
		 * 
		 */
		@RequestMapping(method=RequestMethod.POST,value="/admin/saveagentcommission.do")
		@ResponseBody
		public String saveAgentCommissions(@RequestParam(value="id",required=false)Integer id, @RequestParam("orderId")Integer orderId,
				@RequestParam("agentCommission")Double agentCommission,@RequestParam("commissionRate") Double commissionRate, @RequestParam("commissionPayable") Boolean commissionPayable,
				@RequestParam("week") Integer week, @RequestParam("year") Integer year,
				@RequestParam(value="refund",required=false,defaultValue="false")Boolean refund){
						log.info("inside saveAgentCommissions()");
			try{
				Integer result=0;
				if(refund==true){
					result=this.commissionService.saveOrUpdateAgentCommission(id, orderId, -agentCommission, commissionRate, commissionPayable, refund,week,year);
				}
				else{
					result=this.commissionService.saveOrUpdateAgentCommission(id, orderId, agentCommission, commissionRate, commissionPayable,week,year);
				}
				if(result>0){
					return result.toString();
				}
				else{
					throw new Exception();
				}
			}
			catch(Exception e){
				e.printStackTrace();
				return "error";
			}			
		}
		
		
		
		@RequestMapping(method=RequestMethod.GET,value="/admin/editagentcommission.do")
		public String initEditAgentCommissions(Map<String, Object>map){
			log.info("inside initEditAgentCommissions()");
			try{
				ArrayList<HoveyUserDto> agentDtos=this.adminService.getAgentsFromDao();
				map.put("agents", agentDtos);
				return "admin/editAgentCommissions";
			}
			catch(Exception e){
				String message="Error in Getting Agent Commissions";
				map.put("message", message);
				return "error";
			}
		}
		
		
		
		@RequestMapping(method=RequestMethod.POST,value="/admin/editagentcommission.do")
		public String editAgentCommissions(@RequestParam(value="agent",required=false)String agent,@RequestParam(value="week",required=false)Integer week,
				@RequestParam(value="year",required=false)Integer year,Map<String, Object> map){
			log.info("inside editAgentCommissions()");
			try{
				ArrayList<AgentCommissionsDto> commissionsDtos=this.commissionService.getAgentCommissions(agent, week,year);
				map.put("orders",commissionsDtos);
				return "admin/calculateEditCommissions";
			}
			catch(AgentCommissionNotFoundException e){
				map.put("message", "No Agent Commissions Found");
				return "admin/calculateEditCommissions";
			}
			catch(Exception e){
				map.put("message", "Error while Getting Commissions for Editing");
				e.printStackTrace();
				return "error";
			}
						
		}
		
		
		
		
		
		
		
		
		
		
		
		/*
		 * initiating change kWh Limit
		 */
		@RequestMapping(value="/admin/changekwhlimit.do",method=RequestMethod.GET)
		public String initChangingKwhLimit(Map<String, Object>map){
			log.info("inside initChangingKwhLimit()");
			try{
				KwhLimit kwh=this.commissionService.getKwhLimit();
				map.put("kwh", kwh);
				return "admin/changeKwh";
			}
			catch(Exception e){
				String message="Error while initiating Change Kwh Limit";
				log.error(message+" "+e.toString());
				map.put("message",message);
				return "error";
			}
		}
		
		
		
		/*
		 * Changing kWh Limit
		 */
		@RequestMapping(value="/admin/changekwhlimit.do",method=RequestMethod.POST)
		public String changeKwhLimit(@RequestParam("kWhLimit") Integer kWhLimit,Map<String, Object>map){
			log.info("inside changeKwhLimit()");
			try{
				Integer result=this.commissionService.updatekWhLimit(kWhLimit);
				if(result>0){
					String message="kWh Limit Changed Successfully";
					map.put("message", message);
					return "redirect:/admin/changekwhlimit.do";
				}
				else{
					throw new Exception();
				}
			}
			catch (Exception e) {
				String message="Error While Changing kWh Limit";
				map.put("message", message);
				return "error";
			}
		}
		
		
		
		
		/*
		 * Added by Jeevan on November 22, 2013.
		 * Method to Export Agent Commissions to Export.
		 */		
		@RequestMapping(value="/admin/exportcommissions.do",method=RequestMethod.GET)
		public String exportAgentCommissionsToExcel(@RequestParam("week") Integer week,@RequestParam("year") Integer year,Map<String, Object> map){
			log.info("inside exportAgentCommissionsToExcel()");
			try{
					ArrayList<AgentCommissionsDto> agentCommissionsDto=this.commissionService.getAgentCommissions(null, week,year);
					Map<String, AgentCommissionsDto> agentData=new LinkedHashMap<String, AgentCommissionsDto>();
					int i=0;
					for(AgentCommissionsDto agentCommission:agentCommissionsDto){					
						agentData.put("order"+i, agentCommission);
						i++;
					}
					map.put("agents", agentData);				
					return "agentCommissionXlsReport";				
			}
			catch(AgentCommissionNotFoundException e){
				String message="No Agent Commissions Found Within the Selected Period";
				map.put("message", message);
				return "error";
			}
			catch(Exception e){
				String message="Error while Exporting agent Commission";
				map.put("message", message);
				return "error";
			}			
		}
		
		
/******************************************************************************************Handling Agent Commissions **************************************************8********/
		
		
		
		/*************************************************************************Anniversary Payments*********************************************************/
		/*Added by bhagya on May 26th,2014
		 * method for editing a reconcilation page
		 */
		@RequestMapping(value="/admin/editreconcile.do",method=RequestMethod.GET)
		public String initReconcileCommissions(Map<String, Object>map,@RequestParam(value="message",required=false)String message,
				@RequestParam("orderId")Integer orderId){
			log.info("inside initReconcileCommissions()");
			try{
				
				OrdersDto order=this.customerService.getReconcileOrdersByOrderId(orderId);
				
				map.put("order", order);
				map.put("message", message);
				
				return "admin/editReconcile";
				
			}
			catch(Exception e){
				String mes="Error while initialising Reconcile Commissions Updation";
				log.error(message+" "+e.toString());
				map.put("message",	mes);
				return "error";
			}
		}
		
		
		@RequestMapping(value="/admin/editreconcile.do",method=RequestMethod.POST)
		public String editReconcileCommissions(Map<String, Object>map,@RequestParam("upfrontCommission")Double upCom1,@RequestParam("upfrontCommission2") Double upCom2,
				@RequestParam("upfrontCommission3")Double upCom3,@RequestParam("upfrontPaidDate") Date upDate1,@RequestParam("upfrontPaidDate2") Date upDate2,
				@RequestParam("upfrontPaidDate3") Date upDate3,@RequestParam("id")Integer orderId,@RequestParam("customerId")Integer customerId){
		log.info("inside editReconcileCommissions()");
		try{
			Integer result = this.customerService.saveorReconcileCommissions(upCom1,upCom2,upCom3,upDate1,upDate2,upDate3,orderId);
			if(result>0){
				return "redirect:/admin/reconcile.do?customerId="+customerId;
			}
			else{
				throw new Exception();
			}
		}
		
		catch(Exception e){
			
			String  message="Error while Updating  Reconcile Commissions";
			log.error(message+" "+e.toString());
			map.put("message", message);
			return "error";
			
		}
		
		}
}
