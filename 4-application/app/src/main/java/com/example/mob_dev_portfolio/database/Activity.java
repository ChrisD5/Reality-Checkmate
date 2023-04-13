package com.example.mob_dev_portfolio.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Activity {

    @PrimaryKey (autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "activity_name")
    private String activityName;

    @ColumnInfo(name = "difficulty")
    private String difficulty;


    @Override
    public String toString() {
        return "Activity{" +
                "uid=" + uid +
                ", activityName='" + activityName + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    public Activity(String activityName, String difficulty) {
        this.activityName = activityName;
        this.difficulty = difficulty;

    }





}
