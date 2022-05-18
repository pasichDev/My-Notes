package com.pasich.mynotes.Utils.Anim;

import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.GridView;
import android.widget.ListView;

public class ListViewAnimation {

  protected final LayoutAnimationController lac;

  public ListViewAnimation() {
    ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1);
    sa.setDuration(300);
    this.lac = new LayoutAnimationController(sa, 0.5f);
  }

  public void setListviewAnimation(ListView listView) {
    listView.setLayoutAnimation(lac);
  }

  public void setListviewAnimation(GridView listView) {
    listView.setLayoutAnimation(lac);
  }
}
