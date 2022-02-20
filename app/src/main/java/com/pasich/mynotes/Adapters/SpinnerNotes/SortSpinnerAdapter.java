package com.pasich.mynotes.Adapters.SpinnerNotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pasich.mynotes.R;

public class SortSpinnerAdapter extends BaseAdapter {
    private final String[]  Names;
    private final LayoutInflater inflter;

    public SortSpinnerAdapter(Context applicationContext, String[] Names) {
        this.Names = Names;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Names.length;
    }

    @Override
    public String getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_sort_list, null);
        TextView names =  view.findViewById(R.id.spinnerSortText);

        names.setText(Names[i]);
        return view;
    }
}