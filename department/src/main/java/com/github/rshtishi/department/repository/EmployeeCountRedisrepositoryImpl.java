package com.github.rshtishi.department.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeCountRedisrepositoryImpl implements EmployeeCountRedisRepository {
	
	private static final String HASH_NAME="EmployeeCount";
	
	private RedisTemplate  redisTemplate;
	private HashOperations<String, Integer, Long> hashOperations;
	
	@Autowired
	public EmployeeCountRedisrepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate =redisTemplate;
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void saveEmployeeCount(int departmentId, long employeeCount) {
		hashOperations.put(HASH_NAME, departmentId, employeeCount);
	}

	@Override
	public void deleteEmployeeCount(int departmentId) {
		hashOperations.delete(HASH_NAME, departmentId);
	}

	@Override
	public long findEmployeeCount(int departmentId) {
		return hashOperations.get(HASH_NAME, departmentId);
	}

}
