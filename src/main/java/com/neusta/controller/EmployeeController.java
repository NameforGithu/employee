package com.neusta.controller;

import com.neusta.repo.EmployeeRepository;
import com.neusta.rest.response.EmployeeDto;
import com.neusta.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/createEmployee")
    ResponseEntity<Map<String,String>> createEmployee (@RequestBody EmployeeDto employeeDto){
        Map<String,String> outputInfo = new HashMap<>();
        Long employee_id = employeeService.createEmployee(employeeDto);
        log.info("Employee created with id " + employee_id);
        outputInfo.put("message", "Employee created with id " + employee_id);
        return new ResponseEntity<>(outputInfo, HttpStatus.CREATED);
    }


    @GetMapping("/getEmployee/{employee_id}")
    ResponseEntity <EmployeeDto> findEmployeeById(@PathVariable (value = "employee_id") long employee_id){
        EmployeeDto employeeDto = employeeService.findEmployeeById(employee_id);
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
}