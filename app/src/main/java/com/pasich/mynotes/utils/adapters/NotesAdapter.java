package com.pasich.mynotes.utils.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.ItemNoteBinding;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.ViewHolder> {

  private OnItemClickListener mOnItemClickListener;

  public NotesAdapter(@NonNull noteDiff diffCallback) {
    super(diffCallback);
  }
  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  @NonNull
  @Override
  public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ViewHolder view =
        new NotesAdapter.ViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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
    holder.ItemBinding.setNoteItem(getCurrentList().get(position));
  }

  @Override
  public void onBindViewHolder(
      @NonNull NotesAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
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

  // method for filtering our recyclerview items.
  @SuppressLint("NotifyDataSetChanged")
  public void filterList(ArrayList<Note> filterllist) {
    // below line is to add our filtered
    // list in our course array list.
    //  listNotes = filterllist;
    // below line is to notify our adapter
    // as change in recycler MyView data.
    // notifyDataSetChanged();
  }

  public interface OnItemClickListener {
    void onClick(int position);

    void onLongClick(int position);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ItemNoteBinding ItemBinding;

    ViewHolder(ItemNoteBinding binding) {
      super(binding.getRoot());
      ItemBinding = binding;
    }
  }

  public static class noteDiff extends DiffUtil.ItemCallback<Note> {

    @Override
    public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
      Log.wtf("pasic", "areItemsTheSame: check " + newItem.getTitle());
      return oldItem == newItem;
    }

    /**
     * Это нужно обновить потому что измениния проверяються только в одном случае
     *
     * @param oldItem
     * @param newItem
     * @return
     */
    @Override
    public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
      Log.wtf("pasic", "areContentsTheSame: check ");
      return false ;
    }
  }
}
