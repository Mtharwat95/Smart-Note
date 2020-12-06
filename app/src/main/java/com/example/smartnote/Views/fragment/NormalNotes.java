package com.example.smartnote.Views.fragment;

import android.content.Intent;
import android.os.Bundle;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.smartnote.ViewModels.NewNoteViewModel;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.smartnote.Adapters.NoteAdapter;
import com.example.smartnote.Model.modelClasses.Notes;
import com.example.smartnote.R;
import com.example.smartnote.Views.activity.NewNote;
import com.example.smartnote.Util.Util;
import com.example.smartnote.databinding.FragmentNotesBinding;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NormalNotes extends Fragment implements NoteAdapter.OnItemClickListener {
    String TAG = "NormalNoteFragment";

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

        Disposable disposable =viewModel.getAllNormalNotes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Notes>>() {
                    @Override
                    public void accept(List<Notes> notes) throws Exception {
                        noteAdapter.setTasks(notes);
//                        noteAdapter.notifyDataSetChanged();
                    }
                });
        compositeDisposable.add(disposable);
/*        viewModel.getAllNormalNotes().observe(this,notes -> noteAdapter.setTasks(notes));
        viewModel.notesMutableLiveData.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                Log.d(TAG, "Tharwat :setupViewModel: ");
                noteAdapter.setTasks(notes);
                noteAdapter.notifyDataSetChanged();
            }
        });*/
/*        viewModel.getAllNormalNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> taskEntries) {
                Log.d(TAG, "Tharwat :setupViewModel: "+taskEntries.get(0).getTitle());
                noteAdapter.setTasks(taskEntries);
                noteAdapter.notifyDataSetChanged();

            }
        });*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }



    @Override
    public void onItemClick(Notes notes) {
        Log.d(TAG, "onItemClick: " + notes.getTitle());
        Intent intent = new Intent(requireContext(), NewNote.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SelectedNote",notes);
        intent.putExtras(bundle);
//        intent.putExtra(Constants.EXTRA_ID, notes.getId());
//        intent.putExtra(Constants.EXTRA_TITLE, notes.getTitle());
//        intent.putExtra(Constants.EXTRA_DATE, notes.getDate());
//        intent.putExtra(Constants.EXTRA_TXTNOTE, notes.getTextNote());
//        intent.putExtra(Constants.EXTRA_IMAGENOTE, notes.getImageNote());
//        intent.putExtra(Constants.EXTRA_NOTETYPE, notes.getNoteType());
//        intent.putExtra(Constants.EXTRA_NOTEPRIORITY, notes.getNotePriority());
        startActivity(intent);
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
/*                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.delete(notes);
//                        mDb.noteDAO().deleteNote(notes);
                        dialog.dismiss();
                    }
                });*/

            }
        });


    }
}

