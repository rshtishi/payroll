package com.github.rshtishi.department.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.github.rshtishi.department.service.EmployeeCountRedisService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class EmployeeRestTemplate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestTemplate.class);
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private EmployeeCountRedisService employeeCountRedisService;
	
	@HystrixCommand(fallbackMethod = "countEmployeesByDepartmentIdFallback")
	public long countEmployeesByDepartmentId(int departmentId) {
		LOGGER.info("Called countEmployeesByDepartmentId, departmentId: "+departmentId);
		long employeeCount = employeeCountRedisService.findEmployeeCountFromCache(departmentId);
		LOGGER.info("employeeCount returned from Redis Cache: "+employeeCount);
		if(employeeCount>-1) {
			return employeeCount;
		}
		ResponseEntity<Long> response = restTemplate.exchange("http://employee/employees/{departmentId}/count", 
				HttpMethod.GET, null, Long.class, departmentId);
		employeeCount = response.getBody();
		LOGGER.info("employeeCount returned from employee service: "+employeeCount);
		if(employeeCount>-1) {
			employeeCountRedisService.saveEmployeeCountInCache(departmentId, employeeCount);
		}
		return response.getBody();
	}
	
	public long countEmployeesByDepartmentIdFallback(int departmentId) {
		return -1;
	}

}
