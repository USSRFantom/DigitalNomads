package com.hfad.digitalnomads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hfad.digitalnomads.dataBase.Notes;
import com.hfad.digitalnomads.utils.JSONUtils;
import com.hfad.digitalnomads.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        recyclerViewNotes.setLayoutManager(new GridLayoutManager(this, 1));
        notesAdapter = new NotesAdapter();
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(1);
        ArrayList<Notes> notes = JSONUtils.getNotesFromJSON(jsonObject);
        notesAdapter.setNotes(notes);
        recyclerViewNotes.setAdapter(notesAdapter);



    }
}













/*
1.
Проверка правильности составления запроса
String url = NetworkUtils.buildURL(1).toString();
Log.i("MyResult", url);


2.
проверка метода получения JSON из сети
JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(3);
if (jsonObject == null){
Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
}else{
Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show();
}


3.
проверяем полученые данные (заголовки
JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(2);
ArrayList<Notes> notes = JSONUtils.getNotesFromJSON(jsonObject);
StringBuilder builder = new StringBuilder();
for (Notes note : notes){
builder.append(note.getTitle()).append("\n");
}
Log.i("MyResult", builder.toString());





*/



