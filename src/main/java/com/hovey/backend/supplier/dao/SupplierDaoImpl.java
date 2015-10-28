package com.hovey.backend.supplier.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hovey.backend.supplier.exception.SupplierFileAlreadyExistsException;
import com.hovey.backend.supplier.exception.SupplierFileNotFoundException;
import com.hovey.backend.supplier.exception.SupplierMappingNotFoundException;
import com.hovey.backend.supplier.exception.SupplierNotFoundException;
import com.hovey.backend.supplier.exception.SupplierReportsNotFoundException;
import com.hovey.backend.supplier.model.Supplier;
import com.hovey.backend.supplier.model.SupplierFiles;
import com.hovey.backend.supplier.model.SupplierMapping;
import com.hovey.backend.supplier.model.SupplierReports;


/**
 * 
 * @author JEEVAN
 * 
 * 
 * DAO for Supplier Related activities.
 *
 */


@Repository("supplierDao")
@Transactional
public class SupplierDaoImpl implements SupplierDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private static Logger log=Logger.getLogger(SupplierDaoImpl.class);
	
	
	// Saves/Updates supplier .
	public Integer saveOrUpdateSupplierToDB(Supplier supplier)throws Exception{
		log.info("inside saveOrUpdateSupplierToDB()");
		Assert.notNull(supplier);
		hibernateTemplate.saveOrUpdate(supplier);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return supplier.getId();
	}
	
	
	//get Supplier by Supplier Name.
	
	@SuppressWarnings("unchecked")
	public Supplier getSupplierByName(String supplierName) throws Exception{
		log.info("inside getSupplierByName");
		Assert.notNull(supplierName);
		ArrayList<Supplier> suppliers=(ArrayList<Supplier>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Supplier.class)
				.add(Restrictions.eq("supplierName", supplierName)).list();
		if(!suppliers.isEmpty()){
			return suppliers.get(0);
		}
		else{
			throw new SupplierNotFoundException();
		}
	}
	
	
//get Supplier by SupplierID
	@SuppressWarnings("unchecked")
	public Supplier getSupplierByID(Integer supplierId) throws Exception{
		log.info("inside getSupplierByName");
		Assert.notNull(supplierId);
		ArrayList<Supplier> suppliers=(ArrayList<Supplier>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Supplier.class)
				.add(Restrictions.eq("id", supplierId)).list();
		if(!suppliers.isEmpty()){
			return suppliers.get(0);
		}
		else{
			throw new SupplierNotFoundException();
		}
	}
	
	
	//view all Suppliers..
	@SuppressWarnings("unchecked")
	public ArrayList<Supplier> getSuppliers()throws SupplierNotFoundException{
		log.info("inside getSuppliers()");
		ArrayList<Supplier> suppliers=(ArrayList<Supplier>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Supplier.class).list();
		if(!suppliers.isEmpty()){
			return suppliers;
		}
		else{
			throw new SupplierNotFoundException();
		}
	}
	
	//for Deleting the Supplier	
	public void deleteSupplier(Supplier supplier) throws Exception{
		log.info("inside deleteSupplier()");
		hibernateTemplate.delete(supplier);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	}
	
	

	
