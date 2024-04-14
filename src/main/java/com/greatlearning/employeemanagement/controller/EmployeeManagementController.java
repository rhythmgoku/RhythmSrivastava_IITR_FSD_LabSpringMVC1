package com.greatlearning.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import com.greatlearning.employeemanagement.entity.Employee;
import com.greatlearning.employeemanagement.services.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/employees")
@Slf4j
public class EmployeeManagementController {

	@Autowired
	private EmployeeService employeeservice;

	@GetMapping(value = {"","/"})
	public String getAllEmployees(Model model) {
		List<Employee> employeesList = employeeservice.getAllEmployees();
		model.addAttribute("employees", employeesList);
		return "employees";

	}

	@GetMapping("/addnew")
	public String createEmployeeForm(Model model) {
		log.info("Create Employee Form Invoked");
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "create_employee";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeservice.saveEmployee(employee);
		return "redirect:/employees";
	}

	@GetMapping("/edit/{id}")
	public String editEmployeeForm(@PathVariable("id") int id, Model model) {
		Employee employee = employeeservice.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "edit_employee";
	}

	@GetMapping("/{id}")
	public String deleteEmployee(@PathVariable("id") String id) {
		employeeservice.deleteEmployeeById(Integer.parseInt(id));
		return "redirect:/employees";
	}

	@PostMapping("/{id}")
	public String updateEmployee(@PathVariable("id") String id, @ModelAttribute("employee") Employee updatedEmployee)
			throws Exception {
		Employee employee = employeeservice.getEmployeeById(Integer.parseInt(id));
		if (null != employee) {
			employee = new Employee(updatedEmployee);
			employee.setId(Integer.parseInt(id));
		} else {
			throw new Exception(
					"Employee with provided Employee Id does not exists in the database, Please Add the Employee before editing ");
		}
		employeeservice.updateEmployee(employee);
		return "redirect:/employees";
	}

	@GetMapping("/healthCheck")
	public String healthCheck() {
		return "UP";

	}

}
