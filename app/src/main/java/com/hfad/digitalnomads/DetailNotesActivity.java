package com.hfad.digitalnomads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.digitalnomads.dataBase.MainViewModel;
import com.hfad.digitalnomads.dataBase.Notes;
import com.squareup.picasso.Picasso;

public class DetailNotesActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;  //картинка
    private TextView textViewOriginalAuthor; //автор
    private TextView textViewOriginalPublishedAt;   //Дата публикации
    private TextView textViewOriginalDescriptionFull; //Полное описание
    private Button buttonClick;
    String url;

    private int id;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notes);
        imageViewBigPoster = findViewById(R.id.imageViewOriginalPoster);
        textViewOriginalAuthor = findViewById(R.id.textViewOriginalAuthor);
        textViewOriginalPublishedAt = findViewById(R.id.textViewOriginalPublishedAt);
        textViewOriginalDescriptionFull = findViewById(R.id.textViewOriginalDescriptionFull);
        buttonClick = findViewById(R.id.buttonEnd);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")){
            id = intent.getIntExtra("id", -1);
        }else{
            finish();
        }
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Notes notes = viewModel.getNotesById(id);
        Picasso.get().load(notes.getUrlToImage()).into(imageViewBigPoster);
        textViewOriginalAuthor.setText(notes.getAuthor());
        textViewOriginalPublishedAt.setText(notes.getPublishedAt());
        textViewOriginalDescriptionFull.setText(notes.getDescription());
        url = notes.getUrl();



    }

    public void OnClick(View view) {
        Uri addres = Uri.parse(url);
        Intent openlink = new Intent(Intent.ACTION_VIEW, addres);
        startActivity(Intent.createChooser(openlink, "Browser"));

    }
}
