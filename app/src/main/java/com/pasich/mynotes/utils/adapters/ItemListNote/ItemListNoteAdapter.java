package com.pasich.mynotes.utils.adapters.ItemListNote;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.databinding.ItemListNoteBinding;

import java.util.List;

public class ItemListNoteAdapter extends RecyclerView.Adapter<ItemListNoteAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
    private List<ItemListNote> itemList;

    public ItemListNoteAdapter(List<ItemListNote> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListNoteAdapter.ItemViewHolder view = new ItemViewHolder(ItemListNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemListNote itemListNote = itemList.get(position);
        holder.itemListNoteBinding.valueItem.setText(itemListNote.getValue());
        holder.itemListNoteBinding.checkItem.setChecked(itemListNote.isChecked());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        ItemListNote fromItem = itemList.get(fromPosition);
        itemList.remove(fromPosition);
        itemList.add(toPosition, fromItem);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemListNoteBinding itemListNoteBinding;


        public ItemViewHolder(ItemListNoteBinding binding) {
            super(binding.getRoot());
            itemListNoteBinding = binding;
        }


    }
}
