package com.pasich.mynotes.View;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.pasich.mynotes.R;

public class MainView {

  private final View view;
    public final Toolbar toolbar;


    public MainView(View rootView){
        this.view = rootView;
    this.toolbar = rootView.findViewById(R.id.toolbar_actionbar);
    }





}
