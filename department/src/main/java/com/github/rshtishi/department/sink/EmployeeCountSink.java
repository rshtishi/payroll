package com.github.rshtishi.department.sink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import com.github.rshtishi.department.entity.EmployeeActionEnum;
import com.github.rshtishi.department.entity.EmployeeCountChangeModel;
import com.github.rshtishi.department.service.EmployeeCountRedisService;

@Component
public class EmployeeCountSink {
	
	@Autowired
	private EmployeeCountRedisService employeeCountRedisService;
	
	@StreamListener(Sink.INPUT)
	public void employeeChangeSink(EmployeeCountChangeModel change) {
		long employeeCount = employeeCountRedisService.findEmployeeCountFromCache(change.getDepartmentId());
		if(change.getAction().equals(EmployeeActionEnum.CREATE.action())) {
			employeeCount++;
			employeeCountRedisService.saveEmployeeCountInCache(change.getDepartmentId(), employeeCount);
		} else if (change.getAction().equals(EmployeeActionEnum.DELETE.action())) {
			employeeCount--;
			employeeCountRedisService.saveEmployeeCountInCache(change.getDepartmentId(), employeeCount);		
		}
	}

}
