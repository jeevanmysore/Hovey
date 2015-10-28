package com.hovey.backend.supplier.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hovey.backend.user.model.HoveyUser;


/**
 * 
 * @author JEEVAN]
 * 
 * Model for Supplier Reports..
 *
 */

@Entity
@Table(name="supplier_reports")
public class SupplierReports implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="supplier_name",referencedColumnName="supplier_name")
	private Supplier supplierName;
	
	@Column(name="customer_name",nullable=false,unique=false)
	private String customerName;
	
	@Column(name="accountNumber",nullable=false,unique=false)
	private String accountNumber;
	
	@Column(name="contract_start_date")
	private Date contractStartDate;
	
	@Column(name="contract_end_date")
	private Date contractEndDate;
	
	@Column(name="term",nullable=false)
	private String term;
	
	@Column(name="rate")
	private Double rate;

	@Column(name="annual_kwh",nullable=false)
	private Integer kwh;
	
	@ManyToOne
	@JoinColumn(name="agent_ID",referencedColumnName="agent_number")
	private HoveyUser agentId;

	@Column(name="commission_rate",nullable=false)
	private Double commissionRate;
	
	@Column(name="date_paid",nullable=false)
	private Date upfrontPaidDate;
	
	@Column(name="total_commission_paid",nullable=false)
	private Double totalCommissionPaid;
	
	@ManyToOne
	@JoinColumn(name="fileName",referencedColumnName="fileName",nullable=false)
	private SupplierFiles supplierFile;
	
	@Column(name="updated_pipeline")
	private Boolean updatedPipeline;
	
	
	
	
	

	public Boolean getUpdatedPipeline() {
		return updatedPipeline;
	}

	public void setUpdatedPipeline(Boolean updatedPipeline) {
		this.updatedPipeline = updatedPipeline;
	}

	public SupplierFiles getSupplierFile() {
		return supplierFile;
	}

	public void setSupplierFile(SupplierFiles supplierFile) {
		this.supplierFile = supplierFile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Supplier getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(Supplier supplierName) {
		this.supplierName = supplierName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getKwh() {
		return kwh;
	}

	public void setKwh(Integer kwh) {
		this.kwh = kwh;
	}

	public HoveyUser getAgentId() {
		return agentId;
	}

	public void setAgentId(HoveyUser agentId) {
		this.agentId = agentId;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public Date getUpfrontPaidDate() {
		return upfrontPaidDate;
	}

	public void setUpfrontPaidDate(Date upfrontPaidDate) {
		this.upfrontPaidDate = upfrontPaidDate;
	}

	public Double getTotalCommissionPaid() {
		return totalCommissionPaid;
	}

	public void setTotalCommissionPaid(Double totalCommissionPaid) {
		this.totalCommissionPaid = totalCommissionPaid;
	}
	
	
}
