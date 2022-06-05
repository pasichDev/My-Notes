package com.pasich.mynotes.utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.models.adapter.ChoiceModel;

import java.util.ArrayList;

public class DialogListAdapter extends ArrayAdapter<ChoiceModel> {

  private final LayoutInflater inflater;
  private final int layout;
  private final ArrayList<ChoiceModel> listNotes;

  public DialogListAdapter(Context context, int resource, ArrayList<ChoiceModel> list) {
    super(context, resource, list);
    this.listNotes = list;
    this.layout = resource;
    this.inflater = LayoutInflater.from(getContext());
  }

  @Override
  public ChoiceModel getItem(int i) {
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
    /** Желательно реализовать другой вариант, Этот немного тупой */
    if (getItem(position).getSelected()) {
      viewHolder.nameView.setTextAppearance(R.style.selectedText);
      convertView.setBackground(getContext().getDrawable(R.drawable.item_list_selected));
    } else {
      viewHolder.nameView.setTextAppearance(R.style.nullText);
      convertView.setBackground(getContext().getDrawable(R.drawable.item_list_selected_false));
    }
    return convertView;
  }

  public ArrayList<ChoiceModel> getData() {
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
