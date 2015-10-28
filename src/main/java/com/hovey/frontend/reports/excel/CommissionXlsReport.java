package com.hovey.frontend.reports.excel;


import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.hovey.frontend.agent.dto.OrdersDto;

/*
 * Utility class to create Excel Files
 * it will b intiated on "pipelineExcel" return
 */

	
	
	
public class CommissionXlsReport extends AbstractExcelView{

	private static Logger log=Logger.getLogger(CommissionXlsReport.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("inside buildExcelDocument()");
		
		Map<String, OrdersDto> commissionData=(Map<String, OrdersDto>) model.get("orders");
		HSSFSheet sheet = workbook.createSheet("Commission Report");
		
		 CellStyle style = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setColor(HSSFColor.BLACK.index);
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setFont(font);
		
	        
	        CellStyle currency=workbook.createCellStyle();
			currency.setDataFormat((short)7);
		// Setting Headings..
			
		
		HSSFRow header = sheet.createRow(0);		
	
		header.createCell(0).setCellValue("Account Number");
		header.createCell(1).setCellValue("Business Name");
		header.createCell(2).setCellValue("Sent To Supplier Date");
		//added Deal Start date ,by bhagya on may 06th 2014
		header.createCell(3).setCellValue("Deal Start Date");
		header.createCell(4).setCellValue("Status");
		header.createCell(5).setCellValue("Contract Type");
		header.createCell(6).setCellValue("Expected Commission");
		header.createCell(7).setCellValue("Expected Term Commission");
		header.createCell(8).setCellValue("Commission Received");			
		header.createCell(9).setCellValue("Net Commission");
		if(null!=commissionData.get("order1").getTaxId().getTotalAccounts() && commissionData.get("order1").getTaxId().getTotalAccounts()>0){
			header.createCell(10).setCellValue("Total Accounts");
			header.createCell(11).setCellValue("Supplier");
		}
		else{
			header.createCell(10).setCellValue("Supplier");
		}
		
		header.setRowStyle(style);
		
		int rowNum = 2;
		//Iterator iterator = pipelineData.entrySet().iterator();
		
		HSSFCellStyle dateStyle=workbook.createCellStyle();
		CreationHelper createHelper=workbook.getCreationHelper();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
		// Setting Data for Cells.
		
		for(Map.Entry<String, OrdersDto> entry: commissionData.entrySet()){
			HSSFRow row = sheet.createRow(rowNum++);			
			row.createCell(0).setCellValue(entry.getValue().getAccountNumber());
			row.createCell(1).setCellValue(entry.getValue().getBusinessName());
			Cell cell2=row.createCell(2);
			if(null!=entry.getValue().getSentToSupplier()){
				cell2.setCellValue(entry.getValue().getSentToSupplier());
			}			
			cell2.setCellStyle(dateStyle);
			//added setting the value for deal start date,by bhagya on May 06th,2014
			Cell cell3=row.createCell(3);
			if(null!=entry.getValue().getDealStartDate()){
				cell3.setCellValue(entry.getValue().getDealStartDate());
			}
			cell3.setCellStyle(dateStyle);
			row.createCell(4).setCellValue(entry.getValue().getStatus());	
			row.createCell(5).setCellValue(entry.getValue().getContractType());
			if(null!=entry.getValue().getTaxId().getTotalAccounts() && entry.getValue().getTaxId().getTotalAccounts()>0){
				row.createCell(6).setCellValue(new Double(entry.getValue().getTaxId().getTotalExpectedCommission()));	
				row.getCell(6).setCellStyle(currency);
				row.createCell(7).setCellValue(new Double(entry.getValue().getTaxId().getTotalTermCommission()));	
				row.getCell(7).setCellStyle(currency);
				row.createCell(8).setCellValue(new Double(entry.getValue().getTaxId().getTotalCommissionReceived()));
				row.getCell(8).setCellStyle(currency);
				//System.out.println(entry.getValue().getNetCommission());
				row.createCell(9).setCellValue(new Double(entry.getValue().getNetCommission()));
				row.getCell(9).setCellStyle(currency);
				row.createCell(10).setCellValue(new Integer(entry.getValue().getTaxId().getTotalAccounts()));
				row.createCell(11).setCellValue(entry.getValue().getSupplierName().getSupplierName());
			}
			else{
				row.createCell(6).setCellValue(new Double(entry.getValue().getCommission()));	
				row.getCell(6).setCellStyle(currency);
				row.createCell(7).setCellValue(new Double(entry.getValue().getTermCommission()));	
				row.getCell(7).setCellStyle(currency);
				
				row.createCell(8).setCellValue(new Double(entry.getValue().getUpfrontCommission()));
				row.getCell(8).setCellStyle(currency);
				//System.out.println(entry.getValue().getNetCommission());
				row.createCell(9).setCellValue(new Double(entry.getValue().getNetCommission()));
				row.getCell(9).setCellStyle(currency);
				row.createCell(10).setCellValue(entry.getValue().getSupplierName().getSupplierName());
			}
		}
		
	
	
		for(int i=0;i<=12;i++){
			sheet.autoSizeColumn(i);
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		
		 response.setHeader("Content-Disposition", "inline; filename="+"Commission_Report_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"_"+cal.get(Calendar.YEAR)+".xls");
		  // Make sure to set the correct content type
		  response.setContentType("application/vnd.ms-excel");
		//workbook.write(response.getOutputStream());
		sheet.getWorkbook().write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	
	
}




	

