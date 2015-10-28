package com.hovey.backend.agent.model;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;



import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



import com.hovey.backend.supplier.model.Supplier;
import com.hovey.backend.user.model.HoveyUser;

/**
 * @author KNS-ACCONTS
 *
 */
@Entity
@Table(name="orders",
 uniqueConstraints={@UniqueConstraint(columnNames={"account_no","deal_start_date","status","term","kwh" }) })
public class Orders  implements Serializable{

	/**
	 * @author JEEVAN
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="order_id")
	private Integer orderId;
	
	
	@Column(name="account_no",length=50)
	private String accountNumber;
	
	@Column(name="order_date", nullable=false)
	private Date orderDate;
	
	@Column(name="tpv",length=15)
	private Long tpv;
	
	@Column(name="rate_class",length=20)
	private String rateClass;
	
	@Column(name="rate",nullable=false)
	private Double rate;	
	
	@Column(name="term",nullable=false,length=5)
	private String term;

	@Column(name="business_name",nullable=false)
	private String businessName;
	
	@Column(name="dba")
	private String dba;

	@Column(name="kwh",nullable=false)
	private Integer kwh;
	
	@Column(name="svc_street",nullable=false)
	private String serviceStreet;
	
	@Column(name="svc_unit")
	private String serviceUnit;
	
	@Column(name="svc_city",nullable=false)
	private String serviceCity;

	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="svc_state",referencedColumnName="state")
	private State serviceState;

	@Column(name="service_zip",nullable=false)
	private String serviceZip;
	
	@Column(name="bill_street",nullable=false)
	private String billingStreet;
	
	@Column(name="bill_unit")
	private String billingUnit;
	
	
	@Column(name="bill_city",nullable=false)
	private String billingCity;

	/*@Column(name="bill_state",nullable=false,length=10)
	private String billingState;*/

	
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="bill_state",referencedColumnName="state")	
	private BillingState billingState;
	
	
	@Column(name="bill_zip",nullable=false)
	private String billingZip;
	
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="utitlity",referencedColumnName="utility")
	private Utility utility;
	
	@Column(name="fax_received")
	private boolean faxReceived;
	
	@Column(name="sent_to_supplier")
	private Date sentToSupplier;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="tax_id")
	private Customer taxId;
	
	
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@OrderBy("firstName")
	@JoinColumn(name="created_agent",referencedColumnName="agent_number",nullable=false)
	private HoveyUser agent;
	
	/**  for handling supplier data and admin functionalities*/
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="transaction_id",nullable=false)
	private Transactions transactionId;

	@Column(name="commission")
	private Double commission;
	
	@Column(name="deal_start_date")
	private Date dealStartDate;
	
	@Column(name="upfront_commission")
	private Double upfrontCommission;


	@Column(name="upfront_paid_date")
	private Date upfrontPaidDate;
	
	@Column(name="contract_type")
	private String contractType;
	
	@Column(name="notes")
	private String notes;	
	
	/*@Column(name="supplier_name")
	private String supplierName;*/
	
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="supplier_name",referencedColumnName="supplier_name")
	@Cascade({CascadeType.SAVE_UPDATE})
	private Supplier supplierName;
	
	@Column(name="agent_notes")
	private String agentNotes;
	
	@Column(name="special_pricing")
	private boolean specialPricing;
	
	
	@Column(name="service")
	private String service;
	
	//added on 15-06 as handling renewals would be easy...
	@Column(name="deal_end_date")
	private Date dealEndDate;
	
	
	//added on July 31, 2013 to store commission rate for a order..
	public Double commissionRate;
	
	//added by Jeevanb on August 19,2013 to track Agent Commissions of that Order..
	@Column(name="agent_commision_Status")
	private String agentCommissionStatus;	
	
	//added by Jeevan on September 19,2013 to accomodate county and meter date reading on Database.. and to persist fax received
	@Column(name="county",nullable=true,unique=false)
	private String county;
	
	@Column(name="meter_read_date",nullable=true)
	@Temporal(TemporalType.DATE)
	private Date meterReadDate;
	
	
	//added on September 23,2013 as per clients need to store QA
	
	@Column(name="qa",nullable=false)
	private Boolean QA;	
	
	
	//added by jeevan on December 09,2013
	@Column(name="agent_commission_rate",nullable=true)
	private Double agentCommissionRate;
	
	
	//addded by bhagya on April 15th, 2014
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@OrderBy("firstName")
	@JoinColumn(name="rescinded_order_agent",referencedColumnName="agent_number",nullable=true)
	private HoveyUser resAgent;
	
	//added by bhagya on may 22,2014
	@Column(name="upfront_commission2")
	private Double upfrontCommission2;


	@Column(name="upfront_paid_date2")
	private Date upfrontPaidDate2;
	
	@Column(name="upfront_commission3")
	private Double upfrontCommission3;


	@Column(name="upfront_paid_date3")
	private Date upfrontPaidDate3;
	
	@Column(name="upfront_commission4")
	private Double upfrontCommission4;


	@Column(name="upfront_paid_date4")
	private Date upfrontPaidDate4;
	
	//added by jeevan to get No of Records
	private Integer totalResults;
	

	
	
	
	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public Double getAgentCommissionRate() {
		return agentCommissionRate;
	}

	public void setAgentCommissionRate(Double agentCommissionRate) {
		this.agentCommissionRate = agentCommissionRate;
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

	public String getAgentCommissionStatus() {
		return agentCommissionStatus;
	}

	public void setAgentCommissionStatus(String agentCommissionStatus) {
		this.agentCommissionStatus = agentCommissionStatus;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
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

	/*public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}*/

	
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	
	
	public Supplier getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(Supplier supplierName) {
		this.supplierName = supplierName;
	}

	

	

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
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

	public void setUpfrontCommission(Double upfrontCommission) {
		this.upfrontCommission = upfrontCommission;
	}

	public Date getUpfrontPaidDate() {
		return upfrontPaidDate;
	}

	public void setUpfrontPaidDate(Date upfrontPaidDate) {
		this.upfrontPaidDate = upfrontPaidDate;
	}
	/**  for handling supplier data and admin functionalities*/
	
	
	public Transactions getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Transactions transactionId) {
		this.transactionId = transactionId;
	}

	public HoveyUser getAgent() {
		
		
		return agent;
	}

	public void setAgent(HoveyUser agent) {
		
		this.agent = agent;
		this.agent.setFirstName(agent.getFirstName());
	}

	public String getStatus() {
		return status;
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

	public Utility getUtility() {
		return utility;
	}

	public void setUtility(Utility utility) {
		this.utility = utility;
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

	

	public Long getTpv() {
		return tpv;
	}

	public void setTpv(Long tpv) {
		this.tpv = tpv;
	}

	public String getRateClass() {
		return rateClass;
	}

	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
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

	public Integer getKwh() {
		return kwh;
	}

	public void setKwh(Integer kwh) {
		this.kwh = kwh;
	}

	/*public String getServiceStreet() {
		return serviceStreet;
	}

	public void setServiceStreet(String serviceStreet) {
		this.serviceStreet = serviceStreet;
	}
*/
	
	
	public String getServiceUnit() {
		return serviceUnit;
	}

	public String getServiceStreet() {
		return serviceStreet;
	}

	public void setServiceStreet(String serviceStreet) {
		this.serviceStreet = serviceStreet;
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

	public State getServiceState() {
		return serviceState;
	}

	public void setServiceState(State serviceState) {
		this.serviceState = serviceState;
	}

	public String getServiceZip() {
		return serviceZip;
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

/*	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
*/
	
	
	
	public String getBillingZip() {
		return billingZip;
	}

	public BillingState getBillingState() {
		return billingState;
	}
	

	public void setBillingState(BillingState billingState) {
		this.billingState = billingState;
	}

	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}

	public Customer getTaxId() {
		return taxId;
	}

	public void setTaxId(Customer taxId) {
		this.taxId = taxId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	//added by bhagya on april 15th,2014 
	
	public HoveyUser getResAgent() {
		return resAgent;
	}

	public void setResAgent(HoveyUser resAgent) {
		this.resAgent = resAgent;
	}

	//added by bhagya on may 22nd,2014
	
	public Double getUpfrontCommission2() {
		return upfrontCommission2;
	}

	public void setUpfrontCommission2(Double upfrontCommission2) {
		this.upfrontCommission2 = upfrontCommission2;
	}

	public Date getUpfrontPaidDate2() {
		return upfrontPaidDate2;
	}

	public void setUpfrontPaidDate2(Date upfrontPaidDate2) {
		this.upfrontPaidDate2 = upfrontPaidDate2;
	}

	public Double getUpfrontCommission3() {
		return upfrontCommission3;
	}

	public void setUpfrontCommission3(Double upfrontCommission3) {
		this.upfrontCommission3 = upfrontCommission3;
	}

	public Date getUpfrontPaidDate3() {
		return upfrontPaidDate3;
	}

	public void setUpfrontPaidDate3(Date upfrontPaidDate3) {
		this.upfrontPaidDate3 = upfrontPaidDate3;
	}

	public Double getUpfrontCommission4() {
		return upfrontCommission4;
	}

	public void setUpfrontCommission4(Double upfrontCommission4) {
		this.upfrontCommission4 = upfrontCommission4;
	}

	public Date getUpfrontPaidDate4() {
		return upfrontPaidDate4;
	}

	public void setUpfrontPaidDate4(Date upfrontPaidDate4) {
		this.upfrontPaidDate4 = upfrontPaidDate4;
	}

	
	
	
}
