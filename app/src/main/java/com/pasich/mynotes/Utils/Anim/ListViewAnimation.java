package com.pasich.mynotes.Utils.Anim;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ListView;

public class ListViewAnimation {

  public static void setListviewAnimationLeftToShow(ListView listView) {
    listView.setLayoutAnimation(animateLeftToShow());
  }

  public static void setListviewAnimationLeftToShow(GridView listView) {
    listView.setLayoutAnimation(animateLeftToShow());
  }

  public static void setListviewAnimAlphaTranslate(ListView listView) {
    listView.setAnimation(animAlphaTranslate());
  }

  public static void setListviewAnimAlphaTranslate(GridView listView) {
    listView.setAnimation(animAlphaTranslate());
  }

  protected static LayoutAnimationController animateLeftToShow() {
    ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1);
    sa.setDuration(300);
    return new LayoutAnimationController(sa, 0.5f);
  }

  protected static AnimationSet animAlphaTranslate() {
    TranslateAnimation translateAnimation = new TranslateAnimation(300, 0, 0, 0);
    Animation alphaAnimation = new AlphaAnimation(0, 1);
    translateAnimation.setDuration(500);
    alphaAnimation.setDuration(500);
    AnimationSet animation = new AnimationSet(true);
    animation.addAnimation(translateAnimation);
    animation.addAnimation(alphaAnimation);
    return animation;
  }




}
