package com.github.rshtishi.department.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rshtishi.department.entity.Department;
import com.github.rshtishi.department.repository.DepartmentRepository;
import com.github.rshtishi.department.thirdparty.EmployeeRestTemplate;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private EmployeeRestTemplate employeeRestTemplate;

	@Override
	public List<Department> findAll() {
		List<Department> departmentList =  departmentRepository.findAll()
				.stream().map(department -> addNoEmployee(department)).collect(Collectors.toList());
		return departmentRepository.findAll();
	}

	private Department addNoEmployee(Department department) {
		int employeesNo = (int) employeeRestTemplate.countEmployeesByDepartmentId(department.getId());
		department.setNoOfEmployees(employeesNo);
		return department;
	}

}
