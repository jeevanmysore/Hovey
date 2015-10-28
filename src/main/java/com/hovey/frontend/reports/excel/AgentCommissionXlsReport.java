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


import com.hovey.frontend.agent.dto.AgentCommissionsDto;



/*
 * Utility class to create Excel Files
 * it will b intiated on "pipelineExcel" return
 */

	
	
	
public class AgentCommissionXlsReport extends AbstractExcelView{

	private static Logger log=Logger.getLogger(AgentCommissionXlsReport.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("inside buildExcelDocument()");
		
		Map<String, AgentCommissionsDto> agentData=(Map<String,AgentCommissionsDto>) model.get("agents");
		Map<String, AgentCommissionsDto> resData=(Map<String,AgentCommissionsDto>) model.get("rescissions");
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
	
		header.createCell(0).setCellValue("S.No");
		header.createCell(1).setCellValue("Agent");
		header.createCell(2).setCellValue("Sent to Supplier Date");
		header.createCell(3).setCellValue("Account#");
		header.createCell(4).setCellValue("Business Name");
		header.createCell(5).setCellValue(" kWh ");		
		header.createCell(6).setCellValue("Upfront Commission");
		header.createCell(7).setCellValue("Upfront Paid Date");
		header.createCell(8).setCellValue("Status");
		header.createCell(9).setCellValue("Contract Type");
		header.createCell(10).setCellValue("Eligible for Commission");
		header.createCell(11).setCellValue("Commission Rate");
		header.createCell(12).setCellValue("Commission Payable");
		header.createCell(13).setCellValue("Supplier");			
		
		header.setRowStyle(style);		
		int rowNum = 2;
		//Iterator iterator = pipelineData.entrySet().iterator();		
		HSSFCellStyle dateStyle=workbook.createCellStyle();
		CreationHelper createHelper=workbook.getCreationHelper();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
		int i=1;
		// Setting Data for Cells.
		for(Map.Entry<String, AgentCommissionsDto> entry: agentData.entrySet()){
			HSSFRow row = sheet.createRow(rowNum++);			
			row.createCell(0).setCellValue(i);
			row.createCell(1).setCellValue(entry.getValue().getOrder().getCreatedAgent().getFirstName()+" "+entry.getValue().getOrder().getCreatedAgent().getLastName());
			Cell cell2=row.createCell(2);
			if(null!=entry.getValue().getOrder().getSentToSupplier()){
				cell2.setCellValue(entry.getValue().getOrder().getSentToSupplier());
			}
			cell2.setCellStyle(dateStyle);
			row.createCell(3).setCellValue(entry.getValue().getOrder().getAccountNumber());
			row.createCell(4).setCellValue(entry.getValue().getOrder().getBusinessName());			
			row.createCell(5).setCellValue(new Double(entry.getValue().getOrder().getKwh()));
			row.getCell(5).setCellStyle(num);	
			row.createCell(6).setCellValue(new Double(entry.getValue().getOrder().getUpfrontCommission()));			
			row.getCell(6).setCellStyle(currency);
			Cell cell7=row.createCell(7);
			if(null!=entry.getValue().getOrder().getUpfrontPaidDate()){
				cell7.setCellValue(entry.getValue().getOrder().getUpfrontPaidDate());
			}
			cell7.setCellStyle(dateStyle);			
			row.createCell(8).setCellValue(entry.getValue().getOrder().getStatus());
			row.createCell(9).setCellValue(entry.getValue().getOrder().getContractType());
			if(entry.getValue().isCommissionPayable()){
				row.createCell(10).setCellValue("Yes");
			}
			else{
				row.createCell(10).setCellValue("No");
			}	
			if(null!=entry.getValue().getCommissionRate())
				row.createCell(11).setCellValue(new Double(entry.getValue().getCommissionRate()));
			if(null!=entry.getValue().getAgentCommission()){
				row.createCell(12).setCellValue(new Double(entry.getValue().getAgentCommission()));
				row.getCell(12).setCellStyle(currency);
			}	
			row.createCell(13).setCellValue(entry.getValue().getSupplier().getSupplierName());			
			i++;
		}		
		rowNum+=5;
		HSSFRow row1 = sheet.createRow(rowNum++);	
		row1.createCell(4).setCellValue("COMMISSIONS OF RESCINDED ORDERS");
		row1.getCell(4).setCellStyle(style);
		if(null!=resData && !resData.isEmpty()){
			for(Map.Entry<String, AgentCommissionsDto> entry: resData.entrySet()){
				HSSFRow row = sheet.createRow(rowNum++);			
				row.createCell(0).setCellValue(i);
				row.createCell(1).setCellValue(entry.getValue().getOrder().getCreatedAgent().getFirstName()+" "+entry.getValue().getOrder().getCreatedAgent().getLastName());
				Cell cell2=row.createCell(2);
				if(null!=entry.getValue().getOrder().getSentToSupplier()){
					cell2.setCellValue(entry.getValue().getOrder().getSentToSupplier());
				}
				cell2.setCellStyle(dateStyle);
				row.createCell(3).setCellValue(entry.getValue().getOrder().getAccountNumber());
				row.createCell(4).setCellValue(entry.getValue().getOrder().getBusinessName());			
				row.createCell(5).setCellValue(new Double(entry.getValue().getOrder().getKwh()));
				row.getCell(5).setCellStyle(num);	
				row.createCell(6).setCellValue(new Double(entry.getValue().getOrder().getUpfrontCommission()));			
				row.getCell(6).setCellStyle(currency);
				Cell cell7=row.createCell(7);
				if(null!=entry.getValue().getOrder().getUpfrontPaidDate()){
					cell7.setCellValue(entry.getValue().getOrder().getUpfrontPaidDate());
				}
				cell7.setCellStyle(dateStyle);				
				row.createCell(8).setCellValue(entry.getValue().getOrder().getStatus());
				/*if(entry.getValue().isCommissionPayable()){
					row.createCell(9).setCellValue("Yes");
				}
				else{
					row.createCell(9).setCellValue("No");
				}	*/
				if(null!=entry.getValue().getCommissionRate())
					row.createCell(10).setCellValue(new Double(entry.getValue().getCommissionRate()));
				if(null!=entry.getValue().getAgentCommission()){
					row.createCell(11).setCellValue(new Double(entry.getValue().getAgentCommission()));
					row.getCell(11).setCellStyle(currency);
				}	
				row.createCell(12).setCellValue(entry.getValue().getSupplier().getSupplierName());				
				i++;
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		HSSFRow footer=sheet.createRow((rowNum+3));
		Cell totCom=footer.createCell(0);
		totCom.setCellValue(agentData.size());
		totCom.setCellStyle(num);
		
		
		
		/*Cell netCom=footer.createCell(11);
		netCom.setCellFormula("SUM(L3:L"+rowNum+")");
		netCom.setCellStyle(currency);*/
		
		
		
		for(int j=0;j<=13;j++){
			sheet.autoSizeColumn(j);
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		
		
		 response.setHeader("Content-Disposition", "inline; filename=Agent_Commission_Report_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"_"+cal.get(Calendar.YEAR)+".xls");
		  // Make sure to set the correct content type
		  response.setContentType("application/vnd.ms-excel");
		//workbook.write(response.getOutputStream());
		sheet.getWorkbook().write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	
	
}




	

