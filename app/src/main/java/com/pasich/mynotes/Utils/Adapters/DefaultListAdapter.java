package com.pasich.mynotes.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pasich.mynotes.Model.Adapter.NoteModel;
import com.pasich.mynotes.R;

import java.util.List;

public class DefaultListAdapter extends ArrayAdapter<NoteModel> {

  private final LayoutInflater inflater;
  private final int layout;
  private final List<NoteModel> listNotes;
  private ViewHolder viewHolder;

  public DefaultListAdapter(Context context, int resource, List<NoteModel> list) {
    super(context, resource, list);
    this.listNotes = list;
    this.layout = resource;
    this.inflater = LayoutInflater.from(getContext());
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) {
      convertView = inflater.inflate(this.layout, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    setNameNote(getItem(position).getTitle());
    setPreviewNote(getItem(position).getPreview());
    setTagNote(getItem(position).getTags());
    getItem(position).setItemView(convertView);

    return convertView;
  }

  @Override
  public NoteModel getItem(int i) {
    return listNotes != null ? listNotes.get(i) : null;
  }

  public List<NoteModel> getData() {
    return this.listNotes;
  }

  public int getCountChecked() {
    int count = 0;
    for (int i = 0; i < listNotes.size(); i++) {
      count = listNotes.get(i).getChecked() ? count + 1 : count;
    }
    return count;
  }

  public void setCheckClean() {
    for (int i = 0; i < listNotes.size(); i++) {
      listNotes.get(i).setChecked(false);
    }
  }

  private void setNameNote(String noteTitle) {
    if (noteTitle.length() >= 2) {
      viewHolder.nameView.setVisibility(View.VISIBLE);
      viewHolder.nameView.setText(noteTitle);
    } else {
      viewHolder.nameView.setVisibility(View.GONE);
      viewHolder.nameView.setText("");
    }
  }

  private void setPreviewNote(String previewNote) {
    viewHolder.previewNote.setText(
        previewNote.length() > 200 ? previewNote.substring(0, 200) : previewNote);
  }

  private void setTagNote(String tagNote) {
    if (tagNote != null && tagNote.length() >= 2) {
      viewHolder.tagNote.setVisibility(View.VISIBLE);
      viewHolder.tagNote.setText("#" + tagNote);
    } else {
      viewHolder.tagNote.setVisibility(View.GONE);
    }
  }

  private static class ViewHolder {
    final TextView nameView, previewNote, tagNote;

    ViewHolder(View view) {
      nameView = view.findViewById(R.id.nameNote);
      previewNote = view.findViewById(R.id.previewNote);
      tagNote = view.findViewById(R.id.tagNote);
    }
  }
}
