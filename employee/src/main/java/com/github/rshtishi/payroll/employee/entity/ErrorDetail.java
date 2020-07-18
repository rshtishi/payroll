package com.github.rshtishi.payroll.employee.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ErrorDetail {
	
	private String title;
	private int status;
	private String detail;
	private long timeStamp;
	private String developerMessage;
	private Map<String, List<ValidationError>> errors = new HashMap<>();
}
