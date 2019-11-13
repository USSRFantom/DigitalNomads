package com.hfad.digitalnomads.dataBase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static NotesDatabase database;
    private LiveData<List<Notes>> notes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(getApplication());
        notes = database.notesDao().getAllNotes();
    }

    public  Notes getNotesById (int id){
        try {
            return new GetNotesTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      return null;
    }

    public void deleteAllNotes()
    {
    new DeleteNotesTask().execute();
    }

    public void insertNotes(Notes notes){
        new InsertTask().execute(notes);
    }

    public void deleteNotes(Notes notes)
    {
        new DeleteTask().execute(notes);
    }

    private static class DeleteTask extends AsyncTask<Notes, Void, Void>{

        @Override
        protected Void doInBackground(Notes... notes) {
            if (notes != null && notes.length > 0){
                database.notesDao().deleteNotes(notes[0]);
            }
            return null;
        }
    }

    public LiveData<List<Notes>> getNotes() {
        return notes;
    }

    private static class InsertTask extends AsyncTask<Notes, Void, Void>{

        @Override
        protected Void doInBackground(Notes... notes) {
            if (notes != null && notes.length > 0){
                database.notesDao().insertNotes(notes[0]);
            }
            return null;
        }
    }



    private static class DeleteNotesTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... integers) {
                database.notesDao().deleteAllNotes();

            return null;
        }
    }


    private static class GetNotesTask extends AsyncTask<Integer, Void, Notes>{

        @Override
        protected Notes doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0){
                return database.notesDao().getNotesById(integers[0]);
            }
            return null;
        }
    }

}
