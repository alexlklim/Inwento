package com.alex.inwento.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.Employee;
import com.alex.inwento.util.Util;

import java.util.List;

@Dao
public interface EmployeeDAO {

    @Query("SELECT * FROM employees ORDER BY id DESC")
    List<Employee> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Employee> branches);


    @Query("SELECT id FROM " + Util.TABLE_NAME_EMPLOYEE + " WHERE first_name || ' ' || last_name = :fullName")
    int getEmployeeIdByFullName(String fullName);


    @Query("DELETE FROM branches")
    void deleteAll();

}
