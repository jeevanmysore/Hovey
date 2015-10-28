package com.hovey.frontend.supplier.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.AutoPopulatingList;

import com.hovey.backend.admin.dao.AdminDao;
import com.hovey.backend.agent.dao.DealSheetDao;
import com.hovey.backend.agent.exception.MoreAccountsForAccountDealDateException;
import com.hovey.backend.agent.exception.OrderNotFoundException;
import com.hovey.backend.agent.exception.OrdersExistForAccountException;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.supplier.dao.SupplierDao;
import com.hovey.backend.supplier.exception.SupplierFileNotFoundException;
import com.hovey.backend.supplier.exception.SupplierMappingNotFoundException;
import com.hovey.backend.supplier.exception.SupplierNotFoundException;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.supplier.model.Supplier;
import com.hovey.backend.supplier.model.SupplierFiles;
import com.hovey.backend.supplier.model.SupplierMapping;
import com.hovey.backend.supplier.model.SupplierReports;
import com.hovey.frontend.admin.service.AdminService;
import com.hovey.frontend.agent.service.DealSheetService;
import com.hovey.frontend.reports.ReportsController;
import com.hovey.frontend.supplier.dto.ReportDto;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.supplier.dto.SupplierFilesDto;
import com.hovey.frontend.supplier.dto.SupplierMappingDto;
import com.hovey.frontend.supplier.dto.SupplierReportForm;
import com.hovey.frontend.supplier.dto.SupplierReportsDto;


@Service("supplierService")
@Transactional
public class SupplierServiceImpl implements SupplierService {

	private static Logger log=Logger.getLogger(SupplierServiceImpl.class);
	
	@Resource(name="supplierDao")
	private SupplierDao supplierDao;
	
	@Resource(name="dealSheetDao")
	private DealSheetDao dealSheetDao;
	
	@Resource(name="adminDao")
	private AdminDao adminDao;
	//added adminservice by bhagya on may23rd,2014
	@Resource(name="adminService")
	private AdminService adminService;

	
	//for saving or Updating supplier
	public Integer saveorUpdateSuppliertoDAO(SupplierDto supplierDto)throws Exception{
		log.info("inside saveorUpdateSuppliertoDAO()");
		Supplier supplier=new Supplier();
		if(null!=supplierDto.getSupplierId()){
			supplier.setId(supplierDto.getSupplierId());
		}
		supplier.setSupplierName(supplierDto.getSupplierName());
		supplier.setContractCommission(supplierDto.getContractCommission());
		supplier.setRenewalCommission(supplierDto.getRenewalCommission());
		// added uploadKwhFromSupplier,by bhagya on May 1st,2014
		supplier.setUploadKwhFromSupplier(supplierDto.getUploadKwhFromSupplier());
		Integer status=this.supplierDao.saveOrUpdateSupplierToDB(supplier);
		return status;
	}
	
	//getting Supplier by name
	public SupplierDto getSupplierBySupplierName(String supplierName)throws Exception{
		log.info("inside getSupplierBySupplierName()");
		Supplier supplier=this.supplierDao.getSupplierByName(supplierName);
		SupplierDto supplierDto=SupplierDto.populateSupplier(supplier);
		return supplierDto;
		
	}
	
	
	//getting Supplier by id
		public SupplierDto getSupplierBySupplierId(Integer supplierId)throws Exception{
			log.info("inside getSupplierBySupplierName()");
			Supplier supplier=this.supplierDao.getSupplierByID(supplierId);
			SupplierDto supplierDto=SupplierDto.populateSupplier(supplier);
			return supplierDto;
			
		}
		
		//gets all suppliers from Dao
		public ArrayList<SupplierDto> getSuppliersFromDAO()throws SupplierNotFoundException{
			log.info("inside getSuppliersFromDAO()");
			ArrayList<Supplier> suppliers=this.supplierDao.getSuppliers();
			ArrayList<SupplierDto> supplierDtos=new ArrayList<SupplierDto>();
			for(Supplier supplier : suppliers){
				SupplierDto supplierDto=SupplierDto.populateSupplier(supplier);
				supplierDtos.add(supplierDto);
			}
			return supplierDtos;
		}
		
	
		//saves or Updates Supplier Mappings
		public Integer saveorUpdateSupplierMappings(SupplierMappingDto mappingDto)throws Exception{
			log.info("inside saveOrUpdateSupplierMappings()");
			
			SupplierMapping mapping=new SupplierMapping();
			if(null!=mappingDto.getMappingId()){
				mapping.setId(mappingDto.getMappingId());
			}	
			mapping.setFieldForAccount(mappingDto.getFieldForAccount());
			mapping.setFieldForAgentID(mappingDto.getFieldForAgemtId());
			mapping.setFieldForCommissonRate(mappingDto.getFieldForCommissionRate());
			mapping.setFieldForCustomer(mappingDto.getFieldForCustomer());
			mapping.setFieldforEndDate(mappingDto.getFieldForEndDate());
			mapping.setFieldForKwh(mappingDto.getFieldForKwh());
			mapping.setFieldForPaidDate(mappingDto.getFieldForPaidDate());
			mapping.setFieldForRate(mappingDto.getFieldForRate());
			mapping.setFieldforStartDate(mappingDto.getFieldForStartDate());
			mapping.setFieldForTerm(mappingDto.getFieldForTerm());
			mapping.setFieldForTotalCommission(mappingDto.getFieldForTotalCommissionPaid());
			
			Supplier supplier=this.supplierDao.getSupplierByName(mappingDto.getSupplierName().getSupplierName());
			mapping.setSupplierName(supplier);
			Integer status=this.supplierDao.saveorUpdateSupplierMappings(mapping);
			return status;
			
		}
		
		
		//gets all Supplier Mappings...
		
