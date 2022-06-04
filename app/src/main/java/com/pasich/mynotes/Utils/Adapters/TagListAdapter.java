package com.pasich.mynotes.Utils.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.databinding.ListTagsBinding;
import com.pasich.mynotes.models.adapter.TagsModel;

import java.util.List;

public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.ViewHolder> {

  private final List<TagsModel> listNotes;
  private OnItemClickListener mOnItemClickListener;

  public TagListAdapter(List<TagsModel> listNotes) {
    this.listNotes = listNotes;
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  @Override
  public int getItemCount() {
    return (null != listNotes ? listNotes.size() : 0);
  }

  public TagsModel getItem(int i) {
    return listNotes != null ? listNotes.get(i) : null;
  }

  @NonNull
  @Override
  public TagListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ViewHolder view =
        new ViewHolder(
            ListTagsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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
  public void onBindViewHolder(@NonNull TagListAdapter.ViewHolder holder, int position) {
    holder.ItemBinding.setTagsModel(listNotes.get(position));
  }

  public interface OnItemClickListener {
    void onClick(int position);

    void onLongClick(int position);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ListTagsBinding ItemBinding;

    ViewHolder(ListTagsBinding binding) {
      super(binding.getRoot());
      ItemBinding = binding;
    }
  }
}
