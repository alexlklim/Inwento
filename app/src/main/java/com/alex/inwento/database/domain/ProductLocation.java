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
@Entity(tableName = Util.TABLE_NAME_LOCATION)
public class ProductLocation {

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("location")
    @ColumnInfo(name = "location")
    private String location;

    @SerializedName("active")
    @ColumnInfo(name = "active")
    private boolean active;

    @SerializedName("branch_id")
    @ColumnInfo(name = "branch_id")
    private int branchId;
}
