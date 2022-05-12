package com.pasich.mynotes.Adapters.SourceNoteList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasich.mynotes.R;

import java.util.List;

public class SourceListAdapter extends ArrayAdapter<SourceListContent> {

  private final LayoutInflater inflater;
  private final int layout;
  private final List<SourceListContent> SourceListContent;

  public SourceListAdapter(
      Context context, int resource, List<SourceListContent> SourceListContent) {
    super(context, resource, SourceListContent);
    this.SourceListContent = SourceListContent;
    this.layout = resource;
    this.inflater = LayoutInflater.from(getContext());
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    SourceListContent listNotesfor = SourceListContent.get(position);

    ViewHolder viewHolder;
    if (convertView == null) {
      convertView = inflater.inflate(this.layout, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    // Обработка именни пункта
    String sourceItem = listNotesfor.getSource();
    String typeItem = listNotesfor.getType();
    viewHolder.sourceView.setLines(1);

    switch (typeItem) {
      case "Url":
        viewHolder.imgSource.setVisibility(View.VISIBLE);
        viewHolder.imgSource.setImageResource(R.drawable.ic_url);
        break;
      case "Tel":
        viewHolder.imgSource.setVisibility(View.VISIBLE);
        viewHolder.imgSource.setImageResource(R.drawable.ic_tel);
        break;
      case "Mail":
        viewHolder.imgSource.setVisibility(View.VISIBLE);
        viewHolder.imgSource.setImageResource(R.drawable.ic_mail);
        break;
    }

    viewHolder.sourceView.setText(sourceItem);
    return convertView;
  }

  private static class ViewHolder {
    final TextView sourceView;
    final ImageView imgSource;

    ViewHolder(View view) {
      sourceView = view.findViewById(R.id.nameSource);
      imgSource = view.findViewById(R.id.imageSource);
    }
  }
}
