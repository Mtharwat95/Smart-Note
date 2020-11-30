package com.example.smartnote.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("SELECT * FROM notes ")
    LiveData<List<Notes>> getAllNote();

    @Query("SELECT * FROM notes WHERE notePriority = 'Normal' ")
    LiveData<List<Notes>> getAllNormalNote();

    @Query("SELECT * FROM notes WHERE notePriority = 'Private' ")
    LiveData<List<Notes>> getAllPrivateNote();

    @Insert
    void insertNote(Notes notes);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(Notes notes);

    @Delete
    void deleteNote(Notes notes);

    @Query("SELECT * FROM notes WHERE id = :id")
    LiveData<Notes> loadNoteById(int id);

}
