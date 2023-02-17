package com.neusta.service;

import com.neusta.rest.response.EmployeeDto;

import java.util.List;

public interface IEmployee {

    Long createEmployee (EmployeeDto empolyeeDto);

    EmployeeDto findEmployeeById(long employee_id);

    List<EmployeeDto> findAllEmployees();

    void deleteEmployeeById(long employee_id);
    void updateEmployee(EmployeeDto employeeDto, long employee_id);
    List<EmployeeDto> findEmployeesByProgrammingLanguage(String programming_language, int amountOfExperience, String status);
    void addCapability(long employee_id, String programming_language, int amountOfExperience);
}
