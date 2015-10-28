package com.hovey.frontend.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hovey.backend.agent.exception.ContractTypeNotFoundException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.exception.StateNotFoundException;
import com.hovey.backend.supplier.exception.SupplierNotFoundException;
import com.hovey.backend.user.exception.HoveyUserNotFoundException;
import com.hovey.frontend.admin.dto.ChartOrderDataDto;
import com.hovey.frontend.admin.dto.LineChartDto;
import com.hovey.frontend.admin.dto.PipelineSearchDto;
import com.hovey.frontend.admin.dto.UtilityChartDto;
import com.hovey.frontend.admin.service.AdminService;
import com.hovey.frontend.agent.dto.ContractTypeDto;
import com.hovey.frontend.agent.dto.OrdersDto;
import com.hovey.frontend.agent.dto.StateDto;
import com.hovey.frontend.agent.dto.UtilityDto;
import com.hovey.frontend.agent.service.AgentService;
import com.hovey.frontend.agent.service.DealSheetService;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.supplier.service.SupplierService;
import com.hovey.frontend.user.dto.HoveyUserDto;
import com.hovey.frontend.user.service.UserService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


/**
 * 
 * @author KNS-JEEVAN
 *
 *
 *Controller for All Admin related Tasks.
 */
@Controller
public class AdminController {
	

	private static Logger log=Logger.getLogger(AdminController.class);
	
