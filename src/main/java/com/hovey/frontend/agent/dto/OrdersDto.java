package com.hovey.frontend.agent.dto;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.WordUtils;






import com.hovey.backend.agent.model.Orders;
import com.hovey.backend.user.model.HoveyUser;
import com.hovey.frontend.supplier.dto.SupplierDto;
import com.hovey.frontend.user.dto.HoveyUserDto;

public class OrdersDto {
	
	private Integer id;
	private String accountNumber;
	private Date orderDate;
	private long tpv;
	private String rateClass;
	private double rate;
	private String term;
	private String businessName;
	private String dba;
	private int kwh;	
	private String serviceStreet;
	private String serviceUnit;
	private String serviceCity;
	private StateDto serviceState;
	private String serviceZip;
	private String billingStreet;
	private String billingUnit;
	private String billingCity;
	private BillingStateDto billingState;
	private String billingZip;
	private CustomerDto taxId;	
	private UtilityDto utility;
	private boolean faxReceived;
	private Date sentToSupplier;
	private String status;	
	private HoveyUserDto createdAgent;	
	/*private Collection<TransactionsDto> transDtos=new ArrayList<TransactionsDto>();*/
	private TransactionsDto transDto;	
	private double commission;
	private Date dealStartDate;
	private double upfrontCommission;
	private Date upfrontPaidDate;	
	private String contractType;
	private String notes;
	/*private String supplierName;*/
	private SupplierDto supplierName;
	private String agentNotes;
	private boolean specialPricing;
	private String startDate;	
	private String service;	
	private Date dealEndDate;
	//added recently calculate net commission helpful in reports.,.
	private Double netCommission;	
	/* Added on July 19,2013 by Jeevan to get Total no of Accounts for Each Deal.. */
	private Integer totalAccounts;	
	/* Added on 'July 22, 2013 to get Total No Of Commissions for No of Accounts of a customer */
	private Double totalCommission;
	private Double totalUpfrontCommission;		
	//added on July 31,2013 to store commission rates for each other..
	private Double commissionRate;	
	//added on August 19,2013.
	private String agentCommissionStatus;	
	//added on August 12,2013 in order to start and end date of Reportss
	private Date rstartDate;
	private Date rendDate;	
	//added on August 27,2013 to get total kwh
	private Long totalKwh;	
	//added on August 29,2013 in order to get Agent Commissions in Terms of Week and Year.. Used Just in DTO..
	private Integer week;
	private Integer year;	
	
	//added on September 19,2013 to store New Supplier Details
	private String county;
	private Date meterReadDate;
	
	//added on September 23, 2013
	private Boolean QA;
	
	//added on December 09, 2013.
	private Long weekskWh;
	
	private Map<String, Long> agentCommissionsMap=new HashMap<String, Long>();
	
	//added on December 09
	private Double agentCommissionRate;
	
	//added by bhagya on April 15th 2014
	private HoveyUserDto resAgent;
	
	//added by bhagya on may 22nd,2014
	private double upfrontCommission2;
	private Date upfrontPaidDate2;
	private double upfrontCommission3;
	private Date upfrontPaidDate3;
	private double upfrontCommission4;
	private Date upfrontPaidDate4;
	
	//added by bhagya on May23rd,2014
	private double termCommission;
	
	//added by bhagya on May26th,2014
	private Integer orderYear;
	
	//addd by Jeevan on May 29, 2014
	private Integer termMonths;
	
