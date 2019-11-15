package com.hfad.digitalnomads;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hfad.digitalnomads.dataBase.MainViewModel;
import com.hfad.digitalnomads.dataBase.Notes;
import com.hfad.digitalnomads.utils.JSONUtils;
import com.hfad.digitalnomads.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {


    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;
    private ProgressBar progressBarLoading;
    private MainViewModel viewModel;

    private static int LOADER_ID = 133;
    private LoaderManager loaderManager;
    private static int page = 1;
    private static boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderManager = LoaderManager.getInstance(this);
        viewModel = ViewModelProviders.of(this ).get(MainViewModel.class);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        recyclerViewNotes.setLayoutManager(new GridLayoutManager(this, 1));
        notesAdapter = new NotesAdapter();
        recyclerViewNotes.setAdapter(notesAdapter);







        notesAdapter.setOnPosterClickListener(new NotesAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Notes notes = notesAdapter.getNotes().get(position);
                Intent intent = new Intent(MainActivity.this, DetailNotesActivity.class);
                intent.putExtra("id", notes.getID());
                startActivity(intent);
            }
        });

        notesAdapter.setOnReachEndListener(new NotesAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                if (! isLoading) {
                 downloadData(page);
                }
            }
        });

        LiveData<List<Notes>> notesFromLiveData = viewModel.getNotes();
        notesFromLiveData.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
            }
        });


        downloadData(1);



    }

    private void downloadData(int page){
        URL url = NetworkUtils.buildURL(page);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle bundle) {
        NetworkUtils.JSONloader jsonLoader = new NetworkUtils.JSONloader(this, bundle);
        jsonLoader.setOnStartLoadingListener(new NetworkUtils.JSONloader.OnStartLoadingListener() {
            @Override
            public void onStartLoading() {
                progressBarLoading.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject jsonObject) {
        ArrayList<Notes> notes = JSONUtils.getNotesFromJSON(jsonObject);
        if (notes != null && !notes.isEmpty()){
            viewModel.deleteAllNotes();
            for (Notes notes1 : notes){
                viewModel.insertNotes(notes1);
            }
            notesAdapter.addNotes(notes);
            page++;
        }
        isLoading = false;
        progressBarLoading.setVisibility(View.INVISIBLE);
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

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






*/



