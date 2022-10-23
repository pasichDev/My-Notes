package com.pasich.mynotes.di.main;


import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

  @Provides
  public MainContract.presenter providerPresenter() {
    return new MainPresenter();
  }





}
