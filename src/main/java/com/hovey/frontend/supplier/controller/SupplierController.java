package com.hovey.frontend.supplier.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.supplier.exception.SupplierFileAlreadyExistsException;
import com.hovey.backend.supplier.exception.SupplierFileNotFoundException;
import com.hovey.backend.supplier.exception.SupplierMappingNotFoundException;
import com.hovey.backend.supplier.exception.SupplierNotFoundException;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.supplier.model.SupplierFiles;
import com.hovey.backend.supplier.model.SupplierReports;
import com.hovey.frontend.supplier.dto.ReportDto;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.supplier.dto.SupplierFilesDto;
import com.hovey.frontend.supplier.dto.SupplierMappingDto;
import com.hovey.frontend.supplier.dto.SupplierReportForm;
import com.hovey.frontend.supplier.dto.SupplierReportsDto;
import com.hovey.frontend.supplier.service.SupplierService;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

@Controller
public class SupplierController{

	private static Logger log=Logger.getLogger(SupplierController.class);
	
	@Resource(name="supplierService")
	private SupplierService supplierService;
	
	
/* *****************************************************
 * 
 * 								Supplier Management
 * 
 * ****************************************************************************/	
	
	//Initializing Supplier Creation
	@RequestMapping(value="/admin/addsupplier.do",method=RequestMethod.GET)
	public String initSupplierAdd(@ModelAttribute("supplier") SupplierDto supplier,Map<String, Object> map){
		log.info("inside initSupplierAdd()");
		try{
			return "admin/addSupplier";			
		}
		catch(Exception e){
			String message="Error while intiating create Supplier";
			log.error(message+" "+e.toString());
			map.put("message", message);
			return "error";			
		}	
	}
	
	
	//Saving Supplier
	@RequestMapping(value="/admin/addsupplier.do",method=RequestMethod.POST)
	public String saveSupplier(@ModelAttribute("supplier") SupplierDto supplier,Map<String, Object> map){
		log.info("inside saveSupplier()");
		try{
			Integer savestatus=this.supplierService.saveorUpdateSuppliertoDAO(supplier);
			if(savestatus>0){
				return "redirect:/admin/suppliers.do";
			}
			else{
				throw new Exception();
			}
		}
		catch(Exception e){
			String message="Error while Saving Supplier";
			log.error("message"+" "+e.toString());
			map.put("message", message);
			return "error";
		}
	}
	
	
	//Displays all suppliers..
	@RequestMapping(value="/admin/suppliers.do",method=RequestMethod.GET)
	public String viewSuppliers(Map<String, Object> map){
		log.info("inside viewSuppliers()");
		try{
			ArrayList<SupplierDto> supplierDtos=this.supplierService.getSuppliersFromDAO();
			map.put("suppliers", supplierDtos);
			return "admin/suppliers";
		}
		catch(SupplierNotFoundException e){
			String message="No Suppliers Found";
			map.put("message", message);
			log.error("NO Suppliers Found "+e.toString());
			return "admin/suppliers";
		}
		catch(Exception e){
			String message="Error while displaying Suppliers";
			log.error(message +" "+e.toString());
			map.put("message", message);
			return "error";
		}
	}
	
	
	//displays edit page
	@RequestMapping(value="/admin/editsupplier.do",method=RequestMethod.GET)
	public String displaySupplierforEdit(@RequestParam("id") Integer supplierId,@ModelAttribute("supplier") SupplierDto supplier,Map<String, Object> map){
		log.info("inside display/supplierforEdit()");
		try{
			supplier=this.supplierService.getSupplierBySupplierId(supplierId);
			map.put("supplier",supplier);
			return "admin/editSupplier";
		}
		catch(Exception e){
			
		}
		return "";
		
	}
	
	
	
