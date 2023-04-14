package com.example.mob_dev_portfolio.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Activity.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ActivityDao activityDao();
}
