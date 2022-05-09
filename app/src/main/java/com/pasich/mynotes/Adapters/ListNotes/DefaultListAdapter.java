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
  private final List<ListNotesModel> listNotesfors;

  public DefaultListAdapter(Context context, int resource, List<ListNotesModel> listNotesfors) {
    super(context, resource, listNotesfors);
    this.listNotesfors = listNotesfors;
    this.layout = resource;
    this.inflater = LayoutInflater.from(getContext());
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    ListNotesModel listNotesfor = listNotesfors.get(position);

    ViewHolder viewHolder;
    if (convertView == null) {
      convertView = inflater.inflate(this.layout, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    String nameItem = listNotesfor.getNameList();
    String dateItem = listNotesfor.getDateList();

    viewHolder.imgFolder.setVisibility(View.VISIBLE);
    if (listNotesfor.getBackFolder()) {
      viewHolder.imgFolder.setImageResource(R.drawable.ic_return_folder);
    } else if (listNotesfor.getFolder()) {
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

  private class ViewHolder {
    final TextView nameView, dateView;
    final ImageView imgFolder;

    ViewHolder(View view) {
      nameView = view.findViewById(R.id.nameNotesL);
      dateView = view.findViewById(R.id.dateNotesL);
      imgFolder = view.findViewById(R.id.imageFolder);
    }
  }
}
