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
@Entity(tableName = Util.TABLE_NAME_BRANCH)
public class Branch {

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("branch")
    @ColumnInfo(name = "branch")
    private String branch;

    @SerializedName("active")
    @ColumnInfo(name = "active")
    private boolean active;
}
