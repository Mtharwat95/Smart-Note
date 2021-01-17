package com.example.smartnote.Model.Repository;

import android.app.Application;

import com.example.smartnote.Model.Database.AppDatabase;
import com.example.smartnote.Model.Database.NoteDAO;
import com.example.smartnote.Model.modelClasses.Notes;

import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class Repo {

    private static final String TAG = "Repo";
    private final NoteDAO noteDAO;


    public Repo(Application app){
        AppDatabase db = AppDatabase.getInstance(app);
        noteDAO = db.noteDAO();
    }

    // Insert
    public void insert(Notes notes){
          noteDAO.insertNote(notes).subscribeOn(Schedulers.computation()).subscribe();
    }

    //Delete
    public void delete(Notes notes){
        noteDAO.deleteNote(notes).subscribeOn(Schedulers.computation()).subscribe();
    }

    //Delete
    public void deleteAll(){
        noteDAO.deleteAllNote().subscribeOn(Schedulers.computation()).subscribe();
    }

    //Update
    public void update(Notes notes){
        noteDAO.updateNote(notes).subscribeOn(Schedulers.computation()).subscribe();
    }

    // getAllNotes
    public Flowable<List<Notes>> getAllPrivateNotes(){
        return noteDAO.getAllPrivateNote();
    }

    // getAllNotes
    public Flowable<List<Notes>> getAllNormalNotes(){
        return noteDAO.getAllNormalNote();
    }

    public Flowable<Notes> getNoteById(String id){
        return  noteDAO.getNoteById(id);
    }
}
