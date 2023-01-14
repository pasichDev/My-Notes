package com.pasich.mynotes.utils.recycler.diffutil;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.pasich.mynotes.data.database.model.Coffee;
import com.pasich.mynotes.di.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public class DiffUtiCoffee extends DiffUtil.ItemCallback<Coffee> {

    @Inject
    public DiffUtiCoffee() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull Coffee oldItem, @NonNull Coffee newItem) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Coffee oldItem, @NonNull Coffee newItem) {
        return true;
    }
}

