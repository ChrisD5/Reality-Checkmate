package com.example.mob_dev_portfolio.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM Activity")
    List<Activity> getAllActivities();

    @Insert
    void insertAll(Activity... activities);

    @Query("DELETE FROM Activity")
    void deleteAllActivities();

    @Delete
    void deleteActivity(Activity activity);
}
