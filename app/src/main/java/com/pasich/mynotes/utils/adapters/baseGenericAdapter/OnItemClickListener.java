package com.pasich.mynotes.utils.adapters.baseGenericAdapter;

public interface OnItemClickListener<T> {
    void onClick(int position, T model);

    void onLongClick(int position, T model);
}
