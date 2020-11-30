package com.example.smartnote.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartnote.Database.AppDatabase;
import com.example.smartnote.Database.Notes;
import com.example.smartnote.Database.Repo;

import java.util.List;

public class NewNoteViewModel extends AndroidViewModel {

    private Repo repo;
    private LiveData<List<Notes>> notes;


    public NewNoteViewModel(@NonNull Application application) {
        super(application);
        repo = new Repo(application);
        notes =repo.getAllNormalNotes();
    }

    public LiveData<List<Notes>> getAllNormalNotes()
    {
        return notes;
    }

    public void insert(Notes notes){
        repo.insert(notes);
    }

    public void update(Notes notes){
        repo.update(notes);
    }

    public void delete(Notes note)
    {
        repo.delete(note);
    }


}
