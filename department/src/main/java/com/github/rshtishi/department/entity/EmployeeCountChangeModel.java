package com.github.rshtishi.department.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCountChangeModel {
	
	private int departmentId;
	private String action;
	private String typeName;

}
