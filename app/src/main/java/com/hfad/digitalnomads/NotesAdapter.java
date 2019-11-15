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
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    private List<Notes> notes;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public  NotesAdapter(){
        notes = new ArrayList<>();
    }


    interface OnPosterClickListener {
        void  onPosterClick(int position);
    }

    interface OnReachEndListener {
        void onReachEnd();
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_item, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int i) {
        if (notes.size() >= 20 && i > notes.size() - 4 && onReachEndListener != null){
            onReachEndListener.onReachEnd();
        }

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
            textViewAuthor = itemView.findViewById(R.id.textViewOriginalAuthor);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPublishedAt = itemView.findViewById(R.id.textViewPublishedAt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPosterClickListener != null) {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void clear() {
        this.notes.clear();
        notifyDataSetChanged();
    }


    public void setNotes(List<Notes> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void addNotes(List<Notes> notes){
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }

    public List<Notes> getNotes() {
        return notes;
    }
}
