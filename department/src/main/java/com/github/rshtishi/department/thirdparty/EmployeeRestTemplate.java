package com.github.rshtishi.department.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.github.rshtishi.department.entity.EmployeeCountChangeModel;
import com.github.rshtishi.department.service.EmployeeCountRedisService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class EmployeeRestTemplate {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private EmployeeCountRedisService employeeCountRedisService;
	
	@HystrixCommand(fallbackMethod = "countEmployeesByDepartmentIdFallback")
	public long countEmployeesByDepartmentId(int departmentId) {
		
		long employeeCount = employeeCountRedisService.findEmployeeCountFromCache(departmentId);
		if(employeeCount>-1) {
			return employeeCount;
		}
		ResponseEntity<Long> response = restTemplate.exchange("http://employee/employees/{departmentId}/count", 
				HttpMethod.GET, null, Long.class, departmentId);
		employeeCount = response.getBody();
		if(employeeCount>-1) {
			employeeCountRedisService.saveEmployeeCountInCache(departmentId, employeeCount);
		}
		return response.getBody();
	}
	
	public long countEmployeesByDepartmentIdFallback(int departmentId) {
		return -1;
	}

}
