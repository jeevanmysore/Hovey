package com.hovey.dataload.service;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;
import javax.transaction.Transaction;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hovey.backend.admin.dao.AdminDao;
import com.hovey.backend.agent.dao.AgentDao;
import com.hovey.backend.agent.dao.DealSheetDao;
import com.hovey.backend.agent.exception.CustomersNotFoundException;
import com.hovey.backend.agent.exception.StateNotFoundException;
import com.hovey.backend.agent.model.BillingState;
import com.hovey.backend.agent.model.Customer;
import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.agent.model.State;
import com.hovey.backend.agent.model.Transactions;
import com.hovey.backend.agent.model.Utility;
import com.hovey.backend.supplier.dao.SupplierDao;
import com.hovey.backend.supplier.model.Supplier;
import com.hovey.backend.supplier.model.SupplierReports;
import com.hovey.backend.user.model.HoveyUser;
import com.lowagie.text.Utilities;


@Service("uploadService")
public class UploadServiceImpl implements UploadService {

	private static Logger log=Logger.getLogger(UploadServiceImpl.class);
	
	
	@Resource(name="adminDao")
	private AdminDao adminDao;
	
	@Resource(name="agentDao")
	private AgentDao agentDao;
	
	@Resource(name="supplierDao")
	private SupplierDao supplierDao;
	
	@Resource(name="dealSheetDao")
	private DealSheetDao dealSheetDao;
	
	

