package com.neusta.service;

import com.neusta.config.State;
import com.neusta.domain.Employee;
import com.neusta.domain.ProgrammingLanguage;
import com.neusta.mapper.EmployeeMapper;
import com.neusta.repo.EmployeeRepository;
import com.neusta.rest.response.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public Long createEmployee(EmployeeDto empolyeeDto) {
        Employee employee = employeeRepository.save(employeeMapper.convertToEmployee(empolyeeDto));
        return employee.getId();
    }

    @Override
    public EmployeeDto findEmployeeById(long employee_id) {
        Employee employee = employeeRepository.findById(employee_id).orElseThrow(() -> new RuntimeException("Employee not found"));
        return employeeMapper.convertToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> findAllEmployees() {
        return employeeMapper.convertTOListOfEmployeeMapper(employeeRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteEmployeeById(long employee_id) {
        Employee employee = employeeMapper.convertToEmployee(findEmployeeById(employee_id));
        if (employee != null) {
            employeeRepository.deleteById(employee_id);
        }
    }

    @Override
    public void updateEmployee(EmployeeDto employeeDto, long employee_id) {
        Employee employee = employeeMapper.convertToEmployee(findEmployeeById(employee_id));
        if (employee != null) {
            employee.setFirstname(employeeDto.getFirstname());
            employee.setLastname(employeeDto.getLastname());
            employee.setEmail(employeeDto.getEmail());
            employee.setUsername(employeeDto.getUsername());
            employee.setMobile(employeeDto.getMobile());
            employeeRepository.save(employee);
        }
    }

    @Override
    public List<EmployeeDto> findEmployeesByProgrammingLanguage(String programming_language, int amountOfExperience, String status) {
        List<EmployeeDto> employeeDtos = findAllEmployees();
        return employeeDtos.stream()
                .filter(Employee ->
                        Employee.getProgrammingLanguages().stream()
                                .anyMatch(ProgrammingLanguage ->
                                        ProgrammingLanguage.getProgrammingLanguage().equalsIgnoreCase(programming_language)))
                .filter(Employee -> Employee.getProgrammingLanguages().stream()
                        .anyMatch(ProgrammingLanguage -> ProgrammingLanguage.getWorkExperience() >= amountOfExperience))
                .collect(Collectors.toList());
    }

    @Override
    public void addCapability(long employee_id, String programming_language, int amountOfExperience) {
        Employee employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.getProgrammingLanguages().add(new ProgrammingLanguage(programming_language, amountOfExperience));
        employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDto> filterEmployeesByFramework(String framework, int amountOfExperience, String status) {
        List<EmployeeDto> employeeDtos = findAllEmployees();
        boolean isFree;
        isFree = status.equalsIgnoreCase("free");

        return employeeDtos.stream()
                .filter(Employee ->
                        Employee.getFrameworks().stream()
                                .anyMatch(Framework -> Framework.getFramework().equalsIgnoreCase(framework)))
                .filter(Employee ->
                        Employee.getFrameworks().stream()
                                .anyMatch(Framework -> Framework.getWorkExperience() >= amountOfExperience))

                .collect(Collectors.toList());
    }
    @Override
    public void changeStatusOfEmployee(long employee_id, String status) {
        Employee employee = employeeMapper.convertToEmployee(findEmployeeById(employee_id));
        State userState = State.valueOf(status.toUpperCase());
        if (userState == State.FREE)
            employee.setIsFree(true);
        if (userState == State.BUSY)
            employee.setIsFree(false);
        employeeRepository.save(employee);
    }
}