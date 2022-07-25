package com.pasich.mynotes.utils.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.trash.TrashNote;
import com.pasich.mynotes.databinding.ItemNoteTrashBinding;

import java.util.List;

public class TrashNotesAdapter extends ListAdapter<TrashNote, TrashNotesAdapter.ViewHolder> {

  private OnItemClickListener mOnItemClickListener;

  public TrashNotesAdapter(@NonNull noteDiff diffCallback) {
    super(diffCallback);
  }
  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  @NonNull
  @Override
  public TrashNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ViewHolder view =
        new TrashNotesAdapter.ViewHolder(
                ItemNoteTrashBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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
    holder.ItemBinding.setNoteTrashItem(getCurrentList().get(position));
  }

  @Override
  public void onBindViewHolder(
          @NonNull TrashNotesAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
    if (payloads.isEmpty()) {
      super.onBindViewHolder(holder, position, payloads);
    } else {
      for (Object payload : payloads) {
        int PAYLOAD_BACKGROUND = 22;
        if (payload.equals(PAYLOAD_BACKGROUND)) {
          holder.ItemBinding.setCheckedItem(getCurrentList().get(position).getChecked());

        }
      }
    }
  }


  public interface OnItemClickListener {
    void onClick(int position);

    void onLongClick(int position);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ItemNoteTrashBinding ItemBinding;

    ViewHolder(ItemNoteTrashBinding binding) {
      super(binding.getRoot());
      ItemBinding = binding;
    }
  }

  public static class noteDiff extends DiffUtil.ItemCallback<TrashNote> {

    @Override
    public boolean areItemsTheSame(@NonNull TrashNote oldItem, @NonNull TrashNote newItem) {

      return oldItem == newItem;
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull TrashNote oldItem, @NonNull TrashNote newItem) {
      return oldItem == newItem ;
    }
  }
}
