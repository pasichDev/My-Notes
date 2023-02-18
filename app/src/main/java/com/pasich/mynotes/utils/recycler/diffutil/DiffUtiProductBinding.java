package com.pasich.mynotes.utils.recycler.diffutil;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.android.billingclient.api.ProductDetails;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;

@ActivityScoped
public class DiffUtiProductBinding extends DiffUtil.ItemCallback<ProductDetails> {

    @Inject
    public DiffUtiProductBinding() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull ProductDetails oldItem, @NonNull ProductDetails newItem) {
        return oldItem.getProductId() == newItem.getProductId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ProductDetails oldItem, @NonNull ProductDetails newItem) {
        return true;
    }
}

