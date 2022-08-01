package com.pasich.mynotes.di.trash;


import com.pasich.mynotes.base.dagger.ActivityModule;
import com.pasich.mynotes.di.main.MainActivityScope;
import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.ui.presenter.TrashPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class TrashActivityModule implements ActivityModule {

  public TrashActivityModule() {}


  @MainActivityScope
  @Provides
  TrashContract.presenter providerTrashPresenter() {
    return new TrashPresenter();
  }

}