	//added by Jeevan on May 29,2014
	private Double totalTermCommission;
	
	
	//added by Jeevan on May 30, 2014
	private Integer totalResults;
	
	
	
	
	public Integer getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}
	public Double getTotalTermCommission() {
		return totalTermCommission;
	}
	public void setTotalTermCommission(Double totalTermCommission) {
		this.totalTermCommission = totalTermCommission;
	}
	public Integer getTermMonths() {
		return termMonths;
	}
	public void setTermMonths(Integer termMonths) {
		this.termMonths = termMonths;
	}
	public Double getAgentCommissionRate() {
		return agentCommissionRate;
	}
	public void setAgentCommissionRate(Double agentCommissionRate) {
		this.agentCommissionRate = agentCommissionRate;
	}
	public Map<String, Long> getAgentCommissionsMap() {
		return agentCommissionsMap;
	}
	public void setAgentCommissionsMap(Map<String, Long> agentCommissionsMap) {
		this.agentCommissionsMap = agentCommissionsMap;
	}
	public Long getWeekskWh() {
		return weekskWh;
	}
	public void setWeekskWh(Long weekskWh) {
		this.weekskWh = weekskWh;
	}
	
	public Boolean getQA() {
		return QA;
	}
	public void setQA(Boolean qA) {
		QA = qA;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public Date getMeterReadDate() {
		return meterReadDate;
	}
	public void setMeterReadDate(Date meterReadDate) {
		this.meterReadDate = meterReadDate;
	}
	public int getKwh() {
		return kwh;
	}
	public void setKwh(int kwh) {
		this.kwh = kwh;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Long getTotalKwh() {
		return totalKwh;
	}
	public void setTotalKwh(Long totalKwh) {
		this.totalKwh = totalKwh;
	}
	public String getAgentCommissionStatus() {
		return agentCommissionStatus;
	}
	public void setAgentCommissionStatus(String agentCommissionStatus) {
		this.agentCommissionStatus = agentCommissionStatus;
	}
	public Date getRstartDate() {
		return rstartDate;
	}
	public void setRstartDate(Date rstartDate) {
		this.rstartDate = rstartDate;
	}
	public Date getRendDate() {
		return rendDate;
	}
	public void setRendDate(Date rendDate) {
		this.rendDate = rendDate;
	}
	public Double getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}
	public Double getTotalCommission() {
		return totalCommission;
	}
	public void setTotalCommission(Double totalCommission) {
		this.totalCommission = totalCommission;
	}
	public Double getTotalUpfrontCommission() {
		return totalUpfrontCommission;
	}
	public void setTotalUpfrontCommission(Double totalUpfrontCommission) {
		this.totalUpfrontCommission = totalUpfrontCommission;
	}
	public Integer getTotalAccounts() {
		return totalAccounts;
	}
	public void setTotalAccounts(Integer totalAccounts) {
		this.totalAccounts = totalAccounts;
	}
	public Double getNetCommission() {
		return netCommission;
	}
	public void setNetCommission(Double netCommission) {
		this.netCommission = netCommission;
	}
	public Date getDealEndDate() {
		return dealEndDate;
	}
	public void setDealEndDate(Date dealEndDate) {
		this.dealEndDate = dealEndDate;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public boolean isSpecialPricing() {
		return specialPricing;
	}
	public void setSpecialPricing(boolean specialPricing) {
		this.specialPricing = specialPricing;
	}
	public String getAgentNotes() {
		return agentNotes;
	}
	public void setAgentNotes(String agentNotes) {
		this.agentNotes = agentNotes;
	}	
	public SupplierDto getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(SupplierDto supplierName) {
		this.supplierName = supplierName;
	}	
	public String getContractType() {
		return contractType;
	}
	
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	public Date getDealStartDate() {
		return dealStartDate;
	}
	public void setDealStartDate(Date dealStartDate) {
		this.dealStartDate = dealStartDate;
	}
	public Double getUpfrontCommission() {
		return upfrontCommission;
	}
	public void setUpfrontCommission(double upfrontCommission) {
		this.upfrontCommission = upfrontCommission;
	}
	public Date getUpfrontPaidDate() {
		return upfrontPaidDate;
	}
	public void setUpfrontPaidDate(Date upfrontPaidDate) {
		this.upfrontPaidDate = upfrontPaidDate;
	}
	public HoveyUserDto getCreatedAgent() {
		return createdAgent;
	}
	public void setCreatedAgent(HoveyUserDto createdAgent) {
		this.createdAgent = createdAgent;
	}
	
	public String getStatus() {
		return status;
	}
	public TransactionsDto getTransDto() {
		return transDto;
	}
	public void setTransDto(TransactionsDto transDto) {
		this.transDto = transDto;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isFaxReceived() {
		return faxReceived;
	}
	public void setFaxReceived(boolean faxReceived) {
		this.faxReceived = faxReceived;
	}
	
	
	public Date getSentToSupplier() {
		return sentToSupplier;
	}
	public void setSentToSupplier(Date sentToSupplier) {
		this.sentToSupplier = sentToSupplier;
	}
	public UtilityDto getUtility() {
		return utility;
	}
	public void setUtility(UtilityDto utility) {
		this.utility = utility;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}	
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public long getTpv() {
		return tpv;
	}
	public void setTpv(long tpv) {
		this.tpv = tpv;
	}
	public String getRateClass() {
		return rateClass;
	}
	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getDba() {
		return dba;
	}
	public void setDba(String dba) {
		this.dba = dba;
	}
	public String getServiceStreet() {
		return serviceStreet;
	}
	
	public void setServiceStreet(String serviceStreet) {
		this.serviceStreet = serviceStreet;
	}
	public String getServiceUnit() {
		return serviceUnit;
	}
	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}
	public String getServiceCity() {
		return serviceCity;
	}
	public void setServiceCity(String serviceCity) {
		this.serviceCity = serviceCity;
	}
	
	public String getServiceZip() {
		return serviceZip;
	}
	public StateDto getServiceState() {
		return serviceState;
	}
	public void setServiceState(StateDto serviceState) {
		this.serviceState = serviceState;
	}
	public void setServiceZip(String serviceZip) {
		this.serviceZip = serviceZip;
	}

	public String getBillingStreet() {
		return billingStreet;
	}
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}
	public String getBillingUnit() {
		return billingUnit;
	}
	public void setBillingUnit(String billingUnit) {
		this.billingUnit = billingUnit;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}	
	
	
	
	
	public BillingStateDto getBillingState() {
		return billingState;
	}
	public void setBillingState(BillingStateDto billingState) {
		this.billingState = billingState;
	}
	public String getBillingZip() {
		return billingZip;
	}

	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}	
	public CustomerDto getTaxId() {
		return taxId;
	}
	public void setTaxId(CustomerDto taxId) {
		this.taxId = taxId;
	}
	
	//added by bhagya on April 15th 2014
	public HoveyUserDto getResAgent() {
		return resAgent;
	}
	public void setResAgent(HoveyUserDto resAgent) {
		this.resAgent = resAgent;
	}
	
	// added by bhagya on may 22nd,2014
	public double getUpfrontCommission2() {
		return upfrontCommission2;
	}
	public void setUpfrontCommission2(double upfrontCommission2) {
		this.upfrontCommission2 = upfrontCommission2;
	}
	public Date getUpfrontPaidDate2() {
		return upfrontPaidDate2;
	}
	public void setUpfrontPaidDate2(Date upfrontPaidDate2) {
		this.upfrontPaidDate2 = upfrontPaidDate2;
	}
	public double getUpfrontCommission3() {
		return upfrontCommission3;
	}
	public void setUpfrontCommission3(double upfrontCommission3) {
		this.upfrontCommission3 = upfrontCommission3;
	}
	public Date getUpfrontPaidDate3() {
		return upfrontPaidDate3;
	}
	public void setUpfrontPaidDate3(Date upfrontPaidDate3) {
		this.upfrontPaidDate3 = upfrontPaidDate3;
	}
	public double getUpfrontCommission4() {
		return upfrontCommission4;
	}
	public void setUpfrontCommission4(double upfrontCommission4) {
		this.upfrontCommission4 = upfrontCommission4;
	}
	public Date getUpfrontPaidDate4() {
		return upfrontPaidDate4;
	}
	public void setUpfrontPaidDate4(Date upfrontPaidDate4) {
		this.upfrontPaidDate4 = upfrontPaidDate4;
	}
	
	//added by bhagya on may23rd,2014
	public double getTermCommission() {
		return termCommission;
	}
	public void setTermCommission(double termCommission) {
		this.termCommission = termCommission;
	}
	
	//added by bhagya on may 26th,2014
	public Integer getOrderYear() {
		return orderYear;
	}
	public void setOrderYear(Integer orderYear) {
		this.orderYear = orderYear;
	}
	public static OrdersDto populateOrderDto(Orders order){
		OrdersDto orderDto=new OrdersDto();
		orderDto.setAccountNumber(order.getAccountNumber());
		orderDto.setBillingCity(WordUtils.capitalize(order.getBillingCity()));
		if(null!=order.getBillingState()){
			BillingStateDto stateDto=BillingStateDto.populateBillingStateDto(order.getBillingState());
			orderDto.setBillingState(stateDto);
		}
			
		orderDto.setBillingStreet(WordUtils.capitalize(order.getBillingStreet()));
		orderDto.setBillingUnit(order.getBillingUnit());
		orderDto.setBillingZip(order.getBillingZip());
		orderDto.setBusinessName(WordUtils.capitalize(order.getBusinessName()));
		orderDto.setDba(WordUtils.capitalize(order.getDba()));
		orderDto.setId(order.getOrderId());
		orderDto.setRate(order.getRate());
		orderDto.setRateClass(order.getRateClass());
		orderDto.setKwh(order.getKwh());
		orderDto.setServiceCity(WordUtils.capitalize(order.getServiceCity()));		
		StateDto serviceState=new StateDto();
		if(null!=order.getServiceState()){
			serviceState.setState(order.getServiceState().getState());	
			orderDto.setServiceState(serviceState);
		}
		
		orderDto.setServiceStreet(WordUtils.capitalize(order.getServiceStreet()));
		orderDto.setServiceUnit(WordUtils.capitalize(order.getServiceUnit()));
		orderDto.setServiceZip(order.getServiceZip());
		orderDto.setOrderDate(order.getOrderDate());
		CustomerDto customerDto=CustomerDto.populateCustomerDto(order.getTaxId());
		orderDto.setTaxId(customerDto);
		orderDto.setTerm(order.getTerm());
		if(null!=order.getTpv()){
			orderDto.setTpv(order.getTpv());	
		}		
		orderDto.setFaxReceived(order.isFaxReceived());
		orderDto.setSentToSupplier(order.getSentToSupplier());
		orderDto.setStatus(WordUtils.capitalize(order.getStatus()));
		SupplierDto supplierDto=SupplierDto.populateSupplier(order.getSupplierName());		
		orderDto.setSupplierName(supplierDto);
		UtilityDto utilDto=UtilityDto.populateUtility(order.getUtility());		
		orderDto.setUtility(utilDto);
		orderDto.setSpecialPricing(order.isSpecialPricing());
		HoveyUserDto hoveyDto=HoveyUserDto.populateHoveyUserDto(order.getAgent());
		orderDto.setCreatedAgent(hoveyDto);	
		TransactionsDto transDto=new TransactionsDto();
		transDto.setTransactionId(order.getTransactionId().getId());
		orderDto.setTransDto(transDto);		
		if(null!=order.getCommission()){
			orderDto.setCommission(order.getCommission());
		}
		if(null!=order.getDealStartDate()){
			orderDto.setDealStartDate(order.getDealStartDate());
		}
		if(null!=order.getUpfrontCommission()){
			orderDto.setUpfrontCommission(order.getUpfrontCommission());
		}
		if(null!=order.getUpfrontPaidDate()){
			orderDto.setUpfrontPaidDate(order.getUpfrontPaidDate());
		}
		if(null!=order.getNotes()){
			orderDto.setNotes(order.getNotes());
		}
		
		if(null!=order.getContractType()){
			orderDto.setContractType(order.getContractType());
		}
		if(null!=order.getAgentNotes()){
			orderDto.setAgentNotes(order.getAgentNotes());
		}
		if(null!=order.getService()){
			orderDto.setService(order.getService());
		}
		
		if(null!=order.getDealEndDate()){
			orderDto.setDealEndDate(order.getDealEndDate());
		}		
		if(null!=order.getCommissionRate()){
			orderDto.setCommissionRate(order.getCommissionRate());
		}
		if(null!=order.getAgentCommissionStatus()){
			orderDto.setAgentCommissionStatus(order.getAgentCommissionStatus());
		}
		if(null!=order.getCounty()){
			orderDto.setCounty(order.getCounty());
		}
		if(null!=order.getMeterReadDate()){
			orderDto.setMeterReadDate(order.getMeterReadDate());
		}
		if(null!=order.getQA()){
			orderDto.setQA(order.getQA());
		}
		
		//added by bhagya on april 15th 2014
		
		if(null!=order.getResAgent()){
		orderDto.setResAgent(HoveyUserDto.populateHoveyUserDto(order.getResAgent()));
		}
		//
		//added by bhagya on may 22nd,2014
		if(null!=order.getUpfrontCommission2()){
			orderDto.setUpfrontCommission2(order.getUpfrontCommission2());
		}
		if(null!=order.getUpfrontPaidDate2()){
			orderDto.setUpfrontPaidDate2(order.getUpfrontPaidDate2());
		}
		if(null!=order.getUpfrontCommission3()){
			orderDto.setUpfrontCommission3(order.getUpfrontCommission3());
		}
		if(null!=order.getUpfrontPaidDate3()){
			orderDto.setUpfrontPaidDate3(order.getUpfrontPaidDate3());
		}
		if(null!=order.getUpfrontCommission4()){
			orderDto.setUpfrontCommission4(order.getUpfrontCommission4());
		}
		if(null!=order.getUpfrontPaidDate4()){
			orderDto.setUpfrontPaidDate4(order.getUpfrontPaidDate4());
		}
		if(null!=order.getTotalResults()){
			orderDto.setTotalResults(order.getTotalResults());
		}
		return orderDto;		
	}
	
	
	

	
	public static OrdersDto populateOrderDtoForDashBoard(Orders order){
		OrdersDto orderDto=new OrdersDto();
		orderDto.setAccountNumber(order.getAccountNumber());
		orderDto.setBillingCity(order.getBillingCity());		
		orderDto.setBillingStreet(order.getBillingStreet());
		orderDto.setBillingUnit(order.getBillingUnit());
		orderDto.setBillingZip(order.getBillingZip());
		orderDto.setBusinessName(order.getBusinessName());
		orderDto.setDba(order.getDba());
		orderDto.setId(order.getOrderId());
		orderDto.setRate(order.getRate());
		orderDto.setRateClass(order.getRateClass());
		orderDto.setKwh(order.getKwh());
		orderDto.setServiceCity(order.getServiceCity());		
			
		if(null!=order.getServiceState()){
			StateDto serviceState=StateDto.populateStateDto(order.getServiceState());
				
			orderDto.setServiceState(serviceState);
		}
		
		orderDto.setServiceStreet(order.getServiceStreet());
		orderDto.setServiceUnit(order.getServiceUnit());
		orderDto.setServiceZip(order.getServiceZip());
		orderDto.setOrderDate(order.getOrderDate());
		CustomerDto customerDto=CustomerDto.populateCustomerDto(order.getTaxId());
		orderDto.setTaxId(customerDto);
		orderDto.setTerm(order.getTerm());
		if(null!=order.getTpv()){
			orderDto.setTpv(order.getTpv());	
		}		
		orderDto.setFaxReceived(order.isFaxReceived());
		orderDto.setSentToSupplier(order.getSentToSupplier());
		orderDto.setStatus(order.getStatus());
		SupplierDto supplierDto=SupplierDto.populateSupplier(order.getSupplierName());		
		orderDto.setSupplierName(supplierDto);
		UtilityDto utilDto=UtilityDto.populateUtility(order.getUtility());		
		orderDto.setUtility(utilDto);
		orderDto.setSpecialPricing(order.isSpecialPricing());
		HoveyUserDto hoveyDto=HoveyUserDto.populateHoveyUserDto(order.getAgent());
		orderDto.setCreatedAgent(hoveyDto);	
		TransactionsDto transDto=new TransactionsDto();
		transDto.setTransactionId(order.getTransactionId().getId());
		orderDto.setTransDto(transDto);		
		if(null!=order.getCommission()){
			orderDto.setCommission(order.getCommission());
		}
		if(null!=order.getDealStartDate()){
			orderDto.setDealStartDate(order.getDealStartDate());
		}
		if(null!=order.getUpfrontCommission()){
			orderDto.setUpfrontCommission(order.getUpfrontCommission());
		}
		if(null!=order.getUpfrontPaidDate()){
			orderDto.setUpfrontPaidDate(order.getUpfrontPaidDate());
		}
		if(null!=order.getNotes()){
			orderDto.setNotes(order.getNotes());
		}
		
		if(null!=order.getContractType()){
			orderDto.setContractType(order.getContractType());
		}
		if(null!=order.getAgentNotes()){
			orderDto.setAgentNotes(order.getAgentNotes());
		}
		if(null!=order.getService()){
			orderDto.setService(order.getService());
		}
		
		if(null!=order.getDealEndDate()){
			orderDto.setDealEndDate(order.getDealEndDate());
		}
		
		if(null!=order.getCommissionRate()){
			orderDto.setCommissionRate(order.getCommissionRate());
		}
		if(null!=order.getAgentCommissionStatus()){
			orderDto.setAgentCommissionStatus(order.getAgentCommissionStatus());
		}
		if(null!=order.getCounty()){
			orderDto.setCounty(order.getCounty());
		}
		if(null!=order.getMeterReadDate()){
			orderDto.setMeterReadDate(order.getMeterReadDate());
		}
		if(null!=order.getQA()){
			orderDto.setQA(order.getQA());
		}
		if(null!=order.getAgentCommissionRate()){
			orderDto.setAgentCommissionRate(order.getAgentCommissionRate());
		}
		
		if(null!=order.getTotalResults()){
			orderDto.setTotalResults(order.getTotalResults());
		}
		return orderDto;		
	}
	

}
