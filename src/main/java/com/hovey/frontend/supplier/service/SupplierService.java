package com.hovey.frontend.supplier.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.supplier.exception.SupplierFileNotFoundException;
import com.hovey.backend.supplier.exception.SupplierMappingNotFoundException;
import com.hovey.backend.supplier.exception.SupplierNotFoundException;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.supplier.model.SupplierFiles;
import com.hovey.frontend.supplier.dto.ReportDto;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.supplier.dto.SupplierFilesDto;
import com.hovey.frontend.supplier.dto.SupplierMappingDto;
import com.hovey.frontend.supplier.dto.SupplierReportForm;
import com.hovey.frontend.supplier.dto.SupplierReportsDto;

public interface SupplierService {

	public Integer saveorUpdateSuppliertoDAO(SupplierDto supplierDto)throws Exception;
	public SupplierDto getSupplierBySupplierName(String supplierName)throws Exception;
	public SupplierDto getSupplierBySupplierId(Integer supplierId)throws Exception;
	public ArrayList<SupplierDto> getSuppliersFromDAO()throws SupplierNotFoundException;
	public Integer saveorUpdateSupplierMappings(SupplierMappingDto mappingDto)throws Exception;
	public ArrayList<SupplierMappingDto> getAllMappings()throws SupplierMappingNotFoundException;
	public SupplierMappingDto getSupplierMappingBySupplierName(String supplierName)throws Exception;
	public Map<String, Object> getSupplierReportsData(ReportDto report)throws Exception;
	 public Map<String,Object> saveSupplierReports(SupplierReportForm reportForm)throws Exception;
	public SupplierFiles getSupplierFileByName(String fileName) throws SupplierFileNotFoundException;
	public ArrayList<SupplierFilesDto> getSupplierFiles()throws SupplierFileNotFoundException;
	public ArrayList<SupplierReportsDto> getSupplierReportsByTerm(String term)throws SupplierReportsNotFoundException;
	//public ArrayList<SupplierReportsDto> getOrdersNotinPipeline();
	public ArrayList<SupplierReportsDto> getSupplierReportsFromDao() throws SupplierReportsNotFoundException;
	public ArrayList<SupplierReportsDto> getSupplierReportsBetweenDaysFromDao(Date startDate,Date endDate,String supplier)throws SupplierReportsNotFoundException;
	public ArrayList<SupplierReportsDto> getSupplierReportsOfAccountBetweenDaysFromDao(Date startDate,Date endDate,String businessName)throws SupplierReportsNotFoundException;
	public ArrayList<SupplierReportsDto> getSupplierReportsNotinPipeline(Date startDate,Date endDate,String supplier)throws SupplierReportsNotFoundException;
	
	public Map<String,Object> processUploadingLeftOverSupplierReportUpdationToPipeline();
	public Integer updateSupplierReportManually(ArrayList<Integer> id) throws Exception;
	public String processRemovingSupplierReportsFromPipeline(Integer fileId) throws Exception;
	
	//For handling upfrontCommissions
	public Orders handleUpfrontCommissions(Orders order,Double reportTotalCommission,Date reportPaidDate,Boolean reportRemove) throws SupplierReportsNotFoundException;
}
