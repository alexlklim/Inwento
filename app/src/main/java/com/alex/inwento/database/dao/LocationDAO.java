package com.alex.inwento.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alex.inwento.database.domain.Employee;
import com.alex.inwento.database.domain.Location;

import java.util.List;

@Dao
public interface LocationDAO {

    @Query("SELECT * FROM locations ORDER BY id DESC")
    List<Location> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Location> locations);

    @Query("DELETE FROM locations")
    void deleteAll();

}
