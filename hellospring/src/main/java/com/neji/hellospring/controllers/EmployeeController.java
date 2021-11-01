package com.neji.hellospring.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import com.neji.hellospring.models.dto.request.EmployeeRequest;
import com.neji.hellospring.models.dto.response.EmployeeResponse;
import com.neji.hellospring.models.entity.Employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
 
    private static List<Employee> _employees = new ArrayList<Employee>();

    public EmployeeController() {
        _employees.add(new Employee(1, "Nejimon"));
        _employees.add(new Employee(2, "Sunitha"));
    }

    @GetMapping 
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(){
        var response = new ArrayList<EmployeeResponse>();

        _employees.stream().forEach(e -> 
        {
            response.add(new EmployeeResponse(e.getEmployeeId(), e.getEmployeeName()));
        });

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}") 
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("id") int id){    
        try {
            var employee = _employees.stream()
            .filter(e -> e.getEmployeeId() == id)
            .findFirst().get();

            return ResponseEntity.ok(new EmployeeResponse(employee.getEmployeeId(), employee.getEmployeeName()));
            
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PostMapping 
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeRequest employeeRequest){
        int nextEmployeeId = 0;
        
        try {
            nextEmployeeId = _employees.stream().sorted(Comparator.comparing(Employee::getEmployeeId).reversed()).findFirst().get().getEmployeeId() + 1;
        } catch (NoSuchElementException e) {
            nextEmployeeId = 1;
        }
        
        _employees.add(new Employee(nextEmployeeId, employeeRequest.getEmployeeName()));

        return ResponseEntity.ok("Employee created");
    }

    @PutMapping("/{id}") 
    public ResponseEntity<String> updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeRequest employeeRequest){        
        try {
            var employee = _employees.stream().filter(e -> e.getEmployeeId() == id).findFirst().get();
            employee.setEmployeeName(employeeRequest.getEmployeeName());
            return ResponseEntity.ok("Employee updated");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        } 
    }


    @DeleteMapping("/{id}") 
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id){        
        try {
            var employee = _employees.stream().filter(e -> e.getEmployeeId() == id).findFirst().get();
            _employees.remove(employee);
            return ResponseEntity.ok("Employee deleted");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        } 
    }


    

}
