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


import com.hovey.frontend.admin.dto.AgentOrderDto;


/*
 * Utility class to create Excel Files
 * it will b intiated on "pipelineExcel" return
 */

	
	
	
public class AgentXlsReport extends AbstractExcelView{

	private static Logger log=Logger.getLogger(AgentXlsReport.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("inside buildExcelDocument()");
		
		Map<String, AgentOrderDto> agentData=(Map<String,AgentOrderDto>) model.get("agents");
		HSSFSheet sheet = workbook.createSheet("Agent Report");
		
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
	
		header.createCell(0).setCellValue("Agent ID");
		header.createCell(1).setCellValue("Agent Name");
		header.createCell(2).setCellValue("Total Orders Sold");
		header.createCell(3).setCellValue("Total kWh Sold");
		header.createCell(4).setCellValue("Expected Overall Commission");			
		header.createCell(5).setCellValue("Commission Received");
		
		/*added by bhagya on april 14th,2014*/
		
		header.createCell(6).setCellValue("Order Status");
		header.createCell(7).setCellValue("SentToSupplier Date");
		header.setRowStyle(style);
		
		int rowNum = 2;
		//Iterator iterator = pipelineData.entrySet().iterator();
		
		HSSFCellStyle dateStyle=workbook.createCellStyle();
		CreationHelper createHelper=workbook.getCreationHelper();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
		// Setting Data for Cells.
		for(Map.Entry<String, AgentOrderDto> entry: agentData.entrySet()){
			HSSFRow row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue(entry.getValue().getAgentNumber().getAgentNumber());
			row.createCell(1).setCellValue(entry.getValue().getAgentName());
			row.createCell(2).setCellValue(entry.getValue().getTotalOrders());
			
			row.createCell(3).setCellValue(new Double(entry.getValue().getTotalKwh()));
			row.getCell(3).setCellStyle(num);			
		
			row.createCell(4).setCellValue(new Double(entry.getValue().getTotalExpectedCommission()));
			row.getCell(4).setCellStyle(currency);
			
			row.createCell(5).setCellValue(new Double(entry.getValue().getTotalReceivedCommission()));
			row.getCell(5).setCellStyle(currency);
			
			/*added by bhagya on april 14th,2014*/
			
			row.createCell(6).setCellValue(entry.getValue().getOrderStatus());
			Cell cell6=row.createCell(7);
			if(null!=entry.getValue().getSentToSupplier()){
				cell6.setCellValue(entry.getValue().getSentToSupplier());
			}
			cell6.setCellStyle(dateStyle);	
			
			
		}
		
		HSSFRow footer=sheet.createRow((rowNum+3));
		Cell totCom=footer.createCell(0);
		totCom.setCellValue(agentData.size());
		totCom.setCellStyle(num);
		
		Cell totUpfrontCom=footer.createCell(2);
		totUpfrontCom.setCellFormula("SUM(C3:C"+rowNum+")");
		totUpfrontCom.setCellStyle(num);
		
		Cell netCom=footer.createCell(3);
		netCom.setCellFormula("SUM(D3:D"+rowNum+")");
		netCom.setCellStyle(currency);
		
		Cell expCom=footer.createCell(4);
		expCom.setCellFormula("SUM(E3:E"+rowNum+")");
		expCom.setCellStyle(currency);
		
		
		Cell recCom=footer.createCell(5);
		recCom.setCellFormula("SUM(F3:F"+rowNum+")");
		recCom.setCellStyle(num);
		
		
		for(int i=0;i<=7;i++){
			sheet.autoSizeColumn(i);
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		
		 response.setHeader("Content-Disposition", "inline; filename="+"Agent_Report_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"_"+cal.get(Calendar.YEAR)+".xls");
		  // Make sure to set the correct content type
		  response.setContentType("application/vnd.ms-excel");
		//workbook.write(response.getOutputStream());
		sheet.getWorkbook().write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	
	
}




	

