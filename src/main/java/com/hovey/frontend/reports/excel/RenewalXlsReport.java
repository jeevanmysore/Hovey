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

	
	
	
public class RenewalXlsReport extends AbstractExcelView{

	private static Logger log=Logger.getLogger(RenewalXlsReport.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("inside buildExcelDocument()");
		
		Map<String, OrdersDto> renewalData=(Map<String,OrdersDto>) model.get("renewals");
		HSSFSheet sheet = workbook.createSheet("Renewals Report");
		
		 CellStyle style = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setColor(HSSFColor.BLACK.index);
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setFont(font);
		
	        
	        CellStyle currency=workbook.createCellStyle();
			currency.setDataFormat((short)7);
			CellStyle num=workbook.createCellStyle();
			num.setDataFormat((short) 3);
		// Setting Headings..
		
		HSSFRow header = sheet.createRow(0);		
	
		header.createCell(0).setCellValue("Order Date");
		header.createCell(1).setCellValue("Account#");
		header.createCell(2).setCellValue("Business Name");
		header.createCell(3).setCellValue(" kWh ");
		header.createCell(4).setCellValue("Term");		
		header.createCell(5).setCellValue("Deal Start Date");			
		header.createCell(6).setCellValue("Rate");
		header.createCell(7).setCellValue("Contract Type");
		header.createCell(8).setCellValue("Commission");			
		header.createCell(9).setCellValue("Received Commission");
		header.createCell(10).setCellValue("Deal End Date");			
		
		
		
		
		header.setRowStyle(style);
		
		int rowNum = 2;
		//Iterator iterator = pipelineData.entrySet().iterator();
		
		HSSFCellStyle dateStyle=workbook.createCellStyle();
		CreationHelper createHelper=workbook.getCreationHelper();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
		// Setting Data for Cells.
		for(Map.Entry<String, OrdersDto> entry:renewalData.entrySet()){
			HSSFRow row = sheet.createRow(rowNum++);
			Cell cell0=row.createCell(0);
			if(null!=entry.getValue().getOrderDate()){
				cell0.setCellValue(entry.getValue().getOrderDate());
			}
			cell0.setCellStyle(dateStyle);
			row.createCell(1).setCellValue(entry.getValue().getAccountNumber());
			row.createCell(2).setCellValue(entry.getValue().getBusinessName());			
			row.createCell(3).setCellValue(new Double(entry.getValue().getKwh()));
			row.getCell(3).setCellStyle(num);			
		
			row.createCell(4).setCellValue(entry.getValue().getTerm());
			
			
			
			Cell cell5=row.createCell(5);
			if(null!=entry.getValue().getDealStartDate()){
				cell5.setCellValue(entry.getValue().getDealStartDate());
			}
			cell5.setCellStyle(dateStyle);
			
			row.createCell(6).setCellValue(new Double(entry.getValue().getRate()));
			row.createCell(7).setCellValue(entry.getValue().getContractType());
			
			row.createCell(8).setCellValue(new Double(entry.getValue().getCommission()));
			row.getCell(8).setCellStyle(currency);
			
			row.createCell(9).setCellValue(new Double(entry.getValue().getUpfrontCommission()));
			row.getCell(9).setCellStyle(currency);
			
			Cell cell9=row.createCell(10);
			if(null!=entry.getValue().getDealEndDate()){
				cell9.setCellValue(entry.getValue().getDealEndDate());
			}
			cell9.setCellStyle(dateStyle);		
		}
		
		
		
		HSSFRow footer=sheet.createRow((rowNum+3));
		Cell totCom=footer.createCell(0);
		totCom.setCellValue(renewalData.size());
		totCom.setCellStyle(num);
		
		Cell totUpfrontCom=footer.createCell(3);
		totUpfrontCom.setCellFormula("SUM(D3:D"+rowNum+")");
		totUpfrontCom.setCellStyle(num);
		
		Cell netCom=footer.createCell(9);
		netCom.setCellFormula("SUM(J3:J"+rowNum+")");
		netCom.setCellStyle(currency);
		
		Cell expCom=footer.createCell(8);
		expCom.setCellFormula("SUM(I3:I"+rowNum+")");
		expCom.setCellStyle(currency);	
		
		for(int i=0;i<=5;i++){
			sheet.autoSizeColumn(i);
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		
		
		 response.setHeader("Content-Disposition", "inline; filename=Renewal_Report_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"_"+cal.get(Calendar.YEAR)+".xls");
		  // Make sure to set the correct content type
		  response.setContentType("application/vnd.ms-excel");
		//workbook.write(response.getOutputStream());
		sheet.getWorkbook().write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	
	
}




	

