package com.pasich.mynotes.utils.adapters.searchAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.ItemResultBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchNotesAdapter extends RecyclerView.Adapter<SearchNotesAdapter.ViewHolder> {

    private List<Note> listNotes = new ArrayList<>();
    private SetItemClickListener mOnItemClickListener;
    private int lastPosition = -1;

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
            view.itemView.setOnClickListener(v -> mOnItemClickListener.onClick(getData().get(view.getAdapterPosition()).getId()));
        }

        return view;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ItemBinding.setNote(listNotes.get(position));

    }


    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<Note> newListFilter) {
        listNotes = newListFilter;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void cleanResult() {
        if (listNotes.size() >= 1) {
            listNotes.clear();
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
