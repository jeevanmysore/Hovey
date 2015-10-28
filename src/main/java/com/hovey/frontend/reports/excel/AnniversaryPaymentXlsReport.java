package com.hovey.frontend.reports.excel;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class AnniversaryPaymentXlsReport extends AbstractExcelView{

	private static org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(AnniversaryPaymentXlsReport.class);
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("inside buildExcelDocument()");
		
		Map<String, OrdersDto> commissionData=(Map<String, OrdersDto>) model.get("orders");
		HSSFSheet sheet = workbook.createSheet("Anniversary Payments Report");
		
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
		header.createCell(2).setCellValue("kWh");
		header.createCell(3).setCellValue("Term");
		header.createCell(4).setCellValue("Sent to Supplier Date");
		header.createCell(5).setCellValue("Deal Start Date");
		header.createCell(6).setCellValue("Status");
		header.createCell(7).setCellValue("Expected Upfront Commission");
		header.createCell(8).setCellValue("Expected Annual Commission");
		header.createCell(9).setCellValue("Anniversary Year");
		header.createCell(10).setCellValue("Annual Commission Received");			
		header.createCell(11).setCellValue("Paid Date");
		header.createCell(12).setCellValue("Supplier");	
		
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
			row.createCell(2).setCellValue(entry.getValue().getKwh());
			row.createCell(3).setCellValue(entry.getValue().getTerm());
			Cell cell4=row.createCell(4);
			if(null!=entry.getValue().getSentToSupplier()){
				cell4.setCellValue(entry.getValue().getSentToSupplier());
			}			
			cell4.setCellStyle(dateStyle);
			//added setting the value for deal start date,by bhagya on May 06th,2014
			Cell cell5=row.createCell(5);
			if(null!=entry.getValue().getDealStartDate()){
				cell5.setCellValue(entry.getValue().getDealStartDate());
			}
			cell5.setCellStyle(dateStyle);
			
			row.createCell(6).setCellValue(entry.getValue().getStatus());	
			
				row.createCell(7).setCellValue(new Double(entry.getValue().getCommission()));	
				row.getCell(7).setCellStyle(currency);
			
			row.createCell(8).setCellValue(new Double(entry.getValue().getTermCommission()));	
			row.getCell(8).setCellStyle(currency);
			if(null!=entry.getValue().getOrderYear()){
				row.createCell(9).setCellValue(entry.getValue().getOrderYear());
			}
			if(null!=entry.getValue().getUpfrontCommission()){
				row.createCell(10).setCellValue(new Double(entry.getValue().getUpfrontCommission()));
				row.getCell(10).setCellStyle(currency);
			}
			
			Cell cell11=row.createCell(11);
			if(null!=entry.getValue().getUpfrontPaidDate()){
				cell11.setCellValue(entry.getValue().getUpfrontPaidDate());
			}
			cell11.setCellStyle(dateStyle);
			row.createCell(12).setCellValue(entry.getValue().getSupplierName().getSupplierName());
	
		}
		
	
	
		for(int i=0;i<=12;i++){
			sheet.autoSizeColumn(i);
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		
		 response.setHeader("Content-Disposition", "inline; filename="+"Anniversary_Payments_Report_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"_"+cal.get(Calendar.YEAR)+".xls");
		  // Make sure to set the correct content type
		  response.setContentType("application/vnd.ms-excel");
		//workbook.write(response.getOutputStream());
		sheet.getWorkbook().write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	
}
