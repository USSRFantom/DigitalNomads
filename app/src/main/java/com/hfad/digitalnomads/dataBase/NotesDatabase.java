package com.hfad.digitalnomads.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Notes.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    private static final String DB_NAME = "notes.db";
    private static NotesDatabase database;
    private static final Object LOCK = new Object();

    public static NotesDatabase getInstance(Context context){
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract NotesDao notesDao();
}
