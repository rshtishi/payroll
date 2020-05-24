package com.github.rshtishi.department.helper;

import org.springframework.stereotype.Component;

import com.github.rshtishi.department.entity.Department;
import com.github.rshtishi.department.exception.ResourceNotFoundException;
import com.github.rshtishi.department.service.DepartmentService;

@Component
public class DepartmentHelper {

	public Department verifyDeparmentExistence(DepartmentService departmentService, int id) {
		Department department = departmentService.findById(id);
		if (department == null) {
			throw new ResourceNotFoundException("Department with id: " + id + " not found.");
		}
		return department;
	}

}
