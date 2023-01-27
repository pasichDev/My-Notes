package com.pasich.mynotes.utils.adapters.searchAdapter;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.model.IndexFilter;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.databinding.ItemResultBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.scopes.ActivityScoped;


@ActivityScoped
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
        holder.ItemBinding.setNote(listNotes.get(position));

        final int colorSpannable = MaterialColors.getColor(holder.itemView.getContext(), R.attr.colorSurfaceVariant, Color.GRAY);
        Spannable titleNote = new SpannableString(note.getTitle());
        Spannable valueNote = new SpannableString(note.getValue());

        for (IndexFilter filter : indexValue) {

            if (filter.getIdNote() == listNotes.get(position).getId()) {
                if (filter.getIndexTitle() != -1) {
                    titleNote.setSpan(new BackgroundColorSpan(colorSpannable)
                            , filter.getIndexTitle(), filter.getIndexTitle() + textSearch.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                if (filter.getIndexValue() != -1) {
                    valueNote.setSpan(new BackgroundColorSpan(
                            colorSpannable), filter.getIndexValue(), filter.getIndexValue() + textSearch.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            }
        }

        holder.ItemBinding.previewNote.setText(valueNote);
        holder.ItemBinding.nameNote.setText(titleNote);
        holder.ItemBinding.tagNote.setText(note.getTag());
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
