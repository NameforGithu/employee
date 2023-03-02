package com.neusta.controller;

import com.neusta.domain.Employee;
import com.neusta.mapper.EmployeeMapper;
import com.neusta.repo.EmployeeRepository;
import com.neusta.rest.response.EmployeeDto;
import com.neusta.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping("/createEmployee")
    ResponseEntity<Map<String,String>> createEmployee (@RequestBody EmployeeDto employeeDto){
        Map<String,String> outputInfo = new HashMap<>();
        Employee employee = employeeService.createEmployee(employeeDto);
        log.info("Employee created with id " + employee);
        outputInfo.put("message", "Employee created with id " + employee);
        return new ResponseEntity<>(outputInfo, HttpStatus.CREATED);
    }


    @GetMapping("/getEmployee/{employee_id}")
    ResponseEntity <EmployeeDto> findEmployeeById(@PathVariable (value = "employee_id") long employee_id){
        EmployeeDto employeeDto = employeeMapper.convertToEmployeeDto(employeeService.findEmployeeById(employee_id));
        log.info("Employee found with id " + employee_id);
        return new ResponseEntity<>(employeeDto, HttpStatus.FOUND);
    }


    @GetMapping("/getAllEmployee")
    ResponseEntity<List<EmployeeDto>> giveAllEmployees(){
       List<EmployeeDto> listOfEmployees =  employeeService.findAllEmployees();
       return  new ResponseEntity<>(listOfEmployees,HttpStatus.FOUND);
    }


    @DeleteMapping("/deleteEmployee/{employee_id}")
    ResponseEntity<Map<String,String>> deleteEmployeeById(@PathVariable(value = "employee_id") long employee_id){
        Map<String,String> outputInfo = new HashMap<>();
        employeeService.deleteEmployeeById(employee_id);
        log.info("Employee deleted with id: " + employee_id);
        outputInfo.put("message","Employee deleted with id: " + employee_id);
        return new ResponseEntity<>(outputInfo,HttpStatus.OK);
    }


    @PutMapping("/updateEmployee/{employee_id}")
    ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                               @PathVariable(value = "employee_id") long employee_id){
        Map<String,String> outputInfo = new HashMap<>();
        employeeService.updateEmployee(employeeDto, employee_id);
        log.info("Employee updated with id: " + employee_id);
        outputInfo.put("message","Employee updated with id: " + employee_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getEmployeeByProgrammingLanguage")
    ResponseEntity <List<EmployeeDto>>  filterEmployeesByProgrammingLanguage (@Param("programming_language") String programming_language,
                                                                              @Param("amountOfExperience") int amountOfExperience,
                                                                              @Param("status") String status) {
        log.info("Employees found with programming language: " + programming_language +" and experience " + amountOfExperience);
        List<EmployeeDto>  employeeDtoList = employeeService.findEmployeesByProgrammingLanguage(programming_language, amountOfExperience, status);
        return new ResponseEntity<>(employeeDtoList,HttpStatus.FOUND);
    }

    @PostMapping("/addProgrammingLanguageToEmployee/{employee_id}")
    ResponseEntity<Map<String,String>> addProgrammingLanguageToEmployee (@PathVariable(value = "employee_id") long employee_id,
                                                                         @Param("programming_language") String programming_language,
                                                                         @Param("amountOfExperience") int amountOfExperience ) {
        Map<String,String> outputInfo = new HashMap<>();
        employeeService.addCapability(employee_id, programming_language, amountOfExperience);
        log.info("Programming language added to employee with id: " + employee_id);
        outputInfo.put("message", "Programming language added to employee with id: " + employee_id);
        return  new ResponseEntity<>(outputInfo, HttpStatus.CREATED);
    }

    @GetMapping("/getEmployeesByFramework")
    ResponseEntity<List<EmployeeDto>> filterEmployeesByFramework (@Param("framework") String framework,
                                                                  @Param("amountOfExperience") int amountOfExperience,
                                                                  @Param("status") String status) {
        List<EmployeeDto> employeeDtoList = employeeService.filterEmployeesByFramework(framework, amountOfExperience, status);
        return  new ResponseEntity<>(employeeDtoList,HttpStatus.FOUND);
    }
    @PutMapping("/changeStatusOfEmployee/{employee_id}")
    ResponseEntity<Map<String,String>> changeStatusOfEmployee (@PathVariable (value = "employee_id") long employee_id,
                                                               @Param("status") String status) {
        Map<String, String> outputInfo = new HashMap<>();
        employeeService.changeStatusOfEmployee(employee_id,status);
        log.info("Status of employee with id " + employee_id + " changes to : " + status);
        outputInfo.put("message", "Status of employee with id " + employee_id + " changes to : " + status);
        return new ResponseEntity<>(outputInfo, HttpStatus.OK);
    }

    @GetMapping("/filterEmployeesByStatus/{filter}")
    ResponseEntity <List<EmployeeDto>> filterEmployeesByStatus(@PathVariable(value = "filter") String filter){
        List<EmployeeDto> employeeDtos = employeeService.filterEmployeesByStatus(filter);
        log.info("Employee found with status: " + filter);
        return  new ResponseEntity<>(employeeDtos, HttpStatus.FOUND);
    }
}