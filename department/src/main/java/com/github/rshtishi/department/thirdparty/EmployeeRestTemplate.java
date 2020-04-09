package com.github.rshtishi.department.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class EmployeeRestTemplate {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "countEmployeesByDepartmentIdFallback")
	public long countEmployeesByDepartmentId(int departmentId) {
		ResponseEntity<Long> response = restTemplate.exchange("http://employee/employees/{departmentId}/count", 
				HttpMethod.GET, null, Long.class, departmentId);
		return response.getBody();
	}
	
	public long countEmployeesByDepartmentIdFallback(int departmentId) {
		return -1;
	}

}
