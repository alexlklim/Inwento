package com.alex.inwento.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alex.inwento.database.domain.Branch;

import java.util.List;

@Dao
public interface BranchDAO {

    @Query("SELECT * FROM branches ORDER BY id DESC")
    List<Branch> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Branch> branches);

    @Query("DELETE FROM branches")
    void deleteAll();


}
