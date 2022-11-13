package com.pasich.mynotes.utils.recycler.diffutil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.di.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public class DiffUtilNote extends DiffUtil.ItemCallback<Note> {


    @Inject
    public DiffUtilNote() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
        return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getValue().equals(newItem.getValue()) && oldItem.getTag().equals(newItem.getTag());
    }
}