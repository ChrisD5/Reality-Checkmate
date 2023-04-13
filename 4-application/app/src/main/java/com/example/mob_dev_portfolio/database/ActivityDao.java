package com.example.mob_dev_portfolio.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM Activity")
    List<Activity> getAllActivities();

    @Query("SELECT * FROM Activity WHERE activity_name = :name LIMIT 1")
    Activity getByName(String name);

    @Insert
    void insertAll(Activity... activities);

    @Delete
    void delete(Activity activity);

    @Query("SELECT * FROM Activity ORDER BY uid DESC LIMIT 1")
    Activity getMostRecentlyAdded();

    @Query("DELETE FROM Activity")
    void deleteAllActivities();
}
