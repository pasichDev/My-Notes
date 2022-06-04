package com.pasich.mynotes.Utils.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.databinding.ListTagsBinding;
import com.pasich.mynotes.models.adapter.TagsModel;

import java.util.List;

public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.ViewHolder> {

  private final List<TagsModel> listTags;
  private OnItemClickListener mOnItemClickListener;

  public TagListAdapter(List<TagsModel> listTags) {
    this.listTags = listTags;
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  @Override
  public int getItemCount() {
    return (null != listTags ? listTags.size() : 0);
  }

  /**
   * Метод который возвращет позицию отмеченого тэга
   *
   * @return - позция тэга
   */
  public int getCheckedPosition() {
    int retCount = 0;
    for (int i = 0; i < listTags.size(); i++) {
      if (listTags.get(i).getSelected()) {
        retCount = i;
        break;
      }
    }
    return retCount;
  }

  public void chooseTag(int position) {
    int positionSelected = getCheckedPosition();
    listTags.get(positionSelected).setSelected(false);
    notifyItemChanged(positionSelected);
    listTags.get(position).setSelected(true);
    notifyItemChanged(position);
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
    holder.ItemBinding.setTagsModel(listTags.get(position));
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
