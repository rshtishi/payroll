package com.github.rshtishi.payroll.employee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.rshtishi.payroll.employee.entity.Employee;
import com.github.rshtishi.payroll.employee.entity.EmployeeActionEnum;
import com.github.rshtishi.payroll.employee.repository.EmployeeRepository;
import com.github.rshtishi.payroll.employee.source.EmployeeSource;

@Service
public class EmployeeServiceImp implements EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImp.class);

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeSource employeeSource;

	@Override
	public Page<Employee> findAll(Pageable pageable) {
		LOGGER.info("findAll called");
		return employeeRepository.findAll(pageable);
	}

	@Override
	public Employee findById(int id) {
		LOGGER.info("findById called, id: " + id);
		return employeeRepository.findById(id).get();
	}

	@Override
	public long countByDepartmentId(int departmentId) {
		LOGGER.info("countByDepartmentId called, departmentId: " + departmentId);
		return employeeRepository.countByDepartmentId(departmentId);
	}

	@Override
	public Employee createEmployee(Employee employee) {
		LOGGER.info("createEmployee called, employee: " + employee);
		employee = employeeRepository.save(employee);
		employeeSource.publishEmployeeCountChange(employee.getDepartmentId(), EmployeeActionEnum.CREATE.action());
		return employee;

	}

	@Override
	public Employee updateEmployee(Employee employee) {
		LOGGER.info("updateEmployee, employee: " + employee);
		employee = employeeRepository.save(employee);
		return employee;
	}

	@Override
	public void deleteEmployee(int employeeId) {
		LOGGER.info("deleteEmployee, employeeId: " + employeeId);
		Employee employee = employeeRepository.findById(employeeId).get();
		employeeRepository.deleteById(employeeId);
		employeeSource.publishEmployeeCountChange(employee.getDepartmentId(), EmployeeActionEnum.DELETE.action());
	}

}
