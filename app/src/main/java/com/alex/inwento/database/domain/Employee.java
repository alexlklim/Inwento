package com.alex.inwento.database.domain;

import java.util.List;

public class Employee {

    private int id;
    private String email;
    private String first_name;
    private String last_name;

    public Employee() {
    }

    public Employee(int id, String email, String first_name, String last_name) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public static Employee getEmployeeByName(String fullName, List<Employee> employeeList) {
        for (Employee employee : employeeList) {
            if ((employee.getFirst_name() + " " + employee.getLast_name()).equals(fullName)) {
                return employee;
            }
        }
        return null;
    }
}
