package com.pasich.mynotes.di;

import android.app.Application;
import android.content.Context;

public class App extends Application {

  private static App sInstance;
  private ComponentsHolder componentsHolder;

  public static App getApp(Context context) {
    return (App) context.getApplicationContext();
  }

  public static App getInstance() {
    return sInstance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    componentsHolder = new ComponentsHolder(this);
    sInstance = this;
    componentsHolder.init();
  }

  public ComponentsHolder getComponentsHolder() {
    return componentsHolder;
  }
}
