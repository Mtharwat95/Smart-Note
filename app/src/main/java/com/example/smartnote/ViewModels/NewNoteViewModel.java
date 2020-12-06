package com.example.smartnote.ViewModels;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.smartnote.Model.modelClasses.Notes;
import com.example.smartnote.Model.Repository.Repo;
import java.util.List;
import io.reactivex.Flowable;

public class NewNoteViewModel extends AndroidViewModel {

    private static final String TAG = "NewNoteViewModel";
    private final Repo repo;


    public NewNoteViewModel(@NonNull Application application) {
        super(application);
        repo = new Repo(application);
    }

    @SuppressLint("CheckResult")
    public Flowable<List<Notes>> getAllNormalNotes() {

    return repo.getAllNormalNotes();
    }

    @SuppressLint("CheckResult")
    public Flowable<List<Notes>> getAllPrivateNotes() {
        return repo.getAllPrivateNotes();
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
