package com.hovey.frontend.common.utility;

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
public class PipelineExportView extends AbstractExcelView{
	private static Logger log=Logger.getLogger(PipelineExportView.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("inside buildExcelDocument()");
		
		Map<String, OrdersDto> pipelineData=(Map<String, OrdersDto>) model.get("orders");
		HSSFSheet sheet = workbook.createSheet("Pipeline");
		
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
		header.createCell(0).setCellValue("Order Date");
		header.createCell(1).setCellValue("Supplier Name");
		header.createCell(2).setCellValue("Account Number");
		header.createCell(3).setCellValue("Business Name");
		header.createCell(4).setCellValue("DBA");
		header.createCell(5).setCellValue("Contract Type");
		header.createCell(6).setCellValue("KWH");
		header.createCell(7).setCellValue("Agent Name");
		header.createCell(8).setCellValue("Agent Number");
		header.createCell(9).setCellValue("Status");
		header.createCell(10).setCellValue("Upfront Commission Expected");
		
	//	header.createCell(11).setCellValue("Annual Commission Expected");
		
		header.createCell(11).setCellValue("Sent To Supplier");
		header.createCell(12).setCellValue("Deal Start Date");
	//	header.createCell(14).setCellValue("Annual Year");
		
		header.createCell(13).setCellValue("Upfront Commission1");
		header.createCell(14).setCellValue("Upfront Paid Date");
		header.createCell(15).setCellValue("Upfront Commission2");
		header.createCell(16).setCellValue("Upfront Paid Date2");
		header.createCell(17).setCellValue("Upfront Commission3");
	
		header.createCell(18).setCellValue("Upfront Paid Date3");
		header.createCell(19).setCellValue("Term");
		header.createCell(20).setCellValue(" Rate Sold");
		header.createCell(21).setCellValue("Commission Rate");		
		header.createCell(22).setCellValue("fax Received");
		header.createCell(23).setCellValue("Note");
		header.createCell(24).setCellValue("Phone Number");
		header.createCell(25).setCellValue("Contact Name");
		header.createCell(26).setCellValue("Utility");
		
		/*added by bhagya on april 16th,2014*/
		header.createCell(27).setCellValue("Rescinded Order Agent");
		header.createCell(28).setCellValue("Fronter");
		header.createCell(29).setCellValue("State");
		
		
		header.setRowStyle(style);		
		int rowNum = 2;
		//Iterator iterator = pipelineData.entrySet().iterator();
		HSSFCellStyle dateStyle=workbook.createCellStyle();
		CreationHelper createHelper=workbook.getCreationHelper();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
		// Setting Data for Cells.
		for(Map.Entry<String, OrdersDto> entry: pipelineData.entrySet()){
			HSSFRow row = sheet.createRow(rowNum++);
			Cell cell=row.createCell(0);
			cell.setCellValue(entry.getValue().getOrderDate());
			cell.setCellStyle(dateStyle);			
			row.createCell(1).setCellValue(entry.getValue().getSupplierName().getSupplierName());
			row.createCell(2).setCellValue(entry.getValue().getAccountNumber());
			row.createCell(3).setCellValue(entry.getValue().getBusinessName());
			row.createCell(4).setCellValue(entry.getValue().getDba());
			row.createCell(5).setCellValue(entry.getValue().getContractType());
			row.createCell(6).setCellValue(entry.getValue().getKwh());
			StringBuilder name=new StringBuilder();
			name.append(entry.getValue().getCreatedAgent().getFirstName());
			name.append(" ");
			name.append(entry.getValue().getCreatedAgent().getLastName());
			row.createCell(7).setCellValue(name.toString());
			row.createCell(8).setCellValue(entry.getValue().getCreatedAgent().getAgentNumber());
			row.createCell(9).setCellValue(entry.getValue().getStatus());
			row.createCell(10).setCellValue(new Double(entry.getValue().getCommission()));		
			row.getCell(10).setCellStyle(currency);
			//row.createCell(11).setCellValue(new Double(entry.getValue().getTermCommission()));			
			Cell cell2=row.createCell(11);
			if(null!=entry.getValue().getSentToSupplier()){
				cell2.setCellValue(entry.getValue().getSentToSupplier());
			}
			cell2.setCellStyle(dateStyle);
			Cell cell3=row.createCell(12);
			if(null!=entry.getValue().getDealStartDate()){
				cell3.setCellValue(entry.getValue().getDealStartDate());
			}			
			cell3.setCellStyle(dateStyle);
			//row.createCell(14).setCellValue(new Double(entry.getValue().getTermCommission()));		
			
			row.createCell(13).setCellValue(new Double(entry.getValue().getUpfrontCommission()));
			row.getCell(13).setCellStyle(currency);
			Cell cell4=row.createCell(14);
			if(null!=entry.getValue().getUpfrontPaidDate()){
				cell4.setCellValue(entry.getValue().getUpfrontPaidDate());
			}
			/*added upfrontcommission and paiddate 2,3 by bhagya on may28th,2014*/
			row.createCell(15).setCellValue(new Double(entry.getValue().getUpfrontCommission2()));
			row.getCell(15).setCellStyle(currency);
			cell4.setCellStyle(dateStyle);
			Cell cell5=row.createCell(16);
			if(null!=entry.getValue().getUpfrontPaidDate2()){
				cell5.setCellValue(entry.getValue().getUpfrontPaidDate2());
			}			
			row.createCell(17).setCellValue(new Double(entry.getValue().getUpfrontCommission3()));
			row.getCell(17).setCellStyle(currency);		
			cell5.setCellStyle(dateStyle);
			Cell cell6=row.createCell(18);
			if(null!=entry.getValue().getUpfrontPaidDate3()){
				cell6.setCellValue(entry.getValue().getUpfrontPaidDate3());
			}
			cell6.setCellStyle(dateStyle);
			row.createCell(19).setCellValue(entry.getValue().getTerm());		
			
			row.createCell(20).setCellValue(new Double(entry.getValue().getRate()));	
			Double comRate=entry.getValue().getCommissionRate();
			if(null!=comRate)
			row.createCell(21).setCellValue(comRate);	
			
			
			
			if(entry.getValue().isFaxReceived()){
				row.createCell(22).setCellValue("yes");
			}
			else{
				row.createCell(22).setCellValue("no");
			}			
			row.createCell(23).setCellValue(entry.getValue().getNotes());
			row.createCell(24).setCellValue(entry.getValue().getTaxId().getPhoneNo());
			row.createCell(25).setCellValue(entry.getValue().getTaxId().getFirstName()+" "+entry.getValue().getTaxId().getLastName());
			row.createCell(26).setCellValue(entry.getValue().getUtility().getUtility());
			
			/*added by bhagya on april 16th,2014*/
			
			if(null!=entry.getValue().getResAgent()){
				StringBuilder resName=new StringBuilder();
				resName.append(entry.getValue().getResAgent().getFirstName());
				resName.append(" ");
				resName.append(entry.getValue().getResAgent().getLastName());
			row.createCell(27).setCellValue(resName.toString());
			}	
			row.createCell(28).setCellValue(entry.getValue().getTaxId().getFronter());
			
			//added on June 04, 2014
			row.createCell(29).setCellValue(entry.getValue().getServiceState().getState());
		}		
		for(int i=0;i<=30;i++){
			sheet.autoSizeColumn(i);
		}
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		
		 response.setHeader("Content-Disposition", "inline; filename="+"Pipeline_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE)+"_"+cal.get(Calendar.YEAR)+".xls");
		  // Make sure to set the correct content type
		  response.setContentType("application/vnd.ms-excel");
		//workbook.write(response.getOutputStream());
		sheet.getWorkbook().write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	
	
	
	
	
}
