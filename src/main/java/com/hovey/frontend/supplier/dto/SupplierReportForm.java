package com.hovey.frontend.supplier.dto;

import org.springframework.util.AutoPopulatingList;

/**
 * 
 * @author JEEVAN
 * 
 * 
 * Form Binding Class to Handle Multiple File Uploads at a Stretch
 *
 */
public class SupplierReportForm {

	private AutoPopulatingList<ReportDto> reports=new AutoPopulatingList<ReportDto>(ReportDto.class);

	
	public AutoPopulatingList<ReportDto> getReports() {
		return reports;
	}

	public void setReports(AutoPopulatingList<ReportDto> reports) {
		this.reports = reports;
	}
	
	
}
