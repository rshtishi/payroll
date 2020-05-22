package com.github.rshtishi.department.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<Department> findAll(Pageable pageable) {
		LOGGER.info("findAll method called");
		Page<Department> departmentPage = departmentRepository.findAll(pageable);
		departmentPage.getContent().stream().map(department -> addNoEmployee(department));
		return departmentPage;
	}

	private Department addNoEmployee(Department department) {
		LOGGER.info("addNOEmployee method called, department: " + department);
		int employeesNo = (int) employeeRestTemplate.countEmployeesByDepartmentId(department.getId());
		department.setNoOfEmployees(employeesNo);
		return department;
	}

	@Override
	public Department findById(int id) {
		LOGGER.info("findById called, id: " + id);
		Department department = null;
		Optional<Department> optionalDepartment = departmentRepository.findById(id);
		if (optionalDepartment.isPresent()) {
			department = optionalDepartment.get();
			department = addNoEmployee(department);
		}
		return department;
	}

	@Override
	public Department createEmployee(Department department) {
		LOGGER.info("createEmployee method called, department: " + department);
		return departmentRepository.save(department);
	}

	@Override
	public Department updateEmployee(Department department) {
		LOGGER.info("updateEmployee method called, department: " + department);
		return departmentRepository.save(department);
	}

	@Override
	public void deleteEmployee(int id) {
		LOGGER.info("deleteEmployee called, id: " + id);
		departmentRepository.deleteById(id);
	}

}
