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


import com.hovey.frontend.supplier.dto.SupplierReportsDto;

/*
 * Utility class to create Excel Files
 * it will b intiated on "pipelineExcel" return
 */

	
	
	
public class SupplierXlsReport extends AbstractExcelView{

	private static Logger log=Logger.getLogger(SupplierXlsReport.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("inside buildExcelDocument()");
		
		Map<String, SupplierReportsDto> supplierData=(Map<String,SupplierReportsDto>) model.get("suppliers");
		HSSFSheet sheet = workbook.createSheet("Supplier Report");
		
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
	
		header.createCell(0).setCellValue("Supplier");
		header.createCell(1).setCellValue("Business Name");
		header.createCell(2).setCellValue("Account#");
		header.createCell(3).setCellValue("Start Date");
		header.createCell(4).setCellValue("Term");			
		header.createCell(5).setCellValue("kWh");
		header.createCell(6).setCellValue("Rate");			
		header.createCell(7).setCellValue("Paid Date");
		header.createCell(8).setCellValue("Commission Paid");			
		
		header.setRowStyle(style);
		
		int rowNum = 2;
		//Iterator iterator = pipelineData.entrySet().iterator();
		
		HSSFCellStyle dateStyle=workbook.createCellStyle();
		CreationHelper createHelper=workbook.getCreationHelper();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
		// Setting Data for Cells.
		for(Map.Entry<String, SupplierReportsDto> entry: supplierData.entrySet()){
			HSSFRow row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue(entry.getValue().getSupplierName().getSupplierName());
			row.createCell(2).setCellValue(entry.getValue().getAccountNumber());
			row.createCell(1).setCellValue(entry.getValue().getCustomerName());
			Cell cell2=row.createCell(3);
			if(null!=entry.getValue().getContractStartDate()){
				cell2.setCellValue(entry.getValue().getContractStartDate());
			}			
			cell2.setCellStyle(dateStyle);
			if(null!=entry.getValue().getTerm()){
				row.createCell(4).setCellValue((entry.getValue().getTerm()));	
			}
			row.createCell(5).setCellValue(new Double(entry.getValue().getKwh()));
			
			row.getCell(5).setCellStyle(num);
			
		
			row.createCell(6).setCellValue(new Double(entry.getValue().getRate()));
			
			Cell cell3=row.createCell(7);
			if(null!=entry.getValue().getDatePaid()){
				cell3.setCellValue(entry.getValue().getDatePaid());
			}	
			cell3.setCellStyle(dateStyle);
			
			row.createCell(8).setCellValue(new Double(entry.getValue().getTotalCommissionPaid()));
			row.getCell(8).setCellStyle(currency);
			
		}
		
		HSSFRow footer=sheet.createRow((rowNum+3));
		Cell totCom=footer.createCell(0);
		totCom.setCellValue(supplierData.size());
		totCom.setCellStyle(num);
		
		Cell totUpfrontCom=footer.createCell(5);
		totUpfrontCom.setCellFormula("SUM(F3:F"+rowNum+")");
		totUpfrontCom.setCellStyle(num);
		
		Cell netCom=footer.createCell(8);
		netCom.setCellFormula("SUM(I3:I"+rowNum+")");
		
		
		netCom.setCellStyle(currency);
		
		
		for(int i=0;i<=5;i++){
			sheet.autoSizeColumn(i);
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		
		 response.setHeader("Content-Disposition", "inline; filename="+"Supplier_Report_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"_"+cal.get(Calendar.YEAR)+".xls");
		  // Make sure to set the correct content type
		  response.setContentType("application/vnd.ms-excel");
		//workbook.write(response.getOutputStream());
		sheet.getWorkbook().write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	
	
}




	

