package com.pasich.mynotes.View;

import android.view.View;
import android.widget.GridView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;

public class TrashView {

    protected final View view;
    public final Toolbar toolbar;
    public GridView trashNotesList;

    public TrashView(View rootView){
        this.view = rootView;
        this.toolbar = view.findViewById(R.id.toolbar_actionbar);
       this.trashNotesList = view.findViewById(R.id.ListTrash);
        setToolbar();
    }

    private void setToolbar(){
        toolbar.setTitle(R.string.trashN);

    }



}
