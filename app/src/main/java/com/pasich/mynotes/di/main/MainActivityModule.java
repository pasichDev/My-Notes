package com.pasich.mynotes.di.main;


import com.pasich.mynotes.base.dagger.ActivityModule;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.utils.FormatListUtils;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.actionPanel.tool.NoteActionTool;
import com.pasich.mynotes.utils.activity.MainUtils;

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


    @MainActivityScope
    @Provides
    ActionUtils providerActionUtil() {
        return new ActionUtils();
    }

    @MainActivityScope
    @Provides
    NoteActionTool providerNoteActionTool() {
        return new NoteActionTool();
    }


}
