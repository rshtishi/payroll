package com.github.rshtishi.department.service;


public interface EmployeeCountRedisService {
	
	public long findEmployeeCountFromCache(int departmentId);
	
	public void saveEmployeeCountInCache(int departmentId, long employeeCount);

}
