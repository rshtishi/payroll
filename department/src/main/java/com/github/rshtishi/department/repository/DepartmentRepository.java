package com.github.rshtishi.department.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.rshtishi.department.entity.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

	public List<Department> findAll();

}
