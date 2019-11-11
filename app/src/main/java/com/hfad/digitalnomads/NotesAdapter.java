package com.hfad.digitalnomads;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.digitalnomads.dataBase.Notes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    private ArrayList<Notes> notes;

    public  NotesAdapter(){
        notes = new ArrayList<>();
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_item, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int i) {
        Notes note = notes.get(i);
        notesViewHolder.textViewAuthor.setText(note.getAuthor());
        notesViewHolder.textViewTitle.setText(note.getTitle());
        notesViewHolder.textViewDescription.setText(note.getDescription());
        notesViewHolder.textViewPublishedAt.setText(note.getPublishedAt());
        Picasso.get().load(note.getUrlToImage()).into(notesViewHolder.imageViewNotes);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewNotes;
        private TextView textViewAuthor;
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewUrl;
        private TextView textViewPublishedAt;



        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewNotes = itemView.findViewById(R.id.imageViewNotes);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPublishedAt = itemView.findViewById(R.id.textViewPublishedAt);
        }
    }


    public void setNotes(ArrayList<Notes> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void addNotes(ArrayList<Notes> notes){
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }

    public ArrayList<Notes> getNotes() {
        return notes;
    }
}
