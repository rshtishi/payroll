package com.github.rshtishi.payroll.employee.service;

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

import com.github.rshtishi.payroll.employee.entity.Employee;
import com.github.rshtishi.payroll.employee.repository.EmployeeRepository;
import com.github.rshtishi.payroll.employee.source.EmployeeSource;

class EmployeeServiceImpUnitTest {

	@InjectMocks
	private EmployeeServiceImp employeeService;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private EmployeeSource employeeSource;

	private Employee employee;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		employee = new Employee();
		employee.setId(1);
		employee.setFirstname("Rando");
		employee.setDepartmentId(1);
	}

	@Test
	public void testFindAll() {
		// setup
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(employee);
		Page<Employee> employeePage = new PageImpl<>(employeeList);
		when(employeeRepository.findAll(Mockito.any())).thenReturn(employeePage);
		// execute
		Page<Employee> employeePageActual = employeeService.findAll(PageRequest.of(0, 10));
		// verify
		Assertions.assertEquals(employeePage.getSize(), employeePageActual.getSize());
		Assertions.assertEquals(employee.getFirstname(), employeePageActual.getContent().get(0).getFirstname());
	}

	@Test
	public void testFindById() {
		//setup
		int id = 1;
		when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(employee));
		//execute
		Employee employeeActual = employeeService.findById(id);
		//verify
		Assertions.assertEquals( employee.getId(), employeeActual.getId());
		Assertions.assertEquals( employee.getFirstname(), employeeActual.getFirstname());
	}
	
	@Test
	public void testCountByDepartmentId() {
		//setup
		long noOfEmployees=10L;
		when(employeeRepository.countByDepartmentId(Mockito.anyInt())).thenReturn(noOfEmployees);
		//execute
		long actualNoOfEmployees = employeeService.countByDepartmentId(employee.getDepartmentId());
		//verify
		Assertions.assertEquals(actualNoOfEmployees,noOfEmployees);
	}
	
	@Test
	public void testCreateEmployee() {
		//setup
		when(employeeRepository.save(Mockito.any())).thenReturn(employee);
		doNothing().when(employeeSource).publishEmployeeCountChange(Mockito.anyInt(), Mockito.any());
		//execute
		Employee actualEmployee = employeeService.createEmployee(employee);
		//verify
		Assertions.assertEquals(employee.getId(), actualEmployee.getId());
		Assertions.assertEquals(employee.getFirstname(), actualEmployee.getFirstname());
	}
	
	@Test
	public void testUpdateEmployee() {
		//setup
		when(employeeRepository.save(Mockito.any())).thenReturn(employee);
		//execute
		Employee actualEmployee = employeeService.updateEmployee(employee);
		//verify
		Assertions.assertEquals(employee.getId(), actualEmployee.getId());
		Assertions.assertEquals(employee.getFirstname(), actualEmployee.getFirstname());
	}
	
	@Test
	public void testDeleteEmployee() {
		//setup
		when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(employee));
		doNothing().when(employeeRepository).deleteById(Mockito.anyInt());
		doNothing().when(employeeSource).publishEmployeeCountChange(Mockito.anyInt(), Mockito.any());
		//execute
		employeeService.deleteEmployee(employee.getId());
		//setup
		verify(employeeRepository,times(1)).deleteById(employee.getId());
	}

}
