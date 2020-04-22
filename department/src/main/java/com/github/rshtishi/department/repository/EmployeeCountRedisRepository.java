package com.github.rshtishi.department.repository;

public interface EmployeeCountRedisRepository {
	
	void saveEmployeeCount(int departmentId, long employeeCount);
	
	void deleteEmployeeCount(int dpeartmentId);
	
	long findEmployeeCount(int departmentId);

}