	private int pageSize;
	@Resource(name="adminService")
	private AdminService adminService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="dealSheetService")
	private DealSheetService dealSheetService;
	
	@Resource(name="supplierService")
	private SupplierService supplierService;
	
	@Resource(name="agentService")
	private AgentService agentService;
	
	
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
		

	
	
	
	/*
	 * created by Jeevan on July 31,2013..
	 * Method to populate Dashboard with different types of Charts..
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/admindashboard.do")
	public String getDashboard(Map<String, Object>map){
		log.info("inside getDashboard()");		
		//int totalOrders=0;		
		try{  	
			//Pie Charts
				Map<String, Object>chartsMap=this.adminService.getPieChartsForDashBoard();
				ArrayList<UtilityChartDto> utilCharts=(ArrayList<UtilityChartDto>) chartsMap.get("utils");					
				ArrayList<UtilityChartDto> stateCharts=(ArrayList<UtilityChartDto>) chartsMap.get("states");
				ArrayList<UtilityChartDto> supplierCharts=(ArrayList<UtilityChartDto>) chartsMap.get("suppliers");			
		
			//Line Chart of This Week and Last Year this weeks Orders..
				ArrayList<LineChartDto> weekChartDtos=this.adminService.getWeeksSalesSummary();
				ArrayList<LineChartDto> monthChartDtos=this.adminService.getMonthsSalesSummary();
				/*Map<String,Object> yearlyMap=this.adminService.getYearSalesSummary();
				ArrayList<ChartOrderDataDto> yearChartDtos=(ArrayList<ChartOrderDataDto>) yearlyMap.get("yearOrdersChart");
				ArrayList<ChartOrderDataDto> yearResChartDtos=(ArrayList<ChartOrderDataDto>) yearlyMap.get("yearResOrdersChart");*/
				
				ArrayList<ChartOrderDataDto> yearChartDtos=this.adminService.getYearSalesSummary();
				Map<String, Object> summary=this.adminService.getSummaryDetails();		
		
				map.put("yearKwh", summary.get("yearKwh"));
				map.put("yearCommission", summary.get("yearCommission"));
				map.put("weekKwh", summary.get("weekKwh"));
				map.put("weekCommission", summary.get("weekCommission"));
				map.put("lastYearWeekKwh", summary.get("lastYearWeekKwh"));
				map.put("lastYearWeekCommission", summary.get("lastYearWeekCommission"));
				map.put("monthKwh", summary.get("monthKwh"));
				map.put("monthCommission", summary.get("monthCommission"));
				map.put("lastYearMonthKwh", summary.get("lastYearMonthKwh"));
				map.put("lastYearMonthCommission", summary.get("lastYearMonthCommission"));
				map.put("avgKwh", summary.get("avgKwh"));
				map.put("totalOrders", summary.get("totalOrders"));
				map.put("weekSales", weekChartDtos);	
				map.put("monthSales", monthChartDtos);
				map.put("yearSales", yearChartDtos);
				
				map.put("yearResKwh", summary.get("yearResKwh"));
				map.put("yearResCommission", summary.get("yearResCommission"));
				map.put("avgResKwh", summary.get("avgResKwh"));
				map.put("totalResOrders", summary.get("totalResOrders"));
				
			
			map.put("suppliers", supplierCharts);
			map.put("utilities", utilCharts);
			map.put("states", stateCharts);
			return "admin/admin";
		}//end of try
		catch(OrderNotFoundException e){
			map.put("message", "No Orders Found");
			return "admin/admin";
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	
	
	
	
	
	
   /* ***************For Getting All Support Dtos for Pipeline, and Searches, added to improve reusablity********************************/		
		
		public Map<String, Object> getAllOrdersRelatedData(){
			log.info("inside getAllOrdersRelatedData()");
		    Map<String, Object> ordersMap=new HashMap<String, Object>();		    
		     try{
		    	 ArrayList<SupplierDto> suppliers=this.supplierService.getSuppliersFromDAO();
		    	 ordersMap.put("suppliers", suppliers);
		     }
		    catch(SupplierNotFoundException e){
		    	log.error("No Supplier Found"+e.toString());
		    }
		     
		    try{
		    	ArrayList<HoveyUserDto>agents=this.adminService.getAgentsFromDao();
		    	ordersMap.put("agents",agents);
		    }
		    catch(Exception e){
		    	log.error("Error in Getting Agent Details()");
		    }
		    
		    try{
		    	ArrayList<String>businessNames=this.adminService.getBusinessNames();
		    	ordersMap.put("businessNames", businessNames);
		    }
		    catch(Exception e){
		    	log.error("Error in Getting BusinessName()"+e.toString());
		    }
		    try{
		    	ArrayList<UtilityDto> utilities=this.dealSheetService.getUtilitiesFromDb();
		    	ordersMap.put("utilities", utilities);
		    }
		    catch (Exception e) {
				log.error("Error in getting Utilities"+e.toString());
			}
		    try{
		    	ArrayList<StateDto> states=this.dealSheetService.getStatesFromDao();
		    	ordersMap.put("states", states);
		    }
		    catch(Exception e){
		    	log.error("Error while getting States "+e.toString());
		    }
		    //added by bhagya on May 07th,2014
		    try{
		    	ArrayList<ContractTypeDto> contractTypes=this.adminService.getContractTypesFromDao();
		    	ordersMap.put("contractTypes", contractTypes);
		    }
		    catch(Exception e){
		    	log.error("Error while getting contract types"+e.toString());
		    }
		    return ordersMap;			
		}
		
		
		
	
	
	
	
	
	/*
	 *             SHOWS PIPELINE
	 *             
	 *         Pipeline.do, Shows the Entire List of Orders ... with multiple Search Options and Pagination..
	 *         1. get ORders .
	 *         2. PipelineSearchDto to facilitate multi Search..
	 *         3. Handles Excel Output..
	 *         4. map.put supplier,agents,business names..
	 * 
	 * 
	 * */
	
	//To View DealSheetOrders......
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/admin/getpipeline.do",method=RequestMethod.GET)
		public String getOrdersforAdmin(Map<String, Object>map,@RequestParam(value="page",required=false)Integer pageNumber,
				@RequestParam(value="sortby",required=false) String sortElement, @RequestParam(value="range",required=false,defaultValue="20")Integer range,
				@RequestParam(value="output",required=false,defaultValue="nil") String output,HttpServletRequest request){
			log.info("getOrdersforAdmin()");
			Map<String, Object> ordersMap=this.getAllOrdersRelatedData();
			ArrayList<SupplierDto> suppliers=(ArrayList<SupplierDto>) ordersMap.get("suppliers");
			ArrayList<HoveyUserDto>agents=(ArrayList<HoveyUserDto>) ordersMap.get("agents");
			ArrayList<String>businessNames=(ArrayList<String>) ordersMap.get("businessNames");
			ArrayList<UtilityDto> utilities=(ArrayList<UtilityDto>) ordersMap.get("utilities");
			ArrayList<StateDto> states=(ArrayList<StateDto>) ordersMap.get("states");
			//added  contract types,by bhagya on May07th 2014
			ArrayList<ContractTypeDto> contractTypes=(ArrayList<ContractTypeDto>) ordersMap.get("contractTypes");
			//modified by bhagya
			if(null!=suppliers){
				map.put("suppliers", suppliers);
			}
			if(null!=agents){
				map.put("agents", agents);
			}
			if(null!=businessNames){
				map.put("businessNames", businessNames);
			}
			if(null!=utilities){
				map.put("utils", utilities);
			}
			if(null!=states){
				map.put("states", states);
			}
			//added  contract types,by bhagya on May07th 2014
			if(null!=contractTypes){
				map.put("contractTypes", contractTypes);
			}
			
			int pagesNeeded;
			try{	
				PipelineSearchDto search=new PipelineSearchDto();
				map.put("search", search);
				if(null==pageNumber){
					pageNumber=0;
				}
				if(null==sortElement){
					sortElement="orderDate";
				}				
				if(output.equalsIgnoreCase("excel")){
					ArrayList<OrdersDto> orderDtos=this.adminService.getOrdersFromDAOForReports();					
					Map<String, OrdersDto> pipelineData=new LinkedHashMap<String, OrdersDto>();
					int i=0;
					for(OrdersDto order:orderDtos){
						
						pipelineData.put("order"+i, order);
						i++;
					}
					map.put("orders", pipelineData);
					return "PipelineExcel";					
				}
				else if(output.equalsIgnoreCase("pdf")){
					ArrayList<OrdersDto> orderDtos=this.adminService.getOrdersFromDAOForReports();					
					for(OrdersDto order:orderDtos){
						Double netCommission=order.getUpfrontCommission()-order.getCommission();
						if(netCommission>0){
							order.setNetCommission(netCommission);
						}
						else{
							order.setNetCommission(netCommission);
						}						
					}
					JRDataSource jrDataSource=new JRBeanCollectionDataSource(orderDtos);
					map.put("datasource", jrDataSource);
					return "pipelineReport";
				}		
				else{	
					pageSize=range;					
					pagesNeeded=this.adminService.findTotalNoOfOrderPages(pageSize);
					ArrayList<OrdersDto> orderDtos=this.adminService.getOrdersFromDAO(pageNumber, sortElement,pageSize);	
					int firstOrder=range*pageNumber+1;
					int totalOrders=this.adminService.getToalOrderRecords();
					int lastOrder;
					if(!orderDtos.isEmpty()){
						lastOrder=this.getOrdersOfLastPageFromRangeandTotalOrders(range, totalOrders, firstOrder);
						map.put("first", firstOrder);
						map.put("total", totalOrders);
						map.put("last", lastOrder);
						map.put("orders", orderDtos);
						map.put("page",pageNumber);
						map.put("end", pagesNeeded);
						map.put("sortby", sortElement);
						map.put("range", range);							
						return "admin/pipeline";
					}
					else{
						String message="No Orders Found";
						map.put("message", message);
						return "admin/pipeline";
					}
				}				
			}
			catch(OrderNotFoundException e){
				log.error("Orders Not Found ");
				String message="No Orders Found";
				map.put("message", message);
				e.printStackTrace();
				return "admin/pipeline";
			}
			catch(Exception e){
				log.error("Error in getting Orders"+e.toString());
				String message="Error in Getting Order Details";
				map.put("message", message);
				e.printStackTrace();
				return "error";
			}			
		}
		
		
		/* ****for Calculating Orders of LAst Page Based on Range and Total Records...... *
		 * 
		 * 
		 * Added on July 19,2013, by Jeevan...
		 * 
		 * 
		 * *****/		
		private int getOrdersOfLastPageFromRangeandTotalOrders(int range,int totalOrders,int firstOrder){
			log.info("inside getOrdersOfLastPageFromRangeandTotalOrders()	");
			int lastOrder=firstOrder+range-1;
			if(lastOrder>totalOrders){
				lastOrder=(totalOrders%range)+firstOrder-1;
			}
			return lastOrder;
		}
		
		
		
		
	
		
	/*
	 * 
	 *                               DISPLAYING ALL DEAL SHEETS.
	 * 
	 * 
	 * */
		
		//Display All Deal Sheets
		@RequestMapping(value="/admin/viewdealsheets.do",method=RequestMethod.GET)
		public String getDealSheets(Map<String, Object>map,@RequestParam(value="page",required=false)Integer pageNumber,@RequestParam(value="range",
		defaultValue="20",required=false)Integer pageSize,@RequestParam(value="sortby",required=false,defaultValue="orderDate")String sortBy,
		@RequestParam(value="searchBy",required=false) String searchBy){
			log.info("inside getDealSheets()");
			int pagesNeeded;
			try{				
				if(null==pageNumber){
					pageNumber=0;
				}
				
				pagesNeeded=this.adminService.findTotalNoOfDealPages(pageSize,searchBy);
				ArrayList<OrdersDto> orderDtos=this.adminService.getDealSheetsofAgentsFromDao(pageNumber,pageSize,sortBy,searchBy);
				int firstDeal=pageSize*pageNumber+1;;
				int totalDeals=this.adminService.getTotalDealSheetsCount(searchBy);
				int lastDeal;
				if(!orderDtos.isEmpty()){
					lastDeal=this.getOrdersOfLastPageFromRangeandTotalOrders(pageSize, totalDeals, firstDeal);
					map.put("first", firstDeal);
					map.put("last", lastDeal);
					map.put("total", totalDeals);
					map.put("orders", orderDtos);
					map.put("page",pageNumber);
					map.put("end", pagesNeeded);
					map.put("sortby", sortBy);
					return "admin/viewDeals";
				}
				else{
					throw new OrderNotFoundException();
				}
			}
			catch(OrderNotFoundException e){
				e.printStackTrace();
				log.error("Orders Not Found for Current Agent");
				String message="No Deals Found";
				map.put("message", message);
				return "admin/viewDeals";
			}
			catch(Exception e){
				log.error("Error while getting DealSheets"+e.toString());
				String message="Error in getting DealSheets ";
				map.put("message", message);
				e.printStackTrace();
				return "error";
			}
		}
		
		
		
		//Editing the Deal Sheet...
		/*
		 *  IT is implemented in DealSheetController Section...
		 *  */
		
		
		
		
		
	/* End     DISPLAY DEAL SHEETS*/	
		
		
	/*
	 * 														STATIC SEARCH on Order Date, ACCOUNT#, Business NAME
	 * 										
	 * */	
		
		// getOrdersByFilter
					@SuppressWarnings("unchecked")
					@RequestMapping(value="/admin/getfilteredorder.do",method=RequestMethod.GET)
					public <T> String getFilteredOrders(@RequestParam("field") String propertyName,@RequestParam("type")String fieldType,@RequestParam("value") T propertyValue,
							@RequestParam(value="page",required=false)Integer pageNumber,Map<String, Object>map,@RequestParam(value="sortby",required=false,defaultValue="orderDate")String sortElement,
							@RequestParam(value="range",required=false,defaultValue="20")Integer range,@RequestParam(value="output",required=false,defaultValue="nil")String output ){
						log.info("inside getFilteredOrders()");
						Map<String, Object> ordersMap=this.getAllOrdersRelatedData();
						ArrayList<SupplierDto> suppliers=(ArrayList<SupplierDto>) ordersMap.get("suppliers");
						ArrayList<HoveyUserDto>agents=(ArrayList<HoveyUserDto>) ordersMap.get("agents");
						ArrayList<String>businessNames=(ArrayList<String>) ordersMap.get("businessNames");
						ArrayList<UtilityDto> utilities=(ArrayList<UtilityDto>) ordersMap.get("utilities");
						ArrayList<StateDto> states=(ArrayList<StateDto>) ordersMap.get("states");
						//added  contract types,by bhagya on May07th 2014
						ArrayList<ContractTypeDto> contractTypes=(ArrayList<ContractTypeDto>) ordersMap.get("contractTypes");
						if(null!=suppliers){
							map.put("suppliers", suppliers);
						}
						if(null!=agents){
							map.put("agents", agents);
						}
						if(null!=businessNames){
							map.put("businessNames", businessNames);
						}
						if(null!=utilities){
							map.put("utils", utilities);
						}
						if(null!=states){
							map.put("states", states);
						}
						//added  contract types,by bhagya on May07th 2014
						if(null!=contractTypes){
							map.put("contractTypes", contractTypes);
						}
						int pagesNeeded=0;
						pageSize=range;
						try{
							if(null==pageNumber){
								pageNumber=0;
							}		
							PipelineSearchDto search=new PipelineSearchDto();
							map.put("search", search);							
							if(output.equalsIgnoreCase("excel")){
								ArrayList<OrdersDto> orderDtos=this.adminService.getOrdersFilteredFromDaoForReports(pageNumber, propertyName, propertyValue, fieldType,sortElement,0);
								Map<String, OrdersDto> pipelineData=new LinkedHashMap<String, OrdersDto>();
								int i=0;
								for(OrdersDto order:orderDtos){									
									pipelineData.put("order"+i, order);
									i++;
								}
								map.put("orders", pipelineData);
								return "PipelineExcel";					
							}
							else if(output.equalsIgnoreCase("pdf")){
								ArrayList<OrdersDto> orderDtos=this.adminService.getOrdersFilteredFromDaoForReports(pageNumber, propertyName, propertyValue, fieldType,sortElement,0);
								for(OrdersDto order:orderDtos){
									Double netCommission=order.getUpfrontCommission()-order.getCommission();
									if(netCommission>0){
										order.setNetCommission(netCommission);
									}
									else{
										order.setNetCommission(netCommission);
									}									
								}
								JRDataSource jrDataSource=new JRBeanCollectionDataSource(orderDtos);
								map.put("datasource", jrDataSource);
								return "pipelineReport";
							}				
							else{
								pagesNeeded=this.adminService.getTotalFilteredOrderPages(pageNumber, propertyName, propertyValue, fieldType, sortElement, range);					
								ArrayList<OrdersDto> orderDtos=this.adminService.getOrdersFilteredFromDao(pageNumber, propertyName, propertyValue, fieldType,sortElement,range);					
								int firstOrder=range*pageNumber+1;
								int totalOrders=this.adminService.getTotalFilterOrderRecords(propertyName, propertyValue, pageNumber, sortElement, range);
								int lastOrder;				
								if(!orderDtos.isEmpty()){
									lastOrder=this.getOrdersOfLastPageFromRangeandTotalOrders(range, totalOrders, firstOrder);
									map.put("first", firstOrder);
									map.put("total", totalOrders);
									map.put("last", lastOrder);
									map.put("orders", orderDtos);
									map.put("field", propertyName);
									map.put("type", fieldType);
									map.put("value", propertyValue);
									map.put("sortby", sortElement);
									map.put("range", range);
									map.put("page",pageNumber);
									map.put("end", pagesNeeded);
									return "admin/pipeline";
								}
								else{
									map.put("field", propertyName);
									map.put("type", fieldType);
									map.put("value", propertyValue);
									return "admin/pipeline";
								}
							}							
						}
						catch(OrderNotFoundException e){
							log.error("No ORDERS FOUND"+e.toString());
							String message="No Records Found";
							map.put("message", message);
							map.put("field", propertyName);
							map.put("type", fieldType);
							map.put("value", propertyValue);
							return "admin/pipeline";							
						}
						catch(Exception e){
							e.printStackTrace();
							log.error("Error while getting DealSheets"+e.toString());
							String message="Error in getting DealSheets ";
							map.put("message", message);
							return "error";
						}			
						
					}
					
					
				/*                        End of Static Search                */
					
					
					
					
				
					
					
					
				
					
					/*
					 * 
					 *  
					 *  							DYNAMIC SEARCH.
					 *  
					 *  1. Makes use of PipelineSearchDto which is binded to the form...
					 *  
					 *  
					 *  
					 *  
					 *  */
					
					//Displays Multi Result Page
							@SuppressWarnings("unchecked")
							@RequestMapping(value="/admin/performsearch.do",method=RequestMethod.GET)
							public String performMultiResultSearch(@ModelAttribute("search") PipelineSearchDto search ,Map<String, Object> map,
									@RequestParam(value="page",required=false,defaultValue="0")Integer pageNumber,
									@RequestParam(value="sortby",required=false,defaultValue="orderDate") String sortElement, @RequestParam(value="range",required=false,defaultValue="20")Integer range,
									@RequestParam(value="output",required=false,defaultValue="nil") String output){
								log.info("inside showMultiSearch()");
								Map<String, Object> ordersMap=this.getAllOrdersRelatedData();
								ArrayList<SupplierDto> suppliers=(ArrayList<SupplierDto>) ordersMap.get("suppliers");
								ArrayList<HoveyUserDto>agents=(ArrayList<HoveyUserDto>) ordersMap.get("agents");
								ArrayList<String>businessNames=(ArrayList<String>) ordersMap.get("businessNames");
								ArrayList<UtilityDto> utilities=(ArrayList<UtilityDto>) ordersMap.get("utilities");
								ArrayList<StateDto> states=(ArrayList<StateDto>) ordersMap.get("states");
								//added  contract types,by bhagya on May07th 2014
								ArrayList<ContractTypeDto> contractTypes=(ArrayList<ContractTypeDto>) ordersMap.get("contractTypes");
								if(null!=suppliers){
									map.put("suppliers", suppliers);
								}
								if(null!=agents){
									map.put("agents", agents);
								}
								if(null!=businessNames){
									map.put("businessNames", businessNames);
								}
								if(null!=utilities){
									map.put("utils", utilities);
								}
								if(null!=states){
									map.put("states", states);
								}
								//added  contract types,by bhagya on May07th 2014
								if(null!=contractTypes){
									map.put("contractTypes", contractTypes);
								}
								
								try{	

									if(output.equalsIgnoreCase("excel")){
										ArrayList<OrdersDto> orders=this.adminService.getMultiSearchResultsFromDao(search, pageNumber,0,sortElement);
										Map<String, OrdersDto> pipelineData=new LinkedHashMap<String, OrdersDto>();
										int i=0;
										for(OrdersDto order:orders){								
											pipelineData.put("order"+i, order);
											i++;
										}
										map.put("orders", pipelineData);
										return "PipelineExcel";							
									}
									else if(output.equalsIgnoreCase("pdf")){
										ArrayList<OrdersDto> orders=this.adminService.getMultiSearchResultsFromDao(search, pageNumber,0,sortElement);
										for(OrdersDto order:orders){
											Double netCommission=order.getUpfrontCommission()-order.getCommission();
											if(netCommission>0){
												order.setNetCommission(netCommission);
											}
											else{
												order.setNetCommission(netCommission);
											}
											
										}
										JRDataSource jrDataSource=new JRBeanCollectionDataSource(orders);
										map.put("datasource", jrDataSource);
										return "pipelineReport";
									}		
									
									else{
										int pagesNeeded=this.adminService.getTotalMultiSearchResultsFromDao(search, pageNumber, range, sortElement);								
										ArrayList<OrdersDto> orders=this.adminService.getMultiSearchResultsFromDao(search, pageNumber,range,sortElement);
										int firstOrder=range*pageNumber+1;
										int totalOrders=this.adminService.getTotalMultiSearchOrderRecords(search, pageNumber, range, sortElement);
										int lastOrder;
										if(!orders.isEmpty()){
											lastOrder=this.getOrdersOfLastPageFromRangeandTotalOrders(range, totalOrders, firstOrder);
											map.put("first", firstOrder);
											map.put("total", totalOrders);
											map.put("last", lastOrder);
											map.put("orders", orders);
											map.put("page",pageNumber);
											map.put("end", pagesNeeded);
											map.put("sortby", sortElement);
											map.put("range", range);							
											return "admin/pipeline";
										}
										else{
											String message="No Records Found";
											map.put("message", message);
											return "admin/pipeline";
										}
									}
								}
								catch(OrderNotFoundException e){
									e.printStackTrace();
									log.error("Orders Not Found ");
									String message="No Records Found";
									map.put("message", message);						
									return "admin/pipeline";
									
								}
								catch(Exception e){
									e.printStackTrace();
									log.error("Error while getting DealSheets"+e.toString());
									String message="Error in getting DealSheets ";
									map.put("message", message);
									return "error";
								}			
								
							}
							
						
					/*                End of DYNAMIC SEARCH          */		
							
							
							
							
							
							
		
		
				
	  /* ********************
	   * 
	   * 
	   * 	                     EDITING PIPELINE								
	   * 
	   * 
	   * 
	   * 
	   * *****************************/		
		 // Editing the Pipeline..
			@ResponseBody
			@RequestMapping(value="/admin/editpipeline.do",method=RequestMethod.POST)
			public void savePipelinewithEditedOrder(@RequestParam(value="sentToSupplier",required=false) Date sentToSupplier,@RequestParam(value="commission",required=false) Double commission,
					@RequestParam(value="dealStartDate",required=false) Date dealStartDate, @RequestParam(value="upfrontCommission",required=false,defaultValue="0.0")Double upfrontCommission,
				@RequestParam(value="upfrontPaidDate",required=false) Date upfrontPaidDate,@RequestParam(value="notes",required=false)String notes, @RequestParam("orderId") Integer orderId,
				@RequestParam("status") String status,@RequestParam("term") String term,@RequestParam("commissionRate") Double commissionRate,@RequestParam("faxReceived") Boolean faxReceived
				,@RequestParam("qa") Boolean QA, @RequestParam("contractType") String contractType,@RequestParam("resAgent") String resAgent){
				log.info("inside savePipelinewithEditedOrder()");
				try{
					
					/*
					 * Modified by Jeevan on July 22,2013 acc to the Client req of displaying started date with "date"..  startDate of String is changed to DealStartDate(date)
					 */
					 Integer result=this.adminService.editPipilineDataSenttoDAO(orderId, sentToSupplier, dealStartDate, commission, upfrontPaidDate, upfrontCommission, notes,status,term,commissionRate,faxReceived,QA,contractType,resAgent);
					/*  End of Modification*/
					 if(null==result){
						throw new OrderNotFoundException();
					}
					 
					
				}
				catch(Exception e){
					
					e.printStackTrace();
				}								
			}
			
			
	 /*  ****************END OF EDITING PIPELINE****************          */	
			
			
			
			
		
	 
			
			
	/* ****************************
	 * 
	 *           State Manipulations,
	 *           1. View
	 *           2.Add
	 *           3. Delete
	 * 
	 * ************************************** */		
			
			
	 //     Displays a Page with all the Stated in DB
			@RequestMapping(method=RequestMethod.GET,value="/admin/states.do")
			public String displayStates(Map<String, Object> map) throws Exception{
				log.info("inside displayStates()");
				try{
					ArrayList<StateDto> stateDtos=this.dealSheetService.getStatesFromDao();
					if(stateDtos.isEmpty()){
						throw new StateNotFoundException();
					}
					else{
						
						map.put("states", stateDtos);
						return "admin/states";
					}
				}
				catch(StateNotFoundException e){
					log.error("No States Found "+e.toString());
					return "admin/states";
				}
				catch(Exception e){
					String message="Error While Displaying States";
					log.error(message+e.toString());
					map.put("message", message);
					return "error";
				}				
			}
		
			
	// Adding a State.
			@RequestMapping(method=RequestMethod.POST,value="/admin/states.do")
			public String addState(Map<String, Object> map, @RequestParam("state") String state){
				log.info("inside addState()");
				try{
					this.adminService.saveStateinDao(state);
					return "redirect:/admin/states.do";
				}
				catch(DataIntegrityViolationException e){
					String message="State Already Exists";
					map.put("message", message);
					return "redirect:/admin/states.do?message="+message;
				}
				catch(Exception e){
					String message="Error While Saving State";
					log.error(message+e.toString());
					map.put("message", message);
					return "error";
				}
			}
			
			
			
			
	// Deleting a State..		
			@RequestMapping(value="/admin/deletestate.do",method=RequestMethod.GET)
			public String deleteState(@RequestParam("id") Integer id,Map<String, Object> map){
				log.info("inside deleteState()");
				try{
					this.adminService.deleteStateinDao(id);
					return "redirect:/admin/states.do";
					
				}
				catch(Exception e){
					log.error("Error While Deleting State "+e.toString());
					String message="Error while Deleting State";
					map.put("message", message);
					return "error";
				}
			}
		
  /*          **************End of State Management*****************              */
			
	
			
			
  /*
   * Added by Jeevan on Aug 15,2013 to provide Admin Profile Management..
   */
	@RequestMapping(value="/admin/editprofile.do",method=RequestMethod.GET)
	public String initAdminProfile(Map<String, Object> map,@RequestParam("username") String username,
			@ModelAttribute("admin") HoveyUserDto admin){
		log.info("inside initAdminProfile()");
		try{
			HoveyUserDto adminDto=this.userService.getUserByUsername(username);
			if(null!=adminDto){
				map.put("admin", adminDto);
				return "admin/editProfile";
			}
			else{
				throw new HoveyUserNotFoundException();
			}
		}
		catch (HoveyUserNotFoundException e) {
			String message="No Details found for the User";
			map.put("message", message);
			return "admin/editProfile";
		}
		catch(Exception e){
			String message="Error while initiating Admin Profile Update";
			map.put("message", message);
			return "error";
		}
	}
	
	/*
	 * Editing Admin Profile..
	 */
	@RequestMapping(value="/admin/editprofile.do",method=RequestMethod.POST)
	public String updateAdminProfile(@ModelAttribute("admin") HoveyUserDto admin,Map<String, Object> map){
		log.info("inside UpdateAdminProfile()");
		try{
			String result=this.agentService.updateAgent(admin);
			if(null!=result){
				String message="Profile Updated Successfully";
				map.put("message", message);
				return "admin/editProfile";	
			}
			else{
				throw new Exception();
			}
		}
		catch(Exception e){
			String message="Error while Updating the Profile";
			map.put("message", message);
			return "error";
		}
	}
	
	
	/*
	 * Resetting Password
	 */
	@RequestMapping(method=RequestMethod.GET,value="/admin/resetpassword.do")
	public String initAdminResetPassword(Map<String, Object> map, @RequestParam("username") String username){
		log.info("inside initResetPwd()");
		try{
			map.put("user", username);
			return "admin/adminPassword";
		}
		catch(Exception e){
			String message="Error while initiating Reset Password";
			map.put("message", message);
			return "error";
		}
	}
	
	/*
	 * Performs Admin Password Reset
	 */
	@RequestMapping(method=RequestMethod.POST,value="/admin/resetpassword.do")
	public String resetAdminPassword(@RequestParam("user") String username,@RequestParam("password")String password,
			Map<String, Object>map,HttpServletRequest req,HttpServletResponse response){
		log.info("inside resetAdminPassword()");
		try{
			String result=this.agentService.resetPasswordOfAdmin(username, password);
			if(null!=result){
				String message="Password Changed Successfully, Please Login with New Password to Continue";
				map.put("message", message);
				map.put("status", true);
				return "admin/adminPassword"; 				
			}
			else{
				throw new Exception();
			}
		}
		catch (Exception e) {
			String message="Error while Resetting Password";
			map.put("message", message);
			return "error";
		}
	}
	
   
	/* ****************************
	 * 
	 *           ContractType Manipulations,added by  bhagya on May07th ,2014
	 *           1. View
	 *           2.Add
	 *           3. Delete
	 * 
	 * ************************************** */		
			
			
	 //     Displays a Page with all the ContractTypes in DB
			@RequestMapping(method=RequestMethod.GET,value="/admin/contracttypes.do")
			public String displayContractTypes(Map<String, Object> map) throws Exception{
				log.info("inside displayContractTypes()");
				try{
					ArrayList<ContractTypeDto> contractTypeDtos=this.adminService.getContractTypesFromDao();
					if(contractTypeDtos.isEmpty()){
						throw new StateNotFoundException();
					}
					else{
						
						map.put("contractTypes", contractTypeDtos);
						return "admin/contractTypes";
					}
				}
				catch(ContractTypeNotFoundException e){
					log.error("No ContractTypes Found "+e.toString());
					return "admin/contractTypes";
				}
				catch(Exception e){
					String message="Error While Displaying ContractTypes";
					log.error(message+e.toString());
					map.put("message", message);
					return "error";
				}				
			}
		
			
	// Adding a ContractType.
			@RequestMapping(method=RequestMethod.POST,value="/admin/contracttypes.do")
			public String addContractType(Map<String, Object> map, @RequestParam("contractType") String contractType){
				log.info("inside addContractType()");
				try{
					this.adminService.saveContractTypeinDao(contractType);
					return "redirect:/admin/contracttypes.do";
				}
				catch(Exception e){
					String message="Error While Saving ContractType";
					log.error(message+e.toString());
					map.put("message", message);
					return "error";
				}
			}
			
			
			
			
	// Deleting a ContractType..		
			@RequestMapping(value="/admin/deletecontracttype.do",method=RequestMethod.GET)
			public String deleteContractType(@RequestParam("id") Integer id,Map<String, Object> map){
				log.info("inside deleteContractType()");
				try{
					this.adminService.deleteContractTypeinDao(id);
					return "redirect:/admin/contracttypes.do";
					
				}
				catch(Exception e){
					log.error("Error While Deleting ContractType "+e.toString());
					String message="Error while Deleting ContractType";
					map.put("message", message);
					return "error";
				}
			}
		
  /*          **************End of ContractType Management*****************              */
			
			
			
			

			
	/**              ******************************FOR ANNIERSARY PAYMENTDS *********************************************************/
			
			
			
			@SuppressWarnings("unchecked")
			@RequestMapping(value="/admin/getannivpaypipeline.do",method=RequestMethod.GET)
			public String getAnniversaryPayOrdersforAdmin(Map<String, Object>map,@RequestParam(value="page",required=false,defaultValue="0")Integer pageNumber,
					@RequestParam(value="sortby",required=false) String sortElement, @RequestParam(value="range",required=false,defaultValue="20")Integer range,
					@RequestParam(value="output",required=false,defaultValue="nil") String output,HttpServletRequest request){
				log.info("getOrdersforAdmin()");
				Map<String, Object> ordersMap=this.getAllOrdersRelatedData();
				ArrayList<SupplierDto> suppliers=(ArrayList<SupplierDto>) ordersMap.get("suppliers");
				ArrayList<HoveyUserDto>agents=(ArrayList<HoveyUserDto>) ordersMap.get("agents");
				ArrayList<String>businessNames=(ArrayList<String>) ordersMap.get("businessNames");
				ArrayList<UtilityDto> utilities=(ArrayList<UtilityDto>) ordersMap.get("utilities");
				ArrayList<StateDto> states=(ArrayList<StateDto>) ordersMap.get("states");
				//added  contract types,by bhagya on May07th 2014
				ArrayList<ContractTypeDto> contractTypes=(ArrayList<ContractTypeDto>) ordersMap.get("contractTypes");
				//modified by bhagya
				if(null!=suppliers){
					map.put("suppliers", suppliers);
				}
				if(null!=agents){
					map.put("agents", agents);
				}
				if(null!=businessNames){
					map.put("businessNames", businessNames);
				}
				if(null!=utilities){
					map.put("utils", utilities);
				}
				if(null!=states){
					map.put("states", states);
				}
				//added  contract types,by bhagya on May07th 2014
				if(null!=contractTypes){
					map.put("contractTypes", contractTypes);
				}
				
				int pagesNeeded;
				try{	
					PipelineSearchDto search=new PipelineSearchDto();
					map.put("search", search);
					if(null==pageNumber){
						pageNumber=0;
					}
					if(null==sortElement){
						sortElement="orderDate";
					}
					
					if(output.equalsIgnoreCase("excel")){
						ArrayList<OrdersDto> orderDtos=this.adminService.getAnniversaryOrdersFromDAO(null, sortElement,null);
						
						Map<String, OrdersDto> pipelineData=new LinkedHashMap<String, OrdersDto>();
						int i=0;
						for(OrdersDto order:orderDtos){
							
							pipelineData.put("order"+i, order);
							i++;
						}
						map.put("orders", pipelineData);
						return "PipelineExcel";
						
					}
					else if(output.equalsIgnoreCase("pdf")){
						ArrayList<OrdersDto> orderDtos=this.adminService.getAnniversaryOrdersFromDAO(null, sortElement,null);
						
						for(OrdersDto order:orderDtos){
							Double netCommission=order.getUpfrontCommission()-order.getCommission();
							if(netCommission>0){
								order.setNetCommission(netCommission);
							}
							else{
								order.setNetCommission(netCommission);
							}						
						}
						JRDataSource jrDataSource=new JRBeanCollectionDataSource(orderDtos);
						map.put("datasource", jrDataSource);
						return "pipelineReport";
					}		
					else{										
						pageSize=range;					
						ArrayList<OrdersDto> orderDtos=this.adminService.getAnniversaryOrdersFromDAO(pageNumber, sortElement,pageSize);						
						int firstOrder=range*pageNumber+1;
						int totalOrders=orderDtos.get(0).getTotalResults();
						pagesNeeded=this.getPagesNeededByRangeAndTotalOrders(totalOrders, range);
						int lastOrder;
						if(!orderDtos.isEmpty()){
							lastOrder=this.getOrdersOfLastPageFromRangeandTotalOrders(range, totalOrders, firstOrder);
							map.put("first", firstOrder);
							map.put("total", totalOrders);
							map.put("last", lastOrder);
							map.put("orders", orderDtos);
							map.put("page",pageNumber);
							map.put("end", pagesNeeded);
							map.put("sortby", sortElement);
							map.put("range", range);
							map.put("rescinded", false);
							return "admin/pipeline";
						}
						else{
							map.put("rescinded", false);
							String message="No Orders Found";
							map.put("message", message);
							return "admin/pipeline";
						}
					}				
				}
				catch(OrderNotFoundException e){
					map.put("rescinded", false);
					log.error("Orders Not Found ");
					String message="No Orders Found";
					map.put("message", message);
					e.printStackTrace();
					return "admin/pipeline";
				}
				catch(Exception e){
					log.error("Error in getting Orders"+e.toString());
					String message="Error in Getting Order Details";
					map.put("message", message);
					e.printStackTrace();
					return "error";
				}
				
			}		
			
			
			
			
			// getOrdersByFilter
			@SuppressWarnings("unchecked")
			@RequestMapping(value="/admin/getannivpayfilteredorder.do",method=RequestMethod.GET)
			public <T> String getFilteredAnnivPayOrders(@RequestParam("field") String propertyName,@RequestParam("type")String fieldType,@RequestParam("value") T propertyValue,
					@RequestParam(value="page",required=false)Integer pageNumber,Map<String, Object>map,@RequestParam(value="sortby",required=false,defaultValue="orderDate")String sortElement,
					@RequestParam(value="range",required=false,defaultValue="20")Integer range,@RequestParam(value="output",required=false,defaultValue="nil")String output ){
				log.info("inside getFilteredOrders()");
				Map<String, Object> ordersMap=this.getAllOrdersRelatedData();
				ArrayList<SupplierDto> suppliers=(ArrayList<SupplierDto>) ordersMap.get("suppliers");
				ArrayList<HoveyUserDto>agents=(ArrayList<HoveyUserDto>) ordersMap.get("agents");
				ArrayList<String>businessNames=(ArrayList<String>) ordersMap.get("businessNames");
				ArrayList<UtilityDto> utilities=(ArrayList<UtilityDto>) ordersMap.get("utilities");
				ArrayList<StateDto> states=(ArrayList<StateDto>) ordersMap.get("states");
				//added  contract types,by bhagya on May07th 2014
				ArrayList<ContractTypeDto> contractTypes=(ArrayList<ContractTypeDto>) ordersMap.get("contractTypes");
				if(null!=suppliers){
					map.put("suppliers", suppliers);
				}
				if(null!=agents){
					map.put("agents", agents);
				}
				if(null!=businessNames){
					map.put("businessNames", businessNames);
				}
				if(null!=utilities){
					map.put("utils", utilities);
				}
				if(null!=states){
					map.put("states", states);
				}
				//added  contract types,by bhagya on May07th 2014
				if(null!=contractTypes){
					map.put("contractTypes", contractTypes);
				}
				int pagesNeeded=0;
				pageSize=range;
				try{
					if(null==pageNumber){
						pageNumber=0;
					}		
					PipelineSearchDto search=new PipelineSearchDto();
					map.put("search", search);
					
					if(output.equalsIgnoreCase("excel")){
						ArrayList<OrdersDto> orderDtos=this.adminService.getAnnivPayOrdersFilteredFromDao(null, propertyName, propertyValue, fieldType,sortElement,null);
						Map<String, OrdersDto> pipelineData=new LinkedHashMap<String, OrdersDto>();
						int i=0;
						for(OrdersDto order:orderDtos){
							
							pipelineData.put("order"+i, order);
							i++;
						}
						map.put("orders", pipelineData);
						return "PipelineExcel";					
					}
					else if(output.equalsIgnoreCase("pdf")){
						ArrayList<OrdersDto> orderDtos=this.adminService.getAnnivPayOrdersFilteredFromDao(null, propertyName, propertyValue, fieldType,sortElement,null);
						for(OrdersDto order:orderDtos){
							Double netCommission=order.getUpfrontCommission()-order.getCommission();
							if(netCommission>0){
								order.setNetCommission(netCommission);
							}
							else{
								order.setNetCommission(netCommission);
							}
							
						}
						JRDataSource jrDataSource=new JRBeanCollectionDataSource(orderDtos);
						map.put("datasource", jrDataSource);
						return "pipelineReport";
					}				
					else{				
						ArrayList<OrdersDto> orderDtos=this.adminService.getAnnivPayOrdersFilteredFromDao(pageNumber, propertyName, propertyValue, fieldType,sortElement,range);					
						int firstOrder=range*pageNumber+1;
						int totalOrders=orderDtos.get(0).getTotalResults();
						pagesNeeded=this.getPagesNeededByRangeAndTotalOrders(totalOrders, range);
						int lastOrder;				
						if(!orderDtos.isEmpty()){
							lastOrder=this.getOrdersOfLastPageFromRangeandTotalOrders(range, totalOrders, firstOrder);
							map.put("first", firstOrder);
							map.put("total", totalOrders);
							map.put("last", lastOrder);
							map.put("orders", orderDtos);
							map.put("field", propertyName);
							map.put("type", fieldType);
							map.put("value", propertyValue);
							map.put("sortby", sortElement);
							map.put("range", range);
							map.put("page",pageNumber);
							map.put("end", pagesNeeded);
							map.put("rescinded", false);
							return "admin/pipeline";
							
						}
						else{
							map.put("rescinded", false);
							map.put("field", propertyName);
							map.put("type", fieldType);
							map.put("value", propertyValue);
							return "admin/pipeline";
						}
					}
					
				}
				catch(OrderNotFoundException e){
					map.put("rescinded", false);
					log.error("No ORDERS FOUND"+e.toString());
					String message="No Records Found";
					map.put("message", message);
					map.put("field", propertyName);
					map.put("type", fieldType);
					map.put("value", propertyValue);
					map.put("rescinded", false);
					return "admin/pipeline";
					
				}
				catch(Exception e){
					e.printStackTrace();
					log.error("Error while getting DealSheets"+e.toString());
					String message="Error in getting DealSheets ";
					map.put("message", message);
					return "error";
				}			
				
			}
			
			
		/*                        End of Static Search                */
			
			
			
			
		
			
			
			
		
			
			/*
			 * 
			 *  
			 *  							DYNAMIC SEARCH.
			 *  
			 *  1. Makes use of PipelineSearchDto which is binded to the form...
			 *  
			 *  
			 *  
			 *  
			 *  */
			
			//Displays Multi Result Page
					@SuppressWarnings("unchecked")
					@RequestMapping(value="/admin/performsearchonannivpayments.do",method=RequestMethod.GET)
					public String performMultiResultSearchonAnnivPayments(@ModelAttribute("search") PipelineSearchDto search ,Map<String, Object> map,
							@RequestParam(value="page",required=false,defaultValue="0")Integer pageNumber,
							@RequestParam(value="sortby",required=false,defaultValue="orderDate") String sortElement, @RequestParam(value="range",required=false,defaultValue="20")Integer range,
							@RequestParam(value="output",required=false,defaultValue="nil") String output){
						log.info("inside showMultiSearch()");
						Map<String, Object> ordersMap=this.getAllOrdersRelatedData();
						ArrayList<SupplierDto> suppliers=(ArrayList<SupplierDto>) ordersMap.get("suppliers");
						ArrayList<HoveyUserDto>agents=(ArrayList<HoveyUserDto>) ordersMap.get("agents");
						ArrayList<String>businessNames=(ArrayList<String>) ordersMap.get("businessNames");
						ArrayList<UtilityDto> utilities=(ArrayList<UtilityDto>) ordersMap.get("utilities");
						ArrayList<StateDto> states=(ArrayList<StateDto>) ordersMap.get("states");
						//added  contract types,by bhagya on May07th 2014
						ArrayList<ContractTypeDto> contractTypes=(ArrayList<ContractTypeDto>) ordersMap.get("contractTypes");
						if(null!=suppliers){
							map.put("suppliers", suppliers);
						}
						if(null!=agents){
							map.put("agents", agents);
						}
						if(null!=businessNames){
							map.put("businessNames", businessNames);
						}
						if(null!=utilities){
							map.put("utils", utilities);
						}
						if(null!=states){
							map.put("states", states);
						}
						//added  contract types,by bhagya on May07th 2014
						if(null!=contractTypes){
							map.put("contractTypes", contractTypes);
						}
						
						try{	

							if(output.equalsIgnoreCase("excel")){
								ArrayList<OrdersDto> orders=this.adminService.getMultiSearchResultsofAnnivPaysFromDao(search, null,null,sortElement);
								Map<String, OrdersDto> pipelineData=new LinkedHashMap<String, OrdersDto>();
								int i=0;
								for(OrdersDto order:orders){								
									pipelineData.put("order"+i, order);
									i++;
								}
								map.put("orders", pipelineData);
								return "PipelineExcel";							
							}
							else if(output.equalsIgnoreCase("pdf")){
								ArrayList<OrdersDto> orders=this.adminService.getMultiSearchResultsofAnnivPaysFromDao(search, null,null,sortElement);
								for(OrdersDto order:orders){
									Double netCommission=order.getUpfrontCommission()-order.getCommission();
									if(netCommission>0){
										order.setNetCommission(netCommission);
									}
									else{
										order.setNetCommission(netCommission);
									}
									
								}
								JRDataSource jrDataSource=new JRBeanCollectionDataSource(orders);
								map.put("datasource", jrDataSource);
								return "pipelineReport";
							}		
							
							else{
																
								ArrayList<OrdersDto> orders=this.adminService.getMultiSearchResultsofAnnivPaysFromDao(search, pageNumber,range,sortElement);
								int firstOrder=range*pageNumber+1;
								int totalOrders=orders.get(0).getTotalResults();
								int lastOrder;
								int pagesNeeded=this.getPagesNeededByRangeAndTotalOrders(totalOrders, range);
								if(!orders.isEmpty()){
									lastOrder=this.getOrdersOfLastPageFromRangeandTotalOrders(range, totalOrders, firstOrder);
									map.put("first", firstOrder);
									map.put("total", totalOrders);
									map.put("last", lastOrder);
									map.put("orders", orders);
									map.put("page",pageNumber);
									map.put("end", pagesNeeded);
									map.put("sortby", sortElement);
									map.put("range", range);		
									map.put("rescinded", false);
									return "admin/pipeline";
								}
								else{
									map.put("rescinded", false);
									String message="No Records Found";
									map.put("message", message);
									return "admin/pipeline";
								}
							}
						}
						catch(OrderNotFoundException e){
							map.put("rescinded", false);
							e.printStackTrace();
							log.error("Orders Not Found ");
							String message="No Records Found";
							map.put("message", message);						
							return "admin/pipeline";
							
						}
						catch(Exception e){
							e.printStackTrace();
							log.error("Error while getting DealSheets"+e.toString());
							String message="Error in getting DealSheets ";
							map.put("message", message);
							return "error";
						}			
						
					}
					
				
			/*                End of DYNAMIC SEARCH          */		
				
			
		
					
					
					
					
					@SuppressWarnings("unchecked")
					@RequestMapping(value="/admin/getannivpayduespipeline.do",method=RequestMethod.GET)
					public String getAnniversaryPayDuesOrdersforAdmin2(Map<String, Object>map,@RequestParam(value="page",required=false,defaultValue="0")Integer pageNumber,
							 @RequestParam(value="range",required=false,defaultValue="20")Integer range,
							@RequestParam(value="output",required=false,defaultValue="nil") String output,HttpServletRequest request){
						log.info("getOrdersforAdmin()");					
						Map<String, Object> ordersMap=this.getAllOrdersRelatedData();
						
						//added  contract types,by bhagya on May07th 2014
						ArrayList<ContractTypeDto> contractTypes=(ArrayList<ContractTypeDto>) ordersMap.get("contractTypes");
						
						//added  contract types,by bhagya on May07th 2014
						if(null!=contractTypes){
							map.put("contractTypes", contractTypes);
						}
						
						int pagesNeeded;
						try{								
							if(null==pageNumber){
								pageNumber=0;
							}
							
							if(output.equalsIgnoreCase("excel")){
								ArrayList<OrdersDto> orderDtos=this.adminService.getAllAnniversaryPaymentsDuetoPayForPipelineReports();
								
								Map<String, OrdersDto> pipelineData=new LinkedHashMap<String, OrdersDto>();
								int i=0;
								for(OrdersDto order:orderDtos){
									
									pipelineData.put("order"+i, order);
									i++;
								}
								map.put("orders", pipelineData);
								return "PipelineExcel";
								
							}
							else if(output.equalsIgnoreCase("pdf")){
								ArrayList<OrdersDto> orderDtos=this.adminService.getAllAnniversaryPaymentsDuetoPayForPipelineReports();					
								for(OrdersDto order:orderDtos){
									Double netCommission=order.getUpfrontCommission()-order.getCommission();
									if(netCommission>0){
										order.setNetCommission(netCommission);
									}
									else{
										order.setNetCommission(netCommission);
									}						
								}
								JRDataSource jrDataSource=new JRBeanCollectionDataSource(orderDtos);
								map.put("datasource", jrDataSource);
								return "pipelineReport";
							}		
							else{										
								pageSize=range;					
								ArrayList<OrdersDto> orderDtos=this.adminService.getAllAnniversaryPaymentsDuetoPay(pageNumber, range);						
								int firstOrder=range*pageNumber+1;
								int totalOrders=orderDtos.get(0).getTotalResults();
								pagesNeeded=this.getPagesNeededByRangeAndTotalOrders(totalOrders, range);
								int lastOrder;
								if(!orderDtos.isEmpty()){
									lastOrder=this.getOrdersOfLastPageFromRangeandTotalOrders(range, totalOrders, firstOrder);
									map.put("first", firstOrder);
									map.put("total", totalOrders);
									map.put("last", lastOrder);
									map.put("orders", orderDtos);
									map.put("page",pageNumber);
									map.put("end", pagesNeeded);
									map.put("range", range);
									map.put("rescinded", false);
									map.put("dues", true);
									return "admin/pipeline";
								}
								else{
									map.put("dues", true);
									map.put("rescinded", false);
									String message="No Orders Found";
									map.put("message", message);
									return "admin/pipeline";
								}
							}				
						}
						catch(OrderNotFoundException e){
							map.put("dues", true);
							map.put("rescinded", false);
							log.error("Orders Not Found ");
							String message="No Orders Found";
							map.put("message", message);
							e.printStackTrace();
							return "admin/pipeline";
						}
						catch(Exception e){
							log.error("Error in getting Orders"+e.toString());
							String message="Error in Getting Order Details";
							map.put("message", message);
							e.printStackTrace();
							return "error";
						}
						
					}		
			
			
					
					
			
			
			
			
			
			
			
			public Integer getPagesNeededByRangeAndTotalOrders(Integer totalOrders,Integer range){
				Integer pagesNeeded=0;
				
				if(totalOrders%range==0){
					pagesNeeded=(totalOrders/range);
				}
				else{
					pagesNeeded=(totalOrders/range)+1;
				}
				return pagesNeeded;				
			}
			
			
			
	/************ END OF ANNIVERSARY PAYMENTS                 *****************************************************************************/
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}
