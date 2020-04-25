package com.github.rshtishi.department.entity;

public enum EmployeeActionEnum {
	
	CREATE("create"),
	UPDATE("update"),
	DELETE("delete");
	
	private String action;
	
	EmployeeActionEnum(String action) {
		this.action=action;
	}
	
	public String action() {
		return action;
	}

}
