package com.pasich.mynotes.utils.adapters.ItemListNote;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.model.ItemListNote;
import com.pasich.mynotes.databinding.ItemListNoteBinding;
import com.pasich.mynotes.databinding.ItemListNoteSystemBinding;

import javax.inject.Inject;
import javax.inject.Named;

public class ItemListNoteAdapter extends ListAdapter<ItemListNote, RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    private static final int ADD_ITEM = 505;
    private static final int OTHER_ITEM = 507;
    private ItemListSetOnCLickListener itemListSetOnCLickListener;

    @Inject
    public ItemListNoteAdapter(@NonNull @Named("ItemListNotes") DiffUtil.ItemCallback<ItemListNote> diffCallback) {
        super(diffCallback);
    }


    @Override
    public int getItemViewType(int position) {
        return getCurrentList().get(position).isSystem() ? ADD_ITEM : OTHER_ITEM;
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
            return view;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemListNote itemListNote = getCurrentList().get(position);
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).itemListNoteBinding.valueItem.setText(itemListNote.getValue());
            ((ItemViewHolder) holder).itemListNoteBinding.checkItem.setChecked(itemListNote.isChecked());
        } else {
            ((OtherItemViewHolder) holder).itemListNoteBinding.valueSystem.setText(itemListNote.getValue());
        }

    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        ItemListNote fromItem = getCurrentList().get(fromPosition);
        getCurrentList().remove(fromPosition);
        getCurrentList().add(toPosition, fromItem);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        getCurrentList().remove(position);
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
