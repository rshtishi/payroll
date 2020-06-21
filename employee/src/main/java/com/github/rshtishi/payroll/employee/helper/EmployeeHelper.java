package com.github.rshtishi.payroll.employee.helper;

import org.springframework.stereotype.Component;

import com.github.rshtishi.payroll.employee.entity.Employee;
import com.github.rshtishi.payroll.employee.exception.ResourceNotFoundException;
import com.github.rshtishi.payroll.employee.service.EmployeeService;

@Component
public class EmployeeHelper {
	
	public Employee verifyEmployeeExistance(EmployeeService employeeService, int id) {
		Employee employee = employeeService.findById(id);
		if(employee==null) {
			throw new ResourceNotFoundException("Employee with id: "+id+" not found.");
		}
		return employee;
	}

}
