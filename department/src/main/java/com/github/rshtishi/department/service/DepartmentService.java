package com.github.rshtishi.department.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.rshtishi.department.entity.Department;

public interface DepartmentService {

	public Page<Department> findAll(Pageable pageable);
	
	public Department findById(int id);
	
	public Department createDepartment(Department department);
	
	public Department updateDepartment(Department department);
	
	public void deleteDepartment(int id);

}
