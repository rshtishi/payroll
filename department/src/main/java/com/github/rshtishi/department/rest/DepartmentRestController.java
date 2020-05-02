package com.github.rshtishi.department.rest;

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

import com.github.rshtishi.department.entity.Department;
import com.github.rshtishi.department.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentRestController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping
	public List<Department> findAll() {
		return departmentService.findAll();
	}
	
	@GetMapping("/{id}")
	public Department findById(@PathVariable int id) {
		return departmentService.findById(id);
	}
	
	@PostMapping
	public void create(@RequestBody Department department) {
		departmentService.createEmployee(department);
	}
	
	@PutMapping
	public void update(@RequestBody Department department) {
		departmentService.updateEmployee(department);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		departmentService.deleteEmployee(id);
	}
	
}
