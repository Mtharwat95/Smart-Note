package com.example.smartnote.Util;


import android.os.AsyncTask;

import com.example.smartnote.Model.Database.NoteDAO;
import com.example.smartnote.Model.modelClasses.Notes;

public class BackGroundThread {


    public static class InsertAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NoteDAO noteDAO;

        public InsertAsyncTask(NoteDAO note){
            noteDAO = note;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDAO.insertNote(notes[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NoteDAO noteDAO;

        public DeleteAsyncTask(NoteDAO note){
            noteDAO = note;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDAO.deleteNote(notes[0]);
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<Notes, Void, Void> {
        private NoteDAO noteDAO;

        public UpdateAsyncTask(NoteDAO note){
            noteDAO = note;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDAO.updateNote(notes[0]);
            return null;
        }
    }

}
