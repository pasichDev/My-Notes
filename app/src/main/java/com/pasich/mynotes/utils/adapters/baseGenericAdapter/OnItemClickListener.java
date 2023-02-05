package com.pasich.mynotes.utils.adapters.baseGenericAdapter;

import com.google.android.material.card.MaterialCardView;

public interface OnItemClickListener<T> {
    void onClick(int position, T model, MaterialCardView materialCardView);

    default void onLongClick(int position, T model) {

    }

}
