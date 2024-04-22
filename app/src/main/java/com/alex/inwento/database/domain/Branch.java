package com.alex.inwento.database.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alex.inwento.util.Util;
import com.google.gson.annotations.SerializedName;


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

    public Branch() {
    }

    public Branch(int id, String branch, boolean active) {
        this.id = id;
        this.branch = branch;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", branch='" + branch + '\'' +
                ", active=" + active +
                '}';
    }
}
