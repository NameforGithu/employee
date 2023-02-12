package com.neusta.service;

import com.neusta.rest.response.EmployeeDto;

import java.util.List;

public interface IEmployee {

    Long createEmployee (EmployeeDto empolyeeDto);

    EmployeeDto findEmployeeById(long employee_id);

    List<EmployeeDto> findAllEmployees();

    void deleteEmployeeById(long employee_id);
    void updateEmployee(EmployeeDto employeeDto, long employee_id);
}