	public void parsePipelineData(String fileName,String agentName)throws Exception{
		log.info("inside Parse File()");
		
		
		Workbook workbook=null;
		Sheet sheet=null;
		Row titleRow=null;
		Cell titleCell=null;
		ArrayList<Orders> orders=new ArrayList<Orders>();
		
		String fileExtension=this.getFileExtension(fileName);
		FileInputStream fis=new FileInputStream(fileName);
		
		//handling file type extensions
		if(fileExtension.equalsIgnoreCase("xlsx")){
			workbook=new XSSFWorkbook(fis);
			workbook.createCellStyle().setAlignment(XSSFCellStyle.ALIGN_LEFT);
		}
		else if(fileExtension.equalsIgnoreCase("xls")){				
			workbook=new HSSFWorkbook(fis);
			workbook.createCellStyle().setAlignment(HSSFCellStyle.ALIGN_LEFT);				
		}	
		
		sheet=workbook.getSheetAt(0);
		String username=agentName.replaceAll("\\s+", "");
		HoveyUser agent=this.agentDao.getUserByUsername(username);
		java.util.Iterator<Row> rowIterator=sheet.iterator();
		int temp=0;
		
		Date compareDate=null;
		Transactions trans=null;
		Integer transactionId=0;
		
		Supplier suppliers=null;
		State st=null;
		
		while(rowIterator.hasNext()){
			Row row=rowIterator.next();
			if(temp>=1){ //null check of row..
				if(null!=row.getCell(0) && row.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK){					
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					if(row.getCell(4).getStringCellValue().equalsIgnoreCase(agentName)   || row.getCell(4).getStringCellValue().equalsIgnoreCase("Tracy2") ){
						
						Orders order=new Orders();						
			  //1. Agent 			
						order.setAgent(agent);	
						
					
						Cell cell3=row.getCell(3);						
						Date date=null;
						if (DateUtil.isCellDateFormatted(cell3)) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							String dateString = dateFormat.format(cell3.getDateCellValue());
							date=new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
						} 	
						
						if(temp>=2){
							if(date.equals(compareDate)){
								
								//use existing Transaction Id
								trans=this.dealSheetDao.getTransactionById(transactionId);
								
							}
							else{
								
								compareDate=date;
								//create new Transaction Id.
								trans=this.dealSheetDao.addTransaction(new Transactions());		
								transactionId=trans.getId();
							}
						}
						else{
							compareDate=date;
							trans=this.dealSheetDao.addTransaction(new Transactions());		
							transactionId=trans.getId();
						}
						
						
			//2. Order Date..
						order.setOrderDate(date);
						
						Cell cell6=row.getCell(6);
						cell6.setCellType(Cell.CELL_TYPE_STRING);
						String supplier=cell6.getStringCellValue();
			// 3. Supplier
						suppliers=this.getSupplierFromExcelData(supplier);
						order.setSupplierName(suppliers);
			
			//4,5. States
						Cell cell7=row.getCell(7);
						cell7.setCellType(Cell.CELL_TYPE_STRING);
						String state=cell7.getStringCellValue();	
						st=this.getStateFromExcel(state);
						BillingState bt=this.getBillingStateFromExcel(state);
						order.setBillingState(bt);
						order.setServiceState(st);
						st=null;bt=null;
			//defaulting Address.
		    //6,7,8,9,10,11,12,13 Svc n Billing Addresses
						order.setBillingCity("No Info");
						order.setBillingStreet("No Info");
						order.setBillingUnit("No Info");
						order.setBillingZip("00000");
						
						order.setServiceCity("No Info");
						order.setServiceStreet("No Info");
						order.setServiceUnit("NO Info");
						order.setServiceZip("00000");
						
						
						Cell cell8=row.getCell(8);
						cell8.setCellType(Cell.CELL_TYPE_STRING);
						String utility=cell8.getStringCellValue();
						utility=utility.replaceAll("\\s+", "");
						utility=utility.replaceAll("&", "");
				//14.  Utility
						order.setUtility(this.getUtilityFromExcelDate(utility));
						
						
						Cell cell9=row.getCell(9);
						cell9.setCellType(Cell.CELL_TYPE_STRING);
						String businessName=cell9.getStringCellValue();
				//15   BusinessName		
					order.setBusinessName(businessName);
					
					String account="";
					Cell cell10=row.getCell(10);
					if(null!=cell10 && cell10.getCellType()!=Cell.CELL_TYPE_BLANK){
						cell10.setCellType(Cell.CELL_TYPE_STRING);
						account= cell10.getStringCellValue();
					}
					
			//16   account Number		
					System.out.println(account);
					System.out.println(new DataFormatter(Locale.US).formatCellValue(cell10));
				order.setAccountNumber(account);
			//	order.setTpv(Long.valueOf(account));
				
				Cell cell12=row.getCell(12);
				cell12.setCellType(Cell.CELL_TYPE_NUMERIC);
				Integer kWh=(int) cell12.getNumericCellValue();
			//17 kWh
				if(null!=kWh)
				order.setKwh(kWh);
				
			    Cell cell13=row.getCell(13);
			    cell13.setCellType(Cell.CELL_TYPE_NUMERIC);
			    Double commission=cell13.getNumericCellValue();
			//18  Commission
			    if(null!=commission)
			    order.setCommission(commission);
			   
			 
			    Cell cell14=row.getCell(14);						
				Date date3=null;
				if (DateUtil.isCellDateFormatted(cell14)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					String dateString = dateFormat.format(cell14.getDateCellValue());
					date3=new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
				} 						
				
	//19. Sent Tpv Supplier Date.
				order.setSentToSupplier(date3);
				
				Cell cell15=row.getCell(15);
				
				cell15.setCellType(Cell.CELL_TYPE_STRING);
				String status=cell15.getStringCellValue();
				
				if(status.equalsIgnoreCase("A")){
					order.setStatus("approved");
					order.setContractType("new");
					if(supplier.equalsIgnoreCase("Champion Energy")){
						order.setCommissionRate(0.009);
					}
					else if(supplier.equalsIgnoreCase("Glacial Energy")){
						order.setCommissionRate(0.007);
					}
					else{
						order.setCommissionRate(0.004);					
					}
					
				}
				else if(status.equalsIgnoreCase("UR")){
					order.setStatus("under review");
					order.setContractType("new");
					if(supplier.equalsIgnoreCase("Champion Energy")){
						order.setCommissionRate(0.009);
					}
					else if(supplier.equalsIgnoreCase("Glacial Energy")){
						order.setCommissionRate(0.007);
					}
					else{
						order.setCommissionRate(0.004);					
					}
				}
				else if(status.equalsIgnoreCase("CR")){
					order.setStatus("approved");
					order.setContractType("renewal");
					if(supplier.equalsIgnoreCase("Champion Energy")){
						order.setCommissionRate(0.005);
					}
					else if(supplier.equalsIgnoreCase("Glacial Energy")){
						order.setCommissionRate(0.007);
					}
					else{
						order.setCommissionRate(0.004);					
					}
				}
				else if(status.equalsIgnoreCase("HOV3")){
					order.setStatus("approved");
					order.setContractType("hov3");
					if(supplier.equalsIgnoreCase("Champion Energy")){
						order.setCommissionRate(0.009);
					}
					else if(supplier.equalsIgnoreCase("Glacial Energy")){
						order.setCommissionRate(0.007);
					}
					else{
						order.setCommissionRate(0.004);					
					}
				}
				
		//20 ,21 Status, Contract Type		
			order.setStatus("rescinded");
			if(status.equalsIgnoreCase("A")){
				order.setContractType("new");
			}
			
				
			
			Cell cell16=row.getCell(16);
			Date date4=null;
			if(null!=cell16 && cell16.getCellType()!=Cell.CELL_TYPE_BLANK ){				
				if (DateUtil.isCellDateFormatted(cell16)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					String dateString = dateFormat.format(cell16.getDateCellValue());
					date4=new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
				} 	
		   }
		//22 Deal Start Date	
		   order.setDealStartDate(date4);
				
		  Double upfrontCommission=0.0;
		   Cell cell17=row.getCell(17);
		   if(null!=cell17 && cell17.getCellType()!=Cell.CELL_TYPE_BLANK){
			   cell17.setCellType(Cell.CELL_TYPE_NUMERIC);
			   upfrontCommission=cell17.getNumericCellValue();
		   }
		   
		    
		//23  Upfront Commission
		    if(null!=upfrontCommission)
		    order.setUpfrontCommission(upfrontCommission);
		    
		    
		 
			Cell cell18=row.getCell(18);
			Date date5=null;
			if(null!=cell18 && cell18.getCellType()!=Cell.CELL_TYPE_BLANK ){				
				if (DateUtil.isCellDateFormatted(cell18)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					String dateString = dateFormat.format(cell18.getDateCellValue());
					date5=new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
				} 	
		   }
		//24 Deal Start Date	
		   order.setUpfrontPaidDate(date5);  
		    
		  
		   Cell cell19=row.getCell(19);
		   cell19.setCellType(Cell.CELL_TYPE_STRING);
		   String term=cell19.getStringCellValue();
		   
	  // 25. Term
		   order.setTerm(term);
		   
	 //26  Deal End Date
		   if(null!=date4 && null!= term)
			   order.setDealEndDate(this.getEndDateFromStartDateandTerm(date4, term));
		   
		   
		   Cell cell21=row.getCell(21);
		   Double rate=0.0;
		   if(null!=cell21 && cell21.getCellType()!=Cell.CELL_TYPE_BLANK){
			   cell21.setCellType(Cell.CELL_TYPE_NUMERIC);
			  rate=cell21.getNumericCellValue();
		   }
		   
	//27    Rate	   
			order.setRate(rate);
			
			
			Cell cell23=row.getCell(23);
			String notes="";
			if(null!=cell23 && cell23.getCellType()!=Cell.CELL_TYPE_BLANK){
				cell23.setCellType(Cell.CELL_TYPE_STRING);
				 notes=cell23.getStringCellValue();
			}
	//28     Notes		
			order.setAgentNotes(notes);
			
	//29 , 30 Fax Received, QA
			order.setFaxReceived(false);
			order.setQA(false);
			
	//31 Service
			order.setService("electric");
	//32 Commission Rate				
			
			Calendar cal=Calendar.getInstance();
			cal.set(Calendar.YEAR, 2013);
			cal.set(Calendar.MONTH, 7);
			cal.set(Calendar.DATE, 01);
			Date comDate =new Date(cal.getTimeInMillis());
	//33 Agent Commission Status		
			if(date.after(comDate)){
				order.setAgentCommissionStatus("unpaid");
			}
			else{
				order.setAgentCommissionStatus("paid");
			}
			
			
	//34 Transaction Id		
			order.setTransactionId(trans);
			
			
	//Phone No..		
		Cell cell11=row.getCell(11);
		String phoneNo="";
		if(null!=cell11 && cell11.getCellType()!=Cell.CELL_TYPE_BLANK){
			cell11.setCellType(Cell.CELL_TYPE_STRING);
			phoneNo=cell11.getStringCellValue();
		}
			
	 //Handling TaxId
					String taxId="";
					Cell cell20=row.getCell(20);
					if(null!=cell20 && cell20.getCellType()!=Cell.CELL_TYPE_BLANK){
						cell20.setCellType(Cell.CELL_TYPE_STRING);
						taxId=cell20.getStringCellValue();
						taxId=taxId.replaceAll("-","");
						Customer cust=null;
						try{
							cust=this.dealSheetDao.getCustomerByTaxId(taxId);
							order.setTaxId(cust);
						}
						catch(Exception e){
							cust=new Customer();
							cust.setTaxId(taxId);
							cust.setFirstName(businessName);
							cust.setLastName("No Info");
							cust.setEmail("NO Info");
							cust.setFaxNo("No Info");
							cust.setTaxExempt(false);
							cust.setTitle("No Info");
							cust.setType("commercial");
							cust.setPhoneNo(phoneNo);
						this.dealSheetDao.saveCustomerToDB(cust);	
						order.setTaxId(cust);
						}				
					}
					else{
						Customer cust=new Customer();
						cust.setCustomerId(1);
						order.setTaxId(cust);
					}	
					
					
	 			this.dealSheetDao.saveDealSheetToDB(order);
					
						orders.add(order);
					}
				}	
			}			
			temp++;
	  }	
		
