package com.github.rshtishi.department.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.github.rshtishi.department.exception.ResourceNotFoundException;
import com.github.rshtishi.department.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentRestController {

	@Autowired
	private DepartmentService departmentService;

	protected Department verifyDeparmentExistence(int id) {
		Department department = departmentService.findById(id);
		if (department != null) {
			throw new ResourceNotFoundException("Department with id: " + id + " not found.");
		}
		return department;
	}

	@GetMapping
	public ResponseEntity<List<Department>> findAll() {
		List<Department> departmentList = departmentService.findAll();
		return new ResponseEntity<>(departmentList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Department> findById(@PathVariable int id) {
		Department department = verifyDeparmentExistence(id);
		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Department> create(@Valid @RequestBody Department department) {
		department = departmentService.createEmployee(department);
		HttpHeaders responseHttpHeaders = new HttpHeaders();
		URI newDepartmentUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(department.getId()).toUri();
		responseHttpHeaders.setLocation(newDepartmentUri);
		return new ResponseEntity<>(department, responseHttpHeaders, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Department> update(@Valid @RequestBody Department department) {
		verifyDeparmentExistence(department.getId());
		department = departmentService.updateEmployee(department);
		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		verifyDeparmentExistence(id);
		departmentService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
