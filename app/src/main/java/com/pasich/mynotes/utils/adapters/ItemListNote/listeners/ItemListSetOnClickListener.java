package com.pasich.mynotes.utils.adapters.ItemListNote.listeners;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemListSetOnClickListener {

    void requestDrag(RecyclerView.ViewHolder viewHolder);

    void addItem(RecyclerView.ViewHolder viewHolder);

    void refreshFocus(int position);

    boolean isActivatedEdit();


}
