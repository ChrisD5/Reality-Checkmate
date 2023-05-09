package com.example.mob_dev_portfolio.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 5)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}