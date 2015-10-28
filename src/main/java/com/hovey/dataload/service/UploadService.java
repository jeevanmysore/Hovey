package com.hovey.dataload.service;

import java.util.ArrayList;

import com.hovey.backend.supplier.model.SupplierReports;

public interface UploadService {

	public void parsePipelineData(String fileName,String agentName)throws Exception;
	public ArrayList<SupplierReports> parseSupplierReports(String fileName)throws Exception;
	public void processReports(String fileName)throws Exception;
}
