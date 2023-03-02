package com.neusta.service;

import com.neusta.config.State;
import com.neusta.domain.Employee;
import com.neusta.domain.ProgrammingLanguage;
import com.neusta.mapper.EmployeeMapper;
import com.neusta.repo.EmployeeRepository;
import com.neusta.rest.request.CapabilityRequest;
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
    public Employee createEmployee (EmployeeDto employeeDto){
        Employee employee = employeeRepository.save(employeeMapper.convertToEmployee(employeeDto));
        return employee;
    }

    @Override
    public Employee findEmployeeById(long employee_id) {
        Employee employee = employeeRepository.findById(employee_id).orElseThrow(()-> new RuntimeException("Employee not found"));
        return employee;
    }

    @Override
    public List<EmployeeDto> findAllEmployees (){
        return employeeMapper.convertTOListOfEmployeeDtoMapper(employeeRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteEmployeeById(long employee_id) {
        Employee employee = findEmployeeById(employee_id);
        if (employee != null) {
            employeeRepository.deleteById(employee_id);
        }
    }

    @Override
    public Employee updateEmployee(EmployeeDto employeeDto, long employee_id) {
        Employee employee = findEmployeeById(employee_id);
        if (employee != null) {
            employee.setFirstname(employeeDto.getFirstname());
            employee.setLastname(employeeDto.getLastname());
            employee.setEmail(employeeDto.getEmail());
            employee.setUsername(employeeDto.getUsername());
            employee.setMobile(employeeDto.getMobile());
            employee.setCreativity(employeeDto.getCreativity());
            employee.setFlexibility(employeeDto.getFlexibility());
            //employee.setBirthday(employeeDto.getBirthday());
            employee.setTeamwork(employeeDto.getTeamwork());
            employee.setTroubleshooting(employeeDto.getTroubleshooting());
            employee.setForeignLanguages(employeeDto.getForeignLanguages());
            employeeRepository.save(employee);
        }
        return employee;
    }

    @Override
    public List<EmployeeDto> findEmployeesByProgrammingLanguage(String programming_language, int amountOfExperience, String status) {
        List<EmployeeDto> employeeDtos = findAllEmployees();
        boolean isFree;
        isFree = status.equalsIgnoreCase("free");
        return employeeDtos.stream()
                .filter(Employee ->
                        Employee.getProgrammingLanguages().stream()
                                .anyMatch(ProgrammingLanguage ->
                                        ProgrammingLanguage.getProgrammingLanguage().equalsIgnoreCase(programming_language)))
                .filter(Employee -> Employee.getProgrammingLanguages().stream()
                        .anyMatch(ProgrammingLanguage -> ProgrammingLanguage.getWorkExperience() >= amountOfExperience))
                .filter(Employee -> Employee.getIsFree() == isFree)
                .collect(Collectors.toList());
    }

    @Override
    public Employee addCapability(long employee_id, String programming_language, int amountOfExperience) {
        Employee employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.getProgrammingLanguages().add(new ProgrammingLanguage(programming_language, amountOfExperience));
        employeeRepository.save(employee);
        return employee;
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
                .filter(Employee ->
                        Employee.getIsFree() == isFree)
                .collect(Collectors.toList());
    }
    @Override
    public Employee changeStatusOfEmployee(long employee_id, String status) {
        Employee employee = employeeRepository.findById(employee_id).orElseThrow(()-> new RuntimeException("Not Found"));
        State userState = State.valueOf(status.toUpperCase());
        if (userState == State.FREE)
            employee.setIsFree(true);
        if (userState == State.BUSY)
            employee.setIsFree(false);
        employeeRepository.save(employee);
        return employee;
    }
    @Override
    public List<EmployeeDto> filterEmployeesByStatus(String filter) {
        List<EmployeeDto> employees = findAllEmployees();
        State userState = State.valueOf(filter.toUpperCase());

        if(employees != null && !employees.isEmpty()){
            if (userState == State.FREE){
                return employees.stream()
                        .filter(EmployeeDto::getIsFree)
                        .collect(Collectors.toList());
            }
            if (userState == State.BUSY){
                return employees.stream()
                        .filter(employeeDto -> !employeeDto.getIsFree())
                        .collect(Collectors.toList());
            }
        }
        return null;
    }
    @Override
    public List<Employee> findEmployeesByCapability(CapabilityRequest capabilityRequest) {
        List <Employee> employees = employeeRepository.findAll();
        List <Employee> employeeList =
                employees.stream()
                        .filter(Employee -> Employee.getFlexibility()>= capabilityRequest.getFlexibility())
                        .filter(Employee -> Employee.getTroubleshooting() >= capabilityRequest.getTroubleshooting())
                        .filter(Employee -> Employee.getTeamwork() >= capabilityRequest.getTeamwork())
                        .filter(Employee -> Employee.getCreativity() >= capabilityRequest.getCreativity())
                        .collect(Collectors.toList());
        return employeeList;
    }
}