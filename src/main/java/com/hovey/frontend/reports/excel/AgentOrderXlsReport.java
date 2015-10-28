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
 * it will b initiated on "pipelineExcel" return
 */

	
	
	
public class AgentOrderXlsReport extends AbstractExcelView{

	private static Logger log=Logger.getLogger(AgentOrderXlsReport.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("inside buildExcelDocument()");
		
		Map<String, OrdersDto> agentData=(Map<String,OrdersDto>) model.get("agents");
		HSSFSheet sheet = workbook.createSheet("Agent Order Report");
		
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
		header.createCell(4).setCellValue("Deal Start Date");			
		header.createCell(5).setCellValue("Rate");
		header.createCell(6).setCellValue("Contract Type");
		header.createCell(7).setCellValue("Commission");			
		header.createCell(8).setCellValue("Received Commission");
		header.createCell(9).setCellValue("Paid Date");	
		
		/*added by bhagya on april 16th,2014*/
		
		header.createCell(10).setCellValue("Order Status");
		header.createCell(11).setCellValue("SentToSupplier Date");
		
		
		
		header.setRowStyle(style);
		
		int rowNum = 2;
		//Iterator iterator = pipelineData.entrySet().iterator();
		
		HSSFCellStyle dateStyle=workbook.createCellStyle();
		CreationHelper createHelper=workbook.getCreationHelper();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
		// Setting Data for Cells.
		for(Map.Entry<String, OrdersDto> entry: agentData.entrySet()){
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
		
			Cell cell4=row.createCell(4);
			if(null!=entry.getValue().getDealStartDate()){
				cell4.setCellValue(entry.getValue().getDealStartDate());
			}
			cell4.setCellStyle(dateStyle);			
			row.createCell(5).setCellValue(new Double(entry.getValue().getRate()));
			row.createCell(6).setCellValue(entry.getValue().getContractType());
			row.createCell(7).setCellValue(new Double(entry.getValue().getCommission()));
			row.getCell(7).setCellStyle(currency);			
			row.createCell(8).setCellValue(new Double(entry.getValue().getUpfrontCommission()));
			row.getCell(8).setCellStyle(currency);			
			Cell cell8=row.createCell(9);
			if(null!=entry.getValue().getUpfrontPaidDate()){
				cell8.setCellValue(entry.getValue().getUpfrontPaidDate());
			}
			cell8.setCellStyle(dateStyle);	
			
			/*added by bhagya on april 16th,2014*/
			
			row.createCell(10).setCellValue(entry.getValue().getStatus());
			Cell cell10=row.createCell(11);
			if(null!=entry.getValue().getSentToSupplier()){
				cell10.setCellValue(entry.getValue().getSentToSupplier());
			}
			cell10.setCellStyle(dateStyle);	
			
		}
		
		HSSFRow footer=sheet.createRow((rowNum+3));
		Cell totCom=footer.createCell(0);
		totCom.setCellValue(agentData.size());
		totCom.setCellStyle(num);
		
		Cell totUpfrontCom=footer.createCell(3);
		totUpfrontCom.setCellFormula("SUM(D3:D"+rowNum+")");
		totUpfrontCom.setCellStyle(num);
		
		Cell netCom=footer.createCell(7);
		netCom.setCellFormula("SUM(I3:I"+rowNum+")");
		netCom.setCellStyle(currency);
		
		Cell expCom=footer.createCell(6);
		expCom.setCellFormula("SUM(H3:H"+rowNum+")");
		expCom.setCellStyle(currency);
		
		for(int i=0;i<=11;i++){
			sheet.autoSizeColumn(i);
		}		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		StringBuilder name=new StringBuilder();
		name.append(agentData.get("order0").getCreatedAgent().getFirstName());
		name.append(" ");
		name.append(agentData.get("order0").getCreatedAgent().getLastName());
		 response.setHeader("Content-Disposition", "inline; filename="+name.toString()+"_Report_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"_"+cal.get(Calendar.YEAR)+".xls");
		  // Make sure to set the correct content type
		  response.setContentType("application/vnd.ms-excel");
		//workbook.write(response.getOutputStream());
		sheet.getWorkbook().write(response.getOutputStream());
		response.getOutputStream().flush();
	}	
	
}




	

