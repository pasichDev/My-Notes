package com.pasich.mynotes.Adapters.ListNotes;

import static com.pasich.mynotes.Utils.File.FileCore.getWithoutExtension;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasich.mynotes.R;

import java.util.List;

public class DefaultListAdapter extends ArrayAdapter<ListNotesModel> {

  private final LayoutInflater inflater;
  private final int layout;
  private final List<ListNotesModel> listNotes;

  public DefaultListAdapter(Context context, int resource, List<ListNotesModel> listNotes) {
    super(context, resource, listNotes);
    this.listNotes = listNotes;
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
    ListNotesModel object = listNotes.get(position);

    ViewHolder viewHolder;
    if (convertView == null) {
      convertView = inflater.inflate(this.layout, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    String nameItem = object.getNameList();
    String dateItem = object.getDateList();

    viewHolder.imgFolder.setVisibility(View.VISIBLE);
    if (object.getBackFolder()) {
      viewHolder.imgFolder.setImageResource(R.drawable.ic_return_folder);
    } else if (object.getFolder()) {
      viewHolder.imgFolder.setImageResource(R.drawable.ic_folder);
    } else {
      viewHolder.imgFolder.setImageResource(R.drawable.ic_note);
    }
    nameItem = getWithoutExtension(nameItem);
    if (nameItem.length() > 49) nameItem = nameItem + "...";

    viewHolder.nameView.setText(nameItem);
    viewHolder.dateView.setText(dateItem);

    return convertView;
  }

  private static class ViewHolder {
    final TextView nameView, dateView;
    final ImageView imgFolder;

    ViewHolder(View view) {
      nameView = view.findViewById(R.id.nameNotesL);
      dateView = view.findViewById(R.id.dateNotesL);
      imgFolder = view.findViewById(R.id.imageFolder);
    }
  }

}
