package com.pasich.mynotes.utils.simplifications;

import android.view.animation.Animation;

public abstract class AnimationListener implements Animation.AnimationListener {

  public abstract void animationEnd();

  @Override
  public void onAnimationStart(Animation animation) {
  }

  @Override
  public void onAnimationEnd(Animation animation) {
    animationEnd();
  }

  @Override
  public void onAnimationRepeat(Animation animation) {
  }
}


