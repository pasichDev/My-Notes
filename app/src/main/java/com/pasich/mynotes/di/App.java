package com.pasich.mynotes.di;

import static com.preference.provider.PreferenceProvider.context;

import android.app.Application;

public class App extends Application {

  private static App sInstance;
  private ComponentsHolder componentsHolder;

  public static App getApp() {
    assert context != null;
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
