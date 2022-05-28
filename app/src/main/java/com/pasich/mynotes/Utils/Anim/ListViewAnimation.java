package com.pasich.mynotes.Utils.Anim;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;

public class ListViewAnimation {

  public static void setListviewAnimationLeftToShow(ListView listView) {
    listView.setLayoutAnimation(animateLeftToShow());
  }

  protected static LayoutAnimationController animateLeftToShow() {
    ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1);
    sa.setDuration(300);
    return new LayoutAnimationController(sa, 0.5f);
  }




}
