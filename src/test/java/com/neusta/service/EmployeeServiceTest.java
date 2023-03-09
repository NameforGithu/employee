package com.neusta.service;

import com.neusta.domain.Employee;
import com.neusta.domain.Framework;
import com.neusta.domain.ProgrammingLanguage;
import com.neusta.exception.ResourceNotFoundException;
import com.neusta.mapper.EmployeeMapper;
import com.neusta.repo.EmployeeRepository;
import com.neusta.rest.response.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository mockEmployeeRepository;
    @Mock
    private EmployeeMapper mockEmployeeMapper;

    public Employee generateEmployee (long id,
                                      String firstname,
                                      String lastname,
                                      String username,
                                      String email,
                                      String mobile,
                                      List<ProgrammingLanguage> programmingLanguages,
                                      List<Framework> frameworks,
                                      LocalDateTime creationTime,
                                      boolean status
    ) {
        return Employee.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .email(email)
                .mobile(mobile)
                .programmingLanguages(programmingLanguages)
                .frameworks(frameworks)
                .creationTime(creationTime)
                .isFree(status)
                .build();
    }

    public List<EmployeeDto> employeeDtoList (){
        List<EmployeeDto> employees = new ArrayList<>();
        employees.add(getEmployeeDto());
        employees.add(getEmployeeDto());
        employees.add(getEmployeeDto());
        employees.get(1).setIsFree(false);
        return employees;
    }

    public List<Employee> getListOfEmployee(){
        List<Employee> employees = new ArrayList<>();
        employees.add(getEmployee());
        employees.add(getEmployee());
        employees.add(getEmployee());
        return  employees;
    }

    public Employee getEmployee(){
        List<ProgrammingLanguage> programmingLanguages = new ArrayList<>();
        programmingLanguages.add(new ProgrammingLanguage("Java", 7));
        List<Framework> frameworks = new ArrayList<>();
        frameworks.add(new Framework("Angular", 5));
        return generateEmployee(7, "emre", "emreB", "developerE",
                "aaa@yahoo.com", "01523333",programmingLanguages,frameworks, LocalDateTime.now(), false);
    }

    public EmployeeDto getEmployeeDto(){
        EmployeeDto employeeDto =  new EmployeeDto();
        employeeDto.setFirstname("John");
        employeeDto.setLastname("Doe");
        employeeDto.setUsername("J.Doe");
        employeeDto.setEmail("hallo@email.com");
        employeeDto.setMobile("0152000000");
        employeeDto.setProgrammingLanguages(Arrays.asList(new ProgrammingLanguage("java",6)));
        employeeDto.setFrameworks(Arrays.asList(new Framework("angular", 5)));
        employeeDto.setIsFree(true);
        return employeeDto;
    }

    @Test
    void createEmployee() {
        when(mockEmployeeRepository.save(any())).thenReturn(getEmployee());
        Employee employee = employeeService.createEmployee(getEmployeeDto());
        assertEquals(employee.getFirstname(), getEmployee().getFirstname());
        assertEquals(employee.getLastname(), getEmployee().getLastname());
        assertEquals(employee.getUsername(), getEmployee().getUsername());
        assertEquals(employee.getBirthday(), getEmployee().getBirthday());
        assertEquals(employee.getMobile(), getEmployee().getMobile());
        assertEquals(employee.getEmail(), getEmployee().getEmail());
        assertEquals(employee.getIsFree(), getEmployee().getIsFree());
        assertEquals(employee.getProgrammingLanguages().size(), getEmployee().getProgrammingLanguages().size());
        assertEquals(employee.getFrameworks().size(), getEmployee().getFrameworks().size());
    }

    @Test
    void givenEmployeeId_whenReadSavedEmployeeById_thenFoundEmployeeCorrect() {
        when(mockEmployeeRepository.findById(7L)).thenReturn(Optional.of(getEmployee()));
        Employee employee = employeeService.findEmployeeById(7L);
        assertEquals(employee.getEmail(), getEmployee().getEmail());
    }

    @Test
    void givenEmployeeId_whenNotFoundId_thenResourceNotFoundException() {
        when(mockEmployeeRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class, ()-> employeeService.findEmployeeById(12L));
    }

    @Test
    void findAllEmployees() {
        when(mockEmployeeRepository.findAll()).thenReturn(getListOfEmployee());
        when(mockEmployeeMapper.convertTOListOfEmployeeDtoMapper(any())).thenReturn(employeeDtoList());
        assertEquals(getListOfEmployee().size(), 3);
    }

    @Test
    void givenEmployeeId_whenDeleteEmployeeById_IdFound_thenEmployeeDeleteCorrectly() {
        when(mockEmployeeRepository.findById(any())).thenReturn(Optional.ofNullable(getEmployee()));
        Employee employee = getEmployee();
        when(mockEmployeeRepository.save(any())).thenReturn(employee);
        employeeService.deleteEmployeeById(7L);
        assertFalse(mockEmployeeRepository.existsById(9L));
    }

    @Test
    void givenEmployeeId_whenDeleteEmployeeById_IdNotFound_thenEmployeeNoDelete(){
        when(mockEmployeeRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class, ()-> employeeService.deleteEmployeeById(9L));
    }

    @Test
    void updateEmployee() {
        when(mockEmployeeRepository.findById(any())).thenReturn(Optional.ofNullable(getEmployee()));
        when(mockEmployeeRepository.save(any())).thenReturn(getEmployee());
        EmployeeDto employeeDto = getEmployeeDto();
        employeeDto.setFirstname("New firstname");
        Employee employee = employeeService.updateEmployee(employeeDto, getEmployee().getId());
        assertEquals(employee.getFirstname(), employeeDto.getFirstname());
    }

    @Test
    void findEmployeesByProgrammingLanguage() {
        when(mockEmployeeRepository.findAll()).thenReturn(getListOfEmployee());
        when(mockEmployeeMapper.convertTOListOfEmployeeDtoMapper(any())).thenReturn(employeeDtoList());
        List<EmployeeDto> employeeByFilter = employeeService.findEmployeesByProgrammingLanguage(
                "java",5, "free");
        assertEquals(employeeByFilter.size(), 2);
    }

    @Test
    void addCapabilityAsProgrammingLanguage() {
        when(mockEmployeeRepository.findById(any())).thenReturn(Optional.ofNullable(getEmployee()));
        Employee employee = employeeService.addCapabilityAsProgrammingLanguage(getEmployee().getId(), "C#", 4);
        assertEquals(employee.getProgrammingLanguages().size(), 2);
    }

    @Test
    void addCapabilityAsFramework() {
        when(mockEmployeeRepository.findById(any())).thenReturn(Optional.ofNullable(getEmployee()));
        Employee employee = employeeService.addCapabilityAsFramework(getEmployee().getId(), "react", 4);
        assertEquals(employee.getFrameworks().size(), 2);
    }

    @Test
    void filterEmployeesByFramework() {
        when(mockEmployeeRepository.findAll()).thenReturn(getListOfEmployee());
        when(mockEmployeeMapper.convertTOListOfEmployeeDtoMapper(any())).thenReturn(employeeDtoList());
        List<EmployeeDto> employees = employeeService.filterEmployeesByFramework("angular", 4, "free");
        assertEquals(employees.size(), 2);
    }

    @Test
    void changeStatusOfEmployee() {
        when(mockEmployeeRepository.findById(any())).thenReturn(Optional.ofNullable(getEmployee()));
        Employee employee = employeeService.changeStatusOfEmployee(getEmployee().getId(), "free");
        assertEquals(employee.getIsFree(), true);
    }

    @Test
    void filterEmployeesByStatus() {
        when(mockEmployeeRepository.findAll()).thenReturn(getListOfEmployee());
        when(mockEmployeeMapper.convertTOListOfEmployeeDtoMapper(any())).thenReturn(employeeDtoList());
        List<EmployeeDto> employeeDtos = employeeService.filterEmployeesByStatus("free");
        assertEquals(employeeDtos.size(), 2);
    }
}
