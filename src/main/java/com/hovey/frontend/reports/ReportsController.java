package com.hovey.frontend.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hovey.backend.admin.exception.AgentCommissionNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.supplier.exception.SupplierNotFoundException;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.user.exception.HoveyUserNotFoundException;
import com.hovey.frontend.admin.dto.AgentOrderDto;
import com.hovey.frontend.admin.service.AdminService;
import com.hovey.frontend.admin.service.CommissionService;
import com.hovey.frontend.agent.dto.AgentCommissionsDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.renewals.service.RenewalService;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.supplier.dto.SupplierReportsDto;
import com.hovey.frontend.supplier.service.SupplierService;
import com.hovey.frontend.user.dto.HoveyUserDto;





@Controller
public class ReportsController {
	
	private static Logger log=Logger.getLogger(ReportsController.class);
	
	@Resource(name="adminService")
	private AdminService adminService;
	
	@Resource(name="supplierService")
	private SupplierService supplierService;
	
	@Resource(name="renewalService")
	private RenewalService renewalService;
	
	@Resource(name="commissionService")
	private CommissionService commissionService;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);

	    // true passed to CustomDateEditor constructor means convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	
	
 /****************************************************Commission Reports*********************************************************************************************************/
	
	@RequestMapping(value="/reports/commissionsreports.do",method=RequestMethod.GET)
	public String showCommissionReportPage(Map<String, Object>map){
		log.info("inside showCommissionReportsPage()");
		try{
			return "reports/pipeline";
		}
		catch(Exception e){
			String message="Error while intitating Commission Reports";
			map.put("message", message);
			log.error(message+" "+e.toString());
			return "error";
		}		
	}
	
	
	
	@RequestMapping(value="/reports/getcommissionreports.do",method=RequestMethod.GET)
	public String getCommissionReports( @RequestParam("term") String term,Map<String, Object> map,HttpServletResponse response){
		log.info("getCommissionReports()");
		JRDataSource jrDataSource;
		try{
		
			ArrayList<OrdersDto> ordersList=this.adminService.getOrdersByTerm(term);
			for(OrdersDto order:ordersList){
				Double netCommission=order.getUpfrontCommission()-order.getCommission();
				if(netCommission>0){
					order.setNetCommission(netCommission);
				}
				else{
					order.setNetCommission(netCommission);
				}				
			}
			jrDataSource=new JRBeanCollectionDataSource(ordersList);
			map.put("datasource", jrDataSource);
			System.out.println("Outside Reports");
			return "pdfReport";					
		}
		catch (OrderNotFoundException e) {
			String message="No Orders found this "+term;
			map.put("message", message);
			return "error";
		}
		catch(Exception e){
			String message="No Orders Found to Show Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	@RequestMapping(value="/reports/getcommissionreports.do",method=RequestMethod.POST)
	public String getCommissionReportsByDateRange(@RequestParam(value="startDate",required=false) Date startDate, @RequestParam(value="endDate",required=false) Date endDate,Map<String, Object>map
			, HttpServletResponse response,@RequestParam(value="export",required=false)String export){
		log.info("inside getReportsByDateRange()");
		
		JRDataSource jrDataSource;		
		try{
			ArrayList<OrdersDto> ordersList=null;
			ordersList=this.adminService.getOrdersByPaidDateinDateRange(startDate, endDate);
			for(OrdersDto order:ordersList){
				Double netCommission=order.getUpfrontCommission()-order.getCommission();
				if(netCommission>0){
					order.setNetCommission(netCommission);
				}
				else{
					order.setNetCommission(netCommission);
				}				
			}			
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, OrdersDto> commissionData=new LinkedHashMap<String, OrdersDto>();
				int i=0;
				for(OrdersDto order:ordersList){
					
					commissionData.put("order"+i, order);
					i++;
				}
				map.put("orders", commissionData);				
				return "commissionXlsReport";
			}
			else{
				jrDataSource=new JRBeanCollectionDataSource(ordersList);
				map.put("datasource", jrDataSource);
				
				return "pdfReport";	
			}
				
		}
		catch(OrderNotFoundException e){
			String message="No Orders found between the selected Date Range";
			map.put("message", message);
			return "error";			
		}
		catch(Exception e){
			String message="No Orders Found to Show Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
/************************************************************************************************************************************************************************************************************************************************************/


/***************************************************Supplier Reports..**************************************************************************************************************************************/
	/*
	 * For Displaying the Page..
	 */
	
	@RequestMapping(method=RequestMethod.GET,value="/reports/supplierreports.do")
	public String showSupplierReportsPage(Map<String, Object>map){
		log.info("inside showSupplierReportsPage()");
		try{
			ArrayList<SupplierDto> supplierDtos=this.supplierService.getSuppliersFromDAO();
			ArrayList<String> businessNames=this.adminService.getBusinessNames();
			if(!supplierDtos.isEmpty()){
				map.put("suppliers", supplierDtos);
			}
			else{
				throw new SupplierNotFoundException();
			}	
			map.put("businessNames", businessNames);
			return "reports/suppliers";
		}
		catch(SupplierNotFoundException e){
			log.error("No Suppliers found yet");
			return "reports/suppliers";
		}
		catch(Exception e){
			String message="Error while initiating Supplier Reports";
			map.put("message",message);
			return "error";
		}
	}
	
	
	/*
	 * For Handling Term Supplier Reports
	 */
	@RequestMapping(method=RequestMethod.GET,value="/reports/getsupplierreports.do")
	public String getSupplierReports(@RequestParam("term") String term,Map<String, Object>map,@RequestParam(value="export",required=false)String export){
		log.info("inside getSupplierReports()");
		try{
			ArrayList<SupplierReportsDto> reportDtos=this.supplierService.getSupplierReportsByTerm(term);
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, SupplierReportsDto> supplierData=new LinkedHashMap<String, SupplierReportsDto>();
				int i=0;
				for(SupplierReportsDto order:reportDtos){					
					supplierData.put("order"+i, order);
					i++;
				}
				map.put("suppliers", supplierData);				
				return "supplierXlsReport";
			}
			else{
				JRDataSource jrDataSource=new JRBeanCollectionDataSource(reportDtos);				
				map.put("datasource", jrDataSource);
				return "supplierReport";			
			}
					
		}
		catch(SupplierReportsNotFoundException e){
			String message="No Supplier Reports found for this "+term;
			map.put("message", message);
			return "error";
		}
		catch(Exception e){
			String message="No Reports Found to Show Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	
	/*for Handling Supplier Reports By Range..
	 * 
	 */
	@RequestMapping(method=RequestMethod.POST,value="/reports/getsupplierreports.do")
	public String getSupplierReportsByRange(@RequestParam(value="startDate",required=false)Date startDate,@RequestParam(value="endDate", required=false)Date endDate,
			@RequestParam( value="supplier",required=false)String supplier,Map<String, Object> map,@RequestParam(value="export",required=false)String export){
		log.info("inside getSupplierReportsByRange()");
		try{
			ArrayList<SupplierReportsDto> reportDtos=this.supplierService.getSupplierReportsBetweenDaysFromDao(startDate, endDate, supplier);
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, SupplierReportsDto> supplierData=new LinkedHashMap<String, SupplierReportsDto>();
				int i=0;
				for(SupplierReportsDto order:reportDtos){					
					supplierData.put("order"+i, order);
					i++;
				}
				map.put("suppliers", supplierData);				
				return "supplierXlsReport";
			}
			else{
				JRDataSource jrDataSource=new JRBeanCollectionDataSource(reportDtos);
				map.put("datasource", jrDataSource);
				return "supplierReport";		
			}			
		}
		catch(SupplierReportsNotFoundException e){
			String message="No Supplier Reports found";
			map.put("message", message); e.printStackTrace();
			return "error";
		}
		catch(Exception e){
			String message="No Reports Found to Show Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	

	@RequestMapping(method=RequestMethod.POST,value="/reports/getsupplierreportsofaccount.do")
	public String getSupplierReportsOfAccountByRange(@RequestParam(value="startDate",required=false)Date startDate,@RequestParam(value="endDate", required=false)Date endDate,
			@RequestParam( value="businessName",required=false)String businessName,Map<String, Object> map,@RequestParam(value="export",required=false)String export){
		log.info("inside getSupplierReportsByRange()");
		try{
			ArrayList<SupplierReportsDto> reportDtos=this.supplierService.getSupplierReportsOfAccountBetweenDaysFromDao(startDate, endDate, businessName);
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, SupplierReportsDto> supplierData=new LinkedHashMap<String, SupplierReportsDto>();
				int i=0;
				for(SupplierReportsDto order:reportDtos){					
					supplierData.put("order"+i, order);
					i++;
				}
				map.put("suppliers", supplierData);				
				return "supplierXlsReport";
			}
			else{
				JRDataSource jrDataSource=new JRBeanCollectionDataSource(reportDtos);
				map.put("datasource", jrDataSource);
				return "supplierReport";		
			}
		}
		catch(SupplierReportsNotFoundException e){
			String message="No Supplier Reports found";
			map.put("message", message); e.printStackTrace();
			return "error";
		}
		catch(Exception e){
			String message="No Reports Found to Show Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}	
	
	
		

	@RequestMapping(method=RequestMethod.POST,value="/reports/getsupplierreportsnotinpipeline.do")
	public String getSupplierReportsNotUpdatedToPipeliene(@RequestParam(value="startDate",required=false)Date startDate,@RequestParam(value="endDate", required=false)Date endDate,
			@RequestParam( value="supplier",required=false)String supplier,Map<String, Object> map,@RequestParam(value="export",required=false)String export){
		log.info("inside getSupplireReportsNotUpdatedToPipeline()");
		try{
			ArrayList<SupplierReportsDto> reportDtos=this.supplierService.getSupplierReportsNotinPipeline(startDate, endDate, supplier);
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, SupplierReportsDto> supplierData=new LinkedHashMap<String, SupplierReportsDto>();
				int i=0;
				for(SupplierReportsDto order:reportDtos){					
					supplierData.put("order"+i, order);
					i++;
				}
				map.put("suppliers", supplierData);				
				return "supplierXlsReport";
			}
			else{
				JRDataSource jrDataSource=new JRBeanCollectionDataSource(reportDtos);
				map.put("datasource", jrDataSource);
				return "supplierReport";		
			}
		}
		catch(SupplierReportsNotFoundException e){
			String message="No Supplier Reports found";
			map.put("message", message); e.printStackTrace();
			return "error";
		}
		catch(Exception e){
			String message="No Reports Found to Show Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
/**********************************************************************************************************************************************************************/

	
/****************************************Agent Reports*******************************************************************************************************************************/
	/*
	 * Displays a page for Agent Reports
	 */
	@RequestMapping(value="/reports/agentreports.do",method=RequestMethod.GET)
	public String showAgentReportsPage(Map<String, Object> map){
		log.info("inside showAgentSReportsPage()");
		try{
			ArrayList<HoveyUserDto> agents=this.adminService.getAgentsFromDao();
			map.put("agents", agents);
			return "reports/agents";			
		}
		catch(Exception e){
			String message="Error in Initiating Agent Reports";
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	/*
	 * for getting Agent Reports By Term..
	 */
	@RequestMapping(value="/reports/getagentreports.do",method=RequestMethod.GET)
	public String getAgentReportsOfTerm(@RequestParam("term") String term,Map<String, Object> map,@RequestParam(value="export",required=false)String export){
		log.info("inside getAgentReportsOfTerm()");
		try{			
			ArrayList<AgentOrderDto> agents=this.adminService.getAgentReporsByTerm(term);
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, AgentOrderDto> agentData=new LinkedHashMap<String, AgentOrderDto>();
				int i=0;
				for(AgentOrderDto order:agents){					
					agentData.put("order"+i, order);
					i++;
				}
				map.put("agents", agentData);				
				return "agentXlsReport";
			}
			else{
				JRDataSource jrDataSource=new JRBeanCollectionDataSource(agents);
				map.put("datasource", jrDataSource);
				return "agentReport";			
			}
		}
		catch(HoveyUserNotFoundException e){
			log.error("No Agent Found "+e.toString());
			String message="No Agent found";
			map.put("message", message); e.printStackTrace();
			return "error";
		}
		catch(OrderNotFoundException e){
			log.error("No Orders Found "+e.toString());
			String message="No Orders found";
			map.put("message", message); e.printStackTrace();
			return "error";
		}
		catch(Exception e){
			String message="Error occured while fetching Agent Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	/*
	 * for 
	 *Getting Agent Reports By Range of Dates
	 */	
	@RequestMapping(value="/reports/getagentreports.do",method=RequestMethod.POST)
	public String getAgentReportsByRange(@RequestParam("startDate") Date startDate,@RequestParam("endDate") Date endDate,Map<String, Object> map,
			@RequestParam(value="export",required=false)String export){
		log.info("inside getAgentReportsByRange()");
		try{			
			ArrayList<AgentOrderDto> agents=this.adminService.getAgentReporsByRange(startDate, endDate);
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, AgentOrderDto> agentData=new LinkedHashMap<String, AgentOrderDto>();
				int i=0;
				for(AgentOrderDto order:agents){					
					agentData.put("order"+i, order);
					i++;
				}
				map.put("agents", agentData);				
				return "agentXlsReport";
			}
			else{
				JRDataSource jrDataSource=new JRBeanCollectionDataSource(agents);
				map.put("datasource", jrDataSource);
				return "agentReport";			
			}
		}
		catch(HoveyUserNotFoundException e){
			log.error("No Agent Found "+e.toString());
			String message="No Agent found";
			map.put("message", message); e.printStackTrace();
			return "error";
		}
		catch(OrderNotFoundException e){
			log.error("No Orders Found "+e.toString());
			String message="No Orders found";
			map.put("message", message); e.printStackTrace();
			return "error";
		}
		catch(Exception e){
			String message="Error occured while fetching Agent Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	@RequestMapping(value="/reports/getagentorderreports.do",method=RequestMethod.POST)
	public String getAgentReports(@RequestParam("startDate") Date startDate,@RequestParam("endDate") Date endDate,@RequestParam("agent")
	String agentNumber,Map<String,Object> map,@RequestParam(value="export",required=false)String export){
		log.info("inside getAgentReports()");
		try{
			ArrayList<OrdersDto> agentOrders=this.adminService.getOrdersByRangeOfAgent(startDate, endDate, agentNumber);
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, OrdersDto> agentData=new LinkedHashMap<String, OrdersDto>();
				int i=0;
				for(OrdersDto order:agentOrders){					
					agentData.put("order"+i, order);
					i++;
				}
				map.put("agents", agentData);				
				return "agentOrderXlsReport";
			}
			else{
				JRDataSource jrDataSource=new JRBeanCollectionDataSource(agentOrders);
				map.put("datasource", jrDataSource);
				return "agentOrderReport";
			}
		}
		catch(OrderNotFoundException e){
			String message="No Orders found between the selected Date Range";
			map.put("message", message);
			return "error";			
		}
		catch(Exception e){
			String message="No Orders Found to Show Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	/*
	 * Displays a page for Agent Commission Reports
	 */
	@RequestMapping(value="/reports/getagentcommissionreports.do",method=RequestMethod.GET)
	public String showAgentCommissionReportsPage(Map<String, Object> map){
		log.info("inside showAgentSReportsPage()");
		try{
			ArrayList<HoveyUserDto> agents=this.adminService.getAgentsFromDao();
			map.put("agents", agents);
			return "reports/agentCommissions";			
		}
		catch(Exception e){
			String message="Error in Initiating Agent Reports";
			map.put("message", message);
			return "error";
		}
	}
	
	
	@RequestMapping(value="/reports/getagentcommissionreports.do",method=RequestMethod.POST)
	public String getAgentCommissionsReports(@RequestParam(value="week",required=false)Integer week,@RequestParam(value="year",required=false) Integer year,
			@RequestParam("agent")String agent,Map<String, Object> map,@RequestParam(value="export",required=false)String export){
		log.info("inside getAgentCommissionsReports()");
		try{
			ArrayList<AgentCommissionsDto> commissionDtos=this.commissionService.getAgentCommissionsOfaWeekByAgent(week, agent,year);	
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, AgentCommissionsDto> agentData=new LinkedHashMap<String, AgentCommissionsDto>();
				int i=0;
				for(AgentCommissionsDto order:commissionDtos){					
					agentData.put("order"+i, order);
					i++;
				}
				map.put("agents", agentData);				
				return "agentCommissionXlsReport";
			}
			else{
				JRDataSource jrDataSource=new JRBeanCollectionDataSource(commissionDtos);
				map.put("datasource", jrDataSource);
				return "agentCommissionReport";
			}
		}
		catch(AgentCommissionNotFoundException e){
			String message="No Agent Commissions Found";
			map.put("message", message);
			return "error";			
		}
		catch(Exception e){
			String message="Error while Generating Agent Commissions Report";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	
/********************************************************************************************/
	

	
	
/****************************************RENEWAL REPORTS*****************************************************************************************/	
	
	/*
	 * Displays Renewal Report Page..
	 */
	@RequestMapping(value="/reports/renewalreports.do",method=RequestMethod.GET)
	public String displayRenewalReportsPage(Map<String, Object>map){
		log.info("inside dispalyRenewalReportsPAge()");
		try{
			return "reports/renewals";
		}
		catch(Exception e){
			String message="Error while initiating Renewal Reports";
			log.error(message+e.toString());map.put("message", message);
			return "error";
		}
	}
	
	/*
	 * Generating Renewal Report...
	 * modified by bhagya i.e,start date and end date,supplier added on may 05th,2014
	 */
	@RequestMapping(value="/reports/getrenewalreports.do",method=RequestMethod.GET)
	public String getRenewalReport(Map<String, Object>map,@RequestParam(value="startDate",required=false)Date startDate,
			@RequestParam(value="endDate", required=false)Date endDate,
			@RequestParam(value="export",required=false)String export,
			@RequestParam(value="supplier",required=false)String supplier) throws SupplierNotFoundException{
		log.info("inside getRenewalReport()");
		try{
			ArrayList<OrdersDto> renewalOrders=this.renewalService.getRenewalOrders(startDate, endDate, null, 0, supplier, null, null,null);
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, OrdersDto> renewalData=new LinkedHashMap<String, OrdersDto>();
				int i=0;
				for(OrdersDto order:renewalOrders){					
					renewalData.put("order"+i, order);
					i++;
				}
				map.put("renewals", renewalData);				
				return "renewalXlsReport";
			}
			else{
				JRDataSource jrDataSource=new JRBeanCollectionDataSource(renewalOrders);
				map.put("datasource", jrDataSource);
				return "renewalReport";			
			}
		}
		catch(OrderNotFoundException e){
			String message="No Orders found for Renewals between the selected Date Range";
			map.put("message", message);
			return "error";			
		}
		catch(Exception e){
			String message="No Orders Found to Show Reports";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}	
	
	/*****************************************************************Anniversary Payment Reports***************************************************************/
	/*added by bhagya on may 28th,2014*/
	@RequestMapping(value="/reports/anniversaryreports.do",method=RequestMethod.GET)
	public String initAnniversaryPayments(Map<String, Object> map){
		log.info("inside initAnniversaryPayments()");
		try{


			return "reports/anniversaryPayments";			
		}
		catch(Exception e){
			String message="Error in Initiating AnniversaryPayment Reports";
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	/*
	 * Added by Jeevan on May 29, 2014
	 * Method to creates Reports related to All Anniversary Payments
	 */
	@RequestMapping(value="/reports/getanniversarypayments.do",method=RequestMethod.GET)
	public String getAnniversaryPayments(@RequestParam("output") String export,Map<String, Object>map){
		log.info("inside getAnniversaryPayments()");
		JRDataSource jrDataSource;	
		try{
			ArrayList<OrdersDto> orderDtos=this.adminService.getAllAnniversaryPayments();
			for(OrdersDto order:orderDtos){
				Double netCommission=order.getUpfrontCommission()-order.getTermCommission();
				if(netCommission>0){
					order.setNetCommission(netCommission);
				}
				else{
					order.setNetCommission(netCommission);
				}				
			}			
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, OrdersDto> commissionData=new LinkedHashMap<String, OrdersDto>();
				int i=0;
				for(OrdersDto order:orderDtos){
					
					commissionData.put("order"+i, order);
					i++;
				}
				map.put("orders", commissionData);				
				return "anniversaryPaymentXlsReport";
			}
			else{
				jrDataSource=new JRBeanCollectionDataSource(orderDtos);
				map.put("datasource", jrDataSource);
				
				return "anniversaryReport";	
			}
		}
		catch(OrderNotFoundException e){
			String message="No Anniversary Payments Found";
			map.put("message", message);
			return "error";			
		}
		catch(Exception e){
			String message="Error While Displaying Anniversary Payments";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	/*
	 * Added by Jeevan on May 29, 2014
	 * Method to creates Reports related to All Anniversary Payments
	 */
	@RequestMapping(value="/reports/getanniversarypaymentsdues.do",method=RequestMethod.GET)
	public String getDueAnniversaryPayment2s(@RequestParam("output") String export,Map<String, Object>map){
		log.info("inside getAnniversaryPayments()");
		JRDataSource jrDataSource;	
		try{
			ArrayList<OrdersDto> orderDtos=this.adminService.getAllAnniversaryPaymentsDuetoPay(null,null);
			for(OrdersDto order:orderDtos){
				Double netCommission=order.getUpfrontCommission()-order.getTermCommission();
				if(netCommission>0){
					order.setNetCommission(netCommission);
				}
				else{
					order.setNetCommission(netCommission);
				}				
			}			
			if(null!=export && export.equalsIgnoreCase("excel")){
				Map<String, OrdersDto> commissionData=new LinkedHashMap<String, OrdersDto>();
				int i=0;
				for(OrdersDto order:orderDtos){
					
					commissionData.put("order"+i, order);
					i++;
				}
				map.put("orders", commissionData);				
				return "anniversaryPaymentXlsReport";
			}
			else{
				jrDataSource=new JRBeanCollectionDataSource(orderDtos);
				map.put("datasource", jrDataSource);
				
				return "anniversaryDueReport";	
			}
		}
		catch(OrderNotFoundException e){
			String message="No Anniversary Payments Found";
			map.put("message", message);
			return "error";			
		}
		catch(Exception e){
			String message="Error While Displaying Anniversary Payments";
			log.error(message +" "+e.toString());
			e.printStackTrace();
			map.put("message", message);
			return "error";
		}
	}
	
	
}
