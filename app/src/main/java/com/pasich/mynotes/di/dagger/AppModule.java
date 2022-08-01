package com.pasich.mynotes.di.dagger;

import android.content.Context;

import com.pasich.mynotes.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  private final Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @AppScope
  @Provides
  Context provideContext() {
    return context;
  }

  @AppScope
  @Provides
  DataManager providerDataManager() {
    return new DataManager();
  }
}
