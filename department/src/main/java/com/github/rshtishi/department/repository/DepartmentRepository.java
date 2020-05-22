package com.github.rshtishi.department.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.rshtishi.department.entity.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

	public Page<Department> findAll(Pageable pageable);

}