	//editing Supplier
	@RequestMapping(value="/admin/editsupplier.do",method=RequestMethod.POST)
	public String editSupplier(@ModelAttribute("supplier") SupplierDto supplier,Map<String, Object> map){
		log.info("inside editSupplier()");
		try{
			Integer savestatus=this.supplierService.saveorUpdateSuppliertoDAO(supplier);
			
			if(savestatus>0){
				return "redirect:/admin/suppliers.do";
			}
			else{
				throw new Exception();
			}
		}
		catch(Exception e){
			String message="Error while updating Supplier";
			log.error("message"+" "+e.toString());
			map.put("message", message);
			return "error";
		}
	}
	
	
	//validating suppliername.
	@RequestMapping(value="/validatesupplier.do",method=RequestMethod.GET)
	@ResponseBody
	public String checkIfSupplierExists(@RequestParam("supplierName") String supplierName){
		log.info("inside checkIfSupplierExists()");
		try{
			SupplierDto supplier=this.supplierService.getSupplierBySupplierName(supplierName);
			if(null!=supplier){
				return "fail";
			}
			else{
				throw new SupplierNotFoundException();
			}
		}
		catch(SupplierNotFoundException e){
			log.info("Supplier Does not Exists");
			return "success";
		}
		catch(Exception e){
			log.error("Failed to Validate Supplier");
			return "error";
		}
	}
	
	
	
	
	
	
	
	/* *****************************************************
	 * 
	 * 								Supplier Mappings
	 * 
	 * ****************************************************************************/	
	
	
	
	//intiating Supplier Mapping Add functionality
	@RequestMapping(value="/admin/addmapping.do",method=RequestMethod.GET)
	public String initSupplierMapping(@ModelAttribute("mapping") SupplierMappingDto mapping,Map<String, Object> map){
		log.info("inside initSupplierMapping()");
		try{
			ArrayList<SupplierDto> supplierDtos=this.supplierService.getSuppliersFromDAO();
			map.put("suppliers", supplierDtos);
			return "admin/addMapping";
		}
		catch(Exception e){
			String message="Error whilie initiating Supplier Mapping, No Supplier Exists, Please <a href='./addsupplier.do'>Add Supplier</a> to continue ";
			log.error(message+ " "+e.toString());
			map.put("message", message);
			return "error";
		}
	}
	

	//saves Supplier Mapping Add functionality
	@RequestMapping(value="/admin/addmapping.do",method=RequestMethod.POST)
	public String saveSupplierMapping(@ModelAttribute("mapping") SupplierMappingDto mapping,Map<String, Object>map){
		log.info("inside saveSupplierMapping()");
		try{
			Integer result = this.supplierService.saveorUpdateSupplierMappings(mapping);
			if(result>0){
				return "redirect:/admin/mappings.do";
			}
			else{
				throw new MySQLIntegrityConstraintViolationException();
			}
		}
		catch(MySQLIntegrityConstraintViolationException e){
			String supplierName=mapping.getSupplierName().getSupplierName();
			String message="Supplier Mapping already exists, try editing";
			return "redirect:/admin/editmapping.do?supplier="+supplierName+"&message="+message;
		}
		
		catch(Exception e){
			String supplierName=mapping.getSupplierName().getSupplierName();
			String message="Supplier Mapping already exists, try editing";
			return "redirect:/admin/editmapping.do?supplier="+supplierName+"&message="+message;
		}
		
	}
	
	
	
