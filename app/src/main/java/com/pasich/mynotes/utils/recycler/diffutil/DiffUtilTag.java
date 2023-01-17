package com.pasich.mynotes.utils.recycler.diffutil;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pasich.mynotes.data.model.Tag;
import com.pasich.mynotes.di.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public class DiffUtilTag extends DiffUtil.ItemCallback<Tag> {

    @Inject
    public DiffUtilTag() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull Tag oldItem, @NonNull Tag newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Tag oldItem, @NonNull Tag newItem) {
        return oldItem.getNameTag().equals(newItem.getNameTag());
    }
}

