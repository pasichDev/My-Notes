package com.pasich.mynotes.Utils.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.databinding.ListNotesBinding;
import com.pasich.mynotes.models.adapter.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> {

  private List<NoteModel> listNotes;
  private OnItemClickListener mOnItemClickListener;
  private final int PAYLOAD_BACKGROUND = 22;

  public ListNotesAdapter(List<NoteModel> listNotes) {
    this.listNotes = listNotes;
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  /**
   * Метод который находит елемент в массиве и удаляет из него
   *
   * @param noteID - id заметки которую нужно убрать
   */
  public void removeItemsArray(int noteID) {
    for (int i = 0; i < listNotes.size(); i++) {
      if (listNotes.get(i).getId() == noteID) {
        listNotes.remove(i);
        notifyItemRemoved(i);
      }
    }
  }

  @Override
  public int getItemCount() {
    return (null != listNotes ? listNotes.size() : 0);
  }

  public List<NoteModel> getData() {
    return this.listNotes;
  }

  public NoteModel getItem(int i) {
    return listNotes != null ? listNotes.get(i) : null;
  }

  @NonNull
  @Override
  public ListNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ViewHolder view =
        new ListNotesAdapter.ViewHolder(
            ListNotesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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
    holder.ItemBinding.setNoteItem(listNotes.get(position));
  }

  @Override
  public void onBindViewHolder(
      @NonNull ListNotesAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
    if (payloads.isEmpty()) {
      super.onBindViewHolder(holder, position, payloads);
    } else {
      for (Object payload : payloads) {
        if (payload.equals(PAYLOAD_BACKGROUND)) {
          holder.ItemBinding.setCheckedItem(listNotes.get(position).getChecked());
        }
      }
    }
  }

  // method for filtering our recyclerview items.
  @SuppressLint("NotifyDataSetChanged")
  public void filterList(ArrayList<NoteModel> filterllist) {
    // below line is to add our filtered
    // list in our course array list.
    listNotes = filterllist;
    // below line is to notify our adapter
    // as change in recycler view data.
    notifyDataSetChanged();
  }

  public interface OnItemClickListener {
    void onClick(int position);

    void onLongClick(int position);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ListNotesBinding ItemBinding;

    ViewHolder(ListNotesBinding binding) {
      super(binding.getRoot());
      ItemBinding = binding;
    }
  }
}
