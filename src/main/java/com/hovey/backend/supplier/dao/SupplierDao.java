package com.hovey.backend.supplier.dao;

import java.util.ArrayList;
import java.util.Date;

import com.hovey.backend.supplier.exception.SupplierFileNotFoundException;
import com.hovey.backend.supplier.exception.SupplierMappingNotFoundException;
import com.hovey.backend.supplier.exception.SupplierNotFoundException;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.supplier.model.Supplier;
import com.hovey.backend.supplier.model.SupplierFiles;
import com.hovey.backend.supplier.model.SupplierMapping;
import com.hovey.backend.supplier.model.SupplierReports;

public interface SupplierDao {
	public Integer saveOrUpdateSupplierToDB(Supplier supplier)throws Exception;
	public Supplier getSupplierByName(String supplierName) throws Exception;
	public Supplier getSupplierByID(Integer supplierId) throws Exception;
	public ArrayList<Supplier> getSuppliers()throws SupplierNotFoundException;
	public void deleteSupplier(Supplier supplier) throws Exception;
	public Integer saveorUpdateSupplierMappings(SupplierMapping mapping)throws Exception;
	public SupplierMapping getSupplierMappingBySupplier(String supplierName)throws Exception;
	public SupplierMapping getSupplierMappingById(Integer id)throws Exception;
	public ArrayList<SupplierMapping> getSupplierMappings()throws SupplierMappingNotFoundException;
	public void deleteSupplierMapping(SupplierMapping mapping)throws Exception;
	public Integer saveorUpdateSupplierReports(ArrayList<SupplierReports> reports)throws Exception;
	public ArrayList<SupplierReports> getSupplierReports()throws SupplierReportsNotFoundException;
	public ArrayList<SupplierReports> getSupplierReportsofaSupplier(String supplierName)throws Exception;
	public SupplierReports getSupplierReportById(Integer id)throws Exception;
	public SupplierFiles saveSupplierFile(SupplierFiles file)throws Exception;
	public Integer deleteSupplierFile(SupplierFiles file)throws Exception;
	public SupplierFiles getSupplierFilesByFileName(String fileName)throws SupplierFileNotFoundException;
	public SupplierFiles getSupplierFilesById(Integer id)throws SupplierFileNotFoundException;
	 public ArrayList<SupplierFiles> getSupplierFiles()throws SupplierFileNotFoundException;
	 public Integer saveorUpdateSupplierReports(SupplierReports reports)throws Exception;
	 public ArrayList<SupplierReports> getSupplierReportsBetweenDays(Date startDate,Date endDate)throws SupplierReportsNotFoundException;
	 public ArrayList<SupplierReports> getSupplierReportsBetweenDays(Date startDate,Date endDate,String supplier)throws SupplierReportsNotFoundException;
	 public ArrayList<SupplierReports> getSupplierReportsByBusinessNameBetweenDays(Date startDate,Date endDate,String businessName)throws SupplierReportsNotFoundException;
	 public ArrayList<SupplierReports> getSupplierReportsNotinPipeline(Date startDate,Date endDate,String supplier)throws SupplierReportsNotFoundException;
	 public ArrayList<SupplierReports> getSupplierReportsByIds(ArrayList<Integer> ids) throws SupplierReportsNotFoundException;
	 public ArrayList<SupplierReports> getSupplierReportsOfAgent(String agentNumber)throws SupplierReportsNotFoundException;
	 public ArrayList<SupplierReports> getSupplierReportsBySupplierFile(Integer fileId) throws SupplierReportsNotFoundException;
	 public Integer deleteSupplierReports(ArrayList<SupplierReports> reports) throws Exception;

}