/*
 * 		Supplier Mappings
 * 
 *  */	
	
	//saves or update supplier Mapping.
	public Integer saveorUpdateSupplierMappings(SupplierMapping mapping)throws Exception{
		log.info("inside saveorupdateSupplierMappings");
		Assert.notNull(mapping);
		hibernateTemplate.saveOrUpdate(mapping);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return mapping.getId();
	}
	
	//getting SupplierMapping by SupplierName
	@SuppressWarnings("unchecked")
	public SupplierMapping getSupplierMappingBySupplier(String supplierName)throws Exception{
		log.info("inside getSupplierMappingBySupplierName()");
		ArrayList<SupplierMapping> mappings=(ArrayList<SupplierMapping>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierMapping.class)
				.add(Restrictions.eq("supplierName.supplierName", supplierName)).list();
		if(!mappings.isEmpty()){
			return mappings.get(0);
		}
		else{
			throw new SupplierMappingNotFoundException();
		}		
	}
	
	//getting SupplierMapping by id
		@SuppressWarnings("unchecked")
		public SupplierMapping getSupplierMappingById(Integer id)throws Exception{
			log.info("inside getSupplierMappingBySupplierName()");
			ArrayList<SupplierMapping> mappings=(ArrayList<SupplierMapping>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierMapping.class)
					.add(Restrictions.eq("id", id)).list();
			if(!mappings.isEmpty()){
				return mappings.get(0);
			}
			else{
				throw new SupplierMappingNotFoundException();
			}		
		}
		
	
		//view all Supplier Mappings..
		@SuppressWarnings("unchecked")
		public ArrayList<SupplierMapping> getSupplierMappings()throws SupplierMappingNotFoundException{			
			log.info("inside getSuppliers()");
			ArrayList<SupplierMapping> mappings=(ArrayList<SupplierMapping>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierMapping.class).list();
			if(!mappings.isEmpty()){
				return mappings;
			}
			else{
				throw new SupplierMappingNotFoundException();
			}
		}
			
		//deletes supplierMappings.
	  public void deleteSupplierMapping(SupplierMapping mapping)throws Exception{
		  log.info("inside deleteSupplierMapping");
		  hibernateTemplate.delete(mapping);
		  hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	 }
	
	
	  /* 
	   *  	Supplier Reports......
	   * 
	   * */
	  
	  //save or Update Supplier Reports(BULK)
	  public Integer saveorUpdateSupplierReports(ArrayList<SupplierReports> reports)throws Exception{
		  log.info("inside saveSupplierReports()");
		  Assert.notNull(reports);
		  hibernateTemplate.saveOrUpdateAll(reports);
		  hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		  return reports.get(0).getId();
	  }
	  
	//save or Update Supplier Reports(BULK)
	  public Integer saveorUpdateSupplierReports(SupplierReports report)throws Exception{
		  log.info("inside saveSupplierReports()");		 
		  hibernateTemplate.saveOrUpdate(report);
		//  hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		  return report.getId();
	  }
	  
	  
	  
	 // gwt all supplier reports..
	  @SuppressWarnings("unchecked")
	public ArrayList<SupplierReports> getSupplierReports()throws SupplierReportsNotFoundException{
		  log.info("inside getSupplierReports()");
		  ArrayList<SupplierReports> reports=(ArrayList<SupplierReports>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class).list();
		  if(!reports.isEmpty()){
			  return reports;
		  }
		  else{
			  throw new SupplierReportsNotFoundException();
		  }
	  }
	  
	  
	  
	  //get supplier reports of a supplier
	 @SuppressWarnings("unchecked")
	public ArrayList<SupplierReports> getSupplierReportsofaSupplier(String supplierName)throws Exception{
		 log.info("inside getSupplierReportsOfaSupplier()");
		 ArrayList<SupplierReports> reports=(ArrayList<SupplierReports>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class)
				 .add(Restrictions.eq("supplierName.supplierName", supplierName)).list();
		 
		if(!reports.isEmpty()){
			return reports;
		}
		else{
			throw new SupplierReportsNotFoundException();
		}
	 }
	 
	 //get supplier report by Id
	@SuppressWarnings("unchecked")
	public SupplierReports getSupplierReportById(Integer id)throws Exception{
		log.info("inside getSupplierReportById()");
		ArrayList<SupplierReports> reports=(ArrayList<SupplierReports>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class)
				.add(Restrictions.eq("id",id)).list();
		if(!reports.isEmpty()){
			return reports.get(0);
		}
		else{
			throw new SupplierReportsNotFoundException();
		}
		
	}
	
	
	
	//save supplier file..
	public SupplierFiles saveSupplierFile(SupplierFiles file)throws SupplierFileAlreadyExistsException{
		log.info("inside save SupplierFiles()");
		Assert.notNull(file);
		hibernateTemplate.save(file);
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		return file;
	}
	
	//delete supplier file..
		public Integer deleteSupplierFile(SupplierFiles file)throws Exception{
			log.info("inside save SupplierFiles()");
			Assert.notNull(file);
			hibernateTemplate.delete(file);
			hibernateTemplate.getSessionFactory().getCurrentSession().flush();
			return file.getId();
		}
		
		
		
	  
	
		
     //getting SupplierFiles by Supplier File Name
		@SuppressWarnings("unchecked")
		public SupplierFiles getSupplierFilesByFileName(String fileName)throws SupplierFileNotFoundException{
			log.info("inside getSupplierFilesByFileName( )");
			ArrayList<SupplierFiles> files=(ArrayList<SupplierFiles>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierFiles.class)
					.add(Restrictions.eq("fileName", fileName)).list();
			
			if(!files.isEmpty()){
				return files.get(0);
			}
			else{
				throw new SupplierFileNotFoundException();
			}
			
		}
		
		
		
		
		 //getting SupplierFiles by Supplier File Name
		@SuppressWarnings("unchecked")
		public SupplierFiles getSupplierFilesById(Integer id)throws SupplierFileNotFoundException{
			log.info("inside getSupplierFilesByFileName( )");
			ArrayList<SupplierFiles> files=(ArrayList<SupplierFiles>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierFiles.class)
					.add(Restrictions.eq("id", id)).list();
			
			if(!files.isEmpty()){
				return files.get(0);
			}
			else{
				throw new SupplierFileNotFoundException();
			}
			
		}
		
		//gets all Supplier Files
	  @SuppressWarnings("unchecked")
	 public ArrayList<SupplierFiles> getSupplierFiles()throws SupplierFileNotFoundException{
		  log.info("inside getSupplierFiles()");
		  ArrayList<SupplierFiles> files=(ArrayList<SupplierFiles>) hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierFiles.class)
				  .addOrder(Order.desc("id"))
				  .list();
		  if(!files.isEmpty()){
			  return files;
		  }
		  else{
			  throw new SupplierFileNotFoundException();
		  }
	  }
	
	  
	 /*
	  * added by Jeevan on August 09,2013.. to get all the Orders between Datess..
	  */
	  
	  @SuppressWarnings("unchecked")
	 public ArrayList<SupplierReports> getSupplierReportsBetweenDays(Date startDate,Date endDate)throws SupplierReportsNotFoundException{
		  log.info("inside getSupplierReport()");
		  ArrayList<SupplierReports> reports=null;
		  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class);
		  if(null==startDate && null==endDate){
			  //dont add any restrictions..........
		  }
		  else if(null==startDate){
			  criteria.add(Restrictions.lt("upfrontPaidDate", endDate));
		  }
		  if(null==endDate){
			  criteria.add(Restrictions.ge("upfrontPaidDate", startDate));
		  }
		  else{
			  criteria.add(Restrictions.between("upfrontPaidDate", startDate, endDate));
		  }
		  criteria.addOrder(Order.desc("upfrontPaidDate"));
		  reports=(ArrayList<SupplierReports>) criteria.list();
		  if(!reports.isEmpty()){
			  return reports;
		  }
		  else{
			  throw new SupplierReportsNotFoundException();
		  }
	  }
	  
	  @SuppressWarnings("unchecked")
		 public ArrayList<SupplierReports> getSupplierReportsBetweenDays(Date startDate,Date endDate,String supplier)throws SupplierReportsNotFoundException{
			  log.info("inside getSupplierReport()");
			  ArrayList<SupplierReports> reports=null;
			  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class);
			  System.out.println(startDate);
			  System.out.println(endDate+"ddd" );
			  if(null!=startDate && null!=endDate){				  
				  Calendar cal=Calendar.getInstance();
					cal.setTime(endDate); cal.add(Calendar.DATE, 1); endDate=cal.getTime();
				  criteria.add(Restrictions.between("upfrontPaidDate", startDate, endDate));
			  }
			  else if(null!=startDate){
				  criteria.add(Restrictions.le("upfrontPaidDate", endDate));
			  }
			  else if(null!=endDate){
				  criteria.add(Restrictions.ge("upfrontPaidDate", startDate));
			  }
			  else{
				//dont add any restrictions..........
			  }	
			  
			  if(null!=supplier && supplier!=""){				 
				  criteria.add(Restrictions.eq("supplierName.supplierName", supplier));
			  }
			  
			  criteria.addOrder(Order.desc("upfrontPaidDate"));
			  reports=(ArrayList<SupplierReports>) criteria.list();
			  if(!reports.isEmpty()){
				  return reports;
			  }
			  else{
				  throw new SupplierReportsNotFoundException();
			  }
		  }
	  
	  //Getting Supplier Reports By Business Name
	  @SuppressWarnings("unchecked")
		 public ArrayList<SupplierReports> getSupplierReportsByBusinessNameBetweenDays(Date startDate,Date endDate,String businessName)throws SupplierReportsNotFoundException{
			  log.info("inside getSupplierReport()");
			  ArrayList<SupplierReports> reports=null;
			  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class);
			  System.out.println(startDate);
			  System.out.println(endDate+"ddd" );
			  if(null==startDate && null==endDate){
				  //dont add any restrictions..........
			  }
			  else if(null==startDate){
				  criteria.add(Restrictions.le("upfrontPaidDate", endDate));
			  }
			  else if(null==endDate){
				  criteria.add(Restrictions.ge("upfrontPaidDate", startDate));
			  }
			  else{
				  Calendar cal=Calendar.getInstance();
					cal.setTime(endDate); cal.add(Calendar.DATE, 1); endDate=cal.getTime();
				  criteria.add(Restrictions.between("upfrontPaidDate", startDate, endDate));
			  }	
			  
			  if(null!=businessName && businessName!=""){				 
				  criteria.add(Restrictions.eq("customerName", businessName).ignoreCase());
			  }
			  
			  criteria.addOrder(Order.desc("upfrontPaidDate"));
			  reports=(ArrayList<SupplierReports>) criteria.list();
			  if(!reports.isEmpty()){
				  return reports;
			  }
			  else{
				  throw new SupplierReportsNotFoundException();
			  }
		  }	
	  
	  /*
	   * Method to Get Supplier Reports Not Loaded on Pipeline..
	   * Added by Jeevan on August 28,2013 as per Clients Need to Display Supplier Reports Not in Pipeline
	   */
	  @SuppressWarnings("unchecked")
		 public ArrayList<SupplierReports> getSupplierReportsNotinPipeline(Date startDate,Date endDate,String supplier)throws SupplierReportsNotFoundException{
			  log.info("inside getSupplierReport()");
			  ArrayList<SupplierReports> reports=null;
			  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class);	
			  criteria.add(Restrictions.eq("updatedPipeline", false));
			  if(null==startDate && null==endDate){
				  //dont add any restrictions..........
			  }
			  else if(null==startDate){
				  criteria.add(Restrictions.le("upfrontPaidDate", endDate));
			  }
			  else if(null==endDate){
				  criteria.add(Restrictions.ge("upfrontPaidDate", startDate));
			  }
			  else{
				  Calendar cal=Calendar.getInstance();
					cal.setTime(endDate); cal.add(Calendar.DATE, 1); endDate=cal.getTime();
				  criteria.add(Restrictions.between("upfrontPaidDate", startDate, endDate));
			  }	
			  
			  if(null!=supplier && supplier!=""){				 
				  criteria.add(Restrictions.eq("supplierName.supplierName", supplier));
			  }
			  
			  criteria.addOrder(Order.desc("upfrontPaidDate"));
			  reports=(ArrayList<SupplierReports>) criteria.list();
			  if(!reports.isEmpty()){
				  return reports;
			  }
			  else{
				  throw new SupplierReportsNotFoundException();
			  }
		  }
	  
	  /*
	   * Added by Jeevan on October 17, 2013 to get Supplier Reports of am Agent
	   * 
	   */
	  @SuppressWarnings("unchecked")
	public ArrayList<SupplierReports> getSupplierReportsOfAgent(String agentNumber)throws SupplierReportsNotFoundException{
		  log.info("inside getSupplierReportsOfAgent()");
		  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class);
		  criteria.createCriteria("agentId").add(Restrictions.eq("agentNumber", agentNumber));		  
		  ArrayList<SupplierReports> reports=(ArrayList<SupplierReports>) criteria.list();
		  if(!reports.isEmpty()){
			  return reports;
		  }
		  else{
			  throw new SupplierReportsNotFoundException();
		  }
	  }
	  
	  
	  /*
	   * Added by Jeevan on November 20,2013
	   * Getting Supplier Reports By Ids
	   */
	  @SuppressWarnings("unchecked")
	public ArrayList<SupplierReports> getSupplierReportsByIds(ArrayList<Integer> ids) throws SupplierReportsNotFoundException{
		  log.info("inside getSupplierReportsByIds()");
		  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class);
		  criteria.add(Restrictions.in("id", ids));	  
		  ArrayList<SupplierReports> reports=(ArrayList<SupplierReports>) criteria.list();
		  if(!reports.isEmpty()){
			  return reports;
		  }
		  else{
			  throw new SupplierReportsNotFoundException();
		  }		  
	  }
	  
	  
	 
	  /*
	   * Added by Jeevan on November 20,2013.
	   * Method to get Supplier Reports by Supplier File
	   * 
	   */
	  @SuppressWarnings("unchecked")
	public ArrayList<SupplierReports> getSupplierReportsBySupplierFile(Integer fileId) throws SupplierReportsNotFoundException{
		  log.info("inside getSupplierReportsBySupplierFile()");
		  Criteria criteria=hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(SupplierReports.class);
		  criteria.createCriteria("supplierFile").add(Restrictions.eq("id", fileId));
		  ArrayList<SupplierReports> supplierReports=(ArrayList<SupplierReports>) criteria.list();
		  if(!supplierReports.isEmpty()){
			  return supplierReports;
		  }
		  else{
			  throw new SupplierReportsNotFoundException();
		  }
	  }
	  
	  
	  
	  /*
	   * Added by Jeevan on November 20, 2013
	   * Method to Delete Supplier Reports
	   */
	  public Integer deleteSupplierReports(ArrayList<SupplierReports> reports) throws Exception{
		  log.info("inside deleteSupplierReports()");
		   hibernateTemplate.deleteAll(reports);
		  hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		  return reports.get(0).getId();		  
	  }
}
