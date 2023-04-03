package com.pasich.mynotes.utils.adapters.ItemListNote;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.databinding.ItemListNoteBinding;
import com.pasich.mynotes.databinding.ItemListNoteSystemBinding;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ItemListNoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    private static final int ADD_ITEM = 505;
    private static final int OTHER_ITEM = 507;
    private List<ItemListNote> itemsListNote;
    private ItemListSetOnClickListener itemListSetOnCLickListener;

    public ItemListNoteAdapter(List<ItemListNote> itemsListNote) {
        this.itemsListNote = itemsListNote;

    }

    public List<ItemListNote> getItemsListNote() {
        return itemsListNote;
    }

    public void setItemsListNote(List<ItemListNote> newList) {
        this.itemsListNote = newList;
    }

    @Override
    public int getItemViewType(int position) {
        return itemsListNote.get(position).isSystem() ? ADD_ITEM : OTHER_ITEM;
    }

    public void setItemListSetOnCLickListener(ItemListSetOnClickListener itemListSetOnCLickListener) {
        this.itemListSetOnCLickListener = itemListSetOnCLickListener;
    }


    @NonNull
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ADD_ITEM) {
            ItemListNoteAdapter.OtherItemViewHolder view = new OtherItemViewHolder(ItemListNoteSystemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            view.itemListNoteBinding.getRoot().setOnClickListener(v -> itemListSetOnCLickListener.addItem(view));
            return view;
        } else {
            ItemListNoteAdapter.ItemViewHolder view = new ItemViewHolder(ItemListNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            view.itemListNoteBinding.dragItem.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    itemListSetOnCLickListener.requestDrag(view);
                }
                return false;
            });
            view.itemListNoteBinding.valueItem.setOnFocusChangeListener((v1, hasFocus) -> view.itemListNoteBinding.deleteItem.setVisibility(hasFocus ? View.VISIBLE : View.GONE));
            view.itemListNoteBinding.deleteItem.setOnClickListener(v -> deleteItemList(view.getAdapterPosition()));

            view.itemListNoteBinding.valueItem.setOnTouchListener((v, event) -> !itemListSetOnCLickListener.isActivatedEdit());
            return view;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemListNote itemListNote = itemsListNote.get(position);
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).itemListNoteBinding.valueItem.setText(itemListNote.getValue());
            ((ItemViewHolder) holder).itemListNoteBinding.checkItem.setChecked(itemListNote.isChecked());
        } else {
            ((OtherItemViewHolder) holder).itemListNoteBinding.valueSystem.setText(itemListNote.getValue());
        }

    }

    @Override
    public int getItemCount() {
        return itemsListNote.size();
    }

    public void addNewItem(int noteId) {
        ItemListNote itemListNote = new ItemListNote("", noteId, (itemsListNote.size() + 1));
        itemsListNote.add(itemListNote);
        Collections.sort(itemsListNote, new SortComparator());
        int position = itemsListNote.indexOf(itemListNote);
        notifyItemInserted((position + 1));
    }

    private void deleteItemList(int position) {
        itemsListNote.remove(position);
        itemListSetOnCLickListener.refreshFocus(position);
        notifyItemRemoved(position);
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        ItemListNote fromItem = itemsListNote.get(fromPosition);
        itemsListNote.remove(fromPosition);
        itemsListNote.add(toPosition, fromItem);
        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void onItemDismiss(int position) {
        itemsListNote.remove(position);
        notifyItemRemoved(position);
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemListNoteBinding itemListNoteBinding;


        public ItemViewHolder(ItemListNoteBinding binding) {
            super(binding.getRoot());
            itemListNoteBinding = binding;
        }


    }

    public static class OtherItemViewHolder extends RecyclerView.ViewHolder {

        ItemListNoteSystemBinding itemListNoteBinding;


        public OtherItemViewHolder(ItemListNoteSystemBinding binding) {
            super(binding.getRoot());
            itemListNoteBinding = binding;
        }


    }


    public static class SortComparator implements Comparator<ItemListNote> {
        @Override
        public int compare(ItemListNote item1, ItemListNote item2) {
            if (item1.isSystem() && !item2.isSystem()) {
                return 1;
            } else if (!item1.isSystem() && item2.isSystem()) {
                return -1;
            } else {
                return Integer.compare(item1.getDragPosition(), item2.getDragPosition());
            }
        }
    }

}