		public ArrayList<SupplierMappingDto> getAllMappings()throws SupplierMappingNotFoundException{
			log.info("inside getAllMappings()");
			ArrayList<SupplierMapping> mappings=this.supplierDao.getSupplierMappings();
			ArrayList<SupplierMappingDto> mappingDtos=new ArrayList<SupplierMappingDto>();
			for(SupplierMapping mapping:mappings){
				SupplierMappingDto mappingDto=SupplierMappingDto.populateSupplierMappingDto(mapping);
				mappingDtos.add(mappingDto);
			}
			return mappingDtos;
		}
		
		
		//get Supplier Mapping by Supplier Name;
		public SupplierMappingDto getSupplierMappingBySupplierName(String supplierName)throws Exception{
			log.info("inside getSupplierMAppingBySupplierName()");
			SupplierMapping mapping=this.supplierDao.getSupplierMappingBySupplier(supplierName);
			SupplierMappingDto	mappingDto=SupplierMappingDto.populateSupplierMappingDto(mapping);
			return mappingDto;
		}
		
		
		
		
		//Loading Supplier Reports.......
		/*
		 * Involves following Steps...
		 *   1. Getting Supplier Mapping Object
		 *   2. Sending File info and Mapping Object to parse Excel file using mapping.Field for , finding cell indices and populating Supplier Report Object
		 *   3. Populating Supplier files object using filename and savinf it into DB
		 *   4. Assingning Supplierfiles to all the Reports..
		 *   5. Saving Supplier Reports.
		 *   6. Updating Pipeline.
		 * 
		 * (non-Javadoc)
		 * @see com.hovey.frontend.supplier.service.SupplierService#getSupplierReportsData(com.hovey.frontend.supplier.dto.ReportDto)
		 */		
		@SuppressWarnings("unchecked")
		@Transactional
		 public Map<String,Object> saveSupplierReports(SupplierReportForm reportForm)throws Exception{
			log.info("inside saveSupplierReports()");
			Map<String, Object> supplierReportMap=new HashMap<String, Object>();
			String supplierName=reportForm.getReports().get(0).getSupplierName();
			AutoPopulatingList<ReportDto> reportDtos=reportForm.getReports();
			Iterator<ReportDto> it=reportDtos.iterator();
			ArrayList<SupplierReports> parsedSupplierReports=new ArrayList<SupplierReports>(); // collection of all orders contained in Supplier Reports	(master)  to filter out and get only pipeline orders later, but at this point it has all orders parsed from doc	
			Set<String> errorAccounts=new HashSet<String>();                 //used to store Account No's which are either failed to save or failed to edit pipeline..
			ArrayList<SupplierReports> savedSupplierReports=new ArrayList<SupplierReports>();   //contains all reports data saved in supplier_reports table..              //contains all orders in doc.....
			while(it.hasNext()){				
				ReportDto reportDto=it.next();
				reportDto.setSupplierName(supplierName);				
				Map<String, Object> parsedReportsMap=this.getSupplierReportsData(reportDto);   //fetches supplier report of individual file..	
				ArrayList<SupplierReports> tempReports=(ArrayList<SupplierReports>) parsedReportsMap.get("reports");
				ArrayList<String> errors=(ArrayList<String>) parsedReportsMap.get("errors");
				errorAccounts.addAll(errors);
				//adding all the fetched orders to master orders
				parsedSupplierReports.addAll(tempReports);
				SupplierFiles supplierFile=this.saveSupplierFiles(reportDto.getFileName());
				Map<String, Object>supplierReportsMap=this.saveSupplierReports(tempReports, supplierFile);
				ArrayList<SupplierReports> savedReports=(ArrayList<SupplierReports>) supplierReportsMap.get("savedReports");
				ArrayList<String> errorReports=(ArrayList<String>) supplierReportsMap.get("errorAccounts");		
				savedSupplierReports.addAll(savedReports);
				errorAccounts.addAll(errorReports);
			}				
			ArrayList<SupplierReports> ordersNotinPipeline=this.getOrdersNotinPipeline(savedSupplierReports);
			savedSupplierReports.removeAll(ordersNotinPipeline);			   //gives orders which are only in pipeline..
			if(!savedSupplierReports.isEmpty()){                             //if orders exists in Pipeline, save them...
				ArrayList<String> errorReports=this.updatePipelineWithSupplierData(savedSupplierReports);
				errorAccounts.addAll(errorReports);				
			}				
			supplierReportMap.put("savedReports", savedSupplierReports);
			supplierReportMap.put("errorReports", errorAccounts);
			supplierReportMap.put("nonPipelineOrders", ordersNotinPipeline);
			return supplierReportMap;			
		}		
		
		
		
		
		/*
		
		 * Added by Jeevan on Jul 24,2013 .
		 * 
		 * A method to find out all the Orders which are not in Pipeline..
		 		
		private ArrayList<SupplierReports> getOrdersNotinPipeline(ArrayList<SupplierReports> parsedSupplierReports){
			log.info("in side getOrdersNotinPipeline()");
			ArrayList<SupplierReports> ordersNotinPipeline=new ArrayList<SupplierReports>();
			for(SupplierReports rep:parsedSupplierReports){
				try{
					if(null!=rep.getContractStartDate()){
						this.dealSheetDao.getOrderByAccountNumberandStartDate(rep.getAccountNumber(), rep.getContractStartDate());
					}
					else{
						throw new OrderNotFoundException();
					}
				}
				catch(OrderNotFoundException e){					
					ordersNotinPipeline.add(rep);
				}
			}
			return ordersNotinPipeline;
		}
				*/
		
		
		
		
		/*
		 * Added by Jeevan onNov 19, 2013.
		 * 
		 * A method to find out all the Orders which are not in Pipeline..
		 */		
		private ArrayList<SupplierReports> getOrdersNotinPipeline(ArrayList<SupplierReports> parsedSupplierReports){
			log.info("in side getOrdersNotinPipeline()");
			ArrayList<SupplierReports> ordersNotinPipeline=new ArrayList<SupplierReports>();
			for(SupplierReports rep:parsedSupplierReports){				
				try{					
					Integer accCount=this.dealSheetDao.getAccountsCountByAccountNo(rep.getAccountNumber());
					Integer dateCount=0;
					Integer termCount=0;
					if(accCount>1){
						try{
							dateCount=this.dealSheetDao.getAccountsCountByAccountNoDealDate(rep.getAccountNumber(), rep.getContractStartDate());
						}
						catch(Exception e){							
						}
						
						try{
							termCount=this.dealSheetDao.getAccountsCountByKwHTerm(rep.getAccountNumber(), rep.getKwh(), rep.getTerm());
						}
						catch(Exception e ){							
						}						
						if(termCount==0 && dateCount==0){
							throw new OrderNotFoundException();
						}					
					}
				}
				catch(OrderNotFoundException e){					
						ordersNotinPipeline.add(rep);
				}
			}
			return ordersNotinPipeline;
		}
		
		
		
		
		/*
		 * Saving Supplier File..
		 */
		public SupplierFiles saveSupplierFiles(String fileName)throws Exception{
			log.info("inside saveSupplierFiles()" );
			SupplierFiles file= new SupplierFiles();
			file.setFileName(fileName);
			SupplierFiles supplierFile=this.supplierDao.saveSupplierFile(file);			
			return supplierFile;
		}
		
		
		
		
		/*
		 * Added by Jeevan on July 26, 2013. method to save supplier reports.. its a to handle condition where there User can handle all reports, irrespective of whether it is their or not in pipeline..
		 * 
		 * Method for Saving Reports..
		 */
	
