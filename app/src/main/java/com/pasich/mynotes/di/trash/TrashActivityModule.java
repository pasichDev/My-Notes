package com.pasich.mynotes.di.trash;


import com.pasich.mynotes.base.dagger.ActivityModule;
import com.pasich.mynotes.di.main.MainActivityScope;
import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.ui.presenter.TrashPresenter;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.actionPanel.tool.TrashNoteActionTool;

import dagger.Module;
import dagger.Provides;

@Module
public class TrashActivityModule implements ActivityModule {

    public TrashActivityModule() {
    }


    @MainActivityScope
    @Provides
    TrashContract.presenter providerTrashPresenter() {
        return new TrashPresenter();
    }

    @MainActivityScope
    @Provides
    ActionUtils providerActionUtil() {
        return new ActionUtils();
    }

    @MainActivityScope
    @Provides
    TrashNoteActionTool providerTrashNoteActionTool() {
        return new TrashNoteActionTool();
    }


}
