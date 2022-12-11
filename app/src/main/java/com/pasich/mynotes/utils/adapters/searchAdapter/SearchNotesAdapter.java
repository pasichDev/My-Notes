package com.pasich.mynotes.utils.adapters.searchAdapter;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.database.model.IndexFilter;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.ItemResultBinding;
import com.pasich.mynotes.di.scope.PerActivity;

import java.util.ArrayList;
import java.util.List;


@PerActivity
public class SearchNotesAdapter extends RecyclerView.Adapter<SearchNotesAdapter.ViewHolder> {

    private List<Note> listNotes = new ArrayList<>();
    private List<IndexFilter> indexValue = new ArrayList<>();
    private SetItemClickListener mOnItemClickListener;
    private String textSearch;

    public void setItemClickListener(SetItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public int getItemCount() {
        return (null != listNotes ? listNotes.size() : 0);
    }

    public List<Note> getData() {
        return this.listNotes;
    }


    @NonNull
    @Override
    public SearchNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view = new SearchNotesAdapter.ViewHolder(ItemResultBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        if (mOnItemClickListener != null) {
            view.itemView.setOnClickListener(v -> mOnItemClickListener.onClick(getData().get(view.getAdapterPosition()).getId(), view.ItemBinding.itemNote));
        }

        return view;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Note note = listNotes.get(position);
        final IndexFilter indexFilter = searchNoteIndex(listNotes.get(position).getId());


        holder.ItemBinding.setNote(listNotes.get(position));


        Log.wtf(TAG, "size == id: " + indexValue.size() + "/" + indexFilter.getIdNote());

        if (note.getId() == indexFilter.getIdNote()) {

            Log.wtf(TAG, "id == id: " + note.getId() + "/" + indexFilter.getIndexTitle());
            if (indexFilter.getIndexTitle() != -1) {
                Spannable titleNote = new SpannableString(note.getTitle());
                titleNote.setSpan(new BackgroundColorSpan(Color.BLUE), indexFilter.getIndexTitle(), indexFilter.getIndexTitle() + textSearch.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.ItemBinding.nameNote.setText(titleNote);
            } else {
                holder.ItemBinding.nameNote.setText(note.getTitle());
                holder.ItemBinding.tagNote.setText(note.getTag());
            }


            if (indexFilter.getIndexValue() != -1) {
                Spannable valueNote = new SpannableString(note.getValue());
                valueNote.setSpan(new BackgroundColorSpan(Color.BLUE), indexFilter.getIndexValue(), indexFilter.getIndexValue() + textSearch.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.ItemBinding.nameNote.setText(valueNote);
            } else {
                holder.ItemBinding.previewNote.setText(note.getValue());
                holder.ItemBinding.tagNote.setText(note.getTag());
            }


        } else {
            //здесь если индекс не совпадает просто добавим заметку
            holder.ItemBinding.setNote(note);
        }


        //  String title = listNotes.get(position).getTitle();
        // if (textSearch.length() >= 2 && title.contains(textSearch.toLowerCase())) {


        // }
    }


    private IndexFilter searchNoteIndex(long idNote) {
        for (IndexFilter indexFilter : indexValue) {
            if (indexFilter.getIdNote() == idNote) return indexFilter;
        }
        return new IndexFilter(0, -1, -1);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<Note> newListFilter, String textSearch, ArrayList<IndexFilter> indexValue) {
        this.listNotes = newListFilter;
        this.indexValue = indexValue;
        this.textSearch = textSearch;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void cleanResult() {
        if (listNotes.size() >= 1) {
            listNotes.clear();
            indexValue.clear();
            notifyDataSetChanged();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemResultBinding ItemBinding;

        ViewHolder(ItemResultBinding binding) {
            super(binding.getRoot());
            ItemBinding = binding;
        }
    }
}
