package com.pasich.mynotes.utils.adapters.genericAdapterNote;

public interface OnItemClickListener<T> {
    void onClick(int position, T model);

    void onLongClick(int position, T model);
}
