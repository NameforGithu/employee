package com.neusta.mapper;


import com.neusta.domain.Employee;
import com.neusta.repo.EmployeeRepository;
import com.neusta.rest.response.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapper {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeMapper(ModelMapper modelMapper,
                          EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    public Employee convertToEmployee(EmployeeDto employeeDto){
        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        return newEmployee;
    }

    public EmployeeDto convertToEmployeeDto(Employee employee){
        EmployeeDto newEmployeeDto = modelMapper.map(employee, EmployeeDto.class);
        return newEmployeeDto;
    }


    public List<EmployeeDto> convertTOListOfEmployeeMapper (List<Employee> listOfEmployees){

        EmployeeDto employeeDto;

        List<EmployeeDto>  employeeDtoList = new ArrayList<>();
        for (Employee employee: listOfEmployees) {
            employeeDto = convertToEmployeeDto(employee);
            employeeDtoList.add(employeeDto);
        }
        return employeeDtoList;
    }
}
