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
  private ViewHolder viewHolder;


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

  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = inflater.inflate(this.layout, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    viewHolder.nameView.setText(getItem(position).getTitle());
    viewHolder.previewNote.setText(getItem(position).getPreview());

    return convertView;
  }




  private static class ViewHolder {
    final TextView nameView, previewNote;

    ViewHolder(View view) {
      nameView = view.findViewById(R.id.nameNote);
      previewNote = view.findViewById(R.id.previewNote);
    }
  }
}
