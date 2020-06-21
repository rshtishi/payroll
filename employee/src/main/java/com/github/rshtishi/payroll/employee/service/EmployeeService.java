package com.github.rshtishi.payroll.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.rshtishi.payroll.employee.entity.Employee;

public interface EmployeeService {

	public Page<Employee> findAll(Pageable pageable);
	
	public Employee findById(int id);
	
	public long countByDepartmentId(int departmentId);
	
	public Employee createEmployee(Employee employee);
	
	public Employee updateEmployee(Employee employee);
	
	public void deleteEmployee(int employeeId);

}