	//retreives all the Supplier Mappings..
	@RequestMapping(value="/admin/mappings.do",method=RequestMethod.GET)
	public String displayAllSupplierMappings(Map<String,Object> map){
		log.info("inside displayAllSupplierMappings()");
		try{
			ArrayList<SupplierMappingDto> mappings=this.supplierService.getAllMappings();
			if(!mappings.isEmpty()){
				map.put("mappings", mappings);
				return "admin/supplierMappings";
			}
			else{
				throw new SupplierMappingNotFoundException();
			}
		}
		catch(SupplierMappingNotFoundException e){
			String message="No Supplier Mappings Found";
			map.put("message", message);
			log.info("mappings not found"+e.toString());
			return "admin/supplierMappings";
		}
		catch(Exception e){
			String message="Error while Displaying Supplier Mappings";
			log.error(message+" "+e.toString());
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	//init supplier mapping update..
	@RequestMapping(value="/admin/editmapping.do",method=RequestMethod.GET)
	public String initSupplingMappingUpdate(@RequestParam("supplier") String supplierName,Map<String, Object>map,@ModelAttribute("mapping")SupplierMappingDto mapping
			,@RequestParam(value="message",required=false)String message){
		log.info("inside initSupplingMappingUpdate()");
		try{
			ArrayList<SupplierDto> supplierDtos=this.supplierService.getSuppliersFromDAO();
			map.put("suppliers", supplierDtos);
			mapping=this.supplierService.getSupplierMappingBySupplierName(supplierName);
			map.put("mapping", mapping);
			map.put("message", message);
			return "admin/editMapping";
			
		}
		catch(Exception e){
			String mes="Error while initialising Supplier Mapping Updation";
			log.error(message+" "+e.toString());
			map.put("message",	mes);
			return "error";
		}
	}
	
	
	
	//saves Supplier Mapping Add functionality
		@RequestMapping(value="/admin/editmapping.do",method=RequestMethod.POST)
		public String editSupplierMapping(@ModelAttribute("mapping") SupplierMappingDto mapping,Map<String, Object>map){
			log.info("inside saveSupplierMapping()");
			try{
				Integer result = this.supplierService.saveorUpdateSupplierMappings(mapping);
				if(result>0){
					return "redirect:/admin/mappings.do";
				}
				else{
					throw new Exception();
				}
			}
			
			catch(Exception e){
				
				String message="Error while Updating Supplier Mapping";
				log.error(message+" "+e.toString());
				map.put("message", message);
				return "error";
				
			}
			
		}
		
		
		
	/* *************************
	 * 	
	 * 	Supplier Reports Loading Section.....
	 * 
	 * 
	 * *********************************************************/	
		
		
		
		//initializing supplier reports loading section.. Displays a page to upload the files..
		@RequestMapping(value="/admin/loadsupplierreport.do",method=RequestMethod.GET)
		public String initSupplierReport(Map<String, Object>map,@ModelAttribute("reportForm") SupplierReportForm reportForm){
			log.info("inside initSupplierReports");
			try{
				reportForm=new SupplierReportForm();
				reportForm.setReports(new AutoPopulatingList<ReportDto>(ReportDto.class));
				ArrayList<SupplierMappingDto> mappingDtos=this.supplierService.getAllMappings();
				map.put("mappings", mappingDtos);
				return "admin/supplierReports";			
				//return "admin/supplierReportsResult";	
			}
			catch(SupplierMappingNotFoundException e){
				String message="No Supplier Mappings Found, Please Map the Supplier field entries in <a href='./addmapping.do'>Add Supplier Mappings</a> page ";
				map.put("message", message);
				log.error(message+" "+e.toString());
				return "error";
			}
		}
		
		
		//for dynamically attaching more supplier files...
		
		@RequestMapping(value="/admin/attachsupplierfiles.do",method=RequestMethod.GET)
		public String attachMoreFiles(@RequestParam("fileCount") Integer fileCount,ModelMap model,@ModelAttribute("reportForm") SupplierReportForm reportForm){
			log.info("inside attachMoreFiles()");
			model.addAttribute("count",fileCount);
			return "admin/supplierReports2";		
		}
		
		
		
		
		
		
		
		
		
		
		
		//loads and displays supplier Data
				@SuppressWarnings("unchecked")
				@RequestMapping(value="/admin/loadsupplierreport.do",method=RequestMethod.POST)
				public String loadSupplierReportDate(@ModelAttribute("reportForm") SupplierReportForm reportForm,Map<String, Object>map){
					try{
						Map<String, Object> supplierReportMap=this.supplierService.saveSupplierReports(reportForm);
						ArrayList<SupplierReports> parsedReports=(ArrayList<SupplierReports>) supplierReportMap.get("savedReports");
						Set<String> errorReports=(Set<String>) supplierReportMap.get("errorReports");
						ArrayList<SupplierReports> nonPipelineOrdes=(ArrayList<SupplierReports>) supplierReportMap.get("nonPipelineOrders");
						ArrayList<SupplierReportsDto> reportsDto=new ArrayList<SupplierReportsDto>();
						for(SupplierReports report:parsedReports){
							SupplierReportsDto reportDto=SupplierReportsDto.populateSupplierReportsDto(report);
							reportsDto.add(reportDto);
						}	
						
						ArrayList<SupplierReportsDto> nonPipelineDtos=new ArrayList<SupplierReportsDto>();
						for(SupplierReports report:nonPipelineOrdes){
							SupplierReportsDto reportDto=SupplierReportsDto.populateSupplierReportsDto(report);
							nonPipelineDtos.add(reportDto);
						}	
						
						map.put("nonPipelineOrders", nonPipelineDtos);					
						map.put("savedReports", reportsDto);
						map.put("errorReports", errorReports);		
						return "admin/supplierReportsResult";
					}
					catch(OrderNotFoundException e){
						e.printStackTrace();
						String message="One or More Accounts with Account# given in Supplier Reports does not exists in Pipeline. Reports can only be uploaded for the existing Orders in Pipeline";
						log.error(message +" "+e.toString());
						map.put("message", message);						
						return "error";
					}
					catch(SupplierFileAlreadyExistsException e){
						String message="Supplier File Already Uploaded";
						log.error(message +" "+e.toString());
						map.put("message", message);						
						e.printStackTrace();
						return "error";
					}	
					catch(DataIntegrityViolationException e){
						String message="Same File Cannot be uploaded Twice, Error Occured, No Reports Saved, Please Try Again.";
						map.put("message", message);
						return "error";
					}
					catch(Exception e){
						String message="Error while Loading Supplier Reports";
						e.printStackTrace();
						log.error(message +" "+e.toString());
						map.put("message", message);						
						return "error";
					}			
				}
		
	//validating SupplierFile Based on its Name....	
		@RequestMapping(value="/validatefile.do",method=RequestMethod.GET)		
		@ResponseBody		
		public String validateSupplierFileIfAlreadyUploaded(@RequestParam("fileName") String fileName){
			log.info("inside validateSupplierFileIfAlreadyUploaded() ");
			try{
				SupplierFiles file=this.supplierService.getSupplierFileByName(fileName);
				if(null!=file){
					return "fail";
				}
				else{
					throw new SupplierFileNotFoundException();
				}
			}
			catch(SupplierFileNotFoundException e){
				log.info("NO File Uploaded yet"+e.toString());
				return "success";
			}
		}
		
	//Displaying Supplier Reports
		@RequestMapping(value="/admin/supplierfiles.do",method=RequestMethod.GET)
		public String displaySupplierFiles(Map<String, Object>map){
			log.info("inside displaySupplierFiles()");
			try{
				ArrayList<SupplierFilesDto> fileDtos=this.supplierService.getSupplierFiles();
				map.put("files", fileDtos);
				return "admin/supplierFiles";
			}
			catch(SupplierFileNotFoundException e){
				String message="No Files Uploaded";
				log.info(message + " "+e.toString());
				map.put("message", message);
				return "admin/supplierFiles";
			}
		}
		
		
	/*
	 * 
	 * Created by Jeevan on October 18, 2013
	 * method to Upload leftover Supplier Reports to Pipeline Orders....
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/admin/loadexistingreport.do",method=RequestMethod.GET)
	public String updatePipelineWithNotUpdatedSupplierReports(Map<String, Object> map){
		log.info("inside updatePipelineWithNotUpdatedSupplierReports()");
		try{
			Map<String, Object> resultMap=this.supplierService.processUploadingLeftOverSupplierReportUpdationToPipeline();
			
			ArrayList<SupplierReportsDto> updatedReports=(ArrayList<SupplierReportsDto>) resultMap.get("updatedReports");
			ArrayList<SupplierReportsDto> notUpdatedReports=(ArrayList<SupplierReportsDto>) resultMap.get("notUpdatedReports");
			map.put("updatedReports", updatedReports);
			map.put("notUpdatedReports", notUpdatedReports);
			return "admin/updatedReportsResult";
		}
		catch(Exception e){
			String message="Error while Updating Supplier Reports to Pipeline"+e.toString();
			map.put("message", message);
			return "error";
		}		
	}
		
	
	
	
	/*
	 *
	 * Created by Jeevan on November 18, 2013.
	 * Method to Updat Existing Supplier Report Manually
	 */
	@RequestMapping(value="/admin/uploadreportmanually.do",method=RequestMethod.GET)
   public String performManualExistingSupplierReportUpdate(@RequestParam("id") String id,Map<String, Object> map){
	   log.info("inside performManualExistingSupplierReportUpdate()");
	   try{
		   ArrayList<Integer> ids=new ArrayList<Integer>();
			 String[] repids=id.split(",");
			 
			 for(String repId:repids){
				 ids.add(Integer.parseInt(repId));
			 }
		   Integer i=this.supplierService.updateSupplierReportManually(ids);
		   if(i>0){
			   return "redirect:/admin/loadexistingreport.do";
		   }
		   else{
			   throw new Exception();
		   }
	   }
	   catch(Exception e){
		   String message="Error while Updating Supplier Report Manually";
		   map.put("message", message);
		   return "error";
	   }
 }
	
	
	
	
	
	/*
	 * Added by Jeevan on November 20,2013. A method to Delete all Reports of a Selected File
	 * 
	 * 1. Get all Files
	 * Display in a page
	 */
	@RequestMapping(method=RequestMethod.GET,value="/admin/removeuploadedsupplierreport.do")
	public String initDeleteSupplierReports(Map<String, Object> map){
		log.info("initDeleteSupplierReports()");
		try{
			ArrayList<SupplierFilesDto> supplierFiles=this.supplierService.getSupplierFiles();
			map.put("files", supplierFiles);
			return "admin/deleteSupplierReport";			
		}
		catch(SupplierFileNotFoundException e){
			map.put("message", "No Supplier Report Files Found to Delete");
			return "error";
		}
		catch(Exception e){
			String message="Error while Initiating Uploaded Supplier Reports Removal";
			map.put("message", message);
			return "error";
		}
	}
	
	
	/*
	 * Added by Jeevan on November 20,2013
	 */
	@RequestMapping(method=RequestMethod.POST,value="/admin/removeuploadedsupplierreport.do")
	public String processRemovingSupplierReport(@RequestParam("fileId") Integer fileId,Map<String, Object> map){
		log.info("initDeleteSupplierReports()");
		try{
			 String result=this.supplierService.processRemovingSupplierReportsFromPipeline(fileId);
			 if(result.equalsIgnoreCase("success")){
				 return "redirect://admin/removeuploadedsupplierreport.do?message=Reports Removed Successfully";			
			 }
			 else{
				 throw new Exception();
			 }
		}		
		catch(SupplierFileNotFoundException e){
			map.put("message", "No Supplier Report Files Found to Delete");
			return "error";
		}
		catch(SupplierReportsNotFoundException e){
			e.printStackTrace();
			return "error";
		}
		catch(Exception e){
			String message="Error while Initiating Uploaded Supplier Reports Removal";
			map.put("message", message);
			return "error";
		}
	}
	
	
	
	/*
	 * Added Temporarily
	 */
	
		
		
		
		
}
