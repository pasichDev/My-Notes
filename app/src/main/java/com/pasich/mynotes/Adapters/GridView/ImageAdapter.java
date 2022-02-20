package com.pasich.mynotes.Adapters.GridView;

import static com.pasich.mynotes.Сore.SystemCostant.ColorThemePrimary;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.pasich.mynotes.R;

public class ImageAdapter extends BaseAdapter {
    /**
     * Переменые для адаптера
     * colors = импортируеться из SystemConstant
     */
    private Context mContext;
    private final Integer[] colors = ColorThemePrimary;

    /**
     * Constructor to Adapter
     * @param c - контекс активности скоторой вызываем адаптер
     */
    public ImageAdapter(Context c) {
        mContext = c;
    }

    /**
     * Количество доступных цветов
     * @return
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
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
            imageView.setImageResource(R.drawable.ic_circle);
            imageView.setColorFilter(mContext.getResources().getColor(colors[position]));
        return imageView;
    }

}