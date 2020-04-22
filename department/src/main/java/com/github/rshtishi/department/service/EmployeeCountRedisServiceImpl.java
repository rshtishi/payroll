package com.github.rshtishi.department.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rshtishi.department.repository.EmployeeCountRedisRepository;

@Service
public class EmployeeCountRedisServiceImpl implements EmployeeCountRedisService {

	@Autowired
	private EmployeeCountRedisRepository employeeCountRedisRepository;
	
	@Override
	public long findEmployeeCountFromCache(int departmentId) {
		try {
			return employeeCountRedisRepository.findEmployeeCount(departmentId);
		} catch(Exception exception) {
			return -1;
		}
	}

	@Override
	public void saveEmployeeCountInCache(int departmentId, long employeeCount) {
		try {
			employeeCountRedisRepository.saveEmployeeCount(departmentId, employeeCount);
		} catch(Exception exception) {}
	}

}
