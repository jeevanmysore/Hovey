package com.hovey.frontend.supplier.dto;

import com.hovey.backend.supplier.model.SupplierFiles;

/* 
 * 
 * DTO for Supplier Files..
 * 
 * */

public class SupplierFilesDto {

	private Integer id;
	private String fileName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static SupplierFilesDto populateSupplierFiles(SupplierFiles file){
		SupplierFilesDto fileDto=new SupplierFilesDto();
		fileDto.setId(file.getId());
		fileDto.setFileName(file.getFileName());
		return fileDto;
	}

}
