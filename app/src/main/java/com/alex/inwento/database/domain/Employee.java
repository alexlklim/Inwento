package com.alex.inwento.database.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alex.inwento.util.Util;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@Entity(tableName = Util.TABLE_NAME_EMPLOYEE)
public class Employee {
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;
    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    private String first_name;
    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
