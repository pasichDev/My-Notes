package com.pasich.mynotes.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.ItemNoteBinding;
import com.pasich.mynotes.utils.comparator.NoteComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public NotesAdapter(@NonNull noteDiff diffCallback) {
        super(diffCallback);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void sortList(String arg) {
        List<Note> sortedList = new ArrayList<>(getCurrentList());
        Collections.sort(sortedList, new NoteComparator().getComparator(arg));
        submitList(sortedList);
    }

    public void sortList(List<Note> notesList, String arg) {
        Collections.sort(notesList, new NoteComparator().getComparator(arg));
        submitList(notesList);
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view =
                new NotesAdapter.ViewHolder(
                        ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        if (mOnItemClickListener != null) {
            view.itemView.setOnClickListener(
                    v -> mOnItemClickListener.onClick(view.getAdapterPosition()));

            view.itemView.setOnLongClickListener(
                    v -> {
                        mOnItemClickListener.onLongClick(view.getAdapterPosition());
                        return false;
                    });
        }

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ItemBinding.setNoteItem(getCurrentList().get(position));
    }

    @Override
    public void onBindViewHolder(
            @NonNull NotesAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            for (Object payload : payloads) {
                int PAYLOAD_BACKGROUND = 22;
                if (payload.equals(PAYLOAD_BACKGROUND)) {
                    holder.ItemBinding.setCheckedItem(getCurrentList().get(position).getChecked());
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemNoteBinding ItemBinding;

        ViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            ItemBinding = binding;
        }
    }

    public static class noteDiff extends DiffUtil.ItemCallback<Note> {

        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getValue().equals(newItem.getValue())
                    && oldItem.getTag().length() != newItem.getTag().length();
        }
    }
}
