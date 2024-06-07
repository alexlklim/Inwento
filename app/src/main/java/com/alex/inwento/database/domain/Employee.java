package com.alex.inwento.database.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alex.inwento.util.Util;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String firstName;
    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
    private String lastName;

    @SerializedName("is_active")
    @ColumnInfo(name = "is_active")
    private boolean isActive;


}
