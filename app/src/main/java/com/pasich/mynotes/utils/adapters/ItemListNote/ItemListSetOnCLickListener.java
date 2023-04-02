package com.pasich.mynotes.utils.adapters.ItemListNote;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemListSetOnCLickListener {

    void requestDrag(RecyclerView.ViewHolder viewHolder);

    void addItem(RecyclerView.ViewHolder viewHolder);
}
