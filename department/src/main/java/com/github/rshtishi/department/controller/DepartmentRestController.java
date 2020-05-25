package com.github.rshtishi.department.controller;

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

import com.github.rshtishi.department.entity.Department;
import com.github.rshtishi.department.helper.DepartmentHelper;
import com.github.rshtishi.department.service.DepartmentService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/departments")
@Api(value = "departments", description = "Department API")
public class DepartmentRestController {

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DepartmentHelper departmentHelper;

	@GetMapping
	@ApiOperation(value = "Retrieves all departments", response = Department.class, responseContainer = "List")
	public ResponseEntity<Page<Department>> findAll(Pageable pageable) {
		Page<Department> departments = departmentService.findAll(pageable);
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieves a Deparment associated with deparmentId", response = Department.class)
	public ResponseEntity<Department> findById(@PathVariable int id) {
		Department department = departmentHelper.verifyDeparmentExistence(departmentService, id);
		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "Create a new Department", response = Department.class, notes = "The newly created department id will be sent in the location response header")
	public ResponseEntity<Department> create(@Valid @RequestBody Department department) {
		department = departmentService.createEmployee(department);
		HttpHeaders responseHttpHeaders = new HttpHeaders();
		URI newDepartmentUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(department.getId()).toUri();
		responseHttpHeaders.setLocation(newDepartmentUri);
		return new ResponseEntity<>(department, responseHttpHeaders, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Modifies department associated with departmentId", response = Department.class)
	public ResponseEntity<Department> update(@Valid @RequestBody Department department, @PathVariable int id) {
		departmentHelper.verifyDeparmentExistence(departmentService, department.getId());
		department = departmentService.updateEmployee(department);
		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletes department associated with departmentId")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		departmentHelper.verifyDeparmentExistence(departmentService, id);
		departmentService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
