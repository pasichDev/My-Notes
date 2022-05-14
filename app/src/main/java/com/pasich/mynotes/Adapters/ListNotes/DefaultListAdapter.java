package com.pasich.mynotes.Adapters.ListNotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

    viewHolder.nameView.setText(getWithoutExtension(getItem(position).getNameList()));
    viewHolder.dateView.setText(getItem(position).getDateList());

    return convertView;
  }



  /**
   * A method that truncates the file format, and in this case removes the note extension
   * @param fileName - File name before trimming -
   * @return - Without Extension name file
   */
  private String getWithoutExtension(String fileName) {
      return fileName.endsWith("txt") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
  }

  private static class ViewHolder {
    final TextView nameView, dateView;

    ViewHolder(View view) {
      nameView = view.findViewById(R.id.nameNotesL);
      dateView = view.findViewById(R.id.dateNotesL);
    }
  }
}
