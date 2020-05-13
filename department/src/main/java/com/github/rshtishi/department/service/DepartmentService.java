package com.github.rshtishi.department.service;

import java.util.List;
import com.github.rshtishi.department.entity.Department;

public interface DepartmentService {

	public List<Department> findAll();
	
	public Department findById(int id);
	
	public Department createEmployee(Department department);
	
	public Department updateEmployee(Department department);
	
	public void deleteEmployee(int id);

}
