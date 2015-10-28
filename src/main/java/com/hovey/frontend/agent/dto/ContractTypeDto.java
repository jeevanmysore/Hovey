package com.hovey.frontend.agent.dto;

import com.hovey.backend.agent.model.ContractTypes;

public class ContractTypeDto {

	private int id;
	private String contractType;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public static ContractTypeDto populateContractTypeDto(ContractTypes contractType){
		ContractTypeDto contractTypeDto=new ContractTypeDto();
		contractTypeDto.setId(contractType.getId());
		contractTypeDto.setContractType(contractType.getContractType());
		return contractTypeDto;
	}
	
	
}
