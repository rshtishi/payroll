package com.github.rshtishi.department.thirdparty;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.github.rshtishi.department.service.EmployeeCountRedisService;

class EmployeeRestTemplateTest {

	@InjectMocks
	private EmployeeRestTemplate employeeRestTemplate;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private EmployeeCountRedisService employeeCountRedisService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCountEmployeesByDepartmentId_whenCacheIsNotEmpty() {
		// setup
		int departmentId = 1;
		long employeeNoExpected = 2L;
		when(employeeCountRedisService.findEmployeeCountFromCache(Mockito.anyInt())).thenReturn(employeeNoExpected);
		// execute
		long employeeNoActual = employeeRestTemplate.countEmployeesByDepartmentId(departmentId);
		// verify
		Assertions.assertEquals(employeeNoExpected, employeeNoActual);
	}

	@Test
	public void testCountEmployeesByDepartmentId_whenCacheIsEmpty() {
		int departmentId = 1;
		long employeeNoExpected = 2L;
		String uri = "http://employee/employees/{departmentId}/count";
		ResponseEntity<Long> responseExpected = ResponseEntity.ok(employeeNoExpected);
		when(employeeCountRedisService.findEmployeeCountFromCache(Mockito.anyInt())).thenReturn(-1L);
		when(restTemplate.exchange(uri, HttpMethod.GET, null, Long.class, departmentId))
				.thenReturn(responseExpected);
		// execute
		long employeeNoActual = employeeRestTemplate.countEmployeesByDepartmentId(departmentId);
		// verify
		Assertions.assertEquals(employeeNoExpected, employeeNoActual);

	}

}
