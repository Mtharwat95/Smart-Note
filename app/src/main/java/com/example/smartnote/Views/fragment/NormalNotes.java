package com.example.smartnote.Views.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.smartnote.ViewModels.NormalNoteVM;
import com.example.smartnote.Util.AppExecutors;
import com.example.smartnote.Util.Constants;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smartnote.Adapters.NoteAdapter;
import com.example.smartnote.Database.Notes;
import com.example.smartnote.R;
import com.example.smartnote.Views.activity.NewNote;
import com.example.smartnote.Util.Util;
import com.example.smartnote.databinding.FragmentNotesBinding;


import java.util.List;

public class NormalNotes extends Fragment implements NoteAdapter.OnItemClickListener {
    String TAG = "NormalNoteFragment";

    FragmentNotesBinding binding;
    NormalNoteVM viewModel;

    NoteAdapter noteAdapter;


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
        viewModel = ViewModelProviders.of(this).get(NormalNoteVM.class);
        viewModel.getNormalNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> taskEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                noteAdapter.setTasks(taskEntries);

            }
        });
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
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.delete(notes);
//                        mDb.noteDAO().deleteNote(notes);
                        dialog.dismiss();
                    }
                });

            }
        });


    }
}

