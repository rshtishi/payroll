package com.github.rshtishi.department.service;

import java.util.List;
import com.github.rshtishi.department.entity.Department;

public interface DepartmentService {

	public List<Department> findAll();
	
	public Department findById(int id);
	
	public void createEmployee(Department department);
	
	public void updateEmployee(Department department);
	
	public void deleteEmployee(int id);

}
