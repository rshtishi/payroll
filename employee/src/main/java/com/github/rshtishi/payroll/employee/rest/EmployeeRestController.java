package com.github.rshtishi.payroll.employee.rest;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.rshtishi.payroll.employee.entity.Employee;
import com.github.rshtishi.payroll.employee.helper.EmployeeHelper;
import com.github.rshtishi.payroll.employee.service.EmployeeService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/employees")
@Api(value = "employees", description = "Employee API")
public class EmployeeRestController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeHelper employeeHelper;

	@GetMapping
	@ApiOperation(value = "Retrieves all employees", response = Employee.class, responseContainer = "List")
	public ResponseEntity<Page<Employee>> findAll(Pageable pageable) {
		Page<Employee> employees = employeeService.findAll(pageable);
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieves a Employee associated with employeeId", response = Employee.class)
	public ResponseEntity<Employee> findById(@PathVariable int id) {
		Employee employee = employeeHelper.verifyEmployeeExistance(employeeService, id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@GetMapping("/{departmentId}/count")
	@ApiOperation(value = "Retrieves number of employees associated with departmentId", response = Long.class)
	public long countByDeparmentId(@PathVariable int departmentId) {
		return employeeService.countByDepartmentId(departmentId);
	}

	@PostMapping
	@ApiOperation(value = "Create a new Employee", response = Employee.class, notes = "The newly created employee id will be sent in the location response header")
	public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
		employee = employeeService.createEmployee(employee);
		HttpHeaders responseHttpHeaders = new HttpHeaders();
		URI newEmployeeUri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(employee.getId()).toUri();
		responseHttpHeaders.setLocation(newEmployeeUri);
		return new ResponseEntity<>(employee, responseHttpHeaders, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@ApiOperation(value = "Modifies employee associated with employeeId", response = Employee.class)
	public ResponseEntity<Employee> update(@RequestBody Employee employee, @PathVariable int id) {
		employeeHelper.verifyEmployeeExistance(employeeService, id);
		employee.setId(id);
		employee = employeeService.updateEmployee(employee);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletes employee associated with employeeId")
	public ResponseEntity<?> delete(@PathVariable int id) {
		employeeHelper.verifyEmployeeExistance(employeeService, id);
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
