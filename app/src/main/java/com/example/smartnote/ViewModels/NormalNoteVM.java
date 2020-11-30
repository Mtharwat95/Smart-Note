package com.example.smartnote.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.smartnote.Database.AppDatabase;
import com.example.smartnote.Database.Notes;
import com.example.smartnote.Database.Repo;

import java.util.List;
import java.util.Objects;

public class NormalNoteVM extends AndroidViewModel {

    private static final String TAG = "NormalNoteVM.class";


    private final LiveData<List<Notes>> normalNote;
    private final LiveData<List<Notes>> privateNote;
    Repo repo;

    public NormalNoteVM(Application application) {
        super(application);
        repo = new Repo(application);
        normalNote = repo.getAllNormalNotes();
        privateNote = repo.getAllPrivateNotes();
//        Log.d(TAG, "AllNotesSize:"+ Objects.requireNonNull(normalNote.getValue()).size() );

    }

    public LiveData<List<Notes>> getNormalNotes() {
        return normalNote;
    }

    public LiveData<List<Notes>> getPrivateNote() {
        return privateNote;
    }

    public void delete(Notes note)
    {
        repo.delete(note);
    }

}
