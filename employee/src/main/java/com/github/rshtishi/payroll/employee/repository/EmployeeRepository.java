package com.github.rshtishi.payroll.employee.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.rshtishi.payroll.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	
	public Page<Employee> findAll(Pageable pageable);
	
	public long countByDepartmentId(int departmentId);
	
	public Employee save(Employee employee);

}
