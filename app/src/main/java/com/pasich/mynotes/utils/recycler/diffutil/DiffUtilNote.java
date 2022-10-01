package com.pasich.mynotes.utils.recycler.diffutil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pasich.mynotes.data.notes.Note;

public class DiffUtilNote extends DiffUtil.ItemCallback<Note> {


    @Override
    public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(
            @NonNull Note oldUser, @NonNull Note newUser) {
        return oldUser.equals(newUser);
    }
}


