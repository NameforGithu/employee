package com.neusta.service;

import com.neusta.domain.Employee;
import com.neusta.domain.Framework;
import com.neusta.domain.ProgrammingLanguage;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals(employee.getIsFree(), getEmployee().getIsFree());
        assertEquals(employee.getProgrammingLanguages().size(), getEmployee().getProgrammingLanguages().size());
        assertEquals(employee.getFrameworks().size(), getEmployee().getFrameworks().size());
        assertEquals(employee.getEmail(), getEmployee().getEmail());
    }

    @Test
    void givenEmployeeId_whenReadSavedEmployeeById_thenFoundEmployeeCorrect() {
        when(mockEmployeeRepository.findById(7L)).thenReturn(Optional.of(getEmployee()));
        Employee employee = employeeService.findEmployeeById(7L);
        assertEquals(employee.getEmail(), getEmployee().getEmail());
    }

    @Test
    void givenEmployeeId_whenNotFoundId_thenResourceNoFoundException() {
        when(mockEmployeeRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(RuntimeException.class, ()-> employeeService.findEmployeeById(12L));
    }

    @Test
    void findAllEmployees() {
        when(mockEmployeeRepository.findAll()).thenReturn(getListOfEmployee());
        when(mockEmployeeMapper.convertTOListOfEmployeeDtoMapper(any())).thenReturn(employeeDtoList());
        assertEquals(getListOfEmployee().size(), 3);
    }
}
