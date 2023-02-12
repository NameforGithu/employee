package com.neusta.service;

import com.neusta.domain.Employee;
import com.neusta.mapper.EmployeeMapper;
import com.neusta.repo.EmployeeRepository;
import com.neusta.rest.response.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService implements IEmployee {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Long createEmployee (EmployeeDto empolyeeDto){
        Employee employee = employeeRepository.save(employeeMapper.convertToEmployee(empolyeeDto));
        return employee.getId();
    }

    @Override
    public EmployeeDto findEmployeeById(long employee_id) {
        Employee employee = employeeRepository.findById(employee_id).orElseThrow(()-> new RuntimeException("Employee not found"));
        return employeeMapper.convertToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> findAllEmployees (){
        return employeeMapper.convertTOListOfEmployeeMapper(employeeRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteEmployeeById(long employee_id) {
        Employee employee = employeeMapper.convertToEmployee(findEmployeeById(employee_id));
        if (employee != null){
            employeeRepository.deleteById(employee_id);
        }
    }

    @Override
    public void updateEmployee(EmployeeDto employeeDto, long employee_id) {
        Employee employee = employeeMapper.convertToEmployee(findEmployeeById(employee_id));
        if (employee!=null){
            employee.setFirstname(employeeDto.getFirstname());
            employee.setLastname(employeeDto.getLastname());
            employee.setEmail(employeeDto.getEmail());
            employee.setUsername(employeeDto.getUsername());
            employee.setMobile(employeeDto.getMobile());
            employeeRepository.save(employee);
        }
    }
}