		public Map<String, Object> saveSupplierReports(ArrayList<SupplierReports> parsedreports,SupplierFiles supplierFile){
			log.info("inside saveSupplierReports()");	
			Map<String, Object> supplierReportMap=new HashMap<String, Object>();		
			ArrayList<SupplierReports> savedReports=new ArrayList<SupplierReports>();
			ArrayList<String> errorAccounts=new ArrayList<String>();
			for(SupplierReports report:parsedreports){
				// to prevent process termination in error condition, all successful orders will be saved and error accounts will be collected
				try{
					report.setSupplierFile(supplierFile);
					report.setUpdatedPipeline(false);
					try{
						report.setAgentId(this.dealSheetDao.getOrderByAccountNumber(report.getAccountNumber()).getAgent());
					}
					catch(Exception e){
						log.error("No Agent ID Found "+e.toString());
					}	
					
					Integer result=this.supplierDao.saveorUpdateSupplierReports(report);
					if(result>0){
						report.setId(result);
						savedReports.add(report);
					}
					else{						
						throw new Exception();						
					}
				}
				catch(Exception e){
					log.error("Error while saving the Account "+report.getAccountNumber() + " "+e.toString());
					e.printStackTrace();
					errorAccounts.add(report.getAccountNumber()+" in "+report.getSupplierFile().getFileName());
				}	
			}
			supplierReportMap.put("savedReports", savedReports);
			supplierReportMap.put("errorAccounts", errorAccounts);			
			return supplierReportMap;
		}
		
		
		
		//Updating Pipeline with Successfully save Supplier Reports
		public ArrayList<String> updatePipelineWithSupplierData(ArrayList<SupplierReports> savedReports) {
			log.info("inside updatePipelineWithSupplierData( )");
			ArrayList<String> errorAccounts=new ArrayList<String>();
			for(SupplierReports report:savedReports){
				try{					
					this.UpdateSupplierReportToPipeline(report);					
				}
				catch(Exception e){
					log.error("Failed to Update Pipeline... "+e.toString());
					e.printStackTrace();
					errorAccounts.add(report.getAccountNumber() +" in "+report.getSupplierFile().getFileName());
				}
			}
			 return errorAccounts;
		}
		
		
		
		/*private void UpdateSupplierReportToPipeline(SupplierReports report ) throws Exception{
			Orders order=this.dealSheetDao.getOrderByAccountNumberandStartDate(report.getAccountNumber(), report.getContractStartDate());
			Double prevCommission=order.getUpfrontCommission();
			Double newCommission=report.getTotalCommissionPaid();					
			Double aggCommission=prevCommission+newCommission;					
			order.setUpfrontCommission(aggCommission);
			if(null!=report.getUpfrontPaidDate()){
				order.setUpfrontPaidDate(report.getUpfrontPaidDate());
			}
			else{
				order.setUpfrontPaidDate(new Date());
			}	
			if(!order.getStatus().equalsIgnoreCase("rescinded")){
				order.setStatus("approved");
			}
			order.setQA(true);
			order.setFaxReceived(true);
			order.setDealStartDate(report.getContractStartDate());
			Integer result=this.adminDao.editPipelineData(order);		
			if(result>0){
				log.info("Successfully Updated Pipline");
				report.setUpdatedPipeline(true);
				this.supplierDao.saveorUpdateSupplierReports(report);
			}
			else{
				throw new Exception();
			}
		}*/
		
		//Copy of same method from DealSheetServiceImpl. As a part of last minute modification, have to modify it
		private Double calculateCommission(Supplier supplier, int annualkWh, String contractType){/*assumed to be null for now */
			log.info("inside calculate Commission");
			Double commission=0.0;			
				if(contractType.equalsIgnoreCase("new")){
					commission=annualkWh*supplier.getContractCommission();
				}
				else{
					commission=annualkWh*supplier.getRenewalCommission();
				}		
			return commission;
		}
		
		/*
		 * Modiiffied on December 17, 2013
		 * 
		 * As per clients need kWH is updated too.
		 */
		
		private void UpdateSupplierReportToPipeline(SupplierReports report ) throws Exception{
			Orders order=null;
			Integer accCount=this.dealSheetDao.getAccountsCountByAccountNo(report.getAccountNumber().trim());
			Integer dateCount=0;
			try{
				dateCount=this.dealSheetDao.getAccountsCountByAccountNoDealDate(report.getAccountNumber().trim(), report.getContractStartDate());
			}
			catch(Exception e){				
			}
			Integer termCount=0;
			try{
				termCount=this.dealSheetDao.getAccountsCountByKwHTerm(report.getAccountNumber().trim(), report.getKwh(), report.getTerm());
			}
			catch(Exception e){
				
			}	
		//	System.out.println("ACCOUNT "+report.getAccountNumber()+" "+accCount+" "+dateCount+" "+termCount);
			
			
			
			if(accCount==1){				
				order=this.dealSheetDao.getOrderByAccountNumber(report.getAccountNumber());
			}
			else if(dateCount==1){
				order=this.dealSheetDao.getOrderByAccountNumberandStartDate(report.getAccountNumber(), report.getContractStartDate());
			}
			else if(termCount==1){
				order= this.dealSheetDao.getOrderByAccountNoKwhandTerm(report.getAccountNumber(), report.getKwh(), report.getTerm());
			}
			else{
				throw new OrderNotFoundException();
			} 
			 
			
			/*modiied by bhagya on may23rd,2014,using the method handling upfrontcommission*/
			
			
			order=this.handleUpfrontCommissions(order,report.getTotalCommissionPaid(),report.getUpfrontPaidDate(),false);
			/*  d      dsaDA D              */		
		
			if(!order.getStatus().equalsIgnoreCase("rescinded")){
				order.setStatus("approved");
			}
			order.setQA(true);
			order.setFaxReceived(true);
			if(null!=report.getContractStartDate()){
				order.setDealStartDate(report.getContractStartDate());	
			}				
			/*
			 * Commented by Jeevan on April 01, 2014 as per clients mail saying not to update pipeline kWh with Supplier Reports kWH
			  added Kwh value,by bhagya on may 1st,2014
			  */
			if(report.getSupplierName().isUploadKwhFromSupplier()){
				order.setKwh(report.getKwh());
			}
			
			Integer result=this.adminDao.editPipelineData(order);		
			if(result>0){
				log.info("Successfully Updated Pipline");
				report.setUpdatedPipeline(true);
				this.supplierDao.saveorUpdateSupplierReports(report);
			}
			else{
				 
				throw new Exception();
			}
		}
		
		
		
		
		
	
		
