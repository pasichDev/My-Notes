package com.pasich.mynotes.View;

import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;

public class MainView {

    protected final View view;
    public final Toolbar toolbar;
    public final ImageButton sortButton, formatButton;
    public boolean onCreate = false;
    public ViewPager viewPager;
    public TabLayout tabLayout;


    public MainView(View rootView){
        this.view = rootView;
        this.toolbar = view.findViewById(R.id.toolbar_actionbar);
        this.sortButton = view.findViewById(R.id.sortButton);
        this.formatButton = view.findViewById(R.id.formatButton);
        this.viewPager = view.findViewById(R.id.viewpager);
        this.tabLayout = view.findViewById(R.id.tabModeMain);
    }





}
