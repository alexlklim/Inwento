package com.alex.inwento.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alex.inwento.database.domain.ProductLocation;
import com.alex.inwento.util.Util;

import java.util.List;

@Dao
public interface LocationDAO {

    @Query("SELECT * FROM " + Util.TABLE_NAME_LOCATION + " ORDER BY id DESC")
    List<ProductLocation> getAll();

    @Query("SELECT * FROM locations " +
            "WHERE branch_id = :branchId " +
            "ORDER BY id DESC")
    List<ProductLocation> getAllByBranchId(int branchId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ProductLocation> productLocations);

    @Query("SELECT * FROM " + Util.TABLE_NAME_LOCATION + " WHERE location = :locationName")
    ProductLocation getLocationByName(String locationName);

    @Query("DELETE FROM  " + Util.TABLE_NAME_LOCATION)
    void deleteAll();

}
