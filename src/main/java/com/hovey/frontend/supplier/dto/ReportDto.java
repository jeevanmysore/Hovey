package com.hovey.frontend.supplier.dto;
import org.springframework.web.multipart.MultipartFile;


//Dto to bind to form, no other purpose..

public class ReportDto {
	
	private String supplierName;
	private MultipartFile file;
	private String fileName;
	
	
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
