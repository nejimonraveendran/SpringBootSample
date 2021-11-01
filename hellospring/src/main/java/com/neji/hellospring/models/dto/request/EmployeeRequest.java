package com.neji.hellospring.models.dto.request;

public class EmployeeRequest {
    private String employeeName;

    public EmployeeRequest(String employeeName) {
        this.employeeName = employeeName;
    }

    public EmployeeRequest() {
        super();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }



}
