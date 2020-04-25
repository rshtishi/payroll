package com.github.rshtishi.payroll.employee.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.rshtishi.payroll.employee.entity.Employee;
import com.github.rshtishi.payroll.employee.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public List<Employee> findAll() {
		return employeeService.findAll();
	}
	
	@GetMapping("/{departmentId}/count")
	public long countByDeparmentId(@PathVariable int departmentId) {
		return employeeService.countByDepartmentId(departmentId);
	}
	
	@PostMapping
	public void createEmployee(@RequestBody Employee employee) {
		employeeService.createEmployee(employee);
	}
	
	@PutMapping
	public void updateEmployee(@RequestBody Employee employee) {
		employeeService.updateEmployee(employee);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployee(id);
	}

}
