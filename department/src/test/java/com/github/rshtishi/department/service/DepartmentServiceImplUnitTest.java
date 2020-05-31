package com.github.rshtishi.department.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.github.rshtishi.department.entity.Department;
import com.github.rshtishi.department.repository.DepartmentRepository;
import com.github.rshtishi.department.thirdparty.EmployeeRestTemplate;

class DepartmentServiceImplUnitTest {

	@InjectMocks
	private DepartmentServiceImpl departmentService;
	@Mock
	private DepartmentRepository departmentRepository;
	@Mock
	private EmployeeRestTemplate employeeRestTemplate;

	private Department department;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		department = new Department();
		department.setId(1);
		department.setName("Finance");
	}

	@Test
	public void testFindAll() {
		// setup
		List<Department> departmentList = new ArrayList<>();
		departmentList.add(department);
		Page<Department> departmentPageExpected = new PageImpl<Department>(departmentList);
		when(departmentRepository.findAll(Mockito.any())).thenReturn(departmentPageExpected);
		long noEmployeeExpected = 2L;
		when(employeeRestTemplate.countEmployeesByDepartmentId(Mockito.anyInt())).thenReturn(noEmployeeExpected);
		// execute
		Page<Department> departmentPageActual = departmentService.findAll(PageRequest.of(0, 10));
		// verify
		Assertions.assertEquals(departmentPageExpected.getSize(), departmentPageActual.getSize());
		Assertions.assertEquals(noEmployeeExpected, departmentPageExpected.getContent().get(0).getNoOfEmployees());
	}
	
	@Test
	public void testFindById() {
		//setup
		when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(department));
		long noEmployeeExpected = 2L;
		when(employeeRestTemplate.countEmployeesByDepartmentId(Mockito.anyInt())).thenReturn(noEmployeeExpected);
		//execute
		Department departmentActual = departmentService.findById(1);
		//verify
		Assertions.assertEquals(department.getId(), departmentActual.getId());
		Assertions.assertEquals(department.getName(), departmentActual.getName());
		Assertions.assertEquals(noEmployeeExpected, departmentActual.getNoOfEmployees());
	}
	
	@Test
	public void testCreate() {
		//setup
		when(departmentRepository.save(Mockito.any())).thenReturn(department);
		//execute
		Department departmentActual = departmentService.createEmployee(department);
		//verify
		Assertions.assertEquals(department.getId(), departmentActual.getId());
		Assertions.assertEquals(department.getName(), departmentActual.getName());
	}
	
	@Test
	public void testUpdate() {
		//setup
		when(departmentRepository.save(Mockito.any())).thenReturn(department);
		//execute
		Department departmentActual = departmentService.updateEmployee(department);
		//verify
		Assertions.assertEquals(department.getId(), departmentActual.getId());
		Assertions.assertEquals(department.getName(), departmentActual.getName());
	}

	@Test
	public void testDeleteEmployee() {
		// setup
		int id = 1;
		doNothing().when(departmentRepository).deleteById(Mockito.anyInt());
		// execute
		departmentService.deleteEmployee(id);
		// verify
		verify(departmentRepository, times(1)).deleteById(id);
	}

}
