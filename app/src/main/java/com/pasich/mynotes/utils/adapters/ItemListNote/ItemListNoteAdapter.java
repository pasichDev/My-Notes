package com.pasich.mynotes.utils.adapters.ItemListNote;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.databinding.ItemListNoteBinding;
import com.pasich.mynotes.databinding.ItemListNoteSystemBinding;

import java.util.List;

public class ItemListNoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    private static final int ADD_ITEM = 505;
    private static final int OTHER_ITEM = 507;
    private final List<ItemListNote> itemList;
    private ItemListSetOnCLickListener itemListSetOnCLickListener;

    public ItemListNoteAdapter(List<ItemListNote> itemList) {
        this.itemList = itemList;
    }


    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).isSystem() ? ADD_ITEM : OTHER_ITEM;
    }

    public void setItemListSetOnCLickListener(ItemListSetOnCLickListener itemListSetOnCLickListener) {
        this.itemListSetOnCLickListener = itemListSetOnCLickListener;
    }

    @NonNull
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ADD_ITEM) {

            ItemListNoteAdapter.OtherItemViewHolder view = new OtherItemViewHolder(ItemListNoteSystemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            view.itemListNoteBinding.getRoot().setOnClickListener(v -> {
                itemListSetOnCLickListener.addItem(view);
            });
            return view;
        } else {
            ItemListNoteAdapter.ItemViewHolder view = new ItemViewHolder(ItemListNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            view.itemListNoteBinding.dragItem.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    itemListSetOnCLickListener.requestDrag(view);
                }
                return false;
            });
            return view;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemListNote itemListNote = itemList.get(position);
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).itemListNoteBinding.valueItem.setText(itemListNote.getValue());
            ((ItemViewHolder) holder).itemListNoteBinding.checkItem.setChecked(itemListNote.isChecked());
        } else {
            ((OtherItemViewHolder) holder).itemListNoteBinding.valueSystem.setText(itemListNote.getValue());
        }

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

    public static class OtherItemViewHolder extends RecyclerView.ViewHolder {

        ItemListNoteSystemBinding itemListNoteBinding;


        public OtherItemViewHolder(ItemListNoteSystemBinding binding) {
            super(binding.getRoot());
            itemListNoteBinding = binding;
        }


    }
}
