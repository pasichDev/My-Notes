package com.pasich.mynotes.view.main.dagger;

import com.pasich.mynotes.base.dagger.ActivityModule;
import com.pasich.mynotes.contract.TagsContract;
import com.pasich.mynotes.presenter.TagsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule implements ActivityModule {

  public MainActivityModule() {}

  @MainActivityScope
  @Provides
  TagsContract.presenter providerTagsPresenter() {
    return new TagsPresenter();
  }
}
