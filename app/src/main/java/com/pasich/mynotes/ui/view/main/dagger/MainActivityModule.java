package com.pasich.mynotes.ui.view.main.dagger;

import com.pasich.mynotes.base.dagger.ActivityModule;
import com.pasich.mynotes.ui.contract.TagsContract;
import com.pasich.mynotes.ui.presenter.TagsPresenter;

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
