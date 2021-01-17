package com.example.smartnote.Model.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smartnote.Model.modelClasses.Notes;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface NoteDAO {


    @Query("SELECT * FROM notes WHERE notePriority = 'Normal' ")
    Flowable<List<Notes>> getAllNormalNote();

    @Query("SELECT * FROM notes WHERE notePriority = 'Private' ")
    Flowable<List<Notes>> getAllPrivateNote();

    @Insert
    Completable insertNote(Notes notes);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable updateNote(Notes notes);

    @Delete
    Completable deleteNote(Notes notes);

    @Query("DELETE FROM notes")
    Completable deleteAllNote();

    @Query("SELECT * FROM notes WHERE id=:id")
    Flowable<Notes> getNoteById(String id);

}
