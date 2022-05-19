package com.pasich.mynotes.View;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;

public class SettingsView {

    protected final View view;
    public final Toolbar toolbar;
    public final ViewPager2 viewPager;
    public final TabLayout tabLayout;

    public SettingsView(View rootView){
        this.view = rootView;
        this.toolbar = view.findViewById(R.id.toolbar_actionbar);
        this.viewPager = view.findViewById(R.id.settingsViewPager);
        this.tabLayout = view.findViewById(R.id.settingsTabLayout);
    }





}
