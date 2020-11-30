package com.example.smartnote.ViewModels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartnote.Database.AppDatabase;

public class NewNoteVMF extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;


    public NewNoteVMF(AppDatabase database) {
        mDb = database;

    }

//    @Override
//    public <T extends ViewModel> T create(Class<T> modelClass) {
//        return (T) new NewNoteViewModel(mDb);
//    }
}
