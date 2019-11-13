package com.hfad.digitalnomads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hfad.digitalnomads.dataBase.MainViewModel;
import com.hfad.digitalnomads.dataBase.Notes;
import com.hfad.digitalnomads.utils.JSONUtils;
import com.hfad.digitalnomads.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this ).get(MainViewModel.class);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        recyclerViewNotes.setLayoutManager(new GridLayoutManager(this, 1));
        notesAdapter = new NotesAdapter();
        recyclerViewNotes.setAdapter(notesAdapter);






        notesAdapter.setOnPosterClickListener(new NotesAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Toast.makeText(MainActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });

        notesAdapter.setOnReachEndListener(new NotesAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "конец списка", Toast.LENGTH_SHORT).show();
            }
        });

        LiveData<List<Notes>> notesFromLiveData = viewModel.getNotes();
        notesFromLiveData.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                notesAdapter.setNotes(notes);
            }
        });


        downloadData(1);



    }

    private void downloadData(int page){
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(1);
        ArrayList<Notes> notes = JSONUtils.getNotesFromJSON(jsonObject);
        if (notes != null && !notes.isEmpty()){
            viewModel.deleteAllNotes();
            for (Notes notes1 : notes){
                viewModel.insertNotes(notes1);  //???????????
            }
        }
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



