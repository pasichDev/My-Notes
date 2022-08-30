package com.pasich.mynotes.utils.recycler.diffutil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pasich.mynotes.data.trash.TrashNote;

public class DiffUtilTrash extends DiffUtil.ItemCallback<TrashNote> {


    @Override
    public boolean areItemsTheSame(@NonNull TrashNote oldItem, @NonNull TrashNote newItem) {

        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull TrashNote oldItem, @NonNull TrashNote newItem) {
        return true;
    }
}