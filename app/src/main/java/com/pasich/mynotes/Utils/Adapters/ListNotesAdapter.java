package com.pasich.mynotes.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.Models.Adapter.NoteItemModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ListNotesBinding;

import java.util.ArrayList;
import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> {

  private final Context context;
  private List<NoteItemModel> listNotes;
  private OnItemClickListener mOnItemClickListener;

  public ListNotesAdapter(Context context, List<NoteItemModel> listNotes) {
    this.listNotes = listNotes;
    this.context = context;
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
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
    NoteItemModel note = getItem(position);
    setNameNote(note.getTitle(), holder.ItemBinding);
    setPreviewNote(note.getValue(), holder.ItemBinding);
    setTagNote(note.getTags(), holder.ItemBinding);
    setCheckedItem(note.getChecked(), holder.ItemBinding);
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

  @Override
  public int getItemCount() {
    return listNotes.size();
  }

  private void setCheckedItem(boolean checked, ListNotesBinding ItemBinding) {
    if (checked)
      ItemBinding.getRoot()
          .setBackground(
              ContextCompat.getDrawable(context, R.drawable.item_note_background_selected));
    else
      ItemBinding.getRoot()
          .setBackground(ContextCompat.getDrawable(context, R.drawable.item_selected));
  }

  public List<NoteItemModel> getData() {
    return this.listNotes;
  }

  public NoteItemModel getItem(int i) {
    return listNotes != null ? listNotes.get(i) : null;
  }

  private void setNameNote(String noteTitle, ListNotesBinding ItemBinding) {
    if (noteTitle.length() >= 2) {
      ItemBinding.nameNote.setVisibility(View.VISIBLE);
      ItemBinding.nameNote.setText(noteTitle);
    } else {
      ItemBinding.nameNote.setVisibility(View.GONE);
      ItemBinding.nameNote.setText("");
    }
  }

  private void setPreviewNote(String previewNote, ListNotesBinding ItemBinding) {
    ItemBinding.previewNote.setText(
        previewNote.length() > 200 ? previewNote.substring(0, 200) : previewNote);
  }

  private void setTagNote(String tagNote, ListNotesBinding ItemBinding) {
    if (tagNote != null && tagNote.length() >= 2) {
      ItemBinding.tagNote.setVisibility(View.VISIBLE);
      ItemBinding.tagNote.setText(context.getString(R.string.tagNameListNote, tagNote));
    } else {
      ItemBinding.tagNote.setVisibility(View.GONE);
    }
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
