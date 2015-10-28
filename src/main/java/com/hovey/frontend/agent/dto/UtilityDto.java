package com.hovey.frontend.agent.dto;



import org.apache.commons.lang.WordUtils;

import com.hovey.backend.agent.model.Utility;

public class UtilityDto {

	/**
	 * @author JEEVAN
	 */
	
	private int id;
	private String utility;
	private int accountLength;
	private Boolean isEnabled;
	
	
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUtility() {
		return utility;
	}
	public void setUtility(String utility) {
		this.utility = utility;
	}
	public int getAccountLength() {
		return accountLength;
	}
	public void setAccountLength(int accountLength) {
		this.accountLength = accountLength;
	}
	
	// populating Dto from Model.
	public static UtilityDto populateUtility(Utility utility){
		UtilityDto utilDto=new UtilityDto();
		utilDto.setAccountLength(utility.getAccountLenght());
		utilDto.setId(utility.getId());
		utilDto.setUtility(WordUtils.capitalize(utility.getUtility()));
		utilDto.setIsEnabled(utility.isEnabled());
		return utilDto;
	}
}
