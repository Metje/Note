package com.example.architectureexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository (Application application) {
        MainDatabase database = MainDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        new InsertNoteAsyncTasc(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTasc(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTasc(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTasc(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTasc extends AsyncTask <Note, Void, Void> {

        private NoteDao noteDao;

        private InsertNoteAsyncTasc (NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground (Note... notes){
            noteDao.insert(notes[0]);
            return null;
        }

    }

    private static class UpdateNoteAsyncTasc extends AsyncTask <Note, Void, Void> {

        private NoteDao noteDao;

        private UpdateNoteAsyncTasc (NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground (Note... notes){
            noteDao.update(notes[0]);
            return null;
        }

    }

    private static class DeleteNoteAsyncTasc extends AsyncTask <Note, Void, Void> {

        private NoteDao noteDao;

        private DeleteNoteAsyncTasc (NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground (Note... notes){
            noteDao.delete(notes[0]);
            return null;
        }

    }

    private static class DeleteAllNotesAsyncTasc extends AsyncTask <Void, Void, Void> {

        private NoteDao noteDao;

        private DeleteAllNotesAsyncTasc (NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground (Void... voids){
            noteDao.deleteAllNotes();
            return null;
        }

    }

}