		/*
		 *Modified by  Jeevan on July 23,2013 in order to get the Accounts which are not in Pipeline to show in error message.
		 *
		 */		
		public Map<String, Object> getSupplierReportsData(ReportDto report)throws Exception{			
			SupplierMapping mapping=this.supplierDao.getSupplierMappingBySupplier(report.getSupplierName());
			SupplierMappingDto mappingDto=SupplierMappingDto.populateSupplierMappingDto(mapping);
			Map<String, Object> parsedReportsMap=this.parseSupplierReportFiles(mappingDto, report);
			return parsedReportsMap;						
		}
				
		
		
		
		// parses the Entire EXCel Filed, Makes use of Apache POI.
		/*
		 * Changed to Map type 0n Aug 28 to handle null value error cases for mandatory fields..
		 */
		public Map<String,Object> parseSupplierReportFiles(SupplierMappingDto mapping,ReportDto report)throws Exception{
			int	accIndex = 0;
			int comRateIndex=0,endDateIndex=100,startDateIndex=0,customerIndex = 0,kwhIndex = 0,rateIndex=200,termIndex=0,comPaidIndex=0,paidDateIndex=300;
			Workbook workbook=null;
			Sheet sheet=null;
			Row titleRow=null;
			Cell titleCell=null;
			
			ArrayList<SupplierReports> reports=new ArrayList<SupplierReports>();
			ArrayList<String> errorAccounts=new ArrayList<String>();
			String fileExtension=this.getFileExtension(report.getFile().getOriginalFilename());
			
			//handling file type extensions
			if(fileExtension.equalsIgnoreCase("xlsx")){
				workbook=new XSSFWorkbook(report.getFile().getInputStream());
				workbook.createCellStyle().setAlignment(XSSFCellStyle.ALIGN_LEFT);
			}
			else if(fileExtension.equalsIgnoreCase("xls")){				
				workbook=new HSSFWorkbook(report.getFile().getInputStream());
				workbook.createCellStyle().setAlignment(HSSFCellStyle.ALIGN_LEFT);				
			}				
			
			
			//handles different types of suppliers to retreive respective tab containing report information
			if(report.getSupplierName().equalsIgnoreCase("Glacial Energy")){
				sheet=workbook.getSheetAt(1);
			}
			else{
				sheet=workbook.getSheetAt(0);
			}
			
			
			//getting titles...
			titleRow=sheet.getRow(0);
			Iterator<Cell> titleIterator=titleRow.cellIterator();
			while(titleIterator.hasNext()){
				titleCell=titleIterator.next();
				String title=titleCell.getStringCellValue();	
				
			//comparing Excel files header values with that of supplier mapping values
				if(title.equalsIgnoreCase(mapping.getFieldForAccount()) || title.toLowerCase().contains(mapping.getFieldForAccount().toLowerCase())){
					
					accIndex=titleCell.getColumnIndex();
				}
				
				
				if(title.equalsIgnoreCase(mapping.getFieldForCommissionRate()) || title.toLowerCase().contains(mapping.getFieldForCommissionRate().toLowerCase())){
					comRateIndex=titleCell.getColumnIndex();
				}
				if(title.equalsIgnoreCase(mapping.getFieldForCustomer()) || title.toLowerCase().contains(mapping.getFieldForCustomer().toLowerCase())){
					customerIndex=titleCell.getColumnIndex();
				}
				
				if(!report.getSupplierName().equalsIgnoreCase("Glacial Energy")){
					if(null!=mapping.getFieldForEndDate() && mapping.getFieldForEndDate().trim()!=""){
						if(title.equalsIgnoreCase(mapping.getFieldForEndDate()) || title.toLowerCase().contains(mapping.getFieldForEndDate().toLowerCase())){
							endDateIndex=titleCell.getColumnIndex();
						}
					}
				}
				
				if(title.equalsIgnoreCase(mapping.getFieldForKwh()) || title.toLowerCase().contains(mapping.getFieldForKwh().toLowerCase())){
					
					kwhIndex=titleCell.getColumnIndex();
				}
				if(!report.getSupplierName().equalsIgnoreCase("Glacial Energy")){
					if(title.equalsIgnoreCase(mapping.getFieldForPaidDate()) ){
						System.out.println(titleCell.getColumnIndex());
						paidDateIndex=titleCell.getColumnIndex();
						System.out.println("PAid Date "+paidDateIndex);
					}
				}
			
				if(!report.getSupplierName().equalsIgnoreCase("Glacial Energy")){
					if(title.equalsIgnoreCase(mapping.getFieldForRate()) || title.toLowerCase().contains(mapping.getFieldForRate().toLowerCase())){
						rateIndex=titleCell.getColumnIndex();
					}
				}
				if(title.equalsIgnoreCase(mapping.getFieldForStartDate()) || title.toLowerCase().contains(mapping.getFieldForStartDate().toLowerCase())){
					startDateIndex=titleCell.getColumnIndex();					
				}
				
				if(title.trim().equalsIgnoreCase(mapping.getFieldForTerm()) ){
					termIndex=titleCell.getColumnIndex();
				}
				
				if(title.equalsIgnoreCase(mapping.getFieldForTotalCommissionPaid()) || title.toLowerCase().contains(mapping.getFieldForTotalCommissionPaid().toLowerCase())){
					comPaidIndex=titleCell.getColumnIndex();
				}
			//end of comparision	
			}//end of title iterator
			
						//Handling different cases of Datepaid
			int tempIndex=paidDateIndex;
			System.out.println("temp Index "+tempIndex);
			if(report.getSupplierName().equalsIgnoreCase("Champion Energy")){				
				while(paidDateIndex<=tempIndex+12 ){
					/*if(null!=sheet.getRow(1).getCell(paidDateIndex) && sheet.getRow(1).getCell(paidDateIndex).getCellType()==Cell.CELL_TYPE_BLANK){
						break;
					}
					else{
						paidDateIndex+=3;				
					}*/
					if(null==sheet.getRow(1).getCell(paidDateIndex) ||sheet.getRow(1).getCell(paidDateIndex).getCellType()==Cell.CELL_TYPE_BLANK ){
						paidDateIndex+=3;		
					}
					else{
						break;
					}
				}
			}
			
		   //fetching data from rows based on indices obtained on above step..
			int temp=0;
			Supplier supplierName=this.supplierDao.getSupplierByName(report.getSupplierName());
			Iterator<Row> rowIterator=sheet.iterator();
			while(rowIterator.hasNext()){
				Row row=rowIterator.next();
				try{
					if(temp>=1){
						if(null!=row.getCell(0) && row.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK && row.getCell(0).toString().trim()!=""){   //discarding empty rows.
							SupplierReports supplierReport=new SupplierReports();								
							if(null!=row.getCell(accIndex)){
								row.getCell(accIndex).setCellType(Cell.CELL_TYPE_STRING);
								String accNo=row.getCell(accIndex).getStringCellValue().trim();
								if(null!=accNo && accNo!=""){
									supplierReport.setAccountNumber(accNo);
								}
								else{
									errorAccounts.add("Account No is Empty in Row"+(temp+1)+ ", Skipped Loading that Row ");
									throw new Exception();
								}								
							}	
							if(null!=row.getCell(customerIndex)){
								row.getCell(customerIndex).setCellType(Cell.CELL_TYPE_STRING);
								if(null!=row.getCell(customerIndex).getStringCellValue() && row.getCell(customerIndex).getStringCellValue()!=""){
									supplierReport.setCustomerName(row.getCell(customerIndex).getStringCellValue());
								}
								else{
									errorAccounts.add("Customer Name is Empty in Row"+(temp+1)+ ", Skipped Loading that Row  ");
									throw new Exception();
								}
							}							
							if(null!=row.getCell(kwhIndex)){
								row.getCell(kwhIndex).setCellType(Cell.CELL_TYPE_NUMERIC);								
								supplierReport.setKwh((int)row.getCell(kwhIndex).getNumericCellValue());
							}
							else{
								errorAccounts.add("kWh is Empty in Row"+(temp+1)+ ", Skipped Loading that Row  ");
								throw new Exception();
							}
							if(report.getSupplierName().equalsIgnoreCase("Glacial Energy")){
								supplierReport.setTerm("12");
							}
							else{
								if(null!=row.getCell(termIndex)){
									row.getCell(termIndex).setCellType(Cell.CELL_TYPE_STRING);
									supplierReport.setTerm(row.getCell(termIndex).getStringCellValue());
								}
								else{
									errorAccounts.add("Term is Empty in Row"+(temp+1)+ ", Skipped Loading that Row  ");
									throw new Exception();
								}
							}
							if(null!=row.getCell(comPaidIndex)){
								row.getCell(comPaidIndex).setCellType(Cell.CELL_TYPE_NUMERIC);
								supplierReport.setTotalCommissionPaid(row.getCell(comPaidIndex).getNumericCellValue());								
							}
							else{
								errorAccounts.add("Commission Paid is Empty in Row"+(temp+1)+ ", Skipped Loading that Row  ");
								throw new Exception();
							}							
							if(null!=row.getCell(comRateIndex)){
								row.getCell(comRateIndex).setCellType(Cell.CELL_TYPE_NUMERIC);
								supplierReport.setCommissionRate(row.getCell(comRateIndex).getNumericCellValue());
							}
							else{
								errorAccounts.add("Commission Rate is Empty in Row"+(temp+1)+ ", Skipped Loading that Row  ");
								throw new Exception();
							}
							
							if(null!=row.getCell(rateIndex) && rateIndex!=200){
								row.getCell(rateIndex).setCellType(Cell.CELL_TYPE_NUMERIC);
								supplierReport.setRate(row.getCell(rateIndex).getNumericCellValue());
							}							
							if(null!=row.getCell(startDateIndex) ){		
								
								supplierReport.setContractStartDate(this.parseDateString(row.getCell(startDateIndex).toString()));
							}						
							if(null!=row.getCell(endDateIndex) && endDateIndex!=100){								
								supplierReport.setContractEndDate(this.getCellValueAsDate(row.getCell(endDateIndex)));								
							}
							else{							
								supplierReport.setContractEndDate(null);								
							}
							if(null!=sheet.getRow(1).getCell(paidDateIndex) ){		
									System.out.println(sheet.getRow(1).getCell(paidDateIndex));
									Date paidDate=this.getCellValueAsDate(sheet.getRow(1).getCell(paidDateIndex));
									supplierReport.setUpfrontPaidDate(paidDate);		
									System.out.println("DATE "+supplierReport.getUpfrontPaidDate());
							}	
							else{
								supplierReport.setUpfrontPaidDate(new Date());
							}
							supplierReport.setSupplierName(supplierName);							
							reports.add(supplierReport);
						} //end of if						
					}//end of temp check		
				 }
				catch(Exception e){					
					e.printStackTrace();
				}
				temp++;				
			}//end of row iterator
			
			Map<String, Object> parsedMap=new HashMap<String, Object>();
			parsedMap.put("reports",reports);
			parsedMap.put("errors", errorAccounts);			
			return parsedMap;				
		}

		

		
		/*
		 * Added by Jeevan on July 24, 2013. 
		 * 
		 * Method to obtain Date value directly from Cell.
		 * Coded it in order to convert all Date formats to MM/dd/yyyy;
		 */
		public  Date getCellValueAsDate(Cell cell) {
				String strCellValue = null;
				Date date=null;
				if (cell != null) {
					switch (cell.getCellType()) {
					 	case Cell.CELL_TYPE_STRING:
							strCellValue = cell.toString();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								strCellValue = dateFormat.format(cell.getDateCellValue());
							} 
							else {
								Double value = cell.getNumericCellValue();
								Long longValue = value.longValue();
								strCellValue = new String(longValue.toString());
							}
							break;
						}
			 }				
			try{
				if(null!=strCellValue && strCellValue!=""){
					date=new SimpleDateFormat("MM/dd/yyyy",Locale.US).parse(strCellValue);
				}
			}
			catch(ParseException e){
				log.error("Unable to PArsed Date from Supplier Reports "+e.toString());
			}		
			return date;
		}
		
		
		
   /**********************************************************************************************************************************************/
		/*
		 * Added by Jeevan on January 27, 2014 as per Clients request to handle Mutiple Date Formats
		 */
		private String formatDateString(Cell cell){
			String result="";
			Date date=null;
			final List<String> dateFormats = Arrays.asList("MM/dd/yyyy","M/d/yyyy","MM/dd/yy","M/d/yy","yyyy-MM-dd","dd/MM/yyyy","dd-MM-yyyy","dd-MM-yy","dd/MM/yy");
			for(String format: dateFormats){
				try{					
					SimpleDateFormat sdf = new SimpleDateFormat(format);	       
		           result=sdf.format(cell.getDateCellValue());		           
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}  
		       return result;		    
		}
		
		
		
		/*
		 * Added by Jeevan on January 27, 2014 as per Clients request to handle Mutiple Date Formats
		 */
		private Date parseDateString(String  date){
			Date result=null;			
			final List<String> dateFormats = Arrays.asList("MM/dd/yyyy","M/d/yyyy","MM/dd/yy","M/d/yy","dd-MM-yyyy","dd-MM-yy","dd/MM/yy","dd-MMM-yyyy","dd-MMM-yy","MMM-dd-yyyy","MMM-dd-yy","yyyy-MM-dd","dd/MM/yyyy");
			for(String format: dateFormats){
				try{					
					SimpleDateFormat sdf = new SimpleDateFormat(format);	       
		           result=sdf.parse(date);				           
				}
				catch(Exception e){
					
				}
			}  
		       return result;		    
		}
		
 /***********************************************************************************************************************************************************************/		
		
		
		
		
		
		
				
		//for Getting File Extensions..
		private String getFileExtension(String fileName){
			Assert.notNull(fileName);
			int mid = fileName.lastIndexOf(".");
			String ext = fileName.substring(mid + 1, fileName.length());		
			return ext;
		}
		
		
		//for validating SupplierFiles by its Name...		
		public SupplierFiles getSupplierFileByName(String fileName) throws SupplierFileNotFoundException{
			log.info("inside getSupplierFileByName()");
			SupplierFiles file=this.supplierDao.getSupplierFilesByFileName(fileName);
			return file;
		}
		
		
		//getting all SupplierFiles...
		public ArrayList<SupplierFilesDto> getSupplierFiles()throws SupplierFileNotFoundException{
			log.info("inside getSupplierFiles()");
			ArrayList<SupplierFiles> files=this.supplierDao.getSupplierFiles();
			ArrayList<SupplierFilesDto> fileDtos=new ArrayList<SupplierFilesDto>();
			for(SupplierFiles file:files){
				SupplierFilesDto fileDto=SupplierFilesDto.populateSupplierFiles(file);
				fileDtos.add(fileDto);
			}
			return fileDtos;
		}
		
		
	/*
	 * Added by Jeevan on August 09,2013 to get all Supplier Reports..
	 */
	public ArrayList<SupplierReportsDto> getSupplierReportsFromDao() throws SupplierReportsNotFoundException{
		ArrayList<SupplierReports> reports=this.supplierDao.getSupplierReports();
		ArrayList<SupplierReportsDto> reportsDto=new ArrayList<SupplierReportsDto>();
		if(!reports.isEmpty()){
			for(SupplierReports report:reports){
				SupplierReportsDto reportDto=SupplierReportsDto.populateSupplierReportsDto(report);
				reportsDto.add(reportDto);
			}			
		}
		return reportsDto;
	}
	
	/*
	 * Added by Jeevan on August 09, 2013 to get Supplier Reports between Days;;
	 */
	
	public ArrayList<SupplierReportsDto> getSupplierReportsBetweenDaysFromDao(Date startDate,Date endDate,String supplier)throws SupplierReportsNotFoundException{
		log.info("inside getSupplierReportsBetweenDaysFromDao()");
		ArrayList<SupplierReports> reports=this.supplierDao.getSupplierReportsBetweenDays(startDate, endDate,supplier);
		ArrayList<SupplierReportsDto> reportDtos=new ArrayList<SupplierReportsDto>();
		if(!reports.isEmpty()){
			for(SupplierReports report:reports){
				SupplierReportsDto reportDto=SupplierReportsDto.populateSupplierReportsDto(report);
				reportDto.setRstartDate(startDate);
				reportDto.setRendDate(endDate);
				reportDtos.add(reportDto);
			}
		}
		return reportDtos;
	}
	
	
	/*
	 * Added by Jeevan on August 09, 2013 to get Supplier Reports between Days;;
	 */
	
	public ArrayList<SupplierReportsDto> getSupplierReportsOfAccountBetweenDaysFromDao(Date startDate,Date endDate,String businessName)throws SupplierReportsNotFoundException{
		log.info("inside getSupplierReportsBetweenDaysFromDao()");
		ArrayList<SupplierReports> reports=this.supplierDao.getSupplierReportsByBusinessNameBetweenDays(startDate, endDate, businessName);
		ArrayList<SupplierReportsDto> reportDtos=new ArrayList<SupplierReportsDto>();
		if(!reports.isEmpty()){
			for(SupplierReports report:reports){
				SupplierReportsDto reportDto=SupplierReportsDto.populateSupplierReportsDto(report);
				reportDto.setRstartDate(startDate);
				reportDto.setRendDate(endDate);
				reportDtos.add(reportDto);
			}
		}
		return reportDtos;
	}
	
	
	/*
	 * Added by Jeevan on August 28,2013..
	 * Method to Get Reports Not in Pipeline
	 */
	public ArrayList<SupplierReportsDto> getSupplierReportsNotinPipeline(Date startDate,Date endDate,String supplier)throws SupplierReportsNotFoundException{
		log.info("inside getSupplierReportsBetweenDaysFromDao()");
		ArrayList<SupplierReports> reports=this.supplierDao.getSupplierReportsNotinPipeline(startDate, endDate, supplier);
		ArrayList<SupplierReportsDto> reportDtos=new ArrayList<SupplierReportsDto>();
		if(!reports.isEmpty()){
			for(SupplierReports report:reports){
				SupplierReportsDto reportDto=SupplierReportsDto.populateSupplierReportsDto(report);
				reportDto.setRstartDate(startDate);
				reportDto.setRendDate(endDate);
				reportDtos.add(reportDto);
			}
		}
		return reportDtos;
	}
	
	
	
	
	
	/*
	 * Added by Jeevan on August 09,2013.. for getting weekly/monthly ordersss
	 */
	
	public ArrayList<SupplierReportsDto> getSupplierReportsByTerm(String term)throws SupplierReportsNotFoundException{
		log.info("inside getSupplierReports()");
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 1);
		Date endDate=cal.getTime();
		Date startDate=null;
		if(term.equalsIgnoreCase("week")){
			cal.add(Calendar.DATE, -7);
			startDate=cal.getTime();			
		}
		else if(term.equalsIgnoreCase("month")){
			cal.add(Calendar.MONTH,-1);
			startDate=cal.getTime();			
		}
		ArrayList<SupplierReportsDto> reportDtos=new ArrayList<SupplierReportsDto>();
		ArrayList<SupplierReports> reports=this.supplierDao.getSupplierReportsBetweenDays(startDate, endDate);
		if(!reports.isEmpty()){
			for(SupplierReports report:reports){
				SupplierReportsDto reportDto=SupplierReportsDto.populateSupplierReportsDto(report);
				reportDto.setRstartDate(startDate);
				reportDto.setRendDate(new Date());
				
				reportDtos.add(reportDto);
				
			}
		}
		return reportDtos;
	}
	
	
	
	/*
	 * Created by Jeevan on October 18,2013.
	 * Method to Update not Updated Supplier Reports To Pipeline Orders
	 * 
	 *  Work Flow:
	 *   
	 *   1. Get All the Not Updated Supplier Reports from DB.
	 *   2. Iterating through Reports
	 *      a. Get ORder whose Account n Date Start Date matches with Pipeline Orders
	 *         i. If found Update Pipeline, Supplier Report status to Updated Pipeline..
	 *         ii. Add Supplier Report to Successfully Added Reports
	 *      else
	 *         1. Add Reports to Failed Reports
	 *     Send both reports back to Controller 
	 *      
	 */
	 public Map<String,Object> processUploadingLeftOverSupplierReportUpdationToPipeline(){
		 log.info("inside processUploadingLeftOverSupplierReportUpdationToPipeline()");
		 ArrayList<SupplierReports> supplierReports=null;
		 ArrayList<SupplierReports> updatedReports=new ArrayList<SupplierReports>();
		 ArrayList<SupplierReports>  notUpdatedReports=new ArrayList<SupplierReports>();	
		 
		 ArrayList<SupplierReportsDto> updatedReportsDto=new ArrayList<SupplierReportsDto>();
		 ArrayList<SupplierReportsDto> notUpdatedReportsDto=new ArrayList<SupplierReportsDto>();
		 
		 try{
			 supplierReports=this.supplierDao.getSupplierReportsNotinPipeline(null, null, ""); //making use of existing methods
		 }
		 catch(SupplierReportsNotFoundException e){
			 log.info("NO Supplier Reports found which are yet to be Updated to Pipeline()");			 
		 }
		 
		 if(null!=supplierReports){
			 for(SupplierReports report:supplierReports){				
				 try{
					 this.UpdateSupplierReportToPipeline(report);                 //making use of existing methods	
					 updatedReports.add(report);
				 }
				 catch(Exception e){
					 notUpdatedReports.add(report);
					 log.error("No Order Found"+e.toString());
				 }				 
			 }
		 }		 
		 for(SupplierReports reports:updatedReports){
			 SupplierReportsDto reportDto=new SupplierReportsDto();
			 reportDto.setAccountNumber(reports.getAccountNumber());
			 reportDto.setContractStartDate(reports.getContractStartDate());
			 reportDto.setCustomerName(reports.getCustomerName());
			 reportDto.setTotalCommissionPaid(reports.getTotalCommissionPaid());
			 
			 updatedReportsDto.add(reportDto);
		 }		 
		 for(SupplierReports reports:notUpdatedReports){
			 SupplierReportsDto reportDto=new SupplierReportsDto();
			 reportDto.setReportId(reports.getId());
			 reportDto.setAccountNumber(reports.getAccountNumber());
			 reportDto.setContractStartDate(reports.getContractStartDate());
			 reportDto.setCustomerName(reports.getCustomerName());
			 reportDto.setTotalCommissionPaid(reports.getTotalCommissionPaid());
			 reportDto.setDatePaid(reports.getUpfrontPaidDate());
			 notUpdatedReportsDto.add(reportDto);
		 }
		 Map<String, Object> resultMap=new HashMap<String, Object>();
		 resultMap.put("updatedReports", updatedReportsDto);
		 resultMap.put("notUpdatedReports", notUpdatedReportsDto);		 
		 return resultMap;
	 }
	 
	 
	/*
	 * Added by Jeevan on November 20,2013. Method to Manually Upload Existinf Supplier Report
	 */
	public Integer updateSupplierReportManually(ArrayList<Integer> ids) throws Exception{
		
		ArrayList<SupplierReports> supplierReports=this.supplierDao.getSupplierReportsByIds(ids);
		for(SupplierReports report:supplierReports){
			report.setUpdatedPipeline(true);
		}
		Integer result=this.supplierDao.saveorUpdateSupplierReports(supplierReports);
		return result;
	}
	 
	
	
	
	/*
	 * Added by Jeevan on November 20,2013
	 */
	private void removeSupplierReportFromPipeline(SupplierReports report ) throws Exception{
		Orders order=null;
		Integer accCount=this.dealSheetDao.getAccountsCountByAccountNo(report.getAccountNumber().trim());
		Integer dateCount=0;
		try{
			dateCount=this.dealSheetDao.getAccountsCountByAccountNoDealDate(report.getAccountNumber().trim(), report.getContractStartDate());
		}
		catch(Exception e){				
		}
		Integer termCount=0;
		try{
			termCount=this.dealSheetDao.getAccountsCountByKwHTerm(report.getAccountNumber().trim(), report.getKwh(), report.getTerm());
		}
		catch(Exception e){
			
		}			
		if(accCount==1){				
			order=this.dealSheetDao.getOrderByAccountNumber(report.getAccountNumber());
		}
		else if(dateCount==1){
			order=this.dealSheetDao.getOrderByAccountNumberandStartDate(report.getAccountNumber(), report.getContractStartDate());
		}
		else if(termCount==1){
			order= this.dealSheetDao.getOrderByAccountNoKwhandTerm(report.getAccountNumber(), report.getKwh(), report.getTerm());
		}
		else{
			throw new OrderNotFoundException();
		} 
	/*	modified by bhagya on may23rd,2014,here we are using handling upfrontcommisions method*/
		order=this.handleUpfrontCommissions(order,report.getTotalCommissionPaid(),report.getUpfrontPaidDate(),true);
		
		
		/*Double prevCommission=order.getUpfrontCommission();
		Double newCommission=report.getTotalCommissionPaid();					
		Double aggCommission=prevCommission-newCommission;					
		order.setUpfrontCommission(aggCommission);*/
		Integer result=this.adminDao.editPipelineData(order);		
		if(result>0){
			log.info("Successfully Updated Pipline");
			report.setUpdatedPipeline(true);
			this.supplierDao.saveorUpdateSupplierReports(report);
		}
		else{
			throw new Exception();
		}
	}
	
	
	/*
	 * Added by Jeevan on November 20, 2013
	 * Method to Remove Uploded Supplier Reports from Pipeline
	 * Steps
	 * 
	 * 1. Get Reports By FileID
	 * 2. Update Pipeline
	 * 3. Delete Reports
	 * 4. Delete File
	 * 5. Return
	 * 
	 */
	@Transactional
	public String processRemovingSupplierReportsFromPipeline(Integer fileId) throws Exception{
		log.info("inside processRemovingSupplierReportsFromPipeline()");
		
		ArrayList<SupplierReports> supplierReports=this.supplierDao.getSupplierReportsBySupplierFile(fileId);
		SupplierFiles supplierFile=this.supplierDao.getSupplierFilesById(fileId);
		for(SupplierReports report:supplierReports){
			try{					
				this.removeSupplierReportFromPipeline(report);					
			}
			catch(Exception e){
				log.info("Failed to Update Pipeline... "+e.toString());
			}
		}		
		Integer deleteResult=this.supplierDao.deleteSupplierReports(supplierReports);
		Integer fileResult=this.supplierDao.deleteSupplierFile(supplierFile);		
		if(fileResult>0 && deleteResult>0){
			return "success";
		}
		else{
			return "fail";
		}
	}
	
	
	
	
	
	//added by bhagya on may 23rd,2014,Method For getting Anniversary payments
	
	public Orders handleUpfrontCommissions(Orders order,Double reportTotalCommission,Date reportPaidDate,Boolean reportRemove) throws SupplierReportsNotFoundException{
	  		if(null!=order.getDealStartDate()){
				Integer dealMonths=this.adminService.getNumberOfMonthsBetweenStartdateAndCurrentDate(order.getDealStartDate());					
				Integer year=(dealMonths/12)+1;
				
				switch(year){
				case 1: Double prevCommission=0.0;
						if(null!=order.getUpfrontCommission())
							prevCommission=order.getUpfrontCommission();
						Double newCommission=reportTotalCommission;					
						Double aggCommission=0.0;
							if(reportRemove){
								aggCommission=prevCommission-newCommission;
							}
							else{
								aggCommission=prevCommission+newCommission;
							}
						order.setUpfrontCommission(aggCommission);		
							if(null!=reportPaidDate){
									order.setUpfrontPaidDate(reportPaidDate);
							}
							else{
								order.setUpfrontPaidDate(new Date());
							}
					break;
				case 2: Double prevCommission2=0.0;
						if(null!=order.getUpfrontCommission2())
							prevCommission2=order.getUpfrontCommission2();
						Double newCommission2=reportTotalCommission;
						Double aggCommission2=0.0;
						if(reportRemove){
							aggCommission2=prevCommission2-newCommission2;
						}
						else{
							aggCommission2=prevCommission2+newCommission2;
						}					
						order.setUpfrontCommission2(aggCommission2);
							if(null!=reportPaidDate){
							order.setUpfrontPaidDate2(reportPaidDate);
							}
							else{
							order.setUpfrontPaidDate2(new Date());
							}
						
					break;
				case 3: Double prevCommission3=0.0;
						if(null!=order.getUpfrontCommission3())
						      prevCommission3=order.getUpfrontCommission3();
						Double newCommission3=reportTotalCommission;					
						Double aggCommission3=0.0;
						if(reportRemove){
							aggCommission3=prevCommission3-newCommission3;
						}
						else{
							aggCommission3=prevCommission3+newCommission3;
						}							
						order.setUpfrontCommission3(aggCommission3);		
							if(null!=reportPaidDate){
								order.setUpfrontPaidDate3(reportPaidDate);
							}
							else{
								order.setUpfrontPaidDate3(new Date());
							}
					break;
					default:
						 Double prevCommission4=0.0;
							if(null!=order.getUpfrontCommission())
								prevCommission4=order.getUpfrontCommission();
							Double newCommission4=reportTotalCommission;					
							Double aggCommission4=0.0;
								if(reportRemove){
									aggCommission4=prevCommission4-newCommission4;
								}
								else{
									aggCommission4=prevCommission4+newCommission4;
								}
							order.setUpfrontCommission(aggCommission4);		
								if(null!=reportPaidDate){
										order.setUpfrontPaidDate(reportPaidDate);
								}
								else{
									order.setUpfrontPaidDate(new Date());
								}
						break;
						
						
						
								
				}
		}
		return order;
	
	}
}
