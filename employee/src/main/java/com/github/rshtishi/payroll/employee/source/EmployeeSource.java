package com.github.rshtishi.payroll.employee.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.github.rshtishi.payroll.employee.entity.EmployeeCountChangeModel;

@Component
public class EmployeeSource {

	@Autowired
	private Source source;

	public void publishEmployeeCountChange(int departmentId, long employeeCount) {
		EmployeeCountChangeModel change = new EmployeeCountChangeModel(departmentId, employeeCount,
				EmployeeCountChangeModel.class.getTypeName());
		source.output().send(MessageBuilder.withPayload(change).build());
	}

}
