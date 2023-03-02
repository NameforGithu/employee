package com.neusta.service;

import com.neusta.domain.Employee;
import com.neusta.rest.request.CapabilityRequest;
import com.neusta.rest.response.EmployeeDto;

import java.util.List;

public interface IEmployee {

    Employee createEmployee (EmployeeDto employeeDto);

    Employee findEmployeeById(long employee_id);

    List<EmployeeDto> findAllEmployees();

    void deleteEmployeeById(long employee_id);
    Employee updateEmployee(EmployeeDto employeeDto, long employee_id);
    List<EmployeeDto> findEmployeesByProgrammingLanguage(String programming_language, int amountOfExperience, String status);
    Employee addCapability(long employee_id, String programming_language, int amountOfExperience);
    List<EmployeeDto> filterEmployeesByFramework(String framework, int amountOfExperience, String status);
    Employee changeStatusOfEmployee(long employee_id, String filter);
    List<EmployeeDto> filterEmployeesByStatus(String filter);
    List<Employee> findEmployeesByCapability(CapabilityRequest capabilityRequest);
}
