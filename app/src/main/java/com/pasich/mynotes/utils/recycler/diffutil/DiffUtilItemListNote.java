package com.pasich.mynotes.utils.recycler.diffutil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pasich.mynotes.data.model.ItemListNote;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;

@ActivityScoped
public class DiffUtilItemListNote extends DiffUtil.ItemCallback<ItemListNote> {

    @Inject
    public DiffUtilItemListNote() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull ItemListNote oldItem, @NonNull ItemListNote newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ItemListNote oldItem, @NonNull ItemListNote newItem) {
        return oldItem.getValue().equals(newItem.getValue());
    }
}