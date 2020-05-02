package com.github.rshtishi.department.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rshtishi.department.entity.Department;
import com.github.rshtishi.department.repository.DepartmentRepository;
import com.github.rshtishi.department.thirdparty.EmployeeRestTemplate;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private EmployeeRestTemplate employeeRestTemplate;

	@Override
	public List<Department> findAll() {
		LOGGER.info("findAll method called");
		List<Department> departmentList =  departmentRepository.findAll()
				.stream().map(department -> addNoEmployee(department)).collect(Collectors.toList());
		return departmentRepository.findAll();
	}

	private Department addNoEmployee(Department department) {
		LOGGER.info("addNOEmployee method called, department: "+department);
		int employeesNo = (int) employeeRestTemplate.countEmployeesByDepartmentId(department.getId());
		department.setNoOfEmployees(employeesNo);
		return department;
	}
	
	@Override
	public Department findById(int id) {
		LOGGER.info("findById called, id: "+id);
		return departmentRepository.findById(id).get();
	}
	
	@Override
	public void createEmployee(Department department) {
		LOGGER.info("createEmployee method called, department: "+department);
		departmentRepository.save(department);
	}
	
	@Override
	public void updateEmployee(Department department) {
		LOGGER.info("updateEmployee method called, department: "+department);
		departmentRepository.save(department);
	}
	
	@Override
	public void deleteEmployee(int id) {
		LOGGER.info("deleteEmployee called, id: "+id);
		departmentRepository.deleteById(id);
	}

}
