package com.pasich.mynotes.View;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;

import java.util.Objects;

public class MainView {

  private final View view;
  public final Toolbar toolbar;
  public final TabLayout TabLayout;

    public MainView(View rootView){
        this.view = rootView;
    this.toolbar = rootView.findViewById(R.id.toolbar_actionbar);
    this.TabLayout = rootView.findViewById(R.id.Tags);
    setTabLayout();
    }

  private void setTabLayout() {
    Objects.requireNonNull(TabLayout.getTabAt(1)).select();
  }
}
