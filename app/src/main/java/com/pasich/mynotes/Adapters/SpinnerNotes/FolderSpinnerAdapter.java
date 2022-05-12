package com.pasich.mynotes.Adapters.SpinnerNotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pasich.mynotes.R;

import java.util.List;

public class FolderSpinnerAdapter extends BaseAdapter {

  private final List<String> Names;
  private final LayoutInflater inflater;

  public FolderSpinnerAdapter(Context applicationContext, List<String> Names) {
    this.Names = Names;
    inflater = (LayoutInflater.from(applicationContext));
  }

  @Override
  public int getCount() {
    return Names.size();
  }

  @Override
  public String getItem(int i) {
    return null;
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @SuppressLint("InflateParams")
  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder;
    if (view == null) {
      view = inflater.inflate(R.layout.spinner_list_folders, null);
      viewHolder = new ViewHolder(view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    viewHolder.names.setText(Names.get(i));
    return view;
  }

  private static class ViewHolder {
    final TextView names ;
    ViewHolder(View view) {
      names = view.findViewById(R.id.spinner_textFolders);
    }
  }
}
