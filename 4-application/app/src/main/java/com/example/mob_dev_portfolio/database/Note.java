package com.example.mob_dev_portfolio.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note  {

    @PrimaryKey(autoGenerate = true)
    private int nid;

    @ColumnInfo(name = "note_title")
    String title;

    @ColumnInfo(name = "note_description")
    String description;

    @ColumnInfo(name = "note_created_time")
    long createdTime;

    @Override
    public String toString() {
        return "Note{" +
                "nid=" + nid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
    public int getNid() {
        return nid;
    }

    public void setNid(int uid) {
        this.nid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public Note( String title, String description, long createdTime) {
        this.title = title;
        this.description = description;
        this.createdTime = createdTime;
    }
}
