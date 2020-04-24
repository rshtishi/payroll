package com.github.rshtishi.payroll.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCountChangeModel {
	
	private int departmentId;
	private long employeeCount;
	private String typeName;

}