	//	this.dealSheetDao.saveDealSheetOrdersToDB(orders);
		System.out.println("Sizeee "+orders.size());			
		
	}
	
	
	
	
	
	
	public ArrayList<SupplierReports> parseSupplierReports(String fileName)throws Exception{
		log.info("inside Parse sUPPLIERrEPORTS File()");
		
		
		Workbook workbook=null;
		Sheet sheet=null;
		
	
		
		String fileExtension=this.getFileExtension(fileName);
		FileInputStream fis=new FileInputStream(fileName);
		
		//handling file type extensions
		if(fileExtension.equalsIgnoreCase("xlsx")){
			workbook=new XSSFWorkbook(fis);
			workbook.createCellStyle().setAlignment(XSSFCellStyle.ALIGN_LEFT);
		}
		else if(fileExtension.equalsIgnoreCase("xls")){				
			workbook=new HSSFWorkbook(fis);
			workbook.createCellStyle().setAlignment(HSSFCellStyle.ALIGN_LEFT);				
		}	
		
		sheet=workbook.getSheetAt(0);
		
		java.util.Iterator<Row> rowIterator=sheet.iterator();
		int temp=0;
		
		ArrayList<SupplierReports> reports=new ArrayList<SupplierReports>();
		
		while(rowIterator.hasNext()){
			Row row=rowIterator.next();
			if(temp>=1 && temp<100){ //null check of row..
				if(null!=row.getCell(0) && row.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK){	
					SupplierReports report =new SupplierReports();
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					
						
									
										
										
							
									///1. Customer
									 Cell cell2=row.getCell(2);
									 cell2.setCellType(Cell.CELL_TYPE_STRING);
									 String businessName=cell2.getStringCellValue();					 
									 report.setCustomerName(businessName);
									 
									
								 	//2. Account
									 String account="";
									 Cell cell3=row.getCell(3);
										if(null!=cell3 && cell3.getCellType()!=Cell.CELL_TYPE_BLANK){
											cell3.setCellType(Cell.CELL_TYPE_STRING);
											account= cell3.getStringCellValue();
										}
									report.setAccountNumber(account);
										
									
									//3.  Start Date 
							   
							 
							    Cell cell7=row.getCell(7);						
								Date date3=null;
								/*if (DateUtil.isCellDateFormatted(cell7)) {
									SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									String dateString = dateFormat.format(cell7.getDateCellValue());
									date3=new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
								} */
								if(null!=row.getCell(7)){
									date3=this.getCellValueAsDate(row.getCell(7));			
									report.setContractStartDate(date3);
								}
								
								
								
								
								
								
								
								   Cell cell9=row.getCell(9);
								   cell9.setCellType(Cell.CELL_TYPE_STRING);
								   String term=cell9.getStringCellValue();
								   
							  // 4. Term
								   report.setTerm(term);
								
								
						  Double upfrontCommission=0.0;
						   Cell cell25=row.getCell(25);
						   if(null!=cell25 && cell25.getCellType()!=Cell.CELL_TYPE_BLANK){
							   cell25.setCellType(Cell.CELL_TYPE_NUMERIC);
							   upfrontCommission=cell25.getNumericCellValue();
						   }	   
						    
						//5 Upfront Commission
						    if(null!=upfrontCommission)
						    report.setTotalCommissionPaid(upfrontCommission);		
						    
						   
						   Calendar cal=Calendar.getInstance();
						   cal.set(Calendar.DATE, 18);
						   cal.set(Calendar.MONTH, 6);
						   cal.set(Calendar.YEAR, 2013);
						   
						   Date date=new Date(cal.getTimeInMillis());
						   
						   report.setUpfrontPaidDate(date);
						    
						    
						    
					reports.add(report);
				}
				
			}			
			temp++;
	  }	
		
	//	this.dealSheetDao.saveDealSheetOrdersToDB(orders);
		System.out.println("Sizeee "+reports.size());		
		
		return reports;
		
	}
	
	
	
	
	public  Date getCellValueAsDate1(Cell cell) {
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//calculation of Deal End Date based on Term and Deal Start Date
	public Date getEndDateFromStartDateandTerm(Date dealStartDate,String term){
		Calendar calendar=Calendar.getInstance();
		calendar.clear();
		calendar.setTime(dealStartDate);
		calendar.add(Calendar.MONTH, Integer.parseInt(term));
		/* *****Modified by Jeevan on July 18,2013 to considered Deal End Date to include exclusion Date too******
		 *   calendar.add(Calendar.DATE, -1);  //No Need to Reduce a day for Deal End as deal will be from 01/01/xx to 01/01/xy
		 *   
		 * *******/		      
		Date dealEndDate=calendar.getTime();
		return dealEndDate;
	}
	
	//using ENum to Handle All Possible Solutions
	private enum Util{
		AEPOHCS,AEPOHCSP,AEPOHOP,ComEd,ConEd,ComED,FEIC,FEOE,FETE,JCPL,Penn,Ameren,Dayton,Duke,MetEd,PECO,PotEd,PPL,ACE,MetED; 
		
	}
	
	//getting Utility
	public Utility getUtilityFromExcelDate(String util){
		
		Util uti=Util.valueOf(util);
		Utility utility=null;
		switch (uti) {
		case AEPOHCS:
			try {
				utility=this.dealSheetDao.getUtilityByName("AEP CSP");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
			
		
		case AEPOHCSP:
			try {
				utility=this.dealSheetDao.getUtilityByName("AEP CSP");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case AEPOHOP:
			try {
				utility=this.dealSheetDao.getUtilityByName("AEP OP");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case ComEd:
			try {
				utility=this.dealSheetDao.getUtilityByName("ComEd");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case ComED:
			try {
				utility=this.dealSheetDao.getUtilityByName("ComEd");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case ConEd:
			try {
				utility=this.dealSheetDao.getUtilityByName("ComEd");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
			
		case FEIC:
			try {
				utility=this.dealSheetDao.getUtilityByName("Illuminating Co");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

			
		case FEOE:
			try {
				utility=this.dealSheetDao.getUtilityByName("Ohio Edison");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;	
			
			
			
		case FETE:
			try {
				utility=this.dealSheetDao.getUtilityByName("Toledo Edison");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
			
		case Penn:
			try {
				utility=this.dealSheetDao.getUtilityByName("Penn Power");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
			
		default:
			utility=this.getUtility(util);
		}
		
		
		
		return utility;		
	}
	
	
	//getting Normal case Utilities
	private Utility getUtility(String util){
		Utility utility=null;
		try {
			utility = this.dealSheetDao.getUtilityByName(util);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utility;
	}
	
	
	//getting Supplier
		public Supplier getSupplierFromExcelData(String Supplier){
			
			Supplier supplier=null;
			String sup;
			
			if(Supplier.equalsIgnoreCase("G")){
				sup="Glacial Energy";
			}
			else if(Supplier.equalsIgnoreCase("L")){
				sup="Liberty";				
			}
			else{
				sup="Champion Energy";
			}
			
			try {
				supplier=this.supplierDao.getSupplierByName(sup);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return supplier;			
		}
	
	//Getting State..
		public State getStateFromExcel(String state){
			
			try {
				State states=this.dealSheetDao.getStateByStateName(state);
				return states;
			} catch (StateNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		
		
		public BillingState getBillingStateFromExcel(String state){
			
			try {
				BillingState states=this.dealSheetDao.getBillingStateByStateName(state);
				return states;
			} catch (StateNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}	
		
	
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
				e.printStackTrace();
			}		
			return date;
		}





		//for Getting File Extensions..
		private String getFileExtension(String fileName){
			Assert.notNull(fileName);
			int mid = fileName.lastIndexOf(".");
			String ext = fileName.substring(mid + 1, fileName.length());		
			return ext;
		}
		
		
	
		
	
		@Transactional
		public void processReports(String fileName)throws Exception{
			ArrayList<SupplierReports> reports=this.parseSupplierReports(fileName);
			int i=0;
			if(null!=reports){
				ArrayList<Orders> orders=new ArrayList<Orders>();
				for(SupplierReports report:reports){				
					try{
						Orders order=this.dealSheetDao.getOrderByAccountNumberBusinessNameorTerm(report.getAccountNumber(), report.getCustomerName(), report.getTerm());
						
						if(null!=order){
							if(null!=order.getDealStartDate() || order.getUpfrontCommission()>0.0){
								
							}
							else{
								i++;
								System.out.println(i);
								System.out.println(report.getAccountNumber());
								order.setDealStartDate(report.getContractStartDate());															
								order.setDealEndDate(this.getEndDateFromStartDateandTerm(report.getContractStartDate(), report.getTerm()));
								
								/*System.out.println(report.getTotalCommissionPaid());
								order.setUpfrontCommission(report.getTotalCommissionPaid());*/
				//				order.setUpfrontPaidDate(report.getUpfrontPaidDate());
								order.setQA(true);
								order.setFaxReceived(true);
								
								if(!order.getStatus().equalsIgnoreCase("Rescinded")){
									order.setStatus("false");									
								}
								
								
							/*	Double prevCommission=order.getUpfrontCommission();
								Double newCommission=report.getTotalCommissionPaid();					
								Double aggCommission=prevCommission+newCommission;					
								order.setUpfrontCommission(aggCommission);*/
								
								//orders.add(order);
								try{
									this.dealSheetDao.saveDealSheetToDB(order);
								}
								catch(Exception e){
									e.printStackTrace();
								}
							}
						}
					}
					catch(Exception e){
					
					}
				}
				/*try{
					this.saveOrders(orders);
				}
				catch(Exception e){
					e.printStackTrace();
				}*/
			}		
			
		}
		
		//@Transactional
		/*public void saveOrders(ArrayList<Orders> orders)throws Exception{
			ArrayList<Orders> tempOrders=new ArrayList<Orders>();
			
			for(int i=0;i<orders.size()-1;i++){
				
				Orders order1=orders.get(i);
				Orders order2=orders.get(i+1);
				
					if(order1.getOrderId().equals(order2.getOrderId())){
						Double aggCom=order1.getUpfrontCommission()+order2.getUpfrontCommission();
						order1.setUpfrontCommission(aggCom);
						tempOrders.add(order1);
						System.out.println("SSS "+order1.getOrderId());
						System.out.println("SSS "+order2.getOrderId());
					}
					else{
						
					}
				}	
			
			
			System.out.println("SIXE  "+tempOrders.size());
			
			for(Orders tempOrder:tempOrders){
				if(tempOrder.getOrderId()==0){
					tempOrders.remove(tempOrder);
				}
				
				System.out.println(tempOrder.getOrderId());
			}
			
			System.out.println("SIXE  "+tempOrders.size());
		//	this.dealSheetDao.saveDealSheetOrdersToDB(tempOrders);
	
		
		}*/

		
		
		
		

	
}
