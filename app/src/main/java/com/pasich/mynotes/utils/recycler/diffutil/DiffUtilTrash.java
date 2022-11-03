package com.pasich.mynotes.utils.recycler.diffutil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pasich.mynotes.data.database.model.TrashNote;
import com.pasich.mynotes.di.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public class DiffUtilTrash extends DiffUtil.ItemCallback<TrashNote> {


    @Inject
    public DiffUtilTrash() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull TrashNote oldItem, @NonNull TrashNote newItem) {

        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull TrashNote oldItem, @NonNull TrashNote newItem) {
        return true;
    }
}