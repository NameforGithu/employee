package com.neusta.mapper;


import com.neusta.domain.Employee;
import com.neusta.rest.response.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Employee convertToEmployee(EmployeeDto employeeDto){
        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        newEmployee.setCreationTime(LocalDateTime.now());
        return newEmployee;
    }

    public EmployeeDto convertToEmployeeDto(Employee employee){
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public List<EmployeeDto> convertTOListOfEmployeeDtoMapper(List<Employee> listOfEmployees) {

        EmployeeDto employeeDto;

        List<EmployeeDto>  employeeDtoList = new ArrayList<>();
        for (Employee employee: listOfEmployees) {
            employeeDto = convertToEmployeeDto(employee);
            employeeDtoList.add(employeeDto);
        }
        return employeeDtoList;
    }
}
