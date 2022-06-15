package com.pasich.mynotes.di.main;

import com.pasich.mynotes.base.dagger.ActivityModule;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.utils.MainUtils;
import com.pasich.mynotes.utils.other.FormatListUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule implements ActivityModule {

  public MainActivityModule() {}


  @MainActivityScope
  @Provides
  MainContract.presenter providerMainPresenter() {
    return new MainPresenter();
  }

  @MainActivityScope
  @Provides
  MainUtils providerMainUtils() {
    return new MainUtils();
  }

  @MainActivityScope
  @Provides
  FormatListUtils providerFormatListUtils() {
    return new FormatListUtils();
  }


}
