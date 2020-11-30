package com.example.smartnote.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.smartnote.Util.BackGroundThread;

import java.util.List;

public class Repo {

    private NoteDAO noteDAO;
    private LiveData<List<Notes>> getAllNormalNotes;
    private LiveData<List<Notes>> getAllPrivateNotes;

    public Repo(Application app){
        AppDatabase db = AppDatabase.getInstance(app);
        noteDAO = db.noteDAO();
        getAllNormalNotes = noteDAO.getAllNormalNote();
        getAllPrivateNotes = noteDAO.getAllPrivateNote();
    }

    // Insert
    public void insert(Notes notes){
        new BackGroundThread.InsertAsyncTask(noteDAO).execute(notes);
    }

    //Delete
    public void delete(Notes notes){
        new BackGroundThread.DeleteAsyncTask(noteDAO).execute(notes);
    }

    //Update
    public void update(Notes notes){
        new BackGroundThread.UpdateAsyncTask(noteDAO).execute(notes);
    }

    // getAllNotes
    public LiveData<List<Notes>> getAllPrivateNotes(){

        return getAllPrivateNotes;
    }

    // getAllNotes
    public LiveData<List<Notes>> getAllNormalNotes(){

        return getAllNormalNotes;
    }
}
