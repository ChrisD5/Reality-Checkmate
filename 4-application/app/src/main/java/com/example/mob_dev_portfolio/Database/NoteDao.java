package com.example.mob_dev_portfolio.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getNotes();

    @Query("SELECT * FROM Note")
    List<Note> NotesList();

    @Query("SELECT * FROM Note WHERE nid = :id")
    Note getNoteById(int id);

    @Insert
    void insertAll(Note... notes);

    @Query("DELETE FROM Note")
    void deleteAllNotes();

    @Delete
    void delete(Note note);
}
