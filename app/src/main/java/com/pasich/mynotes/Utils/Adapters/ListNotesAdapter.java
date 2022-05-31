package com.pasich.mynotes.Utils.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.databinding.ListNotesBinding;
import com.pasich.mynotes.models.adapter.NoteItemModel;

import java.util.ArrayList;
import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> {

  private List<NoteItemModel> listNotes;
  private OnItemClickListener mOnItemClickListener;

  public ListNotesAdapter(List<NoteItemModel> listNotes) {
    this.listNotes = listNotes;
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }



  @Override
  public int getItemCount() {
    return (null != listNotes ? listNotes.size() : 0);
  }

  public List<NoteItemModel> getData() {
    return this.listNotes;
  }

  public NoteItemModel getItem(int i) {
    return listNotes != null ? listNotes.get(i) : null;
  }

  @NonNull
  @Override
  public ListNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ViewHolder view =
        new ViewHolder(
            ListNotesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    /** Возможная ошибка без позиции && view.getAdapterPosition() != RecyclerView.NO_POSITION */
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
  public void onBindViewHolder(@NonNull ListNotesAdapter.ViewHolder holder, int position) {
    holder.ItemBinding.setNoteItem(getItem(position));
  }

  // method for filtering our recyclerview items.
  public void filterList(ArrayList<NoteItemModel> filterllist) {
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
