package com.pasich.mynotes.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.Models.Adapter.NoteItemModel;
import com.pasich.mynotes.R;

import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> {

  private final LayoutInflater inflater;
  private final List<NoteItemModel> listNotes;
  private final Context context;
  private OnItemClickListener mOnItemClickListener;

  public ListNotesAdapter(Context context, List<NoteItemModel> listNotes) {
    this.listNotes = listNotes;
    this.context = context;
    this.inflater = LayoutInflater.from(context);
  }

  public interface OnItemClickListener {
    void onClick(int position);

    void onLongClick(int position);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  @NonNull
  @Override
  public ListNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.list_notes, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ListNotesAdapter.ViewHolder holder, int position) {
    NoteItemModel note = getItem(position);
    setNameNote(note.getTitle(), holder);
    setPreviewNote(note.getValue(), holder);
    setTagNote(note.getTags(), holder);
    if (note.getChecked())
      holder.viewH.setBackground(context.getDrawable(R.drawable.item_note_background_selected));
    else holder.viewH.setBackground(context.getDrawable(R.drawable.item_selected));

    if (mOnItemClickListener != null) {
      holder.itemView.setOnClickListener(v -> mOnItemClickListener.onClick(position));

      holder.itemView.setOnLongClickListener(
          v -> {
            mOnItemClickListener.onLongClick(position);
            return false;
          });
    }
  }

  @Override
  public int getItemCount() {
    return listNotes.size();
  }

  public List<NoteItemModel> getData() {
    return this.listNotes;
  }

  public NoteItemModel getItem(int i) {
    return listNotes != null ? listNotes.get(i) : null;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    final TextView nameView, previewNote, tagNote;
    final View viewH;

    ViewHolder(View view) {
      super(view);
      viewH = view;
      nameView = view.findViewById(R.id.nameNote);
      previewNote = view.findViewById(R.id.previewNote);
      tagNote = view.findViewById(R.id.tagNote);
    }
  }

  private void setNameNote(String noteTitle, ListNotesAdapter.ViewHolder holder) {
    if (noteTitle.length() >= 2) {
      holder.nameView.setVisibility(View.VISIBLE);
      holder.nameView.setText(noteTitle);
    } else {
      holder.nameView.setVisibility(View.GONE);
      holder.nameView.setText("");
    }
  }

  private void setPreviewNote(String previewNote, ListNotesAdapter.ViewHolder holder) {
    holder.previewNote.setText(
        previewNote.length() > 200 ? previewNote.substring(0, 200) : previewNote);
  }

  private void setTagNote(String tagNote, ListNotesAdapter.ViewHolder holder) {
    if (tagNote != null && tagNote.length() >= 2) {
      holder.tagNote.setVisibility(View.VISIBLE);
      holder.tagNote.setText("#" + tagNote);
    } else {
      holder.tagNote.setVisibility(View.GONE);
    }
  }
}
