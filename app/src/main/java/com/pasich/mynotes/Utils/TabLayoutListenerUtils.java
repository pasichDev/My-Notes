package com.pasich.mynotes.Utils;

import com.google.android.material.tabs.TabLayout;

/** A class that makes it easy to use TabLayout.OnTabSelectedListener */
public abstract class TabLayoutListenerUtils implements TabLayout.OnTabSelectedListener {

  public abstract void listener(TabLayout.Tab TabLayout);

  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    listener(tab);
  }

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {}

  @Override
  public void onTabReselected(TabLayout.Tab tab) {}
}
