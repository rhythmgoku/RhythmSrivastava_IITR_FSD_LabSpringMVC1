package com.greatlearning.employeemanagement.services;

import java.util.List;

import com.greatlearning.employeemanagement.entity.Employee;

public interface EmployeeService {

	List<Employee> getAllEmployees();

	Employee saveEmployee(Employee employee);

	Employee getEmployeeById(int id);

	Employee updateEmployee(Employee employee);

	void deleteEmployeeById(int id);
}
