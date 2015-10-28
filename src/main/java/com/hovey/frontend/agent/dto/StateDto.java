package com.hovey.frontend.agent.dto;

import com.hovey.backend.agent.model.State;

public class StateDto {

	private int id;
	private String state;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	public static StateDto populateStateDto(State state){
		StateDto stateDto=new StateDto();
		stateDto.setId(state.getId());
		stateDto.setState(state.getState());
		return stateDto;
	}
	
	
}
