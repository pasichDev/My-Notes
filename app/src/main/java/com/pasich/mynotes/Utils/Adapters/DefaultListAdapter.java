package com.pasich.mynotes.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pasich.mynotes.Model.Adapter.ListNotesModel;
import com.pasich.mynotes.R;

import java.util.List;

public class DefaultListAdapter extends ArrayAdapter<ListNotesModel> {

  private final LayoutInflater inflater;
  private final int layout;
  private final List<ListNotesModel> listNotes;

  public DefaultListAdapter(Context context, int resource, List<ListNotesModel> list) {
    super(context, resource, list);
    this.listNotes = list;
    this.layout = resource;
    this.inflater = LayoutInflater.from(getContext());
  }

  @Override
  public ListNotesModel getItem(int i) {
    return listNotes != null ? listNotes.get(i) : null;
  }

  public List<ListNotesModel> getData() {
    return this.listNotes;
  }

  public int getCountChecked() {
    int count = 0;
    for (int i = 0; i < listNotes.size(); i++) {
      count = listNotes.get(i).getChecked() ? count + 1 : count;
    }
    return count;
  }

  public void setChekClean() {
    for (int i = 0; i < listNotes.size(); i++) {
      listNotes.get(i).setChecked(false);
    }
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    if (convertView == null) {
      convertView = inflater.inflate(this.layout, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    String noteTitle = getItem(position).getTitle();

    if (noteTitle.length() >= 2) {
      viewHolder.nameView.setVisibility(View.VISIBLE);
      viewHolder.nameView.setText(noteTitle);
    }
    viewHolder.previewNote.setText(getItem(position).getPreview());
    /*
    if (getItem(position).getTags() != null && getItem(position).getTags().length() >= 2) {
      viewHolder.tagNote.setVisibility(View.VISIBLE);
      viewHolder.tagNote.setText("#" + getItem(position).getTags());
    }else {
      viewHolder.tagNote.setVisibility(View.GONE);
    }*/

    getItem(position).setItemView(convertView);
    return convertView;
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
