package com.pasich.mynotes.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasich.mynotes.Model.Adapter.MoreChoiceModel;
import com.pasich.mynotes.R;

import java.util.ArrayList;

public class MoreListAdapter extends ArrayAdapter<MoreChoiceModel> {

  private final LayoutInflater inflater;
  private final int layout;
  private final ArrayList<MoreChoiceModel> listNotes;

  public MoreListAdapter(Context context, int resource, ArrayList<MoreChoiceModel> list) {
    super(context, resource, list);
    this.listNotes = list;
    this.layout = resource;
    this.inflater = LayoutInflater.from(getContext());
  }

  @Override
  public MoreChoiceModel getItem(int i) {
    return listNotes != null ? listNotes.get(i) : null;
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


    viewHolder.nameView.setText(getItem(position).getName());
    viewHolder.iconVIew.setImageResource(getItem(position).getIcon());
    return convertView;
  }

  public ArrayList<MoreChoiceModel> getData() {
    return this.listNotes;
  }

  private static class ViewHolder {
    final TextView nameView;
    final ImageView iconVIew;

    ViewHolder(View view) {
      nameView = view.findViewById(R.id.nameSource);
      iconVIew = view.findViewById(R.id.imageSource);
    }
  }
}
