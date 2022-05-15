package com.pasich.mynotes.Utils.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.pasich.mynotes.R;

public class ColorsAdapter extends BaseAdapter {
  private final Context mContext;
  private final Integer[] colors = {
  };

  /**
   * Constructor to Adapter
   *
   * @param c - activity context with which we call the adapter
   */
  public ColorsAdapter(Context c) {
    mContext = c;
  }

  /**
   * Number of colors available
   *
   * @return - int
   */
  public int getCount() {
    return colors.length;
  }

  public Object getItem(int position) {
    return colors[position];
  }

  public long getItemId(int position) {
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
   final ImageView imageView;
    if (convertView == null) {
      imageView = new ImageView(mContext);
      imageView.setLayoutParams(
          new GridView.LayoutParams(
              ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    } else {
      imageView = (ImageView) convertView;
    }
    imageView.setImageResource(R.drawable.ic_circle);
    imageView.setColorFilter(mContext.getResources().getColor(colors[position]));
    return imageView;
  }
}
