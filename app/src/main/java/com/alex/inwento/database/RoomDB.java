package com.alex.inwento.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alex.inwento.database.dao.BranchDAO;
import com.alex.inwento.database.dao.EmployeeDAO;
import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.Employee;
import com.alex.inwento.util.Util;

@Database(entities = {Branch.class, Employee.class}, version = Util.DATABASE_VERSION, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;
    private static final String DATABASE_NAME = Util.DATABASE_NAME;

    public synchronized static RoomDB getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract BranchDAO branchDAO();
    public abstract EmployeeDAO employeeDAO();


}