package com.pasich.mynotes.Adapters.SpinnerNotes;

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
    private final LayoutInflater inflter;

    public FolderSpinnerAdapter(Context applicationContext, List<String> Names) {
        this.Names = Names;
        inflter = (LayoutInflater.from(applicationContext));
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_list_folders, null);
        TextView names = view.findViewById(R.id.spinner_textFolders);
        names.setText(Names.get(i));
        return view;
    }
}