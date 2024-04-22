package com.alex.inwento.database.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alex.inwento.util.Util;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = Util.TABLE_NAME_LOCATION)
public class Location {

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


    public Location() {
    }

    public Location(int id, String location, boolean active) {
        this.id = id;
        this.location = location;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", active=" + active +
                '}';
    }
}
