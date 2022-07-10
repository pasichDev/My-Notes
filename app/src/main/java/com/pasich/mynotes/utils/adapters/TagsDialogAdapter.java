package com.pasich.mynotes.utils.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.databinding.ItemTagBinding;

import java.util.List;

public class TagsDialogAdapter extends RecyclerView.Adapter<TagsDialogAdapter.ViewHolder> {

    private final List<Tag> listTags;
    private OnItemClickListener mOnItemClickListener;

    public TagsDialogAdapter(List<Tag> listTags) {
        this.listTags = listTags;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return (null != listTags ? listTags.size() : 0);
    }


    @NonNull
    @Override
    public TagsDialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view =
                new ViewHolder(
                        ItemTagBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        if (mOnItemClickListener != null) {
            view.itemView.setOnClickListener(
                    v -> mOnItemClickListener.onClick(view.getAdapterPosition()));

            view.itemView.setOnLongClickListener(
                    v -> {
                        mOnItemClickListener.onLongClick(view.getAdapterPosition());
                        return false;
                    });
        }

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ItemBinding.setTagsModel(listTags.get(position));
        holder.ItemBinding.setCheckedItem(listTags.get(position).getSelected());
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTagBinding ItemBinding;

        ViewHolder(ItemTagBinding binding) {
            super(binding.getRoot());
            ItemBinding = binding;
        }
    }
}