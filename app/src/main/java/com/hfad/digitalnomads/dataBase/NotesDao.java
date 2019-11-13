package com.hfad.digitalnomads.dataBase;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notes")
    LiveData<List<Notes>> getAllNotes();

    @Query("SELECT * FROM notes WHERE id == :notesID")
    Notes getNotesById(int notesID);

    @Query("DELETE FROM notes")
    void deleteAllNotes();

    @Insert
    void insertNotes(Notes notes);

    @Delete
    void  deleteNotes(Notes notes);
}
