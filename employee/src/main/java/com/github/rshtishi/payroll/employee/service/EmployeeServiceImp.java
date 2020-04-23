package com.github.rshtishi.payroll.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rshtishi.payroll.employee.entity.Employee;
import com.github.rshtishi.payroll.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImp implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public long countByDepartmentId(int departmentId) {
		return employeeRepository.countByDepartmentId(departmentId);
	}

	@Override
	public void createEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

}
