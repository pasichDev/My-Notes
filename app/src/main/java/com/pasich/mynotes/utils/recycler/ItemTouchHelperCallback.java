package com.pasich.mynotes.utils.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.utils.adapters.ItemListNote.ItemListNoteAdapter;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final ItemListNoteAdapter adapter;

    public ItemTouchHelperCallback(ItemListNoteAdapter adapter) {
        this.adapter = adapter;
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

}
