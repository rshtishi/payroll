package com.github.rshtishi.department.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.rshtishi.department.repository.EmployeeCountRedisRepository;
import brave.Span;
import brave.Tracing;

@Service
public class EmployeeCountRedisServiceImpl implements EmployeeCountRedisService {

	@Autowired
	private EmployeeCountRedisRepository employeeCountRedisRepository;
	@Autowired
	private Tracing tracing;

	@Override
	public long findEmployeeCountFromCache(int departmentId) {
		Span span = tracing.tracer().nextSpan().name("ReadEmployeeCountFromCache")
				.tag("peer.service", "redis")
				.start();
		try {
			return employeeCountRedisRepository.findEmployeeCount(departmentId);
		} catch (Exception exception) {
			return -1;
		} finally {
			span.finish();
		}
	}

	@Override
	public void saveEmployeeCountInCache(int departmentId, long employeeCount) {
		Span span = tracing.tracer().nextSpan().name("SaveEmployeeCountInCache");
		try {
			employeeCountRedisRepository.saveEmployeeCount(departmentId, employeeCount);
		} catch (Exception exception) {
		} finally {
			span.finish();
		}
	}

}
