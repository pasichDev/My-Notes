package com.pasich.mynotes.utils.recycler.diffutil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pasich.mynotes.data.model.DataNote;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;

@ActivityScoped
public class DiffUtilNote extends DiffUtil.ItemCallback<DataNote> {

    @Inject
    public DiffUtilNote() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull DataNote oldItem, @NonNull DataNote newItem) {
        return oldItem.getNote().getId() == newItem.getNote().getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull DataNote oldItem, @NonNull DataNote newItem) {
        return oldItem.getNote().getTitle().equals(newItem.getNote().getTitle()) && oldItem.getNote().getValue().equals(newItem.getNote().getValue()) && oldItem.getNote().getTag().equals(newItem.getNote().getTag());
    }
}