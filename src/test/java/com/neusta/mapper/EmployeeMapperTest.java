package com.neusta.mapper;

import com.neusta.domain.Employee;
import com.neusta.domain.ForeignLanguages;
import com.neusta.domain.Framework;
import com.neusta.domain.ProgrammingLanguage;
import com.neusta.rest.response.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperTest {
    private EmployeeMapper mapper;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        this.mapper = new EmployeeMapper(modelMapper);
    }

    public Employee getEmployee() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setLastname("Doe");
        employee.setUsername("J.Doe");
        employee.setEmail("hello@yahoo.com");
        employee.setMobile("01527777777");
        employee.setBirthday(LocalDate.of(1985, 3, 27));
        List<ProgrammingLanguage> programmingLanguages = new ArrayList<>();
        programmingLanguages.add(new ProgrammingLanguage("java", 5));
        employee.setProgrammingLanguages(programmingLanguages);
        List<Framework> frameworks = new ArrayList<>();
        frameworks.add(new Framework("angular", 4));
        employee.setFrameworks(frameworks);
        List<ForeignLanguages> foreignLanguages = new ArrayList<>();
        foreignLanguages.add(new ForeignLanguages(3L, "English", "good"));
        employee.setForeignLanguages(foreignLanguages);
        employee.setIsFree(true);
        employee.setCreativity(3);
        employee.setTroubleshooting(4);
        employee.setTeamwork(3);
        employee.setFlexibility(3);
        return employee;
    }

    public EmployeeDto getEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstname("John");
        employeeDto.setLastname("Doe");
        employeeDto.setUsername("J.Doe");
        employeeDto.setEmail("hello@yahoo.com");
        employeeDto.setMobile("01527777777");
        List<ProgrammingLanguage> programmingLanguages = new ArrayList<>();
        programmingLanguages.add(new ProgrammingLanguage("java", 5));
        employeeDto.setProgrammingLanguages(programmingLanguages);
        List<Framework> frameworks = new ArrayList<>();
        frameworks.add(new Framework("angular", 4));
        employeeDto.setFrameworks(frameworks);
        List<ForeignLanguages> foreignLanguages = new ArrayList<>();
        foreignLanguages.add(new ForeignLanguages(3L, "English", "good"));
        employeeDto.setForeignLanguages(foreignLanguages);
        employeeDto.setIsFree(true);
        employeeDto.setCreativity(3);
        employeeDto.setTroubleshooting(4);
        employeeDto.setTeamwork(3);
        employeeDto.setFlexibility(3);
        return employeeDto;
    }

    public List <Employee> listOfEmployee(){
        List<Employee> employees = new ArrayList<>();
        employees.add(getEmployee());
        employees.add(getEmployee());
        employees.add(getEmployee());
        employees.add(getEmployee());
        return employees;
    }

    @Test
    void convertToEmployee() {
        EmployeeDto employeeDto = getEmployeeDto();
        Employee employee = getEmployee();
        assertEquals(employee.getFirstname(), employeeDto.getFirstname());
        assertEquals(employee.getEmail(), employeeDto.getEmail());
        assertEquals(employee.getFirstname(), employeeDto.getFirstname());
        assertEquals(employee.getIsFree(), employeeDto.getIsFree());
        assertEquals(employee.getForeignLanguages().size(), employeeDto.getForeignLanguages().size());
        assertEquals(employee.getProgrammingLanguages().size(), employeeDto.getProgrammingLanguages().size());
        assertEquals(employee.getFrameworks().size(), employeeDto.getFrameworks().size());
    }

    @Test
    void convertToEmployeeDto() {
        EmployeeDto employeeDto = getEmployeeDto();
        Employee employee = getEmployee();
        assertEquals(employeeDto.getIsFree(), employee.getIsFree());
        assertEquals(employeeDto.getForeignLanguages().size(), employee.getForeignLanguages().size());
        assertEquals(employeeDto.getProgrammingLanguages().size(), employee.getProgrammingLanguages().size());
        assertEquals(employeeDto.getFrameworks().size(), employee.getFrameworks().size());
        assertEquals(employeeDto.getMobile(), employee.getMobile());
    }

    @Test
    void convertTOListOfEmployeeDtoMapper() {
        List <Employee> employees = listOfEmployee();
        List <EmployeeDto> employeeDtos = mapper.convertTOListOfEmployeeDtoMapper(employees);
        assertEquals(employeeDtos.size(),employees.size());
    }
}
