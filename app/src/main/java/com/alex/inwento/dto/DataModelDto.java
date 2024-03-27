package com.alex.inwento.dto;

import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.Employee;

public class DataModelDto {

    private Employee[] employees;
    private Branch[] branches;

    public Employee[] getEmployees() {
        return employees;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }

    public Branch[] getBranches() {
        return branches;
    }

    public void setBranches(Branch[] branches) {
        this.branches = branches;
    }
}
