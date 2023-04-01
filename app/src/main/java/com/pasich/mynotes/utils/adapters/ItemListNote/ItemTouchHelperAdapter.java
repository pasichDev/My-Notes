package com.pasich.mynotes.utils.adapters.ItemListNote;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
