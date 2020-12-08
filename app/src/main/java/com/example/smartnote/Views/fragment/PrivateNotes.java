package com.example.smartnote.Views.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.smartnote.Adapters.NoteAdapter;
import com.example.smartnote.Model.modelClasses.Notes;
import com.example.smartnote.R;
import com.example.smartnote.Util.Util;
import com.example.smartnote.ViewModels.NewNoteViewModel;
import com.example.smartnote.Views.activity.NewNote;
import com.example.smartnote.databinding.FragmentNotesBinding;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class PrivateNotes extends Fragment implements NoteAdapter.OnItemClickListener{
    String TAG = "PrivateNoteFragment";

    FragmentNotesBinding binding;
    NewNoteViewModel viewModel;

    NoteAdapter noteAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false);


        noteAdapter = new NoteAdapter(requireContext(), this);
        binding.normalNoteRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.normalNoteRv.setAdapter(noteAdapter);


        setupViewModel();

        return binding.getRoot();
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(NewNoteViewModel.class);
     /*   viewModel.getPrivateNote().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> taskEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                noteAdapter.setTasks(taskEntries);

            }
        });*/

        Disposable disposable =viewModel.getAllPrivateNotes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Notes>>() {
                    @Override
                    public void accept(List<Notes> notes) throws Exception {
                        noteAdapter.setTasks(notes);
//                        noteAdapter.notifyDataSetChanged();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void onItemClick(Notes notes) {
        MaterialDialog dialog;
        dialog = new MaterialDialog.Builder(requireContext())
                .customView(R.layout.check_password, false)
                .show();
        View view = dialog.getCustomView();
        assert view != null;
        androidx.constraintlayout.utils.widget.ImageFilterView close = view.findViewById(R.id.checkPasswordClose);
        com.google.android.material.textfield.TextInputEditText password = view.findViewById(R.id.checkPassword);
        Button ok = view.findViewById(R.id.checkPasswordOk);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPassword = Util.getFromSharedPref("Password",requireContext());
                assert userPassword != null;
                if (userPassword.equals(Objects.requireNonNull(password.getText()).toString())){
                    Log.d(TAG, "onItemClick: " + notes.getTitle());
                    Intent intent = new Intent(requireContext(), NewNote.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SelectedNote",notes);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    dialog.dismiss();
                }else {
                    password.requestFocus();
                    password.setError("Incorrect Password");
                }
            }
        });


    }

    @Override
    public void onLongPress(Notes notes) {
        Log.d(TAG, "onLongPress: ");
        MaterialDialog dialog = Util.showConfirmationDialog(requireContext());

        View view =dialog.getView();
        com.google.android.material.textview.MaterialTextView title = view.findViewById(R.id.confirmDialogTitle);
        Button no = view.findViewById(R.id.confirmNo);
        Button yes = view.findViewById(R.id.confirmYes);
        title.setText("Are You Sure You Want To Delete ?");

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.delete(notes);
                dialog.dismiss();

            }
        });
    }
}