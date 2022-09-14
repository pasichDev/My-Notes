package com.pasich.mynotes.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.databinding.ItemTagBinding;

public class TagsAdapter extends ListAdapter<Tag, TagsAdapter.ViewHolder> {

  private final int PAYLOAD_BACKGROUND = 22;
  private OnItemClickListener mOnItemClickListener;

  public TagsAdapter(@NonNull DiffUtil.ItemCallback<Tag> diffCallback) {
    super(diffCallback);
  }



  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  /**
   * Метод который возвращет позицию отмеченого тэга
   *
   * @return - позиция метки
   */
  @Deprecated
  public int getCheckedPosition() {
    int retCount = 0;
    for (int i = 0; i < getCurrentList().size(); i++) {
      if (getItem(i).getSelected()) {
        retCount = i;
        break;
      }
    }
    return retCount;
  }

  @Deprecated
  public int getTagForName(String nameTagSearch) {
    int retCount = 0;
    for (int i = 0; i < getCurrentList().size(); i++) {
      if (getItem(i).getNameTag().equals(nameTagSearch)) {
        retCount = i;
        break;
      }
    }
    return retCount;
  }


  @Deprecated
  public void autChoseTag(String nameTag) {
    if (getCurrentList().size() >= 1) {
      int positionTag = getTagForName(nameTag);
      getItem(positionTag).setSelected(true);
      notifyItemChanged(positionTag, PAYLOAD_BACKGROUND);
    }
  }

  public void removeOldCheck() {
    int positionSelected = getCheckedPosition();
    getItem(positionSelected).setSelected(false);
    notifyItemChanged(positionSelected, PAYLOAD_BACKGROUND);
  }


  /**
   * Метод который реализует выбор метки
   *
   * @param position
   */

  @Deprecated
  public void chooseTag(int position) {
    removeOldCheck();
    getItem(position).setSelected(true);
    notifyItemChanged(position, PAYLOAD_BACKGROUND);
  }

  @NonNull
  @Override
  public TagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    holder.ItemBinding.setTagsModel(getItem(position));
    holder.ItemBinding.setCheckedItem(getItem(position).getSelected());
  }

  /*
  @Override
  public void onBindViewHolder(
      @NonNull TagsAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
      super.onBindViewHolder(holder, position, payloads);

  }
*/
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

  public static class tagDiff extends DiffUtil.ItemCallback<Tag> {

    @Override
    public boolean areItemsTheSame(@NonNull Tag oldItem, @NonNull Tag newItem) {
      return oldItem.getId() == (newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Tag oldItem, @NonNull Tag newItem) {
      return oldItem.getNameTag().equals(newItem.getNameTag());
    }
  }
}
