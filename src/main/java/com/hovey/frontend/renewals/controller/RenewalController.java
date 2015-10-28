package com.hovey.frontend.renewals.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hovey.backend.agent.exception.ContractTypeNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.supplier.exception.SupplierNotFoundException;
import com.hovey.frontend.admin.service.AdminService;
import com.hovey.frontend.agent.dto.ContractTypeDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.renewals.service.RenewalService;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.supplier.service.SupplierService;

/**
 *
 * @author JEEVAN
 * Controllere for managing Renewals.
 */

@Controller
public class RenewalController {
	
	private static Logger log=Logger.getLogger(RenewalController.class);
	
	@Resource(name="renewalService")
	private RenewalService renewalService;
	
	@Resource(name="supplierService")
	private SupplierService supplierService;
	
	@Resource(name="adminService")
	private AdminService adminService;
	
	/*
	 * 
	 *  for Handling conditions when Date may be null, useful while editing a Pipeline, where date may be entered for certain Orders..
	 *  
	 *  */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);
	    // true passed to CustomDateEditor constructor means convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
		
	
	//displays all the Orders which are Close to Renewal.....
	//added StartDate,endDate,contractType  and status  by bhagya on May 2nd,2014
	
	@RequestMapping(method=RequestMethod.GET,value="/admin/renewals.do")
	public String getRenewalOrders(Map<String, Object> map,@RequestParam(value="startDate",required=false)Date startDate,
			@RequestParam(value="endDate", required=false)Date endDate,
			@RequestParam(value="page",required=false,defaultValue="0")Integer pageNumber,@RequestParam(value="range",required=false,defaultValue="20")Integer range,
			@RequestParam(value="output",required=false,defaultValue="nil")String output,@RequestParam(value="status",required=false)String status,
			@RequestParam(value="contractType",required=false)String contractType,
			@RequestParam(value="supplier",required=false)String supplier ,
			@RequestParam(value="serviceType",required=false)String serviceType) throws SupplierNotFoundException {
		log.info("inside getRenewalOrders()");
		ArrayList<SupplierDto>suppliers=null;
		suppliers=this.supplierService.getSuppliersFromDAO();
		// added contract types by bhagya on may 09th,2014
		ArrayList<ContractTypeDto>contractTypes=null;
		try{
			//if at all Excel is Required
			if(output.equalsIgnoreCase("excel")){
				return "excel";
			}
			else{
				//	int pagesNeeded=this.renewalService.getTotalNoofRenewalPages(noticePeriodMonths, range, pageNumber);
				suppliers=this.supplierService.getSuppliersFromDAO();
				map.put("suppliers",suppliers);
				// added contract types by bhagya on may 09th,2014
				contractTypes=this.adminService.getContractTypesFromDao();
				map.put("contractTypes", contractTypes);
			//	ArrayList<Integer> renewalsCount=this.renewalService.getRenewalsCountInfo(startDate, endDate, range, pageNumber, supplier,status,contractType,serviceType);
				
				ArrayList<OrdersDto> orders=this.renewalService.getRenewalOrders(startDate, endDate,range,pageNumber,supplier,status,contractType,serviceType);
				
				int totalRenewals=orders.get(0).getTotalResults();
				int result=totalRenewals/range;
				System.out.println(totalRenewals+"SD");
				int pagesNeeded=0;
				if(totalRenewals%range>0){			
					pagesNeeded=result+1;
				}
				else{
					totalRenewals=result;
				}
				System.out.println("pas "+pagesNeeded);
				int firstOrder=range*pageNumber+1;
				int lastOrder;
					
				if(!orders.isEmpty()){
					lastOrder=this.getOrdersOfLastPageFromRangeandTotalOrders(range, totalRenewals, firstOrder);
					map.put("first", firstOrder);
					map.put("total", totalRenewals);
					map.put("last", lastOrder);
					map.put("renewals", orders);
					map.put("range", range);
					map.put("page",pageNumber);
					map.put("end", pagesNeeded);
					map.put("supplier", supplier);
					map.put("status", status);
					map.put("startDate", startDate);
					map.put("endDate", endDate);
					map.put("contractType", contractType);
					map.put("serviceType", serviceType);
					
					return "admin/renewalOrders";
				}
				else{
					throw new OrderNotFoundException();
				}
				
			}			
		}
		catch(OrderNotFoundException e){
			//e.printStackTrace();
			log.error("No Orders For Renewals "+e.toString());
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("contractType", contractType);
			map.put("supplier", supplier);
			map.put("status", status);
			String message="No Orders Available For Renewals";
			map.put("message", message);
			map.put("suppliers",suppliers);	
			map.put("serviceType", serviceType);
			return "admin/renewalOrders";			
		}
		catch(SupplierNotFoundException e){
			e.printStackTrace();
			log.error("No Suppliers For Renewals "+e.toString());
			String message="No Suppliers Available For Renewals";
			map.put("message", message);
			
			return "admin/renewalOrders";			
		}
		catch(ContractTypeNotFoundException e){
			e.printStackTrace();
			log.error("No contractTypes For Renewals "+e.toString());
			String message="No contractTypes Available For Renewals";
			map.put("message", message);
			
			return "admin/renewalOrders";			
		}
		catch(Exception e){
			e.printStackTrace();
			String message="Error while Getting Order closed to Renewal";
			log.error(message +" "+e.toString());
			map.put("message", message);
			return "error";
		}
		
	}
	
	private int getOrdersOfLastPageFromRangeandTotalOrders(int range,int totalOrders,int firstOrder){
		log.info("inside getOrdersOfLastPageFromRangeandTotalOrders()	");
		int lastOrder=firstOrder+range-1;
		if(lastOrder>totalOrders){
			lastOrder=(totalOrders%range)+firstOrder-1;
		}
		return lastOrder;
	}
	
}
